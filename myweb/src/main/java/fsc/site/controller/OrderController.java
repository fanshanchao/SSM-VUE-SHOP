package fsc.site.controller;

import com.github.pagehelper.PageHelper;
import fsc.site.conf.SpringConfig;
import fsc.site.pojo.*;
import fsc.site.service.OrderService;
import fsc.site.service.UserService;
import net.sf.saxon.functions.Put;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/11 18:03
 */
@RestController
public class OrderController {
    //用于记录日志
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    /**
     * 创建一个订单
     * @param acceptOrder
     * @return
     */
    @RequestMapping(value = "orders",method = RequestMethod.POST)
    public Response createOrders(@RequestBody AcceptOrder acceptOrder){
        //注意这里的userId是根据认证用户的认证信息去获取的 不能使用前端传过来的 因为样不安全 会导致认证用户给其他商城会员下订单
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据这个用户的用户名去查询userId
        User user1 = userService.queryIdByUserName(user.getUserName());
        //设置当前订单的userId为这个id
        acceptOrder.setUserId(user1.getUserId());
        //进行添加订单操作
        Integer orderId= orderService.addOrder(acceptOrder,acceptOrder.getOrderItems());
        logger.info("订单"+orderId+"创建成功！用户："+user.getUserName()+"时间："+new Date());
        //并且将订单id给返回
        return ResponseGenerator.getSuccessReponseData(orderId);
    }

    /**
     * 根据分页返回所有订单详情信息
     * @return
     */
    @RequestMapping(value ="orders",method = RequestMethod.GET)
    @RequiresPermissions("order:list")
    public Response getOrder(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        List<ResultOrder> orders = orderService.queryOrders(page,pageSize);
        return ResponseGenerator.getSuccessReponseData(orders);
    }

    /**
     * 获取订单的总数量
     * @return
     */
    @RequestMapping(value = "getOrderCount",method = RequestMethod.GET)
    public Response getOrderCount(){
        return ResponseGenerator.getSuccessReponseData(orderService.getOrderCount());
    }

    /**
     * 用户查询自己某种状态下的所有订单
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    @RequestMapping(value ="userOrders",method = RequestMethod.GET)
    public Response getUserOrder(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize,
                                 @RequestParam(value = "status",required = false) Integer status){
        //获取当前用户的用户名
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logger.info(user.getUserName()+"获取类型为"+status+"的所有订单");
        //进行查询
        List<ResultOrder> list = orderService.getUserOrders(user.getUserName(),status,page,pageSize);
        //返回数据
        return ResponseGenerator.getSuccessReponseData(list);
    }

    /**
     * 返回用户某种状态下的订单数量
     * @param status
     * @return
     */
    @RequestMapping(value ="userOrderCount",method = RequestMethod.GET)
    public Response getUserOrderCount(@RequestParam(value = "status",required = false) Integer status){
        //获取当前用户的用户名
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //进行查询
        Integer result = orderService.getUserOrderCount(user.getUserName(),status);
        return ResponseGenerator.getSuccessReponseData(result);
    }

    /**
     * 对一个订单进行支付
     * @param orderId
     * @return
     */
    @RequestMapping(value = "payOrders/{orderId}",method = RequestMethod.POST)
    public Response payOrder(@PathVariable("orderId") Integer orderId){
        //先获取当前用户的信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //进行支付操作
        if(orderService.payOrder(orderId,user.getUserName())){
            return ResponseGenerator.getSuccessReponse();
        }else{
            return ResponseGenerator.getFailureReponse("非法行为，请停止操作");
        }
    }

    /**
     * 对订单进行发货操作
     * @param orderId
     * @return
     */
    @RequestMapping(value = "dealOrders/{orderId}",method = RequestMethod.PUT)
    @RequiresPermissions("order:update")
    public Response dealOrder(@PathVariable("orderId") Integer orderId){
        orderService.dealOrder(orderId);
        return ResponseGenerator.getSuccessReponse();
    }
    /**
     * 管理员对订单进行关闭操作
     * @param orderId
     * @return
     */
    @RequestMapping(value = "openOrders/{orderId}",method = RequestMethod.PUT)
    @RequiresPermissions("order:delete")
    public Response openOrder(@PathVariable("orderId") Integer orderId){
        orderService.openOrder(orderId);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 用户取消自己的订单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "cancelOrders/{orderId}",method = RequestMethod.PUT)
    public Response cancelOrder(@PathVariable("orderId") Integer orderId){
        //先获取当前用户的信息
        Subject subject = SecurityUtils.getSubject();
        User user  = (User) subject.getPrincipal();
        if(orderService.userOpenOrder(user.getUserName(),orderId)){
            return ResponseGenerator.getSuccessReponse();
        }else {
            return ResponseGenerator.getFailureReponse("非法行为，请停止操作");
        }
    }

    /**
     * 用户确认收货自己的订单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "confirmOrders/{orderId}",method = RequestMethod.PUT)
    public Response confirmOrder(@PathVariable("orderId") Integer orderId){
        //先获取当前用户的信息
        Subject subject = SecurityUtils.getSubject();
        User user  = (User) subject.getPrincipal();
        if(orderService.confirmOrder(user.getUserName(),orderId)){
            return ResponseGenerator.getSuccessReponse();
        }else{
            return ResponseGenerator.getFailureReponse("非法行为，请停止操作");
        }
    }
    /**
     * 根据分页返回所有未处理订单详情信息
     * @return
     */
    @RequestMapping(value ="unOrders",method = RequestMethod.GET)
    @RequiresPermissions("order:update")
    public Response getUnOrder(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){
        List<ResultOrder> orders = orderService.getUnOrders(page,pageSize);
        return ResponseGenerator.getSuccessReponseData(orders);
    }
    /**
     * 获取未处理订单的总数量
     * @return
     */
    @RequestMapping(value = "getUnOrderCount",method = RequestMethod.GET)
    public Response getUnOrderCount(){
        return ResponseGenerator.getSuccessReponseData(orderService.getUnCount());
    }

    /**
     * 返回订单的状态
     * @param orderId
     * @return
     */
    @RequestMapping(value = "orderStatus/{orderId}",method = RequestMethod.GET)
    public Response getOrderStatus(@PathVariable("orderId") Integer orderId){
        Integer status = orderService.getStatus(orderId);
        return ResponseGenerator.getSuccessReponseData(status);
    }
    /**
     * 返回某个订单的价格
     */
    @RequestMapping(value = "orderMoney/{orderId}",method = RequestMethod.GET)
    public Response getOrderMoney(@PathVariable("orderId") Integer orderId){
        Double money = orderService.getOrderMoney(orderId);
        return ResponseGenerator.getSuccessReponseData(money);
    }
}
