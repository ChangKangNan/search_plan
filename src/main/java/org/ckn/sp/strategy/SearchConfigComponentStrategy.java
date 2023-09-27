package org.ckn.sp.strategy;

import cn.ft.ckn.fastmapper.bean.ColumnInfo;
import cn.ft.ckn.fastmapper.bean.TableInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.ckn.sp.dto.AliasDTO;
import org.ckn.sp.fm.bean.*;
import org.ckn.sp.fm.dao.*;
import org.ckn.sp.strategy.api.ISplitStrategy;
import org.ckn.sp.util.SqlUtil;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static cn.ft.ckn.fastmapper.util.PackageSqlUtil.LEFT_BRACKETS;
import static cn.ft.ckn.fastmapper.util.PackageSqlUtil.RIGHT_BRACKETS;
import static org.ckn.sp.constants.StrConstants.*;
import static org.ckn.sp.util.RegxUtil.*;
import static org.ckn.sp.util.SqlUtil.getMatchColumn;
import static org.ckn.sp.util.SqlUtil.getMatchTable;

/**
 * @author ckn
 */
public class SearchConfigComponentStrategy implements ISplitStrategy {
    @Override
    public void split(SearchConfig searchConfig) {
        Long searchConfigId = searchConfig.getId();
        String pageTag = searchConfig.getPageTag();
        String searchSql = searchConfig.getSearchSql();
        List<SearchTableColumn> columnList = SqlUtil.getColumns(searchSql);
        String fromSQL = searchConfig.getSearchSql().substring(StrUtil.indexOfIgnoreCase(searchSql, FROM), StrUtil.lastIndexOfIgnoreCase(searchSql, WHERE));
        fromSQL = StrUtil.removePrefixIgnoreCase(fromSQL, FROM);
        String[] extendTables = SqlUtil.getExtendTables(fromSQL);
        String[] extendTableSQL = SqlUtil.getExtendTableSQL(fromSQL);
        //当前SQL的所有表以及其字段 key:表名 value:字段列表
        Map<String, List<String>> tableColumnsMap = new HashMap<>();

        // 查找扩展表内的所有字段
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
            tableColumnsMap.putIfAbsent(tb, innerColumns);
        }

        // 查找所有的本地存在的调用表
        String replaceAll = fromSQL.replaceAll("\\son\\s.*", "");
        String[] strings = replaceAll.split(JOIN_SPLIT);

