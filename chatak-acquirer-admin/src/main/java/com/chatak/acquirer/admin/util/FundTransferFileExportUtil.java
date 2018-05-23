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

import com.chatak.pg.user.bean.Transaction;
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
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class FundTransferFileExportUtil {

   FundTransferFileExportUtil() {
		super();
	}

private static Logger logger = Logger.getLogger(FundTransferFileExportUtil.class);

  public static void downloadFundTransferPdf(List<Transaction> transactionList,
      HttpServletResponse response, MessageSource messageSource) {

    logger.info("Entering :: TransactionFileExportUtil :: downloadTransactionPdf method");
    String selectedFlag = response.getHeader("selectedFlag");
    if (null == selectedFlag) {
      selectedFlag = "";
    }

    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Transactions" + selectedFlag + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.TWELVE);
    try {
      table.setWidths(new int[] {Constants.FOUR, Constants.FOUR, Constants.THREE, Constants.FOUR, Constants.FOUR, Constants.FIVE, Constants.SIX, Constants.SIX, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
    	logger.error("ERROR:: FundTransferFileExportUtil ::downloadFundTransferPdf ", e1);
    }

    BaseColor myColortext;
    Font myContentStyle = new Font();
    myContentStyle.setSize(Constants.MAX_ENTITY_DISPLAY_SIZE);
    myContentStyle.setStyle(Font.BOLD);
    myColortext = WebColors.getRGBColor("#FFFFFF");
    myContentStyle.setColor(myColortext);

    Font reportStyle = new Font();
    reportStyle.setSize(Constants.MAX_ENTITY_DISPLAY_SIZE);
    reportStyle.setStyle(Font.BOLD);

    Calendar calendar = Calendar.getInstance();
    PdfPCell reportdate = new PdfPCell(new Phrase(messageSource
        .getMessage("userList-file-exportutil-reportdate", null, LocaleContextHolder.getLocale())
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
        new PdfPCell(new Phrase(messageSource.getMessage("comm.program.exportutil.date.time", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("transaction-file-exportutil-txnId", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("transaction-file-exportutil-name", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("transaction-file-exportutil-type", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("transaction-file-exportutil-accountNumber", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fundtransferfile.fee.description", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("fundtransferfile.amount", null, LocaleContextHolder.getLocale()),
        myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fundtransferfile.proc.txn.id", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fundtransferfile.card.number", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fundtransferfile.merchant.code", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fundtransferfile.txn.type", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("fundtransferfile.status", null, LocaleContextHolder.getLocale()),
        myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (Transaction transaction : transactionList) {
      int j = 1;
      table.setHeaderRows(j);
      table.addCell(
          getFundTransferData(transaction.getTransactionDate()));
      table.addCell(getFundTransferData(transaction.getTransactionId()));
      table.addCell(getFundTransferData(transaction.getMerchantName()));
      table.addCell(getFundTransferData(transaction.getMerchantType()));
      table.addCell(getFundTransferData(transaction.getAccountNumber().toString()));
      table.addCell(getFundTransferData(transaction.getTxnDescription()));
      table.addCell(getFundTransferData(transaction.getTransactionAmount()));
      table.addCell(getFundTransferData(transaction.getRef_transaction_id().toString()));
      table.addCell(getFundTransferData(transaction.getMaskCardNumber()));
      table.addCell(getFundTransferData(transaction.getMerchant_code()));
      table.addCell((transaction.getTransaction_type() != null)
          ? transaction.getTransaction_type() + "" : "");
      table.addCell((transaction.getStatusMessage() != null)
          ? transaction.getMerchantSettlementStatus() + "" : "");
    }
    Document document = new Document(PageSize.A2, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      PdfWriter writer = PdfWriter.getInstance(document, baos);

      document.open();
      Font headerStyle = new Font();
      headerStyle.setSize(Constants.EIGHTEEN);
      headerStyle.setStyle(Font.BOLD);

      Rectangle page = document.getPageSize();
      PdfPTable header = new PdfPTable(1);
      PdfPCell headercell =
          new PdfPCell(new Phrase(messageSource.getMessage(Constants.CHATAK_HEADER_TRANSACTION_MESSAGES,
              null, LocaleContextHolder.getLocale()), headerStyle));
      headercell.setColspan(Constants.SIX);
      headercell.setBorder(Rectangle.BOTTOM);
      headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
      headercell.setPaddingBottom(Constants.MAX_ENTITY_DISPLAY_SIZE);
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
      logger.error("ERROR::method1:: TransactionFileExportUtil::downloadTransactionPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: TransactionFileExportUtil::downloadTransactionPdf", e);
    }
    logger.info("Exiting :: TransactionFileExportUtil :: downloadTransactionPdf");
  }

  public static void downloadTransactionXl(List<Transaction> transactionList,
      HttpServletResponse response, MessageSource messageSource) {

    logger.info("Exiting :: TransactionFileExportUtil :: downloadTransactionPdf");
    String selectedFlag = response.getHeader("selectedFlag");
    if (null == selectedFlag) {
      selectedFlag = "";
    }

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Transactions" + selectedFlag + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(messageSource.getMessage(Constants.CHATAK_HEADER_TRANSACTION_MESSAGES,
          null, LocaleContextHolder.getLocale()), 0);

      s.addCell(new Label(0, 0, messageSource.getMessage(Constants.CHATAK_HEADER_TRANSACTION_MESSAGES, null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(0, Constants.TWO, messageSource.getMessage("userList-file-exportutil-reportdate",
          null, LocaleContextHolder.getLocale()) + headerDate));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage("comm.program.exportutil.date.time", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("transaction-file-exportutil-txnId", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage("transaction-file-exportutil-name", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage("transaction-file-exportutil-type", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage(
          "transaction-file-exportutil-accountNumber", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage("fundtransferfile.proc.txn.id", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage("fundtransferfile.fee.description", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource.getMessage("fundtransferfile.amount", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource.getMessage("fundtransferfile.card.number", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.NINE, Constants.FOUR, messageSource.getMessage("fundtransferfile.merchant.code", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.MAX_ENTITY_DISPLAY_SIZE, Constants.FOUR, messageSource.getMessage("fundtransferfile.txn.type", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.ELEVEN, Constants.FOUR, messageSource.getMessage("fundtransferfile.status", null,
          LocaleContextHolder.getLocale())));

      int j = Constants.FIVE;
      for (Transaction transaction : transactionList) {
        int i = 0;
        s.addCell(new Label(i++, j, "" + (getFundTransferData(transaction.getTransactionDate()))));
        s.addCell(new Label(i++, j, "" + (getFundTransferData(transaction.getTransactionId()))));
        s.addCell(new Label(i++, j, ""
            + (getFundTransferData(transaction.getMerchantName()))));
        s.addCell(new Label(i++, j, ""
            + (getFundTransferData(transaction.getMerchantType()))));
        s.addCell(new Label(i++, j, "" + (getFundTransferData(transaction.getAccountNumber().toString()))));

        s.addCell(new Label(i++, j, "" + (getFundTransferData(transaction.getRef_transaction_id().toString()))));
        s.addCell(new Label(i++, j, ""
            + (getFundTransferData(transaction.getTxnDescription()))));
        s.addCell(new Label(i++, j, "" + (getFundTransferData(transaction.getTransactionAmount()))));
        s.addCell(new Label(i++, j, ""
            + (getFundTransferData(transaction.getMaskCardNumber()))));
        s.addCell(new Label(i++, j, "" + (getFundTransferData(transaction.getMerchant_code()))));
        s.addCell(new Label(i++, j, "" + (getFundTransferData(transaction.getTransaction_type()))));

        s.addCell(new Label(i, j, "" + ((transaction.getStatusMessage() != null)
            ? transaction.getMerchantSettlementStatus() + "" : "")));
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
  
  private static String getFundTransferData(String transaction) {
		return (transaction != null) ? transaction + "" : "";
	}
}
