package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_config")
@Entity
@Data
public class SearchConfig {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "config_name")
    private String configName;


    @Column(name = "page_tag")
    private String pageTag;


    @Column(name = "search_sql")
    private String searchSql;


    @Column(name = "use_org_limit_table_alias")
    private String useOrgLimitTableAlias;


    @Column(name = "use_orgs_limit_table_alias")
    private String useOrgsLimitTableAlias;


    @Column(name = "has_code")
    private String hasCode;


    @Column(name = "remark")
    private String remark;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
