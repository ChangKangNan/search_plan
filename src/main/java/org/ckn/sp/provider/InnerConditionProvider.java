package org.ckn.sp.provider;

import cn.hutool.core.collection.CollUtil;
import javafx.util.Pair;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.fm.bean.SearchConfigInnerCondition;
import org.ckn.sp.fm.dao.SearchConfigInnerConditionMapper;
import org.ckn.sp.service.impl.SearchPlanServiceImpl;
import org.ckn.sp.util.SqlUtil;

import java.util.List;

/**
 * @author ckn
 */
public class InnerConditionProvider {
    public static void handle(SearchQueryDTO searchQueryDTO) {
        String pageTag = searchQueryDTO.getPageTag();
        int count = SearchConfigInnerConditionMapper.lambdaQuery().pageTag().equal(pageTag).count();
        if (count == 0) {
            return;
        }
        List<SearchQueryDTO.QueryInfo> queryInfoList = searchQueryDTO.getQueryInfoList();
        if (CollUtil.isEmpty(queryInfoList)) {
            return;
        }
        String packageSql = SearchPlanServiceImpl.QUERY_SQL_LOCAL.get();
        for (SearchQueryDTO.QueryInfo queryInfo : queryInfoList) {
            Integer areInner = queryInfo.getAreInner();
            //如果内部条件则事先封装
            if (areInner == 1) {
                Long innerConditionId = queryInfo.getSearchConfigInnerConditionId();
                SearchConfigInnerCondition condition = SearchConfigInnerConditionMapper.lambdaQuery().id().equal(innerConditionId).one();
                Pair<Integer, Integer> target = SqlUtil.getTarget(packageSql, condition.getAxis().split("\\.")[0]);
                if (target.getKey() != -1) {
                    String prefix = packageSql.substring(0, target.getKey());
                    String substring = packageSql.substring(target.getKey(), target.getValue());
                    String end = packageSql.substring(target.getValue());
                    String sql = SqlUtil.getInnerPackageSql(queryInfo, condition);
                    String finalSql = SqlUtil.getFinalSql(substring, condition.getAxis(), sql, condition.getRelation(), false);
                    packageSql = prefix + finalSql + end;
                }
            }
        }
        SearchPlanServiceImpl.QUERY_SQL_LOCAL.set(packageSql);
    }
}
