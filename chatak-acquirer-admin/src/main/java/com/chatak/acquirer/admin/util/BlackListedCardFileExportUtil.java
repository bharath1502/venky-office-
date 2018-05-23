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

import com.chatak.pg.user.bean.BlackListedCardRequest;
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

public class BlackListedCardFileExportUtil {

  private BlackListedCardFileExportUtil() {
    super();
  }

private static Logger logger = Logger.getLogger(BlackListedCardFileExportUtil.class);

  public static void downloadBlackListedCardPdf(List<BlackListedCardRequest> blackListedCardData,
      HttpServletResponse response, String headerMessage, MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "blackListedCard_" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.TWO);
    try {
      table.setWidths(new int[] {Constants.FIFTEEN, Constants.NINE});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
    	logger.error("ERROR::method1:: BlackListedCardFileExportUtil ::downloadBlackListedCardPdf ", e1);
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

    PdfPCell c1 =
        new PdfPCell(new Phrase(messageSource.getMessage("reports.label.transactions.cardnumber",
            null, LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("reports.label.transactions.status", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (BlackListedCardRequest blcData : blackListedCardData) {
      int j = 1;
      table.setHeaderRows(j);
      
      if (blcData.getCardNumber() != null) {
          c1 = new PdfPCell(new Phrase(blcData.getCardNumber()+""));
          c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
          table.addCell(c1);
        } else {
          table.addCell(" ");
        }
      table.addCell((blcData.getStatusDisp() != null) ? blcData.getStatusDisp() + "" : "");
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
      logger.error("ERROR::method1:: BlackListedCardFileExportUtil::downloadBlackListedCardPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: BlackListedCardFileExportUtil::downloadBlackListedCardPdf", e);
    }
  }

  public static void downloadBlackListedCardXl(List<BlackListedCardRequest> blackListedCardData,
      HttpServletResponse response, String headerMessage, MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "blackListedCard_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.MAX_ENTITY_DISPLAY_SIZE);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(headerMessage, 0);

      s.addCell(new Label(0, 0, headerMessage, cellFormat));
      s.addCell(new Label(0, Constants.TWO, messageSource.getMessage("reports-file-exportutil-reportdate", null,
          LocaleContextHolder.getLocale()) + headerDate, cellFormat));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage("reports.label.transactions.cardnumber",
          null, LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("reports.label.transactions.status", null,
          LocaleContextHolder.getLocale()), cellFormat));
      
      WritableCellFormat writableCellFormat = new WritableCellFormat(cellFont);
      writableCellFormat.setAlignment(Alignment.RIGHT);

      int j = Constants.FIVE;
      for (BlackListedCardRequest blcData : blackListedCardData) {
        int i = 0;
        s.addCell(new Label(i++, j,
            "" + ((blcData.getCardNumber() != null) ? blcData.getCardNumber() : " "),writableCellFormat));
        s.addCell(new Label(i, j,
            "" + ((blcData.getStatusDisp() != null) ? blcData.getStatusDisp() : " ") + ""));
        j = j + 1;
      }
      w.write();
      w.close();
      response.getOutputStream().flush();
      response.getOutputStream().close();
    } catch (Exception e) {
      logger.error("ERROR:: BlackListedCardFileExportUtil::downloadBlackListedCardPdf ", e);
    }

  }

}
