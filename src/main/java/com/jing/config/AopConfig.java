package com.jing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.jing.web.aop.A00Log;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan
public class AopConfig {

//	@Bean
//	public A00Log a00Log(){
//		return new A00Log();
//	}
}
