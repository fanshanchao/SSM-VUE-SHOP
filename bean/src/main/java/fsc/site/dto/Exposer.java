package fsc.site.dto;

import java.io.Serializable;

/**
 * @author fanshanchao
 * @date 2019/7/6 22:18
 * 用于返回秒杀结果的实体类
 */
public class Exposer implements Serializable {
    //标记是否开启地址
    private boolean isOpen;
    //秒杀接口的md值
    private String md5;

    //秒杀id
    private Integer seckillId;

    //系统当前时间和秒杀开启时间以及结束时间
    private long now;
    private long start;
    private long end;
    public Exposer(){

    }
    public Exposer(boolean isOpen, String md5, Integer seckillId) {
        this.isOpen = isOpen;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean isOpen, Integer seckillId, long now, long start, long end) {
        this.isOpen = isOpen;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean isOpen, Integer seckillId) {
        this.isOpen = isOpen;
        this.seckillId = seckillId;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "isOpen=" + isOpen +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
