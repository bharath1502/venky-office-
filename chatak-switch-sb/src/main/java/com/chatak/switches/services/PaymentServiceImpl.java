package com.chatak.switches.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.acq.dao.model.PGSwitchTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.bean.AdjustmentRequest;
import com.chatak.pg.bean.AdjustmentResponse;
import com.chatak.pg.bean.AuthRequest;
import com.chatak.pg.bean.AuthResponse;
import com.chatak.pg.bean.BalanceEnquiryResponse;
import com.chatak.pg.bean.CaptureRequest;
import com.chatak.pg.bean.CaptureResponse;
import com.chatak.pg.bean.CashBackRequest;
import com.chatak.pg.bean.CashBackResponse;
import com.chatak.pg.bean.CashWithdrawalRequest;
import com.chatak.pg.bean.CashWithdrawalResponse;
import com.chatak.pg.bean.NetworkRequest;
import com.chatak.pg.bean.NetworkResponse;
import com.chatak.pg.bean.PurchaseRequest;
import com.chatak.pg.bean.PurchaseResponse;
import com.chatak.pg.bean.RefundRequest;
import com.chatak.pg.bean.RefundResponse;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.Response;
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.ReversalResponse;
import com.chatak.pg.bean.VoidRequest;
import com.chatak.pg.bean.VoidResponse;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.MessageTypeCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.prepaid.ChatakPrepaidSwitchTransaction;
import com.chatak.switches.sb.SwitchTransaction;
import com.chatak.switches.sb.exception.ChatakSwitchException;
import com.chatak.switches.sb.exception.ServiceException;
import com.chatak.switches.sb.util.SpringDAOBeanFactory;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date May 7, 2015 10:52:12 AM
 * @version 1.0
 */
public class PaymentServiceImpl extends TransactionService implements PaymentService {

  private static Logger log = Logger.getLogger(PaymentServiceImpl.class);

  @Autowired
  VoidTransactionDao voidTransactionDao;

  public PaymentServiceImpl() {
    AutowireCapableBeanFactory acbFactory =
        SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
    acbFactory.autowireBean(this);
  }

  public AuthResponse authTransaction(AuthRequest authRequest) throws ServiceException {

    log.info("PaymentServiceImpl | authTransaction | Entering");
    AuthResponse authResponse = new AuthResponse();

    try {

      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.financial(getISOMsg(authRequest,
          MessageTypeCode.AUTHORIZATION_REQUEST, MessageTypeCode.PROC_CODE_AUTH_SAV, txnRefNum));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      authResponse.setTxnResponseTime(System.currentTimeMillis());
      authResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        authResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED);
      }
      if (authResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        authResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          authResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      authResponse.setErrorCode(switchResponseCode);
      authResponse.setErrorMessage(switchResponseMessage);

    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: authTransaction method", e);
      setErrorResponse(authResponse, ActionCode.ERROR_CODE_Z7);
    }

