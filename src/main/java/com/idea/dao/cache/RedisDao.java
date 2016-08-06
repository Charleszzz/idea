package com.idea.dao.cache;

import com.idea.entity.Seckill;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by zhangyong on 16/7/15.
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;
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

}
