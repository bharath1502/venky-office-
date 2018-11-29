package com.chatak.pay.service;

import com.chatak.pay.controller.model.Request;
import com.chatak.pay.controller.model.topup.GetOperatorsResponse;
import com.chatak.pay.controller.model.topup.GetTopupCategoriesResponse;
import com.chatak.pay.controller.model.topup.GetTopupOffersResponse;
import com.chatak.pay.controller.model.topup.TopupRequest;
import com.chatak.pay.controller.model.topup.TopupResponse;

public interface TopupService {
	
	public GetOperatorsResponse getOperators(Request request) ;
	
	public TopupResponse doTopup(TopupRequest topupRequest);
	
	public GetTopupCategoriesResponse getTopupCategories(TopupRequest topupRequest);
	
	public GetTopupOffersResponse getTopupOffers(TopupRequest topupRequest);
}
