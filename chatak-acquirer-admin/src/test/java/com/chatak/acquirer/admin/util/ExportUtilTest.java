package com.chatak.acquirer.admin.util;

import java.io.IOException;
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

import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.pg.enums.ExportType;
import com.itextpdf.text.DocumentException;

import jxl.write.WriteException;

@RunWith(MockitoJUnitRunner.class)
public class ExportUtilTest {

	@InjectMocks
	ExportUtil exportUtil;

	@Mock
	HttpServletResponse response;

	@Mock
	MessageSource messageSource;

	@Test(expected = NullPointerException.class)
	public void testExportDataCSV() throws WriteException, IOException, DocumentException {
		ExportDetails exportDetails = new ExportDetails();
		List<String> headerList = new ArrayList<>();
		List<Object[]> fileData = new ArrayList<>();
		Object[] objects = { 1, 1, 1 };
		String s = new String();
		headerList.add(s);
		fileData.add(objects);
		exportDetails.setHeaderList(headerList);
		exportDetails.setFileData(fileData);
		exportDetails.setExportType(ExportType.CSV);
		exportUtil.exportData(exportDetails, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testExportDataXLS() throws WriteException, IOException, DocumentException {
		ExportDetails exportDetails = new ExportDetails();
		List<String> headerList = new ArrayList<>();
		List<Object[]> fileData = new ArrayList<>();
		Object[] objects = { 1, 1, 1 };
		String s = new String();
		headerList.add(s);
		fileData.add(objects);
		exportDetails.setHeaderList(headerList);
		exportDetails.setFileData(fileData);
		exportDetails.setExcelStartRowNumber(1);
		exportDetails.setExportType(ExportType.XLS);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class)))
				.thenReturn("abcde");
		exportUtil.exportData(exportDetails, response, messageSource);
	}

	@Test(expected = NullPointerException.class)
	public void testExportDataPDF() throws WriteException, IOException, DocumentException {
		ExportDetails exportDetails = new ExportDetails();
		List<String> headerList = new ArrayList<>();
		List<Object[]> fileData = new ArrayList<>();
		Object[] objects = { 1, 1, 1 };
		String s = new String();
		headerList.add(s);
		fileData.add(objects);
		exportDetails.setHeaderList(headerList);
		exportDetails.setFileData(fileData);
		exportDetails.setExcelStartRowNumber(1);
		exportDetails.setExportType(ExportType.PDF);
		Mockito.when(messageSource.getMessage(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(Locale.class)))
				.thenReturn("abcde");
		exportUtil.exportData(exportDetails, response, messageSource);
	}

}
