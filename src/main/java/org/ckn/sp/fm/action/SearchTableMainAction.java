package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableMain;

public class SearchTableMainAction {
public static class InsertMapper extends InsertDao<SearchTableMain, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableMain.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableMain, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> searchTableName() {
        return new SelectCriteria<>(this,"search_table_name",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> alias() {
        return new SelectCriteria<>(this,"alias",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> isExtend() {
        return new SelectCriteria<>(this,"is_extend",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableMain.class);
        }


        public SelectCriteria<SearchTableMain, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableMain.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableMain, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableMain.class);
        }

        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> searchTableName() {
        return new UpdateCriteria<>(this,"search_table_name",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> alias() {
        return new UpdateCriteria<>(this,"alias",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> isExtend() {
        return new UpdateCriteria<>(this,"is_extend",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableMain.class);
        }


        public UpdateCriteria<SearchTableMain, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableMain.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableMain, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> searchTableName() {
        return new DeletedCriteria<>(this,"search_table_name",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> alias() {
        return new DeletedCriteria<>(this,"alias",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> isExtend() {
        return new DeletedCriteria<>(this,"is_extend",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableMain.class);
        }


        public DeletedCriteria<SearchTableMain, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableMain.class);
        }

    }
}
