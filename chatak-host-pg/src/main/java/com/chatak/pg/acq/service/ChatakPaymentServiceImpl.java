/**
 * 
 */
package com.chatak.pg.acq.service;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.validator.routines.CreditCardValidator;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
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
import com.chatak.pg.acq.dao.model.PGSwitch;
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
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.EncryptionUtil;
import com.chatak.pg.util.JPOSUtil;
import com.chatak.pg.util.PGConstants;
import com.chatak.pg.util.PGUtils;
import com.chatak.pg.util.POSEntryMode;
import com.chatak.pg.util.StringUtils;
import com.chatak.switches.prepaid.ChatakPrepaidSwitchTransaction;
import com.chatak.switches.sb.SwitchTransaction;
import com.chatak.switches.sb.exception.ChatakSwitchException;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 09-Mar-2015 2:26:44 pm
 * @version 1.0
 */
public class ChatakPaymentServiceImpl implements PaymentService {

  private static Logger log = Logger.getLogger(ChatakPaymentServiceImpl.class);;

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
  
  public ChatakPaymentServiceImpl() {
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
        pgTransaction.setTxnMode(authRequest.getMode());
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
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.auth(authRequest.getIsoMsg());

        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;

        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        pgSwitchTransaction.setTxnMode(authRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setTxnMode(authRequest.getMode());
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

      }catch (ChatakSwitchException e) {
        log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :"+e.getMessage(),e);
        authResponse.setErrorCode(e.getMessage());
        authResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
        
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(authRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(authRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(pgTransaction.getTxnMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setTxnMode(authRequest.getMode());
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
        pgTransaction.setTxnMode(captureRequest.getMode());
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
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.financialAdvice(captureRequest.getIsoMsg());

        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;

        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        pgSwitchTransaction.setTxnMode(captureRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setTxnMode(captureRequest.getMode());
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

      }catch (ChatakSwitchException e) {
        log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :"+e.getMessage(),e);
        captureResponse.setErrorCode(e.getMessage());
        captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
        
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(captureRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(captureRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(captureRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(captureRequest.getMode());
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
        pgTransaction.setTxnMode(purchaseRequest.getMode());
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
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.financial(purchaseRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        pgSwitchTransaction.setTxnMode(purchaseRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        pgTransaction.setTxnMode(purchaseRequest.getMode());
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
        
      }catch (ChatakSwitchException e) {
        log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :"+e.getMessage(),e);
        purchaseResponse.setErrorCode(e.getMessage());
        purchaseResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
        
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(purchaseRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(purchaseRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(purchaseRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(purchaseRequest.getMode());
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
      throws ServiceException {
    log.debug("PaymentServiceImpl | adjustmentTransaction | Entering");
    AdjustmentResponse adjustmentResponse = new AdjustmentResponse();

    try{
      
      //validation of Request
      validateRequest(adjustmentRequest);

      String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
      String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_SALE_ADJ);
      Long txnTotalAmount = adjustmentRequest.getTxnAmount() + feeAmount;
      
      PGSwitchTransaction pgSwitchTransaction = null;
      PGTransaction pgTransaction = null;
      adjustmentRequest.setTxnRefNumber(txnRefNum);
      try{
        //Fetch original transaction
        PGTransaction saleTransaction = transactionDao.getTransaction(
            adjustmentRequest.getMerchantId().toString(),
            adjustmentRequest.getTerminalId(),
            adjustmentRequest.getTxnRefNum()
            );
        if(saleTransaction == null ){
          throw new ServiceException(ActionCode.ERROR_CODE_78);
        }
        
        //Create Transaction record 
        pgTransaction = populatePGTransaction(adjustmentRequest);
        pgTransaction.setTransactionId(txnRefNum);
        pgTransaction.setRefTransactionId(adjustmentRequest.getTxnRefNum());
        pgTransaction.setAuthId(authId);
        pgTransaction.setTransactionType(PGConstants.TXN_TYPE_SALE_ADJ);
        pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
        pgTransaction.setFeeAmount(saleTransaction.getFeeAmount());
        pgTransaction.setTxnTotalAmount(adjustmentRequest.getTxnAmount() + saleTransaction.getFeeAmount());
        pgTransaction.setCreatedDate(timestamp);
        pgTransaction.setUpdatedDate(timestamp);
        pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        pgTransaction.setTxnMode(adjustmentRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);

        //Logging EMVTransation based on chipTransaction true
        if(adjustmentRequest.getChipTransaction()){
          logEmvTransaction(adjustmentRequest.getEmvData(), txnRefNum);
        }
        
        //Switch transaction log before Switch call
        pgSwitchTransaction = populateSwitchTransactionRequest(adjustmentRequest);
        pgSwitchTransaction.setPgTransactionId(txnRefNum);
        pgSwitchTransaction.setCreatedDate(timestamp);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        
        //Switch interface call
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.authAdvice(adjustmentRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        pgSwitchTransaction.setTxnMode(adjustmentRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        pgTransaction.setTxnMode(adjustmentRequest.getMode());
        //Update transaction status and switch response
        voidtransactiondao.createTransaction(pgTransaction);
        
        //Set Response fields
        adjustmentResponse.setTxnRefNum(txnRefNum);
        adjustmentResponse.setAuthId(authId);
        adjustmentResponse.setTxnAmount(adjustmentRequest.getTxnAmount());
        adjustmentResponse.setFeeAmount(feeAmount);
        adjustmentResponse.setTotalAmount(txnTotalAmount);
        adjustmentResponse.setErrorCode(switchResponseCode);
        adjustmentResponse.setErrorMessage(switchResponseMessage);
        
      }catch (ChatakSwitchException e) {
        log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :"+e.getMessage(),e);
        adjustmentResponse.setErrorCode(e.getMessage());
        adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
        
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(adjustmentRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(adjustmentRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(adjustmentRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(adjustmentRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
        adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
        adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      }
      
      //Required to set in reversal 
      adjustmentResponse.setTxnRefNum(txnRefNum);

    } catch (ServiceException e) {
      adjustmentResponse.setErrorCode(e.getMessage());
      adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | adjustmentTransaction | ServiceException :"+e.getMessage(),e);
    } catch (DataAccessException e) {
      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | adjustmentTransaction | DataAccessException :"+e.getMessage(),e);
    } catch (Exception e) {
      adjustmentResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      adjustmentResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | adjustmentTransaction | Exception :"+e.getMessage(),e);
    } 

    log.debug("PaymentServiceImpl | adjustmentTransaction | Exiting");
    return adjustmentResponse;
  }


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
        pgTransaction.setTxnMode(voidRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);

        //Logging EMVTransation based on chipTransaction true
        if(voidRequest.getChipTransaction()){
          logEmvTransaction(voidRequest.getEmvData(), txnRefNum);
        }
        
        //Switch transaction log before Switch call
        pgSwitchTransaction = populateSwitchTransactionRequest(voidRequest);
        pgSwitchTransaction.setPgTransactionId(txnRefNum);
        pgSwitchTransaction.setCreatedDate(timestamp);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        
        //Switch interface call
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.reversalAdvice(voidRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        pgSwitchTransaction.setTxnMode(voidRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        pgTransaction.setTxnMode(voidRequest.getMode());
        //Update transaction status and switch response
        voidtransactiondao.createTransaction(pgTransaction);
        
        //Set Response fields
        voidResponse.setTxnRefNum(txnRefNum);
        voidResponse.setAuthId(authId);
        voidResponse.setErrorCode(switchResponseCode);
        voidResponse.setErrorMessage(switchResponseMessage);
        
      }catch (ChatakSwitchException e) {
        log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :"+e.getMessage(),e);
        voidResponse.setErrorCode(e.getMessage());
        voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
        
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(voidRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(voidRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(voidRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(voidRequest.getMode());
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
  public ReversalResponse reversalTransaction (ReversalRequest reversalRequest) throws ServiceException {
    log.debug("PaymentServiceImpl | reversalTransaction | Entering");
    ReversalResponse reversalResponse = new ReversalResponse();
    try {

      
      //validation of Request
      validateRequest(reversalRequest);

      String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
      String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      PGSwitchTransaction pgSwitchTransaction = null;
      PGTransaction pgTransaction = null;
      reversalRequest.setTxnRefNumber(txnRefNum);
      try{
        //Fetch original transaction
        PGTransaction orgTransaction = voidtransactiondao.getTransactionOnInvoiceNum(
            reversalRequest.getMerchantId().toString(),
            reversalRequest.getTerminalId(),
            reversalRequest.getInvoiceNumber());
        if(orgTransaction == null){
          throw new Exception(ActionCode.ERROR_CODE_78);
        }
        
        //Create Transaction record 
        pgTransaction = populatePGTransaction(reversalRequest);
        pgTransaction.setTransactionId(txnRefNum);
        pgTransaction.setRefTransactionId(orgTransaction.getTransactionId());
        pgTransaction.setAuthId(authId);
        pgTransaction.setTransactionType(PGConstants.TXN_TYPE_REVERSAL);
        pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
        pgTransaction.setFeeAmount(orgTransaction.getFeeAmount());
        pgTransaction.setTxnTotalAmount(orgTransaction.getTxnTotalAmount());
        pgTransaction.setCreatedDate(timestamp);
        pgTransaction.setUpdatedDate(timestamp);
        pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        pgTransaction.setTxnMode(reversalRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);

        //Logging EMVTransation based on chipTransaction true
        if(reversalRequest.getChipTransaction()){
          logEmvTransaction(reversalRequest.getEmvData(), txnRefNum);
        }
        
        //Switch transaction log before Switch call
        pgSwitchTransaction = populateSwitchTransactionRequest(reversalRequest);
        pgSwitchTransaction.setPgTransactionId(txnRefNum);
        pgSwitchTransaction.setCreatedDate(timestamp);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        
        //Switch interface call
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.reversal(reversalRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        pgSwitchTransaction.setTxnMode(reversalRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        pgTransaction.setTxnMode(reversalRequest.getMode());
        //Update transaction status and switch response
        voidtransactiondao.createTransaction(pgTransaction);
        
        //Set Response fields
        reversalResponse.setTxnRefNum(txnRefNum);
        reversalResponse.setAuthId(authId);
        reversalResponse.setErrorCode(switchResponseCode);
        reversalResponse.setErrorMessage(switchResponseMessage);
        
      }catch (ChatakSwitchException e) {
        log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :"+e.getMessage(),e);
        reversalResponse.setErrorCode(e.getMessage());
        reversalResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
        
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(reversalRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(reversalRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(reversalRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(reversalRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
        reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
        reversalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      }
      
    } catch (ServiceException e) {
      reversalResponse.setErrorCode(e.getMessage());
      reversalResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | reversalTransaction | ServiceException :"+e.getMessage(),e);
    } catch (DataAccessException e) {
      reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      reversalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | reversalTransaction | DataAccessException :"+e.getMessage(),e);
    } catch (Exception e) {
      reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      reversalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | reversalTransaction | Exception :"+e.getMessage(),e);
    } 
    log.debug("PaymentServiceImpl | reversalTransaction | Exiting");
    return reversalResponse;
  }

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
  public RefundResponse refundTransaction (RefundRequest refundRequest) throws ServiceException {
    log.debug("PaymentServiceImpl | refundTransaction | Entering");
    RefundResponse refundResponse = new RefundResponse();
    try {

      
      //validation of Request
      validateRequest(refundRequest);

      String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
      String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_REFUND);
      Long txnTotalAmount = refundRequest.getTxnAmount() + feeAmount;
      
      PGSwitchTransaction pgSwitchTransaction = null;
      PGTransaction pgTransaction = null;
      refundRequest.setTxnRefNumber(txnRefNum);
      try{
        
        //Create Transaction record 
        pgTransaction = populatePGTransaction(refundRequest);
        pgTransaction.setTransactionId(txnRefNum);
        pgTransaction.setAuthId(authId);
        pgTransaction.setTransactionType(PGConstants.TXN_TYPE_REFUND);
        pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_CREDIT);
        pgTransaction.setFeeAmount(feeAmount);
        pgTransaction.setTxnTotalAmount(txnTotalAmount);
        pgTransaction.setCreatedDate(timestamp);
        pgTransaction.setUpdatedDate(timestamp);
        pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        pgTransaction.setTxnMode(refundRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);

        //Logging EMVTransation based on chipTransaction true
        if(refundRequest.getChipTransaction()){
          logEmvTransaction(refundRequest.getEmvData(), txnRefNum);
        }
        
        //Switch transaction log before Switch call
        pgSwitchTransaction = populateSwitchTransactionRequest(refundRequest);
        pgSwitchTransaction.setPgTransactionId(txnRefNum);
        pgSwitchTransaction.setCreatedDate(timestamp);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        
        //Switch interface call
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.financial(refundRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        pgSwitchTransaction.setTxnMode(refundRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        pgTransaction.setTxnMode(refundRequest.getMode());
        //Update transaction status and switch response
        voidtransactiondao.createTransaction(pgTransaction);
        
        //Set Response fields
        refundResponse.setTxnRefNum(txnRefNum);
        refundResponse.setAuthId(authId);
        refundResponse.setTxnAmount(refundRequest.getTxnAmount());
        refundResponse.setFeeAmount(feeAmount);
        refundResponse.setErrorCode(switchResponseCode);
        refundResponse.setErrorMessage(switchResponseMessage);
        
      }catch (ChatakSwitchException e) {
        log.error("PaymentServiceImpl | ChatakSwitchException | ServiceException :"+e.getMessage(),e);
        refundResponse.setErrorCode(e.getMessage());
        refundResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
        
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(refundRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);

        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(refundRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgSwitchTransaction.setTxnMode(refundRequest.getMode());
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        pgTransaction.setTxnMode(refundRequest.getMode());
        voidtransactiondao.createTransaction(pgTransaction);
        
        refundResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
        refundResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      }
      
      //Required to set in reversal 
      refundResponse.setTxnRefNum(txnRefNum);
    
    } catch (ServiceException e) {
      refundResponse.setErrorCode(e.getMessage());
      refundResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | refundTransaction | ServiceException :"+e.getMessage(),e);
    } catch (DataAccessException e) {
      refundResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      refundResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | refundTransaction | DataAccessException :"+e.getMessage(),e);
    } catch (Exception e) {
      refundResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      refundResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | refundTransaction | Exception :"+e.getMessage(),e);
    } 
    log.debug("PaymentServiceImpl | refundTransaction | Exiting");
    return refundResponse;
  }


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
        //throw new ServiceException(ActionCode.ERROR_CODE_14);
      } else if(!PGUtils.isValidCardExpiryDate(request.getExpDate())){
        throw new ServiceException(ActionCode.ERROR_CODE_54);
      }
      
      PGMerchant pgMerchant = merchantTerminalDao.validateMerchantIdAndTerminalId(request.getMerchantId().toString(), request.getTerminalId());

      if(null == pgMerchant){
        throw new ServiceException(ActionCode.ERROR_CODE_03);
      }
      
      if(!isValidPosEntryMode(request)){
        throw new ServiceException(ActionCode.ERROR_CODE_96);
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
    //pgemvTransaction.setAppCrypto();
    pgemvTransaction.setAtc(emvData.getAtc());
    //pgemvTransaction.setCryptoInfo(emvData.getc);
    pgemvTransaction.setCvrm(emvData.getCvmr());
    pgemvTransaction.setFci(emvData.getFci());
    pgemvTransaction.setFcip(emvData.getFcip());
    pgemvTransaction.setIad(emvData.getIad());
    pgemvTransaction.setIfd(emvData.getIfd());
    //pgemvTransaction.setIid(emvData.getii);
    pgemvTransaction.setIsr(emvData.getIsr());
    pgemvTransaction.setIst(emvData.getIst());
    pgemvTransaction.setIst1(emvData.getIst_1());
    pgemvTransaction.setLanRef(emvData.getLan());
    pgemvTransaction.setPgTransactionId(txnRefNumber);
    pgemvTransaction.setPsn(emvData.getPsn());
    //pgemvTransaction.setTavn(emvData.getta);
    pgemvTransaction.setTcc(emvData.getTcc());
    //pgemvTransaction.setTerminalCapabilities(emvData.getter);
    //pgemvTransaction.setTerminalType(terminalType);
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
    //pgSwitchTransaction.setMti(request.getMti());
    pgSwitchTransaction.setPanMasked(StringUtils.getMaskedString(request.getCardNum(), 5, 4));
    pgSwitchTransaction.setPosEntryMode(request.getPosEntryMode());
    pgSwitchTransaction.setPan(EncryptionUtil.encrypt(request.getCardNum()));
    pgSwitchTransaction.setTxnMode(request.getMode());
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
    
    //request.getIsoMsg().set(23, "000");

    String functionCode = (String)request.getIsoMsg().getValue(24);
    request.getIsoMsg().set(24, functionCode.substring(1,4));
    
    request.getIsoMsg().set(60, "1111");//Setting dummy data to issuance - utcd
    
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
    pgTransaction.setExpDate(request.getExpDate()!=null?Long.valueOf(request.getExpDate()):null);
    pgTransaction.setPosEntryMode(request.getPosEntryMode().substring(1));
    pgTransaction.setProcessor(ProcessorType.CHATAK.toString());
    pgTransaction.setTxnMode(request.getMode());
    return pgTransaction;
  }
  
  public BalanceEnquiryResponse balanceEnquiryTransaction(BalanceEnquiryRequest balanceEnquiryRequest)
      throws ServiceException {
    log.debug("PaymentServiceImpl | balanceEnquiryTransaction | Entering");
    BalanceEnquiryResponse balanceEnquiryResponse = new BalanceEnquiryResponse();

    try{
      
      //validation of Request
      validateRequest(balanceEnquiryRequest);

      String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
      String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_BALANCE_ENQ);
      //Long txnTotalAmount = balanceEnquiryRequest.getTxnAmount() + feeAmount;
      
      PGSwitchTransaction pgSwitchTransaction = null;
      PGTransaction pgTransaction = null;
      balanceEnquiryRequest.setTxnRefNumber(txnRefNum);
      try{
        
        //Create Transaction record 
        pgTransaction = populatePGTransaction(balanceEnquiryRequest);
        pgTransaction.setTransactionId(txnRefNum);
        pgTransaction.setAuthId(authId);
        pgTransaction.setTransactionType(PGConstants.TXN_TYPE_BALANCE_ENQ);
        pgTransaction.setCreatedDate(timestamp);
        pgTransaction.setUpdatedDate(timestamp);
        pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        voidtransactiondao.createTransaction(pgTransaction);

        //Logging EMVTransation based on chipTransaction true
        if(balanceEnquiryRequest.getChipTransaction()){
          logEmvTransaction(balanceEnquiryRequest.getEmvData(), txnRefNum);
        }
        
        //Switch transaction log before Switch call
        pgSwitchTransaction = populateSwitchTransactionRequest(balanceEnquiryRequest);
        pgSwitchTransaction.setPgTransactionId(txnRefNum);
        pgSwitchTransaction.setCreatedDate(timestamp);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        
        //Switch interface call
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.authAdvice(balanceEnquiryRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          balanceEnquiryResponse.setBalance(switchISOMsg.getValue(54)!=null?switchISOMsg.getString(54):"");
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        //Update transaction status and switch response
        voidtransactiondao.createTransaction(pgTransaction);
        
        //Set Response fields
        balanceEnquiryResponse.setTxnRefNum(txnRefNum);
        balanceEnquiryResponse.setAuthId(authId);
        balanceEnquiryResponse.setErrorCode(switchResponseCode);
        balanceEnquiryResponse.setErrorMessage(switchResponseMessage);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        voidtransactiondao.createTransaction(pgTransaction);
        
        balanceEnquiryResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
        balanceEnquiryResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      }

    } catch (ServiceException e) {
      balanceEnquiryResponse.setErrorCode(e.getMessage());
      balanceEnquiryResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | balanceEnquiryTransaction | ServiceException :"+e.getMessage(),e);
    } catch (DataAccessException e) {
      balanceEnquiryResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      balanceEnquiryResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | balanceEnquiryTransaction | DataAccessException :"+e.getMessage(),e);
    } catch (Exception e) {
      balanceEnquiryResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      balanceEnquiryResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | balanceEnquiryTransaction | Exception :"+e.getMessage(),e);
    } 

    log.debug("PaymentServiceImpl | balanceEnquiryTransaction | Exiting");
    return balanceEnquiryResponse;
  }
  
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
      throws ServiceException {
    log.debug("PaymentServiceImpl | cashWithdrawalTransaction | Entering");
    CashWithdrawalResponse cashWithdrawalResponse = new CashWithdrawalResponse();

    try{
      
      //validation of Request
      validateRequest(cashWithdrawalRequest);

      String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
      String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_CASH_WITHDRAWAL);
      Long txnTotalAmount = cashWithdrawalRequest.getTxnAmount() + feeAmount;
      
      PGSwitchTransaction pgSwitchTransaction = null;
      PGTransaction pgTransaction = null;
      cashWithdrawalRequest.setTxnRefNumber(txnRefNum);
      
      try{
        
        //Create Transaction record 
        pgTransaction = populatePGTransaction(cashWithdrawalRequest);
        pgTransaction.setTransactionId(txnRefNum);
        pgTransaction.setAuthId(authId);
        pgTransaction.setTransactionType(PGConstants.TXN_TYPE_CASH_WITHDRAWAL);
        pgTransaction.setPaymentMethod(PGConstants.PAYMENT_METHOD_DEBIT);
        pgTransaction.setFeeAmount(feeAmount);
        pgTransaction.setTxnTotalAmount(txnTotalAmount);
        pgTransaction.setCreatedDate(timestamp);
        pgTransaction.setUpdatedDate(timestamp);
        pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        voidtransactiondao.createTransaction(pgTransaction);

        //Logging EMVTransation based on chipTransaction true
        if(cashWithdrawalRequest.getChipTransaction()){
          logEmvTransaction(cashWithdrawalRequest.getEmvData(), txnRefNum);
        }
        
        //Switch transaction log before Switch call
        pgSwitchTransaction = populateSwitchTransactionRequest(cashWithdrawalRequest);
        pgSwitchTransaction.setPgTransactionId(txnRefNum);
        pgSwitchTransaction.setCreatedDate(timestamp);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        
        //Switch interface call
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.financial(cashWithdrawalRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        //Update transaction status and switch response
        voidtransactiondao.createTransaction(pgTransaction);
        
        //Set Response fields
        cashWithdrawalResponse.setTxnRefNum(txnRefNum);
        cashWithdrawalResponse.setAuthId(authId);
        cashWithdrawalResponse.setTxnAmount(cashWithdrawalRequest.getTxnAmount());
        cashWithdrawalResponse.setFeeAmount(feeAmount);
        cashWithdrawalResponse.setTotalAmount(txnTotalAmount);
        cashWithdrawalResponse.setErrorCode(switchResponseCode);
        cashWithdrawalResponse.setErrorMessage(switchResponseMessage);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        voidtransactiondao.createTransaction(pgTransaction);
        
        cashWithdrawalResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
        cashWithdrawalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      }
      
      //Required to set in reversal 
      cashWithdrawalResponse.setTxnRefNum(txnRefNum);

    } catch (ServiceException e) {
      cashWithdrawalResponse.setErrorCode(e.getMessage());
      cashWithdrawalResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | cashWithdrawalTransaction | ServiceException :"+e.getMessage(),e);
    } catch (DataAccessException e) {
      cashWithdrawalResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      cashWithdrawalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | cashWithdrawalTransaction | DataAccessException :"+e.getMessage(),e);
    } catch (Exception e) {
      cashWithdrawalResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      cashWithdrawalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | cashWithdrawalTransaction | Exception :"+e.getMessage(),e);
    } 

    log.debug("PaymentServiceImpl | cashWithdrawalTransaction | Exiting");
    return cashWithdrawalResponse;
  }
  
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
      throws ServiceException {
    log.debug("PaymentServiceImpl | cashBackTransaction | Entering");
    CashBackResponse cashBackResponse = new CashBackResponse();

    try{
      
      //validation of Request
      validateRequest(cashBackRequest);

      String txnRefNum = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_TXN_REF_NUM);
      String authId = RandomGenerator.generateRandNumeric(PGConstants.LENGTH_AUTH_ID);
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      Long feeAmount = feeDetailDao.getPGFeeAmount(PGConstants.TXN_TYPE_CASH_BACK);
      Long txnTotalAmount = cashBackRequest.getTxnAmount() + feeAmount;
      
      PGSwitchTransaction pgSwitchTransaction = null;
      PGTransaction pgTransaction = null;
      cashBackRequest.setTxnRefNumber(txnRefNum);
      
      try{
        
        //Create Transaction record 
        pgTransaction = populatePGTransaction(cashBackRequest);
        pgTransaction.setTransactionId(txnRefNum);
        pgTransaction.setAuthId(authId);
        pgTransaction.setTransactionType(PGConstants.TXN_TYPE_CASH_BACK);
        pgTransaction.setFeeAmount(feeAmount);
        pgTransaction.setTxnTotalAmount(txnTotalAmount);
        pgTransaction.setCreatedDate(timestamp);
        pgTransaction.setUpdatedDate(timestamp);
        pgTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        voidtransactiondao.createTransaction(pgTransaction);

        //Logging EMVTransation based on chipTransaction true
        if(cashBackRequest.getChipTransaction()){
          logEmvTransaction(cashBackRequest.getEmvData(), txnRefNum);
        }
        
        //Switch transaction log before Switch call
        pgSwitchTransaction = populateSwitchTransactionRequest(cashBackRequest);
        pgSwitchTransaction.setPgTransactionId(txnRefNum);
        pgSwitchTransaction.setCreatedDate(timestamp);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_INPROCESS);
        
        //Switch interface call
        SwitchTransaction switchTransaction = new ChatakPrepaidSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.CHATAK.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.financial(cashBackRequest.getIsoMsg());
        
        String switchResponseCode = switchISOMsg.getValue(39)!=null?(String)switchISOMsg.getValue(39):null;
        String switchResponseMessage = switchISOMsg.getValue(44)!=null?(String)switchISOMsg.getValue(44):null;
        
        //TODO: check response code to set declined and failed cases
        if(switchResponseCode!=null && switchResponseCode.equals(ActionCode.ERROR_CODE_00)){
          //Switch transaction id
          String issuerTxnRefNumber = switchISOMsg.getValue(37)!=null?(String)switchISOMsg.getValue(37):null;
          
          pgSwitchTransaction.setTransactionId(issuerTxnRefNumber);
          pgSwitchTransaction.setStatus(PGConstants.STATUS_SUCCESS);
          
          pgTransaction.setIssuerTxnRefNum(issuerTxnRefNumber);
          pgTransaction.setStatus(PGConstants.STATUS_SUCCESS);
        }else{
          pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
          pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        }
        pgSwitchTransaction.setTransactionId(switchISOMsg.getString(37)!=null?switchISOMsg.getString(37):null);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
      
        //Update transaction status and switch response
        voidtransactiondao.createTransaction(pgTransaction);
        
        //Set Response fields
        cashBackResponse.setTxnRefNum(txnRefNum);
        cashBackResponse.setAuthId(authId);
        cashBackResponse.setTxnAmount(cashBackRequest.getTxnAmount());
        cashBackResponse.setFeeAmount(feeAmount);
        cashBackResponse.setTotalAmount(txnTotalAmount);
        cashBackResponse.setErrorCode(switchResponseCode);
        cashBackResponse.setErrorMessage(switchResponseMessage);
        
      }catch(Exception e){
        log.error("Exception :"+ e);
        pgSwitchTransaction.setStatus(PGConstants.STATUS_FAILED);
        switchTransactionDao.createTransaction(pgSwitchTransaction);
        
        pgTransaction.setStatus(PGConstants.STATUS_FAILED);
        voidtransactiondao.createTransaction(pgTransaction);
        
        cashBackResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
        cashBackResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      }
      
      //Required to set in reversal 
      cashBackResponse.setTxnRefNum(txnRefNum);

    } catch (ServiceException e) {
      cashBackResponse.setErrorCode(e.getMessage());
      cashBackResponse.setErrorMessage(ActionCode.getInstance().getMessage(e.getMessage()));
      log.error("PaymentServiceImpl | cashBackTransaction | ServiceException :"+e.getMessage(),e);
    } catch (DataAccessException e) {
      cashBackResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      cashBackResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | cashBackTransaction | DataAccessException :"+e.getMessage(),e);
    } catch (Exception e) {
      cashBackResponse.setErrorCode(ActionCode.ERROR_CODE_Z5);
      cashBackResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z5));
      log.error("PaymentServiceImpl | cashBackTransaction | Exception :"+e.getMessage(),e);
    } 

    log.debug("PaymentServiceImpl | cashBackTransaction | Exiting");
    return cashBackResponse;
  }
  
  private boolean isValidPosEntryMode(Request request){
    
    boolean flag = true;
    String posEntryMode = request.getIsoMsg().getString(22).substring(1,4);
    
    if(posEntryMode.equals(POSEntryMode.MANUAL_WITH_PIN_UNSPECIFIED) || 
       posEntryMode.equals(POSEntryMode.MANUAL_WITH_NO_PIN) ||
       posEntryMode.equals(POSEntryMode.MANUAL_WITH_PIN) ||
       posEntryMode.equals(POSEntryMode.SWIPE_WITH_PIN_UNSPECIFIED) ||
       posEntryMode.equals(POSEntryMode.SWIPE_WITH_NO_PIN) ||
       posEntryMode.equals(POSEntryMode.SWIPE_WITH_PIN) ||
       posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN_UNSPECIFIED) ||
       posEntryMode.equals(POSEntryMode.ICC_READ_WITH_NO_PIN) ||
       posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN) ||
       posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN_UNSPECIFIED_9) ||
       posEntryMode.equals(POSEntryMode.ICC_READ_WITH_NO_PIN_9) ||
       posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN_9) ||
       posEntryMode.equals(POSEntryMode.ICC_SWIPE_WITH_PIN_UNSPECIFIED) ||
       posEntryMode.equals(POSEntryMode.ICC_SWIPE_WITH_NO_PIN) ||
       posEntryMode.equals(POSEntryMode.ICC_SWIPE_WITH_PIN)){
      flag=true;
    }else{
      return false;
    }
    
    if((posEntryMode.equals(POSEntryMode.MANUAL_WITH_PIN_UNSPECIFIED) || posEntryMode.equals(POSEntryMode.MANUAL_WITH_NO_PIN)) && request.getIsoMsg().getString(2) == null){
      flag=false;
    } else if(posEntryMode.equals(POSEntryMode.MANUAL_WITH_PIN) && (request.getIsoMsg().getString(2) == null || request.getIsoMsg().getString(52) == null)){
      flag=false;
    } else if((posEntryMode.equals(POSEntryMode.SWIPE_WITH_PIN_UNSPECIFIED) || posEntryMode.equals(POSEntryMode.SWIPE_WITH_NO_PIN)) && request.getIsoMsg().getString(35) == null){
      flag=false;
    }else if(posEntryMode.equals(POSEntryMode.SWIPE_WITH_PIN) && (request.getIsoMsg().getString(35) == null || request.getIsoMsg().getString(52) == null)){
      flag=false;
    }else if((posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN_UNSPECIFIED) || posEntryMode.equals(POSEntryMode.ICC_READ_WITH_NO_PIN)) && request.getIsoMsg().getString(55) == null){
      flag=false;
    }else if(posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN) && (request.getIsoMsg().getString(55) == null || request.getIsoMsg().getString(52) == null)){
      flag=false;
    }else if((posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN_UNSPECIFIED_9) || posEntryMode.equals(POSEntryMode.ICC_READ_WITH_NO_PIN_9)) && request.getIsoMsg().getString(55) == null){
      flag=false;
    }else if(posEntryMode.equals(POSEntryMode.ICC_READ_WITH_PIN_9) && (request.getIsoMsg().getString(55) == null || request.getIsoMsg().getString(52) == null)){
      flag=false;
    }else if((posEntryMode.equals(POSEntryMode.ICC_SWIPE_WITH_PIN_UNSPECIFIED) || posEntryMode.equals(POSEntryMode.ICC_SWIPE_WITH_NO_PIN)) && request.getIsoMsg().getString(35) == null){
      flag=false;
    }else if(posEntryMode.equals(POSEntryMode.ICC_SWIPE_WITH_PIN) && (request.getIsoMsg().getString(35) == null || request.getIsoMsg().getString(52) == null)){
      flag=false;
    }
    
    return flag;
  }
  
  
}
