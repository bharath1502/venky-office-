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

import com.chatak.pg.model.ReportsDTO;

@RunWith(MockitoJUnitRunner.class)
public class ReportsFileExportsUtilTest {

	@InjectMocks
	ReportsFileExportsUtil reportsFileExportsUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test(expected=NullPointerException.class)
	public void testDownloadReportsPdf() {
		List<ReportsDTO> txnList = new ArrayList<>();
		ReportsDTO dto = new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("543"));
		dto.setPaymentMethod("debit");
		txnList.add(dto);
		reportsFileExportsUtil.downloadReportsPdf(txnList, response, "headerProperty", messageSource);

	}
	
	@Test(expected=NullPointerException.class)
	public void testDownloadReportsPdfElse() {
		List<ReportsDTO> txnList = new ArrayList<>();
		ReportsDTO dto = new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("543"));
		dto.setPaymentMethod("abcd");
		txnList.add(dto);
		reportsFileExportsUtil.downloadReportsPdf(txnList, response, "headerProperty", messageSource);

	}
	
	@Test(expected=NullPointerException.class)
	public void testDownloadReportsPdfNull() {
		List<ReportsDTO> txnList = new ArrayList<>();
		ReportsDTO dto = new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("543"));
		txnList.add(dto);
		reportsFileExportsUtil.downloadReportsPdf(txnList, response, "headerProperty", messageSource);
	}
	
	@Test
	public void testDownloadReportsXl() {
		List<ReportsDTO> txnList = new ArrayList<>();
		ReportsDTO dto = new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("543"));
		dto.setPaymentMethod("debit");
		txnList.add(dto);
		reportsFileExportsUtil.downloadReportsXl(txnList, response, "headerProperty", messageSource);

	}
	
	@Test
	public void testDownloadReportsXlElse() {
		List<ReportsDTO> txnList = new ArrayList<>();
		ReportsDTO dto = new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("543"));
		dto.setPaymentMethod("xyz");
		txnList.add(dto);
		reportsFileExportsUtil.downloadReportsXl(txnList, response, "headerProperty", messageSource);

	}

}
