package com.chatak.acquirer.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

public class TransactionFileExportUtil {

  TransactionFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(TransactionFileExportUtil.class);

  private static Object getRefTxnId(Long txnRefId) {
    return (txnRefId != null) ? validateTxnRefId(txnRefId) : "";
  }

  private static Object validateTxnRefId(Long txnRefId) {
    return 0L == txnRefId ? "N/A" : txnRefId;
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

      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet(messageSource.getMessage("reports.label.settlementReport",
              null, LocaleContextHolder.getLocale()));
      Font hFont = wb.createFont();
      hFont.setFontHeightInPoints((short)10);
      hFont.setFontName("Arial");
      hFont.setBold(true);
      CellStyle headerStyle = wb.createCellStyle();
      headerStyle.setFont(hFont);
      
      CellUtil.createCell(sheet.createRow(0), 0,messageSource.getMessage("reports.label.settlementReport", null,
              LocaleContextHolder.getLocale()), headerStyle);
      
      CellUtil.createCell(sheet.createRow(Constants.TWO),0, messageSource.getMessage("transaction-file-exportutil-reportdate", null,
              LocaleContextHolder.getLocale()) + headerDate, headerStyle);
      
      
      if (isValidRequestData(request)) {
        CellUtil.createCell(sheet.createRow(Constants.THREE),0, messageSource.getMessage("transaction-file-exportutil-txndate", null,
                LocaleContextHolder.getLocale())
            + (request.getFrom_date() + Constants.TO + request.getTo_date()), headerStyle);
      }
      
      Row headerRow = sheet.createRow(Constants.FIVE);
      CellUtil.createCell(headerRow, 0, messageSource.getMessage("reports.label.transactions.dateortime", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow, 1,messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow, Constants.TWO,messageSource.getMessage("transaction-file-exportutil-transactionId", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow, Constants.THREE,messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantName", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.FOUR,messageSource.getMessage("reports.label.balancereports.merchantorsubmerchantcode", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.FIVE,messageSource.getMessage("transaction-file-exportutil-terminalid", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.SIX, messageSource.getMessage("reports.label.balancereports.accountnumber", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.SEVEN,messageSource.getMessage("reports.label.transactions.description", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.EIGHT,messageSource.getMessage("transaction-file-exportutil-procTxnId", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.NINE, messageSource.getMessage(
              "transaction-report-batchID", null, LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.TEN, messageSource.getMessage("reports.label.transactions.cardnumberField", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.ELEVEN, messageSource.getMessage("currency-search-page.label.currency", null,
              LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.TWELVE, messageSource
              .getMessage("transactionFileExportUtil.admin.txn.amt", null, LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.THIRTEEN, messageSource
              .getMessage("transactions-search.label.merchantfee", null, LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.FOURTEEN, messageSource
              .getMessage("transaction-file-exportutil-totaltxnamt", null, LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.FIFTEEN, messageSource
              .getMessage("transaction-file-exportutil-txnType", null, LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.SIXTEEN, messageSource
              .getMessage("transaction-file-exportutil-status", null, LocaleContextHolder.getLocale()), headerStyle);
      CellUtil.createCell(headerRow,Constants.SEVENTEEN, messageSource.getMessage("login.label.username", null,
              LocaleContextHolder.getLocale()), headerStyle);
      
      getTransactionList(transactionList, sheet, wb);
      
      wb.write(response.getOutputStream());
      wb.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR :: TransactionFileExportUtil ::downloadTransactionXl ", e);
    }
  }

/**
 * @param transactionList
 * @param s
 * @param cellFormatRight
 * @throws WriteException
 * @throws RowsExceededException
 */
	private static void getTransactionList(List<Transaction> transactionList, HSSFSheet sheet, HSSFWorkbook wb) {
		Font dFont = wb.createFont();
	    dFont.setBold(true);
	    CellStyle dataStyleRight = wb.createCellStyle();
	    dataStyleRight.setAlignment(HorizontalAlignment.RIGHT);
	    dataStyleRight.setFont(dFont);
		
	    CellStyle floatStyle = wb.createCellStyle();
		floatStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		if (transactionList != null) {
			int j = Constants.SIX;
			Row dataRow = sheet.createRow(j);
			for (Transaction transaction : transactionList) {
				validateTimeZone(transaction);
				int i = 0;
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getTransactionDate())));
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getDeviceLocalTxnTime()
						+ getTransactionDetails(transaction.getTimeZoneOffset()))));
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getTransactionId())),dataStyleRight);
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getMerchantBusinessName())));
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getMerchant_code())),dataStyleRight);
				CellUtil.createCell(dataRow, i++, "" + (getTerminalDetails(transaction)),dataStyleRight);
				CellUtil.createCell(dataRow, i++, "" + (getAccountNumber(transaction)),dataStyleRight);
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getTxnDescription())));
				CellUtil.createCell(dataRow, i++, "" + (getRefTxnId(transaction.getRef_transaction_id())),dataStyleRight);
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getBatchId())));
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getMaskCardNumber())),dataStyleRight);
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getLocalCurrency())),dataStyleRight);
				CellUtil.createCell(dataRow, i++, "" + (
						(transaction.getTxn_amount() != null) ? transaction.getTxn_amount() : 0d),floatStyle);
				CellUtil.createCell(dataRow, i++, "" + (
						(transaction.getFee_amount() != null) ? transaction.getFee_amount() : 0d),floatStyle);
				CellUtil.createCell(dataRow, i++, "" + (
						(transaction.getTxn_total_amount() != null) ? transaction.getTxn_total_amount() : 0d),floatStyle);
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getTransaction_type()).toUpperCase()));
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getMerchantSettlementStatus())));
				CellUtil.createCell(dataRow, i++, "" + (getTransactionDetails(transaction.getUserName())));
				j = j + 1;
			}
		}
	}

	/**
	 * @param transaction
	 */
	private static void validateTimeZone(Transaction transaction) {
		if (!"".equals(transaction.getTimeZoneOffset()) && null != transaction.getTimeZoneOffset()) {
			transaction.setTimeZoneOffset("(" + transaction.getTimeZoneOffset() + ")");
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
