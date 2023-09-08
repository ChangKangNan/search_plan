package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableMainRelationLink;

public class SearchTableMainRelationLinkAction {
public static class InsertMapper extends InsertDao<SearchTableMainRelationLink, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableMainRelationLink.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableMainRelationLink, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> searchTableMainRelationId() {
        return new SelectCriteria<>(this,"search_table_main_relation_id",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> tableColumnName() {
        return new SelectCriteria<>(this,"table_column_name",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> targetTableName() {
        return new SelectCriteria<>(this,"target_table_name",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> targetTableAlias() {
        return new SelectCriteria<>(this,"target_table_alias",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> targetTableColumnName() {
        return new SelectCriteria<>(this,"target_table_column_name",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> parentRelationId() {
        return new SelectCriteria<>(this,"parent_relation_id",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableMainRelationLink.class);
        }


        public SelectCriteria<SearchTableMainRelationLink, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableMainRelationLink.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableMainRelationLink, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableMainRelationLink.class);
        }

        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> searchTableMainRelationId() {
        return new UpdateCriteria<>(this,"search_table_main_relation_id",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> tableColumnName() {
        return new UpdateCriteria<>(this,"table_column_name",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> targetTableName() {
        return new UpdateCriteria<>(this,"target_table_name",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> targetTableAlias() {
        return new UpdateCriteria<>(this,"target_table_alias",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> targetTableColumnName() {
        return new UpdateCriteria<>(this,"target_table_column_name",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> parentRelationId() {
        return new UpdateCriteria<>(this,"parent_relation_id",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableMainRelationLink.class);
        }


        public UpdateCriteria<SearchTableMainRelationLink, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableMainRelationLink.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableMainRelationLink, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> searchTableMainRelationId() {
        return new DeletedCriteria<>(this,"search_table_main_relation_id",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> tableColumnName() {
        return new DeletedCriteria<>(this,"table_column_name",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> targetTableName() {
        return new DeletedCriteria<>(this,"target_table_name",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> targetTableAlias() {
        return new DeletedCriteria<>(this,"target_table_alias",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> targetTableColumnName() {
        return new DeletedCriteria<>(this,"target_table_column_name",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> parentRelationId() {
        return new DeletedCriteria<>(this,"parent_relation_id",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableMainRelationLink.class);
        }


        public DeletedCriteria<SearchTableMainRelationLink, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableMainRelationLink.class);
        }

    }
}
