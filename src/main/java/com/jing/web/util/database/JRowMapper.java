package com.jing.web.util.database;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.jing.web.util.string.StringFormat;


public class JRowMapper<T> implements RowMapper<T>{
	
	private Class<T> clazz;
	

	public JRowMapper(Class<T> clazz){
		this.clazz = clazz;
	}
	

	public T mapRow(ResultSet result, int rowNum) throws SQLException {	
		T t;
		try {
			t = (T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		Class<? extends Object> c = t.getClass();
		while (Object.class != c) {
			Field[] field = c.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
			
			for (int j = 0; j < field.length; j++) { // 遍历所有属性
				String name = field[j].getName(); // 获取属性的名字
				if(Modifier.isStatic(field[j].getModifiers())){
					continue;
				}
				String key = name;
				String dbKey = StringFormat.camel2UnderScore(key);
				name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
				Class<?> clazz =(Class<?>) field[j].getGenericType();
				Method m;
				try {
					m = t.getClass().getMethod("set" + name, clazz);
					Object value = null;
					
					if(long.class ==clazz||Long.class==clazz){
						value = result.getLong(dbKey);
					}else if(int.class==clazz||Integer.class==clazz){
						value = result.getInt(dbKey);
					}else if(boolean.class==clazz||Boolean.class==clazz){
						value = result.getBoolean(dbKey);
					}else if(float.class==clazz||Float.class==clazz){
						value = result.getFloat(dbKey);
					}else if(double.class==clazz||Double.class==clazz){
						value = result.getDouble(dbKey);
					}else if(String.class==clazz){
						value = result.getString(dbKey);
					}else if(Date.class==clazz){
						value  =result.getDate(dbKey);
					}
					
					if(null==value){
						continue;
					}
					m.invoke(t, value);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}

			c = c.getSuperclass();
		}
		
		return t;
		 

	}

}
