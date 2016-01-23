package com.jing.web.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.jing.config.CacheConfig;
import com.jing.config.DatabaseConfig;
import com.jing.config.RedisConfig;
import com.jing.config.RootConfig;
import com.jing.config.WebConfig;
import com.jing.web.model.User;
import com.jing.web.util.database.Compare;
import com.jing.web.util.database.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={WebConfig.class,RootConfig.class,RedisConfig.class,DatabaseConfig.class,CacheConfig.class})
@WebAppConfiguration
public class UserDaoTest {
	@Autowired
	UserDao userDao;
	@Autowired
	DbDao<User> dbDao;
	
	@Before
	public void before(){
		dbDao.setClass(User.class);
	}
	
//	@Test
	public void find(){
		for(int i =0;i<10;i++){
			User user = dbDao.find(21007);
			
			
			System.out.println(user);
		}
		
	}
//	@Test
	public void insert(){
		User user = new User();
		for(int i=0;i<10;i++){
//			user.setId(i+1000000l);
			user.setName("旺旺");
			user.setAge(i);
			user.setGender("dog");
			user.setVersion(0);
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			long id = dbDao.insert2(user);
			System.out.println(id);
		}
		
	}
	@Test
	public void batchInsert(){
		List<User> users = new ArrayList<User>();
		for(int i=0;i<1000;i++){
			User user = new User();
//			user.setId(i+1000000l);
//			user.setName("旺旺");
			user.setAge(i+900000);
			user.setGender("dog");
			user.setVersion(0);
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			users.add(user);
		}
		dbDao.batchInsert(users);
	}
//	@Test
	public void update(){
		User user = dbDao.find(1);
		user.setAge(55);
		user.setUpdatedAt(new Date());
		dbDao.update(user);
	}
	
//	@Test
	public void delete(){
		for(int i=10;i<20;i++){			
			dbDao.delete(i);
		}
	}
//	@Test
	public void delete2(){
		List<Long> ids = new ArrayList<Long>();
		ids.add(11l);
		ids.add(12l);
		ids.add(13l);
		ids.add(14l);
		ids.add(15l);
		dbDao.delete(ids);
	}
	
//	@Test
	public void delete3(){
		long[]  ids = {16,17,18};
		dbDao.delete(ids);
	}
//	@Test
	public void page(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "旺旺");
		map.put("gender", "dog");
//		List<Long> ids = new ArrayList<Long>();
//		ids.add(100l);
//		ids.add(200l);
//		map.put("id", ids);
		Compare idc = new Compare();
		idc.setGt(250l);
		idc.setLte(330l);
		idc.setNe(310l);
		map.put("id", idc);
		Page<User> page = new Page<User>();
		page.setPage(2);
		page = userDao.query(map, page);
		String json = JSONObject.toJSONString(page);
		System.out.println(json);
	}
	
//	@Test
	public void query(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "旺旺");
		map.put("gender", "dog");
		List<Long> ids = new ArrayList<Long>();
		ids.add(100l);
		ids.add(200l);
		map.put("id", ids);
		
//		Compare idc = new Compare();
//		idc.setGt(21007l);
//		idc.setLte(21028l);
//		idc.setNe(21026l);
//		map.put("id", idc);
		List<User> users = dbDao.query(map);
		String json = JSONObject.toJSONString(users);
		System.out.println(json);
	}
}
