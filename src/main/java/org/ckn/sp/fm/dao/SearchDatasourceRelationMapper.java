package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchDatasourceRelationAction;

public class SearchDatasourceRelationMapper {
    private SearchDatasourceRelationMapper() {}

    public static SearchDatasourceRelationAction.InsertMapper lambdaInsert() {
    return new SearchDatasourceRelationAction.InsertMapper();
  }
    public static SearchDatasourceRelationAction.BaseSelectMapper lambdaQuery() {
        return new SearchDatasourceRelationAction.BaseSelectMapper();
  }
    public static SearchDatasourceRelationAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchDatasourceRelationAction.BaseUpdateMapper();
  }
    public static SearchDatasourceRelationAction.BaseDeletedMapper lambdaDelete() {
        return new SearchDatasourceRelationAction.BaseDeletedMapper();
  }
}
