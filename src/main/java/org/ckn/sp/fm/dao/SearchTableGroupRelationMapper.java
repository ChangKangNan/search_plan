package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableGroupRelationAction;

public class SearchTableGroupRelationMapper {
    private SearchTableGroupRelationMapper() {}

    public static SearchTableGroupRelationAction.InsertMapper lambdaInsert() {
    return new SearchTableGroupRelationAction.InsertMapper();
  }
    public static SearchTableGroupRelationAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableGroupRelationAction.BaseSelectMapper();
  }
    public static SearchTableGroupRelationAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableGroupRelationAction.BaseUpdateMapper();
  }
    public static SearchTableGroupRelationAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableGroupRelationAction.BaseDeletedMapper();
  }
}
