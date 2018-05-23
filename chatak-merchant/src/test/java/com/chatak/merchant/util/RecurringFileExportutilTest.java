package com.chatak.merchant.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pg.model.RecurringCustomerInfoDTO;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class RecurringFileExportutilTest {

	@InjectMocks
	RecurringFileExportutil recurringFileExportutil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("chatak.header.recurring.messages", "0");
		Properties.mergeProperties(properties);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadRecurringPdf() {
		List<RecurringCustomerInfoDTO> list = new ArrayList<>();
		RecurringCustomerInfoDTO customerInfoDTO = new RecurringCustomerInfoDTO();
		list.add(customerInfoDTO);
		recurringFileExportutil.downloadRecurringPdf(list, response, messageSource);
	}

	@Test
	public void testDownloadRecurringExcel() {
		List<RecurringCustomerInfoDTO> list = new ArrayList<>();
		RecurringCustomerInfoDTO customerInfoDTO = new RecurringCustomerInfoDTO();
		list.add(customerInfoDTO);
		recurringFileExportutil.downloadRecurringExcel(list, response, messageSource);
	}

}
