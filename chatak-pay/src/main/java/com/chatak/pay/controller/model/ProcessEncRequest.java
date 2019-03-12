package com.chatak.pay.controller.model;

public class ProcessEncRequest {
	
	private String applicationName;
	
	private String applicationversion;
	
	private String applicationSignature;
	
	private DeviceInfo deviceInfo;
	
	private TransactionDetails transactionDetails;
	
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getApplicationversion() {
		return applicationversion;
	}
	public void setApplicationversion(String applicationversion) {
		this.applicationversion = applicationversion;
	}
	public String getApplicationSignature() {
		return applicationSignature;
	}
	public void setApplicationSignature(String applicationSignature) {
		this.applicationSignature = applicationSignature;
	}
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public TransactionDetails getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(TransactionDetails transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

}
