package com.chatak.pg.acq.processor;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.chatak.pg.acq.dao.MerchantTerminalDao;
import com.chatak.pg.acq.dao.impl.MerchantTerminalDaoImpl;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.spring.util.SpringDAOBeanFactory;
import com.chatak.pg.bean.ISOInRequest;
import com.chatak.pg.common.MessageTypeCode;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.emv.util.EMVData;
import com.chatak.pg.exception.InvalidMerchantException;
import com.chatak.pg.exception.MagneticStripeParseException;
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.sb.util.ProcessorConfig;

/**
 * @Comments : This class encapsulates the properties and operations of the life
 *           of a transaction along with the business objects required to
 *           process and authorize that transaction.
 */
public class TxnAuthorizer implements MessageTypeCode {

  private Logger logger = Logger.getLogger(TxnAuthorizer.class);
  
  @Autowired
  MerchantTerminalDao merchantTerminalDao;
   
  
  public TxnAuthorizer(){
  AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
  acbFactory.autowireBean(this);
  }

  private TransactionHandler _txnHandler;
  
  private ISOInRequest _ISORequest;

  private String _autoMessage = null;

  private String _actionCode = null;

  private RequestMessage _requestMessage = null;

  private ISOMsg _requestIsoMsg = null;

  private String responseMTI = "";
  
  private String mode = ProcessorConfig.DEMO;
 

  /**
   * Constructor which accepts the TransactionHandler as a parameter
   * 
   * @param TransactionHandler
   */
  public TxnAuthorizer(TransactionHandler txnHandlerTemp) {
	  AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
	  acbFactory.autowireBean(this);
    _txnHandler = txnHandlerTemp;
    if(_txnHandler != null) {
      _requestMessage = _txnHandler.getRequestMessage();
      if(_requestMessage != null)
        _requestIsoMsg = _requestMessage.getISOMessage();
    }
  }

  /**
   * Process transaction request and generate response
   */
  public void processTxn() {
    logger.debug("TxnAuthorizer | processTxn | Entering");
    boolean validated = populateRequestMessage();
    
    if(validated) {

      logger.info("xxxxxxx REQUEST MESSAGE RIGHT FORMAT xxxxxxx");
      // 0800 Network Management Request
      if(_ISORequest.get_MTI().equalsIgnoreCase(NETWORK_REQUEST)) {
        responseMTI = NETWORK_RESPONSE;
        processNetworkTransaction();
      }
      // 0100 Authorization request
      else if(_ISORequest.get_MTI().equalsIgnoreCase(AUTHORIZATION_REQUEST)) {
        responseMTI = AUTHORIZATION_RESPONSE;
        processFinancialAuthTransaction();
      }
      // 0200 Acquirer Financial Request
      else if(_ISORequest.get_MTI().equalsIgnoreCase(ONLINE_REQUEST)) {
        responseMTI = ONLINE_RESPONSE;
        processFinancialTransaction();
      }
      // 0220 Acquirer Financial Advice
      else if(_ISORequest.get_MTI().equalsIgnoreCase(OFFLINE_REQUEST)) {
        responseMTI = OFFLINE_RESPONSE;
        processFinancialAdviceTransactions();
      }
      // 0400 Acquirer Reversal Request
      else if(_ISORequest.get_MTI().equalsIgnoreCase(REVERSAL_REQUEST)) {
        responseMTI = REVERSAL_RESPONSE;
        processReversalTransaction();
      }
      // 0500 Batch Settlement request
      else if(_ISORequest.get_MTI().equalsIgnoreCase(RECONCILATION_REQUEST)) {
        responseMTI = RECONCILATION_RESPONSE;
        processFinancialSettlementTransaction();
      }
      else {
        logger.info("xxxxxxx REQUEST MESSAGE WRONG FORMAT xxxxxxx");
        if(_actionCode == null) {
          _actionCode = ActionCode.ERROR_CODE_96;
        }

        if(_autoMessage == null) {
          _autoMessage = new ActionCode().getMessage(ActionCode.ERROR_CODE_96);
        }
      }
    }
    else {
      logger.info("xxxxxxx REQUEST MESSAGE WRONG FORMAT xxxxxxx");
      if(_actionCode == null) {
        _actionCode = ActionCode.ERROR_CODE_96;
      }

      if(_autoMessage == null) {
        _autoMessage = new ActionCode().getMessage(ActionCode.ERROR_CODE_96);
      }
    }
    // Setting Response ISO message
    populateResponseISOMessage(_actionCode, _autoMessage);
    logger.debug("TxnAuthorizer | processTxn | Exiting");
  }

