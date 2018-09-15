package com.chatak.acquirer.admin.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import be.quodlibet.boxable.utils.PDStreamUtils;

public class ExportUtil {

  private static final String ATTACHMENT_FILE_NAME = "attachment;filename=";
  private static final String TIMES_NEW_ROMAN = "Times New Roman";
  private static final String CONTENT_DESCRIPTION = "Content-Disposition";
  private static final int TABLE_ROW_NUM = 7;
  private static final int CELL_COUNT = 2;

  private ExportUtil() {

  }

  public static void exportData(ExportDetails exportDetails, HttpServletResponse response,
      MessageSource messageSource) throws IOException {

    ExportType expTypeEnum = exportDetails.getExportType();

    String name = (exportDetails.getReportName() == null) ? "" : exportDetails.getReportName();

    String dateTernary = (Constants.EXPORT_FILE_NAME_DATE_FORMAT) == null ? "MMddyyyyHHmmss"
        : Constants.EXPORT_FILE_NAME_DATE_FORMAT;
    String dateFormat =
        (exportDetails.getDateFormatter() == null) ? dateTernary : exportDetails.getDateFormatter();

    Date date = new Date();
    String dateString = new SimpleDateFormat(dateFormat).format(date);
    StringBuilder fileName = new StringBuilder();

    switch (expTypeEnum) {
      case CSV:
        response.setContentType("text/csv;charset=UTF-8");
        fileName.append(name).append(dateString).append(".csv");
        response.setHeader(CONTENT_DESCRIPTION, ATTACHMENT_FILE_NAME + fileName);
        populateCSVData(exportDetails, response, messageSource);
        break;
      case XLS:
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        fileName.append(name).append(dateString).append(".xls");
        response.setHeader(CONTENT_DESCRIPTION, ATTACHMENT_FILE_NAME + fileName);
        populateXLSData(exportDetails, response, messageSource);
        break;
      case PDF:
        response.setContentType("application/pdf;charset=UTF-8");
        fileName.append(name).append(dateString).append(".pdf");
        response.setHeader(CONTENT_DESCRIPTION, ATTACHMENT_FILE_NAME + fileName);
        populatePDFData(exportDetails, response, messageSource);
        break;
      default:
        // Do nothing
    }
  }

