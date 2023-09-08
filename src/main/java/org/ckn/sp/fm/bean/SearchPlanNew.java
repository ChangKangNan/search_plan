package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_plan_new")
@Entity
@Data
public class SearchPlanNew {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "user_account")
    private String userAccount;


    @Column(name = "plan_name")
    private String planName;


    @Column(name = "page_tag")
    private String pageTag;


    @Column(name = "custom_marker")
    private String customMarker;


    @Column(name = "search_sql")
    private String searchSql;


    @Column(name = "sort_num")
    private Integer sortNum;


    @Column(name = "def")
    private Boolean def;


    @Column(name = "create_org")
    private Long createOrg;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
