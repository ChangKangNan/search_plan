package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchDatasourceAction;

public class SearchDatasourceMapper {
    private SearchDatasourceMapper() {}

    public static SearchDatasourceAction.InsertMapper lambdaInsert() {
    return new SearchDatasourceAction.InsertMapper();
  }
    public static SearchDatasourceAction.BaseSelectMapper lambdaQuery() {
        return new SearchDatasourceAction.BaseSelectMapper();
  }
    public static SearchDatasourceAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchDatasourceAction.BaseUpdateMapper();
  }
    public static SearchDatasourceAction.BaseDeletedMapper lambdaDelete() {
        return new SearchDatasourceAction.BaseDeletedMapper();
  }
}
