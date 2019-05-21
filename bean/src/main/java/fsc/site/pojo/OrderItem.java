package fsc.site.pojo;

/**
 * @author fanshanchao
 * @date 2019/4/11 16:34
 * 对应mysql库中的t_orderItem表
 */
public class OrderItem {
    public OrderItem(){

    }
    private Integer id;
    private Integer orderId;
    private Integer goodsId;
    private Integer goodsNumber;
    private Double goodsPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", goodsNumber=" + goodsNumber +
                ", goodsPrice=" + goodsPrice +
                '}';
    }
}
