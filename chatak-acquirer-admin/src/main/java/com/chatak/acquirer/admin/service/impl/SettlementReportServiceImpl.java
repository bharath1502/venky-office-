package com.chatak.acquirer.admin.service.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.SettlementReportService;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.SettlementReportDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.user.bean.GetBatchReportRequest;
import com.chatak.pg.user.bean.GetTransactionsListRequest;
import com.chatak.pg.user.bean.GetTransactionsListResponse;
import com.chatak.pg.user.bean.Transaction;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.StringUtils;

@Service
public class SettlementReportServiceImpl implements SettlementReportService {

  private Logger logger = Logger.getLogger(SettlementReportServiceImpl.class);

  @Autowired
  private SettlementReportDao settlementReportDao;
  
  @Autowired
  private CurrencyConfigDao currencyConfigDao;

  @Override
  public GetTransactionsListResponse searchSettlementReportTransactions(
      GetTransactionsListRequest getTransactionsListRequest) throws ChatakAdminException {
    logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions =
          settlementReportDao.getSettlementReportTransactions(getTransactionsListRequest);

      if (transactions != null) {
        response.setTransactionList(transactions);
        response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
      } else {
        response.setTransactionList(transactions);
        response.setTotalResultCount(getTransactionsListRequest.getNoOfRecords());
        response.setErrorCode(Constants.ERROR_CODE);
        response.setErrorMessage(Constants.ERROR);
      }
      logger.info("Exiting :: TransactionServiceImpl :: searchTransactions method");
      return response;
    } catch (Exception exp) {
      logger.error("Error :: TransactionServiceImpl :: searchTransactions method", exp);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

  @Override
  public GetTransactionsListResponse searchBatchReportTransactions(
      GetBatchReportRequest batchReportRequest) throws ChatakAdminException {
    logger.info("Entering :: TransactionServiceImpl :: searchTransactions method");
    GetTransactionsListResponse response = new GetTransactionsListResponse();
    try {
      List<Transaction> transactions =
          settlementReportDao.getBatchReportTransactions(batchReportRequest);

      if (transactions != null) {
        response.setTransactionList(transactions);
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setTotalResultCount(batchReportRequest.getNoOfRecords());
        response.setErrorMessage(Constants.SUCESS);
      } else {
        response.setTransactionList(transactions);
        response.setErrorCode(Constants.ERROR_CODE);
        response.setTotalResultCount(batchReportRequest.getNoOfRecords());
        response.setErrorMessage(Constants.ERROR);
      }
      logger.info("Exiting :: TransactionServiceImpl :: searchTransactions method");
      return response;
    } catch (Exception exp) {
      logger.error("Error :: TransactionServiceImpl :: searchTransactions method", exp);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return null;
  }

}
