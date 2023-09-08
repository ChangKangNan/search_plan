package org.ckn.sp.strategy;

import cn.hutool.core.util.StrUtil;
import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.fm.bean.SearchTableColumn;
import org.ckn.sp.strategy.api.ISplitStrategy;
import org.ckn.sp.util.SqlUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ckn.sp.constants.StrConstants.*;

/**
 * @author ckn
 */
public class SearchConfigComponentStrategy implements ISplitStrategy {
    @Override
    public void split(SearchConfig searchConfig) {
        String pageTag = searchConfig.getPageTag();
        String searchSql = searchConfig.getSearchSql();
        List<SearchTableColumn> columnList = SqlUtil.getColumns(searchSql);
        String fromSQL = searchConfig.getSearchSql().substring(StrUtil.indexOfIgnoreCase(searchSql, FROM), StrUtil.lastIndexOfIgnoreCase(searchSql, WHERE));
        fromSQL = StrUtil.removePrefixIgnoreCase(fromSQL, FROM);
        String[] extendTables = SqlUtil.getExtendTables(fromSQL);
        String[] extendTableSQL = SqlUtil.getExtendTableSQL(fromSQL);
        Map<String, List<String>> tableColumnsMap = new HashMap<>();
        for (int i = 0; i < extendTables.length; i++) {
            String extendSQL = extendTableSQL[i];
            String tb = extendTables[i];
            //将SQL中的前后括号去掉
            int begin_index = fromSQL.indexOf(extendSQL);
            int end_index = fromSQL.indexOf(extendSQL) + extendSQL.length();
            while (fromSQL.charAt(begin_index) != '(') {
                begin_index--;
            }
            while (fromSQL.charAt(end_index) != ')') {
                end_index++;
            }
            fromSQL = fromSQL.substring(0, begin_index) + fromSQL.substring(end_index + 1);
            int from_index = extendSQL.toLowerCase().indexOf("from");
            String column_sql = extendSQL.substring(0, from_index);
            String columnSql = StrUtil.replaceIgnoreCase(column_sql, SELECT, "");
            List<String> innerColumns = SqlUtil.getInnerColumns(columnSql);
            tableColumnsMap.putIfAbsent(tb,innerColumns);
        }
    }
}
