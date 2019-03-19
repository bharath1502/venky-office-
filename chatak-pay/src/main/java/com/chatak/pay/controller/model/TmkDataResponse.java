package com.chatak.pay.controller.model;

public class TmkDataResponse extends Response{

	private static final long serialVersionUID = 1L;
	
	private String tmk;
	
	private String responseCode;
	
	private String responseMessage;
	
	public String getTmk() {
		return tmk;
	}

	public void setTmk(String tmk) {
		this.tmk = tmk;
	}
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	

}
