/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGMerchantCategoryCode;

/**
 * @Author: Girmiti Software
 * @Date: Aug 22, 2016
 * @Time: 6:01:12 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class GetMerchantCategoryCodeListResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9125312387667437774L;

	private List<PGMerchantCategoryCode> pgMCC;

	private Integer noOfRecords;

	public List<PGMerchantCategoryCode> getPgMCC() {
		return pgMCC;
	}

	public void setPgMCC(List<PGMerchantCategoryCode> pgMCC) {
		this.pgMCC = pgMCC;
	}

	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

}
