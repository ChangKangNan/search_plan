package org.ckn.sp.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.ckn.sp.annotation.InsertStrategyOrder;
import org.ckn.sp.annotation.UpdateStrategyOrder;
import org.ckn.sp.fm.action.SearchPlanNewConditionAction;
import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.bean.SearchPlanNewCondition;
import org.ckn.sp.fm.bean.SearchTableColumn;
import org.ckn.sp.fm.dao.SearchConfigInfoMapper;
import org.ckn.sp.fm.dao.SearchPlanNewConditionMapper;
import org.ckn.sp.service.IChangeTableService;
import org.ckn.sp.strategy.api.ISplitStrategy;
import org.ckn.sp.util.BusinessUtil;
import org.ckn.sp.util.RegxUtil;
import org.ckn.sp.util.SqlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ckn
 */
@InsertStrategyOrder
@UpdateStrategyOrder
public class SearchInfoISplitStrategy implements ISplitStrategy {

    @Override
    public void split(SearchConfig searchConfig) {
        String searchSql = searchConfig.getSearchSql();
        searchSql = SqlUtil.replaceLineSeparator(searchSql);
        List<String> remarks = RegxUtil.getRemarks(searchSql);
        List<SearchTableColumn> columnList = SqlUtil.getColumns(searchSql);
        String pageTag = searchConfig.getPageTag();
        List<SearchConfigInfo> infos = new ArrayList<>();
        for (int i = 0; i < columnList.size(); i++) {
            SearchTableColumn column = columnList.get(i);
            String searchTableColumnInfo = column.getSearchTableColumnInfo().trim();
            String searchKey;
            String searchTableColumn = column.getSearchTableColumn().trim();
            if (!searchTableColumn.equals(searchTableColumnInfo)) {
                searchKey = searchTableColumnInfo + "/*alias " + searchTableColumn + "*/";
            } else {
                searchKey = searchTableColumn;
            }
            String remark = remarks.get(i);
            SearchConfigInfo baseInfo = BusinessUtil.getBaseInfo(remark);
            baseInfo.setSearchConfigId(searchConfig.getId());
            baseInfo.setPageTag(pageTag);
            baseInfo.setSearchKey(searchKey);
            if (baseInfo.getChangeTb() == null) {
                baseInfo.setChangeTb(false);
            }
            infos.add(baseInfo);
        }
        //校验更新与插入
        handSearchConfigInfo(pageTag, infos);
        IChangeTableService changeTableService = SpringUtil.getBean(IChangeTableService.class);
        changeTableService.handleChangeTable(searchConfig, infos);
    }


    public boolean compareEqual(SearchConfigInfo searchConfigInfo, SearchConfigInfo info) {
        return StrUtil.equals(searchConfigInfo.getSearchName(), info.getSearchName())
                && StrUtil.equals(searchConfigInfo.getLimitSearchConditions(), info.getLimitSearchConditions())
                && StrUtil.equals(searchConfigInfo.getLimitSearchValues(), (info.getLimitSearchValues()))
                && StrUtil.equals(searchConfigInfo.getSearchValueSource(), (info.getSearchValueSource()))
                && StrUtil.equals(searchConfigInfo.getSearchType(), (info.getSearchType()))
                && BooleanUtil.isTrue(searchConfigInfo.getHidden()) == BooleanUtil.isTrue(info.getHidden())
                && StrUtil.equals(searchConfigInfo.getAxis(), info.getAxis())
                && StrUtil.equals(searchConfigInfo.getChangeTbName(), info.getChangeTbName())
                && StrUtil.equals(searchConfigInfo.getChangeDateRule(), info.getChangeDateRule())
                && BooleanUtil.isTrue(searchConfigInfo.getChangeTb() == info.getChangeTb())
                && StrUtil.equals(searchConfigInfo.getPoint(), info.getPoint())
                && StrUtil.equals(searchConfigInfo.getLimitSearchConditions(), info.getLimitSearchConditions())
                && BooleanUtil.isTrue(searchConfigInfo.getDefaultOrderBy()) == BooleanUtil.isTrue(info.getDefaultOrderBy())
                && BooleanUtil.isTrue(searchConfigInfo.getDefaultOrderByDesc()) == BooleanUtil.isTrue(info.getDefaultOrderByDesc());
    }


