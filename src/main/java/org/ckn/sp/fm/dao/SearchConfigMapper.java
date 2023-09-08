package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchConfigAction;

public class SearchConfigMapper {
    private SearchConfigMapper() {}

    public static SearchConfigAction.InsertMapper lambdaInsert() {
    return new SearchConfigAction.InsertMapper();
  }
    public static SearchConfigAction.BaseSelectMapper lambdaQuery() {
        return new SearchConfigAction.BaseSelectMapper();
  }
    public static SearchConfigAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchConfigAction.BaseUpdateMapper();
  }
    public static SearchConfigAction.BaseDeletedMapper lambdaDelete() {
        return new SearchConfigAction.BaseDeletedMapper();
  }
}
