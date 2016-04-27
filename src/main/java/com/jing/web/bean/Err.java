/**
 * Project Name:springlearn
 * File Name:Error.java
 * Package Name:com.jing.web.bean
 * Date:2016年4月8日下午7:07:23
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.bean;
/**
 * ClassName:Error <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年4月8日 下午7:07:23 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Err {
	private String errName;
	private String errMessage;
	private int errCode;
	
	public Err(){}
	public Err(Exception e){
		errName =e.getClass().getName();
		errMessage = e.getMessage();
	}
	
	public String getErrName() {
		return errName;
	}
	public void setErrName(String errName) {
		this.errName = errName;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
}

