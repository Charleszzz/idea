package com.idea.service;

import com.idea.dto.Exposer;
import com.idea.dto.Execution;
import com.idea.entity.Seckill;
import com.idea.exception.CloseException;
import com.idea.exception.DataReWriteException;
import com.idea.exception.SeckillException;
import com.idea.exception.RepeatException;

import java.util.List;

/**
 * 接口设计原则：站在’使用者‘角度设计接口
 * 三个方面：方法颗粒度，参数，以及返回值（return type和抛出异常）
 * Created by Administrator on 2016/5/20.
 */
public interface SeckillService {

    /**
     * 查询列表
     * @return
     */
    List<Seckill> queryAll();

    /**
     * 通过id查询
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 暴漏秒杀接口地址
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀接口
     * @parm seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    Execution executeSeckill(long seckillId, String md5, long userPhone)
            throws CloseException, DataReWriteException, RepeatException, SeckillException;

    /**
     * 执行秒杀接口 by 存储过程
     * @parm seckillId
     * @param md5
     * @param userPhone
     * @return
     */
    Execution executionSeckillByProcedure(long seckillId, long userPhone, String md5);
}
