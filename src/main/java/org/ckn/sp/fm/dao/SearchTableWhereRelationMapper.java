package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableWhereRelationAction;

public class SearchTableWhereRelationMapper {
    private SearchTableWhereRelationMapper() {}

    public static SearchTableWhereRelationAction.InsertMapper lambdaInsert() {
    return new SearchTableWhereRelationAction.InsertMapper();
  }
    public static SearchTableWhereRelationAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableWhereRelationAction.BaseSelectMapper();
  }
    public static SearchTableWhereRelationAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableWhereRelationAction.BaseUpdateMapper();
  }
    public static SearchTableWhereRelationAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableWhereRelationAction.BaseDeletedMapper();
  }
}
