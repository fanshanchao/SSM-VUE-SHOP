package fsc.site.exepection;

/**
 * @author fanshanchao
 * @date 2019/7/6 22:25
 * 秒杀异常的父类
 */
public class SeckillExeception extends RuntimeException {
    public SeckillExeception(String message) {
        super(message);
    }

    public SeckillExeception(String message, Throwable cause) {
        super(message, cause);
    }
}