    log.info("PaymentServiceImpl | authTransaction | Exiting");
    return authResponse;
  }

  /**
   * Method to Capture the Authorised Payment Transaction Steps involved 1.
   * Validate Request PAYMENT INTEGRATION 2. Validate Auth Transaction
   * reference Number 2. Create Transaction record 3. TXN_CARD_INFO RECORD not
   * created as it is available in Auth transaction 4. SET response fields 5.
   * return response
   * 
   * @param captureRequest
   * @return CaptureResponse
   * @throws ServiceException
   */
  public CaptureResponse captureTransaction(CaptureRequest captureRequest) throws ServiceException {
    log.info("PaymentServiceImpl | captureTransaction | Entering");
    CaptureResponse captureResponse = new CaptureResponse();

    try {

      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.financial(getISOMsg(captureRequest,
          MessageTypeCode.OFFLINE_REQUEST, MessageTypeCode.PROC_CODE_AUTH_SAV, txnRefNum));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      captureResponse.setTxnResponseTime(System.currentTimeMillis());
      captureResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null){
        captureResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED);
      }
      if (captureResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        captureResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          captureResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      captureResponse.setErrorCode(switchResponseCode);
      captureResponse.setErrorMessage(switchResponseMessage);

    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: captureTransaction method", e);
      setErrorResponse(captureResponse, ActionCode.ERROR_CODE_Z7);
    }

    log.info("PaymentServiceImpl | captureTransaction | Exiting");
    return captureResponse;
  }

  /**
   * Method to Auth-Capture Payment Transaction Steps Involved 1. Validate
   * Request PAYMENT INTEGRATION 2. Create Transaction record 3. Create
   * TXN_CARD_INFO RECORD 4. SET response fields 5. return response
   * 
   * @param purchaseRequest
   * @return PurchaseResponse
   * @throws ServiceException
   */
  public PurchaseResponse purchaseTransaction(PurchaseRequest purchaseRequest)
      throws ServiceException {
    log.info("PaymentServiceImpl | purchaseTransaction | Entering");
    log.info("Transaction Currency : " + purchaseRequest.getCurrencyCode());
    PurchaseResponse purchaseResponse = new PurchaseResponse();

    try {

      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.financial(getISOMsg(purchaseRequest,
          MessageTypeCode.ONLINE_REQUEST, MessageTypeCode.PROC_CODE_AUTH_SAV, txnRefNum));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      purchaseResponse.setTxnResponseTime(System.currentTimeMillis());
      purchaseResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null){
        purchaseResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED));
      }
      if (purchaseResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS) ||
    		  purchaseResponse.getUpStreamStatus().equals(PGConstants.STATUS_FAILED)) {
        purchaseResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          purchaseResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      purchaseResponse.setErrorCode(switchResponseCode);
      purchaseResponse.setErrorMessage(switchResponseMessage);

    } catch (ISOException e) {
      log.error("ERROR:: PaymentServiceImpl:: Error in  sale IsoPacket", e);
      setErrorResponse(purchaseResponse, ActionCode.ERROR_CODE_ISO);
    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: purchaseTransaction method", e);
      setErrorResponse(purchaseResponse, ActionCode.ERROR_CODE_Z7);
    }

    log.info("PaymentServiceImpl | purchaseTransaction | Exiting");
    return purchaseResponse;
  }

  private void setErrorResponse(Response response, String errorCode) {
    response.setUpStreamStatus(PGConstants.STATUS_FAILED);
    response.setErrorCode(errorCode);
    response.setErrorMessage(ActionCode.getInstance().getMessage(errorCode));
    response.setUpStreamMessage(ActionCode.getInstance().getMessage(errorCode));
  }

  /**
   * Method to Adjust to successfull Payment Transaction (Tip Adjustment, sale
   * amount adjustment) Steps Involved 1. Validate Request PAYMENT
   * INTEGRATION 2. Validate Auth Transaction reference Number 2. Create
   * Transaction record 3. TXN_CARD_INFO RECORD not created as it is available
   * in Auth transaction 4. SET response fields 5. return response
   * 
   * @param adjustmentRequest
   * @return AdjustmentResponse
   * @throws ServiceException
   */
  public AdjustmentResponse adjustmentTransaction(AdjustmentRequest adjustmentRequest)
      throws ServiceException {
    log.info("PaymentServiceImpl | adjustmentTransaction | Entering");
    AdjustmentResponse adjustmentResponse = new AdjustmentResponse();
    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;

    try {

      // validation of Request
      validateRequest(adjustmentRequest);

      PGTransaction saleTransaction =
          transactionDao.getTransaction(adjustmentRequest.getMerchantId(),
              adjustmentRequest.getTerminalId(), adjustmentRequest.getTxnRefNum());
      if (saleTransaction == null) {
        throw new ServiceException(ActionCode.ERROR_CODE_78);
      }

      adjustmentRequest.setTxnRefNumber(txnRefNum);

      // Create Transaction record
      pgTransaction = populatePGTransaction(adjustmentRequest, PGConstants.TXN_TYPE_SALE_ADJ);
      pgTransaction.setAuthId(adjustmentRequest.getAuthId());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(adjustmentRequest);

      // Switch interface call
      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.authAdvice(
          getISOMsg(adjustmentRequest, MessageTypeCode.OFFLINE_REQUEST, "021010", txnRefNum));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage =
          switchISOMsg.getValue(Integer.parseInt("44")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("44")) : null;

      //  check response code to set declined and failed cases
      if (switchResponseCode != null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)) {
        // Switch transaction id
        String issuerTxnRefNumber =
            switchISOMsg.getValue(Integer.parseInt("37")) != null ? ((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim() : null;

        pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);

        pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
        pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);

        adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_00);
        adjustmentResponse
            .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_00));

      } else {
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);

        adjustmentResponse.setErrorCode(switchResponseCode);
        adjustmentResponse.setErrorMessage(switchResponseMessage != null ? switchResponseMessage
            : ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_12));
      }
      pgSwitchTransaction
          .setTransactionId(switchISOMsg.getString(Integer.parseInt("37")) != null ? switchISOMsg.getString(Integer.parseInt("37")) : null);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Update transaction status and switch response
      voidTransactionDao.createTransaction(pgTransaction);

      // Set Response attributes
      adjustmentResponse.setTxnRefNum(txnRefNum);
      adjustmentResponse.setAuthId(adjustmentRequest.getAuthId());
      adjustmentResponse.setTxnAmount(adjustmentRequest.getTxnAmount());
      adjustmentResponse.setAdjAmount(adjustmentRequest.getAdjAmount());
      adjustmentResponse.setFeeAmount(saleTransaction.getFeeAmount());
      adjustmentResponse
          .setTotalAmount(adjustmentRequest.getTxnAmount() + saleTransaction.getFeeAmount());

    } catch (ChatakSwitchException e) {
      log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :" + e.getMessage(),
          e);
      adjustmentResponse.setErrorCode(e.getMessage());
      adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));

      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      voidTransactionDao.createTransaction(pgTransaction);

    } catch (DataAccessException e) {
      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      adjustmentResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      log.error(
          "PaymentServiceImpl | adjustmentTransaction | DataAccessException :" + e.getMessage(), e);
    } catch (ServiceException e) {
      adjustmentResponse.setErrorCode(e.getMessage());
      adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | adjustmentTransaction | ServiceException :", e);
    } catch (Exception e) {
      log.error("Exception :" + e);
      validatePgSwitchTransactionAndPgTransaction(pgSwitchTransaction, pgTransaction);
      voidTransactionDao.createTransaction(pgTransaction);

      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      adjustmentResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
    }

    log.info("PaymentServiceImpl | adjustmentTransaction | Exiting");
    return adjustmentResponse;
  }

  /**
   * Method to Void/Cancel an successfull sale/refund transaction
   * 
   * @param voidRequest
   * @return VoidResponse
   * @throws ServiceException
   */
  public VoidResponse voidTransaction(VoidRequest voidRequest) throws ServiceException {
    log.info("PaymentServiceImpl | voidTransaction | Entering");
    VoidResponse voidResponse = new VoidResponse();

    try {

      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg =
          switchTransaction.reversal(getISOMsg(voidRequest, MessageTypeCode.ONLINE_REQUEST,
              MessageTypeCode.PROC_CODE_VOID, voidRequest.getIssuerTxnRefNum()));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      voidResponse.setTxnResponseTime(System.currentTimeMillis());
      voidResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        voidResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED);
      }
      if (PGConstants.SUCCESS.equals(switchResponseCode)) {
        voidResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          voidResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      voidResponse.setErrorCode(switchResponseCode);
      voidResponse.setErrorMessage(switchResponseMessage);

    } catch (ISOException e) {
      log.error("ERROR:: PaymentServiceImpl:: Error in IsoPacket", e);
      setErrorResponse(voidResponse, ActionCode.ERROR_CODE_ISO);
    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: voidTransaction method", e);
      setErrorResponse(voidResponse, ActionCode.ERROR_CODE_Z7);
    }

    log.info("PaymentServiceImpl | voidTransaction | Exiting");
    return voidResponse;
  }

  /**
   * Method to Reverse a transaction
   * 
   * @param reversalRequest
   * @return ReversalResponse
   * @throws ServiceException
   */
  public ReversalResponse reversalTransaction(ReversalRequest reversalRequest)
      throws ServiceException {
    log.info("PaymentServiceImpl | reversalTransaction | Entering");
    ReversalResponse reversalResponse = new ReversalResponse();
    try {

      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction
          .reversalAdvice(getISOMsg(reversalRequest, MessageTypeCode.REVERSAL_ADVICE_REQUEST,
              MessageTypeCode.PROC_CODE_VOID_SAV, reversalRequest.getIssuerTxnRefNum()));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      reversalResponse.setTxnResponseTime(System.currentTimeMillis());
      reversalResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        reversalResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED);
      }
      if (reversalResponse.getUpStreamStatus().equals(PGConstants.SUCCESS)) {
        reversalResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          reversalResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      reversalResponse.setErrorCode(switchResponseCode);
      reversalResponse.setErrorMessage(switchResponseMessage);

    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: reversalTransaction method", e);
      setErrorResponse(reversalResponse, ActionCode.ERROR_CODE_Z7);
    }

    log.info("PaymentServiceImpl | reversalTransaction | Exiting");
    return reversalResponse;
  }

  /**
   * Method to Refund a transaction
   * 
   * @param reversalRequest
   * @return ReversalResponse
   * @throws ServiceException
   */
  public RefundResponse refundTransaction(RefundRequest refundRequest) throws ServiceException {

    log.info("PaymentServiceImpl | refundTransaction | Entering");
    RefundResponse refundResponse = new RefundResponse();

    try {

      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg =
          switchTransaction.financial(getISOMsg(refundRequest, MessageTypeCode.ONLINE_REQUEST,
              MessageTypeCode.PROC_CODE_REFUND_SAV, refundRequest.getIssuerTxnRefNum()));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      refundResponse.setTxnResponseTime(System.currentTimeMillis());
      refundResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        refundResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED);
      }
        
      if (refundResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        refundResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          refundResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      refundResponse.setErrorCode(switchResponseCode);
      refundResponse.setErrorMessage(switchResponseMessage);
    } catch (ISOException e) {
      log.error("ERROR:: PaymentServiceImpl:: Error in IsoPacket", e);
      setErrorResponse(refundResponse, ActionCode.ERROR_CODE_ISO);
    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: refundTransaction method", e);
      setErrorResponse(refundResponse, ActionCode.ERROR_CODE_Z7);
    }

    log.info("PaymentServiceImpl | refundTransaction | Exiting");
    return refundResponse;
  }

  public BalanceEnquiryResponse balanceEnquiryTransaction(
      Request balanceEnquiryRequest) throws ServiceException {
    log.debug("PaymentServiceImpl | balanceEnquiryTransaction | Entering");
    BalanceEnquiryResponse balanceEnquiryResponse = new BalanceEnquiryResponse();

    ISOMsg balanceEnquiryIsoMsg = new ISOMsg();

    try {
      // validation of Request
      validateRequest(balanceEnquiryRequest);
      // Switch interface call
      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      balanceEnquiryIsoMsg = getBalanceEnquiryISOMsg(balanceEnquiryRequest, balanceEnquiryRequest.getIsoMsg());
      ISOMsg switchISOMsg = switchTransaction.authAdvice(balanceEnquiryIsoMsg);
      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      balanceEnquiryResponse.setTxnResponseTime(System.currentTimeMillis());
      balanceEnquiryResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        balanceEnquiryResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED));
      }
      if (balanceEnquiryResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS) || 
    		  balanceEnquiryResponse.getUpStreamStatus().equals(PGConstants.STATUS_FAILED)) {
      // Amount Will be like 0002170C000009855850
      //Postion 1,2 = Account type | 3,4 = amount type | 5-7 = Currency code, 8-20 = amount
        balanceEnquiryResponse.setBalance((String) switchISOMsg.getValue(Integer.parseInt("54")));
        balanceEnquiryResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          balanceEnquiryResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      balanceEnquiryResponse.setErrorCode(switchResponseCode);
      balanceEnquiryResponse.setErrorMessage(switchResponseMessage);
    } catch (ISOException e) {
      log.error("ERROR:: PaymentServiceImpl:: Error in IsoPacket", e);
      setErrorResponse(balanceEnquiryResponse, ActionCode.ERROR_CODE_ISO);
    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: purchaseTransaction method", e);
      setErrorResponse(balanceEnquiryResponse, ActionCode.ERROR_CODE_Z7);
    }

    return balanceEnquiryResponse;
  }

  /**
   * Method to Cash withdrawal Payment Transaction Steps Involved 1. Validate
   * Request 2. Create Transaction record 3. Create switch Transaction record 4.
   * Call Switch interface 5. Update transaction and switch transaction record
   * status 4. SET response fields 5. return response
   * 
   * @param purchaseRequest
   * @return PurchaseResponse
   * @throws ServiceException
   */
  public CashWithdrawalResponse cashWithdrawalTransaction(
      CashWithdrawalRequest cashWithdrawalRequest) throws ServiceException {
    log.debug("PaymentServiceImpl | cashWithdrawalTransaction | Entering");
    CashWithdrawalResponse cashWithdrawalResponse = new CashWithdrawalResponse();
    ISOMsg isoMsg = new ISOMsg();

    try {
      // validation of Request
      validateRequest(cashWithdrawalRequest);
      // Switch interface call
      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      isoMsg = getISOMsg(cashWithdrawalRequest.getIsoMsg());
      ISOMsg switchISOMsg = switchTransaction.financial(isoMsg);

      isoMessageValidation(cashWithdrawalResponse, switchISOMsg);
    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: cashWithdrawalTransaction method", e);
      setErrorResponse(cashWithdrawalResponse, ActionCode.ERROR_CODE_Z7);
    }
    log.debug("PaymentServiceImpl | cashWithdrawalTransaction | Exiting");
    return cashWithdrawalResponse;
  }

