package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableMainAction;

public class SearchTableMainMapper {
    private SearchTableMainMapper() {}

    public static SearchTableMainAction.InsertMapper lambdaInsert() {
    return new SearchTableMainAction.InsertMapper();
  }
    public static SearchTableMainAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableMainAction.BaseSelectMapper();
  }
    public static SearchTableMainAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableMainAction.BaseUpdateMapper();
  }
    public static SearchTableMainAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableMainAction.BaseDeletedMapper();
  }
}
