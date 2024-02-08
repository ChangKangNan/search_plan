package org.ckn.sp.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.fm.bean.*;
import org.ckn.sp.fm.dao.SearchConfigInfoMapper;
import org.ckn.sp.fm.dao.SearchExtendTableMapper;
import org.ckn.sp.fm.dao.SearchTableMainMapper;
import org.ckn.sp.fm.dao.SearchTableMainRelationMapper;
import org.ckn.sp.service.impl.SearchPlanServiceImpl;
import org.ckn.sp.util.SQLUtils;
import org.ckn.sp.util.SqlUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author ckn
 */
public class OptimizationProvider {

    public static void handle(SearchQueryDTO searchQueryDTO) {
        String packageSql = SearchPlanServiceImpl.QUERY_SQL_LOCAL.get();
        List<SearchQueryDTO.QueryInfo> preList = searchQueryDTO.getQueryInfoList();
        String pageTag = searchQueryDTO.getPageTag();
        List<SearchQueryDTO.QueryInfo> preStream = preList.stream().filter(f -> f.getAreInnerFilter() == 1).collect(Collectors.toList());
        if (CollUtil.isEmpty(preStream)) {
            return;
        }
        Map<String, String> updateSQL = new HashMap<>();
        for (SearchQueryDTO.QueryInfo queryInfo : preStream) {
            final String searchKey = queryInfo.getSearchKey();
            List<String> tableList = SQLUtils.getSQLCutColumnOrTable(searchKey, "TABLE");
            if (CollUtil.isEmpty(tableList)) {
                continue;
            }
            String table = tableList.get(0);
            SearchTableMain searchTableMain = SearchTableMainMapper.lambdaQuery().pageTag().equal(pageTag).one();
            if (searchTableMain == null) {
                continue;
            }
            boolean isExtendMain = false;
            if (StrUtil.equals(table, "MAIN_TABLE") || StrUtil.equals(table, StrUtil.isBlank(searchTableMain.getAlias()) ? searchTableMain.getSearchTableName() : searchTableMain.getAlias())) {
                isExtendMain = true;
                table = StrUtil.isBlank(searchTableMain.getAlias()) ? searchTableMain.getSearchTableName() : searchTableMain.getAlias();
            }
            String targetTableName = null;
            String targetTableAlias;
            Boolean isExtend = null;
            String linkRelation = null;
            targetTableAlias = table;
            SearchTableMainRelation tableMainRelation = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(searchTableMain.getId()).targetTableAlias().equal(targetTableAlias).one();
            if (tableMainRelation != null) {
                targetTableName = tableMainRelation.getTargetTableName();
                targetTableAlias = tableMainRelation.getTargetTableAlias();
                isExtend = tableMainRelation.getIsExtend();
                linkRelation = tableMainRelation.getLinkRelation();
            } else {
                SearchTableMainRelation searchTableMainRelation = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(searchTableMain.getId()).targetTableName().equal(targetTableAlias).one();
                if (searchTableMainRelation != null) {
                    targetTableName = searchTableMainRelation.getTargetTableName();
                    targetTableAlias = searchTableMainRelation.getTargetTableAlias();
                    isExtend = searchTableMainRelation.getIsExtend();
                    linkRelation = searchTableMainRelation.getLinkRelation();
                } else if (isExtendMain) {
                    targetTableName = searchTableMain.getSearchTableName();
                    targetTableAlias = searchTableMain.getAlias();
                    isExtend = searchTableMain.getIsExtend();
                }
            }
            if (linkRelation == null && !Boolean.TRUE.equals(isExtend)) {
                return ;
            }
            String searchGroupRelation = queryInfo.getSearchGroupRelation();
            SearchConfigInfo configInfo = SearchConfigInfoMapper.lambdaQuery().id().equal(queryInfo.getSearchConfigInfoId()).one();
            if (isExtend) {
                SearchExtendTable searchExtendTable = SearchExtendTableMapper.lambdaQuery().searchTableMainId().equal(searchTableMain.getId()).extendTableName().equal(targetTableName).one();
                if (searchExtendTable == null) {
                    continue;
                }
                String extendSql_replace = updateSQL.get(targetTableName);
                String extendSql = StrUtil.isBlank(extendSql_replace) ? searchExtendTable.getExtendSql() : extendSql_replace;
                SearchConfigInnerCondition searchConfigInnerCondition = new SearchConfigInnerCondition();
                searchConfigInnerCondition.setSearchType(configInfo.getSearchType());
                String[] split = queryInfo.getSearchKey().split("\\.");
                String key = split.length > 1 ? split[1].trim() : split[0].trim();
                queryInfo.setSearchKey(key);
                //寻找
                String link_sql = SqlUtil.getInnerPackageSql(queryInfo, searchConfigInnerCondition);
                //处理/*....*/
                link_sql = link_sql.replaceAll("/\\*(.+)\\*/", "");
                String finalSql = SqlUtil.getFinalSql(extendSql, targetTableName, link_sql, queryInfo.getSearchGroupRelation(), false);
                updateSQL.put(targetTableName, finalSql);
            }else {
                //外部
                String replaceChars = StrUtil.replaceChars(targetTableName, "*", "\\*");
                String regex = linkRelation + "\\s+" + replaceChars + "\\s+" + (StrUtil.isBlank(targetTableAlias) ? "" : targetTableAlias) + "\\s+on(.*)";
                Pattern replace = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                String[] lines = packageSql.split(System.lineSeparator());
                int black = 0;
                int i = 0;
                for (String line : lines) {
                    //括号处理
                    for (int j = 0; j < line.length(); j++) {
                        char charAt = line.charAt(j);
                        if (charAt == '(') {
                            black++;
                        } else if (charAt == ')') {
                            black--;
                        }
                    }
                    if (black > 0) {
                        i++;
                        continue;
                    }
                    Matcher matcher = replace.matcher(line);
                    if (matcher.matches()) {
                        SearchConfigInnerCondition searchConfigInnerCondition = new SearchConfigInnerCondition();
                        searchConfigInnerCondition.setSearchType(configInfo.getSearchType());
                        String innerPackageSql = SqlUtil.getInnerPackageSql(queryInfo, searchConfigInnerCondition);
                        //处理/*....*/
                        innerPackageSql = innerPackageSql.replaceAll("/\\*(.+)\\*/", "");
                        lines[i] = line + StrUtil.SPACE + searchGroupRelation + StrUtil.SPACE + innerPackageSql;
                    }
                    i++;
                }
                packageSql = ArrayUtil.join(lines, System.lineSeparator());
            }
        }
        SearchPlanServiceImpl.QUERY_SQL_LOCAL.set(packageSql);
    }
}
