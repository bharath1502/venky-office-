package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.model.MerchantCategoryCode;

public class MerchantCategoryCodeSearchResponse extends Response {

	private static final long serialVersionUID = 6610292360286404276L;

	List<MerchantCategoryCode> mccs;

	public List<MerchantCategoryCode> getMccs() {
		return mccs;
	}

	public void setMccs(List<MerchantCategoryCode> mccs) {
		this.mccs = mccs;
	}

}
