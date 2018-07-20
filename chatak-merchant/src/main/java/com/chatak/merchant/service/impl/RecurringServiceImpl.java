package com.chatak.merchant.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.merchant.service.RecurringService;
import com.chatak.pg.acq.dao.CountryDao;
import com.chatak.pg.acq.dao.MerchantProfileDao;
import com.chatak.pg.acq.dao.RecurringServicesDao;
import com.chatak.pg.acq.dao.model.PGState;
import com.chatak.pg.acq.dao.model.RecurringContractInfo;
import com.chatak.pg.acq.dao.model.RecurringCustomerInfo;
import com.chatak.pg.acq.dao.model.RecurringPaymentInfo;
import com.chatak.pg.bean.CountryRequest;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.Status;
import com.chatak.pg.model.RecurringContractInfoDTO;
import com.chatak.pg.model.RecurringCustomerInfoDTO;
import com.chatak.pg.model.RecurringPaymentInfoDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

@Service
public class RecurringServiceImpl implements RecurringService {

  private final String className = RecurringServiceImpl.class.getSimpleName();

  private static Logger logger = Logger.getLogger(RecurringServiceImpl.class);

  @Autowired
  RecurringServicesDao recurringServicesDao;

  @Autowired
  private CountryDao countryDao;

  @Autowired
  MerchantProfileDao merchantProfileDao;

