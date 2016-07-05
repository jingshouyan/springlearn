package com.jing.web.util.vrv;
/**
 * ClassName:AccessToken <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 用于存储AccessToken <br/>
 * Date:     2016年2月19日 上午9:20:11 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class AccessToken {
	//机器人appID，用于多机器人区分。若只有一个。可以不使用
	private String appID;
	//accessToken 字符串
	private String tokenStr;
	//失效时间
	private long expAt;
	
	public String getAppID() {
		return appID;
	}
	public void setAppID(String appID) {
		this.appID = appID;
	}
	public String getTokenStr() {
		return tokenStr;
	}
	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}
	public long getExpAt() {
		return expAt;
	}
	public void setExpAt(long expAt) {
		this.expAt = expAt;
	}
	
	/**
	 * 
	 * enable:判断accessToken是否有效. <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public boolean enable(){
		return System.currentTimeMillis()<expAt;
	}
}

