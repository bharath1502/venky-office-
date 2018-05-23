package com.chatak.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.model.GetMerchantDetailsResponse;
import com.chatak.merchant.model.GetTransactionResponse;
import com.chatak.merchant.model.TransactionResponse;
import com.chatak.merchant.model.VirtualTerminalAdjustmentRequest;
import com.chatak.merchant.model.VirtualTerminalAdjustmentResponse;
import com.chatak.merchant.service.RestPaymentService;
import com.chatak.merchant.util.JsonUtil;
import com.chatak.merchant.util.StringUtil;
import com.chatak.pg.acq.dao.BlackListedCardDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.RefundTransactionDao;
import com.chatak.pg.acq.dao.TerminalDao;
import com.chatak.pg.acq.dao.TransactionDao;
import com.chatak.pg.acq.dao.VoidTransactionDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.acq.dao.model.PGTransaction;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.bean.BillingData;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.enums.EntryModeEnum;
import com.chatak.pg.enums.OriginalChannelEnum;
import com.chatak.pg.enums.TransactionType;
import com.chatak.pg.model.CardData;
import com.chatak.pg.model.TransactionRequest;
import com.chatak.pg.model.VirtualTerminalAdjustmentDTO;
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
import com.sun.jersey.api.client.ClientResponse;

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
  TransactionDao transactionDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  TerminalDao terminalDao;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  BlackListedCardDao blackListedCardDao;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  RefundTransactionDao refundTransactionDao;

  @Autowired
  VoidTransactionDao voidTransactionDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

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

      long cardNumber = Long.parseLong(terminalSaleDTO.getCardNum());
      Response blackListedCardResponse = blackListedCardDao.getCardDataByCardNumber(cardNumber);
      if (blackListedCardResponse.getErrorCode() != null
          && blackListedCardResponse.getErrorCode().equals(ActionErrorCode.ERROR_CODE_Z5)) {
        throw new ChatakPayException(messageSource.getMessage("chatak.transaction.blacklistedcard",
            null, LocaleContextHolder.getLocale()));
      }

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
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      CardData card = new CardData();
      card.setCardHolderName(terminalSaleDTO.getCardHolderName());
      card.setExpDate(terminalSaleDTO.getExpDate());
      card.setCardNumber(terminalSaleDTO.getCardNum());
      card.setCvv(terminalSaleDTO.getCvv());
      card.setCardType(PGUtils.getCCType(terminalSaleDTO.getCardNum()));// Setting card type
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
      transactionRequest.setRegisterNumber(terminalSaleDTO.getTerminalId());// TO-DO Need to add register number
      transactionRequest.setMerchantName(terminalSaleDTO.getTerminalId());// TO-DO Need to add merchant name
      transactionRequest.setDescription(terminalSaleDTO.getDescription());
      transactionRequest.setOriginChannel(OriginalChannelEnum.MERCHANT_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      transactionRequest.setTimeZoneOffset(terminalSaleDTO.getTimeZoneOffset());
      transactionRequest.setTimeZoneRegion(terminalSaleDTO.getTimeZoneRegion());

      ClientResponse response = JsonUtil.postRequest(transactionRequest, Constants.TRANSACTION_PROCESS);

      if (response.getStatus() != HttpStatus.SC_OK) {
        throw new ChatakPayException(messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
      } else {
        String output = response.getEntity(String.class);
        TransactionResponse transactionResponse =
            mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doSale method");
        return transactionResponse;
      }

    } catch (Exception e) {
      logger.error("ERROR :: RestPaymentServiceImpl :: doSale method", e);
      throw new ChatakPayException(e.getMessage());
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
      transactionRequest.setOriginChannel(OriginalChannelEnum.MERCHANT_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      ClientResponse response = JsonUtil.postRequest(transactionRequest, Constants.TRANSACTION_PROCESS);

      if (response.getStatus() == HttpStatus.SC_OK) {

        String output = response.getEntity(String.class);
        TransactionResponse voidResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doVoid method");
        return voidResponse;
      } else {
        throw new ChatakPayException(messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
      }

    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: doVoid method", e);
      throw new ChatakPayException(e.getMessage());
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
      transactionRequest.setFeeAmount(CommonUtil.getLongAmount(preAuthDTO.getFeeAmount()));
      transactionRequest.setTotalTxnAmount(CommonUtil.getLongAmount(preAuthDTO.getTxnAmount()));
      transactionRequest.setOrderId(RandomGenerator.generateRandNumeric(Constants.SIX));
      CardData card = new CardData();
      card.setCardHolderName(preAuthDTO.getCardHolderName());
      card.setExpDate(preAuthDTO.getExpDate());
      card.setCardNumber(preAuthDTO.getCardNum());
      card.setCvv(preAuthDTO.getCvv().toString());
      card.setCardType(PGUtils.getCCType(preAuthDTO.getCardNum()));// Setting card type
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
      transactionRequest.setOriginChannel(OriginalChannelEnum.MERCHANT_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      ClientResponse response = JsonUtil.postRequest(transactionRequest, Constants.TRANSACTION_PROCESS);

      if (response.getStatus() != HttpStatus.SC_OK) {
        throw new ChatakPayException(messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
      } else {

        String output = response.getEntity(String.class);
        TransactionResponse preAuthResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doPreAuth method");
        return preAuthResponse;
      }

    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: doPreAuth method", e);
      throw new ChatakPayException(e.getMessage());
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
      transactionRequest.setOriginChannel(OriginalChannelEnum.MERCHANT_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      ClientResponse response = JsonUtil.postRequest(transactionRequest, Constants.TRANSACTION_PROCESS);

      if (response.getStatus() != HttpStatus.SC_OK) {
        logger.error("Error :: RestPaymentServiceImpl :: doPreAuthCapture method");
        throw new ChatakPayException(messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
      } else {
        String output = response.getEntity(String.class);
        TransactionResponse captureResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doPreAuthCapture method");
        return captureResponse;
      }
    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: doPreAuthCapture method", e);
      throw new ChatakPayException(e.getMessage());
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
      transactionRequest.setTxnAmount(CommonUtil.getLongAmount(refundDTO.getSubTotal()));
      transactionRequest.setTotalTxnAmount(CommonUtil.getLongAmount(refundDTO.getTxnAmount()));
      transactionRequest.setMerchantAmount(CommonUtil.getLongAmount(refundDTO.getSubTotal()));
      transactionRequest.setFeeAmount(CommonUtil.getLongAmount(refundDTO.getFeeAmount()));
      transactionRequest.setTransactionType(TransactionType.REFUND);
      transactionRequest.setOriginChannel(OriginalChannelEnum.MERCHANT_WEB.value());
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      transactionRequest.setTimeZoneOffset(refundDTO.getTimeZoneOffset());
      transactionRequest.setTimeZoneRegion(refundDTO.getTimeZoneRegion());
      if (refundDTO.getTxnRefNum() != null) {
        transactionRequest.setTxnRefNumber(refundDTO.getTxnRefNum());
      } else {
        transactionRequest.setTxnRefNumber(refundDTO.getTxnRefNumber());
      }
      ClientResponse response = JsonUtil.postRequest(transactionRequest, Constants.TRANSACTION_PROCESS);
      if (response.getStatus() != HttpStatus.SC_OK) {
        logger.error("Error :: RestPaymentServiceImpl :: doRefund method");
        throw new ChatakPayException(messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
      } else {
        String output = response.getEntity(String.class);
        TransactionResponse refundResponse = mapper.readValue(output, TransactionResponse.class);
        logger.info("Exiting :: RestPaymentServiceImpl :: doRefund method");
        return refundResponse;
      }
    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: doRefund method", e);
      throw new ChatakPayException(e.getMessage());
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

      ClientResponse response = JsonUtil.postRequest(virtualTerminalAdjustmentRequest,
          "/transactionService/transaction/adjustment");

      if (response.getStatus() != HttpStatus.SC_OK) {
        throw new ChatakPayException(messageSource.getMessage(
            ActionErrorCode.ERROR_CODE_API_CONNECT, null, LocaleContextHolder.getLocale()));
      } else {
        String output = response.getEntity(String.class);
        return mapper.readValue(output, VirtualTerminalAdjustmentResponse.class);
      }
    } catch (Exception e) {
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
      String cardNum, String invoiceNum) {
    PGTransaction pgTransaction;
    GetTransactionResponse getTransactionResponse = new GetTransactionResponse();

    pgTransaction = voidTransactionDao.findTransaction(merchantId, terminalId, authId,
        EncryptionUtil.encrypt(cardNum), invoiceNum);

    if (pgTransaction != null) {

      getTransactionResponse.setAuthId(pgTransaction.getAuthId());
      getTransactionResponse.setCardNum(EncryptionUtil.decrypt(pgTransaction.getPan()));
      getTransactionResponse.setCardNumMasked(pgTransaction.getPanMasked());
      getTransactionResponse
          .setSubTotal(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
      getTransactionResponse.setInvoiceNumber(pgTransaction.getInvoiceNumber());
      getTransactionResponse.setMerchantId(pgTransaction.getMerchantId());
      getTransactionResponse.setTerminalId(pgTransaction.getTerminalId());
      getTransactionResponse
          .setTxnAmount(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
      getTransactionResponse.setTaxAmt(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
      getTransactionResponse.setCardHolderName(pgTransaction.getPanMasked());
      getTransactionResponse.setExpDate(
          pgTransaction.getExpDate() == null ? "" : pgTransaction.getExpDate().toString());
      getTransactionResponse.setTxnRefNum(pgTransaction.getTransactionId());

      getTransactionResponse.setErrorCode(Constants.SUCCESS_CODE);
      getTransactionResponse.setErrorMessage(Constants.SUCESS);
    } else {
      getTransactionResponse.setErrorCode(Constants.ERROR);
      getTransactionResponse.setErrorMessage(Constants.ERROR);
    }
    return getTransactionResponse;
  }

  @Override
  public GetTransactionResponse getTransactionByRefId(String merchantId, String terminalId,
      String refId, String txnType) {
    PGTransaction pgTransaction;
    GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
    try {
     // if txn is not completed then fetch

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
          getTransactionResponse
              .setSubTotal(CommonUtil.getDoubleAmountNotNull(pgTransaction.getTxnAmount()));
          getTransactionResponse.setInvoiceNumber(pgTransaction.getInvoiceNumber());
          getTransactionResponse.setMerchantId(pgTransaction.getMerchantId());
          getTransactionResponse.setTerminalId(pgTransaction.getTerminalId());

          Long refundedAmount =
              refundTransactionDao.getRefundedAmountOnTxnId(pgTransaction.getTransactionId());
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
          setTransactionResponseDetails(pgTransaction, getTransactionResponse);
        } else {
          getTransactionResponse.setErrorCode(Constants.ERROR);
          getTransactionResponse.setErrorMessage(Constants.ERROR);
        }
      
    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: getTransactionByRefId method ", e);
    }
    return getTransactionResponse;
  }

  private void setTransactionResponseDetails(PGTransaction pgTransaction, GetTransactionResponse getTransactionResponse) {
	getTransactionResponse
	      .setTaxAmt(CommonUtil.getDoubleAmount(pgTransaction.getTxnAmount()));
	  getTransactionResponse.setCardHolderName(pgTransaction.getCardHolderName());
	  getTransactionResponse.setExpDate(
	      pgTransaction.getExpDate() == null ? "" : pgTransaction.getExpDate().toString());
	  getTransactionResponse.setTxnRefNum(pgTransaction.getTransactionId());
	  getTransactionResponse.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
	  getTransactionResponse
	      .setFeeAmount(CommonUtil.getDoubleAmountNotNull(pgTransaction.getFeeAmount()));

	  getTransactionResponse.setErrorCode(Constants.SUCCESS_CODE);
	  getTransactionResponse.setErrorMessage(Constants.SUCESS);
	  getTransactionResponse.setSettlementStatus(pgTransaction.getMerchantSettlementStatus());
  }

  @Override
  public GetTransactionResponse getTransactionByRefIdForRefund(String merchantId, String terminalId,
      String refId, String txnType) throws ChatakMerchantException {
    PGTransaction pgTransaction;
    GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
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

        if (pgTransaction != null) {
          Long refundedAmount = refundTransactionDao.getRefundedAmountOnTxnId(refId);
          PGTransaction pgRefundTransaction = transactionDao.getTransaction(merchantId,
              merchantId.substring(merchantId.length() - Constants.EIGHT, merchantId.length()), refId);
          getTxnSubTotal(getTransactionResponse, refundedAmount, pgRefundTransaction);
          getTransactionResponse.setAuthId(pgTransaction.getAuthId());
          getTransactionResponse.setCardNum(EncryptionUtil.decrypt(pgTransaction.getPan()));
          getTransactionResponse.setCardNumMasked(pgTransaction.getPanMasked());
          getTransactionResponse.setInvoiceNumber(pgTransaction.getInvoiceNumber());
          getTransactionResponse.setMerchantId(pgTransaction.getMerchantId());
          getTransactionResponse.setTerminalId(pgTransaction.getTerminalId());
          getTransactionResponse
              .setTxnAmount(CommonUtil.getDoubleAmountNotNull(pgTransaction.getTxnAmount())
                  + (CommonUtil.getDoubleAmountNotNull(pgTransaction.getFeeAmount())));
          setTransactionResponseDetails(pgTransaction, getTransactionResponse);
          getTransactionResponse.setTotalRefundableBalance(getTransactionResponse.getSubTotal());
        } else {
          getTransactionResponse.setErrorCode(Constants.ERROR);
          getTransactionResponse.setErrorMessage(Constants.ERROR);
        }
      } else {
        getTransactionResponse.setErrorCode(Constants.ERROR);
        getTransactionResponse.setErrorMessage(Constants.ERROR);
      }
    } catch (Exception e) {
      logger.error("Error :: RestPaymentServiceImpl :: getTransactionByRefId method ", e);
    }
    return getTransactionResponse;
  }

  private void getTxnSubTotal(GetTransactionResponse getTransactionResponse, Long refundedAmount,
      PGTransaction pgRefundTransaction) {
    if (null != refundedAmount) {
      getTransactionResponse.setSubTotal(CommonUtil
          .getDoubleAmount(pgRefundTransaction.getTxnTotalAmount() - refundedAmount));
    } else {
      getTransactionResponse
          .setSubTotal(CommonUtil.getDoubleAmount(pgRefundTransaction.getTxnTotalAmount()));
    }
  }

  /**
   * @param merchantId
   * @return
   * @throws ChatakPayException
   * @throws Exception
   */
  @Override
  public GetMerchantDetailsResponse getMerchantIdAndTerminalId(String merchantId)
      throws ChatakPayException {
    PGMerchant pgMerchant = merchantProfileDao.getMerchantById(Long.valueOf(merchantId));
    GetMerchantDetailsResponse merchantDetailsResponse = new GetMerchantDetailsResponse();
    if (pgMerchant != null) {
      PGTerminal pgTerminal = terminalDao.getTerminalonMerchantCode(Long.valueOf(merchantId));
      if (pgTerminal != null) {
        merchantDetailsResponse.setMerchantId(pgMerchant.getMerchantCode());
        merchantDetailsResponse.setBusinessName(pgMerchant.getBusinessName());
        merchantDetailsResponse.setTerminalId(pgTerminal.getTerminalId().toString());
        merchantDetailsResponse.setErrorCode(Constants.SUCCESS_CODE);
      } else {
        merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
        merchantDetailsResponse.setErrorMessage(Properties.getProperty("chatak.general.error"));
      }
    } else {
      merchantDetailsResponse.setErrorCode(Constants.ERROR_CODE);
      merchantDetailsResponse.setErrorMessage(Properties.getProperty("chatak.general.error"));
    }
    return merchantDetailsResponse;
  }

  @Override
  public TransactionResponse processPopupVoidOrRefund(TransactionRequest transactionRequest)
      throws ChatakMerchantException {

    logger.info("Entering :: RestPaymentServiceImpl :: processPopupVoidOrRefund method");
    TransactionResponse response = new TransactionResponse();
    PGTransaction pgTransaction =
        refundTransactionDao.getTransactionForVoidOrRefundByAccountTransactionId(
            transactionRequest.getAccountTransactionId(), transactionRequest.getMerchantId());
    if (null != pgTransaction) {
      transactionRequest.setEntryMode(EntryModeEnum.MANUAL);
      transactionRequest.setOriginChannel(OriginalChannelEnum.MERCHANT_WEB.value());
      transactionRequest.setTxnRefNumber(pgTransaction.getTransactionId());
      transactionRequest.setCgRefNumber(pgTransaction.getIssuerTxnRefNum());
      transactionRequest.setTerminalId(pgTransaction.getTerminalId());
      ClientResponse clientResponse;
      try {
        clientResponse = JsonUtil.postRequest(transactionRequest, Constants.TRANSACTION_PROCESS);
        if (clientResponse.getStatus() != HttpStatus.SC_OK) {
          throw new ChatakMerchantException(ActionErrorCode.ERROR_CODE_Z5);
        } else {
          String output = clientResponse.getEntity(String.class);
          response = mapper.readValue(output, TransactionResponse.class);
        }
      } catch (Exception e1) {
        response.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        response.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
        logger.error("ERROR :: RestPaymentServiceImpl :: processPopupVoidOrRefund method", e1);
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

}
