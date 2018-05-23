package com.chatak.mailsender.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class MailSendRequest {

	private String text;

	private String toAddress;

	private String subject;

	private Map<String, byte[]> attachmentMap = new LinkedHashMap<String, byte[]>();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, byte[]> getAttachmentMap() {
		return attachmentMap;
	}

	public void setAttachmentMap(Map<String, byte[]> attachmentMap) {
		this.attachmentMap = attachmentMap;
	}

}
