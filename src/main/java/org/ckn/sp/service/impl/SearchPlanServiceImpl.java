package org.ckn.sp.service.impl;

import cn.ft.ckn.fastmapper.config.ApiException;
import cn.ft.ckn.fastmapper.util.SQLUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import io.netty.util.concurrent.FastThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.ckn.sp.dto.*;
import org.ckn.sp.factory.DataSourceFactory;
import org.ckn.sp.fm.bean.*;
import org.ckn.sp.fm.dao.*;
import org.ckn.sp.provider.*;
import org.ckn.sp.service.ISearchPlanService;
import org.ckn.sp.util.CopyUtil;
import org.ckn.sp.util.RegxUtil;
import org.ckn.sp.util.SQLUtils;
import org.ckn.sp.util.SqlUtil;
import org.ckn.sp.vo.PageInfo;
import org.ckn.sp.vo.SearchPageConfigVO;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.ckn.sp.constants.StrConstants.*;

/**
 * @author ckn
 */
@Service
@Slf4j
public class SearchPlanServiceImpl implements ISearchPlanService {
    public static FastThreadLocal<String> QUERY_SQL_LOCAL = new FastThreadLocal<>();
    public static FastThreadLocal<Map<String,Object>> QUERY_SQL_PARAM_LOCAL = new FastThreadLocal<>();


