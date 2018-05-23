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

import com.chatak.merchant.model.MerchantData;
import com.chatak.pg.user.bean.Transaction;
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
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class UserReportsFileExportsUtil {

	private UserReportsFileExportsUtil(){}
	private static Logger logger = Logger.getLogger(UserReportsFileExportsUtil.class);

	public static void downloadSpecificAllTranXl(List<MerchantData> list, HttpServletResponse response,
			String headerMessage, MessageSource messageSource) {

		response.setContentType(Constants.APPLICATION_VND_MS_EXCEL);
		Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
		String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
		String filename = "Reports" + dateString + ".xls";
		response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
		try {

			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet s = w.createSheet(headerMessage, 0);

			s.addCell(new Label(0, 0, headerMessage));
			s.addCell(new Label(0, Constants.TWO, messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_REPORT_DATE, null,
					LocaleContextHolder.getLocale()) + headerDate));
			s.addCell(new Label(0, Constants.FOUR,
					messageSource.getMessage("reportFileExportUtil.user.name", null, LocaleContextHolder.getLocale())));
			s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("merchantFileExportUtil.company.name", null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage("merchantFileExportUtil.first.name", null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage("merchantFileExportUtil.last.name", null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage("merchantFileExportUtil.Creation.date", null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.FIVE, Constants.FOUR,
					messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_STATUS, null, LocaleContextHolder.getLocale())));

			int j = Constants.FIVE;
			for (MerchantData merData : list) {
				int i = 0;
				s.addCell(new Label(i++, j, "" + ((merData.getUserName() != null) ? merData.getUserName() : " ") + ""));
				s.addCell(new Label(i++, j,
						"" + ((merData.getBusinessName() != null) ? merData.getBusinessName() : " ") + ""));
				s.addCell(
						new Label(i++, j, "" + ((merData.getFirstName() != null) ? merData.getFirstName() : " ") + ""));
				s.addCell(new Label(i++, j, "" + ((merData.getLastName() != null) ? merData.getLastName() : " ") + ""));
				s.addCell(new Label(i++, j,
						"" + ((merData.getCreatedDateString() != null) ? merData.getCreatedDateString() : " ") + ""));
				s.addCell(new Label(i, j, "" + ((merData.getStatus() != null) ? merData.getStatus() : " ") + ""));

				j = j + 1;
			}
			w.write();
			w.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("ERROR :: UserReportsFileExportsUtil :: downloadSpecificAllTranXl ", e);
		}

	}

	public static void downloadSpecificAllTransPdf(List<MerchantData> list, HttpServletResponse response,
			String headerMessage, MessageSource messageSource) {
		response.setContentType(Constants.CONTENT_TYPE_PDF);
		Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

		String filename = "Reports" + dateString + ".pdf";
		response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
		PdfPTable table = new PdfPTable(Constants.SIX);
		fetchException(table);

		BaseColor myColortext;
		Font myContentStyle = new Font();
		myContentStyle.setSize(Constants.TEN);
		myContentStyle.setStyle(Font.BOLD);
		myColortext = WebColors.getRGBColor(Constants.FFFFFF);
		myContentStyle.setColor(myColortext);

		Font reportStyle = new Font();
		reportStyle.setSize(Constants.TEN);
		reportStyle.setStyle(Font.BOLD);

		Calendar calendar = Calendar.getInstance();
		PdfPCell reportdate = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_REPORT_DATE, null, LocaleContextHolder.getLocale())
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
				messageSource.getMessage("merchantFileExportUtil.user.name", null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage("merchantFileExportUtil.company.name", null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage("merchantFileExportUtil.first.name", null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage("merchantFileExportUtil.last.name", null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage("merchantFileExportUtil.Creation.date", null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_STATUS, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		for (MerchantData merData : list) {
			int j = 1;
			table.setHeaderRows(j);
			table.addCell((merData.getUserName() != null) ? merData.getUserName() + "" : "");
			table.addCell((merData.getBusinessName() != null) ? merData.getBusinessName() + "" : "");
			table.addCell((merData.getFirstName() != null) ? merData.getFirstName() : "");
			table.addCell((merData.getLastName() != null) ? merData.getLastName() : "");
			table.addCell((merData.getCreatedDateString() != null) ? merData.getCreatedDateString() + "" : "");
			table.addCell((merData.getStatus() != null) ? merData.getStatus() + "" : "");
		}
		Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			TableHeader event = new TableHeader();
			writer.setPageEvent(event);
			event.setFooter(Properties.getProperty(Constants.CHATAK_FOOTER_COPYRIGHT_MESSAGE));

			document.open();
			Font headerStyle = new Font();
			headerStyle.setSize(Constants.EIGHTEEN);
			headerStyle.setStyle(Font.BOLD);

			Rectangle page = document.getPageSize();
			PdfPTable header = new PdfPTable(1);
			PdfPCell headercell = new PdfPCell(new Phrase(headerMessage, headerStyle));
			headercell.setColspan(Constants.SIX);
			headercell.setBorder(Rectangle.BOTTOM);
			headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headercell.setPaddingBottom(Constants.TEN);
			header.addCell(headercell);
			header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());

			header.writeSelectedRows(0, -1, document.leftMargin(),
					page.getHeight() - document.topMargin() + header.getTotalHeight(), writer.getDirectContent());

			document.add(table);

			document.close();
			response.setHeader(Constants.EXPIRES, "0");
			response.setHeader(Constants.CACHE_CONTROL, Constants.REVALIDATE);
			response.setHeader(Constants.PRAGMA, Constants.PUBLIC);
			response.setContentType(Constants.CONTENT_TYPE_PDF);
			response.setContentLength(baos.size());
			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (DocumentException e) {
			logger.error("ERROR :: method1 :: UserReportsFileExportsUtil :: downloadSpacificAllTransPdf", e);
		} catch (IOException e) {
			logger.error("ERROR :: method2 :: UserReportsFileExportsUtil :: downloadSpacificAllTransPdf", e);
		}
	}

	private static void fetchException(PdfPTable table) {
		try {
			table.setWidths(new int[] { Constants.FIVE, Constants.FIVE, Constants.FIVE, Constants.FIVE, Constants.FIVE, Constants.FIVE });
			table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
		} catch (DocumentException e1) {
			logger.error("ERROR :: UserReportsFileExportsUtil :: downloadSpecificAllTransPdf ", e1);
		}
	}

	public static void downloadSpecificUserTransactionsXl(List<Transaction> list, HttpServletResponse response,
			String headerMessage, MessageSource messageSource) {

		response.setContentType(Constants.APPLICATION_VND_MS_EXCEL);
		Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
		String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
		String filename = "User_Transactions" + dateString + ".xls";
		response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
		try {

			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet s = w.createSheet(headerMessage, 0);

			s.addCell(new Label(0, 0, headerMessage));
			s.addCell(new Label(0, Constants.TWO, messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_REPORT_DATE, null,
					LocaleContextHolder.getLocale()) + headerDate));
			s.addCell(new Label(0, Constants.FOUR,
					messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_DATE_TIME, null, LocaleContextHolder.getLocale())));
			s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_ACCOUNT_TRANSACTION_ID, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_TRANSACTION_DESCRIPTION, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_CARDTYPE, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_DEBIT, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_CREDIT, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage(Constants.FILE_EXPORT_UTIL_AVAILABLE_BALANCE, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.SEVEN, Constants.FOUR,
					messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_STATUS, null, LocaleContextHolder.getLocale())));

			int j = Constants.FIVE;
			for (Transaction userTrans : list) {
				j = getUserTxnDetails(s, j, userTrans);
			}
			w.write();
			w.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("ERROR :: UserReportsFileExportsUtil :: downloadSpecificUserTransactionsXl ", e);
		}

	}

	private static int getUserTxnDetails(WritableSheet s, int j, Transaction userTrans)
			throws WriteException, RowsExceededException {
		int i = 0;
		s.addCell(new Label(i++, j,
				"" + ((userTrans.getTransactionDate() != null) ? userTrans.getTransactionDate() : " ") + ""));
		s.addCell(new Label(i++, j,
				"" + ((userTrans.getTransactionId() != null) ? userTrans.getTransactionId() : " ") + ""));
		s.addCell(new Label(i++, j,
				"" + ((userTrans.getTxnDescription() != null) ? userTrans.getTxnDescription() : " ") + ""));
		s.addCell(new Label(i++, j,
				"" + ((userTrans.getTransaction_type() != null) ? userTrans.getTransaction_type() : " ") + ""));
		if (Constants.DEBIT.equalsIgnoreCase(userTrans.getTransaction_type())) {
			s.addCell(new Label(i++, j,
					"" + ((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() : " ")
							+ ""));
			s.addCell(new Label(i++, j, "" + ""));
		} else {
			s.addCell(new Label(i++, j, "" + ""));
			s.addCell(new Label(i++, j,
					"" + ((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() : " ")
							+ ""));
		}
		s.addCell(new Label(i++, j,
				"" + ((userTrans.getAvailableBalance() != null) ? userTrans.getAvailableBalance() : " ") + ""));
		s.addCell(new Label(i, j,
				"" + ((userTrans.getStatusMessage() != null) ? userTrans.getStatusMessage() : " ") + ""));

		j = j + 1;
		return j;
	}

	public static void downloadSpecificUserTransactionsPdf(List<Transaction> list, HttpServletResponse response,
			String headerMessage, MessageSource messageSource) {
		response.setContentType(Constants.CONTENT_TYPE_PDF);
		Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

		String filename = "User_Transactions" + dateString + ".pdf";
		response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
		PdfPTable table = new PdfPTable(Constants.EIGHT);
		getDocumentExceptionDetails(table);

		BaseColor myColortext;
		Font myContentStyle = new Font();
		myContentStyle.setSize(Constants.TEN);
		myContentStyle.setStyle(Font.BOLD);
		myColortext = WebColors.getRGBColor(Constants.FFFFFF);
		myContentStyle.setColor(myColortext);

		Font reportStyle = new Font();
		reportStyle.setSize(Constants.TEN);
		reportStyle.setStyle(Font.BOLD);

		Calendar calendar = Calendar.getInstance();
		PdfPCell reportdate = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_REPORT_DATE, null, LocaleContextHolder.getLocale())
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
				messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_DATE_TIME, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_ACCOUNT_TRANSACTION_ID, null,
				LocaleContextHolder.getLocale()), myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_TRANSACTION_DESCRIPTION, null,
				LocaleContextHolder.getLocale()), myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_CARDTYPE, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_DEBIT, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_CREDIT, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.FILE_EXPORT_UTIL_AVAILABLE_BALANCE, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_STATUS, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		for (Transaction userTrans : list) {
			int j = 1;
			table.setHeaderRows(j);
			table.addCell((userTrans.getTransactionDate() != null) ? userTrans.getTransactionDate() + "" : "");
			table.addCell((userTrans.getTransactionId() != null) ? userTrans.getTransactionId() + "" : "");
			table.addCell((userTrans.getTxnDescription() != null) ? userTrans.getTxnDescription() : "");
			table.addCell((userTrans.getTransaction_type() != null) ? userTrans.getTransaction_type() + "" : "");

			getUserTxnValueDetails(table, userTrans);
			table.addCell((userTrans.getAvailableBalance() != null) ? userTrans.getAvailableBalance() : "");
			table.addCell((userTrans.getStatusMessage() != null) ? userTrans.getStatusMessage() + "" : "");
		
		}
		Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			TableHeader event = new TableHeader();
			writer.setPageEvent(event);
			event.setFooter(Properties.getProperty(Constants.CHATAK_FOOTER_COPYRIGHT_MESSAGE));

			document.open();
			Font headerStyle = new Font();
			headerStyle.setSize(Constants.EIGHTEEN);
			headerStyle.setStyle(Font.BOLD);

			Rectangle page = document.getPageSize();
			PdfPTable header = new PdfPTable(1);
			PdfPCell headercell = new PdfPCell(new Phrase(headerMessage, headerStyle));
			headercell.setColspan(Constants.SIX);
			headercell.setBorder(Rectangle.BOTTOM);
			headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headercell.setPaddingBottom(Constants.TEN);
			header.addCell(headercell);
			header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());

			header.writeSelectedRows(0, -1, document.leftMargin(),
					page.getHeight() - document.topMargin() + header.getTotalHeight(), writer.getDirectContent());

			document.add(table);

			document.close();
			response.setHeader(Constants.EXPIRES, "0");
			response.setHeader(Constants.CACHE_CONTROL, Constants.REVALIDATE);
			response.setHeader(Constants.PRAGMA, Constants.PUBLIC);
			response.setContentType(Constants.CONTENT_TYPE_PDF);
			response.setContentLength(baos.size());
			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (DocumentException e) {
			logger.error("ERROR :: method1 :: UserReportsFileExportsUtil :: downloadSpecificUserTransactionsPdf", e);
		} catch (IOException e) {
			logger.error("ERROR :: method2 :: UserReportsFileExportsUtil :: downloadSpecificUserTransactionsPdf", e);
		}
	}

	private static void getDocumentExceptionDetails(PdfPTable table) {
		try {
			table.setWidths(new int[] { Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR });
			table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
		} catch (DocumentException e1) {
			logger.error("ERROR :: UserReportsFileExportsUtil :: downloadSpecificUserTransactionsPdf ", e1);
		}
	}

	private static void getUserTxnValueDetails(PdfPTable table, Transaction userTrans) {
		if (Constants.DEBIT.equalsIgnoreCase(userTrans.getTransaction_type())) {
			table.addCell((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() + "" : "");
			table.addCell("");
		} else {
			table.addCell("");
			table.addCell((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() + "" : "");
		}
	}

	public static void downloadSpecificUserStatementXl(List<Transaction> list, HttpServletResponse response,
			String headerMessage, MessageSource messageSource) {

		response.setContentType(Constants.APPLICATION_VND_MS_EXCEL);
		Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
		String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
		String filename = "Statement" + dateString + ".xls";
		response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
		try {

			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet s = w.createSheet(headerMessage, 0);

			s.addCell(new Label(0, 0, headerMessage));
			s.addCell(new Label(0, Constants.TWO, messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_REPORT_DATE, null,
					LocaleContextHolder.getLocale()) + headerDate));
			s.addCell(new Label(0, Constants.FOUR,
					messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_DATE_TIME, null, LocaleContextHolder.getLocale())));
			s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_ACCOUNT_TRANSACTION_ID, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_TRANSACTION_DESCRIPTION, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_CARDTYPE, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_DEBIT, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_CREDIT, null,
					LocaleContextHolder.getLocale())));
			s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage(Constants.FILE_EXPORT_UTIL_AVAILABLE_BALANCE, null,
					LocaleContextHolder.getLocale())));

			int j = Constants.FIVE;
			for (Transaction userTrans : list) {
				int i = 0;
				s.addCell(new Label(i++, j,
						"" + ((userTrans.getTransactionDate() != null) ? userTrans.getTransactionDate() : " ") + ""));
				s.addCell(new Label(i++, j,
						"" + ((userTrans.getTransactionId() != null) ? userTrans.getTransactionId() : " ") + ""));
				s.addCell(new Label(i++, j,
						"" + ((userTrans.getTxnDescription() != null) ? userTrans.getTxnDescription() : " ") + ""));
				s.addCell(new Label(i++, j,
						"" + ((userTrans.getTransaction_type() != null) ? userTrans.getTransaction_type() : " ") + ""));
				i = getUserTxnAmountDetails(s, j, userTrans, i);
				s.addCell(new Label(i, j,
						"" + ((userTrans.getAvailableBalance() != null) ? userTrans.getAvailableBalance() : " ") + ""));

				j = j + 1;
			}
			w.write();
			w.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("ERROR :: UserReportsFileExportsUtil :: downloadSpecificUserStatementXl ", e);
		}

	}

	private static int getUserTxnAmountDetails(WritableSheet s, int j, Transaction userTrans, int i)
			throws WriteException, RowsExceededException {
		if (Constants.DEBIT.equalsIgnoreCase(userTrans.getTransaction_type())) {
			s.addCell(new Label(i++, j, "" + ""));
			s.addCell(new Label(i++, j,
					"" + ((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() : " ")
							+ ""));

		} else {

			s.addCell(new Label(i++, j,
					"" + ((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() : " ")
							+ ""));
			s.addCell(new Label(i++, j, "" + ""));
		}
		return i;
	}

	public static void downloadSpecificUserStatementPdf(List<Transaction> list, HttpServletResponse response,
			String headerMessage, MessageSource messageSource) {
		response.setContentType(Constants.CONTENT_TYPE_PDF);
		Date date = new Date();
		String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

		String filename = "Statement" + dateString + ".pdf";
		response.setHeader(Constants.CONTENT_DISPOSITION, Constants.ATTACHMENT_FILENAME + filename);
		PdfPTable table = new PdfPTable(Constants.SEVEN);
		try {
			table.setWidths(new int[] { Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR, Constants.FOUR });
			table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
		} catch (DocumentException e1) {
			logger.error("ERROR :: UserReportsFileExportsUtil :: downloadSpecificUserStatementPdf ", e1);
		}

		BaseColor myColortext;
		Font myContentStyle = new Font();
		myContentStyle.setSize(Constants.TEN);
		myContentStyle.setStyle(Font.BOLD);
		myColortext = WebColors.getRGBColor(Constants.FFFFFF);
		myContentStyle.setColor(myColortext);

		Font reportStyle = new Font();
		reportStyle.setSize(Constants.TEN);
		reportStyle.setStyle(Font.BOLD);

		Calendar calendar = Calendar.getInstance();
		PdfPCell reportdate = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_REPORT_DATE, null, LocaleContextHolder.getLocale())
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
				messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_DATE_TIME, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_ACCOUNT_TRANSACTION_ID, null,
				LocaleContextHolder.getLocale()), myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(messageSource.getMessage(Constants.REPORT_FILE_EXPORT_UTIL_TRANSACTION_DESCRIPTION, null,
				LocaleContextHolder.getLocale()), myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.MERCHANT_FILE_EXPORT_UTIL_CARDTYPE, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_DEBIT, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.TRANSACTION_FILE_EXPORT_UTIL_CREDIT, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(
				messageSource.getMessage(Constants.FILE_EXPORT_UTIL_AVAILABLE_BALANCE, null, LocaleContextHolder.getLocale()),
				myContentStyle));
		c1.setPadding(Constants.FOUR);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.GRAY);
		table.addCell(c1);

		for (Transaction userTrans : list) {
			int j = 1;
			table.setHeaderRows(j);
			table.addCell((userTrans.getTransactionDate() != null) ? userTrans.getTransactionDate() + "" : "");
			table.addCell((userTrans.getTransactionId() != null) ? userTrans.getTransactionId() + "" : "");
			table.addCell((userTrans.getTxnDescription() != null) ? userTrans.getTxnDescription() : "");
			table.addCell((userTrans.getTransaction_type() != null) ? userTrans.getTransaction_type() + "" : "");

			getUserTxnDetails(table, userTrans);
			table.addCell((userTrans.getAvailableBalance() != null) ? userTrans.getAvailableBalance() : "");

		}
		Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);

			TableHeader event = new TableHeader();
			writer.setPageEvent(event);
			event.setFooter(Properties.getProperty(Constants.CHATAK_FOOTER_COPYRIGHT_MESSAGE));

			document.open();
			Font headerStyle = new Font();
			headerStyle.setSize(Constants.EIGHTEEN);
			headerStyle.setStyle(Font.BOLD);

			Rectangle page = document.getPageSize();
			PdfPTable header = new PdfPTable(1);
			PdfPCell headercell = new PdfPCell(new Phrase(headerMessage, headerStyle));
			headercell.setColspan(Constants.SIX);
			headercell.setBorder(Rectangle.BOTTOM);
			headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headercell.setPaddingBottom(Constants.TEN);
			header.addCell(headercell);
			header.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());

			header.writeSelectedRows(0, -1, document.leftMargin(),
					page.getHeight() - document.topMargin() + header.getTotalHeight(), writer.getDirectContent());

			document.add(table);

			document.close();
			response.setHeader(Constants.EXPIRES, "0");
			response.setHeader(Constants.CACHE_CONTROL, Constants.REVALIDATE);
			response.setHeader(Constants.PRAGMA, Constants.PUBLIC);
			response.setContentType(Constants.CONTENT_TYPE_PDF);
			response.setContentLength(baos.size());
			ServletOutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (DocumentException e) {
			logger.error("ERROR :: method1 :: UserReportsFileExportsUtil :: downloadSpecificUserStatementPdf", e);
		} catch (IOException e) {
			logger.error("ERROR :: method2 :: UserReportsFileExportsUtil :: downloadSpecificUserStatementPdf", e);
		}
	}

	private static void getUserTxnDetails(PdfPTable table, Transaction userTrans) {
		if (Constants.DEBIT.equalsIgnoreCase(userTrans.getTransaction_type())) {
			table.addCell("");
			table.addCell((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() + "" : "");
		} else {
			table.addCell((userTrans.getTransactionAmount() != null) ? userTrans.getTransactionAmount() + "" : "");
			table.addCell("");
		}
	}
}
