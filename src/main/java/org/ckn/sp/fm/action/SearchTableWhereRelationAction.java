package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableWhereRelation;

public class SearchTableWhereRelationAction {
public static class InsertMapper extends InsertDao<SearchTableWhereRelation, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableWhereRelation.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableWhereRelation, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> searchTableWhereId() {
        return new SelectCriteria<>(this,"search_table_where_id",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> searchTableMainRelationId() {
        return new SelectCriteria<>(this,"search_table_main_relation_id",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableWhereRelation.class);
        }


        public SelectCriteria<SearchTableWhereRelation, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableWhereRelation.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableWhereRelation, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableWhereRelation.class);
        }

        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> searchTableWhereId() {
        return new UpdateCriteria<>(this,"search_table_where_id",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> searchTableMainRelationId() {
        return new UpdateCriteria<>(this,"search_table_main_relation_id",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableWhereRelation.class);
        }


        public UpdateCriteria<SearchTableWhereRelation, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableWhereRelation.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableWhereRelation, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> searchTableWhereId() {
        return new DeletedCriteria<>(this,"search_table_where_id",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> searchTableMainRelationId() {
        return new DeletedCriteria<>(this,"search_table_main_relation_id",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableWhereRelation.class);
        }


        public DeletedCriteria<SearchTableWhereRelation, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableWhereRelation.class);
        }

    }
}