    @Override
    public void savePlan(SearchPlanDTO searchSavePlanDTO) {
        Long planId = searchSavePlanDTO.getPlanId();
//        if (!searchSavePlanDTO.getIsDefaultChange() && (planId != null && SearchPlanNewMapper.lambdaQuery().id().equal(planId).one() != null) && SearchPlanNewMapper.lambdaQuery().id().equal(planId).one().getDef()) {
//            throw new RuntimeException("默认方案不允许更新!");
//        }
        if (planId != null) {
            String planName = searchSavePlanDTO.getPlanName();
            if (StrUtil.isBlank(planName)) {
                SearchPlanNew searchPlanNew = SearchPlanNewMapper.lambdaQuery().id().equal(planId).one();
                if (searchPlanNew == null) {
                    throw new RuntimeException("该方案不存在!");
                }
                searchSavePlanDTO.setPlanName(searchPlanNew.getPlanName());
            }
            List<SearchPlanDTO.SearchColumn> searchColumns = searchSavePlanDTO.getSearchColumns();
            if (CollUtil.isEmpty(searchColumns)) {
                SearchPlanNew searchPlanNew = BeanUtil.copyProperties(searchSavePlanDTO, SearchPlanNew.class);
                searchPlanNew.setId(planId);
                SearchPlanNewMapper.lambdaUpdate().id().equal(planId).update(searchPlanNew);
            }
            List<SearchPlanDTO.QueryInfo> queryInfoList = searchSavePlanDTO.getQueryInfoList();
            if (CollUtil.isNotEmpty(queryInfoList)) {
                SearchPlanNewConditionMapper.lambdaDelete().searchPlanId().equal(planId).closeDeletedProtect().delete();
            }
            SearchPlanNewShowMapper.lambdaDelete().searchPlanId().equal(planId).delete();
        }
        List<SearchPlanDTO.SearchColumn> searchColumns = searchSavePlanDTO.getSearchColumns();
        String pageTag = searchSavePlanDTO.getPageTag();
        List<String> columnNames = new ArrayList<>();
        List<String> columnHidden = new ArrayList<>();
        if (CollUtil.isNotEmpty(searchColumns)) {
            List<String> hidden = searchColumns.stream().filter(t -> t.getStatus().equals("hidden")).map(SearchPlanDTO.SearchColumn::getColumnName).collect(Collectors.toList());
            columnHidden = hidden;
            Set<String> hiddenSet = getHiddenSet(pageTag);
            if (CollUtil.isNotEmpty(hiddenSet)) {
                if (CollUtil.isEmpty(hidden)) {
                    columnNames.addAll(hiddenSet);
                } else {
                    List<String> subtractToList = CollUtil.subtractToList(hiddenSet, hidden);
                    if (CollUtil.isNotEmpty(subtractToList)) {
                        columnNames.addAll(subtractToList);
                    }
                }
            }
            for (SearchPlanDTO.SearchColumn searchColumn : searchColumns) {
                if (StrUtil.equalsAny(searchColumn.getStatus(), "on", "hidden")) {
                    boolean is_Exist = false;
                    for (String columnName : columnNames) {
                        if (searchColumn.getColumnName().equals(columnName)) {
                            is_Exist = true;
                            break;
                        }
                    }
                    if (is_Exist) {
                        continue;
                    }
                    columnNames.add(searchColumn.getColumnName());
                }
            }
        }
        columnNames = columnNames.stream().distinct().collect(Collectors.toList());
        String packageSql = SQLUtils.packageSql(pageTag, columnNames, columnHidden);
        SearchPlanNew searchPlanNew = BeanUtil.copyProperties(searchSavePlanDTO, SearchPlanNew.class);
        searchPlanNew.setId(searchSavePlanDTO.getPlanId());
        searchPlanNew.setSearchSql(packageSql.trim());

        if(planId == null){
            searchPlanNew.setId(null);
            SearchPlanNewMapper.lambdaInsert().insert(searchPlanNew);
        }else {
            SearchPlanNewMapper.lambdaUpdate().id().equal(planId).update(searchPlanNew);
        }

        Long searchPlanNewId = searchPlanNew.getId();
        if (CollUtil.isNotEmpty(searchColumns)) {
            for (SearchPlanDTO.SearchColumn searchColumn : searchColumns) {
                SearchPlanNewShow searchPlanNewShow = new SearchPlanNewShow();
                searchPlanNewShow.setSearchPlanId(searchPlanNewId);
                searchPlanNewShow.setUserAccount(searchPlanNew.getUserAccount());
                searchPlanNewShow.setSearchColumn(searchColumn.getColumnName());
                searchPlanNewShow.setShowStatus(searchColumn.getStatus());
                searchPlanNewShow.setPageTag(searchSavePlanDTO.getPageTag());
                SearchPlanNewShowMapper.lambdaInsert().insert(searchPlanNewShow);
            }
        }
        List<SearchPlanDTO.QueryInfo> queryInfoList = searchSavePlanDTO.getQueryInfoList();
        if (CollUtil.isNotEmpty(queryInfoList)) {
            for (SearchPlanDTO.QueryInfo queryInfo : queryInfoList) {
                SearchPlanNewCondition searchPlanNewCondition = BeanUtil.copyProperties(queryInfo, SearchPlanNewCondition.class);
                searchPlanNewCondition.setUserAccount(searchPlanNew.getUserAccount());
                searchPlanNewCondition.setSearchPlanId(searchPlanNewId);
                searchPlanNewCondition.setPageTag(searchSavePlanDTO.getPageTag());
                searchPlanNewCondition.setSearchColumn(queryInfo.getSearchKey());
                SearchPlanNewConditionMapper.lambdaInsert().insert(searchPlanNewCondition);
            }
        }
        searchSavePlanDTO.setPlanId(searchPlanNewId);
    }

    @Override
    public void generateDefault(String pagTag) {
        SearchPlanNew planNew = SearchPlanNewMapper.lambdaQuery().pageTag().equal(pagTag).def().equal(true).one();
        SearchPlanDTO searchPlanDTO=new SearchPlanDTO();
        if (planNew != null) {
            searchPlanDTO.setPlanId(planNew.getId());
        }
        searchPlanDTO.setPageTag(pagTag);
        searchPlanDTO.setPlanName("默认方案");
        searchPlanDTO.setDef(true);
        searchPlanDTO.setIsDefaultChange(true);
        List<SearchTableColumn> tableColumns = SearchTableColumnMapper.lambdaQuery().pageTag().equal(pagTag).list();
        List<SearchPlanDTO.SearchColumn> searchColumns = new ArrayList<>();
        for (SearchTableColumn tableColumn : tableColumns) {
            String searchTableColumn = tableColumn.getSearchTableColumn();
            searchColumns.add(new SearchPlanDTO.SearchColumn() {{
                setColumnName(searchTableColumn);
                setStatus("on");
            }});
        }
        searchPlanDTO.setSearchColumns(searchColumns);
        savePlan(searchPlanDTO);
    }

