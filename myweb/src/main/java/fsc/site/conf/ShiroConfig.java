package fsc.site.conf;

import fsc.site.shiro.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author fanshanchao
 * @date 2019/3/18 14:01
 *
 * 这个类用于编写shiro的配置信息
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private Environment env;


    /**
     * 凭证匹配器 就是我们的密码以后使用md5算法进行匹配
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置使用md5 去匹配密码
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置散列的次数
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
    /**
     * 配置自定义的realm
     * @return
     */
    @Bean
    public UserRealm getUserRealm(){
        //设置UserRealm
        UserRealm realm = new UserRealm();
        //设置凭证匹配器 使用md5匹配器
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        //realm.setPermissionResolver();

//        //开启使用redis缓存 不知道为什么这三个不能开启  已开启就用户不存在
//        realm.setCachingEnabled(true);
//        realm.setAuthenticationCachingEnabled(true);
//        realm.setAuthorizationCachingEnabled(true);
        return realm;
    }
    /**
     * 配置安全管理器
     * @return
     */
    @Bean("securityManager")
    public SecurityManager getDefaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义的realm
        securityManager.setRealm(getUserRealm());
        //安全管理器使用redis的会话管理器
        securityManager.setSessionManager(sessionManager());
        //设置使用了redis的缓存实现
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }
    /**
     * 配置Shiro的拦截器
     * @param
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager());
        //设置过滤器拦截链  value值都是shiro中的一些拦截器的值
        Map<String,String> filterChain = new LinkedHashMap<>();
        //配置退出过滤器 登陆退出后跳转的url 这个跳转的url可以随便设置
        filterChain.put("/logout==GET","anon");
        //配置不会被拦截器拦截的的链接 注意 这里是顺序判断的  这里是表示除了登陆url以外 其他都不能匿名访问  这里还一些url还要慢慢来配置
        filterChain.put("/login==POST","anon");
        filterChain.put("/getCode==POST","anon");
        filterChain.put("/refresh==GET","anon");
        filterChain.put("/user==POST","anon");
        filterChain.put("/goodsTypes==GET","anon");
        filterChain.put("/goods==GET","anon");
        filterChain.put("/likes==GET","anon");
        filterChain.put("/likes==POST","anon");
        filterChain.put("/goods/*==GET","anon");
        filterChain.put("/getGoodsCount==GET","anon");
        filterChain.put("/seckill/**/detail==GET","anon");
        filterChain.put("/seckill/list==GET","anon");
        filterChain.put("/seckill/now==GET","anon");
        filterChain.put("/seckill/count==GET","anon");
        filterChain.put("/getPermission==GET","authc");
        //这个表示所有页面都需要认证
        filterChain.put("/**","authc");
        //设置拦截链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);
        //设置未授权的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        //设置自己的过滤器 将自定义的过滤器设置进去
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc",corsAuthenticationFilter());
        //配置自己的退出登陆过滤器
        filters.put("logout",corsLogoutFilter());
        //设置自己的权限拦截器
        //filters.put("perms",corsPermissionsAuthorizationFilter());
        return shiroFilterFactoryBean;
    }
//

    /**
     * 配置redis管理器
     * 这里使用的是一个开源的插件 否则要自己去实现cache、cacheManager、SessionDAO
     * @return
     */
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(env.getProperty("redis.host"));
        redisManager.setPort(Integer.parseInt(env.getProperty("redis.port")));
        redisManager.setPassword(env.getProperty("redis.password"));
        return redisManager;
    }

    /**
     * 配置redis缓存管理器 设置了这个就会对权限进行缓存
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setPrincipalIdFieldName("userName");
        return redisCacheManager;
    }

    /**
     * 这个是 RedisSessionDao层的实现
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        //设置SessionId在redis中保存的时间 此处应该大于会话存在的时间 这里的单位是秒
        redisSessionDAO.setExpire(Integer.parseInt(env.getProperty("redis.expire")));
        return redisSessionDAO;
    }
    /**
     * 设置自定义的Session管理器 从而从请求头中去获取Session id
     * 并且将会话用redisDao的缓存技术
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        MySessionManger mySessionManger = new MySessionManger();
        //设置会话使用的dao层
        mySessionManger.setSessionDAO(redisSessionDAO());
        //设置会话使用的cookie
        //mySessionManger.setSessionIdCookie(simpleCookie());
        //设置会话的超时时间 先设置为一分钟便于测试
        mySessionManger.setGlobalSessionTimeout(1800000);
        //设置删除过期的session
        mySessionManger.setDeleteInvalidSessions(true);
        //设置定期删除过期的sessionId
        mySessionManger.setSessionValidationSchedulerEnabled(true);
        return mySessionManger;
    }

    /**
     * 这个cookie是配置保存在sessionId的cookie
     * 这个cookie不是记住我的cookie 记住我的功能和这个会话的cookie效果是不一样的 这个可以理解为一直保持为认证状态
     * 因为SessionId的名字默认为JSESSIONID 与Servlet容器名冲突 所以修改为sid
     *
     * 其实完全不用用到cookie 已经前后端分离已经禁止受用cookie了
     * @return
     */
//    @Bean
//    public SimpleCookie simpleCookie(){
//        SimpleCookie simpleCookie = new SimpleCookie("sid");
//        //设置所有请求都可以访问
//        simpleCookie.setHttpOnly(false);
//        simpleCookie.setPath("/");
//        //表示浏览器关闭此会话失效 失效此cookie
//        simpleCookie.setMaxAge(-1);
//        return simpleCookie;
//    }
    /**
     * 自定义的shiro表单过滤器 用于过滤option请求
     * @return
     */
    public CORSAuthenticationFilter corsAuthenticationFilter(){
        return new CORSAuthenticationFilter();
    }
//    /**
//     * 自定义的AccessControlFilter拦截器 这个拦截器是上面这个表单过滤器的父类 应该会在它前面执行
//     *
//     */
//    public AccessControlFilter accessControlFilter(){
//        return new MyAccessControlFilter();
//    }

    /**
     * 自定义的退出登陆过滤器
     * @return
     */
    public CORSLogoutFilter corsLogoutFilter(){
        return new CORSLogoutFilter();
    }

//    /**
//     * 自定义的权限拦截器
//     * @return
//     */
//    public CORSPermissionsAuthorizationFilter corsPermissionsAuthorizationFilter(){
//        return new CORSPermissionsAuthorizationFilter();
//    }
}
