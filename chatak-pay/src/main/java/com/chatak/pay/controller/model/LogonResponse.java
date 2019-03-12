package com.chatak.pay.controller.model;

public class LogonResponse extends Response{
	
	private static final long serialVersionUID = 1L;
	
	private String sessionKey;
	
	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
