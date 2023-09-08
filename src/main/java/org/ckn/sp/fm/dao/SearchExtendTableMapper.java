package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchExtendTableAction;

public class SearchExtendTableMapper {
    private SearchExtendTableMapper() {}

    public static SearchExtendTableAction.InsertMapper lambdaInsert() {
    return new SearchExtendTableAction.InsertMapper();
  }
    public static SearchExtendTableAction.BaseSelectMapper lambdaQuery() {
        return new SearchExtendTableAction.BaseSelectMapper();
  }
    public static SearchExtendTableAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchExtendTableAction.BaseUpdateMapper();
  }
    public static SearchExtendTableAction.BaseDeletedMapper lambdaDelete() {
        return new SearchExtendTableAction.BaseDeletedMapper();
  }
}