        Map<String, String> existTableMap = new HashMap<>();
        for (String string : strings) {
            String trimStart = StrUtil.trimStart(string);
            String[] split = trimStart.split(StrUtil.SPACE);
            if (split.length > 1) {
                if (ArrayUtil.contains(extendTables, split[1].trim())) {
                    continue;
                }
                existTableMap.put(split[1].trim(), split[0].trim());
            } else {
                if (ArrayUtil.contains(extendTables, split[0].trim())) {
                    continue;
                }
                existTableMap.put(split[0].trim(), split[0].trim());
            }
        }
        // 根据对应的数据源去查找对应表的字段
        SearchDatasourceRelation datasourceRelation = SearchDatasourceRelationMapper.lambdaQuery().searchConfigId().equal(searchConfigId).one();
        if (datasourceRelation != null) {
            Long searchDatasourceId = datasourceRelation.getSearchDatasourceId();
            SearchDatasource searchDatasource = SearchDatasourceMapper.lambdaQuery().id().equal(searchDatasourceId).one();
            if (searchDatasource != null) {
                DruidDataSource targetSource = new DruidDataSource();
                targetSource.setDriverClassName(searchDatasource.getDatasourceDriver());
                targetSource.setUrl(searchDatasource.getDatasourceUrl());
                targetSource.setUsername(searchDatasource.getDatasourceUsername());
                targetSource.setPassword(searchDatasource.getDatasourcePassword());
                targetSource.setConnectionErrorRetryAttempts(0);
                targetSource.setBreakAfterAcquireFailure(true);
                try {
                    DatabaseMetaData metaData = targetSource.getConnection().getMetaData();
                    String dataSourceName = metaData.getConnection().getCatalog();
                    ResultSet tables = metaData.getTables(dataSourceName, null, null, new String[]{"TABLE"});
                    while (tables.next()) {
                        String table_name = tables.getString("TABLE_NAME");
                        TableInfo tableInfo = new TableInfo();
                        tableInfo.setTableName(table_name);
                        for (String otherName : existTableMap.keySet()) {
                            String table = existTableMap.get(otherName);
                            if (table_name.equalsIgnoreCase(table)) {
                                setAllColumns(tableInfo, metaData);
                                List<String> cols = tableInfo.getColumns().stream().map(ColumnInfo::getColumnName).collect(Collectors.toList());
                                tableColumnsMap.put(otherName, cols);
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("查询数据库列异常!");
                }
            }
        }

        //生成关联表以及配置信息
        for (int i = 0; i < extendTables.length; i++) {
            fromSQL = cutSqlByBrackets(fromSQL, extendTables[i], extendTableSQL[i].length());
        }

        String[] splitJoinTableArray = fromSQL.split(JOIN_SPLIT_AND_PREFIX);
        Map<String, String> aliasCondition = new HashMap<>();
        Map<String, String> aliasTable = new HashMap<>();

        //主表信息建立
        String mainTableInfo = splitJoinTableArray[0];
        String mainTable = getMainTableNameBySub(mainTableInfo) == null ? mainTableInfo.trim() : getMainTableNameBySub(mainTableInfo);
        int lastIndexOf = mainTableInfo.trim().lastIndexOf(StrUtil.SPACE);
        String tableAliasNameBySub;

        if (mainTableInfo.trim().contains("/*REPLACE TO")) {
            String trim = mainTableInfo.trim();
            String str = trim.substring(0, trim.indexOf("/*") - 1) + trim.substring(trim.indexOf("*/") + 1);
            int indexOf = str.trim().lastIndexOf(StrUtil.SPACE);
            tableAliasNameBySub = lastIndexOf != -1 ? str.trim().substring(indexOf) : null;
        } else {
            tableAliasNameBySub = lastIndexOf != -1 ? tableAliasNameBySub = mainTableInfo.trim().substring(lastIndexOf).trim() : null;
        }
        String mainTable_remark = getReplaceRemark(mainTableInfo);
        String aliasMainName;
        SearchTableMain searchTableMain = new SearchTableMain();
        boolean isMainAlias = false;
        if (StrUtil.isNotBlank(tableAliasNameBySub) && (!StrUtil.equals(tableAliasNameBySub, mainTable))) {
            isMainAlias = true;
            aliasMainName = tableAliasNameBySub;
            searchTableMain.setAlias(aliasMainName);
        } else {
            aliasMainName = mainTable;
        }
        if (StrUtil.isBlank(mainTable) && StrUtil.isNotBlank(aliasMainName)) {
            mainTable = aliasMainName;
        }
        if (mainTable_remark != null) {
            mainTable += mainTable_remark;
        }
        searchTableMain.setSearchTableName(mainTable);
        searchTableMain.setPageTag(pageTag);
        for (String extendTable : extendTables) {
            if (!extendTable.equals(mainTable)) {
                continue;
            }
            searchTableMain.setIsExtend(true);
        }
        SearchTableMainMapper.lambdaInsert().insert(searchTableMain);
        for (int i = 0; i < extendTables.length; i++) {
            String table = extendTables[i];
            String table_sql = extendTableSQL[i];
            SearchExtendTable searchExtendTable = new SearchExtendTable();
            searchExtendTable.setSearchTableMainId(searchTableMain.getId());
            searchExtendTable.setExtendSql(StrUtil.trimStart(table_sql));
            searchExtendTable.setExtendTableName(table);
            SearchExtendTableMapper.lambdaInsert().insert(searchExtendTable);
        }

        Map<String, String> mapRelation = new HashMap<>();
        List<String> relationTableName = new ArrayList<>();
        for (int i = 1; i < splitJoinTableArray.length; i++) {
            String relationSql = splitJoinTableArray[i];
            String str = StrUtil.trimEnd(StrUtil.trimStart(relationSql));
            String relation = null;
            String tableSub = null;
            if (StrUtil.containsAnyIgnoreCase(str, "left join")) {
                relation = str.substring(0, 9);
                tableSub = str.substring(9);
            } else if (StrUtil.containsAnyIgnoreCase(str, "right join", "inner join")) {
                relation = str.substring(0, 10);
                tableSub = str.substring(10);
            }
            if (relation == null) {
                continue;
            }
            //逻辑调整
            String on = tableSub.split("(?i) on ")[1];
            String aliasName = getAliasNameBySub(tableSub);
            String nameBySub = getNameBySub(relationSql);
            String replaceRemark = getReplaceRemark(tableSub);
            if (replaceRemark != null) {
                nameBySub += replaceRemark;
            }
            if (StrUtil.isNotBlank(aliasName) && (!StrUtil.equals(aliasName, nameBySub))) {
                aliasTable.put(aliasName, nameBySub);
                aliasCondition.put(aliasName, on);
                relationTableName.add(aliasName);
                mapRelation.put(aliasName, relation);
            } else {
                aliasTable.put(nameBySub, nameBySub);
                aliasCondition.put(nameBySub, on);
                relationTableName.add(nameBySub);
                mapRelation.put(nameBySub, relation);
            }
        }

        for (String rel_table_name : relationTableName) {
            String tableName = aliasTable.get(rel_table_name);
            String onCondition = aliasCondition.get(rel_table_name);
            SearchTableMainRelation searchTableMainRelation = new SearchTableMainRelation();
            List<SearchTableMainRelationLink> searchTableMainRelationLinks = new ArrayList<>();
            List<SearchTableMainRelationWhere> SearchTableMainRelationWheres = new ArrayList<>();
            String[] segments = onCondition.split("(?i) and ");
            for (String segment : segments) {
                if (!segment.contains(EQUAL)) {
                    segment = segment + EQUAL + "1";
                }
                String[] equalSplitArray = segment.split(EQUAL);
                String leftFragment = equalSplitArray[0];
                String rightFragment = equalSplitArray[1];
                boolean isColumnRelation = leftFragment.contains(POINT) && rightFragment.contains(POINT);
                if (isColumnRelation) {
                    SearchTableMainRelationLink searchTableMainRelationLink = new SearchTableMainRelationLink();
                    for (int j = 0; j < 2; j++) {
                        if (StrUtil.containsIgnoreCase(segment, LEFT_BRACKETS) && StrUtil.containsIgnoreCase(segment, RIGHT_BRACKETS)) {
                            String table = "";
                            Pattern pattern = Pattern.compile("[\\w]+\\.");
                            Matcher matcher = pattern.matcher(segment);
                            while (matcher.find()) {
                                table = matcher.group().substring(0, matcher.group().length() - 1).trim();
                            }
                            if (StrUtil.equals(table, rel_table_name)) {
                                searchTableMainRelation.setTargetTableName(tableName);
                                if (!StrUtil.equals(tableName, table)) {
                                    searchTableMainRelation.setTargetTableAlias(table);
                                }
                                searchTableMainRelation.setLinkRelation(mapRelation.get(table));
                                searchTableMainRelationLink.setTableColumnName(equalSplitArray[j]);
                            } else {
                                setRelationLink(aliasTable, aliasMainName, isMainAlias, mainTable, searchTableMainRelationLink, table);
                                searchTableMainRelationLink.setTargetTableColumnName(equalSplitArray[j]);
                            }
                        } else {
                            //处理无括号匹配
                            String[] s = equalSplitArray[j].split("\\.");
                            String table = s[0].trim();
                            String columnName = s[1].trim();
                            if (StrUtil.equals(table, rel_table_name)) {
                                searchTableMainRelation.setTargetTableName(tableName);
                                if (!StrUtil.equals(tableName, table)) {
                                    searchTableMainRelation.setTargetTableAlias(table);
                                }
                                searchTableMainRelation.setLinkRelation(mapRelation.get(table));
                                searchTableMainRelationLink.setTableColumnName(columnName);
                            } else {
                                setRelationLink(aliasTable, aliasMainName, isMainAlias, mainTable, searchTableMainRelationLink, table);
                                searchTableMainRelationLink.setTargetTableColumnName(columnName);
                            }
                        }
                    }
                    searchTableMainRelationLinks.add(searchTableMainRelationLink);
                } else {
                    //默认条件(左边字段右结果)
                    SearchTableMainRelationWhere searchTableMainRelationWhere = new SearchTableMainRelationWhere();
                    String[] s = leftFragment.split("\\.");
                    String aliasName = s[0].trim();
                    String sourceTable = aliasTable.get(aliasName);
                    if (StrUtil.isNotBlank(sourceTable)) {
                        searchTableMainRelationWhere.setTargetTableName(sourceTable);
                        if (!StrUtil.equals(sourceTable, aliasName)) {
                            searchTableMainRelationWhere.setTargetTableAlias(aliasName);
                        }
                    } else {
                        if (StrUtil.equalsAny(aliasName, mainTable, aliasMainName)) {
                            searchTableMainRelationWhere.setTargetTableName(mainTable);
                            if (isMainAlias) {
                                searchTableMainRelationWhere.setTargetTableAlias(aliasMainName);
                            }
                        }
                    }
                    searchTableMainRelationWhere.setWhereInfo(segment.trim());
                    SearchTableMainRelationWheres.add(searchTableMainRelationWhere);
                }
            }
            searchTableMainRelation.setSearchTableMainId(searchTableMain.getId());

            for (String extendTable : extendTables) {
                if (!extendTable.equals(searchTableMainRelation.getTargetTableName())) {
                    continue;
                }
                searchTableMainRelation.setIsExtend(true);
            }
            SearchTableMainRelationMapper.lambdaInsert().insert(searchTableMainRelation);
            if (CollUtil.isNotEmpty(searchTableMainRelationLinks)) {
                searchTableMainRelationLinks.forEach(t -> t.setSearchTableMainRelationId(searchTableMainRelation.getId()));
                SearchTableMainRelationLinkMapper.lambdaInsert().insertBatch(searchTableMainRelationLinks);
            }
            if (CollUtil.isNotEmpty(SearchTableMainRelationWheres)) {
                SearchTableMainRelationWheres.forEach(t -> t.setSearchTableMainRelationId(searchTableMainRelation.getId()));
                SearchTableMainRelationWhereMapper.lambdaInsert().insertBatch(SearchTableMainRelationWheres);
            }
        }

        List<SearchTableMainRelation> tableMainRelations = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(searchTableMain.getId()).list();
        if (CollUtil.isNotEmpty(tableMainRelations)) {
            for (SearchTableMainRelation relation : tableMainRelations) {
                List<SearchTableMainRelationLink> mainRelationLinks = SearchTableMainRelationLinkMapper.lambdaQuery().searchTableMainRelationId().equal(relation.getId()).list();
                if (CollUtil.isEmpty(mainRelationLinks)) {
                    continue;
                }
                for (SearchTableMainRelationLink relationLink : mainRelationLinks) {
                    String targetTableName = relationLink.getTargetTableName();
                    String targetTableAlias = relationLink.getTargetTableAlias();
                    if (StrUtil.isBlank(targetTableName) && StrUtil.isBlank(targetTableAlias)) {
                        continue;
                    }
                    SearchTableMainRelation tableMainRelation = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(searchTableMain.getId()).targetTableName().equal(targetTableName).targetTableAlias().equal(targetTableAlias).one();
                    if (tableMainRelation == null) {
                        continue;
                    }
                    SearchTableMainRelationLinkMapper.lambdaUpdate()
                            .id().equal(relationLink.getId())
                            .value()
                            .set(SearchTableMainRelationLink::getParentRelationId, tableMainRelation.getId())
                            .execute();
                }
            }
        }

        AliasDTO aliasDTO = new AliasDTO();
        aliasDTO.setTableMain(searchTableMain);
        aliasDTO.setAliasTooN(aliasCondition);
        Map<String, String> tableExtendMap = new HashMap<>();
        for (int i = 0; i < extendTables.length; i++) {
            String extendTable = extendTables[i];
            String sql = extendTableSQL[i];
            tableExtendMap.put(extendTable, sql);
        }
        aliasDTO.setTableExtendMap(tableExtendMap);
        aliasDTO.setAliasToTable(aliasTable);
        aliasDTO.setMainAliasName(aliasMainName);
        aliasDTO.setMainTable(mainTable);
        aliasDTO.setIsAlias(isMainAlias);

        for (SearchTableColumn tableColumn : columnList) {
            String searchTableColumnInfo = tableColumn.getSearchTableColumnInfo();
            tableColumn.setPageTag(pageTag);
            List<String> matchColumns = getMatchColumn(searchTableColumnInfo);
            //缺省补全
            for (String matchColumn : matchColumns) {
                for (String key : tableColumnsMap.keySet()) {
                    List<String> values = tableColumnsMap.get(key);
                    for (String value : values) {
                        if (value.equals(matchColumn)) {
                            String replace = searchTableColumnInfo.replaceAll("([!+\\-*/(,%]\\s*|\\s+)" + matchColumn + "([+\\-*/%),=]|\\s+)", "$1" + key + "." + matchColumn + "$2");
                            tableColumn.setSearchTableColumnInfo(replace);
                        }
                    }
                }
            }
            String columnInfo = tableColumn.getSearchTableColumnInfo();
            if (columnInfo.equals(searchTableColumnInfo)) {
                for (String matchColumn : matchColumns) {
                    for (String key : tableColumnsMap.keySet()) {
                        List<String> values = tableColumnsMap.get(key);
                        for (String value : values) {
                            if (value.equals(matchColumn)) {
                                String replace = searchTableColumnInfo.replaceAll(value, key + "." + value);
                                tableColumn.setSearchTableColumnInfo(replace);
                            }
                        }
                    }
                }
            }
            SearchTableColumnMapper.lambdaInsert().insert(tableColumn);
            //获取对应列中的表
            Set<String> tbSet = getMatchTable(tableColumn.getSearchTableColumnInfo());
            insertRelation(tbSet, aliasDTO.getTableMain().getId(), tableColumn.getId(), COLUMN);
        }

        //处理where条件
        String whereSQL = searchConfig.getSearchSql().substring(searchConfig.getSearchSql().toLowerCase().lastIndexOf(WHERE));
        whereSQL = StrUtil.replaceIgnoreCase(whereSQL, WHERE, "");
        whereSQL = whereSQL.trim();
        int group_by = whereSQL.toLowerCase().lastIndexOf(GROUP_BY);
        String groupSql = "";
        if (group_by != -1) {
            groupSql = whereSQL.substring(group_by + 8);
            whereSQL = whereSQL.substring(0, group_by);
        }
        final String WHERE_REGEX = "(?i)(?= and )|(?=\nand )|(?=\nand\n)|(?= or )|(?=\nor )|(?=\nor\n)";
        if (StrUtil.isNotBlank(whereSQL)) {
            String[] WHERE_SPLIT = whereSQL.split(WHERE_REGEX);
            for (int i = 0; i < WHERE_SPLIT.length; i++) {
                StringBuilder str = new StringBuilder(WHERE_SPLIT[i]);
                int blank = 0;
                for (int j = 0; j < str.length(); j++) {
                    if (str.charAt(j) == '(') {
                        blank++;
                    }
                    if (str.charAt(j) == ')') {
                        blank--;
                    }
                }
                if (blank > 0) {
                    int now_count = 1;
                    while (blank > 0 && (i + now_count) < WHERE_SPLIT.length) {
                        str.append(WHERE_SPLIT[i + now_count]);
                        for (int j = 0; j < str.length(); j++) {
                            if (str.charAt(j) == '(') {
                                blank++;
                            }
                            if (str.charAt(j) == ')') {
                                blank--;
                            }
                        }
                    }
                }
                String where = str.toString();
                where = StrUtil.trimStart(where);
                SearchTableWhere searchTableWhere = new SearchTableWhere();
                if (StrUtil.startWithIgnoreCase(where, AND)) {
                    where = where.substring(3);
                    searchTableWhere.setRelation(AND);
                }
                if (StrUtil.startWithIgnoreCase(where, OR)) {
                    where = where.substring(2);
                    searchTableWhere.setRelation(OR);
                }
                where = replaceStringWithTable(tableColumnsMap, where);
                searchTableWhere.setTableWhere(where);
                searchTableWhere.setPageTag(pageTag);
                SearchTableWhereMapper.lambdaInsert().insert(searchTableWhere);
                Set<String> tbSet = getMatchTable(where);
                insertRelation(tbSet, aliasDTO.getTableMain().getId(), searchTableWhere.getId(), WHERE);
            }
        }
        //group by处理
        if (StrUtil.isEmpty(groupSql)) {
            return;
        }
        String having_sql = null;
        int having = SqlUtil.getIndex(groupSql, HAVING);
        if (having != -1) {
            having_sql = groupSql.substring(having);
            groupSql = groupSql.substring(0, having);
        }
        StringBuilder builder = new StringBuilder();
        int blank = 0;

        List<String> group_columns = new ArrayList<>();
        for (int i = 0; i < groupSql.length(); i++) {
            if (groupSql.charAt(i) == '(') {
                blank++;
            }
            if (groupSql.charAt(i) == ')') {
                blank--;
            }
            if (groupSql.charAt(i) != ',') {
                builder.append(groupSql.charAt(i));
            }
            if ((i != 0 && groupSql.charAt(i) == ',') || i == (groupSql.length() - 1)) {
                String groupBy = StrUtil.removePrefixIgnoreCase(builder.toString().trim(), "group by");
                group_columns.add(groupBy);
                builder = new StringBuilder();
            }
        }

        for (String group_column : group_columns) {
            SearchTableGroup tableGroup = new SearchTableGroup();
            group_column = getExistColumnInfo(columnList, group_column);
            group_column = replaceStringWithTable(tableColumnsMap, group_column);
            tableGroup.setTableGroup(group_column);
            tableGroup.setPageTag(pageTag);
            SearchTableGroupMapper.lambdaInsert().insert(tableGroup);
            Set<String> tbSet = getMatchTable(group_column);
            insertRelation(tbSet, aliasDTO.getTableMain().getId(), tableGroup.getId(), GROUP);
        }
        if (having == -1) {
            return;
        }
        String[] HAVING_SPLIT = having_sql.split(WHERE_REGEX);
        for (int i = 0; i < HAVING_SPLIT.length; i++) {
            StringBuilder str = new StringBuilder(HAVING_SPLIT[i]);
            int blank_having = 0;
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '(') {
                    blank_having++;
                }
                if (str.charAt(j) == ')') {
                    blank_having--;
                }
            }
            if (blank_having > 0) {
                int now_count = 1;
                while (blank_having > 0 && (i + now_count) < HAVING_SPLIT.length) {
                    str.append(HAVING_SPLIT[i + now_count]);
                    for (int j = 0; j < str.length(); j++) {
                        if (str.charAt(j) == '(') {
                            blank_having++;
                        }
                        if (str.charAt(j) == ')') {
                            blank_having--;
                        }
                    }
                }
            }
            String where = str.toString();
            where = StrUtil.trimStart(where);
            where = StrUtil.removePrefixIgnoreCase(where, "having");
            SearchTableHaving searchTableHaving = new SearchTableHaving();
            if (StrUtil.startWithIgnoreCase(where, AND)) {
                where = where.substring(3);
                searchTableHaving.setRelation(AND);
            }
            if (StrUtil.startWithIgnoreCase(where, OR)) {
                where = where.substring(2);
                searchTableHaving.setRelation(OR);
            }
            where = replaceStringWithTable(tableColumnsMap, where);
            searchTableHaving.setTableHavingWhere(where);
            searchTableHaving.setPageTag(pageTag);
            SearchTableHavingMapper.lambdaInsert().insert(searchTableHaving);
            Set<String> tbSet = getMatchTable(where);
            insertRelation(tbSet, aliasDTO.getTableMain().getId(), searchTableHaving.getId(), HAVING);
        }
    }

