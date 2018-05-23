package com.chatak.acquirer.admin.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportUtil {

  private static final String ATTACHMENT_FILE_NAME = "attachment;filename=";

  private static final String CONTENT_DESCRIPTION = "Content-Disposition";
  private static final int WIDTH_ARRAY_INDEX = 3;
  private static final int TABLE_TOTAL_WIDTH = 180;
  private static final int TABLE_ROW_NUM = 7;
  private static final int FONT_SIZE_10 = 10;
  private static final int CELL_SIZE_8 = 8;
  private static final int CELL_SIZE_4 = 4;
  private static final int HEADDER_BOTTOM_SIZE_10 = 10;
  private static final int HEADDER_BOTTOM_SIZE_18 = 18;
  private static final int DOC_MARGIN_LEFT = 50;
  private static final int DOC_MARGIN_RIGHT = 50;
  private static final int DOC_MARGIN_TOP = 70;
  private static final int DOC_MARGIN_BOTTOM = 70;

  private ExportUtil() {

  }

  public static void exportData(ExportDetails exportDetails, HttpServletResponse response,
      MessageSource messageSource) throws IOException, WriteException, DocumentException {

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
      MessageSource messageSource) throws IOException, WriteException {

    String headerMsgProp = exportDetails.getHeaderMessageProperty();
    WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
    WritableSheet s = w.createSheet(
        messageSource.getMessage(headerMsgProp, null, LocaleContextHolder.getLocale()), 0);
    WritableFont wfobj = new WritableFont(WritableFont.TIMES, Integer.parseInt("11"), WritableFont.BOLD);
    WritableCellFormat cellFormat = new WritableCellFormat(wfobj);
    Date date = new Date();
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);

    s.addCell(new Label(0, 0,
        messageSource.getMessage(headerMsgProp, null, LocaleContextHolder.getLocale()),
        cellFormat));
    s.addCell(new Label(0, Integer.parseInt("2"), messageSource.getMessage("userList-file-exportutil-reportdate", null,
        LocaleContextHolder.getLocale()) + headerDate));

    int rowNum = TABLE_ROW_NUM;
    if (exportDetails.getExcelStartRowNumber() != null) {
      rowNum = exportDetails.getExcelStartRowNumber();
    }

    List<String> headerList = exportDetails.getHeaderList();
    List<Object[]> fileData = exportDetails.getFileData();

    for (int i = 0, len = headerList.size(); i < len; i++) {
      s.addCell(new Label(i, rowNum, headerList.get(i), cellFormat));
    }

    int j = Constants.SIX;
    for (Object[] rowData : fileData) {
      rowNum++;
      int i = 0;

      for (Object rowElement : rowData) {
        if (rowElement instanceof Double) {
          s.addCell(StringUtil.getAmountInFloat(i++, j, processDoubleAmount(rowElement)));
        } else if (rowElement instanceof String) {
          s.addCell(new Label(i++, rowNum, ((String)rowElement) + ""));
        } else if (rowElement instanceof Date) {
          s.addCell(new Label(i++, rowNum, ((Date)rowElement) + ""));
        } else if (rowElement instanceof Boolean) {
          s.addCell(new Label(i++, rowNum, ((Boolean)rowElement) + ""));
        } else if (rowElement instanceof Long) {
          s.addCell(new jxl.write.Number(i++, j, ((Long)rowElement)));
        } else if (rowElement instanceof Integer) {
          s.addCell(new jxl.write.Number(i++, j, ((Integer)rowElement)));
        } else {
          s.addCell(new jxl.write.Label(i++, j, ((String)rowElement)));
        }
      }
      j = j + 1;
    }

    w.write();
    w.close();
  }

  private static double processDoubleAmount(Object rowElement) {
    return (!"".equals(rowElement)) ? Double.parseDouble(rowElement.toString()): 0d;
  }

  private static void populatePDFData(ExportDetails exportDetails, HttpServletResponse response,
      MessageSource messageSource) throws IOException, DocumentException {

    String headerMsgProp = exportDetails.getHeaderMessageProperty();
    List<String> headerList = exportDetails.getHeaderList();
    List<Object[]> fileData = exportDetails.getFileData();

    PdfPTable table = new PdfPTable(headerList.size());
    int[] widthArr = new int[headerList.size()];
    for (int i = 0, len = headerList.size(); i < len; i++) {
      widthArr[i] = WIDTH_ARRAY_INDEX;
    }
    table.setWidths(widthArr);
    table.setTotalWidth(TABLE_TOTAL_WIDTH);

    // Add Report Date
    Calendar calendar = Calendar.getInstance();
    String phraseText = messageSource.getMessage("userList-file-exportutil-reportdate", null,
        LocaleContextHolder.getLocale())
        + DateUtil.toDateStringFormat(new Timestamp(calendar.getTimeInMillis()),
            Constants.EXPORT_HEADER_DATE_FORMAT);
    PdfPCell reportDateCell =
        getPdfPCell(phraseText, getFont(FONT_SIZE_10, null), headerList.size(), Rectangle.NO_BORDER,
            Element.ALIGN_RIGHT, null, null, CELL_SIZE_8, CELL_SIZE_8);
    table.addCell(reportDateCell);

    // Add header data
    Font tableHeaderFont = getFont(FONT_SIZE_10, "#FFFFFF");
    for (String headerElement : headerList) {
      PdfPCell tableHeaderCell = getPdfPCell(headerElement, tableHeaderFont, null, null,
          Element.ALIGN_CENTER, BaseColor.GRAY, CELL_SIZE_4, null, null);
      table.addCell(tableHeaderCell);
    }

    // Add table data
    for (Object[] rowData : fileData) {
      int j = 1;
      table.setHeaderRows(j);

      for (Object rowElement : rowData) {
        table.addCell((rowElement != null) ? rowElement + "" : "");
      }
    }

    Document document = new Document(PageSize.A3, DOC_MARGIN_LEFT, DOC_MARGIN_RIGHT, DOC_MARGIN_TOP,
        DOC_MARGIN_BOTTOM);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    TableHeader event = new TableHeader();
    writer.setPageEvent(event);
    event.setFooter(messageSource.getMessage("chatak.footer.copyright.message", null,
        LocaleContextHolder.getLocale()));

    document.open();
    Rectangle page = document.getPageSize();
    PdfPTable header = new PdfPTable(1);

    PdfPCell pageHeadercell =
        getPdfPCell(messageSource.getMessage(headerMsgProp, null, LocaleContextHolder.getLocale()),
            getFont(HEADDER_BOTTOM_SIZE_18, null), headerList.size(), Rectangle.BOTTOM,
            Element.ALIGN_CENTER, null, null, null, HEADDER_BOTTOM_SIZE_10);
    header.addCell(pageHeadercell);
    header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());

    header.writeSelectedRows(0, -1, document.leftMargin(),
        page.getHeight() - document.topMargin() + header.getTotalHeight(),
        writer.getDirectContent());

    document.add(table);
    document.close();
    response.setHeader("Expires", "0");
    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Pragma", "public");
    response.setContentLength(baos.size());
    ServletOutputStream os = response.getOutputStream();
    baos.writeTo(os);
    os.flush();
    os.close();
  }

  private static Font getFont(int size, String rgbColor) {
    Font font = new Font();
    font.setSize(size);
    font.setStyle(Font.BOLD);

    if (rgbColor != null) {
      BaseColor headerColor = WebColors.getRGBColor(rgbColor);
      font.setColor(headerColor);
    }
    return font;
  }

  private static PdfPCell getPdfPCell(String phraseText, Font font, Integer colSpan, Integer border,
      Integer horizontalAlignment, BaseColor backgroundColor, Integer padding, Integer paddingTop,
      Integer paddingBottom) {
    PdfPCell cell = new PdfPCell(new Phrase(phraseText, font));
    if (colSpan != null) {
      cell.setColspan(colSpan);
    }
    if (border != null) {
      cell.setBorder(border);
    }
    if (horizontalAlignment != null) {
      cell.setHorizontalAlignment(horizontalAlignment);
    }
    if (backgroundColor != null) {
      cell.setBackgroundColor(backgroundColor);
    }
    if (padding != null) {
      cell.setPadding(padding);
    }
    if (paddingTop != null) {
      cell.setPaddingTop(paddingTop);
    }
    if (paddingBottom != null) {
      cell.setPaddingBottom(paddingBottom);
    }

    return cell;
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
