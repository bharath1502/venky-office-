/**
 * 
 */
package com.chatak.switches.services;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.chatak.pg.acq.dao.model.PGSwitch;
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
import com.chatak.pg.bean.ReversalRequest;
import com.chatak.pg.bean.ReversalResponse;
import com.chatak.pg.bean.VoidRequest;
import com.chatak.pg.bean.VoidResponse;
import com.chatak.pg.constants.ActionCode;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.ISOConstants;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.enums.ProcessorType;
import com.chatak.switches.prepaid.PulseSwitchTransaction;
import com.chatak.switches.sb.SwitchTransaction;
import com.chatak.switches.sb.exception.ServiceException;
import com.chatak.switches.sb.util.SpringDAOBeanFactory;

/**
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date Jul 8, 2015 8:02:38 PM
 * @version 1.0
 */
public class PulseServiceImpl extends TransactionService implements PulseService,PaymentService {
  private static Logger log = Logger.getLogger(PulseServiceImpl.class);
  
  public PulseServiceImpl() {
    AutowireCapableBeanFactory acbFactory = SpringDAOBeanFactory.getSpringContext().getAutowireCapableBeanFactory();
    acbFactory.autowireBean(this);
  }

  /**
   * @param networkRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public NetworkResponse networkTransaction(NetworkRequest networkRequest) throws ServiceException {

    log.info("PusleServiceImpl | authTransaction | Entering");
    NetworkResponse networkResponse = new NetworkResponse();

    try {
      if("071".equals(networkRequest.getNetworkManagementCode())
         || "072".equals(networkRequest.getNetworkManagementCode())
         || "371".equals(networkRequest.getNetworkManagementCode())) {
        SwitchTransaction switchTransaction = new PulseSwitchTransaction();
        PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
        switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
        ISOMsg switchISOMsg = switchTransaction.network(getNetworkIsoMsg("0800",
                                                                         networkRequest.getNetworkManagementCode(),
                                                                         "49149",
                                                                         txnRefNum));// 071
                                                                                     // sign
                                                                                     // on
                                                                                     // as
                                                                                     // acq
                                                                                     // 072
                                                                                     // sign
                                                                                     // off
                                                                                     // as
                                                                                     // acq
                                                                                     // 371
                                                                                     // acq
                                                                                     // echo
                                                                                     // test
        String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
        String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
        networkResponse.setTxnResponseTime(System.currentTimeMillis());
        networkResponse.setUpStreamMessage(switchResponseMessage);

        if(switchResponseCode != null) {
          networkResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
              : PGConstants.STATUS_FAILED);
        }

        if(networkResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
          networkResponse.setUpStreamAuthCode(authId);
          networkResponse.setUpStreamTxnRefNum(txnRefNum);
        }
        networkResponse.setErrorCode(switchResponseCode);
        networkResponse.setErrorMessage(switchResponseMessage);
      }
      else {
        log.error("ERROR:: PusleServiceImpl:: authTransaction method:invalid network management code");
        throw new ServiceException(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z9));
      }

    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: authTransaction method" + e);
      networkResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      networkResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      networkResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      networkResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }

    log.info("PusleServiceImpl | authTransaction | Exiting");
    return networkResponse;
  }

  /**
   * @return
   */
  @Override
  public String getProcessor() {
      return ProcessorType.PULSE.value();
  }

