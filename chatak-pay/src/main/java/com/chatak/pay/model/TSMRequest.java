package com.chatak.pay.model;

public class TSMRequest {

	private String merchantCode;
	private String deviceSerial;
	private String currentAppVersion;
	private String tid;
	
	public String getCurrentAppVersion() {
		return currentAppVersion;
	}
	public void setCurrentAppVersion(String currentAppVersion) {
		this.currentAppVersion = currentAppVersion;
	}
	
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	public String getDeviceSerial() {
		return deviceSerial;
	}
	
	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
}
