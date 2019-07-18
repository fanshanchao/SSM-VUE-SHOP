package fsc.site.exepection;

/**
 * @author fanshanchao
 * @date 2019/7/6 22:29
 * 秒杀关闭异常
 */
public class SeckillShutDownExeception extends SeckillExeception {
    public SeckillShutDownExeception(String message) {
        super(message);
    }

    public SeckillShutDownExeception(String message, Throwable cause) {
        super(message, cause);
    }
}
