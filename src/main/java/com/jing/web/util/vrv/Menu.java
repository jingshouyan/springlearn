/**
 * Project Name:springlearn
 * File Name:Menu.java
 * Package Name:com.jing.web.util.vrv
 * Date:2016年1月26日下午9:08:43
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.vrv;

import java.util.List;

/**
 * ClassName:Menu <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月26日 下午9:08:43 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Menu {
	private String type;
	private String name;
	private String key;
	private String url;
	private List<Menu> sub_menus;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Menu> getSub_menus() {
		return sub_menus;
	}
	public void setSub_menus(List<Menu> sub_menus) {
		this.sub_menus = sub_menus;
	}
}

