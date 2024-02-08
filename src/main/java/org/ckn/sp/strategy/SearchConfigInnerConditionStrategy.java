package org.ckn.sp.strategy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.annotation.InsertStrategyOrder;
import org.ckn.sp.annotation.UpdateStrategyOrder;
import org.ckn.sp.constants.StrConstants;
import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.bean.SearchConfigInnerCondition;
import org.ckn.sp.fm.dao.SearchConfigInnerConditionMapper;
import org.ckn.sp.strategy.api.ISplitStrategy;
import org.ckn.sp.util.BusinessUtil;
import org.ckn.sp.util.RegxUtil;
import org.ckn.sp.util.SqlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ckn
 */
@InsertStrategyOrder(index = 1)
@UpdateStrategyOrder(index = 1)
public class SearchConfigInnerConditionStrategy implements ISplitStrategy {

    @Override
    public void split(SearchConfig searchConfig) {
        String searchSql = searchConfig.getSearchSql();
        searchSql = SqlUtil.replaceLineSeparator(searchSql);
        List<String> inner = RegxUtil.getInnerCondition(searchSql);
        String pageTag = searchConfig.getPageTag();
        if (CollUtil.isEmpty(inner)) {
            return;
        }
        int innerConditions = SearchConfigInnerConditionMapper.lambdaQuery().pageTag().equal(pageTag).count();
        if (innerConditions > 0) {
            SearchConfigInnerConditionMapper.lambdaDelete().pageTag().equal(pageTag)
                    .closeDeletedProtect()
                    .delete();
        }
        List<SearchConfigInnerCondition> searchConfigInnerConditions = new ArrayList<>();
        for (String condition : inner) {
            String[] condition_split = condition.split(StrUtil.SPACE);
            String searchName = condition_split[0];
            String axis = condition_split[1];
            String relation = condition_split[2];
            boolean containsDefault = condition.contains(StrConstants.INNER_DEFAULT);
            String remark = containsDefault ?
                    Arrays.stream(condition_split).skip(3).collect(Collectors.joining(StrUtil.SPACE))
                    :
                    Arrays.stream(condition_split).skip(4).collect(Collectors.joining(StrUtil.SPACE));
            SearchConfigInfo baseInfo = BusinessUtil.getBaseInfo(remark);
            SearchConfigInnerCondition searchConfigInnerCondition = BeanUtil.copyProperties(baseInfo, SearchConfigInnerCondition.class);
            searchConfigInnerCondition.setId(null);
            searchConfigInnerCondition.setSearchConfigId(searchConfig.getId());
            searchConfigInnerCondition.setPageTag(pageTag);
            searchConfigInnerCondition.setAxis(getRightVal(axis));
            searchConfigInnerCondition.setRelation(getRightVal(relation));
            searchConfigInnerCondition.setSearchKey(searchName);
            searchConfigInnerConditions.add(searchConfigInnerCondition);
        }
        SearchConfigInnerConditionMapper.lambdaInsert().insertBatch(searchConfigInnerConditions);
    }
    public static String getRightVal(String key) {
        return key.split("=")[1];
    }
}
