/**
 * 
 */
package com.chatak.pg.user.bean;

import com.chatak.pg.bean.SearchRequest;

/**
 * @Author: Girmiti Software
 * @Date: Jan 31, 2019
 * @Time: 5:28:41 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class PanRange extends SearchRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4608415521108640377L;
	
	private Long programManagerId;
	
	private Long panLow;
	
	private Long panHigh;

	/**
	 * @return the programManagerId
	 */
	public Long getProgramManagerId() {
		return programManagerId;
	}

	/**
	 * @param programManagerId the programManagerId to set
	 */
	public void setProgramManagerId(Long programManagerId) {
		this.programManagerId = programManagerId;
	}

	/**
	 * @return the panLow
	 */
	public Long getPanLow() {
		return panLow;
	}

	/**
	 * @param panLow the panLow to set
	 */
	public void setPanLow(Long panLow) {
		this.panLow = panLow;
	}

	/**
	 * @return the panHigh
	 */
	public Long getPanHigh() {
		return panHigh;
	}

	/**
	 * @param panHigh the panHigh to set
	 */
	public void setPanHigh(Long panHigh) {
		this.panHigh = panHigh;
	}
	

}
