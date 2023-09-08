package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchConfigInnerCondition;

public class SearchConfigInnerConditionAction {
public static class InsertMapper extends InsertDao<SearchConfigInnerCondition, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchConfigInnerCondition.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchConfigInnerCondition, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> searchConfigId() {
        return new SelectCriteria<>(this,"search_config_id",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> searchName() {
        return new SelectCriteria<>(this,"search_name",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> searchKey() {
        return new SelectCriteria<>(this,"search_key",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> limitSearchConditions() {
        return new SelectCriteria<>(this,"limit_search_conditions",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> limitSearchValues() {
        return new SelectCriteria<>(this,"limit_search_values",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> searchValueSource() {
        return new SelectCriteria<>(this,"search_value_source",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> searchType() {
        return new SelectCriteria<>(this,"search_type",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> axis() {
        return new SelectCriteria<>(this,"axis",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> relation() {
        return new SelectCriteria<>(this,"relation",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> defaultValue() {
        return new SelectCriteria<>(this,"default_value",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchConfigInnerCondition.class);
        }


        public SelectCriteria<SearchConfigInnerCondition, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchConfigInnerCondition.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchConfigInnerCondition, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchConfigInnerCondition.class);
        }

        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> searchConfigId() {
        return new UpdateCriteria<>(this,"search_config_id",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> searchName() {
        return new UpdateCriteria<>(this,"search_name",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> searchKey() {
        return new UpdateCriteria<>(this,"search_key",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> limitSearchConditions() {
        return new UpdateCriteria<>(this,"limit_search_conditions",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> limitSearchValues() {
        return new UpdateCriteria<>(this,"limit_search_values",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> searchValueSource() {
        return new UpdateCriteria<>(this,"search_value_source",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> searchType() {
        return new UpdateCriteria<>(this,"search_type",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> axis() {
        return new UpdateCriteria<>(this,"axis",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> relation() {
        return new UpdateCriteria<>(this,"relation",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> defaultValue() {
        return new UpdateCriteria<>(this,"default_value",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchConfigInnerCondition.class);
        }


        public UpdateCriteria<SearchConfigInnerCondition, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchConfigInnerCondition.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchConfigInnerCondition, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> searchConfigId() {
        return new DeletedCriteria<>(this,"search_config_id",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> searchName() {
        return new DeletedCriteria<>(this,"search_name",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> searchKey() {
        return new DeletedCriteria<>(this,"search_key",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> limitSearchConditions() {
        return new DeletedCriteria<>(this,"limit_search_conditions",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> limitSearchValues() {
        return new DeletedCriteria<>(this,"limit_search_values",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> searchValueSource() {
        return new DeletedCriteria<>(this,"search_value_source",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> searchType() {
        return new DeletedCriteria<>(this,"search_type",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> axis() {
        return new DeletedCriteria<>(this,"axis",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> relation() {
        return new DeletedCriteria<>(this,"relation",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> defaultValue() {
        return new DeletedCriteria<>(this,"default_value",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchConfigInnerCondition.class);
        }


        public DeletedCriteria<SearchConfigInnerCondition, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchConfigInnerCondition.class);
        }

    }
}
