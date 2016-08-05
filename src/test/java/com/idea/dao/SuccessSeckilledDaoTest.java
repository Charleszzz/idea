package com.idea.dao;

import com.idea.entity.SuccessSeckilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessSeckilledDaoTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SuccessSeckilledDao successSeckilledDao;

    @Test
    public void insertSuccessSeckilled() throws Exception {
        long id = 1000;
        long userPhone= 13112345678L;
        int rows = successSeckilledDao.insertSuccessSeckilled(1000,userPhone);
        logger.debug("rows = {}", rows);
    }

    @Test
    public void queryBySeckillId() throws Exception {
        long id = 1000;
        long userPhone = 13112345678L;
        SuccessSeckilled successSeckilled = successSeckilledDao.queryBySeckillId(id, userPhone);
        logger.debug("successSeckilled = {}", successSeckilled);

        //successSeckilled = SuccessSeckilled{seckillId=1000, userPhone=13112345678, state=1, createTime=Fri May 20 23:34:22 CST 2016, seckill=Seckill{seckillId=1000, name='100元秒杀iPhone 6s', number=1000, startTime=Fri Jan 01 00:00:00 CST 2016, endTime=Sat Jan 02 00:00:00 CST 2016, createTime=Thu May 19 13:35:59 CST 2016}}
    }

}