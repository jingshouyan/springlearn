/**
 * Project Name:springlearn
 * File Name:GroupInfo.java
 * Package Name:com.jing.web.util.vrv
 * Date:2016年1月26日下午2:40:13
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.vrv;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:GroupInfo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月26日 下午2:40:13 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class GroupInfo {
	private String groupID;
	private String groupName;	
	private String groupIcon;	
	private String groupType;	
	private int groupLevel;	
	private String groupBrief;	
	private String groupBulletin;	
	private long relatedEnterpriseID;	
	private List<Long> initGroupMembers = new ArrayList<Long>();
	
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupIcon() {
		return groupIcon;
	}
	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public int getGroupLevel() {
		return groupLevel;
	}
	public void setGroupLevel(int groupLevel) {
		this.groupLevel = groupLevel;
	}
	public String getGroupBrief() {
		return groupBrief;
	}
	public void setGroupBrief(String groupBrief) {
		this.groupBrief = groupBrief;
	}
	public String getGroupBulletin() {
		return groupBulletin;
	}
	public void setGroupBulletin(String groupBulletin) {
		this.groupBulletin = groupBulletin;
	}
	public long getRelatedEnterpriseID() {
		return relatedEnterpriseID;
	}
	public void setRelatedEnterpriseID(long relatedEnterpriseID) {
		this.relatedEnterpriseID = relatedEnterpriseID;
	}
	public List<Long> getInitGroupMembers() {
		return initGroupMembers;
	}
	public void setInitGroupMembers(List<Long> initGroupMembers) {
		this.initGroupMembers = initGroupMembers;
	}
}

