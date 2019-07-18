package fsc.site.dao;

import fsc.site.pojo.Seckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用于实现秒杀功能的dao
 */
@Repository
public interface SeckillMapperDao {

    //创建一个秒杀
    Integer addSeckill(Seckill seckill);

    //获取秒杀列表
    List<Seckill> getSeckillList();

    //根据id获取一个秒杀
    Seckill getSeckill(Integer seckillId);

    //减少秒杀库存
    Integer reduceRepertory(@Param("seckillId")Integer seckillId, @Param("killTime")Date killTime);

    //获取秒杀的数量
    Integer getSeckillCount();

    //使用存储过程执行一个秒杀
    Integer seckillProcess(Map<String,Object> map);

}
