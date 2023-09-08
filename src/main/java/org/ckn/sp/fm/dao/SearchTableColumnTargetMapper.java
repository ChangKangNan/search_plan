package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchTableColumnTargetAction;

public class SearchTableColumnTargetMapper {
    private SearchTableColumnTargetMapper() {}

    public static SearchTableColumnTargetAction.InsertMapper lambdaInsert() {
    return new SearchTableColumnTargetAction.InsertMapper();
  }
    public static SearchTableColumnTargetAction.BaseSelectMapper lambdaQuery() {
        return new SearchTableColumnTargetAction.BaseSelectMapper();
  }
    public static SearchTableColumnTargetAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchTableColumnTargetAction.BaseUpdateMapper();
  }
    public static SearchTableColumnTargetAction.BaseDeletedMapper lambdaDelete() {
        return new SearchTableColumnTargetAction.BaseDeletedMapper();
  }
}
