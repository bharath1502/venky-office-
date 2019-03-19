package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.FaqManagementRequest;
import com.chatak.pg.user.bean.FaqManagementResponse;



public interface FaqManagementService {

	public List<FaqManagementRequest> getAllCategories() throws ChatakAdminException;

	public FaqManagementResponse searchModule(FaqManagementRequest faqManagementRequest) throws ChatakAdminException;

	public Response createFaqManagement(FaqManagementRequest faqManagementRequest)throws ChatakAdminException;

	public FaqManagementResponse searchFaqManagement(FaqManagementRequest faqManagementRequest) throws ChatakAdminException;

	public FaqManagementResponse searcFaqMgmtById(FaqManagementRequest faqManagementRequest) throws ChatakAdminException;

}
