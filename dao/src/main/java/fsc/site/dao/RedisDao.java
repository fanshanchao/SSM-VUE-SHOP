package fsc.site.dao;

import fsc.site.pojo.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * @author fanshanchao
 * @date 2019/7/18 17:24
 * 用于向redis中存放数据和获取数据的dao
 */
@Repository
public class RedisDao {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 以seckillId为key往redis中存放一个Seckill
     * @param seckill
     */
    public void putSeckill(Seckill seckill){
        //设置接口在redis中缓存一个小时
        redisTemplate.opsForValue().set(seckill.getSeckillId(),seckill,1L, TimeUnit.HOURS);
    }

    /**
     * 以seckillId为key获取存放在redis中的接口信息
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(Integer seckillId){
        return (Seckill) redisTemplate.opsForValue().get(seckillId);
    }

}
