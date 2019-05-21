package fsc.site.service;

import com.github.pagehelper.PageHelper;
import fsc.site.dao.OrderMapperDao;
import fsc.site.pojo.Order;
import fsc.site.pojo.OrderItem;
import fsc.site.pojo.ResultOrder;
import fsc.site.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/11 17:16
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapperDao orderMapperDao;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;
    /**
     * 添加订单的方法 用了事务 因为要向两个表去添加数据
     * @param order
     * @param list
     */
    @Transactional
    public Integer addOrder(Order order, List<OrderItem> list){
        //先获取到订单的总价格
        Double orderPrice = Double.valueOf(0.00) ;
        //对订单项进行设置订单号操作 以及价格
        for(OrderItem orderItem:list){
            //根据商品id去获取当前商品价格
            Double price = goodsService.queryGoodsPrice(orderItem.getGoodsId());
            //根据数量去设置总价格
            Double allPrice = orderItem.getGoodsNumber()*price;
            //设置订单项的总价
            orderItem.setGoodsPrice(allPrice);
            //将当前商品的价格添加到订单总价格上
            orderPrice+=allPrice;
        }
        order.setCreateDate(new Date());
        //设置订单的总价
        order.setPayMoney(orderPrice);
        //添加订单
        orderMapperDao.addOrder(order);
        //再循环遍历一次订单项 将订单号添加到订单项里面去
        for(OrderItem orderItem:list){
            orderItem.setOrderId(order.getOrderId());
        }
        //对订单项进行插入操作
        orderMapperDao.addOrderItem(list);
        return order.getOrderId();
    }

    /**
     * 利用分页查询订单详情
     * @return
     */
    public List<ResultOrder> queryOrders(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return orderMapperDao.queryOrders();
    }

    /**
     * 获取所有订单的数量
     * @return
     */
    public Integer getOrderCount(){
        return orderMapperDao.getOrderCount();
    }

    /**
     * 支付一个订单 还需要将订单中商品库存进行减少 所以要开启事务
     * @param orderId
     * @return
     */
    @Transactional
    public boolean payOrder(Integer orderId,String userName){
        //判断是不是本人 并且判断当前订单是否已经支付
        if(!this.isSelf(userName,orderId)||this.getStatus(orderId)!=0){
            return false;
        }
        //查询出当前订单的所有商品信息
        List<OrderItem> list = orderMapperDao.selectOrderItem(orderId);
        //对所有商品进行购买操作 这里没有考虑到商品如果库存不足应该如何去处理这个订单
        for(OrderItem orderItem:list){
            Integer result = goodsService.payGoods(orderItem.getGoodsId(),orderItem.getGoodsNumber());
        }
        //修改订单状态
        orderMapperDao.payOrder(orderId,new Date());
        return true;
    }
    /**
     * 查询出一个订单所属的用户id
     */
    public Integer queryUserId(Integer orderId){
        return orderMapperDao.queryUserId(orderId);
    }

    /**
     * 对一个订单进行发货操作
     * @param orderId
     * @return
     */
    @Transactional
    public Integer dealOrder(Integer orderId){
        //对订单进行设置发货时间

        return orderMapperDao.dealOrder(orderId,new Date());
    }

    /**
     * 关闭一个订单
     * @param orderId
     * @return
     */
    public Integer openOrder(Integer orderId){
        return orderMapperDao.openOrder(orderId);
    }

    /**
     * 用户取消自己订单的 注意这里必须判断取消的是自己的订单
     * @param userName
     * @param orderId
     * @return
     */
    public boolean userOpenOrder(String userName,Integer orderId){
        //判断是不是本人
        if(!this.isSelf(userName,orderId)){
            return false;
        }
        orderMapperDao.openOrder(orderId);
        return true;
    }
    /**
     * 确认收货一个订单这里要注意的是必须收货的是本人的订单
     * @param userName
     * @param orderId
     * @return
     */
    public boolean confirmOrder(String userName,Integer orderId){
        //判断是不是本人
        if(!this.isSelf(userName,orderId)){
            return false;
        }
        orderMapperDao.confirmOrder(orderId,new Date());
        return true;
    }

    /**
     * 获取未处理订单数量
     * @return
     */
    public Integer getUnCount(){
        return orderMapperDao.getUnCount();
    }

    /**
     * 获取未处理订单列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public  List<ResultOrder> getUnOrders(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return orderMapperDao.getUnOrders();
    }
    /**
     * 用来服务中判断是否是本人在操作自己的订单
     * @param userName
     * @param orderId
     * @return
     */
    private boolean isSelf(String userName,Integer orderId){
        //首先根据用户名去查询出这个用户的userId 再和这个需要支付的订单中用户id进行比较 如果用户id相同 那么代表支付的是本人订单 否则不是
        User user1 = userService.queryIdByUserName(userName);
        Integer userId = this.queryUserId(orderId);
        if(user1.getUserId()!=userId){
            return false;
        }
        return true;
    }

    /**
     * 获取某个订单的状态
     * @param orderId
     * @return
     */
    public Integer getStatus(Integer orderId){
        return orderMapperDao.getStatus(orderId);
    }

    /**
     * 返回某个定订单的价格
     * @param orderId
     * @return
     */
    public Double getOrderMoney(Integer orderId){
        return orderMapperDao.getOrderMoney(orderId);
    }

    /**
     * 用户查询自己某种状态下的所有订单  根据分页查询
     * @param userName
     * @param status
     * @return
     */
    public List<ResultOrder> getUserOrders(String userName,Integer status,Integer pageNum,Integer pageSize){
        //先根据用户名查询当前用户的id
        Integer userId = userService.queryIdByUserName(userName).getUserId();
        //查询当前用户的订单 根据分页
        PageHelper.startPage(pageNum,pageSize);
        return orderMapperDao.getUserOrders(userId,status);
    }

    /**
     * 用户查询自己某种状态下订单的数量
     * @param userName
     * @param status
     * @return
     */
    public Integer getUserOrderCount(String userName,Integer status){
        //先根据用户名查询当前用户的id
        Integer userId = userService.queryIdByUserName(userName).getUserId();
        return orderMapperDao.getUserOrderCount(userId,status);
    }
}
