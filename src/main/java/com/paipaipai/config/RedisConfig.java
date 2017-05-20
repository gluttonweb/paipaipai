package com.paipaipai.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by weibo on 2017/1/11.
 */
@Configuration
//@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    @Bean(name = "jedis.pool")
    @Autowired
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
                               @Value("${jedis.pool.host}") String host,
                               @Value("${jedis.pool.port}") int port) {
        return new JedisPool(config, host, port);
    }

    @Bean(name = "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig(@Value("${jedis.pool.config.maxTotal}") int maxTotal,
                                           @Value("${jedis.pool.config.maxIdle}") int maxIdle,
                                           @Value("${jedis.pool.config.maxWaitMillis}") int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }
//    @Bean
//    public KeyGenerator wiselyKeyGenerator(){
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//
//    }

//    @Bean
//    public CacheManager cacheManager(
//            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//        return new RedisCacheManager(redisTemplate);
//    }

//    @Bean
//    public RedisTemplate<String, String> redisTemplate(
//            RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory cf = new JedisConnectionFactory();
//        cf.setHostName("127.0.0.1");
//        cf.setPort(6379);
//        cf.setPassword("superman");
//        cf.afterPropertiesSet();
//        return cf;
//    }


//    @Bean
//    public RedisTemplate getRedisTemplate() {
//        RedisConnectionFactory redisConnectionFactory = redisConnectionFactory();
//        RedisTemplate<String, String> redisTemplate = this.redisTemplate(redisConnectionFactory);
//        RedisTemplate rt = new RedisTemplate();
//        rt.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }
}