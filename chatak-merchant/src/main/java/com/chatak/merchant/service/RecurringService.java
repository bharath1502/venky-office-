package com.chatak.merchant.service;

import java.util.List;

import com.chatak.merchant.controller.model.Option;
import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.exception.ChatakPayException;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.RecurringContractInfoDTO;
import com.chatak.pg.model.RecurringCustomerInfoDTO;
import com.chatak.pg.model.RecurringPaymentInfoDTO;

public interface RecurringService {
  
  // CRUD for the Customer starts here
  /**
   * @param recurringCustomerInfoDTO
   * @throws ChatakPayException
   */
  public RecurringCustomerInfoDTO createCustomerInfo(RecurringCustomerInfoDTO recurringCustomerInfoDTO)throws ChatakPayException;
  
  
  /**
   * @param recurringCustomerInfoDTO
   * @throws ChatakPayException
   */
  public RecurringCustomerInfoDTO updateCustomerInfo(RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws ChatakPayException;
  
  
  /**
   * @param recurringCustomerInfoDTO
   * @throws ChatakPayException
   */
  public List<RecurringCustomerInfoDTO> searchCustomerInfo(RecurringCustomerInfoDTO recurringCustomerInfoDTO)throws ChatakPayException;
  
  /**
   * @param recurringCustomerInfoDTO
   * @return
   * @throws ChatakPayException
   */
  public RecurringCustomerInfoDTO searchCustomerInfoById(RecurringCustomerInfoDTO recurringCustomerInfoDTO)throws ChatakPayException;
  
  /**
   * @param recurringPaymentInfoDTO
   * @throws ChatakPayException
   */
  public void deleteCustomerInfo(RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws ChatakPayException;
  
  // CRUD for the Customer ends here
  
  
  // CRUD for the Payment starts here
  /**
   * @param recurringPaymentInfoDTO
   * @throws ChatakPayException
   */
  public RecurringPaymentInfoDTO createPaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO)throws ChatakPayException;
  
  /**
   * @param recurringPaymentInfoDTO
   * @return
   * @throws ChatakPayException
   */
  public List<RecurringPaymentInfoDTO> searchPaymentInfoByCustomerId(RecurringPaymentInfoDTO recurringPaymentInfoDTO) throws ChatakPayException;
  
  /**
   * @param recurringPaymentInfoDTO
   * @return
   * @throws ChatakPayException
   */
  public RecurringPaymentInfoDTO searchPaymentInfoById(RecurringPaymentInfoDTO recurringPaymentInfoDTO) throws ChatakPayException;
  
  /**
   * @param recurringPaymentInfoDTO
   * @throws ChatakPayException
   */
  public void updatePaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO) throws ChatakPayException;
  
  /**
   * @param recurringPaymentInfoDTO
   * @throws ChatakPayException
   */
  public void deletePaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO) throws ChatakPayException;
  
  // CRUD for the Payment ends here
  
  
  // CRUD for the Contract starts here
  /**
   * @throws ChatakPayException
   */
  
  public void createContractInfo(RecurringContractInfoDTO recurringContractInfoDTO)throws ChatakPayException;
  
  /**
   * @param recurringContractInfoDTO
   * @throws ChatakPayException
   */
  public void deleteContractInfo(RecurringContractInfoDTO recurringContractInfoDTO) throws ChatakPayException;
  
  /**
   * @param recurringContractInfoDTO
   * @throws ChatakPayException
   */
  public List<RecurringContractInfoDTO> searchContractInfoByCustomerInfoId(RecurringContractInfoDTO recurringContractInfoDTO,Long customerId) throws ChatakPayException;
  
  /**
   * @param recurringCustomerInfoDTO
   * @return
   * @throws ChatakPayException
   */
  public RecurringContractInfoDTO updateContractInfo(RecurringContractInfoDTO contractInfoDTO) throws ChatakPayException;
  
  
  /**
   * @param contractInfoId
   * @param status
   * @return
   */
  public RecurringContractInfoDTO serachContractInfoByContractId(Long contractInfoId, String status) throws ChatakMerchantException;
  
  public Response getStatesByCountry(String countryId) throws ChatakMerchantException;
  
  
  public List<Option> getCountries();
  
  public Response validateEmailId(String emailId) throws ChatakMerchantException;
  
  public Response validateUpdateEmailId(String emailId, String customerId) throws ChatakMerchantException;
  
  // CRUD for the Contract ends here
}
