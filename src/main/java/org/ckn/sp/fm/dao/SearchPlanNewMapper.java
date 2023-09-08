package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchPlanNewAction;

public class SearchPlanNewMapper {
    private SearchPlanNewMapper() {}

    public static SearchPlanNewAction.InsertMapper lambdaInsert() {
    return new SearchPlanNewAction.InsertMapper();
  }
    public static SearchPlanNewAction.BaseSelectMapper lambdaQuery() {
        return new SearchPlanNewAction.BaseSelectMapper();
  }
    public static SearchPlanNewAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchPlanNewAction.BaseUpdateMapper();
  }
    public static SearchPlanNewAction.BaseDeletedMapper lambdaDelete() {
        return new SearchPlanNewAction.BaseDeletedMapper();
  }
}
