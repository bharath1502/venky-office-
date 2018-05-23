package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantCategoryCodeSearchResponse;
import com.chatak.pg.model.MerchantCategoryCode;
import com.chatak.pg.user.bean.MerchantCategoryCodeResponse;

public interface MerchantCategoryCodeService {

	public MerchantCategoryCodeResponse createMerchantCategoryCode(
			MerchantCategoryCode mcc) throws ChatakAdminException;

	public MerchantCategoryCodeSearchResponse searchMerchantCategoryCode(
			MerchantCategoryCode mcc) throws ChatakAdminException;

	public MerchantCategoryCode getMCCDetails(Long id)
			throws ChatakAdminException;

	public MerchantCategoryCodeResponse updateMerchantCategoryCode(
			MerchantCategoryCode mcc) throws ChatakAdminException;

	public List<Option> getAllTCCs();
	
	public List<String> getAllMCC();
	
	public MerchantCategoryCodeResponse changeMerchantCategoryCodeStatus(MerchantCategoryCode merchantCategoryCode);
	
	public MerchantCategoryCodeResponse deleteMcc(Long id) throws ChatakAdminException;
	
	

}
