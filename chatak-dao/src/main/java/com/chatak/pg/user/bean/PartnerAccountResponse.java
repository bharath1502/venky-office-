package com.chatak.pg.user.bean;

import java.util.List;

public class PartnerAccountResponse extends com.chatak.pg.bean.Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6896041084265485219L;
	
	private List<PartnerAccountRequest> partnerList;

	/**
	 * @return the partnerList
	 */
	public List<PartnerAccountRequest> getPartnerList() {
		return partnerList;
	}

	/**
	 * @param partnerList the partnerList to set
	 */
	public void setPartnerList(List<PartnerAccountRequest> partnerList) {
		this.partnerList = partnerList;
	}

}
