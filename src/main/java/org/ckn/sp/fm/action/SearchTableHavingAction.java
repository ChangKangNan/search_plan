package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableHaving;

public class SearchTableHavingAction {
public static class InsertMapper extends InsertDao<SearchTableHaving, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableHaving.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableHaving, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> relation() {
        return new SelectCriteria<>(this,"relation",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> tableHavingWhere() {
        return new SelectCriteria<>(this,"table_having_where",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableHaving.class);
        }


        public SelectCriteria<SearchTableHaving, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableHaving.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableHaving, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableHaving.class);
        }

        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> relation() {
        return new UpdateCriteria<>(this,"relation",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> tableHavingWhere() {
        return new UpdateCriteria<>(this,"table_having_where",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableHaving.class);
        }


        public UpdateCriteria<SearchTableHaving, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableHaving.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableHaving, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> relation() {
        return new DeletedCriteria<>(this,"relation",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> tableHavingWhere() {
        return new DeletedCriteria<>(this,"table_having_where",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableHaving.class);
        }


        public DeletedCriteria<SearchTableHaving, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableHaving.class);
        }

    }
}
