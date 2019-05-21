package fsc.site.controller;

import com.alibaba.fastjson.JSONObject;
import fsc.site.pojo.Goods;
import fsc.site.pojo.Response;
import fsc.site.pojo.ResponseGenerator;
import fsc.site.pojo.ResultGoods;
import fsc.site.service.GoodsService;
import fsc.site.shiro.RestShiroFilterFactoryBean;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author fanshanchao
 * @date 2019/4/8 14:20
 */
@RestController
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
    //暂时先用一个静态变量来保存商品图片存放的路径吧
    @Value("${goodImagePath}")
    private String goodImagePath;
    @Autowired
    private GoodsService goodsService;
    /**
     * 查询所有商品类别的接口
     */
    @RequestMapping(value = "goodsTypes",method = RequestMethod.GET)
    public Response goodsTypes(){
        logger.info("获取所有商品类型一次");
        return ResponseGenerator.getSuccessReponseData(goodsService.queryTypes());
    }

    /**
     * 用来添加一个分类
     * @param object
     * @return
     */
    @RequestMapping(value = "goodsTypes",method = RequestMethod.POST)
    @RequiresPermissions("goods:add")
    public Response postGoodsTypes(@RequestBody JSONObject object){
        //获取提交的分类名
        String typeName = object.getString("typeName");
        //查看是否已经存在了重复的分类名
        Integer typeId = goodsService.queryTypeId(typeName);
        if(typeId!=null){
            return ResponseGenerator.getFailureReponse("当前分类名已被使用");
        }
        goodsService.addType(typeName);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 实现商品图片的上传 为了避免文件名重复
     * @param file
     * @return
     */
    @RequestMapping(value = "uploadGoodsImage",method = RequestMethod.POST)
    @RequiresPermissions("goods:add")
    public Response uploadGoodsImage(MultipartFile file){
        if(!file.getOriginalFilename().endsWith(".jpg")){
            return ResponseGenerator.getFailureReponse("只能上传jpg格式");
        }
        if(file.isEmpty()){
            return ResponseGenerator.getFailureReponse("文件为空");
        }
        if(file.getSize()>1024*1024*2){
            return ResponseGenerator.getFailureReponse("请传文件为2M以内的图片");
        }
        //文件名前面加上当前时间毫秒数 避免上传同名的图片导致覆盖
        String newFileName = new Date().getTime()+file.getOriginalFilename();
        String filePath = goodImagePath+"\\"+newFileName;
        System.out.println(filePath);
        //根据这个路径去创建一个文件
        File file1 = new File(filePath);
        //判断父目录是否存在
        if(!file1.getParentFile().exists()){
            //如果不存在则重新创建一个目录
            file1.mkdirs();
        }
        try {
            //进行文件存储
            file.transferTo(file1);
        }catch (IllegalStateException e){
            return ResponseGenerator.getFailureReponse("IO异常，添加图片失败");
        } catch (IOException e) {
            return ResponseGenerator.getFailureReponse("IO异常，添加图片失败");
        }
        return ResponseGenerator.getSuccessReponseData(newFileName);
    }
    /**
     * 添加一个商品
     * @param goods
     * @return
     */
    @RequestMapping(value = "goods",method = RequestMethod.POST)
    @RequiresPermissions("goods:add")
    public Response addGoods(@RequestBody Goods goods){
        goodsService.addGoods(goods);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 返回所有的商品 这里额外用两个参数标识什么类型和什么排序方式 这两个参数不是必需的
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "goods",method = RequestMethod.GET)
    public Response getGoods(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize,
                             @RequestParam(value = "typeId",required = false)Integer typeId,
                             @RequestParam(value = "sortName",required = false)String sortName){
        List<ResultGoods> resultGoods = goodsService.getGoods(page,pageSize,typeId,sortName);
        return ResponseGenerator.getSuccessReponseData(resultGoods);
    }
    /**
     * 更新一个商品
     * @param goodsId
     * @param goods
     * @return
     */
    @RequestMapping(value = "goods/{goodsId}",method = RequestMethod.PUT)
    @RequiresPermissions("goods:update")
    public Response updateGoods(@PathVariable("goodsId")Integer goodsId,@RequestBody Goods goods){
        goods.setGoodsId(goodsId);
        goodsService.updateGoods(goods);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 下架一个商品
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "outGoods/{goodsId}",method = RequestMethod.PUT)
    @RequiresPermissions("goods:update")
    public Response outGoods(@PathVariable("goodsId")Integer goodsId){
        goodsService.outGoods(goodsId);
        return ResponseGenerator.getSuccessReponse();
    }
    /**
     * 重新上架一个商品
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "goGoods/{goodsId}",method = RequestMethod.PUT)
    @RequiresPermissions("goods:update")
    public Response goGoods(@PathVariable("goodsId")Integer goodsId){
        goodsService.goGoods(goodsId);
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 获取商品的数量
     * @return
     */
    @RequestMapping(value = "getGoodsCount",method = RequestMethod.GET)
    public Response getGoodsCount(@RequestParam(value = "typeId",required = false)Integer typeId){
        Integer count = goodsService.getGoodsCount(typeId);
        return ResponseGenerator.getSuccessReponseData(count);
    }

    /**
     * 获取特定商品的数据
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "goods/{goodsId}",method = RequestMethod.GET)
    public Response getGoodsById(@PathVariable("goodsId")Integer goodsId){
        ResultGoods resultGoods = goodsService.getGoodsById(goodsId);
        return ResponseGenerator.getSuccessReponseData(resultGoods);
    }
}
