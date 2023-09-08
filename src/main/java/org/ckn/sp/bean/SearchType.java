package org.ckn.sp.bean;

import lombok.Data;

/**
 * @author ckn
 */
@Data
public class SearchType {
    private String searchType;
    private String limitSearchConditions;

    public SearchType(String searchType, String limitSearchConditions) {
        this.searchType = searchType;
        this.limitSearchConditions = limitSearchConditions;
    }
}
