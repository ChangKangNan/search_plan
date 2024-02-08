package org.ckn.sp.datasource;

import cn.ft.ckn.fastmapper.aspect.CustomActuatorAspect;
import cn.ft.ckn.fastmapper.aspect.SqlActuatorAspect;
import cn.ft.ckn.fastmapper.config.FastMapperConfig;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ckn
 * @date 2023/11/28
 */
@Configuration
public class DruidConfig {
    static {
        FastMapperConfig.isOpenSQLPrint = true;
        FastMapperConfig.addMapperExpander(SqlActuatorAspect.class);
        FastMapperConfig.addMapperExpander(CustomActuatorAspect.class);
        FastMapperConfig.setTimeAuto(true,true);
        FastMapperConfig.setTimeColumn("create_time","update_time");
        FastMapperConfig.setDeleted(true,"deleted",0,1);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getMyDruidDataSource() {
        return new DruidDataSource();
    }
}
