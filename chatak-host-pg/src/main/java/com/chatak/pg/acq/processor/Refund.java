package com.chatak.pg.acq.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.service.PaymentService;
import com.chatak.pg.acq.spring.util.SpringDAOBeanFactory;
import com.chatak.pg.bean.RefundRequest;
import com.chatak.pg.bean.RefundResponse;
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.ReversalResponse;
import com.chatak.pg.bean.VoidRequest;
import com.chatak.pg.bean.VoidResponse;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.NationalPOSEntryModeEnum;
import com.chatak.pg.upstream.BINUpstreamRouter;
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.PGConstants;
import com.chatak.pg.util.Properties;
import com.chatak.switches.sb.SwitchServiceBroker;
import com.chatak.switches.sb.exception.ServiceException;

/**
 * @Comments : This class process the Refund / credit transaction
 */
public class Refund extends Processor {
  private static Logger log = Logger.getLogger(Refund.class);
  @Autowired
  private ApplicationContext appContext;
  @Autowired
  private TransactionDao transactionDao;
  @Autowired
  private VoidTransactionDao voidtransactiondao;

  public Refund(TxnAuthorizer txnAuth) {
    super(txnAuth);
    AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
    acbFactory.autowireBean(this);
  }
  
  private PaymentService getPaymentService() {
    return BINUpstreamRouter.getPaymentService(_ISOInputRequest.get_cardNum());
  }

  /**
   * Method is invoked when Void transaction (on successful Auth transaction) is
   * processed from PG This method makes a service call to create records to log
   * in database and Payment Integration Request Object is constructed using the
   * ISO Message and Response ISO Message is constructed using the Response
   * Object of Service integration
   */
  public boolean processVoid() {
    log.info("Refund | processVoid | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      PGTransaction originalSale =voidtransactiondao.findTransactionToVoidByPGTxnIdAndMerchantIdAndTerminalId( _ISOInputRequest.get_authTxnRefNum(),_ISOInputRequest.get_merchantId(),_ISOInputRequest.get_terminalId());
      // Populate Request Object
      VoidRequest voidRequest = new VoidRequest();
      voidRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      voidRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      voidRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      voidRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      voidRequest.setAuthId(_ISOInputRequest.get_authId());
      voidRequest.setTxnRefNum(_ISOInputRequest.get_authTxnRefNum());
      voidRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());

      voidRequest.setCardNum(_ISOInputRequest.get_cardNum());
      voidRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      voidRequest.setExpDate(_ISOInputRequest.get_expDate());
      voidRequest.setTrack2(_ISOInputRequest.get_track2());
      voidRequest.setEmvData(_ISOInputRequest.getEmvData());
      voidRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      voidRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      voidRequest.setAcq_channel("POS");//TODO: need to set proper value
      voidRequest.setAcq_mode("CASH");//TODO: need to set proper value
      voidRequest.setMti(_ISOInputRequest.get_MTI());
      voidRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      voidRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      voidRequest.setEntryMode(EntryModeEnum.fromValue(_ISOInputRequest.get_Field22().substring(0,2)));
      voidRequest.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(voidRequest.getEntryMode().toString()+"_DE58"));
      voidRequest.setTotalTxnAmount(_ISOInputRequest.get_txnAmount());
      voidRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      if(null!=originalSale){
        voidRequest.setIssuerTxnRefNum(originalSale.getIssuerTxnRefNum());
      }

      // Service Call to Void transaction
     /* PaymentService paymentService = new PaymentServiceImpl();
      VoidResponse voidResponse = paymentService.voidTransaction(voidRequest);*/
      /*VoidResponse voidResponse =  getPaymentService().voidTransaction(voidRequest);*/
      VoidResponse voidResponse = new SwitchServiceBroker().voidTransaction(voidRequest, originalSale);

