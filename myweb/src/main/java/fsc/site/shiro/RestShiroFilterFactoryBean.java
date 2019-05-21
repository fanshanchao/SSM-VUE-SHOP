package fsc.site.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;

/**
 * @author fanshanchao
 * @date 2019/4/16 11:00
 * 重写shiro的工厂bean  以使用自己定义的url匹配规则
 */
public class RestShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    private static final Logger logger = LoggerFactory.getLogger(RestShiroFilterFactoryBean.class);
    public RestShiroFilterFactoryBean(){
        super();
    }

    /**
     * 重写获取shiro拦截链的方法
     * @return
     * @throws Exception
     */
    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        logger.debug("Creating Shiro Filter instance.");
        //获取管理器
        SecurityManager securityManager = this.getSecurityManager();
        String msg;
        if(securityManager==null){
            msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }else if(!(securityManager instanceof WebSecurityManager)){
            msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }else{
            FilterChainManager manager = this.createFilterChainManager();
            RestPathMatchingFilterChainResolver resolver = new RestPathMatchingFilterChainResolver();
            resolver.setFilterChainManager(manager);
            return new RestShiroFilterFactoryBean.SpringShiroFilter((WebSecurityManager)securityManager, resolver);
        }
    }
    private static final class SpringShiroFilter extends AbstractShiroFilter {
        protected SpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            } else {
                this.setSecurityManager(webSecurityManager);
                if (resolver != null) {
                    this.setFilterChainResolver(resolver);
                }

            }
        }
    }
}
