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

import com.chatak.pg.model.AccountTransactionDTO;
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

public class GlobalManualReportFileExportUtil {

  GlobalManualReportFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(GlobalManualReportFileExportUtil.class);

  public static void downloadManualTransactionsXl(List<AccountTransactionDTO> list,
      HttpServletResponse response, MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Manual_Transactions" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(messageSource.getMessage(Constants.CHATAK_HEADER_MANUAL_TRANSACTIONS_REPORTS, null,
              LocaleContextHolder.getLocale()), 0);

      s.addCell(
          new Label(0, 0, messageSource.getMessage(Constants.CHATAK_HEADER_MANUAL_TRANSACTIONS_REPORTS,
              null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(
          new Label(0, Constants.TWO, messageSource.getMessage("reports-file-exportutil-reportdate",
              null, LocaleContextHolder.getLocale()) + headerDate, cellFormat));

      s.addCell(new Label(0, Constants.FOUR,
          messageSource.getMessage("reports.label.transactions.dateortime",
              null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(1, Constants.FOUR,
          messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FOUR,
          messageSource.getMessage("show-account-transfer.label.description", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FOUR,
          messageSource.getMessage("accounts-manual-debit.label.merchantorsubmerchantcode", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(
          new Label(Constants.FOUR, Constants.FOUR,
              messageSource.getMessage(
                  "reports.label.balancereports.manualtransactions.transactionID", null,
                  LocaleContextHolder.getLocale()),
              cellFormat));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR,
          messageSource.getMessage("currency-search-page.label.currencycode", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SIX, Constants.FOUR,
          messageSource.getMessage("show-account-transfer.label.availablebalance", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-credit", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-debit", null, LocaleContextHolder.getLocale()), cellFormat));

      WritableCellFormat writableCellFormat = new WritableCellFormat(cellFont);
      writableCellFormat.setAlignment(Alignment.RIGHT);

      WritableCellFormat cellDataFormat = new WritableCellFormat(cellFont);
      cellDataFormat.setAlignment(Alignment.RIGHT);
      int j = Constants.FIVE;
      for (AccountTransactionDTO eftData : list) {
        if (!"".equals(eftData.getTimeZoneOffset()) && null != eftData.getTimeZoneOffset()) {
          eftData.setTimeZoneOffset("("+eftData.getTimeZoneOffset()+")");
        }
        int i = 0;
        s.addCell(new Label(i++, j, "" + (getDetails(eftData.getTransactionTime())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(eftData.getDeviceLocalTxnTime()+eftData.getTimeZoneOffset())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(eftData.getDescription())) + ""));

        s.addCell(
            new Label(i++, j, "" + (getDetails(eftData.getMerchantCode())), writableCellFormat));
        s.addCell(
            new Label(i++, j, "" + (getDetails(eftData.getTransactionId())), writableCellFormat));
        s.addCell(new Label(i++, j, "" + (getDetails(eftData.getCurrency())), writableCellFormat));
        s.addCell(StringUtil.getAmountInFloat(i++, j, (eftData.getCurrentBalance() !=null) ? Double.parseDouble(eftData.getCurrentBalance()): 0d));
        if ("MANUAL_DEBIT".equalsIgnoreCase(eftData.getTransactionCode())) {
          s.addCell(new Label(i++, j, "" + ""));
          s.addCell(StringUtil.getAmountInFloat(i, j, (eftData.getDebit() !=null) ? Double.parseDouble(eftData.getDebit()): 0d));
        } else {
          s.addCell(StringUtil.getAmountInFloat(i++, j, (eftData.getCredit() !=null) ? Double.parseDouble(eftData.getCredit()): 0d));
          s.addCell(new Label(i, j, "" + ""));
        }

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

  public static void downloadManualTransactionsPdf(List<AccountTransactionDTO> list,
      HttpServletResponse response, MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Manual_Transactions" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.NINE);
    try {
      table.setWidths(new int[] {Constants.FOUR,Constants.FOUR, Constants.FOUR, Constants.FIVE, Constants.FOUR,
          Constants.FOUR, Constants.THREE, Constants.THREE, Constants.THREE});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: ReportsFileExportsUtil :: downloadManualTransactionsPdf", e1);
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

    PdfPCell c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("reports.label.transactions.dateortime", null,
            LocaleContextHolder.getLocale()),
        myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("admin.common-deviceLocalTxnTime",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("show-account-transfer.label.description",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("accounts-manual-debit.label.merchantorsubmerchantcode",
            null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("reports.label.balancereports.manualtransactions.transactionID",
            null, LocaleContextHolder.getLocale()),
        myContentStyle));
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

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("show-account-transfer.label.availablebalance", null,
            LocaleContextHolder.getLocale()), myContentStyle));
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

    for (AccountTransactionDTO eftData : list) {
      if (!"".equals(eftData.getTimeZoneOffset()) && null != eftData.getTimeZoneOffset()) {
        eftData.setTimeZoneOffset("("+eftData.getTimeZoneOffset()+")");
      }
      int j = 1;
      table.setHeaderRows(j);
      table.addCell(getDetails(eftData.getTransactionTime()));
      table.addCell(getDetails(eftData.getDeviceLocalTxnTime()+eftData.getTimeZoneOffset()));
      table.addCell(getDetails(eftData.getDescription()));

      if (eftData.getMerchantCode() != null) {
        c1 = new PdfPCell(new Phrase(eftData.getMerchantCode() + ""));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }

      if (eftData.getTransactionId() != null) {
        c1 = new PdfPCell(new Phrase(eftData.getTransactionId() + ""));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }

      if (eftData.getCurrency() != null) {
        c1 = new PdfPCell(new Phrase(eftData.getCurrency() + ""));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      c1 = new PdfPCell(new Phrase(eftData.getCurrentBalance()));
      c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(c1);

      setCreditOrDebit(table, eftData);

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
          new Phrase(messageSource.getMessage(Constants.CHATAK_HEADER_MANUAL_TRANSACTIONS_REPORTS, null,
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
      response.setContentType("application/pdf");
      response.setContentLength(baos.size());
      ServletOutputStream os = response.getOutputStream();
      baos.writeTo(os);
      os.flush();
      os.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();

    } catch (DocumentException e) {
      logger.error("ERROR::method1:: ReportsFileExportUtil::downloadManualTransactionsPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: ReportsFileExportUtil::downloadManualTransactionsPdf", e);
    }
  }

  private static PdfPTable setCreditOrDebit(PdfPTable table, AccountTransactionDTO eftData) {
    PdfPCell c1;
    if (eftData.getTransactionCode() != null
        && "MANUAL_CREDIT".equalsIgnoreCase(eftData.getTransactionCode())) {
      c1 = new PdfPCell(new Phrase(eftData.getCredit()));
      c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(c1);

      table.addCell("");
    } else if (eftData.getTransactionCode() != null
        && "MANUAL_DEBIT".equalsIgnoreCase(eftData.getTransactionCode())) {
      table.addCell("");
      c1 = new PdfPCell(new Phrase(eftData.getDebit()));
      c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(c1);

    } else {
      table.addCell("");
      table.addCell("");
    }
    return table;
  }

  private static String getDetails(String transaction) {
    return (transaction != null) ? transaction + "" : "";
  }
}
