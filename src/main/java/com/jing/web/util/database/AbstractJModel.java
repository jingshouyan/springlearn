package com.jing.web.util.database;

public abstract class AbstractJModel {
	
	public static String table(){
		return null;
	}
	
	public static String primaryKey(){
		return "id";
	}
	
	public static String version(){
		return null;
	}
}
