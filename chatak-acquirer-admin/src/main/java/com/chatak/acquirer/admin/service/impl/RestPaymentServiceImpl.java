package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.exception.ChatakPayException;
import com.chatak.acquirer.admin.service.RestPaymentService;
import com.chatak.acquirer.admin.util.JsonUtil;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.AccountTransactionsRepository;
import com.chatak.pg.bean.BillingData;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.OriginalChannelEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.exception.HttpClientException;
import com.chatak.pg.model.CardData;
import com.chatak.pg.model.GetMerchantDetailsResponse;
import com.chatak.pg.model.GetTransactionResponse;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.model.TransactionResponse;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
import com.chatak.pg.model.VirtualTerminalAdjustmentRequest;
import com.chatak.pg.model.VirtualTerminalAdjustmentResponse;
import com.chatak.pg.model.VirtualTerminalCaptureDTO;
import com.chatak.pg.model.VirtualTerminalPreAuthDTO;
import com.chatak.pg.model.VirtualTerminalRefundDTO;
import com.chatak.pg.model.VirtualTerminalSaleDTO;
import com.chatak.pg.model.VirtualTerminalVoidDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.RandomGenerator;
/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 16-Mar-2015 6:44:56 PM
 * @version 1.0
 */
@Service
public class RestPaymentServiceImpl implements RestPaymentService {
  private static Logger logger = Logger.getLogger(RestPaymentServiceImpl.class);

  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  MerchantDao merchantDao;
  
  @Autowired
  TransactionDao transactionDao;

  @Autowired
  TerminalDao terminalDao;

  @Autowired
  MessageSource messageSource;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  AccountTransactionsRepository accountTransactionsRepository;

  @Autowired
  RefundTransactionDao refundTransactionDao;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  /**
   * @param terminalSaleDTO
   * @return
   * @throws ChatakPayException
   */
  @Override
  public TransactionResponse doSale(VirtualTerminalSaleDTO terminalSaleDTO)
      throws ChatakPayException {
    try {
      logger.info("Entering :: RestPaymentServiceImpl :: doSale method");

      TransactionRequest transactionRequest =
          CommonUtil.copyBeanProperties(terminalSaleDTO, TransactionRequest.class);
      transactionRequest
          .setMerchantAmount(CommonUtil.getLongAmountNotNull(terminalSaleDTO.getSubTotal())
              + CommonUtil.getLongAmountNotNull(terminalSaleDTO.getTaxAmt())
              + CommonUtil.getLongAmountNotNull(terminalSaleDTO.getTipAmount())
              + CommonUtil.getLongAmountNotNull(terminalSaleDTO.getShippingAmt()));
      transactionRequest.setFeeAmount(CommonUtil.getLongAmount(terminalSaleDTO.getFeeAmount()));
      transactionRequest
          .setTotalTxnAmount(CommonUtil.getLongAmount(terminalSaleDTO.getTxnAmount()));
      transactionRequest.setOrderId(RandomGenerator.generateRandNumeric(Constants.SIX));
      CardData card = new CardData();
      card.setCardHolderName(terminalSaleDTO.getCardHolderName());
      card.setExpDate(terminalSaleDTO.getExpDate());
      card.setCardNumber(terminalSaleDTO.getCardNum());
      card.setCvv(terminalSaleDTO.getCvv());
      card.setCardType(PGUtils.getCCType());// Setting
                                                                        // card
                                                                        // type
      transactionRequest.setCardData(card);
      BillingData billingData = new BillingData();
      billingData.setAddress1(CommonUtil.isNullAndEmpty(terminalSaleDTO.getStreet())
          ? Constants.DUMMY_STREET : terminalSaleDTO.getStreet());
      billingData.setAddress2(terminalSaleDTO.getCity());
      billingData.setCity(CommonUtil.isNullAndEmpty(terminalSaleDTO.getCity()) ? Constants.DUMMY_STREET
          : terminalSaleDTO.getCity());
      billingData.setCountry(CommonUtil.isNullAndEmpty(terminalSaleDTO.getCountry()) ? "USA"
          : terminalSaleDTO.getCountry());
      billingData.setEmail(CommonUtil.isNullAndEmpty(terminalSaleDTO.getEmail()) ? "email@email.com"
          : terminalSaleDTO.getEmail());
      billingData.setState(CommonUtil.isNullAndEmpty(terminalSaleDTO.getState()) ? "California"
          : terminalSaleDTO.getState());
      billingData.setZipCode(CommonUtil.isNullAndEmpty(terminalSaleDTO.getZip()) ? "111111"
          : terminalSaleDTO.getZip());
      transactionRequest.setBillingData(billingData);
      transactionRequest.setTransactionType(TransactionType.SALE);
      transactionRequest.setRegisterNumber(terminalSaleDTO.getTerminalId());// TO-DO
                                                                            // Need
                                                                            // to
                                                                            // add
                                                                            // register
                                                                            // number
      transactionRequest.setMerchantName(terminalSaleDTO.getTerminalId());// TO-DO
                                                                           // Need
                                                                           // to
                                                                           // add
                                                                           // merchant
                                                                           // name
      transactionRequest.setDescription(terminalSaleDTO.getDescription());
      transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      transactionRequest.setTimeZoneOffset(terminalSaleDTO.getTimeZoneOffset());
      transactionRequest.setTimeZoneRegion(terminalSaleDTO.getTimeZoneRegion());
        String output =JsonUtil.postRequest(transactionRequest,String.class,Constants.TRANSACTION_PROCESS);
        TransactionResponse transactionResponse =
            mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doSale method");
        return transactionResponse;
    } catch (HttpClientException exp) {
        logger.error("ERROR :: RestPaymentServiceImpl :: doSale method", exp);
        throw new ChatakPayException(Properties.getProperty("prepaid.agentadmin.general.error.message"));
      }catch (Exception exp) {
      logger.error("ERROR :: RestPaymentServiceImpl :: doSale method", exp);
      throw new ChatakPayException(exp.getMessage());
    }
  }

