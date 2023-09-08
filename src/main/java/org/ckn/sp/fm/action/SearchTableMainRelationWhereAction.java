package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableMainRelationWhere;

public class SearchTableMainRelationWhereAction {
public static class InsertMapper extends InsertDao<SearchTableMainRelationWhere, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableMainRelationWhere.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableMainRelationWhere, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> searchTableMainRelationId() {
        return new SelectCriteria<>(this,"search_table_main_relation_id",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> targetTableName() {
        return new SelectCriteria<>(this,"target_table_name",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> targetTableAlias() {
        return new SelectCriteria<>(this,"target_table_alias",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> whereInfo() {
        return new SelectCriteria<>(this,"where_info",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableMainRelationWhere.class);
        }


        public SelectCriteria<SearchTableMainRelationWhere, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableMainRelationWhere.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableMainRelationWhere, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableMainRelationWhere.class);
        }

        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> searchTableMainRelationId() {
        return new UpdateCriteria<>(this,"search_table_main_relation_id",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> targetTableName() {
        return new UpdateCriteria<>(this,"target_table_name",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> targetTableAlias() {
        return new UpdateCriteria<>(this,"target_table_alias",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> whereInfo() {
        return new UpdateCriteria<>(this,"where_info",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableMainRelationWhere.class);
        }


        public UpdateCriteria<SearchTableMainRelationWhere, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableMainRelationWhere.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableMainRelationWhere, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> searchTableMainRelationId() {
        return new DeletedCriteria<>(this,"search_table_main_relation_id",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> targetTableName() {
        return new DeletedCriteria<>(this,"target_table_name",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> targetTableAlias() {
        return new DeletedCriteria<>(this,"target_table_alias",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> whereInfo() {
        return new DeletedCriteria<>(this,"where_info",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableMainRelationWhere.class);
        }


        public DeletedCriteria<SearchTableMainRelationWhere, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableMainRelationWhere.class);
        }

    }
}
