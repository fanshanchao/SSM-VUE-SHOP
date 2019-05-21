package fsc.site.conf;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * spring mvc的配置文件
 */
@Configuration
@EnableWebMvc
//只扫描Controller注解的
@ComponentScan(basePackages = {"fsc.site.controller","fsc.site.shiro"},useDefaultFilters = false,includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {RestController.class, ControllerAdvice.class})})
public class MvcConfig implements WebMvcConfigurer {
    //配置JSP视图解析器 前后端分离还要什么jsp解析器
//    @Bean
//    public ViewResolver viewResolver(){
//        InternalResourceViewResolver resolver = new StandaloneMvcTestViewResolver();
//        return resolver;
//    }
    /**
     * 相当于 <mvc:default-servlet-handler/>
     * 后端只返回json数据 还设置静态资源干嘛呢
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    /**
     * 添加拦截器
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//    }

    /**
     * 开启跨域 并且设置各个参数
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST","DELETE","PUT")
                .maxAge(3600);
    }

    /**
     * 配置上传文件所需要的解析器
     * @return
     */
    @Bean("multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSize(1024000);
        return commonsMultipartResolver;
    }
    /**
     * 配置shiro生命周期处理器
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 这个方法用于开启shiro注解的使用 需要上面这个advisorAutoProxyCreator方法来能够使用
     * 这个注解的使用是基于AOP的
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
