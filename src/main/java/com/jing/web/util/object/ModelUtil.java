package com.jing.web.util.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* @ClassName: ModelUtil
* @Description: model获取所有值的map及通过map付值
* @author 靖守彦 jingshouyan@126.com
* @date 2015年12月10日 下午5:41:53
*
 */
public class ModelUtil {

	/**
	 * 
	* @Title: valueMap 
	* @Description: 获取model的值map
	* @param @param obj
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public static Map<String, Object> valueMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		// System.out.println(obj.getClass());
		// 获取f对象对应类中的所有属性域
		Class<? extends Object> c = obj.getClass();
		while (c != Object.class) {
			Field[] fields = c.getDeclaredFields();
			// obj.getClass().get
			for (int i = 0, len = fields.length; i < len; i++) {
				String varName = fields[i].getName();
				try {
					if(Modifier.isStatic(fields[i].getModifiers())){
						continue;
					}
					// 获取原来的访问控制权限
					boolean accessFlag = fields[i].isAccessible();
					// 修改访问控制权限
					fields[i].setAccessible(true);
					// 获取在对象f中属性fields[i]对应的对象中的变量
					Object o = fields[i].get(obj);
					map.put(varName, o);
					// 恢复访问控制权限
					fields[i].setAccessible(accessFlag);
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				}
			}
			c = c.getSuperclass();
		}
		return map;
	}

	/**
	 * 
	* @Title: setValues 
	* @Description: 使用map给model赋值
	* @param @param model
	* @param @param map    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void setValues(Object model, Map<String, Object> map) {
		Class<? extends Object> c = model.getClass();
		while (Object.class != c) {
			Field[] fields = c.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
			
			for (int j = 0; j < fields.length; j++) { // 遍历所有属性
				String name = fields[j].getName(); // 获取属性的名字
				if(Modifier.isStatic(fields[j].getModifiers())){
					continue;
				}
				String key = name;
				name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
				Class<?> clazz =(Class<?>) fields[j].getGenericType();
				Method m;
				try {
					m = model.getClass().getMethod("set" + name, clazz);
					Object value = map.get(key);
					if(null==value){
						continue;
					}
					m.invoke(model, value);
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
	}
}
