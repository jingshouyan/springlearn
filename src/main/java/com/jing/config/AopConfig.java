package com.jing.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan//配置项中一个config要添加这个注解，否则在其他配置中引入*。properties 会出现string转int报错
public class AopConfig {


}
