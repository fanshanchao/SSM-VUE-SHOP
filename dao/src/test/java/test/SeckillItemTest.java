package test;

import fsc.site.dao.SeckillItemMapperDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author fanshanchao
 * @date 2019/7/5 1:13
 */
@RunWith(SpringJUnit4ClassRunner.class)//代表使用Junit的测试
@ContextConfiguration(classes = SpringConfig.class)
public class SeckillItemTest {
    @Autowired
    private SeckillItemMapperDao seckillItemMapperDao;
    @Test
    public void test01(){
        //测试创建一个测试明细
       // seckillItemMapperDao.addSeckillItem(2,2,new Date());

        //测试获取所有查询明细
        System.out.println(seckillItemMapperDao.getSuccessSeckillList());

    }
}
