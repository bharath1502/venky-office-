package com.chatak.acquirer.admin.model;

import java.util.List;

public class ResellerSearchResponse extends Response
{

	
	private static final long serialVersionUID = -567928951385723217L;
         
	  private List<ResellerValue> reseller;

	/**
	 * @return the reseller
	 */
	public List<ResellerValue> getReseller() {
		return reseller;
	}

	/**
	 * @param reseller the reseller to set
	 */
	public void setReseller(List<ResellerValue> reseller) {
		this.reseller = reseller;
	}

		
		

}
