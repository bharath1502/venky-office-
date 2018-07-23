package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class ApplicationIdDTO extends SearchRequest
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5361810757853962081L;

	private Long applicationId;

	private String applicationName;
	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}



}
