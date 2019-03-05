package com.chatak.pay.controller.model.topup;

import java.util.List;

import com.chatak.pay.controller.model.Response;

public class GetTopupOffersResponse extends Response {

	private static final long serialVersionUID = 5945833094591890322L;

	 private List<TopupOfferDetailDTO> topupOfferDetailDTOs;

	/**
	 * @return the topupOfferDetailDTOs
	 */
	public List<TopupOfferDetailDTO> getTopupOfferDetailDTOs() {
		return topupOfferDetailDTOs;
	}

	/**
	 * @param topupOfferDetailDTOs
	 *            the topupOfferDetailDTOs to set
	 */
	public void setTopupOfferDetailDTOs(List<TopupOfferDetailDTO> topupOfferDetailDTOs) {
		this.topupOfferDetailDTOs = topupOfferDetailDTOs;
	}

}
