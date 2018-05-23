package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.model.FeeCodeDTO;

public class FeeCodeResponseDetails  extends Response
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6148487176024569908L;
	private List<FeeCodeDTO> feeCodeList;
	/**
	 * @return the feeCodeList
	 */
	public List<FeeCodeDTO> getFeeCodeList() {
		return feeCodeList;
	}
	/**
	 * @param feeCodeList the feeCodeList to set
	 */
	public void setFeeCodeList(List<FeeCodeDTO> feeCodeList) {
		this.feeCodeList = feeCodeList;
	}
	
	
	
}
