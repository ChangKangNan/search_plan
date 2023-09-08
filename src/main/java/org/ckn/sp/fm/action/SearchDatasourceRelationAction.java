package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchDatasourceRelation;

public class SearchDatasourceRelationAction {
public static class InsertMapper extends InsertDao<SearchDatasourceRelation, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchDatasourceRelation.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchDatasourceRelation, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchDatasourceRelation.class);
        }


        public SelectCriteria<SearchDatasourceRelation, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchDatasourceRelation.class);
        }


        public SelectCriteria<SearchDatasourceRelation, BaseSelectMapper> searchDatasourceId() {
        return new SelectCriteria<>(this,"search_datasource_id",SearchDatasourceRelation.class);
        }


        public SelectCriteria<SearchDatasourceRelation, BaseSelectMapper> searchConfigId() {
        return new SelectCriteria<>(this,"search_config_id",SearchDatasourceRelation.class);
        }


        public SelectCriteria<SearchDatasourceRelation, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchDatasourceRelation.class);
        }


        public SelectCriteria<SearchDatasourceRelation, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchDatasourceRelation.class);
        }


        public SelectCriteria<SearchDatasourceRelation, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchDatasourceRelation.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchDatasourceRelation, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchDatasourceRelation.class);
        }

        public UpdateCriteria<SearchDatasourceRelation, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchDatasourceRelation.class);
        }


        public UpdateCriteria<SearchDatasourceRelation, BaseUpdateMapper> searchDatasourceId() {
        return new UpdateCriteria<>(this,"search_datasource_id",SearchDatasourceRelation.class);
        }


        public UpdateCriteria<SearchDatasourceRelation, BaseUpdateMapper> searchConfigId() {
        return new UpdateCriteria<>(this,"search_config_id",SearchDatasourceRelation.class);
        }


        public UpdateCriteria<SearchDatasourceRelation, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchDatasourceRelation.class);
        }


        public UpdateCriteria<SearchDatasourceRelation, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchDatasourceRelation.class);
        }


        public UpdateCriteria<SearchDatasourceRelation, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchDatasourceRelation.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchDatasourceRelation, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchDatasourceRelation.class);
        }


        public DeletedCriteria<SearchDatasourceRelation, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchDatasourceRelation.class);
        }


        public DeletedCriteria<SearchDatasourceRelation, BaseDeletedMapper> searchDatasourceId() {
        return new DeletedCriteria<>(this,"search_datasource_id",SearchDatasourceRelation.class);
        }


        public DeletedCriteria<SearchDatasourceRelation, BaseDeletedMapper> searchConfigId() {
        return new DeletedCriteria<>(this,"search_config_id",SearchDatasourceRelation.class);
        }


        public DeletedCriteria<SearchDatasourceRelation, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchDatasourceRelation.class);
        }


        public DeletedCriteria<SearchDatasourceRelation, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchDatasourceRelation.class);
        }


        public DeletedCriteria<SearchDatasourceRelation, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchDatasourceRelation.class);
        }

    }
}
