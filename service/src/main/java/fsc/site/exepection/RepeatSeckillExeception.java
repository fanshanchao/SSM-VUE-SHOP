package fsc.site.exepection;

/**
 * @author fanshanchao
 * @date 2019/7/6 22:28
 * 重复秒杀异常
 */
public class RepeatSeckillExeception extends SeckillExeception {
    public RepeatSeckillExeception(String message) {
        super(message);
    }

    public RepeatSeckillExeception(String message, Throwable cause) {
        super(message, cause);
    }
}
