package com.idea.dao;

import com.idea.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * seckill借口
 * Created by Administrator on 2016/5/19.
 */
public interface SeckillDao {

    /**
     * 通过偏移量查询seckill
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 通过id查询秒杀seckill
     * @param seckillId
     * @return
     */
    Seckill queryById(@Param("seckillId") long seckillId);

    /**
     * 减库存
     * @param seckillId
     * @param seckillDateTime
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("seckillDateTime") Date seckillDateTime);

    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);

}
