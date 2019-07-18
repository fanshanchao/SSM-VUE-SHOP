package test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@Configuration
//不扫描Controller注解的bean
@ComponentScan(basePackages = {"fsc.site.dao"},excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value={Controller.class})})
@PropertySource(value = {"classpath:dataSource.properties"})//加载数据库配置文件
@EnableTransactionManagement
@MapperScan("fsc.site.dao")
public class SpringConfig {
    private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);
    @Autowired
    private Environment env;
//    @Bean //这个bean用来加载配置文件
//    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
    /**
     * 配置mysql数据源
     * @return
     */
    @Bean(value = "dataSource",destroyMethod = "close")
    public BasicDataSource getDataSource(){
        logger.info("加载mysql数据库");
        //创建dbcp数据源
        BasicDataSource basicDataSource = new BasicDataSource();
        //设置数据库的相关属性
        basicDataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        basicDataSource.setUrl(env.getProperty("jdbc.url"));
        basicDataSource.setUsername(env.getProperty("jdbc.username"));
        basicDataSource.setPassword(env.getProperty("jdbc.password"));
        basicDataSource.setMaxTotal(Integer.parseInt(env.getProperty("jdbc.maxtotal")));
        basicDataSource.setMinIdle(Integer.parseInt(env.getProperty("jdbc.minidle")));
        basicDataSource.setMaxIdle(Integer.parseInt(env.getProperty("jdbc.maxidle")));
        return basicDataSource;
    }

    /**
     * 开启事务 并且注入数据源
     * @return
     */
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(){
        return new DataSourceTransactionManager(getDataSource());
    }

    /**
     *整合mybatis 创建SqlSessionFactoryBean对象
     * @return
     */
    @Bean
    public SqlSessionFactory getSqlSessionFactory()  {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(getDataSource());
        //设置配置mybatis全局配置文件
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(env.getProperty("mybatis.configLocation")));
        //设置mybatis 所有的mapper文件
        try {
            System.out.println(env.getProperty("mybatis.mapperLocations"));
            //sqlSessionFactoryBean.setTypeAliasesPackage(env.getProperty("mybatis.dapPackage"));
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));
            return sqlSessionFactoryBean.getObject();
        } catch (IOException e) {
            logger.error("解析mapper.xml文件出错");
            e.printStackTrace();
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    /**
     * 配置扫描Dao接口包，便于动态实现Dao接口，并且注入到容器中去
     * @return
     */
//    @Bean
//    public MapperScannerConfigurer getMapperScannerConfigurer(){
//        System.out.println("进来了吗getMapperScannerConfigurer");
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        //注入sqlSessionFactory
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName(env.getProperty("mybatis.sqlSessionFactory"));
//        System.out.println(env.getProperty("mybatis.sqlSessionFactory"));
//        mapperScannerConfigurer.setBasePackage(env.getProperty("mybatis.dapPackage"));
//        return mapperScannerConfigurer;
//    }
}
