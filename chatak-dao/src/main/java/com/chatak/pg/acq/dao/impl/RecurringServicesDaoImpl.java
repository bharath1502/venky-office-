package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.RecurringServicesDao;
import com.chatak.pg.acq.dao.model.QRecurringContractInfo;
import com.chatak.pg.acq.dao.model.QRecurringCustomerInfo;
import com.chatak.pg.acq.dao.model.QRecurringPaymentInfo;
import com.chatak.pg.acq.dao.model.RecurringContractInfo;
import com.chatak.pg.acq.dao.model.RecurringCustomerInfo;
import com.chatak.pg.acq.dao.model.RecurringPaymentInfo;
import com.chatak.pg.acq.dao.repository.RecurringContractInfoRepository;
import com.chatak.pg.acq.dao.repository.RecurringCustomerInfoRepository;
import com.chatak.pg.acq.dao.repository.RecurringPaymentInfoRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.Status;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.RecurringContractInfoDTO;
import com.chatak.pg.model.RecurringCustomerInfoDTO;
import com.chatak.pg.model.RecurringPaymentInfoDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("recurringServicesDao")
public class RecurringServicesDaoImpl implements RecurringServicesDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	RecurringCustomerInfoRepository recurringCustomerInfoRepository;

	@Autowired
	RecurringPaymentInfoRepository recurringPaymentInfoRepository;

	@Autowired
	RecurringContractInfoRepository recurringContractInfoRepository;

	@Override
	public RecurringCustomerInfo saveOrUpdateRecurringCustomerInfo(
			RecurringCustomerInfo recurringCustomerInfo)
					throws DataAccessException {
		return recurringCustomerInfoRepository.save(recurringCustomerInfo);
	}

	@Override
	public RecurringCustomerInfo findByRecurringCustomerInfoId(Long id)
			throws DataAccessException {
		return recurringCustomerInfoRepository.findOne(id);
	}

	@Override
	public RecurringPaymentInfo saveOrUpdateRecurringPaymentInfo(
			RecurringPaymentInfo recurringPaymentInfo)
					throws DataAccessException {
		return recurringPaymentInfoRepository.save(recurringPaymentInfo);
	}

	@Override
	public RecurringPaymentInfo findByRecurringPaymentInfoId(Long id)
			throws DataAccessException {
		return recurringPaymentInfoRepository.findOne(id);
	}

	@Override
	public RecurringContractInfo saveOrUpdateRecurringContractInfo(
			RecurringContractInfo recurringContractInfo)
					throws DataAccessException {
		return recurringContractInfoRepository.save(recurringContractInfo);
	}

	@Override
	public RecurringContractInfo findByRecurringContractInfoId(Long id)
			throws DataAccessException {
		return recurringContractInfoRepository.findOne(id);
	}

	@Override
	public List<RecurringCustomerInfoDTO> searchCustomerInfo(RecurringCustomerInfoDTO recurringCustomerInfoDTO) throws InstantiationException, IllegalAccessException {
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = recurringCustomerInfoDTO.getPageIndex();
		Integer pageSize = recurringCustomerInfoDTO.getPageSize();
		Integer totalRecords;
		List<RecurringCustomerInfoDTO> custInfoList = new ArrayList<RecurringCustomerInfoDTO>();

		if(recurringCustomerInfoDTO.getPageIndex() == null || recurringCustomerInfoDTO.getPageIndex() == 1){
			totalRecords = getCustomerInfoTotalRecords(recurringCustomerInfoDTO);
			recurringCustomerInfoDTO.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}

		JPAQuery query = new JPAQuery(entityManager);
		List<RecurringCustomerInfo> infoList = query.from(QRecurringCustomerInfo.recurringCustomerInfo)
				.where(isMerchantIdEq(recurringCustomerInfoDTO.getMerchantId()),
						isCustomerIdEq(recurringCustomerInfoDTO.getCustomerId()),
						isFirstNameEq(recurringCustomerInfoDTO.getFirstName()),
						isLastnameEq(recurringCustomerInfoDTO.getLastName()),
						isPhoneNumberEq(recurringCustomerInfoDTO.getMobileNumber()),
						isEmailIdEq(recurringCustomerInfoDTO.getEmailId()),
						isCityEq(recurringCustomerInfoDTO.getCity()),
						isBusinessNameEq(recurringCustomerInfoDTO.getBusinessName()),
						isStatusEq(recurringCustomerInfoDTO.getStatus()))
						.offset(offset)
						.limit(limit)
						.orderBy(orderByCustomerInfoIdDesc())
						.list(QRecurringCustomerInfo.recurringCustomerInfo);
		if(StringUtil.isListNotNullNEmpty(infoList)){
			custInfoList = CommonUtil.copyListBeanProperty(infoList, RecurringCustomerInfoDTO.class);
		}
		return custInfoList;
	}
	
	
	/**
	 * @param businessName
	 * @return
	 */
	private BooleanExpression  isBusinessNameEq(String businessName) {
		return (businessName != null && !"".equals(businessName)) ? QRecurringCustomerInfo.recurringCustomerInfo.businessName.toUpperCase().like(
				"%" + businessName.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression  isMerchantIdEq(String merchantId) {
		return (merchantId != null && !"".equals(merchantId)) ? QRecurringCustomerInfo.recurringCustomerInfo.merchantId.toUpperCase().like(
				"%" + merchantId.toUpperCase().replace("*", "") + "%"): null;
	}


	private BooleanExpression isCustomerIdEq(String customerId) {
		return (customerId != null && !"".equals(customerId)) ? QRecurringCustomerInfo.recurringCustomerInfo.customerId.toUpperCase().like(
				"%" + customerId.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isFirstNameEq(String firstName) {
		return (firstName != null && !"".equals(firstName)) ? QRecurringCustomerInfo.recurringCustomerInfo.firstName.toUpperCase().like(
				"%" + firstName.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isLastnameEq(String lastName) {
		return (lastName != null && !"".equals(lastName)) ? QRecurringCustomerInfo.recurringCustomerInfo.lastName.toUpperCase().like(
				"%" + lastName.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isPhoneNumberEq(String phNumber) {
		return (phNumber != null && !"".equals(phNumber)) ? QRecurringCustomerInfo.recurringCustomerInfo.mobileNumber.toUpperCase().like(
				"%" + phNumber.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isEmailIdEq(String emaild) {
		return (emaild != null && !"".equals(emaild)) ? QRecurringCustomerInfo.recurringCustomerInfo.emailId.toUpperCase().like(
				"%" + emaild.toUpperCase().replace("*", "") + "%") : null;
	}
	
	private BooleanExpression isCityEq(String city) {
		return (city != null && !"".equals(city)) ? QRecurringCustomerInfo.recurringCustomerInfo.city.toUpperCase().like(
				"%" + city.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status)) ? QRecurringCustomerInfo.recurringCustomerInfo.status.eq(status): null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.chatak.pg.acq.dao.RecurringServicesDao#searchPaymentInfo(com.chatak.pg.model.RecurringPaymentInfoDTO)
	 */
	@Override
	public List<RecurringPaymentInfoDTO> searchPaymentInfo(RecurringPaymentInfoDTO recurringPaymentInfoDTO) {
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = recurringPaymentInfoDTO.getPageIndex();
		Integer pageSize = recurringPaymentInfoDTO.getPageSize();
		Integer totalRecords;
		List<RecurringPaymentInfoDTO> paymentInfoList = new ArrayList<RecurringPaymentInfoDTO>();
		RecurringPaymentInfoDTO paymentInfoDTO = null;

		if(recurringPaymentInfoDTO.getPageIndex() == null || recurringPaymentInfoDTO.getPageIndex() == 1){
			totalRecords = getPaymentInfoTotalRecords(recurringPaymentInfoDTO);
			recurringPaymentInfoDTO.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}

		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> dataList = query.from(QRecurringPaymentInfo.recurringPaymentInfo,QRecurringCustomerInfo.recurringCustomerInfo)
				.where(QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId.eq(
						recurringPaymentInfoDTO.getRecurringCustomerInfoId()),
						QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId.eq(
								QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId))
								.offset(offset)
								.limit(limit).orderBy(orderByPaymentInfoIdDesc()).list(
										QRecurringPaymentInfo.recurringPaymentInfo.recurringPaymentInfoId,
										QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId,
										QRecurringPaymentInfo.recurringPaymentInfo.cardNumber,
										QRecurringPaymentInfo.recurringPaymentInfo.expDt,
										QRecurringPaymentInfo.recurringPaymentInfo.nameOnCard,
										QRecurringPaymentInfo.recurringPaymentInfo.status
										);
		for (Tuple tuple : dataList) {
			paymentInfoDTO = new RecurringPaymentInfoDTO();
			paymentInfoDTO.setRecurringPaymentInfoId(tuple.get(QRecurringPaymentInfo.recurringPaymentInfo.recurringPaymentInfoId));
			paymentInfoDTO.setRecurringCustomerInfoId(tuple.get(QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId));
			paymentInfoDTO.setCardNumber(tuple.get(QRecurringPaymentInfo.recurringPaymentInfo.cardNumber));
			paymentInfoDTO.setExpDt(tuple.get(QRecurringPaymentInfo.recurringPaymentInfo.expDt));
			paymentInfoDTO.setNameOnCard(tuple.get(QRecurringPaymentInfo.recurringPaymentInfo.nameOnCard));
			paymentInfoDTO.setStatus(tuple.get(QRecurringPaymentInfo.recurringPaymentInfo.status));
			paymentInfoList.add(paymentInfoDTO);
		}
		return paymentInfoList;
	}

	private OrderSpecifier<Long> orderByPaymentInfoIdDesc() {
		return QRecurringPaymentInfo.recurringPaymentInfo.recurringPaymentInfoId.desc();
	}
	
	
	private OrderSpecifier<Long> orderByCustomerInfoIdDesc() {
		return QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId.desc();
	}
	
	private OrderSpecifier<Long> orderByContractInfoIdAsc() {
		return QRecurringContractInfo.recurringContractInfo.recurringContractInfoId.desc();
	}
	
	
	/**
	 * @param recurringPaymentInfoDTO
	 * @return
	 */
	private Integer getPaymentInfoTotalRecords(RecurringPaymentInfoDTO recurringPaymentInfoDTO){
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> dataList = query.from(QRecurringPaymentInfo.recurringPaymentInfo,QRecurringCustomerInfo.recurringCustomerInfo)
				.where(QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId.eq(
						recurringPaymentInfoDTO.getRecurringCustomerInfoId()),
						QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId.eq(
								QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId))
								.orderBy(orderByPaymentInfoIdDesc()).list(
										QRecurringPaymentInfo.recurringPaymentInfo.recurringPaymentInfoId);
		return (StringUtil.isListNotNullNEmpty(dataList) ? dataList.size() : 0);
	}
	
	/**
	 * @param recurringContractInfoDTO
	 * @param custInfoId
	 * @return
	 */
	private Integer getContractInfoTotalRecords(RecurringContractInfoDTO recurringContractInfoDTO){
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> dataList = query.from(QRecurringContractInfo.recurringContractInfo,
				QRecurringPaymentInfo.recurringPaymentInfo,QRecurringCustomerInfo.recurringCustomerInfo)
				.where(
						QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId.eq(recurringContractInfoDTO.getRecurringCustInfoId()),
						isContractId(recurringContractInfoDTO.getRecurringContractInfoId()),
						QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId.eq(
								QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId),
								QRecurringContractInfo.recurringContractInfo.recurringPaymentInfoId.eq(
										QRecurringPaymentInfo.recurringPaymentInfo.recurringPaymentInfoId))
										.orderBy(orderByContractInfoIdAsc())
										.list(QRecurringContractInfo.recurringContractInfo.recurringContractInfoId
												);
		return (StringUtil.isListNotNullNEmpty(dataList) ? dataList.size() : 0);
	}
	
	/**
	 * @param recurringCustomerInfoDTO
	 * @return
	 */
	private Integer getCustomerInfoTotalRecords(RecurringCustomerInfoDTO recurringCustomerInfoDTO) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> dataList = query.from(QRecurringCustomerInfo.recurringCustomerInfo)
				.where(isMerchantIdEq(recurringCustomerInfoDTO.getMerchantId()),
						isCustomerIdEq(recurringCustomerInfoDTO.getCustomerId()),
						isFirstNameEq(recurringCustomerInfoDTO.getFirstName()),
						isLastnameEq(recurringCustomerInfoDTO.getLastName()),
						isPhoneNumberEq(recurringCustomerInfoDTO.getMobileNumber()),
						isEmailIdEq(recurringCustomerInfoDTO.getEmailId()),
						isCityEq(recurringCustomerInfoDTO.getCity()),
						isBusinessNameEq(recurringCustomerInfoDTO.getBusinessName()),
						isStatusEq(recurringCustomerInfoDTO.getStatus())
						).orderBy(orderByCustomerInfoIdDesc()).list(QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId);
		return (StringUtil.isListNotNullNEmpty(dataList) ? dataList.size() : 0);
	}
	
	
	/* (non-Javadoc)
	 * @see com.chatak.pg.acq.dao.RecurringServicesDao#deletePaymentInfo(java.lang.Long)
	 */
	@Override
	@Transactional
	public Boolean deletePaymentInfo(Long paymentInfoId) throws DataAccessException {
		Boolean isUpdated = Boolean.FALSE;
		Query query = entityManager.createQuery("UPDATE RecurringPaymentInfo P SET P.status = :status WHERE P.recurringPaymentInfoId = :paymentInfoId");
		query.setParameter("status",Status.INACTIVE.name());
		query.setParameter("paymentInfoId",paymentInfoId);
		int updatedCount = query.executeUpdate();

		if(updatedCount >0 ){
			isUpdated = Boolean.TRUE;
		}
		return isUpdated;
	}
	
	@Override
	public Boolean deleteContractInfo(Long contractInfoId) throws DataAccessException {
		Boolean isUpdated = Boolean.FALSE;
		Query query = entityManager.createQuery("UPDATE RecurringContractInfo C SET C.status = :status WHERE C.recurringContractInfoId = :contractInfoId");
		query.setParameter("status",Status.INACTIVE.name());
		query.setParameter("contractInfoId",contractInfoId);
		int updatedCount = query.executeUpdate();

		if(updatedCount >0 ){
			isUpdated = Boolean.TRUE;
		}
		return isUpdated;
	}
	public List<RecurringContractInfoDTO> searchContractInfoByCustomerInfoId(RecurringContractInfoDTO recurringContractInfoDTO){
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = recurringContractInfoDTO.getPageIndex();
		Integer pageSize = recurringContractInfoDTO.getPageSize();
		Integer totalRecords;
		List<RecurringContractInfoDTO> contractInfoList = new ArrayList<RecurringContractInfoDTO>();
		RecurringContractInfoDTO contractInfoDTO = null;

		if(recurringContractInfoDTO.getPageIndex() == null || recurringContractInfoDTO.getPageIndex() == 1){
			totalRecords = getContractInfoTotalRecords(recurringContractInfoDTO);
			recurringContractInfoDTO.setNoOfRecords(totalRecords);
		}

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}

		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> dataList = query.from(QRecurringContractInfo.recurringContractInfo,
				QRecurringPaymentInfo.recurringPaymentInfo,QRecurringCustomerInfo.recurringCustomerInfo)
				.where(
						QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId.eq(recurringContractInfoDTO.getRecurringCustInfoId()),
						isContractId(recurringContractInfoDTO.getRecurringContractInfoId()),
						QRecurringPaymentInfo.recurringPaymentInfo.recurringCustomerInfoId.eq(
								QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId),
								QRecurringContractInfo.recurringContractInfo.recurringPaymentInfoId.eq(
										QRecurringPaymentInfo.recurringPaymentInfo.recurringPaymentInfoId))
										.offset(offset)
										.limit(limit).orderBy(orderByContractInfoIdAsc()).
										list(QRecurringContractInfo.recurringContractInfo.contractId,
												QRecurringContractInfo.recurringContractInfo.contractAmount,
												QRecurringContractInfo.recurringContractInfo.contractName,
												QRecurringContractInfo.recurringContractInfo.startDate,
												QRecurringContractInfo.recurringContractInfo.endDate,
												QRecurringContractInfo.recurringContractInfo.tax,
												QRecurringContractInfo.recurringContractInfo.status,
												QRecurringContractInfo.recurringContractInfo.transactionApprovedEmail,
												QRecurringContractInfo.recurringContractInfo.transactionDeclinedEmail,
												QRecurringContractInfo.recurringContractInfo.contractExecution,
												QRecurringPaymentInfo.recurringPaymentInfo.nameOnCard,
												QRecurringContractInfo.recurringContractInfo.recurringContractInfoId,
												QRecurringContractInfo.recurringContractInfo.recurringPaymentInfoId,
												QRecurringContractInfo.recurringContractInfo.status,
												QRecurringContractInfo.recurringContractInfo.startDate,
												QRecurringContractInfo.recurringContractInfo.endDate,
												QRecurringContractInfo.recurringContractInfo.contractAmount,
												QRecurringContractInfo.recurringContractInfo.tax,
												QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId,
												QRecurringContractInfo.recurringContractInfo.lastBillDate,
												QRecurringContractInfo.recurringContractInfo.contractExecutionReprocess
												
												);
		for (Tuple tuple : dataList) {
			contractInfoDTO = new RecurringContractInfoDTO();
			contractInfoDTO.setContractAmount(tuple.get(QRecurringContractInfo.recurringContractInfo.contractAmount));
			contractInfoDTO.setContractName(tuple.get(QRecurringContractInfo.recurringContractInfo.contractName));
			contractInfoDTO.setRecurringpaymentInfoId(tuple.get(QRecurringContractInfo.recurringContractInfo.recurringPaymentInfoId));
			contractInfoDTO.setStartDate(tuple.get(QRecurringContractInfo.recurringContractInfo.startDate).toString());
			contractInfoDTO.setEndDate(tuple.get(QRecurringContractInfo.recurringContractInfo.endDate).toString());
			contractInfoDTO.setStatus(tuple.get(QRecurringContractInfo.recurringContractInfo.status));
			contractInfoDTO.setTransactionApprovedEmail(tuple.get(QRecurringContractInfo.recurringContractInfo.transactionApprovedEmail));
			contractInfoDTO.setTransactionDeclinedEmail(tuple.get(QRecurringContractInfo.recurringContractInfo.transactionDeclinedEmail));
			contractInfoDTO.setRecurringContractInfoId(tuple.get(QRecurringContractInfo.recurringContractInfo.recurringContractInfoId));
			contractInfoDTO.setContractId(tuple.get(QRecurringContractInfo.recurringContractInfo.contractId));
			contractInfoDTO.setStatus(tuple.get(QRecurringContractInfo.recurringContractInfo.status));
			contractInfoDTO.setRecurringCustInfoId(tuple.get(QRecurringCustomerInfo.recurringCustomerInfo.recurringCustInfoId));
			contractInfoDTO.setLastBillDate(DateUtil.toDateStringFormat(tuple.get(QRecurringContractInfo.recurringContractInfo.lastBillDate), "MM/dd/yyyy"));
			contractInfoDTO.setContractExecution(tuple.get(QRecurringContractInfo.recurringContractInfo.contractExecution));
			contractInfoDTO.setContractExecutionReprocess(tuple.get(QRecurringContractInfo.recurringContractInfo.contractExecutionReprocess));
			contractInfoDTO.setBillAmount(
					tuple.get(QRecurringContractInfo.recurringContractInfo.contractAmount) +
					tuple.get(QRecurringContractInfo.recurringContractInfo.tax));
			contractInfoList.add(contractInfoDTO);
		}
		return contractInfoList;
	}
	
	public List<RecurringContractInfo> findContractInfoByPaymentInfoId(Long paymentInfoId)throws DataAccessException{
		return recurringContractInfoRepository.findByRecurringPaymentInfoId(paymentInfoId);
	}
	
	public BooleanExpression isContractId(Long recurringContractInfoId) {
		return recurringContractInfoId != null ? QRecurringContractInfo.recurringContractInfo.recurringContractInfoId.eq(recurringContractInfoId) : null;
		
	}

	@Override
	public RecurringContractInfoDTO searchContractInfoByContractInfoIdCon(
			Long contractInfoId, String status) throws DataAccessException {


		RecurringContractInfoDTO contractInfoDTO = new RecurringContractInfoDTO();
		
		JPAQuery query = new JPAQuery(entityManager);
		List<Tuple> data = query.from(QRecurringContractInfo.recurringContractInfo,QRecurringPaymentInfo.recurringPaymentInfo).where(QRecurringContractInfo.recurringContractInfo.recurringContractInfoId.eq(contractInfoId),
																	   	QRecurringContractInfo.recurringContractInfo.status.eq(status))
																	   	.orderBy(orderByContractInfoIdAsc()).
																		list(QRecurringContractInfo.recurringContractInfo.contractId,
																				QRecurringContractInfo.recurringContractInfo.contractAmount,
																				QRecurringContractInfo.recurringContractInfo.contractName,
																				QRecurringContractInfo.recurringContractInfo.startDate,
																				QRecurringContractInfo.recurringContractInfo.endDate,
																				QRecurringContractInfo.recurringContractInfo.tax,
																				QRecurringContractInfo.recurringContractInfo.status,
																				QRecurringContractInfo.recurringContractInfo.transactionApprovedEmail,
																				QRecurringContractInfo.recurringContractInfo.transactionDeclinedEmail,
																				QRecurringContractInfo.recurringContractInfo.contractExecution,
																				QRecurringContractInfo.recurringContractInfo.recurringContractInfoId,
																				QRecurringContractInfo.recurringContractInfo.recurringPaymentInfoId,
																				QRecurringPaymentInfo.recurringPaymentInfo.nameOnCard,
																				QRecurringContractInfo.recurringContractInfo.lastBillDate,
																				QRecurringContractInfo.recurringContractInfo.contractExecutionReprocess
																		);
		for(Tuple tuple : data) {
			contractInfoDTO.setContractId(tuple.get(QRecurringContractInfo.recurringContractInfo.contractId));
			contractInfoDTO.setContractAmount(tuple.get(QRecurringContractInfo.recurringContractInfo.contractAmount));
			contractInfoDTO.setContractName(tuple.get(QRecurringContractInfo.recurringContractInfo.contractName));
			contractInfoDTO.setStartDate(DateUtil.toDateStringFormat(tuple.get(QRecurringContractInfo.recurringContractInfo.startDate), DateUtil.SIMPLE_DATE_FORMAT));
			contractInfoDTO.setEndDate( DateUtil.toDateStringFormat(tuple.get(QRecurringContractInfo.recurringContractInfo.endDate), DateUtil.SIMPLE_DATE_FORMAT));
			contractInfoDTO.setTax(tuple.get(QRecurringContractInfo.recurringContractInfo.tax));
			contractInfoDTO.setStatus(tuple.get(QRecurringContractInfo.recurringContractInfo.status));
			contractInfoDTO.setTransactionApprovedEmail(tuple.get(QRecurringContractInfo.recurringContractInfo.transactionApprovedEmail));
			contractInfoDTO.setTransactionDeclinedEmail(tuple.get(QRecurringContractInfo.recurringContractInfo.transactionDeclinedEmail));
			contractInfoDTO.setContractExecution(tuple.get(QRecurringContractInfo.recurringContractInfo.contractExecution));
			contractInfoDTO.setRecurringContractInfoId(tuple.get(QRecurringContractInfo.recurringContractInfo.recurringContractInfoId));
			contractInfoDTO.setRecurringpaymentInfoId(tuple.get(QRecurringContractInfo.recurringContractInfo.recurringPaymentInfoId));
			contractInfoDTO.setNameOnCard(tuple.get(QRecurringPaymentInfo.recurringPaymentInfo.nameOnCard));
			contractInfoDTO.setContractExecutionReprocess(tuple.get(QRecurringContractInfo.recurringContractInfo.contractExecutionReprocess));
			contractInfoDTO.setLastBillDate(DateUtil.toDateStringFormat(tuple.get(QRecurringContractInfo.recurringContractInfo.lastBillDate), DateUtil.VIEW_DATE_FORMAT));
		}
		
		return  contractInfoDTO;	
	}

  /**
   * @param custId
   * @return
   */
  @Override
  public RecurringCustomerInfo findByCustomerId(String custId) {
    return recurringCustomerInfoRepository.findByCustomerId(custId);
  }
  
  /**
 * @param emailId
 * @return
 */
@Override
public Response getUserByEmailId(String emailId) {
	
	List<RecurringCustomerInfo> recurringCustomerInfo	= recurringCustomerInfoRepository.findByEmailId(emailId);
	
	  Response response = new Response();
	    if(StringUtils.isListNotNullNEmpty(recurringCustomerInfo)) {
	      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
	      response.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
	      return response;
	    }
	    else {
	      validateErrorMessageAndErrorCode(response);
	      return response;
	    }
}

/**
 * @param emailId
 * @param customerId
 * @return
 */
@Override
public Response getUserByUpdateEmailId(String emailId, String customerId) {

	List<RecurringCustomerInfo> recurringCustomerInfo	= recurringCustomerInfoRepository.findByEmailId(emailId);
	  Response response = new Response();
	    if(StringUtils.isListNotNullNEmpty(recurringCustomerInfo)) {
	    	
	    	if(customerId.equals(recurringCustomerInfo.get(0).getCustomerId())) {
	            validateErrorMessageAndErrorCode(response);
	            return response;
	          }
	    	response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
	        response.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
	        return response;
       }
	    else
	    {
	    	validateErrorMessageAndErrorCode(response);
	        return response;
	    }
   }

private void validateErrorMessageAndErrorCode(Response response) {
	response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
	response.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
}
}
