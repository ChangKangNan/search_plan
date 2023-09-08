package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableHavingRelation;

public class SearchTableHavingRelationAction {
public static class InsertMapper extends InsertDao<SearchTableHavingRelation, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableHavingRelation.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableHavingRelation, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> searchTableHavingId() {
        return new SelectCriteria<>(this,"search_table_having_id",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> searchTableMainRelationId() {
        return new SelectCriteria<>(this,"search_table_main_relation_id",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableHavingRelation.class);
        }


        public SelectCriteria<SearchTableHavingRelation, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableHavingRelation.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableHavingRelation, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableHavingRelation.class);
        }

        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> searchTableHavingId() {
        return new UpdateCriteria<>(this,"search_table_having_id",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> searchTableMainRelationId() {
        return new UpdateCriteria<>(this,"search_table_main_relation_id",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableHavingRelation.class);
        }


        public UpdateCriteria<SearchTableHavingRelation, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableHavingRelation.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableHavingRelation, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> searchTableHavingId() {
        return new DeletedCriteria<>(this,"search_table_having_id",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> searchTableMainRelationId() {
        return new DeletedCriteria<>(this,"search_table_main_relation_id",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableHavingRelation.class);
        }


        public DeletedCriteria<SearchTableHavingRelation, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableHavingRelation.class);
        }

    }
}
