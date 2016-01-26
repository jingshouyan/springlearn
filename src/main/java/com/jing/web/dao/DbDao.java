package com.jing.web.dao;

import java.util.List;
import java.util.Map;


import com.jing.web.util.database.Page;

public interface DbDao<T> {
//	@Cacheable(value="users", key="#p0")
	T find(long id) ;
	List<T> query(Map<String, Object> map);
	Page<T> query(Map<String,Object> map,Page<T> page);
	long count(Map<String, Object> map);
	void insert(T t);
	long insert2(T t);
	void batchInsert(List<T> list);
	void update(T t) ;
	void delete(long id);
	void delete(List<Long> ids);
	void delete(long[] ids);
	void setClass(Class<T> clazz);
}
