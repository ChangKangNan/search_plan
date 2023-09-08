package org.ckn.sp.factory;

import org.ckn.sp.fm.bean.SearchDatasource;
import org.ckn.sp.fm.bean.SearchDatasourceRelation;
import org.ckn.sp.fm.dao.SearchDatasourceMapper;
import org.ckn.sp.fm.dao.SearchDatasourceRelationMapper;
import org.ckn.sp.util.DataSourceUtil;

import javax.sql.DataSource;

/**
 * @author ckn
 * 数据源工厂
 */
public class DataSourceFactory {

    public static DataSource buildDataSource(String driverClass, String url, String userName, String passWord) {
        return DataSourceUtil.getDataSource(driverClass, url, userName, passWord);
    }

    public static DataSource buildDataSource(SearchDatasource datasource) {
        return buildDataSource(datasource.getDatasourceDriver(), datasource.getDatasourceUrl(), datasource.getDatasourceUsername(), datasource.getDatasourcePassword());
    }

    public static DataSource buildDataSource(Long searchConfigId) {
        SearchDatasourceRelation datasourceRelation = SearchDatasourceRelationMapper.lambdaQuery().searchConfigId().equal(searchConfigId).one();
        if (datasourceRelation == null) {
            return null;
        }
        SearchDatasource searchDatasource = SearchDatasourceMapper.lambdaQuery().id().equal(datasourceRelation.getSearchDatasourceId()).one();
        if (searchDatasource == null) {
            return null;
        }
        return buildDataSource(searchDatasource);
    }
}
