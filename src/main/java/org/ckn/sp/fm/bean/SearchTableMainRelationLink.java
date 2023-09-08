package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_table_main_relation_link")
@Entity
@Data
public class SearchTableMainRelationLink {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "search_table_main_relation_id")
    private Long searchTableMainRelationId;


    @Column(name = "table_column_name")
    private String tableColumnName;


    @Column(name = "target_table_name")
    private String targetTableName;


    @Column(name = "target_table_alias")
    private String targetTableAlias;


    @Column(name = "target_table_column_name")
    private String targetTableColumnName;


    @Column(name = "parent_relation_id")
    private Long parentRelationId;


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
