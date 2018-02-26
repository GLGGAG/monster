package com.orange.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author:GLGGAG
 * @Date:2017/10/18
 * @Description:sql数据源配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.orange.mapper")
public class SqlDataSource {

    /** mybaits mapper xml搜索路径 */
    private final static String MAPPER_LOCATIONS ="classpath:/mybatis/*.xml";

    /**
     * http://docs.spring.io/spring-boot/docs/1.3.6.RELEASE/reference/htmlsingle/#howto-two-datasources<br/>
     * &#064;EnableTransactionManagement 注解等同于: &lt;tx:annotation-driven /&gt;
     *
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
     * @see org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
     */
    @Bean
    @ConfigurationProperties(prefix = "database")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        // 装载 xml 实现
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATIONS));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate", destroyMethod = "clearCache")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
