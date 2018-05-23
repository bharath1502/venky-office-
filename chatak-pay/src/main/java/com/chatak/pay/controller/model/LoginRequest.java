package com.chatak.pay.controller.model;

import java.io.Serializable;

public class LoginRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String deviceSerial;
	private String currentAppVersion;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCurrentAppVersion() {
		return currentAppVersion;
	}
	
	public void setCurrentAppVersion(String currentAppVersion) {
		this.currentAppVersion = currentAppVersion;
	}
	
	public String getDeviceSerial() {
		return deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
}
