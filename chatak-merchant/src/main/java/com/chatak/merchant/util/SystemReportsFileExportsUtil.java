package com.chatak.merchant.util;

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
import com.chatak.pg.util.Properties;
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

public class SystemReportsFileExportsUtil {
  
  private SystemReportsFileExportsUtil(){}
  
  private static Logger logger = Logger.getLogger(SystemReportsFileExportsUtil.class);

  public static void downloadRevenueGeneratedXl(List<ReportsDTO> list, HttpServletResponse response,
      MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Revenue" + dateString + ".xls";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(messageSource.getMessage(Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE,
              null, LocaleContextHolder.getLocale()), 0);

      s.addCell(
          new Label(0, 0, messageSource.getMessage(Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE,
              null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(0, Constants.TWO, messageSource.getMessage("merchantFileExportUtil.report.date", null,
          LocaleContextHolder.getLocale()) + headerDate, cellFormat));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage("dash-board.label.transactiontime", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.user.name", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.company", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.account.number",
          null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.transaction.id",
          null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(
          new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.transaction.description",
              null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.total.amount", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.currency", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.NINE, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.rapid.revenue", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TEN, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.merchant.revenue",
          null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.ELEVEN, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.amt.to.merchant.ac",
          null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(
          new Label(Constants.TWELVE, Constants.FOUR, messageSource.getMessage("reportFileExportUtil.amt.to.submerchant.ac",
              null, LocaleContextHolder.getLocale()), cellFormat));

      WritableFont writableFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      WritableCellFormat cellFormatRight = new WritableCellFormat(writableFont);
      cellFormatRight.setAlignment(Alignment.RIGHT);
      
      WritableCellFormat amountcellFormat = new WritableCellFormat(cellFont);
      amountcellFormat.setAlignment(Alignment.RIGHT);
      
      int j = Constants.FIVE;
      for (ReportsDTO userTrans : list) {
        if (!"".equals(userTrans.getTimeZoneOffset())
            && null != userTrans.getTimeZoneOffset()) {
          userTrans.setTimeZoneOffset("(" + userTrans.getTimeZoneOffset() + ")");
        }
        int i = 0;
        s.addCell(new Label(i++, j, getTxndata(userTrans.getDateTime())));
        s.addCell(new Label(i++, j, getTxndata(userTrans.getDeviceLocalTxnTime()+userTrans.getTimeZoneOffset())));
        s.addCell(new Label(i++, j, getTxndata(userTrans.getUserName())));
        s.addCell(new Label(i++, j,
        		getTxndata(userTrans.getCompanyName())));
        s.addCell(new Label(i++, j, "" + ((userTrans.getAccountNumber().toString() != null)
                ? userTrans.getAccountNumber().toString() : " ") + "", cellFormatRight));
        s.addCell(new Label(i++, j, ""
                + ((userTrans.getTransactionId() != null) ? userTrans.getTransactionId() : " ") + "", cellFormatRight));
        s.addCell(new Label(i++, j,
        		getTxndata(userTrans.getDescription())));
        s.addCell(StringUtil.getAmountInFloat(i++, j, (userTrans.getAmount() !=null) ? Double.parseDouble(userTrans.getAmount()): 0d));
        s.addCell(new Label(i++, j,
        		getTxndata(userTrans.getCurrency()), cellFormatRight));
        s.addCell(StringUtil.getAmountInFloat(i++, j, (userTrans.getChatakFee() !=null) ? Double.parseDouble(userTrans.getChatakFee()): 0d));
        s.addCell(StringUtil.getAmountInFloat(i++, j, (userTrans.getFee() !=null) ? Double.parseDouble(userTrans.getFee()): 0d));
        if (StringUtils.isValidString(userTrans.getParentMerchantId())) {
          s.addCell(new Label(i++, j, "" + ("NA") + ""));
          s.addCell(StringUtil.getAmountInFloat(i, j, (userTrans.getTotalTxnAmount() !=null) ? Double.parseDouble(userTrans.getTotalTxnAmount()): 0d));
        } else {
          s.addCell(StringUtil.getAmountInFloat(i++, j, (userTrans.getTotalTxnAmount() !=null) ? Double.parseDouble(userTrans.getTotalTxnAmount()): 0d));
          s.addCell(new Label(i, j, "" + ("NA") + ""));
        }

        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR :: SystemReportsFileExportsUtil :: downloadRevenueGeneratedXl ", e);
    }

  }

private static String getTxndata(String txnData) {
	return "" + ((txnData != null) ? txnData : " ") + "";
}

  public static void downloadRevenueGeneratedPdf(List<ReportsDTO> list,
      HttpServletResponse response, MessageSource messageSource) {
    response.setContentType(Constants.CONTENT_TYPE_PDF);
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Revenue" + dateString + ".pdf";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    PdfPTable table = new PdfPTable(Constants.THIRTEEN);
    try {
      table.setWidths(new int[] {Constants.FOUR, Constants.FOUR,Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("ERROR :: UserReportsFileExportsUtil :: downloadRevenueGeneratedPdf ", e1);
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
        .getMessage("merchantFileExportUtil.report.date", null, LocaleContextHolder.getLocale())
        + DateUtil.toDateStringFormat(new Timestamp(calendar.getTimeInMillis()),
            Constants.EXPORT_HEADER_DATE_FORMAT),
        reportStyle));
    reportdate.setColspan(Constants.THIRTEEN);
    reportdate.setPaddingBottom(Constants.EIGHT);
    reportdate.setPaddingTop(Constants.EIGHT);
    reportdate.setHorizontalAlignment(Element.ALIGN_RIGHT);
    reportdate.setBorder(Rectangle.NO_BORDER);
    table.addCell(reportdate);

    PdfPCell c1 = new PdfPCell(new Phrase(messageSource.getMessage("dash-board.label.transactiontime",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant.common-deviceLocalTxnTime", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.user.name", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.company", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.account.number",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.transaction.id",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reportFileExportUtil.transaction.description", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.total.amount", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.currency", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.rapid.revenue",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.merchant.revenue",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.amt.to.merchant.ac",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("reportFileExportUtil.amt.to.submerchant.ac", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

  
    for (ReportsDTO userTrans : list) {
      if (!"".equals(userTrans.getTimeZoneOffset()) && null != userTrans.getTimeZoneOffset()) {
        userTrans.setTimeZoneOffset("(" + userTrans.getTimeZoneOffset() + ")");
      }
      getUserTxnData(table, userTrans);
    }
    Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

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
      logger.error(
          "ERROR :: method1 :: SystemReportsFileExportsUtil :: downloadRevenueGeneratedPdf", e);
    } catch (IOException e) {
      logger.error(
          "ERROR :: method2 :: SystemReportsFileExportsUtil :: downloadRevenueGeneratedPdf", e);
    }
  }

private static void getUserTxnData(PdfPTable table, ReportsDTO userTrans) {
	PdfPCell c1;
	int j = 1;
      table.setHeaderRows(j);
      table.addCell(getTxndata(userTrans.getDateTime()));
      table.addCell(getTxndata(userTrans.getDeviceLocalTxnTime()+userTrans.getTimeZoneOffset()));
      table.addCell(getTxndata(userTrans.getUserName()));
      table.addCell(getTxndata(userTrans.getCompanyName()));
      if (userTrans.getAccountNumber() != null) {
        c1 = new PdfPCell(new Phrase(userTrans.getAccountNumber().toString()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      if (userTrans.getTransactionId() != null) {
        c1 = new PdfPCell(new Phrase(userTrans.getTransactionId()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      table.addCell(getTxndata(userTrans.getDescription()));
      if (userTrans.getAmount() != null) {
        c1 = new PdfPCell(new Phrase(userTrans.getAmount()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      if (userTrans.getCurrency() != null) {
        c1 = new PdfPCell(new Phrase(userTrans.getCurrency()));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
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
      
		validateUserTxnData(table, userTrans);
     
}

private static void validateUserTxnData(PdfPTable table, ReportsDTO userTrans) {
	PdfPCell c1;
	if (StringUtils.isValidString(userTrans.getParentMerchantId())) {
		table.addCell("NA");
		if (userTrans.getTotalTxnAmount() != null) {
			c1 = new PdfPCell(new Phrase(userTrans.getTotalTxnAmount()));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(c1);
		}
	} else {
		if (userTrans.getTotalTxnAmount() != null) {
			c1 = new PdfPCell(new Phrase(userTrans.getTotalTxnAmount()));
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(c1);
			table.addCell("NA");
		}
	}
}

  public static void downloadSystemOverviewXl(ReportsDTO list, HttpServletResponse response) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "System_Overview" + dateString + ".xls";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    try {

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(Properties.getProperty(Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE), 0);

      s.addCell(
          new Label(0, 0, Properties.getProperty(Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE)));
      s.addCell(new Label(0, Constants.TWO, "Report Date: " + headerDate));
      s.addCell(new Label(0, Constants.FOUR, "Account Types"));
      s.addCell(new Label(1, Constants.FOUR, "Active and Blocked Accounts"));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, "Currency"));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, "Total Balances"));

      int j = Constants.FIVE;
      int a = 0;
      s.addCell(new Label(a++, j, "" + ("Merchant") + ""));
      s.addCell(new Label(a++, j, "" + ((list.getMerchantAccountCount().toString() != null)
          ? list.getMerchantAccountCount().toString() : " ") + ""));
      s.addCell(
          new Label(a++, j, "" + ((list.getCurrency() != null) ? list.getCurrency() : " ") + ""));
      s.addCell(new Label(a, j,
          "" + ((list.getMerchantAccountBalance() != null) ? list.getMerchantAccountBalance() : " ")
              + ""));

      j = j + 1;

      int b = 0;
      s.addCell(new Label(b++, j, "" + ("Sub Merchant") + ""));
      s.addCell(new Label(b++, j, "" + ((list.getSubMerchantAccountCount().toString() != null)
          ? list.getSubMerchantAccountCount().toString() : " ") + ""));
      s.addCell(
          new Label(b++, j, "" + ((list.getCurrency() != null) ? list.getCurrency() : " ") + ""));
      s.addCell(new Label(b, j, "" + ((list.getSubMerchantAccountBalance() != null)
          ? list.getSubMerchantAccountBalance() : " ") + ""));

      j = j + 1;

      int c = 0;
      s.addCell(new Label(c++, j, "" + ("Chatak") + ""));
      s.addCell(new Label(c++, j, "" + ((list.getChatakAccountCount().toString() != null)
          ? list.getChatakAccountCount().toString() : " ") + ""));
      s.addCell(
          new Label(c++, j, "" + ((list.getCurrency() != null) ? list.getCurrency() : " ") + ""));
      s.addCell(new Label(c, j,
          "" + ((list.getChatakAccountBalance() != null) ? list.getChatakAccountBalance() : " ")
              + ""));

      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR :: SystemReportsFileExportsUtil :: downloadSystemOverviewXl ", e);
    }

  }

  public static void downloadSystemOverviewPdf(ReportsDTO list, HttpServletResponse response) {
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
      logger.error("ERROR :: UserReportsFileExportsUtil :: downloadSystemOverviewPdf ", e1);
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
    PdfPCell reportdate =
        new PdfPCell(
            new Phrase(
                "Report Date: " + DateUtil.toDateStringFormat(
                    new Timestamp(calendar.getTimeInMillis()), Constants.EXPORT_HEADER_DATE_FORMAT),
        reportStyle));
    reportdate.setColspan(Constants.TWELVE);
    reportdate.setPaddingBottom(Constants.EIGHT);
    reportdate.setPaddingTop(Constants.EIGHT);
    reportdate.setHorizontalAlignment(Element.ALIGN_RIGHT);
    reportdate.setBorder(Rectangle.NO_BORDER);
    table.addCell(reportdate);

    PdfPCell c1 = new PdfPCell(new Phrase("Account Types", myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase("Active and Blocked Accounts", myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase("Currency", myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase("Total Balances", myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    int j = 1;
    table.setHeaderRows(j);
    table.addCell("Merchant");
    table.addCell((list.getMerchantAccountCount().toString() != null)
        ? list.getMerchantAccountCount().toString() : "");
    table.addCell((list.getCurrency() != null) ? list.getCurrency() : "");
    table.addCell(
        (list.getMerchantAccountBalance() != null) ? list.getMerchantAccountBalance() : "");

    j = Constants.TWO;
    table.setHeaderRows(j);
    table.addCell("Sub Merchant");
    table.addCell((list.getSubMerchantAccountCount().toString() != null)
        ? list.getSubMerchantAccountCount().toString() + "" : "");
    table.addCell((list.getCurrency() != null) ? list.getCurrency() : "");
    table.addCell(
        (list.getSubMerchantAccountBalance() != null) ? list.getSubMerchantAccountBalance() : "");

    j = Constants.THREE;
    table.setHeaderRows(j);
    table.addCell("Chatak");
    table.addCell((list.getChatakAccountCount().toString() != null)
        ? list.getChatakAccountCount().toString() + "" : "");
    table.addCell((list.getCurrency() != null) ? list.getCurrency() : "");
    table.addCell((list.getChatakAccountBalance() != null) ? list.getChatakAccountBalance() : "");

    Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfWriter writer = PdfWriter.getInstance(document, baos);

      TableHeader event = new TableHeader();
      writer.setPageEvent(event);
      event.setFooter(Properties.getProperty("chatak.footer.copyright.message"));

      document.open();
      Font headerStyle = new Font();
      headerStyle.setSize(Constants.EIGHTEEN);
      headerStyle.setStyle(Font.BOLD);

      Rectangle page = document.getPageSize();
      PdfPTable header = new PdfPTable(1);
      PdfPCell headercell = new PdfPCell(new Phrase(
          Properties.getProperty(Constants.CHATAK_ADMIN_REVENUE_GENERATED_HEADER_MESSAGE), headerStyle));
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
      logger.error("ERROR :: method1 :: SystemReportsFileExportsUtil :: downloadSystemOverviewPdf",
          e);
    } catch (IOException e) {
      logger.error("ERROR :: method2 :: SystemReportsFileExportsUtil :: downloadSystemOverviewPdf",
          e);
    }
  }
}
