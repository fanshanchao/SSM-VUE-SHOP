package fsc.site.pojo;

import java.util.Date;

/**
 * @author fanshanchao
 * @date 2019/7/4 22:52
 * 返回一个秒杀明细 包括订单id
 */
public class SuccessSeckill {
    private Integer orderId;
    private Integer seckillId;
    private Integer userId;
    private Date createTime;
    private Integer seckillState;
    private Seckill seckill;

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSeckillState() {
        return seckillState;
    }

    public void setSeckillState(Integer seckillState) {
        this.seckillState = seckillState;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public SuccessSeckill(){

    }
    public SuccessSeckill(Integer orderId, Integer seckillId, Integer userId, Date createTime, Integer seckillState, Seckill seckill) {
        this.orderId = orderId;
        this.seckillId = seckillId;
        this.userId = userId;
        this.createTime = createTime;
        this.seckillState = seckillState;
        this.seckill = seckill;
    }
}
