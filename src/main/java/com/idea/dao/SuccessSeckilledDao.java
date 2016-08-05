package com.idea.dao;

import com.idea.entity.SuccessSeckilled;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SuccessSeckilled借口
 * Created by Administrator on 2016/5/19.
 */
public interface SuccessSeckilledDao {

    /**
     * 新增秒杀成功明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessSeckilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 通过seckillId查询秒杀成功明细列表
     * @param seckillId
     * @return
     */
    SuccessSeckilled queryBySeckillId(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
