package fsc.site.shiro;

import com.alibaba.fastjson.JSON;
import fsc.site.pojo.ResponseCodeEnum;
import fsc.site.pojo.ResponseGenerator;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** 也要重新shiro的权限过滤器去允许跨域的potion请求通过
 * @author fanshanchao
 * @date 2019/4/2 17:58
 */
public class CORSPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        if(request instanceof HttpServletRequest){
            //允许potion请求访问
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 没有权限时调用的方法
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setCharacterEncoding("utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(JSON.toJSONString(ResponseGenerator.getFailureReponse(ResponseCodeEnum.UNAUTHORIZED,"没有权限")));
        printWriter.close();
        return false;
    }
}
