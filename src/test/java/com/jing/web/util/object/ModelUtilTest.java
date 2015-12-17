package com.jing.web.util.object;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.jing.web.model.User;

public class ModelUtilTest {

	@Test
	public void test(){
		User user = new User();
		user.setId(1234567);
		user.setName("张三");
		user.setCreatedAt(new Date());
		
		Map<String, Object> map = ModelUtil.valueMap(user);
		System.out.println(map);
		
		User user2 = new User();
		ModelUtil.setValues(user2, map);
		Map<String, Object> map2 = ModelUtil.valueMap(user2);
		System.out.println(map2);
	}
}
