package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchPlanNewCondition;

public class SearchPlanNewConditionAction {
public static class InsertMapper extends InsertDao<SearchPlanNewCondition, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchPlanNewCondition.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchPlanNewCondition, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> userAccount() {
        return new SelectCriteria<>(this,"user_account",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchConfigInfoId() {
        return new SelectCriteria<>(this,"search_config_info_id",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchConfigInnerConditionId() {
        return new SelectCriteria<>(this,"search_config_inner_condition_id",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchPlanId() {
        return new SelectCriteria<>(this,"search_plan_id",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchColumn() {
        return new SelectCriteria<>(this,"search_column",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchCondition() {
        return new SelectCriteria<>(this,"search_condition",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchGroup() {
        return new SelectCriteria<>(this,"search_group",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchGroupRelation() {
        return new SelectCriteria<>(this,"search_group_relation",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> parentGroup() {
        return new SelectCriteria<>(this,"parent_group",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> parentGroupRelation() {
        return new SelectCriteria<>(this,"parent_group_relation",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> searchValue() {
        return new SelectCriteria<>(this,"search_value",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> areInner() {
        return new SelectCriteria<>(this,"are_inner",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchPlanNewCondition.class);
        }


        public SelectCriteria<SearchPlanNewCondition, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchPlanNewCondition.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchPlanNewCondition, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchPlanNewCondition.class);
        }

        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> userAccount() {
        return new UpdateCriteria<>(this,"user_account",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchConfigInfoId() {
        return new UpdateCriteria<>(this,"search_config_info_id",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchConfigInnerConditionId() {
        return new UpdateCriteria<>(this,"search_config_inner_condition_id",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchPlanId() {
        return new UpdateCriteria<>(this,"search_plan_id",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchColumn() {
        return new UpdateCriteria<>(this,"search_column",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchCondition() {
        return new UpdateCriteria<>(this,"search_condition",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchGroup() {
        return new UpdateCriteria<>(this,"search_group",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchGroupRelation() {
        return new UpdateCriteria<>(this,"search_group_relation",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> parentGroup() {
        return new UpdateCriteria<>(this,"parent_group",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> parentGroupRelation() {
        return new UpdateCriteria<>(this,"parent_group_relation",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> searchValue() {
        return new UpdateCriteria<>(this,"search_value",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> areInner() {
        return new UpdateCriteria<>(this,"are_inner",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchPlanNewCondition.class);
        }


        public UpdateCriteria<SearchPlanNewCondition, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchPlanNewCondition.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchPlanNewCondition, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> userAccount() {
        return new DeletedCriteria<>(this,"user_account",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchConfigInfoId() {
        return new DeletedCriteria<>(this,"search_config_info_id",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchConfigInnerConditionId() {
        return new DeletedCriteria<>(this,"search_config_inner_condition_id",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchPlanId() {
        return new DeletedCriteria<>(this,"search_plan_id",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchColumn() {
        return new DeletedCriteria<>(this,"search_column",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchCondition() {
        return new DeletedCriteria<>(this,"search_condition",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchGroup() {
        return new DeletedCriteria<>(this,"search_group",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchGroupRelation() {
        return new DeletedCriteria<>(this,"search_group_relation",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> parentGroup() {
        return new DeletedCriteria<>(this,"parent_group",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> parentGroupRelation() {
        return new DeletedCriteria<>(this,"parent_group_relation",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> searchValue() {
        return new DeletedCriteria<>(this,"search_value",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> areInner() {
        return new DeletedCriteria<>(this,"are_inner",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchPlanNewCondition.class);
        }


        public DeletedCriteria<SearchPlanNewCondition, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchPlanNewCondition.class);
        }

    }
}
