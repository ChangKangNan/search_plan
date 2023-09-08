package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableWhere;

public class SearchTableWhereAction {
public static class InsertMapper extends InsertDao<SearchTableWhere, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableWhere.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableWhere, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> relation() {
        return new SelectCriteria<>(this,"relation",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> tableWhere() {
        return new SelectCriteria<>(this,"table_where",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableWhere.class);
        }


        public SelectCriteria<SearchTableWhere, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableWhere.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableWhere, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableWhere.class);
        }

        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> relation() {
        return new UpdateCriteria<>(this,"relation",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> tableWhere() {
        return new UpdateCriteria<>(this,"table_where",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableWhere.class);
        }


        public UpdateCriteria<SearchTableWhere, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableWhere.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableWhere, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> relation() {
        return new DeletedCriteria<>(this,"relation",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> tableWhere() {
        return new DeletedCriteria<>(this,"table_where",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableWhere.class);
        }


        public DeletedCriteria<SearchTableWhere, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableWhere.class);
        }

    }
}
