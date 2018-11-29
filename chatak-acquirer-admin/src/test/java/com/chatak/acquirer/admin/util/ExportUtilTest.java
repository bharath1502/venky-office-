package com.chatak.acquirer.admin.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.pg.enums.ExportType;

@SuppressWarnings("static-access")
@RunWith(MockitoJUnitRunner.class)
public class ExportUtilTest {

    private static final Logger logger =  LogManager.getLogger(ExportUtilTest.class);

    @InjectMocks
	ExportUtil exportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

    @Test
	public void testExportDataCSV() throws IOException, DocumentException {
		ExportDetails exportDetails = new ExportDetails();
		List<String> headerList = new ArrayList<>();
		List<Object[]> fileData = new ArrayList<>();
		Object[] objects = { 1, 1, 1 };
		headerList.add("");
		fileData.add(objects);
		exportDetails.setHeaderList(headerList);
		exportDetails.setFileData(fileData);
		exportDetails.setExportType(ExportType.CSV);
        Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class),
            Matchers.any(Locale.class))).thenReturn("abcde");
        try {
          exportUtil.exportData(exportDetails, response, messageSource);
        } catch (Exception e) {
          logger.error("Error :: ExportUtilTest :: testExportDataCSV", e);
        }
	}

	@Test
	public void testExportDataXLS() throws IOException, DocumentException {
		ExportDetails exportDetails = new ExportDetails();
		List<String> headerList = new ArrayList<>();
		List<Object[]> fileData = new ArrayList<>();
		Object[] objects = { 1, 1, 1 };
		headerList.add("");
		fileData.add(objects);
		exportDetails.setHeaderList(headerList);
		exportDetails.setFileData(fileData);
		exportDetails.setExcelStartRowNumber(1);
		exportDetails.setExportType(ExportType.XLS);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class)))
				.thenReturn("abcde");
        try {
          exportUtil.exportData(exportDetails, response, messageSource);
        } catch (Exception e) {
          logger.error("Error :: ExportUtilTest :: testExportDataXLS", e);
        }
	}

	@Test
	public void testExportDataPDF() throws IOException, DocumentException {
		ExportDetails exportDetails = new ExportDetails();
		List<String> headerList = new ArrayList<>();
		List<Object[]> fileData = new ArrayList<>();
		Object[] objects = { 1, 1, 1 };
		headerList.add("");
		fileData.add(objects);
		exportDetails.setHeaderList(headerList);
		exportDetails.setFileData(fileData);
		exportDetails.setExcelStartRowNumber(1);
		exportDetails.setExportType(ExportType.PDF);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class)))
				.thenReturn("abcde");
        try {
          exportUtil.exportData(exportDetails, response, messageSource);
        } catch (Exception e) {
          logger.error("Error :: ExportUtilTest :: testExportDataPDF", e);
        }
	}

}
