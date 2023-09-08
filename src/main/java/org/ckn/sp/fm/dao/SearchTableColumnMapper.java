package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableColumnAction;

public class SearchTableColumnMapper {
    private SearchTableColumnMapper() {}

    public static SearchTableColumnAction.InsertMapper lambdaInsert() {
    return new SearchTableColumnAction.InsertMapper();
  }
    public static SearchTableColumnAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableColumnAction.BaseSelectMapper();
  }
    public static SearchTableColumnAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableColumnAction.BaseUpdateMapper();
  }
    public static SearchTableColumnAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableColumnAction.BaseDeletedMapper();
  }
}
