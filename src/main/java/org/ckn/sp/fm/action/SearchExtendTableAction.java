package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchExtendTable;

public class SearchExtendTableAction {
public static class InsertMapper extends InsertDao<SearchExtendTable, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchExtendTable.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchExtendTable, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> searchTableMainId() {
        return new SelectCriteria<>(this,"search_table_main_id",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> extendTableName() {
        return new SelectCriteria<>(this,"extend_table_name",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> extendSql() {
        return new SelectCriteria<>(this,"extend_sql",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchExtendTable.class);
        }


        public SelectCriteria<SearchExtendTable, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchExtendTable.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchExtendTable, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchExtendTable.class);
        }

        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> searchTableMainId() {
        return new UpdateCriteria<>(this,"search_table_main_id",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> extendTableName() {
        return new UpdateCriteria<>(this,"extend_table_name",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> extendSql() {
        return new UpdateCriteria<>(this,"extend_sql",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchExtendTable.class);
        }


        public UpdateCriteria<SearchExtendTable, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchExtendTable.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchExtendTable, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> searchTableMainId() {
        return new DeletedCriteria<>(this,"search_table_main_id",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> extendTableName() {
        return new DeletedCriteria<>(this,"extend_table_name",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> extendSql() {
        return new DeletedCriteria<>(this,"extend_sql",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchExtendTable.class);
        }


        public DeletedCriteria<SearchExtendTable, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchExtendTable.class);
        }

    }
}
