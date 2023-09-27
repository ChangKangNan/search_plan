package org.ckn.sp.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.*;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import javafx.util.Pair;
import org.ckn.sp.constants.SearchConditionsEnum;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.bean.SearchConfigInnerCondition;
import org.ckn.sp.fm.bean.SearchTableColumn;
import org.ckn.sp.fm.dao.SearchConfigInfoMapper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static cn.ft.ckn.fastmapper.util.PackageSqlUtil.LEFT_BRACKETS;
import static cn.ft.ckn.fastmapper.util.PackageSqlUtil.RIGHT_BRACKETS;
import static org.ckn.sp.constants.StrConstants.*;
import static org.ckn.sp.util.RegxUtil.JOIN_SPLIT;
import static org.ckn.sp.util.RegxUtil.ReplaceToReg;

/**
 * @author ckn
 */
public class SqlUtil {

    public static int getIndex(String str, String key) {
        int index = StrUtil.indexOfIgnoreCase(str, "\n" + key + "\n");
        if (index != -1) {
            return index;
        }
        index = StrUtil.indexOfIgnoreCase(str, StrUtil.SPACE + key + "\n");
        if (index != -1) {
            return index;
        }
        index = StrUtil.indexOfIgnoreCase(str, StrUtil.SPACE + key + StrUtil.SPACE);
        return index;
    }


    public static List<SearchTableColumn> getColumns(String sql) {
        //筛选对应列
        sql = StrUtil.subByCodePoint(sql, StrUtil.indexOfIgnoreCase(sql, SELECT), StrUtil.indexOfIgnoreCase(sql, FROM));
        //找寻处理所有备注信息
        sql = sql.replaceAll("#(.*)\n", "#");
        sql = sql.replaceAll("(,(\\s+)#)", ",#");
        sql = StrUtil.removePrefixIgnoreCase(sql, "#");
        sql = StrUtil.removePrefixIgnoreCase(sql, SELECT);
        List<String> columns = getMatchColumns(sql);
        List<SearchTableColumn> columnList = new ArrayList<>();
        for (String column : columns) {
            SearchTableColumn searchTableColumn = new SearchTableColumn();
            boolean as = StrUtil.containsIgnoreCase(column, AS);
            if (as) {
                String[] split = column.split("(?i)" + AS);
                searchTableColumn.setSearchTableColumnInfo(split[0].trim());
                searchTableColumn.setSearchTableColumn(split[1].trim());
            } else {
                searchTableColumn.setSearchTableColumnInfo(column.trim());
                String[] split = column.split("\\.");
                if (split.length == 2) {
                    searchTableColumn.setSearchTableColumn(split[1].trim());
                } else {
                    searchTableColumn.setSearchTableColumn(column.trim());
                }
            }
            columnList.add(searchTableColumn);
        }
        return columnList;
    }


    public static List<String> getMatchColumns(String sql) {
        int match = 0;
        int matchBlack = 0;
        StringBuilder str = new StringBuilder();
        List<String> list = new ArrayList<>();
        for (int j = 0; j < sql.length(); j++) {
            char c = sql.charAt(j);
            if ((match == 1 && matchBlack == 0) || (c == '#' && (j == sql.length() - 1))) {
                String toString = str.toString();
                toString = StrUtil.trimStart(toString);
                toString = StrUtil.trimEnd(toString);
                toString = StrUtil.removeSuffix(toString, ",#");
                list.add(toString);
                str = new StringBuilder();
                match = 0;
            }
            if (match == 0) {
                str.append(c);
            }
            if (c == '#') {
                match = 1;
            }
            if (c == '(') {
                matchBlack++;
            }
            if (c == ')') {
                matchBlack--;
            }
            if (c == ',' && j != (sql.length() - 1) && matchBlack == 0) {
                if (sql.charAt(j + 1) != '#') {
                    str = new StringBuilder();
                }
            }
        }
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().filter(StrUtil::isNotBlank).filter(t -> !StrUtil.equals(t, "#")).collect(Collectors.toList());
    }

    public static String replaceLineSeparator(String sql) {
        if (sql.contains("\r\n")) {
            sql = sql.replaceAll("\r\n", "\n");
        } else if (sql.contains("\r")) {
            sql = sql.replaceAll("\r", "\n");
        }
        return sql;
    }


