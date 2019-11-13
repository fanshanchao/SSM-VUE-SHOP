package fsc.site.service;

import com.github.pagehelper.PageHelper;
import fsc.site.dao.GoodsMapperDao;
import fsc.site.pojo.Goods;
import fsc.site.pojo.GoodsType;
import fsc.site.pojo.ResultGoods;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/8 14:13
 */
@Service
public class GoodsService {
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private GoodsMapperDao goodsMapperDao;
    /**
     * 查询所有商品分类信息
     * @return
     */
    public List<GoodsType> queryTypes(){
        return goodsMapperDao.queryTypes();
    }

    /**
     * 创建一个分类
     * @param typeName
     * @return
     */
    public Integer addType(String typeName){
        return goodsMapperDao.addType(typeName);
    }

    /**
     * 根据类型名称去查询是否有已经存在这个类别
     * @param typeName
     * @return
     */
    public Integer queryTypeId(String typeName){
        return goodsMapperDao.queryTypeId(typeName);
    }

    /**
     * 添加一个商品 同时向elasticsearch也添加一条数据
     * @param goods
     * @return
     */
    @Transactional
    public Integer addGoods(Goods goods){
        //往数据库添加操作后 再查询此条数据的全部信息 然后添加到elasticsearch中去
         goodsMapperDao.addGoods(goods);
         ResultGoods resultGoods = goodsMapperDao.getGoodsById(goods.getGoodsId());
         //添加到elasticsearch中去
        //elasticsearchService.putGoods(resultGoods);
        return goods.getGoodsId();
    }
    /**
     * 获取所有商品 利用分页查询
     */
    public List<ResultGoods> getGoods(int pageNum,int pageSize,Integer typeId,String sortName){
        //先用分页查询
        PageHelper.startPage(pageNum,pageSize);
        return goodsMapperDao.getGoods(typeId,sortName);
    }

    /**
     * 更新一个商品 同样也向elasticsearch中去更新一个商品数据
     * @param goods
     * @return
     */
    @Transactional
    public Integer updateGoods(Goods goods){
        goodsMapperDao.updateGoods(goods);
        ResultGoods resultGoods = goodsMapperDao.getGoodsById(goods.getGoodsId());
        //插入到elasticsearch中去
        //elasticsearchService.putGoods(resultGoods);
        return goods.getGoodsId();
    }

    /**
     * 下架一个商品 同时删除elasticsearch中的商品信息
     * @param goodsId
     * @return
     */
    @Transactional
    public Integer outGoods(Integer goodsId){
        //删除elasticsearch中的这个商品
        //elasticsearchService.deleteGoods(goodsId);
        return goodsMapperDao.outGoods(goodsId);
    }

    /**
     * 重新上架一个商品 同时获取到这个上架的商品添加到elasticsearch中去
     * @param goodsId
     * @return
     */
    @Transactional
    public Integer goGoods(Integer goodsId){
        goodsMapperDao.goGoods(goodsId);
        ResultGoods resultGoods = goodsMapperDao.getGoodsById(goodsId);
        //elasticsearchService.putGoods(resultGoods);
        return goodsId;
    }

    /**
     * 获取所有商品的数量 根据分类id
     * @return
     */
    public Integer getGoodsCount(Integer typeId){
        return goodsMapperDao.getGoodsCount(typeId);
    }

    /**
     * 查询某个商品的价格
     * @param goodsId
     * @return
     */
    public Double queryGoodsPrice(Integer goodsId){
        return goodsMapperDao.queryGoodsPrice(goodsId);
    }

    /**
     * 获取某个商品的信息
     * @param goodsId
     * @return
     */
    public ResultGoods getGoodsById(Integer goodsId){
        return goodsMapperDao.getGoodsById(goodsId);
    }

    /**
     * 购买一个商品
     * @param goodsId 商品id
     * @param nums 商品数量
     * @return
     */
    public Integer payGoods(Integer goodsId,Integer nums){
        return goodsMapperDao.payGoods(goodsId,nums);
    }
}