  private static void populateXLSData(ExportDetails exportDetails, HttpServletResponse response,
      MessageSource messageSource) throws IOException {		

	    String headerMsgProp = exportDetails.getHeaderMessageProperty();
	    HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet =wb.createSheet(
		        messageSource.getMessage(headerMsgProp, null, LocaleContextHolder.getLocale()));
	    Font font = wb.createFont();
	    font.setFontHeightInPoints((short)11);
	    font.setFontName(TIMES_NEW_ROMAN);
	    font.setBold(true);
	    CellStyle style = wb.createCellStyle();
	    style.setFont(font);
	    
	    Date date = new Date();
	    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
	    CellUtil.createCell(sheet.createRow(0), 0, messageSource.getMessage(headerMsgProp, null, LocaleContextHolder.getLocale()), style);
	    CellUtil.createCell(sheet.createRow(2), 0, messageSource.getMessage("userList-file-exportutil-reportdate", null,
		        LocaleContextHolder.getLocale()) + headerDate);
	    
			Map<String, String> map = null;
			if (exportDetails.getMap() != null) {
				map = exportDetails.getMap();
				int cellCount = CELL_COUNT;
				for (Map.Entry  MapColummn : map.entrySet()) {
					sheet.createRow(cellCount++).createCell(0).setCellValue(MapColummn.getKey() + "" + MapColummn.getValue());
				}
			}
		
	    int rowNum = TABLE_ROW_NUM;
	    if (exportDetails.getExcelStartRowNumber() != null) {
	      rowNum = exportDetails.getExcelStartRowNumber();
	    }

	    List<String> headerList = exportDetails.getHeaderList();
	    List<Object[]> fileData = exportDetails.getFileData();
	    Row headerRow = sheet.createRow(rowNum);
	    for (int i = 0, len = headerList.size(); i < len; i++) {
	      CellUtil.createCell(headerRow, i,headerList.get(i), style);
	    }
	   
	    CellStyle floatStyle = wb.createCellStyle();
		floatStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
	    int j = Constants.SIX;
	    for (Object[] rowData : fileData) {
	      rowNum++;
	      int i = 0;
	      HSSFRow colRow = sheet.createRow(rowNum);
	      for (Object rowElement : rowData) {
	        if (rowElement instanceof Double) {
	        	HSSFCell cell = colRow.createCell(i++);
	        	cell.setCellValue(processDoubleAmount(rowElement));
	        	cell.setCellStyle(floatStyle);
	        } else if (rowElement instanceof String) {
	        	colRow.createCell(i++).setCellValue((String)rowElement + "");
	        } else if (rowElement instanceof Date) {
	        	colRow.createCell(i++).setCellValue((Date)rowElement);
	        } else if (rowElement instanceof Boolean) {
	            colRow.createCell(i++).setCellValue((Boolean)rowElement + "");
	        } else if (rowElement instanceof Long) {
	        	colRow.createCell(i++).setCellValue((Long)rowElement);
	        } else if (rowElement instanceof Integer) {
	        	colRow.createCell(i++).setCellValue((Integer)rowElement);
	        } else if(rowElement == null){
	        	colRow.createCell(i++).setCellValue("" + "");
	        } else {
	        	colRow.createCell(i++).setCellValue(String.valueOf(rowElement));
	          
	        }
	      }
	      j = j + 1;
	    }

	    wb.write(response.getOutputStream());
	    wb.close();
	  }

  private static double processDoubleAmount(Object rowElement) {
    return (!"".equals(rowElement)) ? Double.parseDouble(rowElement.toString()): 0d;
  }

