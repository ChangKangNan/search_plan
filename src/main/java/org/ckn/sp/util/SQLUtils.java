package org.ckn.sp.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.ckn.sp.fm.bean.*;
import org.ckn.sp.fm.dao.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author ckn
 */
@Slf4j
public class SQLUtils {
    static String SELECT = "SELECT";
    static String BLANK = " ";
    static String TURN = System.lineSeparator();
    static String AS = "as";
    static String FROM = "from";
    static String LEFT_BRACKET = "(";
    static String RIGHT_BRACKET = ")";
    static String LEFT_JOIN = "LEFT JOIN";
    static String ON = "on";
    static String POINT = ".";
    static String EQUAL = "=";
    static String COMMA = ",";
    static String AND="AND";
    static String WHERE="where";
    static String GROUP_BY="group by";
    static String HAVING="having";
   public static String packageSql(String pagTag, List<String> columnNames,List<String> columnHidden) {
        SearchTableMain searchTableMain = SearchTableMainMapper.lambdaQuery().pageTag().equal(pagTag).one();
        Boolean isExtend = searchTableMain.getIsExtend();
        Long tableMainId = searchTableMain.getId();
        Map<String,String> tableAliasMap=new HashMap<>();
        List<SearchTableMainRelation> searchTableMainRelations = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(tableMainId).list();
        if(CollUtil.isNotEmpty(searchTableMainRelations)){
            for (SearchTableMainRelation tableMainRelation : searchTableMainRelations) {
                tableAliasMap.put(tableMainRelation.getTargetTableName(),tableMainRelation.getTargetTableAlias());
            }
        }
        String mainTableAlias = searchTableMain.getAlias();
        tableAliasMap.put(searchTableMain.getSearchTableName(),mainTableAlias);
        StringBuilder sqlBuilder = new StringBuilder(SELECT);
        sqlBuilder.append(TURN);
        List<SearchTableColumn> searchTableColumns = SearchTableColumnMapper.lambdaQuery().pageTag().equal(pagTag).list();
        if (CollUtil.isEmpty(searchTableColumns)) {
            return "";
        }
        Map<String, String> columnToInfos = new HashMap<>();
        Set<Long> relationIds=new HashSet<>();
        for (SearchTableColumn searchTableColumn : searchTableColumns) {
            String searchTableColumnInfo = searchTableColumn.getSearchTableColumnInfo();
            columnToInfos.put(searchTableColumn.getSearchTableColumn(),
                    searchTableColumnInfo);
        }
        String[] columnAll = ArrayUtil.toArray(columnNames, String.class);
        List<String> columnShow=new ArrayList<>();
        for (int i = 0; i < columnAll.length; i++) {
            sqlBuilder.append(columnToInfos.get(columnAll[i]));
            log.info("{}", columnAll[i]);
            columnShow.add(columnToInfos.get(columnAll[i]).trim());
            sqlBuilder.append(BLANK);
            sqlBuilder.append(AS);
            sqlBuilder.append(BLANK);
            sqlBuilder.append(columnAll[i]);
            if (i != columnAll.length - 1) {
                sqlBuilder.append(COMMA);
            }
            sqlBuilder.append(TURN);

            log.info("columnAll["+i+"]"+"="+columnAll[i]);
            String column =columnAll[i];
            if (StrUtil.containsIgnoreCase(column, " as ")) {
                String[] res = column.split("(?i) as ");
                column = res[1].trim();
            }
            SearchTableColumn tableColumn = SearchTableColumnMapper.lambdaQuery().pageTag().equal(pagTag).searchTableColumn().equal(column).one();
            Long searchTableColumnId = tableColumn.getId();
            List<SearchTableColumnTarget> tableColumnTargets = SearchTableColumnTargetMapper.lambdaQuery().searchTableColumnId().equal(searchTableColumnId).list();
            if(CollUtil.isNotEmpty(tableColumnTargets)){
                for (SearchTableColumnTarget columnTarget : tableColumnTargets) {
                    relationIds.add(columnTarget.getSearchTableMainRelationId());
                }
            }
        }
        //若存在表中间关联
       Set<Long> newLink=new HashSet<Long>();
       if (CollUtil.isNotEmpty(relationIds)) {
           for (Long relationId : relationIds) {
               SearchTableMainRelationLink tableMainRelationLink = SearchTableMainRelationLinkMapper.lambdaQuery().searchTableMainRelationId().equal(relationId).one();
               if (tableMainRelationLink == null) {
                   continue;
               }
               Optional.ofNullable(tableMainRelationLink.getParentRelationId()).ifPresent(newLink::add);
           }
       }
       if(CollUtil.isNotEmpty(newLink)){
           relationIds.addAll(newLink);
       }
        String searchTableName = searchTableMain.getSearchTableName();
        addOperation(sqlBuilder, searchTableName, isExtend, FROM.toUpperCase(), searchTableName, BLANK,tableAliasMap.get(searchTableMain.getSearchTableName()),tableMainId);
       //查询关联关系中隐藏的关系字段
       Long searchTableMainId = searchTableMain.getId();
       List<Long> longs = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(searchTableMainId).list().stream().map(SearchTableMainRelation::getId).collect(Collectors.toList());
       List<SearchTableMainRelationLink> relationLinks = SearchTableMainRelationLinkMapper.lambdaQuery().searchTableMainRelationId().in(longs).list();
       List<SearchTableMainRelationWhere> tableMainRelationWheres = SearchTableMainRelationWhereMapper.lambdaQuery().searchTableMainRelationId().equal(searchTableMain.getId()).list();
       if(CollUtil.isNotEmpty(relationLinks)){
           Set<Long> hiddenIds=new HashSet<>();
           for (SearchTableMainRelationLink link : relationLinks) {
               if (CollUtil.isNotEmpty(relationIds) && relationIds.contains(link.getSearchTableMainRelationId())) {
                   hiddenIds.add(link.getSearchTableMainRelationId());
               }
           }
           relationIds.addAll(hiddenIds);
       }
       if(CollUtil.isNotEmpty(relationLinks)){
           Set<Long> hiddenIds=new HashSet<>();
           for (SearchTableMainRelationWhere where : tableMainRelationWheres) {
               if (CollUtil.isNotEmpty(relationIds) && relationIds.contains(where.getSearchTableMainRelationId())) {
                   hiddenIds.add(where.getSearchTableMainRelationId());
               }
           }
           relationIds.addAll(hiddenIds);
       }
       String mainTb = StrUtil.isBlank(mainTableAlias) ? searchTableName : mainTableAlias;
       if (relationIds.size() > 0) {
           int j = 0;
           List<SearchTableMainRelation> mainRelations = SearchTableMainRelationMapper.lambdaQuery().searchTableMainId().equal(searchTableMainId).list();
           for (SearchTableMainRelation tableMainRelation : mainRelations) {
                j++;
                Boolean is = tableMainRelation.getIsExtend();
                String targetTableAlias = tableMainRelation.getTargetTableAlias();
                if(j==1){
                    sqlBuilder.append(TURN);
                }
                sqlBuilder.append(tableMainRelation.getLinkRelation().toUpperCase());
                sqlBuilder.append(BLANK);
                if (is) {
                    SearchExtendTable extendTable =SearchExtendTableMapper.lambdaQuery().extendTableName().equal(tableMainRelation.getTargetTableName()).searchTableMainId().equal(tableMainId).one();
                    String extendSql = extendTable.getExtendSql();
                    sqlBuilder.append(LEFT_BRACKET);
                    sqlBuilder.append(extendSql);
                    sqlBuilder.append(RIGHT_BRACKET);
                }
                sqlBuilder.append(BLANK);
                sqlBuilder.append(tableMainRelation.getTargetTableName());
                if(StrUtil.isNotBlank(targetTableAlias)){
                    sqlBuilder.append(BLANK);
                    sqlBuilder.append(targetTableAlias);
                }
                sqlBuilder.append(BLANK);
                sqlBuilder.append(ON);
                sqlBuilder.append(BLANK);
                List<SearchTableMainRelationLink> mainRelationLinks = SearchTableMainRelationLinkMapper.lambdaQuery().searchTableMainRelationId().equal(tableMainRelation.getId()).list();
                List<SearchTableMainRelationWhere> mainRelationWheres = SearchTableMainRelationWhereMapper.lambdaQuery().searchTableMainRelationId().equal(tableMainRelation.getId()).list();
                if(CollUtil.isNotEmpty(mainRelationLinks)){
                    for (int i = 0; i < mainRelationLinks.size(); i++) {
                        SearchTableMainRelationLink relationLink=mainRelationLinks.get(i);
                        if(!StrUtil.containsIgnoreCase(relationLink.getTableColumnName(),LEFT_BRACKET)
                                && !StrUtil.containsIgnoreCase(relationLink.getTableColumnName(),RIGHT_BRACKET)){
                            if(StrUtil.isNotBlank(targetTableAlias)){
                                sqlBuilder.append(targetTableAlias);
                            }else {
                                if(StrUtil.isNotBlank(tableMainRelation.getTargetTableName())){
                                    sqlBuilder.append(tableMainRelation.getTargetTableName());
                                }else {
                                    sqlBuilder.append(mainTb);
                                }
                            }
                            sqlBuilder.append(POINT);
                        }
                        sqlBuilder.append(relationLink.getTableColumnName().trim());
                        sqlBuilder.append(EQUAL);
                        String tableAlias = relationLink.getTargetTableAlias();
                        String targetTableColumnName = relationLink.getTargetTableColumnName();
                        if(!StrUtil.containsIgnoreCase(targetTableColumnName,LEFT_BRACKET)
                                && !StrUtil.containsIgnoreCase(targetTableColumnName,RIGHT_BRACKET)){
                            sqlBuilder.append(StrUtil.isNotBlank(tableAlias) ? tableAlias : StrUtil.isBlank(relationLink.getTargetTableName()) ? mainTb : relationLink.getTargetTableName());
                            sqlBuilder.append(POINT);
                        }
                        sqlBuilder.append(relationLink.getTargetTableColumnName());
                        if (i != mainRelationLinks.size() - 1) {
                            sqlBuilder.append(BLANK);
                            sqlBuilder.append(AND);
                            sqlBuilder.append(BLANK);
                        }
                    }
                }
                if(CollUtil.isNotEmpty(mainRelationWheres)){
                    sqlBuilder.append(BLANK);
                    sqlBuilder.append(AND);
                    sqlBuilder.append(BLANK);
                    for (int i = 0; i < mainRelationWheres.size(); i++) {
                        SearchTableMainRelationWhere where=mainRelationWheres.get(i);
                        sqlBuilder.append(where.getWhereInfo());
                        if (i != mainRelationWheres.size() - 1) {
                            sqlBuilder.append(BLANK);
                            sqlBuilder.append(AND);
                            sqlBuilder.append(BLANK);
                        }
                    }
                }
               if (j != mainRelations.size()) {
                   sqlBuilder.append(TURN);
               }
            }
        }
        List<SearchTableWhere> tableWheres = SearchTableWhereMapper.lambdaQuery().pageTag().equal(pagTag).list();
        if(CollUtil.isNotEmpty(tableWheres)){
            Set<Long> checkWhereId=new HashSet<>();
            for (SearchTableWhere where : tableWheres) {
                Long whereId = where.getId();
                List<SearchTableWhereRelation> tableWhereRelations = SearchTableWhereRelationMapper.lambdaQuery().searchTableWhereId().equal(whereId).list();
                if(CollUtil.isEmpty(tableWhereRelations)){
                    checkWhereId.add(whereId);
                }else {
                    Set<Long> relations=new HashSet<>();
                    for (SearchTableWhereRelation whereRelation : tableWhereRelations) {
                        relations.add(whereRelation.getSearchTableMainRelationId());
                        List<Long> parentIds = getRelationParentIds(whereRelation.getSearchTableMainRelationId());
                        relations.addAll(parentIds);
                    }
                    boolean containsAll = CollUtil.containsAll(relationIds, relations);
                    if(containsAll){
                        checkWhereId.add(whereId);
                    }
                }
            }
            sqlBuilder.append(TURN);
            sqlBuilder.append(WHERE.toUpperCase());
            sqlBuilder.append(BLANK);
            int i=0;
            for (SearchTableWhere where : tableWheres) {
                if(checkWhereId.contains(where.getId())){
                    if(i !=0){
                        sqlBuilder.append(TURN);
                        sqlBuilder.append(where.getRelation());
                    }
                    i++;
                    sqlBuilder.append(BLANK);
                    sqlBuilder.append(where.getTableWhere().trim());
                }
            }
        }
       List<SearchTableGroup> tableGroups = SearchTableGroupMapper.lambdaQuery().pageTag().equal(pagTag).list();
       if(CollUtil.isNotEmpty(tableGroups)){
           Set<Long> checkWhereId=new HashSet<>();
           for (SearchTableGroup tableGroup : tableGroups) {
               Long groupId = tableGroup.getId();
               List<SearchTableGroupRelation> tableWhereRelations = SearchTableGroupRelationMapper.lambdaQuery().searchTableGroupId().equal(groupId).list();
               if(CollUtil.isEmpty(tableWhereRelations)){
                   checkWhereId.add(groupId);
               }else {
                   Set<Long> relations=new HashSet<>();
                   for (SearchTableGroupRelation relation : tableWhereRelations) {
                       relations.add(relation.getSearchTableMainRelationId());
                       List<Long> parentIds = getRelationParentIds(relation.getSearchTableMainRelationId());
                       relations.addAll(parentIds);
                   }
                   boolean containsAll = CollUtil.containsAll(relationIds, relations);
                   if(containsAll){
                       checkWhereId.add(groupId);
                   }
               }
           }
           sqlBuilder.append(TURN);
           sqlBuilder.append(GROUP_BY);
           sqlBuilder.append(TURN);
           for (SearchTableGroup t : tableGroups) {
               if (checkWhereId.contains(t.getId())) {
                   boolean isExist = false;
                   for (String column : columnShow) {
                       boolean contains = column.contains(t.getTableGroup().trim());
                       if (contains) {
                           isExist = true;
                           break;
                       }
                   }
                   boolean contains = columnShow.contains(t.getTableGroup().trim()) || isExist;
                   if (contains) {
                       sqlBuilder.append(t.getTableGroup());
                       sqlBuilder.append(",");
                       sqlBuilder.append(TURN);
                   }
               }
           }
           sqlBuilder = new StringBuilder(StrUtil.removeSuffix(sqlBuilder.toString().trim(), COMMA));
       }
       List<SearchTableHaving> tableHaving = SearchTableHavingMapper.lambdaQuery().pageTag().equal(pagTag).list();
       if(CollUtil.isNotEmpty(tableHaving)){
           Set<Long> checkWhereId=new HashSet<>();
           for (SearchTableHaving having : tableHaving) {
               Long havingId = having.getId();
               List<SearchTableHavingRelation> tableWhereRelations = SearchTableHavingRelationMapper.lambdaQuery().searchTableHavingId().equal(havingId).list();
               if(CollUtil.isEmpty(tableWhereRelations)){
                   checkWhereId.add(havingId);
               }else {
                   Set<Long> relations=new HashSet<>();
                   for (SearchTableHavingRelation relation : tableWhereRelations) {
                       relations.add(relation.getSearchTableMainRelationId());
                       List<Long> parentIds = getRelationParentIds(relation.getSearchTableMainRelationId());
                       relations.addAll(parentIds);
                   }
                   boolean containsAll = CollUtil.containsAll(relationIds, relations);
                   if(containsAll){
                       checkWhereId.add(havingId);
                   }
               }
           }
           sqlBuilder.append(TURN);
           sqlBuilder.append(HAVING);
           sqlBuilder.append(TURN);
           int i=0;
           for (SearchTableHaving o : tableHaving) {
               if (checkWhereId.contains(o.getId())) {
                   if (i != 0) {
                       sqlBuilder.append(TURN);
                       sqlBuilder.append(o.getRelation());
                   }
                   sqlBuilder.append(BLANK);
                   sqlBuilder.append(o.getTableHavingWhere());
                   i++;
               }
           }
       }
       return sqlBuilder.toString().trim();
    }

