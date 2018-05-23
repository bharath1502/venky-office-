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

public class ReportsFileExportsUtil {

  ReportsFileExportsUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(ReportsFileExportsUtil.class);

  public static void downloadReportsPdf(List<ReportsDTO> txnList, HttpServletResponse response,
      String headerProperty, MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Reports_" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.ELEVEN);
    try {
      table.setWidths(new int[] {Constants.FOUR,Constants.FOUR, Constants.FOUR, Constants.THREE, Constants.THREE,
          Constants.THREE, Constants.THREE, Constants.SIX, Constants.THREE, Constants.TWO,
          Constants.TWO});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: ReportsFileExportsUtil :: downloadReportsPdf", e1);
    }

    BaseColor myColortext;
    Font myContentStyle = new Font();
    myContentStyle.setSize(Constants.ELEVEN);
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
        new PdfPCell(new Phrase(messageSource.getMessage("reports.label.transactions.dateortime", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
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

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-accountType",
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

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("currency-search-page.label.currencycode",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-credit", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports-file-exportutil-debit", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (ReportsDTO executedTransactionsReport : txnList) {
      if (!"".equals(executedTransactionsReport.getTimeZoneOffset()) && null != executedTransactionsReport.getTimeZoneOffset()) {
        executedTransactionsReport.setTimeZoneOffset("("+executedTransactionsReport.getTimeZoneOffset()+")");
      }
      int j = 1;
      table.setHeaderRows(j);
      table.addCell(getDetails(executedTransactionsReport.getDateTime()));
      table.addCell(getDetails(executedTransactionsReport.getDeviceLocalTxnTime()+executedTransactionsReport.getTimeZoneOffset()));
      table.addCell(getDetails(executedTransactionsReport.getUserName()));
      table.addCell(getDetails(executedTransactionsReport.getCompanyName()));

      addTableCells(table, executedTransactionsReport.getAccountNumber().toString());
      table.addCell(getDetails(executedTransactionsReport.getAccountType()));
      addTableCells(table, executedTransactionsReport.getTransactionId());

      table.addCell(getDetails(executedTransactionsReport.getDescription()));

      addTableCells(table, executedTransactionsReport.getCurrency());

      if (executedTransactionsReport.getPaymentMethod() != null) {
        if ("debit".equalsIgnoreCase(executedTransactionsReport.getPaymentMethod())) {
          table.addCell("");
          c1 = new PdfPCell(new Phrase(executedTransactionsReport.getAmount()));
          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
          table.addCell(c1);
        } else {
          c1 = new PdfPCell(new Phrase(executedTransactionsReport.getAmount()));
          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
          table.addCell(c1);
        }
      } else {
        table.addCell("");
      }
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
      PdfPCell headercell = new PdfPCell(new Phrase(headerProperty, headerStyle));
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
      logger.error("ERROR::method1:: FeeProgramListFileExportUtil::downloadFeeProgramPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: FeeProgramListFileExportUtil::downloadFeeProgramPdf", e);
    }
  }

  public static void downloadReportsXl(List<ReportsDTO> txnList, HttpServletResponse response,
      String headerProperty, MessageSource messageSource) {
    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Reports_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.ELEVEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(headerProperty, 0);

      s.addCell(new Label(0, 0, headerProperty));
      s.addCell(
          new Label(0, Constants.TWO, messageSource.getMessage("reports-file-exportutil-reportdate",
              null, LocaleContextHolder.getLocale()) + headerDate, cellFormat));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage(
          "reports.label.transactions.dateortime", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage(
          "admin.common-deviceLocalTxnTime", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(2, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-userName", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-companyOrFullName", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-accountNumber", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource
          .getMessage("reports-file-exportutil-accountType", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SIX, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-transactionId", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR,
          messageSource.getMessage("reports-file-exportutil-transactionDescription", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR,
          messageSource.getMessage("currency-search-page.label.currencycode", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(
          Constants.NINE, Constants.FOUR, messageSource
              .getMessage("reports-file-exportutil-credit", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.TEN, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-debit", null, LocaleContextHolder.getLocale()), cellFormat));

      WritableCellFormat writableCellFormat = new WritableCellFormat(cellFont);
      writableCellFormat.setAlignment(Alignment.RIGHT);
      WritableCellFormat cellDataFormat = new WritableCellFormat(cellFont);
      cellDataFormat.setAlignment(Alignment.RIGHT);
      int j = Constants.FIVE;
      for (ReportsDTO executedTransactionsReport : txnList) {
        if (!"".equals(executedTransactionsReport.getTimeZoneOffset()) && null != executedTransactionsReport.getTimeZoneOffset()) {
          executedTransactionsReport.setTimeZoneOffset("("+executedTransactionsReport.getTimeZoneOffset()+")");
        }
        int i = 0;
        s.addCell(new Label(i++, j, "" + (getDetails(executedTransactionsReport.getDateTime()))));
        s.addCell(
            new Label(i++, j, "" + (getDetails(executedTransactionsReport.getDeviceLocalTxnTime()
                + getDetails(executedTransactionsReport.getTimeZoneOffset())))));
        s.addCell(new Label(i++, j, "" + (getDetails(executedTransactionsReport.getUserName()))));
        s.addCell(
            new Label(i++, j, "" + (getDetails(executedTransactionsReport.getCompanyName()))));
        s.addCell(new Label(i++, j,
            "" + (getDetails(executedTransactionsReport.getAccountNumber().toString())),
            writableCellFormat));
        s.addCell(
            new Label(i++, j, "" + (getDetails(executedTransactionsReport.getAccountType()))));
        s.addCell(new Label(i++, j,
            "" + (getDetails(executedTransactionsReport.getTransactionId())), writableCellFormat));
        s.addCell(
            new Label(i++, j, "" + (getDetails(executedTransactionsReport.getDescription()))));
        s.addCell(new Label(i++, j, "" + (getDetails(executedTransactionsReport.getCurrency())),
            writableCellFormat));
        if ("debit".equalsIgnoreCase(executedTransactionsReport.getPaymentMethod())) {
          s.addCell(new Label(i++, j, ""));
          s.addCell(StringUtil.getAmountInFloat(i, j, (executedTransactionsReport.getAmount() !=null) ? Double.parseDouble(executedTransactionsReport.getAmount()): 0d));
        } else {
          s.addCell(StringUtil.getAmountInFloat(i++, j, (executedTransactionsReport.getAmount() !=null) ? Double.parseDouble(executedTransactionsReport.getAmount()): 0d));
          s.addCell(new Label(i, j, ""));
        }
        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramListFileExportUtil::downloadFeeProgramXl ", e);
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
