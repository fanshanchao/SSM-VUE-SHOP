package fsc.site.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fanshanchao
 * @date 2019/7/4 22:28
 * 用于创建映射秒杀表
 */
public class Seckill implements Serializable {
    private Integer seckillId;
    private Integer goodsId;
    private Integer seckillRepertory;
    private Double seckillPrice;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private Goods goods;
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSeckillRepertory() {
        return seckillRepertory;
    }

    public void setSeckillRepertory(Integer seckillRepertory) {
        this.seckillRepertory = seckillRepertory;
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", goodsId=" + goodsId +
                ", seckillRepertory=" + seckillRepertory +
                ", seckillPrice=" + seckillPrice +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", goods=" + goods +
                '}';
    }
    public Seckill(){

    }
}
