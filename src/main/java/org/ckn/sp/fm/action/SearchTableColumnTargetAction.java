package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableColumnTarget;

public class SearchTableColumnTargetAction {
public static class InsertMapper extends InsertDao<SearchTableColumnTarget, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableColumnTarget.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableColumnTarget, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> searchTableColumnId() {
        return new SelectCriteria<>(this,"search_table_column_id",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> searchTableMainRelationId() {
        return new SelectCriteria<>(this,"search_table_main_relation_id",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableColumnTarget.class);
        }


        public SelectCriteria<SearchTableColumnTarget, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableColumnTarget.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableColumnTarget, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableColumnTarget.class);
        }

        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> searchTableColumnId() {
        return new UpdateCriteria<>(this,"search_table_column_id",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> searchTableMainRelationId() {
        return new UpdateCriteria<>(this,"search_table_main_relation_id",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableColumnTarget.class);
        }


        public UpdateCriteria<SearchTableColumnTarget, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableColumnTarget.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableColumnTarget, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> searchTableColumnId() {
        return new DeletedCriteria<>(this,"search_table_column_id",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> searchTableMainRelationId() {
        return new DeletedCriteria<>(this,"search_table_main_relation_id",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableColumnTarget.class);
        }


        public DeletedCriteria<SearchTableColumnTarget, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableColumnTarget.class);
        }

    }
}