    public void handSearchConfigInfo(String pageTag, List<SearchConfigInfo> infos) {
        List<SearchConfigInfo> searchConfigInfos = SearchConfigInfoMapper.lambdaQuery().pageTag().equal(pageTag).list();
        if (CollUtil.isNotEmpty(searchConfigInfos)) {
            final Map<String, SearchConfigInfo> collect = searchConfigInfos.stream().collect(Collectors.toMap(SearchConfigInfo::getSearchKey, data -> data));
            List<SearchConfigInfo> addList = infos.stream().filter(info -> collect.get(info.getSearchKey()) == null).collect(Collectors.toList());
            List<SearchConfigInfo> updateList = infos.stream().filter(info -> collect.get(info.getSearchKey()) != null && !compareEqual(collect.get(info.getSearchKey()), info)).collect(Collectors.toList());
            List<SearchConfigInfo> originalData = infos.stream().filter(info -> collect.get(info.getSearchKey()) != null && compareEqual(collect.get(info.getSearchKey()), info)).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(updateList)) {
                for (SearchConfigInfo t : updateList) {
                    SearchConfigInfo configInfo = collect.get(t.getSearchKey());
                    t.setId(configInfo.getId());
                    t.setCreateTime(configInfo.getCreateTime());
                    t.setDeleted(configInfo.getDeleted());
                    SearchConfigInfoMapper.lambdaUpdate().id().equal(configInfo.getId()).updateOverride(t);
                    //刷新所有用户方案对应输入框的类型
                    if (t.getLimitSearchConditions().equals(configInfo.getLimitSearchConditions())) {
                        continue;
                    }
                    SearchPlanNewConditionAction.BaseSelectMapper baseSelectMapper = SearchPlanNewConditionMapper.lambdaQuery();
                    baseSelectMapper.pageTag().equal(pageTag);
                    int indexOf = t.getSearchKey().indexOf("/*alias");
                    if (indexOf != -1) {
                        baseSelectMapper.searchColumn().like(StrUtil.trimEnd(t.getSearchKey().substring(indexOf)));
                    } else {
                        baseSelectMapper.searchColumn().equal(t.getSearchKey());
                    }
                    List<SearchPlanNewCondition> planNewConditions = baseSelectMapper.list();
                    if (CollUtil.isEmpty(planNewConditions)) {
                        continue;
                    }
                    for (SearchPlanNewCondition newCondition : planNewConditions) {
                        updateSearchCondition(newCondition, t);
                    }
                }
            }
            infos = addList;
            List<Long> sum = new ArrayList<>();
            if (CollUtil.isNotEmpty(originalData)) {
                List<Long> longs = originalData.stream().map(t -> collect.get(t.getSearchKey()).getId()).collect(Collectors.toList());
                CollUtil.addAllIfNotContains(sum, longs);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                List<Long> longs = updateList.stream().map(t -> collect.get(t.getSearchKey()).getId()).collect(Collectors.toList());
                CollUtil.addAllIfNotContains(sum, longs);
            }
            if (CollUtil.isNotEmpty(sum)) {
                SearchConfigInfoMapper.lambdaDelete().id().notIn(sum).pageTag().equal(pageTag).closeDeletedProtect().delete();
                SearchPlanNewConditionMapper.lambdaDelete().searchConfigInfoId().notIn(sum).pageTag().equal(pageTag).closeDeletedProtect().delete();
            } else {
                SearchConfigInfoMapper.lambdaDelete().pageTag().equal(pageTag).closeDeletedProtect().delete();
                SearchPlanNewConditionMapper.lambdaDelete().pageTag().equal(pageTag).closeDeletedProtect().delete();
            }
        }
        if (CollUtil.isNotEmpty(infos)) {
            SearchConfigInfoMapper.lambdaInsert().insertBatch(infos);
        }
    }

    private void updateSearchCondition(SearchPlanNewCondition planNewCondition, SearchConfigInfo configInfo) {
        String limitSearchConditions = configInfo.getLimitSearchConditions();
        JSONArray jsonArray = JSONUtil.parseArray(limitSearchConditions);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        Object value = jsonObject.get("value");
        SearchPlanNewCondition newCondition = new SearchPlanNewCondition();
        newCondition.setSearchCondition(value + "");
        SearchPlanNewConditionMapper.lambdaUpdate().id().equal(planNewCondition.getId()).update(newCondition);
    }


}
