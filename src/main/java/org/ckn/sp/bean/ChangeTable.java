package org.ckn.sp.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangeTable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String columnName;
    private String rule;
    private String finalName;
    private String axis;
    private String point;
}