package org.ckn.sp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ckn
 */
@Data
public class SearchPlanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long planId;
    /**
     * 方案名称
     */
    private String planName;
    private String pageTag;
    private String orderBy;
    private Boolean desc = false;
    private Boolean isDefaultChange = false;
    private Long orgId;
    private Boolean def = false;
    private String userAccount;
    /**
     * 搜素条件
     */
    private List<QueryInfo> queryInfoList;
    /**
     * 列状态
     */
    private List<SearchColumn> searchColumns;
    /**
     * 默认页码
     */
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    @Data
    public static class QueryInfo implements Serializable{
        private static final long serialVersionUID = 1L;
        private String searchKey;
        private Long searchConfigInfoId;
        private String searchCondition;
        private String searchValue;
        private String searchGroup;
        private String searchGroupRelation;
        private String parentGroup;
        private String parentGroupRelation;
        private Long searchConfigInnerConditionId;
        private Integer areInner;
    }

    @Data
    public static class SearchColumn implements Serializable{
        private static final long serialVersionUID = 1L;
        private String columnName;
        private String status;
    }

}
