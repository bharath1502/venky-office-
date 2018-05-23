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

import com.chatak.pg.model.AccountBalanceReportDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;
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
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class EftBalanceReportsFileExportUtil {
  private EftBalanceReportsFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(EftBalanceReportsFileExportUtil.class);

  public static void downloadGlobalBalanceReportXl(List<AccountBalanceReportDTO> balanceReportList,
      HttpServletResponse response,MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Balance_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(Properties.getProperty("chatak.header.global.bal.reports.messages"), 0);

      s.addCell(
          new Label(0, 0, Properties.getProperty("chatak.header.global.bal.reports.messages")));
      s.addCell(
          new Label(0, Constants.TWO, messageSource.getMessage("merchantFileExportUtil.report.date",
              null, LocaleContextHolder.getLocale()) + headerDate));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage("reports.label.username",
          null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(1, Constants.FOUR, messageSource
          .getMessage("reportseft.label.companyname", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage(
          "merchantFileExportUtil.Account.Creation.Date", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource
          .getMessage("fileExportUtil.account.number", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource
          .getMessage("reportFileExportUtil.account.type", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource
          .getMessage("fileExportUtil.currency", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource
          .getMessage("fileExportUtil.available.balance", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource
          .getMessage("fileExportUtil.current.balance", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource
          .getMessage("fileExportUtil.status", null, LocaleContextHolder.getLocale())));

      int j = Constants.FIVE;
      for (AccountBalanceReportDTO accData : balanceReportList) {
        int i = 0;
        s.addCell(new Label(i++, j,
            "" + (getAccountDetails(accData.getUserName())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getAccountDetails(accData.getBusinessName())) + ""));
        s.addCell(new Label(i++, j, ""
            + ((accData.getAccCreationDate() != null) ? accData.getAccCreationDate() : " ") + ""));
        s.addCell(new Label(i++, j,
            "" + ((accData.getAccountNumber() != null) ? accData.getAccountNumber() : " ") + ""));
        s.addCell(new Label(i++, j,
            "" + (getAccountDetails(accData.getAccountType())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getAccountDetails(accData.getCurrency())) + ""));
        s.addCell(new Label(i++, j,
            "" + ((accData.getAvailableBalance() != null) ? accData.getAvailableBalance() : " ")
                + ""));
        s.addCell(new Label(i++, j,
            "" + ((accData.getCurrentBalance() != null) ? accData.getCurrentBalance() : " ") + ""));
        s.addCell(new Label(i, j,
            "" + (getAccountDetails(accData.getStatus())) + ""));

        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR :: EftBalanceReportsFileExportUtil :: downloadGlobalBalanceReportXl ", e);
    }

  }

  public static void downloadGlobalBalanceReportPdf(List<AccountBalanceReportDTO> balanceReportList,
      HttpServletResponse response,MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Balance_" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.NINE);
    try {
      table.setWidths(new int[] {Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("ERROR :: UserReportsFileExportsUtil :: downloadGlobalBalanceReportPdf ", e1);
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

    PdfPCell c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports.label.username",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportseft.label.companyname",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchantFileExportUtil.Account.Creation.Date",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fileExportUtil.account.number",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reportFileExportUtil.account.type",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fileExportUtil.currency",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fileExportUtil.available.balance",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fileExportUtil.current.balance",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fileExportUtil.status",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (AccountBalanceReportDTO accData : balanceReportList) {
      int j = 1;
      table.setHeaderRows(j);
      table.addCell(getAccountDetails(accData.getUserName()));
      table.addCell(getAccountDetails(accData.getBusinessName()));
      table.addCell((accData.getAccCreationDate() != null) ? accData.getAccCreationDate() : "");
      table.addCell((accData.getAccountNumber().toString() != null)
          ? accData.getAccountNumber().toString() : "");
      table.addCell(getAccountDetails(accData.getAccountType()));
      table.addCell(getAccountDetails(accData.getCurrency()));
      table.addCell((accData.getAvailableBalance() != null) ? accData.getAvailableBalance() : "");
      table.addCell((accData.getCurrentBalance() != null) ? accData.getCurrentBalance() + "" : "");
      table.addCell(getAccountDetails(accData.getStatus()));
    }
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
          Properties.getProperty("chatak.header.global.bal.reports.messages"), headerStyle));
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
      response.setContentType("application/pdf");
      response.setContentLength(baos.size());
      ServletOutputStream os = response.getOutputStream();
      baos.writeTo(os);
      os.flush();
      os.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();

    } catch (DocumentException e) {
      logger.error(
          "ERROR :: method1 :: EftBalanceReportsFileExportUtil :: downloadGlobalBalanceReportPdf",
          e);
    } catch (IOException e) {
      logger.error(
          "ERROR :: method2 :: EftBalanceReportsFileExportUtil :: downloadGlobalBalanceReportPdf",
          e);
    }
  }

  private static String getAccountDetails(String accData) {
    return (accData != null) ? accData + "" : "";
  }

}
