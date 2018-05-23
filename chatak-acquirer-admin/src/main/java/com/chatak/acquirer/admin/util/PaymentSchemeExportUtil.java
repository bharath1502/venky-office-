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

import com.chatak.pg.user.bean.PaymentSchemeRequest;
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

public class PaymentSchemeExportUtil {

  PaymentSchemeExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(PaymentSchemeExportUtil.class);

  public static void downloadPaymentSchemaPdf(List<PaymentSchemeRequest> paymentSchemeRequest,
      HttpServletResponse response, String headerMessage, MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "paymentScheme_" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.SEVEN);
    try {
      table.setWidths(new int[] {Constants.NINE, Constants.NINE, Constants.FIFTEEN, Constants.NINE, Constants.NINE, Constants.NINE, Constants.NINE});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("ERROR:: PaymentSchemeExportUtil::downloadPaymentSchemaPdf ", e1);
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

    PdfPCell c1 = new PdfPCell(new Phrase(messageSource.getMessage("payment.label.paymentname",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("payment.label.contactname", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("payment.label.contactemail", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("payment.label.contactphone", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("payment.label.rid", null, LocaleContextHolder.getLocale()),
        myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("payment.label.typeofcard", null, LocaleContextHolder.getLocale()),
        myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(
        messageSource.getMessage("common.label.status", null, LocaleContextHolder.getLocale()),
        myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);
    for (PaymentSchemeRequest psData : paymentSchemeRequest) {
      int j = 1;
      table.setHeaderRows(j);
      table.addCell(getDetails(psData.getPaymentSchemeName()));
      table.addCell(getDetails(psData.getContactName()));
      table.addCell(getDetails(psData.getContactEmail()));
      table.addCell(getDetails(psData.getContactPhone()));
      if (psData.getRid() != null) {
          c1 = new PdfPCell(new Phrase(psData.getRid()+""));
          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
          table.addCell(c1);
        } else {
          table.addCell(" ");
        }
      table.addCell(getDetails(psData.getTypeOfCard()));
      table.addCell(getDetails(psData.getStatus().toString()));
    }
    Document document = new Document(PageSize.A3, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

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
      PdfPCell headercell = new PdfPCell(new Phrase(headerMessage, headerStyle));
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
      logger.error("ERROR::method1:: PaymentSchemeExportUtil::downloadPaymentSchemaPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: PaymentSchemeExportUtil::downloadPaymentSchemaPdf", e);
    }
  }

  public static void downloadPaymentSchemeXl(List<PaymentSchemeRequest> paymentSchemeRequest,
      HttpServletResponse response, String headerMessage, MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "paymentScheme_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(headerMessage, 0);

      s.addCell(new Label(0, 0, headerMessage, cellFormat));
      s.addCell(new Label(0, Constants.TWO, messageSource.getMessage("reports-file-exportutil-reportdate", null,
          LocaleContextHolder.getLocale()) + headerDate, cellFormat));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage("payment.label.paymentname", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("payment.label.contactname", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage("payment.label.contactemail", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage("payment.label.contactphone", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR,
          messageSource.getMessage("payment.label.rid", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage("payment.label.typeofcard", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.SIX, Constants.FOUR,
          messageSource.getMessage("common.label.status", null, LocaleContextHolder.getLocale()),
          cellFormat));
       
      WritableCellFormat writableCellFormat = new WritableCellFormat(cellFont);
      writableCellFormat.setAlignment(Alignment.RIGHT);

      int j = Constants.FIVE;
      for (PaymentSchemeRequest psData : paymentSchemeRequest) {
        int i = 0;
        s.addCell(new Label(i++, j,
            "" + (getDetails(psData.getPaymentSchemeName()))
                + ""));
        s.addCell(new Label(i++, j,
            "" + (getDetails(psData.getContactName())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getDetails(psData.getContactEmail())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getDetails(psData.getContactPhone())) + ""));
        s.addCell(new Label(i++, j, "" + (getDetails(psData.getRid())),writableCellFormat));
        s.addCell(new Label(i++, j,
            "" + (getDetails(psData.getTypeOfCard())) + ""));
        s.addCell(
            new Label(i, j, "" + (getDetails(psData.getStatus().toString())) + ""));
        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR:: PaymentSchemeExportUtil::downloadPaymentSchemaPdf ", e);
    }
  }

  private static String getDetails(String psData) {
    return (psData != null) ? psData + "" : "";
  }
}
