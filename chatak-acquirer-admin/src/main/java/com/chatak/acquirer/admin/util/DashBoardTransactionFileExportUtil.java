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

import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.LitleEFTDTO;
import com.chatak.pg.user.bean.Transaction;
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

public class DashBoardTransactionFileExportUtil {

  DashBoardTransactionFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(DashBoardTransactionFileExportUtil.class);

  public static void downloadDashBoardTransPdf(List<Transaction> transactionList,
      HttpServletResponse response, MessageSource messageSource) {
    logger.info("Entering :: TransactionFileExportUtil :: downloadDashBoardTransPdf method");
    String selectedFlag = response.getHeader("selectedFlag");
    if (null == selectedFlag) {
      selectedFlag = "";
    }
    response.setContentType(Constants.CONTENT_TYPE_PDF);
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Transactions" + selectedFlag + dateString + ".pdf";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    PdfPTable table = new PdfPTable(Constants.NINE);
    try {
      table.setWidths(new int[] {Constants.FOUR, Constants.FOUR, Constants.THREE, Constants.FOUR,
          Constants.FOUR, Constants.FIVE, Constants.SIX, Constants.SIX, Constants.FOUR});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: TransactionFileExportUtil :: downloadDashBoardTransPdf", e1);
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

    c1 = new PdfPCell(
        new Phrase(messageSource.getMessage("transaction-file-exportutil-merchantCode", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("transaction-file-exportutil-txnType",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("transaction-file-exportutil-amount",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("transaction-file-exportutil-description",
        null, LocaleContextHolder.getLocale()), myContentStyle));
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
      table.addCell(getTransactionDetails(transaction.getTransactionDate()));
      addTableCells(table, transaction.getTransactionId());
      table.addCell(getTransactionDetails(transaction.getTransactionId()));
      addTableCells(table, transaction.getRef_transaction_id().toString());
      addTableCells(table, transaction.getMaskCardNumber());
      addTableCells(table, transaction.getMerchant_code());
      table.addCell(getTransactionDetails(transaction.getTransaction_type()));
      addTableCells(table, transaction.getTransactionAmount());
      table.addCell(getTransactionDetails(transaction.getTxnDescription()));
      table.addCell(getTransactionDetails(transaction.getMerchantSettlementStatus()));

    }
    Document document = new Document(PageSize.A2, Constants.FIFTY, Constants.FIFTY,
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
      PdfPCell headercell =
          new PdfPCell(new Phrase(messageSource.getMessage(Constants.CHATAK_HEADER_TRANSACTION_MESSAGES,
              null, LocaleContextHolder.getLocale()), headerStyle));
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
      logger.error("ERROR::method1:: TransactionFileExportUtil::downloadDashBoardTransPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: TransactionFileExportUtil::downloadDashBoardTransPdf", e);
    }
    logger.info("Exiting :: TransactionFileExportUtil :: downloadDashBoardTransPdf");
  }

  public static void downloadDashBoardTransXl(List<Transaction> transactionList,
      HttpServletResponse response, MessageSource messageSource) {

    logger.info("Exiting :: TransactionFileExportUtil :: downloadDashBoardTransXl");
    String selectedFlag = response.getHeader("selectedFlag");
    if (null == selectedFlag) {
      selectedFlag = "";
    }

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Transactions" + selectedFlag + dateString + ".xls";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    try {

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(messageSource.getMessage(Constants.CHATAK_HEADER_TRANSACTION_MESSAGES,
          null, LocaleContextHolder.getLocale()), 0);

      s.addCell(new Label(0, 0, messageSource.getMessage(Constants.CHATAK_HEADER_TRANSACTION_MESSAGES, null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(0, Constants.TWO,
          messageSource.getMessage("userList-file-exportutil-reportdate", null,
              LocaleContextHolder.getLocale()) + headerDate));
      s.addCell(new Label(0, Constants.FOUR, messageSource
          .getMessage("comm.program.exportutil.date.time", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(1, Constants.FOUR, messageSource
          .getMessage("transaction-file-exportutil-txnId", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource
          .getMessage("fundtransferfile.proc.txn.id", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource
          .getMessage("fundtransferfile.card.number", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage(
          "transaction-file-exportutil-merchantCode", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage(
          "transaction-file-exportutil-txnType", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage(
          "transaction-file-exportutil-amount", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource.getMessage(
          "transaction-file-exportutil-description", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource
          .getMessage("fundtransferfile.status", null, LocaleContextHolder.getLocale())));

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
      cellFormat.setAlignment(Alignment.RIGHT);

      WritableFont writableFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      WritableCellFormat cellFormatRight = new WritableCellFormat(writableFont);
      cellFormatRight.setAlignment(Alignment.RIGHT);

      int j = Constants.FIVE;
      for (Transaction transaction : transactionList) {
        int i = 0;
        s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getTransactionDate()))));
        s.addCell(new Label(i++, j, ""
            + (getTransactionDetails(transaction.getTransactionId())),
            cellFormatRight));
        s.addCell(new Label(i++, j,
            "" + ((transaction.getRef_transaction_id() != null)
                ? (0L == transaction.getRef_transaction_id() ? "NA"
                    : transaction.getRef_transaction_id())
                : ""),
            cellFormatRight));
        s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getMaskCardNumber())), cellFormatRight));
        s.addCell(new Label(i++, j, ""
            + (getTransactionDetails(transaction.getMerchant_code())),
            cellFormatRight));
        s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getTransaction_type()))));
        s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getTransactionAmount())), cellFormat));
        s.addCell(new Label(i++, j, ""
            + (getTransactionDetails(transaction.getTxnDescription()))));
        s.addCell(new Label(i, j, "" + (getTransactionDetails(transaction.getMerchantSettlementStatus()))));
        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramListFileExportUtil::downloadDashBoardTransXl ", e);
    }
  }

  public static void downloadDashBoardEFTTransPdf(List<LitleEFTDTO> litleEFTRequestFromDashBoard,
      HttpServletResponse response, MessageSource messageSource) {
    logger.info("Entering :: TransactionFileExportUtil :: downloadDashBoardEFTTransPdf method");
    String selectedFlag = response.getHeader("selectedFlag");
    if (null == selectedFlag) {
      selectedFlag = "";
    }
    response.setContentType(Constants.CONTENT_TYPE_PDF);
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Transactions" + selectedFlag + dateString + ".pdf";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    PdfPTable table = new PdfPTable(Constants.FOUR);
    try {
      table.setWidths(new int[] {Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: TransactionFileExportUtil :: downloadDashBoardEFTTransPdf", e1);
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

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("fundtransferfile.proc.txn.id", null,
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
    c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("fundtransferfile.amount", null, LocaleContextHolder.getLocale()),
        myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (LitleEFTDTO transaction : litleEFTRequestFromDashBoard) {
      int j = 1;
      table.setHeaderRows(j);
      table.addCell((transaction.getDateTime() != null)
          ? DateUtil.toDateStringFormat(transaction.getDateTime(), DateUtil.VIEW_DATE_TIME_FORMAT) + ""
          : "");
      
      addTableCells(table, transaction.getTransactionId());
      addTableCells(table, transaction.getMerchantCode());
      addTableCells(table, transaction.getAmount().toString());
      
      table.addCell((transaction.getAmount() != null)
          ? (PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(transaction.getAmount())) + ""
          : "");

    }
    Document document = new Document(PageSize.A2, Constants.FIFTY, Constants.FIFTY,
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
          new Phrase(messageSource.getMessage("chatak.header.eftexecuted.transaction.messages",
              null, LocaleContextHolder.getLocale()), headerStyle));
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
      logger.error("ERROR::method1:: TransactionFileExportUtil::downloadDashBoardEFTTransPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: TransactionFileExportUtil::downloadDashBoardEFTTransPdf", e);
    }
    logger.info("Exiting :: TransactionFileExportUtil :: downloadDashBoardEFTTransPdf");
  }

  public static void downloadDashBoardEFTTransXl(List<LitleEFTDTO> litleEFTRequestFromDashBoard,
      HttpServletResponse response, MessageSource messageSource) {
    logger.info("Exiting :: TransactionFileExportUtil :: downloadDashBoardEFTTransXl");
    String selectedFlag = response.getHeader("selectedFlag");
    if (null == selectedFlag) {
      selectedFlag = "";
    }
    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Transactions" + selectedFlag + dateString + ".xls";
    response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
    try {

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(messageSource.getMessage("chatak.header.eftexecuted.transaction.messages",
              null, LocaleContextHolder.getLocale()), 0);

      s.addCell(
          new Label(0, 0, messageSource.getMessage("chatak.header.eftexecuted.transaction.messages",
              null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(0, Constants.TWO,
          messageSource.getMessage("transaction-file-exportutil-reportdate", null,
              LocaleContextHolder.getLocale()) + headerDate));
      s.addCell(new Label(0, Constants.FOUR, messageSource
          .getMessage("comm.program.exportutil.date.time", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(1, Constants.FOUR, messageSource
          .getMessage("transaction-file-exportutil-txnId", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource
          .getMessage("fundtransferfile.merchant.code", null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource
          .getMessage("fundtransferfile.amount", null, LocaleContextHolder.getLocale())));

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
      cellFormat.setAlignment(Alignment.RIGHT);

      WritableFont writableFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      WritableCellFormat cellFormatRight = new WritableCellFormat(writableFont);
      cellFormatRight.setAlignment(Alignment.RIGHT);

      int j = Constants.FIVE;
      for (LitleEFTDTO transaction : litleEFTRequestFromDashBoard) {
        int i = 0;
        s.addCell(new Label(i++, j,
            "" + ((transaction.getDateTime() != null) ? DateUtil
                .toDateStringFormat(transaction.getDateTime(), DateUtil.VIEW_DATE_TIME_FORMAT) + ""
                : "")));
        s.addCell(new Label(i++, j, ""
            + (getTransactionDetails(transaction.getTransactionId())),
            cellFormatRight));
        s.addCell(new Label(i++, j, ""
            + (getTransactionDetails(transaction.getMerchantCode())),
            cellFormatRight));
        s.addCell(new Label(i, j, "" + ((transaction.getAmount() != null)
            ? (PGConstants.DOLLAR_SYMBOL + StringUtils.amountToString(transaction.getAmount())) + ""
            : ""), cellFormat));
        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramListFileExportUtil::downloadDashBoardEFTTransXl ", e);
    }
  }

  private static String getTransactionDetails(String transaction) {
    return (transaction != null) ? transaction + "" : "";
  }

  private static PdfPTable addTableCells(PdfPTable table, String transaction) {
    PdfPCell c1;
    if (transaction != null) {
      c1 = new PdfPCell(new Phrase(transaction));
      c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(c1);
    } else {
      table.addCell(" ");
    }
    return table;
  }
}
