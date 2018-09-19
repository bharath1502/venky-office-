/**
 * 
 */
package com.chatak.switches.sb;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.AccountFeeLogDao;
import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAccountFeeLog;
import com.chatak.pg.acq.dao.model.PGAccountTransactions;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.acq.dao.model.PGSwitchTransaction;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
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
import com.chatak.pg.bean.PurchaseRequest;
import com.chatak.pg.bean.PurchaseResponse;
import com.chatak.pg.bean.RefundRequest;
import com.chatak.pg.bean.RefundResponse;
import com.chatak.pg.bean.Request;
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.ReversalResponse;
import com.chatak.pg.bean.VoidRequest;
import com.chatak.pg.bean.VoidResponse;
import com.chatak.pg.constants.AccountTransactionCode;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.pg.model.ProcessingFee;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.prepaid.ChatakPrepaidSwitchTransaction;
import com.chatak.switches.sb.exception.ServiceException;
import com.chatak.switches.sb.util.SpringDAOBeanFactory;
import com.chatak.switches.services.PaymentService;
import com.chatak.switches.services.TransactionService;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 22-May-2015 11:53:34 AM
 * @version 1.0
 */
public class SwitchServiceBroker extends TransactionService {

  private static Logger logger = Logger.getLogger(SwitchServiceBroker.class);

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  RefundTransactionDao refundTransactionDao;

  BINUpstreamRouter binUpstreamRouter;

  PaymentService paymentService = null;

  public SwitchServiceBroker() {
    AutowireCapableBeanFactory acbFactory =
        SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
    acbFactory.autowireBean(this);
  }

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  AccountFeeLogDao accountFeeLogDao;

  @Autowired
  CurrencyConfigDao currencyConfigDao;

  @Autowired
  TransactionRepository transactionRepository;
  
  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  /**
   * Method to Authorise a payment transaction Steps involved 1. Validate
   * Request 2. TODO:PAYMENT INTEGRATION 3. Create Transaction record 4. Create
   * TXN_CARD_INFO RECORD 5. SET response fields 6. return response
   * 
   * @param authRequest
   * @return AuthResponse
   * @throws ServiceException
   */
  public AuthResponse authTransaction(AuthRequest authRequest) throws ServiceException {
    logger.info("SwitchServiceBroker | authTransaction | Entering");
    AuthResponse authResponse = new AuthResponse();
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());

    PGSwitchTransaction pgSwitchTransaction = new PGSwitchTransaction();
    PGTransaction pgTransaction = new PGTransaction();

    try {
      // validation of Request
      validateRequest(authRequest);

      // Create Transaction record
      pgTransaction = populatePGTransaction(authRequest, PGConstants.TXN_TYPE_AUTH);
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);

      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(authRequest);
      pgSwitchTransaction.setProcessorResponsePostDate(new Timestamp(System.currentTimeMillis()));

      paymentService = binUpstreamRouter.getPaymentService();
      if (authRequest.getCardNum() != null && authRequest.getCardNum().length() > Constants.FOUR) {
        logger.info("SwitchServiceBroker::AUTH Transaction for ending Card: "
            + authRequest.getCardNum().substring(authRequest.getCardNum().length() - Constants.FOUR,
                authRequest.getCardNum().length()));
      }
      /********* Sending to upstream processor *********/
      // Calling the Upstream processor service
      authResponse = paymentService.authTransaction(authRequest);

      logger.info("SwitchServiceBroker::AUTH Transaction Response: " + authResponse.getErrorCode()
          + "::" + authResponse.getErrorMessage());

      pgSwitchTransaction
          .setProcessorResponseTime(new Timestamp(authResponse.getTxnResponseTime()));

