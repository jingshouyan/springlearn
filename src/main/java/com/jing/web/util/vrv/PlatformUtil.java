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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
	
	private static final Logger logger = LoggerFactory.getLogger(PlatformUtil.class);
	
//	private static String appID = "4328621727";// 4395733556   4328612680
//	private static String appSecret = "ojKlsmsma2Wdg7G7L4NHkg";
//	private static String entID = "258";	
//	private static String token="randomString123";
//	public static final String BASE_URL = "http://vrv.linkdood.cn/platform/platform/";// http://192.168.0.60:3801/platform/platform/
	
//	private static String appID = "4328612480";// 4395733556   4328612680
//	private static String appSecret = "MPmrkhCBSKEb61hprqUyhw";
//	private static String entID = "258";	
//	private static String token="randomString123";
	
//	private static String appID = "4395730591";// 4395733556   4328612680
//	private static String appSecret = "WRiTpNxyC5hcZXN9fBKXew";
//	private static String entID = "262";	
//	private static String token="randomString123";
//	public static final String BASE_URL = "http://test.linkdood.cn:10080/platform/platform/";
	
	private static AccessToken accessToken = new AccessToken();
	//开放平台根路径
	
	
	private static String appID = "9151315548882010112";// 4395733556   4328612680
	private static String appSecret = "kW3OBK_vl4_vprssrbiXzjVGbCf3xIyD78j7RQln2U_OhF1bNadY-l0oLDcaA4C0";
	private static String entID = "545460912128";	
	private static String token="randomString123";
	public static final String BASE_URL = "http://im.linkdood.cn/platform/platform/";
	
	