  @Override
  @Transactional(rollbackFor = ChatakPayException.class)
  public RecurringCustomerInfoDTO createCustomerInfo(
      RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws ChatakPayException {
    final String methodName = "createCustomerInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {
      RecurringCustomerInfo recurringCustomerInfo = null;
      recurringCustomerInfo =
          CommonUtil.copyBeanProperties(recurringCustomerInfoDTO, RecurringCustomerInfo.class);

      recurringCustomerInfo.setCustomerId(generateCustomerId(recurringCustomerInfo));
      recurringCustomerInfo =
          recurringServicesDao.saveOrUpdateRecurringCustomerInfo(recurringCustomerInfo);
      recurringCustomerInfoDTO.setCustomerId(recurringCustomerInfo.getCustomerId());
      recurringCustomerInfoDTO
          .setRecurringCustInfoId(recurringCustomerInfo.getRecurringCustInfoId());
      recurringCustomerInfoDTO.setTermsFlag(recurringCustomerInfo.getTermsFlag());
    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while creating customer Info: "
          + recurringCustomerInfoDTO.getFirstName(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
    return recurringCustomerInfoDTO;
  }

  public RecurringCustomerInfoDTO updateCustomerInfo(
      RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws ChatakPayException {
    final String methodName = "updateCustomerInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {
      RecurringCustomerInfo recurringCustomerInfo = null;

      recurringCustomerInfo = recurringServicesDao
          .findByRecurringCustomerInfoId(recurringCustomerInfoDTO.getRecurringCustInfoId());
      if (recurringCustomerInfo != null
          && Status.INACTIVE.equals(recurringCustomerInfo.getStatus())) {
        throw new ChatakPayException();
      }

      recurringCustomerInfo =
          CommonUtil.copyBeanProperties(recurringCustomerInfoDTO, RecurringCustomerInfo.class);
      recurringCustomerInfo =
          recurringServicesDao.saveOrUpdateRecurringCustomerInfo(recurringCustomerInfo);
      recurringCustomerInfoDTO.setCustomerId(recurringCustomerInfo.getCustomerId());
      recurringCustomerInfoDTO
          .setRecurringCustInfoId(recurringCustomerInfo.getRecurringCustInfoId());
    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while updating customer Info: "
          + recurringCustomerInfoDTO.getFirstName(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
    return recurringCustomerInfoDTO;
  }

  @Override
  public List<RecurringCustomerInfoDTO> searchCustomerInfo(
      RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws ChatakPayException {
    final String methodName = "searchCustomerInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      return recurringServicesDao.searchCustomerInfo(recurringCustomerInfoDTO);

    } catch (Exception ex) {
      logger.error(className + methodName + Constants.EXCEPTION_OCCURED_WHILE_SEARCHING_CUSTOMER_INFO,
          ex);
      throw new ChatakPayException();
    }
  }

  @Override
  public RecurringCustomerInfoDTO searchCustomerInfoById(
      RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws ChatakPayException {
    final String methodName = "searchCustomerInfoById";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    RecurringCustomerInfo recurringCustomerInfo = null;
    try {

      recurringCustomerInfo = recurringServicesDao
          .findByRecurringCustomerInfoId(recurringCustomerInfoDTO.getRecurringCustInfoId());
     CommonUtil.copyBeanProperties(recurringCustomerInfo, RecurringCustomerInfoDTO.class);
      return recurringCustomerInfoDTO;
    } catch (Exception ex) {
      logger.error(className + methodName + Constants.EXCEPTION_OCCURED_WHILE_SEARCHING_CUSTOMER_INFO,
          ex);
      throw new ChatakPayException();
    }
  }

  @Override
  public RecurringPaymentInfoDTO createPaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO)
      throws ChatakPayException {
    final String methodName = "createPaymentInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      RecurringPaymentInfo recurringPaymentInfo = null;
      recurringPaymentInfoDTO.setStatus(Status.ACTIVE.name());
      recurringPaymentInfo =
          CommonUtil.copyBeanProperties(recurringPaymentInfoDTO, RecurringPaymentInfo.class);
      recurringPaymentInfo.setCardNumber(recurringPaymentInfo.getCardNumber());//removing masked card for 12-19 digit accepting
      recurringPaymentInfo =
          recurringServicesDao.saveOrUpdateRecurringPaymentInfo(recurringPaymentInfo);

      recurringPaymentInfoDTO
          .setRecurringPaymentInfoId(recurringPaymentInfo.getRecurringPaymentInfoId());
      recurringPaymentInfoDTO.setNameOnCard(recurringPaymentInfo.getNameOnCard());
    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while creating payment Info: "
          + recurringPaymentInfoDTO.getNameOnCard(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
    return recurringPaymentInfoDTO;
  }

  public List<RecurringPaymentInfoDTO> searchPaymentInfoByCustomerId(
      RecurringPaymentInfoDTO recurringPaymentInfoDTO) throws ChatakPayException {
    final String methodName = "searchPaymentInfoById";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      return recurringServicesDao.searchPaymentInfo(recurringPaymentInfoDTO);
    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while searchPaymentInfo: ", ex);
      throw new ChatakPayException();
    }
  }

  @Override
  public RecurringPaymentInfoDTO searchPaymentInfoById(
      RecurringPaymentInfoDTO recurringPaymentInfoDTO) throws ChatakPayException {
    final String methodName = "searchPaymentInfoById";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      RecurringPaymentInfo recurringPaymentInfo = null;
      recurringPaymentInfo = recurringServicesDao
          .findByRecurringPaymentInfoId(recurringPaymentInfoDTO.getRecurringPaymentInfoId());
    CommonUtil.copyBeanProperties(recurringPaymentInfo, RecurringPaymentInfoDTO.class);

    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while searchPaymentInfoById: "
          + recurringPaymentInfoDTO.getRecurringPaymentInfoId(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
    return recurringPaymentInfoDTO;
  }

  @Override
  public void updatePaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO)
      throws ChatakPayException {
    final String methodName = "updatePaymentInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      RecurringPaymentInfo recurringPaymentInfo = null;
      recurringPaymentInfo = recurringServicesDao
          .findByRecurringPaymentInfoId(recurringPaymentInfoDTO.getRecurringPaymentInfoId());
      if (Status.INACTIVE.name().equals(recurringPaymentInfo.getStatus())) {
        throw new ChatakPayException();
      }
      recurringPaymentInfo =
          CommonUtil.copyBeanProperties(recurringPaymentInfoDTO, RecurringPaymentInfo.class);
      recurringServicesDao.saveOrUpdateRecurringPaymentInfo(recurringPaymentInfo);

    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while creating payment Info: "
          + recurringPaymentInfoDTO.getNameOnCard(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
  }

  @Override
  public void deletePaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO)
      throws ChatakPayException {
    final String methodName = "deletePaymentInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      RecurringPaymentInfo paymentInfo = recurringServicesDao
          .findByRecurringPaymentInfoId(recurringPaymentInfoDTO.getRecurringPaymentInfoId());

      if (!Status.ACTIVE.name().equals(paymentInfo.getStatus())) {
        throw new ChatakPayException();
      }
      paymentInfo.setUpdatedBy(recurringPaymentInfoDTO.getUpdatedBy());
      paymentInfo.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      paymentInfo.setStatus(Status.INACTIVE.name());
      recurringServicesDao.saveOrUpdateRecurringPaymentInfo(paymentInfo);

    } catch (Exception ex) {
      logger.error(className + methodName + Constants.EXCEPTION_OCCURED_WHILE_DELETING_CUSTOMER_INFO
          + recurringPaymentInfoDTO.getRecurringPaymentInfoId(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
  }

  @Override
  @Transactional(rollbackFor = ChatakPayException.class)
  public void createContractInfo(RecurringContractInfoDTO recurringContractInfoDTO)
      throws ChatakPayException {
    final String methodName = "createContractInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    RecurringContractInfo recurringContractInfo = null;
    RecurringPaymentInfo recurringPaymentInfo = null;
    try {

      recurringPaymentInfo = recurringServicesDao
          .findByRecurringPaymentInfoId(recurringContractInfoDTO.getRecurringpaymentInfoId());

      // validate the card status.
      if (recurringPaymentInfo != null       
    		  && Status.INACTIVE.name().equalsIgnoreCase(recurringPaymentInfo.getStatus())) {
          throw new ChatakMerchantException("payment information is null");
      } else if ((recurringPaymentInfo != null && Status.ACTIVE.name().equals(recurringPaymentInfo.getStatus()))) {
        validateContractEndDateWithCardExpDate(recurringContractInfoDTO, recurringPaymentInfo);

        recurringContractInfo =
            CommonUtil.copyBeanProperties(recurringContractInfoDTO, RecurringContractInfo.class);
        recurringContractInfo.setStartDate(
            DateUtil.toTimestamp(recurringContractInfoDTO.getStartDate(), Constants.MM_DD_YYYY));
        recurringContractInfo
            .setEndDate(DateUtil.toTimestamp(recurringContractInfoDTO.getEndDate(), Constants.MM_DD_YYYY));

        recurringContractInfo.setLastBillDate(
            DateUtil.toTimestamp(recurringContractInfoDTO.getStartDate(), Constants.MM_DD_YYYY));

        recurringContractInfo
            .setRecurringPaymentInfoId(recurringPaymentInfo.getRecurringPaymentInfoId());
        recurringContractInfo =
            recurringServicesDao.saveOrUpdateRecurringContractInfo(recurringContractInfo);
        // Generating the contract Id
        recurringContractInfo.setContractId(generateContractId(recurringContractInfo));
        recurringServicesDao.saveOrUpdateRecurringContractInfo(recurringContractInfo);
      }
    } catch (Exception ex) {
      recurringPaymentInfo = new RecurringPaymentInfo();
      recurringContractInfoDTO
          .setRecurringpaymentInfoId(recurringPaymentInfo.getRecurringPaymentInfoId());
      recurringContractInfoDTO.setNameOnCard(recurringPaymentInfo.getNameOnCard());
      logger.error(className + methodName + "Exception Occured while creating the contract Info : "
          + recurringContractInfoDTO.getContractName(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
  }

  /**
   * @param recurringCustomerInfo
   * @return
   */
  private String generateCustomerId(RecurringCustomerInfo recurringCustomerInfo) {
    String firstName = recurringCustomerInfo.getFirstName();
    String zipCode = recurringCustomerInfo.getZipCode();
    firstName = firstName.substring(0, Math.min(firstName.length(), Constants.THREE));
    zipCode = zipCode.substring(0, Math.min(zipCode.length(), Constants.THREE));
    String custId = firstName + zipCode + CommonUtil.generateRandNumeric(Constants.SIX);
    if (recurringServicesDao.findByCustomerId(custId) == null) {
      return custId;
    } else {
      return generateCustomerId(recurringCustomerInfo);
    }

  }

  /**
   * @param recurringContractInfo
   * @return
   */
  private String generateContractId(RecurringContractInfo recurringContractInfo) {
    String contractName = recurringContractInfo.getContractName();
    Long paymentInfoId = recurringContractInfo.getRecurringPaymentInfoId();
    Long id = recurringContractInfo.getRecurringContractInfoId();
    contractName = contractName.substring(0, Math.min(contractName.length(), Constants.THREE));
    return contractName + paymentInfoId + id;
  }

  /**
   * @param recurringContractInfoDTO
   * @param recurringPaymentInfo
   * @throws ChatakPayException
   */
  private void validateContractEndDateWithCardExpDate(
      RecurringContractInfoDTO recurringContractInfoDTO, RecurringPaymentInfo recurringPaymentInfo)
          throws ChatakPayException {
    final String methodName = "validateContractEndDateWithCardExpDate";
    logger.info(Constants.ENTERING + className + methodName);
    String endDate = recurringContractInfoDTO.getEndDate();
    String cardExpDate = recurringPaymentInfo.getExpDt();
    Calendar calendar = Calendar.getInstance();

    if (cardExpDate == null) {
      throw new ChatakPayException();
    }

    Timestamp contractEndDate = DateUtil.toTimestamp(endDate, Constants.MM_DD_YYYY);
    calendar.set(Calendar.YEAR, Integer.valueOf(cardExpDate.split("/")[1]));
    calendar.set(Calendar.MONTH, Integer.valueOf(cardExpDate.split("/")[0]) - 1);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    Timestamp expDate = new java.sql.Timestamp(calendar.getTimeInMillis());
    // Validating the contract end date against to card exp date, Contract end
    // date should not be grater than the card exp date.
    if (contractEndDate.after(expDate)) {
      // Need to define the error code.
      recurringContractInfoDTO.setErrorCode(Constants.ERROR_CODE);
      throw new ChatakPayException();
    }
  }

  @Override
  public void deleteContractInfo(RecurringContractInfoDTO recurringContractInfoDTO)
      throws ChatakPayException {
    final String methodName = "deleteContractInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      RecurringContractInfo contractInfo = recurringServicesDao
          .findByRecurringContractInfoId(recurringContractInfoDTO.getRecurringContractInfoId());

      if (!Status.ACTIVE.name().equals(contractInfo.getStatus())) {
        throw new ChatakPayException();
      }
      contractInfo.setUpdatedBy(recurringContractInfoDTO.getUpdatedBy());
      contractInfo.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      contractInfo.setStatus(Status.INACTIVE.name());
      recurringServicesDao.saveOrUpdateRecurringContractInfo(contractInfo);

    } catch (Exception ex) {
      logger.error(className + methodName + Constants.EXCEPTION_OCCURED_WHILE_DELETING_CUSTOMER_INFO
          + recurringContractInfoDTO.getRecurringContractInfoId(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
  }

  public List<RecurringContractInfoDTO> searchContractInfoByCustomerInfoId(
      RecurringContractInfoDTO recurringContractInfoDTO, Long customerId)
          throws ChatakPayException {
    final String methodName = "searchCustomerInfoById";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    List<RecurringContractInfoDTO> contractList = new ArrayList<>();
    try {
      recurringContractInfoDTO.setRecurringCustInfoId(customerId);
      contractList =
          recurringServicesDao.searchContractInfoByCustomerInfoId(recurringContractInfoDTO);

      for (RecurringContractInfoDTO contractInfoDTO : contractList) {
        if (contractInfoDTO.getContractExecution().equals("Daily")) {
          Timestamp date = null;
          date = DateUtil.toTimestamp(contractInfoDTO.getLastBillDate(), Constants.DD_MM_YYYY);

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.DATE, 1);
          date.setTime(calendar.getTime().getTime());
          contractInfoDTO.setNextBilldate(date.toString());
        } else if (contractInfoDTO.getContractExecution().equals("Weekly")) {
          Timestamp date = null;
          date = DateUtil.toTimestamp(contractInfoDTO.getLastBillDate(), Constants.DD_MM_YYYY);

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.DATE, Constants.SEVEN);
          date.setTime(calendar.getTime().getTime());
          contractInfoDTO.setNextBilldate(date.toString());
        } else if (contractInfoDTO.getContractExecution().equals("Bi-Weekly")) {
          Timestamp date = null;
          date = DateUtil.toTimestamp(contractInfoDTO.getLastBillDate(), Constants.DD_MM_YYYY);

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.DATE, Constants.FOURTEEN);
          date.setTime(calendar.getTime().getTime());
          contractInfoDTO.setNextBilldate(date.toString());
        } else if (contractInfoDTO.getContractExecution().equals("Monthly")) {
          Timestamp date = null;
          date = DateUtil.toTimestamp(contractInfoDTO.getLastBillDate(), Constants.DD_MM_YYYY);

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.MONTH, 1);
          date.setTime(calendar.getTime().getTime());
          contractInfoDTO.setNextBilldate(date.toString());
        } else if (contractInfoDTO.getContractExecution().equals("Quarterly")) {
          Timestamp date = null;
          date = DateUtil.toTimestamp(contractInfoDTO.getLastBillDate(), Constants.DD_MM_YYYY);

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.MONTH, Constants.FOUR);
          date.setTime(calendar.getTime().getTime());
          contractInfoDTO.setNextBilldate(date.toString());
        } else if (contractInfoDTO.getContractExecution().equals("Annually")) {
          Timestamp date = null;
          date = DateUtil.toTimestamp(contractInfoDTO.getLastBillDate(), Constants.DD_MM_YYYY);

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          calendar.add(Calendar.YEAR, 1);
          date.setTime(calendar.getTime().getTime());
          contractInfoDTO.setNextBilldate(date.toString());
        }
      }
      return contractList;
    } catch (Exception ex) {
      logger.error(className + methodName + Constants.EXCEPTION_OCCURED_WHILE_SEARCHING_CUSTOMER_INFO,
          ex);
      throw new ChatakPayException();
    }

  }

  public RecurringContractInfoDTO updateContractInfo(
      RecurringContractInfoDTO recurringContractInfoDTO) throws ChatakPayException {
    final String methodName = "createCustomerInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {
      RecurringContractInfo recurringContractInfo = null;

      recurringContractInfo = recurringServicesDao
          .findByRecurringContractInfoId(recurringContractInfoDTO.getRecurringContractInfoId());
      if (recurringContractInfo != null
          && Status.INACTIVE.equals(recurringContractInfo.getStatus())) {
        throw new ChatakPayException();
      }
      if (recurringContractInfo != null) {
        recurringContractInfoDTO.setContractId(recurringContractInfo.getContractId());
        recurringContractInfoDTO
            .setRecurringpaymentInfoId(recurringContractInfo.getRecurringPaymentInfoId());
        recurringContractInfoDTO.setCreatedBy(recurringContractInfo.getCreatedBy());
        recurringContractInfoDTO.setCreatedDate(recurringContractInfo.getCreatedDate());
        recurringContractInfo
            .setRecurringContractInfoId(recurringContractInfo.getRecurringContractInfoId());

        recurringContractInfo =
            CommonUtil.copyBeanProperties(recurringContractInfoDTO, RecurringContractInfo.class);
        recurringContractInfo.setStartDate(
            DateUtil.toTimestamp(recurringContractInfoDTO.getStartDate(), Constants.DD_MM_YYYY));
        recurringContractInfo.setEndDate(
            DateUtil.toTimestamp(recurringContractInfoDTO.getEndDate(), Constants.DD_MM_YYYY));
        recurringContractInfo
            .setRecurringPaymentInfoId(recurringContractInfoDTO.getRecurringpaymentInfoId());
        recurringContractInfo.setLastBillDate(
            DateUtil.toTimestamp(recurringContractInfoDTO.getLastBillDate(), Constants.DD_MM_YYYY));
      }
      recurringServicesDao.saveOrUpdateRecurringContractInfo(recurringContractInfo);

    } catch (ChatakPayException chpex) {
      logger.error(
          className + methodName + "ChatakPayException occured while creating customer Info: "
              + recurringContractInfoDTO.getContractName() + "" + chpex);
      throw new ChatakPayException();
    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while creating customer Info: "
          + recurringContractInfoDTO.getContractName(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
    return recurringContractInfoDTO;
  }

  @Override
  public void deleteCustomerInfo(RecurringCustomerInfoDTO recurringCustomerInfoDTO)
      throws ChatakPayException {
    final String methodName = "deleteCustomerInfo";
    logger.info(Constants.ENTERING + className + " : " + methodName);
    try {

      RecurringCustomerInfo customerInfo = recurringServicesDao
          .findByRecurringCustomerInfoId(recurringCustomerInfoDTO.getRecurringCustInfoId());

      if (!Status.ACTIVE.name().equals(customerInfo.getStatus())) {
        throw new ChatakPayException();
      }
      customerInfo.setUpdatedBy(recurringCustomerInfoDTO.getUpdatedBy());
      customerInfo.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      customerInfo.setStatus(Status.INACTIVE.name());
      recurringServicesDao.saveOrUpdateRecurringCustomerInfo(customerInfo);

    } catch (Exception ex) {
      logger.error(className + methodName + Constants.EXCEPTION_OCCURED_WHILE_DELETING_CUSTOMER_INFO
          + recurringCustomerInfoDTO.getRecurringCustInfoId(), ex);
      throw new ChatakPayException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);

  }

  @Override
  public RecurringContractInfoDTO serachContractInfoByContractId(Long contractInfoId, String status)
      throws ChatakMerchantException {

    final String methodName = "serachContractInfoByContractId";
    logger.info(Constants.ENTERING + className + " : " + methodName);

    RecurringContractInfoDTO recurringContractInfoDTO = new RecurringContractInfoDTO();
    try {

      recurringContractInfoDTO =
          recurringServicesDao.searchContractInfoByContractInfoIdCon(contractInfoId, status);

    } catch (Exception ex) {
      logger.error(className + methodName + "Exception occured while searching Contract Info: ",
          ex);
      throw new ChatakMerchantException();
    }
    logger.info(Constants.EXITING + className + " : " + methodName);
    return recurringContractInfoDTO;

  }

  /**
   * @param countryId
   * @return
   * @throws ChatakMerchantException
   */
  @Override
  public Response getStatesByCountry(String countryId) throws ChatakMerchantException {
    Response response = new Response();
    CountryRequest countryRequest = countryDao.getCountryByName(countryId);
    if (null != countryRequest.getName()) {

      List<PGState> pgStates = merchantProfileDao.getStateByCountryId(countryRequest.getId());

      List<Option> options = new ArrayList<>(pgStates.size());
      for (PGState state : pgStates) {
        Option option = new Option();
        option.setLabel(state.getName());
        option.setValue(state.getName());
        options.add(option);
      }
      Collections.sort(options, alphabeticalOrder);
      response.setResponseList(options);
      response.setErrorMessage("SUCCESS");
      response.setErrorCode("00");
      response.setTotalNoOfRows(options.size());

    } else {
      response.setErrorCode("99");
      response.setErrorMessage("failure");
    }
    return response;
  }

  @Override
  public List<Option> getCountries() {
    List<CountryRequest> countryRequests = countryDao.findAllCountries();
    List<Option> options = new ArrayList<>();
    if (null != countryRequests) {
      for (CountryRequest countryRequest : countryRequests) {
        Option option = new Option();
        option.setValue(countryRequest.getName());
        option.setLabel(countryRequest.getName());
        options.add(option);
      }
    }
    Collections.sort(options, alphabeticalOrder);
    return options;
  }

  /**
   * Comparator method for option class
   */
  private static Comparator<Option> alphabeticalOrder = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (0 == res) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });

  @Override
  public Response validateEmailId(String emailId) throws ChatakMerchantException {
    return recurringServicesDao.getUserByEmailId(emailId);
  }

  @Override
  public Response validateUpdateEmailId(String emailId, String customerId)
      throws ChatakMerchantException {
    return recurringServicesDao.getUserByUpdateEmailId(emailId, customerId);
  }

}
