package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_datasource")
@Entity
@Data
public class SearchDatasource {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "datasource_name")
    private String datasourceName;


    @Column(name = "datasource_driver")
    private String datasourceDriver;


    @Column(name = "datasource_url")
    private String datasourceUrl;


    @Column(name = "datasource_username")
    private String datasourceUsername;


    @Column(name = "datasource_password")
    private String datasourcePassword;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
