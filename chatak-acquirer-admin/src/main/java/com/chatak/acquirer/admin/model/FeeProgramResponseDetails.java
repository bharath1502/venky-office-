package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.model.FeeProgramDTO;

public class FeeProgramResponseDetails extends Response
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1735389218594406325L;
	private Long feeCode;
	private List<FeeProgramDTO> feeCodeList;
	/**
	 * @return the feeCode
	 */
	public Long getFeeCode() {
		return feeCode;
	}
	/**
	 * @param feeCode the feeCode to set
	 */
	public void setFeeCode(Long feeCode) {
		this.feeCode = feeCode;
	}
	/**
	 * @return the feeCodeList
	 */
	public List<FeeProgramDTO> getFeeCodeList() {
		return feeCodeList;
	}
	/**
	 * @param feeCodeList the feeCodeList to set
	 */
	public void setFeeCodeList(List<FeeProgramDTO> feeCodeList) {
		this.feeCodeList = feeCodeList;
	}

	
}
