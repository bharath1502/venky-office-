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

import com.chatak.pg.model.GenericUserDTO;
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

public class UserListFileExportUtil {

  UserListFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(UserListFileExportUtil.class);

  public static void downloadUserXl(List<GenericUserDTO> userList, HttpServletResponse response,
      MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "User_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableCellFormat cellFormat = StringUtil.getExportUtilCellFormat();

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(messageSource.getMessage("chatak.header.merchant.messages",
          null, LocaleContextHolder.getLocale()), 0);

      s.addCell(new Label(0, 0, messageSource.getMessage("chatak.header.user.messages", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(0, Constants.TWO,
          messageSource.getMessage("userList-file-exportutil-reportdate", null,
              LocaleContextHolder.getLocale()) + headerDate,
          cellFormat));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage(
          "report-common-created-date", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage(
          "userList-file-exportutil-userType", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage(
          "userList-file-exportutil-roleName", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage(
          "merchant.label.merchantcode", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage(
          "merchant.label.merchantname", null, LocaleContextHolder.getLocale()), cellFormat));
      
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage(
          "merchant.label.submerchantcode", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage(
          "sub-merchant-account-search.label.sub-merchantname", null, LocaleContextHolder.getLocale()), cellFormat));
      
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource.getMessage(
          "reports-file-exportutil-userName", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource
          .getMessage("userList-file-exportutil-firstName", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.NINE, Constants.FOUR, messageSource.getMessage(
          "userList-file-exportutil-lastName", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TEN, Constants.FOUR, messageSource.getMessage(
          "userList-file-exportutil-emailId", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.ELEVEN, Constants.FOUR, messageSource.getMessage(
          "userList-file-exportutil-status", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TWELVE, Constants.FOUR, messageSource.getMessage(
          "report-common-suspended-date", null, LocaleContextHolder.getLocale()), cellFormat));

      WritableFont writableFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      WritableCellFormat cellFormatRight = new WritableCellFormat(writableFont);
      cellFormatRight.setAlignment(Alignment.RIGHT);
      
      int j = Constants.FIVE;
      for (GenericUserDTO userData : userList) {
        String status = "";
        status = getStatus(userData);
        int i = 0;

        s.addCell(new Label(i++, j, "" + (getUserDetails(getTimeString(userData.getCreatedDate()))) + ""));

        s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getUserType())) + ""));

        s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getUserRoleName())) + ""));

