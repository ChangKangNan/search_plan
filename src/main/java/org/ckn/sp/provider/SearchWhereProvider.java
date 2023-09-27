package org.ckn.sp.provider;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.constants.SearchConditionsEnum;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.dao.SearchConfigInfoMapper;
import org.ckn.sp.service.impl.SearchPlanServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.ft.ckn.fastmapper.util.PackageSqlUtil.*;
import static org.ckn.sp.util.SqlUtil.containsCompositeFunction;

/**
 * @author ckn
 */
public class SearchWhereProvider {

    public static void handle(List<SearchQueryDTO.QueryInfo> infoList){
        StrBuilder sql = new StrBuilder(SearchPlanServiceImpl.QUERY_SQL_LOCAL.get());
        ParamIndex paramIndex = new ParamIndex();
        paramIndex.setParamType(WHERE_PARAM_TYPE);
        int where = sql.toString().toUpperCase().lastIndexOf("WHERE");
        if (where == -1) {
            sql.append("WHERE 1");
            where = sql.toString().toUpperCase().indexOf("WHERE");
        }
        int groupBy = sql.toString().toUpperCase().lastIndexOf("GROUP BY");
        int having = sql.toString().toUpperCase().lastIndexOf("HAVING");
        if (where > groupBy) {
            groupBy = -1;
        }
        infoList = infoList.stream().filter(m -> m.getAreInner() == 0 && m.getAreInnerFilter()==0 && m.getSearchConfigInfoId() != null).collect(Collectors.toList());
        //此处支持根据搜索条件选择拼接where or having
        if (CollUtil.isEmpty(infoList)) {
            return ;
        }
        int index = 0;
        for (SearchQueryDTO.QueryInfo queryInfo : infoList) {
            String searchKey = queryInfo.getSearchKey();
            boolean isGroup = containsCompositeFunction(searchKey);
            if (StrUtil.equals(queryInfo.getSearchGroup(), "default") && (!isGroup)) {
                index++;
                queryInfo.setSearchGroup(queryInfo.getSearchGroup() + index);
            }
        }
        long count = infoList.stream().filter(info -> StrUtil.isBlank(info.getParentGroup())).count();
        boolean needSort = count != infoList.size();
        List<List<SearchQueryDTO.QueryInfo>> pkOrder = new ArrayList<>();
        if (needSort) {
            List<String> parentGroupList = infoList.stream().filter(info -> StrUtil.isBlank(info.getParentGroup())).map(SearchQueryDTO.QueryInfo::getSearchGroup).distinct().collect(Collectors.toList());
            for (String group : parentGroupList) {
                List<SearchQueryDTO.QueryInfo> queryInfos = infoList.stream().filter(info -> StrUtil.equals(info.getSearchGroup(), group)).collect(Collectors.toList());
                //放置父节点
                List<SearchQueryDTO.QueryInfo> g = new ArrayList<>(queryInfos);
                //放置子节点
                setChild(group, g, infoList);
                pkOrder.add(g);
            }
        }
        if (CollUtil.isEmpty(pkOrder)) {
            Map<String, List<SearchQueryDTO.QueryInfo>> map = infoList.stream().collect(Collectors.groupingBy(SearchQueryDTO.QueryInfo::getSearchGroup));
            for (String key : map.keySet()) {
                pkOrder.add(map.get(key));
            }
        }
        StrBuilder whereSql = new StrBuilder();
        StrBuilder havingSql = new StrBuilder();
        Map<String, Object> paramMap = new HashMap<>();
        for (List<SearchQueryDTO.QueryInfo> list : pkOrder) {
            String searchKey = list.get(0).getSearchKey();
            boolean isGroup = containsCompositeFunction(searchKey);
            StrBuilder concatSql = new StrBuilder();
            String parentGroupRelation = list.get(0).getParentGroupRelation();
            concatSql.append(StrUtil.SPACE);
            concatSql.append(StrUtil.isBlank(parentGroupRelation) ? "AND" : parentGroupRelation);
            concatSql.append(StrUtil.SPACE);
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    concatSql.append(LEFT_BRACKETS);
                }
                SearchQueryDTO.QueryInfo info = list.get(i);
                String searchGroupRelation = info.getSearchGroupRelation();
                if (i != 0) {
                    concatSql.append(StrUtil.SPACE);
                    concatSql.append(StrUtil.isBlank(searchGroupRelation) ? "AND" : searchGroupRelation);
                    concatSql.append(StrUtil.SPACE);
                    if (!StrUtil.equals(list.get(i - 1).getSearchGroup(), info.getSearchGroup())) {
                        concatSql.append(LEFT_BRACKETS);
                    }
                }
                SearchConfigInfo configInfo = SearchConfigInfoMapper.lambdaQuery().id().equal(info.getSearchConfigInfoId()).one();
                String searchType = configInfo.getSearchType();
                condition(info, concatSql, paramMap, paramIndex, searchType);
                if (i != (list.size() - 1) && (!StrUtil.equals(list.get(i + 1).getSearchGroup(), info.getSearchGroup()))) {
                    concatSql.append(StrUtil.SPACE);
                    concatSql.append(RIGHT_BRACKETS);
                }
                if (i == (list.size() - 1)) {
                    concatSql.append(RIGHT_BRACKETS);
                }
            }
            if (isGroup) {
                havingSql.append(concatSql);
            } else {
                whereSql.append(concatSql);
            }
        }
        if (!whereSql.isEmpty()) {
            if (groupBy == -1) {
                sql.append(whereSql);
            } else {
                String groupSql = sql.toString().substring(groupBy);
                sql.del(groupBy, sql.toString().length());
                sql.append(whereSql).append(groupSql);
            }
        }
        if (!havingSql.isEmpty()) {
            if (having == -1) {
                sql.append(StrUtil.SPACE);
                sql.append("HAVING 1");
            }
            sql.append(havingSql);
        }
        SearchPlanServiceImpl.QUERY_SQL_LOCAL.set(sql.toString());
        SearchPlanServiceImpl.QUERY_SQL_PARAM_LOCAL.set(paramMap);
    }
    private static List<SearchQueryDTO.QueryInfo> setChild(String group, List<SearchQueryDTO.QueryInfo> insertList, List<SearchQueryDTO.QueryInfo> original) {
        List<SearchQueryDTO.QueryInfo> collect = original.stream().filter(info -> StrUtil.equals(info.getParentGroup(), group)).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) {
            return insertList;
        }
        insertList.addAll(collect);
        String searchGroup = collect.get(0).getSearchGroup();
        return setChild(searchGroup, insertList, original);
    }

    private static class ParamIndex {
        private int index = 0;
        private String paramType;

        public int get() {
            return index;
        }

        public int add() {
            this.index++;
            return this.index;
        }

        public String getParamType() {
            return paramType;
        }

        public void setParamType(String paramType) {
            this.paramType = paramType;
        }
    }



    private static void condition(SearchQueryDTO.QueryInfo queryInfo, StrBuilder sql, Map<String, Object> paramMap, ParamIndex paramIndex, String type) {
        SearchConditionsEnum conditionsEnum = SearchConditionsEnum.getSearchConditions(queryInfo.getSearchCondition());
        String searchKey = queryInfo.getSearchKey();
        if (conditionsEnum == null) {
            return;
        }
        //条件类型判断
        switch (conditionsEnum) {
            case IS:
                if (!StrUtil.equalsAny(queryInfo.getSearchValue().toString(), "NULL", "NOT NULL")) {
                    throw new RuntimeException("非法查询");
                }
                sql.append(searchKey).append(conditionsEnum.expression).append(queryInfo.getSearchValue() + " ");
                break;
            case In:
            case NotIn:
                String[] strings = StrUtil.splitToArray(queryInfo.getSearchValue().toString(), StrUtil.C_COMMA);
                if (ArrayUtil.isEmpty(strings)) {
                    return;
                }
                sql.append(searchKey).append(conditionsEnum.expression).append(LEFT_BRACKETS);
                for (String s : strings) {
                    packParam(sql, paramMap, s, paramIndex).append(StrUtil.COMMA);
                }
                sql.del(sql.length() - 1, sql.length()).append(RIGHT_BRACKETS);
                sql.append(CRLF);
                break;
            case Like:
            case NotLike:
                sql.append(searchKey).append(conditionsEnum.expression);
                packParam(sql, paramMap, StrBuilder.create("%", queryInfo.getSearchValue().toString(), "%").toString(), paramIndex);
                sql.append(CRLF);
                break;
            case Match:
            case NotMatch:
                sql.append(conditionsEnum.name).append(LEFT_BRACKETS).append(searchKey)
                        .append(RIGHT_BRACKETS).append(conditionsEnum.expression).append(LEFT_BRACKETS);
                packParam(sql, paramMap, queryInfo.getSearchValue(), paramIndex).append(RIGHT_BRACKETS);
                sql.append(CRLF);
                break;
            case NotEqual:
                if (type.equals("date")) {
                    String dayStr = DateUtil.format(DateUtil.parse(queryInfo.getSearchValue().toString()), "yyyy-MM-dd");
                    String startDate = dayStr + " 00:00:00";
                    String endDate = dayStr + " 23:59:59";
                    sql.append(" (").append(searchKey).append(" < '").append(startDate + "' ")
                            .append(" or ").append(searchKey).append(" > '").append(endDate + "' ").append(") ");
                } else {
                    sql.append(searchKey).append(conditionsEnum.expression);
                    boolean numeric = isNumeric(queryInfo.getSearchValue());
                    packParam(sql, paramMap, numeric ? new BigDecimal(queryInfo.getSearchValue()) : queryInfo.getSearchValue(), paramIndex);
                }
                sql.append(CRLF);
                break;
            default:
                sql.append(searchKey).append(conditionsEnum.expression);
                boolean numeric = isNumeric(queryInfo.getSearchValue());
                packParam(sql, paramMap, numeric ? new BigDecimal(queryInfo.getSearchValue()) : queryInfo.getSearchValue(), paramIndex);
                sql.append(CRLF);
        }
    }
    private static StrBuilder packParam(StrBuilder sqlBuilder, Map<String, Object> paramMap, Object value, ParamIndex paramIndex) {
        String paramKey = paramIndex.getParamType() + paramIndex.get()+"_SUF";
        paramMap.put(paramKey, value);
        paramIndex.add();
        return packJdbcParam(sqlBuilder, paramKey);
    }

    private static final String PARAM_PREFIX = "#{";

    private static StrBuilder packJdbcParam(StrBuilder sqlBuilder, String paramKey) {
        sqlBuilder.append(PARAM_PREFIX).append(paramKey).append(PARAM_SUFFIX);
        return sqlBuilder;
    }

    private static boolean isNumeric(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return str.matches("[+-]?[0-9]+(\\.[0-9]+)?");
    }
}
