package com.chatak.pg.bean;

import java.io.Serializable;
import java.util.List;

import com.chatak.pg.model.LitleEFTDTO;

public class LitleEFTRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7829254016581788252L;

	private Integer pageIndex;

	private Integer pageSize;
	  
	private Integer noOfRecords;
	
	private List<LitleEFTDTO> litleEFTDTOs;

	public Integer getPageIndex() {
		return pageIndex;
	}
	
	public Integer getPageSize() {
    return pageSize;
  }

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getNoOfRecords() {
		return noOfRecords;
	}
	
	public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public List<LitleEFTDTO> getLitleEFTDTOs() {
		return litleEFTDTOs;
	}

	public void setLitleEFTDTOs(List<LitleEFTDTO> litleEFTDTOs) {
		this.litleEFTDTOs = litleEFTDTOs;
	}
	
	
}
