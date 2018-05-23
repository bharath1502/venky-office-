package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.pg.model.AccountTransactionDTO;

@RunWith(MockitoJUnitRunner.class)

public class GlobalManualReportFileExportUtilTest {

	@InjectMocks
	GlobalManualReportFileExportUtil globalManualReportFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test
	public void testDownloadManualTransactionsXl() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		accountTransactionDTO.setTransactionCode("MANUAL_DEBIT");
		list.add(accountTransactionDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		globalManualReportFileExportUtil.downloadManualTransactionsXl(list, response, messageSource);

	}

	@Test
	public void testDownloadManualTransactionsXlElse() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		list.add(accountTransactionDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		globalManualReportFileExportUtil.downloadManualTransactionsXl(list, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadManualTransactionsPdf() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		AccountTransactionDTO accountTransactionDTO2 = new AccountTransactionDTO();
		accountTransactionDTO.setMerchantCode("234");
		accountTransactionDTO.setTransactionId("123");
		accountTransactionDTO.setCurrency("321");
		accountTransactionDTO.setTransactionCode("MANUAL_CREDIT");
		accountTransactionDTO2.setTransactionCode("MANUAL_DEBIT");
		list.add(accountTransactionDTO2);
		list.add(accountTransactionDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		globalManualReportFileExportUtil.downloadManualTransactionsPdf(list, response, messageSource);

	}

	@Test(expected = NullPointerException.class)
	public void testDownloadManualTransactionsPdfElse() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
		list.add(accountTransactionDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		globalManualReportFileExportUtil.downloadManualTransactionsPdf(list, response, messageSource);

	}

}
