package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchPlanNewConditionAction;

public class SearchPlanNewConditionMapper {
    private SearchPlanNewConditionMapper() {}

    public static SearchPlanNewConditionAction.InsertMapper lambdaInsert() {
    return new SearchPlanNewConditionAction.InsertMapper();
  }
    public static SearchPlanNewConditionAction.BaseSelectMapper lambdaQuery() {
        return new SearchPlanNewConditionAction.BaseSelectMapper();
  }
    public static SearchPlanNewConditionAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchPlanNewConditionAction.BaseUpdateMapper();
  }
    public static SearchPlanNewConditionAction.BaseDeletedMapper lambdaDelete() {
        return new SearchPlanNewConditionAction.BaseDeletedMapper();
  }
}
