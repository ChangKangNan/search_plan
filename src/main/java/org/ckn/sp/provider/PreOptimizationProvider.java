package org.ckn.sp.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.dto.OptimizationDTO;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.fm.bean.SearchTableMain;
import org.ckn.sp.fm.bean.SearchTableMainRelation;
import org.ckn.sp.fm.bean.SearchTableMainRelationLink;
import org.ckn.sp.fm.dao.SearchTableMainMapper;
import org.ckn.sp.fm.dao.SearchTableMainRelationLinkMapper;
import org.ckn.sp.fm.dao.SearchTableMainRelationMapper;
import org.ckn.sp.util.CopyUtil;
import org.ckn.sp.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

import static org.ckn.sp.constants.StrConstants.POINT;

/**
 * @author ckn
 */
public class PreOptimizationProvider {

    public static void handle(SearchQueryDTO searchQueryDTO){
        String pageTag = searchQueryDTO.getPageTag();
        SearchTableMain searchTableMain = SearchTableMainMapper.lambdaQuery().pageTag().equal(pageTag).one();
        if (ObjectUtil.isNull(searchTableMain)) {
            return;
        }
        List<SearchQueryDTO.QueryInfo> queryInfoList = searchQueryDTO.getQueryInfoList();
        if (CollUtil.isEmpty(queryInfoList)) {
            return;
        }
        Long mainId = searchTableMain.getId();
        List<SearchTableMainRelation> mainRelations = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(mainId).list();
        String mainTableAlias = searchTableMain.getAlias();
        String searchTableName = searchTableMain.getSearchTableName();
        String mainTb = (StrUtil.isBlank(mainTableAlias) ? searchTableName : mainTableAlias).trim();
        List<OptimizationDTO> optimizationList = new ArrayList<>();
        Boolean isExtend = searchTableMain.getIsExtend();
        List<SearchQueryDTO.QueryInfo> addQueryInfos = new ArrayList<>();
        if (isExtend != null && isExtend) {
            for (SearchQueryDTO.QueryInfo queryInfo : queryInfoList) {
                String searchKey = queryInfo.getSearchKey();
                List<String> column = SQLUtils.getSQLCutColumnOrTable(searchKey, "COLUMN");
                if (CollUtil.isNotEmpty(column) && column.size() == 1 && queryInfo.getSearchConfigInnerConditionId() == null) {
                    SearchQueryDTO.QueryInfo info = CopyUtil.copy(queryInfo, SearchQueryDTO.QueryInfo.class);
                    info.setAreInnerFilter(1);
                    addQueryInfos.add(info);
                }
            }
        }
        queryInfoList.addAll(addQueryInfos);
        if (CollUtil.isEmpty(mainRelations)) {
            return;
        }
        for (SearchTableMainRelation tableMainRelation : mainRelations) {
            List<SearchTableMainRelationLink> mainRelationLinks = SearchTableMainRelationLinkMapper.lambdaQuery().searchTableMainRelationId().equal(tableMainRelation.getId()).list();
            if (CollUtil.isEmpty(mainRelationLinks)) {
                continue;
            }
            String targetTableAlias = tableMainRelation.getTargetTableAlias();
            for (SearchTableMainRelationLink relationLink : mainRelationLinks) {
                StrBuilder leftBuilder = new StrBuilder();
                OptimizationDTO optimizationDTO = new OptimizationDTO();
                optimizationDTO.setExtend(tableMainRelation.getIsExtend());
                if (!StrUtil.containsIgnoreCase(relationLink.getTableColumnName(), "(")
                        && !StrUtil.containsIgnoreCase(relationLink.getTableColumnName(), ")")) {
                    if (StrUtil.isNotBlank(targetTableAlias)) {
                        leftBuilder.append(targetTableAlias);
                        optimizationDTO.setTable(targetTableAlias);
                    } else {
                        if (StrUtil.isNotBlank(tableMainRelation.getTargetTableName())) {
                            leftBuilder.append(tableMainRelation.getTargetTableName());
                            optimizationDTO.setTable(tableMainRelation.getTargetTableName());
                        } else {
                            leftBuilder.append(mainTb);
                            optimizationDTO.setTable(mainTb);
                        }
                    }
                    leftBuilder.append(POINT);
                }
                leftBuilder.append(relationLink.getTableColumnName().trim());
                optimizationDTO.setLeft(leftBuilder.toString());
                StrBuilder rightBuilder = new StrBuilder();
                String tableAlias = relationLink.getTargetTableAlias();
                String targetTableColumnName = relationLink.getTargetTableColumnName();
                if (!StrUtil.containsIgnoreCase(targetTableColumnName, "(")
                        && !StrUtil.containsIgnoreCase(targetTableColumnName, ")")) {
                    rightBuilder.append(StrUtil.isNotBlank(tableAlias) ? tableAlias : StrUtil.isBlank(relationLink.getTargetTableName()) ? mainTb : relationLink.getTargetTableName());
                    rightBuilder.append(POINT);
                }
                rightBuilder.append(relationLink.getTargetTableColumnName());
                optimizationDTO.setRight(rightBuilder.toString());
                optimizationList.add(optimizationDTO);
            }
        }
        if (CollUtil.isEmpty(optimizationList)) {
            return;
        }
        List<SearchQueryDTO.QueryInfo> opTarget = new ArrayList<>();
        for (SearchQueryDTO.QueryInfo queryInfo : queryInfoList) {
            Long searchConfigInfoId = queryInfo.getSearchConfigInfoId();
            if (searchConfigInfoId == null) {
                continue;
            }
            String searchGroupRelation = queryInfo.getSearchGroupRelation();
            if (StrUtil.isNotBlank(searchGroupRelation) && StrUtil.equalsIgnoreCase(searchGroupRelation, "or")) {
                continue;
            }
            String searchKey = queryInfo.getSearchKey();
            List<String> column = SQLUtils.getSQLCutColumnOrTable(searchKey, "COLUMN");
            if (CollUtil.isNotEmpty(column) && column.size() == 1 && queryInfo.getAreInnerFilter() == 0) {
                opTarget.add(queryInfo);
            }
        }
        if (CollUtil.isEmpty(opTarget)) {
            return;
        }
        List<SearchQueryDTO.QueryInfo> newSearchCondition = new ArrayList<>();
        for (SearchQueryDTO.QueryInfo queryInfo : opTarget) {
            String key = queryInfo.getSearchKey().replaceAll("/\\*alias\\s(.*)\\*/", "").trim();
            String column = SQLUtils.getSQLCutColumnOrTable(queryInfo.getSearchKey(), "COLUMN").get(0);
            String table = SQLUtils.getSQLCutColumnOrTable(queryInfo.getSearchKey(), "TABLE").get(0);
            if (StrUtil.equals(table, "MAIN_TABLE")) {
                table = StrUtil.isBlank(searchTableMain.getAlias()) ? searchTableMain.getSearchTableName() : searchTableMain.getAlias();
            }
            String column_info = table + "." + column;
            for (OptimizationDTO optimizationDTO : optimizationList) {
                String right = optimizationDTO.getRight();
                if (right.equals(key) || right.equals(column_info)) {
                    SearchQueryDTO.QueryInfo info = CopyUtil.copy(queryInfo, SearchQueryDTO.QueryInfo.class);
                    String sk = key.contains(".") ? key.replaceAll(column_info, optimizationDTO.getLeft()) : key.replaceAll(column, optimizationDTO.getLeft());
                    info.setSearchKey(sk);
                    info.setAreInnerFilter(1);
                    newSearchCondition.add(info);
                }
            }
        }
        queryInfoList.addAll(newSearchCondition);
        List<SearchQueryDTO.QueryInfo> news = new ArrayList<>();
        catchAllCondition(newSearchCondition, optimizationList, news);
        if (CollUtil.isEmpty(news)) {
            return;
        }
        queryInfoList.addAll(news);
    }

    private static void catchAllCondition(List<SearchQueryDTO.QueryInfo> queryInfos, List<OptimizationDTO> optimizationList, List<SearchQueryDTO.QueryInfo> news) {
        List<SearchQueryDTO.QueryInfo> newSearchCondition = new ArrayList<>();
        for (SearchQueryDTO.QueryInfo queryInfo : queryInfos) {
            String key = queryInfo.getSearchKey().replaceAll("/\\*alias\\s(.*)\\*/", "").trim();
            for (OptimizationDTO optimizationDTO : optimizationList) {
                String right = optimizationDTO.getRight();
                if (right.equals(key)) {
                    SearchQueryDTO.QueryInfo info = CopyUtil.copy(queryInfo, SearchQueryDTO.QueryInfo.class);
                    info.setSearchKey(optimizationDTO.getLeft());
                    info.setAreInnerFilter(1);
                    newSearchCondition.add(info);
                }
            }
        }
        if (CollUtil.isEmpty(newSearchCondition)) {
            return;
        }
        news.addAll(newSearchCondition);
        catchAllCondition(newSearchCondition, optimizationList, news);
    }
}
