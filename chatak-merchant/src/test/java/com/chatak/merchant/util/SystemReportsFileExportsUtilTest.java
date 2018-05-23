package com.chatak.merchant.util;

import static org.junit.Assert.*;

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

import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.util.Properties;
@RunWith(MockitoJUnitRunner.class)
public class SystemReportsFileExportsUtilTest {
	
	@InjectMocks
	SystemReportsFileExportsUtil systemReportsFileExportsUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Before
	public void pro() {
		java.util.Properties properties = new java.util.Properties();
		properties.setProperty("chatak.admin.revenue.generated.header.message", "0");
		Properties.mergeProperties(properties);
	}
	
	@Test
	public void testdownloadRevenueGeneratedXl(){
		List<ReportsDTO> list=new ArrayList<>();
		ReportsDTO dto=new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("8977"));
		dto.setParentMerchantId("6546");
		list.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(),Matchers.any(Object[].class),Matchers.any(Locale.class))).thenReturn("abcde");
		systemReportsFileExportsUtil.downloadRevenueGeneratedXl(list, response, messageSource);
		
	}
	
	@Test
	public void testdownloadRevenueGeneratedXlElse(){
		List<ReportsDTO> list=new ArrayList<>();
		ReportsDTO dto=new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("8977"));
		list.add(dto);
		Mockito.when(messageSource.getMessage(Matchers.anyString(),Matchers.any(Object[].class),Matchers.any(Locale.class))).thenReturn("abcde");
		systemReportsFileExportsUtil.downloadRevenueGeneratedXl(list, response, messageSource);
		
	}
	
	@Test(expected=NullPointerException.class)
	public void testDownloadRevenueGeneratedPdf(){
		List<ReportsDTO> list=new ArrayList<>();
		ReportsDTO dto=new ReportsDTO();
		dto.setAccountNumber(Long.parseLong("8977"));
		dto.setTransactionId("6546");
		dto.setAmount("867");
		dto.setCurrency("243");
		dto.setChatakFee("86");
		dto.setFee("9786");
		dto.setParentMerchantId("7897");
		dto.setTotalTxnAmount("8678");
		list.add(dto);
		systemReportsFileExportsUtil.downloadRevenueGeneratedPdf(list, response, messageSource);
		
	}
	
	@Test(expected=NullPointerException.class)
	public void testDownloadRevenueGeneratedPdfElse(){
		List<ReportsDTO> list=new ArrayList<>();
		ReportsDTO dto=new ReportsDTO();
		list.add(dto);
		systemReportsFileExportsUtil.downloadRevenueGeneratedPdf(list, response, messageSource);
		
	}
	
	@Test
	public void testDownloadSystemOverviewXl(){
		ReportsDTO dto=new ReportsDTO();
		dto.setMerchantAccountCount(Long.parseLong("765"));
		dto.setSubMerchantAccountCount(Long.parseLong("7665"));
		dto.setChatakAccountCount(Long.parseLong("665"));
		systemReportsFileExportsUtil.downloadSystemOverviewXl(dto, response);
		
	}

	
	@Test(expected=NullPointerException.class)
	public void testDownloadSystemOverviewPdf(){
		ReportsDTO dto=new ReportsDTO();
		dto.setMerchantAccountCount(Long.parseLong("765"));
		dto.setSubMerchantAccountCount(Long.parseLong("7665"));
		dto.setChatakAccountCount(Long.parseLong("665"));
		systemReportsFileExportsUtil.downloadSystemOverviewPdf(dto, response);
		
	}


}
