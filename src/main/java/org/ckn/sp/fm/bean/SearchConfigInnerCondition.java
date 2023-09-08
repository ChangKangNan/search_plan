package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_config_inner_condition")
@Entity
@Data
public class SearchConfigInnerCondition {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "search_config_id")
    private Long searchConfigId;


    @Column(name = "page_tag")
    private String pageTag;


    @Column(name = "search_name")
    private String searchName;


    @Column(name = "search_key")
    private String searchKey;


    @Column(name = "limit_search_conditions")
    private String limitSearchConditions;


    @Column(name = "limit_search_values")
    private String limitSearchValues;


    @Column(name = "search_value_source")
    private String searchValueSource;


    @Column(name = "search_type")
    private String searchType;


    @Column(name = "axis")
    private String axis;


    @Column(name = "relation")
    private String relation;


    @Column(name = "default_value")
    private String defaultValue;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
