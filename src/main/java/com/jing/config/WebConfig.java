package com.jing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 
* @ClassName: WebConfig
* @Description: 基于java bean的spring mvc配置
* @author 靖守彦 jingshouyan@126.com
* @date 2015年12月8日 下午3:12:13
*
 */
@Configuration
@EnableWebMvc//启用 spring mvc
@ComponentScan("com.jing.web")//包扫描
public class WebConfig extends WebMvcConfigurerAdapter{

	/**
	 * 
	* @Title: viewResolver 
	* @Description: 配置view 模板引擎 为freemarker
	* @param @return    设定文件 
	* @return ViewResolver    返回类型 
	* @throws
	 */
	@Bean
	public ViewResolver viewResolver(){
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setCache(true);
		resolver.setPrefix("");
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html; charset=UTF-8");
		return resolver;
	}
	
	/**
	 * 
	* @Title: multipartResolver 
	* @Description: 配置文件上传 解析器
	* @param @return    设定文件 
	* @return MultipartResolver    返回类型 
	* @throws
	 */
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		return resolver;
	}
	/**
	 * 
	* @Title: freeMarkerConfigurer 
	* @Description: freemarker配置
	* @param @return    设定文件 
	* @return FreeMarkerConfigurer    返回类型 
	* @throws
	 */
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer(){
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();		
		freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/");
		freeMarkerConfigurer.setDefaultEncoding("UTF-8");
		return freeMarkerConfigurer;
	}
	
	/**
	 * 
	* @Title: propertyPlaceholderConfigurer 
	* @Description: 添加@Value对*。properties配置文件的支持
	* @param @return    设定文件 
	* @return PropertySourcesPlaceholderConfigurer    返回类型 
	* @throws
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		configurer.enable();
	}
}
