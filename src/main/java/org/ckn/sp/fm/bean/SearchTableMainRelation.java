package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_table_main_relation")
@Entity
@Data
public class SearchTableMainRelation {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "search_table_main_id")
    private Long searchTableMainId;


    @Column(name = "source_table_name")
    private String sourceTableName;


    @Column(name = "source_table_alias")
    private String sourceTableAlias;


    @Column(name = "target_table_name")
    private String targetTableName;


    @Column(name = "target_table_alias")
    private String targetTableAlias;


    @Column(name = "source_table_column_name")
    private String sourceTableColumnName;


    @Column(name = "target_table_column_name")
    private String targetTableColumnName;


    @Column(name = "is_extend")
    private Boolean isExtend;


    @Column(name = "link_relation")
    private String linkRelation;


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
