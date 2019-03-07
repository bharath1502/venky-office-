package com.chatak.pg.acq.service;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.validator.routines.CreditCardValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.EMVTransactionDao;
import com.chatak.pg.acq.dao.FeeDetailDao;
import com.chatak.pg.acq.dao.MerchantTerminalDao;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.SwitchTransactionDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGEMVTransaction;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGSwitchTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.spring.util.SpringDAOBeanFactory;
import com.chatak.pg.bean.AdjustmentRequest;
import com.chatak.pg.bean.AdjustmentResponse;
import com.chatak.pg.bean.AuthRequest;
import com.chatak.pg.bean.AuthResponse;
import com.chatak.pg.bean.BalanceEnquiryRequest;
import com.chatak.pg.bean.BalanceEnquiryResponse;
import com.chatak.pg.bean.CaptureRequest;
import com.chatak.pg.bean.CaptureResponse;
import com.chatak.pg.bean.CashBackRequest;
import com.chatak.pg.bean.CashBackResponse;
import com.chatak.pg.bean.CashWithdrawalRequest;
import com.chatak.pg.bean.CashWithdrawalResponse;
import com.chatak.pg.bean.PurchaseRequest;
import com.chatak.pg.bean.PurchaseResponse;
import com.chatak.pg.bean.RefundRequest;
import com.chatak.pg.bean.RefundResponse;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.ReversalResponse;
import com.chatak.pg.bean.VoidRequest;
import com.chatak.pg.bean.VoidResponse;
import com.chatak.pg.common.MessageTypeCode;
import com.chatak.pg.common.RandomGenerator;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.emv.util.EMVData;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.exception.ServiceException;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.JPOSUtil;
import com.chatak.pg.util.PGConstants;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.StringUtils;

import com.chatak.switches.sb.util.ProcessorConfig;


import com.litle.sdk.generate.MethodOfPaymentTypeEnum;

public class PaymentServiceImpl implements PaymentService {

	private static Logger log = Logger.getLogger(PaymentServiceImpl.class);

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private FeeDetailDao feeDetailDao;

	@Autowired
	private EMVTransactionDao emvTransactionDao;
	
	@Autowired
	private SwitchTransactionDao switchTransactionDao;
	
	@Autowired
	private SwitchDao switchDao;
	
	@Autowired
	private VoidTransactionDao voidtransactiondao;
	
	@Autowired
	private MerchantTerminalDao merchantTerminalDao;
	
	public PaymentServiceImpl() {
		AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
	}

