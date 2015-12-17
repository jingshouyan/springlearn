package com.jing.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

public interface DbDao<T> {
	@Cacheable(value="users", key="#p0")
	T find(long id) ;
	List<T> query(Map<String, Object> map);
	void insert(T t);
	long insert2(T t);
	void update(T t) ;
	void delete(long id);
	void setClass(Class<T> clazz);
}
