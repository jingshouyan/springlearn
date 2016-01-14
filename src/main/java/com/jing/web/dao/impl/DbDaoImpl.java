package com.jing.web.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
import com.jing.web.model.User;
import com.jing.web.util.database.Compare;
import com.jing.web.util.database.Page;
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
	
	public void init(){
		Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            clazz = (Class<T>)p[0];
        }
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
	 * query:分页查询记录. <br/>
	 *
	 * @author bxy-jing
	 * @param map 查询条件
	 * @param page 分页情况
	 * @return
	 * @since JDK 1.6
	 */
	public Page<T> query(Map<String,Object> map,Page<T> page){
		if(null==map){
			map = new HashMap<String, Object>();
		}
		//查询符合条件的总记录数
		Map<String, Object> countMap = new HashMap<String, Object>(map);
		long count = count(countMap);
		page.setTotalCount(count);
		//查询该页记录，拼装查询sql
		StringBuilder sb = new StringBuilder();
		sb.append("select * from "+tableName()+" where 1=1 ");
		sb.append(where(map));
		//如果有orderBy选项，添加order by
		if(page.getOrderBy()!=null){
			sb.append(" order by "+field2DbColumn(page.getOrderBy())+" "+page.getSort()+" ");
		}
		//计算
		long limit = (page.getPage()-1)*page.getPageSize();
		sb.append(" limit "+limit+", "+page.getPageSize()+" ");
		String sql = sb.toString();
		logger.info(sql+map.toString());
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		List<T> ts = template.query(sql, map, rowMapper);
		page.setList(ts);
		return page;
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
		if(null==map){
			map =  new HashMap<String, Object>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select * from "+tableName()+" where 1=1 ");
		sb.append(where(map));
		String sql = sb.toString();
		logger.info(sql+map.toString());
		RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(clazz);
		List<T> ts = template.query(sql, map, rowMapper);
		return ts;
	}
	
	/**
	 * 
	 * count:查询满足挑你的记录行数
	 *
	 * @author bxy-jing
	 * @param map
	 * @return
	 * @since JDK 1.6
	 */
	public long count(Map<String, Object> map){
		if(null==map){
			map =  new HashMap<String, Object>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from "+tableName()+" where 1=1 ");
		sb.append(where(map));
		String sql = sb.toString();
		logger.info(sql+map.toString());
		long count = template.queryForObject(sql, map, Long.class);
		return count;
	}
	
	/**
	 * 
	 * where:根据查询条件拼装sql. <br/>
	 * 且将map重构
	 * @author bxy-jing
	 * @param map 查询条件
	 * @return
	 * @since JDK 1.6
	 */
	private String where(Map<String, Object> map){
		StringBuilder sb = new StringBuilder();
		Map<String,Object> mapB = new HashMap<String, Object>();
		if(!map.isEmpty()){
			for(String key:map.keySet()){
				String column = field2DbColumn(key);
				Object value = map.get(key);
				if(value instanceof List){
					sb.append(" and " + column + " in (:" + key+")");
				}else if(value instanceof Compare){
					//各种比较
					Compare compare = (Compare) value;
					if(null!=compare.getGt()){
						sb.append(" and " + column + ">:" + key+"__gt");
						mapB.put(key+"__gt", compare.getGt());
					}
					if(null!=compare.getLt()){
						sb.append(" and " + column + "<:" + key+"__lt");
						mapB.put(key+"__lt", compare.getLt());
					}
					if(null!=compare.getGte()){
						sb.append(" and " + column + ">=:" + key+"__gte");
						mapB.put(key+"__gte", compare.getGte());
					}
					if(null!=compare.getLte()){
						sb.append(" and " + column + "<=:" + key+"__lte");
						mapB.put(key+"__lte", compare.getLte());
					}
					if(null!=compare.getNe()){
						sb.append(" and " + column + "<>:" + key+"__ne");
						mapB.put(key+"__ne", compare.getNe());
					}
				}else{					
					sb.append(" and " + column + "=:" + key);
				}
			}	
		}
		map.putAll(mapB);
		return sb.toString();
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
			keys.append(field2DbColumn(key) + ",");
			values.append(":" + key + ",");
		}
		if (keys.length() > 0) {
			keys.deleteCharAt(keys.length() - 1);
			values.deleteCharAt(values.length() - 1);
		}
		sb.append("insert into " + tableName());
		sb.append("(" + keys.toString() + ") ");
		sb.append(" values(" + values.toString() + ")");
		String sql = sb.toString();
		logger.info(sql + valueMap.toString());
		template.update(sql, valueMap);
	}
	
	public void batchInsert(List<T> list){		
		//如果list为空，直接返回
		if(null==list||list.isEmpty()){
			return;
		}
		T t = list.get(0);
		Map<String,Object> map = ModelUtil.valueMap(t);
		//如果model没有属性，直接返回
		if(map.isEmpty()){
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		StringBuilder keys = new StringBuilder();
		StringBuilder values = new StringBuilder();
		values.append(" values");
		int i=0;
		Map<Integer,String> keyMap = new HashMap<Integer,String>();
		Map<String,Object> valueMap = new HashMap<String,Object>();
		for(String key:map.keySet()){
			// 如果是主键，且主键为空，说明是由数据库维护的主键
			if (key.equals(key()) && isEmtry(map.get(key))) {
				continue;
			}
			//拼装keys字符串
			keys.append(field2DbColumn(key) + ",");
			//将所有的key标上序号放在map中，待用
			keyMap.put(i, key);
			i++;
		}
		keys.deleteCharAt(keys.length()-1);
		int listSize = list.size();
		for(int j=0;j<listSize;j++){
			t = list.get(j);
			map = ModelUtil.valueMap(t);
			StringBuilder valueSb = new StringBuilder();
			for(int k = 0;k<i;k++){
				String key = keyMap.get(k)+"_"+j+"_"+k;
				valueSb.append(":"+key+",");
				valueMap.put(key, map.get(keyMap.get(k)));
			}
			valueSb.deleteCharAt(valueSb.length()-1);
			values.append("("+valueSb.toString()+"),");
		}
		values.deleteCharAt(values.length()-1);
		sb.append("insert into " + tableName());
		sb.append(" (" + keys.toString() + ") ");
		sb.append(values.toString());
		
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
			keys.append(field2DbColumn(key) + ",");
			values.append(":" + key + ",");
		}
		if (keys.length() > 0) {
			keys.deleteCharAt(keys.length() - 1);
			values.deleteCharAt(values.length() - 1);
		}
		sb.append("insert into " + tableName());
		sb.append("(" + keys.toString() + ") ");
		sb.append(" values(" + values.toString() + ")");
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
		String sql = "delete from " + tableName() + " where " + dbKey() + "=:id";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		logger.info(sql+paramMap.toString());
		template.update(sql, paramMap);
	}

	public void delete(List<Long> ids){
		String sql = "delete from " + tableName() + " where " + dbKey() + " in (:id) ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", ids);
		logger.info(sql+paramMap.toString());
		template.update(sql, paramMap);
	}
	
	public void delete(long[] ids){
		String sql = "delete from " + tableName() + " where " + dbKey() + " in (:id) ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", ids);
		logger.info(sql+paramMap.toString());
		template.update(sql, paramMap);
	}
	/**
	 * 
	 * tableName:获取表名. <br/>
	 * 如果需要自定义表名.<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public String tableName() {
		String className = clazz.getSimpleName();
		String tableName = StringFormat.pluralize(StringFormat.camel2UnderScore(className));
		return TABLE_PREFIX + tableName;
	}
	
	/**
	 * 
	 * field2DbColumn:JAVABEAN中属性转换成数据库中列名<br/>
	 * 驼峰转下划线小写<br/>
	 * 可以根据情况改写此方法<br/>
	 *
	 * @author bxy-jing
	 * @param field
	 * @return
	 * @since JDK 1.6
	 */
	public String field2DbColumn(String field){
		return StringFormat.camel2UnderScore(field);
	}

	/**
	 * 
	 * key:JAVABEAN中主键名. <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	public String key() {
		return "id";
	}
	
	/**
	 * 
	 * dbKey:. <br/>
	 * 数据库主键名.<br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	private String dbKey(){
		return field2DbColumn(key());
	}

	/**
	 * 
	 * version:记录版本号对应的JAVABEAN属性名. <br/>
	 *
	 * @author bxy-jing
	 * @return
	 * @since JDK 1.6
	 */
	private String version() {
		return "version";
	}

	
	/**
	 * 
	 * isEmtry:判断是否为空，null、0、空字符串. <br/>
	 *
	 * @author bxy-jing
	 * @param o
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isEmtry(Object o) {
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
	
	
	
	public static void main(String[] args) {
		DbDaoImpl<User> dao = new DbDaoImpl<User>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "zhangsan");
		map.put("nickname", "金牙");
		map.put("testMessage", "金牙");
		List<Long> ids = new ArrayList<Long>();
		for(long i = 1;i<100;i++){
			ids.add(i);
		}
		map.put("id", ids);
		Compare c = new Compare();
		c.setGt(1000l);
		c.setLte(100000l);
		map.put("money",c);
		Map<String,Object> map2 = new HashMap<String,Object>(null);
		String where = dao.where(map);
		System.out.println(where);
		System.out.println(map);
	}

}
