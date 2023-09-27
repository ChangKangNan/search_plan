package org.ckn.sp.dto;

import lombok.Data;
import org.ckn.sp.fm.bean.SearchTableMain;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ckn
 * @date 2022/7/7
 */
@Data
public class AliasDTO implements Serializable {

    private static final long serialVersionUID = -505258122L;

    String mainTable;

    String mainAliasName;

    Boolean isAlias;

    SearchTableMain tableMain;

    Map<String, String> tableExtendMap;

    Map<String, String> aliasToTable;

    Map<String, String> aliasTooN;
}
