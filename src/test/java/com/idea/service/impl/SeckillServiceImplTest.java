package com.idea.service.impl;

import com.idea.dto.Execution;
import com.idea.dto.Exposer;
import com.idea.entity.Seckill;
import com.idea.exception.CloseException;
import com.idea.exception.DataReWriteException;
import com.idea.exception.RepeatException;
import com.idea.exception.SeckillException;
import com.idea.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zhangyong on 16/5/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class SeckillServiceImplTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills = seckillService.queryAll();
        for (Seckill seckill :
                seckills) {
            logger.debug("{}", seckill);
        }
    }

    @Test
    public void testQueryById() throws Exception {
        long id = 1002;
        Seckill seckill = seckillService.queryById(id);
        logger.debug("{}", seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1002;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.debug("{}", exposer);
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        long id = 1002;
        String md5= "3e7e98740cdd45ba69f8e138f19ee63a";
        long userPhone = 18613777688L;
        Execution execution = seckillService.executeSeckill(id, md5, userPhone);
        logger.debug("{}", execution);
    }

    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1002;
        long userPhone = 11122223333L;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(id);
            if (exposer.isExposered()) {
                Execution execution = seckillService.executeSeckill(exposer.getSeckillId(), exposer.getMd5(), userPhone);
                logger.info("{}", execution);
            } else {
                throw new CloseException("seckill closed!");
            }
        }catch (CloseException e){
            logger.warn(e.getMessage());
        }catch (RepeatException e){
            logger.warn(e.getMessage());
        }catch (DataReWriteException e){
            logger.warn(e.getMessage());
        }catch (SeckillException e){
            logger.warn(e.getMessage());
        }
    }

    @Test
    public void testExecutionSeckillByProcedure() throws Exception {
        long seckillId = 1002;
        long userPhone = 19900000000l;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposered()){
            String md5 = exposer.getMd5();
            Execution execution = seckillService.executionSeckillByProcedure(seckillId, userPhone, md5);
            logger.info(execution.getStateInfo());
        }
    }
}