package com.chatak.acquirer.admin.spring.scheduler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BatchSchedularService;
import com.chatak.acquirer.admin.service.FundTransfersService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.ExecutedTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.bean.LitleEFTRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

public class MerchantPayoutScheduler {

  private static final Logger logger = Logger.getLogger(MerchantPayoutScheduler.class);

  @Autowired
  AccountDao accountDao;

  @Autowired
  FundTransfersService fundTransfersService;

  @Autowired
  TransactionDao transactionDao;

  @Autowired
  ExecutedTransactionDao executedTransactionDao;

  @Autowired
  BatchSchedularService batchSchedularService;

  /**
   * Method to process Litle Executed EFT transactions
   */
  public void processAutoPayment() {
    logger.info("Eft Sweeper is started @" + new Timestamp(System.currentTimeMillis()));

    processEFTSweeper();

    processFundingReportSchedular();//To insert data into table ralated to daily funds

    logger.info("Eft Sweeper is Ended @" + new Timestamp(System.currentTimeMillis()));
  }

  /**
   * 
   * Method to return List of Litle Executed  
   * @param payOutFrequency
   * @return
   * 
   */
  public List<LitleEFTRequest> getLitleEFTDTOsLists(String payOutFrequency) {
    List<PGAccount> accounts = accountDao.getPGAccountsOnPayoutFrequency(payOutFrequency);
    Integer payoutCount = 0;
    if (payOutFrequency.equals(Constants.DAILY)) {
      payoutCount = 1;
    } else if (payOutFrequency.equals(Constants.WEEKLY)) {
      payoutCount = Constants.SEVEN;
    } else if (payOutFrequency.equals(Constants.MONTHLY)) {
      payoutCount = Constants.THIRTY;
    }

    List<LitleEFTRequest> litleEFTDTOsLists = new ArrayList<>();
    for (PGAccount account : accounts) {
      LitleEFTRequest litleEFTDTOs = null;
      if (null != account.getEntityId()) {
        litleEFTDTOs =
            executedTransactionDao.getLitleExecutedTransactionsOnMerchantCodeAndPayoutFrequency(
                account.getEntityId(), payoutCount);
        litleEFTDTOsLists.add(litleEFTDTOs);
      }
    }
    return litleEFTDTOsLists;
  }

  /**
   * Scheduled method to process EFT sweep on Daily,Weekly and Monthly configuration.
   */
  public void processEFTSweeper() {
    List<LitleEFTRequest> dailyLitleEFTRequests = getLitleEFTDTOsLists(Constants.DAILY);
    for (LitleEFTRequest litleEFTRequest : dailyLitleEFTRequests) {
      litleEFTRequest.getLitleEFTDTOs();
      try {
        if (CommonUtil.isListNotNullAndEmpty(litleEFTRequest.getLitleEFTDTOs())) {
          fundTransfersService.processLitleEFT(litleEFTRequest.getLitleEFTDTOs());
        }
      } catch (ChatakAdminException e) {
        logger.error("Exception at Daily payout list" + e);
      }
    }
    List<LitleEFTRequest> weeklyLitleEFTRequests = getLitleEFTDTOsLists(Constants.WEEKLY);
    for (LitleEFTRequest litleEFTRequest : weeklyLitleEFTRequests) {
      litleEFTRequest.getLitleEFTDTOs();
      try {
        if (CommonUtil.isListNotNullAndEmpty(litleEFTRequest.getLitleEFTDTOs())) {
          fundTransfersService.processLitleEFT(litleEFTRequest.getLitleEFTDTOs());
        }

      } catch (ChatakAdminException e) {
        logger.error("Exception at Daily payout list" + e);
      }
    }
    List<LitleEFTRequest> monthlyLitleEFTRequests = getLitleEFTDTOsLists(Constants.MONTHLY);
    for (LitleEFTRequest litleEFTRequest : monthlyLitleEFTRequests) {
      litleEFTRequest.getLitleEFTDTOs();
      try {
        if (CommonUtil.isListNotNullAndEmpty(litleEFTRequest.getLitleEFTDTOs())) {
          fundTransfersService.processLitleEFT(litleEFTRequest.getLitleEFTDTOs());
        }
      } catch (ChatakAdminException e) {
        logger.error("Exception at Daily payout list" + e);
      }
    }
  }

  public void processFundingReportSchedular() {
    logger.info("Entering :: MerchantPayoutScheduler :: processFundingReportSchedular");
    batchSchedularService.fundingReportSchedular();
    logger.info("Exiting :: MerchantPayoutScheduler :: processFundingReportSchedular");
  }
}