	/**
	 * Method to Authorise a payment transaction
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param authRequest
	 * @return AuthResponse
	 * @throws ServiceException
	 */
	@Override
	public AuthResponse authTransaction(AuthRequest authRequest)
			throws ServiceException {
		log.debug("PaymentServiceImpl | authTransaction | Entering");
		AuthResponse authResponse = new AuthResponse();

		try{

			//validation of Request
			validateRequest(authRequest);

			String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
			String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_AUTH);
			Long txnTotalAmount = authRequest.getTxnAmount() + feeAmount;

			PGSwitchTransaction pgSwitchTransaction = null;
			PGTransaction pgTransaction = null;
			authRequest.setTxnRefNumber(txnRefNum);

			try{

				//Create Transaction record 
				pgTransaction = populatePGTransaction(authRequest);
				pgTransaction.setTransactionId(txnRefNum);
				pgTransaction.setAuthId(authId);
				pgTransaction.setTransactionType(PGConstants.TXN_TYPE_AUTH);
				pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
				pgTransaction.setFeeAmount(feeAmount);
				pgTransaction.setTxnTotalAmount(txnTotalAmount);
				pgTransaction.setCreatedDate(timestamp);
				pgTransaction.setUpdatedDate(timestamp);
				pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
				voidtransactiondao.createTransaction(pgTransaction);

				//Logging EMVTransation based on chipTransaction true
				if(authRequest.getChipTransaction()){
					logEmvTransaction(authRequest.getEmvData(), txnRefNum);
				}

				//Switch transaction log before Switch call
				pgSwitchTransaction = populateSwitchTransactionRequest(authRequest);
				pgSwitchTransaction.setPgTransactionId(txnRefNum);
				pgSwitchTransaction.setCreatedDate(timestamp);
				pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);

				//Switch interface call
				
				
			// Display Results
	 //     System.out.println("Litle Auth Transaction ID: " + litleResponse.getLitleTxnId());
	      String switchResponseCode = "";
        String switchResponseMessage = "";
	 
	        
	
	        
	
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
	
	        pgTransaction.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	        switchResponseCode = ActionCode.ERROR_CODE_00;
	        switchResponseMessage = ActionCode.ERROR_CODE_00;
	  		
				//Update transaction status and switch response
				voidtransactiondao.createTransaction(pgTransaction);

				//Set Response fields
				authResponse.setTxnRefNum(txnRefNum);
				authResponse.setAuthId(authId);
				authResponse.setTxnAmount(authRequest.getTxnAmount());
				authResponse.setFeeAmount(feeAmount);
				authResponse.setTotalAmount(txnTotalAmount);
				authResponse.setErrorCode(switchResponseCode);
				authResponse.setErrorMessage(switchResponseMessage);

			}catch(Exception e){
				log.error("Exception :"+ e);
				pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
				switchTransactionDao.createTransaction(pgSwitchTransaction);

				pgTransaction.setStatus(PGConstants.STATUS_FAILED);
				voidtransactiondao.createTransaction(pgTransaction);

				authResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
				authResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			}
			
