package fsc.site.controller;

import fsc.site.pojo.Response;
import fsc.site.pojo.ResponseGenerator;
import fsc.site.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanshanchao
 * @date 2019/5/7 22:29
 */
@RestController
public class LikesController {
    @Autowired
    private LikesService likesService;

    /**
     * 对网站进行一次点赞操作
     * @return
     */
    @RequestMapping(value = "likes",method = RequestMethod.POST)
    public Response giveLikes(){
        likesService.giveLike();
        return ResponseGenerator.getSuccessReponse();
    }

    /**
     * 获取网站的点赞数
     * @return
     */
    @RequestMapping(value = "likes",method = RequestMethod.GET)
    public Response getLikes(){
        Integer likes = likesService.getLikes();
        return ResponseGenerator.getSuccessReponseData(likes);
    }
}