  /**
   * This method populate the basic data
   * 
   * @return
   */
  private boolean populateRequestMessage() {
    boolean status = false;
    logger.debug("**************** VALIDATION AND POPULATION OF REQUEST START *****************");

    logger.info("MTI is " + _requestIsoMsg.getString(0));
    try {
      _ISORequest = new ISOInRequest(_requestIsoMsg);
      logger.info("Is Chip (EMV) based transaction: " + _ISORequest.get_isChipTransaction());
      logger.info("Is Chip (EMV) based transaction Fallback: " + _ISORequest.get_isFallback());
      EMVData.validateEMVRequest(_ISORequest.get_isChipTransaction(), _ISORequest.getEmvData());
      validateMerchantIdAndTerminalId(_ISORequest.get_merchantId(), _ISORequest.get_terminalId());
      status = true;
    }
    catch(ISOException e) {
      _actionCode = ActionCode.ERROR_CODE_96;
      _autoMessage = new ActionCode().getMessage(_actionCode);
      logger.error("TxnAuthorizer | validateAndPopulateRequestMessage | ISOException: " + e.getMessage(), e);
    }
    catch(MagneticStripeParseException e) {
      _actionCode = ActionCode.ERROR_CODE_96;
      _autoMessage = new ActionCode().getMessage(_actionCode);
      logger.error("TxnAuthorizer | validateAndPopulateRequestMessage | MagneticStripeParseException: "
                       + e.getMessage(),
                   e);
    }catch(InvalidMerchantException e){
      status = false;
      _actionCode = ActionCode.ERROR_CODE_03;
      _autoMessage = new ActionCode().getMessage(_actionCode);
      logger.error("TxnAuthorizer | validateAndPopulateRequestMessage | Exception: " + e.getMessage(), e);
    }
    catch(Exception e) {
      status = false;
      _actionCode = ActionCode.ERROR_CODE_96;
      _autoMessage = new ActionCode().getMessage(_actionCode);
      logger.error("TxnAuthorizer | validateAndPopulateRequestMessage | Exception: " + e.getMessage(), e);
    }
    
    logger.debug("**************** VALIDATION AND POPULATION OF REQUEST END *****************");
    return status;
  }

  /**
   * @param actionCode
   *          , ErrorCode This method sets the ISOMessage for the Request
   */
  private void populateResponseISOMessage(String actionCode, String receiptMsg) {
    logger.info("ActionCode: " + actionCode + ", ActionMsg : " + receiptMsg);

    ResponseMessage responseMsg = _txnHandler.getResponseMessage();

    logger.info("Response MTI is " + responseMTI);
    try {
      responseMsg.getISOMessage().setMTI(responseMTI);
    }
    catch(ISOException e) {
      logger.debug("Unable to set response MTI: " + responseMTI);
    }

    // Processing Code - Field 3
    copyReq2RespField(3);

    // original trxn amount
    copyReq2RespField(4);

    // System trace audit number (Field 11)
    copyReq2RespField(11);

    // SYSTEM DATE TIME
    responseMsg.setFieldValue(12, DateUtils.getCurrentTime());

    // Transaction date MMdd
    responseMsg.setFieldValue(13, DateUtils.getTransactionDate());

    // Application PAN sequence number
    copyReq2RespField(23);

    // Network International identifier (NII)
    copyReq2RespField(24);

    // Card Acceptor Terminal Id (Field 41)
    copyReq2RespField(41);

    // Chip data
    copyReqByte2RespField(55);

    // copyReq2RespField(62);

    // Update the field 39 and 44 for Message to be printed on POS
    if(StringUtils.isValidString(actionCode)) {
      responseMsg.setFieldValue(39, actionCode);
    }
    else {
      responseMsg.setFieldValue(39, ActionCode.ERROR_CODE_Z5);
    }
  }

  /**
   * @param idx
   */
  public void copyReqByte2RespField(int idx) {
    RequestMessage requestMsg = _requestMessage;
    ResponseMessage responseMsg = _txnHandler.getResponseMessage();
    if(_requestMessage.getFieldByteValue(idx) != null)
      responseMsg.setFieldValue(idx, requestMsg.getFieldByteValue(idx));
  }

  /**
   * Method to copy the request field value to same response field
   * 
   * @param idx
   */
  public void copyReq2RespField(int idx) {
    String fieldValue = _requestMessage.getFieldValue(idx);
    if(fieldValue != null)
      _txnHandler.getResponseMessage().setFieldValue(idx, fieldValue);
  }

  /**
   * Method to copy the request field value to different response field
   * 
   * @param reqIdx
   * @param resIdx
   */
  public void copyReq2RespField(int reqIdx, int resIdx) {
    String fieldValue = _requestMessage.getFieldValue(reqIdx);
    if(fieldValue != null)
      _txnHandler.getResponseMessage().setFieldValue(resIdx, fieldValue);
  }
  
  /**
   * Method to process Financial Auth Transaction
   * 
   * @param isAuthTxn
   */
  private void processFinancialAuthTransaction() {
	  if(_ISORequest.get_processingCode().equalsIgnoreCase(PROC_CODE_AUTH)) {// Auth
		  new Purchase(this).processAuth();
	  }
	  else if(_ISORequest.get_processingCode().equalsIgnoreCase(PROC_CODE_VOID)) {
		  if(_ISORequest.get_txnAmount() == 0L) { // Void
			  new Refund(this).processVoid();
		  }
		  else {// Adjustment
			  new Purchase(this).processAjustment();
		  }
	  } 
	  else if(_ISORequest.get_processingCode().equalsIgnoreCase(PROC_CODE_REFUND)) {// Refund
		  new Refund(this).processRefund();
	  }else {
		  logger.info("FinancialAuthTransaction Unknown transaction");
	  }
  }

