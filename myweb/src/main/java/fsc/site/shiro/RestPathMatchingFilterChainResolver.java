package fsc.site.shiro;

import fsc.site.conf.SpringConfig;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Iterator;

/**
 * @author fanshanchao
 * @date 2019/4/16 10:15
 * 因为shiro对rest风格的url有些限制 所以自己重写url和拦截器的匹配方法 例如orders==get就代表了对order的get请求 从而实现rest风格匹配
 */
public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    private static final Logger logger = LoggerFactory.getLogger(RestPathMatchingFilterChainResolver.class);
    public RestPathMatchingFilterChainResolver(){
        super();
    }
    public RestPathMatchingFilterChainResolver(FilterConfig filterConfig) {
        super(filterConfig);
    }

    /**
     * 重写它的获取url和拦截器链的方法
     * @param request
     * @param response
     * @param originalChain
     * @return
     */
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = this.getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        } else {
            String requestURI = this.getPathWithinApplication(request);
            Iterator var6 = filterChainManager.getChainNames().iterator();

            String pathPattern;
            //用一个数组来存放切割后的url
            String[] strings = null;
            //用一个变量来判断你是否符合规则
            boolean flag = true;
            do {
                if (!var6.hasNext()) {
                    return null;
                }
                pathPattern = (String)var6.next();
                //之前的路径匹配是order 限制是order==method所以要切分一下
                strings = pathPattern.split("==");
                //如果它的长度等于2 那么代表是上面这种rest风格的url
                if(strings.length==2){
                    //分割出url和method判断这个method是否和request的请求方法是否一致，不一致返回true因为下面while循环还要继续进行
                    if (WebUtils.toHttp(request).getMethod().toUpperCase().equals(strings[1].toUpperCase())) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                }else{
                    flag = false;
                }
                //用切割开来后的url去匹配
                pathPattern = strings[0];
                //这里判断是否匹配还要看
            } while(!this.pathMatches(pathPattern, requestURI)||flag);
            if (logger.isTraceEnabled()) {
                logger.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  " + "Utilizing corresponding filter chain...");
            }
            //上面的路径和拦截器匹配成功后再拼接回原来的url
            if(strings.length == 2){
                pathPattern = pathPattern.concat("==").concat(WebUtils.toHttp(request).getMethod().toUpperCase());
            }
            //继续对拦截器进行匹配操作
            return filterChainManager.proxy(originalChain, pathPattern);
        }
    }
}
