package fsc.site.shiro;

import com.alibaba.fastjson.JSON;
import fsc.site.pojo.ResponseCodeEnum;
import fsc.site.pojo.ResponseGenerator;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author fanshanchao
 * @date 2019/3/20 17:56
 * 前后端分离的项目中，会发送跨域请求，而跨域请求是复杂请求，会在请求先发送一个position请求然后再发送实际的post，get请求，而前面这次的position请求并不带shiro的Authorization字段
 * 实际这个字段存储的就是sessionID。现在这position请求没有会话id，所以会导致认证失败 所以需要写一个过滤器过滤掉所有的potion请求
 */
public class CORSAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(CORSAuthenticationFilter.class);

    /**
     * 这个方法其实就是当前请求是否认证过  我们这里将option请求标记为被认证过
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(request instanceof HttpServletRequest){
            //允许potion请求访问
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 此方法是请求未通过认证时执行的方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse  res= (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        //认证未通过时执行的方法
        writer.write(JSON.toJSONString(ResponseGenerator.getFailureReponse(ResponseCodeEnum.NOT_LOGIN,"未登陆")));
        writer.close();
        return false;
    }
}
