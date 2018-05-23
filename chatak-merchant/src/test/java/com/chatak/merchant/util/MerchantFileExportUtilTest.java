package com.chatak.merchant.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.merchant.model.MerchantData;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)

public class MerchantFileExportUtilTest {

	@InjectMocks
	MerchantFileExportUtil merchantFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("chatak.header.sub.merchant.messages", "0");
		properties.setProperty("chatak.header.manual.transactions.reports", "0");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testDownloadMerchantXl() {
		List<MerchantData> merchantData = new ArrayList<>();
		MerchantData data = new MerchantData();
		data.setPhone(Long.parseLong("4823749123"));
		merchantData.add(data);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		merchantFileExportUtil.downloadMerchantXl(merchantData, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadMerchantPdf() {
		List<MerchantData> merchantData = new ArrayList<>();
		MerchantData data = new MerchantData();
		data.setPhone(Long.parseLong("4823749123"));
		merchantData.add(data);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		merchantFileExportUtil.downloadMerchantPdf(merchantData, response, messageSource);
	}

}
