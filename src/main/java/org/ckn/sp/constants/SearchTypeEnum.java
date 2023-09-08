package org.ckn.sp.constants;

import cn.hutool.core.util.EnumUtil;

import java.util.HashMap;
import java.util.Map;

import static org.ckn.sp.constants.StrConstants.*;

/**
 * 搜索类型枚举
 */
public enum SearchTypeEnum {
    input(INPUT,"[{\"name\":\"包含\",\"value\":\"Like\"}]"),
    date(DATE,"[{\"name\":\"范围\",\"value\":\"Range\"},{\"name\":\"等于\",\"value\":\"Equal\"},{\"name\":\"大于\",\"value\":\"Greater\"},{\"name\":\"大于等于\",\"value\":\"GreaterOrEqual\"},{\"name\":\"小于\",\"value\":\"Less\"},{\"name\":\"小于等于\",\"value\":\"LessOrEqual\"}]"),
    date_year(DATE_YEAR,"[{\"name\":\"等于\",\"value\":\"Equal\"}]"),
    month(MONTH,"[{\"name\":\"范围\",\"value\":\"Range\"},{\"name\":\"等于\",\"value\":\"Equal\"},{\"name\":\"大于\",\"value\":\"Greater\"},{\"name\":\"大于等于\",\"value\":\"GreaterOrEqual\"},{\"name\":\"小于\",\"value\":\"Less\"},{\"name\":\"小于等于\",\"value\":\"LessOrEqual\"}]"),
    invoice_month(INVOICE_MONTH,"[{\"name\":\"等于\",\"value\":\"Equal\"}]"),
    number(NUMBER,"[{\"name\":\"等于\",\"value\":\"Equal\"},{\"name\":\"大于\",\"value\":\"Greater\"},{\"name\":\"大于等于\",\"value\":\"GreaterOrEqual\"},{\"name\":\"小于\",\"value\":\"Less\"},{\"name\":\"小于等于\",\"value\":\"LessOrEqual\"},{\"name\":\"不等于\",\"value\":\"NotEqual\"}]"),
    values(SELECT,"[{\"name\":\"等于\",\"value\":\"In\"},{\"name\":\"不等于\",\"value\":\"NotIn\"}]"),
    values_like(SELECT,"[{\"name\":\"等于\",\"value\":\"In\"},{\"name\":\"不等于\",\"value\":\"NotIn\"}]"),
    dict(SELECT,"[{\"name\":\"等于\",\"value\":\"In\"},{\"name\":\"不等于\",\"value\":\"NotIn\"}]"),
    url(SELECT,"[{\"name\":\"等于\",\"value\":\"In\"},{\"name\":\"不等于\",\"value\":\"NotIn\"}]"),
    year(YEAR,"[{\"name\":\"等于\",\"value\":\"Equal\"}]");

    public String searchType;
    public String limitSearchConditions;

    SearchTypeEnum(String searchType, String limitSearchConditions) {
        this.searchType = searchType;
        this.limitSearchConditions = limitSearchConditions;
    }

    private static final Map<String, SearchTypeEnum> exMap = new HashMap<>();

    public static SearchTypeEnum getSearchType(String name){
        SearchTypeEnum ex = exMap.get(name);
        if (ex != null) {
            return ex;
        }
        ex = EnumUtil.likeValueOf(SearchTypeEnum.class,name);
        exMap.put(name,ex);
        return ex;
    }
}