  /**
   * @param authRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public AuthResponse authTransaction(AuthRequest authRequest) throws ServiceException {
    log.info("PusleServiceImpl | authTransaction | Entering");
    AuthResponse authResponse = new AuthResponse();

    try {

      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.financial(getISOMsg(authRequest, "0100", "001010", txnRefNum));

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      authResponse.setTxnResponseTime(System.currentTimeMillis());
      authResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        authResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED);
      }

      if(authResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        authResponse.setUpStreamAuthCode(authId);
        authResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      authResponse.setErrorCode(switchResponseCode);
      authResponse.setErrorMessage(switchResponseMessage);

    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: authTransaction method", e);
      authResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      authResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      authResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      authResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }

    log.info("PusleServiceImpl | authTransaction | Exiting");
    return authResponse;
  }

  /**
   * @param captureRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public CaptureResponse captureTransaction(CaptureRequest captureRequest) throws ServiceException {
    log.info("PusleServiceImpl | captureTransaction | Entering");
    CaptureResponse captureResponse = new CaptureResponse();

    try {

      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.financial(getISOMsg(captureRequest, "0220", "001010", txnRefNum));

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      captureResponse.setTxnResponseTime(System.currentTimeMillis());
      captureResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        captureResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED);
      }
      if(captureResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        captureResponse.setUpStreamAuthCode(authId);
        captureResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      captureResponse.setErrorCode(switchResponseCode);
      captureResponse.setErrorMessage(switchResponseMessage);

    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: captureTransaction method", e);
      captureResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      captureResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      captureResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      captureResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }

    log.info("PusleServiceImpl | captureTransaction | Exiting");
    return captureResponse;
  }

  /**
   * @param purchaseRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public PurchaseResponse purchaseTransaction(PurchaseRequest purchaseRequest) throws ServiceException {
    log.info("PusleServiceImpl | purchaseTransaction | Entering");
    PurchaseResponse purchaseResponse = new PurchaseResponse();

    try {

      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.financial(getISOMsg(purchaseRequest, "0200", "001010", txnRefNum));

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      purchaseResponse.setTxnResponseTime(System.currentTimeMillis());
      purchaseResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        purchaseResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED));
      }
      if(purchaseResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        purchaseResponse.setUpStreamAuthCode(authId);
        purchaseResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      purchaseResponse.setErrorCode(switchResponseCode);
      purchaseResponse.setErrorMessage(switchResponseMessage);

    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: purchaseTransaction method", e);
      purchaseResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      purchaseResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      purchaseResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      purchaseResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }

    log.info("PusleServiceImpl | purchaseTransaction | Exiting");
    return purchaseResponse;

  }

  /**
   * @param adjustmentRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public AdjustmentResponse adjustmentTransaction(AdjustmentRequest adjustmentRequest) throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @param voidRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public VoidResponse voidTransaction(VoidRequest voidRequest) throws ServiceException {
    log.info("PusleServiceImpl | voidTransaction | Entering");
    VoidResponse voidResponse = new VoidResponse();

    try {

      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.reversalAdvice(getISOMsg(voidRequest,
                                                                       "0420",
                                                                       "021010",
                                                                       voidRequest.getIssuerTxnRefNum()));

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      voidResponse.setTxnResponseTime(System.currentTimeMillis());
      voidResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        voidResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED);
      }
      if(voidResponse.getUpStreamStatus().equals(PGConstants.SUCCESS)) {
        voidResponse.setUpStreamAuthCode(authId);
        voidResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      voidResponse.setErrorCode(switchResponseCode);
      voidResponse.setErrorMessage(switchResponseMessage);

    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: voidTransaction method", e);
      voidResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      voidResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      voidResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      voidResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }

    log.info("PusleServiceImpl | voidTransaction | Exiting");
    return voidResponse;
  }

  /**
   * @param reversalRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public ReversalResponse reversalTransaction(ReversalRequest reversalRequest) throws ServiceException {
    log.info("PusleServiceImpl | reversalTransaction | Entering");
    ReversalResponse reversalResponse = new ReversalResponse();
    try {

      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      ISOMsg switchISOMsg = switchTransaction.reversalAdvice(getISOMsg(reversalRequest,
                                                                       "0420",
                                                                       "021010",
                                                                       reversalRequest.getIssuerTxnRefNum()));

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      reversalResponse.setTxnResponseTime(System.currentTimeMillis());
      reversalResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        reversalResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED);
      }
      if(reversalResponse.getUpStreamStatus().equals(PGConstants.SUCCESS)) {
        reversalResponse.setUpStreamAuthCode(authId);
        reversalResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      reversalResponse.setErrorCode(switchResponseCode);
      reversalResponse.setErrorMessage(switchResponseMessage);

    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: reversalTransaction method", e);
      reversalResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      reversalResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      reversalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      reversalResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }
    log.info("PusleServiceImpl | reversalTransaction | Exiting");
    return reversalResponse;
  }

  /**
   * @param refundRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public RefundResponse refundTransaction(RefundRequest refundRequest) throws ServiceException {
    log.info("PusleServiceImpl | refundTransaction | Entering");
    RefundResponse refundResponse = new RefundResponse();

    try {

      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));

      ISOMsg switchISOMsg = switchTransaction.financial(getISOMsg(refundRequest,
                                                                  "0200",
                                                                  "201010",
                                                                  refundRequest.getIssuerTxnRefNum()));

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      refundResponse.setTxnResponseTime(System.currentTimeMillis());
      refundResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        refundResponse.setUpStreamStatus(switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED);
      }
      if(refundResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        refundResponse.setUpStreamAuthCode(authId);
        refundResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      refundResponse.setErrorCode(switchResponseCode);
      refundResponse.setErrorMessage(switchResponseMessage);
    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: refundTransaction method", e);
      refundResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      refundResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      refundResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      refundResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }

    log.info("PusleServiceImpl | refundTransaction | Exiting");
    return refundResponse;
  }

  /**
   * @param balanceEnquiryRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public BalanceEnquiryResponse balanceEnquiryTransaction(Request balanceEnquiryRequest) throws ServiceException {
    log.debug("PusleServiceImpl | balanceEnquiryTransaction | Entering");
    BalanceEnquiryResponse balanceEnquiryResponse = new BalanceEnquiryResponse();

    ISOMsg balanceEnquiryIsoMsg = new ISOMsg();

    try {
      // validation of Request
      validateRequest(balanceEnquiryRequest);
      // Switch interface call
      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      balanceEnquiryIsoMsg = getBalanceEnquiryISOMsg(balanceEnquiryRequest, balanceEnquiryRequest.getIsoMsg());
      ISOMsg switchISOMsg = switchTransaction.authAdvice(balanceEnquiryIsoMsg);
      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      balanceEnquiryResponse.setTxnResponseTime(System.currentTimeMillis());
      balanceEnquiryResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        balanceEnquiryResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED));
      }
      if(balanceEnquiryResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        balanceEnquiryResponse.setBalance((String) switchISOMsg.getValue(Integer.parseInt("54")));
        balanceEnquiryResponse.setUpStreamAuthCode(authId);
        balanceEnquiryResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      balanceEnquiryResponse.setErrorCode(switchResponseCode);
      balanceEnquiryResponse.setErrorMessage(switchResponseMessage);
    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: balanceEnquiryTransaction method", e);
      balanceEnquiryResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      balanceEnquiryResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      balanceEnquiryResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      balanceEnquiryResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }

    return balanceEnquiryResponse;
  }

  /**
   * @param cashWithdrawalRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public CashWithdrawalResponse cashWithdrawalTransaction(CashWithdrawalRequest cashWithdrawalRequest) throws ServiceException {
    log.debug("PusleServiceImpl | cashWithdrawalTransaction | Entering");
    CashWithdrawalResponse cashWithdrawalResponse = new CashWithdrawalResponse();
    ISOMsg isoMsg = new ISOMsg();

    try {
      // validation of Request
      validateRequest(cashWithdrawalRequest);
      // Switch interface call
      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      isoMsg = getISOMsg(cashWithdrawalRequest.getIsoMsg());
      ISOMsg switchISOMsg = switchTransaction.financial(isoMsg);

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      cashWithdrawalResponse.setTxnResponseTime(System.currentTimeMillis());
      cashWithdrawalResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        cashWithdrawalResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED));
      }
      if(cashWithdrawalResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        cashWithdrawalResponse.setUpStreamAuthCode(authId);
        cashWithdrawalResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      cashWithdrawalResponse.setErrorCode(switchResponseCode);
      cashWithdrawalResponse.setErrorMessage(switchResponseMessage);
    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: cashWithdrawalTransaction method", e);
      cashWithdrawalResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      cashWithdrawalResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      cashWithdrawalResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      cashWithdrawalResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }
    log.debug("PusleServiceImpl | cashWithdrawalTransaction | Exiting");
    return cashWithdrawalResponse;
  }

  /**
   * @param cashBackRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public CashBackResponse cashBackTransaction(CashBackRequest cashBackRequest) throws ServiceException {
    log.debug("PusleServiceImpl | cashBackTransaction | Entering");
    CashBackResponse cashBackResponse = new CashBackResponse();
    ISOMsg isoMsg = new ISOMsg();
    try {
      // validation of Request
      validateRequest(cashBackRequest);
      // Switch interface call
      SwitchTransaction switchTransaction = new PulseSwitchTransaction();
      PGSwitch pgSwitch = switchDao.getSwitchByName(ProcessorType.PULSE.value());
      switchTransaction.initConfig(pgSwitch.getPrimarySwitchURL(), Integer.valueOf(pgSwitch.getPrimarySwitchPort()));
      isoMsg = getISOMsg(cashBackRequest.getIsoMsg());
      ISOMsg switchISOMsg = switchTransaction.financial(isoMsg);

      String switchResponseCode = switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) != null ? (String) switchISOMsg.getValue(ISOConstants.RESPONSE_CODE) : null;
      String switchResponseMessage = ActionCode.getInstance().getMessage(switchResponseCode);
      cashBackResponse.setTxnResponseTime(System.currentTimeMillis());
      cashBackResponse.setUpStreamMessage(switchResponseMessage);

      if(switchResponseCode != null) {
        cashBackResponse.setUpStreamStatus((switchResponseCode.equals(PGConstants.SUCCESS) ? PGConstants.STATUS_SUCCESS
            : PGConstants.STATUS_FAILED));
      }
      if(cashBackResponse.getUpStreamStatus().equals(PGConstants.STATUS_SUCCESS)) {
        cashBackResponse.setUpStreamAuthCode(authId);
        cashBackResponse.setUpStreamTxnRefNum(txnRefNum);
      }
      cashBackResponse.setErrorCode(switchResponseCode);
      cashBackResponse.setErrorMessage(switchResponseMessage);
    }
    catch(Exception e) {
      log.error("ERROR:: PusleServiceImpl:: cashWithdrawalTransaction method", e);
      cashBackResponse.setUpStreamStatus(PGConstants.STATUS_FAILED);
      cashBackResponse.setErrorCode(ActionCode.ERROR_CODE_Z7);
      cashBackResponse.setErrorMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
      cashBackResponse.setUpStreamMessage(ActionCode.getInstance().getMessage(ActionCode.ERROR_CODE_Z7));
    }
    log.debug("PusleServiceImpl | cashBackTransaction | Exiting");
    return cashBackResponse;
  }

  /**
   * @param captureRequest
   * @return
   * @throws ServiceException
   */
  @Override
  public CaptureResponse partialCaptureTransaction(CaptureRequest captureRequest) throws ServiceException {
    // TODO Auto-generated method stub
    return null;
  }

}