  /**
   * Method to process Financial Transaction
   * 
   */
	private void processFinancialTransaction() {
		if (_ISORequest.get_processingCode().equalsIgnoreCase(PROC_CODE_AUTH)) {// Sale
			new Purchase(this).processAuthCapture();
		} else if (_ISORequest.get_processingCode().equalsIgnoreCase(PROC_CODE_CASH_WITHDRAWAL)) {// cash withdrawal
			new Purchase(this).processCashWithdrawal();
		} else if (_ISORequest.get_processingCode().equalsIgnoreCase(PROC_CODE_CASH_BACK)) {// cash back
			new Purchase(this).processCashBack();
		} else if (_ISORequest.get_processingCode().equalsIgnoreCase(
				PROC_CODE_VOID)
				|| _ISORequest.get_processingCode().equalsIgnoreCase(
						PROC_CODE_REFUND_ADJUSTMENT)) {
			new Refund(this).processVoid();
		} else if (_ISORequest.get_processingCode().equalsIgnoreCase(
				PROC_CODE_REFUND)) {// Refund
			new Refund(this).processRefund();
		}else if(_ISORequest.get_processingCode().equalsIgnoreCase(PROC_CODE_BALANCE_ENQUIRY)) {
			 
			  new Purchase(this).balanceEnquiry();
	  } else {
			logger.info(" FinancialTransaction Unknown transaction");
		}
	}

  /**
   * Process Settlement Transaction
   */
  private void processFinancialSettlementTransaction() {
    new Purchase(this).settlementTransaction();
  }

  /**
   * Method to process network transaction
   */
  private void processNetworkTransaction() {
    new Purchase(this).networkTransaction();
  }

  /**
   * Method to process financial advice transactions
   */
	private void processFinancialAdviceTransactions() {
		if (_ISORequest.get_processingCode().equalsIgnoreCase(
				PROC_CODE_PURCHASE)) {// Sale
			Purchase purchase = new Purchase(this);
			// sale completion/Capture
			if (StringUtils.isValidString(_ISORequest.get_authTxnRefNum())
					&& StringUtils.isValidString(_ISORequest.get_authId())) {
				purchase.processCapture();
			} else {
				purchase.processAuthCapture();
			}
		} else if (_ISORequest.get_processingCode().equalsIgnoreCase(
				PROC_CODE_TIP_ADJUSTMENT)) {// Adjustment
			new Purchase(this).processAjustment();
		} else if (_ISORequest.get_processingCode().equalsIgnoreCase(
				PROC_CODE_REFUND)) {// Refund
			new Refund(this).processRefund();
		} else {
			logger.info("FinancialAdviceTransaction Unknown transaction");
		}
	}

  /**
   * Method to process reversal transactions
   */
  private void processReversalTransaction() {
    new Refund(this).processReversal();
  }

  /**
   * @return
   */
  public String get_actionCode() {
    return _actionCode;
  }

  public void set_actionCode(String code) {
    _actionCode = code;
  }

  public String get_autoMessage() {
    return _autoMessage;
  }

  public void set_autoMessage(String message) {
    _autoMessage = message;
  }

  public ISOMsg get_requestIsoMsg() {
    return _requestIsoMsg;
  }

  public void set_requestIsoMsg(ISOMsg isoMsg) {
    _requestIsoMsg = isoMsg;
  }

  public RequestMessage get_requestMessage() {
    return _requestMessage;
  }

  public void set_requestMessage(RequestMessage message) {
    _requestMessage = message;
  }

  public TransactionHandler get_txnHandler() {
    return _txnHandler;
  }

  public void set_txnHandler(TransactionHandler handler) {
    _txnHandler = handler;
  }

  /**
   * @return the _ISOInputRequest
   */
  public ISOInRequest get_ISOInputRequest() {
    return _ISORequest;
  }

  /**
   * @param _ISOInputRequest
   *          the _ISOInputRequest to set
   */
  public void set_ISOInputRequest(ISOInRequest _ISOInputRequest) {
    this._ISORequest = _ISOInputRequest;
  }
  
  /**<<Method to validate merchat id and terminal id>>
   * @param mId
   * @param tId
   * @throws InvalidMerchantException
   */
  public void validateMerchantIdAndTerminalId(String mId, String tId)throws InvalidMerchantException {
    PGMerchant pgMerchant = merchantTerminalDao.validateMerchantIdAndTerminalId(mId, tId);
    if(null != pgMerchant) {
      mode = pgMerchant.getAppMode();
    }else{
    	throw new InvalidMerchantException();
    }
  }

  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }


}