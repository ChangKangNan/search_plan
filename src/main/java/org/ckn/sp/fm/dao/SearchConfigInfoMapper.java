package org.ckn.sp.fm.dao;
import org.ckn.sp.fm.action.SearchConfigInfoAction;

public class SearchConfigInfoMapper {
    private SearchConfigInfoMapper() {}

    public static SearchConfigInfoAction.InsertMapper lambdaInsert() {
    return new SearchConfigInfoAction.InsertMapper();
  }
    public static SearchConfigInfoAction.BaseSelectMapper lambdaQuery() {
        return new SearchConfigInfoAction.BaseSelectMapper();
  }
    public static SearchConfigInfoAction.BaseUpdateMapper lambdaUpdate() {
        return new SearchConfigInfoAction.BaseUpdateMapper();
  }
    public static SearchConfigInfoAction.BaseDeletedMapper lambdaDelete() {
        return new SearchConfigInfoAction.BaseDeletedMapper();
  }
}
