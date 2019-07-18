package test;

import fsc.site.dao.SeckillMapperDao;
import fsc.site.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author fanshanchao
 * @date 2019/7/5 1:01
 */
@RunWith(SpringJUnit4ClassRunner.class)//代表使用Junit的测试
@ContextConfiguration(classes = SpringConfig.class)
public class SeckillDaoTest {
    @Autowired
    private SeckillMapperDao seckillMapperDao;

    @Test
    public void test01(){
        //创建一个秒杀
//        Seckill seckill = new Seckill();
//        seckill.setGoodsId(2);
//        seckill.setSeckillRepertory(100);
//        seckill.setSeckillPrice(12.00);
//        seckill.setCreateTime(new Date());
//        seckill.setStartTime(new Date());
//        seckill.setEndTime(new Date());
//        seckillMapperDao.addSeckill(seckill);

        //查询所有秒杀
        System.out.println(seckillMapperDao.getSeckillList());
    }
}
