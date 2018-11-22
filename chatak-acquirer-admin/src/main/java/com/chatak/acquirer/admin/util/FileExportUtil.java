/**
 * 
 */
package com.chatak.acquirer.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jun 19, 2015 11:49:17 AM
 * @version 1.0
 */
public class FileExportUtil {
   FileExportUtil() {
		super();
	}

private static Logger logger = Logger.getLogger(FileExportUtil.class);

  public static void downloadEFTRequestsXlBatch(List<PGTransfers> transfersList,
      List<Long> transfersIds, Map<String, String> merchantNameMap, HttpServletResponse response,
      MessageSource messageSource) {

    logger.info("Entering :: FileExportUtil :: downloadEFTRequestsXlBatch method");
    response.setContentType("application/vnd.ms-excel");
    Date date = new Date();
    String dateString = new SimpleDateFormat(Constants.EXPORT_FILE_NAME_DATE_FORMAT).format(date);
    String filename = "Requests Batch Report" + dateString + ".xls";
    response.setHeader("Content-Disposition", "attachment;filename=" + filename);
   
    try (HSSFWorkbook wb = new HSSFWorkbook()) {
    	HSSFSheet sheet = wb.createSheet(messageSource.getMessage("chatak.report.batch.sheetName",
    	          null, LocaleContextHolder.getLocale()));

      int j = 0;
      Row dataRow = sheet.createRow(j);
      for (PGTransfers transfers : transfersList) {
        int i = 0;
        if (transfersIds.indexOf(transfers.getPgTransfersId()) != -1) {
          dataRow.createCell(i++).setCellValue( "" + ((transfers.getMerchantId() != null)
              ? merchantNameMap.get(transfers.getMerchantId() + "") : " ") + "");
          dataRow.createCell(i++).setCellValue("" + (getEFTDetails(transfers.getFromAccount())) + "");
          dataRow.createCell(i++).setCellValue("" + (getEFTDetails(transfers.getToAccount())) + "");

          dataRow.createCell(i++).setCellValue("" + ((transfers.getAmount() != null)
                  ? PGConstants.DOLLAR_SYMBOL + (CommonUtil.getDoubleAmount(transfers.getAmount()))
                  : " ") + "");
          dataRow.createCell(i++).setCellValue( "" + (getEFTDetails(transfers.getBankRoutingNumber())) + "");
          dataRow.createCell(i++).setCellValue("" + ((transfers.getCreatedDate() != null) ? (DateUtil
                  .toDateStringFormat(transfers.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT))
                  : " ") + "");
          dataRow.createCell(i++).setCellValue( "" + ("Checking Debit") + "");
          j = j + 1;
        }
      }
      wb.write(response.getOutputStream());
    } catch (Exception e) {
      logger.error("ERROR :: FileExportUtil ::downloadEFTRequestsXlBatch ", e);
    }
    logger.info("Exiting :: FileExportUtil :: downloadEFTRequestsXlBatch");
  }

  private static String getEFTDetails(String transfers) {
		return (transfers != null) ? transfers + "" : "";
	}
}
