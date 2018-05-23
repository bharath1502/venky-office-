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

import com.chatak.pg.model.UserRolesDTO;
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
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class RoleListFileExportUtil {
  
  RoleListFileExportUtil() {
    super();
  }

  private static Logger logger = Logger.getLogger(RoleListFileExportUtil.class);

  public static void downloadRoleXl(List<UserRolesDTO> userRoleList, HttpServletResponse response,
      MessageSource messageSource) {

    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String headerDate = new SimpleDateFormat(Constants.EXPORT_HEADER_DATE_FORMAT).format(date);
    String filename = "ROle_" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    try {

      WritableFont cellFont = new WritableFont(WritableFont.ARIAL, Constants.TEN);
      cellFont.setBoldStyle(WritableFont.BOLD);
      WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(messageSource.getMessage("chatak.header.role.messages", null,
          LocaleContextHolder.getLocale()), 0);

      s.addCell(new Label(0, 0, messageSource.getMessage("chatak.header.role.messages", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(0, Constants.TWO, messageSource.getMessage("roleList-file-exportutil-reportdate",
          null, LocaleContextHolder.getLocale()) + headerDate, cellFormat));
      s.addCell(new Label(0, Constants.FOUR, messageSource.getMessage("roleList-file-exportutil-roleType", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(1, Constants.FOUR, messageSource.getMessage("roleList-file-exportutil-rolename", null,
          LocaleContextHolder.getLocale()), cellFormat));
      s.addCell(new Label(Constants.TWO, Constants.FOUR, messageSource.getMessage("roleList-file-exportutil-status", null,
          LocaleContextHolder.getLocale()), cellFormat));



      int j = Constants.FIVE;
      for (UserRolesDTO roleData : userRoleList) {
        int i = 0;
        s.addCell(new Label(i++, j,
            "" + ((roleData.getRoleType() != null) ? roleData.getRoleType() : " ") + ""));
        s.addCell(new Label(i++, j,
            "" + ((roleData.getRoleName() != null) ? roleData.getRoleName() : " ") + ""));
        s.addCell(new Label(i, j,
            "" + ((roleData.getStatus().intValue() == 0) ? "Active" : "Inactive") + ""));
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

  public static void downloadRolePdf(List<UserRolesDTO> roleList, HttpServletResponse response,
      MessageSource messageSource) {
    response.setContentType("application/pdf");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);

    String filename = "Role_" + dateString + ".pdf";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
    PdfPTable table = new PdfPTable(Constants.THREE);
    try {
      table.setWidths(new int[] {1, 1, 1});
      table.setWidthPercentage(Constants.MAX_PAGE_SIZE);
    } catch (DocumentException e1) {
      logger.error("Error :: RoleListFileExportUtil :: downloadRolePdf", e1);
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
        .getMessage("roleList-file-exportutil-reportdate", null, LocaleContextHolder.getLocale())
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
        new PdfPCell(new Phrase(messageSource.getMessage("roleList-file-exportutil-roleType", null,
            LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("roleList-file-exportutil-rolename", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase(messageSource.getMessage("roleList-file-exportutil-status", null,
        LocaleContextHolder.getLocale()), myContentStyle));
    c1.setPadding(Constants.FOUR);
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    c1.setBackgroundColor(BaseColor.GRAY);
    table.addCell(c1);

    for (UserRolesDTO roleData : roleList) {
      int j = 1;
      table.setHeaderRows(j);
      
      table.addCell((roleData.getRoleType() != null) ? roleData.getRoleType() + "" : "");

      table.addCell((roleData.getRoleName() != null) ? roleData.getRoleName() + "" : "");

      table.addCell((roleData.getStatus().intValue() == 0) ? "Active" : "Inactive");

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
      PdfPCell headercell =
          new PdfPCell(new Phrase(messageSource.getMessage("chatak.header.role.messages", null,
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
      logger.error("ERROR::method1:: FeeProgramListFileExportUtil::downloadFeeProgramPdf", e);
    } catch (IOException e) {
      logger.error("ERROR::method2:: FeeProgramListFileExportUtil::downloadFeeProgramPdf", e);
    }
  }

}

