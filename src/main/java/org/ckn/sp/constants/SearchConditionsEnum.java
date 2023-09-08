package org.ckn.sp.constants;


import cn.hutool.core.util.EnumUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 搜索条件枚举
 */
public enum SearchConditionsEnum {

    /**
     * 条件表达式
     */
    IS("空判断", " IS "),
    In("in", " IN "),
    NotIn("notIn", " NOT IN "),
    Equal("等于", " = "),
    NotEqual("不等于", " != "),
    Like("包含", " LIKE "),
    NotLike("不包含", " NOT LIKE "),
    Match(" MATCH", " AGAINST"),
    NotMatch(" NOT MATCH", " AGAINST"),
    Greater("大于", " > "),
    GreaterOrEqual("大于等于", " >= "),
    Less("小于", " < "),
    LessOrEqual("小于等于", " <= ");

    public final String name;
    public final String expression;

    SearchConditionsEnum(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    private static final Map<String, SearchConditionsEnum> exMap = new HashMap<>();

    public static SearchConditionsEnum getSearchConditions(String name){
        SearchConditionsEnum ex = exMap.get(name);
        if (ex != null) {
            return ex;
        }
        ex = EnumUtil.likeValueOf(SearchConditionsEnum.class,name);
        exMap.put(name,ex);
        return ex;
    }

}
