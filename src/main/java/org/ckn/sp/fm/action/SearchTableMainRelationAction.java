package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableMainRelation;

public class SearchTableMainRelationAction {
public static class InsertMapper extends InsertDao<SearchTableMainRelation, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableMainRelation.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableMainRelation, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> searchTableMainId() {
        return new SelectCriteria<>(this,"search_table_main_id",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> sourceTableName() {
        return new SelectCriteria<>(this,"source_table_name",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> sourceTableAlias() {
        return new SelectCriteria<>(this,"source_table_alias",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> targetTableName() {
        return new SelectCriteria<>(this,"target_table_name",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> targetTableAlias() {
        return new SelectCriteria<>(this,"target_table_alias",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> sourceTableColumnName() {
        return new SelectCriteria<>(this,"source_table_column_name",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> targetTableColumnName() {
        return new SelectCriteria<>(this,"target_table_column_name",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> isExtend() {
        return new SelectCriteria<>(this,"is_extend",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> linkRelation() {
        return new SelectCriteria<>(this,"link_relation",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableMainRelation.class);
        }


        public SelectCriteria<SearchTableMainRelation, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableMainRelation.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableMainRelation, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableMainRelation.class);
        }

        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> searchTableMainId() {
        return new UpdateCriteria<>(this,"search_table_main_id",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> sourceTableName() {
        return new UpdateCriteria<>(this,"source_table_name",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> sourceTableAlias() {
        return new UpdateCriteria<>(this,"source_table_alias",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> targetTableName() {
        return new UpdateCriteria<>(this,"target_table_name",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> targetTableAlias() {
        return new UpdateCriteria<>(this,"target_table_alias",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> sourceTableColumnName() {
        return new UpdateCriteria<>(this,"source_table_column_name",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> targetTableColumnName() {
        return new UpdateCriteria<>(this,"target_table_column_name",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> isExtend() {
        return new UpdateCriteria<>(this,"is_extend",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> linkRelation() {
        return new UpdateCriteria<>(this,"link_relation",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableMainRelation.class);
        }


        public UpdateCriteria<SearchTableMainRelation, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableMainRelation.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableMainRelation, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> searchTableMainId() {
        return new DeletedCriteria<>(this,"search_table_main_id",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> sourceTableName() {
        return new DeletedCriteria<>(this,"source_table_name",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> sourceTableAlias() {
        return new DeletedCriteria<>(this,"source_table_alias",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> targetTableName() {
        return new DeletedCriteria<>(this,"target_table_name",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> targetTableAlias() {
        return new DeletedCriteria<>(this,"target_table_alias",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> sourceTableColumnName() {
        return new DeletedCriteria<>(this,"source_table_column_name",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> targetTableColumnName() {
        return new DeletedCriteria<>(this,"target_table_column_name",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> isExtend() {
        return new DeletedCriteria<>(this,"is_extend",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> linkRelation() {
        return new DeletedCriteria<>(this,"link_relation",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableMainRelation.class);
        }


        public DeletedCriteria<SearchTableMainRelation, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableMainRelation.class);
        }

    }
}
