/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: Mar 1, 2016
 * @Time: 12:36:40 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class MerchantDetailsForAccountResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8029307965781590865L;
	
	private List<MerchantDetailsForAccountCreate> merchantDetailsList;
	
	private int totalRecords;

	/**
	 * @return the merchantDetailsList
	 */
	public List<MerchantDetailsForAccountCreate> getMerchantDetailsList() {
		return merchantDetailsList;
	}

	/**
	 * @param merchantDetailsList the merchantDetailsList to set
	 */
	public void setMerchantDetailsList(List<MerchantDetailsForAccountCreate> merchantDetailsList) {
		this.merchantDetailsList = merchantDetailsList;
	}

	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
