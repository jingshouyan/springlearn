/**
 * Project Name:springlearn
 * File Name:WebSocketConfig.java
 * Package Name:com.jing.config
 * Date:2016年2月16日下午4:01:25
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jing.web.socket.ChatHandshakeInterceptor;
import com.jing.web.socket.ChatMessageHandler;

/**
 * ClassName:WebSocketConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: web socket 配置信息 <br/>
 * Date: 2016年2月16日 下午4:01:25 <br/>
 * 
 * @author bxy-jing
 * @version
 * @since JDK 1.6
 * @see
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		registry.addHandler(chatMessageHandler(), "/websocket/chatMessageServer.do")
				.addInterceptors(new ChatHandshakeInterceptor());
		registry.addHandler(chatMessageHandler(), "/sockjs/chatMessageServer.do")
				.addInterceptors(new ChatHandshakeInterceptor()).withSockJS();
	}

	@Bean
	public TextWebSocketHandler chatMessageHandler() {
		return new ChatMessageHandler();
	}

}
