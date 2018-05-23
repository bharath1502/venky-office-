package com.chatak.merchant.model;

public class PayPageConfig {
	
	private Long merchantId;

	private String header;

	private String footer;

	private byte[] payPageLogo;

	private String createdBy;

	private String updatedBy;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public byte[] getPayPageLogo() {
		return payPageLogo;
	}

	public void setPayPageLogo(byte[] payPageLogo) {
		this.payPageLogo = payPageLogo;
	}
	
	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
