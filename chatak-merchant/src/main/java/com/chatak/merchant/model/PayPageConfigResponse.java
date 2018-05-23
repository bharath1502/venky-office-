package com.chatak.merchant.model;

public class PayPageConfigResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3507013014016048175L;
	
	private String header;

	private String footer;

	private byte[] payPageLogo;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public byte[] getPayPageLogo() {
		return payPageLogo;
	}

	public void setPayPageLogo(byte[] payPageLogo) {
		this.payPageLogo = payPageLogo;
	}

}