    private static List<Long> getRelationParentIds(Long id){
        List<Long> ids = new ArrayList<>();
        List<SearchTableMainRelationLink> mainRelationLinks = SearchTableMainRelationLinkMapper.lambdaQuery().searchTableMainRelationId().equal(id).list();
        if (CollUtil.isNotEmpty(mainRelationLinks)) {
            List<Long> collect = mainRelationLinks.stream().map(SearchTableMainRelationLink::getParentRelationId).filter(Objects::nonNull).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(collect)) {
                for (Long col : collect) {
                    ids.add(col);
                    ids.addAll(getRelationParentIds(col));
                }
            }
        }
        if (CollUtil.isNotEmpty(ids)) {
            return ids.stream().distinct().collect(Collectors.toList());
        }
       return new ArrayList<>();
    }

    private static void addOperation(StringBuilder sqlBuilder, String searchTableName, Boolean isExtend, String operatorHead, String on, String operatorTail, String alias, Long mainId) {
        sqlBuilder.append(operatorHead);
        if (isExtend) {
            SearchExtendTable extendTable =SearchExtendTableMapper.lambdaQuery().extendTableName().equal(searchTableName).searchTableMainId().equal(mainId).one();
            String extendSql = extendTable.getExtendSql();
            sqlBuilder.append(LEFT_BRACKET);
            sqlBuilder.append(extendSql);
            sqlBuilder.append(RIGHT_BRACKET);
        }
        sqlBuilder.append(BLANK);
        sqlBuilder.append(on);
        if(StrUtil.isNotBlank(alias) && (! searchTableName.equals(alias))){
            sqlBuilder.append(BLANK);
            sqlBuilder.append(alias);
        }
        sqlBuilder.append(operatorTail);
    }

    /**
     * 获取列或者引用表名集合
     */
    public static List<String> getSQLCutColumnOrTable(String sql, String tag) {
        if (!StrUtil.equalsAny(tag, "TABLE", "COLUMN")) {
            return new ArrayList<>();
        }
        final List<String> keys = ListUtil.of("end","case","when","else","then","max","min","sum", "avg", "ifnull", "group_concat", "concat", "any_value", "if", "not");
        String replaceSql = sql.replaceAll("'.*'", "");
        replaceSql = replaceSql.replaceAll("/\\*alias.*\\*/", "");
        Pattern compile = Pattern.compile("(`\\w+`\\.\\w+)|(\\w+\\.\\w+)|(\\w+)");
        Matcher matcher = compile.matcher(replaceSql);
        Set<String> tbs = new HashSet<>();
        Set<String> columns = new HashSet<>();
        while (matcher.find()) {
            String group = matcher.group().trim();
            if ((!keys.contains(group.toLowerCase())) && (!NumberUtil.isNumber(group))) {
                if (group.contains(".")) {
                    String[] split = group.split("\\.");
                    String tb = split[0];
                    tbs.add(tb.replaceAll("`", ""));
                    columns.add(split[1]);
                } else {
                    tbs.add("MAIN_TABLE");
                    columns.add(group);
                }
            }else {
                break;
            }
        }
        if (StrUtil.equals(tag, "TABLE")) {
            return new ArrayList<>(tbs);
        }
        return new ArrayList<>(columns);
    }

    private static String searchKeyReplace(String column,String regex,String replaceKey){
       return column.replaceAll(regex,replaceKey);
    }
}
