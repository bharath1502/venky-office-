package com.chatak.pg.acq.processor;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTransaction;
/*import com.chatak.pg.acq.service.PaymentService;
 import com.chatak.pg.acq.service.PaymentServiceImpl;
 */
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
import com.chatak.pg.bean.ISOInRequest;
import com.chatak.pg.bean.PurchaseRequest;
import com.chatak.pg.bean.PurchaseResponse;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.NationalPOSEntryModeEnum;
import com.chatak.pg.exception.ValidationException;
import com.chatak.switches.sb.exception.ServiceException;
/*import com.chatak.pg.upstream.BINUpstreamRouter;*/
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.Properties;
import com.chatak.switches.sb.SwitchServiceBroker;

/**
 * @Comments : This class process the purchase / sale transaction
 */
public class Purchase extends Processor {
  @Autowired
  private ApplicationContext appContext;

  @Autowired
  private TransactionDao transactionDao;
  
  @Autowired
  private VoidTransactionDao voidtransactiondao;

  private static Logger log = Logger.getLogger(Purchase.class);

  /* private PaymentService paymentService; */

  public Purchase(TxnAuthorizer txnAuth) {
    super(txnAuth);
    AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
    acbFactory.autowireBean(this);
  }

  /**
   * Method is invoked when sale/auth-capture transaction is processed from PG
   * This method makes a service call to create records to log in database and
   * Payment Integration Request Object is constructed using the ISO Message and
   * Response ISO Message is constructed using the Response Object of Service
   * integration
   */
  public boolean processAuthCapture() {
    log.debug("Purchase | processAuthCapture | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      // validating duplicate invoice request
      validateDuplicateRequest(_ISOInputRequest);
      // Populate Request Object
      PurchaseRequest purchaseRequest = new PurchaseRequest();
      purchaseRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      purchaseRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      purchaseRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      purchaseRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      purchaseRequest.setCardNum(_ISOInputRequest.get_cardNum());
      purchaseRequest.setExpDate(_ISOInputRequest.get_expDate());
      purchaseRequest.setTrack2(_ISOInputRequest.get_track2());
      purchaseRequest.setPosEntryMode(_ISOInputRequest.get_Field22());
      purchaseRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      purchaseRequest.setEmvData(_ISOInputRequest.getEmvData());
      purchaseRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      purchaseRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      purchaseRequest.setAcq_channel("POS");// TODO: need to set proper value
      purchaseRequest.setAcq_mode("CASH");// TODO: need to set proper value
      purchaseRequest.setMti(_ISOInputRequest.get_MTI());
      purchaseRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      purchaseRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      purchaseRequest.setEntryMode(EntryModeEnum.fromValue(_ISOInputRequest.get_Field22().substring(0, 2)));
      purchaseRequest.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(purchaseRequest.getEntryMode().toString()
                                                                               + "_DE58"));
      purchaseRequest.setTotalTxnAmount(_ISOInputRequest.get_txnAmount());
      purchaseRequest.setTxnFee(PGConstants.ZERO);// TODO: need to change
      purchaseRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      purchaseRequest.setMode(_txnAuthorizer.getMode());

      /*
       * PurchaseResponse purchaseResponse =
       * getPaymentService().purchaseTransaction(purchaseRequest);
       */
      PurchaseResponse purchaseResponse = new SwitchServiceBroker().purchaseTransaction(purchaseRequest, new PGMerchant());

      // set fields to response
      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());

