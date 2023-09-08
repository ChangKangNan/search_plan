package org.ckn.sp.fm.bean;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain=true)
@Table(name = "search_plan_new_show")
@Entity
@Data
public class SearchPlanNewShow {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "user_account")
    private String userAccount;


    @Column(name = "page_tag")
    private String pageTag;


    @Column(name = "search_plan_id")
    private Long searchPlanId;


    @Column(name = "search_column")
    private String searchColumn;


    @Column(name = "show_status")
    private String showStatus;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "deleted")
    private Boolean deleted;


}
