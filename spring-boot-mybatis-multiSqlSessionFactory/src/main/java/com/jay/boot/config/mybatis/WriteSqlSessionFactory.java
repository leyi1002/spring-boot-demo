package com.jay.boot.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/3/2.
 */
@Configuration
@MapperScan(basePackages = "com.jay.boot.dao.write",sqlSessionTemplateRef = "writeSqlSessionTemplate")
public class WriteSqlSessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(WriteSqlSessionFactory.class);

    @Autowired
    private WriteDataProperties writeDataProperties;

    @Bean(name = "writeDataSourceBean")
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(writeDataProperties.getUrl());
        datasource.setUsername(writeDataProperties.getUsername());
        datasource.setPassword(writeDataProperties.getPassword());
        datasource.setDriverClassName(writeDataProperties.getDriverClassName());
        datasource.setInitialSize(writeDataProperties.getInitialSize());
        datasource.setMinIdle(writeDataProperties.getMinIdle());
        datasource.setMaxActive(writeDataProperties.getMaxActive());
        datasource.setMaxWait(writeDataProperties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(writeDataProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(writeDataProperties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(writeDataProperties.getValidationQuery());
        datasource.setTestWhileIdle(writeDataProperties.getTestWhileIdle());
        datasource.setTestOnBorrow(writeDataProperties.getTestOnBorrow());
        datasource.setTestOnReturn(writeDataProperties.getTestOnReturn());
        try {
            datasource.setFilters(writeDataProperties.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return datasource;
    }


    @Bean(name = "writeSqlSessionFactoryBean")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("writeDataSourceBean")DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // sessionFactory.setPlugins(new Interceptor[]{new PageInterceptor()});
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath*:mapper/write/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean(name = "writeSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("writeSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
