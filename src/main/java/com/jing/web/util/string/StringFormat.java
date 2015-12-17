package com.jing.web.util.string;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
* @ClassName: StringFormat
* @Description: 字符串处理类
* @author 靖守彦 jingshouyan@126.com
* @date 2015年12月10日 下午1:30:58
*
 */
public class StringFormat {
	private static final char SEPARATOR = '_';

	/**
	 * 
	* @Title: underScore2Camel 
	* @Description: 下划线格式转驼峰格式
	* @param @param underScore
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String underScore2Camel(String underScore) {

		if (underScore == null) {
			return null;
		}

		underScore = underScore.toLowerCase();

		StringBuilder sb = new StringBuilder(underScore.length());
		boolean upperCase = false;
		for (int i = 0; i < underScore.length(); i++) {
			char c = underScore.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 
	* @Title: camel2UnderScore 
	* @Description: 驼峰格式转下划线格式
	* @param @param camel
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String camel2UnderScore(String camel) {
		if (camel == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < camel.length(); i++) {
			char c = camel.charAt(i);

			boolean nextUpperCase = true;

			if (i < (camel.length() - 1)) {
				nextUpperCase = Character.isUpperCase(camel.charAt(i + 1));
			}

			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 
	* @Title: underScore2CapitalizeCamel 
	* @Description: 下划线格式转首字母大写的驼峰格式
	* @param @param underScore
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String underScore2CapitalizeCamel(String underScore) {
		if (underScore == null) {
			return null;
		}
		underScore = underScore2Camel(underScore);
		return underScore.substring(0, 1).toUpperCase() + underScore.substring(1);
	}

	/**
	 * 
	* @Title: map2QueryString 
	* @Description: 将map转为http请求字符串
	* @param @param map
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String map2QueryString(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			try {
				Object value = map.get(key);
				if (null == value)
					value = "";
				sb.append(URLEncoder.encode(key, "UTF-8") + "=");
				sb.append(URLEncoder.encode(value.toString(), "UTF-8") + "&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		int sbLength = sb.length();
		if (sbLength > 0) {
			sb.deleteCharAt(sbLength - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	* @Title: queryString2map 
	* @Description: http请求字符串转map
	* @param @param queryString
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	public static Map<String, String> queryString2map(String queryString) {
		Map<String, String> map = new HashMap<String, String>();
		String[] ss = queryString.split("&");
		for (int i = 0; i < ss.length; i++) {
			String[] kv = ss[i].split("=");
			String key = kv[0];
			String value = kv[1];
			try {
				key = URLDecoder.decode(key, "UTF-8");
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 性能略高(也差不多)
	* @Title: sortedString 
	* @Description: 将map对象排序后拼接成key=value&key2=value2格式的字符串（排除value为空的），通常作为签名原串使用
	* @param @param map
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String sortedString(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();

		List<String> list = new ArrayList<String>(map.keySet());
		Collections.sort(list);
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = map.get(key);
			if (null == value) {
				continue;
			}
			String v = value.toString();
			if ("".equals(v)) {
				continue;
			}
			sb.append(key + "=" + v + "&");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	* @Title: sortedString2 
	* @Description: 将map对象排序后拼接成key=value&key2=value2格式的字符串（排除value为空的），通常作为签名原串使用
	* @param @param map
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String sortedString2(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>(map);
		for (Entry<String, Object> entry : treeMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (null == value) {
				continue;
			}
			String v = value.toString();
			if ("".equals(v)) {
				continue;
			}
			sb.append(key + "=" + value + "&");
		}
		int sbLength = sb.length();
		if (sbLength > 0) {
			sb.deleteCharAt(sbLength - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	* @Title: pluralize 
	* @Description: 获取单词的复数
	* @param @param word
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String pluralize(String word) {
		return Inflector.getInstance().pluralize(word);
	}

	/**
	 * 
	* @Title: singularize 
	* @Description: 获取单词的单数
	* @param @param word
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String singularize(String word) {
		return Inflector.getInstance().singularize(word);
	}

}
