package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pg.user.bean.PaymentSchemeRequest;
@RunWith(MockitoJUnitRunner.class)
public class PaymentSchemeExportUtilTest {
	
	@InjectMocks
	PaymentSchemeExportUtil paymentSchemeExportUtil;

	@Mock
	HttpServletResponse response;
	
	@Mock
	MessageSource messageSource;
	
	@Test(expected=NullPointerException.class)
	public void testDownloadPaymentSchemaPdf() {
		List<PaymentSchemeRequest> paymentSchemeRequest=new ArrayList<>();
		PaymentSchemeRequest schemeRequest=new PaymentSchemeRequest();
		schemeRequest.setStatus(1);
		schemeRequest.setRid("43");
		paymentSchemeRequest.add(schemeRequest);
		paymentSchemeExportUtil.downloadPaymentSchemaPdf(paymentSchemeRequest, response, "headerMessage", messageSource);
	}
	
	@Test(expected=NullPointerException.class)
	public void testDownloadPaymentSchemaPdfElse() {
		List<PaymentSchemeRequest> paymentSchemeRequest=new ArrayList<>();
		PaymentSchemeRequest schemeRequest=new PaymentSchemeRequest();
		schemeRequest.setStatus(1);
		paymentSchemeRequest.add(schemeRequest);
		paymentSchemeExportUtil.downloadPaymentSchemaPdf(paymentSchemeRequest, response, "headerMessage", messageSource);
	}

	@Test
	public void testDownloadPaymentSchemeXl() {
		List<PaymentSchemeRequest> paymentSchemeRequest=new ArrayList<>();
		PaymentSchemeRequest schemeRequest=new PaymentSchemeRequest();
		schemeRequest.setStatus(1);
		paymentSchemeRequest.add(schemeRequest);
		paymentSchemeExportUtil.downloadPaymentSchemeXl(paymentSchemeRequest, response, "headerMessage", messageSource);
	}
	
	
}
