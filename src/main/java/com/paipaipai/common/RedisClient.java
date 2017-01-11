package com.paipaipai.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by weibo on 2017/1/11.
 */
@Component
public class RedisClient {

    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public void set(String key, Object t) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key.getBytes(), SerializationUtil.serialize(t));
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public String get(String key) throws Exception {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

//    public byte[] get(byte[] key,class Class<T> t) throws Exception  {
//
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            byte[]  value = jedis.get(key);
//            T object = (T)SerializationUtil.unserialize(value);
//            return
//        } finally {
//            //返还到连接池
//            jedis.close();
//        }
//    }

}
