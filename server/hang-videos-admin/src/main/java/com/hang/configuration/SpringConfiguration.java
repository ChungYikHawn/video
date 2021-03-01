package com.hang.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"com.hang"})
@EnableTransactionManagement
@MapperScan(basePackages = "cn.hang.mapper")
public class SpringConfiguration {

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager dataSourceTransactionManager(DataSource dataSource) {

        return new DataSourceTransactionManager(dataSource);

    }

    @Bean
    public Resource resource() throws IOException {
        Resource resource=new ClassPathResource("mybatis/mybatis-config.xml");

        return resource;
    }

    @Bean
    public SqlSessionFactory getSqlSessionFactory(DataSource dataSource,Resource resource){
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setConfigLocation(resource);
            return sqlSessionFactoryBean.getObject();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
