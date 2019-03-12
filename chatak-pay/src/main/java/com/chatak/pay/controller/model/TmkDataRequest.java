package com.chatak.pay.controller.model;

public class TmkDataRequest extends Request{

	private static final long serialVersionUID = 1L;
	
	private String deviceSerialNumber;
	
	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}
	
	
}
