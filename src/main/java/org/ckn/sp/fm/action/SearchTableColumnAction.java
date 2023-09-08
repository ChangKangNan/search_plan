package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableColumn;

public class SearchTableColumnAction {
public static class InsertMapper extends InsertDao<SearchTableColumn, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableColumn.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableColumn, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> searchTableColumn() {
        return new SelectCriteria<>(this,"search_table_column",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> searchTableColumnInfo() {
        return new SelectCriteria<>(this,"search_table_column_info",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableColumn.class);
        }


        public SelectCriteria<SearchTableColumn, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableColumn.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableColumn, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableColumn.class);
        }

        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> searchTableColumn() {
        return new UpdateCriteria<>(this,"search_table_column",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> searchTableColumnInfo() {
        return new UpdateCriteria<>(this,"search_table_column_info",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableColumn.class);
        }


        public UpdateCriteria<SearchTableColumn, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableColumn.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableColumn, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> searchTableColumn() {
        return new DeletedCriteria<>(this,"search_table_column",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> searchTableColumnInfo() {
        return new DeletedCriteria<>(this,"search_table_column_info",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableColumn.class);
        }


        public DeletedCriteria<SearchTableColumn, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableColumn.class);
        }

    }
}
