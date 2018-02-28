package com.jh.myutils.mailutil;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jh.myutils.jdbcutil.JDBCUtil;

public class MailUtil {
	
	private static Properties PROP = null;
	private static String FROM;
	private static String PASSWORD;
	
	static {
		loadConfig();
	}
	
	public static void loadConfig() {
		try {
			InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("mail.properties");
			MailUtil.PROP = new Properties();
			PROP.load(is);
			FROM = PROP.getProperty("mail.from");
			PASSWORD = PROP.getProperty("mail.password");
		} catch(Exception e) {
			throw new RuntimeException("读取配置文件异常", e);
		}
	}

	public static void sendSimpleMail(Mail mail) throws AddressException, MessagingException {
		Session session = Session.getInstance(PROP);
		MimeMessage message = createSimpleMimeMessage(session, mail);
		Transport transport = session.getTransport();
		transport.connect(FROM, PASSWORD);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	private static MimeMessage createSimpleMimeMessage(Session session, Mail mail) throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mail.getTo()));
		message.setSubject(mail.getSubject());
		message.setText(mail.getContent());
		return message;
	}
}
