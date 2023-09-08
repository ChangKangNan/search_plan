package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_table_main")
@Entity
@Data
public class SearchTableMain {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "search_table_name")
    private String searchTableName;


    @Column(name = "alias")
    private String alias;


    @Column(name = "is_extend")
    private Boolean isExtend;


    @Column(name = "page_tag")
    private String pageTag;


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
