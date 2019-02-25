/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.bean.SearchRequest;

/**
 * @Author: Girmiti Software
 * @Date: Jan 31, 2019
 * @Time: 5:28:41 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class PanRangeRequest extends SearchRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4608415521108640377L;
	
	private Long programManagerId;
	
	private Long panLow;
	
	private Long panHigh;
	
	private Long isoId;
	
	private String panRange;

	private List<PanRangeRequest> panRangeRequests;

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

	public Long getIsoId() {
		return isoId;
	}

	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	public List<PanRangeRequest> getPanRangeRequests() {
		return panRangeRequests;
	}

	public void setPanRangeRequests(List<PanRangeRequest> panRangeRequests) {
		this.panRangeRequests = panRangeRequests;
	}

	public String getPanRange() {
		return panRange;
	}

	public void setPanRange(String panRange) {
		this.panRange = panRange;
	}
	
	
	
}
