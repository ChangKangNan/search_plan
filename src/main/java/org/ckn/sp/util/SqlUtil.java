package org.ckn.sp.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.fm.bean.SearchTableColumn;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.ckn.sp.constants.StrConstants.*;
import static org.ckn.sp.util.RegxUtil.JOIN_SPLIT;

/**
 * @author ckn
 */
public class SqlUtil {

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
}
