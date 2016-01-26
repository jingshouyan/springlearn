/**
 * Project Name:springlearn
 * File Name:PlatformUtil.java
 * Package Name:com.jing.web.util.vrv
 * Date:2016年1月23日下午3:08:05
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.vrv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public static final String USER_ORGANIZATION_URL=BASE_URL+"user/organization";
	public static final String USER_SHARE_OPTIONS_URL=BASE_URL+"user/shareOption";
	public static final String USER_SHARE_NOTIFICATION_SWITCH_URL=BASE_URL+"user/shareNotificationSwitch";
	public static final String GROUP_CREATE_URL=BASE_URL+"group/create";
	public static final String GROUP_REMOVE_URL=BASE_URL+"group/remove";
	public static final String GROUP_INFO_URL=BASE_URL+"group/info";
	public static final String GROUP_LIST_URL=BASE_URL+"group/list";
	public static final String GROUP_REMOVE_MEMBERS_URL=BASE_URL+"group/removeMembers";
	public static final String GROUP_ADD_MEMBERS_URL=BASE_URL+"group/addMembers";
	public static final String GROUP_GET_MEMBER_URL=BASE_URL+"group/getMember";
	public static final String GROUP_GET_MEMBERS_URL=BASE_URL+"group/getMembers";
	public static final String GROUP_GET_MEMBERS_PAGE_TIMESTAMP_URL=BASE_URL+"group/getMembersPageTimestamp";
	public static final String APP_UPDATE_APP_INFO=BASE_URL+"app/updateAppInfo";
	
	public static final String ACCESS_TYPE_CLIENT="CLIENT";
	public static final String DEFAULT_LANG="zh_CN";
	public static final int SHARE_OPTION_ALLOW=1;
	public static final int SHARE_OPETION_DENY=2;
	public static final int GROUP_LEVEL_TEMPORARY= 1; 
	public static final int GROUP_LEVEL_ORDINARY=2;// ordinary
	public static final int GROUP_LEVEL_SENIOR=3; //senior
	public static final int GROUP_LEVEL_SUPER=4;
	
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
	

	public void userInfo(String userToken){
		Map<String, String> params=new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(USER_INFO_URL, params);
		System.out.println(response.getBody());
	}
	public void userUpdate(String userToken,UserInfo user){
		String url = USER_UPDATE_URL+"?access_token="+accessToken()+"&user_token="+userToken+"&lang="+DEFAULT_LANG;
		Map<String,String>params = new HashMap<String,String>();
		params.put("userInfo", JSON.toJSONString(user));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}
	
	public void userBuddysTimestamp(String userToken){
		Map<String,String>params = new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang",DEFAULT_LANG);
		String url=USER_BUDDYS_TIMESTAMP_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public void userBuddys(String userToken,int pageNum){
		Map<String,String>params = new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang",DEFAULT_LANG);
		params.put("page_no",String.valueOf(pageNum));		
		String url=USER_BUDDYS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public void userOrganization(String userToken){
		Map<String,String>params = new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang",DEFAULT_LANG);
		String url=USER_ORGANIZATION_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public void userShareOption(String userToken,int shareType){
		Map<String,String>params = new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang",DEFAULT_LANG);
		params.put("shareType",String.valueOf(shareType));
		String url=USER_SHARE_OPTIONS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public void userShareNotificationSwitch(String userToken){
		Map<String,String>params = new HashMap<String,String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang",DEFAULT_LANG);
		String url=USER_SHARE_NOTIFICATION_SWITCH_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	public void groupCreate(GroupInfo group){
		String url = GROUP_CREATE_URL+"?access_token="+accessToken()+"&lang="+DEFAULT_LANG;
		Map<String,String>params = new HashMap<String,String>();
		params.put("groupInfo", JSON.toJSONString(group));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}
	
	public static void main(String[] args) {
		String userToken="4395733301";	//4395732306  4395733301
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
//		platformUtil.userUpdate("4395733301", user);
//		platformUtil.userBuddysTimestamp(userToken);
//		platformUtil.userBuddys(userToken,  1);
//		platformUtil.userOrganization(userToken);
//		platformUtil.userShareOption(userToken, 3);
		platformUtil.userShareNotificationSwitch(userToken);
		
//		GroupInfo groupInfo = new GroupInfo();
//		groupInfo.setGroupName("开放平台测试组");
//		groupInfo.setGroupIcon("");
//		groupInfo.setGroupType("测试群");
//		groupInfo.setGroupLevel(GROUP_LEVEL_TEMPORARY);
//		groupInfo.setGroupBrief("这只是一个开放平台的接口测试");
//		groupInfo.setGroupBulletin("群公告呀");
//		groupInfo.setRelatedEnterpriseID(Long.parseLong(platformUtil.appID));
//		List<Long> initGroupMembers=new ArrayList<Long>();
//		initGroupMembers.add(4395733301l);
//		initGroupMembers.add(4395732306l);
//		groupInfo.setInitGroupMembers(initGroupMembers);
//		platformUtil.groupCreate(groupInfo);//4404020002
		
	}
}

