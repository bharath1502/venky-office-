package com.chatak.acquirer.admin.util;

import java.sql.Timestamp;
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

import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.user.bean.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class DashBoardTransactionFileExportUtilTest {

	@InjectMocks
	DashBoardTransactionFileExportUtil dashBoardTransactionFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test(expected = NullPointerException.class)
	public void testDownloadDashBoardTransPdf() {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setRef_transaction_id(Long.parseLong("345"));
		transactionList.add(transaction);
		dashBoardTransactionFileExportUtil.downloadDashBoardTransPdf(transactionList, response, messageSource);
	}

	@Test
	public void testDownloadDashBoardTransXl() {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setRef_transaction_id(Long.parseLong("345"));
		transactionList.add(transaction);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		dashBoardTransactionFileExportUtil.downloadDashBoardTransXl(transactionList, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadDashBoardEFTTransPdf() {
		Timestamp timestamp = new Timestamp(Long.parseLong("543543"));
		List<LitleEFTDTO> litleEFTRequestFromDashBoard = new ArrayList<>();
		LitleEFTDTO litleEFTDTO = new LitleEFTDTO();
		litleEFTDTO.setDateTime(timestamp);
		litleEFTDTO.setAmount(Long.parseLong("543"));
		litleEFTRequestFromDashBoard.add(litleEFTDTO);
		dashBoardTransactionFileExportUtil.downloadDashBoardEFTTransPdf(litleEFTRequestFromDashBoard, response,
				messageSource);
	}

	@Test
	public void testDownloadDashBoardEFTTransXl() {
		Timestamp timestamp = new Timestamp(Long.parseLong("543543"));
		List<LitleEFTDTO> litleEFTRequestFromDashBoard = new ArrayList<>();
		LitleEFTDTO litleEFTDTO = new LitleEFTDTO();
		litleEFTDTO.setDateTime(timestamp);
		litleEFTDTO.setAmount(Long.parseLong("543"));
		litleEFTRequestFromDashBoard.add(litleEFTDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		dashBoardTransactionFileExportUtil.downloadDashBoardEFTTransXl(litleEFTRequestFromDashBoard, response,
				messageSource);
	}

}
