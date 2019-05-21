package fsc.site.pojo;

import java.io.Serializable;

/**
 * @author fanshanchao
 * @date 2019/3/17 22:28
 * 这个类的作用就是用于返回一个统一的json格式的数据 格式如下
 * {
 *     code:状态码
 *     msg:信息
 *     data:返回数据
 * }
 */
public class Response {
    private int code;
    private String msg;
    private Object data;

    public Response() {
    }

    public Serializable getToken() {
        return token;
    }
    public Response setToken(Serializable token) {
        this.token = token;
        return this;
    }

    private Serializable token;
    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }
}