//	private static String appID = "9151316648393637888";// 4395733556   4328612680
//	private static String appSecret = "d4I75I7pPFlqTWoNA59rRGNcfWwJ214sfG_aXnKscoLQzvQ6V5ZmEvpPchGcDha_";
//	private static String entID = "545460977664";	
//	private static String token="randomString123";
//	public static final String BASE_URL = "http://im.linkdood.com/platform/platform/";
	//获取access_token的url地址
	public static final String ACCESS_TOKEN_URL = BASE_URL + "token";
	//发送普通消息的url地址
	public static final String MESSAGE_SEND_URL = BASE_URL + "message/send";
	//群发普通消息的url地址
	public static final String MESSAGE_SENDS_URL = BASE_URL + "message/sends";
	//分享通知接口的url地址
	public static final String MESSAGE_NOTIFY_URL= BASE_URL+"message/notify";
	//新增临时素材的url地址
	public static final String RESOURCE_UPLOAD_URL = BASE_URL + "resource/upload";
	//获取临时素材的url地址
	public static final String RESOURCE_QUERY_URL = BASE_URL + "resource/query";
	//获取user_token的url地址
	public static final String USER_TOKEN_URL = BASE_URL + "userToken";
	//获取用户基本信息的url地址
	public static final String USER_INFO_URL = BASE_URL + "user/info";
	//更新用户信息的url地址
	public static final String USER_UPDATE_URL = BASE_URL + "user/update";
	//获取好友列表分页时间戳的url地址
	public static final String USER_BUDDYS_TIMESTAMP_URL = BASE_URL + "user/buddysTimestamp";
	//获取用户好友关注列表的url地址
	public static final String USER_BUDDYS_URL = BASE_URL + "user/buddys";
	//获取用户好友列表 (不推荐使用，下个版本会废弃)的url地址
	public static final String USER_ALL_BUDDYS_URL=BASE_URL+"user/allBuddys";
	//获取用户所在组织信息的url地址
	public static final String USER_ORGANIZATION_URL = BASE_URL + "user/organization";
	//获取用户分享隐私设置的url地址
	public static final String USER_SHARE_OPTIONS_URL = BASE_URL + "user/shareOption";
	//获取用户分享更新通知的url地址
	public static final String USER_SHARE_NOTIFICATION_SWITCH_URL = BASE_URL + "user/shareNotificationSwitch";
	//创建群的url地址
	public static final String GROUP_CREATE_URL = BASE_URL + "group/create";
	//解散群的url地址
	public static final String GROUP_REMOVE_URL = BASE_URL + "group/remove";
	//获取群信息的url地址
	public static final String GROUP_INFO_URL = BASE_URL + "group/info";
	//获取群列表的url地址
	public static final String GROUP_LIST_URL = BASE_URL + "group/list";
	//移除群成员的url地址
	public static final String GROUP_REMOVE_MEMBERS_URL = BASE_URL + "group/removeMembers";
	//添加群成员的url地址
	public static final String GROUP_ADD_MEMBERS_URL = BASE_URL + "group/addMembers";
	//获取群成员信息的url地址
	public static final String GROUP_GET_MEMBER_URL = BASE_URL + "group/getMember";
	//获取群成员列表信息的url地址
	public static final String GROUP_GET_MEMBERS_URL = BASE_URL + "group/getMembers";
	//获取群成员列表页码时间戳的url地址
	public static final String GROUP_GET_MEMBERS_PAGE_TIMESTAMP_URL = BASE_URL + "group/getMembersPageTimestamp";
	//设置自定义菜单的url地址
	public static final String APP_UPDATE_APP_INFO_URL = BASE_URL + "app/updateAppInfo";
	//设置机器人子账户的url地址
	public static final String APP_SET_SUBACCOUNT_URL = BASE_URL+"app/setAppSubAccount";
	//移除机器人子账户的url地址
	public static final String APP_REMOVE_SUBACCOUNT_URL=BASE_URL+"app/removeAppSubAccount";

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
	/**
	 * 群级别  1：临时群、2：普通群、3：高级群、4：超级群
	 */
	public static final int GROUP_LEVEL_TEMPORARY = 1;
	public static final int GROUP_LEVEL_ORDINARY = 2;// ordinary
	public static final int GROUP_LEVEL_SENIOR = 3; // senior
	public static final int GROUP_LEVEL_SUPER = 4;

	/**
	 * 
	 * validToken:验证token. <br/>
	 *
	 * @author bxy-jing
	 * @param params
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean validToken(Map<String,String> params){
		String entid = params.get("entid");
		String timestamp = params.get("timestamp");
		String nonce = params.get("nonce");
		String signature = params.get("signature");
		if(entid==null||timestamp==null||timestamp==null||timestamp==null){
			logger.info("params is not enough {}",params);
			return false;
		}
		List<String> list = new ArrayList<>();
		list.add(token);
		list.add(entid);
		list.add(timestamp);
		list.add(nonce);
		//字典排序
		Collections.sort(list);
		//拼接字符串
		String str = StringUtils.arrayToDelimitedString(list.toArray(), "");
		//sha1摘要算法
		String shaStr = DigestUtils.shaHex(str);
		//对比
		return signature.equals(shaStr);
	}
	
	/**
	 * 
	 * accessTokenWithCatch:获取accessToken基于缓存 <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public static String accessTokenWithCatch(){
		//如果缓存的accessToken可用，则直接返回
		if(accessToken.enable()){
			return accessToken.getTokenStr();
		}
		synchronized (PlatformUtil.class) {			
			//请求开放平台，获取accessToken
			String token = accessToken();
			//将获取的accessToken存在缓存中
			accessToken.setTokenStr(token);
			//设置过期时间为7000秒，小于开放平台规定的7200秒
			accessToken.setExpAt(System.currentTimeMillis()+7000l*1000);
			return token;
		}
	}
	
	/**
	 * 
	 * accessToken:调用开放平台接口获取accessToken. <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public static String accessToken() {
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

	/**
	 * 
	 * sendMsg:发送普通消息. <br/>
	 *
	 * @author bxy-jing
	 * @param message
	 * @since JDK 1.6
	 */
	public static void sendMsg(Message message) {
		String url = MESSAGE_SEND_URL + "?access_token=" + accessTokenWithCatch() + "&device_type=2";
		Map<String, String> params = new HashMap<String, String>();
		params.put("msg", JSON.toJSONString(message));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * sendMsgs:群发普通消息. <br/>
	 *
	 * @author bxy-jing
	 * @param messages
	 * @since JDK 1.6
	 */
	public static void sendMsgs(Messages messages) {
		String url = MESSAGE_SENDS_URL + "?access_token=" + accessTokenWithCatch() + "&device_type=2";
		Map<String, String> params = new HashMap<String, String>();
		params.put("msg", JSON.toJSONString(messages));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}
	/**
	 * 
	 * msgNotify:分享通知接口. <br/>
	 * 暂不可用
	 * @author bxy-jing
	 * @param notification
	 * @since JDK 1.6
	 */
	public static void msgNotify(Notification notification) {
		String url = MESSAGE_NOTIFY_URL+"?access_token=" + accessTokenWithCatch();
		Map<String, String> params = new HashMap<String, String>();
		params.put("notification", JSON.toJSONString(notification));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * resourceUpload:新增临时素材. <br/>
	 *
	 * @author bxy-jing
	 * @param type 媒体文件类型(1缩略图、2图片、3语音、4视频)
	 * @param file 媒体文件本地地址
	 * @since JDK 1.6
	 */
	public static void resourceUpload(String type, String file) {
		String url = RESOURCE_UPLOAD_URL + "?access_token=" + accessTokenWithCatch() + "&type=" + type;
		Map<String, String> files = new HashMap<String, String>();
		files.put("file", file);
		Response response = HttpUtil.upload(url, files);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * resourceQuery:获取临时素材. <br/>
	 *
	 * @author bxy-jing
	 * @param type 媒体文件类型(1图文、2图片、3语音、4视频)
	 * @param pageNum 	页码
	 * @param pageSize  每页大小
	 * @since JDK 1.6
	 */
	public static void resourceQuery(String type, int pageNum, int pageSize) {
		String url = RESOURCE_QUERY_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", type);
		params.put("pageNum", String.valueOf(pageNum));
		params.put("pageSize", String.valueOf(pageSize));
		params.put("access_token", accessTokenWithCatch());
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	/**
	 * 
	 * userToken:获取user_token. <br/>
	 *
	 * @author bxy-jing
	 * @param clientKey
	 * @since JDK 1.6
	 */
	public static void userToken(String clientKey) {
		String url = USER_TOKEN_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("clientKey", clientKey);
		params.put("access_token", accessTokenWithCatch());
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * userInfo:获取用户基本信息. <br/>
	 *
	 * @author bxy-jing
	 * @param userToken
	 * @since JDK 1.6
	 */
	public static void userInfo(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(USER_INFO_URL, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * userUpdate:更新用户信息. <br/>
	 *
	 * @author bxy-jing
	 * @param userToken
	 * @param user
	 * @since JDK 1.6
	 */
	public static void userUpdate(String userToken, UserInfo user) {
		String url = USER_UPDATE_URL + "?access_token=" + accessTokenWithCatch() + "&user_token=" + userToken + "&lang="
				+ DEFAULT_LANG;
		Map<String, String> params = new HashMap<String, String>();
		params.put("userInfo", JSON.toJSONString(user));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * userBuddysTimestamp:获取好友列表分页时间戳. <br/>
	 *
	 * @author bxy-jing
	 * @param userToken
	 * @since JDK 1.6
	 */
	public static void userBuddysTimestamp(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		String url = USER_BUDDYS_TIMESTAMP_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * userBuddys:获取用户好友关注列表. <br/>
	 *
	 * @author bxy-jing
	 * @param userToken
	 * @param pageNum
	 * @since JDK 1.6
	 */
	public static void userBuddys(String userToken, int pageNum) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		params.put("page_no", String.valueOf(pageNum));
		String url = USER_BUDDYS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	
	public static void userAllBuddys(String userToken){
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		String url = USER_ALL_BUDDYS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}
	
	/**
	 * 
	 * userOrganization:获取用户所在组织信息. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author bxy-jing
	 * @param userToken
	 * @since JDK 1.6
	 */
	public static void userOrganization(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		String url = USER_ORGANIZATION_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * userShareOption:获取用户分享隐私设置. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author bxy-jing
	 * @param userToken
	 * @param shareType
	 * @since JDK 1.6
	 */
	public static void userShareOption(String userToken, int shareType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		params.put("shareType", String.valueOf(shareType));
		String url = USER_SHARE_OPTIONS_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * userShareNotificationSwitch:获取用户分享更新通知(暂不可用). <br/>
	 *
	 * @author bxy-jing
	 * @param userToken
	 * @since JDK 1.6
	 */
	public static void userShareNotificationSwitch(String userToken) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("user_token", userToken);
		params.put("lang", DEFAULT_LANG);
		String url = USER_SHARE_NOTIFICATION_SWITCH_URL;
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupCreate:创建群. <br/>
	 *
	 * @author bxy-jing
	 * @param group
	 * @since JDK 1.6
	 */
	public static void groupCreate(GroupInfo group) {
		String url = GROUP_CREATE_URL + "?access_token=" + accessTokenWithCatch() + "&lang=" + DEFAULT_LANG;
		Map<String, String> params = new HashMap<String, String>();		
		params.put("groupInfo", JSON.toJSONString(group));
		System.out.println(JSON.toJSONString(group));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupRemove:解散群. <br/>
	 *
	 * @author bxy-jing
	 * @param groupID
	 * @since JDK 1.6
	 */
	public static void groupRemove(String groupID) {
		String url = GROUP_REMOVE_URL + "?access_token=" + accessTokenWithCatch() + "&lang=" + DEFAULT_LANG + "&groupID="
				+ groupID;
		Map<String, String> params = new HashMap<String, String>();
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupInfo:获取群信息. <br/>
	 *
	 * @author bxy-jing
	 * @param groupID
	 * @since JDK 1.6
	 */
	public static void groupInfo(String groupID) {
		String url = GROUP_INFO_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("access_token", accessTokenWithCatch());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupList:获取群列表. <br/>
	 *
	 * @author bxy-jing
	 * @param openID
	 * @since JDK 1.6
	 */
	public static void groupList(String openID) {
		String url = GROUP_LIST_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		params.put("openID", openID);
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupRemoveMembers:移除群成员. <br/>
	 *
	 * @author bxy-jing
	 * @param groupID
	 * @param groupMembers
	 * @since JDK 1.6
	 */
	public static void groupRemoveMembers(String groupID, List<String> groupMembers) {
		String url = GROUP_REMOVE_MEMBERS_URL + "?access_token=" + accessTokenWithCatch() + "&lang=" + DEFAULT_LANG + "&groupID="
				+ groupID;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupMembers", JSON.toJSONString(groupMembers));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupAddMembers:添加群成员. <br/>
	 *
	 * @author bxy-jing
	 * @param groupID
	 * @param groupMembers
	 * @since JDK 1.6
	 */
	public static void groupAddMembers(String groupID, List<String> groupMembers) {
		String url = GROUP_ADD_MEMBERS_URL + "?access_token=" + accessTokenWithCatch() + "&lang=" + DEFAULT_LANG + "&groupID="
				+ groupID;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupMembers", JSON.toJSONString(groupMembers));
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupGetMember:获取群成员信息. <br/>
	 *
	 * @author bxy-jing
	 * @param groupID
	 * @param groupMemberID
	 * @since JDK 1.6
	 */
	public static void groupGetMember(String groupID, String groupMemberID) {
		String url = GROUP_GET_MEMBER_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("groupMemberID", groupMemberID);
		params.put("access_token", accessTokenWithCatch());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupGetMembers:获取群成员列表信息. <br/>
	 *
	 * @author bxy-jing
	 * @param groupID
	 * @param pageNo
	 * @since JDK 1.6
	 */
	public static void groupGetMembers(String groupID, int pageNo) {
		String url = GROUP_GET_MEMBERS_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("pageNo", String.valueOf(pageNo));
		params.put("access_token", accessTokenWithCatch());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * groupGetMembersPageTimetamp:获取群成员列表页码时间戳. <br/>
	 *
	 * @author bxy-jing
	 * @param groupID
	 * @since JDK 1.6
	 */
	public static void groupGetMembersPageTimetamp(String groupID) {
		String url = GROUP_GET_MEMBERS_PAGE_TIMESTAMP_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("groupID", groupID);
		params.put("access_token", accessTokenWithCatch());
		params.put("lang", DEFAULT_LANG);
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	/**
	 * 
	 * appUpdateAppInfo:设置自定义菜单. <br/>
	 *
	 * @author bxy-jing
	 * @param menus
	 * @since JDK 1.6
	 */
	public static void appUpdateAppInfo(List<Menu> menus) {
		String url = APP_UPDATE_APP_INFO_URL + "?access_token=" + accessTokenWithCatch() + "&accessType=" + ACCESS_TYPE_CLIENT;
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

//	public static void setSubAccount(List<SubAccount> sas){
//		String url = APP_SET_SUBACCOUNT_URL+"?access_token=" + accessTokenWithCatch();
//		Map<String, String> params = new HashMap<String, String>();
//		String json = JSON.toJSONString(sas);
//		params.put("subAccount", json);
//		System.out.println(json);
//		Response response = HttpUtil.post(url, params);
//		System.out.println(response.getBody());
//	}
	
	public static void setSubAccount(JSONArray sas){
		String url = APP_SET_SUBACCOUNT_URL+"?access_token=" + accessTokenWithCatch();
		Map<String, String> params = new HashMap<String, String>();
		String json = sas.toJSONString();
		params.put("subAccount", json);
		System.out.println(json);
		Response response = HttpUtil.post(url, params);
		System.out.println(response.getBody());
	}
	
	public static void removeSubAccount(){
		String url = APP_REMOVE_SUBACCOUNT_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessTokenWithCatch());
		Response response = HttpUtil.get(url, params);
		System.out.println(response.getBody());
	}

	public static void sendImgMsg(String userId){
		Message message = new Message();
		message.setSendUserID(PlatformUtil.appID);
		message.setReceTargetID(userId);
		message.setMessageType("5");
		JSONObject img = new JSONObject();
		img.put("thumbUrl", "hd3/0/appimage/20160323/1520/head_SNa4_7b900000017cb1d5.jpg");
		img.put("mediaUrl", "hd3/0/appimage/20160323/1520/head_SNa4_7b900000017cb1d5.jpg");
		img.put("fileName", "head_SNa4_7b900000017cb1d5.jpg");
		img.put("docid", "0");
//		img.put("enc_dec_key", "123");
		message.setMessage(img);
		PlatformUtil.sendMsg(message);
	}
	
	public static void main(String[] args) {
//		removeSubAccount();
//		System.out.println(Long.toString(4328621782l));
//		List<SubAccount> sas = new ArrayList<SubAccount>();
//		SubAccount sa = new SubAccount();
//		sa.setDID(appID+"001");
//		sa.setDName("神灯");
//		sa.setDPic("http://vrv.linkdood.cn/hd5/0/appimage/20160310/1525/head_hcw5_2c11000038362a01.png");
//		sa.setDDesc("阿拉丁的神灯");
//		sas.add(sa);
//		setSubAccount(sas);
		
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("DID", appID+"001");
		jo.put("DName", "神灯25");
		jo.put("DPic", "http://vrv.linkdood.cn/hd5/0/appimage/20160310/1525/head_hcw5_2c11000038362a01.png");
		jo.put("DDesc", "阿拉hsd");
		JSONObject jo2 = new JSONObject();
		jo2.put("DID", appID+"002");
		jo2.put("DName", "钢铁侠");
		jo2.put("DPic", "http://vrv.linkdood.cn/hd5/0/appimage/20160310/1525/head_hcw5_2c11000038362a01.png");
		jo2.put("DDesc", "赫赫");
		ja.add(jo);
//		ja.add(jo2);
//		setSubAccount(ja);
//		userInfo("9151315548882010115");
//		removeSubAccount();
		//4328621782 朱建宇   4328625706 靖守彦
		String userToken = "9151316648393638091"; // 4395732306 4395733301 4395733556		
		sendImgMsg("9151315548882010969");
//		// String accessToken = platformUtil.accessTokenWithCatch();
//		// System.out.println(accessToken);
//		// platformUtil.userInfo("4395733301");
//
		 Messages messages = new Messages();
		 messages.setSendUserID(PlatformUtil.appID);
		 List<String> targets = new ArrayList<String>();
		 targets.add(userToken);
		 targets.add(userToken);
		 targets.add(userToken);
		 targets.add(userToken);
		 targets.add(userToken);
		 targets.add(userToken);
		 targets.add(userToken);
		 messages.setReceTargetID(targets);
		 messages.setMessage("开放平台测试");
		 messages.setMessageType("2");
//		 PlatformUtil.sendMsgs(messages);
		 
		 String type="2";
		 String file="d:/48540923dd54564ec40a9c82b3de9c82d1584f19.jpg";
//		 PlatformUtil.resourceUpload(type, file);
//		 File f =new File(file);
//		 PlatformUtil.resourceQuery("2",1, 100);

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

//		 GroupInfo groupInfo = new GroupInfo();
//		 groupInfo.setGroupName("开放平台测试组2");
//		 groupInfo.setGroupIcon("");
//		 groupInfo.setGroupType("测试群");
//		 groupInfo.setGroupLevel(1);
//		 groupInfo.setGroupBrief("这只是一个开放平台的接口测试");
//		 groupInfo.setGroupBulletin("群公告呀");
//		 groupInfo.setRelatedEnterpriseID(Long.parseLong(entID));
//		 List<Long> initGroupMembers=new ArrayList<Long>();
//		 initGroupMembers.add(4328621782l);
//		 initGroupMembers.add(4328625706l);
//		 initGroupMembers.add(Long.parseLong(appID));
//		 groupInfo.setInitGroupMembers(initGroupMembers);
//		 PlatformUtil.groupCreate(groupInfo);//4404020003  4336919145
//		 
//		 List<String> members = new ArrayList<String>();
//		 members.add(appID);
//		 PlatformUtil.groupAddMembers("4336919145",members );

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
		
//		Notification notification =  new Notification();
//		notification.setFromUser("4395733556");
//		
//		notification.setTitle("分享测试标题");
//		notification.setContent("分享测试内容");
//		notification.setToUsers("4395733556");
//		PlatformUtil.msgNotify(notification);

		List<Menu> menus = new ArrayList<Menu>();
//		Menu menu1 = new Menu();
//		menu1.setName("呵呵哒");
//		menu1.setType("view");
//		menu1.setUrl("http://www.baidu.com");
		Menu menu2 = new Menu();
		menu2.setName("帮助");
		menu2.setType("deviceInfo");
		menu2.setKey("DEVICE_INFO");
//		Menu menu3 = new Menu();
//		menu3.setName("菜单组");
//		List<Menu> ms = new ArrayList<Menu>();
//		Menu menu31 = new Menu();
//		menu31.setName("呵呵哒2");
//		menu31.setType("view");
//		menu31.setUrl("http://www.baidu.com");
//		ms.add(menu31);
//		Menu menu32 = new Menu();
//		menu32.setName("赫赫2");
//		menu32.setType("click");
//		menu32.setKey("CLICK_HEHE");
//		menu3.setSub_menus(ms);
//		menus.add(menu1);
//		menus.add(menu3);
		menus.add(menu2);
//		ms.add(menu32);

//		PlatformUtil.appUpdateAppInfo(menus);
//		PlatformUtil.accessToken();
		
		PlatformUtil.userAllBuddys(PlatformUtil.appID);

	}
}
