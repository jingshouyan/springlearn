/**
 * Project Name:springlearn
 * File Name:Mail.java
 * Package Name:com.jing.web.util.mail
 * Date:2016年4月20日下午6:49:00
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.mail;

import java.util.Date;

/**
 * ClassName:Mail <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年4月20日 下午6:49:00 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Mail {
	private String sender;
	private String title;
	private long sendDate;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getSendDate() {
		return sendDate;
	}
	public void setSendDate(long sendDate) {
		this.sendDate = sendDate;
	}
}

