package org.ckn.sp.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ckn
 */
@Data
public class UserPlanDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String planName;

    private String pageTag;

    private Boolean isDef;
    private String userName;
}
