package com.jing.web.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jing.web.dao.DbDao;
import com.jing.web.util.database.Compare;
import com.jing.web.util.object.ModelUtil;
import com.jing.web.util.string.StringFormat;

@Repository
public class DbDaoImpl<T>  implements DbDao<T>{

	public static final String TABLE_PREFIX = "";

	public static final Logger logger = LoggerFactory.getLogger(DbDaoImpl.class);

	private Class<T> clazz;

	public void setClass(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 
	* @Title: find 
	* @Description: 根据主键查询单条
	* @param @param id
	* @param @return    设定文件 
	* @return T    返回类型 
	* @throws
	 */	
	public T find(long id) {
		String sql = "select * from " + tableName() + " where " + dbKey() + "=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);		
//		RowMapper<T> rowMapper = new JRowMapper<T>(clazz);
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		
		logger.info(sql+paramMap.toString());
		List<T> ts = template.query(sql, paramMap, rowMapper);
		if (ts.isEmpty()) {
			return null;
		}
		return ts.get(0);
	}
	
	/**
	 * 
	* @Title: query 
	* @Description: 条件查询多条记录
	* @param @param map
	* @param @return    设定文件 
	* @return List<T>    返回类型 
	* @throws
	 */
	public List<T> query(Map<String, Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from "+tableName()+" where 1=1 ");
		Map<String,Object> mapB = new HashMap<String, Object>();
		if(!map.isEmpty()){
			for(String key:map.keySet()){
				String underScoreKey = StringFormat.camel2UnderScore(key);
				Object value = map.get(key);
				if(value instanceof List){
					sb.append(" and " + underScoreKey + " in (:" + key+")");
				}else if(value instanceof Compare){
					//各种比较
					Compare compare = (Compare) value;
					if(null!=compare.getGt()){
						sb.append(" and " + underScoreKey + ">:" + key+"__gt");
						mapB.put(key+"__gt", compare.getGt());
					}
					if(null!=compare.getLt()){
						sb.append(" and " + underScoreKey + "<:" + key+"__lt");
						mapB.put(key+"__lt", compare.getLt());
					}
					if(null!=compare.getGte()){
						sb.append(" and " + underScoreKey + ">=:" + key+"__gte");
						mapB.put(key+"__gte", compare.getGte());
					}
					if(null!=compare.getLte()){
						sb.append(" and " + underScoreKey + "<=:" + key+"__lte");
						mapB.put(key+"__lte", compare.getLte());
					}
					if(null!=compare.getNe()){
						sb.append(" and " + underScoreKey + "<>:" + key+"__ne");
						mapB.put(key+"__ne", compare.getNe());
					}
				}else{					
					sb.append(" and " + underScoreKey + "=:" + key);
				}
			}
		}
		map.putAll(mapB);
		String sql = sb.toString();
		logger.info(sql+map.toString());
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		List<T> ts = template.query(sql, map, rowMapper);
		return ts;
	}

	
	/**
	 * 
	* @Title: insert 
	* @Description: 新增记录
	* @param @param t    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insert(T t) {
		StringBuilder sb = new StringBuilder();
		StringBuilder keys = new StringBuilder();
		StringBuilder values = new StringBuilder();
		Map<String, Object> valueMap = ModelUtil.valueMap(t);
		for (String key : valueMap.keySet()) {
			// 如果是主键，且主键为空，说明是由数据库维护的主键
			if (key.equals(key()) && isEmtry(valueMap.get(key))) {
				continue;
			}
			keys.append(StringFormat.camel2UnderScore(key) + ",");
			values.append(":" + key + ",");
		}
		if (keys.length() > 0) {
			keys.deleteCharAt(keys.length() - 1);
			values.deleteCharAt(values.length() - 1);
		}
		sb.append("insert into " + tableName());
		sb.append("(" + keys.toString() + ") ");
		sb.append("values(" + values.toString() + ")");
		String sql = sb.toString();
		logger.info(sql + valueMap.toString());
		template.update(sql, valueMap);
	}
	
	public long insert2(T t){
		StringBuilder sb = new StringBuilder();
		StringBuilder keys = new StringBuilder();
		StringBuilder values = new StringBuilder();
		Map<String, Object> valueMap = ModelUtil.valueMap(t);
		for (String key : valueMap.keySet()) {
			// 如果是主键，且主键为空，说明是由数据库维护的主键
			if (key.equals(key()) && isEmtry(valueMap.get(key))) {
				continue;
			}
			keys.append(StringFormat.camel2UnderScore(key) + ",");
			values.append(":" + key + ",");
		}
		if (keys.length() > 0) {
			keys.deleteCharAt(keys.length() - 1);
			values.deleteCharAt(values.length() - 1);
		}
		sb.append("insert into " + tableName());
		sb.append("(" + keys.toString() + ") ");
		sb.append("values(" + values.toString() + ")");
		String sql = sb.toString();
		logger.info(sql + valueMap.toString());
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(t);
		KeyHolder generatedKeyHolder=new GeneratedKeyHolder(); 
		template.update(sql, paramSource, generatedKeyHolder);
		long key = generatedKeyHolder.getKey().longValue();
		return key;
	}

	/**
	 * 
	* @Title: update 
	* @Description: 更新记录
	* @param @param t    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void update(T t) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sets = new StringBuilder();
		StringBuilder where = new StringBuilder();
		where.append(" where 1=1 ");
		Map<String, Object> valueMap = ModelUtil.valueMap(t);
		for (String key : valueMap.keySet()) {
			String underScoreKey = StringFormat.camel2UnderScore(key);
			if (key.equals(key())) {
				where.append(" and " + underScoreKey + "=:" + key);
			} else if (key.equals(version())) {
				sets.append(" " + underScoreKey + "=" + underScoreKey + "+1,");
				where.append(" and " + underScoreKey + "=:" + key);
			} else {
				sets.append(" " + underScoreKey + "=:" + key + ",");
			}
		}
		if (sets.length() > 0) {
			sets.deleteCharAt(sets.length() - 1);
		}
		sb.append("update " + tableName() + " set ");
		sb.append(sets.toString());
		sb.append(where.toString());
		String sql = sb.toString();
		logger.info(sql + valueMap.toString());
		int fetch = template.update(sql, valueMap);
		if (0 == fetch) {

		}
	}

	/**
	 * 
	* @Title: delete 
	* @Description: 通过主键删除记录
	* @param @param id    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void delete(long id) {
		String sql = "delete " + tableName() + " where " + dbKey() + "=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		logger.info(sql+paramMap.toString());
		template.update(sql, paramMap);
	}

	private String tableName() {
		String className = clazz.getSimpleName();
		String tableName = StringFormat.pluralize(StringFormat.camel2UnderScore(className));
		return TABLE_PREFIX + tableName;
	}

	private String key() {
		return "id";
	}
	
	private String dbKey(){
		return StringFormat.camel2UnderScore(key());
	}

	private String version() {
		return "version";
	}

	private static boolean isEmtry(Object o) {
		if (null == o) {
			return true;
		}
		if (o instanceof Integer) {
			return o.equals(0);
		}
		if (o instanceof Long) {
			return o.equals(0l);
		}
		return o.equals("");
	}

}
