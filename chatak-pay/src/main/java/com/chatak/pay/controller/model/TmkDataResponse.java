package com.chatak.pay.controller.model;

public class TmkDataResponse extends Response{

	private static final long serialVersionUID = 1L;
	
	private String deviceSerialNumber;
	
	private String tmk;
	
	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}
	
	public String getTmk() {
		return tmk;
	}

	public void setTmk(String tmk) {
		this.tmk = tmk;
	}
	
	

}
