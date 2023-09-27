package org.ckn.sp.factory;

import io.netty.util.concurrent.FastThreadLocal;
import org.ckn.sp.fm.bean.SearchDatasource;
import org.ckn.sp.fm.bean.SearchDatasourceRelation;
import org.ckn.sp.fm.dao.SearchDatasourceMapper;
import org.ckn.sp.fm.dao.SearchDatasourceRelationMapper;
import org.ckn.sp.util.DataSourceUtil;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ckn
 * 数据源工厂
 */
public class DataSourceFactory {
    private static final FastThreadLocal<Map<String, DataSource>> dataSourceThreadLocal = new FastThreadLocal<>();

    public static DataSource buildDataSource(Long searchConfigId) {
        SearchDatasourceRelation datasourceRelation = SearchDatasourceRelationMapper.lambdaQuery().searchConfigId().equal(searchConfigId).one();
        if (datasourceRelation == null) {
            return null;
        }
        Long datasourceId = datasourceRelation.getSearchDatasourceId();
        SearchDatasource datasource = SearchDatasourceMapper.lambdaQuery().id().equal(datasourceId).one();
        if (datasource == null) {
            return null;
        }
        String key = datasource.getDatasourceDriver() + "-" + datasource.getDatasourceUrl() + "-" + datasource.getDatasourceUsername() + "-" + datasource.getDatasourcePassword();
        Map<String, DataSource> stringDataSourceMap = dataSourceThreadLocal.get();
        if (stringDataSourceMap == null) {
            stringDataSourceMap = new HashMap<>();
            DataSource dataSource = buildDataSource(
                    datasource.getDatasourceDriver(),
                    datasource.getDatasourceUrl(),
                    datasource.getDatasourceUsername(),
                    datasource.getDatasourcePassword());
            stringDataSourceMap.put(key, dataSource);
            return dataSource;
        } else {
            DataSource dataSource = stringDataSourceMap.get(key);
            if (dataSource == null) {
                DataSource dataSource_new = buildDataSource(
                        datasource.getDatasourceDriver(),
                        datasource.getDatasourceUrl(),
                        datasource.getDatasourceUsername(),
                        datasource.getDatasourcePassword());
                stringDataSourceMap.put(key, dataSource_new);
                return dataSource_new;
            } else {
                return dataSource;
            }
        }
    }

    public static DataSource buildDataSource(String driverClass, String url, String userName, String passWord) {
        return DataSourceUtil.getDataSource(driverClass, url, userName, passWord);
    }

    public static DataSource buildDataSource(SearchDatasource datasource) {
        return buildDataSource(datasource.getDatasourceDriver(), datasource.getDatasourceUrl(), datasource.getDatasourceUsername(), datasource.getDatasourcePassword());
    }
}
