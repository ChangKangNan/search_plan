package org.ckn.sp.dto;

import lombok.Data;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.bean.SearchConfigInnerCondition;

import java.io.Serializable;
import java.util.List;

/**
 * @author ckn
 */
@Data
public class PlanFilterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long planId;
    private List<QueryInfo> queryInfoList;

    private List<SearchColumn> searchColumns;

    @Data
    public static class QueryInfo implements Serializable{
        private static final long serialVersionUID = 1L;

        SearchConfigInfo searchConfigInfo;
        SearchConfigInnerCondition searchConfigInnerCondition;

        private Long id;
        /**
         * 搜索字段名
         */
        private String searchKey;

        /**
         * 搜索字段名ID
         */
        private Long searchConfigInfoId;

        /**
         * 内部条件配置表ID
         */
        private Long searchConfigInnerConditionId;

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
         *是否是内部条件(0:不是 ,1:是)
         */
        private Integer areInner;
    }

    @Data
    public static class SearchColumn implements Serializable{
        private static final long serialVersionUID = 1L;

        private String columnName;

        private String status;
    }

}
