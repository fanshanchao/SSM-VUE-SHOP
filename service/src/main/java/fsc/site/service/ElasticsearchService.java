package fsc.site.service;

import fsc.site.pojo.ResultGoods;
import fsc.site.util.ElasticsearchUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/5/6 22:53
 * 对elasticsearch操作的服务类
 */
@Service
public class ElasticsearchService {
    /**
     * 添加/更新一个商品到elasticsearch中去
     * @param goods
     */
    public void putGoods(ResultGoods goods){
        ElasticsearchUtil elasticsearchUtil = new ElasticsearchUtil();
        elasticsearchUtil.putGoods(goods);
        elasticsearchUtil.closeConnection();
    }
    /**
     * 添加/更新一个列表商品到elasticsearch
     * @param list
     */
    public void putGoodsList(List<ResultGoods> list){
        ElasticsearchUtil elasticsearchUtil = new ElasticsearchUtil();
        elasticsearchUtil.putGoodsList(list);
        elasticsearchUtil.closeConnection();
    }

    /**
     * 删除一个商品
     * @param goodsId
     */
    public void deleteGoods(Integer goodsId){
        ElasticsearchUtil elasticsearchUtil = new ElasticsearchUtil();
        elasticsearchUtil.deleteGoods(goodsId);
        elasticsearchUtil.closeConnection();
    }
}
