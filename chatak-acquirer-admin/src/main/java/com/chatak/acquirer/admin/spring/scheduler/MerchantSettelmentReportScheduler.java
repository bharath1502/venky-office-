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
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

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
		try {
			if (Constants.SCHEDULER_ENABLE_FLAG.equalsIgnoreCase("true")) {
			String startTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
			String batchDate = LocalDateTime.now().toString();
			List<ProgramManager> programManagerList = programManagerDao.findByBatchTime(startTime);
			 processSettlementReport(programManagerList, batchDate);
			}
		} catch (Exception e) {
			LOGGER.error("Error :: MerchantSettelmentReportScheduler :: generateMerchantSettelmentReport : " + e.getMessage(), e);
		}
	}

	private void processSettlementReport(List<ProgramManager> programManagerList,String batchDate) {
		for (ProgramManager programManagerData : programManagerList) {
			String batchTime = programManagerData.getPmSystemConvertedTime();
			Long pmId  = programManagerData.getId();
			String schedulerStatus = programManagerData.getSchedulerRunStatus();
			if ((!StringUtil.isNullAndEmpty(batchTime) && null != pmId)  && (StringUtil.isNull(schedulerStatus) || schedulerStatus.equalsIgnoreCase(PGConstants.BATCH_STATUS_COMPLETED))) {
				
				Runnable task = () ->{
					processMerchantSettlement(pmId,batchDate);
				
			};
            new Thread(task).start();
			}
		}
	}

	private synchronized void processMerchantSettlement(Long pmId,String batchDate){
		PGBatch batch = batchDao.findByProgramManagerIdAndStatus(pmId, PGConstants.BATCH_STATUS_ASSIGNED);
		if(null != batch) {
			batch.setStatus(PGConstants.BATCH_STATUS_PROCESSING);
			PGBatch pgbatch = batchDao.save(batch);
			LOGGER.info("updated Pg Batch as Processing" + pgbatch.getStatus());	
			//update ProgramManger Status Processing
			ProgramManager programManager = programManagerDao.findByProgramManagerId(pmId);
			programManager.setSchedulerRunStatus(PGConstants.BATCH_STATUS_PROCESSING);
			programManagerDao.saveOrUpdateProgramManager(programManager);
			
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
			PGBatch pgBatchStatus = batchDao.save(batch);
			LOGGER.info("updated Pg Batch status as completed" + pgBatchStatus.getStatus());
			
			//update ProgramManger Status Processing To Completed		
			programManager.setSchedulerRunStatus(PGConstants.BATCH_STATUS_COMPLETED);
			programManagerDao.saveOrUpdateProgramManager(programManager);
		}
	}
}
