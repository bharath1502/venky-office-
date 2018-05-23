package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.RecurringContractInfo;
import com.chatak.pg.acq.dao.model.RecurringCustomerInfo;
import com.chatak.pg.acq.dao.model.RecurringPaymentInfo;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.RecurringContractInfoDTO;
import com.chatak.pg.model.RecurringCustomerInfoDTO;
import com.chatak.pg.model.RecurringPaymentInfoDTO;


public interface RecurringServicesDao {
	
	
	/**
	 * @param recurringCustomerInfo
	 * @return
	 * @throws DataAccessException
	 */
	public RecurringCustomerInfo saveOrUpdateRecurringCustomerInfo(RecurringCustomerInfo recurringCustomerInfo) throws DataAccessException;
	
	/**
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public RecurringCustomerInfo findByRecurringCustomerInfoId(Long id) throws DataAccessException;
	
	/**
	 * @param recurringPaymentInfo
	 * @return
	 * @throws DataAccessException
	 */
	public RecurringPaymentInfo saveOrUpdateRecurringPaymentInfo(RecurringPaymentInfo recurringPaymentInfo) throws DataAccessException;
	
	/**
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public RecurringPaymentInfo findByRecurringPaymentInfoId(Long id) throws DataAccessException;
	
	/**
	 * @param recurringContractInfo
	 * @return
	 * @throws DataAccessException
	 */
	public RecurringContractInfo saveOrUpdateRecurringContractInfo(RecurringContractInfo recurringContractInfo) throws DataAccessException;
	
	/**
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	public RecurringContractInfo findByRecurringContractInfoId(Long id) throws DataAccessException;
	
	/**
	 * @param recurringPaymentInfoDTO
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public List<RecurringPaymentInfoDTO> searchPaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO);
	
	/**
	 * @param paymentInfoId
	 * @return
	 * @throws DataAccessException
	 */
	public Boolean deletePaymentInfo(Long paymentInfoId) throws DataAccessException;
	
	/**
	 * @param recurringCustomerInfoDTO
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public List<RecurringCustomerInfoDTO> searchCustomerInfo(RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws InstantiationException, IllegalAccessException;
	
	/**
	 * @param contractInfoId
	 * @return
	 * @throws DataAccessException
	 */
	public Boolean deleteContractInfo(Long contractInfoId) throws DataAccessException;
	
	/**
	 * @param paymentInfoId
	 * @return
	 * @throws DataAccessException
	 */
	public List<RecurringContractInfo> findContractInfoByPaymentInfoId(Long paymentInfoId)throws DataAccessException;
	
	/**
	 * @param recurringContractInfoDTO
	 * @param custInfoId
	 * @return
	 * @throws DataAccessException
	 */
	public List<RecurringContractInfoDTO> searchContractInfoByCustomerInfoId(RecurringContractInfoDTO recurringContractInfoDTO) throws DataAccessException;
	
	/**
	 * @param contractInfoId
	 * @param status
	 * @return
	 * @throws DataAccessException
	 */
	public RecurringContractInfoDTO searchContractInfoByContractInfoIdCon(Long contractInfoId, String status) throws DataAccessException;
	
	/**
	 * @param CustomerId
	 **/
	public RecurringCustomerInfo findByCustomerId(String custId);
	
	/**
	 * @param Email Id
	 **/
	
	public Response getUserByEmailId(String emailId);
	
	public Response getUserByUpdateEmailId(String emailId, String customerId);
}
