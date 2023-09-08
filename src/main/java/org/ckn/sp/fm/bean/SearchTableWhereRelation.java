package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_table_where_relation")
@Entity
@Data
public class SearchTableWhereRelation {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "search_table_where_id")
    private Long searchTableWhereId;


    @Column(name = "search_table_main_relation_id")
    private Long searchTableMainRelationId;


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
