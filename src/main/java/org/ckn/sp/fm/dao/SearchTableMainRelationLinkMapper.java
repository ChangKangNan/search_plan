package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableMainRelationLinkAction;

public class SearchTableMainRelationLinkMapper {
    private SearchTableMainRelationLinkMapper() {}

    public static SearchTableMainRelationLinkAction.InsertMapper lambdaInsert() {
    return new SearchTableMainRelationLinkAction.InsertMapper();
  }
    public static SearchTableMainRelationLinkAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableMainRelationLinkAction.BaseSelectMapper();
  }
    public static SearchTableMainRelationLinkAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableMainRelationLinkAction.BaseUpdateMapper();
  }
    public static SearchTableMainRelationLinkAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableMainRelationLinkAction.BaseDeletedMapper();
  }
}
