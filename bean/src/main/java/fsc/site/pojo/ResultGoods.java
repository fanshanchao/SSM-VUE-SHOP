package fsc.site.pojo;

/**
 * @author fanshanchao
 * @date 2019/4/10 16:50
 * 注意这个bean和之前的Goods不一样 之前哪个映射了数据库中的表 但这个是从数据库中返回数据的封装类型
 */
public class ResultGoods {
    public ResultGoods(){

    }
    private Integer goodsId;
    private String goodsName;
    private String goodsDesc;
    private Double goodsPrice;
    private String typeName;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public int getGoodsSales() {
        return goodsSales;
    }

    public void setGoodsSales(int goodsSales) {
        this.goodsSales = goodsSales;
    }

    @Override
    public String toString() {
        return "ResultGoods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", typeName='" + typeName + '\'' +
                ", goodsRepertory=" + goodsRepertory +
                ", imageUrl='" + imageUrl + '\'' +
                ", goodsStatus=" + goodsStatus +
                ", goodsSales=" + goodsSales +
                '}';
    }
}
