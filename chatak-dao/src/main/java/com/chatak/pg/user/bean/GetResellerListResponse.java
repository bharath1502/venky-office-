/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGReseller;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 2:11:42 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class GetResellerListResponse extends Response {


	private static final long serialVersionUID = 3250065631856624945L;
	
	 private List<PGReseller> resellers;
	 
	 private Integer noOfRecords;

	/**
	 * @return the noOfRecords
	 */
	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	/**
	 * @param noOfRecords the noOfRecords to set
	 */
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	/**
	 * @return the resellers
	 */
	public List<PGReseller> getResellers() {
		return resellers;
	}

	/**
	 * @param resellers the resellers to set
	 */
	public void setResellers(List<PGReseller> resellers) {
		this.resellers = resellers;
	}

}
