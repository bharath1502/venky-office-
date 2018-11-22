package com.chatak.pg.user.bean;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.bean.Response;


public class CurrencyDTOList extends Response{

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer noOfRecords;
	
	private List<PGCurrencyConfig> pgCurrencyDTOLists;


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
	 * @return the pgCurrencyDTOLists
	 */
	public List<PGCurrencyConfig> getPgCurrencyDTOLists() {
		return pgCurrencyDTOLists;
	}

	/**
	 * @param pgCurrencyDTOLists the pgCurrencyDTOLists to set
	 */
	public void setPgCurrencyDTOLists(List<PGCurrencyConfig> pgCurrencyDTOLists) {
		this.pgCurrencyDTOLists = pgCurrencyDTOLists;
	}
	

}

