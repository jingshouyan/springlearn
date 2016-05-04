/**
 * Project Name:springlearn
 * File Name:MailUtil.java
 * Package Name:com.jing.web.util.mail
 * Date:2016年4月14日下午4:27:40
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;

/**
 * ClassName:MailUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年4月14日 下午4:27:40 <br/>
 * 
 * @author bxy-jing
 * @version
 * @since JDK 1.6
 * @see
 */
public class MailUtil {

	private static long startTime = 0;
	private static long endTime = 0;

	private static void showDelay() {
		endTime = System.currentTimeMillis();
		System.out.println("timeDelay>>>" + (endTime - startTime) + "ms");
		startTime = endTime;
	}

	/**
	 * 
	 * getMails:获取邮件. <br/>
	 *
	 * @author bxy-jing
	 * @param mailInfo
	 * @param start
	 *            第几封邮件开始，负值为倒数第几封开始
	 * @param count
	 *            查询数量
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @since JDK 1.6
	 */
	@SuppressWarnings("restriction")
	public static List<Mail> getMails(MailInfo mailInfo, int start, int count, String protocol)
			throws MessagingException, IOException {
		showDelay();
		List<Mail> mails = new ArrayList<Mail>();
		// 准备连接服务器的会话信息
		if (count < 1 || start == 0) {
			// 数量参数错误，返回空
			return mails;
		}
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = System.getProperties();
		if(protocol.equals("pop3")){
			props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.store.protocol", "pop3"); // 协议
			props.setProperty("mail.pop3.port", String.valueOf(mailInfo.getPop3Port())); // 端口
			props.setProperty("mail.pop3.host", mailInfo.getPop3Host()); // pop3服务器
			props.setProperty("mail.pop3.starttls.enable", "true");
		}else{
			props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.store.protocol", "imap"); // 协议
			props.setProperty("mail.imap.port", String.valueOf(mailInfo.getPop3Port())); // 端口
			props.setProperty("mail.imap.host", mailInfo.getPop3Host()); // pop3服务器
		}
		// props.setProperty("mail.store.protocol", "imap");
		// props.setProperty("mail.imap.host", "imap.163.com");
		// props.setProperty("mail.imap.port", "993");
		props.setProperty("mail.pop3.starttls.enable", "true");
		// 创建Session实例对象
		Session session = Session.getInstance(props);
		Store store ;
		store = session.getStore(protocol);
		store.connect(mailInfo.getUsername(), mailInfo.getPassword());
		// 获得收件箱
		Folder folder = store.getFolder("INBOX");
		/*
		 * Folder.READ_ONLY：只读权限 Folder.READ_WRITE：可读可写（可以修改邮件的状态）
		 */
		folder.open(Folder.READ_ONLY); // 打开收件箱
		int total = folder.getMessageCount();
		Message[] messages;
		int begin = 1;
		int end = 1;
		// start
		if (start >= 0) {
			begin = start;
		} else {
			begin = total + start + 1;
		}
		end = begin + count - 1;
		if (begin < 1 || begin > total) {
			// 开始节点鼠标
			return mails;
		}
		if (end > total) {
			end = total;
		}
		messages = folder.getMessages(begin, end);
		for (Message message : messages) {
			if (!message.getFolder().isOpen()) {
				message.getFolder().open(Folder.READ_ONLY);
			}
			String sub = message.getSubject();
			Address[] addrs = message.getFrom();
			String sender = addrs[0].toString();
			try {
				sender = MimeUtility.decodeText(sender);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Date date = message.getSentDate();
			int i = message.getMessageNumber();
			Mail mail = new Mail();
			mail.setTitle(sub);
			mail.setSender(sender);
			mail.setSendDate(date.getTime());
			mails.add(mail);
			// StringBuilder content = new StringBuilder();
			// getMailTextContent(message, content);
			// System.out.println(content.toString());
		}
		folder.close(false);
		store.close();
		showDelay();
		return mails;
	}

	/**
	 * 获得邮件文本内容
	 * 
	 * @param part
	 *            邮件体
	 * @param content
	 *            存储邮件文本内容的字符串
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void getMailTextContent(Part part, StringBuilder content) throws MessagingException, IOException {
		// 如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
		if (part.isMimeType("text/*") && !isContainTextAttach) {
			content.append(part.getContent().toString());
		} else if (part.isMimeType("message/rfc822")) {
			getMailTextContent((Part) part.getContent(), content);
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				getMailTextContent(bodyPart, content);
			}
		}
	}

	public static void main(String[] args) {
		try {
			MailInfo mailInfo = new MailInfo();
			List<Mail> mails;
//			mailInfo.setUsername("jingshouyan@foxmail.com");
//			mailInfo.setPassword("aywypdaorgoxcbde");
//			mailInfo.setPop3Host("imap.qq.com");
//			mailInfo.setPop3Port(993);
//			mails = getMails(mailInfo, -10, 10, "imap");
//			for (Mail mail : mails) {
//				System.out.println(
//						"sender>>>" + mail.getSender() + "|title>>>" + mail.getTitle() + "|at>>>" + mail.getSendDate());
//			}
//			mailInfo.setUsername("jingshouyan@126.com");
//			mailInfo.setPassword("3333");
//			mailInfo.setPop3Host("pop.126.com");
//			mailInfo.setPop3Port(110);

			mailInfo.setUsername("zhangxiaojun@vrvmail.com.cn");
			mailInfo.setPassword("36985421.");
			mailInfo.setPop3Host("vrvmail.com.cn");
			mailInfo.setPop3Port(143);

			mails = getMails(mailInfo, -10, 10, "imap");
			for (Mail mail : mails) {
				System.out.println(
						"sender>>>" + mail.getSender() + "|title>>>" + mail.getTitle() + "|at>>>" + mail.getSendDate());
			}
			// mailInfo.setUsername("vrvnanjing@163.com");
			// mailInfo.setPassword("a123456");
			// mailInfo.setPop3Host("pop.163.com");
			// mailInfo.setPop3Port(995);

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (MessagingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
}
