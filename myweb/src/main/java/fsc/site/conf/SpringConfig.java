package fsc.site.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

@Configuration
//不扫描Controller注解的bean
@ComponentScan(basePackages = {"fsc.site.service","fsc.site.dao"},excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value={EnableWebMvc.class, RestController.class})})
@PropertySource(value = {"classpath:dataSource.properties","classpath:system.properties"},encoding = "UTF-8")//加载数据库配置文件
@EnableTransactionManagement //开启使用事务注解
@MapperScan("fsc.site.dao")
public class SpringConfig {
    private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);
    @Autowired
    private Environment env;
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
        //配置分页插件所需要的属性

        //sqlSessionFactoryBean.setPlugins(new Interceptor[]{});
        //设置配置mybatis全局配置文件
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(env.getProperty("mybatis.configLocation")));
        //设置mybatis 所有的mapper文件
        try {
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
     * 使用redis的连接池技术
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(Integer.parseInt(env.getProperty("redis.pool.maxTotal")));
        //设置最小空闲时间
        jedisPoolConfig.setMinIdle(Integer.parseInt(env.getProperty("redis.pool.minIdle")));
        //设置最大等待时间
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(env.getProperty("redis.pool.maxWaitMills")));
        return jedisPoolConfig;
    }
    /**
     * 创建redis 的bean工厂
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        //单机版redis
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(env.getProperty("redis.host"));
        //设置redis使用的默认数据库
        redisStandaloneConfiguration.setDatabase(2);
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(env.getProperty("redis.password")));
        //设置端口号
        redisStandaloneConfiguration.setPort(Integer.parseInt(env.getProperty("redis.port")));
        //获取默认的连接池构造器
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcp =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器
        jpcp.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcp.build();
        //返回redis的工厂 单机配置+客户端配置=jredis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    /**
     * 使用spring -data的redisTemplate
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }



}
