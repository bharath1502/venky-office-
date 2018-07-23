package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.bean.Response;


public class FeeProgramNameListDTO extends Response{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer noOfRecords;
	
	private List<PGFeeProgram> feeProgramDTOs;


	/**
	 * @return the feeProgramDTOs
	 */
	public List<PGFeeProgram> getFeeProgramDTOs() {
		return feeProgramDTOs;
	}

	/**
	 * @param feeProgramDTOs the feeProgramDTOs to set
	 */
	public void setFeeProgramDTOs(List<PGFeeProgram> feeProgramDTOs) {
		this.feeProgramDTOs = feeProgramDTOs;
	}

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
	

}
