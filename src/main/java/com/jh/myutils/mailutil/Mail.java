package com.jh.myutils.mailutil;

public class Mail {

	// 主题
	private String subject;
	
	// 内容
	private String content;
	
	// 接收地址
	private String to;

	public Mail(String subject, String content, String to) {
		super();
		this.subject = subject;
		this.content = content;
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
