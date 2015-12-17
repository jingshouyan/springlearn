package com.jing.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;


import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {
	
	@Value("${redis.hostname:127.0.0.1}")
	private String hostname;
	@Value("${redis.port:6379}")
	private int port;
	@Value("${redis.password:}")
	private String password;
	@Value("${redis.usePool:true}")
	private boolean usePool;
	@Value("${redis.maxTotal:300}")
	private int maxTotal;
	@Value("${redis.maxIdle:50}")	
	private int maxIdle;
	@Value("${redis.maxWaitMillis:1000}")
	private long maxWaitMillis;
	@Value("${redis.testOnBorrow:false}")
	private boolean testOnBorrow;
	@Bean
	public RedisConnectionFactory redisCF(){
		JedisConnectionFactory cf = new JedisConnectionFactory();		
		cf.setHostName(hostname);
		cf.setPort(port);
		cf.setPassword(password);
		cf.setUsePool(usePool);		
		return cf;
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setTestOnBorrow(testOnBorrow);
		return config;
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory cf){
		return new StringRedisTemplate(cf);
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory cf){
		RedisTemplate redis = new RedisTemplate();
		redis.setConnectionFactory(cf);
//		redis.setKeySerializer(new StringRedisSerializer());
//		redis.setValueSerializer(new Jackson2JsonRedisSerializer<IRedisSupport>(IRedisSupport.class));
		return redis;
	}

}
