package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_extend_table")
@Entity
@Data
public class SearchExtendTable {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "search_table_main_id")
    private Long searchTableMainId;


    @Column(name = "extend_table_name")
    private String extendTableName;


    @Column(name = "extend_sql")
    private String extendSql;


    @Column(name = "remark")
    private String remark;


    @Column(name = "create_by")
    private String createBy;


    @Column(name = "update_by")
    private String updateBy;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