			//Required to set in reversal 
			authResponse.setTxnRefNum(txnRefNum);

		} catch (ServiceException e) {
			authResponse.setErrorCode(e.getMessage());
			authResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
			log.error("PaymentServiceImpl | authTransaction | ServiceException :"+e.getMessage(),e);
		} catch (DataAccessException e) {
			authResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			authResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | authTransaction | DataAccessException :"+e.getMessage(),e);
		} catch (Exception e) {
			authResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			authResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | authTransaction | Exception :"+e.getMessage(),e);
		} 

		log.debug("PaymentServiceImpl | authTransaction | Exiting");
		return authResponse;
	}


	/**
	 * Method to Capture the Authorised Payment Transaction
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param captureRequest
	 * @return CaptureResponse
	 * @throws ServiceException
	 */
	@Override
	public CaptureResponse captureTransaction(CaptureRequest captureRequest)
			throws ServiceException {
		log.debug("PaymentServiceImpl | captureTransaction | Entering");
		CaptureResponse captureResponse = new CaptureResponse();

		try{

			//validation of Request
			validateRequest(captureRequest);

			String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
			String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_SALE);
			Long txnTotalAmount = captureRequest.getTxnAmount() + feeAmount;

			PGSwitchTransaction pgSwitchTransaction = null;
			PGTransaction pgTransaction = null;
			captureRequest.setTxnRefNumber(txnRefNum);
			try{
				
				//Fetch the auth transaction
				PGTransaction authTransaction = voidtransactiondao.getAuthTransaction(
						captureRequest.getMerchantId().toString(),
						captureRequest.getTerminalId(),
						captureRequest.getAuthTxnRefNum(),
						PGConstants.TXN_TYPE_AUTH, 
						captureRequest.getAuthId());
				if(authTransaction == null){
					throw new ServiceException(ActionCode.ERROR_CODE_78);
				}

				//Create Transaction record 
				pgTransaction = populatePGTransaction(captureRequest);
				pgTransaction.setTransactionId(txnRefNum);
				pgTransaction.setRefTransactionId(authTransaction.getTransactionId());
				pgTransaction.setAuthId(authId);
				pgTransaction.setTransactionType(PGConstants.TXN_TYPE_SALE);
				pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
				pgTransaction.setFeeAmount(feeAmount);
				pgTransaction.setTxnTotalAmount(txnTotalAmount);
				pgTransaction.setCreatedDate(timestamp);
				pgTransaction.setUpdatedDate(timestamp);
				pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
				
				voidtransactiondao.createTransaction(pgTransaction);

				//Logging EMVTransation based on chipTransaction true
				if(captureRequest.getChipTransaction()){
					logEmvTransaction(captureRequest.getEmvData(), txnRefNum);
				}

				//Switch transaction log before Switch call
				pgSwitchTransaction = populateSwitchTransactionRequest(captureRequest);
				pgSwitchTransaction.setPgTransactionId(txnRefNum);
				pgSwitchTransaction.setCreatedDate(timestamp);
				pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);

				
			//Switch interface call
       
      // Display Results
   
    //    System.out.println("Litle Capture Transaction ID: " + litleResponse.getLitleTxnId());
        String switchResponseCode = "";
        String switchResponseMessage = "";
      
				
				//Update transaction status and switch response
				voidtransactiondao.createTransaction(pgTransaction);

				//Set Response fields
				captureResponse.setTxnRefNum(txnRefNum);
				captureResponse.setAuthId(authId);
				captureResponse.setTxnAmount(captureRequest.getTxnAmount());
				captureResponse.setFeeAmount(feeAmount);
				captureResponse.setTotalAmount(txnTotalAmount);
				captureResponse.setErrorCode(switchResponseCode);
				captureResponse.setErrorMessage(switchResponseMessage);

			}catch(Exception e){
				log.error("Exception :"+ e);
				pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
				switchTransactionDao.createTransaction(pgSwitchTransaction);

				pgTransaction.setStatus(PGConstants.STATUS_FAILED);
				voidtransactiondao.createTransaction(pgTransaction);

				captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
				captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			}
			
			//Required to set in reversal 
			captureResponse.setTxnRefNum(txnRefNum);
			
		} catch (ServiceException e) {
			captureResponse.setErrorCode(e.getMessage());
			captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
			log.error("PaymentServiceImpl | captureTransaction | ServiceException :"+e.getMessage(),e);
		} catch (DataAccessException e) {
			captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | captureTransaction | DataAccessException :"+e.getMessage(),e);
		} catch (Exception e) {
			captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | captureTransaction | Exception :"+e.getMessage(),e);
		} 

		log.debug("PaymentServiceImpl | captureTransaction | Exiting");
		return captureResponse;
	}

	/**
	 * Method to Auth-Capture Payment Transaction
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param purchaseRequest
	 * @return PurchaseResponse
	 * @throws ServiceException
	 */
	@Override
	public PurchaseResponse purchaseTransaction(PurchaseRequest purchaseRequest)
			throws ServiceException {
		log.debug("PaymentServiceImpl | purchaseTransaction | Entering");
		PurchaseResponse purchaseResponse = new PurchaseResponse();

		try{
			
			//validation of Request
			validateRequest(purchaseRequest);

			String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
			String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_SALE);
			Long txnTotalAmount = purchaseRequest.getTxnAmount() + feeAmount;
			
			PGSwitchTransaction pgSwitchTransaction = null;
			PGTransaction pgTransaction = null;
			purchaseRequest.setTxnRefNumber(txnRefNum);
			
			try{
				
				//Create Transaction record 
				pgTransaction = populatePGTransaction(purchaseRequest);
				pgTransaction.setTransactionId(txnRefNum);
				pgTransaction.setAuthId(authId);
				pgTransaction.setTransactionType(PGConstants.TXN_TYPE_SALE);
				pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
				pgTransaction.setFeeAmount(feeAmount);
				pgTransaction.setTxnTotalAmount(txnTotalAmount);
				pgTransaction.setCreatedDate(timestamp);
				pgTransaction.setUpdatedDate(timestamp);
				pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
				voidtransactiondao.createTransaction(pgTransaction);

				//Logging EMVTransation based on chipTransaction true
				if(purchaseRequest.getChipTransaction()){
					logEmvTransaction(purchaseRequest.getEmvData(), txnRefNum);
				}
				
				//Switch transaction log before Switch call
				pgSwitchTransaction = populateSwitchTransactionRequest(purchaseRequest);
				pgSwitchTransaction.setPgTransactionId(txnRefNum);
				pgSwitchTransaction.setCreatedDate(timestamp);
				pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
				
				
			//Switch interface call
       
        
      // Display Results
      
        String switchResponseCode = "";
        String switchResponseMessage = "";
       
				//Update transaction status and switch response
				voidtransactiondao.createTransaction(pgTransaction);
				
				//Set Response fields
				purchaseResponse.setTxnRefNum(txnRefNum);
				purchaseResponse.setAuthId(authId);
				purchaseResponse.setTxnAmount(purchaseRequest.getTxnAmount());
				purchaseResponse.setFeeAmount(feeAmount);
				purchaseResponse.setTotalAmount(txnTotalAmount);
				purchaseResponse.setErrorCode(switchResponseCode);
				purchaseResponse.setErrorMessage(switchResponseMessage);
				
			}catch(Exception e){
				log.error("Exception :"+ e);
				pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
				switchTransactionDao.createTransaction(pgSwitchTransaction);
				
				pgTransaction.setStatus(PGConstants.STATUS_FAILED);
				voidtransactiondao.createTransaction(pgTransaction);
				
				purchaseResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
				purchaseResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			}
			
			//Required to set in reversal 
			purchaseResponse.setTxnRefNum(txnRefNum);

		} catch (ServiceException e) {
			purchaseResponse.setErrorCode(e.getMessage());
			purchaseResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
			log.error("PaymentServiceImpl | purchaseTransaction | ServiceException :"+e.getMessage(),e);
		} catch (DataAccessException e) {
			purchaseResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			purchaseResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | purchaseTransaction | DataAccessException :"+e.getMessage(),e);
		} catch (Exception e) {
			purchaseResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			purchaseResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | purchaseTransaction | Exception :"+e.getMessage(),e);
		} 

		log.debug("PaymentServiceImpl | purchaseTransaction | Exiting");
		return purchaseResponse;
	}

	/**
	 * Method to Adjust to successfull Payment Transaction (Tip Adjustment, sale amount adjustment)
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Fetch original transaction record
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param adjustmentRequest
	 * @return AdjustmentResponse
	 * @throws ServiceException
	 */
	@Override
	public AdjustmentResponse adjustmentTransaction(AdjustmentRequest adjustmentRequest)
			throws ServiceException {return null;}


	/**
	 * Method to Void/Cancel an successfull sale/refund transaction
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param voidRequest
	 * @return VoidResponse
	 * @throws ServiceException
	 */
	public VoidResponse voidTransaction (VoidRequest voidRequest) throws ServiceException {
		log.debug("PaymentServiceImpl | voidTransaction | Entering");
		VoidResponse voidResponse = new VoidResponse();
		try {
			
			//validation of Request
			validateRequest(voidRequest);

			String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
			String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_VOID);
			Long txnTotalAmount = voidRequest.getTxnAmount() + feeAmount;
			
			PGSwitchTransaction pgSwitchTransaction = null;
			PGTransaction pgTransaction = null;
			try{
				//Fetch original transaction
				PGTransaction saleOrRefundransaction = voidtransactiondao.getTransactionToVoid(
						voidRequest.getMerchantId().toString(), voidRequest.getTerminalId(),
						voidRequest.getTxnRefNum(),
						voidRequest.getAuthId());
				if(saleOrRefundransaction == null){
					throw new Exception(ActionCode.ERROR_CODE_78);
				}
				
				//Create Transaction record 
				pgTransaction = populatePGTransaction(voidRequest);
				pgTransaction.setTransactionId(txnRefNum);
				pgTransaction.setRefTransactionId(voidRequest.getTxnRefNum());
				pgTransaction.setAuthId(authId);
				pgTransaction.setTransactionType(PGConstants.TXN_TYPE_VOID);
				pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
				pgTransaction.setFeeAmount(saleOrRefundransaction.getFeeAmount());
				pgTransaction.setTxnTotalAmount(voidRequest.getTxnAmount() + saleOrRefundransaction.getFeeAmount());
				pgTransaction.setCreatedDate(timestamp);
				pgTransaction.setUpdatedDate(timestamp);
				pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
				voidtransactiondao.createTransaction(pgTransaction);

				//Logging EMVTransation based on chipTransaction true
				if(voidRequest.getChipTransaction()){
					logEmvTransaction(voidRequest.getEmvData(), txnRefNum);
				}
				
			//Switch interface call
     
        
      // Display Results
      
        String switchResponseCode = "";
        String switchResponseMessage = "";
     
      
			
				//Update transaction status and switch response
				voidtransactiondao.createTransaction(pgTransaction);
				
				//Set Response fields
				voidResponse.setTxnRefNum(txnRefNum);
				voidResponse.setAuthId(authId);
				voidResponse.setErrorCode(switchResponseCode);
				voidResponse.setErrorMessage(switchResponseMessage);
				
			}catch(Exception e){
				log.error("Exception :"+ e);
				pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
				switchTransactionDao.createTransaction(pgSwitchTransaction);
				
				pgTransaction.setStatus(PGConstants.STATUS_FAILED);
				voidtransactiondao.createTransaction(pgTransaction);
				
				voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
				voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			}
			
			//Required to set in reversal 
			voidResponse.setTxnRefNum(txnRefNum);

		} catch (ServiceException e) {
			voidResponse.setErrorCode(e.getMessage());
			voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
			log.error("PaymentServiceImpl | voidTransaction | ServiceException :"+e.getMessage(),e);
		} catch (DataAccessException e) {
			voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | voidTransaction | DataAccessException :"+e.getMessage(),e);
		} catch (Exception e) {
			voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
			voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
			log.error("PaymentServiceImpl | voidTransaction | Exception :"+e.getMessage(),e);
		} 
		log.debug("PaymentServiceImpl | voidTransaction | Exiting");
		return voidResponse;
	}


	/**
	 * Method to Reverse a transaction 
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param reversalRequest
	 * @return ReversalResponse
	 * @throws ServiceException
	 */
	public ReversalResponse reversalTransaction (ReversalRequest reversalRequest) throws ServiceException {return null;}

	/**
	 * Method to Refund a transaction 
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param reversalRequest
	 * @return ReversalResponse
	 * @throws ServiceException
	 */
	public RefundResponse refundTransaction (RefundRequest refundRequest) throws ServiceException {return null;}


	/**
	 * Method to validate the card transaction request
	 * @param request
	 * @return boolean
	 * @throws ServiceException
	 */
	private boolean validateRequest(Request request) throws ServiceException{
		log.debug("PaymentServiceImpl | validateRequest | Entering");
		boolean status = false;
		try{

			String cardNumber = request.getCardNum().trim();
			CreditCardValidator ccValidator = new CreditCardValidator(
					CreditCardValidator.VISA 
					+ CreditCardValidator.AMEX
					+ CreditCardValidator.DISCOVER
					+ CreditCardValidator.MASTERCARD);

			if(!ccValidator.isValid(cardNumber)){
				
			} else if(!PGUtils.isValidCardExpiryDate(request.getExpDate())){
				throw new ServiceException(ActionCode.ERROR_CODE_54);
			}
			PGMerchant pgMerchant = merchantTerminalDao.validateMerchantIdAndTerminalId(request.getMerchantId().toString(), request.getTerminalId());
			if(null == pgMerchant){
        throw new ServiceException(ActionCode.ERROR_CODE_03);
      }

			//TODO: Other card validations to be performed here
		} catch (NumberFormatException e) {
			log.error("PaymentServiceImpl | validateRequest | NumberFormatException :"+e.getMessage(), e);
			throw new ServiceException(ActionCode.ERROR_CODE_14);
		} catch (DataAccessException e) {
			log.error("PaymentServiceImpl | validateRequest | DataAccessException :"+e.getMessage(), e);
			throw new ServiceException(ActionCode.ERROR_CODE_Z5);
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage());
		} catch (Exception e) {
			log.error("PaymentServiceImpl | validateRequest | Exception :"+e.getMessage(), e);
			throw new ServiceException(ActionCode.ERROR_CODE_Z5);
		}
		log.debug("PaymentServiceImpl | validateRequest | Exiting");
		return status;
	}

	/**
	 * Method used to log EMV Transaction data
	 * @param emvData
	 * @param txnRefNumber
	 */
	private void logEmvTransaction(EMVData emvData, String txnRefNumber){

		PGEMVTransaction pgemvTransaction = new PGEMVTransaction();
		pgemvTransaction.setAed(emvData.getAed());
		pgemvTransaction.setAid(emvData.getAid());
		pgemvTransaction.setAip(emvData.getAip());
		pgemvTransaction.setAtc(emvData.getAtc());
		pgemvTransaction.setCvrm(emvData.getCvmr());
		pgemvTransaction.setFci(emvData.getFci());
		pgemvTransaction.setFcip(emvData.getFcip());
		pgemvTransaction.setIad(emvData.getIad());
		pgemvTransaction.setIfd(emvData.getIfd());
		pgemvTransaction.setIsr(emvData.getIsr());
		pgemvTransaction.setIst(emvData.getIst());
		pgemvTransaction.setIst1(emvData.getIst_1());
		pgemvTransaction.setLanRef(emvData.getLan());
		pgemvTransaction.setPgTransactionId(txnRefNumber);
		pgemvTransaction.setPsn(emvData.getPsn());
		pgemvTransaction.setTcc(emvData.getTcc());
		pgemvTransaction.setTsn(emvData.getTsn());
		pgemvTransaction.setTvr(emvData.getTvr());
		pgemvTransaction.setTxnStatusInfo(emvData.getTxnStatusInfo());
		pgemvTransaction.setUnPredNumber(emvData.getUnPredictableNum());

		emvTransactionDao.createTransaction(pgemvTransaction);

	}
	
	/**
	 * Method used to populate PGSwitchTransaction object with request object
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private PGSwitchTransaction populateSwitchTransactionRequest(Request request) throws Exception{
		PGSwitchTransaction pgSwitchTransaction = new PGSwitchTransaction();
		pgSwitchTransaction.setTxnAmount(request.getTxnAmount());
		pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
		pgSwitchTransaction.setPanMasked(StringUtils.getMaskedString(request.getCardNum(), 5, 4));
		pgSwitchTransaction.setPosEntryMode(request.getPosEntryMode());
		pgSwitchTransaction.setPan(EncryptionUtil.encrypt(request.getCardNum()));
		switchTransactionDao.createTransaction(pgSwitchTransaction);
		
		String proccode = (String)request.getIsoMsg().getValue(3);
		String mti = request.getIsoMsg().getMTI();
		//online and offline void cases
		//in case of offline void mti will be 220 and field 39 - appr code is 00
		if(mti.equals(MessageTypeCode.ONLINE_REQUEST) && (proccode.equals(MessageTypeCode.PROC_CODE_REFUND_ADJUSTMENT) || proccode.equals(MessageTypeCode.PROC_CODE_VOID) || proccode.equals(MessageTypeCode.PROC_CODE_BALANCE_ENQUIRY))){
			request.getIsoMsg().setMTI("420");
			//request.getIsoMsg().set(39, "00");//TODO: Response code 
			request.getIsoMsg().set(60, "0000");//TODO: Switch expected fields for reversal
			request.getIsoMsg().set(90, "0000000");//TODO: Switch expected fields for reversal
			
			if(proccode.equals(MessageTypeCode.PROC_CODE_REFUND_ADJUSTMENT)){
				request.getIsoMsg().set(12,DateUtils.getLocalTransactionTime());//TODO: local time of transaction origination hhmmss
				request.getIsoMsg().set(13,DateUtils.getLocalTransactionDate());//TODO: local date of transaction origination MMDD
			}
			if(proccode.equals(MessageTypeCode.PROC_CODE_BALANCE_ENQUIRY)){
				request.getIsoMsg().setMTI("200");
				request.getIsoMsg().set(4,"00");//TODO: Amount field
				request.getIsoMsg().set(12,DateUtils.getLocalTransactionTime());//TODO: local time of transaction origination hhmmss
				request.getIsoMsg().set(13,DateUtils.getLocalTransactionDate());//TODO: local date of transaction origination MMDD
				request.getIsoMsg().set(29,"1");//TODO: Acquirer fee
				
				request.getIsoMsg().set(18, "1001");
				request.getIsoMsg().set(37, request.getIsoMsg().getString(37)!=null?request.getIsoMsg().getString(37):request.getTxnRefNumber());//Retrieval Reference Number
				request.getIsoMsg().set(43, "chatak Acquirer");//TODO: Card Acceptor Name & Location
				request.getIsoMsg().set(48, "chatak merchant");//TODO: Merchant/Bank Name
				request.getIsoMsg().set(57, "220");//TODO: Auth life cycle
				request.getIsoMsg().set(59,"11111");//TODO: National Pointof-Service Geographic Data an..17
			}
		}else if (mti.equals(MessageTypeCode.REVERSAL_REQUEST)){ 
			request.getIsoMsg().setMTI("420");
			request.getIsoMsg().set(60, "0000");//TODO: Switch expected fields for reversal
			request.getIsoMsg().set(90, "0000000");//TODO: Switch expected fields for reversal
			request.getIsoMsg().set(4,"00");//TODO: Amount field
			request.getIsoMsg().set(12,DateUtils.getLocalTransactionTime());//TODO: local time of transaction origination hhmmss
			request.getIsoMsg().set(13,DateUtils.getLocalTransactionDate());//TODO: local date of transaction origination MMDD
			request.getIsoMsg().set(29,"1");//TODO: Acquirer fee
			
			request.getIsoMsg().set(18, "1001");
			request.getIsoMsg().set(37, request.getIsoMsg().getString(37));//Retrieval Reference Number
			request.getIsoMsg().set(43, "chatak Acquirer");//TODO: Card Acceptor Name & Location
			request.getIsoMsg().set(48, "chatak merchant");//TODO: Merchant/Bank Name
			request.getIsoMsg().set(57, "220");//TODO: Auth life cycle
			request.getIsoMsg().set(59,"11111");//TODO: National Pointof-Service Geographic Data an..17
		}else{
			request.getIsoMsg().set(12,DateUtils.getLocalTransactionTime());//TODO: local time of transaction origination hhmmss
			request.getIsoMsg().set(13,DateUtils.getLocalTransactionDate());//TODO: local date of transaction origination MMDD
			request.getIsoMsg().set(29,"1");//TODO: Acquirer fee
			
			request.getIsoMsg().set(18, "1001");
			request.getIsoMsg().set(37, request.getIsoMsg().getString(37)!=null?request.getIsoMsg().getString(37):request.getTxnRefNumber());//Retrieval Reference Number
			request.getIsoMsg().set(43, "chatak Acquirer");//TODO: Card Acceptor Name & Location
			request.getIsoMsg().set(48, "chatak merchant");//TODO: Merchant/Bank Name
			request.getIsoMsg().set(57, "220");//TODO: Auth life cycle
			request.getIsoMsg().set(59,"11111");//TODO: National Pointof-Service Geographic Data an..17
		}
		
		request.getIsoMsg().set(3, proccode.substring(0, 2)+"1010");
		request.getIsoMsg().set(7, DateUtils.getTransmissionDate());//TODO:set transmission date and time MMDDhhmmss
		String posEntryMode = (String)request.getIsoMsg().getValue(22);
		request.getIsoMsg().set(22, posEntryMode.substring(1,4));
		
		String panSequenceNumber = (String)request.getIsoMsg().getValue(23);
		if(panSequenceNumber!=null){
			request.getIsoMsg().set(23, panSequenceNumber.substring(1,4));
		}
		
		request.getIsoMsg().set(15,DateUtils.getSettlementTransactionDate(new Date()));//TODO: settlement date of transaction  MMDD
		request.getIsoMsg().set(49, "840");//TODO: Currency Code, Transaction
		request.getIsoMsg().set(32, "1111");
		request.getIsoMsg().set(58, "11111");//TODO: National Pointof-Service Condition Code an..11

		String functionCode = (String)request.getIsoMsg().getValue(24);
		request.getIsoMsg().set(24, functionCode.substring(1,4));
		
		
		JPOSUtil.logISOData(request.getIsoMsg(), log);
		return pgSwitchTransaction;
	}
	
	/**
	 * Method used to populate PGTransaction object with request object
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private PGTransaction populatePGTransaction(Request request) throws Exception{
		PGTransaction pgTransaction = new PGTransaction();
		pgTransaction.setSysTraceNum(request.getSysTraceNum());
		pgTransaction.setTransactionType(PGConstants.TXN_TYPE_SALE);
		pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
		pgTransaction.setTxnAmount(request.getTxnAmount());
		pgTransaction.setMerchantId(request.getMerchantId().toString());
		pgTransaction.setTerminalId(request.getTerminalId());
		pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
		pgTransaction.setInvoiceNumber(request.getInvoiceNumber());
		pgTransaction.setAcqChannel(request.getAcq_channel());
		pgTransaction.setAcqTxnMode(request.getAcq_mode());
		pgTransaction.setMti(request.getMti());
		pgTransaction.setProcCode(request.getProcessingCode());
		pgTransaction.setChipTransaction(request.getChipTransaction()?1:0);
		pgTransaction.setChipFallbackTransaction(request.getChipFallback()?1:0);
		pgTransaction.setPanMasked(StringUtils.getMaskedString(request.getCardNum(), 5, 4));
		pgTransaction.setPan(EncryptionUtil.encrypt(request.getCardNum()));
		pgTransaction.setProcessor(ProcessorType.LITLE.value());
		pgTransaction.setExpDate(request.getExpDate()!=null?Long.valueOf(request.getExpDate()):null);
		return pgTransaction;
	}
	
	public BalanceEnquiryResponse balanceEnquiryTransaction(BalanceEnquiryRequest balanceEnquiryRequest)
			throws ServiceException {return null;}
	
	/**
	 * Method to Cash withdrawal Payment Transaction
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param purchaseRequest
	 * @return PurchaseResponse
	 * @throws ServiceException
	 */
	public CashWithdrawalResponse cashWithdrawalTransaction(CashWithdrawalRequest cashWithdrawalRequest)
			throws ServiceException {return null;}
	
	/**
	 * Method to Cash back Transaction
	 * Steps Involved
	 * 1. Validate Request
	 * 2. Create Transaction record 
	 * 3. Create switch Transaction record
	 * 4. Call Switch interface
	 * 5. Update transaction and switch transaction record status 
	 * 4. SET response fields
	 * 5. return response
	 * @param CashBackRequest
	 * @return CashBackResponse
	 * @throws ServiceException
	 */
	public CashBackResponse cashBackTransaction(CashBackRequest cashBackRequest)
			throws ServiceException {return null;}
	
	
}
