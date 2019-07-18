package fsc.site.service;

import fsc.site.pojo.ResultGoods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/10 17:02
 */
@RunWith(SpringJUnit4ClassRunner.class)//代表使用Junit的测试
@ContextConfiguration(classes = SpringConfig.class)
public class GoodsServiceTest {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Test
    public void testGetGoods(){
        //System.out.println(goodsService.getGoods(1,2));
        //查询所有商品 然后添加到elasticsearch中去
        List<ResultGoods> resultGoods = goodsService.getGoods(0,0,0,"");
        elasticsearchService.putGoodsList(resultGoods);
    }
}
