package com.dreamTimes.commons;

import com.dreamTimes.utils.PropertiesUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    private static JedisPool pool;

    private static String ip = PropertiesUtils.getKey("redis.ip");
    private static Integer port = Integer.parseInt(PropertiesUtils.getKey("redis.port"));
    private static Integer maxTotal = Integer.parseInt(PropertiesUtils.getKey("redis.max.total"));
    private static Integer maxIdle = Integer.parseInt(PropertiesUtils.getKey("redis.max.idle"));
    private static Integer minIdle = Integer.parseInt(PropertiesUtils.getKey("redis.min.idle"));
    private static boolean JedisBorrow = Boolean.parseBoolean(PropertiesUtils.getKey("redis.test.Borrow"));
    private static boolean JedisReturn = Boolean.parseBoolean(PropertiesUtils.getKey("redis.test.Return"));


//    初始化jedis连接池
    private static void init(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
//        设置当连接池耗尽的时候，如果是false的话会发生异常，默认为true，true会进行阻塞，知道超时
        config.setBlockWhenExhausted(true);
        config.setTestOnBorrow(JedisBorrow);
        config.setTestOnReturn(JedisReturn);

        pool = new JedisPool(config,ip,port,1000*2);
    }

    static {
        init();
    }

//    从jedis中获取一个连接
    public static Jedis getJedis(){
        return pool.getResource();
    }

//    将jedis实例返回连接池中
    public static void returnJedis(Jedis jedis){
        pool.returnResource(jedis);
    }

//    发生异常时，将资源进行返回
    public static void returnBrokenJedis(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

}
