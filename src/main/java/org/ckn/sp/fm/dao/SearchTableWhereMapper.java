package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableWhereAction;

public class SearchTableWhereMapper {
    private SearchTableWhereMapper() {}

    public static SearchTableWhereAction.InsertMapper lambdaInsert() {
    return new SearchTableWhereAction.InsertMapper();
  }
    public static SearchTableWhereAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableWhereAction.BaseSelectMapper();
  }
    public static SearchTableWhereAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableWhereAction.BaseUpdateMapper();
  }
    public static SearchTableWhereAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableWhereAction.BaseDeletedMapper();
  }
}
