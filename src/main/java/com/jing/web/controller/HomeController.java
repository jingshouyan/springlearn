package com.jing.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jing.web.bean.Err;
import com.jing.web.dao.DbDao;
import com.jing.web.model.User;
import com.jing.web.util.Constants;
import com.jing.web.util.redis.ObjectOps;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ObjectOps opt;
	@Autowired
	private DbDao<User> dbDaoUser;	

	public HomeController() {
		
	}
	

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		logger.info("HomeController.index");
		return "HELLO WORLD!!";
	}

	@RequestMapping("home")
	public String home(HttpServletRequest request) {
		String userid = UUID.randomUUID().toString();
		request.getSession().setAttribute(Constants.SESSION_USER_ID, userid);
		return "home";
	}

	@RequestMapping(value = "user")
	@ResponseBody
	public User getUser() {
		dbDaoUser.setClass(User.class);
		User user = dbDaoUser.find(3031);
		user.setCreatedAt(new Date());
		return user;
	}

	@RequestMapping(value = "redis")
	@ResponseBody
	public User jredis() {
		User user = new User();
		user.setId(123123l);
		user.setName("张三");
		user.setAge(23);
		user.setGender("male");
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		opt.save(user);

		User u = opt.get("123123", User.class);
		// opt.delete(user);
		return u;
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(MultipartFile file,String data) throws IOException {
		String originalFileName = "NAN";
		System.out.println(data);
		try {
			if (!file.isEmpty()) {
				originalFileName = file.getOriginalFilename();
				// byte[] bytes = originalFileName.getBytes("utf-8");
				// String convert = new String(bytes,"ISO-8859-1");
				// System.out.println(bytes);
				// System.out.println(convert);
				System.out.println(originalFileName);
				System.out.println(file.getName());
				System.out.println(file.getSize());
				System.out.println(file.getContentType());
				String realPath = "d:/zip";
				String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
				FileUtils.copyInputStreamToFile(file.getInputStream(),
						new File(realPath, UUID.randomUUID().toString() + suffix));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return originalFileName;
	}
	@RequestMapping(value = "test", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject test(@RequestBody String body){
		
		return JSON.parseObject(body);
	}
	
	@RequestMapping(value = "test2", method = RequestMethod.POST)
	@ResponseBody
	public String test2(@RequestBody JSONObject body){
		
		return body.toJSONString();
	}
	
	@RequestMapping(value = "test3", method = RequestMethod.POST)
	@ResponseBody
	public User test3(@RequestBody User body){
		
		return body;
	}
	
	@RequestMapping(value = "cache")
	@ResponseBody
	public String cache(){
		 
		return "cache";
	}
	
	@ExceptionHandler
	@ResponseBody
	public Err error(Exception e){
		logger.error("",e);
		return new Err(e);
	}
}
