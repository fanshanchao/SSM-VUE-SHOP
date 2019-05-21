package fsc.site.pojo;

/**
 * @author fanshanchao
 * @date 2019/4/12 13:00
 * 这个类用于返回订单详情代表着商品详情那部分
 */
public class ResultOrderGoods {
    public ResultOrderGoods(){

    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer goodsId;
    private Integer goodsNumber;
    private Double goodsPrice;
    private String goodsName;
    private String imageUrl;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return "ResultOrderGoods{" +
                "goodsId=" + goodsId +
                ", goodsNumber=" + goodsNumber +
                ", goodsPrice=" + goodsPrice +
                ", goodsName='" + goodsName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
