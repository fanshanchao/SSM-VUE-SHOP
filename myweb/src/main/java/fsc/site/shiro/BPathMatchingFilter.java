package fsc.site.shiro;

import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;

/**
 * @author fanshanchao
 * @date 2019/4/16 10:45 注意项目并没有用到这个过滤器 这个是用来jwt鉴权filter
 * 重写过滤器匹配规则以让它，增加rest风格支持
 */
public abstract class  BPathMatchingFilter extends PathMatchingFilter {
    /**
     * 重写url匹配 加入method支持
     * @param
     * @param path 这个path是url+method
     * @return
     * 注意这里有几个重载的方法
     */
    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        //获取url
        String requestUrl = this.getPathWithinApplication(request);
        //分割出path中url和method
        String[] strings = path.split("==");
        if(strings.length==1){
            //如果分割出来只有url 实现原来的匹配 这里调用的不是本方法 参数不一样
            return this.pathsMatch(strings[0],requestUrl);
        }else{
            // 分割出url+httpMethod,判断httpMethod和request请求的method是否一致,不一致直接false
            String httpMethod = WebUtils.toHttp(request).getMethod().toUpperCase();
            return httpMethod.equals(strings[1].toUpperCase()) && this.pathsMatch(strings[0], requestUrl);
        }
    }
}
