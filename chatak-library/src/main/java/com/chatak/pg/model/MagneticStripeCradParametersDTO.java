package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class MagneticStripeCradParametersDTO extends SearchRequest
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 739359693916331081L;

	
	private Long magneticStripeId;

	private String magneticStripeName;
	/**
	 * @return the magneticStripeId
	 */
	public Long getMagneticStripeId() {
		return magneticStripeId;
	}

	/**
	 * @param magneticStripeId the magneticStripeId to set
	 */
	public void setMagneticStripeId(Long magneticStripeId) {
		this.magneticStripeId = magneticStripeId;
	}

	/**
	 * @return the magneticStripeName
	 */
	public String getMagneticStripeName() {
		return magneticStripeName;
	}

	/**
	 * @param magneticStripeName the magneticStripeName to set
	 */
	public void setMagneticStripeName(String magneticStripeName) {
		this.magneticStripeName = magneticStripeName;
	}


}
