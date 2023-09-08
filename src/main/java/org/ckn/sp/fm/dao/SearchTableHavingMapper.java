package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableHavingAction;

public class SearchTableHavingMapper {
    private SearchTableHavingMapper() {}

    public static SearchTableHavingAction.InsertMapper lambdaInsert() {
    return new SearchTableHavingAction.InsertMapper();
  }
    public static SearchTableHavingAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableHavingAction.BaseSelectMapper();
  }
    public static SearchTableHavingAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableHavingAction.BaseUpdateMapper();
  }
    public static SearchTableHavingAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableHavingAction.BaseDeletedMapper();
  }
}