    @Override
    public void generateUserOverride(String pageTag){
        List<SearchTableColumn> tableColumns = SearchTableColumnMapper.lambdaQuery().pageTag().equal(pageTag).list();
        if(CollUtil.isEmpty(tableColumns)){
            return;
        }
        List<SearchConfigInfo> configInfos = SearchConfigInfoMapper.lambdaQuery().pageTag().equal(pageTag).list();
        if(CollUtil.isEmpty(configInfos)){
            return;
        }
        List<String> allColumns = tableColumns.stream().map(SearchTableColumn::getSearchTableColumn).collect(Collectors.toList());
        List<SearchPlanNew> searchPlanNews = SearchPlanNewMapper.lambdaQuery().pageTag().equal(pageTag).def().equal(true).list();
        if(CollUtil.isEmpty(searchPlanNews)){
            return;
        }
        for (SearchPlanNew planNew : searchPlanNews) {
            Long planNewId = planNew.getId();
            List<SearchPlanNewShow> planNewShows = SearchPlanNewShowMapper.lambdaQuery().searchPlanId().equal(planNewId).list();
            if(CollUtil.isEmpty(planNewShows)){
                continue;
            }
            List<SearchPlanDTO.SearchColumn> searchColumns = new ArrayList<>();
            List<Long> shouldDelIDs = new ArrayList<>();
            for (SearchPlanNewShow planNewShow : planNewShows) {
                String newShowSearchColumn = planNewShow.getSearchColumn();
                if(!allColumns.contains(newShowSearchColumn.trim())){
                    shouldDelIDs.add(planNewShow.getId());
                }else {
                    searchColumns.add(new SearchPlanDTO.SearchColumn() {{
                        setColumnName(newShowSearchColumn);
                        setStatus(planNewShow.getShowStatus());
                    }});
                }
            }
            if(CollUtil.isNotEmpty(shouldDelIDs)){
                SearchPlanNewShowMapper.lambdaDelete().id().in(shouldDelIDs).closeDeletedProtect().delete();
            }
            SearchPlanDTO searchSavePlanDTO = new SearchPlanDTO();
            searchSavePlanDTO.setPlanId(planNew.getId());
            searchSavePlanDTO.setPageTag(pageTag);
            searchSavePlanDTO.setPlanName(planNew.getPlanName());
            searchSavePlanDTO.setUserAccount(planNew.getUserAccount());
            searchSavePlanDTO.setSearchColumns(searchColumns);
            searchSavePlanDTO.setDef(planNew.getDef());
            savePlan(searchSavePlanDTO);
        }
    }

