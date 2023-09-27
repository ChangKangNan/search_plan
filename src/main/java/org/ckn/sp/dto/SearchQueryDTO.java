package org.ckn.sp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ckn
 */
@Data
public class SearchQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String pageTag;
    private String orderBy;
    private Long searchPlanId;
    private Boolean desc;

    private List<QueryInfo> queryInfoList;

    @Data
    public static class QueryInfo implements Serializable{
        private static final long serialVersionUID = 1L;
        /**
         * 搜索字段名
         */
        private String searchKey;

        /**
         * 搜索字段名ID
         */
        private Long searchConfigInfoId;

        /**
         * 搜索条件
         */
        private String searchCondition;

        /**
         * 搜索参数
         */
        private String searchValue;

        /**
         *查询条件分组
         */
        private String searchGroup;

        /**
         *查询条件分组方式(and or)
         */
        private String searchGroupRelation;

        /**
         *查询父条件分组
         */
        private String parentGroup;

        /**
         *查询父条件分组方式(and or)
         */
        private String parentGroupRelation;

        /**
         * 内部条件配置表ID
         */
        private Long searchConfigInnerConditionId;

        /**
         *是否是内部条件(0:不是 ,1:是)
         */
        private Integer areInner=0;

        /**
         *是否内部过滤
         */
        private Integer areInnerFilter=0;
    }
}
