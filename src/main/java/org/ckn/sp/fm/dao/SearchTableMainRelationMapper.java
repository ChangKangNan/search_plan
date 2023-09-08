package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableMainRelationAction;

public class SearchTableMainRelationMapper {
    private SearchTableMainRelationMapper() {}

    public static SearchTableMainRelationAction.InsertMapper lambdaInsert() {
    return new SearchTableMainRelationAction.InsertMapper();
  }
    public static SearchTableMainRelationAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableMainRelationAction.BaseSelectMapper();
  }
    public static SearchTableMainRelationAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableMainRelationAction.BaseUpdateMapper();
  }
    public static SearchTableMainRelationAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableMainRelationAction.BaseDeletedMapper();
  }
}
