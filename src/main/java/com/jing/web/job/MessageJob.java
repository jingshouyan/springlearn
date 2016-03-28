/**
 * Project Name:springlearn
 * File Name:MessageJob.java
 * Package Name:com.jing.web.job
 * Date:2016年2月18日上午9:47:12
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jing.web.dao.DbDao;
import com.jing.web.model.User;
import com.jing.web.socket.ChatMessageHandler;

/**
 * ClassName:MessageJob <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年2月18日 上午9:47:12 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Component
public class MessageJob {
	@Autowired
	private DbDao<User> dbDaoUser;	

	private ChatMessageHandler chatMessageHandler = new ChatMessageHandler();

//	@Scheduled(fixedDelay=5000)
	public void sendMessage(){
		System.out.println("-----------------");
		List<User> users = dbDaoUser.query(null);
		chatMessageHandler.sendMessageToAll(users);
	}
}

