package com.chatak.acquirer.admin.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chatak.acquirer.admin.constants.URLMappingConstants;
import com.chatak.acquirer.admin.controller.model.ExportDetails;
import com.chatak.acquirer.admin.controller.model.SettlementDataRequest;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.EntityMappedTransactions;
import com.chatak.acquirer.admin.model.IsoSettlementDetails;
import com.chatak.acquirer.admin.model.TransactionResponse;
import com.chatak.acquirer.admin.service.IsoService;
import com.chatak.acquirer.admin.service.SettlementReportService;
import com.chatak.acquirer.admin.service.SettlementService;
import com.chatak.acquirer.admin.util.ExportUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.model.Iso;
import com.chatak.pg.acq.dao.model.PGIssSettlementData;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.settlement.PGSettlementEntityHistory;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactionEntity;
import com.chatak.pg.bean.settlement.IssuanceSettlementTransactions;
import com.chatak.pg.bean.settlement.SettlementEntity;
import com.chatak.pg.bean.settlement.SettlementMerchantDetails;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ExportType;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.model.FeeReportRequest;
import com.chatak.pg.model.Response;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.Properties;

@Controller
public class SettlementReportController implements URLMappingConstants {
	
	private static Logger logger = Logger.getLogger(SettlementReportController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private SettlementService settlementService;
	
	@Autowired
	private SettlementReportService settlementReportService;
	
	@Autowired
	private IsoService isoService;
	
	private Timestamp batchProcessedDate = DateUtil.getCurrentTimestamp();
	
	String merchantId = "Merchant Id";
	String trminalId = "Terminal Id";
	String txnReferenceNumber = "Transaction Reference Number";
	String gatewayTxnId = "Gateway Transaction Id";
	String txnAmount = "Transaction Amount";
	String txnType = "Transaction Type";
	String txnCode = "Transaction Code";
	String txnStatus = "Transaction Status";
	String txnDesc = "Transaction Description";
	String txnTime = "Transaction Time";
	String deviceLocalTxnTime = "Device Local Transaction Time";
	String pmName = "Program Manager Name";
	String batchTime = "Batch Time";
	String pmId = "PMId";
	
	private static final String MONEY_MOMENT="MONEY_MOMENT";

	private String programManagerId = null;

	@RequestMapping(value = PROCESS_SETTLEMENT_DATA, method = RequestMethod.POST)
	public ModelAndView processAndValidateSettlementData(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Map model, SettlementDataRequest settlementDataRequest,
			@RequestParam("dataFile") MultipartFile file) {
		
		ModelAndView modelAndView = new ModelAndView(FETCH_SETTLEMENT_DATA_BY_PMID);
		logger.info("Entering :: SettlementReportController :: processAndValidateSettlementData");
		
		HashMap<Integer , String> map = new HashMap<>();
		int rowCount = 0;
		SettlementReportController controller = new SettlementReportController();
		byte[] bytes = null;
		try {
			if (!file.isEmpty()) {
				bytes = file.getBytes();
				String[] strArray = file.getOriginalFilename().split("_");
				int size = strArray.length;
				String programName = strArray[0];
				if(size>0 && size<2){
					throw new ChatakAdminException(Properties.getProperty("admin.invalid.name"));
				}
				
				String dateTime = strArray[1].substring(0, strArray[1].indexOf('.'));
				
				validateFileName(settlementDataRequest, dateTime, programName);
				
				if(validateFileExists(programName, settlementDataRequest)) {
				String fileData = new String(bytes, "UTF-8");
				String[] lines = fileData.split("\n");
				int length = lines.length;
				
				for (int i = 4; i < length; i++) {
					rowCount = validateRowCount(map, rowCount, controller, lines, i);
				}
				
				if(map.size() > 0) {
					modelAndView.addObject(map);
					
					return modelAndView;
				}
				
				processSettlementData(lines, modelAndView, settlementDataRequest);
				} else {
					modelAndView.addObject(Constants.ERROR, messageSource.getMessage("admin.label.settlement.executed",
			                null, LocaleContextHolder.getLocale()));
				}
			}
			
			logger.info("Exiting :: SettlementReportController :: processAndValidateSettlementData");
			
		} catch (ChatakAdminException e) {
			logger.error("ERROR:: SettlementReportController:: processAndValidateSettlementData method", e);
			modelAndView.addObject(Constants.ERROR, e.getMessage());
		} catch (Exception e) {
			logger.error("ERROR:: SettlementReportController:: processAndValidateSettlementData method", e);
			modelAndView.addObject(Constants.ERROR, e.getMessage());
		}
		 model.put("settlementDataRequest", settlementDataRequest);
		return modelAndView;
	}

	private int validateRowCount(HashMap<Integer, String> map, int rowCount, SettlementReportController controller,
			String[] lines, int i) throws ChatakAdminException, ParseException {
		SettlementDataRequest dataRequest = new SettlementDataRequest();
		String errorMessage = "";
		String[] columns = lines[i].split(",");
		fetchColumn(columns);
		if (i == 4) {
			if (validateHeaderForCsv(columns, controller)) {
				return rowCount;
			} else {
				throw new ChatakAdminException(Properties.getProperty("admin.invalid.header.fileupload"));
			}
		}
		rowCount = validateFiledValue(map, rowCount, dataRequest, errorMessage, columns);
		return rowCount;
	}
	
	private ModelAndView processSettlementData(String[] lines, ModelAndView modelAndView, SettlementDataRequest settlementDataRequest) throws ChatakAdminException, PrepaidAdminException {
		logger.info("Entering :: SettlementReportController :: processSettlementData");
		
		int length = lines.length;
		
		// Map of all matched transactions based on merchant id
		Map<String, IssuanceSettlementTransactionEntity> settlementEntityList = new HashMap<>();
		
		// List of all acquiring batches as per the issuance csv file
		Map<String, List<String>> batchIdandTxnIdList = new HashMap<>();
		
		// List of all unmatched transactions, present in csv but absent in PG
		List<IssuanceSettlementTransactionEntity> issuanceTransactionNotfoundAcquiringList = new ArrayList<>();
		
		// List of all unmatched transactions, present in PG but not in csv
		List<PGTransaction> acquiringTransactionNotfoundIssuanceList = new ArrayList<>();
		
		//List of all mapped TxnIds in pgTxnTable
		List<String> pgTxnIdList = new ArrayList<>();
		try {
		  
		// Truncate all data from issuance settlement transaction table
		settlementService.deleteAllIssuanceSettlementData(programManagerId);
		
		for (int i = 5; i < length; i++) {
			
			String[] columns = lines[i].split(",");
			
			logger.info("processSettlementData :: Retrieve PG transactions for MID : " + columns[0] + ", TID: " + columns[1]
                    + ", Issuance Txn: " + columns[2] + ", PG Txn: " + columns[3]);
			
			// Fetch PG transaction based on mid, tid...
			List<SettlementEntity> transactionList = settlementService.getPgTransactions(columns[0], // Merchant id
					columns[1], // Terminal id
					columns[2], //TransactionRefferencenumber
					columns[3]);  //GateWAyTxnId
			if(StringUtil.isListNullNEmpty(transactionList)) {
				// Add to the list of not found acquiring transactions
				populateMissingAcquiringTransactions(columns, issuanceTransactionNotfoundAcquiringList);
				continue;
			}
			
			SettlementEntity transaction = transactionList.get(0);
			
			IssuanceSettlementTransactionEntity entity = settlementEntityList.get(transaction.getMerchantId());
			entity = setEntityMerchantId(transaction, entity);
			
			populateSettlementEntity(entity, transaction, columns, settlementEntityList, settlementDataRequest);
			
			// List of batch id's vs pg transaction ids
			// This is to maintain any missing transactions that are present in issuance csv file
			// but not in acquiring.
			List<String> pgTxnIdsForBatch = batchIdandTxnIdList.get(transaction.getBatchId());
			pgTxnIdsForBatch = setListPgTxnId(pgTxnIdsForBatch);
			pgTxnIdsForBatch.add(transaction.getPgTxnId());
			
			batchIdandTxnIdList.put(transaction.getBatchId(), pgTxnIdsForBatch);
			
			pgTxnIdList.add(transaction.getPgTxnId());
		}
		Integer batchCount = 0;
		Integer batchSize = Integer.parseInt("100");
		for (Map.Entry<String, IssuanceSettlementTransactionEntity> entry : settlementEntityList.entrySet()) {
			batchCount++;
		  Response insertion = settlementService.saveIssuanceSettlementTransaction(entry.getValue(), batchCount, batchSize);
	        
	        // If failure, terminate
	        if (insertion.getErrorCode().equals(PGConstants.SUCCESS)) {
	            logger.info("processSettlementData :: saved issuance settlement row with MID: " + entry.getKey());
	            // successfully inserted 1 row
	        } else {
	            logger.info("processSettlementData :: ERROR saving issuance settlement row with MID: "
	                            + entry.getKey() + ", message: " + insertion.getErrorMessage());
	            throw new ChatakAdminException("ERROR saving issuance settlement row: " + insertion.getErrorMessage());
	        }
        }
		
		for (Map.Entry<String, List<String>> entry : batchIdandTxnIdList.entrySet()) {
			// Retrieve all transactions for each batch id from PG transaction table
			// where the status was not processed					
			// Fetch all transaction from PG transaction table
			// where batch id = ? and pg transaction id not in (id1, id2....) ie. pgTxnIdList
			List<PGTransaction> transactionListNotInPgTransactionTable = settlementService.getPGTransactionListNotInAcquiring(entry.getKey(), entry.getValue());
			if(StringUtil.isListNotNullNEmpty(transactionListNotInPgTransactionTable)) {
			    logger.info("processSettlementData :: Found missing PG transaction, for batchID: " + entry.getKey()
								+ ", rows: " + transactionListNotInPgTransactionTable.size());
				acquiringTransactionNotfoundIssuanceList.addAll(transactionListNotInPgTransactionTable);
			}
		}
		//Save List of all unmatched transactions, present in csv but absent in PG
		//Using batch processing
		Integer batchNotFoundAcqCount = 0;
		Integer batchNotFoundAcqSize = Integer.parseInt("100");
		for(IssuanceSettlementTransactionEntity IssuanceSettlementTxnEnt : issuanceTransactionNotfoundAcquiringList) {
			batchNotFoundAcqCount++;
			settlementService.saveIssuanceSettlementTransaction(IssuanceSettlementTxnEnt, batchNotFoundAcqCount, batchNotFoundAcqSize);
		}
		
		// Set all lists and hashmaps in the modelandview of the controller
		modelAndView.addObject("acquiringTransactionNotfoundIssuanceList",
				acquiringTransactionNotfoundIssuanceList);
		modelAndView.addObject("totalAcquiringTransactionNotfoundIssuanceList", acquiringTransactionNotfoundIssuanceList.size());
		
		modelAndView.addObject("issuanceTransactionNotfoundAcquiringList",
				issuanceTransactionNotfoundAcquiringList);
		modelAndView.addObject("totalIssuanceTransactionNotfoundAcquiringList", issuanceTransactionNotfoundAcquiringList.size());
		
		modelAndView.addObject("settlementEntityList", settlementEntityList);
		modelAndView.addObject("totalSettlementEntityList", settlementEntityList.size());
		
		modelAndView.addObject(Constants.SUCESS, messageSource.getMessage("admin.label.process",
                null, LocaleContextHolder.getLocale()));
		
		logger.info("processSettlementData :: acquiringTransactionNotfoundIssuanceList: "
						+ acquiringTransactionNotfoundIssuanceList + ", issuanceTransactionNotfoundAcquiringList: "
						+ issuanceTransactionNotfoundAcquiringList + ", settlementEntityList: " + settlementEntityList);
		} catch (InstantiationException e) {
			logger.error("Error :: SettlementReportController :: processSettlementData : InstantiationException" + e.getMessage(), e);
		} catch (IllegalAccessException e) {
		    logger.error("Error :: SettlementReportController :: processSettlementData : IllegalAccessException" + e.getMessage(), e);
		} catch (Exception e) {
		    logger.error("Error :: SettlementReportController :: processSettlementData : " + e.getMessage(), e);
		}
		logger.info("Exiting :: SettlementReportController :: processSettlementData");
		return modelAndView;
	}

	private List<String> setListPgTxnId(List<String> pgTxnIdsForBatch) {
		if (StringUtil.isListNullNEmpty(pgTxnIdsForBatch)) {
			pgTxnIdsForBatch = new ArrayList<>();
		}
		return pgTxnIdsForBatch;
	}

	private IssuanceSettlementTransactionEntity setEntityMerchantId(SettlementEntity transaction,
			IssuanceSettlementTransactionEntity entity) {
		if (entity == null) {
		    logger.info("processSettlementData :: Merchant entity not found, creating new");
			entity = new IssuanceSettlementTransactionEntity();
			entity.setMerchantId(transaction.getMerchantId());
		}
		return entity;
	}

	private void populateMissingAcquiringTransactions(String[] columns, 
			List<IssuanceSettlementTransactionEntity> issuanceTransactionNotfoundAcquiringList) throws InstantiationException, IllegalAccessException, ChatakAdminException {
		logger.info("Entering :: SettlementReportController :: populateMissingAcquiringTransactions");
		
		IssuanceSettlementTransactionEntity entity = new IssuanceSettlementTransactionEntity();
		entity.setMerchantId(columns[0]); // Merchant id
		
		// Create a new transaction object and add it
		IssuanceSettlementTransactions settlementTransaction = new IssuanceSettlementTransactions();
		settlementTransaction.setTerminalId(columns[1]); // Terminal id
		settlementTransaction.setIssuerTxnID(columns[Integer.parseInt("2")]);
		settlementTransaction.setPgTransactionId(columns[Integer.parseInt("3")]);
		settlementTransaction.setTxnDate(DateUtil.toTimestamp(columns[Integer.parseInt("10")], PGConstants.DATE_FORMAT));
		
		// Retrieve mapped transactions
		List<IssuanceSettlementTransactions> settlementTransactionsList = entity.getSettlementTransactionsList();
		// Add the new transaction to the list
		settlementTransactionsList.add(settlementTransaction);
		// Set the data back
		entity.setSettlementTransactionsList(settlementTransactionsList);
		
		entity.setIssSaleAmount(new BigDecimal(columns[Integer.parseInt("4")]));
		entity.setIssPmId(Long.valueOf(columns[Integer.parseInt("13")]));
		entity.setBatchFileProcessedDate(batchProcessedDate);
		entity.setIssuerTxnID(columns[Integer.parseInt("2")]);
		entity.setPgTransactionId(columns[Integer.parseInt("3")]);
		issuanceTransactionNotfoundAcquiringList.add(entity);
		//Commented for Batch Inserts
		/*settlementService.saveIssuanceSettlementTransaction(entity);*/
		logger.info("Missing Acquiring Transactions : IssuerTxnID " +columns[2]
		    +", PgTransactionId " + columns[3]);
		logger.info("Exiting :: SettlementReportController :: populateMissingAcquiringTransactions");
	}
	
	private void populateSettlementEntity(IssuanceSettlementTransactionEntity entity, SettlementEntity transaction, String[] columns, 
			Map<String, IssuanceSettlementTransactionEntity> settlementEntityList, SettlementDataRequest settlementDataRequest) {
		logger.info("Entering :: SettlementReportController :: populateSettlementEntity");
		
		// Create a new transaction object and add it
		IssuanceSettlementTransactions settlementTransaction = new IssuanceSettlementTransactions();
		
		settlementTransaction.setTerminalId(transaction.getTerminalId());
		settlementTransaction.setIssuerTxnID(columns[Integer.parseInt("2")]);
		settlementTransaction.setPgTransactionId(transaction.getPgTxnId());
		settlementTransaction.setTxnDate(DateUtil.toTimestamp(columns[Integer.parseInt("10")], PGConstants.DATE_FORMAT));
		settlementTransaction.setIsoId(transaction.getIsoId());
		settlementTransaction.setTxnType(transaction.getTransactionType());
		
		BigDecimal totalAcquirerAmount = BigDecimal.valueOf(Long.valueOf(transaction.getAcquirerAmount()));
		if(columns[Integer.parseInt("6")].equalsIgnoreCase(Constants.POS_PURCHASE)) {
			totalAcquirerAmount = totalAcquirerAmount.add(entity.getAcqSaleAmount());
		} else if (columns[Integer.parseInt("6")].equalsIgnoreCase(Constants.POS_REFUND)) {
			totalAcquirerAmount = entity.getAcqSaleAmount().subtract(totalAcquirerAmount);
		}
		entity.setAcqSaleAmount(totalAcquirerAmount);
		
		BigDecimal totalIssuanceAmount = new BigDecimal(columns[Integer.parseInt("4")]);
		if(columns[Integer.parseInt("6")].equalsIgnoreCase(Constants.POS_PURCHASE)) {
			totalIssuanceAmount = totalIssuanceAmount.add(entity.getIssSaleAmount());
		} else if (columns[Integer.parseInt("6")].equalsIgnoreCase(Constants.POS_REFUND)) {
			totalIssuanceAmount = entity.getIssSaleAmount().subtract(totalIssuanceAmount);
		}
		entity.setIssSaleAmount(totalIssuanceAmount);
		
		entity.setAcqPmId(Long.valueOf(transaction.getAcqPmId()));
		entity.setIssPmId(Long.valueOf(columns[Integer.parseInt("13")]));
		entity.setBatchid(transaction.getBatchId());
		entity.setBatchFileDate(settlementDataRequest.getBatchDate());
		entity.setBatchFileProcessedDate(batchProcessedDate);
		
		
		Long isoAmt = getIsoAmount(transaction);
		Long pmAmt = transaction.getPmAmount() !=null ? transaction.getPmAmount() : 0;
		
		settlementTransaction.setIsoAmount(isoAmt);
		
		// Retrieve mapped transactions
		List<IssuanceSettlementTransactions> settlementTransactionsList = entity.getSettlementTransactionsList();
		// Add the new transaction to the list
		settlementTransactionsList.add(settlementTransaction);
		// Set the data back
		entity.setSettlementTransactionsList(settlementTransactionsList);
		
		//entity.setIsoAmount(entity.getIsoAmount() == null ? isoAmt : (entity.getIsoAmount() + isoAmt));
		
		Long merchantAmount = (Long.valueOf(transaction.getAcquirerAmount()) - (isoAmt + pmAmt));
		if (columns[Integer.parseInt("6")].equalsIgnoreCase(Constants.POS_PURCHASE)) {
			entity.setPmAmount(entity.getPmAmount() == null ? pmAmt : (entity.getPmAmount() + pmAmt));
			entity.setMerchantAmount(entity.getMerchantAmount() == null ? merchantAmount : (entity.getMerchantAmount() + merchantAmount));		
		} else if(columns[Integer.parseInt("6")].equalsIgnoreCase(Constants.POS_REFUND)){
			entity.setPmAmount(entity.getPmAmount() == null ? PGConstants.ZERO-pmAmt : (entity.getPmAmount() - pmAmt));
			entity.setMerchantAmount(entity.getMerchantAmount() == null ? PGConstants.ZERO-merchantAmount : (entity.getMerchantAmount() - merchantAmount));
		}
		entity.setStatus(PGConstants.BATCH_STATUS_PROCESSING);
		
		logger.info("populateSettlementEntity :: Actual merchant amount for PgTransactionId: " + transaction.getPgTxnId()
            + ", Individual Merchant Amount: " + merchantAmount
            + ", ISO amount: " + transaction.getIsoAmount()
            + ", PM amount: " + transaction.getPmAmount()
            + ", Total sale amount: " + transaction.getAcquirerAmount()
            + ", Total Pm Amount: "+entity.getPmAmount()
            + ", Total merchant transactions related to ISO: " + settlementTransactionsList.size()
            + ", Total Merchant Amount "+entity.getMerchantAmount());
		
		settlementEntityList.put(transaction.getMerchantId(), entity);
		
		logger.info("Exiting :: SettlementReportController :: populateSettlementEntity");
	}

  private long getIsoAmount(SettlementEntity transaction) {
    return transaction.getIsoAmount() !=null ? transaction.getIsoAmount() : 0;
  }

	/**
	 * @param map
	 * @param rowCount
	 * @param dataRequest
	 * @param errorMessage
	 * @param columns
	 * @return
	 * @throws ParseException
	 */
	private int validateFiledValue(HashMap<Integer, String> map, int rowCount, SettlementDataRequest dataRequest,
			String errorMessage, String[] columns) throws ParseException {
		for (Integer j = 0; j < columns.length; j++) {
			errorMessage = populateCSVDetails(dataRequest, columns, j, errorMessage);
		}
		rowCount++;
		if (null != errorMessage && !"".equals(errorMessage)) {
			map.put(rowCount, errorMessage);
		}
		return rowCount;
	}

	/**
	 * @param settlementRequest
	 * @param date
	 * @param programName
	 * @param time
	 * @throws ChatakAdminException
	 */
	private void validateFileName(SettlementDataRequest settlementRequest, String date, String programName)
			throws ChatakAdminException {
		logger.info("Entering :: SettlementReportController :: validateFileName");
		
		if (!date.matches(PGConstants.REGEX_DATE) || !date.equals(DateUtil.toDateStringFormat(settlementRequest.getBatchDate(), Constants.SETTLEMENT_DATE_FORMAT))) {
			throw new ChatakAdminException(Properties.getProperty("admin.invalid.date"));
		} else if (!programName.equals(settlementRequest.getProgramManagerName())) {
			throw new ChatakAdminException(Properties.getProperty("admin.invalid.name"));
		}
		logger.info("Exiting :: SettlementReportController :: validateFileName");
	}

	private void fetchColumn(String[] columns) throws ChatakAdminException {
		logger.info("Entering :: SettlementReportController :: fetchColumn");
		if (!StringUtil.isNullAndEmpty(columns[columns.length - 1]) && columns[columns.length - 1].contains("\r")) {
			columns[columns.length - 1] = columns[columns.length - 1].replaceAll("\r", "");
		}
		if (columns.length == 0) {
			throw new ChatakAdminException(Properties.getProperty("chatak.admin.user.inactive.error.message"));
		}
		logger.info("Exiting :: SettlementReportController :: fetchColumn");
	}

	private boolean validateHeaderForCsv(String[] columns, SettlementReportController controller) {
		return columns[0].equalsIgnoreCase(controller.merchantId)
				&& columns[PGConstants.INDEX_ONE].equalsIgnoreCase(controller.trminalId)
				&& columns[PGConstants.INDEX_TWO].equalsIgnoreCase(controller.txnReferenceNumber)
				&& columns[PGConstants.INDEX_THREE].equalsIgnoreCase(controller.gatewayTxnId)
				&& columns[PGConstants.INDEX_FOUR].equalsIgnoreCase(controller.txnAmount)
				&& columns[PGConstants.INDEX_FIVE].equalsIgnoreCase(controller.txnType)
				&& columns[PGConstants.INDEX_SIX].equalsIgnoreCase(controller.txnCode)
				&& columns[PGConstants.INDEX_SEVEN].equalsIgnoreCase(controller.txnStatus)
				&& columns[PGConstants.INDEX_EIGHT].equalsIgnoreCase(controller.txnDesc)
				&& columns[PGConstants.INDEX_NINE].equalsIgnoreCase(controller.txnTime)
				&& columns[PGConstants.INDEX_TEN].equalsIgnoreCase(controller.deviceLocalTxnTime)
				&& columns[PGConstants.INDEX_ELEVEN].equalsIgnoreCase(controller.pmName)
		        && columns[PGConstants.INDEX_TWELVE].equalsIgnoreCase(controller.batchTime)
		        && columns[PGConstants.INDEX_THIRTEEN].equalsIgnoreCase(controller.pmId);
	}

	private String populateCSVDetails(SettlementDataRequest dataRequest, String[] columns, int j, String errorMessage)
			throws ParseException {
		logger.info("Entering :: SettlementReportController :: populateCSVDetails");
		// setting the Card Number
		if (j == 0) {
			merchantId = columns[j];
			errorMessage = validateMerchantId(merchantId, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_ONE) {
			trminalId = columns[j];
			errorMessage = validateTerminalId(trminalId, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_TWO) {
			txnReferenceNumber = columns[j];
			errorMessage = validateTxnRefNum(txnReferenceNumber, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_THREE) {
			gatewayTxnId = columns[j];
			errorMessage = validateGatewayTxnId(gatewayTxnId, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_FOUR) {
			txnAmount = columns[j];
			errorMessage = validateTxnAmount(txnAmount, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_FIVE) {
			txnType = columns[j];
			errorMessage = validateTxnType(txnType, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_SIX) {
			txnCode = columns[j];
			errorMessage = validateTxnCode(txnCode, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_SEVEN) {
			txnStatus = columns[j];
			errorMessage = validateTxnStatus(txnStatus, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_EIGHT) {
			txnDesc = columns[j];
			errorMessage = validateTxnDesc(txnDesc, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_NINE) {
			txnTime = columns[j];
			errorMessage = validateTxnTime(txnTime, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_TEN) {
			deviceLocalTxnTime = columns[j];
			errorMessage = validateDeviceLocalTxnTime(deviceLocalTxnTime, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_ELEVEN) {
			pmName = columns[j];
			errorMessage = validatePMName(pmName, dataRequest, errorMessage);
		} else if (j == PGConstants.INDEX_TWELVE) {
			batchTime = columns[j];
			errorMessage = validateBatchTime(batchTime, dataRequest, errorMessage);
		}else if (j == PGConstants.INDEX_THIRTEEN) {
			pmId = columns[j];
			errorMessage = validatePMId(pmId, dataRequest, errorMessage);
		}  
		
		logger.info("Exiting :: SettlementReportController :: populateCSVDetails");
		return errorMessage;
	}

	private String validateMerchantId(String merchantId, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateMerchantId");
		if (StringUtil.isNullAndEmpty(merchantId)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource
						.getMessage("reports.label.transactions.merchantcode", null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("reports.label.transactions.merchantcode", null,
						LocaleContextHolder.getLocale());
			}
		} else if (merchantId.length() == Integer.parseInt("15")) {
          dataRequest.setMerchantId(merchantId);
        } else {
          errorMessage = (StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") + messageSource.getMessage("admin.valid.merchanid", null, LocaleContextHolder.getLocale());
		}
		logger.info("Exiting :: SettlementReportController :: validateMerchantId");
		return errorMessage;
	}

	private String validateTerminalId(String trminalId, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTerminalId");
		if (StringUtil.isNullAndEmpty(trminalId)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("transaction-file-exportutil-terminalid",
						null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("transaction-file-exportutil-terminalid", null,
						LocaleContextHolder.getLocale());
			}
		}
		dataRequest.setTerminalId(trminalId);
		logger.info("Exiting :: SettlementReportController :: validateTerminalId");
		return errorMessage;
	}

	private String validateTxnRefNum(String txnReferenceNumber, SettlementDataRequest dataRequest,
			String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTxnRefNum");
		if (StringUtil.isNullAndEmpty(txnReferenceNumber)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("reports.label.transactions.txnRefNume",
						null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("reports.label.transactions.txnRefNume", null,
						LocaleContextHolder.getLocale());
			}
		} else if (txnReferenceNumber.length() >= Integer.parseInt("8")) {
			dataRequest.setTxnRefNum(txnReferenceNumber);
		} else {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") + messageSource.getMessage("admin.lable.txnRefNume", null, LocaleContextHolder.getLocale());
		}
		logger.info("Exiting :: SettlementReportController :: validateTxnRefNum");
		return errorMessage;
	}

	private String validateGatewayTxnId(String gatewayTxnId, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateGatewayTxnId");
		if (StringUtil.isNullAndEmpty(gatewayTxnId)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("reports-file-exportutil-transactionId",
						null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("reports-file-exportutil-transactionId", null,
						LocaleContextHolder.getLocale());
			}
		} else if (gatewayTxnId.length() >= Integer.parseInt("8")) {
			dataRequest.setGatewayTxnId(gatewayTxnId);
		} else {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") + messageSource.getMessage("admin.lable.gatewayTxnId", null, LocaleContextHolder.getLocale());
		}
		logger.info("Exiting :: SettlementReportController :: validateGatewayTxnId");
		return errorMessage;
	}

	private String validateTxnAmount(String txnAmount, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTxnAmount");
		if (StringUtil.isNullAndEmpty(txnAmount)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("admin.label.taxnamountvalue", null,
						LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("admin.label.taxnamountvalue", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((txnAmount.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") + messageSource.getMessage("admin.label.valid.taxnamountvalue", null,
					LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnAmount(Long.valueOf((txnAmount)));
		}
		logger.info("Exiting :: SettlementReportController :: validateTxnAmount");
		return errorMessage;
	}

	private String validateTxnType(String txnType, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTxnType");
		if (StringUtil.isNullAndEmpty(txnType)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , "
						+ messageSource.getMessage("fundtransferfile.txn.type", null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("fundtransferfile.txn.type", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((txnType.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ")+  messageSource.getMessage("admin.label.valid.taxnamountvalue", null,
					LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnType(txnType);
		}
		logger.info("Exiting :: SettlementReportController :: validateTxnType");
		return errorMessage;
	}

	private String validateTxnCode(String txnCode, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTxnCode");
		if (StringUtil.isNullAndEmpty(txnCode)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , "
						+ messageSource.getMessage("admin.label.txnCode", null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("admin.label.txnCode", null, LocaleContextHolder.getLocale());
			}
		} else if ((txnCode.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") + messageSource.getMessage("admin.label.valid.txnCode", null, LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnCode(txnCode);
		}
		logger.info("Exiting :: SettlementReportController :: validateTxnCode");
		return errorMessage;
	}

	private String validateTxnStatus(String txnStatus, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTxnStatus");
		if (StringUtil.isNullAndEmpty(txnStatus)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , "
						+ messageSource.getMessage("admin.label.txnstatus", null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("admin.label.txnstatus", null, LocaleContextHolder.getLocale());
			}
		} else if ((txnCode.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ")+ messageSource.getMessage("admin.label.valid.txnstatus", null,
					LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnStatus(txnStatus);
		}
		logger.info("Exiting :: SettlementReportController :: validateTxnStatus");
		return errorMessage;
	}

	private String validateTxnDesc(String txnDesc, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTxnDesc");
		if (StringUtil.isNullAndEmpty(txnDesc)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage(
						"reports-file-exportutil-transactionDescription", null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("reports-file-exportutil-transactionDescription", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((txnDesc.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") +  messageSource.getMessage("admin.label.valid.txndesc", null, LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnDesc(txnDesc);
		}
		logger.info("Exiting :: SettlementReportController :: validateTxnDesc");
		return errorMessage;
	}

	private String validateTxnTime(String txnTime, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateTxnTime");
		if (StringUtil.isNullAndEmpty(txnTime)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("home.label.transaction.date.time", null,
						LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("home.label.transaction.date.time", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((txnTime.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") +  messageSource.getMessage("admin.label.valid.txnTime", null, LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnDesc(txnTime);
		}
		logger.info("Exiting :: SettlementReportController :: validateTxnTime");
		return errorMessage;
	}

	private String validateDeviceLocalTxnTime(String deviceLocalTxnTime, SettlementDataRequest dataRequest,
			String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateDeviceLocalTxnTime");
		if (StringUtil.isNullAndEmpty(deviceLocalTxnTime)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("admin.common-deviceLocalTxnTime:", null,
						LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("admin.common-deviceLocalTxnTime:", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((deviceLocalTxnTime.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") +  messageSource.getMessage("admin.common.valid.deviceLocalTxnTime", null,
					LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnDesc(deviceLocalTxnTime);
		}
		logger.info("Exiting :: SettlementReportController :: validateDeviceLocalTxnTime");
		return errorMessage;
	}

	private String validatePMName(String pmName, SettlementDataRequest dataRequest, String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validatePMName");
		if (StringUtil.isNullAndEmpty(pmName)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("access-user-create.label.entityname",
						null, LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("access-user-create.label.entityname", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((pmName.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") +  messageSource.getMessage("access-user.Valid.label.entityname", null,
					LocaleContextHolder.getLocale());
		} else {
			dataRequest.setTxnDesc(pmName);
		}
		logger.info("Exiting :: SettlementReportController :: validatePMName");
		return errorMessage;
	}
	
	private String validateBatchTime(String batchTime, SettlementDataRequest dataRequest,
			String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validateBatchTime");
		if (StringUtil.isNullAndEmpty(batchTime)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("admin.common-deviceLocalTxnTime:", null,
						LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("admin.common-deviceLocalTxnTime:", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((batchTime.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") +  messageSource.getMessage("admin.common.valid.deviceLocalTxnTime", null,
					LocaleContextHolder.getLocale());
		} else {
			dataRequest.setBatchtime(DateUtil.toTimestamp(batchTime, PGConstants.DATE_FORMAT));
		}
		logger.info("Exiting :: SettlementReportController :: validateBatchTime");
		return errorMessage;
	}
	
	private String validatePMId(String pmId, SettlementDataRequest dataRequest,
			String errorMessage) {
		logger.info("Entering :: SettlementReportController :: validatePMId");
		if (StringUtil.isNullAndEmpty(pmId)) {
			if (!StringUtil.isNull(errorMessage)) {
				errorMessage = errorMessage + " , " + messageSource.getMessage("admin.common-deviceLocalTxnTime:", null,
						LocaleContextHolder.getLocale());
			} else {
				errorMessage = messageSource.getMessage("admin.common-deviceLocalTxnTime:", null,
						LocaleContextHolder.getLocale());
			}
		} else if ((pmId.length() < 0)) {
			errorMessage =(StringUtil.isNullAndEmpty(errorMessage) ? "" : errorMessage + " , ") +  messageSource.getMessage("admin.common.valid.deviceLocalTxnTime", null,
					LocaleContextHolder.getLocale());
		} else {
			programManagerId = pmId;
			dataRequest.setPmId(Long.valueOf(pmId));
		}
		logger.info("Exiting :: SettlementReportController :: validatePMId");
		return errorMessage;
	}

	@RequestMapping(value = SETTLEMENT_MONEY_MOVEMENT, method = RequestMethod.GET)
	public ModelAndView showMoneyMoment(HttpServletRequest request, Map model, HttpSession session) {
		logger.info("Entering :: SettlementReportController :: showMoneyMoment");
		ModelAndView modelAndView = new ModelAndView(SETTLEMENT_MONEY_MOVEMENT);
		Long programViewId = (Long) session.getAttribute("programViewId");
		Timestamp date = (Timestamp) session.getAttribute("batchDate");
		try {
			TransactionResponse response = settlementReportService.calculateSettlementAmounts(programViewId, date);
			if (null != response) {
				
				Map<Long, EntityMappedTransactions> isoTotalRevenue = response.getIsoTotalRevenue();
				List<IsoSettlementDetails> isoDetailsList = new ArrayList<IsoSettlementDetails>();
				
				for (Map.Entry<Long, EntityMappedTransactions> entry : isoTotalRevenue.entrySet()) {
					Long isoId = entry.getKey();
					
					Iso isodetails = isoService.findIsoByIsoId(isoId).get(0);
					
					IsoSettlementDetails details = new IsoSettlementDetails();
					
					details.setBankAccNum(isodetails.getBankAccNum());
					details.setBankName(isodetails.getBankName());
					details.setIsoName(isodetails.getIsoName());
					details.setRoutingNumber(isodetails.getRoutingNumber());
					details.setCurrency(isodetails.getCurrency());

					BigDecimal isoTotalAmt = divideBigIntegerByHundered(entry.getValue().getTotalEntityAmount());
					details.setAmount(String.format("%.2f", isoTotalAmt));
					details.setpGTransactionIds(entry.getValue().getpGTransactionIds());

					isoDetailsList.add(details);
				}
				
				List<SettlementMerchantDetails> isoMappedMerchantDetails = new ArrayList<>();
				for (Map.Entry<String, EntityMappedTransactions> entryMap : response.getIsoMappedMerchantTotalRevenue().entrySet()) {
					String mId = entryMap.getKey();
					BigInteger amount = entryMap.getValue().getTotalEntityAmount();

					getMerchantDetails(isoMappedMerchantDetails, mId, amount, entryMap.getValue().getpGTransactionIds());
				}

				List<SettlementMerchantDetails> pmMappedMerchantDetails = new ArrayList<>();
				for (Map.Entry<String, EntityMappedTransactions> entryMap : response.getPmMappedMerchantTotalRevenue().entrySet()) {
					String mId = entryMap.getKey();
					BigInteger amount = entryMap.getValue().getTotalEntityAmount();
					getMerchantDetails(pmMappedMerchantDetails, mId, amount, entryMap.getValue().getpGTransactionIds());
				}
				
				modelAndView.addObject("isoTotalRevenue",response.getIsoTotalRevenue());
				logger.info("isoDetailsList : " + isoDetailsList + " isoMappedMerchantDetails : " + isoMappedMerchantDetails);
				if(StringUtil.isListNotNullNEmpty(isoDetailsList)) {
				logger.info("isoDetailsList : " + isoDetailsList.get(0).getCurrency());
				model.put("isoRevenueCurrency", isoDetailsList.get(0).getCurrency());
				}
				if(StringUtil.isListNotNullNEmpty(isoMappedMerchantDetails)) {
				model.put("isoCurrency", isoMappedMerchantDetails.get(0).getLocalCurrency());
				}
				if(StringUtil.isListNotNullNEmpty(pmMappedMerchantDetails)) {
				model.put("pmCurrency", pmMappedMerchantDetails.get(0).getLocalCurrency());
				}
				modelAndView.addObject("isoDetailsList", isoDetailsList);
				modelAndView.addObject("isoMappedMerchantTotalRevenue", isoMappedMerchantDetails);
				modelAndView.addObject("pmMappedMerchantTotalRevenue", pmMappedMerchantDetails);
				BigDecimal pmDebitAmount = divideBigIntegerByHundered(response.getPmDebitAmount());
				modelAndView.addObject("pmDebitAmount",String.format("%.2f", pmDebitAmount));
			}
		} catch (Exception e) {
		  logger.error("Error :: SettlementReportController :: showMoneyMoment : " + e.getMessage(), e);
		}
		logger.info("Exiting :: SettlementReportController :: showMoneyMoment");
		return modelAndView;
	}

	/**
	 * @param merchantDetails
	 * @param mId
	 * @param amount
	 */
	private void getMerchantDetails(List<SettlementMerchantDetails> merchantDetails, String mId, BigInteger amount,List<String> pGTransactionIds) {
		List<SettlementMerchantDetails> settlementMerchantDetails = settlementReportService
				.fetchMerchantDetailsByMerchantCode(mId);
		if (StringUtil.isListNotNullNEmpty(settlementMerchantDetails)) {
			SettlementMerchantDetails details = new SettlementMerchantDetails();
			
			for (SettlementMerchantDetails list : settlementMerchantDetails) {
				details.setBusinessName(list.getBusinessName());
				details.setMerchantCode(list.getMerchantCode());
				details.setBankAccountNumber(list.getBankAccountNumber());
				details.setBankNmae(list.getBankNmae());
				details.setBankRoutingNumber(list.getBankRoutingNumber());
				BigDecimal convertedTotalRevenue = divideBigIntegerByHundered(amount);
				details.setEntityTotalRevenueAmount(String.format("%.2f", convertedTotalRevenue));
				details.setLocalCurrency(list.getLocalCurrency());
				details.setpGTransactionIds(pGTransactionIds);
				merchantDetails.add(details);
			}
		}
	}

  private BigDecimal divideBigIntegerByHundered(BigInteger amount) {
    String amountStr = amount.toString();
    BigDecimal convertedAmt = new BigDecimal(amountStr);
    return convertedAmt.divide(new BigDecimal(Constants.ONE_HUNDRED));
  }
	  
	@RequestMapping(value = EXECUTE_SETTLEMENT_DATA)
	public ModelAndView executeSettlementTxn(HttpServletRequest request, Map model, HttpSession session) {
		logger.info("Entering :: SettlementReportController :: executeSettlementTxn");

		ModelAndView modelAndView = new ModelAndView(SETTLEMENT_MONEY_MOVEMENT);

		Long entityId = (Long) session.getAttribute("programViewId");
		String timeZoneOffset = request.getParameter("timeZoneOffset");
		String timeZoneRegion = request.getParameter("timeZoneRegion");
		logger.info("Executing settlement for entity "+entityId);

		try {
			TransactionResponse response = settlementReportService.executeSettlement(entityId, timeZoneOffset, timeZoneRegion);
			if (response.getErrorCode().equals(Constants.SUCCESS_CODE)) {
			  settlementReportService.insertDataFromIssuanceSettlementTransaction();
			  modelAndView.setViewName(CHATAK_ADMIN_HOME);
			  modelAndView.addObject("pendingMerchantSucess", messageSource
                  .getMessage("settlement.executed.success", null,
                          LocaleContextHolder.getLocale()));
			}else {
			  modelAndView.addObject(Constants.ERROR, messageSource.getMessage("settlement.executed.error", null, LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
		    logger.error("Error :: SettlementReportController :: executeSettlementTxn : " + e.getMessage(), e);
			modelAndView.addObject(Constants.ERROR, messageSource.getMessage("settlement.executed.error", null, LocaleContextHolder.getLocale()));
		}
		logger.info("Exiting :: SettlementReportController :: executeSettlementTxn");
		return modelAndView;
	}
	
	@RequestMapping(value = PREPAID_SHOW_MATCHED_TRANSACTIONS_PAGE, method = RequestMethod.POST)
	public ModelAndView showAllMatchedTxnsByPgTxnId(HttpSession session, @FormParam("pgTxnIds") final String pgTxnIds,
			@FormParam("getModelView") final String getModelView, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Entering :: SettlementReportController :: showAllMatchedTxnsByPgTxnId");
		ModelAndView modelAndView = new ModelAndView(FETCH_SETTLEMENT_DATA_BY_PMID);
		logger.info(pgTxnIds);
		logger.info(getModelView);
		if(!StringUtil.isNullAndEmpty(getModelView) && getModelView.equalsIgnoreCase(MONEY_MOMENT)) {
			modelAndView.setViewName(SETTLEMENT_MONEY_MOVEMENT);
		}
		FeeReportRequest transactionRequest = new FeeReportRequest();
		transactionRequest.setPgTxnIds(pgTxnIds);
		try {
			TransactionResponse transactionResponse = settlementReportService
					.getAllMatchedTxnsByPgTxns(transactionRequest);
			ExportDetails exportDetails = new ExportDetails();
			if (!StringUtil.isNull(transactionResponse)
					&& StringUtil.isListNotNullNEmpty(transactionResponse.getSettlementEntity())) {
				exportDetails.setExportType(ExportType.XLS);
				exportDetails.setExcelStartRowNumber(Integer.parseInt("5"));
				exportDetails.setReportName("Matched_Transactions_");
				exportDetails.setHeaderMessageProperty("matched-transactions.label.matchedtxns");
				exportDetails.setHeaderList(getSettlementReportHeaderList());
				exportDetails.setFileData(getSettlementReportFileData(transactionResponse.getSettlementEntity()));
				ExportUtil.exportData(exportDetails, response, messageSource);
				return null;
			} else {
				modelAndView = showMoneyMoment(request, model, session);
				modelAndView.addObject(Constants.ERROR, messageSource.getMessage(
						"matched-transactions.label.nomatchedtxns", null, LocaleContextHolder.getLocale()));
			}
		} catch (Exception e) {
		    logger.error("Error :: SettlementReportController :: showAllMatchedTxnsByPgTxnId : " + e.getMessage(), e);
			modelAndView = showMoneyMoment(request, model, session);
		}
		logger.info("Exiting :: SettlementReportController :: showAllMatchedTxnsByPgTxnId");
		return modelAndView;
	}
	
	private List<String> getSettlementReportHeaderList() {
		String[] headerArr = {
				messageSource.getMessage("reports.label.transactions.merchantcode", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("matched-transactions.label.terminal.id", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("home.label.transactionid", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("matched-transactions.label.transaction.type", null,
						LocaleContextHolder.getLocale()),
				messageSource.getMessage("home.label.transactiontime", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.common-deviceLocalTxnTime", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.pm.Name.message", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.iso.label.message", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.merchantamount", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.pmamount", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("admin.label.isoamount", null, LocaleContextHolder.getLocale()) };
		return new ArrayList<>(Arrays.asList(headerArr));
	}
	
	private static List<Object[]> getSettlementReportFileData(List<SettlementEntity> settlementDatas) {
		List<Object[]> fileData = new ArrayList<>();

		for (SettlementEntity settlementData : settlementDatas) {
			Object[] rowData = { settlementData.getMerchantId(), settlementData.getTerminalId(),
					settlementData.getPgTxnId(), settlementData.getTxnType(), settlementData.getTxnDate(),
					settlementData.getDeviceLocalTxnTime(), settlementData.getProgramManagerName(),settlementData.getIsoName(),
					(settlementData.getMerchantAmount()/Constants.ONE_HUNDRED), (settlementData.getPmAmount()/Constants.ONE_HUNDRED), (settlementData.getIsoAmount()/Constants.ONE_HUNDRED) };
			fileData.add(rowData);
		}
		logger.info("XL data size " + fileData.size());
		return fileData;
	}
	
	private Boolean validateFileExists(String programManagerName,  SettlementDataRequest settlementDataRequest) {
		ProgramManager programManager = settlementService.findByProgramMangerName(programManagerName).get(0);
		PGIssSettlementData issSettlementData = settlementReportService
				.findByAcqPmIdAndBatchDate(programManager.getId(), settlementDataRequest.getBatchDate()).get(0);
		if (issSettlementData.getStatus().equalsIgnoreCase(Constants.EXECUTED_STATUS)) {
			return false;
		}
		return true;
	}
	
}
