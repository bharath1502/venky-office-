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

import com.chatak.pg.model.RecurringCustomerInfoDTO;
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

public class RecurringFileExportutil {

  private RecurringFileExportutil() {
    super();
  }
  private static Logger logger = Logger.getLogger(RecurringFileExportutil.class);

  public static void downloadRecurringPdf(List<RecurringCustomerInfoDTO> list,
      HttpServletResponse response, MessageSource messageSource) {
    logger.info("Entering :: TransactionFileExportUtil :: downloadTransactionPdf method");


    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Recurring" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.EIGHT);
    try {
      table.setWidths(new int[] {Constants.FOUR, Constants.FOUR, Constants.THREE, Constants.FOUR, Constants.FOUR, Constants.FIVE, Constants.FOUR, Constants.SIX});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("ERROR::method1:: RecurringFileExportutil::downloadRecurringPdf", e1);
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
        .getMessage("merchantFileExportUtil.report.date", null, LocaleContextHolder.getLocale())
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
        new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.customer.id",
            null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.first.name",
        null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.last.name", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.phone", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.emailid", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.city", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.company", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("recurringFileExportUtil.status", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);


    for (RecurringCustomerInfoDTO recurring : list) {
      int j = 1;
      table.setHeaderRows(j);
      table.addCell(fetchRecurringCustomDetails(recurring.getCustomerId()));
      table.addCell(fetchRecurringCustomDetails(recurring.getFirstName()));
      table.addCell(fetchRecurringCustomDetails(recurring.getLastName()));
      table.addCell(fetchRecurringCustomDetails(recurring.getMobileNumber()));
      table.addCell(fetchRecurringCustomDetails(recurring.getEmailId()));
      table.addCell(fetchRecurringCustomDetails(recurring.getCity()));
      table.addCell(fetchRecurringCustomDetails(recurring.getBusinessName()));
      table.addCell(fetchRecurringCustomDetails(recurring.getStatus()));

    }
    Document document = new Document(PageSize.A2, Constants.FIFTY, Constants.FIFTY, Constants.SEVENTY, Constants.SEVENTY);

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
          new Phrase(Properties.getProperty("chatak.header.recurring.messages"), headerStyle));
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
      logger.error("ERROR::method1:: TransactionFileExportUtil::downloadTransactionPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: TransactionFileExportUtil::downloadTransactionPdf", e);
    }

    logger.info("Exiting :: TransactionFileExportUtil :: downloadTransactionPdf");
  }

  private static String fetchRecurringCustomDetails(String recurring) {
    return (recurring != null) ? recurring + "" : "";
  }

  public static void downloadRecurringExcel(List<RecurringCustomerInfoDTO> list,
      HttpServletResponse response, MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "Merchant_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s =
          w.createSheet(Properties.getProperty("chatak.header.recurring.messages"), 0);

      s.addCell(new Label(0, 0, Properties.getProperty("chatak.header.recurring.messages")));
      s.addCell(new Label(0, Constants.TWO, messageSource.getMessage("merchantFileExportUtil.report.date", null,
          LocaleContextHolder.getLocale()) + headerDate));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.customer.id",
          null, LocaleContextHolder.getLocale())));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.first.name", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.last.name", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.THREE, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.phone", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FOUR, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.emailid", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.FIVE, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.city", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SIX, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.company", null,
          LocaleContextHolder.getLocale())));
      s.addCell(new Label(Constants.SEVEN, Constants.FOUR, messageSource.getMessage("recurringFileExportUtil.status", null,
          LocaleContextHolder.getLocale())));


      int j = Constants.FIVE;
      for (RecurringCustomerInfoDTO recurring : list) {
        int i = 0;
        s.addCell(new Label(i++, j,
            "" + fetchRecurringCustomDetails(recurring.getCustomerId())));
        s.addCell(new Label(i++, j,
            "" + fetchRecurringCustomDetails(recurring.getFirstName())));
        s.addCell(new Label(i++, j,
            "" + fetchRecurringCustomDetails(recurring.getLastName())));
        s.addCell(new Label(i++, j,
            "" + fetchRecurringCustomDetails(recurring.getMobileNumber())));
        s.addCell(new Label(i++, j,
            "" + fetchRecurringCustomDetails(recurring.getEmailId())));
        s.addCell(
            new Label(i++, j, "" + fetchRecurringCustomDetails(recurring.getCity())));
        s.addCell(new Label(i++, j,
            "" + fetchRecurringCustomDetails(recurring.getBusinessName())));
        s.addCell(new Label(i, j,
            "" + fetchRecurringCustomDetails(recurring.getStatus())));
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
}
