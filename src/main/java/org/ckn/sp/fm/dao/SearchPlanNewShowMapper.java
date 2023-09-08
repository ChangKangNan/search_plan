package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchPlanNewShowAction;

public class SearchPlanNewShowMapper {
    private SearchPlanNewShowMapper() {}

    public static SearchPlanNewShowAction.InsertMapper lambdaInsert() {
    return new SearchPlanNewShowAction.InsertMapper();
  }
    public static SearchPlanNewShowAction.BaseSelectMapper lambdaQuery() {
        return new SearchPlanNewShowAction.BaseSelectMapper();
  }
    public static SearchPlanNewShowAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchPlanNewShowAction.BaseUpdateMapper();
  }
    public static SearchPlanNewShowAction.BaseDeletedMapper lambdaDelete() {
        return new SearchPlanNewShowAction.BaseDeletedMapper();
  }
}
