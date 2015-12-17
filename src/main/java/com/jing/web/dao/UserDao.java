package com.jing.web.dao;

import com.jing.web.model.User;

public interface UserDao {
	void add(User user);
	void update(User user);
	User find(long id);
	void delete(User user);
}
