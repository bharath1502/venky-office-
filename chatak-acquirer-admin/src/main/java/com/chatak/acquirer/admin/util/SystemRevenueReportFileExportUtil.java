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

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
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
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class SystemRevenueReportFileExportUtil {

  SystemRevenueReportFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(SystemRevenueReportFileExportUtil.class);

  public static void downloadRevenueGeneratedXl(List<ReportsDTO> list, HttpServletResponse response,
      MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Revenue" + dateString + ".xls";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    try {

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(messageSource.getMessage(Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE,
              null, LocaleContextHolder.getLocale()), 0);
      WritableCellFormat cellFormat = new WritableCellFormat();
      cellFormat.setAlignment(Alignment.RIGHT);

      s.addCell(new Label(0, 0, messageSource.getMessage(
          Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE, null, LocaleContextHolder.getLocale())));
      s.addCell(
          new Label(0, Constants.TWO, messageSource.getMessage("reports-file-exportutil-reportDate",
              null, LocaleContextHolder.getLocale()) + headerDate));
      s.addCell(new Label(0, Constants.FOUR, messageSource
          .getMessage("reports-file-exportutil-dateTime", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(1, Constants.FOUR, messageSource
          .getMessage("reports-file-exportutil-userName", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-companyOrFullName", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-accountNumber", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-transactionId", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-transactionDescription", null,
              LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SIX, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-totalTransactionAmount", null,
              LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource
          .getMessage("reports-file-exportutil-currency", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-rapidRevenue", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.NINE, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-merchantRevenue", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.TEN, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-amountToMerchantA/C", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.ELEVEN, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-amountToSubMerchantA/C", null,
              LocaleContextHolder.getLocale())));

      int j = Constants.FIVE;
      for (ReportsDTO userTrans : list) {
        int i = 0;
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getDateTime())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getUserName())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getCompanyName())) + ""));
        s.addCell(
            new Label(i++, j, "" + (getDetails(userTrans.getAccountNumber().toString())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getTransactionId())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getDescription())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getAmount())) + "", cellFormat));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getCurrency())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getChatakFee())) + "", cellFormat));
        s.addCell(new Label(i++, j, "" + (getDetails(userTrans.getFee())) + "", cellFormat));
        if (StringUtils.isValidString(userTrans.getParentMerchantId())) {
          s.addCell(new Label(i++, j, "" + ("NA") + ""));
          s.addCell(StringUtil.getAmountInFloat(i, j, (userTrans.getTotalTxnAmount() != null)
              ? Double.parseDouble(userTrans.getTotalTxnAmount()) : 0d));
        } else {
          s.addCell(StringUtil.getAmountInFloat(i, j, (userTrans.getTotalTxnAmount() != null)
              ? Double.parseDouble(userTrans.getTotalTxnAmount()) : 0d));
          s.addCell(new Label(i, j, "" + ("NA") + ""));
        }

        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR:: ReportsFileExportUtil::downloadRevenueGeneratedXl ", e);
    }

  }

  public static void downloadRevenueGeneratedPdf(List<ReportsDTO> list,
      HttpServletResponse response, MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Revenue" + dateString + ".pdf";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    PdfPTable table = new PdfPTable(Constants.TWELVE);
    try {
      table.setWidths(new int[] {Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR,
          Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR,
          Constants.FOUR, Constants.FOUR, Constants.FOUR});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: ReportsFileExportsUtil :: downloadRevenueGeneratedPdf", e1);
    }

    BaseColor myColortext;
    Font myContentStyle = new Font();
    myContentStyle.setSize(Constants.TEN);
    myContentStyle.setStyle(Font.BOLD);
    myColortext = WebColors.getRGBColor("#FFFFFF");
    myContentStyle.setColor(myColortext);

    Font reportStyle = new Font();
    reportStyle.setSize(Constants.TEN);
    reportStyle.setStyle(Font.BOLD);

    Calendar calendar = Calendar.getInstance();
    PdfPCell reportdate = new PdfPCell(new Phrase(messageSource
        .getMessage("reports-file-exportutil-reportDate", null, LocaleContextHolder.getLocale())
        + DateUtil.toDateStringFormat(new Timestamp(calendar.getTimeInMillis()),
            Constants.EXPORT_HEADER_DATE_FORMAT),
        reportStyle));
    reportdate.setColspan(Constants.THIRTEEN);
    reportdate.setPaddingBottom(Constants.EIGHT);
    reportdate.setPaddingTop(Constants.EIGHT);
    reportdate.setHorizontalAlignment(Element.ALIGN_RIGHT);
    reportdate.setBorder(Rectangle.NO_BORDER);
    table.addCell(reportdate);

    PdfPCell c1 =
        new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-dateTime", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-userName", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reports-file-exportutil-companyOrFullName", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-accountNumber",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-transactionId",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reports-file-exportutil-transactionDescription", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reports-file-exportutil-totalTransactionAmount", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-currency", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-rapidRevenue",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-merchantRevenue",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reports-file-exportutil-amountToMerchantA/C", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reports-file-exportutil-amountToSubMerchantA/C", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (ReportsDTO userTrans : list) {
      int j = 1;
      table.setHeaderRows(j);
      table.addCell(getDetails(userTrans.getDateTime()));
      table.addCell(getDetails(userTrans.getUserName()));
      table.addCell(getDetails(userTrans.getCompanyName()));
      table.addCell(getDetails(userTrans.getAccountNumber().toString()));
      table.addCell(getDetails(userTrans.getTransactionId()));
      table.addCell(getDetails(userTrans.getDescription()));
      if (userTrans.getAmount() != null) {
        c1 = new PdfPCell(new Phrase(userTrans.getAmount()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      table.addCell(getDetails(userTrans.getCurrency()));
      if (userTrans.getChatakFee() != null) {
        c1 = new PdfPCell(new Phrase(userTrans.getChatakFee()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      if (userTrans.getFee() != null) {
        c1 = new PdfPCell(new Phrase(userTrans.getFee()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      c1 = new PdfPCell(new Phrase(userTrans.getTotalTxnAmount()));
      c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(c1);
    }
    Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY,
        Constants.SEVENTY, Constants.SEVENTY);

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfWriter writer = PdfWriter.getInstance(document, baos);

      TableHeader event = new TableHeader();
      writer.setPageEvent(event);
      event.setFooter(messageSource.getMessage("chatak.footer.copyright.message", null,
          LocaleContextHolder.getLocale()));

      document.open();
      Font headerStyle = new Font();
      headerStyle.setSize(Constants.EIGHTEEN);
      headerStyle.setStyle(Font.BOLD);

      Rectangle page = document.getPageSize();
      PdfPTable header = new PdfPTable(1);
      PdfPCell headercell = new PdfPCell(
          new Phrase(messageSource.getMessage(Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE, null,
              LocaleContextHolder.getLocale()), headerStyle));
      headercell.setColspan(Constants.SIX);
      headercell.setBorder(Rectangle.BOTTOM);
      headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
      headercell.setPaddingBottom(Constants.TEN);
      header.addCell(headercell);
      header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());

      header.writeSelectedRows(0, -1, document.leftMargin(),
          page.getHeight() - document.topMargin() + header.getTotalHeight(),
          writer.getDirectContent());

      document.add(table);

      document.close();
      response.setHeader("Expires", "0");
      response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
      response.setHeader("Pragma", "public");
      response.setContentType(Constants.CONTENT_TYPE_PDF);
      response.setContentLength(baos.size());
      ServletOutputStream os = response.getOutputStream();
      baos.writeTo(os);
      os.flush();
      os.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();

    } catch (DocumentException e) {
      logger.error("ERROR::method1:: ReportsFileExportUtil::downloadRevenueGeneratedPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: ReportsFileExportUtil::downloadRevenueGeneratedPdf", e);
    }
  }

  public static void downloadSystemOverviewXl(List<ReportsDTO> overViewDownloadList,
      HttpServletResponse response, MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "System_Overview" + dateString + ".xls";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(messageSource.getMessage(Constants.CHATAK_SYSTEM_OVERVIEW_REPORTS_HEADER_MESSAGE,
              null, LocaleContextHolder.getLocale()), 0);
      cellFormat.setAlignment(Alignment.RIGHT);

      s.addCell(
          new Label(0, 0, messageSource.getMessage(Constants.CHATAK_SYSTEM_OVERVIEW_REPORTS_HEADER_MESSAGE,
              null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(
          new Label(0, Constants.TWO, messageSource.getMessage("reports-file-exportutil-reportdate",
              null, LocaleContextHolder.getLocale()) + headerDate, cellFormat));
      s.addCell(new Label(0, Constants.FOUR, messageSource
          .getMessage("reports-file-exportutil-accountType", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(1, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-activeAndBlockedAccounts", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FOUR,
          messageSource.getMessage("currency-search-page.label.currencycode", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-totalBalances", null,
              LocaleContextHolder.getLocale()),
          cellFormat));

      WritableCellFormat writableCellFormat = new WritableCellFormat(cellFont);
      writableCellFormat.setAlignment(Alignment.RIGHT);

      int j = Constants.FIVE;
      for (ReportsDTO accessLogs : overViewDownloadList) {
        int a = 0;
        s.addCell(new Label(a++, j, "" + ("Merchant") + ""));
        s.addCell(new Label(a++, j,
            "" + (getDetails(accessLogs.getMerchantAccountCount().toString())) + ""));
        s.addCell(
            new Label(a++, j, "" + (getDetails(accessLogs.getCurrency())), writableCellFormat));
        s.addCell(StringUtil.getAmountInFloat(a, j, (accessLogs.getMerchantAccountBalance() != null)
            ? Double.parseDouble(accessLogs.getMerchantAccountBalance()) : 0d));
        j = j + 1;
      }
      for (ReportsDTO accessLogs : overViewDownloadList) {
        int b = 0;
        s.addCell(new Label(b++, j, "" + ("Sub Merchant") + ""));
        s.addCell(new Label(b++, j,
            "" + (getDetails(accessLogs.getSubMerchantAccountCount().toString())) + ""));
        s.addCell(
            new Label(b++, j, "" + (getDetails(accessLogs.getCurrency())), writableCellFormat));
        s.addCell(
            StringUtil.getAmountInFloat(b, j, (accessLogs.getSubMerchantAccountBalance() != null)
                ? Double.parseDouble(accessLogs.getSubMerchantAccountBalance()) : 0d));
        j = j + 1;
      }
      for (ReportsDTO accessLogs : overViewDownloadList) {
        int c = 0;
        s.addCell(new Label(c++, j, "" + ("Revenue Account") + ""));
        s.addCell(new Label(c++, j,
            "" + (getDetails(accessLogs.getChatakAccountCount().toString()) + "")));
        s.addCell(
            new Label(c++, j, "" + (getDetails(accessLogs.getCurrency())), writableCellFormat));
        s.addCell(StringUtil.getAmountInFloat(c, j, (accessLogs.getChatakAccountBalance() != null)
            ? Double.parseDouble(accessLogs.getChatakAccountBalance()) : 0d));
        j = j + 1;
      }

      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR:: ReportsFileExportUtil::downloadManualTransactionsXl ", e);
    }

  }

  public static void downloadSystemOverviewPdf(List<ReportsDTO> overViewDownloadList,
      HttpServletResponse response, MessageSource messageSource) {
    response.setContentType(Constants.CONTENT_TYPE_PDF);
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "System_Overview" + dateString + ".pdf";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    PdfPTable table = new PdfPTable(Constants.FOUR);
    try {
      table.setWidths(new int[] {Constants.FIVE, Constants.FIVE, Constants.FIVE, Constants.FIVE});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: ReportsFileExportsUtil :: downloadSystemOverviewPdf", e1);
    }

    BaseColor myColortext;
    Font myContentStyle = new Font();
    myContentStyle.setSize(Constants.TEN);
    myContentStyle.setStyle(Font.BOLD);
    myColortext = WebColors.getRGBColor("#FFFFFF");
    myContentStyle.setColor(myColortext);

    Font reportStyle = new Font();
    reportStyle.setSize(Constants.TEN);
    reportStyle.setStyle(Font.BOLD);

    Calendar calendar = Calendar.getInstance();
    PdfPCell reportdate = new PdfPCell(new Phrase(messageSource
        .getMessage("reports-file-exportutil-reportdate", null, LocaleContextHolder.getLocale())
        + DateUtil.toDateStringFormat(new Timestamp(calendar.getTimeInMillis()),
            Constants.EXPORT_HEADER_DATE_FORMAT),
        reportStyle));
    reportdate.setColspan(Constants.TWELVE);
    reportdate.setPaddingBottom(Constants.EIGHT);
    reportdate.setPaddingTop(Constants.EIGHT);
    reportdate.setHorizontalAlignment(Element.ALIGN_RIGHT);
    reportdate.setBorder(Rectangle.NO_BORDER);
    table.addCell(reportdate);

    PdfPCell c1 =
        new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-accountType",
            null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reports-file-exportutil-activeAndBlockedAccounts",
            null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("currency-search-page.label.currencycode",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-totalBalances",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    int j;
    for (ReportsDTO accessLogs : overViewDownloadList) {
      j = 1;
      table.setHeaderRows(j);
      table.addCell("Merchant");
      table.addCell((accessLogs.getMerchantAccountCount().toString() != null)
          ? accessLogs.getMerchantAccountCount().toString() : "");

      addTableCells(table, accessLogs.getCurrency());

      addTableCells(table, accessLogs.getMerchantAccountBalance());
    }
    for (ReportsDTO accessLogs : overViewDownloadList) {
      j = Constants.TWO;
      table.setHeaderRows(j);
      table.addCell("Sub Merchant");
      table.addCell((accessLogs.getSubMerchantAccountCount().toString() != null)
          ? accessLogs.getSubMerchantAccountCount().toString() + "" : "");

      addTableCells(table, accessLogs.getCurrency());

      addTableCells(table, accessLogs.getSubMerchantAccountBalance());
    }
    for (ReportsDTO accessLogs : overViewDownloadList) {
      j = Constants.THREE;
      table.setHeaderRows(j);
      table.addCell("Revenue Account");
      table.addCell((accessLogs.getChatakAccountCount().toString() != null)
          ? accessLogs.getChatakAccountCount().toString() + "" : "");

      addTableCells(table, accessLogs.getCurrency());

      addTableCells(table, accessLogs.getChatakAccountBalance());
    }
    Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY,
        Constants.SEVENTY, Constants.SEVENTY);

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfWriter writer = PdfWriter.getInstance(document, baos);

      TableHeader event = new TableHeader();
      writer.setPageEvent(event);
      event.setFooter(messageSource.getMessage("chatak.footer.copyright.message", null,
          LocaleContextHolder.getLocale()));

      document.open();
      Font headerStyle = new Font();
      headerStyle.setSize(Constants.EIGHTEEN);
      headerStyle.setStyle(Font.BOLD);

      Rectangle page = document.getPageSize();
      PdfPTable header = new PdfPTable(1);
      PdfPCell headercell = new PdfPCell(
          new Phrase(messageSource.getMessage(Constants.CHATAK_SYSTEM_OVERVIEW_REPORTS_HEADER_MESSAGE, null,
              LocaleContextHolder.getLocale()), headerStyle));
      headercell.setColspan(Constants.SIX);
      headercell.setBorder(Rectangle.BOTTOM);
      headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
      headercell.setPaddingBottom(Constants.TEN);
      header.addCell(headercell);
      header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());

      header.writeSelectedRows(0, -1, document.leftMargin(),
          page.getHeight() - document.topMargin() + header.getTotalHeight(),
          writer.getDirectContent());

      document.add(table);

      document.close();
      response.setHeader("Expires", "0");
      response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
      response.setHeader("Pragma", "public");
      response.setContentType(Constants.CONTENT_TYPE_PDF);
      response.setContentLength(baos.size());
      ServletOutputStream os = response.getOutputStream();
      baos.writeTo(os);
      os.flush();
      os.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();

    } catch (DocumentException e) {
      logger.error("ERROR::method1:: ReportsFileExportUtil::downloadAccessLogsPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: ReportsFileExportUtil::downloadAccessLogsPdf", e);
    }
  }

  private static PdfPTable addTableCells(PdfPTable table, String reportData) {
    PdfPCell c1;
    if (reportData != null) {
      c1 = new PdfPCell(new Phrase(reportData));
      c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(c1);
    } else {
      table.addCell(" ");
    }
    return table;
  }

  private static String getDetails(String transaction) {
    return (transaction != null) ? transaction + "" : "";
  }
}
