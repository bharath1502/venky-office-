package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.PaymentSchemeNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.PaymentScheme;
import com.chatak.pg.user.bean.PaymentSchemeRequest;
import com.chatak.pg.user.bean.PaymentSchemeResponse;

public interface PaymentSchemeService {
	
	public PaymentSchemeRequest getpaymentSchemeyInfoId(Long id) throws ChatakAdminException;
	
	public PaymentSchemeResponse updatePaymentSchemeInformation(PaymentSchemeRequest updatePaymentScheme, String userid) throws ChatakAdminException;
	
	public PaymentSchemeResponse addPaymentSchemeInformation(PaymentScheme paymentSchemeInfo, String userid) throws ChatakAdminException;
	
	public PaymentSchemeResponse searchPaymentScheme(PaymentSchemeRequest paymentScheme) throws ChatakAdminException;
	
	public Response validateEmailId(String contactEmail) throws ChatakAdminException;
	
	public PaymentSchemeResponse changePaymentSchemeStatus(PaymentSchemeRequest  paymentSchemeRequest, String paymentSchemeStatus) throws ChatakAdminException;
    
	public PaymentSchemeNameResponse validatePaymentSchemeName(String paymentSchemeName);
}
