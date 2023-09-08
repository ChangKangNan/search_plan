package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchConfigInnerConditionAction;

public class SearchConfigInnerConditionMapper {
    private SearchConfigInnerConditionMapper() {}

    public static SearchConfigInnerConditionAction.InsertMapper lambdaInsert() {
    return new SearchConfigInnerConditionAction.InsertMapper();
  }
    public static SearchConfigInnerConditionAction.BaseSelectMapper lambdaQuery() {
        return new SearchConfigInnerConditionAction.BaseSelectMapper();
  }
    public static SearchConfigInnerConditionAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchConfigInnerConditionAction.BaseUpdateMapper();
  }
    public static SearchConfigInnerConditionAction.BaseDeletedMapper lambdaDelete() {
        return new SearchConfigInnerConditionAction.BaseDeletedMapper();
  }
}
