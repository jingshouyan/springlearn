package com.jing.web.dao.impl;

//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jing.web.dao.UserDao;
import com.jing.web.model.User;
import com.jing.web.util.database.JRowMapper;

@Repository
public class UserDaoImpl2 implements UserDao{
	@Autowired
	private NamedParameterJdbcTemplate template;

	public void add(User user) {
		// TODO Auto-generated method stub
		
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	public User find(long id) {
		String sql = "select * from users where id=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
//		RowMapper<User> rowMapper = new RowMapper<User>() {
//			public User mapRow(ResultSet result, int rowNum) throws SQLException {
//				
//				User user = new User();
//				user.setId(result.getLong("id"));
//				user.setName(result.getString("name"));
//				user.setGender(result.getString("gender"));
//				user.setAge(result.getInt("age"));
//				user.setCreatedAt(result.getDate("created_at"));
//				user.setUpdatedAt(result.getDate("updated_at"));
//				return user;
//			}
//		};
		RowMapper<User> rowMapper = new JRowMapper<User>(User.class);
		List<User> users = template.query(sql, paramMap, rowMapper);
		if(users.isEmpty()){
			return null;
		}
		return users.get(0);
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}

}
