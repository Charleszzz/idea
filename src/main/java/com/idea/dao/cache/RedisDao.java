package com.idea.dao.cache;

import com.idea.entity.Seckill;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created by zhangyong on 16/7/15.
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;
    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port){
        jedisPool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(Long seckillId){
        //redis逻辑操作
        try{
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //没有实现内部序列化
                //get->byte[]->反序列化->Object(Seckill)
                //采用自定义序列化
                //protostuff: pojo
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //被反序列化
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public String putSeckill(Seckill seckill){
        // set Object(Seckill) -> 序列化 -> byte[]
        try{
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //缓存超时  1小时
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.shutdown();
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Seckill getSeckillWithSpring(final long seckillId){
        return redisTemplate.execute(new RedisCallback<Seckill>() {
            public Seckill doInRedis(RedisConnection redisConnection) throws DataAccessException {
                String key = "seckill:" + seckillId;
                try {
                    byte[] bytes = redisConnection.get(key.getBytes("UTF-8"));
                    if (bytes != null){
                        Seckill seckill = schema.newMessage();
                        ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                        return seckill;
                    }
                } catch (UnsupportedEncodingException e) {
                   logger.error(e.getMessage(), e);
                }
                return null;
            }
        });
    }

    public void putSeckillWithSpring(final Seckill seckill){
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                String key = "secill:"+seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema,LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                try {
                    redisConnection.setEx(key.getBytes("UTF-8"), timeout, bytes);
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            }
        });
    }
}
