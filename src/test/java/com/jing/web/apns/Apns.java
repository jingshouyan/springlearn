/**
 * Project Name:springlearn
 * File Name:Apns.java
 * Package Name:com.jing.web.apns
 * Date:2016年3月25日上午10:18:25
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.apns;
/**
 * ClassName:Apns <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月25日 上午10:18:25 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Apns {
	private String user = "admin";
	private String pwd ="123456";
	private long entid=258;
	private String certpwd;
	private String certtype;
	private String certtopic;
	private long certid;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public long getEntid() {
		return entid;
	}
	public void setEntid(long entid) {
		this.entid = entid;
	}
	public String getCertpwd() {
		return certpwd;
	}
	public void setCertpwd(String certpwd) {
		this.certpwd = certpwd;
	}
	public String getCerttype() {
		return certtype;
	}
	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}
	public String getCerttopic() {
		return certtopic;
	}
	public void setCerttopic(String certtopic) {
		this.certtopic = certtopic;
	}
	public long getCertid() {
		return certid;
	}
	public void setCertid(long certid) {
		this.certid = certid;
	}
}

