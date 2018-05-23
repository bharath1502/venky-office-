package com.chatak.acquirer.admin.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BatchSchedularService;
import com.chatak.pg.acq.dao.BatchSchedularDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.bean.Response;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.StringUtils;

@Service("batchSchedularService")
public class BatchSchedularServiceImpl implements BatchSchedularService {

  private static Logger logger = Logger.getLogger(BatchSchedularServiceImpl.class);

  @Autowired
  BatchSchedularDao batchSchedularDao;

  @Autowired
  private CurrencyConfigDao currencyConfigDao;

  @Override
  public GetDailyFundingReportResponse searchDailyFundingReportDetails(
      GetDailyFundingReportRequest getDailyFundingReportRequest) throws ChatakAdminException {
    logger.info("Entering :: BatchSchedularServiceImpl :: searchDailyFundingReportDetails");
    GetDailyFundingReportResponse response = new GetDailyFundingReportResponse();
    try {
      List<DailyFundingReport> dailyFundingReports =
          batchSchedularDao.searchDailyFundingReportDetails(getDailyFundingReportRequest);

      if (dailyFundingReports != null) {
        response.setDailyFundingReport(dailyFundingReports);
        response.setTotalResultCount(getDailyFundingReportRequest.getNoOfRecords());
        response.setErrorMessage(Constants.SUCESS);
        response.setErrorCode(Constants.SUCCESS_CODE);
      } else {
        response.setTotalResultCount(getDailyFundingReportRequest.getNoOfRecords());
        response.setErrorMessage(Constants.ERROR);
        response.setErrorCode(Constants.ERROR_CODE);
      }

      logger.info("Exiting :: BatchSchedularServiceImpl :: searchDailyFundingReportDetails");
      return response;
    } catch (Exception exp) {
      logger.error("Error :: BatchSchedularServiceImpl :: searchDailyFundingReportDetails", exp);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  public void fundingReportSchedular() {
    logger.info("Entering :: BatchSchedularServiceImpl :: fundingReportSchedular ");
    batchSchedularDao.insertFundingReport();
    logger.info("Exiting :: BatchSchedularServiceImpl :: fundingReportSchedular ");
  }
  
  public Response manualFundingReport() {
    logger.info("Entering :: BatchSchedularServiceImpl :: manualFundingReport ");
    return batchSchedularDao.showManualFundingReport();
  }

  @Override
  public GetTransactionsListResponse getMerchantHistoryOnId(
      GetTransactionsListRequest transactionRequest) {
    logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
    List<Transaction> transactions = batchSchedularDao.getTxnHistoryOnId(transactionRequest);
    if (transactions != null) {
      response.setTransactionList(transactions);
      response.setTotalResultCount(transactionRequest.getNoOfRecords());
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setErrorMessage(Constants.SUCESS);
    } else {
      response.setTransactionList(transactions);
      response.setTotalResultCount(transactionRequest.getNoOfRecords());
      response.setErrorCode(Constants.ERROR_CODE);
      response.setErrorMessage(Constants.ERROR);
    }
    logger.info("Exiting :: TransactionServiceImpl :: searchTransactions method");
    return response;
  } catch (Exception e) {
    logger.error("Error :: TransactionServiceImpl :: searchTransactions method", e);
    response.setErrorCode(Constants.ERROR);
    response.setErrorMessage(Constants.ERROR_DATA);
  }
  return null;
  }
}
