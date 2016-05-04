/**
 * Project Name:springlearn
 * File Name:Image.java
 * Package Name:com.jing.web.util.vrv
 * Date:2016年4月28日下午1:50:40
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.vrv;
/**
 * ClassName:Image <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年4月28日 下午1:50:40 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Image {
	private String thumbUrl;
	private String mediaUrl;
	private String fileName;
	private int docid;
	private String enc_dec_key;
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getDocid() {
		return docid;
	}
	public void setDocid(int docid) {
		this.docid = docid;
	}
	public String getEnc_dec_key() {
		return enc_dec_key;
	}
	public void setEnc_dec_key(String enc_dec_key) {
		this.enc_dec_key = enc_dec_key;
	}
}

