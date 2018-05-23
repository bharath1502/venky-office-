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

import com.chatak.acquirer.admin.model.MerchantData;
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

public class MerchantFileExportUtil {

  MerchantFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(MerchantFileExportUtil.class);

  public static void downloadMerchantXl(List<MerchantData> merchantData,
      HttpServletResponse response, String headerMessage, MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Merchant_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(headerMessage, 0);

      s.addCell(new Label(0, 0, headerMessage, cellFormat));
      s.addCell(new Label(0, Constants.TWO,
          messageSource.getMessage("merchant-file-exportutil-reportdate", null,
              LocaleContextHolder.getLocale()) + headerDate,
          cellFormat));
      if (headerMessage.equals("Merchant List")) {
        s.addCell(new Label(0, Constants.FOUR,
            messageSource.getMessage("manage.label.sub-merchant.merchantcode", null,
                LocaleContextHolder.getLocale()),
            cellFormat));
      } else {
        s.addCell(new Label(0, Constants.FOUR,
            messageSource.getMessage("manage.label.sub-merchant.submerchantcode", null,
                LocaleContextHolder.getLocale()),
            cellFormat));
      }
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("merchant.label.merchantname",
          null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FOUR,
          messageSource.getMessage("currency-search-page.label.currencycode", null,
              LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource
          .getMessage("merchant-file-exportutil-firstName", null, LocaleContextHolder.getLocale()),
          cellFormat));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage(
          "merchant-file-exportutil-lastName", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage(
          "merchant-file-exportutil-email", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage(
          "merchant-file-exportutil-phone", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource.getMessage(
          "merchant-file-exportutil-city", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.EIGHT, Constants.FOUR, messageSource.getMessage(
          "merchant-file-exportutil-country", null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(
          Constants.NINE, Constants.FOUR, messageSource
              .getMessage("merchant-file-exportutil-status", null, LocaleContextHolder.getLocale()),
          cellFormat));

      WritableFont cellFontRight = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      WritableCellFormat writableCellFormat = new WritableCellFormat(cellFontRight);
      writableCellFormat.setAlignment(Alignment.RIGHT);

      int j = Constants.FIVE;
      for (MerchantData merData : merchantData) {
        int i = 0;
        s.addCell(new Label(i++, j,
            "" + getMerchantDetails(merData.getMerchantCode()),
            writableCellFormat));
        s.addCell(new Label(i++, j,
            "" + (getMerchantDetails(merData.getBusinessName())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getMerchantDetails(merData.getLocalCurrency())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getMerchantDetails(merData.getFirstName())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getMerchantDetails(merData.getLastName())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getMerchantDetails(merData.getEmailId())) + ""));
        s.addCell(
            new Label(i++, j, "" + (getMerchantDetails(merData.getPhone().toString())) + "", writableCellFormat));
        s.addCell(
            new Label(i++, j, "" + (getMerchantDetails(merData.getCity())) + ""));
        s.addCell(new Label(i++, j,
            "" + (getMerchantDetails(merData.getCountry())) + ""));
        s.addCell(
            new Label(i, j, "" + (getMerchantDetails(merData.getStatus())) + ""));

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

  public static void downloadMerchantPdf(List<MerchantData> merchantData,
      HttpServletResponse response, String headerMessage, MessageSource messageSource) {
    response.setContentType(Constants.CONTENT_TYPE_PDF);
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Merchant_" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.TEN);
    PdfPCell c1 = null;
    try {
      table.setWidths(new int[] {Constants.SIXTEEN, Constants.NINE, Constants.EIGHT, Constants.NINE,
          Constants.NINE, Constants.TEN, Constants.TWENTY, Constants.NINE, Constants.NINE,
          Constants.NINE});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("ERROR:: MerchantFileExportUtil ::downloadMerchantPdf ", e1);
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
        .getMessage("merchant-file-exportutil-reportdate", null, LocaleContextHolder.getLocale())
        + DateUtil.toDateStringFormat(new Timestamp(calendar.getTimeInMillis()),
            Constants.EXPORT_HEADER_DATE_FORMAT),
        reportStyle));
    reportdate.setColspan(Constants.TWELVE);
    reportdate.setPaddingBottom(Constants.EIGHT);
    reportdate.setPaddingTop(Constants.EIGHT);
    reportdate.setHorizontalAlignment(Element.ALIGN_RIGHT);
    reportdate.setBorder(Rectangle.NO_BORDER);
    table.addCell(reportdate);
    if (headerMessage.equals("Merchant List")) {
      c1 = new PdfPCell(
          new Phrase(messageSource.getMessage("manage.label.sub-merchant.merchantcode", null,
              LocaleContextHolder.getLocale()), myContentStyle));
    } else {
      c1 = new PdfPCell(
          new Phrase(messageSource.getMessage("manage.label.sub-merchant.submerchantcode", null,
              LocaleContextHolder.getLocale()), myContentStyle));
    }
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

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("currency-search-page.label.currencycode",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant-file-exportutil-firstName",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant-file-exportutil-lastName", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant-file-exportutil-phone", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant-file-exportutil-email", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant-file-exportutil-city", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant-file-exportutil-country", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("merchant-file-exportutil-status", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (MerchantData merData : merchantData) {
      int j = 1;
      table.setHeaderRows(j);
      if (merData.getMerchantCode() != null) {
        c1 = new PdfPCell(new Phrase(merData.getMerchantCode() + ""));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      table.addCell(getMerchantDetails(merData.getBusinessName()));
      table.addCell(getMerchantDetails(merData.getLocalCurrency()));
      table.addCell(getMerchantDetails(merData.getFirstName()));
      table.addCell(getMerchantDetails(merData.getLastName()));
      if (merData.getPhone() != null) {
        c1 = new PdfPCell(new Phrase(merData.getPhone() + ""));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(c1);
      } else {
        table.addCell(" ");
      }
      table.addCell(getMerchantDetails(merData.getEmailId()));
      table.addCell(getMerchantDetails(merData.getCity()));
      table.addCell(getMerchantDetails(merData.getCountry()));
      table.addCell(getMerchantDetails(merData.getStatus()));
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
      response.setContentType(Constants.CONTENT_TYPE_PDF);
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
  
  private static String getMerchantDetails(String merData) {
    return (merData != null) ? merData : " ";
  }
  
}
