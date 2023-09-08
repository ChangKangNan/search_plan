package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_plan_new_condition")
@Entity
@Data
public class SearchPlanNewCondition {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "user_account")
    private String userAccount;


    @Column(name = "page_tag")
    private String pageTag;


    @Column(name = "search_config_info_id")
    private Long searchConfigInfoId;


    @Column(name = "search_config_inner_condition_id")
    private Long searchConfigInnerConditionId;


    @Column(name = "search_plan_id")
    private Long searchPlanId;


    @Column(name = "search_column")
    private String searchColumn;


    @Column(name = "search_condition")
    private String searchCondition;


    @Column(name = "search_group")
    private String searchGroup;


    @Column(name = "search_group_relation")
    private String searchGroupRelation;


    @Column(name = "parent_group")
    private String parentGroup;


    @Column(name = "parent_group_relation")
    private String parentGroupRelation;


    @Column(name = "search_value")
    private String searchValue;


    @Column(name = "are_inner")
    private Integer areInner;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
