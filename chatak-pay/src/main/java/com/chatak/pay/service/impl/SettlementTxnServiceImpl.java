package com.chatak.pay.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.pay.service.SettlementTxnService;
import com.chatak.pg.acq.dao.IsoServiceDao;
import com.chatak.pg.acq.dao.IssSettlementDataDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.SettlementTxnResponse;
import com.chatak.pg.util.Constants;

@Service
public class SettlementTxnServiceImpl implements SettlementTxnService {

	private static Logger logger = LogManager.getLogger(SettlementTxnServiceImpl.class);

	@Autowired
	private IssSettlementDataDao issSettlementDataDao;

	@Autowired
	ProgramManagerDao programManagerDao;

	@Autowired
	IsoServiceDao isoServiceDao;
 
	@Override
	public SettlementTxnResponse saveIssSettlementData(PGIssSettlementData issSettlementData) {
	  logger.info("Entering :: SettlementTxnServiceImpl :: saveIssSettlementData");
      SettlementTxnResponse txnResponse = new SettlementTxnResponse();
      try {
          List<ProgramManager> list = programManagerDao.findByIssuancePmid(issSettlementData.getProgramManagerId());
          if(StringUtil.isListNotNullNEmpty(list)) {
            
              issSettlementData.setAcqPmId(list.get(0).getId());
              
              ProgramManagerAccount pmAccount = programManagerDao.findByProgramManagerIdAndAccountType(issSettlementData.getAcqPmId(),
                      Constants.ACCOUNT_NAME_SYSTEM);
              pmAccount.setAvailableBalance(pmAccount.getAvailableBalance() + issSettlementData.getTotalAmount().longValue());
              pmAccount.setCurrentBalance(pmAccount.getCurrentBalance() + issSettlementData.getTotalAmount().longValue());
              programManagerDao.saveOrUpdateProgramManagerAccount(pmAccount);
              
          } else {
            logger.info("Received funds from a PM not found in issuance");
          }
          issSettlementDataDao.saveIssSettlementData(issSettlementData);
          txnResponse.setErrorCode(Constants.SUCCESS_CODE);
      } catch (Exception e) {
          logger.error("Error :: SettlementTxnServiceImpl :: saveIssSettlementData : " + e.getMessage(), e);
          txnResponse.setErrorCode(Constants.ERROR_CODE);
      }
      logger.info("Exiting :: SettlementTxnServiceImpl :: saveIssSettlementData");
      return txnResponse;
	}
}
