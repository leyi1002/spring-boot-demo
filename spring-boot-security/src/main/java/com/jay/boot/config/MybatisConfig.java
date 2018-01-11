package com.jay.boot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2017/12/26.
 */

/**
 * 两种方式：
 * ①mybatis：加上@MapperScan扫描包则可以不用Dao实线类（当前采用）
 * ②ibatis：注释掉@MapperScan，则需要提供Dao接口的实现类
 */
@Configuration
@MapperScan(basePackages = "com.jay.boot.dao")
public class MybatisConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // sessionFactory.setPlugins(new Interceptor[]{new PageInterceptor()});
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath*:mapper/*.xml"));
        return sessionFactory.getObject();
    }



}
