package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_table_having")
@Entity
@Data
public class SearchTableHaving {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "page_tag")
    private String pageTag;


    @Column(name = "relation")
    private String relation;


    @Column(name = "table_having_where")
    private String tableHavingWhere;


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
