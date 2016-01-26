/**
 * Project Name:springlearn
 * File Name:RedisUtil.java
 * Package Name:com.jing.web.util.redis
 * Date:2016年1月23日下午1:16:22
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;

/**
 * ClassName:RedisUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月23日 下午1:16:22 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class RedisUtil {
	@Autowired
	private StringRedisTemplate srt;
	
	public void setObject(IRedisSupport obj){		
		String key=IRedisSupport.KEY_PREFIX+obj.getClass().getName()+":"+obj.uid();
		String value=JSON.toJSONString(obj);
		srt.opsForValue().set(key, value);
	}
	
	public <T extends IRedisSupport> T getObject(String uid,Class<T> clazz){
		String key=IRedisSupport.KEY_PREFIX+clazz.getName()+":"+uid;
		String value = srt.opsForValue().get(key);
		T t = null;
		try{
			t=JSON.parseObject(value, clazz);
		}catch(Exception e){
		}
		return t;
	}
	
	public <T extends IRedisSupport> void deleteObject(String uid,Class<T> clazz){
		String key=IRedisSupport.KEY_PREFIX+clazz.getName()+":"+uid;
		srt.delete(key);
	}
	
	public void deleteObject(IRedisSupport obj){
		deleteObject(obj.uid(), obj.getClass());
	}
	
}

