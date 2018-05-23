/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGBank;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 7:46:26 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public class GetBankListResopnse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7802425882170336908L;

	private List<PGBank> pgBanks;
	private Integer totalNoOfRows;

	public List<PGBank> getBanks() {
		return pgBanks;
	}

	public void setBanks(List<PGBank> pgBanks) {
		this.pgBanks = pgBanks;
	}

	public Integer getTotalNoOfRows() {
		return totalNoOfRows;
	}

	public void setTotalNoOfRows(Integer totalNoOfRows) {
		this.totalNoOfRows = totalNoOfRows;
	}

	

}
