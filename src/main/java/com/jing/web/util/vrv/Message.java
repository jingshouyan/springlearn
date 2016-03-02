/**
 * Project Name:springlearn
 * File Name:Message.java
 * Package Name:com.jing.web.util.vrv
 * Date:2016年1月23日下午4:27:17
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.vrv;
/**
 * ClassName:Message <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月23日 下午4:27:17 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Message {
	private String sendUserID;
	private String messageType;
	private String receTargetID;
	private Object message;
	public String getSendUserID() {
		return sendUserID;
	}
	public void setSendUserID(String sendUserID) {
		this.sendUserID = sendUserID;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getReceTargetID() {
		return receTargetID;
	}
	public void setReceTargetID(String receTargetID) {
		this.receTargetID = receTargetID;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
}

