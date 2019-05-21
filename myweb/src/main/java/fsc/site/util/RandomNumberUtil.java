package fsc.site.util;


/**
 * @author fanshanchao
 * @date 2019/3/25 20:20
 */
public class RandomNumberUtil {
    /**
     * 利用当前时间毫秒数 随机产生一个5位数
     * @return
     */
    public static int getFourNumber(){
        return (int)((Math.random()*9+1)*10000);
    }

}
