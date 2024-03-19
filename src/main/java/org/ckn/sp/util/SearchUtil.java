package org.ckn.sp.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.bean.SearchConfigInnerCondition;
import org.ckn.sp.fm.bean.SearchPlanNew;
import org.ckn.sp.fm.dao.SearchConfigInfoMapper;
import org.ckn.sp.fm.dao.SearchConfigInnerConditionMapper;
import org.ckn.sp.fm.dao.SearchPlanNewMapper;
import org.ckn.sp.service.ISearchPlanService;
import org.ckn.sp.vo.PageInfo;

import java.util.ArrayList;
import java.util.List;
/**
 * @author ckn
 */
@Slf4j
public class SearchUtil {
    private ISearchPlanService searchPlanService;
    private String pageTag;
    private Long planId;
    private List<WhereCondition> conditions;

    public SearchUtil(ISearchPlanService searchPlanService, String pageTag) {
        this.searchPlanService = searchPlanService;
        this.pageTag = pageTag;
        SearchPlanNew searchPlanNew = SearchPlanNewMapper.lambdaQuery().pageTag().equal(pageTag).def().equal(true).one();
        if (searchPlanNew == null) {
            throw new RuntimeException("初始化失败!");
        }
        this.planId = searchPlanNew.getId();
        this.conditions = new ArrayList<>();
    }

    public PageInfo query(){
        SearchQueryDTO searchQueryDTO=new SearchQueryDTO();
        searchQueryDTO.setPageTag(pageTag);
        searchQueryDTO.setSearchPlanId(planId);
        if(CollUtil.isEmpty(conditions)){
            return searchPlanService.query(searchQueryDTO);
        }
        for (WhereCondition condition : conditions) {
            SearchQueryDTO.QueryInfo queryInfo=new SearchQueryDTO.QueryInfo();
            queryInfo.setSearchKey(condition.getColumnName());
            if(condition.getInner()){
                queryInfo.setSearchConfigInnerConditionId(condition.getLinkId());
                queryInfo.setAreInner(1);
            }else {
                queryInfo.setSearchConfigInfoId(condition.getLinkId());
                queryInfo.setAreInner(0);
            }
            queryInfo.setSearchCondition(condition.getExpression());
            queryInfo.setSearchGroup("default");
            queryInfo.setSearchValue(condition.getVal());
            queryInfo.setSearchGroupRelation("and");
        }
       return searchPlanService.query(searchQueryDTO);
    }

    public SearchUtil addWhereCondition(String searchKey,SearchTypeEnum searchTypeEnum,String val){
        SearchConfigInfo searchConfigInfo = SearchConfigInfoMapper.lambdaQuery()
                .pageTag().equal(this.pageTag)
                .bracketPrefix()
                .searchKey().equal(searchKey).or()
                .searchKey().like("/*alias" + StrUtil.SPACE + searchKey + "*/")
                .bracketSuffix()
                .one();
        if (searchConfigInfo == null) {
            log.info("addWhereCondition->searchKey:{}于数据库search_config_info 未找到",searchKey);
            return this;
        }
        this.conditions.add(new WhereCondition(searchConfigInfo.getId(),searchKey,val,searchTypeEnum.name(),false));
        return this;
    }

    public SearchUtil addWhereConditionInner(String searchKey,SearchTypeEnum searchTypeEnum,String val){
        SearchConfigInnerCondition configInnerCondition = SearchConfigInnerConditionMapper.lambdaQuery().searchKey().equal(searchKey).or()
                .pageTag().equal(this.pageTag)
                .bracketPrefix()
                .searchKey().equal(searchKey).or()
                .searchKey().like("/*alias" + StrUtil.SPACE + searchKey + "*/")
                .bracketSuffix()
                .one();
        if (configInnerCondition == null) {
            log.info("addWhereCondition->searchKey:{}于数据库search_config_inner_condition 未找到",searchKey);
            return this;
        }
        this.conditions.add(new WhereCondition(configInnerCondition.getId(),searchKey,val,searchTypeEnum.name(),true));
        return this;
    }

    @Data
    @AllArgsConstructor
    class WhereCondition {
        private Long linkId;
        private String columnName;
        private String val;
        private String expression;
        private Boolean inner;
    }

    enum SearchTypeEnum{
        Like,Equal,In,NotIn,GreaterOrEqual,Less,LessOrEqual
    }

}
