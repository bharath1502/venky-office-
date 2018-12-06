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

import com.chatak.pg.model.ReportsDTO;

@RunWith(MockitoJUnitRunner.class)
public class SystemRevenueReportFileExportUtilTest {

	@InjectMocks
	SystemRevenueReportFileExportUtil systemRevenueReportFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test
	public void testDownloadRevenueGeneratedXl() {
		List<ReportsDTO> list = new ArrayList<>();
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setAccountNumber(Long.parseLong("534"));
		reportsDTO.setParentMerchantId("54");
		list.add(reportsDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		systemRevenueReportFileExportUtil.downloadRevenueGeneratedXl(list, response, messageSource);
	}

	@Test
	public void testDownloadRevenueGeneratedXlElse() {
		List<ReportsDTO> list = new ArrayList<>();
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setAccountNumber(Long.parseLong("534"));
		list.add(reportsDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		systemRevenueReportFileExportUtil.downloadRevenueGeneratedXl(list, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadRevenueGeneratedPdf() {
		List<ReportsDTO> list = new ArrayList<>();
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setAccountNumber(Long.parseLong("534"));
		reportsDTO.setAmount("23423");
		reportsDTO.setCurrency("23");
		reportsDTO.setChatakFee("543");
		reportsDTO.setFee("23");
		list.add(reportsDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		systemRevenueReportFileExportUtil.downloadRevenueGeneratedPdf(list, response, messageSource);

	}

	@Test(expected = NullPointerException.class)
	public void testDownloadRevenueGeneratedPdfElse() {
		List<ReportsDTO> list = new ArrayList<>();
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setAccountNumber(Long.parseLong("534"));
		reportsDTO.setParentMerchantId("54");
		list.add(reportsDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		systemRevenueReportFileExportUtil.downloadRevenueGeneratedPdf(list, response, messageSource);
	}

	@Test
	public void testDownloadSystemOverviewXl() {
		List<ReportsDTO> list = new ArrayList<>();
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setMerchantAccountCount(Long.parseLong("53"));
		reportsDTO.setSubMerchantAccountCount(Long.parseLong("53"));
		reportsDTO.setChatakAccountCount(Long.parseLong("53"));
		list.add(reportsDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		systemRevenueReportFileExportUtil.downloadSystemOverviewXl(list, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadSystemOverviewPdf() {
		List<ReportsDTO> list = new ArrayList<>();
		ReportsDTO reportsDTO = new ReportsDTO();
		reportsDTO.setMerchantAccountCount(Long.parseLong("53"));
		reportsDTO.setSubMerchantAccountCount(Long.parseLong("53"));
		reportsDTO.setChatakAccountCount(Long.parseLong("53"));
		list.add(reportsDTO);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
				Matchers.any(Locale.class))).thenReturn("abcde");
		systemRevenueReportFileExportUtil.downloadSystemOverviewPdf(list, response, messageSource);
	}

}