    public static String[] getExtendTables(String sql) {
        int from = sql.toLowerCase().indexOf(FROM);
        int where = sql.toLowerCase().lastIndexOf(WHERE);
        String str = sql.substring(from, where);
        int count = 0;
        List<String> table = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (count < 0) {
                break;
            }
            String temp = str.substring(i, i + 1);
            if (temp.equals("(")) {
                count++;
            }
            if (count > 0) {
                stringBuilder.append(temp);
            }
            if (temp.equals(")")) {
                count--;
                if (count == 0) {
                    String string = stringBuilder.toString();
                    if (StrUtil.containsIgnoreCase(string, SELECT)
                            && StrUtil.containsIgnoreCase(string, FROM)) {
                        String sub = str.substring(i + 1);
                        Pattern pattern = Pattern.compile("\\w+");
                        Matcher matcher = pattern.matcher(sub);
                        if (matcher.find()) {
                            String group = matcher.group();
                            table.add(group.trim());
                        }
                    }
                    stringBuilder = new StringBuilder();
                }
            }
        }
        return ArrayUtil.toArray(table, String.class);
    }


    public static String[] getExtendTableSQL(String str) {
        int count = 0;
        List<String> table = new ArrayList<>();
        int check = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (count < 0) {
                break;
            }
            String temp = str.substring(i, i + 1);
            if (check == 1) {
                stringBuilder.append(temp);
            }
            if (temp.equals("(")) {
                count++;
                check = 1;
            }
            if (temp.equals(")")) {
                count--;
                if (count == 0) {
                    String sql = stringBuilder.substring(0, stringBuilder.length() - 1);
                    if (StrUtil.containsIgnoreCase(sql, SELECT) && StrUtil.containsIgnoreCase(sql, FROM)) {
                        table.add(sql);
                    }
                    stringBuilder = new StringBuilder();
                    check = 0;
                }
            }
        }
        return ArrayUtil.toArray(table, String.class);
    }

    public static List<String> getInnerColumns(String sql) {
        int matchBlack = 0;
        List<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < sql.length(); j++) {
            char c = sql.charAt(j);
            if (c == '(') {
                matchBlack++;
            }
            if (c == ')') {
                matchBlack--;
            }
            if (c != '(' && c != ')' && c != ',') {
                stringBuilder.append(c);
            }
            if (c == ',' && matchBlack == 0) {
                list.add(getInnerColumnString(stringBuilder.toString()));
                stringBuilder = new StringBuilder();
            }
            if (j == (sql.length() - 1) && matchBlack == 0) {
                list.add(getInnerColumnString(stringBuilder.toString()));
                stringBuilder = new StringBuilder();
            }
        }
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }

    public static String getInnerColumnString(String sql) {
        String[] strings = sql.split("(?i) as ");
        return strings.length == 2 ? strings[1].trim() : (strings[0].trim().contains(".") ? strings[0].trim().split("\\.")[1].trim() : strings[0].trim());
    }


    public static Map<String, String> getTableNameNotInExtends(String str, String[] relationTables) {
        String replaceAll = str.replaceAll("\\son\\s.*", "");
        String[] strings = replaceAll.split(JOIN_SPLIT);
        Map<String, String> stringMap = new HashMap<>();
        for (String string : strings) {
            String trimStart = StrUtil.trimStart(string);
            String[] split = trimStart.split(StrUtil.SPACE);
            String tb = split.length > 1 ? split[1].trim() : split[0].trim();
            if (!ArrayUtil.contains(relationTables, tb)) {
                continue;
            }
            stringMap.put(tb, split[0].trim());
        }
        return stringMap;
    }


    public static List<String> getMatchColumn(String column) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\w.]+");
        Matcher matcher = pattern.matcher(column);
        while (matcher.find()) {
            String group = matcher.group().trim();
            if (!StrUtil.containsAnyIgnoreCase(group, ".", "'") && !StrUtil.equalsAnyIgnoreCase(
                    group,
                    "distinct",
                    "true", "false",
                    "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "LOCALTIME",
                    "ALL", "ANY", "and", "or", "sum", "avg", "to_days", "if", "is", "null", "as") && (!NumberUtil.isNumber(group))) {
                int endIndex = column.indexOf(group) + group.length();
                if (endIndex <= (column.length() - 1)) {
                    String para = column.charAt(endIndex) + "";
                    if (StrUtil.equalsAny(para, "(", ")", "'")) {
                        continue;
                    }
                }
                list.add(group);
            }
        }
        return list;
    }


   public static Set<String> getMatchTable(String sql) {
        Pattern pattern = Pattern.compile("[\\w]+\\.");
        Matcher matcher = pattern.matcher(sql);
        Set<String> tbSet = new HashSet<>();
        while (matcher.find()) {
            String group = matcher.group();
            if (group.contains(".")) {
                String prefix = group.substring(0, group.length() - 1);
                if (!NumberUtil.isNumber(prefix)) {
                    tbSet.add(prefix);
                }
            }
        }
        return tbSet;
    }

    public static String getFinalSql(String sql, String axis, String link_sql, String relation, Boolean jump) {
        String[] strings = sql.split(System.lineSeparator());
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replaceAll("#.*", "");
        }
        sql = ArrayUtil.join(strings, System.lineSeparator());
        int where = sql.toUpperCase().lastIndexOf(" WHERE ");
        int groupBy = sql.toUpperCase().lastIndexOf(" GROUP BY ");
        if(where > groupBy){
            groupBy = -1;
        }
        if(relation == null){
            relation = "and";
        }
        int union = sql.toUpperCase().lastIndexOf(" UNION");
        int unionAll = sql.toUpperCase().lastIndexOf(" UNION ALL");
        int having = sql.toUpperCase().lastIndexOf(" HAVING ");
        StringBuilder finalSql = new StringBuilder();
        if (!jump) {
            if (union != -1 && unionAll != -1) {
                String[] sqlSplit = splitUnion(sql, "( UNION ALL )|("+System.lineSeparator()+"UNION ALL"+System.lineSeparator()+")|( UNION ALL"+System.lineSeparator()+")|("+System.lineSeparator()+"UNION ALL )");
                int end = sqlSplit.length;
                int i = 0;
                String firstSql = sqlSplit[0];
                List<String> columnList = getColumnList(firstSql);
                for (String child : sqlSplit) {
                    i++;
                    String change_sql = getExistLinkSQL(child, link_sql, columnList);
                    String finalSqlRoll = getFinalSql(child, axis, change_sql, relation, true);
                    finalSql.append(StrUtil.isBlank(finalSqlRoll) ? child : finalSqlRoll);
                    if (i != end) {
                        finalSql.append(" UNION ALL ");
                    }
                }
                return finalSql.toString();
            }
            if (union != -1) {
                String[] sqlSplit = splitUnion(sql, "( UNION )|("+System.lineSeparator()+"UNION"+System.lineSeparator()+")|( UNION"+System.lineSeparator()+")|("+System.lineSeparator()+"UNION )");
                int i = 0;
                int end = sqlSplit.length;
                for (String child : sqlSplit) {
                    i++;
                    String firstSql = sqlSplit[0];
                    List<String> columnList = getColumnList(firstSql);
                    String change_sql = getExistLinkSQL(child, link_sql, columnList);
                    String finalSqlRoll = getFinalSql(child, axis, change_sql, relation, true);
                    finalSql.append(StrUtil.isBlank(finalSqlRoll) ? child : finalSqlRoll);
                    if (i != end) {
                        finalSql.append(" UNION ");
                    }
                }
                return finalSql.toString();
            }

            List<String> columnList = getColumnList(sql);
            link_sql = getExistLinkSQL(sql, link_sql, columnList);
        }
        //校验是否是坐标表
        String[] split = axis.split("\\.");
        if (split.length == 1) {
            if (groupBy != -1) {
                String groupBySql = sql.substring(groupBy);
                String prefix_where = sql.substring(0, groupBy);
                if (containsCompositeFunction(link_sql)) {
                    if (having == -1) {
                        finalSql = new StringBuilder(prefix_where + groupBySql + " having " + link_sql);
                    } else {
                        finalSql = new StringBuilder(prefix_where + groupBySql + " " + relation + " " + link_sql);
                    }
                } else {
                    if (where == -1) {
                        finalSql = new StringBuilder(prefix_where + " where " + link_sql + System.lineSeparator() + groupBySql);
                    } else {
                        finalSql = new StringBuilder(prefix_where + " " + relation + " " + link_sql + System.lineSeparator() + groupBySql);
                    }
                }
            } else {
                if (where == -1) {
                    finalSql = new StringBuilder(sql + " where " + link_sql);
                } else {
                    finalSql = new StringBuilder(sql + " " + relation + " " + link_sql);
                }
            }
        } else {
            String targetTable = split[1];
            int from_index = sql.toUpperCase().lastIndexOf(" FROM ");
            if (groupBy != -1) {
                if (where == -1) {
                    String tableNames = sql.substring(from_index, groupBy);
                    String[] table = tableNames.split(" ");
                    for (String t : table) {
                        if (t.equals(targetTable)) {
                            String groupBySql = sql.substring(groupBy);
                            String prefix_where = sql.substring(0, groupBy);
                            if (containsCompositeFunction(link_sql)) {
                                if (having == -1) {
                                    finalSql = new StringBuilder(prefix_where + groupBySql + " having " + link_sql);
                                } else {
                                    finalSql = new StringBuilder(prefix_where + groupBySql + " " + relation + " " + link_sql);
                                }
                            } else {
                                finalSql = new StringBuilder(prefix_where + " where " + link_sql + System.lineSeparator() + groupBySql);
                            }
                        }
                    }
                } else {
                    String tableNames = sql.substring(from_index, where);
                    String[] table = tableNames.split(" ");
                    for (String t : table) {
                        if (t.trim().equals(targetTable)) {
                            String groupBySql = sql.substring(groupBy);
                            String prefix_where = sql.substring(0, groupBy);
                            if (containsCompositeFunction(link_sql)) {
                                if (having == -1) {
                                    finalSql = new StringBuilder(prefix_where + groupBySql + " having " + link_sql);
                                } else {
                                    finalSql = new StringBuilder(prefix_where + groupBySql + " " + relation + " " + link_sql);
                                }
                            } else {
                                finalSql = new StringBuilder(prefix_where + " " + relation + " " + link_sql + System.lineSeparator() + groupBySql);
                            }
                        }
                    }
                }
            } else {
                if (where == -1) {
                    String tableNames = sql.substring(from_index);
                    String[] table = tableNames.split(" ");
                    for (String t : table) {
                        if (t.trim().equals(targetTable)) {
                            finalSql = new StringBuilder(sql + " where " + link_sql);
                        }
                    }
                } else {
                    String tableNames = sql.substring(from_index, where);
                    String[] table = tableNames.split(" ");
                    for (String t : table) {
                        if (t.trim().equals(targetTable)) {
                            finalSql = new StringBuilder(sql + " " + relation + " " + link_sql);
                        }
                    }
                }
            }
        }
        return finalSql.toString();
    }

    public static String getInnerPackageSql(SearchQueryDTO.QueryInfo queryInfo, SearchConfigInnerCondition condition) {
        StrBuilder sqlBuilder = new StrBuilder();
        String searchType = condition.getSearchType();
        SearchConditionsEnum conditionsEnum = SearchConditionsEnum.getSearchConditions(queryInfo.getSearchCondition());
        if (conditionsEnum == null) {
            return "";
        }
        switch ((conditionsEnum)) {
            case IS:
                if (!StrUtil.equalsAny(queryInfo.getSearchValue(), "NULL", "NOT NULL")) {
                    throw new RuntimeException("非法查询");
                }
                sqlBuilder.append(queryInfo.getSearchKey()).append(conditionsEnum.expression);
                if (!StrUtil.equals(searchType, "number")) {
                    sqlBuilder.append("'");
                }
                sqlBuilder.append(queryInfo.getSearchValue());
                if (!StrUtil.equals(searchType, "number")) {
                    sqlBuilder.append("'");
                }
                sqlBuilder.append(" ");
                break;
            case In:
            case NotIn:
                String[] strings = StrUtil.splitToArray(queryInfo.getSearchValue(), StrUtil.C_COMMA);
                if (ArrayUtil.isEmpty(strings)) {
                    return "";
                }
                sqlBuilder.append(queryInfo.getSearchKey()).append(conditionsEnum.expression).append(LEFT_BRACKETS);
                for (String s : strings) {
                    if (!StrUtil.equals(searchType, "number")) {
                        sqlBuilder.append("'");
                    }
                    sqlBuilder.append(s);
                    if (!StrUtil.equals(searchType, "number")) {
                        sqlBuilder.append("'");
                    }
                    sqlBuilder.append(StrUtil.COMMA);
                }
                sqlBuilder.del(sqlBuilder.length() - 1, sqlBuilder.length()).append(RIGHT_BRACKETS);
                sqlBuilder.append(System.lineSeparator());
                break;
            case Like:
            case NotLike:
                sqlBuilder.append(queryInfo.getSearchKey()).append(conditionsEnum.expression).append(" ")
                        .append(StrBuilder.create("'%", queryInfo.getSearchValue(), "%'").toString());
                break;
            case Match:
            case NotMatch:
                sqlBuilder.append(conditionsEnum.name).append(LEFT_BRACKETS).append(queryInfo.getSearchKey())
                        .append(RIGHT_BRACKETS).append(conditionsEnum.expression).append(LEFT_BRACKETS);
                if (!StrUtil.equals(searchType, "number")) {
                    sqlBuilder.append("'");
                }
                sqlBuilder.append(queryInfo.getSearchValue());
                if (!StrUtil.equals(searchType, "number")) {
                    sqlBuilder.append("'");
                }
                sqlBuilder.append(RIGHT_BRACKETS);
                sqlBuilder.append(System.lineSeparator());
                break;
            default:
                sqlBuilder.append(queryInfo.getSearchKey()).append(conditionsEnum.expression);
                if (!StrUtil.equals(searchType, "number")) {
                    sqlBuilder.append("'");
                }
                sqlBuilder.append(queryInfo.getSearchValue());
                if (!StrUtil.equals(searchType, "number")) {
                    sqlBuilder.append("'");
                }
                break;
        }
        return sqlBuilder.toString();
    }


    private static String[] splitUnion(String sql, String regex) {
        String[] strings = sql.split("(?i)" + regex);
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            int black = 0;
            for (int j = str.length() - 1; j >= 0; j--) {
                char c = str.charAt(j);
                if (c == ')') {
                    black++;
                }
                if (c == '(') {
                    black--;
                }
            }
            if (black > 0) {
                index.add(i);
            }
        }
        String unionTag;
        if (regex.toUpperCase().contains("UNION ALL")) {
            unionTag = System.lineSeparator() + "UNION ALL" + System.lineSeparator();
        } else {
            unionTag = System.lineSeparator() + "UNION" + System.lineSeparator();
        }
        for (int i = strings.length - 1; i >= 0; i--) {
            if (index.contains(i)) {
                strings[i - 1] = strings[i - 1] + unionTag + strings[i];
            }
        }
        String[] array = new String[strings.length - index.size()];
        int z = 0;
        for (int i = 0; i < strings.length; i++) {
            if (!index.contains(i)) {
                array[z] = strings[i];
                z++;
            }
        }
        return array;
    }


    private static String getExistLinkSQL(String sql, String linkSQL, List<String> firstNames) {
        String[] strings = linkSQL.split("(?i)(?=>|<|>=|<=|<>| not in | in |!=|=| not like | like )");
        String key = strings[0].trim();
        boolean contains = key.contains(".");
        if (contains && (!StrUtil.containsAny(key, "(", ")"))) {
            return linkSQL;
        }
        if (StrUtil.containsIgnoreCase(key, "select ")) {
            return linkSQL;
        }
        String column_info = key;
        if (StrUtil.containsAny(key, "(", ")", "+", "-", "*", "/")) {
            List<String> cutColumnOrTable = SQLUtils.getSQLCutColumnOrTable(key, "COLUMN");
            if (CollUtil.isNotEmpty(cutColumnOrTable)) {
                column_info = cutColumnOrTable.get(0);
            }
        }
        Map<String, String> column = getColumnMap(sql);
        String s = column.get(key);
        if (StrUtil.containsIgnoreCase(s, "select ")) {
            Pattern SELECT_REG = Pattern.compile("(select\\s+(\\w+))", Pattern.CASE_INSENSITIVE);
            Matcher matcher = SELECT_REG.matcher(s);
            while (matcher.find()) {
                String group = matcher.group(1);
                String group_key = matcher.group(2);
                String innerKey = column.get(group_key.trim());
                if (innerKey != null) {
                    s = s.replace(group, innerKey.trim());
                }
            }
            if (StrUtil.containsIgnoreCase(s, "select ")) {
                return linkSQL;
            }
        }
        if (StrUtil.isBlank(s)) {
            if (CollUtil.isEmpty(firstNames)) {
                return linkSQL;
            }
            for (int i = 0; i < firstNames.size(); i++) {
                String firstName = firstNames.get(i);
                if (firstName.trim().equals(column_info.trim())) {
                    List<String> columnInfoList = getColumnInfoList(sql);
                    String info = columnInfoList.get(i);
                    if (StrUtil.isBlank(info)) {
                        break;
                    } else {
                        s = info;
                    }
                }
            }
        }
        strings[0] = key.replaceAll(column_info, s);
        return ArrayUtil.join(strings, "");
    }

    private static Map<String, String> getColumnMap(String sql) {
        Map<String, String> column = new HashMap<>();
        int from = sql.toUpperCase().indexOf("FROM");
        String substring = sql.substring(0, from).trim();
        String select = StrUtil.removePrefixIgnoreCase(substring, "select");
        int c_black = 0;
        StrBuilder str = new StrBuilder();
        for (int i = 0; i < select.length(); i++) {
            char charAt = select.charAt(i);
            if (charAt == '(') {
                c_black++;
            } else if (charAt == ')') {
                c_black--;
            }
            str.append(charAt);
            if (c_black == 0 && (charAt == ',' || i == (select.length() - 1))) {
                String string = str.toString().trim();
                boolean b = string.toLowerCase().contains(" as ");
                boolean c = string.toLowerCase().contains(")as ");
                if (b || c) {
                    String[] split;
                    if (b) {
                        split = string.split("(?i) as ");
                    } else {
                        split = string.split("(?i)as ");
                    }
                    column.put(StrUtil.removeSuffix(split[1].trim(), ","), split[0].trim());
                } else {
                    int length = string.length();
                    int black = 0;
                    int end_index = 0;
                    for (int j = 0; j < length; j++) {
                        char charAts = string.charAt(j);
                        if (charAts == '(') {
                            black++;
                        } else if (charAts == ')') {
                            black--;
                        }
                        if (black == 0 && charAts == ' ' && (!StrUtil.containsAny(string.substring(j), "+", "-", "*", "/"))) {
                            end_index = j;
                            break;
                        }
                    }
                    String name = string.substring(end_index);
                    name = StrUtil.removeSuffix(name, ",");
                    String info = string.substring(0, end_index);
                    if (StrUtil.isBlank(info)) {
                        info = name;
                        int indexOf = name.indexOf(".");
                        if (indexOf != -1) {
                            name = name.substring(indexOf + 1);
                        }
                    }
                    column.put(name.trim(), info.trim());
                }
                str = new StrBuilder();
            }
        }
        return column;
    }

    private static List<String> getColumnList(String sql) {
        List<String> column = new ArrayList<>();
        int from = sql.toUpperCase().indexOf("FROM");
        String substring = sql.substring(0, from).trim();
        String select = StrUtil.removePrefixIgnoreCase(substring, "select");
        int c_black = 0;
        StrBuilder str = new StrBuilder();
        for (int i = 0; i < select.length(); i++) {
            char charAt = select.charAt(i);
            if (charAt == '(') {
                c_black++;
            } else if (charAt == ')') {
                c_black--;
            }
            str.append(charAt);
            if (c_black == 0 && (charAt == ',' || i == (select.length() - 1))) {
                String string = str.toString().trim();
                boolean b = string.toLowerCase().contains(" as ");
                boolean c = string.toLowerCase().contains(")as ");
                if (b || c) {
                    String[] split;
                    if (b) {
                        split = string.split("(?i) as ");
                    } else {
                        split = string.split("(?i)as ");
                    }
                    column.add(StrUtil.removeSuffix(split[1].trim(), ","));
                } else {
                    int length = string.length();
                    int black = 0;
                    int end_index = 0;
                    for (int j = 0; j < length; j++) {
                        char charAts = string.charAt(j);
                        if (charAts == '(') {
                            black++;
                        } else if (charAts == ')') {
                            black--;
                        }
                        if (black == 0 && charAts == ' ' && (!StrUtil.containsAny(string.substring(j), "+", "-", "*", "/"))) {
                            end_index = j;
                            break;
                        }
                    }
                    String name = string.substring(end_index);
                    name = StrUtil.removeSuffix(name, ",");
                    String info = string.substring(0, end_index);
                    if (StrUtil.isBlank(info)) {
                        int indexOf = name.indexOf(".");
                        if (indexOf != -1) {
                            name = name.substring(indexOf + 1);
                        }
                    }
                    column.add(name.trim());
                }
                str = new StrBuilder();
            }
        }
        return column;
    }

    private static List<String> getColumnInfoList(String sql) {
        List<String> columnInfoList = new ArrayList<>();
        int from = sql.toUpperCase().indexOf("FROM");
        String substring = sql.substring(0, from).trim();
        String select = StrUtil.removePrefixIgnoreCase(substring, "select");
        int c_black = 0;
        StrBuilder str = new StrBuilder();
        for (int i = 0; i < select.length(); i++) {
            char charAt = select.charAt(i);
            if (charAt == '(') {
                c_black++;
            } else if (charAt == ')') {
                c_black--;
            }
            str.append(charAt);
            if (c_black == 0 && (charAt == ',' || i == (select.length() - 1))) {
                String string = str.toString().trim();
                String lowerCase = string.toLowerCase();
                boolean b = lowerCase.contains(" as ");
                boolean c = lowerCase.contains(")as ");
                Pattern compile = Pattern.compile(".*\\(.*as.*\\).*");
                Matcher matcher = compile.matcher(lowerCase);
                boolean find = matcher.find();
                if (b || c || find) {
                    String[] split = new String[3];
                    if (b) {
                        split = string.split("(?i) as ");
                    } else if (c) {
                        split = string.split("(?i)as ");
                    }
                    if (find) {
                        columnInfoList.add(StrUtil.removeSuffix(string, ","));
                    } else {
                        columnInfoList.add(split[0].trim());
                    }
                } else {
                    int length = string.length();
                    int black = 0;
                    int end_index = 0;
                    for (int j = 0; j < length; j++) {
                        char charAts = string.charAt(j);
                        if (charAts == '(') {
                            black++;
                        } else if (charAts == ')') {
                            black--;
                        }
                        if (black == 0 && charAts == ' ' && (!StrUtil.containsAny(string.substring(j), "+", "-", "*", "/"))) {
                            end_index = j;
                            break;
                        }
                    }
                    String name = string.substring(end_index);
                    name = StrUtil.removeSuffix(name, ",");
                    String info = string.substring(0, end_index);
                    if (StrUtil.isBlank(info)) {
                        info = name;
                    }
                    columnInfoList.add(info.trim());
                }
                str = new StrBuilder();
            }
        }
        return columnInfoList;
    }

    public static boolean containsCompositeFunction(String key) {
        return StrUtil.containsAnyIgnoreCase(key,
                "sum(", "avg(", "count(", "max(", "min(", "group_concat(",
                "json_arrayagg(", "bit_and(", "bit_or(", "bit_xor(", "json_objectagg(",
                "std(", "stddev(", "stddev_pop(", "stddev_samp(", "var_pop(", "var_samp(", "variance("
        );
    }



    public static Pair<Integer, Integer> getTarget(String sql, String target) {
        int from = sql.toUpperCase().indexOf("FROM");
        int where = sql.toUpperCase().lastIndexOf("WHERE");
        String center = sql.substring(from, where);
        int end = -1;
        int begin = -1;
        int count = 0;
        for (int i = 0; i < center.length(); i++) {
            if (count < 0) {
                break;
            }
            String temp = center.substring(i, i + 1);
            if (temp.equals("(")) {
                count++;
            }
            if (temp.equals(")")) {
                count--;
                if (count == 0) {
                    String substring = center.substring(i + 1);
                    int index = substring.toLowerCase().indexOf(" on ");
                    String tableName = substring;
                    if (index != -1) {
                        tableName = substring.substring(0, index);
                    }
                    String[] table = tableName.split(" ");
                    for (String t : table) {
                        if (t.trim().equals(target)) {
                            end = i;
                            String s = center.substring(0, end);
                            int c = 1;
                            for (int j = s.length() - 1; j > 0; j--) {
                                String te = s.substring(j - 1, j);
                                if (te.equals("(")) {
                                    c--;
                                }
                                if (te.equals(")")) {
                                    c++;
                                }
                                if (c == 0) {
                                    begin = j;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    if (end != -1) {
                        break;
                    }
                }
            }
        }
        if (end != -1) {
            begin = from + begin;
            end = from + end;
        }
        return new Pair<>(begin, end);
    }


    public static String getChangeSql(Long searchConfigInfoId, SearchQueryDTO.QueryInfo queryInfo, String packageSql, List<SearchConfigInfo> influences, Set<String> tables) {
        //判断是否可以更改
        SearchConfigInfo searchConfigInfo = SearchConfigInfoMapper.lambdaQuery().id().equal(searchConfigInfoId).one();
        if (ObjectUtil.isNull(searchConfigInfo)) {
            return packageSql;
        }
        String infoSearchType = searchConfigInfo.getSearchType();
        String point = searchConfigInfo.getPoint();
        if (StrUtil.isBlank(point)) {
            return packageSql;
        }
        String searchValue = queryInfo.getSearchValue();
        //校验是否满足拼接条件
        if (getReturnFlag(infoSearchType, searchValue, point)) {
            return packageSql;
        }
        List<SearchConfigInfo> infos = influences.stream().filter(f -> Objects.equals(f.getId(), searchConfigInfoId)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(infos)) {
            SearchConfigInfo configInfo = infos.get(0);
            String axis = configInfo.getAxis();
            String tbName = configInfo.getChangeTbName();
            String dateRule = configInfo.getChangeDateRule();
            JSONArray axisArray;
            JSONArray tbArray;
            JSONArray ruleArray;
            String returnSql = "";
            if (StrUtil.isNotBlank(axis) && StrUtil.isNotBlank(tbName)) {
                try {
                    axisArray = JSONUtil.parseArray(axis);
                    tbArray = JSONUtil.parseArray(tbName);
                } catch (Exception e) {
                    axisArray = new JSONArray();
                    axisArray.add(axis);
                    tbArray = new JSONArray();
                    tbArray.add(tbName);
                }
                if (StrUtil.isBlank(dateRule)) {
                    ruleArray = null;
                } else {
                    try {
                        ruleArray = JSONUtil.parseArray(dateRule);
                    } catch (Exception e) {
                        ruleArray = new JSONArray();
                        for (int i = 0; i < axisArray.size(); i++) {
                            ruleArray.add(dateRule);
                        }
                    }
                }
                for (int j = 0; j < axisArray.size(); j++) {
                    String configInfoAxis = (String) axisArray.get(j);
                    String changeTbName = (String) tbArray.get(j);
                    String changeDateRule = ruleArray == null ? null : (String) ruleArray.get(j);
                    String searchType = configInfo.getSearchType();
                    String[] splitDOT = configInfoAxis.split("\\.");
                    int from = packageSql.toUpperCase().indexOf("FROM");
                    int where = packageSql.toUpperCase().lastIndexOf("WHERE");
                    String center = packageSql.substring(from, where);
                    if (splitDOT.length == 1) {
                        //外部表名更换
                        int tbCount = 0;
                        for (int i = 4; i < center.length(); i++) {
                            char charAt = center.charAt(i);
                            if (charAt == '(') {
                                tbCount++;
                            }
                            if (charAt == ')') {
                                tbCount++;
                            }
                            if (tbCount == 0) {
                                String sub = center.substring(i);
                                int indexOf = sub.toLowerCase().indexOf(" on ");
                                String tb_center;
                                if (indexOf >= 0) {
                                    int l = sub.toLowerCase().indexOf(" left join ");
                                    int r = sub.toLowerCase().indexOf(" right join ");
                                    int in = sub.toLowerCase().indexOf(" inner join ");
                                    indexOf = ListUtil.of(indexOf, l, r, in).stream().filter(t -> t >= 0).min(Integer::compareTo).get();
                                    tb_center = sub.substring(i, indexOf);
                                } else {
                                    String table = sub.trim();
                                    String tb = getReplaceTableName(queryInfo, changeTbName, changeDateRule, searchType);
                                    tables.add(tb);
                                    String orig = tb;
                                    if (table.equals(configInfoAxis) || StrUtil.contains(table, configInfoAxis + StrUtil.SPACE)) {
                                        if ((!table.equals(configInfoAxis)) && StrUtil.contains(table, configInfoAxis + StrUtil.SPACE)) {
                                            String trim = table.trim();
                                            tb = tb + StrUtil.SPACE + trim.split(StrUtil.SPACE)[1];
                                        }
                                        returnSql = packageSql.substring(0, from)
                                                + StrUtil.SPACE +
                                                "FROM" +
                                                StrUtil.SPACE +
                                                tb.trim() +
                                                StrUtil.SPACE +
                                                packageSql.substring(where);
                                    }
                                    returnSql = returnSql.replaceAll(configInfoAxis + "\\.", orig + ".");
                                    break;
                                }
                                if (StrUtil.equals(tb_center.trim(), configInfoAxis) || StrUtil.contains(tb_center, configInfoAxis + StrUtil.SPACE)) {
                                    String tb = getReplaceTableName(queryInfo, changeTbName, changeDateRule, searchType);
                                    String orig = tb;
                                    tables.add(tb);
                                    if (StrUtil.contains(tb_center, configInfoAxis + StrUtil.SPACE) && !StrUtil.equals(tb_center.trim(), configInfoAxis)) {
                                        String trim = tb_center.trim();
                                        tb = tb + StrUtil.SPACE + trim.split(StrUtil.SPACE)[1];
                                    }
                                    returnSql = packageSql.substring(0, from)
                                            + StrUtil.SPACE +
                                            "FROM" +
                                            center.substring(4, i) +
                                            tb.trim() +
                                            center.substring(i + tb_center.length()) +
                                            packageSql.substring(where);
                                    returnSql = returnSql.replaceAll(configInfoAxis + "\\.", orig + ".");
                                    break;
                                }
                            }
                        }
                    } else {
                        String innerTable = splitDOT[1];
                        String tb = getReplaceTableName(queryInfo, changeTbName, changeDateRule, searchType);
                        tables.add(tb);
                        //内部表名替换
                        Pair<Integer, Integer> target = getTarget(packageSql, configInfoAxis.split("\\.")[0]);
                        if (target.getKey() != -1) {
                            String prefix = packageSql.substring(0, target.getKey());
                            String substring = packageSql.substring(target.getKey(), target.getValue());
                            String end = packageSql.substring(target.getValue());
                            //处理内部表名替换
                            String lineSeparator = System.lineSeparator();
                            substring = substring.replaceAll(StrUtil.SPACE + innerTable + StrUtil.SPACE, StrUtil.SPACE + tb + StrUtil.SPACE);
                            substring = substring.replaceAll(lineSeparator + innerTable + lineSeparator, lineSeparator + tb + lineSeparator);
                            substring = substring.replaceAll(StrUtil.SPACE + innerTable + lineSeparator, StrUtil.SPACE + tb + lineSeparator);
                            substring = substring.replaceAll(lineSeparator + innerTable + StrUtil.SPACE, lineSeparator + tb + StrUtil.SPACE);
                            if (StrUtil.endWith(substring, StrUtil.SPACE + innerTable) || StrUtil.endWith(substring, lineSeparator + innerTable)) {
                                substring = substring.replaceAll(innerTable, tb);
                            }
                            substring = substring.replaceAll(innerTable + "\\.", tb + ".");
                            returnSql = prefix + substring + end;
                        }
                    }
                    packageSql = returnSql;
                }
            } else {
                returnSql = packageSql;
                String[] lines = returnSql.split(System.lineSeparator());
                int i = 0;
                for (String line : lines) {
                    if (StrUtil.containsIgnoreCase(line, "/*REPLACE TO")) {
                        Matcher replaceToMatcher = ReplaceToReg.matcher(line);
                        if (StrUtil.contains(dateRule, "[") && StrUtil.contains(dateRule, "]")) {
                            JSONArray jsonArray = JSONUtil.parseArray(dateRule);
                            Object o = jsonArray.get(i);
                            if (o == null) {
                                continue;
                            }
                            if (replaceToMatcher.matches()) {
                                final String toText = replaceToMatcher.group(3);
                                String tb = getReplaceTableName(queryInfo, toText, o + "", configInfo.getSearchType());
                                tables.add(tb);
                                returnSql = returnSql.replace(line, line.replace(replaceToMatcher.group(1), tb));
                            }
                        } else {
                            if (replaceToMatcher.matches()) {
                                final String toText = replaceToMatcher.group(3);
                                String tb = getReplaceTableName(queryInfo, toText, dateRule, configInfo.getSearchType());
                                tables.add(tb);
                                returnSql = returnSql.replace(line, line.replace(replaceToMatcher.group(1), tb));
                            } else {
                                final Pattern replaceToReg = Pattern.compile(".*\\s+((\\S+)/\\*\\s*REPLACE TO (.+)\\*/)", Pattern.CASE_INSENSITIVE);
                                Matcher matcher = replaceToReg.matcher(line);
                                if (matcher.matches()) {
                                    final String toText = matcher.group(3);
                                    String tb = getReplaceTableName(queryInfo, toText, dateRule, configInfo.getSearchType());
                                    tables.add(tb);
                                    returnSql = returnSql.replace(line, line.replace(matcher.group(1), tb));
                                }
                            }
                        }
                        i++;
                    }
                }
            }
            return returnSql;
        }
        return "";
    }

    public static boolean getReturnFlag(String infoSearchType, String searchValue, String point) {
        if (StrUtil.equals(infoSearchType, "date")) {
            if (!StrUtil.equalsAny(point, "LAST_DAY", "CURRENT_DAY", "LAST_MONTH", "CURRENT_MONTH")) {
                return true;
            }
            Date date = new Date();
            int now_year = DateUtil.year(date);
            int now_month = DateUtil.month(date) + 1;
            int now_day = DateUtil.dayOfMonth(date);
            Date parse = DateUtil.parse(searchValue);
            int rec_year = DateUtil.year(parse);
            int rec_month = DateUtil.month(parse) + 1;
            int rec_day = DateUtil.dayOfMonth(parse);
            boolean is_return = false;
            switch (point) {
                case "LAST_DAY":
                    if ((now_day == rec_day) && (now_month == rec_month) && (now_year == rec_year)) {
                        is_return = true;
                    }
                    break;
                case "CURRENT_DAY":
                    if (!((now_day == rec_day) && (now_month == rec_month) && (now_year == rec_year))) {
                        is_return = true;
                    }
                    break;
                case "LAST_MONTH":
                    if ((now_year == rec_year) && (now_month == rec_month)) {
                        is_return = true;
                    }
                    break;
                case "CURRENT_MONTH":
                    if (!((now_year == rec_year) && (now_month == rec_month))) {
                        is_return = true;
                    }
                    break;
                default:
                    break;
            }
            return is_return;
        } else {
            return !StrUtil.equals(searchValue, point);
        }
    }

    private static String getReplaceTableName(SearchQueryDTO.QueryInfo queryInfo, String changeTbName, String changeDateRule, String searchType) {
        String tb;
        if (StrUtil.equals(searchType, "date") && StrUtil.isNotBlank(changeDateRule)) {
            String searchValue = queryInfo.getSearchValue();
            String format = DateUtil.format(DateUtil.parse(searchValue), changeDateRule);
            tb = changeTbName.replace("@this", format);
        } else {
            tb = changeTbName.replace("@this", queryInfo.getSearchValue());
        }
        return tb;
    }

    public static String sqlConversion(String sql) {
        if (sql.contains("#{")) {
            sql = ReUtil.replaceAll(sql, "[#][{](\\w*)[}]", ":$1");
        }
        if (sql.contains("${")) {
            sql = ReUtil.replaceAll(sql,"[$][{](\\w*)[}]", ":$1");
        }
        return sql;
    }
}