private void isoMessageValidation(CashWithdrawalResponse cashWithdrawalResponse, ISOMsg switchISOMsg)
		throws ISOException {
	String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      cashWithdrawalResponse.setTxnResponseTime(System.currentTimeMillis());
      cashWithdrawalResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        cashWithdrawalResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED));
      }
      if (cashWithdrawalResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        cashWithdrawalResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          cashWithdrawalResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      cashWithdrawalResponse.setErrorCode(switchResponseCode);
      cashWithdrawalResponse.setErrorMessage(switchResponseMessage);
}

  /**
   * Method to Cash back Transaction Steps Involved 1. Validate Request 2.
   * Create Transaction record 3. Create switch Transaction record 4. Call
   * Switch interface 5. Update transaction and switch transaction record status
   * 4. SET response fields 5. return response
   * 
   * @param CashBackRequest
   * @return CashBackResponse
   * @throws ServiceException
   */
  public CashBackResponse cashBackTransaction(CashBackRequest cashBackRequest)
      throws ServiceException {
    log.debug("PaymentServiceImpl | cashBackTransaction | Entering");
    CashBackResponse cashBackResponse = new CashBackResponse();
    ISOMsg isoMsg = new ISOMsg();
    try {
      // validation of Request
      validateRequest(cashBackRequest);
      // Switch interface call
      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      isoMsg = getISOMsg(cashBackRequest.getIsoMsg());
      validateISOMsg(cashBackResponse, isoMsg, switchTransaction);
    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: cashWithdrawalTransaction method", e);
      setErrorResponse(cashBackResponse, ActionCode.ERROR_CODE_Z7);
    }
    log.debug("PaymentServiceImpl | cashBackTransaction | Exiting");
    return cashBackResponse;
  }

