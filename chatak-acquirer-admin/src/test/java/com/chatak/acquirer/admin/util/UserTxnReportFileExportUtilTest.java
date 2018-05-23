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

import com.chatak.pg.user.bean.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class UserTxnReportFileExportUtilTest {
	@InjectMocks
	UserTxnReportFileExportUtil userTxnReportFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test
	public void testDownloadSpecificUserStatementXl() {
		List<Transaction> list = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setTransaction_type("debit");
		list.add(transaction);
		userTxnReportFileExportUtil.downloadSpecificUserStatementXl(list, response, "headerMessage", messageSource);
	}

	@Test
	public void testDownloadSpecificUserStatementXlElse() {
		List<Transaction> list = new ArrayList<>();
		Transaction transaction = new Transaction();
		list.add(transaction);
		userTxnReportFileExportUtil.downloadSpecificUserStatementXl(list, response, "headerMessage", messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadSpecificUserStatementPdf() {
		List<Transaction> list = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setPayment_method("433");
		list.add(transaction);
		userTxnReportFileExportUtil.downloadSpecificUserStatementPdf(list, response, "headerMessage", messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadSpecificUserStatementPdfElse() {
		List<Transaction> list = new ArrayList<>();
		Transaction transaction = new Transaction();
		list.add(transaction);
		userTxnReportFileExportUtil.downloadSpecificUserStatementPdf(list, response, "headerMessage", messageSource);
	}

}