  /**
   * @param voidDTO
   * @return
   * @throws ChatakPayException
   */
  @Override
  public TransactionResponse doVoid(VirtualTerminalVoidDTO voidDTO) throws ChatakPayException {

    try {
      logger.info("Entering :: RestPaymentServiceImpl :: doVoid method");
      TransactionRequest transactionRequest =
          CommonUtil.copyBeanProperties(voidDTO, TransactionRequest.class);
      transactionRequest.setTransactionType(TransactionType.VOID);
      transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
      transactionRequest.setTimeZoneOffset(voidDTO.getTimeZoneOffset());
      transactionRequest.setTimeZoneRegion(voidDTO.getTimeZoneRegion());
        String output = JsonUtil.postRequest(transactionRequest,String.class,Constants.TRANSACTION_PROCESS);
        TransactionResponse voidResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doVoid method");
        return voidResponse;
    } catch (HttpClientException exp) {
        logger.error("Error :: RestPaymentServiceImpl :: doVoid method", exp);
        throw new ChatakPayException(Constants.UNABLE_TO_PROCESS_REQUEST);
      }catch (Exception exp) {
      logger.error("Error :: RestPaymentServiceImpl :: doVoid method", exp);
      throw new ChatakPayException(exp.getMessage());
    }
  }

