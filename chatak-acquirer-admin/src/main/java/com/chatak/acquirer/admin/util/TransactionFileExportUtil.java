package com.chatak.acquirer.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class TransactionFileExportUtil {

  TransactionFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(TransactionFileExportUtil.class);

  private static Object getRefTxnId(Long txnRefId) {
    return (txnRefId != null) ? (0L == txnRefId ? "N/A" : txnRefId) : "";
  }

  private static String getTransactionDetails(String transaction) {
    return (transaction != null) ? transaction + "" : "";
  }
  
  public static void downloadSettlementReportXl(List<Transaction> transactionList,
      HttpServletResponse response, MessageSource messageSource, GetTransactionsListRequest request) {
    logger.info("Exiting :: TransactionFileExportUtil :: downloadTransactionXl");

    String selectedFlag = response.getHeader("selectedFlag");
    if (null == selectedFlag) {
      selectedFlag = "";
    }

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Settlement_Report" + selectedFlag + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(messageSource.getMessage("reports.label.settlementReport",
          null, LocaleContextHolder.getLocale()), 0);

      s.addCell(new Label(0, 0, messageSource.getMessage("reports.label.settlementReport", null,
          LocaleContextHolder.getLocale()), cellFormat));
      
      s.addCell(new Label(0, Constants.TWO,
          messageSource.getMessage("transaction-file-exportutil-reportdate", null,
              LocaleContextHolder.getLocale()) + headerDate,
          cellFormat));
      
      if (isValidRequestData(request)) {
        s.addCell(new Label(0, Constants.THREE,
            messageSource.getMessage("transaction-file-exportutil-txndate", null,
                LocaleContextHolder.getLocale())
            + (request.getFrom_date() + Constants.TO + request.getTo_date()), cellFormat));
      }
      
      s.addCell(new Label(0, Constants.FIVE,
          messageSource.getMessage("reports.label.transactions.dateortime", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(1, Constants.FIVE,
          messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FIVE,
          messageSource.getMessage("transaction-file-exportutil-transactionId", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FIVE,
          messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantName", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FOUR, Constants.FIVE,
          messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantcode", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FIVE, Constants.FIVE,
          messageSource.getMessage("transaction-file-exportutil-terminalid", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SIX, Constants.FIVE,
          messageSource.getMessage("reports.label.balancereports.accountnumber", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SEVEN, Constants.FIVE,
          messageSource.getMessage("reports.label.transactions.description", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.EIGHT, Constants.FIVE,
          messageSource.getMessage("transaction-file-exportutil-procTxnId", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.NINE, Constants.FIVE, messageSource.getMessage(
          "transaction-report-batchID", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TEN, Constants.FIVE,
          messageSource.getMessage("reports.label.transactions.cardnumberField", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.ELEVEN, Constants.FIVE,
          messageSource.getMessage("currency-search-page.label.currency", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.TWELVE, Constants.FIVE, messageSource
          .getMessage("transactionFileExportUtil.admin.txn.amt", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.THIRTEEN, Constants.FIVE, messageSource
          .getMessage("transactions-search.label.merchantfee", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FOURTEEN, Constants.FIVE, messageSource
          .getMessage("transaction-file-exportutil-totaltxnamt", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FIFTEEN, Constants.FIVE, messageSource
          .getMessage("transaction-file-exportutil-txnType", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SIXTEEN, Constants.FIVE, messageSource
          .getMessage("transaction-file-exportutil-status", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.SEVENTEEN, Constants.FIVE, messageSource.getMessage("login.label.username", null,
          LocaleContextHolder.getLocale()), cellFormat));

      WritableCellFormat writableCellFormat = new WritableCellFormat(cellFont);
      writableCellFormat.setAlignment(Alignment.RIGHT);

      WritableFont writableFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      WritableCellFormat cellFormatRight = new WritableCellFormat(writableFont);
      cellFormatRight.setAlignment(Alignment.RIGHT);

      if (transactionList != null) {
        int j = Constants.SIX;
        for (Transaction transaction : transactionList) {
          if (!"".equals(transaction.getTimeZoneOffset()) && null != transaction.getTimeZoneOffset()) {
            transaction.setTimeZoneOffset("("+transaction.getTimeZoneOffset()+")");
          }
          int i = 0;
          s.addCell(
              new Label(i++, j, "" + (getTransactionDetails(transaction.getTransactionDate()))));
          s.addCell(
              new Label(i++, j, "" + (getTransactionDetails(transaction.getDeviceLocalTxnTime()
                  + getTransactionDetails(transaction.getTimeZoneOffset())))));
          s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getTransactionId())),
              cellFormatRight));
          s.addCell(new Label(i++, j,
              "" + (getTransactionDetails(transaction.getMerchantBusinessName()))));
          s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getMerchant_code())),
              cellFormatRight));
          s.addCell(new Label(i++, j, "" + getTerminalDetails(transaction), cellFormatRight));
          s.addCell(new Label(i++, j, "" + getAccountNumber(transaction), cellFormatRight));
          s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getTxnDescription()))));
          s.addCell(new Label(i++, j, "" + getRefTxnId(transaction.getRef_transaction_id()),
              cellFormatRight));
          s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getBatchId()))));
          s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getMaskCardNumber())),
              cellFormatRight));
          s.addCell(new Label(i++, j, "" + (getTransactionDetails(transaction.getLocalCurrency())),
              cellFormatRight));
          s.addCell(StringUtil.getAmountInFloat(i++, j, (transaction.getTxn_amount() !=null) ? transaction.getTxn_amount(): 0d));
          s.addCell(StringUtil.getAmountInFloat(i++, j, (transaction.getFee_amount() !=null) ? transaction.getFee_amount(): 0d));
          s.addCell(StringUtil.getAmountInFloat(i++, j, (transaction.getTxn_total_amount() !=null) ? transaction.getTxn_total_amount(): 0d));
           s.addCell(new Label(i++, j,
              "" + (getTransactionDetails(transaction.getTransaction_type()).toUpperCase())));
          s.addCell(new Label(i++, j, "" + getTransactionDetails(transaction.getMerchantSettlementStatus())));
          s.addCell(new Label(i, j, "" + getTransactionDetails(transaction.getUserName())));
          j = j + 1;
        }
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR :: TransactionFileExportUtil ::downloadTransactionXl ", e);
    }
  }

  private static Object getAccountNumber(Transaction transaction) {
    return (transaction.getAccountNumber() != null)
        ? transaction.getAccountNumber() : "";
  }

  private static String getTerminalDetails(Transaction transaction) {
    return (transaction.getTerminal_id() != null)
        ? CommonUtil.validateTerminalId(transaction.getTerminal_id().toString()) : "";
  }

  private static boolean isValidRequestData(GetTransactionsListRequest request) {
    return request != null && !request.getFrom_date().equals("") && !request.getTo_date().equals("");
  }
  
}
