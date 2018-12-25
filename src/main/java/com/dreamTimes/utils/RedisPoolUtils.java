package com.dreamTimes.utils;

import com.dreamTimes.commons.RedisPool;
import redis.clients.jedis.Jedis;

public class RedisPoolUtils {

    /**
     * redis常用api的封装
     */

    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value);
        }catch (Exception e){
            e.printStackTrace();
            RedisPool.returnBrokenJedis(jedis);
        }
        RedisPool.returnJedis(jedis);
        return result;
    }

    public static String setEx(String key,String value,Integer timeout){
        Jedis jedis = null;
        String result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,timeout,value);
        }catch (Exception e){
            e.printStackTrace();
            RedisPool.returnBrokenJedis(jedis);
        }
        RedisPool.returnJedis(jedis);
        return result;
    }

//    设置key的有效期
    public static Long expire(String key,Integer timeout){
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.expire(key,timeout);
        }catch (Exception e){
            e.printStackTrace();
            RedisPool.returnBrokenJedis(jedis);
        }
        RedisPool.returnJedis(jedis);
        return result;
    }

//      获取值
    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
            RedisPool.returnBrokenJedis(jedis);
        }
        RedisPool.returnJedis(jedis);
        return result;
    }

//    删除一个key
    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        }catch (Exception e){
            e.printStackTrace();
            RedisPool.returnBrokenJedis(jedis);
        }
        RedisPool.returnJedis(jedis);
        return result;
    }
}
