package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.model.CommissionDTO;


public class CommissionProgramResponseDetails extends Response {

	private static final long serialVersionUID = -1952854837440269007L;
	
	private List<CommissionDTO> commProgList;

	public List<CommissionDTO> getCommProgList() {
		return commProgList;
	}

	public void setCommProgList(List<CommissionDTO> commProgList) {
		this.commProgList = commProgList;
	}
	
}
