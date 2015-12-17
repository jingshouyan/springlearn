package com.jing.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
/**
 * 
* @ClassName: DruidServletInitailizer
* @Description: 配置druid Servlet和拦截器
* @author 靖守彦 jingshouyan@126.com
* @date 2015年12月9日 下午5:28:07
*
 */
public class DruidServletInitailizer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		Dynamic druidStatView = servletContext.addServlet("druidStatView", StatViewServlet.class);
		druidStatView.addMapping("/druid/*");
		druidStatView.setInitParameter("loginUsername", "jingshouyan");
		druidStatView.setInitParameter("loginPassword", "jing@123");
		javax.servlet.FilterRegistration.Dynamic filter = servletContext.addFilter("DruidWebStatFilter",
				WebStatFilter.class);
		filter.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");
		filter.setInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
	}

}
