package com.chatak.pay.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest extends Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String deviceSerial;
	private String currentAppVersion;
	private String osName;
	private String model;
	private String timeZoneOffset;
	private String timeZoneRegion;
	private String manufacturer;
	private String osVersion;
	private String os;
	

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(String timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

	public String getTimeZoneRegion() {
		return timeZoneRegion;
	}

	public void setTimeZoneRegion(String timeZoneRegion) {
		this.timeZoneRegion = timeZoneRegion;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

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
