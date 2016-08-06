package com.idea.dao.cache;

import com.idea.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by zhangyong on 16/8/6.
 */
@Repository
public class SeckillDao {

    @Autowired
    private RedisTemplate<String, Seckill> template;

    private ValueOperations<String, Seckill> operations;

    @PostConstruct
    private void init(){
        //这里设置value的序列化方式为JacksonJsonRedisSerializer
        template.setValueSerializer(new JacksonJsonRedisSerializer<Seckill>(Seckill.class));
        operations = template.opsForValue();
    }

    public void set(String key, Seckill seckill){
        operations.set(key, seckill);
    }

    public Seckill get(String key){
        return operations.get(key);
    }

}
