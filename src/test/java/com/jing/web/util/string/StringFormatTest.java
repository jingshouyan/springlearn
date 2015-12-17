package com.jing.web.util.string;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

public class StringFormatTest {

	// @Test
	public void map2QueryStringTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "张三");
		map.put("age", 23);
		map.put("gender", "male");
		String toBe = "gender=male&name=%E5%BC%A0%E4%B8%89&age=23";
		String queryString = StringFormat.map2QueryString(map);
		System.out.println(queryString);
		Assert.assertEquals(queryString, toBe);

	}

	// @Test
	public void underScore2CamelTest() {
		String underScore = "name_old_time";
		String toBe = "nameOldTime";
		String camel = StringFormat.underScore2Camel(underScore);
		System.out.println(camel);
		Assert.assertEquals(camel, toBe);
	}

	// @Test
	public void camel2UnderScore() {
		String camel = "HelloWorld";
		String toBe = "hello_world";
		String underScore = StringFormat.camel2UnderScore(camel);
		System.out.println(underScore);
		Assert.assertEquals(underScore, toBe);
	}

	// @Test
	public void underScore2CapitalizeCamelTest() {
		String underScore = "name_old_time";
		String toBe = "NameOldTime";
		String camel = StringFormat.underScore2CapitalizeCamel(underScore);
		System.out.println(camel);
		Assert.assertEquals(camel, toBe);
	}

//	@Test
	public void sortedStringTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < 100; i++) {
			String key = UUID.randomUUID().toString();
			map.put(key, i);
		}
		long start;
		long end;
		long delay;
		String sort = "";
		for (int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			sort = StringFormat.sortedString(map);
			end = System.currentTimeMillis();
			delay = end - start;
			System.out.println("1----delay:" + delay);
		}

		System.out.println(sort);
		for (int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			sort = StringFormat.sortedString2(map);
			end = System.currentTimeMillis();
			delay = end - start;
			System.out.println("2----delay:" + delay);
		}
		System.out.println(sort);

	}
	
	@Test
	public void pluralizeTest(){
		String word = "good";
		String words = StringFormat.pluralize(word);
		System.out.println(words);
	}
//	@Test
	public void singularizeTest(){
		String words = "goods";
		String word = StringFormat.singularize(words);
		System.out.println(word);
	}

}
