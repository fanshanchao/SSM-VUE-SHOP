package fsc.site.service;

import fsc.site.dao.OrderMapperDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author fanshanchao
 * @date 2019/4/12 13:36
 */
@RunWith(SpringJUnit4ClassRunner.class)//代表使用Junit的测试
@ContextConfiguration(classes = SpringConfig.class)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Test
    public void queryOrders(){
        System.out.println("查询到订单"+orderService.queryOrders(1,3));
    }
}
