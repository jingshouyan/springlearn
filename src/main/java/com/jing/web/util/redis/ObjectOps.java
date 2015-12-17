package com.jing.web.util.redis;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: ObjectOps
* @Description: redis 对象操作类
* @author 靖守彦 jingshouyan@126.com
* @date 2015年12月9日 下午3:46:36
*
 */
@Component
public class ObjectOps {
	
	@Resource
	private RedisTemplate<String, IRedisSupport> redisTemplate;
	
	/**
	 * 
	* @Title: save 
	* @Description: 保存对象
	* @param @param obj    需要保存到redis中的对象
	* @return void    返回类型 
	* @throws
	 */
	public void save(final IRedisSupport obj){
		redisTemplate.execute(new RedisCallback<IRedisSupport>() {

			public IRedisSupport doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(obj.getClass().getName()+":"+obj.uid());
				byte[] value = (new Jackson2JsonRedisSerializer<IRedisSupport>(IRedisSupport.class)).serialize(obj);
				conn.set(key, value);
				return null;
			}
		});
	}
	
	/**
	 * 
	* @Title: get 
	* @Description: 获取对象
	* @param @param uid   对象标示
	* @param @param clazz 对象类型
	* @param @return    设定文件 
	* @return T    返回类型 
	* @throws
	 */
	public <T extends IRedisSupport> T get(final String uid,final Class<T> clazz){
		return redisTemplate.execute(new RedisCallback<T>() {

			public T doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(clazz.getName()+":"+uid);
				if(conn.exists(key)){
					byte[] value = conn.get(key);
					T t =(new Jackson2JsonRedisSerializer<T>(clazz)).deserialize(value);
					return t;
				}
				return null;
			}
		});
	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除对象 
	* @param @param uid   对象标示
	* @param @param clazz 对象类型
	* @return void    返回类型 
	* @throws
	 */
	public <T extends IRedisSupport> void delete(final String uid,final Class<T> clazz){
		redisTemplate.execute(new RedisCallback<IRedisSupport>() {

			public IRedisSupport doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(clazz.getName()+":"+uid);
				conn.del(key);
				return null;
			}
		});
	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除对象
	* @param @param obj    要删除的对象
	* @return void    返回类型 
	* @throws
	 */
	public void delete(IRedisSupport obj){
		this.delete(obj.uid(), obj.getClass());
	}
}