  public static void populatePDFData(ExportDetails exportDetails, HttpServletResponse response, MessageSource messageSource) throws IOException {
  	    //Initialize Document
  	    PDDocument doc = new PDDocument();
  	    PDPage page = new PDPage();

  	    String headerMsgProp = exportDetails.getHeaderMessageProperty();
  	    String reportName = messageSource.getMessage(headerMsgProp, null, LocaleContextHolder.getLocale());
  	    //Create a landscape page
  	    page.setMediaBox(new PDRectangle(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight()));
  	    doc.addPage(page);

  	    Calendar calendar = Calendar.getInstance();
  	    String reportDateText = messageSource.getMessage("userList-file-exportutil-reportdate", null, LocaleContextHolder.getLocale())
  	       + DateUtil.toDateStringFormat(new Timestamp(calendar.getTimeInMillis()),
  	        		Constants.EXPORT_HEADER_DATE_FORMAT);

  	    //Initialize table
  	    float margin = Constants.INT_THIRTY;
  	    float tableWidth = page.getMediaBox().getWidth() - (Constants.INT_TWO * margin);
  	    float yStartNewPage = page.getMediaBox().getHeight() - (Constants.INT_TWO * margin);
  	    float yStart = yStartNewPage;
  	    float bottomMargin = Constants.TWENTY_FIVE;
  	    float topMargin = Constants.INT_TEN;
  	    FooterPageProvider pageProvider = new FooterPageProvider(doc,
  	        new PDRectangle(PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight()));
  	    BaseTable dataTable = new BaseTable(yStart, yStartNewPage, topMargin, bottomMargin, tableWidth,
  	        bottomMargin, doc, page, true, true, pageProvider);
  	    
  		PDPageContentStream contentStream = new PDPageContentStream(doc, page);
  		 PDStreamUtils.rect(contentStream, Constants.INT_THIRTY, Constants.INDEX_EIGHTEEN,
  			        page.getMediaBox().getWidth() - Constants.INT_FIFTY, Constants.INDEX_ONE, Color.BLACK);
  		 PDStreamUtils.write(contentStream, FooterPageProvider.FOOTER, PDType1Font.TIMES_ROMAN,  Constants.INDEX_TEN, Constants.INT_THIRTY, Constants.INDEX_TEN, Color.BLACK);
  		 PDStreamUtils.write(contentStream, "1", PDType1Font.TIMES_ROMAN, Constants.INDEX_TEN,
  				 page.getMediaBox().getWidth() - Constants.INT_THIRTY, Constants.INDEX_TEN, Color.BLACK);
  		    
  			dataTable.drawTitle(reportName, PDType1Font.TIMES_ROMAN, Constants.INDEX_FIFTEEN, tableWidth,
  					Constants.TWENTY_FIVE, "center", 0, true);

  			PDStreamUtils.rect(contentStream, Constants.INT_THIRTY, Constants.SEVEN_HUNDRED_SIXTY_FIVE,
  				page.getMediaBox().getWidth() - Constants.TWENTY_FIVE, 0, Color.BLACK);

  			dataTable.drawTitle(reportDateText, PDType1Font.TIMES_ROMAN, Constants.INDEX_TEN, tableWidth, Constants.INDEX_TEN, "right",
  				0, true);

  			contentStream.close();

  	    //add the column Header list
  	    List<List> data = new ArrayList<List>();
  	    data.add(new ArrayList<String>(exportDetails.getHeaderList()));

  	    // Add table data    
  	    for (Object[] rowData : exportDetails.getFileData()) {
  	      List<String> dataList = new ArrayList<String>();
  	      for (Object rowElement : rowData) {
  	        dataList.add((rowElement != null) ? rowElement + "" : "");
  	      }
  	      data.add(dataList);
  	    }

  	    DataTable t = new DataTable(dataTable, page);
  	    t.getHeaderCellTemplate().setFillColor(Color.LIGHT_GRAY);
  	    t.getDataCellTemplateEven().setFontSize(Constants.EIGHT);
  	    t.getDataCellTemplateOdd().setFontSize(Constants.EIGHT);
  	    t.addListToTable(data, DataTable.HASHEADER);

  	    dataTable.draw();
  	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
  	    doc.save(baos);
  	    doc.close();

  	    response.setHeader("Expires", "0");
  	    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
  	    response.setHeader("Pragma", "public");
  	    response.setContentLength(baos.size());

  	    ServletOutputStream os = response.getOutputStream();
  	    baos.writeTo(os);
  	    os.flush();
  	    os.close();
  	  }

  private static void populateCSVData(ExportDetails exportDetails, HttpServletResponse response,
      MessageSource messageSource) throws IOException {

    Date date = new Date();
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String headerMsgProp = exportDetails.getHeaderMessageProperty();
    StringBuilder fw = new StringBuilder();
    fw.append(messageSource.getMessage(headerMsgProp, null, LocaleContextHolder.getLocale()));
    fw.append('\n');
    fw.append('\n');
    fw.append(messageSource.getMessage("userList-file-exportutil-reportdate", null,
        LocaleContextHolder.getLocale()) + headerDate);
    fw.append('\n');
    fw.append('\n');

    List<String> headerList = exportDetails.getHeaderList();
    List<Object[]> fileData = exportDetails.getFileData();

    for (String headerElement : headerList) {
      fw.append(headerElement).append(",");
    }
    fw.append('\n');

    for (Object[] rowData : fileData) {
      boolean isFirstRowElement = true;
      for (Object rowElement : rowData) {
        if (isFirstRowElement) {
          isFirstRowElement = false;
        } else {
          fw.append(",");
        }
        fw.append(
            (rowElement != null) ? Utils.formatCommaSeparatedValues(rowElement.toString()) : "");
      }
      fw.append('\n');
    }
    response.getWriter().print(fw);

  }

}
