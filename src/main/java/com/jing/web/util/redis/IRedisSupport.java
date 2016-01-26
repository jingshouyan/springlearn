package com.jing.web.util.redis;

import java.io.Serializable;

public interface IRedisSupport extends Serializable{
	public static final String KEY_PREFIX="";
	String uid();
}
