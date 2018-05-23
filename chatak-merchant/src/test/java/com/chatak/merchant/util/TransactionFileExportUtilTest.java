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

import com.chatak.pg.model.AccountTransactionDTO;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public class TransactionFileExportUtilTest {

	@InjectMocks
	TransactionFileExportUtil transactionFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Mock
	GetTransactionsListRequest request;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("merchantFileExportUtil.report.date.report.date", "0");
		Properties.mergeProperties(properties);
	}

	@Test
	public void testDownloadManualTransactionsXl() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO transaction = new AccountTransactionDTO();
		transaction.setTransactionCode("MANUAL_DEBIT");
		list.add(transaction);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("ab");
		transactionFileExportUtil.downloadManualTransactionsXl(list, response, messageSource);
	}

	@Test
	public void testDownloadManualTransactionsXlElse() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO transaction = new AccountTransactionDTO();
		list.add(transaction);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("ab");
		transactionFileExportUtil.downloadManualTransactionsXl(list, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadManualTransactionsPdf() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO transaction = new AccountTransactionDTO();
		transaction.setTransactionCode("MANUAL_CREDIT");
		list.add(transaction);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("ab");
		transactionFileExportUtil.downloadManualTransactionsPdf(list, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadManualTransactionsPdfElse() {
		List<AccountTransactionDTO> list = new ArrayList<>();
		AccountTransactionDTO transaction = new AccountTransactionDTO();
		transaction.setTransactionCode("MANUAL_DEBIT");
		list.add(transaction);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("ab");
		transactionFileExportUtil.downloadManualTransactionsPdf(list, response, messageSource);
	}

}
