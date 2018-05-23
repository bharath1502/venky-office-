package com.chatak.pay.controller.model.topup;

import java.util.List;

import com.chatak.pay.controller.model.Response;

public class GetTopupCategoriesResponse extends Response {

	private static final long serialVersionUID = 7656560896368071106L;
	
	private List<TopupCategoryDTO> topupOfferCategoryDTOs;

	/**
	 * @return the topupOfferCategoryDTOs
	 */
	public List<TopupCategoryDTO> getTopupOfferCategoryDTOs() {
		return topupOfferCategoryDTOs;
	}

	/**
	 * @param topupOfferCategoryDTOs
	 *            the topupOfferCategoryDTOs to set
	 */
	public void setTopupOfferCategoryDTOs(List<TopupCategoryDTO> topupOfferCategoryDTOs) {
		this.topupOfferCategoryDTOs = topupOfferCategoryDTOs;
	}

}
