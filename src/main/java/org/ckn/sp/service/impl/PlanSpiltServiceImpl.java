package org.ckn.sp.service.impl;

import cn.ft.ckn.fastmapper.config.ApiException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import org.ckn.sp.annotation.LoadSplitConfig;
import org.ckn.sp.fm.bean.SearchConfig;
import org.ckn.sp.fm.bean.SearchDatasource;
import org.ckn.sp.fm.bean.SearchDatasourceRelation;
import org.ckn.sp.fm.dao.SearchConfigMapper;
import org.ckn.sp.fm.dao.SearchDatasourceMapper;
import org.ckn.sp.fm.dao.SearchDatasourceRelationMapper;
import org.ckn.sp.service.IPlanSpiltService;
import org.ckn.sp.strategy.GenerateInsertStrategy;
import org.ckn.sp.strategy.GenerateUpdateStrategy;
import org.ckn.sp.strategy.api.IGenerateStrategy;
import org.ckn.sp.util.ErrorUtil;
import org.ckn.sp.util.RegxUtil;
import org.ckn.sp.util.SqlUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.ckn.sp.util.PinyinUtil.getPinyin;

/**
 * @author ckn
 */
@Service
public class PlanSpiltServiceImpl implements IPlanSpiltService {

    private Map<String, IGenerateStrategy> handleList =new HashMap<String, IGenerateStrategy>(){{
        put("INSERT",new GenerateInsertStrategy());
        put("UPDATE",new GenerateUpdateStrategy());
    }};

    @Override
    @Transactional
    @LoadSplitConfig
    public void split(String sqlPath) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource resource = resourceResolver.getResource("plan/" + sqlPath);
        try {
            String hex = MD5.create().digestHex16(resource.getInputStream());
            String sql = IoUtil.read(resource.getInputStream(), StandardCharsets.UTF_8);
            sql = SqlUtil.replaceLineSeparator(sql);
            String fileName = resource.getFilename();
            String chineseName = StrUtil.subBefore(fileName, ".", true);
            String pageTag = StrUtil.removeSuffix(getPinyin(Objects.requireNonNull(StrUtil.removeSuffix(chineseName, "_"))).trim(), "_");
            SearchConfig searchConfig = SearchConfigMapper.lambdaQuery().pageTag().equal(pageTag).one();
            String useOrg = RegxUtil.getUseOrg(sql);
            String useOrgs = RegxUtil.getUseOrgs(sql);
            String dataSourceName = RegxUtil.getDataSourceName(sql);
            SearchDatasource searchDatasource = SearchDatasourceMapper.lambdaQuery().datasourceName().equal(dataSourceName).one();
            ErrorUtil.isEmpty(searchDatasource, "数据源配置信息不存在!");
            Long searchConfigId;
            if (ObjectUtil.isEmpty(searchConfig)) {
                //插入
                SearchConfig search = new SearchConfig();
                search.setSearchSql(sql);
                search.setPageTag(pageTag);
                search.setHasCode(hex);
                search.setConfigName(chineseName);
                Optional.ofNullable(useOrg).ifPresent(search::setUseOrgLimitTableAlias);
                Optional.ofNullable(useOrgs).ifPresent(search::setUseOrgsLimitTableAlias);
                SearchConfigMapper.lambdaInsert().insert(search);
                searchConfigId = search.getId();
                IGenerateStrategy strategy = handleList.get("INSERT");
                strategy.handle(searchConfig);
                //生成config_info
                //生成查询方案配置组件
                ////默认方案生成
            } else {
                //更新
                searchConfigId = searchConfig.getId();
                //校验hex
                String hasCode = searchConfig.getHasCode();
                IGenerateStrategy strategy = handleList.get("UPDATE");
                strategy.handle(searchConfig);
                //更新主表
                //更新config_info
                //重新生成查询方案配置组件
                //默认方案与用户方案重新生成
            }
            //数据源配置统一更新
            handleDataSource(dataSourceName, searchConfigId);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    public void handleDataSource(String dataSourceName, Long searchConfigId) {
        if (StrUtil.isNotEmpty(dataSourceName)) {
            return;
        }
        SearchDatasource searchDatasource = SearchDatasourceMapper.lambdaQuery().datasourceName().equal(dataSourceName).one();
        SearchDatasourceRelation datasourceRelation = new SearchDatasourceRelation();
        datasourceRelation.setSearchDatasourceId(searchDatasource.getId());
        SearchDatasourceRelation relation = SearchDatasourceRelationMapper.lambdaQuery().searchConfigId().equal(searchConfigId).one();
        if (relation != null) {
            Long searchDatasourceId = relation.getSearchDatasourceId();
            if (!Objects.equals(searchDatasourceId, searchDatasource.getId())) {
                SearchDatasourceRelationMapper.lambdaUpdate().id().equal(relation.getId()).update(datasourceRelation);
            }
        } else {
            datasourceRelation.setSearchConfigId(searchConfigId);
            SearchDatasourceRelationMapper.lambdaInsert().insert(datasourceRelation);
        }
    }
}