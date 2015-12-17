package com.jing.web.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jing.web.dao.UserDao;
import com.jing.web.model.User;

@Repository
public class UserDaoImpl implements UserDao{
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public void add(User user) {
		// TODO Auto-generated method stub
		
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	public User find(long id) {
		String sql = "select * from users where id=?";
		Object[] args = {id};
		List<User> users = jdbcTemplate.query(sql, args, new RowMapper<User>() {
			public User mapRow(ResultSet result, int rowNum) throws SQLException {
				User user = new User();
				user.setId(result.getLong("id"));
				user.setName(result.getString("name"));
				user.setGender(result.getString("gender"));
				user.setAge(result.getInt("age"));
				user.setCreatedAt(result.getDate("created_at"));
				user.setUpdatedAt(result.getDate("updated_at"));
				return user;
			}
		});
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}




}
