package fsc.site.dao;

import fsc.site.pojo.Order;
import fsc.site.pojo.OrderItem;
import fsc.site.pojo.ResultOrder;
import fsc.site.pojo.ResultOrderGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/11 16:05
 */
@Repository
public interface OrderMapperDao {
    //添加一个订单
    Integer addOrder(Order order);
    //添加订单项
    Integer addOrderItem(@Param("list")List<OrderItem> list);
    //查询所有订单的详情 根据分页来查
    List<ResultOrder> queryOrders();
    //根据订单id查询某个订单项的商品信息
    ResultOrderGoods queryByOrderId(Integer orderId);
    //获取订单的数量
    Integer getOrderCount();
    //支付一个订单
    Integer payOrder(@Param("orderId") Integer orderId,@Param("time") Date date);
    //查询出这个订单所属的用户id
    Integer queryUserId(Integer orderId);
    //为一个订单进行发货
    Integer dealOrder(@Param("orderId") Integer orderId,@Param("time") Date date);
    //关闭一个订单
    Integer openOrder(Integer orderId);
    //确认收货一个订单
    Integer confirmOrder(@Param("orderId") Integer orderId,@Param("time") Date date);
    //获取未处理订单数量
    Integer getUnCount();
    //获取未处理订单列表 也是根据分页
    List<ResultOrder> getUnOrders();
    //查看商品是否已经支付
    Integer getStatus(@Param("orderId") Integer orderId);
    //返回某个订单的价格
    Double getOrderMoney(@Param("orderId") Integer orderId);
    //查询订单项
    List<OrderItem> selectOrderItem(@Param("orderId") Integer orderId);
    //用户查询自己的订单 status是查询条件 某种状态的订单
    List<ResultOrder> getUserOrders(@Param("userId") Integer userId,@Param("status") Integer status);
    //用户获取自己某种状态的订单数量
    Integer getUserOrderCount(@Param("userId") Integer userId,@Param("status") Integer status);
}
