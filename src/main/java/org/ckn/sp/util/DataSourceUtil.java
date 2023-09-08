package org.ckn.sp.util;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

public class DataSourceUtil {
    public static DataSource getDataSource(String driverClassName, String url, String userName, String password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        try {
            druidDataSource.setDriverClassName(driverClassName);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(userName);
            druidDataSource.setPassword(password);
            druidDataSource.setLoginTimeout(2);
            druidDataSource.setConnectionErrorRetryAttempts(8);
            druidDataSource.setBreakAfterAcquireFailure(true);
            return druidDataSource;
        }catch (Exception e){
            return null;
        }
    }
}