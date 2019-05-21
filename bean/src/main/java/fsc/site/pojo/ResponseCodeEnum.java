package fsc.site.pojo;

/**
 * 这个枚举用来表示状态码
 */
public enum ResponseCodeEnum {
    SUCCESS(200),//成功的状态码
    FAIL(400),//失败的状态码
    UNAUTHORIZED(403),//未授权的状态码
    NOT_FOUND(404),//资源没找到
    INTERNAL_SERVER_ERROR(500),//服务器内部错误
    NOT_LOGIN(422),//未登陆
    AUTH_ERROR(401),//认证出错 如账号密码错误之类的
    YET_LOGIN(423),//已经登陆 防止重复登陆
    TIMEOUT_LOGIN(424),
    SUCCESS_LOGIN(202);//登陆成功状态码
    ResponseCodeEnum(int code){
        this.code = code;
    }

    ResponseCodeEnum() {
    }

    public int getCode() {
        return code;
    }

    private int code;

}
