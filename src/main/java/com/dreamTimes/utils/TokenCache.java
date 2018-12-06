package com.dreamTimes.utils;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 基于谷歌guava cache的类
 */
public class TokenCache {

    /*LoadingCache是谷歌guava里面的一个类，CacheBuilder也是里面的一个类*/
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)//初始化缓存项为1000
            .maximumSize(10000)//设置缓存项最大值不超过10000
            .expireAfterAccess(12,TimeUnit.HOURS)//定时回收：过期回收，超过12个小时就会回收掉
            .build(new CacheLoader<String, String>() {//通过builder来构建对象，CacheLoader可以理解为一个内部类
                //当缓存没有值的时候执行load方法
                @Override
                public String load(String s) throws Exception {
                    return "null";//利用空字符串，不能直接用null，底下用equals比较的时候会报空指针
                }
            });

    public static void set(String key,String value){
        localCache.put(key, value);
    }

    public static String get(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)){
                return null;
            }
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return value;
    }

}