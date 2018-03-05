package com.jay.boot.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2018/3/2.
 */
@Configuration
@MapperScan(basePackages = "com.jay.boot.dao.read",sqlSessionTemplateRef = "readSqlSessionTemplate")
public class ReadSqlSessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(ReadSqlSessionFactory.class);

    @Autowired
    private ReadDataProperties readDataProperties;

    @Primary
    @Bean(name = "readDataSourceBean")
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(readDataProperties.getUrl());
        datasource.setUsername(readDataProperties.getUsername());
        datasource.setPassword(readDataProperties.getPassword());
        datasource.setDriverClassName(readDataProperties.getDriverClassName());
        datasource.setInitialSize(readDataProperties.getInitialSize());
        datasource.setMinIdle(readDataProperties.getMinIdle());
        datasource.setMaxActive(readDataProperties.getMaxActive());
        datasource.setMaxWait(readDataProperties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(readDataProperties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(readDataProperties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(readDataProperties.getValidationQuery());
        datasource.setTestWhileIdle(readDataProperties.getTestWhileIdle());
        datasource.setTestOnBorrow(readDataProperties.getTestOnBorrow());
        datasource.setTestOnReturn(readDataProperties.getTestOnReturn());
        try {
            datasource.setFilters(readDataProperties.getFilters());
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        return datasource;
    }

    @Primary
    @Bean(name = "readSqlSessionFactoryBean")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("readDataSourceBean") DataSource dataSource,ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // sessionFactory.setPlugins(new Interceptor[]{new PageInterceptor()});
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath*:mapper/read/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean(name = "readSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("readSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
