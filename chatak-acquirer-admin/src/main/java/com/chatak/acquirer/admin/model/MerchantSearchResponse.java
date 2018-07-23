package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.model.MerchantRequest;

public class MerchantSearchResponse extends Response {

	private static final long serialVersionUID = 6306783400381206195L;

	private List<MerchantData> merchants;

	private List<MerchantData> subMerchants;
	
	private List<MerchantRequest> subMerchantResponse;
	
	private List<MerchantCreateResponse> merchantCreateResponseList;

	public List<MerchantCreateResponse> getMerchantCreateResponseList() {
		return merchantCreateResponseList;
	}

	public void setMerchantCreateResponseList(List<MerchantCreateResponse> merchantCreateResponseList) {
		this.merchantCreateResponseList = merchantCreateResponseList;
	}

	public List<MerchantRequest> getSubMerchantResponse() {
		return subMerchantResponse;
	}

	public void setSubMerchantResponse(List<MerchantRequest> subMerchantResponse) {
		this.subMerchantResponse = subMerchantResponse;
	}

	private PGMerchant merchantData;
	
	private List<MerchantRequest>merchantRequests;
	
	private List<MerchantCreateResponse>merchantCreateResponses;
	
	public List<MerchantData> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<MerchantData> merchants) {
		this.merchants = merchants;
	}

	public List<MerchantData> getSubMerchants() {
		return subMerchants;
	}

	public void setSubMerchants(List<MerchantData> subMerchants) {
		this.subMerchants = subMerchants;
	}

	public PGMerchant getMerchantData() {
		return merchantData;
	}

	public void setMerchantData(PGMerchant merchantData) {
		this.merchantData = merchantData;
	}

	public List<MerchantRequest> getMerchantRequests() {
		return merchantRequests;
	}

	public void setMerchantRequests(List<MerchantRequest> merchantRequests) {
		this.merchantRequests = merchantRequests;
	}

	public List<MerchantCreateResponse> getMerchantCreateResponses() {
		return merchantCreateResponses;
	}

	public void setMerchantCreateResponses(List<MerchantCreateResponse> merchantCreateResponses) {
		this.merchantCreateResponses = merchantCreateResponses;
	}
 }
