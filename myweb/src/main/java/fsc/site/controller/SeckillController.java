package fsc.site.controller;

import fsc.site.dto.Exposer;
import fsc.site.exepection.RepeatSeckillExeception;
import fsc.site.exepection.SeckillExeception;
import fsc.site.exepection.SeckillShutDownExeception;
import fsc.site.pojo.*;
import fsc.site.service.SeckillService;
import fsc.site.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/7/6 23:47
 * 秒杀的Controller
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController{
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private UserService userService;
    /**
     * 根据id获取一个秒杀信息
     * @return
     */
    @RequestMapping(value = "{seckillId}/detail",method = RequestMethod.GET)
    public Response getSeckillById(@PathVariable("seckillId") Integer seckillId){
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if(seckill==null){
            return ResponseGenerator.getFailureReponse("没有找到该秒杀");
        }
        return ResponseGenerator.getSuccessReponseData(seckill);
    }

    /**
     * 获取秒杀列表 使用到了分页
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Response getSeckillList(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){
        List<Seckill> seckillList = seckillService.getSeckillList(page,pageSize);
        if(seckillList==null||seckillList.size()==0){
            return ResponseGenerator.getFailureReponse("没有秒杀");
        }
        return ResponseGenerator.getSuccessReponseData(seckillList);
    }

    /**
     * 获取当前系统时间
     * @return
     */
    @RequestMapping(value = "now",method = RequestMethod.GET)
    public Response getNow(){

        return ResponseGenerator.getSuccessReponseData(new Date().getTime());
    }

    /**
     * 获取秒杀地址 必须要登陆才可以获取
     * @param seckillId
     * @return
     */
    @RequestMapping(value = "{seckillId}/exposer",method = RequestMethod.GET)
    public Response getExposer(@PathVariable("seckillId") Integer seckillId){
        Exposer exposer = null;
        try {
            exposer = seckillService.exposerSeckill(seckillId);

        }catch (Exception e){
            exposer = new Exposer(false,seckillId);
            return ResponseGenerator.getSuccessReponseData(exposer);
        }
        return ResponseGenerator.getSuccessReponseData(exposer);
    }

    /**
     * 执行秒杀 要求必须登陆
     * @param seckillId
     * @param md5
     * @return
     */
    @RequestMapping(value = "{seckillId}/{md5}/execution",method = RequestMethod.POST)
    public Response executionSeckill(@PathVariable("seckillId") Integer seckillId,@PathVariable("md5") String md5,@RequestBody AcceptOrder acceptOrder){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据这个用户的用户名去查询userId
        //User user1 = userService.queryIdByUserName(user.getUserName());
        SuccessSeckill successSeckill;
        try {
            successSeckill = seckillService.seckillProcess(seckillId,user.getUserId(),md5,acceptOrder);
        }catch (RepeatSeckillExeception e1){
            return ResponseGenerator.getFailureReponse(e1.getMessage());
        }catch (SeckillShutDownExeception e2){
            return ResponseGenerator.getFailureReponse(e2.getMessage());
        }catch (SeckillExeception e3){
            return ResponseGenerator.getFailureReponse(e3.getMessage());
        }
        return ResponseGenerator.getSuccessReponseData(successSeckill);
    }

    /**
     * 添加一个秒杀
     * @param seckill
     * @return
     */
    @RequiresPermissions("goods:add")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Response addSeckill(@RequestBody Seckill seckill){
        seckillService.addSeckill(seckill);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 获取秒杀的数量
     * @return
     */
    @RequestMapping(value = "count",method = RequestMethod.GET)
    public Response getSeckillCount(){
        return ResponseGenerator.getSuccessReponseData(seckillService.getSeckillCount());
    }

}
