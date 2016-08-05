package com.idea.web;

import com.idea.dto.Execution;
import com.idea.dto.Exposer;
import com.idea.dto.SeckillResult;
import com.idea.entity.Seckill;
import com.idea.enums.SeckillState;
import com.idea.exception.CloseException;
import com.idea.exception.DataReWriteException;
import com.idea.exception.RepeatException;
import com.idea.exception.SeckillException;
import com.idea.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangyong on 16/5/27.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> seckillList = seckillService.queryAll();
        logger.info("{}", seckillList);
        model.addAttribute("seckillList", seckillList);
        return "list";  //WEB-INF/jsp/list.jsp
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String queryById(@PathVariable("seckillId") Long seckillId, Model model){
        if (seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.queryById(seckillId);
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax json
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            result = new SeckillResult<Exposer>(false, ex.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<Execution> execute(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5, @CookieValue(value = "userPhone" , required = false) Long userPhone){
        if (userPhone == null){
            return new SeckillResult<Execution>(false,"未注册!");
        }
        SeckillResult<Execution> result;
        Execution execution;
        execution = seckillService.executionSeckillByProcedure(seckillId, userPhone, md5);
        result = new SeckillResult<Execution>(true, execution);
        return result;
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }

}