  /**
   * @param preAuthDTO
   * @return
   * @throws ChatakPayException
   */
  @Override
  public TransactionResponse doPreAuth(VirtualTerminalPreAuthDTO preAuthDTO)
      throws ChatakPayException {

    try {
      logger.info("Entering :: RestPaymentServiceImpl :: doPreAuth method");
      TransactionRequest transactionRequest =
          CommonUtil.copyBeanProperties(preAuthDTO, TransactionRequest.class);
      transactionRequest.setTxnAmount(CommonUtil.getLongAmount(preAuthDTO.getTxnAmount()));

      transactionRequest = CommonUtil.copyBeanProperties(preAuthDTO, TransactionRequest.class);
      transactionRequest.setMerchantAmount(CommonUtil.getLongAmountNotNull(preAuthDTO.getSubTotal())
          + CommonUtil.getLongAmountNotNull(preAuthDTO.getTaxAmt())
          + CommonUtil.getLongAmountNotNull(preAuthDTO.getTipAmount())
          + CommonUtil.getLongAmountNotNull(preAuthDTO.getShippingAmt()));
      transactionRequest.setOrderId(RandomGenerator.generateRandNumeric(Constants.SIX));
      transactionRequest.setFeeAmount(CommonUtil.getLongAmount(preAuthDTO.getFeeAmount()));
      transactionRequest.setTotalTxnAmount(CommonUtil.getLongAmount(preAuthDTO.getTxnAmount()));
      CardData card = new CardData();
      card.setCardHolderName(preAuthDTO.getCardHolderName());
      card.setExpDate(preAuthDTO.getExpDate());
      card.setCardNumber(preAuthDTO.getCardNum());
      card.setCvv(preAuthDTO.getCvv().toString());
      card.setCardType(PGUtils.getCCType());// Setting
                                                                   // card type
      transactionRequest.setCardData(card);
      BillingData billingData = new BillingData();
      billingData.setAddress1(CommonUtil.isNullAndEmpty(preAuthDTO.getStreet()) ? Constants.DUMMY_STREET
          : preAuthDTO.getStreet());
      billingData.setAddress2(preAuthDTO.getCity());
      billingData.setCity(
          CommonUtil.isNullAndEmpty(preAuthDTO.getCity()) ? Constants.DUMMY_STREET : preAuthDTO.getCity());
      billingData.setCountry(
          CommonUtil.isNullAndEmpty(preAuthDTO.getCountry()) ? "USA" : preAuthDTO.getCountry());
      billingData.setEmail(CommonUtil.isNullAndEmpty(preAuthDTO.getEmail()) ? "email@email.com"
          : preAuthDTO.getEmail());
      billingData.setState(
          CommonUtil.isNullAndEmpty(preAuthDTO.getState()) ? "California" : preAuthDTO.getState());
      billingData.setZipCode(
          CommonUtil.isNullAndEmpty(preAuthDTO.getZip()) ? "111111" : preAuthDTO.getZip());
      transactionRequest.setBillingData(billingData);
      transactionRequest.setRegisterNumber(preAuthDTO.getTerminalId());// TO-DO Need to add register number
      transactionRequest.setMerchantName(preAuthDTO.getTerminalId());// TO-DO Need to add merchant name
      transactionRequest.setTransactionType(TransactionType.AUTH);
      transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      String output=JsonUtil.postRequest(transactionRequest,String.class, Constants.TRANSACTION_PROCESS);
        TransactionResponse preAuthResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doPreAuth method");
        return preAuthResponse;
    }catch (HttpClientException exp) {
        logger.error("Error :: RestPaymentServiceImpl :: doPreAuth method", exp);
        throw new ChatakPayException(Constants.UNABLE_TO_PROCESS_REQUEST);
      } catch (Exception exp) {
      logger.error("Error :: RestPaymentServiceImpl :: doPreAuth method", exp);
      throw new ChatakPayException(exp.getMessage());
    }
  }

  /**
   * @param captureDTO
   * @return
   * @throws ChatakPayException
   */
  @Override
  public TransactionResponse doPreAuthCapture(VirtualTerminalCaptureDTO captureDTO)
      throws ChatakPayException {

    try {
      logger.info("Exiting :: RestPaymentServiceImpl :: doPreAuthCapture method");
      TransactionRequest transactionRequest =
          CommonUtil.copyBeanProperties(captureDTO, TransactionRequest.class);
      transactionRequest.setTxnAmount(CommonUtil.getLongAmount(captureDTO.getTxnAmount()));
      transactionRequest.setTxnRefNumber(captureDTO.getTxnRefNumber());
      transactionRequest.setTransactionType(TransactionType.CAPTURE);
      transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
        String output = JsonUtil.postRequest(transactionRequest,String.class, Constants.TRANSACTION_PROCESS);
        TransactionResponse captureResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doPreAuthCapture method");
        return captureResponse;
    } catch (HttpClientException exp) {
        logger.error("Error :: RestPaymentServiceImpl :: doPreAuthCapture method", exp);
        throw new ChatakPayException(Constants.UNABLE_TO_PROCESS_REQUEST);
      }catch (Exception exp) {
      logger.error("Error :: RestPaymentServiceImpl :: doPreAuthCapture method", exp);
      throw new ChatakPayException(exp.getMessage());
    }
  }

