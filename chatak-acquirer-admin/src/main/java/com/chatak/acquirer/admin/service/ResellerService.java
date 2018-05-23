package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.ResellerSearchResponse;
import com.chatak.pg.model.ResellerData;
import com.chatak.pg.user.bean.AddResellerResponse;
import com.chatak.pg.user.bean.DeleteResellerResponse;
import com.chatak.pg.user.bean.UpdateResellerResponse;

/**
 * 
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Jan-2015 4:28:46 PM
 * @version 1.0
 */
public interface ResellerService {

	public AddResellerResponse addReseller(ResellerData resellerData) throws ChatakAdminException;

	public String getResllerAccountNumber()  throws ChatakAdminException;
	
	public ResellerSearchResponse searchReseller(ResellerData resellerData) throws ChatakAdminException;
	
	public ResellerData getReseller(ResellerData resellerData) throws ChatakAdminException;
	
	public UpdateResellerResponse updateReseller(ResellerData resellerData) throws ChatakAdminException;
	
	public DeleteResellerResponse deleteReseller(Long resellerId);

	public List<Option> getResellerData() throws ChatakAdminException;
}