package com.chatak.acquirer.admin.model;

import java.io.Serializable;
import java.util.List;

import com.chatak.pg.model.ReportsDTO;

public class SubMerchantDTOList implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3080114980001651212L;
	private List<ReportsDTO> list;

	public List<ReportsDTO> getList() {
		return list;
	}

	public void setList(List<ReportsDTO> list) {
		this.list = list;
	}
	 
}
