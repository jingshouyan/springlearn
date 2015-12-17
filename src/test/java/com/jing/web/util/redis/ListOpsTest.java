package com.jing.web.util.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jing.config.DatabaseConfig;
import com.jing.config.RedisConfig;
import com.jing.config.RootConfig;
import com.jing.config.WebConfig;
import com.jing.web.util.redis.ListOps;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={WebConfig.class,RootConfig.class,RedisConfig.class,DatabaseConfig.class})
@WebAppConfiguration
public class ListOpsTest {
	@Autowired
	ListOps listOps;
	
	@Test
	public void test(){
		
		String key="user:123";
		String value="jingshouyan";
		listOps.lpush(key, value);
	}
}
