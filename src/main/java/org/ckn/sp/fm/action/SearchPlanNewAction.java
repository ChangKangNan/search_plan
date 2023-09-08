package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchPlanNew;

public class SearchPlanNewAction {
public static class InsertMapper extends InsertDao<SearchPlanNew, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchPlanNew.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchPlanNew, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> userAccount() {
        return new SelectCriteria<>(this,"user_account",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> planName() {
        return new SelectCriteria<>(this,"plan_name",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> customMarker() {
        return new SelectCriteria<>(this,"custom_marker",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> searchSql() {
        return new SelectCriteria<>(this,"search_sql",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> sortNum() {
        return new SelectCriteria<>(this,"sort_num",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> def() {
        return new SelectCriteria<>(this,"def",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> createOrg() {
        return new SelectCriteria<>(this,"create_org",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchPlanNew.class);
        }


        public SelectCriteria<SearchPlanNew, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchPlanNew.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchPlanNew, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchPlanNew.class);
        }

        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> userAccount() {
        return new UpdateCriteria<>(this,"user_account",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> planName() {
        return new UpdateCriteria<>(this,"plan_name",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> customMarker() {
        return new UpdateCriteria<>(this,"custom_marker",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> searchSql() {
        return new UpdateCriteria<>(this,"search_sql",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> sortNum() {
        return new UpdateCriteria<>(this,"sort_num",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> def() {
        return new UpdateCriteria<>(this,"def",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> createOrg() {
        return new UpdateCriteria<>(this,"create_org",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchPlanNew.class);
        }


        public UpdateCriteria<SearchPlanNew, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchPlanNew.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchPlanNew, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> userAccount() {
        return new DeletedCriteria<>(this,"user_account",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> planName() {
        return new DeletedCriteria<>(this,"plan_name",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> customMarker() {
        return new DeletedCriteria<>(this,"custom_marker",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> searchSql() {
        return new DeletedCriteria<>(this,"search_sql",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> sortNum() {
        return new DeletedCriteria<>(this,"sort_num",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> def() {
        return new DeletedCriteria<>(this,"def",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> createOrg() {
        return new DeletedCriteria<>(this,"create_org",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchPlanNew.class);
        }


        public DeletedCriteria<SearchPlanNew, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchPlanNew.class);
        }

    }
}
