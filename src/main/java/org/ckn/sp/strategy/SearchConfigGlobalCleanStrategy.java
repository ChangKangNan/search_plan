package org.ckn.sp.strategy;

import cn.hutool.core.collection.CollUtil;
import org.ckn.sp.annotation.InsertStrategyOrder;
import org.ckn.sp.annotation.UpdateStrategyOrder;
import org.ckn.sp.fm.bean.*;
import org.ckn.sp.fm.dao.*;
import org.ckn.sp.strategy.api.ISplitStrategy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ckn
 */
@InsertStrategyOrder(index = 2)
@UpdateStrategyOrder(index = 2)
public class SearchConfigGlobalCleanStrategy implements ISplitStrategy {

    @Override
    public void split(SearchConfig searchConfig) {
        String pageTag = searchConfig.getPageTag();
        List<SearchTableMain> searchTableMains = SearchTableMainMapper.lambdaQuery().pageTag().equal(pageTag).list();
        if (CollUtil.isEmpty(searchTableMains)) {
            return;
        }
        for (SearchTableMain tableMain : searchTableMains) {
            List<SearchTableMainRelation> mainRelations = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(tableMain.getId()).list();
            if (CollUtil.isNotEmpty(mainRelations)) {
                List<Long> collect = mainRelations.stream().map(SearchTableMainRelation::getId).collect(Collectors.toList());
                SearchTableMainRelationLinkMapper.lambdaDelete().searchTableMainRelationId().in(collect).closeDeletedProtect().delete();
                SearchTableMainRelationWhereMapper.lambdaDelete().searchTableMainRelationId().in(collect).closeDeletedProtect().delete();
            }
            SearchTableMainRelationMapper.lambdaDelete().searchTableMainId().in(tableMain.getId()).closeDeletedProtect().delete();
            SearchExtendTableMapper.lambdaDelete().searchTableMainId().in(tableMain.getId()).closeDeletedProtect().delete();
        }
        List<SearchTableColumn> tableColumns = SearchTableColumnMapper.lambdaQuery().pageTag().equal(pageTag).list();
        if (CollUtil.isNotEmpty(tableColumns)) {
            List<Long> longList = tableColumns.stream().map(SearchTableColumn::getId).collect(Collectors.toList());
            SearchTableColumnTargetMapper.lambdaDelete().searchTableColumnId().in(longList).closeDeletedProtect().delete();
        }
        List<SearchTableWhere> tableWheres = SearchTableWhereMapper.lambdaQuery().pageTag().equal(pageTag).list();
        if (CollUtil.isNotEmpty(tableWheres)) {
            List<Long> longList = tableWheres.stream().map(SearchTableWhere::getId).collect(Collectors.toList());
            SearchTableWhereRelationMapper.lambdaDelete().searchTableWhereId().in(longList).closeDeletedProtect().delete();
            SearchTableWhereMapper.lambdaDelete().pageTag().equal(pageTag).closeDeletedProtect().delete();
        }
        SearchTableColumnMapper.lambdaDelete().pageTag().equal(pageTag).closeDeletedProtect().delete();
        SearchTableMainMapper.lambdaDelete().pageTag().equal(pageTag).closeDeletedProtect().delete();

        List<SearchTableGroup> tableGroups = SearchTableGroupMapper.lambdaQuery().pageTag().equal(pageTag).list();
        List<SearchTableHaving> tableHavings = SearchTableHavingMapper.lambdaQuery().pageTag().equal(pageTag).list();

        if (CollUtil.isNotEmpty(tableGroups)) {
            for (SearchTableGroup tableGroup : tableGroups) {
                SearchTableGroupMapper.lambdaDelete().id().equal(tableGroup.getId()).closeDeletedProtect().delete();
                SearchTableGroupRelationMapper.lambdaDelete().searchTableGroupId().equal(tableGroup.getId()).closeDeletedProtect().delete();
            }
        }

        if (CollUtil.isEmpty(tableHavings)) {
            return;
        }
        for (SearchTableHaving tableHaving : tableHavings) {
            SearchTableHavingMapper.lambdaDelete().id().equal(tableHaving.getId()).closeDeletedProtect().delete();
            SearchTableHavingRelationMapper.lambdaDelete().searchTableHavingId().equal(tableHaving.getId()).closeDeletedProtect().delete();
        }
    }
}