  /**
   * @param refundDTO
   * @return
   * @throws ChatakPayException
   */
  @Override
  public TransactionResponse doRefund(VirtualTerminalRefundDTO refundDTO)
      throws ChatakPayException {
    try {
      logger.info("Entering :: RestPaymentServiceImpl :: doRefund method");
      TransactionRequest transactionRequest =
          CommonUtil.copyBeanProperties(refundDTO, TransactionRequest.class);
      transactionRequest.setTxnAmount((long) Math.round(refundDTO.getSubTotal()*Integer.parseInt("100")));
      transactionRequest.setMerchantAmount((long) Math.round(refundDTO.getSubTotal()*Integer.parseInt("100")));
      transactionRequest.setFeeAmount((long) Math.round(refundDTO.getFeeAmount()*Integer.parseInt("100")));
      transactionRequest.setTotalTxnAmount((long) Math.round(refundDTO.getTxnAmount()*Integer.parseInt("100")));
      transactionRequest.setTransactionType(TransactionType.REFUND);
      transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      transactionRequest.setTimeZoneOffset(refundDTO.getTimeZoneOffset());
      transactionRequest.setTimeZoneRegion(refundDTO.getTimeZoneRegion());
      transactionRequest.setMerchantCode(refundDTO.getMerchantId());
      if (refundDTO.getTxnRefNum() != null) {
        transactionRequest.setTxnRefNumber(refundDTO.getTxnRefNum());
      } else {
        transactionRequest.setTxnRefNumber(refundDTO.getTxnRefNumber());
      }
        String output = JsonUtil.postRequest(transactionRequest,String.class,Constants.TRANSACTION_PROCESS);
        TransactionResponse refundResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doRefund method");
        return refundResponse;
    } catch (HttpClientException exp) {
        logger.error("Error :: RestPaymentServiceImpl :: doRefund method", exp);
        throw new ChatakPayException(Constants.UNABLE_TO_PROCESS_REQUEST);
      }catch (Exception exp) {
      logger.error("Error :: RestPaymentServiceImpl :: doRefund method", exp);
      throw new ChatakPayException(exp.getMessage());
    }
  }

