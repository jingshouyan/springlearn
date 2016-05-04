/**
 * Project Name:springlearn
 * File Name:TemplateMessage.java
 * Package Name:com.jing.web.util.vrv
 * Date:2016年4月7日上午9:53:46
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.vrv;

import java.util.List;
import java.util.Map;

/**
 * ClassName:TemplateMessage <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年4月7日 上午9:53:46 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TemplateMessage {
	private String sendUserID;
	private String title;
	private String titleColor;
	private String titleBgColor;
	private String creator;
	private String type;
	private String content;
	private String detailUrl;
	private String toUsers;
	private List<Map<String,String>> fields;
	private String status;
	private String statusColor;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleColor() {
		return titleColor;
	}
	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}
	public String getTitleBgColor() {
		return titleBgColor;
	}
	public void setTitleBgColor(String titleBgColor) {
		this.titleBgColor = titleBgColor;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public String getToUsers() {
		return toUsers;
	}
	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}
	public String getSendUserID() {
		return sendUserID;
	}
	public void setSendUserID(String sendUserID) {
		this.sendUserID = sendUserID;
	}
	public List<Map<String, String>> getFields() {
		return fields;
	}
	public void setFields(List<Map<String, String>> fields) {
		this.fields = fields;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusColor() {
		return statusColor;
	}
	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}
}

