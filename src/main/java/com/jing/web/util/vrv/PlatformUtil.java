/**
 * Project Name:springlearn
 * File Name:PlatformUtil.java
 * Package Name:com.jing.web.util.vrv
 * Date:2016年1月23日下午3:08:05
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.vrv;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jing.web.util.http.HttpUtil;
import com.jing.web.util.http.Response;

/**
 * ClassName:PlatformUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月23日 下午3:08:05 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class PlatformUtil {
	private String appID = "4395721494";
	private String appSecret="wiVpM6MbEozRmzuHqTDrqw";
	public static final String DEFAULT_LANG="zh_CN";
	public static final String BASE_URL = "http://test.linkdood.cn:10080/platform/platform/";
	public static final String ACCESS_TOKEN_URL=BASE_URL+"token";
	public static final String MESSAGE_SEND_URL=BASE_URL+"message/send";
	public static final String RESOURCE_UPLOAD_URL=BASE_URL+"resource/upload";
	public static final String RESOURCE_QUERY_URL=BASE_URL+"resource/query";
	public static final String USER_TOKEN_URL=BASE_URL+"userToken";
	public static final String USER_INFO_URL=BASE_URL+"user/info";
	public static final String USER_UPDATE_URL=BASE_URL+"user/update";
	public static final String USER_BUDDYS_TIMESTAMP_URL=BASE_URL+"user/buddysTimestamp";
	public static final String USER_BUDDYS_URL=BASE_URL+"user/buddys";
	
	public static final String ACCESS_TYPE_CLIENT="CLIENT";
	
	public String accessToken(){
		Map<String, String> params=new HashMap<String,String>();
		params.put("accessType", ACCESS_TYPE_CLIENT);
		params.put("appID", appID);
		params.put("appSecret", appSecret);
		Response response = HttpUtil.get(ACCESS_TOKEN_URL, params);
		if(200==response.getStatusCode()){
			String json = response.getBody();
			JSONObject jo = JSON.parseObject(json);
			String accessToken=jo.getJSONObject("result").getString("access_token");
			return accessToken;
		}
		return null;
	}
	
	public void sendMsg(Message message){
		String url = MESSAGE_SEND_URL+"?access_token="+accessToken()+"&device_type=2";
		Map<String,String>params = new HashMap<String,String>();
		params.put("msg", JSON.toJSONString(message));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}
	
	public void resourceUpload(String type,String file){
		String url = RESOURCE_UPLOAD_URL+"?access_token="+accessToken()+"&type="+type;
		Map<String,String> files = new HashMap<String,String>();
		files.put("file", file);
		Response response = HttpUtil.upload(url, files);
		System.out.println(response.getBody());
	}
	
	public void resourceQuery(String type,int pageNum,int pageSize){
		String url = RESOURCE_QUERY_URL;
		Map<String,String> params=new HashMap<String,String>();
		params.put("type", type);
		params.put("pageNum", String.valueOf(pageNum));
		params.put("pageSize", String.valueOf(pageSize));
		params.put("access_token", accessToken());
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public void userToken(String clientKey){
		String url = USER_TOKEN_URL;
		Map<String,String> params=new HashMap<String,String>();
		params.put("clientKey", clientKey);
		params.put("access_token", accessToken());
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	//4395732306  4395733301
	public void userInfo(String userToken){
		Map<String, String> params=new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(USER_INFO_URL, params);
		System.out.println(response.getBody());
	}
	public void userUpdate(String userToken,UserInfo user,String lang){
		String url = USER_UPDATE_URL+"?access_token="+accessToken()+"&user_token="+userToken+"&lang="+lang;
		Map<String,String>params = new HashMap<String,String>();
		params.put("userInfo", JSON.toJSONString(user));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}
	
	public void userBuddysTimestamp(String userToken,String lang){
		Map<String,String>params = new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang",lang);
		String url=USER_BUDDYS_TIMESTAMP_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public void userBuddys(String userToken,String lang,int pageNum){
		Map<String,String>params = new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang",lang);
		params.put("page_no",String.valueOf(pageNum));		
		String url=USER_BUDDYS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public static void main(String[] args) {
		String userToken="4395733301";
		String lang="a";
		PlatformUtil platformUtil = new PlatformUtil();
//		String accessToken = platformUtil.accessToken();
//		System.out.println(accessToken);
//		platformUtil.userInfo("4395733301");
		
//		Message message = new Message();
//		message.setSendUserID(platformUtil.appID);
//		message.setReceTargetID("4395733301");
//		message.setMessage("开放平台测试");
//		message.setMessageType("2");
//		
//		platformUtil.sendMsg(message);
//		String type="2";
//		String file="d:/48540923dd54564ec40a9c82b3de9c82d1584f19.jpg";
//		platformUtil.resourceUpload(type, file);
//		File f =new File(file);
//		platformUtil.resourceQuery("2", 2, 100);
		
//		platformUtil.userToken("123123");
		
//		UserInfo user = new UserInfo();
//		user.setName("监控2");
//		user.setBirthday("2015-01-12");
//		user.setSex(1);
//		user.setSign("哈哈，123.");
//		platformUtil.userUpdate("4395733301", user, "zh_CN");
//		platformUtil.userBuddysTimestamp(userToken, lang);
		platformUtil.userBuddys(userToken, lang, 1);
	}
}

