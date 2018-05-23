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

import com.chatak.pg.user.bean.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class FundTransferFileExportUtilTest {

	@InjectMocks
	FundTransferFileExportUtil fundTransferFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test(expected=NullPointerException.class)
	public void testDownloadFundTransferPdf() {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(Long.parseLong("43"));
		transaction.setRef_transaction_id(Long.parseLong("21"));
		transactionList.add(transaction);
		fundTransferFileExportUtil.downloadFundTransferPdf(transactionList, response, messageSource);

	}

	@Test
	public void testdownloadTransactionXl() {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(Long.parseLong("43"));
		transaction.setRef_transaction_id(Long.parseLong("21"));
		transactionList.add(transaction);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		fundTransferFileExportUtil.downloadTransactionXl(transactionList, response, messageSource);

	}

}
