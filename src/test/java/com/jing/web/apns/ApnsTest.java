/**
 * Project Name:springlearn
 * File Name:ApnsTest.java
 * Package Name:com.jing.web.apns
 * Date:2016年3月25日上午10:13:56
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.apns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jing.web.util.http.HttpUtil;
import com.jing.web.util.http.Response;
import com.jing.web.util.invoke.AsynInvoker;

/**
 * ClassName:ApnsTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月25日 上午10:13:56 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ApnsTest {
	
	private static final String BASE_URL="http://192.168.0.59:2197/config/";
	private static final String CREATE_URL=BASE_URL+"create/";
	private static final String MODIFY_URL=BASE_URL+"modify/";
	private static final String QUERY_URL=BASE_URL+"query/";
	private static final String DELETE_URL=BASE_URL+"remove/";
	
	public static Response create(Apns apns,String file){
		String url = CREATE_URL;
		Map<String, String> files =new HashMap<String,String>();
		Map<String, String> params=new HashMap<String,String>();
		files.put("file", file);
		params.put("data", JSON.toJSONString(apns));
		Response response = HttpUtil.upload(url, files, params);
		return response;
	}
	
	public static Response update(Apns apns,String file){
		String url = MODIFY_URL;
		Map<String, String> files =new HashMap<String,String>();
		Map<String, String> params=new HashMap<String,String>();
		files.put("file", file);
		params.put("data", JSON.toJSONString(apns));
		Response response = HttpUtil.upload(url, files, params);
		return response;
	}
	
	public static Response delete(Apns apns){
		String url = DELETE_URL;
		Map<String, String> params=new HashMap<String,String>();
		params.put("data", JSON.toJSONString(apns));
		Response response = HttpUtil.post(url, params);
		return response;
	}
	
	public static Response query(Apns apns){
		String url = QUERY_URL;
		Map<String, String> params=new HashMap<String,String>();
		params.put("data", JSON.toJSONString(apns));
		Response response = HttpUtil.post(url, params);
		return response;
	}
	static int successCount = 0;
	public static void cad(){
		Apns apns = new Apns();
		apns.setCertpwd("123456");
		apns.setCerttopic("cn.vrv.sdk");
		apns.setCerttype("prod");
//		apns.setCertid(4l);
		String file = "d:/cn.vrv.sdk(发布).p12";
//		String file = "d:/BugReport.txt";
		Response response = new Response();
		response = create(apns,file);
		System.out.println(response.getBody());
		JSONObject res = JSON.parseObject(response.getBody());
		if(res.getIntValue("code")==0){
			successCount++;
		}
		try{
			int certid = res.getJSONObject("msg").getIntValue("certid");
//			apns.setCertid(certid);
//			 response = update(apns,file);
//			response =query(new Apns());
//			 response = delete(apns);
//			 System.out.println(response.getBody());
		}catch(Exception e){
			
		}

	}
	
	
	public static void main(String[] args) {
		ApnsTest a = new ApnsTest();
		List<AsynInvoker<ApnsTest>> asynInvokers = new ArrayList<AsynInvoker<ApnsTest>>();
		for(int i = 0;i<1;i++){			
			AsynInvoker<ApnsTest> asynInvoker = new AsynInvoker<>(a, "cad");
			asynInvokers.add(asynInvoker);			
		}
		for(AsynInvoker<ApnsTest> a1:asynInvokers){
			a1.getData();
		}
		System.out.println(successCount);
		
//		Random r = new Random();
//		long now = System.currentTimeMillis();
//		String s =  Long.toHexString(now)+UUID.randomUUID().toString().replace("-", "");
//		int i = r.nextInt(10000);
//		System.out.println(i);
//		System.out.println(s+">>>"+s.length());
//		long n2 = Long.parseLong(s.substring(28), 16);
//		System.out.println(n2);
	}
}

