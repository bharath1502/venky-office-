package com.chatak.pay.controller.model;

public class MerchantIsoOnboardingRequest {
	
	private Long pmId;

	private String programManagerName;

	private Long isoId;

	private String isoName;
	
	private String currency;

	public Long getPmId() {
		return pmId;
	}

	public void setPmId(Long pmId) {
		this.pmId = pmId;
	}

	public String getProgramManagerName() {
		return programManagerName;
	}

	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}

	public Long getIsoId() {
		return isoId;
	}

	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	public String getIsoName() {
		return isoName;
	}

	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