      // Update transaction status and switch response
      pgTransaction.setStatus(authResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(authResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());

      pgSwitchTransaction.setProcessorAuthCode(authResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setProcessorMessage(authResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(authResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setProcessorResponseMsg(authResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(authResponse.getUpStreamResponse());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
        pgTransaction.setTxnDescription(PGConstants.TXN_TYPE_AUTH.toUpperCase());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_PENDING);
        authResponse.setAuthId(pgTransaction.getAuthId());
      } else {
        pgTransaction.setTxnDescription(authResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
      }
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      voidTransactionDao.createTransaction(pgTransaction);

      // Set Response fields
      authResponse.setTxnRefNum(pgTransaction.getTransactionId());
      authResponse.setTxnAmount(authRequest.getTxnAmount());
      authResponse.setFeeAmount(pgTransaction.getFeeAmount());
      authResponse.setTotalAmount(pgTransaction.getTxnTotalAmount());
      authResponse.setErrorMessage(authResponse.getUpStreamMessage());

    } catch (ServiceException e) {
      logger.error(Constants.EXCEPTIONS + e);

      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
      pgTransaction
          .setTxnDescription(ActionCode.getInstance().getMessage(authResponse.getErrorCode()));
      voidTransactionDao.createTransaction(pgTransaction);
      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      authResponse.setErrorCode(e.getMessage());
      authResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(authResponse.getErrorCode()));
      logger.error("SwitchServiceBroker | authTransaction | ServiceException :", e);
    } catch (DataAccessException e) {
      authRequest.setReversalReason(e.getMessage());
      autoReversal(populateReversalRequest(authRequest, authResponse));// Reversaing
      // transaction
      authResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      authResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      validatePGTransaction(pgSwitchTransaction, pgTransaction);
      logger.error("SwitchServiceBroker | authTransaction | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      pgTransactionValidation(pgSwitchTransaction, pgTransaction);

      authResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      authResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | authTransaction | Exiting");
    return authResponse;
  }

private void pgTransactionValidation(PGSwitchTransaction pgSwitchTransaction, PGTransaction pgTransaction) {
	pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
      pgTransaction
          .setTxnDescription(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      voidTransactionDao.createTransaction(pgTransaction);
}

private void validatePGTransaction(PGSwitchTransaction pgSwitchTransaction, PGTransaction pgTransaction) {
      logger.info("Entering :: SwitchServiceBroker :: validatePGTransaction");

      if (pgTransaction != null) {
      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
      pgTransaction
          .setTxnDescription(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      voidTransactionDao.createTransaction(pgTransaction);
      }
      if (pgSwitchTransaction != null) {
      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      }

      logger.info("Entering :: SwitchServiceBroker :: validatePGTransaction");
}

  /**
   * Method to Capture the Authorised Payment Transaction Steps involved 1.
   * Validate Request TODO:PAYMENT INTEGRATION 2. Validate Auth Transaction
   * reference Number 2. Create Transaction record 3. TXN_CARD_INFO RECORD not
   * created as it is available in Auth transaction 4. SET response fields 5.
   * return response
   * 
   * @param captureRequest
   * @return CaptureResponse
   * @throws ServiceException
   */
  public CaptureResponse captureTransaction(CaptureRequest captureRequest) throws ServiceException {
    logger.info("SwitchServiceBroker | captureTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    CaptureResponse captureResponse = new CaptureResponse();
    PGSwitchTransaction pgSwitchTransaction = new PGSwitchTransaction();
    PGTransaction pgTransaction = new PGTransaction();

    try {
      // validation of Request
      validateRequest(captureRequest);

      // 2. Validate Auth transaction Id
      PGTransaction authTransaction = voidTransactionDao
          .findTransactionToCaptureByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
              captureRequest.getAuthTxnRefNum(), captureRequest.getIssuerTxnRefNum(),
              captureRequest.getMerchantCode(), captureRequest.getTerminalId());

      if (authTransaction == null) {
        throw new ServiceException(ActionCode.ERROR_CODE_78);
      }
      // checking for is already auth captured or not?
      PGTransaction lookForSale =
          transactionDao.getTransactionOnRefNumber(captureRequest.getMerchantCode(),
              captureRequest.getTerminalId(), authTransaction.getTransactionId());
      if (!(lookForSale == null)) {
        Integer checkCapturedStatus = lookForSale.getStatus();
        if (checkCapturedStatus == 0) {
          throw new ServiceException(ActionCode.ERROR_CODE_94);
        }
      }

      // Create Transaction record
      pgTransaction = populatePGTransaction(captureRequest, PGConstants.TXN_TYPE_SALE);

      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
      pgTransaction.setRefTransactionId(authTransaction.getTransactionId());
      pgTransaction.setAuthId(authTransaction.getAuthId());
      pgTransaction.setTxnAmount(authTransaction.getTxnAmount());
      pgTransaction.setFeeAmount(authTransaction.getFeeAmount());
      pgTransaction.setTxnTotalAmount(authTransaction.getTxnTotalAmount());
      pgTransaction.setReason(authTransaction.getReason());
      pgTransaction.setCardHolderEmail(authTransaction.getCardHolderEmail());

      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(captureRequest);

      paymentService = binUpstreamRouter.getPaymentService();

      if (captureRequest.getCardNum() != null && captureRequest.getCardNum().length() > Constants.FOUR) {
        logger.info("SwitchServiceBroker::CAPTURE Transaction for ending Card: "
            + captureRequest.getCardNum().substring(captureRequest.getCardNum().length() - Constants.FOUR,
                captureRequest.getCardNum().length()));
      }
      /********* Sending to upstream processor *********/
      // Calling the Upstream processor service
      captureResponse = paymentService.captureTransaction(captureRequest);

      logger.info("SwitchServiceBroker::CAPTURE Transaction Response: "
          + captureResponse.getErrorCode() + "::" + captureResponse.getErrorMessage());

      pgTransaction.setStatus(captureResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(captureResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());
      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_PENDING);
        String descriptionTemplate =
            Properties.getProperty("chatak-pay.pending.description.template");
        descriptionTemplate =
            MessageFormat.format(descriptionTemplate, pgTransaction.getTransactionId());
        pgTransaction.setTxnDescription(descriptionTemplate);
        authTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        descriptionTemplate =
            Properties.getProperty("chatak-pay.auth.completion.description.template");
        descriptionTemplate =
            MessageFormat.format(descriptionTemplate, pgTransaction.getTransactionId());
        authTransaction.setTxnDescription(descriptionTemplate);
        PGAccount pgAccount = logAccountTransaction(pgTransaction,captureRequest);//Logging account transactions
        logPgAccountFee(pgTransaction, pgAccount);
        voidTransactionDao.createTransaction(authTransaction);
      } else {
        pgTransaction.setTxnDescription(captureResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
      }
      // Update transaction status and switch response
      voidTransactionDao.createTransaction(pgTransaction);

      // updating pgswitch after response
      pgSwitchTransaction.setProcessorMessage(captureResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponseMsg(captureResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(captureResponse.getUpStreamResponse());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Set Response attributes
      captureResponse.setTxnRefNum(pgTransaction.getTransactionId());
      captureResponse.setAuthId(captureResponse.getUpStreamAuthCode());
      captureResponse.setTxnAmount(captureRequest.getTxnAmount());
      captureResponse.setFeeAmount(authTransaction.getFeeAmount());
      captureResponse.setTotalAmount(authTransaction.getTxnTotalAmount());
    } catch (ServiceException e) {
      captureResponse.setErrorCode(e.getMessage());
      captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      pgTransaction.setTxnDescription(ActionCode.getInstance().getMessage(e.getMessage()));
      voidTransactionDao.createTransaction(pgTransaction);
      logger.error("SwitchServiceBroker | captureTransaction | ServiceException :", e);
    } catch (DataAccessException e) {
      captureRequest.setReversalReason(e.getMessage());
      autoReversal(populateReversalRequest(captureRequest, captureResponse));
      captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      captureResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      pgTransactionValidation(pgSwitchTransaction, pgTransaction);
      logger.error("SwitchServiceBroker | captureTransaction | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      pgTransactionValidation(pgSwitchTransaction, pgTransaction);

      captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      captureResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | captureTransaction | Exiting");
    return captureResponse;
  }

  /**
   * Method to Auth-Capture Payment Transaction Steps Involved 1. Validate
   * Request TODO:PAYMENT INTEGRATION 2. Create Transaction record 3. Create
   * TXN_CARD_INFO RECORD 4. SET response fields 5. return response
   * 
   * @param purchaseRequest
   * @return PurchaseResponse
   * @throws ServiceException
   */
  public PurchaseResponse purchaseTransaction(PurchaseRequest purchaseRequest, PGMerchant pgMerchant)
      throws ServiceException, NestedRuntimeException {
    logger.info("SwitchServiceBroker | purchaseTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    PurchaseResponse purchaseResponse = new PurchaseResponse();

    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    try {
      // validation of Request
      validateRequest(purchaseRequest);
      // Create Transaction record
      pgTransaction = populatePGTransaction(purchaseRequest, PGConstants.TXN_TYPE_SALE);
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
      pgTransaction.setUserName(purchaseRequest.getUserName());
      
      pgTransaction.setPmId(purchaseRequest.getPmId());
      pgTransaction.setIsoId(purchaseRequest.getIsoId());

      //Commenting since there is already another call at the bottom where the transaction is updated.
      /*voidTransactionDao.createTransaction(pgTransaction);*/


      // PERF >> Will use the primary auto increment key as the transaction id
      // Set the gateway transaction id
      //purchaseRequest.setTransactionId(pgTransaction.getTransactionId());
      
      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(purchaseRequest);

      paymentService = binUpstreamRouter.getPaymentService();
      fetchCardNumberLength(purchaseRequest);
      /********* Sending to upstream processor *********/

      //purchaseResponse = paymentService.purchaseTransaction(purchaseRequest);
      String mock = Properties.getProperty("issuance.mock");
      if ("true".equals(mock)) {
        purchaseResponse = paymentService.purchaseTransactionMock();
      } else {
        purchaseResponse = paymentService.purchaseTransaction(purchaseRequest);
      }

      logger.info("SwitchServiceBroker::SALE Transaction Response: "
          + purchaseResponse.getErrorCode() + "::" + purchaseResponse.getErrorMessage());
      pgTransaction.setStatus(purchaseResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(purchaseResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());

      pgSwitchTransaction.setProcessorMessage(purchaseResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponse(purchaseResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponseMsg(purchaseResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorAuthCode(purchaseResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      pgTransaction.setIssuancePartner(purchaseResponse.getIssuancePartner());
      //setting transaction based on processor response
      pgTransaction.setDeviceLocalTxnTime(purchaseResponse.getIssuanceTxnTime() == null
    		  ? pgTransaction.getDeviceLocalTxnTime() : purchaseResponse.getIssuanceTxnTime());
      
      // PERF >> Moving it down to after transaction insertion
      // Update account
      //statusValidation(purchaseRequest, purchaseResponse, pgTransaction);

      String autoSettlement = pgMerchant.getMerchantConfig().getAutoSettlement() !=null ? pgMerchant.getMerchantConfig().getAutoSettlement().toString() : "1";
      autoSettlement = (autoSettlement!=null && autoSettlement.equals("1")) ? Constants.AUTO_SETTLEMENT_STATUS_YES
          : Constants.BATCH_STATUS_NA;

      if (ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
        pgTransaction.setEftStatus(PGConstants.LITLE_EXECUTED);
      }
      pgTransaction.setBatchId(Constants.BATCH_STATUS_NA);
      pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
      pgTransaction.setAutoSettlementStatus(autoSettlement);

      // PERF >> Moving it down to after transaction insertion
      //switchTransactionDao.createTransaction(pgSwitchTransaction);
      logger.info("transaction id is " + pgTransaction.getTransactionId());

      if(purchaseResponse.getErrorCode().equals(ActionCode.ERROR_CODE_UID)) {
    	  pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_REJECTED);  
      }
      
      // Update transaction status and switch response
      PGTransaction insertedPgTransaction = voidTransactionDao.createTransaction(pgTransaction);

      // PERF >> Changes related to transaction id
      // Fetch inserted TXN ID
      BigInteger transactionID = insertedPgTransaction.getId();
      pgSwitchTransaction.setPgTransactionId(String.valueOf(transactionID));
      
      asyncService.saveSwitchTransaction(pgSwitchTransaction);
//      switchTransactionDao.createTransaction(pgSwitchTransaction);
      // Update account
      pgTransaction.setTransactionId(String.valueOf(transactionID));
      statusValidation(purchaseRequest, purchaseResponse, pgTransaction);
      
      // Set Response fields
      //purchaseResponse.setTxnRefNum(pgTransaction.getTransactionId());
      // PERF >> 
      purchaseResponse.setTxnRefNum(String.valueOf(transactionID));
      
      purchaseResponse.setTxnAmount(purchaseRequest.getTxnAmount());
      purchaseResponse.setFeeAmount(pgTransaction.getFeeAmount());
      purchaseResponse.setTotalAmount(pgTransaction.getTxnTotalAmount());
      purchaseResponse.setErrorMessage(purchaseResponse.getUpStreamMessage());
      purchaseResponse.setIssuerTxnRefNum(pgTransaction.getIssuerTxnRefNum());
    } catch (ServiceException e) {
      logger.error(Constants.EXCEPTIONS + e.getMessage(), e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
        pgTransaction.setTxnDescription(
            ActionCode.getInstance().getMessage(purchaseResponse.getErrorCode()));
        voidTransactionDao.createTransaction(pgTransaction);

        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        //switchTransactionDao.createTransaction(pgSwitchTransaction);
        asyncService.saveSwitchTransaction(pgSwitchTransaction);
      }

      purchaseResponse.setErrorCode(e.getMessage());
      purchaseResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(purchaseResponse.getErrorCode()));
      logger.error("SwitchServiceBroker | purchaseTransaction | ServiceException :", e);
    } catch (DataAccessException e) {
      logger.error("DataAccessException" + e.getMessage(), e);
      purchaseResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      purchaseResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        purchaseRequest.setReversalReason(e.getMessage());
        autoReversal(populateReversalRequest(purchaseRequest, purchaseResponse));// Reversing
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }

      logger.error("SwitchServiceBroker | purchaseTransaction | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e.getMessage(), e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }

      purchaseResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      purchaseResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | purchaseTransaction | Exiting");
    return purchaseResponse;
  }

private boolean checkPgTransaction(PGSwitchTransaction pgSwitchTransaction, PGTransaction pgTransaction) {
	return null != pgTransaction && null != pgSwitchTransaction;
}

  private void fetchCardNumberLength(PurchaseRequest purchaseRequest) {
	if (purchaseRequest.getCardNum() != null && purchaseRequest.getCardNum().length() > Constants.FOUR) {
        logger.info("SwitchServiceBroker::SALE Transaction for ending Card: "
            + purchaseRequest.getCardNum().substring(purchaseRequest.getCardNum().length() - Constants.FOUR,
                purchaseRequest.getCardNum().length()));
      }
  }

  private void statusValidation(PurchaseRequest purchaseRequest, PurchaseResponse purchaseResponse,
		PGTransaction pgTransaction) throws Exception {
	  logger.info(" With Status : " + pgTransaction.getStatus() + " and Error Code : " + purchaseResponse.getErrorCode());

	if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {

        pgTransaction.setRefundStatus(0);

        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_PROCESSING);
        if (ProcessorType.LITLE.value().equals(pgTransaction.getProcessor())) {
          // Setting Initial Litle EFT status
          pgTransaction.setEftStatus(PGConstants.LITLE_PENDING);
        }
        PGAccount pgAccount = logAccountTransaction(pgTransaction,purchaseRequest);
        logPgAccountFee(pgTransaction, pgAccount);// Logging transaction fee details
        String descriptionTemplate =
            Properties.getProperty("chatak-pay.pending.description.template");
        descriptionTemplate =
            MessageFormat.format(descriptionTemplate, pgTransaction.getTransactionId());
        pgTransaction.setTxnDescription(descriptionTemplate);
        purchaseResponse.setAuthId(pgTransaction.getAuthId());
      } else {
        if (purchaseResponse.getErrorCode().equals(PGConstants.FORMAT_ERROR)) {
          purchaseRequest
              .setReversalReason(Properties.getProperty("chatak-pay.pulse.format.error"));
          autoReversal(populateReversalRequest(purchaseRequest, purchaseResponse));// Reversing
          // transaction when it receives format error from upstream processor
        }
        pgTransaction.setTxnDescription(purchaseResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
      }
	logger.info("Exiting :: SwitchServiceBroker :: statusValidation");
  }

  /**
   * Method to Adjust to successful Payment Transaction (Tip Adjustment, sale
   * amount adjustment) Steps Involved 1. Validate Request TODO:PAYMENT
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
    logger.info("SwitchServiceBroker | adjustmentTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    AdjustmentResponse adjustmentResponse = new AdjustmentResponse();

    try {

      // validation of Request
      validateRequest(adjustmentRequest);

      PGTransaction saleTransaction =
          transactionDao.getTransaction(adjustmentRequest.getMerchantCode(),
              adjustmentRequest.getTerminalId(), adjustmentRequest.getTxnRefNum());
      if (saleTransaction == null) {
        throw new ServiceException(ActionCode.ERROR_CODE_78);
      }

      PGSwitchTransaction pgSwitchTransaction = null;
      PGTransaction pgTransaction = null;

      validatePGSwitchTransaction(adjustmentRequest, adjustmentResponse, saleTransaction,
          pgSwitchTransaction, pgTransaction);

      // Required to set in reversal
      adjustmentResponse.setTxnRefNum(txnRefNum);

    } catch (ServiceException e) {
      adjustmentResponse.setErrorCode(e.getMessage());
      adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));

      logger.error(
          "SwitchServiceBroker | adjustmentTransaction | ServiceException :" + e.getMessage(), e);
    } catch (DataAccessException e) {
      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      adjustmentResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      logger.error(
          "SwitchServiceBroker | adjustmentTransaction | DataAccessException :" + e.getMessage(), e);
    } catch (Exception e) {
      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      adjustmentResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      logger.error("SwitchServiceBroker | adjustmentTransaction | Exception :" + e.getMessage(), e);
    }

    logger.info("SwitchServiceBroker | adjustmentTransaction | Exiting");
    return adjustmentResponse;
  }

  private void validatePGSwitchTransaction(AdjustmentRequest adjustmentRequest,
      AdjustmentResponse adjustmentResponse, PGTransaction saleTransaction,
      PGSwitchTransaction pgSwitchTransaction, PGTransaction pgTransaction) {
    try {

      // Create Transaction record
      pgTransaction = populatePGTransaction(adjustmentRequest, PGConstants.TXN_TYPE_SALE_ADJ);

      pgTransaction.setRefTransactionId(adjustmentRequest.getTxnRefNum());
      pgTransaction.setSysTraceNum(adjustmentRequest.getSysTraceNum());
      pgTransaction.setAuthId(adjustmentRequest.getAuthId());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(adjustmentRequest);

      // Switch interface call
      SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(),
          Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg =
          switchTransaction.auth(getISOMsg("0200", "021010", adjustmentRequest.getTxnAmount(),
              adjustmentRequest.getCardNum(), adjustmentRequest.getExpDate(), txnRefNum));

      String switchResponseCode = switchISOMsg.getValue(Constants.THIRTYNINE) != null
          ? (String) switchISOMsg.getValue(Constants.THIRTYNINE) : null;

      if (switchResponseCode != null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)) {
        // Switch transaction id
        String issuerTxnRefNumber = switchISOMsg.getValue(Constants.THIRTYSEVEN) != null
            ? (String) switchISOMsg.getValue(Constants.THIRTYSEVEN) : null;

        pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);

        pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
        pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
      } else {
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      }
      pgSwitchTransaction.setTransactionId(switchISOMsg.getString(Constants.THIRTYSEVEN) != null
          ? switchISOMsg.getString(Constants.THIRTYSEVEN) : null);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Update transaction status and switch response
      voidTransactionDao.createTransaction(pgTransaction);

      // Set Response attributes
      adjustmentResponse.setTxnRefNum(pgTransaction.getTransactionId());
      adjustmentResponse.setAuthId(pgTransaction.getAuthId());
      adjustmentResponse.setTxnAmount(adjustmentRequest.getTxnAmount());
      adjustmentResponse.setAdjAmount(adjustmentRequest.getAdjAmount());
      adjustmentResponse.setFeeAmount(saleTransaction.getFeeAmount());
      adjustmentResponse
          .setTotalAmount(adjustmentRequest.getTxnAmount() + saleTransaction.getFeeAmount());
      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_00);
      adjustmentResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_00));

    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        voidTransactionDao.createTransaction(pgTransaction);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      }

      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      adjustmentResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }
  }

  /**
   * Method to Void/Cancel an successfull sale/refund transaction
   * 
   * @param voidRequest
   * @return VoidResponse
   * @throws ServiceException
   */
  public VoidResponse voidTransaction(VoidRequest voidRequest) throws ServiceException {
    logger.info("SwitchServiceBroker | voidTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    VoidResponse voidResponse = new VoidResponse();
    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    try {

      // validation of Request
      validateRequest(voidRequest);

      // Create Transaction record
      pgTransaction = populatePGTransaction(voidRequest, PGConstants.TXN_TYPE_VOID);
      pgTransaction.setRefTransactionId(voidRequest.getTxnRefNumber());
      pgTransaction.setSysTraceNum(voidRequest.getSysTraceNum());
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_CREDIT);
      pgTransaction.setTxnAmount(voidRequest.getTxnAmount());
      pgTransaction.setAdjAmount(Long.valueOf(PGConstants.VOID_TXN_AMOUNT));
      pgTransaction.setTxnTotalAmount(voidRequest.getTotalTxnAmount());
      pgTransaction.setFeeAmount(voidRequest.getTxnFee());
      pgTransaction.setUserName(voidRequest.getUserName());
      Date date = new Date();
      String batchId = new SimpleDateFormat(Constants.BATCH_ID_DATE_FORMAT).format(date);
      pgTransaction.setBatchId(batchId + pgTransaction.getMerchantId());
      pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(voidRequest);
      paymentService = binUpstreamRouter.getPaymentService();
      logger.info("SwitchServiceBroker::VOID Transaction Request: for SALE txn id "
          + voidRequest.getIssuerTxnRefNum());

      /********* Sending to upstream processor *********/

      voidResponse = paymentService.voidTransaction(voidRequest);

      logger.info("SwitchServiceBroker::VOID Transaction Response: " + voidResponse.getErrorCode()
          + "::" + voidResponse.getErrorMessage());
      pgTransaction.setIssuerTxnRefNum(voidResponse.getUpStreamTxnRefNum());
      pgTransaction.setStatus(voidResponse.getUpStreamStatus());
      pgTransaction.setProcessor(paymentService.getProcessor());
      pgTransaction.setRefTransactionId(voidRequest.getTxnRefNum());

      pgSwitchTransaction.setProcessorResponse(voidResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorMessage(voidResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponseMsg(voidResponse.getUpStreamMessage());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());
      pgSwitchTransaction.setProcessorAuthCode(voidResponse.getUpStreamAuthCode());

      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
        statusVoidValidation(voidRequest, voidResponse, pgTransaction);
      } else {
        pgTransaction.setTxnDescription(voidResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
      }

      voidTransactionDao.createTransaction(pgTransaction);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Set Response attributes
      voidResponse.setTxnRefNum(pgTransaction.getTransactionId());
      voidResponse.setTxnAmount(Double.valueOf(pgTransaction.getTxnAmount()));
      voidResponse.setFeeAmount(Double.valueOf(PGConstants.VOID_TXN_AMOUNT));
      voidResponse.setTxnRefNum(pgTransaction.getTransactionId());
      voidResponse.setAuthId(pgTransaction.getAuthId());
      voidResponse.setErrorMessage(voidResponse.getUpStreamMessage());

    } catch (DataAccessException e) {
        voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
        voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
        if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
          validatePGTransaction(pgSwitchTransaction, pgTransaction);
        }
        logger.error("SwitchServiceBroker | voidTransaction | DataAccessException :", e);
      } catch (ServiceException e) {
      
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
        pgTransaction
            .setTxnDescription(ActionCode.getInstance().getMessage(voidResponse.getErrorCode()));
        voidTransactionDao.createTransaction(pgTransaction);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      }

      voidResponse.setErrorCode(e.getMessage());
      voidResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(voidResponse.getErrorCode()));
      logger.error("ServiceException :" + e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }

      voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | voidTransaction | Exiting");
    return voidResponse;

  }

  private void statusVoidValidation(VoidRequest voidRequest, VoidResponse voidResponse, PGTransaction pgTransaction)
		throws Exception, ServiceException {
	PGTransaction originalSaleTransaction;
	voidResponse.setAuthId(voidRequest.getAuthId());
	originalSaleTransaction = voidTransactionDao
	    .findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
	        voidRequest.getTxnRefNum(), voidRequest.getIssuerTxnRefNum(),
	        pgTransaction.getMerchantId(), pgTransaction.getTerminalId());
	if ((originalSaleTransaction.getMerchantSettlementStatus()
	    .equals(PGConstants.PG_SETTLEMENT_PENDING))
	    || (originalSaleTransaction.getMerchantSettlementStatus()
	        .equals(PGConstants.PG_SETTLEMENT_PROCESSING))) {
		PGAccount pgAccount = logAccountTransaction(pgTransaction, voidRequest);
	  originalSaleTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_VOIDED);
	  pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
	  logPgAccountFee(pgTransaction, pgAccount);
	  String descriptionTemplate =
	      Properties.getProperty("chatak-pay.reverse.description.template");
	  descriptionTemplate =
	      MessageFormat.format(descriptionTemplate, originalSaleTransaction.getTransactionId());

	  pgTransaction.setTxnDescription(descriptionTemplate);
	  originalSaleTransaction.setTxnDescription(descriptionTemplate);
	  voidTransactionDao.createTransaction(originalSaleTransaction);
	}

	else if (originalSaleTransaction.getMerchantSettlementStatus()
	    .equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {

	  updateMerchantAccountDetails(pgTransaction, originalSaleTransaction);
	}
  }

private void updateMerchantAccountDetails(PGTransaction pgTransaction, PGTransaction originalSaleTransaction)
		throws ServiceException {
	String nbFlag = updateMerchantSettledAccount(originalSaleTransaction);// nbFlag is for negative balance
	  originalSaleTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_VOIDED);// updating sale txn status based on void
	  String descriptionTemplate =
	      Properties.getProperty("chatak-pay.void.description.template");
	  descriptionTemplate = MessageFormat.format(descriptionTemplate,
	      originalSaleTransaction.getTransactionId(), pgTransaction.getIssuerTxnRefNum());

	  pgTransaction.setTxnDescription(descriptionTemplate + nbFlag);// settingdescription
	  pgTransaction.setMerchantFeeAmount(
	      StringUtils.getValidLongValue(originalSaleTransaction.getMerchantFeeAmount()));
	  pgTransaction.setMerchantSettlementStatus(PGConstants.TXN_TYPE_VOID);
	  voidTransactionDao.createTransaction(originalSaleTransaction);
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
    logger.info("SwitchServiceBroker | reversalTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    ReversalResponse reversalResponse = new ReversalResponse();
    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    try {

      // validation of Request
      validateRequest(reversalRequest);

      // Create Transaction record
      pgTransaction = populatePGTransaction(reversalRequest, PGConstants.TXN_TYPE_REVERSAL);
      pgTransaction.setRefTransactionId(reversalRequest.getTxnRefNumber());
      pgTransaction.setSysTraceNum(reversalRequest.getSysTraceNum());
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_CREDIT);
      pgTransaction.setTxnAmount(reversalRequest.getTxnAmount());
      pgTransaction.setFeeAmount(reversalRequest.getTxnFee());
      pgTransaction.setAdjAmount(Long.valueOf(PGConstants.VOID_TXN_AMOUNT));
      pgTransaction.setTxnTotalAmount(reversalRequest.getTotalTxnAmount());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(reversalRequest);
      paymentService = binUpstreamRouter.getPaymentService();
      logger.info("SwitchServiceBroker::REVERSAL Transaction REQUEST for : "
          + reversalRequest.getIssuerTxnRefNum());
      reversalResponse = paymentService.reversalTransaction(reversalRequest);
      logger.info("SwitchServiceBroker::REVERSAL Transaction Response: "
          + reversalResponse.getErrorCode() + "::" + reversalResponse.getErrorMessage());
      pgTransaction.setIssuerTxnRefNum(reversalResponse.getUpStreamTxnRefNum());
      pgTransaction.setStatus(reversalResponse.getUpStreamStatus());
      pgTransaction.setProcessor(paymentService.getProcessor());
      pgTransaction.setRefTransactionId(reversalRequest.getTxnRefNum());

      pgSwitchTransaction.setProcessorMessage(reversalResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponseMsg(reversalResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(reversalResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorAuthCode(reversalResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
        validateStatus(reversalRequest, pgTransaction);
      }

      voidTransactionDao.createTransaction(pgTransaction);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Set Response attributes
      reversalResponse.setTxnRefNum(pgTransaction.getTransactionId());
      reversalResponse.setAuthId(reversalRequest.getAuthId());
      reversalResponse.setTxnRefNum(pgTransaction.getTransactionId());
      reversalResponse.setAuthId(pgTransaction.getAuthId());
      reversalResponse.setErrorMessage(reversalResponse.getUpStreamMessage());

    } catch (DataAccessException e) {
        reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
        reversalResponse
            .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
        if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
          voidTransactionDao.createTransaction(pgTransaction);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          switchTransactionDao.createTransaction(pgSwitchTransaction);
        }
        logger.error("SwitchServiceBroker | voidTransaction | DataAccessException :", e);
      }  catch (ServiceException e) {

      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        voidTransactionDao.createTransaction(pgTransaction);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      }

      reversalResponse.setErrorCode(e.getMessage());
      reversalResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(reversalResponse.getErrorCode()));
      logger.error("ServiceException :" + e);
    }catch (Exception e) {
      
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
    	  creatTransaction(pgSwitchTransaction, pgTransaction);
      }

      reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      reversalResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      logger.error(Constants.EXCEPTIONS + e);
    }

    logger.info("SwitchServiceBroker | reversalTransaction | Exiting");
    return reversalResponse;
  }
  
	private void creatTransaction(PGSwitchTransaction pgSwitchTransaction, PGTransaction pgTransaction) {
		if (pgTransaction != null) {
			pgTransaction.setStatus(PGConstants.STATUS_FAILED);
			pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
			voidTransactionDao.createTransaction(pgTransaction);
		}
		if (pgSwitchTransaction != null) {
			pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
			switchTransactionDao.createTransaction(pgSwitchTransaction);
		}
	}

  private void validateStatus(ReversalRequest reversalRequest, PGTransaction pgTransaction)
		throws ServiceException, Exception {
	PGTransaction originalSaleTransaction;
	originalSaleTransaction = voidTransactionDao
	    .findTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
	        reversalRequest.getTxnRefNum(), reversalRequest.getIssuerTxnRefNum(),
	        pgTransaction.getMerchantId(), pgTransaction.getTerminalId());
	if ((originalSaleTransaction.getMerchantSettlementStatus()
	    .equals(PGConstants.PG_SETTLEMENT_PENDING))
	    || (originalSaleTransaction.getMerchantSettlementStatus()
	        .equals(PGConstants.PG_SETTLEMENT_PROCESSING))) {
	  updateMerchantAccount(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_CREDIT,
	      originalSaleTransaction.getTxnAmount(),
	      pgTransaction.getFeeAmount() == null ? 0 : pgTransaction.getFeeAmount(),
	      pgTransaction.getTransactionId());
	  originalSaleTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_VOIDED);
	  pgTransaction.setMerchantSettlementStatus(PGConstants.TXN_TYPE_VOID);
	  logPgAccountFee(pgTransaction, null);
	  voidTransactionDao.createTransaction(originalSaleTransaction);
	}

	else if (originalSaleTransaction.getMerchantSettlementStatus()
	    .equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {

	  updateMerchantAccountDetails(pgTransaction, originalSaleTransaction);
	  logPgAccountHistory(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_DEBIT,
	      pgTransaction.getTransactionId(), null);
	}
  }

  /**
   * Method to Refund a transaction
   * 
   * @param reversalRequest
   * @return ReversalResponse
   * @throws ServiceException
   */
  public RefundResponse refundTransaction(RefundRequest refundRequest) throws ServiceException {
    logger.info("SwitchServiceBroker | refundTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    RefundResponse refundResponse = new RefundResponse();

    PGTransaction orignalSaleTxn = refundTransactionDao
        .findTransactionToRefundByPGTxnIdAndIssuerTxnIdAndMerchantId(refundRequest.getTxnRefNum(),
            refundRequest.getIssuerTxnRefNum(), refundRequest.getMerchantCode());

    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    try {

      // validation of Request
      validateRequest(refundRequest);
      // Create Transaction record
      pgTransaction = populatePGTransaction(refundRequest, PGConstants.TXN_TYPE_REFUND);
      pgTransaction.setRefTransactionId(refundRequest.getTxnRefNumber());
      pgTransaction.setSysTraceNum(refundRequest.getSysTraceNum());
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_CREDIT);
      pgTransaction.setTxnAmount(refundRequest.getTxnAmount());
      pgTransaction.setFeeAmount(refundRequest.getTxnFee());
      pgTransaction.setAdjAmount(Long.valueOf(PGConstants.VOID_TXN_AMOUNT));
      pgTransaction.setTxnTotalAmount(refundRequest.getTotalTxnAmount());
      pgTransaction.setUserName(refundRequest.getUserName());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(refundRequest);
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      paymentService = binUpstreamRouter.getPaymentService();
      logger.info("SwitchServiceBroker::REFUND Transaction REQUEST for : "
          + refundRequest.getIssuerTxnRefNum());

      /********* Sending to upstream processor *********/

      refundResponse = paymentService.refundTransaction(refundRequest);

      logger.info("SwitchServiceBroker::REFUND Transaction Response: "
          + refundResponse.getErrorCode() + "::" + refundResponse.getErrorMessage());
      pgTransaction.setStatus(refundResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(refundResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());
      pgTransaction.setRefTransactionId(refundRequest.getTxnRefNum());
      
      pgTransaction.setIssuancePartner(refundResponse.getIssuancePartner());
      pgTransaction.setDeviceLocalTxnTime(refundResponse.getIssuanceTxnTime());
      
      Date date = new Date();
      String batchId = new SimpleDateFormat(Constants.BATCH_ID_DATE_FORMAT).format(date);
      pgTransaction.setBatchId(batchId + pgTransaction.getMerchantId());
      pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));

      pgSwitchTransaction.setProcessorMessage(refundResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponse(refundResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponseMsg(refundResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorAuthCode(refundResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      // Update transaction status and switch response
      voidTransactionDao.createTransaction(pgTransaction);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
        validateSuccessStatus(refundRequest, refundResponse, orignalSaleTxn, pgTransaction);
      } else {
        pgTransaction.setTxnDescription(refundResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      }
      voidTransactionDao.createTransaction(pgTransaction);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Set Response attributes
      refundResponse.setTxnRefNum(pgTransaction.getTransactionId());
      refundResponse.setTotalTxnAmount(pgTransaction.getTxnTotalAmount());
      refundResponse.setTxnAmount(Long.valueOf(pgTransaction.getTxnAmount()));
      refundResponse.setErrorMessage(refundResponse.getUpStreamMessage());

    } catch (DataAccessException e) {
      refundRequest.setReversalReason(e.getMessage());
      autoReversal(populateReversalRequest(refundRequest, refundResponse));// Reversaing
      // transaction
      refundResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      refundResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }
      logger.error(
          "SwitchServiceBroker | refundTransaction | DataAccessException :" + e.getMessage(), e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
    	  creatTransaction(pgSwitchTransaction, pgTransaction);
      }
      refundResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      refundResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    // Required to set in reversal

    logger.info("SwitchServiceBroker | refundTransaction | Exiting");
    return refundResponse;

  }

  private void validateSuccessStatus(RefundRequest refundRequest, RefundResponse refundResponse,
		PGTransaction orignalSaleTxn, PGTransaction pgTransaction) throws Exception, ServiceException {
	orignalSaleTxn.setRefundStatus(
	    null != refundRequest.getRefundStatus() ? refundRequest.getRefundStatus() : 0);
	pgTransaction.setRefundStatus(orignalSaleTxn.getRefundStatus());
	if (refundRequest.isSaleDependentRefund()) {
	  
	  if (orignalSaleTxn.getMerchantSettlementStatus().equals(PGConstants.PG_SETTLEMENT_PENDING)
	      || orignalSaleTxn.getMerchantSettlementStatus()
	          .equals(PGConstants.PG_SETTLEMENT_PROCESSING)) {
	    validateMerchantSettlementProcessing(orignalSaleTxn, pgTransaction);
	  } else if (orignalSaleTxn.getMerchantSettlementStatus()
	      .equals(PGConstants.PG_SETTLEMENT_EXECUTED)
	      || orignalSaleTxn.getMerchantSettlementStatus().equals(PGConstants.PG_TXN_REFUNDED)) {
	    validateMerchantSettlementExecuted(orignalSaleTxn, pgTransaction,refundRequest);
	  }

	  voidTransactionDao.createTransaction(orignalSaleTxn);
	  voidTransactionDao.createTransaction(pgTransaction);
	  refundResponse.setAuthId(pgTransaction.getAuthId());
	} else {
		PGAccount pgAccount = updateMerchantAccount(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_CREDIT,
	      pgTransaction.getTxnAmount(),
	      pgTransaction.getFeeAmount() == null ? 0 : pgTransaction.getFeeAmount(),
	      pgTransaction.getTransactionId());
	  String descriptionTemplate =
	      Properties.getProperty("chatak-pay.refund.description.template");
	  descriptionTemplate =
	      MessageFormat.format(descriptionTemplate, pgTransaction.getRefTransactionId());
	  pgTransaction.setTxnDescription(descriptionTemplate);
	  pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
	  logPgAccountFee(pgTransaction, pgAccount);// Logging transaction fee details
	}
  }

  private void validateMerchantSettlementProcessing(PGTransaction orignalSaleTxn, PGTransaction pgTransaction)
		throws Exception, ServiceException {   
	if (PGConstants.PG_SETTLEMENT_PROCESSING
	    .equals(orignalSaleTxn.getMerchantSettlementStatus())) {
	  updateAccountCCTransactions(orignalSaleTxn.getTransactionId(),
	      orignalSaleTxn.getTransactionType());
	}
	logPartialRefundToAccountTransaction(pgTransaction);
	String descriptionTemplate =
	    Properties.getProperty("chatak-pay.refund.description.template");
	descriptionTemplate =
	    MessageFormat.format(descriptionTemplate, pgTransaction.getRefTransactionId());
	pgTransaction.setTxnDescription(descriptionTemplate);
	pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
	orignalSaleTxn.setMerchantSettlementStatus(PGConstants.PG_TXN_REFUNDED);
	orignalSaleTxn.setTxnDescription(descriptionTemplate);
	PGAccount pgAccount = null;
	logPgAccountFee(pgTransaction, pgAccount);// Logging transaction fee details
	logPgAccountHistory(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_DEBIT,
	    pgTransaction.getTransactionId(), pgAccount);
  }

  private void validateMerchantSettlementExecuted(PGTransaction orignalSaleTxn, PGTransaction pgTransaction,RefundRequest refundRequest)
		throws Exception, ServiceException {
	logAccountTransaction(pgTransaction, refundRequest);
	String nbFlag = updateMerchantSettledAccount(orignalSaleTxn);
	String descriptionTemplate =
	    Properties.getProperty("chatak-pay.refund.description.template");
	descriptionTemplate =
	    MessageFormat.format(descriptionTemplate, pgTransaction.getRefTransactionId());
	pgTransaction.setTxnDescription(descriptionTemplate);
	pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
	orignalSaleTxn.setMerchantSettlementStatus(PGConstants.PG_TXN_REFUNDED);
	if (ProcessorType.LITLE.value().equals(orignalSaleTxn.getProcessor())) {
	  // Removing Litle executed transaction from dash board EFT queue
	  orignalSaleTxn.setEftStatus(PGConstants.LITLE_REFUNDED);
	}
	orignalSaleTxn.setTxnDescription(descriptionTemplate + nbFlag);
	PGAccount pgAccount = null;
	logPgAccountFee(pgTransaction, pgAccount);// Logging transaction fee details
	// Reversing issuance fee
	postReversalFeeToIssuance(orignalSaleTxn.getMerchantId(),
	    orignalSaleTxn.getTransactionId(), pgTransaction.getTransactionId());
  }

  public VoidResponse voidRefundTransaction(VoidRequest voidRequest) throws ServiceException {
    logger.info("SwitchServiceBroker | voidTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    VoidResponse voidResponse = new VoidResponse();
    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    
    try {
      // validation of Request
      validateRequest(voidRequest);
      // Create Transaction record
      pgTransaction = populatePGTransaction(voidRequest, PGConstants.TXN_TYPE_VOID);
      pgTransaction.setRefTransactionId(voidRequest.getTxnRefNumber());
      pgTransaction.setSysTraceNum(voidRequest.getSysTraceNum());
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
      pgTransaction.setTxnAmount(voidRequest.getTxnAmount());
      pgTransaction.setFeeAmount(voidRequest.getTxnFee());
      pgTransaction.setAdjAmount(Long.valueOf(PGConstants.VOID_TXN_AMOUNT));
      pgTransaction.setTxnTotalAmount(voidRequest.getTotalTxnAmount());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(voidRequest);
      paymentService = binUpstreamRouter.getPaymentService();
      logger.info("SwitchServiceBroker::REFUND-VOID Transaction REQUEST for : "
          + voidRequest.getIssuerTxnRefNum());

      /********* Sending to upstream processor *********/

      voidResponse = paymentService.voidTransaction(voidRequest);

      logger.info("SwitchServiceBroker::REFUND-VOID Transaction Response: "
          + voidResponse.getErrorCode() + "::" + voidResponse.getErrorMessage());

      pgTransaction.setStatus(voidResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(voidResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());
      pgTransaction.setRefTransactionId(voidRequest.getTxnRefNum());

      pgSwitchTransaction.setProcessorMessage(voidResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponse(voidResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponseMsg(voidResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorAuthCode(voidResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      // Update account
      updatePGTxnAccount(voidRequest, voidResponse, pgTransaction);
      voidTransactionDao.createTransaction(pgTransaction);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Set Response attributes
      voidResponse.setTxnRefNum(pgTransaction.getTransactionId());
      voidResponse.setTxnAmount(Double.valueOf(pgTransaction.getTxnAmount()));
      voidResponse.setFeeAmount(Double.valueOf(PGConstants.VOID_TXN_AMOUNT));
      voidResponse.setTxnRefNum(pgTransaction.getTransactionId());
      voidResponse.setErrorMessage(voidResponse.getUpStreamMessage());

    } catch (ServiceException e) {
      logger.error(Constants.EXCEPTIONS + e);

      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
        pgTransaction
            .setTxnDescription(ActionCode.getInstance().getMessage(voidResponse.getErrorCode()));
        voidTransactionDao.createTransaction(pgTransaction);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      }
      voidResponse.setErrorCode(e.getMessage());
      voidResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(voidResponse.getErrorCode()));
    } catch (DataAccessException e) {
      voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }
      logger.error("SwitchServiceBroker | voidTransaction | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }
      voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | voidTransaction | Exiting");
    return voidResponse;

  }

  private void updatePGTxnAccount(VoidRequest voidRequest, VoidResponse voidResponse,
      PGTransaction pgTransaction) throws ServiceException {
    
    PGTransaction originalRefundTransaction;
    if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
      voidResponse.setAuthId(voidRequest.getAuthId());
      originalRefundTransaction = refundTransactionDao
          .findRefundTransactionToVoidByPGTxnIdAndIssuerTxnIdAndMerchantIdAndTerminalId(
              voidRequest.getTxnRefNum(), voidRequest.getIssuerTxnRefNum(),
              pgTransaction.getMerchantId(), pgTransaction.getTerminalId());

      if ((originalRefundTransaction.getMerchantSettlementStatus()
          .equals(PGConstants.PG_SETTLEMENT_PENDING))
          || (originalRefundTransaction.getMerchantSettlementStatus()
              .equals(PGConstants.PG_SETTLEMENT_PROCESSING))) {
        updateMerchantAccount(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_DEBIT,
            originalRefundTransaction.getTxnAmount(),
            pgTransaction.getFeeAmount() == null ? 0 : pgTransaction.getFeeAmount(),
            pgTransaction.getTransactionId());
        originalRefundTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_VOIDED);
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        String descriptionTemplate =
            Properties.getProperty("chatak-pay.reverse.description.template");
        descriptionTemplate = MessageFormat.format(descriptionTemplate,
            originalRefundTransaction.getTransactionId());

        pgTransaction.setTxnDescription(descriptionTemplate);
        voidTransactionDao.createTransaction(originalRefundTransaction);
      }

      else if (originalRefundTransaction.getMerchantSettlementStatus()
          .equals(PGConstants.PG_SETTLEMENT_EXECUTED)) {

        String nbFlag = updateMerchantSettledAccountOnRefund(originalRefundTransaction);
        String descriptionTemplate =
            Properties.getProperty("chatak-pay.reverse.description.template");
        descriptionTemplate =
            MessageFormat.format(descriptionTemplate, pgTransaction.getRefTransactionId());
        pgTransaction.setTxnDescription(descriptionTemplate);
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
        originalRefundTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_VOIDED);
        originalRefundTransaction.setTxnDescription(descriptionTemplate + nbFlag);
        voidTransactionDao.createTransaction(originalRefundTransaction);
        logPgAccountHistory(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_DEBIT,
            pgTransaction.getTransactionId(), null);
      }
      voidResponse.setAuthId(pgTransaction.getAuthId());
    }
    else {
      pgTransaction.setTxnDescription(voidResponse.getErrorMessage());
      pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
    }
  }

  /**
   * <<Method to reverse(sale,auth and refund transaction)if it encounter any
   * incomplete transaction>>
   * 
   * @param reversalRequest
   * @return
   */
  public ReversalResponse autoReversal(ReversalRequest reversalRequest) {

    logger.info("SwitchServiceBroker | autoReversal | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    ReversalResponse reversalResponse = new ReversalResponse();
    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    try {

      // validation of Request
      validateRequest(reversalRequest);

      // Create Transaction record
      pgTransaction = populateReversalPGTransaction(reversalRequest, PGConstants.TXN_TYPE_REVERSAL);
      pgTransaction.setRefTransactionId(reversalRequest.getTxnRefNumber());
      pgTransaction.setSysTraceNum(reversalRequest.getSysTraceNum());
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_CREDIT);
      pgTransaction.setTxnAmount(reversalRequest.getTxnAmount());
      pgTransaction.setFeeAmount(reversalRequest.getTxnFee());
      pgTransaction.setAdjAmount(Long.valueOf(PGConstants.VOID_TXN_AMOUNT));
      pgTransaction.setTxnTotalAmount(reversalRequest.getTotalTxnAmount());
      pgTransaction.setReason(reversalRequest.getReversalReason());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(reversalRequest);
      paymentService = binUpstreamRouter.getPaymentService();
      logger.info("SwitchServiceBroker::REVERSAL Transaction REQUEST for : "
          + reversalRequest.getIssuerTxnRefNum());

      /********* Sending to upstream processor *********/
      reversalResponse = paymentService.reversalTransaction(reversalRequest);

      logger.info("SwitchServiceBroker::REVERSAL Transaction Response: "
          + reversalResponse.getErrorCode() + "::" + reversalResponse.getErrorMessage());

      pgTransaction.setStatus(reversalResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(reversalResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());
      pgTransaction.setRefTransactionId(reversalRequest.getTxnRefNum());

      pgSwitchTransaction.setProcessorMessage(reversalResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponse(reversalResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponseMsg(reversalResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorAuthCode(reversalResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());
      String descriptionTemplate = Properties.getProperty("chatak-pay.void.description.template");
      descriptionTemplate = MessageFormat.format(descriptionTemplate,
          reversalRequest.getIssuerTxnRefNum(), pgTransaction.getIssuerTxnRefNum());

      pgTransaction.setTxnDescription(descriptionTemplate);// setting
      // description

      voidTransactionDao.createTransaction(pgTransaction);
      switchTransactionDao.createTransaction(pgSwitchTransaction);

      // Set Response attributes
      reversalResponse.setTxnRefNum(pgTransaction.getTransactionId());
      reversalResponse.setAuthId(reversalRequest.getAuthId());
      reversalResponse.setFeeAmount(Double.valueOf(PGConstants.VOID_TXN_AMOUNT));
      reversalResponse.setTxnRefNum(pgTransaction.getTransactionId());
      reversalResponse.setAuthId(pgTransaction.getAuthId());
      reversalResponse.setErrorMessage(reversalResponse.getUpStreamMessage());

    } catch (ServiceException e) {
      logger.error(Constants.EXCEPTIONS + e);

      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        voidTransactionDao.createTransaction(pgTransaction);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      }
      reversalResponse.setErrorCode(e.getMessage());
      reversalResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(reversalResponse.getErrorCode()));
    } catch (DataAccessException e) {
      reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      reversalResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        voidTransactionDao.createTransaction(pgTransaction);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      }
      logger.error("SwitchServiceBroker | autoReversal | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
    	  creatTransaction(pgSwitchTransaction, pgTransaction);
      }
      reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      reversalResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | autoReversal | Exiting");
    return reversalResponse;

  }

  public BalanceEnquiryResponse balanceEnquiry(Request balanceEnquiryRequest)
      throws ServiceException {
    logger.info("SwitchServiceBroker | balanceEnquiry | Entering");
    BalanceEnquiryResponse balanceEnquiryResponse = new BalanceEnquiryResponse();
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());

    PGSwitchTransaction pgSwitchTransaction = new PGSwitchTransaction();
    PGTransaction pgTransaction = new PGTransaction();

    try {
      // validation of Request
      validateRequest(balanceEnquiryRequest);

      // Create Transaction record
      pgTransaction =
          populatePGTransaction(balanceEnquiryRequest, Constants.BALANCE_ENQUIRY);
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);

      pgTransaction.setTxnAmount(0l);
      pgTransaction.setAdjAmount(0l);
      pgTransaction.setFeeAmount(0l);
      pgTransaction.setTxnTotalAmount(0l);
      
      String descriptionTemplate =
          Properties.getProperty("chatak-pay.balance.success.description.template");
      descriptionTemplate =
          MessageFormat.format(descriptionTemplate, StringUtils.lastFourDigits(balanceEnquiryRequest.getCardNum()));
      pgTransaction.setTxnDescription(descriptionTemplate);
      pgTransaction.setUserName(balanceEnquiryRequest.getUserName());
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(balanceEnquiryRequest);
      pgSwitchTransaction.setProcessorResponsePostDate(new Timestamp(System.currentTimeMillis()));

      paymentService = binUpstreamRouter.getPaymentService();
      // Calling the Upstream processor service

      /********* Sending to upstream processor *********/
      balanceEnquiryResponse = paymentService.balanceEnquiryTransaction(balanceEnquiryRequest);

      logger.info("SwitchServiceBroker::BALANCE ENQUIRY Transaction Response: "
          + balanceEnquiryResponse.getErrorCode() + "::"
          + balanceEnquiryResponse.getErrorMessage());
      pgSwitchTransaction
          .setProcessorResponseTime(new Timestamp(balanceEnquiryResponse.getTxnResponseTime()));

      // Update transaction status and switch response
      pgTransaction.setStatus(balanceEnquiryResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(balanceEnquiryResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());

      pgSwitchTransaction.setProcessorAuthCode(balanceEnquiryResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setProcessorMessage(balanceEnquiryResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(balanceEnquiryResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setProcessorResponseMsg(balanceEnquiryResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(balanceEnquiryResponse.getUpStreamResponse());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
        balanceEnquiryResponse.setAuthId(pgTransaction.getAuthId());
      } else {
        pgTransaction.setTxnDescription(balanceEnquiryResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
      }
      if(balanceEnquiryResponse.getErrorCode().equals(PGConstants.SUCCESS)) {
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
      } else {
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
      }
      pgTransaction.setCardHolderName(balanceEnquiryRequest.getCardHolderName());
      pgTransaction.setInvoiceNumber(balanceEnquiryRequest.getInvoiceNumber());
      
      Date date = new Date();
      String batchId = new SimpleDateFormat(Constants.BATCH_ID_DATE_FORMAT).format(date);
      pgTransaction.setBatchId(batchId + pgTransaction.getMerchantId());
      pgTransaction.setBatchDate(new Timestamp(System.currentTimeMillis()));
      
      if(balanceEnquiryResponse.getErrorCode().equals(ActionCode.ERROR_CODE_UID)) {
    	  pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_REJECTED);  
      }
      
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      voidTransactionDao.createTransaction(pgTransaction);
      
      balanceEnquiryResponse.setMerchantId(pgTransaction.getMerchantId());
      balanceEnquiryResponse.setTerminalId(pgTransaction.getTerminalId());
      balanceEnquiryResponse.setTxnId(pgTransaction.getTransactionId());
      balanceEnquiryResponse.setProcTxnId(balanceEnquiryResponse.getUpStreamTxnRefNum());

      // Set Response fields
      balanceEnquiryResponse.setTxnRefNum(pgTransaction.getTransactionId());

    } catch (ServiceException e) {
      logger.error(Constants.EXCEPTIONS + e);

      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
      pgTransaction.setTxnDescription(
          ActionCode.getInstance().getMessage(balanceEnquiryResponse.getErrorCode()));
      voidTransactionDao.createTransaction(pgTransaction);
      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      balanceEnquiryResponse.setErrorCode(e.getMessage());
      balanceEnquiryResponse.setErrorMessage(
          ActionCode.getInstance().getMessage(balanceEnquiryResponse.getErrorCode()));
      logger.error("SwitchServiceBroker | balanceEnquiry | ServiceException :", e);
    } catch (DataAccessException e) {
      balanceEnquiryRequest.setReversalReason(e.getMessage());
      autoReversal(populateReversalRequest(balanceEnquiryRequest, balanceEnquiryResponse));// Reversaing
      // transaction
      balanceEnquiryResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      balanceEnquiryResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      validatePGTransaction(pgSwitchTransaction, pgTransaction);
      logger.error("SwitchServiceBroker | authTransaction | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      pgTransactionValidation(pgSwitchTransaction, pgTransaction);

      balanceEnquiryResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      balanceEnquiryResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | balanceEnquiry | Exiting");
    return balanceEnquiryResponse;
  }

  public CashBackResponse processCashBackTransaction(CashBackRequest cashBackRequest)
      throws ServiceException {
    logger.info("SwitchServiceBroker | purchaseTransaction | Entering");
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());
    CashBackResponse cashBackResponse = new CashBackResponse();

    PGSwitchTransaction pgSwitchTransaction = null;
    PGTransaction pgTransaction = null;
    try {
      // validation of Request
      validateRequest(cashBackRequest);
      // Create Transaction record
      pgTransaction = populatePGTransaction(cashBackRequest, PGConstants.TXN_TYPE_CASH_BACK);
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call

      pgSwitchTransaction = populateSwitchTransactionRequest(cashBackRequest);

      paymentService = binUpstreamRouter.getPaymentService();

      /********* Sending to upstream processor *********/

      cashBackResponse = paymentService.cashBackTransaction(cashBackRequest);

      /********* Sending to upstream processor *********/
      logger.info("SwitchServiceBroker::CASHBACK Transaction Response: "
          + cashBackResponse.getErrorCode() + "::" + cashBackResponse.getErrorMessage());
      pgTransaction.setStatus(cashBackResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(cashBackResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());

      pgSwitchTransaction.setProcessorMessage(cashBackResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponse(cashBackResponse.getUpStreamResponse());
      pgSwitchTransaction.setProcessorResponseMsg(cashBackResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorAuthCode(cashBackResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {

        Long merchantFeeAmount = 0l;
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_PENDING);

        updateMerchantAccount(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_DEBIT,
            pgTransaction.getTxnAmount(), StringUtils.getValidValue(merchantFeeAmount),
            pgTransaction.getTransactionId());
        String descriptionTemplate =
            Properties.getProperty("chatak-pay.pending.description.template");
        descriptionTemplate =
            MessageFormat.format(descriptionTemplate, pgTransaction.getTransactionId());
        pgTransaction.setTxnDescription(descriptionTemplate);
        cashBackResponse.setAuthId(pgTransaction.getAuthId());
      } else {
        setMessage(cashBackRequest, cashBackResponse);
        pgTransaction.setTxnDescription(cashBackResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
      }

      switchTransactionDao.createTransaction(pgSwitchTransaction);
     logger.info("transaction id is " + pgTransaction.getTransactionId());
      // Update transaction status and switch response
      voidTransactionDao.createTransaction(pgTransaction);

      // Set Response fields
      cashBackResponse.setTxnRefNum(pgTransaction.getTransactionId());
      cashBackResponse.setTxnAmount(cashBackRequest.getTxnAmount());
      cashBackResponse.setFeeAmount(pgTransaction.getFeeAmount());
      cashBackResponse.setTotalAmount(pgTransaction.getTxnTotalAmount());
      cashBackResponse.setErrorMessage(cashBackResponse.getUpStreamMessage());
      cashBackResponse.setIssuerTxnRefNum(pgTransaction.getIssuerTxnRefNum());
    } catch (ServiceException e) {
      logger.error(Constants.EXCEPTIONS + e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
        pgTransaction.setTxnDescription(
            ActionCode.getInstance().getMessage(cashBackResponse.getErrorCode()));
        voidTransactionDao.createTransaction(pgTransaction);

        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      }

      cashBackResponse.setErrorCode(e.getMessage());
      cashBackResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(cashBackResponse.getErrorCode()));
      logger.error("SwitchServiceBroker | purchaseTransaction | ServiceException :", e);
    } catch (DataAccessException e) {

      cashBackResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      cashBackResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        cashBackRequest.setReversalReason(e.getMessage());
        autoReversal(populateReversalRequest(cashBackRequest, cashBackResponse));// Reversaing
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }

      logger.error("SwitchServiceBroker | purchaseTransaction | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      if (checkPgTransaction(pgSwitchTransaction, pgTransaction)) {
        validatePGTransaction(pgSwitchTransaction, pgTransaction);
      }

      cashBackResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      cashBackResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    // Required to set in reversal
    logger.info("SwitchServiceBroker | purchaseTransaction | Exiting");
    return cashBackResponse;
  }

  private void setMessage(CashBackRequest cashBackRequest, CashBackResponse cashBackResponse) {
    if (cashBackResponse.getErrorCode().equals(PGConstants.FORMAT_ERROR)) {
      cashBackRequest
          .setReversalReason(Properties.getProperty("chatak-pay.pulse.format.error"));
      autoReversal(populateReversalRequest(cashBackRequest, cashBackResponse));// Reversing
      // transaction when it receives format error from upstream processor
    }
  }

  public CashWithdrawalResponse cashWithdrawalTransaction(
      CashWithdrawalRequest cashWithdrawalRequest) throws ServiceException {
    logger.info("SwitchServiceBroker | authTransaction | Entering");
    CashWithdrawalResponse cashWithdrawalResponse = new CashWithdrawalResponse();
    binUpstreamRouter = new BINUpstreamRouter(binDao.getAllActiveBins());

    PGSwitchTransaction pgSwitchTransaction = new PGSwitchTransaction();
    PGTransaction pgTransaction = new PGTransaction();
    try {
      // validation of Request
      validateRequest(cashWithdrawalRequest);

      // Create Transaction record
      pgTransaction =
          populatePGTransaction(cashWithdrawalRequest, PGConstants.TXN_TYPE_CASH_WITHDRAWAL);
      pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);

      voidTransactionDao.createTransaction(pgTransaction);

      // Switch transaction log before Switch call
      pgSwitchTransaction = populateSwitchTransactionRequest(cashWithdrawalRequest);
      pgSwitchTransaction.setProcessorResponsePostDate(new Timestamp(System.currentTimeMillis()));

      paymentService = binUpstreamRouter.getPaymentService();
      /********* Sending to upstream processor *********/
      // Calling the Upstream processor service
      cashWithdrawalResponse = paymentService.cashWithdrawalTransaction(cashWithdrawalRequest);

      logger.info("SwitchServiceBroker::CASHWITHDRAWL Transaction Response: "
          + cashWithdrawalResponse.getErrorCode() + "::"
          + cashWithdrawalResponse.getErrorMessage());
      pgSwitchTransaction
          .setProcessorResponseTime(new Timestamp(cashWithdrawalResponse.getTxnResponseTime()));

      // Update transaction status and switch response
      pgTransaction.setStatus(cashWithdrawalResponse.getUpStreamStatus());
      pgTransaction.setIssuerTxnRefNum(cashWithdrawalResponse.getUpStreamTxnRefNum());
      pgTransaction.setProcessor(paymentService.getProcessor());

      pgSwitchTransaction.setProcessorAuthCode(cashWithdrawalResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setProcessorMessage(cashWithdrawalResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(cashWithdrawalResponse.getUpStreamAuthCode());
      pgSwitchTransaction.setProcessorResponseMsg(cashWithdrawalResponse.getUpStreamMessage());
      pgSwitchTransaction.setProcessorResponse(cashWithdrawalResponse.getUpStreamResponse());
      pgSwitchTransaction.setStatus(pgTransaction.getStatus());

      // Update account
      if (pgTransaction.getStatus().equals(PGConstants.STATUS_SUCCESS)) {
        updateMerchantAccount(pgTransaction.getMerchantId(), PGConstants.PAYMENT_METHOD_DEBIT,
            pgTransaction.getTxnAmount(),
            pgTransaction.getFeeAmount() == null ? 0 : pgTransaction.getFeeAmount(),
            pgTransaction.getTransactionId());
        pgTransaction.setTxnDescription(PGConstants.TXN_TYPE_CASH_WITHDRAWAL.toUpperCase());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_SETTLEMENT_PENDING);
        cashWithdrawalResponse.setAuthId(pgTransaction.getAuthId());
      } else {
        pgTransaction.setTxnDescription(cashWithdrawalResponse.getErrorMessage());
        pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_DECLILNED);
      }
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      voidTransactionDao.createTransaction(pgTransaction);

      // Set Response fields
      cashWithdrawalResponse.setTxnRefNum(pgTransaction.getTransactionId());
      cashWithdrawalResponse.setTxnAmount(cashWithdrawalRequest.getTxnAmount());
      cashWithdrawalResponse.setFeeAmount(pgTransaction.getFeeAmount());
      cashWithdrawalResponse.setTotalAmount(pgTransaction.getTxnTotalAmount());
      cashWithdrawalResponse.setErrorMessage(cashWithdrawalResponse.getUpStreamMessage());

    } catch (ServiceException e) {
      logger.error(Constants.EXCEPTIONS + e);

      pgTransaction.setStatus(PGConstants.STATUS_FAILED);
      pgTransaction.setMerchantSettlementStatus(PGConstants.PG_TXN_FAILED);
      pgTransaction.setTxnDescription(
          ActionCode.getInstance().getMessage(cashWithdrawalResponse.getErrorCode()));
      voidTransactionDao.createTransaction(pgTransaction);
      pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
      switchTransactionDao.createTransaction(pgSwitchTransaction);
      cashWithdrawalResponse.setErrorCode(e.getMessage());
      cashWithdrawalResponse.setErrorMessage(
          ActionCode.getInstance().getMessage(cashWithdrawalResponse.getErrorCode()));
      logger.error("SwitchServiceBroker | authTransaction | ServiceException :", e);
    } catch (DataAccessException e) {
      cashWithdrawalRequest.setReversalReason(e.getMessage());
      autoReversal(populateReversalRequest(cashWithdrawalRequest, cashWithdrawalResponse));// Reversaing
      // transaction
      cashWithdrawalResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      cashWithdrawalResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
      validatePGTransaction(pgSwitchTransaction, pgTransaction);
      logger.error("SwitchServiceBroker | authTransaction | DataAccessException :", e);
    } catch (Exception e) {
      logger.error(Constants.EXCEPTIONS + e);
      pgTransactionValidation(pgSwitchTransaction, pgTransaction);

      cashWithdrawalResponse.setErrorCode(ActionCode.ERROR_CODE_Z12);
      cashWithdrawalResponse
          .setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z12));
    }

    logger.info("SwitchServiceBroker | authTransaction | Exiting");
    return cashWithdrawalResponse;
  }


  public void logPgAccountFee(PGTransaction pgTransaction, PGAccount pgAccount) throws Exception {
    logger.info("Entering :: SwitchServiceBroker :: logPgAccountFee");

    Long merchantFeeAmount = 0l;
    Long chatakFeeValue = null;
    boolean iterationFlag = true;
    PGAccountFeeLog pgAccountFeeLog;
    List<Object> objectResult =
        getProcessingFee(PGUtils.getCCType(), 1L,
            pgTransaction.getMerchantId(), pgTransaction.getTxnTotalAmount());
    @SuppressWarnings("unchecked")
    List<ProcessingFee> list = (List<ProcessingFee>) objectResult.get(0);
    Long chatakFeeAmountTotal = (Long) objectResult.get(1);
    Long totalFeeAmount = pgTransaction.getTxnTotalAmount() - pgTransaction.getTxnAmount();
    Long subMerchantAmount = pgTransaction.getTxnAmount();

    if (totalFeeAmount > chatakFeeAmountTotal) {
      merchantFeeAmount = totalFeeAmount - chatakFeeAmountTotal;
    } else {
      chatakFeeAmountTotal = totalFeeAmount;
    }
    if(null == pgAccount) {
    	pgAccount = accountDao.getPgAccount(pgTransaction.getMerchantId());
    }
    String parentEntityId = merchantDao.getParentMerchantCode(pgTransaction.getMerchantId());
    for (ProcessingFee chatakFee : list) {
      chatakFeeValue = CommonUtil.getLongAmount(chatakFee.getChatakProcessingFee());
      pgAccountFeeLog = new PGAccountFeeLog();
      pgAccountFeeLog.setAccountDesc(pgAccount.getAccountDesc());
      pgAccountFeeLog.setAccountNum(pgAccount.getAccountNum());
      pgAccountFeeLog.setCategory(pgAccount.getCategory());
      pgAccountFeeLog.setCurrency(pgAccount.getCurrency());
      pgAccountFeeLog.setEntityId(pgAccount.getEntityId());
      pgAccountFeeLog.setEntityType(pgAccount.getEntityType());
      pgAccountFeeLog
          .setParentEntityId(parentEntityId);
      pgAccountFeeLog.setMerchantFee(iterationFlag ? merchantFeeAmount : 0);
      pgAccountFeeLog.setChatakFee(
          chatakFeeAmountTotal > chatakFeeValue ? chatakFeeValue : chatakFeeAmountTotal);
      pgAccountFeeLog.setTxnAmount(iterationFlag ? subMerchantAmount : 0);
      pgAccountFeeLog.setStatus(pgTransaction.getMerchantSettlementStatus());
      pgAccountFeeLog.setPaymentMethod(pgTransaction.getPaymentMethod());
      pgAccountFeeLog.setTransactionId(pgTransaction.getTransactionId());
      pgAccountFeeLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      pgAccountFeeLog.setSpecificAccNumber(chatakFee.getAccountNumber());
      accountFeeLogDao.createOrSave(pgAccountFeeLog);
      iterationFlag = false;
      chatakFeeAmountTotal = (chatakFeeAmountTotal - chatakFeeValue) > 0
          ? (chatakFeeAmountTotal - chatakFeeValue) : 0l;

    }
    logger.info("Exiting :: SwitchServiceBroker :: logPgAccountFee");
  }

  /**
   * @param pgTransactionId
   * @param txnType
   */
  private void updateAccountCCTransactions(String pgTransactionId, String txnType) {
    PGAccount account = null;
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    logger.info("Entering:: SwitchServiceBroker:: updateAccountCCTransactions method ");
    List<PGAccountTransactions> accountTxns = accountTransactionsDao
        .getAccountTransactionsOnTransactionIdAndTransactionType(pgTransactionId, txnType);
    for (PGAccountTransactions accTxn : accountTxns) {
      switch (accTxn.getTransactionCode()) {
        case AccountTransactionCode.CC_AMOUNT_CREDIT:
          // updating pg account debting refund amount
          account = accountDao.getPgAccount(accTxn.getMerchantCode());
          setPGAccountDetails(currentTime, accTxn, account);
          break;
        case AccountTransactionCode.CC_FEE_DEBIT:
          account = accountDao.getPgAccount(accTxn.getMerchantCode());
          account.setCurrentBalance(account.getCurrentBalance() - accTxn.getDebit());
          account.setAvailableBalance(account.getAvailableBalance() - accTxn.getDebit());
          setPGAccDetails(currentTime, accTxn, account);
          break;
        case AccountTransactionCode.CC_MERCHANT_FEE_CREDIT:
          account = accountDao.getPgAccount(merchantDao.getParentMerchantCode(accTxn.getMerchantCode()));
          if (null == account) {
            account = accountDao.getPgAccount(accTxn.getMerchantCode()); }
          setPGAccountDetails(currentTime, accTxn, account);
          break;
        case AccountTransactionCode.CC_ACQUIRER_FEE_CREDIT:

          logger.info(
              "SwitchServiceBroker:: updateAccountCCTransactions method fetching transactions by PG TRANS ID: "
                  + accTxn.getPgTransactionId());
          fetchListOfPGTransaction(currentTime, accTxn);
          break;
        default:
      }
      accountTransactionsDao.createOrUpdate(accTxn);
    }
  }

  private void fetchListOfPGTransaction(Timestamp currentTime, PGAccountTransactions accTxn) {
    PGAccount account;
    PGTransaction transaction = transactionRepository.findByTransactionId(accTxn.getPgTransactionId());

    PGCurrencyConfig currencyConfig =
        currencyConfigDao.getcurrencyCodeAlpha(transaction.getTxnCurrencyCode());
    logger.info(
        "SwitchServiceBroker:: updateAccountCCTransactions method :: currency code alpha for the above: "
            + currencyConfig.getCurrencyCodeAlpha());
    account =
        accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE,
            currencyConfig.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);

    setPGAccountDetails(currentTime, accTxn, account);
  }

  private void setPGAccountDetails(Timestamp currentTime, PGAccountTransactions accTxn,
      PGAccount account) {
    account.setAvailableBalance(account.getAvailableBalance() + accTxn.getCredit());
    account.setCurrentBalance(account.getCurrentBalance() + accTxn.getCredit());
    setPGAccDetails(currentTime, accTxn, account);
  }

  private void setPGAccDetails(Timestamp currentTime, PGAccountTransactions accTxn,
      PGAccount account) {
    accountDao.savePGAccount(account);
    accTxn.setProcessedTime(currentTime);
    accTxn.setCurrentBalance(account.getCurrentBalance());
    accTxn.setStatus(PGConstants.PG_SETTLEMENT_EXECUTED);
  }
}
