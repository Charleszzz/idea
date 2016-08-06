package com.idea.common.redis;

import redis.clients.jedis.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhangyong on 16/8/6.
 */
public class RedisClient {

    private JedisPool jedisPool;

    private String host;

    private int port = 6379;

    //最大连接数,默认为coreSize * 2
    private int maxTotal = Runtime.getRuntime().availableProcessors() * 2;

    //最大空闲数
    private int maxIdle = 2;

    //最小空闲数
    private int minIdle = 1;

    //如果连接耗尽,最大堵塞时间,默认30s
    private int maxWait = 30000;

    private int timeout = 30000;

    private Redis proxy;

    private synchronized void init(){
        if (jedisPool != null){
            return;
        }
        JedisPoolConfig config = buildConfig();
        jedisPool = new JedisPool(config, host, port, timeout);
        proxy = (Redis) Proxy.newProxyInstance(RedisClient.class.getClassLoader(), new Class[]{Redis.class}, new InnerInvocationHandler());
    }

    JedisPoolConfig buildConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setFairness(true);
        return jedisPoolConfig;
    }

    class InnerInvocationHandler implements InvocationHandler{

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Jedis jedis = jedisPool.getResource();
            try {
                return method.invoke(jedis,args);
            }finally {
                jedis.close();
            }
        }
    }

    interface Redis extends JedisCommands,
            MultiKeyCommands, AdvancedJedisCommands, ScriptingCommands,
            BasicCommands, ClusterCommands {

    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Redis getResource(){
        init();
        return proxy;
    }
}
