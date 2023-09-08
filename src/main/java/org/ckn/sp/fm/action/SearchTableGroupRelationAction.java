package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableGroupRelation;

public class SearchTableGroupRelationAction {
public static class InsertMapper extends InsertDao<SearchTableGroupRelation, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableGroupRelation.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableGroupRelation, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> searchTableGroupId() {
        return new SelectCriteria<>(this,"search_table_group_id",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> searchTableMainRelationId() {
        return new SelectCriteria<>(this,"search_table_main_relation_id",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableGroupRelation.class);
        }


        public SelectCriteria<SearchTableGroupRelation, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableGroupRelation.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableGroupRelation, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableGroupRelation.class);
        }

        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> searchTableGroupId() {
        return new UpdateCriteria<>(this,"search_table_group_id",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> searchTableMainRelationId() {
        return new UpdateCriteria<>(this,"search_table_main_relation_id",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableGroupRelation.class);
        }


        public UpdateCriteria<SearchTableGroupRelation, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableGroupRelation.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableGroupRelation, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> searchTableGroupId() {
        return new DeletedCriteria<>(this,"search_table_group_id",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> searchTableMainRelationId() {
        return new DeletedCriteria<>(this,"search_table_main_relation_id",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableGroupRelation.class);
        }


        public DeletedCriteria<SearchTableGroupRelation, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableGroupRelation.class);
        }

    }
}