    @Override
    public Map<String, String> getAllResource() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource[] resources;
        try {
            resources = resourceResolver.getResources("plan/*");
        } catch (IOException e) {
            throw new ApiException("获取资源文件报错!");
        }
        if(resources.length == 0){
           throw new ApiException("无可同步资源文件!");
        }
        return Arrays.stream(resources).collect(
                Collectors.toMap(k -> Objects.requireNonNull(k.getFilename()).substring(0, k.getFilename().lastIndexOf(".")),
                        org.springframework.core.io.Resource::getFilename));
    }

    @Override
    public PlanFilterDTO savePlanFilter(PlanFilterDTO planFilterDTO) {
        List<PlanFilterDTO.QueryInfo> queryInfoList = planFilterDTO.getQueryInfoList();
        if(CollUtil.isEmpty(queryInfoList)){
            return planFilterDTO;
        }
        SearchPlanNewConditionMapper.lambdaDelete().searchPlanId().equal(planFilterDTO.getPlanId()).closeDeletedProtect().delete();
        SearchPlanNew planNew = SearchPlanNewMapper.lambdaQuery().id().equal(planFilterDTO.getPlanId()).one();
        List<SearchPlanNewCondition> insertList=new ArrayList<>();
        for (PlanFilterDTO.QueryInfo queryInfo : queryInfoList) {
            SearchPlanNewCondition searchPlanNewCondition=new SearchPlanNewCondition();
            searchPlanNewCondition.setSearchColumn(queryInfo.getSearchKey());
            searchPlanNewCondition.setSearchConfigInfoId(queryInfo.getSearchConfigInfoId());
            searchPlanNewCondition.setUserAccount(planNew.getUserAccount());
            searchPlanNewCondition.setSearchPlanId(planNew.getId());
            searchPlanNewCondition.setPageTag(planNew.getPageTag());
            searchPlanNewCondition.setSearchValue(queryInfo.getSearchValue());
            searchPlanNewCondition.setSearchCondition(queryInfo.getSearchCondition());
            searchPlanNewCondition.setSearchGroup(queryInfo.getSearchGroup());
            searchPlanNewCondition.setSearchGroupRelation(queryInfo.getSearchGroupRelation());
            searchPlanNewCondition.setParentGroup(queryInfo.getParentGroup());
            searchPlanNewCondition.setParentGroupRelation(queryInfo.getParentGroupRelation());
            insertList.add(searchPlanNewCondition);
        }
        SearchPlanNewConditionMapper.lambdaInsert().insertBatch(insertList);
        return planFilterDTO;
    }

    @Override
    public PlanFilterDTO getPlanFilter(Long planId) {
        List<SearchPlanNewCondition> planNewConditions = SearchPlanNewConditionMapper.lambdaQuery().searchPlanId().equal(planId).list();
        PlanFilterDTO planFilterDTO = new PlanFilterDTO();
        planFilterDTO.setPlanId(planId);
        List<PlanFilterDTO.QueryInfo> queryInfoList = new ArrayList<>();
        int checkInInner = 0;
        if (CollUtil.isNotEmpty(planNewConditions)) {
            for (SearchPlanNewCondition newCondition : planNewConditions) {
                PlanFilterDTO.QueryInfo queryInfo = CopyUtil.copy(newCondition, PlanFilterDTO.QueryInfo.class);
                if(queryInfo == null){
                    continue;
                }
                queryInfo.setSearchKey(newCondition.getSearchColumn());
                Integer areInner = newCondition.getAreInner();
                if (areInner == 0) {
                    Long searchConfigInfoId = newCondition.getSearchConfigInfoId();
                    SearchConfigInfo configInfo = SearchConfigInfoMapper.lambdaQuery().id().equal(searchConfigInfoId).one();
                    if (configInfo.getHidden() == null || (!configInfo.getHidden())) {
                        queryInfo.setSearchConfigInfo(configInfo);
                    }
                } else {
                    checkInInner = 1;
                    Long innerConditionId = newCondition.getSearchConfigInnerConditionId();
                    SearchConfigInnerCondition innerCondition = SearchConfigInnerConditionMapper.lambdaQuery().id().equal(innerConditionId).one();
                    queryInfo.setAreInner(1);
                    queryInfo.setSearchConfigInnerCondition(innerCondition);
                }
                queryInfoList.add(queryInfo);
            }
        }
        if (checkInInner == 0) {
            SearchPlanNew planNew = SearchPlanNewMapper.lambdaQuery().id().equal(planId).one();
            List<SearchConfigInnerCondition> conditions = SearchConfigInnerConditionMapper.lambdaQuery().pageTag().equal(planNew.getPageTag()).list();
            if (CollUtil.isNotEmpty(conditions)) {
                for (SearchConfigInnerCondition condition : conditions) {
                    PlanFilterDTO.QueryInfo queryInfo = new PlanFilterDTO.QueryInfo();
                    String searchType = condition.getSearchType();
                    if (searchType.equals("date")) {
                        String limitSearchConditions = condition.getLimitSearchConditions();
                        JSONArray jsonArray = JSONUtil.parseArray(limitSearchConditions);
                        int size = jsonArray.size();
                        if (size == 1) {
                            queryInfo.setSearchCondition("Equal");
                        } else {
                            queryInfo.setSearchCondition("Range");
                        }
                    }
                    if (searchType.equals("input")) {
                        queryInfo.setSearchCondition("Like");
                    }
                    if (searchType.equals("number") || searchType.equals("select")) {
                        queryInfo.setSearchCondition("Equal");
                    }
                    queryInfo.setSearchKey(condition.getSearchKey());
                    queryInfo.setAreInner(1);
                    queryInfo.setSearchConfigInnerCondition(condition);
                    queryInfo.setSearchValue(condition.getDefaultValue());
                    queryInfoList.add(queryInfo);
                }
            }
        }
        queryInfoList = queryInfoList.stream().sorted(Comparator.comparing(PlanFilterDTO.QueryInfo::getAreInner).reversed()).collect(Collectors.toList());
        planFilterDTO.setQueryInfoList(queryInfoList);
        List<SearchPlanNewShow> searchPlanNewShows = SearchPlanNewShowMapper.lambdaQuery().searchPlanId().equal(planId).list();
        List<PlanFilterDTO.SearchColumn> columnList = new ArrayList<>();
        if (CollUtil.isNotEmpty(searchPlanNewShows)) {
            for (SearchPlanNewShow show : searchPlanNewShows) {
                PlanFilterDTO.SearchColumn s = new PlanFilterDTO.SearchColumn();
                s.setColumnName(show.getSearchColumn());
                s.setStatus(show.getShowStatus());
                columnList.add(s);
            }
        }
        planFilterDTO.setSearchColumns(columnList);
        return planFilterDTO;
    }

    @Override
    public List<Map<String, String>> getFilterColumnByPlanId(Long planId) {
        List<Map<String, String>> result = new ArrayList<>();
        List<SearchPlanNewCondition> planNewConditions = SearchPlanNewConditionMapper.lambdaQuery().searchPlanId().equal(planId).list();
        if(CollUtil.isEmpty(planNewConditions)){
            return result;
        }
        Map<Long, List<SearchPlanNewCondition>> collect = planNewConditions.stream().collect(Collectors.groupingBy(SearchPlanNewCondition::getSearchConfigInfoId));
        for (Long key : collect.keySet()) {
            SearchConfigInfo searchConfigInfo = SearchConfigInfoMapper.lambdaQuery().id().equal(key).one();
            String searchKey = searchConfigInfo.getSearchKey();
            String searchName = searchConfigInfo.getSearchName();
            boolean contains = searchKey.contains("/*alias");
            if (contains) {
                searchKey = RegxUtil.getAliasName(searchKey);
            }
            Map<String, String> stringMap = MapUtil.builder(searchName, searchKey).build();
            if (searchConfigInfo.getHidden() == null || (!searchConfigInfo.getHidden())) {
                result.add(stringMap);
            }
        }
        return result;
    }

    @Override
    public Boolean delPlan(Long planId) {
        SearchPlanNewMapper.lambdaDelete().id().equal(planId).delete();
        return true;
    }

    @Override
    public SearchPageConfigVO getUserPageSearchConfig(String pageTag) {
        SearchConfig searchConfig = SearchConfigMapper.lambdaQuery().pageTag().equal(pageTag).one();
        List<SearchConfigInfo> configInfos = SearchConfigInfoMapper.lambdaQuery().searchConfigId().equal(searchConfig.getId()).pageTag().equal(pageTag).list();
        if(CollUtil.isEmpty(configInfos)){
            return new SearchPageConfigVO();
        }
        for (SearchConfigInfo configInfo : configInfos) {
            if(configInfo.getHidden() != null){
                continue;
            }
            configInfo.setHidden(false);
        }
        List<SearchConfigInfoDTO> searchConfigInfoDTOS = CopyUtil.copy(configInfos, SearchConfigInfoDTO.class);
        if (CollUtil.isNotEmpty(searchConfigInfoDTOS)) {
            for (SearchConfigInfoDTO configInfoDTO : searchConfigInfoDTOS) {
                String searchKey = configInfoDTO.getSearchKey().trim();
                boolean contains = searchKey.contains("/*alias");
                if (contains) {
                    searchKey = RegxUtil.getAliasName(searchKey);
                } else {
                    if (searchKey.contains(".")) {
                        searchKey = searchKey.split("\\.")[1];
                    }
                }
                configInfoDTO.setPlanColumnName(searchKey);
            }
        }
        SearchPageConfigVO vo = new SearchPageConfigVO();
        vo.setConfigInfoList(searchConfigInfoDTOS);
        return vo;
    }

    @Override
    public PageInfo query(SearchQueryDTO queryDTO) {
        Long searchPlanId = queryDTO.getSearchPlanId();
        String pageTag = queryDTO.getPageTag();
        SearchConfig searchConfig = SearchConfigMapper.lambdaQuery().pageTag().equal(pageTag).one();
        SearchPlanNew planNew = SearchPlanNewMapper.lambdaQuery().id().equal(searchPlanId).one();
        QUERY_SQL_LOCAL.set(planNew.getSearchSql());
        handleSQL(queryDTO);
        DataSource dataSource = DataSourceFactory.buildDataSource(searchConfig.getId());
        return findPage(new StringBuilder(SqlUtil.sqlConversion(QUERY_SQL_LOCAL.get())),queryDTO.getPageNum(),queryDTO.getPageSize(),QUERY_SQL_PARAM_LOCAL.get() == null?new HashMap<>():QUERY_SQL_PARAM_LOCAL.get(),dataSource);
    }

    @Override
    public PageInfo querySum(SearchQueryDTO queryDTO) {
        Long searchPlanId = queryDTO.getSearchPlanId();
        String pageTag = queryDTO.getPageTag();
        SearchConfig searchConfig = SearchConfigMapper.lambdaQuery().pageTag().equal(pageTag).one();
        SearchPlanNew planNew = SearchPlanNewMapper.lambdaQuery().id().equal(searchPlanId).one();
        QUERY_SQL_LOCAL.set(planNew.getSearchSql());
        handleSQL(queryDTO);
        DataSource dataSource = DataSourceFactory.buildDataSource(searchConfig.getId());
        String sql = SqlUtil.sqlConversion(QUERY_SQL_LOCAL.get());

        int from = sql.toLowerCase().indexOf("from");
        String fromSql = sql.substring(0, from).trim().substring(6).trim();
        List<SearchTableColumn> searchTableColumns = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\s+as\\s+(.*)", Pattern.CASE_INSENSITIVE);
        String[] lines = fromSql.split(System.lineSeparator());
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                SearchTableColumn searchTableColumn = new SearchTableColumn();
                String trim = matcher.group(1).trim();
                String replace = trim.replace(StrUtil.COMMA, "");
                searchTableColumn.setSearchTableColumn(replace);
                searchTableColumns.add(searchTableColumn);
            }
        }
        String sumSQL = packSumSQL(sql, searchTableColumns);
        return findPage(new StringBuilder(sumSQL),queryDTO.getPageNum(),queryDTO.getPageSize(),QUERY_SQL_PARAM_LOCAL.get(),dataSource);
    }

    @Override
    public List<UserPlanDTO> queryPlan(UserPlanDTO userPlanDTO) {
        String pageTag = userPlanDTO.getPageTag();
        List<SearchPlanNew> planNews = SearchPlanNewMapper.lambdaQuery().pageTag().equal(pageTag).list();
        if(CollUtil.isEmpty(planNews)){
            return new ArrayList<>();
        }
        List<UserPlanDTO> userPlanDTOS = new ArrayList<>();
        String username = userPlanDTO.getUserName();
        for (SearchPlanNew searchPlanNew : planNews) {
            String userAccount = searchPlanNew.getUserAccount();
            if (StrUtil.contains(userAccount, username) || searchPlanNew.getDef()) {
                UserPlanDTO user = new UserPlanDTO();
                user.setPlanName(searchPlanNew.getPlanName());
                user.setId(searchPlanNew.getId());
                user.setPageTag(pageTag);
                user.setIsDef(searchPlanNew.getDef());
                userPlanDTOS.add(user);
            }
        }
        return userPlanDTOS;
    }


    public static String packSumSQL(String sql,List<SearchTableColumn> columns){
        StringBuilder sumSqlBuilder=new StringBuilder();
        sumSqlBuilder.append(SELECT);
        sumSqlBuilder.append(StrUtil.SPACE);
        String columnStr = columns.stream().map(t -> packColum(t.getSearchTableColumn())).collect(Collectors.joining(StrUtil.COMMA));
        sumSqlBuilder.append(columnStr);
        sumSqlBuilder.append(System.lineSeparator());
        sumSqlBuilder.append(FROM);
        sumSqlBuilder.append("(");
        sumSqlBuilder.append(sql);
        sumSqlBuilder.append(")");
        sumSqlBuilder.append(StrUtil.SPACE);
        sumSqlBuilder.append("sum_table");
        sumSqlBuilder.append(StrUtil.SPACE);
        sumSqlBuilder.append(WHERE);
        sumSqlBuilder.append(StrUtil.SPACE);
        sumSqlBuilder.append("true");
        return sumSqlBuilder.toString();
    }
    private static String packColum(String column){
        return "SUM(if("+column+" is null,0,"+column+"))"+AS+column;
    }

    public PageInfo<Map<String,Object>> findPage(StringBuilder sql, Integer pageNumber, Integer pageSize, Map<String ,Object> params, DataSource dataSource) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        String countSQL = "SELECT count(*) from(" + sql +
                ")query_count";
        if (pageNumber != null && pageSize != null) {
            sql.append(System.lineSeparator());
            sql.append("LIMIT");
            sql.append(StrUtil.SPACE);
            int pageNum = pageNumber - 1;
            sql.append(pageNum*pageSize);
            sql.append(StrUtil.C_COMMA);
            sql.append(pageSize);
        }
        Integer totalCount = 0;
        try {
            List<Map<String, Object>> mapList = jdbcTemplate.query(sql.toString(),params, (rs, rowNum) -> {
                final ResultSetMetaData md = rs.getMetaData();
                final int columnCount = md.getColumnCount();
                final ResultSetMetaData metaData = rs.getMetaData();
                final Map<String, Object> res = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    final String columnName = md.getColumnLabel(i);
                    final String columnTypeName = metaData.getColumnTypeName(i);
                    res.put(columnName, "VARBINARY".equals(columnTypeName) ? rs.getString(i) : rs.getObject(i));
                }
                return res;
            });
            totalCount = pageSize != null && pageNumber != null && mapList.size() < pageSize ?
                    ((pageNumber - 1) * pageSize + mapList.size()) :
                    jdbcTemplate.queryForObject(countSQL, params, Integer.class);
            PageInfo<Map<String, Object>> mapPageInfo = new PageInfo<>(mapList, pageNumber, pageSize, totalCount);
                if(mapPageInfo.getTotal() <=1000){
                    SQLUtil.print(SQLUtil.printSql(sql.toString(),params)
                            , SQLUtil.printResult(JSONUtil.toJsonStr(mapPageInfo)));
                }else {
                    SQLUtil.print(SQLUtil.printSql(sql.toString(),params)
                            , SQLUtil.printResult("总记录数:"+mapPageInfo.getTotal()));
                }
            return mapPageInfo;
        }catch (Exception e){
                try {
                    SQLUtil.print(SQLUtil.printSql(sql.toString(),params)
                            , SQLUtil.printResult(""));
                }catch (Exception e1){
                    log.warn("未知记录数!", e);
                }
            log.warn("sql 执行错误", e);
            return new PageInfo<>(new ArrayList<>(),pageNumber,pageSize,totalCount);
        }
    }

    public void handleSQL(SearchQueryDTO queryDTO){
        PreOptimizationProvider.handle(queryDTO);
        OptimizationProvider.handle(queryDTO);
        InnerConditionProvider.handle(queryDTO);
        ChangeTableProvider.handle(queryDTO);
        SearchWhereProvider.handle(queryDTO.getQueryInfoList());
        SearchOrderByProvider.handle(queryDTO);
    }

    public Set<String> getHiddenSet(String pageTag) {
        List<SearchConfigInfo> configInfos = SearchConfigInfoMapper.lambdaQuery().pageTag().equal(pageTag).hidden().equal(true).list();
        Set<String> hiddenSet = new HashSet<>();
        if (CollUtil.isEmpty(configInfos)) {
            return hiddenSet;
        }
        for (SearchConfigInfo info : configInfos) {
            String searchKey = info.getSearchKey().trim();
            hiddenSet.add(searchKey.replaceAll("(.*)/\\*\\s*alias\\s*(.+)\\s*\\*/", "$2").trim());
        }
        return hiddenSet;
    }


}
