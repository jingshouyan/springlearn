package com.jing.web.model;

import java.util.Date;
import java.util.List;

import com.jing.web.util.database.AbstractJModel;
import com.jing.web.util.database.PrimaryKey;
import com.jing.web.util.database.Version;
import com.jing.web.util.redis.IRedisSupport;

public class User extends AbstractJModel implements IRedisSupport{
	/**
	* @Fields serialVersionUID 
	*/	
	private static final long serialVersionUID = 5887407396419668791L;
	
	@PrimaryKey
	private long id;
	private String name;
	private int age;
	private String gender;
	private Date createdAt;
	private Date updatedAt;
	@Version
	private int version;
	
	private List<String> names;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}


	public String uid() {
		return String.valueOf(id);
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", version=" + version + ", names=" + names + "]";
	}
}
