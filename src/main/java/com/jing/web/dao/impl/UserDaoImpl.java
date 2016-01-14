package com.jing.web.dao.impl;

import org.springframework.stereotype.Repository;

import com.jing.web.dao.UserDao;
import com.jing.web.model.User;

@Repository
public class UserDaoImpl extends DbDaoImpl<User> implements UserDao{

	public UserDaoImpl(){
		init();
	}
}
