package com.jing.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@ComponentScan(basePackages = { "com.jing" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = {
				EnableWebMvc.class,
				EnableWebSocket.class,
				EnableScheduling.class
				}) })
public class RootConfig {

}
