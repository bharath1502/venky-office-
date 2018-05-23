package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class ActionCodeParametersDTO  extends SearchRequest
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3404668771256341230L;

	private Long actioncodeId;

	private String actioncodeName;
	
	/**
	 * @return the actioncodeId
	 */
	public Long getActioncodeId() {
		return actioncodeId;
	}

	/**
	 * @param actioncodeId the actioncodeId to set
	 */
	public void setActioncodeId(Long actioncodeId) {
		this.actioncodeId = actioncodeId;
	}

	/**
	 * @return the actioncodeName
	 */
	public String getActioncodeName() {
		return actioncodeName;
	}

	/**
	 * @param actioncodeName the actioncodeName to set
	 */
	public void setActioncodeName(String actioncodeName) {
		this.actioncodeName = actioncodeName;
	}



}
