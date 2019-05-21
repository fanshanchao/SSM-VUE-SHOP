package fsc.site.conf;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 这个类相当于web.xml
 */
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 这个方法返回所有的配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{SpringConfig.class,ShiroConfig.class,EmailConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{MvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }



    /**
     * 这个类用来配置web.xml中的拦截器 映射会自动和上面getServletMappings方法中的映射一样
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy();
        //设置true由servelt来控制filter的生命周期
        shiroFilter.setTargetFilterLifecycle(true);
        //设置spring容器filter的bean id，如果不设置则找与filter-name一致的bean
        shiroFilter.setTargetBeanName("shiroFilter");
        return new Filter[]{shiroFilter};
    }

}
