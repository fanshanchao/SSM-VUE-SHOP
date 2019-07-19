package fsc.site.service;

import com.github.pagehelper.PageHelper;
import fsc.site.dao.RedisDao;
import fsc.site.dao.SeckillItemMapperDao;
import fsc.site.dao.SeckillMapperDao;
import fsc.site.dto.Exposer;
import fsc.site.exepection.RepeatSeckillExeception;
import fsc.site.exepection.SeckillExeception;
import fsc.site.exepection.SeckillShutDownExeception;
import fsc.site.pojo.AcceptOrder;
import fsc.site.pojo.Seckill;
import fsc.site.pojo.SuccessSeckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanshanchao
 * @date 2019/7/6 19:25
 * 获取秒杀信息的服务类
 */
@Service
public class SeckillService {
    @Autowired
    private SeckillMapperDao seckillMapperDao;

    @Autowired
    private SeckillItemMapperDao seckillItemMapperDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisDao redisDao;

    private String salt = "dasdasd*7%^#@!^$#__+!~~";
    /**
     * 生成一个md5值的方法 使用一个随机盐来加密
     * @param seckillId
     * @return
     */
    private String getMd5(Integer seckillId){
        String base = seckillId+"/"+salt;
        String md5 =  DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 根据id获取一个秒杀的信息
     * @param seckillId
     * @return
     */
    public Seckill getSeckillById(Integer seckillId){
        return seckillMapperDao.getSeckill(seckillId);
    }

    /**
     * 获取秒杀列表
     * @return
     */
    public List<Seckill> getSeckillList(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return seckillMapperDao.getSeckillList();
    }

    /**
     * 添加一个秒杀
     * @param seckill
     * @return
     */
    public Integer addSeckill(Seckill seckill){
        return seckillMapperDao.addSeckill(seckill);
    }

    /**
     * 暴露秒杀地址的服务
     * @param seckillId
     * @return
     */
    public  Exposer exposerSeckill(Integer seckillId){
        Seckill seckill = null;
        seckill = redisDao.getSeckill(seckillId);
        //如果在redis没有查到 那么从数据库中查找
        if(seckill==null){
            //先根据秒杀id去查询是否有这个秒杀
            seckill = seckillMapperDao.getSeckill(seckillId);
            //判断是否查询到了
            if(seckill==null){
                return new Exposer(false,seckillId);
            }
            //将查询到的seckill信息存放到redis中
            redisDao.putSeckill(seckill);
        }
        //代表查询到了
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();
        //判断当前时间和这个秒杀时间进行匹配
        if(now.getTime()>endTime.getTime()||now.getTime()<startTime.getTime()){
            return new Exposer(false,seckillId,now.getTime(),startTime.getTime(),endTime.getTime());
        }
        return new Exposer(true,getMd5(seckillId),seckillId);
    }

    /**
     * 执行秒杀操作的服务 使用事务
     * @param seckillId
     * @param userId
     * @param md5
     */
    @Transactional
    public SuccessSeckill executeSeckill(Integer seckillId, Integer userId, String md5, AcceptOrder acceptOrder)throws SeckillExeception,RepeatSeckillExeception,SeckillShutDownExeception{
        //验证md5值是否正确
        if(!getMd5(seckillId).equals(md5)){
            throw new SeckillExeception("秒杀异常");
        }
        try {
            //开始执行秒杀
            Integer insertCount = seckillItemMapperDao.addSeckillItem(seckillId,userId,new Date());
            if(insertCount <= 0){
                //代表秒杀重复秒杀
                throw new RepeatSeckillExeception("重复秒杀");
            }{
                //减少秒杀库存
                Integer updateCount = seckillMapperDao.reduceRepertory(seckillId,new Date());
                if(updateCount <= 0){
                    throw new SeckillShutDownExeception("秒杀结束");
                }
                //查询到成功插入的插入明细
                SuccessSeckill successSeckill = seckillItemMapperDao.getSuccessSeckill(seckillId,userId);
                //添加订单到订单表中 设置订单的创建时间和价格
                acceptOrder.setCreateDate(new Date());
                acceptOrder.setPayMoney(successSeckill.getSeckill().getSeckillPrice());
                //注意这里要设置订单的商品id是秒杀中那个商品的id  防止用户传入恶意的商品id
                acceptOrder.getOrderItems().get(0).setGoodsId(successSeckill.getSeckill().getGoods().getGoodsId());
                //也设置商品价格为为秒杀价格
                acceptOrder.getOrderItems().get(0).setGoodsPrice(successSeckill.getSeckill().getSeckillPrice());
                //开始添加订单
                Integer orderId = orderService.addSeckillOrder(acceptOrder);
                //设置成功明细的订单id 返回给前台
                successSeckill.setOrderId(orderId);
                return successSeckill;
            }
        }catch (SeckillShutDownExeception e1){
            throw e1;
        }catch (RepeatSeckillExeception e2){
            throw e2;
        }catch (Exception e){
            //将编译异常转换成运行异常
            throw new SeckillExeception("秒杀异常");
        }
    }

    /**
     * 通过存储过程添加秒杀
     * @param seckillId
     * @param userId
     * @param md5
     * @param acceptOrder
     * @return
     */
    public SuccessSeckill seckillProcess(Integer seckillId, Integer userId, String md5, AcceptOrder acceptOrder)throws SeckillExeception,RepeatSeckillExeception,SeckillShutDownExeception{
        //验证md5值是否正确
        if(!getMd5(seckillId).equals(md5)){
            throw new SeckillExeception("接口地址不正确，秒杀异常");
        }
        //查看这个秒杀的信息
        Seckill seckill = null;
        //先从redis上查找这个秒杀的信息
        seckill = redisDao.getSeckill(seckillId);
        if(seckill==null){
            //redis中没有再从数据库中获取
            seckill = seckillMapperDao.getSeckill(seckillId);
            if(seckill==null){
                //代表没有这个秒杀
                throw new SeckillExeception("没有这个秒杀");
            }
            //将秒杀信息存入到redis中
            redisDao.putSeckill(seckill);
        }
        //调用存储过程存储信息
        Date killTime = new Date();
        Map<String,Object> map = new HashMap<>();
        //将存储过程需要的参数插入到map中去
        map.put("seckillId",seckillId);
        map.put("userId",userId);
        map.put("createTime",killTime);
        map.put("result",null);
        map.put("receiverName",acceptOrder.getReceiverName());
        map.put("mobile",acceptOrder.getMobile());
        map.put("address",acceptOrder.getAddress());
        map.put("message",acceptOrder.getMessage());
        map.put("payMoney",seckill.getSeckillPrice());
        map.put("goodsId",seckill.getGoodsId());
        seckillMapperDao.seckillProcess(map);
        //获取存储过程执行结果
        Integer result = null;
        result = (Integer) map.get("result");
        if(result==null||result==-2){
            throw new SeckillExeception("秒杀异常 result的值是"+result);
        }else if(result == -1){
            throw new RepeatSeckillExeception("重复秒杀");
        }else if(result == -3){
            throw new SeckillShutDownExeception("秒杀已结束");
        }
        //返回秒杀成功的订单id
        SuccessSeckill successSeckill = new SuccessSeckill();
        successSeckill.setOrderId(result);
        return successSeckill;
    }

    /**
     * 获取秒杀的数量
     * @return
     */
    public Integer getSeckillCount(){

        return seckillMapperDao.getSeckillCount();
    }

}