    private String getExistColumnInfo(List<SearchTableColumn> searchTableColumns, String column) {
        for (SearchTableColumn searchTableColumn : searchTableColumns) {
            String tableColumnInfo = searchTableColumn.getSearchTableColumnInfo();
            String tableColumn = searchTableColumn.getSearchTableColumn();
            if (tableColumn.trim().equals(column.trim())) {
                return tableColumnInfo.trim();
            }
        }
        return column;
    }

    private String cutSqlByBrackets(String sql, String tableName, Integer length) {
        int tb_index = sql.indexOf(StrUtil.SPACE + tableName + StrUtil.SPACE);
        if (tb_index == -1) {
            tb_index = sql.indexOf(StrUtil.SPACE + tableName + System.lineSeparator());
        }
        int left_black_index = tb_index - length - 2;
        return sql.substring(0, left_black_index) + StrUtil.SPACE + sql.substring(tb_index);
    }

    public void setAllColumns(TableInfo tableInfo, DatabaseMetaData metaData) throws SQLException {
        String catalog = metaData.getConnection().getCatalog();
        ResultSet columns = metaData.getColumns(catalog, null, tableInfo.getTableName(), "%");
        String columnName;
        List<ColumnInfo> columnInfoList = new ArrayList<>();
        while (columns.next()) {
            columnName = columns.getString("COLUMN_NAME");
            ColumnInfo info = new ColumnInfo();
            info.setColumnName(columnName);
            columnInfoList.add(info);
        }
        tableInfo.setColumns(columnInfoList);
    }

