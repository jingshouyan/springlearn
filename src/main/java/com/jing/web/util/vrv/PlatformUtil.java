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

import org.aspectj.weaver.JoinPointSignature;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jing.web.util.http.HttpUtil;
import com.jing.web.util.http.Response;

/**
 * ClassName:PlatformUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年1月23日 下午3:08:05 <br/>
 * 
 * @author bxy-jing
 * @version
 * @since JDK 1.6
 * @see
 */
public class PlatformUtil {
	private String appID = "4395721494";// 4395733556
	private String appSecret = "wiVpM6MbEozRmzuHqTDrqw";
	private String entID = "262";

	public static final String BASE_URL = "http://test.linkdood.cn:10080/platform/platform/";// http://192.168.0.60:3801/platform/platform/
																								// ,http://test.linkdood.cn:10080/platform/platform/
	public static final String ACCESS_TOKEN_URL = BASE_URL + "token";
	public static final String MESSAGE_SEND_URL = BASE_URL + "message/send";
	public static final String MESSAGE_SENDS_URL = BASE_URL + "message/sends";
	public static final String RESOURCE_UPLOAD_URL = BASE_URL + "resource/upload";
	public static final String RESOURCE_QUERY_URL = BASE_URL + "resource/query";
	public static final String USER_TOKEN_URL = BASE_URL + "userToken";
	public static final String USER_INFO_URL = BASE_URL + "user/info";
	public static final String USER_UPDATE_URL = BASE_URL + "user/update";
	public static final String USER_BUDDYS_TIMESTAMP_URL = BASE_URL + "user/buddysTimestamp";
	public static final String USER_BUDDYS_URL = BASE_URL + "user/buddys";
	public static final String USER_ORGANIZATION_URL = BASE_URL + "user/organization";
	public static final String USER_SHARE_OPTIONS_URL = BASE_URL + "user/shareOption";
	public static final String USER_SHARE_NOTIFICATION_SWITCH_URL = BASE_URL + "user/shareNotificationSwitch";
	public static final String GROUP_CREATE_URL = BASE_URL + "group/create";
	public static final String GROUP_REMOVE_URL = BASE_URL + "group/remove";
	public static final String GROUP_INFO_URL = BASE_URL + "group/info";
	public static final String GROUP_LIST_URL = BASE_URL + "group/list";
	public static final String GROUP_REMOVE_MEMBERS_URL = BASE_URL + "group/removeMembers";
	public static final String GROUP_ADD_MEMBERS_URL = BASE_URL + "group/addMembers";
	public static final String GROUP_GET_MEMBER_URL = BASE_URL + "group/getMember";
	public static final String GROUP_GET_MEMBERS_URL = BASE_URL + "group/getMembers";
	public static final String GROUP_GET_MEMBERS_PAGE_TIMESTAMP_URL = BASE_URL + "group/getMembersPageTimestamp";
	public static final String APP_UPDATE_APP_INFO = BASE_URL + "app/updateAppInfo";

	public static final String ACCESS_TYPE_CLIENT = "CLIENT";
	public static final String DEFAULT_LANG = "zh_CN";
	/**
	 * 分享设置，允许
	 */
	public static final int SHARE_OPTION_ALLOW = 1;
	/**
	 * 分享设置，拒绝
	 */
	public static final int SHARE_OPTION_DENY = 2;
	public static final int GROUP_LEVEL_TEMPORARY = 1;
	public static final int GROUP_LEVEL_ORDINARY = 2;// ordinary
	public static final int GROUP_LEVEL_SENIOR = 3; // senior
	public static final int GROUP_LEVEL_SUPER = 4;

