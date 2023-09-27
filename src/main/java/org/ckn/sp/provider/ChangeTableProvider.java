package org.ckn.sp.provider;

import cn.ft.ckn.fastmapper.config.ApiException;
import cn.ft.ckn.fastmapper.util.SQLRunnerUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.ckn.sp.dto.SearchQueryDTO;
import org.ckn.sp.factory.DataSourceFactory;
import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.fm.bean.SearchConfigInfo;
import org.ckn.sp.fm.dao.SearchConfigInfoMapper;
import org.ckn.sp.fm.dao.SearchConfigMapper;
import org.ckn.sp.service.impl.SearchPlanServiceImpl;
import org.ckn.sp.util.RegxUtil;
import org.ckn.sp.util.SqlUtil;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ckn
 */
public class ChangeTableProvider {

    public static void handle(SearchQueryDTO searchQueryDTO) {
        String pageTag = searchQueryDTO.getPageTag();
        SearchConfig searchConfig = SearchConfigMapper.lambdaQuery().pageTag().equal(pageTag).one();
        List<SearchConfigInfo> configInfos = SearchConfigInfoMapper.lambdaQuery().pageTag().equal(pageTag).list();
        List<SearchConfigInfo> influences = configInfos.stream().filter(SearchConfigInfo::getChangeTb).collect(Collectors.toList());
        String error_msg = "";
        Set<String> changeTableNames = new HashSet<>();
        if(CollUtil.isEmpty(influences)){
            return;
        }
        List<SearchQueryDTO.QueryInfo> queryInfoList = searchQueryDTO.getQueryInfoList();
        if(CollUtil.isEmpty(queryInfoList)){
            return;
        }
        String packageSql = SearchPlanServiceImpl.QUERY_SQL_LOCAL.get();
        for (SearchQueryDTO.QueryInfo queryInfo : queryInfoList) {
            Long searchConfigInfoId = queryInfo.getSearchConfigInfoId();
            packageSql = SqlUtil.getChangeSql(searchConfigInfoId, queryInfo, packageSql, influences, changeTableNames);
            //获取error_tip
            SearchConfigInfo searchConfigInfo = SearchConfigInfoMapper.lambdaQuery().id().equal(searchConfigInfoId).one();
            List<String> changeTable = RegxUtil.getChangeTable(searchConfig.getSearchSql());
            if (CollUtil.isNotEmpty(changeTable)) {
                String searchKey = searchConfigInfo.getSearchKey();
                for (String value : changeTable) {
                    String trim = value.split(StrUtil.SPACE)[0].trim();
                    if (StrUtil.contains(searchKey, "/*alias " + trim + "*/") || StrUtil.equals(searchKey, trim)) {
                        error_msg = Arrays.stream(value.split(StrUtil.SPACE))
                                .filter(m -> m.contains("="))
                                .collect(Collectors.toMap(k -> k.split("=")[0], v -> v.split("=")[1], (oldValue, newValue) -> newValue)).get("error_tip");
                    }
                }
            }
        }
        DataSource dataSource = DataSourceFactory.buildDataSource(searchConfig.getId());
        if(dataSource == null){
            throw new ApiException(error_msg);
        }
        boolean isExist = true;
        if (CollUtil.isNotEmpty(changeTableNames) && StrUtil.isNotBlank(error_msg)) {
            for (String changeTableName : changeTableNames) {
                String existSQL = "SELECT * FROM information_schema.TABLES where TABLE_NAME='" + changeTableName + "'";
                SQLRunnerUtil build = SQLRunnerUtil.build();
                build.setSalveDataSource(dataSource);
                NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
                try {
                    List<Map<String, Object>> mapList = namedParameterJdbcTemplate.queryForList(existSQL,new HashMap<>());
                    if(CollUtil.isEmpty(mapList)){
                        isExist =false;
                    }
                }catch (Exception e){
                    isExist =false;
                }
            }
        }
        SearchPlanServiceImpl.QUERY_SQL_LOCAL.set(packageSql);
    }
}
