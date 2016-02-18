/**
 * Project Name:springlearn
 * File Name:ChatHandshakeInterceptor.java
 * Package Name:com.jing.web.socket
 * Date:2016年2月16日下午6:03:50
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.socket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.jing.web.util.Constants;

/**
 * ClassName:ChatHandshakeInterceptor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年2月16日 下午6:03:50 <br/>
 * 
 * @author bxy-jing
 * @version
 * @since JDK 1.6
 * @see
 */
public class ChatHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
			HttpSession session = httpRequest.getServletRequest().getSession(false);
			if(null!=session){
				String userid = String.valueOf(session.getAttribute(Constants.SESSION_USER_ID));
				attributes.put(Constants.SESSION_USER_ID, userid);
			}
		}
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