	public String accessToken() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accessType", ACCESS_TYPE_CLIENT);
		params.put("appID", appID);
		params.put("appSecret", appSecret);
		Response response = HttpUtil.get(ACCESS_TOKEN_URL, params);
		if (200 == response.getStatusCode()) {
			String json = response.getBody();
			JSONObject jo = JSON.parseObject(json);
			String accessToken = jo.getJSONObject("result").getString("access_token");
			return accessToken;
		}
		return null;
	}

	public void sendMsg(Message message) {
		String url = MESSAGE_SEND_URL + "?access_token=" + accessToken() + "&device_type=2";
		Map<String, String> params = new HashMap<String, String>();
		params.put("msg", JSON.toJSONString(message));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	public void sendMsgs(Messages messages) {
		String url = MESSAGE_SENDS_URL + "?access_token=" + accessToken() + "&device_type=2";
		Map<String, String> params = new HashMap<String, String>();
		params.put("msg", JSON.toJSONString(messages));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	public void resourceUpload(String type, String file) {
		String url = RESOURCE_UPLOAD_URL + "?access_token=" + accessToken() + "&type=" + type;
		Map<String, String> files = new HashMap<String, String>();
		files.put("file", file);
		Response response = HttpUtil.upload(url, files);
		System.out.println(response.getBody());
	}

	public void resourceQuery(String type, int pageNum, int pageSize) {
		String url = RESOURCE_QUERY_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", type);
		params.put("pageNum", String.valueOf(pageNum));
		params.put("pageSize", String.valueOf(pageSize));
		params.put("access_token", accessToken());
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void userToken(String clientKey) {
		String url = USER_TOKEN_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("clientKey", clientKey);
		params.put("access_token", accessToken());
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void userInfo(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(USER_INFO_URL, params);
		System.out.println(response.getBody());
	}

	public void userUpdate(String userToken, UserInfo user) {
		String url = USER_UPDATE_URL + "?access_token=" + accessToken() + "&user_token=" + userToken + "&lang="
				+ DEFAULT_LANG;
		Map<String, String> params = new HashMap<String, String>();
		params.put("userInfo", JSON.toJSONString(user));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	public void userBuddysTimestamp(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		String url = USER_BUDDYS_TIMESTAMP_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void userBuddys(String userToken, int pageNum) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		params.put("page_no", String.valueOf(pageNum));
		String url = USER_BUDDYS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void userOrganization(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		String url = USER_ORGANIZATION_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void userShareOption(String userToken, int shareType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		params.put("shareType", String.valueOf(shareType));
		String url = USER_SHARE_OPTIONS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void userShareNotificationSwitch(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		String url = USER_SHARE_NOTIFICATION_SWITCH_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void groupCreate(GroupInfo group) {
		String url = GROUP_CREATE_URL + "?access_token=" + accessToken() + "&lang=" + DEFAULT_LANG;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupInfo", JSON.toJSONString(group));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	public void groupRemove(String groupID) {
		String url = GROUP_REMOVE_URL + "?access_token=" + accessToken() + "&lang=" + DEFAULT_LANG + "&groupID="
				+ groupID;
		Map<String, String> params = new HashMap<String, String>();
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	public void groupInfo(String groupID) {
		String url = GROUP_INFO_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("access_token", accessToken());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void groupList(String openID) {
		String url = GROUP_LIST_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken());
		params.put("openID", openID);
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void groupRemoveMembers(String groupID, List<String> groupMembers) {
		String url = GROUP_REMOVE_MEMBERS_URL + "?access_token=" + accessToken() + "&lang=" + DEFAULT_LANG + "&groupID="
				+ groupID;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupMembers", JSON.toJSONString(groupMembers));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	public void groupAddMembers(String groupID, List<String> groupMembers) {
		String url = GROUP_ADD_MEMBERS_URL + "?access_token=" + accessToken() + "&lang=" + DEFAULT_LANG + "&groupID="
				+ groupID;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupMembers", JSON.toJSONString(groupMembers));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	public void groupGetMember(String groupID, String groupMemberID) {
		String url = GROUP_GET_MEMBER_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("groupMemberID", groupMemberID);
		params.put("access_token", accessToken());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void groupGetMembers(String groupID, int pageNo) {
		String url = GROUP_GET_MEMBERS_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("pageNo", String.valueOf(pageNo));
		params.put("access_token", accessToken());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void groupGetMembersPageTimetamp(String groupID) {
		String url = GROUP_GET_MEMBERS_PAGE_TIMESTAMP_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("access_token", accessToken());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public void appUpdateAppInfo(List<Menu> menus) {
		String url = APP_UPDATE_APP_INFO + "?access_token=" + accessToken() + "&accessType=" + ACCESS_TYPE_CLIENT;
		JSONObject appInfo = new JSONObject();
		appInfo.put("appID", appID);
		appInfo.put("entID", entID);
		JSONObject appMenus = new JSONObject();
		appMenus.put("menu", menus);
		appInfo.put("appMenus", appMenus.toJSONString());
		Map<String, String> params = new HashMap<String, String>();
		params.put("appInfo", appInfo.toJSONString());
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}
	
	public static void tokenTest(){
		String token = "LvR5zAouzSQeERcUk5m1xskRfpHDJS-7Xb0ef6tnoBdeZuzbWcvSRFoI5-jhXr1YaV2SJ0kxfKdW8hZmcEveID6tkYZ2FyTV9K-yDlcFUGY";
		
	}

	public static void main(String[] args) {
		String userToken = "4395733556"; // 4395732306 4395733301 4395733556
		PlatformUtil platformUtil = new PlatformUtil();
		// String accessToken = platformUtil.accessToken();
		// System.out.println(accessToken);
		// platformUtil.userInfo("4395733301");

		// Messages messages = new Messages();
		// messages.setSendUserID(platformUtil.appID);
		// List<String> targets = new ArrayList<String>();
		// targets.add(userToken);
		// targets.add(userToken);
		// targets.add(userToken);
		// messages.setReceTargetID(targets);
		// messages.setMessage("开放平台测试");
		// messages.setMessageType("2");
		// platformUtil.sendMsgs(messages);
		// String type="2";
		// String file="d:/48540923dd54564ec40a9c82b3de9c82d1584f19.jpg";
		// platformUtil.resourceUpload(type, file);
		// File f =new File(file);
		// platformUtil.resourceQuery("2", 2, 100);

		// platformUtil.userToken("123123");

		// UserInfo user = new UserInfo();
		// user.setName("监控2");
		// user.setBirthday("2015-01-12");
		// user.setSex(1);
		// user.setSign("哈哈，123.");
		// platformUtil.userUpdate("4395733301", user);
		// platformUtil.userBuddysTimestamp(userToken);
		// platformUtil.userBuddys(userToken, 1);
		// platformUtil.userOrganization(userToken);
		// platformUtil.userShareOption(userToken, 3);
		// platformUtil.userShareNotificationSwitch(userToken);

		// GroupInfo groupInfo = new GroupInfo();
		// groupInfo.setGroupName("开放平台测试组");
		// groupInfo.setGroupIcon("");
		// groupInfo.setGroupType("测试群");
		// groupInfo.setGroupLevel(GROUP_LEVEL_TEMPORARY);
		// groupInfo.setGroupBrief("这只是一个开放平台的接口测试");
		// groupInfo.setGroupBulletin("群公告呀");
		// groupInfo.setRelatedEnterpriseID(Long.parseLong(platformUtil.appID));
		// List<Long> initGroupMembers=new ArrayList<Long>();
		// initGroupMembers.add(4395733301l);
		// initGroupMembers.add(4395732306l);
		// groupInfo.setInitGroupMembers(initGroupMembers);
		// platformUtil.groupCreate(groupInfo);//4404020003

		// platformUtil.groupRemove("4404020002");

		// platformUtil.groupInfo("4404020003");

		// platformUtil.groupList("4395721494");
		// List<String> members = new ArrayList<String>();
		// members.add("4395732306");
		// platformUtil.groupAddMembers("4404020003", members);
		// platformUtil.groupRemoveMembers("4404020003", members);

		// platformUtil.groupGetMember("4404020003", "4395732306");
		// platformUtil.groupGetMembers("4404020003", 1);
		// platformUtil.groupGetMembersPageTimetamp("4404020003");

		List<Menu> menus = new ArrayList<Menu>();
		Menu menu1 = new Menu();
		menu1.setName("呵呵哒");
		menu1.setType("view");
		menu1.setUrl("http://www.baidu.com");
		Menu menu2 = new Menu();
		menu2.setName("赫赫");
		menu2.setType("click");
		menu2.setKey("CLICK_HEHE");
		Menu menu3 = new Menu();
		menu3.setName("菜单组");
		List<Menu> ms = new ArrayList<Menu>();
		Menu menu31 = new Menu();
		menu31.setName("呵呵哒2");
		menu31.setType("view");
		menu31.setUrl("http://www.baidu.com");
		ms.add(menu31);
		Menu menu32 = new Menu();
		menu32.setName("赫赫2");
		menu32.setType("click");
		menu32.setKey("CLICK_HEHE");
		menu3.setSub_menus(ms);
		menus.add(menu1);
		menus.add(menu3);
		menus.add(menu2);
		ms.add(menu32);

		platformUtil.appUpdateAppInfo(menus);

	}
}
