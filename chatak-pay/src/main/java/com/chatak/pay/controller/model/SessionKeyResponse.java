package com.chatak.pay.controller.model;

public class SessionKeyResponse extends Response{

	private static final long serialVersionUID = 1L;
	
	private String results;
	
	private String sessionKeyUnderLMK;
	
	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getSessionKeyUnderLMK() {
		return sessionKeyUnderLMK;
	}

	public void setSessionKeyUnderLMK(String sessionKeyUnderLMK) {
		this.sessionKeyUnderLMK = sessionKeyUnderLMK;
	}

}
