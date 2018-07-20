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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
    try {
      WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
      WritableSheet s = w.createSheet(messageSource.getMessage("chatak.report.batch.sheetName",
          null, LocaleContextHolder.getLocale()), 0);

      int j = 0;
      for (PGTransfers transfers : transfersList) {
        int i = 0;
        if (transfersIds.indexOf(transfers.getPgTransfersId()) != -1) {
          s.addCell(new Label(i++, j, "" + ((transfers.getMerchantId() != null)
              ? merchantNameMap.get(transfers.getMerchantId() + "") : " ") + ""));
          s.addCell(new Label(i++, j,
              "" + (getEFTDetails(transfers.getFromAccount())) + ""));
          s.addCell(new Label(i++, j,
              "" + (getEFTDetails(transfers.getToAccount())) + ""));
          s.addCell(new Label(i++, j,
              "" + ((transfers.getAmount() != null)
                  ? PGConstants.DOLLAR_SYMBOL + (CommonUtil.getDoubleAmount(transfers.getAmount()))
                  : " ") + ""));
          s.addCell(new Label(i++, j, "" + (getEFTDetails(transfers.getBankRoutingNumber())) + ""));
          s.addCell(new Label(i++, j,
              "" + ((transfers.getCreatedDate() != null) ? (DateUtil
                  .toDateStringFormat(transfers.getCreatedDate(), DateUtil.VIEW_DATE_TIME_FORMAT))
                  : " ") + ""));
          s.addCell(new Label(i, j, "" + ("Checking Debit") + ""));
          j = j + 1;
        }
      }
      w.write();
      w.close();
    } catch (Exception e) {
      logger.error("ERROR :: FileExportUtil ::downloadEFTRequestsXlBatch ", e);
    }
    logger.info("Exiting :: FileExportUtil :: downloadEFTRequestsXlBatch");
  }

  private static String getEFTDetails(String transfers) {
		return (transfers != null) ? transfers + "" : "";
	}
}
