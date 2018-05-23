package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;


public class MccCodesDTO extends SearchRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2078160906431801262L;
	

	private Long mccCodesId;

	private String mccCode;

	/**
	 * @return the mccCodesId
	 */
	public Long getMccCodesId() {
		return mccCodesId;
	}

	/**
	 * @param mccCodesId the mccCodesId to set
	 */
	public void setMccCodesId(Long mccCodesId) {
		this.mccCodesId = mccCodesId;
	}

	/**
	 * @return the mccCode
	 */
	public String getMccCode() {
		return mccCode;
	}

	/**
	 * @param mccCode the mccCode to set
	 */
	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
	}
	
	
	
}
