package fsc.site.service;

import fsc.site.dao.LikesMapperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fanshanchao
 * @date 2019/5/7 22:26
 */
@Service
public class LikesService {
    @Autowired
    private LikesMapperDao likesMapperDao;

    /**
     * 对网站进行一次点赞操作
     * @return
     */
    public Integer giveLike(){
        return likesMapperDao.giveLike();
    }

    /**
     * 获取网站的点赞数
     * @return
     */
    public Integer getLikes(){
        return likesMapperDao.getLikes();
    }
}
