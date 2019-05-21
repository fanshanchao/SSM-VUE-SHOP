package fsc.site.shiro;

import fsc.site.pojo.Response;
import fsc.site.pojo.ResponseCodeEnum;
import fsc.site.pojo.ResponseGenerator;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author fanshanchao
 * @date 2019/3/19 18:11
 * 配置shiro的全局异常处理
 * 这个全局异常处理类要慎用 可能导致所有异常都只显示一种异常
 */
@ControllerAdvice(basePackages = "fsc.site.controller")//指定异常控制包
public class MyExceptionHandler{
    /**
     * 认证失败异常
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public  Response unauthorizedException(){
        return ResponseGenerator.getFailureReponse(ResponseCodeEnum.UNAUTHORIZED,"认证失败");
    }
//    /**
//     * 处理服务器内部io异常
//     */
//    @ExceptionHandler(IOException.class)
//    @ResponseBody
//    public Response IOException(){
//        return ResponseGenerator.getFailureReponse("服务器内部IO异常");
//    }
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public  Response sendServerException02(){
//        System.out.println("异常");
//        return ResponseGenerator.getFailureReponse(ResponseCodeEnum.INTERNAL_SERVER_ERROR,"服务器内部异常");
//    }
}
