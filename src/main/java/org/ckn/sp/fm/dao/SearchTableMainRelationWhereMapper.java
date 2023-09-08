package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableMainRelationWhereAction;

public class SearchTableMainRelationWhereMapper {
    private SearchTableMainRelationWhereMapper() {}

    public static SearchTableMainRelationWhereAction.InsertMapper lambdaInsert() {
    return new SearchTableMainRelationWhereAction.InsertMapper();
  }
    public static SearchTableMainRelationWhereAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableMainRelationWhereAction.BaseSelectMapper();
  }
    public static SearchTableMainRelationWhereAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableMainRelationWhereAction.BaseUpdateMapper();
  }
    public static SearchTableMainRelationWhereAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableMainRelationWhereAction.BaseDeletedMapper();
  }
}
