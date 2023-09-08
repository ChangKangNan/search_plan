package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchDatasource;

public class SearchDatasourceAction {
public static class InsertMapper extends InsertDao<SearchDatasource, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchDatasource.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchDatasource, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> datasourceName() {
        return new SelectCriteria<>(this,"datasource_name",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> datasourceDriver() {
        return new SelectCriteria<>(this,"datasource_driver",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> datasourceUrl() {
        return new SelectCriteria<>(this,"datasource_url",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> datasourceUsername() {
        return new SelectCriteria<>(this,"datasource_username",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> datasourcePassword() {
        return new SelectCriteria<>(this,"datasource_password",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchDatasource.class);
        }


        public SelectCriteria<SearchDatasource, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchDatasource.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchDatasource, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchDatasource.class);
        }

        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> datasourceName() {
        return new UpdateCriteria<>(this,"datasource_name",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> datasourceDriver() {
        return new UpdateCriteria<>(this,"datasource_driver",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> datasourceUrl() {
        return new UpdateCriteria<>(this,"datasource_url",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> datasourceUsername() {
        return new UpdateCriteria<>(this,"datasource_username",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> datasourcePassword() {
        return new UpdateCriteria<>(this,"datasource_password",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchDatasource.class);
        }


        public UpdateCriteria<SearchDatasource, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchDatasource.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchDatasource, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> datasourceName() {
        return new DeletedCriteria<>(this,"datasource_name",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> datasourceDriver() {
        return new DeletedCriteria<>(this,"datasource_driver",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> datasourceUrl() {
        return new DeletedCriteria<>(this,"datasource_url",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> datasourceUsername() {
        return new DeletedCriteria<>(this,"datasource_username",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> datasourcePassword() {
        return new DeletedCriteria<>(this,"datasource_password",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchDatasource.class);
        }


        public DeletedCriteria<SearchDatasource, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchDatasource.class);
        }

    }
}