        if (userData.getUserType().equals("Merchant")) {          
          s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getMerchantCode())) + "", cellFormatRight));
          s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getMerchantName())) + ""));
        } else {
          s.addCell(new Label(i++, j, "" + (getUserDetails("")) + ""));
          s.addCell(new Label(i++, j, "" + (getUserDetails("")) + ""));
        }
        
        if (userData.getUserType().equals("SubMerchant")) {          
          s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getMerchantCode())) + "", cellFormatRight));
          s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getMerchantName())) + ""));
        } else {
          s.addCell(new Label(i++, j, "" + (getUserDetails("")) + ""));
          s.addCell(new Label(i++, j, "" + (getUserDetails("")) + ""));
        }

        s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getUserName())) + ""));

        s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getFirstName())) + ""));

        s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getLastName())) + ""));

        s.addCell(new Label(i++, j, "" + (getUserDetails(userData.getEmail())) + ""));

        s.addCell(new Label(i++, j, "" + (getUserDetails(status)) + ""));

        if (userData.getStatus() == Constants.TWO) {
          s.addCell(new Label(i, j,
              "" + (getUserDetails(getTimeString(userData.getUpdatedDate()))) + ""));
        } else {
          s.addCell(new Label(i, j, "" + (getUserDetails("")) + ""));
        }

        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR :: UserListFileExportUtil :: downloadUserXl ", e);
    }

  }

  public static void downloadUserPdf(List<GenericUserDTO> userList, HttpServletResponse response,
      MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "User_" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.THIRTEEN);
    setTableWidth(table);

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
    reportdate.setColspan(Constants.THIRTEEN);
    reportdate.setPaddingBottom(Constants.EIGHT);
    reportdate.setPaddingTop(Constants.EIGHT);
    reportdate.setHorizontalAlignment(Element.ALIGN_RIGHT);
    reportdate.setBorder(Rectangle.NO_BORDER);
    table.addCell(reportdate);

    PdfPCell c1 = new PdfPCell(new Phrase(messageSource.getMessage("report-common-created-date", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("userList-file-exportutil-userType", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("userList-file-exportutil-roleName", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant.label.merchantcode", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant.label.merchantname", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant.label.submerchantcode", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("sub-merchant-account-search.label.sub-merchantname", null,
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

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("userList-file-exportutil-firstName",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("userList-file-exportutil-lastName", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("userList-file-exportutil-emailId", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("userList-file-exportutil-status", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("report-common-suspended-date", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (GenericUserDTO userData : userList) {
      String status = "";
      status = getStatus(userData);

      int j = 1;
      table.setHeaderRows(j);
      
      table.addCell(getUserDetails(getTimeString(userData.getCreatedDate())));

      table.addCell(getUserDetails(userData.getUserType()));

      table.addCell(getUserDetails(userData.getUserRoleName()));

      if (userData.getUserType().equals("Merchant")) {
        setMerchantCode(table, userData);
        table.addCell(getUserDetails(userData.getMerchantName()));
      } else {
        table.addCell(getUserDetails(""));
        table.addCell(getUserDetails(""));
      }
      if (userData.getUserType().equals("SubMerchant")) {
        setMerchantCode(table, userData);
        table.addCell(getUserDetails(userData.getMerchantName()));
      } else {
        table.addCell(getUserDetails(""));
        table.addCell(getUserDetails(""));
      }

      table.addCell(getUserDetails(userData.getUserName()));

      table.addCell(getUserDetails(userData.getFirstName()));

      table.addCell(getUserDetails(userData.getLastName()));

      table.addCell(getUserDetails(userData.getEmail()));

      table.addCell(getUserDetails(status));
      if (userData.getStatus() == Constants.TWO) {
        table.addCell(getUserDetails(getTimeString(userData.getUpdatedDate())));
      } else {
        table.addCell(getUserDetails(""));
      }
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
          new PdfPCell(new Phrase(messageSource.getMessage("chatak.header.user.messages", null,
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
      logger.error("ERROR :: UserListFileExportUtil ::downloadUserPdf", e);
    } catch (IOException e) {
      logger.error("ERROR :: UserListFileExportUtil ::downloadUserPdf IOException", e);
    }
  }

  private static void setMerchantCode(PdfPTable table, GenericUserDTO userData) {
    PdfPCell c1;
    if (userData.getMerchantCode() != null) {
      c1 = new PdfPCell(new Phrase(userData.getMerchantCode()));
      c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
      table.addCell(c1);
    } else {
      table.addCell(" ");
    }
  }

  private static String getUserDetails(String userData) {
    return (userData != null) ? userData + "" : "";
  }

  private static String getStatus(GenericUserDTO userData) {
    String status;
    if (userData.getStatus() == 0) {
      status = "Active";
    } else if (userData.getStatus() == Constants.ONE) {
      status = "Pending";
    } else if (userData.getStatus() == Constants.TWO) {
      status = "Suspended";
    } else if (userData.getStatus() == Constants.THREE) {
      status = "Deleted";
    } else {
      status = "Declined";
    }
    return status;
  }

  private static PdfPTable setTableWidth(PdfPTable table) {
    try {
      table.setWidths(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: UserListFileExportUtil :: downloadUserPdf", e1);
    }
    return table;
  }

  private static String getTimeString(Timestamp time) {
    return (DateUtil.toDateStringFormat(time, DateUtil.VIEW_DATE_TIME_FORMAT));
  }
}
