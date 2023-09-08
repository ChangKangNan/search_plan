package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableGroupAction;

public class SearchTableGroupMapper {
    private SearchTableGroupMapper() {}

    public static SearchTableGroupAction.InsertMapper lambdaInsert() {
    return new SearchTableGroupAction.InsertMapper();
  }
    public static SearchTableGroupAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableGroupAction.BaseSelectMapper();
  }
    public static SearchTableGroupAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableGroupAction.BaseUpdateMapper();
  }
    public static SearchTableGroupAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableGroupAction.BaseDeletedMapper();
  }
}
