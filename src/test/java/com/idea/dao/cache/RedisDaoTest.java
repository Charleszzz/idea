package com.idea.dao.cache;

import com.idea.dao.SeckillDao;
import com.idea.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by zhangyong on 16/7/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private long seckillId = 1000;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception {
        //get and put
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            seckill = seckillDao.queryById(seckillId);
            String result = redisDao.putSeckill(seckill);
            if ("OK".equalsIgnoreCase(result)){
                seckill = redisDao.getSeckill(seckillId);
                if (seckill != null){
                    logger.info("seckill= = {}", seckill.toString());
                }
            }
        }
    }
}