package com.jing.web.util.redis;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ListOps {


	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	

	/**
	 * 
	* @Title: lpush 
	* @Description: 左进
	* @param @param key
	* @param @param value
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long lpush(String key, String value) {		
		return stringRedisTemplate.opsForList().leftPush(IRedisSupport.KEY_PREFIX + key, value);
	}

	/**
	 * 
	* @Title: lpop 
	* @Description: 左出
	* @param @param key
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String lpop(String key) {
		return stringRedisTemplate.opsForList().leftPop(IRedisSupport.KEY_PREFIX + key);
	}

	/**
	 * 
	* @Title: rpush 
	* @Description: 右入
	* @param @param key
	* @param @param value
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long rpush(String key, String value) {
		return stringRedisTemplate.opsForList().rightPush(IRedisSupport.KEY_PREFIX + key, value);
	}

	/**
	 * 
	* @Title: rpop 
	* @Description: 右出
	* @param @param key
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String rpop(String key) {
		return stringRedisTemplate.opsForList().rightPop(IRedisSupport.KEY_PREFIX + key);
	}

	/**
	 * 
	* @Title: length 
	* @Description: 队列长度
	* @param @param key
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long length(String key) {
		return stringRedisTemplate.opsForList().size(IRedisSupport.KEY_PREFIX + key);
	}

	/**
	 * 
	* @Title: range 
	* @Description: 范围检索
	* @param @param key
	* @param @param start
	* @param @param end
	* @param @return    设定文件 
	* @return List<String>    返回类型 
	* @throws
	 */
	public List<String> range(String key, int start, int end) {
		return stringRedisTemplate.opsForList().range(IRedisSupport.KEY_PREFIX + key, start, end);
	}

	/**
	 * 
	* @Title: remove 
	* @Description: 移除
	* @param @param key
	* @param @param i
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void remove(String key, long i, String value) {
		stringRedisTemplate.opsForList().remove(IRedisSupport.KEY_PREFIX + key, i, value);
	}

	/**
	 * 
	* @Title: index 
	* @Description: 检索
	* @param @param key
	* @param @param index
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String index(String key, long index) {
		return stringRedisTemplate.opsForList().index(IRedisSupport.KEY_PREFIX + key, index);
	}

	/**
	 * 
	* @Title: set 
	* @Description: 置值
	* @param @param key
	* @param @param index
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void set(String key, long index, String value) {
		stringRedisTemplate.opsForList().set(IRedisSupport.KEY_PREFIX + key, index, value);
	}

	/**
	 * 
	* @Title: trim 
	* @Description: 裁剪
	* @param @param key
	* @param @param start
	* @param @param end    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void trim(String key, long start, int end) {
		stringRedisTemplate.opsForList().trim(IRedisSupport.KEY_PREFIX + key, start, end);
	}
}
