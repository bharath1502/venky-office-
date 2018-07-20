package com.chatak.acquirer.admin.spring.scheduler;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.BatchDao;
import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.SettlementReportDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.model.PGBatch;
import com.chatak.pg.acq.dao.model.PGSettlementReport;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.LogHelper;
import com.chatak.pg.util.LoggerMessage;

public class MerchantSettelmentReportScheduler {

	private static final Logger LOGGER = Logger.getLogger(MerchantSettelmentReportScheduler.class);

	@Autowired
	private ProgramManagerDao programManagerDao;
	
	@Autowired
	private BatchDao batchDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private SettlementReportDao settlementReportDao;

	public void generateMerchantSettelmentReport() {
		LogHelper.logEntry(LOGGER, LoggerMessage.getCallerName());
		try {
			String startTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
			String batchDate = LocalDateTime.now().toString();
			List<ProgramManager> programManagerList = programManagerDao.findByBatchTime(startTime);
			 processSettlementReport(programManagerList, batchDate);
		} catch (Exception e) {
			LogHelper.logError(LOGGER, LoggerMessage.getCallerName(), e, "");
		}
	}

	private void processSettlementReport(List<ProgramManager> programManagerList,String batchDate) {
		for (ProgramManager programManagerData : programManagerList) {
			String batchTime = programManagerData.getPmSystemConvertedTime();
			Long pmId  = programManagerData.getId();
			if (!StringUtil.isNullAndEmpty(batchTime) && null != pmId) {
				
				Runnable task = () ->{
					processMerchantSettlement(pmId,batchDate);
				
			};
            new Thread(task).start();
			}
		}
	}

	private synchronized void processMerchantSettlement(Long pmId,String batchDate){
		LogHelper.logEntry(LOGGER, LoggerMessage.getCallerName());	
		PGBatch batch = batchDao.findByProgramManagerId(pmId);
		if(null != batch && batch.getStatus().equals(PGConstants.BATCH_STATUS_ASSIGNED)){
			batch.setStatus(PGConstants.BATCH_STATUS_PROCESSING);
			batchDao.save(batch);
		List<PGTransaction> transactions = transactionDao.getTransactionsByBatchId(batch.getBatchId());
		HashMap<String,BigInteger> merchantTxn = new HashMap<>();
		if(StringUtil.isListNotNullNEmpty(transactions)){
			for(PGTransaction transaction :transactions){
				String merchantId = transaction.getMerchantId();
				BigInteger amount = merchantTxn.get(merchantId);
				if(amount != null){
					amount = amount.add(BigInteger.valueOf(transaction.getTxnTotalAmount()));
					merchantTxn.put(merchantId, amount);
				} else {
					merchantTxn.put(merchantId, BigInteger.valueOf(transaction.getTxnTotalAmount()));
				}
			}
		}
		//storing in settelment Report
			for (HashMap.Entry<String, BigInteger> entry : merchantTxn.entrySet()) {
				PGSettlementReport settlementReport = new PGSettlementReport();
				settlementReport.setMerchantId(entry.getKey());
				settlementReport.setSettlementAmount(entry.getValue());
				settlementReport.setProgramManagerId(pmId);
				settlementReport.setBatchId(batch.getBatchId());
				settlementReport.setBatchTime(DateUtil.toTimestamp(batchDate, PGConstants.DATE_TIME_ZONE_FORMAT));
				settlementReportDao.save(settlementReport);
			}
			batch.setStatus(PGConstants.BATCH_STATUS_COMPLETED);
			batchDao.save(batch);
		}
	}
}
