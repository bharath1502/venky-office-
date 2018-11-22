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

import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class TransactionFileExportUtilTest {

	@InjectMocks
	TransactionFileExportUtil transactionFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test
	public void testDownloadSettlementReportXl() {
		List<Transaction> transactionList = new ArrayList<>();
		GetTransactionsListRequest request = new GetTransactionsListRequest();
		Transaction transaction = new Transaction();
		request.setFrom_date("1/1/1999");
		request.setTo_date("1/1/1999");
		transactionList.add(transaction);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		transactionFileExportUtil.downloadSettlementReportXl(transactionList, response, messageSource, request);

	}

}
