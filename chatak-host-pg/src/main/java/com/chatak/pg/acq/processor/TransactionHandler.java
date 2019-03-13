package com.chatak.pg.acq.processor;

import java.net.Socket;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.chatak.pg.acq.dao.ActivityLogDao;
import com.chatak.pg.acq.dao.EMVTransactionDao;
import com.chatak.pg.acq.dao.model.PGActivityLog;
import com.chatak.pg.acq.spring.util.SpringDAOBeanFactory;
import com.chatak.pg.exception.TransactionException;

/**
 * This class encapsulates the properties and operations of the life
 * of a Payment Gateway Authorization Service (PGAS) transaction which
 * includes invoking the Parser, calling the Authorizer, and
 * formatting a proper Response.
 */
public class TransactionHandler {

	@Autowired
	private ActivityLogDao activityLogDao;

	// The request for this particular transaction
	private RequestMessage mRequestMessage;

	// The response for this particular transaction
	private ResponseMessage mResponseMessage;

	// Logger for TransactionHandler
	private Logger logger = Logger.getLogger(TransactionHandler.class);;

	private TxnAuthorizer _txnAuthorizer;

	@Autowired
	private EMVTransactionDao emvTransactionDao;


	public TransactionHandler() {
		AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
	}

	/**
	 * Default constructor
	 */
	public TransactionHandler(Socket mSocket) {
		AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
		mRequestMessage = new RequestMessage();
		mResponseMessage = new ResponseMessage();
	}

	public TransactionHandler init(Socket mSocket) {
		mRequestMessage = new RequestMessage();
		mResponseMessage = new ResponseMessage();
		return this;
	}

	/**
	 * This method is used to process the Transaction Request. Input buffer is
	 * pure (validated) transaction request i.e. received transaction request that
	 * is unpacked by PGFormatter. 1. Call RequestMessage.generateISOMessage to
	 * validate and generate a properly formatted ISO Message 2. Call Authorize to
	 * authorize the transaction according to the authorizing rules after checking
	 * for all validity conditions. 3. Build a response...along the way ??
	 * 
	 * @param txnRequest
	 * @return byte[]
	 * @throws TransactionException
	 */
	public byte[] processTransaction(byte[] txnRequest) throws TransactionException {
		logger.info("================= START PROCESS TRANSACTION ==============");
		byte[] txnResponse = null;

		// 1. Call RequestMessage.generateISOMessage to validate and generate a properly formatted ISO Message
		try {
			if(mRequestMessage.generateISOMessage(txnRequest)) {

				logger.info("ISO REQUEST MSG VALIDATED >>>");

				// 2. Call Authorizer which authorizes the entire data according to the authorizing rules
				_txnAuthorizer = new TxnAuthorizer(this);
				_txnAuthorizer.processTxn();

				// 3. Call response message formatter
				txnResponse = mResponseMessage.generateFormattedResponse();
				logger.info("Txn Response : " + txnResponse);
				
				//Log Request and Response to ActivityLog table
				logActivity();

			} else {

				logger.info("TransactionHandler | processTransaction| ISO REQUEST MSG NOT VALIDATED");
				logger.error("ISO REQUEST MSG NOT VALIDATED");

			}
		}
		catch(ISOException e) {
			logger.error("ERROR:: processTransaction method: "+e.getMessage(), e);
		}
		catch(Exception e) {
			logger.error("ERROR:: processTransaction method: "+e.getMessage(), e);
		}

		if(txnResponse == null) {
			logger.info("================= END PROCESS TRANSACTION EMPTY==============");
			throw new TransactionException("Invalid Response Message");
		}

		logger.info("================= END PROCESS TRANSACTION ==============");
		return txnResponse;
	}

	/**
	 * Returns the RequestMessage value
	 * 
	 * @return RequestMessage.
	 */
	public RequestMessage getRequestMessage() {
		return mRequestMessage;
	}

	/**
	 * Returns the ResponseMessage value
	 * 
	 * @return ResponseMessage.
	 */
	public ResponseMessage getResponseMessage() {
		return mResponseMessage;
	}

	/**
	 * Save the request and response in xml format
	 * 
	 * @param gatewayRequest
	 * @param gatewayResponse
	 */
	private void logActivity() {
		logger.info("Entering :: TransactionHandler :: logActivity");
		PGActivityLog pgActivityLog = new PGActivityLog();

		//set Request packet details

		pgActivityLog.setRequestIP(mRequestMessage.getGatewayRequest().getRequestIP());
		pgActivityLog.setRequestPort(mRequestMessage.getGatewayRequest().getRequestPort());
		if(null != _txnAuthorizer.get_ISOInputRequest()) {
			pgActivityLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			pgActivityLog.setProcessingCode(_txnAuthorizer.get_ISOInputRequest().get_processingCode());
			pgActivityLog.setSysTraceNum(_txnAuthorizer.get_ISOInputRequest().get_sysTraceNum());
			pgActivityLog.setChipTransaction(_txnAuthorizer.get_ISOInputRequest().get_isChipTransaction()?1:0);
			pgActivityLog.setPosEntryMode(_txnAuthorizer.get_ISOInputRequest().get_Field22());

			pgActivityLog.setF39(_txnAuthorizer.get_ISOInputRequest().get_field39());

			pgActivityLog.setTxnAmount(_txnAuthorizer.get_ISOInputRequest().get_txnAmount());
			pgActivityLog.setAdjAmount(_txnAuthorizer.get_ISOInputRequest().get_adjustedTxnAmount());
			pgActivityLog.setMti(_txnAuthorizer.get_ISOInputRequest().get_MTI());
			pgActivityLog.setPan(_txnAuthorizer.get_ISOInputRequest().get_cardNum());
			pgActivityLog.setPanMasked(_txnAuthorizer.get_ISOInputRequest().get_cardNum());
			if(_txnAuthorizer.get_ISOInputRequest().get_expDate()!=null){
				pgActivityLog.setExpDate(Long.valueOf(_txnAuthorizer.get_ISOInputRequest().get_expDate()));
			}
			pgActivityLog.setF55(_txnAuthorizer.get_ISOInputRequest().get_Field55());
		}

		
		logger.info("Before committing pgActivityLog :: TransactionHandler :: logActivity");
		//get Dao instance 
		activityLogDao.logRequest(pgActivityLog);
		logger.info("Exiting :: TransactionHandler :: logActivity");
	}

}