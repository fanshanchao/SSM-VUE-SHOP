package fsc.site.dao;

import fsc.site.pojo.SuccessSeckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 秒杀明细的mapper
 */
@Repository
public interface SeckillItemMapperDao {
    /**
     * 插入一个秒杀明细 返回0则代表秒杀失败 这里
     * @param seckillId
     * @param userId
     * @return
     */
    Integer addSeckillItem(@Param("seckillId")Integer seckillId, @Param("userId") Integer userId, @Param("createTime")Date createTime);

    /**
     * 获取一个秒杀明细
     * @param seckillId
     * @param userId
     * @return
     */
    SuccessSeckill getSuccessSeckill(@Param("seckillId")Integer seckillId,@Param("userId") Integer userId);

    /**
     * 获取所有的秒杀明细
     * @return
     */
    List<SuccessSeckill> getSuccessSeckillList();
}