      // Amount void transaction set to 00
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(4, PGConstants.VOID_TXN_AMOUNT);

      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());

      // set fields to response
      if(voidResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37,voidResponse.getTxnRefNum());
      }

      // feild 39 and 44
      setResponseFields(voidResponse.getErrorCode());
      status = true;

    }
    catch(ServiceException e) {
      log.error("Refund | processVoid | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(Exception e) {
      log.error("Refund | processVoid | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.info("Refund | processVoid | Exiting");
    return status;
  }

  /**
   * Method is invoked when Reversal transaction is processed from PG The
   * Reversal message is sent if the terminal sent a transaction request into
   * the network, and did not receive any response before the transaction
   * time-out period expired This method makes a service call to create records
   * to log in database and Payment Integration Request Object is constructed
   * using the ISO Message and Response ISO Message is constructed using the
   * Response Object of Service integration
   */
  public boolean processReversal() {
    log.info("Refund | processReversal | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      PGTransaction originalTxn =voidtransactiondao.findTransactionToReversalByMerchantIdAndPGTxnId(_ISOInputRequest.get_merchantId(), _ISOInputRequest.get_authTxnRefNum());
      // Populate Request Object
      ReversalRequest reversalRequest = new ReversalRequest();
      reversalRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      reversalRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      reversalRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      reversalRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());

      reversalRequest.setCardNum(_ISOInputRequest.get_cardNum());
      reversalRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      reversalRequest.setExpDate(_ISOInputRequest.get_expDate());
      reversalRequest.setTrack2(_ISOInputRequest.get_track2());
      reversalRequest.setEmvData(_ISOInputRequest.getEmvData());
      reversalRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      reversalRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      reversalRequest.setAcq_channel("POS");//TODO: need to set proper value
      reversalRequest.setAcq_mode("CASH");//TODO: need to set proper value
      reversalRequest.setMti(_ISOInputRequest.get_MTI());
      reversalRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      reversalRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      reversalRequest.setEntryMode(EntryModeEnum.fromValue(_ISOInputRequest.get_Field22().substring(1,3)));
      reversalRequest.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(reversalRequest.getEntryMode().toString()+"_DE58"));
      reversalRequest.setTotalTxnAmount(_ISOInputRequest.get_txnAmount());
      reversalRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      
      if(null!=originalTxn){
        reversalRequest.setIssuerTxnRefNum(originalTxn.getIssuerTxnRefNum());
      }

      // Service Call to Void transaction
      /*PaymentService paymentService = new PaymentServiceImpl();
      ReversalResponse reversalResponse = paymentService.reversalTransaction(reversalRequest);*/
     /* ReversalResponse reversalResponse =  getPaymentService().reversalTransaction(reversalRequest);*/
      ReversalResponse reversalResponse =  new SwitchServiceBroker().reversalTransaction(reversalRequest);

      // SYSTEM DATE TIME
      _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(12, DateUtils.getCurrentTime());

      // set fields to response
      if(reversalResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, reversalResponse.getTxnRefNum());
        // AuthId
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, reversalResponse.getAuthId());
      }

      // feild 39 and 44
      setResponseFields(reversalResponse.getErrorCode());
      status = true;

    }
    catch(ServiceException e) {
      log.error("Refund | processReversal | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(Exception e) {
      log.error("Refund | processReversal | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.info("Refund | processReversal | Exiting");
    return status;
  }

  /**
   * Method is invoked when Refund transaction (on Successful Sale Transaction)
   * is processed from PG This method makes a service call to create records to
   * log in database and Payment Integration Request Object is constructed using
   * the ISO Message and Response ISO Message is constructed using the Response
   * Object of Service integration
   */
  public boolean processRefund() {
    log.info("Refund | processRefund | Entering");
    boolean status = false;
    try {
      com.chatak.switches.sb.util.SpringDAOBeanFactory.appContext = appContext;
      PGTransaction originalTxn =voidtransactiondao.findTransactionToRefundByPGTxnIdAndMerchantIdAndTerminalId(_ISOInputRequest.get_authTxnRefNum(),_ISOInputRequest.get_merchantId(),_ISOInputRequest.get_terminalId());
      // Populate Request Object
      RefundRequest refundRequest = new RefundRequest();
      refundRequest.setTerminalId(_ISOInputRequest.get_terminalId());
      refundRequest.setMerchantId(Long.valueOf(_ISOInputRequest.get_merchantId()));
      refundRequest.setSysTraceNum(_ISOInputRequest.get_sysTraceNum());
      refundRequest.setInvoiceNumber(_ISOInputRequest.get_invoiceNumber());
      // refundRequest.setTxnRefNum(saleTxnId);
      refundRequest.setCardNum(_ISOInputRequest.get_cardNum());
      refundRequest.setTxnAmount(_ISOInputRequest.get_txnAmount());
      refundRequest.setExpDate(_ISOInputRequest.get_expDate());
      refundRequest.setTrack2(_ISOInputRequest.get_track2());
      refundRequest.setEmvData(_ISOInputRequest.getEmvData());
      refundRequest.setChipTransaction(_ISOInputRequest.get_isChipTransaction());
      refundRequest.setChipFallback(_ISOInputRequest.get_isFallback());
      refundRequest.setAcq_channel("POS");//TODO: need to set proper value
      refundRequest.setAcq_mode("CASH");//TODO: need to set proper value
      refundRequest.setMti(_ISOInputRequest.get_MTI());
      refundRequest.setProcessingCode(_ISOInputRequest.get_processingCode());
      refundRequest.setIsoMsg(_ISOInputRequest.getIsoMsg());
      refundRequest.setEntryMode(EntryModeEnum.fromValue(_ISOInputRequest.get_Field22().substring(0,2)));
      refundRequest.setNationalPOSEntryMode(NationalPOSEntryModeEnum.valueOf(refundRequest.getEntryMode().toString()+"_DE58"));
      refundRequest.setTotalTxnAmount(_ISOInputRequest.get_txnAmount());
      refundRequest.setPulseData(Properties.getProperty("chatak-pay.pulse.data"));
      if(null!=originalTxn){
        refundRequest.setIssuerTxnRefNum(originalTxn.getIssuerTxnRefNum());
        refundRequest.setSaleDependentRefund(true);//flag to set sale dependent refund
      }
      else{
        refundRequest.setSaleDependentRefund(false);//flag to set sale independent refund
      }
      // Service Call to Void transaction
      /*PaymentService paymentService = new PaymentServiceImpl();
      RefundResponse refundResponse = paymentService.refundTransaction(refundRequest);*/
      /*RefundResponse refundResponse =  getPaymentService().refundTransaction(refundRequest);*/
      RefundResponse refundResponse = new SwitchServiceBroker().refundTransaction(refundRequest);

      // set fields to response
      if(refundResponse.getErrorCode().equalsIgnoreCase(ActionCode.ERROR_CODE_00)) {
        // Transaction Reference Number
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(37, refundResponse.getTxnRefNum());
        _txnAuthorizer.get_txnHandler().getResponseMessage().setFieldValue(38, refundResponse.getAuthId());
      }

      // feild 39 and 44
      setResponseFields(refundResponse.getErrorCode());
      status = true;

    }
    catch(ServiceException e) {
      log.error("Refund | processRefund | ServiceException :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }
    catch(Exception e) {
      log.error("Refund | processRefund | Exception :" + e.getMessage(), e);
      setResponseFields(ActionCode.ERROR_CODE_Z5);
      status = false;
    }

    log.info("Refund | processRefund | Exiting");
    return status;
  }

}