package fsc.site.pojo;

import java.io.Serializable;

/**
 * @author fanshanchao
 * @date 2019/3/18 14:36
 *
 * 这个类用于快速生成Reponse结果
 */
public class ResponseGenerator {
    private static final String DEFAULT_SUCCESS_MSG = "SUCCESS";
    /**
     * 用于返回默认成功的json数据
     * @return
     */
    public static Response getSuccessReponse(){
        return new Response().setCode(ResponseCodeEnum.SUCCESS.getCode()).setMsg(DEFAULT_SUCCESS_MSG);
    }

    /**
     * 返回认证成功带有token 的json信息
     * @return
     */
    public static Response getSuccessLogin(String msg, Serializable token, Object object){
        return new Response().setCode(ResponseCodeEnum.SUCCESS_LOGIN.getCode()).setToken(token).setMsg(msg).setData(object);
    }
    /**
     * 返回带有数据的成功json
     * @param data
     * @param
     * @return
     */
    public static Response getSuccessReponseData(Object data){
        return new Response().setCode(ResponseCodeEnum.SUCCESS.getCode()).setMsg(DEFAULT_SUCCESS_MSG).setData(data);
    }

    /**
     * 返回带有token的成功json
     * @param token
     * @return
     */
    public static Response getSuccessReponse(Serializable token){
        return new Response().setToken(token).setCode(ResponseCodeEnum.SUCCESS.getCode()).setMsg(DEFAULT_SUCCESS_MSG);
    }

    /**
     * 返回带有错误信息的json
     * @param msg
     * @return
     */
    public static Response getFailureReponse(String msg){
        return new Response().setCode(ResponseCodeEnum.FAIL.getCode()).setMsg(msg);
    }

    /**
     * 返回带有特有错误码和错误信息的json
     * @param codeEnum
     * @param msg
     * @return
     */
    public static Response getFailureReponse(ResponseCodeEnum codeEnum, String msg){
        return new Response().setCode(codeEnum.getCode()).setMsg(msg);
    }
}
