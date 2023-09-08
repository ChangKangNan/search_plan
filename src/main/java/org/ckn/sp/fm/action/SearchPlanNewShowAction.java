package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchPlanNewShow;

public class SearchPlanNewShowAction {
public static class InsertMapper extends InsertDao<SearchPlanNewShow, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchPlanNewShow.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchPlanNewShow, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> userAccount() {
        return new SelectCriteria<>(this,"user_account",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> searchPlanId() {
        return new SelectCriteria<>(this,"search_plan_id",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> searchColumn() {
        return new SelectCriteria<>(this,"search_column",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> showStatus() {
        return new SelectCriteria<>(this,"show_status",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchPlanNewShow.class);
        }


        public SelectCriteria<SearchPlanNewShow, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchPlanNewShow.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchPlanNewShow, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchPlanNewShow.class);
        }

        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> userAccount() {
        return new UpdateCriteria<>(this,"user_account",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> searchPlanId() {
        return new UpdateCriteria<>(this,"search_plan_id",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> searchColumn() {
        return new UpdateCriteria<>(this,"search_column",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> showStatus() {
        return new UpdateCriteria<>(this,"show_status",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchPlanNewShow.class);
        }


        public UpdateCriteria<SearchPlanNewShow, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchPlanNewShow.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchPlanNewShow, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> userAccount() {
        return new DeletedCriteria<>(this,"user_account",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> searchPlanId() {
        return new DeletedCriteria<>(this,"search_plan_id",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> searchColumn() {
        return new DeletedCriteria<>(this,"search_column",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> showStatus() {
        return new DeletedCriteria<>(this,"show_status",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchPlanNewShow.class);
        }


        public DeletedCriteria<SearchPlanNewShow, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchPlanNewShow.class);
        }

    }
}
