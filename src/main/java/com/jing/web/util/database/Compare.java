package com.jing.web.util.database;

/**
 * 
* @ClassName: Compare
* @Description: 数据查询各种比较
* @author 靖守彦 jingshouyan@126.com
* @date 2015年12月11日 下午6:01:59
*
 */
public class Compare {	

	private Object gt;//大于
	private Object lt;//小于 
	private Object gte;//大于等于
	private Object lte;//小于等于
	private Object ne;//不等于
	public Object getNe() {
		return ne;
	}
	public void setNe(Object ne) {
		this.ne = ne;
	}
	public Object getGt() {
		return gt;
	}
	public void setGt(Object gt) {
		this.gt = gt;
	}
	public Object getLt() {
		return lt;
	}
	public void setLt(Object lt) {
		this.lt = lt;
	}
	public Object getGte() {
		return gte;
	}
	public void setGte(Object gte) {
		this.gte = gte;
	}
	public Object getLte() {
		return lte;
	}
	public void setLte(Object lte) {
		this.lte = lte;
	}
	
}