      if(purchaseResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, purchaseResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, purchaseResponse.getAuthId());
      }

      // feild 39 and 44
      setResponseFields(purchaseResponse.getErrorCode());

      status = true;

    }
    catch(ServiceException e) {
      log.error("Purchase | processAuthCapture | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(ValidationException e) {
      log.error("Purchase | processAuthCapture | Exception :" + e.getMessage(), e);
      setResponseFields(e.getMessage());
      status = false;
    }
    catch(Exception e) {
      log.error("Purchase | processAuthCapture | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.debug("Purchase | processAuthCapture | Exiting");
    return status;
  }

  /**
   * Method is invoked when Auth transaction is processed from PG This method
   * makes a service call to create records to log in database and Payment
   * Integration Request Object is constructed using the ISO Message and
   * Response ISO Message is constructed using the Response Object of Service
   * integration
   */
  public boolean processAuth() {

    log.info("Purchase | processAuth | Entering");
    boolean status = false;

    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      // validating duplicate invoice request
      validateDuplicateRequest(_ISOInputRequest);
      // Populate Request Object
      AuthRequest authRequest = new AuthRequest();
      authRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      authRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      authRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      authRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      authRequest.setCardNum(_ISOInputRequest.get_cardNum());
      authRequest.setExpDate(_ISOInputRequest.get_expDate());
      authRequest.setTrack2(_ISOInputRequest.get_track2());
      authRequest.setPosEntryMode(_ISOInputRequest.get_Field22());
      authRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      authRequest.setEmvData(_ISOInputRequest.getEmvData());
      authRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      authRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      authRequest.setAcq_channel("POS");// TODO: need to set proper value
      authRequest.setAcq_mode("CASH");// TODO: need to set proper value
      authRequest.setMti(_ISOInputRequest.get_MTI());
      authRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      authRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      authRequest.setEntryMode(EntryModeEnum.fromValue(_ISOInputRequest.get_Field22().substring(0, 2)));
      authRequest.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(authRequest.getEntryMode().toString()
                                                                           + "_DE58"));
      authRequest.setTotalTxnAmount(_ISOInputRequest.get_txnAmount());
      authRequest.setTxnFee(PGConstants.ZERO);// TODO: need to change
      authRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      authRequest.setMode(_txnAuthorizer.getMode());

      // Service Call to create auth transaction records
      /*
       * AuthResponse authResponse =
       * getPaymentService().authTransaction(authRequest);
       */
      AuthResponse authResponse = new SwitchServiceBroker().authTransaction(authRequest);

      // set fields to response
      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());
      if(authResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, authResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, authResponse.getAuthId());
      }

      // feild 39 and 44
      setResponseFields(authResponse.getErrorCode());

      status = true;

    }
    catch(ServiceException e) {
      log.error("Purchase | processAuth | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(ValidationException e) {
      log.error("Purchase | processAuth | Exception :" + e.getMessage(), e);
      setResponseFields(e.getMessage());
      status = false;
    }
    catch(Exception e) {
      log.error("Purchase | processAuth | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.info("Purchase | processAuth | Exiting");
    return status;
  }

  /**
   * Method is invoked when Capture transaction (on successful Auth transaction)
   * is processed from PG This method makes a service call to create records to
   * log in database and Payment Integration Request Object is constructed using
   * the ISO Message and Response ISO Message is constructed using the Response
   * Object of Service integration
   */
  public boolean processCapture() {
    log.info("Purchase | processCapture | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      PGTransaction originalSale = voidtransactiondao.findTransactionToCaptureByPGTxnIdAndMerchantIdAndTerminalId(_ISOInputRequest.get_authTxnRefNum(),
                                                                                                              _ISOInputRequest.get_merchantId(),
                                                                                                              _ISOInputRequest.get_terminalId());
      // Populate Request Object
      CaptureRequest captureRequest = new CaptureRequest();
      captureRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      captureRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      captureRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      captureRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      captureRequest.setCardNum(_ISOInputRequest.get_cardNum());
      captureRequest.setExpDate(_ISOInputRequest.get_expDate());
      captureRequest.setTrack2(_ISOInputRequest.get_track2());
      captureRequest.setPosEntryMode(_ISOInputRequest.get_Field22());
      captureRequest.setAuthTxnRefNum(_ISOInputRequest.get_authTxnRefNum());
      captureRequest.setAuthId(_ISOInputRequest.get_authId());
      captureRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      captureRequest.setEmvData(_ISOInputRequest.getEmvData());
      captureRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      captureRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      captureRequest.setAcq_channel("POS");// TODO: need to set proper value
      captureRequest.setAcq_mode("CASH");// TODO: need to set proper value
      captureRequest.setMti(_ISOInputRequest.get_MTI());
      captureRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      captureRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      captureRequest.setEntryMode(EntryModeEnum.fromValue(_ISOInputRequest.get_Field22().substring(0, 2)));
      captureRequest.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(captureRequest.getEntryMode().toString()
                                                                              + "_DE58"));
      captureRequest.setTotalTxnAmount(_ISOInputRequest.get_txnAmount());
      captureRequest.setTxnFee(PGConstants.ZERO);// TODO: need to change
      captureRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      if(null != originalSale) {
        captureRequest.setIssuerTxnRefNum(originalSale.getIssuerTxnRefNum());
      }
      captureRequest.setMode(_txnAuthorizer.getMode());

      // Service Call to create auth transaction records
      /*
       * CaptureResponse captureResponse =
       * getPaymentService().captureTransaction(captureRequest);
       */
      CaptureResponse captureResponse = new SwitchServiceBroker().captureTransaction(captureRequest);

      // set response fields
      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());
      if(captureResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, captureResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, captureResponse.getAuthId());
      }

      setResponseFields(captureResponse.getErrorCode());
      status = true;

    }
    catch(ServiceException e) {
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(Exception e) {
      log.error("Purchase | processCapture | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.info("Purchase | processCapture | Exiting");
    return status;
  }

  /**
   * Method is invoked when Adjustment transaction (on successful Captured
   * transaction) is processed from PG This method makes a service call to
   * create records to log in database and Payment Integration Request Object is
   * constructed using the ISO Message and Response ISO Message is constructed
   * using the Response Object of Service integration
   */
  public boolean processAjustment() {
    log.info("Purchase | processAjustment | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      // Populate Request Object
      AdjustmentRequest adjustmentRequest = new AdjustmentRequest();
      adjustmentRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      adjustmentRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      adjustmentRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      adjustmentRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      adjustmentRequest.setCardNum(_ISOInputRequest.get_cardNum());
      adjustmentRequest.setExpDate(_ISOInputRequest.get_expDate());
      adjustmentRequest.setTrack2(_ISOInputRequest.get_track2());
      adjustmentRequest.setPosEntryMode(_ISOInputRequest.get_Field22());
      adjustmentRequest.setTxnRefNum(_ISOInputRequest.get_authTxnRefNum());
      adjustmentRequest.setAuthId(_ISOInputRequest.get_authId());
      adjustmentRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      adjustmentRequest.setAdjAmount(_ISOInputRequest.get_adjustedTxnAmount());
      adjustmentRequest.setEmvData(_ISOInputRequest.getEmvData());
      adjustmentRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      adjustmentRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      adjustmentRequest.setAcq_channel("POS");// TODO: need to set proper value
      adjustmentRequest.setAcq_mode("CASH");// TODO: need to set proper value
      adjustmentRequest.setMti(_ISOInputRequest.get_MTI());
      adjustmentRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      adjustmentRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      adjustmentRequest.setMode(_txnAuthorizer.getMode());

      // Service Call to create auth transaction records
      /*
       * AdjustmentResponse adjustmentResponse =
       * getPaymentService().adjustmentTransaction(adjustmentRequest);
       */
      AdjustmentResponse adjustmentResponse = new SwitchServiceBroker().adjustmentTransaction(adjustmentRequest);

      // set response fields
      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());
      if(adjustmentResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, adjustmentResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, adjustmentResponse.getAuthId());
      }

      setResponseFields(adjustmentResponse.getErrorCode());
      status = true;

    }
    catch(ServiceException e) {
      log.error("Purchase | processAjustment | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(Exception e) {
      log.error("Purchase | processAjustment | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.info("Purchase | processAjustment | Exiting");
    return status;
  }

  /**
   * Method is invoked when Network transaction request is processed
   */
  // TODO: implement this
  public void networkTransaction() {
    log.info("Purchase | networkTransaction | Entering");
    setResponseFields(ActionCode.ERROR_CODE_00);
    log.info("Purchase | networkTransaction | Exiting");
  }

  /**
   * Method is invoked when Network transaction request is processed
   */
  // TODO: implement this
  public void settlementTransaction() {
    log.info("Purchase | settlementTransaction | Entering");
    setResponseFields(ActionCode.ERROR_CODE_00);
    log.info("Purchase | settlementTransaction | Exiting");
  }

  /*
   * private PaymentService getPaymentService() { return
   * BINUpstreamRouter.getPaymentService(_ISOInputRequest.get_cardNum()); }
   */

  /**
   * Method is invoked when Adjustment transaction (on successful Captured
   * transaction) is processed from PG This method makes a service call to
   * create records to log in database and Payment Integration Request Object is
   * constructed using the ISO Message and Response ISO Message is constructed
   * using the Response Object of Service integration
   */
  public boolean balanceEnquiry() {
    log.info("Purchase | balanceEnquiry | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      // Populate Request Object
      BalanceEnquiryRequest balanceEnquiryRequest = new BalanceEnquiryRequest();
      balanceEnquiryRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      balanceEnquiryRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      balanceEnquiryRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      balanceEnquiryRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      balanceEnquiryRequest.setCardNum(_ISOInputRequest.get_cardNum());
      balanceEnquiryRequest.setExpDate(_ISOInputRequest.get_expDate());
      balanceEnquiryRequest.setTrack2(_ISOInputRequest.get_track2());
      balanceEnquiryRequest.setPosEntryMode(_ISOInputRequest.get_Field22());
      balanceEnquiryRequest.setEmvData(_ISOInputRequest.getEmvData());
      balanceEnquiryRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      balanceEnquiryRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      balanceEnquiryRequest.setAcq_channel("POS");// TODO: need to set proper
                                                  // value
      balanceEnquiryRequest.setAcq_mode("CASH");// TODO: need to set proper
                                                // value
      balanceEnquiryRequest.setMti(_ISOInputRequest.get_MTI());
      balanceEnquiryRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      balanceEnquiryRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      balanceEnquiryRequest.setMode(_txnAuthorizer.getMode());

      // Service Call to create auth transaction records
      /*
       * BalanceEnquiryResponse balanceEnquiryResponse =
       * getPaymentService().balanceEnquiryTransaction(balanceEnquiryRequest);
       */
      BalanceEnquiryResponse balanceEnquiryResponse = new SwitchServiceBroker().balanceEnquiry(balanceEnquiryRequest);

      // set response fields
      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());
      if(balanceEnquiryResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, balanceEnquiryResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, balanceEnquiryResponse.getAuthId());
        // balance amount
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(54, balanceEnquiryResponse.getBalance());
      }

      setResponseFields(balanceEnquiryResponse.getErrorCode());
      status = true;
    }
    catch(ServiceException e) {
      log.error("Purchase | balanceEnquiry | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(Exception e) {
      log.error("Purchase | balanceEnquiry | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.info("Purchase | balanceEnquiry | Exiting");
    return status;
  }

  /**
   * Method is invoked when cash withdrwal transaction is processed This method
   * makes a service call to create records to log in database and Payment
   * Integration Request Object is constructed using the ISO Message and
   * Response ISO Message is constructed using the Response Object of Service
   * integration
   */
  public boolean processCashWithdrawal() {
    log.debug("Purchase | processCashWithdrawal | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      // Populate Request Object
      CashWithdrawalRequest cashWithdrawalRequest = new CashWithdrawalRequest();
      cashWithdrawalRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      cashWithdrawalRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      cashWithdrawalRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      cashWithdrawalRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      cashWithdrawalRequest.setCardNum(_ISOInputRequest.get_cardNum());
      cashWithdrawalRequest.setExpDate(_ISOInputRequest.get_expDate());
      cashWithdrawalRequest.setTrack2(_ISOInputRequest.get_track2());
      cashWithdrawalRequest.setPosEntryMode(_ISOInputRequest.get_Field22());
      cashWithdrawalRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      cashWithdrawalRequest.setEmvData(_ISOInputRequest.getEmvData());
      cashWithdrawalRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      cashWithdrawalRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      cashWithdrawalRequest.setAcq_channel("POS");// TODO: need to set proper
                                                  // value
      cashWithdrawalRequest.setAcq_mode("CASH");// TODO: need to set proper
                                                // value
      cashWithdrawalRequest.setMti(_ISOInputRequest.get_MTI());
      cashWithdrawalRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      cashWithdrawalRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      cashWithdrawalRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      cashWithdrawalRequest.setMode(_txnAuthorizer.getMode());

      /*
       * CashWithdrawalResponse cashWithdrawalResponse =
       * getPaymentService().cashWithdrawalTransaction(cashWithdrawalRequest);
       */
      CashWithdrawalResponse cashWithdrawalResponse = new SwitchServiceBroker().cashWithdrawalTransaction(cashWithdrawalRequest);

      // set fields to response
      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());

      if(cashWithdrawalResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, cashWithdrawalResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, cashWithdrawalResponse.getAuthId());
      }

      // feild 39 and 44
      setResponseFields(cashWithdrawalResponse.getErrorCode());

      status = true;
    }
    catch(Exception e) {
      log.error("Purchase | processCashWithdrawal | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    

    log.debug("Purchase | processCashWithdrawal | Exiting");
    return status;
  }

  /**
   * Method is invoked when cash back transaction is processed This method makes
   * a service call to create records to log in database and Payment Integration
   * Request Object is constructed using the ISO Message and Response ISO
   * Message is constructed using the Response Object of Service integration
   */
  public boolean processCashBack() {
    log.debug("Purchase | processCashBack | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      // Populate Request Object
      CashBackRequest cashBackRequest = new CashBackRequest();
      cashBackRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      cashBackRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      cashBackRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      cashBackRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      cashBackRequest.setCardNum(_ISOInputRequest.get_cardNum());
      cashBackRequest.setExpDate(_ISOInputRequest.get_expDate());
      cashBackRequest.setTrack2(_ISOInputRequest.get_track2());
      cashBackRequest.setPosEntryMode(_ISOInputRequest.get_Field22());
      cashBackRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      cashBackRequest.setEmvData(_ISOInputRequest.getEmvData());
      cashBackRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      cashBackRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      cashBackRequest.setAcq_channel("POS");// TODO: need to set proper value
      cashBackRequest.setAcq_mode("CASH");// TODO: need to set proper value
      cashBackRequest.setMti(_ISOInputRequest.get_MTI());
      cashBackRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      cashBackRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      cashBackRequest.setEntryMode(EntryModeEnum.fromValue(_ISOInputRequest.get_Field22().substring(0, 2)));
      cashBackRequest.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(cashBackRequest.getEntryMode().toString()
                                                                               + "_DE58"));
      cashBackRequest.setTotalTxnAmount(_ISOInputRequest.get_txnAmount());
      cashBackRequest.setTxnFee(PGConstants.ZERO);// TODO: need to change
      cashBackRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      cashBackRequest.setMode(_txnAuthorizer.getMode());
      
      /*
       * CashBackResponse cashBackResponse =
       * getPaymentService().cashBackTransaction(cashBackRequest);
       */
      CashBackResponse cashBackResponse = new SwitchServiceBroker().processCashBackTransaction(cashBackRequest);

      // set fields to response
      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());

      if(cashBackResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, cashBackResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, cashBackResponse.getAuthId());
      }

      // feild 39 and 44
      setResponseFields(cashBackResponse.getErrorCode());

      status = true;
    }
    catch(ServiceException e) {
      log.error("Purchase | processCashBack | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(Exception e) {
      log.error("Purchase | processCashBack | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.debug("Purchase | processCashBack | Exiting");
    return status;
  }

  /**
   * <<Method to validate the duplicate invoice entry for sale and auth txn
   * request>>
   * 
   * @param _InRequest
   * @throws ValidationException
   */
  public void validateDuplicateRequest(ISOInRequest _InRequest) throws ValidationException {
    PGTransaction duplicateAmountTxn;
    try {
      duplicateAmountTxn = voidtransactiondao.findDuplicateTransactionOnPanAndInvoiceNumberAndMerchantIdAndTerminalIdAndTxnAmount(EncryptionUtil.encrypt(_InRequest.get_cardNum()),
                                                                                                                              _InRequest.get_invoiceNumber(),
                                                                                                                              _InRequest.get_merchantId(),
                                                                                                                              _InRequest.get_terminalId(),
                                                                                                                              _InRequest.get_txnAmount());
      if(null != duplicateAmountTxn) {
        Timestamp previousRequest = duplicateAmountTxn.getUpdatedDate();
        if(null != previousRequest) {
          Calendar calendar = Calendar.getInstance();
          Date currentDateAndTime = calendar.getTime();
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
          long pastHoursData = 0;
          try {
            pastHoursData = currentDateAndTime.getHours()
                            - simpleDateFormat.parse(previousRequest.toString()).getHours();
          }
          catch(ParseException e) {
            throw new ValidationException(ActionErrorCode.ERROR_CODE_Z5);
          }
          if(pastHoursData >= 0 && pastHoursData < 24) {
            throw new ValidationException(ActionErrorCode.ERROR_CODE_SAME_PAN_SAME_AMOUNT);
          }
        }

      }
    }
    catch(Exception e) {
      throw new ValidationException(e.getMessage());
    }
    PGTransaction duplicateInvoiceTxn = transactionDao.getTransactionOnInvoiceNum(_InRequest.get_merchantId(),
                                                                                  _InRequest.get_terminalId(),
                                                                                  _InRequest.get_invoiceNumber());

    if(null != duplicateInvoiceTxn) {
      throw new ValidationException(ActionErrorCode.ERROR_CODE_DUPLICATE_INVOICE);
    }

  }

}