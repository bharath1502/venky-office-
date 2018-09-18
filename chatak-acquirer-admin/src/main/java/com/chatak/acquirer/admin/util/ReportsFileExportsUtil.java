package com.chatak.acquirer.admin.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.util.Constants;

public class ReportsFileExportsUtil {

  ReportsFileExportsUtil() {
    super();
  }
  
  private static ExportDetails exportDetails = new ExportDetails();
	
	public static void exportDetailsDataForDownloadRoleReport( List<ReportsDTO> allTransDownloadList,
		      MessageSource messageSource,final String downloadType,HttpServletResponse response) throws IOException {
	      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
	    	  exportDetails.setExportType(ExportType.PDF);

	      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
	    	  exportDetails.setExportType(ExportType.XLS);
			  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
	      }
		    exportDetails.setReportName("Reports");
		    exportDetails.setHeaderMessageProperty("chatak.header.all.transactions.reports");
		    exportDetails.setHeaderList(getTransRoleHeaderList(messageSource));
		    exportDetails.setFileData(getRoleFileDataDetails(allTransDownloadList));
		    ExportUtil.exportData(exportDetails, response, messageSource);
		  }
	  
	public static List<String> getTransRoleHeaderList(MessageSource messageSource) {
		    String[] headerArr = {
		        messageSource.getMessage("reports.label.transactions.dateortime", null,
		            LocaleContextHolder.getLocale()),
		        messageSource.getMessage("admin.common-deviceLocalTxnTime", null,
		            LocaleContextHolder.getLocale()),
		        messageSource.getMessage("reports-file-exportutil-userName", null,
		            LocaleContextHolder.getLocale()),
		        messageSource.getMessage("reports-file-exportutil-companyOrFullName", null,
		            LocaleContextHolder.getLocale()),
		        messageSource.getMessage("reports-file-exportutil-accountNumber", null,
		            LocaleContextHolder.getLocale()),
		        messageSource.getMessage("reports-file-exportutil-accountType", null,
			        LocaleContextHolder.getLocale()),
			    messageSource.getMessage("reports-file-exportutil-transactionId", null,
				    LocaleContextHolder.getLocale()),
			    messageSource.getMessage("reports-file-exportutil-transactionDescription", null,
				    LocaleContextHolder.getLocale()),
				messageSource.getMessage("currency-search-page.label.currencycode", null,
				    LocaleContextHolder.getLocale()),
				messageSource.getMessage("reports-file-exportutil-credit", null,
			        LocaleContextHolder.getLocale()),
			    messageSource.getMessage("reports-file-exportutil-debit", null,
			        LocaleContextHolder.getLocale())};
		    return new ArrayList<String>(Arrays.asList(headerArr));
		  }
	  
	public static void setExportDetailsDataForPendingRoleReport(List<ReportsDTO> pendingTransDownloadList,
		       MessageSource messageSource,final String downloadType,HttpServletResponse response) throws IOException {
	      if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
	    	  exportDetails.setExportType(ExportType.PDF);

	      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
	    	  exportDetails.setExportType(ExportType.XLS);
			  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
	      }
		    exportDetails.setReportName("Reports_");
		    exportDetails.setHeaderMessageProperty("chatak.header.pending.transactions.reports");
		    exportDetails.setHeaderList(getTransRoleHeaderList(messageSource));
		    exportDetails.setFileData(getRoleFileDataDetails(pendingTransDownloadList));
		    ExportUtil.exportData(exportDetails, response, messageSource);
		  }
	  
	public static List<Object[]> getRoleFileDataDetails(List<ReportsDTO> transDownloadList) { 
		  List<Object[]> fileData = new ArrayList<Object[]>();
		  
		  for (ReportsDTO trans : transDownloadList) {  
			  Object[] rowData = new Object[Integer.parseInt("11")];   
			  rowData[0] = trans.getDateTime();    
			  rowData[1] = trans.getDeviceLocalTxnTime()+trans.getTimeZoneOffset(); 
			  rowData[Integer.parseInt("2")] = trans.getUserName(); 
			  rowData[Integer.parseInt("3")] = trans.getCompanyName();  
			  rowData[Integer.parseInt("4")] = trans.getAccountNumber().toString(); 
			  rowData[Integer.parseInt("5")] = trans.getAccountType();
			  rowData[Integer.parseInt("6")] = trans.getTransactionId();
			  rowData[Integer.parseInt("7")] = trans.getDescription();
			  rowData[Integer.parseInt("8")] = trans.getCurrency();
			 
			  if (trans.getPaymentMethod() != null) { 
				  if("credit".equalsIgnoreCase(trans.getPaymentMethod())) {
					  rowData[Integer.parseInt("9")] = trans.getAmount();  
				  } else {  
					  rowData[Integer.parseInt("10")] = trans.getAmount();
				  }
					  } 
			  fileData.add(rowData); 
		  }
		  return fileData; 
		  }
	
	public static void setExportDetailsDataForDownloadRoleReports(List<ReportsDTO> executedTransactionsReportList,
		    MessageSource messageSource,final String downloadType,HttpServletResponse response) throws IOException {
		 if (Constants.PDF_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
	    	  exportDetails.setExportType(ExportType.PDF);

	      } else if (Constants.XLS_FILE_FORMAT.equalsIgnoreCase(downloadType)) {
	    	  exportDetails.setExportType(ExportType.XLS);
			  exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
	      }
		    exportDetails.setReportName("Reports_");
		    exportDetails.setHeaderMessageProperty("chatak.header.all.executed.trans.reports.messages");
		    exportDetails.setHeaderList(getTransRoleHeaderList(messageSource));
		    exportDetails.setFileData(getRoleFileDataDetails(executedTransactionsReportList));
		    ExportUtil.exportData(exportDetails, response, messageSource);
		  }
  
}