    private String replaceStringWithTable(Map<String, List<String>> extendTableAndColumn, String trim) {
        String preValue = trim;
        List<String> matchColumn = getMatchColumn(trim);
        for (String s : matchColumn) {
            for (String key : extendTableAndColumn.keySet()) {
                List<String> values = extendTableAndColumn.get(key);
                for (String value : values) {
                    if (value.equals(s)) {
                        trim = trim.replaceAll("([+\\-*/(,]\\s*|\\s+)" + s + "([+\\-*/),=]|\\s+)", "$1" + key + "." + s + "$2");
                    }
                }
            }
        }
        if (preValue.equals(trim)) {
            for (String s : matchColumn) {
                for (String key : extendTableAndColumn.keySet()) {
                    List<String> values = extendTableAndColumn.get(key);
                    for (String value : values) {
                        if (value.equals(s)) {
                            trim = trim.replaceAll(value, key + "." + value);
                        }
                    }
                }
            }
        }
        return trim;
    }

    private void setRelationLink(Map<String, String> aliasToTable, String aliasMainName, boolean isMainAlias, String mainTable, SearchTableMainRelationLink searchTableMainRelationLink, String table) {
        String sourceTable = aliasToTable.get(table);
        if (StrUtil.isBlank(sourceTable)) {
            if (StrUtil.equalsAny(table, mainTable, aliasMainName)) {
                searchTableMainRelationLink.setTargetTableName(mainTable);
                if (isMainAlias) {
                    searchTableMainRelationLink.setTargetTableAlias(table);
                }
            }
        } else {
            searchTableMainRelationLink.setTargetTableName(sourceTable);
            if (!sourceTable.equals(table)) {
                searchTableMainRelationLink.setTargetTableAlias(table);
            }
        }
    }

