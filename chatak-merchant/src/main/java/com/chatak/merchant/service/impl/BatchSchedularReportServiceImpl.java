package com.chatak.merchant.service.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.MerchantData;
import com.chatak.merchant.service.BatchSchedularReportService;
import com.chatak.pg.acq.dao.BatchSchedularDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.user.bean.DailyFundingReport;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportRequest;
import com.chatak.pg.user.bean.GetDailyFundingReportResponse;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.Constants;


@Service("batchSchedularReportService")
public class BatchSchedularReportServiceImpl implements BatchSchedularReportService{
  
  private Logger logger = Logger.getLogger(BatchSchedularReportServiceImpl.class);
  
  @Autowired
  private BatchSchedularDao batchSchedularDao;
  
  @Autowired
  private MerchantDao merchantDao;

  @Override
  public MerchantData getMerchantCodeAndCompanyName(Long userid) {
    MerchantData merchantData = new MerchantData();
    PGMerchant pgMerchant = batchSchedularDao.getMerchantCodeAndCompanyName(userid);
    if (pgMerchant != null) {
      merchantData.setId(pgMerchant.getId());
      merchantData.setMerchantCode(pgMerchant.getMerchantCode());
      merchantData.setBusinessName(pgMerchant.getBusinessName());
    }
    return merchantData;
  }

  @Override
  public List<PGMerchant> findById(Long parentMerchantId) {
    return merchantDao.findById(parentMerchantId);
  }

  @Override
  public GetTransactionsListResponse searchBatchReportTransactions(
      GetBatchReportRequest batchReportRequest) throws ChatakMerchantException {
    logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions =
          batchSchedularDao.getMerchantBatchReportTransactions(batchReportRequest);
      if (transactions != null) {
        response.setTransactionList(transactions);
        response.setTotalResultCount(batchReportRequest.getNoOfRecords());
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
      } else {
        response.setTransactionList(transactions);
        response.setTotalResultCount(batchReportRequest.getNoOfRecords());
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

  @Override
  public GetDailyFundingReportResponse searchDailyFundingReportDetails(
      GetDailyFundingReportRequest getDailyFundingReportRequest, String userType) throws ChatakMerchantException {
    logger.info("Entering :: BatchSchedularServiceImpl :: searchDailyFundingReportDetails");
    GetDailyFundingReportResponse response = new GetDailyFundingReportResponse();
    try {
      List<DailyFundingReport> dailyFundingReports =
          batchSchedularDao.searchMerchantDailyFundingReportDetails(getDailyFundingReportRequest, userType);

      if (dailyFundingReports != null) {
        response.setDailyFundingReport(dailyFundingReports);
        response.setTotalResultCount(getDailyFundingReportRequest.getNoOfRecords());
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
      } else {
        response.setTotalResultCount(getDailyFundingReportRequest.getNoOfRecords());
        response.setErrorCode(Constants.ERROR_CODE);
        response.setErrorMessage(Constants.ERROR);
      }

      logger.info("Exiting :: BatchSchedularServiceImpl :: searchDailyFundingReportDetails");
      return response;
    } catch (Exception e) {
      logger.error("Error :: BatchSchedularServiceImpl :: searchDailyFundingReportDetails", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }
}
