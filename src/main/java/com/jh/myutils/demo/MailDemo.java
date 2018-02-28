package com.jh.myutils.demo;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.jh.myutils.mailutil.Mail;
import com.jh.myutils.mailutil.MailUtil;

public class MailDemo {

	public static void main(String args[]) {
		Mail mail = new Mail("test", "this is test mail", "1193048581@qq.com");
		try {
			MailUtil.sendSimpleMail(mail);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
