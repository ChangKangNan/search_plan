package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchConfigInfo;

public class SearchConfigInfoAction {
public static class InsertMapper extends InsertDao<SearchConfigInfo, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchConfigInfo.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchConfigInfo, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> searchConfigId() {
        return new SelectCriteria<>(this,"search_config_id",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> searchName() {
        return new SelectCriteria<>(this,"search_name",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> searchKey() {
        return new SelectCriteria<>(this,"search_key",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> limitSearchConditions() {
        return new SelectCriteria<>(this,"limit_search_conditions",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> limitSearchValues() {
        return new SelectCriteria<>(this,"limit_search_values",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> searchValueSource() {
        return new SelectCriteria<>(this,"search_value_source",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> searchType() {
        return new SelectCriteria<>(this,"search_type",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> sortNum() {
        return new SelectCriteria<>(this,"sort_num",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> hidden() {
        return new SelectCriteria<>(this,"hidden",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> changeTb() {
        return new SelectCriteria<>(this,"change_tb",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> changeDateRule() {
        return new SelectCriteria<>(this,"change_date_rule",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> changeTbName() {
        return new SelectCriteria<>(this,"change_tb_name",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> axis() {
        return new SelectCriteria<>(this,"axis",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> point() {
        return new SelectCriteria<>(this,"point",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> defaultOrderBy() {
        return new SelectCriteria<>(this,"default_order_by",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> defaultOrderByDesc() {
        return new SelectCriteria<>(this,"default_order_by_desc",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchConfigInfo.class);
        }


        public SelectCriteria<SearchConfigInfo, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchConfigInfo.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchConfigInfo, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchConfigInfo.class);
        }

        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> searchConfigId() {
        return new UpdateCriteria<>(this,"search_config_id",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> searchName() {
        return new UpdateCriteria<>(this,"search_name",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> searchKey() {
        return new UpdateCriteria<>(this,"search_key",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> limitSearchConditions() {
        return new UpdateCriteria<>(this,"limit_search_conditions",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> limitSearchValues() {
        return new UpdateCriteria<>(this,"limit_search_values",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> searchValueSource() {
        return new UpdateCriteria<>(this,"search_value_source",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> searchType() {
        return new UpdateCriteria<>(this,"search_type",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> sortNum() {
        return new UpdateCriteria<>(this,"sort_num",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> hidden() {
        return new UpdateCriteria<>(this,"hidden",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> changeTb() {
        return new UpdateCriteria<>(this,"change_tb",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> changeDateRule() {
        return new UpdateCriteria<>(this,"change_date_rule",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> changeTbName() {
        return new UpdateCriteria<>(this,"change_tb_name",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> axis() {
        return new UpdateCriteria<>(this,"axis",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> point() {
        return new UpdateCriteria<>(this,"point",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> defaultOrderBy() {
        return new UpdateCriteria<>(this,"default_order_by",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> defaultOrderByDesc() {
        return new UpdateCriteria<>(this,"default_order_by_desc",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchConfigInfo.class);
        }


        public UpdateCriteria<SearchConfigInfo, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchConfigInfo.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchConfigInfo, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> searchConfigId() {
        return new DeletedCriteria<>(this,"search_config_id",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> searchName() {
        return new DeletedCriteria<>(this,"search_name",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> searchKey() {
        return new DeletedCriteria<>(this,"search_key",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> limitSearchConditions() {
        return new DeletedCriteria<>(this,"limit_search_conditions",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> limitSearchValues() {
        return new DeletedCriteria<>(this,"limit_search_values",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> searchValueSource() {
        return new DeletedCriteria<>(this,"search_value_source",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> searchType() {
        return new DeletedCriteria<>(this,"search_type",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> sortNum() {
        return new DeletedCriteria<>(this,"sort_num",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> hidden() {
        return new DeletedCriteria<>(this,"hidden",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> changeTb() {
        return new DeletedCriteria<>(this,"change_tb",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> changeDateRule() {
        return new DeletedCriteria<>(this,"change_date_rule",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> changeTbName() {
        return new DeletedCriteria<>(this,"change_tb_name",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> axis() {
        return new DeletedCriteria<>(this,"axis",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> point() {
        return new DeletedCriteria<>(this,"point",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> defaultOrderBy() {
        return new DeletedCriteria<>(this,"default_order_by",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> defaultOrderByDesc() {
        return new DeletedCriteria<>(this,"default_order_by_desc",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchConfigInfo.class);
        }


        public DeletedCriteria<SearchConfigInfo, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchConfigInfo.class);
        }

    }
}
