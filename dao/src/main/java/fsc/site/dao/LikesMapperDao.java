package fsc.site.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface LikesMapperDao {
    //对网站进行一次点赞操作
    Integer giveLike();
    //获取网站的点赞数
    Integer getLikes();
}
