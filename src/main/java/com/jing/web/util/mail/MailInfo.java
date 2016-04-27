/**
 * Project Name:springlearn
 * File Name:MailInfo.java
 * Package Name:com.jing.web.util.mail
 * Date:2016年4月14日下午4:21:42
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.mail;
/**
 * ClassName:MailInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年4月14日 下午4:21:42 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class MailInfo {
	
	private String pop3Host;
	private int pop3Port;
	private String username;
	private String password;
	
	public String getPop3Host() {
		return pop3Host;
	}
	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}
	public int getPop3Port() {
		return pop3Port;
	}
	public void setPop3Port(int pop3Port) {
		this.pop3Port = pop3Port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

