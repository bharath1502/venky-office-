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

import com.chatak.acquirer.admin.model.MerchantData;

@RunWith(MockitoJUnitRunner.class)
public class MerchantFileExportUtilTest {

	@InjectMocks
	MerchantFileExportUtil merchantFileExportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test
	public void testDownloadMerchantXl() {
		List<MerchantData> merchantData = new ArrayList<>();
		MerchantData data = new MerchantData();
		data.setPhone(Long.parseLong("5345435"));
		merchantData.add(data);
		merchantFileExportUtil.downloadMerchantXl(merchantData, response, "Merchant List", messageSource);
	}

	@Test
	public void testDownloadMerchantXlElse() {
		List<MerchantData> merchantData = new ArrayList<>();
		MerchantData data = new MerchantData();
		data.setPhone(Long.parseLong("5345435"));
		merchantData.add(data);
		merchantFileExportUtil.downloadMerchantXl(merchantData, response, "headerMessage", messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadMerchantPdf() {
		List<MerchantData> merchantData = new ArrayList<>();
		MerchantData data = new MerchantData();
		data.setPhone(Long.parseLong("5345435"));
		data.setMerchantCode("5435");
		merchantData.add(data);
		merchantFileExportUtil.downloadMerchantPdf(merchantData, response, "Merchant List", messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testDownloadMerchantPdfElse() {
		List<MerchantData> merchantData = new ArrayList<>();
		MerchantData data = new MerchantData();
		merchantData.add(data);
		merchantFileExportUtil.downloadMerchantPdf(merchantData, response, "headerMessage", messageSource);
	}

}
