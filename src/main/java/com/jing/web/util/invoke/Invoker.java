package com.jing.web.util.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invoker {

	/**
	 * 执行对象的指定方法
	 * @param o 对象
	 * @param method 方法名
	 * @param returnType 返回参数类型
	 * @param args 方法参数数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invoke(Object o, String method, Class<T> returnType, Object... args) {
		T r = null;
		try {
			Class<? extends Object> clazz = o.getClass();
			System.out.println(clazz.toString());
			int len = args.length;
			Method me = null;
			Method[] ms = clazz.getDeclaredMethods();
			for (Method m : ms) {
				String mName = m.getName();
				if (!method.equals(mName)) {//名称匹配
					continue;
				}
				Class<?> rType = m.getReturnType();
				if (!classEquals(rType, returnType)) {//返回类型不匹配
					continue;
				}
				Class<?>[] pts = m.getParameterTypes();
				if (pts.length != len) {//参数数量不匹配
					continue;
				}
				boolean paramsTypeEqual = true;
				for (int i = 0; i < len; i++) {
					if(!classEEI(pts[i],args[i].getClass())){
						paramsTypeEqual = false;
						break;
					}
				}
				if(paramsTypeEqual){
					me = m;
					break;
				}

			}
			System.out.println(me.toString());
			r = (T) me.invoke(o, args);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	/**
	 * 执行对象的方法（void方法或不需要返回）
	 * @param o 对象
	 * @param method 方法名
	 * @param args 参数数组
	 */
	public static void invoke(Object o, String method, Object... args) {
		
		try {
			Class<? extends Object> clazz = o.getClass();
			int len = args.length;
			Method me = null;
			Method[] ms = clazz.getDeclaredMethods();
			for (Method m : ms) {
				String mName = m.getName();
				if (!method.equals(mName)) {//名称匹配
					continue;
				}				
				Class<?>[] pts = m.getParameterTypes();
				if (pts.length != len) {//参数数量不匹配
					continue;
				}
				boolean paramsTypeEqual = true;
				for (int i = 0; i < len; i++) {
					if(!classEEI(pts[i],args[i].getClass())){
						paramsTypeEqual = false;
						break;
					}
				}
				if(paramsTypeEqual){
					me = m;
					break;
				}
			}
			me.invoke(o, args);
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

	public static boolean classEquals(Class<?> a, Class<?> b) {
		if (a.equals(b)) {
			return true;
		}
		if (boxing(a).equals(boxing(b))) {
			return true;
		}
		return false;
	}

	public static Class<?> boxing(Class<?> clazz) {
		if (byte.class.equals(clazz)) {
			return Byte.class;
		}
		if (short.class.equals(clazz)) {
			return Short.class;
		}
		if (int.class.equals(clazz)) {
			return Integer.class;
		}
		if (long.class.equals(clazz)) {
			return Long.class;
		}
		if (char.class.equals(clazz)) {
			return Character.class;
		}
		if (float.class.equals(clazz)) {
			return Float.class;
		}
		if (double.class.equals(clazz)) {
			return Double.class;
		}
		if (boolean.class.equals(clazz)) {
			return Boolean.class;
		}
		return clazz;
	}

	public static boolean classEEI(Class<?> parent, Class<?> child) {
		if (classEquals(parent, child)) {
			return true;
		}
		Class<?> pbox = boxing(parent);
		Class<?> cbox = boxing(child);
		if (cbox.isAssignableFrom(pbox)) {
			return true;
		}
		return false;
	}
}
