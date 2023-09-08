package org.ckn.sp.fm.action;
import cn.ft.ckn.fastmapper.component.action.*;
import cn.ft.ckn.fastmapper.component.criteria.*;
import cn.ft.ckn.fastmapper.component.dao.*;
import org.ckn.sp.fm.bean.SearchTableGroup;

public class SearchTableGroupAction {
public static class InsertMapper extends InsertDao<SearchTableGroup, InsertMapper> {
        public InsertMapper(){
        super(InsertMapper.class,SearchTableGroup.class);
        }
}

public static class BaseSelectMapper extends BaseSelectAction<SearchTableGroup, BaseSelectMapper> {
        public BaseSelectMapper() {
        super(BaseSelectMapper.class,SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> id() {
        return new SelectCriteria<>(this,"id",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> pageTag() {
        return new SelectCriteria<>(this,"page_tag",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> tableGroup() {
        return new SelectCriteria<>(this,"table_group",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> remark() {
        return new SelectCriteria<>(this,"remark",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> createBy() {
        return new SelectCriteria<>(this,"create_by",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> updateBy() {
        return new SelectCriteria<>(this,"update_by",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> createTime() {
        return new SelectCriteria<>(this,"create_time",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> updateTime() {
        return new SelectCriteria<>(this,"update_time",SearchTableGroup.class);
        }


        public SelectCriteria<SearchTableGroup, BaseSelectMapper> deleted() {
        return new SelectCriteria<>(this,"deleted",SearchTableGroup.class);
        }

}

public static class BaseUpdateMapper extends BaseUpdateAction<SearchTableGroup, BaseUpdateMapper> {

        public BaseUpdateMapper() {
        super(BaseUpdateMapper.class,SearchTableGroup.class);
        }

        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> id() {
        return new UpdateCriteria<>(this,"id",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> pageTag() {
        return new UpdateCriteria<>(this,"page_tag",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> tableGroup() {
        return new UpdateCriteria<>(this,"table_group",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> remark() {
        return new UpdateCriteria<>(this,"remark",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> createBy() {
        return new UpdateCriteria<>(this,"create_by",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> updateBy() {
        return new UpdateCriteria<>(this,"update_by",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> createTime() {
        return new UpdateCriteria<>(this,"create_time",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> updateTime() {
        return new UpdateCriteria<>(this,"update_time",SearchTableGroup.class);
        }


        public UpdateCriteria<SearchTableGroup, BaseUpdateMapper> deleted() {
        return new UpdateCriteria<>(this,"deleted",SearchTableGroup.class);
        }

}

public static class BaseDeletedMapper extends BaseDeletedAction<SearchTableGroup, BaseDeletedMapper> {
        public BaseDeletedMapper() {
        super(BaseDeletedMapper.class,SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> id() {
        return new DeletedCriteria<>(this,"id",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> pageTag() {
        return new DeletedCriteria<>(this,"page_tag",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> tableGroup() {
        return new DeletedCriteria<>(this,"table_group",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> remark() {
        return new DeletedCriteria<>(this,"remark",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> createBy() {
        return new DeletedCriteria<>(this,"create_by",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> updateBy() {
        return new DeletedCriteria<>(this,"update_by",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> createTime() {
        return new DeletedCriteria<>(this,"create_time",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> updateTime() {
        return new DeletedCriteria<>(this,"update_time",SearchTableGroup.class);
        }


        public DeletedCriteria<SearchTableGroup, BaseDeletedMapper> deleted() {
        return new DeletedCriteria<>(this,"deleted",SearchTableGroup.class);
        }

    }
}
