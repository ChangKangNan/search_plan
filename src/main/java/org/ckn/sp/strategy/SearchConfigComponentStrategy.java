package org.ckn.sp.strategy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
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
            tableColumnsMap.putIfAbsent(tb, innerColumns);
        }
        Map<String, String> tableNameNotInExtends = SqlUtil.getTableNameNotInExtends(fromSQL, extendTables);
        //构建扩展表
        String[] RELATION_SQL_SPLIT = fromSQL.split(JOIN_SPLIT_AND_PREFIX);
        Map<String, String> aliasToON = new HashMap<>();
        List<String> relationKey = new ArrayList<>();
        String mainTbInfo = RELATION_SQL_SPLIT[0];
        String mainTable = getMainTableNameBySub(mainTbInfo) == null ? mainTbInfo.trim() : getMainTableNameBySub(mainTbInfo);
        int lastIndexOf = mainTbInfo.trim().lastIndexOf(StrUtil.SPACE);
        String tableAliasNameBySub = "";

        if (mainTbInfo.trim().contains("/*REPLACE TO")) {
            String trim = mainTbInfo.trim();
            String str = trim.substring(0, trim.indexOf("/*") - 1) + trim.substring(trim.indexOf("*/") + 1);
            int indexOf = str.trim().lastIndexOf(StrUtil.SPACE);
            if (indexOf != -1) {
                tableAliasNameBySub = str.trim().substring(indexOf);
            } else {
                tableAliasNameBySub = null;
            }
        } else {
            if (lastIndexOf != -1) {
                tableAliasNameBySub = mainTbInfo.trim().substring(lastIndexOf).trim();
            } else {
                tableAliasNameBySub = null;
            }
        }

        String main_tb_remark = getReplaceRemark(mainTbInfo);
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
        if (main_tb_remark != null) {
            mainTable += main_tb_remark;
        }
        searchTableMain.setSearchTableName(mainTable);
        searchTableMain.setPageTag(pageTag);
        for (String extendTable : extendTables) {
            if (extendTable.equals(mainTable)) {
                searchTableMain.setIsExtend(true);
            }
        }
        SearchTableMainMapper.lambdaInsert().insert(searchTableMain);
        for (int i = 0; i < extendTables.length; i++) {
            SearchExtendTable searchExtendTable = new SearchExtendTable();
            searchExtendTable.setSearchTableMainId(searchTableMain.getId());
            searchExtendTable.setExtendSql(StrUtil.trimStart(extendTableSQL[i]));
            searchExtendTable.setExtendTableName(extendTables[i]);
            SearchExtendTableMapper.lambdaInsert().insert(searchExtendTable);
        }
        Map<String, String> aliasToTable = new HashMap<>();
        Map<String, String> mapRelation = new HashMap<>();

        for (int i = 1; i < RELATION_SQL_SPLIT.length; i++) {
            String relationSql = RELATION_SQL_SPLIT[i];
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
                aliasToTable.put(aliasName, nameBySub);
                aliasToON.put(aliasName, on);
                relationKey.add(aliasName);
                mapRelation.put(aliasName, relation);
            } else {
                aliasToTable.put(nameBySub, nameBySub);
                aliasToON.put(nameBySub, on);
                relationKey.add(nameBySub);
                mapRelation.put(nameBySub, relation);
            }
        }

        for (String targetTableAliasName : relationKey) {
            String tableName = aliasToTable.get(targetTableAliasName);
            String on = aliasToON.get(targetTableAliasName);
            SearchTableMainRelation searchTableMainRelation = new SearchTableMainRelation();
            List<SearchTableMainRelationLink> searchTableMainRelationLinks = new ArrayList<>();
            List<SearchTableMainRelationWhere> SearchTableMainRelationWheres = new ArrayList<>();
            String[] allCups = on.split("(?i) and ");
            for (String segment : allCups) {
                if (!segment.contains(EQUAL)) {
                    segment = segment + EQUAL + "1";
                }
                String[] equalSplitArray = segment.split(EQUAL);
                String leftFragment = equalSplitArray[0];
                String rightFragment = equalSplitArray[1];
                boolean isColumnRelation = leftFragment.contains(POINT) && rightFragment.contains(POINT);
                if (isColumnRelation) {
                    //处理两端列关系
                    SearchTableMainRelationLink searchTableMainRelationLink = new SearchTableMainRelationLink();
                    for (int j = 0; j < 2; j++) {
                        if (StrUtil.containsIgnoreCase(segment, LEFT_BRACKETS) && StrUtil.containsIgnoreCase(segment, RIGHT_BRACKETS)) {
                            String table = "";
                            Pattern pattern = Pattern.compile("[\\w]+\\.");
                            Matcher matcher = pattern.matcher(segment);
                            while (matcher.find()) {
                                table = matcher.group().substring(0, matcher.group().length() - 1).trim();
                            }
                            if (StrUtil.equals(table, targetTableAliasName)) {
                                searchTableMainRelation.setTargetTableName(tableName);
                                if (!StrUtil.equals(tableName, table)) {
                                    searchTableMainRelation.setTargetTableAlias(table);
                                }
                                searchTableMainRelation.setLinkRelation(mapRelation.get(table));
                                searchTableMainRelationLink.setTableColumnName(equalSplitArray[j]);
                            } else {
                                setRelationLink(aliasToTable, aliasMainName, isMainAlias, mainTable, searchTableMainRelationLink, table);
                                searchTableMainRelationLink.setTargetTableColumnName(equalSplitArray[j]);
                            }
                        } else {
                            //处理无括号匹配
                            String[] strings = equalSplitArray[j].split("\\.");
                            String table = strings[0].trim();
                            String columnName = strings[1].trim();
                            if (StrUtil.equals(table, targetTableAliasName)) {
                                searchTableMainRelation.setTargetTableName(tableName);
                                if (!StrUtil.equals(tableName, table)) {
                                    searchTableMainRelation.setTargetTableAlias(table);
                                }
                                searchTableMainRelation.setLinkRelation(mapRelation.get(table));
                                searchTableMainRelationLink.setTableColumnName(columnName);
                            } else {
                                setRelationLink(aliasToTable, aliasMainName, isMainAlias, mainTable, searchTableMainRelationLink, table);
                                searchTableMainRelationLink.setTargetTableColumnName(columnName);
                            }
                        }
                    }
                    searchTableMainRelationLinks.add(searchTableMainRelationLink);
                } else {
                    //默认条件(左边字段右结果)
                    SearchTableMainRelationWhere searchTableMainRelationWhere = new SearchTableMainRelationWhere();
                    String[] strings = leftFragment.split("\\.");
                    String aliasName = strings[0].trim();
                    String sourceTable = aliasToTable.get(aliasName);
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
                if (extendTable.equals(searchTableMainRelation.getTargetTableName())) {
                    searchTableMainRelation.setIsExtend(true);
                }
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

        //更新上级依赖关系
        List<SearchTableMainRelation> targetRelations = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(searchTableMain.getId()).list();
        if (CollUtil.isNotEmpty(targetRelations)) {
            for (SearchTableMainRelation relation : targetRelations) {
                List<SearchTableMainRelationLink> mainRelationLinks = SearchTableMainRelationLinkMapper.lambdaQuery().searchTableMainRelationId().equal(relation.getId()).list();
                if (CollUtil.isNotEmpty(mainRelationLinks)) {
                    for (SearchTableMainRelationLink relationLink : mainRelationLinks) {
                        String targetTableName = relationLink.getTargetTableName();
                        String targetTableAlias = relationLink.getTargetTableAlias();
                        if (StrUtil.isBlank(targetTableName) && StrUtil.isBlank(targetTableAlias)) {
                            continue;
                        }
                        SearchTableMainRelation tableMainRelation = SearchTableMainRelationMapper.lambdaQuery()
                                .searchTableMainId().equal(searchTableMain.getId())
                                .targetTableName().equal(targetTableName)
                                .targetTableAlias().equal(targetTableAlias)
                                .one();
                        if (tableMainRelation != null) {
                            SearchTableMainRelationLink searchTableMainRelationLink = new SearchTableMainRelationLink();
                            searchTableMainRelationLink.setParentRelationId(tableMainRelation.getId());
                            SearchTableMainRelationLinkMapper.lambdaUpdate().id().equal(relationLink.getId()).update(searchTableMainRelationLink);
                        }
                    }
                }
            }
        }


        Map<String, List<String>> extendTableAndColumn = new HashMap<>() ;//getExtendTableAndColumn(fromSQL, dataSourceName, extendTables);
        String s ="a";
        for (SearchTableColumn tableColumn : columnList) {
            String searchTableColumnInfo = tableColumn.getSearchTableColumnInfo();
            tableColumn.setPageTag(pageTag);
            List<String> matchColumn = getMatchColumn(searchTableColumnInfo);
            for (String column : matchColumn) {
                for (String key : extendTableAndColumn.keySet()) {
                    List<String> values = extendTableAndColumn.get(key);
                    for (String value : values) {
                        if (value.equals(column)) {
                            String replace = searchTableColumnInfo.replaceAll("([!+\\-*/(,%]\\s*|\\s+)" + s + "([+\\-*/%),=]|\\s+)", "$1" + key + "." + s + "$2");
                            tableColumn.setSearchTableColumnInfo(replace);
                        }
                    }
                }
            }
            String columnInfo = tableColumn.getSearchTableColumnInfo();
            if (columnInfo.equals(searchTableColumnInfo)) {
                for (String column : matchColumn) {
                    for (String key : extendTableAndColumn.keySet()) {
                        List<String> values = extendTableAndColumn.get(key);
                        for (String value : values) {
                            if (value.equals(column)) {
                                String replace = searchTableColumnInfo.replaceAll(value, key + "." + value);
                                tableColumn.setSearchTableColumnInfo(replace);
                            }
                        }
                    }
                }
            }
            SearchTableColumnMapper.lambdaInsert().insert(tableColumn);
            Set<String> tbSet = getMatchTable(tableColumn.getSearchTableColumnInfo());
            insertRelation(tbSet, searchTableMain.getId(), tableColumn.getId(), COLUMN);
        }

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