  /**
   * @param adjustmentDTO
   * @return
   * @throws ChatakPayException
   */
  @Override
  public VirtualTerminalAdjustmentResponse doAdjust(VirtualTerminalAdjustmentDTO adjustmentDTO)
      throws ChatakPayException {

    try {
      VirtualTerminalAdjustmentRequest virtualTerminalAdjustmentRequest =
          CommonUtil.copyBeanProperties(adjustmentDTO, VirtualTerminalAdjustmentRequest.class);
      virtualTerminalAdjustmentRequest
          .setTxnAmount(CommonUtil.getLongAmount(adjustmentDTO.getTxnAmount()));
      String output=JsonUtil.postRequest(virtualTerminalAdjustmentRequest,String.class,
              "/transactionService/transaction/adjustment");
        logger.info("Exiting :: RestPaymentServiceImpl :: doAdjust");
        return mapper.readValue(output, VirtualTerminalAdjustmentResponse.class);
    }
    catch (HttpClientException e) {
        logger.error("Error :: RestPaymentServiceImpl :: doAdjust", e);
        throw new ChatakPayException(Constants.UNABLE_TO_PROCESS_REQUEST);
      }catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: doAdjust", e);
      throw new ChatakPayException(e.getMessage());
    }
  }

  /**
   * @param merchantId
   * @param terminalId
   * @param authId
   * @param cardNum
   * @param invoiceNum
   * @return
   * @throws Exception
   */
  @Override
  public GetTransactionResponse getTransaction(String merchantId, String terminalId, String authId,
      String cardNum, String invoiceNum) throws ChatakPayException {
    PGTransaction pgTransaction;
    GetTransactionResponse getTransactionResponse = new GetTransactionResponse();

    pgTransaction = voidTransactionDao.findTransaction(merchantId, terminalId, authId,
        EncryptionUtil.encrypt(cardNum), invoiceNum);

    if (pgTransaction != null) {

      getTransactionResponse.setCardNum(EncryptionUtil.decrypt(pgTransaction.getPan()));
      getTransactionResponse.setCardNumMasked(pgTransaction.getPanMasked());
      getTransactionResponse.setAuthId(pgTransaction.getAuthId());
      getTransactionResponse.setSubTotal(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
      getTransactionResponse.setInvoiceNumber(pgTransaction.getInvoiceNumber());
      getTransactionResponse.setTerminalId(pgTransaction.getTerminalId());
      getTransactionResponse.setTxnAmount(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
      getTransactionResponse.setMerchantId(pgTransaction.getMerchantId());
      getTransactionResponse.setTaxAmt(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
      getTransactionResponse.setCardHolderName(pgTransaction.getPanMasked());
      getTransactionResponse.setExpDate(
          pgTransaction.getExpDate() == null ? "" : pgTransaction.getExpDate().toString());
      getTransactionResponse.setTxnRefNum(pgTransaction.getTransactionId());

      getTransactionResponse.setErrorMessage(Constants.SUCESS);
      getTransactionResponse.setErrorCode(Constants.SUCCESS_CODE);
     
    } else {
      getTransactionResponse.setErrorCode(Constants.ERROR);
      getTransactionResponse.setErrorMessage(Constants.ERROR);
    }

    logger.info("Exiting :: RestPaymentServiceImpl :: getTransaction");
    return getTransactionResponse;
  }

  @Override
  public GetTransactionResponse getTransactionByRefId(String merchantId, String terminalId,
      String refId, String txnType) {
    PGTransaction pgTransaction;
    GetTransactionResponse getTransactionResponse = new GetTransactionResponse();

    try {

      // for completed txns check
        if ("sale".equalsIgnoreCase(txnType)) {
          pgTransaction = transactionDao.getTransaction(merchantId, terminalId, refId);
        } else {
          pgTransaction = transactionDao.getTransactionOnTxnIdAndTxnType(merchantId, terminalId,
              refId, txnType);
        }

        if (pgTransaction != null) {

          getTransactionResponse.setAuthId(pgTransaction.getAuthId());
          getTransactionResponse.setCardNum(EncryptionUtil.decrypt(pgTransaction.getPan()));
          getTransactionResponse.setCardNumMasked(pgTransaction.getPanMasked());
          Long refundedAmount =
              refundTransactionDao.getRefundedAmountOnTxnId(pgTransaction.getTransactionId());
          getTransactionResponse = getAmountOnRefundAmount(pgTransaction, getTransactionResponse, refundedAmount);
          getTransactionResponse.setInvoiceNumber(pgTransaction.getInvoiceNumber());
          getTransactionResponse.setMerchantId(pgTransaction.getMerchantId());
          getTransactionResponse.setTerminalId(pgTransaction.getTerminalId());

          getTransactionResponse = getTxnDoubleAmount(pgTransaction, getTransactionResponse, refundedAmount);
          
          getTransactionResponse
              .setTaxAmt(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
          getTransactionResponse.setCardHolderName(pgTransaction.getCardHolderName());
          getTransactionResponse.setExpDate(
              pgTransaction.getExpDate() == null ? "" : pgTransaction.getExpDate().toString());
          getTransactionResponse.setTxnRefNum(pgTransaction.getTransactionId());
          getTransactionResponse.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
          getTransactionResponse
              .setFeeAmount(CommonUtil.getDoubleAmount(pgTransaction.getFeeAmount()));

          getTransactionResponse.setErrorCode(Constants.SUCCESS_CODE);
          getTransactionResponse.setErrorMessage(Constants.SUCESS);
          getTransactionResponse.setSettlementStatus(pgTransaction.getMerchantSettlementStatus());
        } else {
          getTransactionResponse.setErrorCode(Constants.ERROR);
          getTransactionResponse.setErrorMessage(Constants.ERROR);
        }
    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: getTransactionByRefId", e);
    }
    logger.info("Exiting :: RestPaymentServiceImpl :: getTransactionByRefId");
    return getTransactionResponse;
  }

  private GetTransactionResponse getAmountOnRefundAmount(PGTransaction pgTransaction,
      GetTransactionResponse getTransactionResponse, Long refundedAmount) {
    if (null != refundedAmount) {
      getTransactionResponse.setSubTotal(
          CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount() - refundedAmount));
    } else {
      getTransactionResponse
          .setSubTotal(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
    }
    return getTransactionResponse;
  }

  private GetTransactionResponse getTxnDoubleAmount(PGTransaction pgTransaction,
      GetTransactionResponse getTransactionResponse, Long refundedAmount) {
    if (null != refundedAmount) {
      //Returning balance amount for refund
      getTransactionResponse.setTxnAmount(
          CommonUtil.getDoubleAmount(pgTransaction.getTxnTotalAmount() - refundedAmount));

    } else {
      //Returning total transaction amount 
      getTransactionResponse
          .setTxnAmount(CommonUtil.getDoubleAmountNotNull(pgTransaction.getTxnAmount())
              + (CommonUtil.getDoubleAmountNotNull(pgTransaction.getFeeAmount())));
    }
    return getTransactionResponse;
  }

  /**
   * @param merchantId
   * @return
   * @throws ChatakPayException
   * @throws Exception
   */
  @Override
  public GetMerchantDetailsResponse getMerchantIdAndTerminalId(String merchantCode) {
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchantCode);
    GetMerchantDetailsResponse merchantDetailsResponse = new GetMerchantDetailsResponse();
    if (pgMerchant != null) {
      PGTerminal pgTerminal = terminalDao.getTerminalonMerchantCode(pgMerchant.getId());
      if (pgTerminal != null) {

        merchantDetailsResponse.setMerchantId(pgMerchant.getMerchantCode());
        merchantDetailsResponse.setTerminalId(pgTerminal.getTerminalId().toString());
        merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
      } else {
        merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
        merchantDetailsResponse.setErrorMessage(
            messageSource.getMessage("chatak.admin.virtual.terminal.invalid.merchant", null,
                LocaleContextHolder.getLocale()));
      }
    } else {
      merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
      merchantDetailsResponse.setErrorMessage(messageSource.getMessage(
          "chatak.admin.virtual.terminal.invalid.merchant", null, LocaleContextHolder.getLocale()));
    }
    return merchantDetailsResponse;
  }

  @Override
  public TransactionResponse processPopupVoidOrRefund(TransactionRequest transactionRequest)
      throws ChatakAdminException {

    logger.info("Entering :: RestPaymentServiceImpl :: processPopupVoidOrRefund method");
    TransactionResponse response = new TransactionResponse();
    PGTransaction pgTransaction =
        refundTransactionDao.getTransactionForVoidOrRefundByAccountTransactionId(
            transactionRequest.getAccountTransactionId(), transactionRequest.getMerchantId().toString());
    if (null != pgTransaction) {
      transactionRequest.setTxnRefNumber(pgTransaction.getTransactionId());
      transactionRequest.setOriginChannel(OriginalChannelEnum.ADMIN_WEB.value());
      transactionRequest.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
      transactionRequest.setTerminalId(pgTransaction.getTerminalId());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      transactionRequest.setMerchantCode(pgTransaction.getMerchantId());
      try {
          String output = JsonUtil.postRequest(transactionRequest,String.class,Constants.TRANSACTION_PROCESS);
          response = mapper.readValue(output, TransactionResponse.class);
      } 
      catch (HttpClientException e) {
          logger.error("Error :: RestPaymentServiceImpl :: doAdjust", e);
          throw new ChatakAdminException(Constants.UNABLE_TO_PROCESS_REQUEST);
        }
      catch (Exception e) {
        response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        response.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
        logger.error("ERROR :: RestPaymentServiceImpl :: processPopupVoidOrRefund method", e);
      }
      logger.info("Exiting :: RestPaymentServiceImpl :: processPopupVoidOrRefund method");
      return response;
    } else {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_TXN_NULL);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_TXN_NULL));
    }
    logger.info("Exiting :: RestPaymentServiceImpl :: processPopupVoidOrRefund method");
    return response;
  }

  @Override
  public GetTransactionResponse getTransactionByRefIdForRefund(String merchantId, String terminalId,
      String refId, String txnType) {
    GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
    PGTransaction pgTransaction;

    try {
      List<String> merchantSettlementStatus = new ArrayList<>();
      merchantSettlementStatus.add(Constants.EXECUTED_STATUS);
      merchantSettlementStatus.add(Constants.REFUNDED_STATUS);

      List<PGTransaction> pgList = refundTransactionDao
          .findByMerchantIdAndTerminalIdAndTransactionIdAndStatusAndMerchantSettlementStatusInAndRefundStatusNotLike(
              merchantId, terminalId, refId, 0, 1, merchantSettlementStatus);// 0 for completed txns and 1 for fully refunded transactions
      if (!StringUtil.isListNullNEmpty(pgList)) {
        pgTransaction =
            transactionDao.getTransactionOnTxnIdAndTxnType(merchantId, terminalId, refId, txnType);

        if (null != pgTransaction) {
          Long refundedAmount = refundTransactionDao.getRefundedAmountOnTxnId(refId);
          PGTransaction pgRefundTransaction = transactionDao.getTransaction(merchantId,
              merchantId.substring(merchantId.length() - Constants.EIGHT, merchantId.length()), refId);
          
          getTransactionResponse = getTxnSubTotal(pgTransaction, getTransactionResponse, refundedAmount,
              pgRefundTransaction);
          
          getTransactionResponse.setAuthId(pgTransaction.getAuthId());
          getTransactionResponse.setCardNumMasked(pgTransaction.getPanMasked());
          getTransactionResponse.setInvoiceNumber(pgTransaction.getInvoiceNumber());
          getTransactionResponse.setCardNum(EncryptionUtil.decrypt(pgTransaction.getPan()));
          getTransactionResponse.setMerchantId(pgTransaction.getMerchantId());
          getTransactionResponse.setTerminalId(pgTransaction.getTerminalId());
          getTransactionResponse.setCardHolderName(pgTransaction.getCardHolderName());
          getTransactionResponse
              .setTxnAmount(CommonUtil.getDoubleAmountNotNull(pgTransaction.getTxnAmount())
                  + (CommonUtil.getDoubleAmountNotNull(pgTransaction.getFeeAmount())));
          getTransactionResponse
              .setTaxAmt(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
          getTransactionResponse.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
          getTransactionResponse.setExpDate(
              pgTransaction.getExpDate() == null ? "" : pgTransaction.getExpDate().toString());
          getTransactionResponse.setTxnRefNum(pgTransaction.getTransactionId());
          getTransactionResponse.setErrorMessage(Constants.SUCESS);
          getTransactionResponse
              .setFeeAmount(CommonUtil.getDoubleAmountNotNull(pgTransaction.getFeeAmount()));
          getTransactionResponse.setErrorCode(Constants.SUCCESS_CODE);
          getTransactionResponse.setTotalRefundableBalance(getTransactionResponse.getSubTotal());
          getTransactionResponse.setSettlementStatus(pgTransaction.getMerchantSettlementStatus());
        } else {
          getTransactionResponse.setErrorCode(Constants.ERROR);
          getTransactionResponse.setErrorMessage(Constants.ERROR);
        }
      } else {
        getTransactionResponse.setErrorCode(Constants.ERROR);
        getTransactionResponse.setErrorMessage(Constants.ERROR);
      }
    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: getTransactionByRefId ", e);
    }
    return getTransactionResponse;
  }

  private GetTransactionResponse getTxnSubTotal(PGTransaction pgTransaction,
      GetTransactionResponse getTransactionResponse, Long refundedAmount,
      PGTransaction pgRefundTransaction) {
    if (null != refundedAmount && null != pgTransaction) {
      getTransactionResponse.setSubTotal(CommonUtil
          .getDoubleAmount(pgRefundTransaction.getTxnTotalAmount() - refundedAmount));
    } else if (null != pgTransaction) {
      getTransactionResponse
          .setSubTotal(CommonUtil.getDoubleAmount(pgRefundTransaction.getTxnTotalAmount()));
    }
    return getTransactionResponse;
  }

}
