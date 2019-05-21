package fsc.site.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author fanshanchao
 * @date 2019/3/18 17:50
 * 因为在传统前后端不分离的项目中 shiro从cookie中来获取SessionId  在前后端分离的项目中我们使用在ajax中请求头中带session id来进行记住会话
 * 所以我们需重写getSessionId 让它从请求头中去获取SessionId
 * 因为传统从cookie中获取Session ID是不支持跨域的
 */
public class MySessionManger extends DefaultWebSessionManager {
    public MySessionManger(){
        super();
        super.setGlobalSessionTimeout(60000);
    }
    //这个是请求头中的字段名称
    private static final String AUTHORIZATION = "Authorization";
    //设置引用session id源？？？？？ 这里不懂
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        System.out.println("进来一次会话管理器");
        //先从请求头中获取id
        String id  = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
       // 判断获取到的session id 判断是否为空 将
        if(!StringUtils.isEmpty(id)){
            //这几行不懂 是为了以后用到这些属性？
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
            return id;
        }
        //否则按默认规则去获取Session id
        return super.getSessionId(request, response);
    }
}
