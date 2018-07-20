package com.chatak.pg.model;

import com.chatak.pg.bean.SearchRequest;

public class PanLengthDTO extends SearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1121381403322509449L;

	private Long panLengthId;
	
	private Long panLength;

	/**
	 * @return the panLengthId
	 */
	public Long getPanLengthId() {
		return panLengthId;
	}

	/**
	 * @param panLengthId the panLengthId to set
	 */
	public void setPanLengthId(Long panLengthId) {
		this.panLengthId = panLengthId;
	}

	/**
	 * @return the panLength
	 */
	public Long getPanLength() {
		return panLength;
	}

	/**
	 * @param panLength the panLength to set
	 */
	public void setPanLength(Long panLength) {
		this.panLength = panLength;
	}
	
	
}
