package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.acquirer.admin.model.Response;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;

public class MerchantAccountSearchResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MerchantAccountSearchDto> merchantAccountSearchDtoList;
	
	private String[][] sortProperty;

	/**
	 * @return the merchantAccountSearchDtoList
	 */
	public List<MerchantAccountSearchDto> getMerchantAccountSearchDtoList() {
		return merchantAccountSearchDtoList;
	}

	/**
	 * @param merchantAccountSearchDtoList the merchantAccountSearchDtoList to set
	 */
	public void setMerchantAccountSearchDtoList(List<MerchantAccountSearchDto> merchantAccountSearchDtoList) {
		this.merchantAccountSearchDtoList = merchantAccountSearchDtoList;
	}

	/**
	 * @return the sortProperty
	 */
	public String[][] getSortProperty() {
		return sortProperty;
	}

	/**
	 * @param sortProperty the sortProperty to set
	 */
	public void setSortProperty(String[][] sortProperty) {
		this.sortProperty = sortProperty;
	}

}