private void validateISOMsg(CashBackResponse cashBackResponse, ISOMsg isoMsg, SwitchTransaction switchTransaction)
		throws ChatakSwitchException, ISOException {
	ISOMsg switchISOMsg = switchTransaction.financial(isoMsg);

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      cashBackResponse.setTxnResponseTime(System.currentTimeMillis());
      cashBackResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        cashBackResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS)
            ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED));
      }
      if (cashBackResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        cashBackResponse.setUpStreamAuthCode(authId);
        if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
          cashBackResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
      }
      cashBackResponse.setErrorCode(switchResponseCode);
      cashBackResponse.setErrorMessage(switchResponseMessage);
}

  /**
   * <<method to perform partial capture transaction>>
   * 
   * @param captureRequest
   * @return
   * @throws ServiceException
   */
  public CaptureResponse partialCaptureTransaction(CaptureRequest captureRequest)
      throws ServiceException {
    log.info("PaymentServiceImpl | captureTransaction | Entering");
    CaptureResponse captureResponse = new CaptureResponse();
    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    try {

      // validation of Request
      validateRequest(captureRequest);

      PGTransaction authTransaction = voidTransactionDao
          .findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
              captureRequest.getAuthTxnRefNum(), captureRequest.getIssuerTxnRefNum(),
              captureRequest.getMerchantId(), captureRequest.getTerminalId());
      validatePGTransaction(captureRequest, authTransaction);

      // Create Transaction record
      pgTransaction = populatePGTransaction(captureRequest, PGConstants.TXN_TYPE_SALE);
      pgTransaction.setAuthId(authTransaction.getAuthId());
      pgTransaction.setTxnAmount(authTransaction.getTxnAmount());
      pgTransaction.setFeeAmount(authTransaction.getFeeAmount());
      pgTransaction.setTxnTotalAmount(authTransaction.getTxnTotalAmount());
      pgTransaction.setProcessor(ProcessorType.CHATAK.value());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(captureRequest);
      pgSwitchTransaction.setPgTransactionId(txnRefNum);

      // Switch interface call
      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.financialAdvice(getISOMsg(captureRequest,
          MessageTypeCode.OFFLINE_REQUEST, MessageTypeCode.PROC_CODE_AUTH_SAV, txnRefNum));

      String switchResponseCode =
          switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
      String switchResponseMessage =
          switchISOMsg.getValue(Integer.parseInt("44")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("44")) : null;

      //  check response code to set declined and failed cases
      if (switchResponseCode != null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)) {

        // updating account
        updateMerchantAccount(pgTransaction.getMerchantId(), PGConstants.CAPTURE_PAYMENT_METHOD,
            pgTransaction.getTxnTotalAmount(),
            pgTransaction.getFeeAmount() == null ? 0 : pgTransaction.getFeeAmount(),
            pgTransaction.getTransactionId());
        // Switch transaction id
        String issuerTxnRefNumber =
            switchISOMsg.getValue(Integer.parseInt("37")) != null ? ((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim() : null;

        pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);

        pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
        pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);

        captureResponse.setErrorCode(ActionCode.ERROR_CODE_00);
        captureResponse
            .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_00));
        captureResponse.setUpStreamTxnRefNum(issuerTxnRefNumber);

      } else {
        validateSwitchResponseCode(captureResponse, pgSwitchTransaction, pgTransaction,
            switchResponseCode, switchResponseMessage);
      }
      pgSwitchTransaction
          .setTransactionId(switchISOMsg.getString(Integer.parseInt("37")) != null ? switchISOMsg.getString(Integer.parseInt("37")) : null);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Update transaction status and switch response
      voidTransactionDao.createTransaction(pgTransaction);

      // Set Response attributes
      captureResponse.setTxnRefNum(txnRefNum);
      captureResponse.setAuthId(captureRequest.getAuthId());
      captureResponse.setTxnAmount(captureRequest.getTxnAmount());
      captureResponse.setFeeAmount(authTransaction.getFeeAmount());
      captureResponse.setTotalAmount(authTransaction.getTxnTotalAmount());

    } catch (ChatakSwitchException e) {
      log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :", e);
      captureResponse.setErrorCode(e.getMessage());
      captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));

      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      voidTransactionDao.createTransaction(pgTransaction);

    } catch (DataAccessException e) {
      captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      captureResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      log.error("PaymentServiceImpl | captureTransaction | DataAccessException :", e);
    } catch (ServiceException e) {
      captureResponse.setErrorCode(e.getMessage());
      captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | captureTransaction | ServiceException :", e);
    } catch (Exception e) {
      log.error("Exception :", e);
      validatePgSwitchTransactionAndPgTransaction(pgSwitchTransaction, pgTransaction);
      voidTransactionDao.createTransaction(pgTransaction);

      captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      captureResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
    }

    log.info("PaymentServiceImpl | captureTransaction | Exiting");
    return captureResponse;
  }

  private void validateSwitchResponseCode(CaptureResponse captureResponse,
      PGSwitchTransaction pgSwitchTransaction, PGTransaction pgTransaction,
      String switchResponseCode, String switchResponseMessage) {
    pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
    pgTransaction.setStatus(PGConstants.STATUS_FAILED);

    captureResponse.setErrorCode(switchResponseCode);
    captureResponse.setErrorMessage(switchResponseMessage != null ? switchResponseMessage
        : ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_12));
  }

  private void validatePgSwitchTransactionAndPgTransaction(PGSwitchTransaction pgSwitchTransaction,
      PGTransaction pgTransaction) {
    if(pgSwitchTransaction != null) {
      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
    }
    switchTransactionDao.createTransaction(pgSwitchTransaction);

    if(pgTransaction != null) {
      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
    }
  }

  private void validatePGTransaction(CaptureRequest captureRequest, PGTransaction authTransaction)
      throws ServiceException {
    if (authTransaction == null) {
      throw new ServiceException(ActionCode.ERROR_CODE_78);
    } else {
      List<PGTransaction> authTransactionDuplicate =
          voidTransactionDao.findByMerchantIdAndTerminalIdAndRefTransactionIdAndStatus(
              captureRequest.getMerchantId(), captureRequest.getTerminalId(),
              captureRequest.getAuthTxnRefNum(), 0);
      if (StringUtils.isListNotNullNEmpty(authTransactionDuplicate)) {
        throw new ServiceException(ActionCode.ERROR_CODE_94);
      }
    }
  }

  /**
   * @return
   */
  @Override
  public String getProcessor() {
    return ProcessorType.CHATAK.value();
  }

  /**
   * <<Method to process network management messages >>
   * 
   * @param networkRequest
   * @return
   * @throws ServiceException
   */
  public NetworkResponse networkTransaction(NetworkRequest networkRequest) throws ServiceException {

    log.info("PaymentServiceImpl | authTransaction | Entering");
    NetworkResponse networkResponse = new NetworkResponse();

    try {
      if ("071".equals(networkRequest.getNetworkManagementCode())
          || "072".equals(networkRequest.getNetworkManagementCode())
          || "371".equals(networkRequest.getNetworkManagementCode())) {
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
            Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.network(getNetworkIsoMsg("0800",
            networkRequest.getNetworkManagementCode(), "49149", txnRefNum));// 071 sign on as acq 072 sign off as acq 371 acq echo test
        String switchResponseCode =
            switchISOMsg.getValue(Integer.parseInt("39")) != null ? (String) switchISOMsg.getValue(Integer.parseInt("39")) : null;
        String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
        networkResponse.setTxnResponseTime(System.currentTimeMillis());
        networkResponse.setUpStreamMessage(switchResponseMessage);

        if(switchResponseCode != null) {
          networkResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS)
              ? PGConstants.STATUS_SUCCESS : PGConstants.STATUS_FAILED);
        }

        validateUpStreamStatus(networkResponse, switchISOMsg);
        networkResponse.setErrorCode(switchResponseCode);
        networkResponse.setErrorMessage(switchResponseMessage);
      } else {
        log.error(
            "ERROR:: PaymentServiceImpl:: authTransaction method:invalid network management code");
        throw new ServiceException(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z9));
      }

    } catch (Exception e) {
      log.error("ERROR:: PaymentServiceImpl:: authTransaction method" + e);
      setErrorResponse(networkResponse, ActionCode.ERROR_CODE_Z7);
    }

    log.info("PaymentServiceImpl | authTransaction | Exiting");
    return networkResponse;
  }

  private void validateUpStreamStatus(NetworkResponse networkResponse, ISOMsg switchISOMsg)
      throws ISOException {
    if (networkResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
      networkResponse.setUpStreamAuthCode(authId);
      if (switchISOMsg.getValue(Integer.parseInt("37")) != null)
        networkResponse.setUpStreamTxnRefNum(((String) switchISOMsg.getValue(Integer.parseInt("37"))).trim());
    }
  }

}
