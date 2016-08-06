package com.idea.common.redis;

import com.idea.Base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhangyong on 16/8/6.
 */
public class RedisClientTest extends BaseTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void test(){
        RedisClient.Redis redis = redisClient.getResource();

        redis.set("1000","1000");


        System.out.println(redis.get("1000"));
    }


}