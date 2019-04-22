package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.PaymentSchemeServiceImpl;
import com.chatak.pg.acq.dao.PaymentSchemeDao;
import com.chatak.pg.acq.dao.model.PGPaymentScheme;
import com.chatak.pg.model.PaymentScheme;
import com.chatak.pg.user.bean.PaymentSchemeRequest;

@RunWith(MockitoJUnitRunner.class)
public class PaymentSchemeServiceImplTest {

	@InjectMocks
	PaymentSchemeServiceImpl paymentSchemeServiceImpl = new PaymentSchemeServiceImpl();

	@Mock
	private PaymentSchemeDao paymentSchemeDao;

	@Test
	public void testGetpaymentSchemeyInfoId() throws ChatakAdminException {
		PGPaymentScheme pgPaymentScheme=new PGPaymentScheme();
		Mockito.when(paymentSchemeDao.getPaymentSchemeInfoId(Matchers.anyLong())).thenReturn(pgPaymentScheme);
		paymentSchemeServiceImpl.getpaymentSchemeyInfoId(Long.parseLong("534"));
	}

	@Test
	public void testUpdatePaymentSchemeInformation() throws ChatakAdminException {
		PaymentSchemeRequest paymentSchemesRequest = new PaymentSchemeRequest();
		paymentSchemeServiceImpl.updatePaymentSchemeInformation(paymentSchemesRequest, "423");
	}
	
	@Test
	public void testAddPaymentSchemeInformation() throws ChatakAdminException {
		PaymentScheme paymentSchemeInfo = new PaymentScheme();
		paymentSchemeServiceImpl.addPaymentSchemeInformation(paymentSchemeInfo, "423");
	}

	@Test
	public void testSearchPaymentScheme() throws ChatakAdminException {
		List<PaymentSchemeRequest> paymentScheme = new ArrayList<>();
		PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
		paymentScheme.add(paymentSchemeRequest);
		Mockito.when(paymentSchemeDao.findPaymentScheme(Matchers.any(PaymentSchemeRequest.class)))
				.thenReturn(paymentScheme);
		paymentSchemeServiceImpl.searchPaymentScheme(paymentSchemeRequest);
	}

	@Test
	public void testValidateEmailId() throws ChatakAdminException {
		paymentSchemeServiceImpl.validateEmailId("423");
	}

	@Test
	public void testChangePaymentSchemeStatus() throws ChatakAdminException {
		PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
		PGPaymentScheme pgPaymentScheme = new PGPaymentScheme();
		paymentSchemeRequest.setId(Long.parseLong("68"));
		Mockito.when(paymentSchemeDao.findPaymentSchemeById(Matchers.anyLong())).thenReturn(pgPaymentScheme);
		paymentSchemeServiceImpl.changePaymentSchemeStatus(paymentSchemeRequest, "Active");

	}

	@Test
	public void testChangePaymentSchemeStatusElse() throws ChatakAdminException {
		PaymentSchemeRequest paymentSchemeRequest = new PaymentSchemeRequest();
		PGPaymentScheme pgPaymentScheme = new PGPaymentScheme();
		Mockito.when(paymentSchemeDao.findPaymentSchemeById(Matchers.anyLong())).thenReturn(pgPaymentScheme);
		paymentSchemeServiceImpl.changePaymentSchemeStatus(paymentSchemeRequest, "423");
	}

	@Test
	public void testValidatePaymentSchemeName() throws ChatakAdminException {
		PGPaymentScheme paymentScheme = new PGPaymentScheme();
		paymentScheme.setStatus(0);
		paymentScheme.setId(Long.parseLong("5345"));
		Mockito.when(paymentSchemeDao.getPaymentSchemeName(Matchers.anyString())).thenReturn(paymentScheme);
		paymentSchemeServiceImpl.validatePaymentSchemeName("423");
	}

	@Test
	public void testValidatePaymentSchemeNameElse() throws ChatakAdminException {
		PGPaymentScheme paymentScheme = new PGPaymentScheme();
		paymentScheme.setStatus(0);
		paymentScheme.setId(Long.parseLong("5345"));
		Mockito.when(paymentSchemeDao.getPaymentSchemeName(Matchers.anyString())).thenReturn(paymentScheme);
		paymentSchemeServiceImpl.validatePaymentSchemeName("423");
	}

	@Test
	public void testValidatePaymentSchemeNameNull() throws ChatakAdminException {
		paymentSchemeServiceImpl.validatePaymentSchemeName("423");
	}

}
