package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchConfig;

public class SearchConfigAction {
public static class InsertMapper extends InsertDao<SearchConfig, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchConfig.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchConfig, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> configName() {
        return new SelectCriteria<>(this,"config_name",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> searchSql() {
        return new SelectCriteria<>(this,"search_sql",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> useOrgLimitTableAlias() {
        return new SelectCriteria<>(this,"use_org_limit_table_alias",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> useOrgsLimitTableAlias() {
        return new SelectCriteria<>(this,"use_orgs_limit_table_alias",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> hasCode() {
        return new SelectCriteria<>(this,"has_code",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchConfig.class);
        }


        public SelectCriteria<SearchConfig, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchConfig.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchConfig, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchConfig.class);
        }

        public UpdateCriteria<SearchConfig, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> configName() {
        return new UpdateCriteria<>(this,"config_name",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> searchSql() {
        return new UpdateCriteria<>(this,"search_sql",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> useOrgLimitTableAlias() {
        return new UpdateCriteria<>(this,"use_org_limit_table_alias",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> useOrgsLimitTableAlias() {
        return new UpdateCriteria<>(this,"use_orgs_limit_table_alias",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> hasCode() {
        return new UpdateCriteria<>(this,"has_code",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchConfig.class);
        }


        public UpdateCriteria<SearchConfig, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchConfig.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchConfig, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> configName() {
        return new DeletedCriteria<>(this,"config_name",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> searchSql() {
        return new DeletedCriteria<>(this,"search_sql",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> useOrgLimitTableAlias() {
        return new DeletedCriteria<>(this,"use_org_limit_table_alias",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> useOrgsLimitTableAlias() {
        return new DeletedCriteria<>(this,"use_orgs_limit_table_alias",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> hasCode() {
        return new DeletedCriteria<>(this,"has_code",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchConfig.class);
        }


        public DeletedCriteria<SearchConfig, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchConfig.class);
        }

    }
}
