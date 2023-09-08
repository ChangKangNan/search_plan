package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_datasource_relation")
@Entity
@Data
public class SearchDatasourceRelation {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "search_datasource_id")
    private Long searchDatasourceId;


    @Column(name = "search_config_id")
    private Long searchConfigId;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
