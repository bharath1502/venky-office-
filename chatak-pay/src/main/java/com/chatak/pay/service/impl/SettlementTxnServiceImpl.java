package com.chatak.pay.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
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
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;

@Service
public class SettlementTxnServiceImpl implements SettlementTxnService {

	private Logger logger = Logger.getLogger(SettlementTxnServiceImpl.class);

	@Autowired
	private IssSettlementDataDao issSettlementDataDao;

	@Autowired
	ProgramManagerDao programManagerDao;

	@Autowired
	IsoServiceDao isoServiceDao;
 
	@Override
	public SettlementTxnResponse saveIssSettlementData(PGIssSettlementData issSettlementData) {
	  LogHelper.logEntry(logger, LoggerMessage.getCallerName());
      SettlementTxnResponse txnResponse = new SettlementTxnResponse();
      try {
          List<ProgramManager> list = programManagerDao.findByIssuancePmid(issSettlementData.getProgramManagerId());
          if(StringUtil.isListNotNullNEmpty(list)) {
            LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Found onboarded PM");
            
              issSettlementData.setAcqPmId(list.get(0).getId());
              
              LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Saved PGIssSettlementData successfully");
              ProgramManagerAccount pmAccount = programManagerDao.findByProgramManagerIdAndAccountType(issSettlementData.getAcqPmId(),
                      Constants.ACCOUNT_NAME_SYSTEM);
              pmAccount.setAvailableBalance(pmAccount.getAvailableBalance() + issSettlementData.getTotalAmount().longValue());
              pmAccount.setCurrentBalance(pmAccount.getCurrentBalance() + issSettlementData.getTotalAmount().longValue());
              programManagerDao.saveOrUpdateProgramManagerAccount(pmAccount);
              
          } else {
            LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Received funds from a PM not found in issuance");
          }
          issSettlementDataDao.saveIssSettlementData(issSettlementData);
          
          LogHelper.logInfo(logger, LoggerMessage.getCallerName(), "Saved ProgramManagerAccount successfully");
          txnResponse.setErrorCode(Constants.SUCCESS_CODE);
      } catch (Exception e) {
          LogHelper.logError(logger, LoggerMessage.getCallerName(), e, e.getMessage());
          txnResponse.setErrorCode(Constants.ERROR_CODE);
      }
      return txnResponse;
	}
}
