package com.paipaipai.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by weibo1 on 2017/1/12.
 */
@Component
public class RedisClient {

    @Autowired
    private JedisPool jedisPool;

    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

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

    public void set(final String key, Object value) {
//        ValueOperations opsForValue = redisTemplate.opsForValue();
//        opsForValue.set(key, value);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        try {
            jedisConnectionFactory.setHostName("127.0.0.1");
            jedisConnectionFactory.setPort(6379);
            jedisConnectionFactory.afterPropertiesSet();
            final RedisTemplate<String, String> redisTemplate = this.redisTemplate(jedisConnectionFactory);
            final byte[] vbytes = SerializationUtil.serialize(value);
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.set(redisTemplate.getStringSerializer().serialize(key), vbytes);
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedisConnectionFactory.getConnection().close();
        }
    }


    public <T> T get(final String key, Class<T> elementType) {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        try {
            jedisConnectionFactory.setHostName("127.0.0.1");
            jedisConnectionFactory.setPort(6379);
            jedisConnectionFactory.afterPropertiesSet();
            final RedisTemplate<String, String> redisTemplate = this.redisTemplate(jedisConnectionFactory);
            return redisTemplate.execute(new RedisCallback<T>() {
                @Override
                public T doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    byte[] keybytes = redisTemplate.getStringSerializer().serialize(key);
                    if (connection.exists(keybytes)) {
                        byte[] valuebytes = connection.get(keybytes);
                        @SuppressWarnings("unchecked")
                        T value = (T) SerializationUtil.unserialize(valuebytes);
                        return value;
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedisConnectionFactory.getConnection().close();
        }
    }

}