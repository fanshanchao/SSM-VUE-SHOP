package fsc.site.dao;


import fsc.site.pojo.Goods;
import fsc.site.pojo.GoodsType;
import fsc.site.pojo.ResultGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("goodsMapperDao")
public interface GoodsMapperDao {
    //查询所有分类信息
    List<GoodsType> queryTypes();
    //添加一个分类
    Integer addType(String typeName);
    //查询一个分类的id
    Integer queryTypeId(String typeName);
    //添加一个商品
    Integer addGoods(Goods goods);
    //查询所有商品 并且封装在一个新的bean上
    List<ResultGoods> getGoods(@Param("typeId")Integer typeId,@Param("sortName")String sortName);
    //更新一个商品
    Integer updateGoods(Goods goods);
    //下架一个商品
    Integer outGoods(Integer goodsId);
    //重新上架一个商品
    Integer goGoods(Integer goodsId);
    //获取所有商品的数量
    Integer getGoodsCount(@Param("typeId") Integer typeId);
    //查询某个商品的价格
    Double queryGoodsPrice(Integer goodsId);
    //查询一个商品的信息
    ResultGoods getGoodsById(@Param("goodsId")Integer goodsId);
    //购买一个商品的方法
    Integer payGoods(@Param("goodsId")Integer goodsId,@Param("nums")Integer nums);
}