    public void insertRelation(Set<String> tableSet, Long tableMainId, Long id, String tag) {
        if (CollUtil.isEmpty(tableSet)) {
            return;
        }
        for (String aliasName : tableSet) {
            SearchTableMainRelation searchTableMainRelation = SearchTableMainRelationMapper.lambdaQuery()
                    .targetTableAlias().equal(aliasName)
                    .searchTableMainId().equal(tableMainId)
                    .one();
            SearchTableColumnTarget searchTableColumnTarget = new SearchTableColumnTarget();
            if (ObjectUtil.isNotNull(searchTableMainRelation)) {
                searchTableColumnTarget.setSearchTableMainRelationId(searchTableMainRelation.getId());
            } else {
                List<SearchTableMainRelation> relations = SearchTableMainRelationMapper.lambdaQuery()
                        .targetTableName().equal(aliasName)
                        .searchTableMainId().equal(tableMainId)
                        .list();
                if (relations.size() == 1) {
                    searchTableColumnTarget.setSearchTableMainRelationId(relations.get(0).getId());
                }
            }
            if (searchTableColumnTarget.getSearchTableMainRelationId() != null) {
                switch (tag) {
                    case COLUMN:
                        searchTableColumnTarget.setSearchTableColumnId(id);
                        SearchTableColumnTargetMapper.lambdaInsert().insert(searchTableColumnTarget);
                        break;
                    case WHERE:
                        SearchTableWhereRelation tableWhereRelation = BeanUtil.copyProperties(searchTableColumnTarget, SearchTableWhereRelation.class);
                        tableWhereRelation.setSearchTableWhereId(id);
                        SearchTableWhereRelationMapper.lambdaInsert().insert(tableWhereRelation);
                        break;
                    case GROUP:
                        SearchTableGroupRelation tableGroupRelation = BeanUtil.copyProperties(searchTableColumnTarget, SearchTableGroupRelation.class);
                        tableGroupRelation.setSearchTableGroupId(id);
                        SearchTableGroupRelationMapper.lambdaInsert().insert(tableGroupRelation);
                        break;
                    case HAVING:
                        SearchTableHavingRelation tableHavingRelation = BeanUtil.copyProperties(searchTableColumnTarget, SearchTableHavingRelation.class);
                        tableHavingRelation.setSearchTableHavingId(id);
                        SearchTableHavingRelationMapper.lambdaInsert().insert(tableHavingRelation);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
