package fsc.site.pojo;

import java.math.BigDecimal;

/**
 * @author fanshanchao
 * @date 2019/4/8 13:58
 * 这个类是商品的pojo类
 */
public class Goods {
    public Goods(){

    }
    private Integer goodsId;
    private String goodsName;
    private String goodsDesc;
    private Double goodsPrice;
    private Integer typeId;
    private Integer goodsRepertory;
    private String imageUrl;
    private int goodsStatus;
    private int goodsSales;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getGoodsRepertory() {
        return goodsRepertory;
    }

    public void setGoodsRepertory(Integer goodsRepertory) {
        this.goodsRepertory = goodsRepertory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public int getGoods_sales() {
        return goodsSales;
    }

    public void setGoods_sales(int goods_sales) {
        this.goodsSales = goods_sales;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", typeId=" + typeId +
                ", goodsRepertory=" + goodsRepertory +
                ", imageUrl=" + imageUrl +
                ", goodsStatus=" + goodsStatus +
                ", goodsSales=" + goodsSales +
                '}';
    }
}
