package com.jing.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
* @ClassName: DatabaseConfig
* @Description: 数据库配置
* @author 靖守彦 jingshouyan@126.com
* @date 2015年12月9日 下午4:25:35
*
 */
@Configuration
@PropertySource("classpath:db.properties")
public class DatabaseConfig {
	
	@Value("${db.driver}")
	private String driver;
	@Value("${db.url}")
	private String url;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	@Value("${db.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${db.validationQuery:SELECT 1}")
	private String validationQuery;
	@Value("${db.initialSize:10}")
	private int initialSize;
	@Value("${db.minIdle:5}")
	private int minIdle;
	@Value("${db.maxActive:100}")
	private int maxActive;
	
	/**
	 * 
	* @Title: dataSource 
	* @Description: 配置数据库连接druid 
	* @param @return    设定文件 
	* @return DataSource    返回类型 
	* @throws
	 */
	@Bean
	public DataSource dataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        try
        {
            dataSource.setFilters("stat,wall");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
		return dataSource;
	}
	/**
	 * 
	* @Title: jdbcTemplate 
	* @Description: 配置jdbcTemplate
	* @param @param dataSource
	* @param @return    设定文件 
	* @return JdbcTemplate    返回类型 
	* @throws
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
	/**
	 * 
	* @Title: namedParameterJdbcTemplate 
	* @Description: 配置namedParameterJdbcTemplate
	* @param @param dataSource
	* @param @return    设定文件 
	* @return NamedParameterJdbcTemplate    返回类型 
	* @throws
	 */
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
		return new NamedParameterJdbcTemplate(dataSource);
	}
	

}
