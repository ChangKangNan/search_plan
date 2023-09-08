package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableHavingRelationAction;

public class SearchTableHavingRelationMapper {
    private SearchTableHavingRelationMapper() {}

    public static SearchTableHavingRelationAction.InsertMapper lambdaInsert() {
    return new SearchTableHavingRelationAction.InsertMapper();
  }
    public static SearchTableHavingRelationAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableHavingRelationAction.BaseSelectMapper();
  }
    public static SearchTableHavingRelationAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableHavingRelationAction.BaseUpdateMapper();
  }
    public static SearchTableHavingRelationAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableHavingRelationAction.BaseDeletedMapper();
  }
}
