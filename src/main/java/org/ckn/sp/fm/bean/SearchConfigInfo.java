package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_config_info")
@Entity
@Data
public class SearchConfigInfo {

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


    @Column(name = "sort_num")
    private Integer sortNum;


    @Column(name = "hidden")
    private Boolean hidden;


    @Column(name = "change_tb")
    private Boolean changeTb;


    @Column(name = "change_date_rule")
    private String changeDateRule;


    @Column(name = "change_tb_name")
    private String changeTbName;


    @Column(name = "axis")
    private String axis;


    @Column(name = "point")
    private String point;


    @Column(name = "default_order_by")
    private Boolean defaultOrderBy;


    @Column(name = "default_order_by_desc")
    private Boolean defaultOrderByDesc;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
