package com.chatak.pg.bean;

import java.io.Serializable;

public class NetworkRequest extends Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -471920122246830066L;
	
	String networkManagementCode;//071 sign on as acq 072 sign off as acq 371 acq echo test

	/**
	 * @return the networkManagementCode
	 */
	public String getNetworkManagementCode() {
		return networkManagementCode;
	}

	/**
	 * @param networkManagementCode the networkManagementCode to set
	 */
	public void setNetworkManagementCode(String networkManagementCode) {
		this.networkManagementCode = networkManagementCode;
	}
	

}
