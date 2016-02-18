/**
 * Project Name:springlearn
 * File Name:ChatMessageHandler.java
 * Package Name:com.jing.web.socket
 * Date:2016年2月16日下午4:10:35
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.socket;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.jing.web.util.Constants;

/**
 * ClassName:ChatMessageHandler <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年2月16日 下午4:10:35 <br/>
 * 
 * @author bxy-jing
 * @version
 * @since JDK 1.6
 * @see
 */
public class ChatMessageHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class);
	private static final Map<String, WebSocketSession> users = new HashMap<String, WebSocketSession>();

	/**
	 * 
	 * 连接成功触发 将用户回话保存
	 * 
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userid = String.valueOf(session.getAttributes().get(Constants.SESSION_USER_ID));
		users.put(userid, session);
		logger.info("user is connected [userid:{}]", userid);
	}

	/**
	 * 
	 * TODO 接受用户消息
	 * 
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#handleTextMessage(org.springframework.web.socket.WebSocketSession,
	 *      org.springframework.web.socket.TextMessage)
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String userid = String.valueOf(session.getAttributes().get(Constants.SESSION_USER_ID));
		String str = message.getPayload();
		logger.info("user[{}]:{}", userid, str);
		sendMessageToUser(userid, toTextMessage(str));

		super.handleTextMessage(session, message);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		String userid = String.valueOf(session.getAttributes().get(Constants.SESSION_USER_ID));
		if (session.isOpen()) {
			session.close();
		}
		users.remove(userid);
		logger.info("connection is errpr [userid:{}],error:{}", userid, exception.getMessage());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		String userid = String.valueOf(session.getAttributes().get(Constants.SESSION_USER_ID));
		users.remove(userid);
		logger.info("connection is closed [userid:{}]", userid);
	}

	/**
	 * 
	 * sendMessageToUser:给用户发消息. <br/>
	 *
	 * @author bxy-jing
	 * @param userid
	 *            用户编号
	 * @param message
	 *            消息
	 * @since JDK 1.6
	 */
	public void sendMessageToUser(String userid, TextMessage message) {
		if (null == message)
			return;
		WebSocketSession session = users.get(userid);
		if (null != session && session.isOpen()) {
			try {
				session.sendMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * sendMessageToUser:给用户发消息json格式字符串. <br/>
	 *
	 * @author bxy-jing
	 * @param userid 用户编号
	 * @param obj 消息
	 * @since JDK 1.6
	 */
	public void sendMessageToUser(String userid,Object obj){
		sendMessageToUser(userid, toTextMessage(obj));
	}

	/**
	 * 
	 * sendMessageToAll:给所有在线用户发消息. <br/>
	 *
	 * @author bxy-jing
	 * @param message
	 *            消息
	 * @since JDK 1.6
	 */
	public void sendMessageToAll(TextMessage message) {
		if (null == message)
			return;
		for (WebSocketSession session : users.values()) {
			if (session.isOpen()) {
				try {
					session.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * sendMessageToAll:给所有用户发消息 json格式<br/>
	 *
	 * @author bxy-jing
	 * @param obj 消息
	 * @since JDK 1.6
	 */
	public void sendMessageToAll(Object obj){
		sendMessageToAll(toTextMessage(obj));
	}

	/**
	 * 
	 * toTextMessage:将对象转成json格式的TextMessage. <br/>
	 *
	 * @author bxy-jing
	 * @param obj
	 * @return
	 * @since JDK 1.6
	 */
	private TextMessage toTextMessage(Object obj) {
		if (null == obj)
			return null;
		String msg = JSON.toJSONString(obj);
		byte[] bytes = msg.getBytes();
		return new TextMessage(bytes);

	}

}
