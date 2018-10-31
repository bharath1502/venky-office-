/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantEntityMapDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantEntityMap;
import com.chatak.pg.acq.dao.repository.MerchantEntityMapRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.MerchantRequest;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.StringUtils;

/**
 * @Author: Girmiti Software
 * @Date: May 10, 2018
 * @Time: 3:58:02 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("merchantEntityMapDao")
public class MerchantEntityMapDaoImpl implements MerchantEntityMapDao {
	
	private static Logger logger = Logger.getLogger(MerchantProfileDaoImpl.class);

	@Autowired
	private MerchantEntityMapRepository merchantEntityMapRepository;
	
	@PersistenceContext
	 private EntityManager entityManager;
	
	@Override
	public List<PGMerchantEntityMap> findByMerchantId(Long merchantId) {
		return merchantEntityMapRepository.findByMerchantId(merchantId);
	}

	@Override
	public GetMerchantListResponse fetchMerchantsForPM(GetMerchantListRequest searchMerchant, Long entityId) {
		 logger.info("MerchantEntityMapDaoImpl | fetchMerchantsForPM | Entering");
		    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		    List<MerchantRequest> merchantList = new ArrayList<>();
		    try {
		    	 int startIndex = 0;
		         int endIndex = 0;
		         Integer totalRecords = searchMerchant.getNoOfRecords();

		         if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
		           totalRecords = getTotalNumberOfMerchantRecordsForPM(searchMerchant, entityId);
		           searchMerchant.setNoOfRecords(totalRecords);
		         }
		         getMerchantListResponse.setNoOfRecords(totalRecords);
		         if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
		        	 startIndex = 0;
		         } else {
		        	 startIndex = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
		        	 endIndex = searchMerchant.getPageSize() + startIndex;
		         }
		         	int resultIndex = endIndex - startIndex;
					StringBuilder query = new StringBuilder("select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
							+ "(select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.ID = PMEM.MERCHANT_ID	AND PMEM.ENTITY_ID=:entityId")
					.append(" union ")
					.append(" select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
					.append(" from PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.ID = PMEM.MERCHANT_ID "
							+ "INNER JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID AND PMIM.PM_ID =:entityId ");
					query.append(" )a  where 1=1 ");
					merchantFilterParameters(searchMerchant, query);
					query.append("  ORDER BY a.CREATED_DATE DESC");
					query.append("  limit :startIndex,:resultSize");
					Query qry = entityManager.createNativeQuery(query.toString());
					qry.setParameter("startIndex", startIndex);
					qry.setParameter("resultSize", resultIndex);
					qry.setParameter("entityId", entityId);
					List<Object> cardProgramResponse = qry.getResultList();
					MerchantRequest request = null;
					if (StringUtil.isListNotNullNEmpty(cardProgramResponse)) {
						Iterator<Object> itr = cardProgramResponse.iterator();
						while (itr.hasNext()) {
							Object[] object = (Object[]) itr.next();
							request = new MerchantRequest();
							request.setBusinessName(object[0].toString());
							request.setMerchantCode(object[1].toString());
							request.setEmailId(object[2].toString());
							request.setCountry(object[3].toString());
							request.setCurrency(object[4].toString());
							request.setStatus(Integer.valueOf(object[5].toString()));
							request.setId(((BigInteger)object[6]).longValue());
							request.setPhone(((BigInteger)object[7]).longValue());
							merchantList.add(request);
						}
					}
					getMerchantListResponse.setNoOfRecords(searchMerchant.getNoOfRecords());    	
					getMerchantListResponse.setMerchantRequests(merchantList);
		    } catch (Exception e) {
		      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
		      getMerchantListResponse
		          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
		      logger.error("MerchantEntityMapDaoImpl | fetchMerchantsForPM | Exception " + e);
		    }
		    logger.info("MerchantEntityMapDaoImpl | fetchMerchantsForPM | Exiting");
		    return getMerchantListResponse;
	}

	@Override
	public GetMerchantListResponse fetchMerchantsForISO(GetMerchantListRequest searchMerchant, Long entityId) {
		 logger.info("MerchantEntityMapDaoImpl | fetchMerchantsForISO | Entering");
		    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		    List<MerchantRequest> merchantList = new ArrayList<>();
		    try {
		    	 int startIndex = 0;
		         int endIndex = 0;
		         Integer totalRecords = searchMerchant.getNoOfRecords();

		         if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
		           totalRecords = getTotalNumberOfMerchantRecordsForISO(searchMerchant, entityId);
		           searchMerchant.setNoOfRecords(totalRecords);
		         }
		         getMerchantListResponse.setNoOfRecords(totalRecords);
		         if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
		        	 startIndex = 0;
		         } else {
		        	 startIndex = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
		        	 endIndex = searchMerchant.getPageSize() + startIndex;
		         }
		         	int resultIndex = endIndex - startIndex;
					StringBuilder query = new StringBuilder("select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
					+ "( select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE,PMEM.ENTITY_ID ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.ID = PMEM.MERCHANT_ID)a	")
					.append(" where a.ENTITY_ID=:entityId ");
					merchantFilterParameters(searchMerchant, query);
					query.append("   ORDER BY a.CREATED_DATE DESC");
					query.append("  limit :startIndex,:resultSize");
					Query qry = entityManager.createNativeQuery(query.toString());
					qry.setParameter("startIndex", startIndex);
					qry.setParameter("resultSize", resultIndex);
					qry.setParameter("entityId", entityId);
					List<Object> cardProgramResponse = qry.getResultList();
					MerchantRequest request = null;
					if (StringUtil.isListNotNullNEmpty(cardProgramResponse)) {
						Iterator<Object> itr = cardProgramResponse.iterator();
						while (itr.hasNext()) {
							Object[] object = (Object[]) itr.next();
							request = new MerchantRequest();
							request.setBusinessName(object[0].toString());
							request.setMerchantCode(object[1].toString());
							request.setEmailId(object[2].toString());
							request.setCountry(object[3].toString());
							request.setCurrency(object[4].toString());
							request.setStatus(Integer.valueOf(object[5].toString()));
							request.setId(((BigInteger)object[6]).longValue());
							request.setPhone(((BigInteger)object[7]).longValue());
							merchantList.add(request);
						}
					}
					getMerchantListResponse.setNoOfRecords(searchMerchant.getNoOfRecords());    	
					getMerchantListResponse.setMerchantRequests(merchantList);
		    } catch (Exception e) {
		      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
		      getMerchantListResponse
		          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
		      logger.error("MerchantEntityMapDaoImpl | fetchMerchantsForISO | Exception " + e);
		    }
		    logger.info("MerchantEntityMapDaoImpl | fetchMerchantsForISO | Exiting");
		    return getMerchantListResponse;
	}
	
	private int getTotalNumberOfMerchantRecordsForPM(GetMerchantListRequest searchMerchant, Long entityId) {
		StringBuilder query = new StringBuilder("select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
				+ "( select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.ID = PMEM.MERCHANT_ID	AND PMEM.ENTITY_ID=:entityId")
					.append(" union ")
					.append(" select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
					.append(" from PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.ID = PMEM.MERCHANT_ID "
					+ "INNER JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID AND PMIM.PM_ID =:entityId ");
		query.append(" )a where 1=1 ");
		merchantFilterParameters(searchMerchant, query);
		query.append(" ORDER BY a.CREATED_DATE DESC");

		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("entityId", entityId);
		List<Object> cardProgramResponse = qry.getResultList();
 		return (StringUtils.isListNotNullNEmpty(cardProgramResponse) ? cardProgramResponse.size() : 0);
	}
	
	private int getTotalNumberOfMerchantRecordsForISO(GetMerchantListRequest searchMerchant, Long entityId) {
		StringBuilder query = new StringBuilder(" select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
				+ "( select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE,PMEM.ENTITY_ID ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.ID = PMEM.MERCHANT_ID)a	")
					.append(" where a.ENTITY_ID=:entityId ");
					merchantFilterParameters(searchMerchant, query);
					query.append("   ORDER BY a.CREATED_DATE DESC");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("entityId", entityId);
		List<Object> cardProgramResponse = qry.getResultList();
 		return (StringUtils.isListNotNullNEmpty(cardProgramResponse) ? cardProgramResponse.size() : 0);
	}
	
	private void merchantFilterParameters(GetMerchantListRequest searchMerchant, StringBuilder query) {
		if(!StringUtils.isNullAndEmpty(searchMerchant.getMerchantCode())) {
			query.append(" and ( a.MERCHANT_CODE= '"+searchMerchant.getMerchantCode()+"' ) "); 
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getBusinessName())){
			query.append(" and (a.BUSINESS_NAME= '"+searchMerchant.getBusinessName()+"' )");
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getEmailId())){
			query.append(" and (a.EMAIL= '"+searchMerchant.getEmailId()+"' )");
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getCountry())){
			query.append(" and (a.COUNTRY= '"+searchMerchant.getCountry()+"' )");
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getStatus())){
			query.append(" and (a.STATUS= '"+searchMerchant.getStatus()+"' )");
		}
	}

	@Override
	public GetMerchantListResponse fetchSubMerchantsForPM(GetMerchantListRequest searchMerchant, Long entityId) {
		  logger.info("Entering :: MerchantEntityMapDaoImpl :: fetchSubMerchantsForPM Method");
		    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
		    List<PGMerchant> merchantList = null;
		    List<MerchantRequest> subMerchantList = new ArrayList<MerchantRequest>();
		    try {

		      int startIndex = 0;
		      int endIndex = 0;
		      Integer totalRecords = searchMerchant.getNoOfRecords();

		      if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
		        totalRecords = getTotalNumberOfSubMerchantRecordsForPM(searchMerchant, entityId);
		        searchMerchant.setNoOfRecords(totalRecords);
		      }
		      getMerchantListResponse.setNoOfRecords(totalRecords);
		      if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
		    	  startIndex = 0;
		    	  endIndex = Constants.DEFAULT_PAGE_SIZE;
		      } else {
		    	  startIndex = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
		    	  endIndex = startIndex + searchMerchant.getPageSize();
		      }
		      int resultIndex = endIndex - startIndex;
				StringBuilder query = new StringBuilder("select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
						+ "(select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
				.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.PARENT_MERCHANT_ID = PMEM.MERCHANT_ID	AND PMEM.ENTITY_ID=:entityId")
				.append(" union ")
				.append(" select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
				.append(" from PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.PARENT_MERCHANT_ID = PMEM.MERCHANT_ID "
						+ "INNER JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID AND PMIM.PM_ID =:entityId ");
				query.append(" )a  where 1=1 ");
				subMerchantFilterParameters(searchMerchant, query);
				query.append("  ORDER BY a.CREATED_DATE DESC");
				query.append("  limit :startIndex,:resultSize");
				Query qry = entityManager.createNativeQuery(query.toString());
				qry.setParameter("startIndex", startIndex);
				qry.setParameter("resultSize", resultIndex);
				qry.setParameter("entityId", entityId);
					  List<Object> listOfReport = qry.getResultList();
					  getListOfSubMerchants(subMerchantList, listOfReport);
		      if (!subMerchantList.isEmpty()) {
		        getMerchantListResponse.setMerchantRequestList(subMerchantList);
		        getMerchantListResponse.setMerchants(merchantList);
		        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
		        getMerchantListResponse.setErrorMessage(
		            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
		      } else {
		        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
		        getMerchantListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
		      }

		    } catch (Exception e) {
		      logger.error("Error :: MerchantEntityMapDaoImpl :: fetchSubMerchantsForPM Method", e);
		      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
		      getMerchantListResponse
		          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
		    }

		    logger.info("Exiting :: MerchantEntityMapDaoImpl :: fetchSubMerchantsForPM Method");
		    return getMerchantListResponse;
	}

	/**
	 * @param searchMerchant
	 * @param entityId
	 * @return
	 */
	@Override
	public GetMerchantListResponse fetchSubMerchantsForISO(GetMerchantListRequest searchMerchant, Long entityId) {
		logger.info("Entering :: MerchantEntityMapDaoImpl :: fetchSubMerchantsForISO Method");
	    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
	    List<PGMerchant> merchantList = null;
	    List<MerchantRequest> subMerchantList = new ArrayList<MerchantRequest>();
	    try {

	      int startIndex = 0;
	      int endIndex = 0;
	      Integer totalRecords = searchMerchant.getNoOfRecords();

	      if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
	        totalRecords = getTotalNumberOfSubMerchantRecordsForISO(searchMerchant, entityId);
	        searchMerchant.setNoOfRecords(totalRecords);
	      }
	      getMerchantListResponse.setNoOfRecords(totalRecords);
	      if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
	    	  startIndex = 0;
	    	  endIndex = Constants.DEFAULT_PAGE_SIZE;
	      } else {
	    	  startIndex = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
	    	  endIndex = startIndex + searchMerchant.getPageSize();
	      }
	      int resultIndex = endIndex - startIndex;
	      StringBuilder query = new StringBuilder("select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
					+ "( select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE,PMEM.ENTITY_ID ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.PARENT_MERCHANT_ID = PMEM.MERCHANT_ID)a	")
					.append(" where a.ENTITY_ID=:entityId ");
			subMerchantFilterParameters(searchMerchant, query);
			query.append("  ORDER BY a.CREATED_DATE DESC");
			query.append("  limit :startIndex,:resultSize");
			Query qry = entityManager.createNativeQuery(query.toString());
			qry.setParameter("startIndex", startIndex);
			qry.setParameter("resultSize", resultIndex);
			qry.setParameter("entityId", entityId);
				  List<Object> listOfReport = qry.getResultList();
				  getListOfSubMerchants(subMerchantList, listOfReport);
	      if (!subMerchantList.isEmpty()) {
	        getMerchantListResponse.setMerchantRequestList(subMerchantList);
	        getMerchantListResponse.setMerchants(merchantList);
	        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
	        getMerchantListResponse.setErrorMessage(
	            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
	      } else {
	        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
	        getMerchantListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
	      }
	    } catch (Exception e) {
	      logger.error("Error :: MerchantEntityMapDaoImpl :: fetchSubMerchantsForISO Method", e);
	      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
	      getMerchantListResponse
	          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
	    }
	    logger.info("Exiting :: MerchantEntityMapDaoImpl :: fetchSubMerchantsForISO Method");
	    return getMerchantListResponse;
	}
	
	private void getListOfSubMerchants(List<MerchantRequest> subMerchantList, List<Object> listOfReport) {
		if (StringUtil.isListNotNullNEmpty(listOfReport)) {
			Iterator it = listOfReport.iterator();
			while (it.hasNext()) {
				try {
					Object[] obj = (Object[]) it.next();
					MerchantRequest merchantRequest = new MerchantRequest();
					merchantRequest.setBusinessName(obj[0].toString());
					merchantRequest.setMerchantCode(obj[1].toString());
					merchantRequest.setEmailId(obj[2].toString());
					merchantRequest.setCountry(obj[3].toString());
					merchantRequest.setCurrency(obj[4].toString());
					merchantRequest.setStatus(Integer.valueOf(obj[5].toString()));
					merchantRequest.setId(((BigInteger)obj[6]).longValue());
					merchantRequest.setPhone(((BigInteger)obj[7]).longValue());
					subMerchantList.add(merchantRequest);
				} catch (Exception e) {
					logger.error("Error :: MerchantEntityMapDaoImpl :: fetchSubMerchantsForPM Method", e);
				}
			}
		}
	}
	
	private int getTotalNumberOfSubMerchantRecordsForPM(GetMerchantListRequest searchMerchant, Long entityId) {
		StringBuilder query = new StringBuilder("select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
				+ "( select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.PARENT_MERCHANT_ID = PMEM.MERCHANT_ID	AND PMEM.ENTITY_ID=:entityId")
					.append(" union ")
					.append(" select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE ")
					.append(" from PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.PARENT_MERCHANT_ID = PMEM.MERCHANT_ID "
					+ "INNER JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID AND PMIM.PM_ID =:entityId ");
		query.append(" )a where 1=1 ");
		subMerchantFilterParameters(searchMerchant, query);
		query.append(" ORDER BY a.CREATED_DATE DESC");

		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("entityId", entityId);
		List<Object> cardProgramResponse = qry.getResultList();
 		return (StringUtils.isListNotNullNEmpty(cardProgramResponse) ? cardProgramResponse.size() : 0);
	}
	
	private void subMerchantFilterParameters(GetMerchantListRequest searchMerchant, StringBuilder query) {
		if(!StringUtils.isNullAndEmpty(searchMerchant.getSubMerchantCode())) {
			query.append(" and ( a.MERCHANT_CODE= '"+searchMerchant.getSubMerchantCode()+"' ) "); 
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getBusinessName())){
			query.append(" and (a.BUSINESS_NAME= '"+searchMerchant.getBusinessName()+"' )");
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getEmailId())){
			query.append(" and (a.EMAIL= '"+searchMerchant.getEmailId()+"' )");
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getCountry())){
			query.append(" and (a.COUNTRY= '"+searchMerchant.getCountry()+"' )");
		} if(!StringUtils.isNullAndEmpty(searchMerchant.getStatus())){
			query.append(" and (a.STATUS= '"+searchMerchant.getStatus()+"' )");
		}
	}
	
	private int getTotalNumberOfSubMerchantRecordsForISO(GetMerchantListRequest searchMerchant, Long entityId) {
		StringBuilder query = new StringBuilder(
				" select a.BUSINESS_NAME,a.MERCHANT_CODE,a.EMAIL,a.COUNTRY,a.LOCAL_CURRENCY,a.STATUS,a.ID,a.PHONE from "
						+ "( select PGM.BUSINESS_NAME,PGM.MERCHANT_CODE,PGM.EMAIL,PGM.COUNTRY,PGM.LOCAL_CURRENCY,PGM.STATUS,PGM.CREATED_DATE,PGM.ID,PGM.PHONE,PMEM.ENTITY_ID ")
				.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON	PGM.PARENT_MERCHANT_ID = PMEM.MERCHANT_ID)a	")
				.append(" where a.ENTITY_ID=:entityId ");
		subMerchantFilterParameters(searchMerchant, query);
		query.append("   ORDER BY a.CREATED_DATE DESC");
		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("entityId", entityId);
		List<Object> cardProgramResponse = qry.getResultList();
		return (StringUtils.isListNotNullNEmpty(cardProgramResponse) ? cardProgramResponse.size() : 0);
	}
	
  public Map<String, String> getMerchantCodeForPMOrIso(Long entityId, String loginUserType) {
    StringBuilder query = new StringBuilder();
    if (loginUserType.equals(Constants.PM_USER_TYPE)) {
      query = new StringBuilder(
          "select a.ID,concat(a.MERCHANT_CODE, '-', a.BUSINESS_NAME) as nname from "
              + "( select PGM.ID, PGM.BUSINESS_NAME, PGM.MERCHANT_CODE FROM ")
                  .append(
                      " PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON PGM.ID = PMEM.MERCHANT_ID AND PMEM.ENTITY_ID=:entityId")
                  .append(" union ")
                  .append(" select PGM.ID, PGM.BUSINESS_NAME, PGM.MERCHANT_CODE FROM").append(
                      " PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.ID = PMEM.MERCHANT_ID "
                          + "INNER JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID AND PMIM.PM_ID =:entityId ");
      query.append(" )a where 1=1 ");
    } else if (loginUserType.equals(Constants.ISO_USER_TYPE)) {
      query = new StringBuilder("SELECT a.ID,a.MerchantCodeName FROM "
          + "(SELECT PGM.ID,concat(PGM.MERCHANT_CODE,' - ',PGM.BUSINESS_NAME) as MerchantCodeName,PMEM.ENTITY_ID,PGM.CREATED_DATE "
          + "FROM PG_MERCHANT PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON PGM.ID = PMEM.MERCHANT_ID")
              .append(" )a where a.ENTITY_ID=:entityId ");
      query.append("   ORDER BY a.CREATED_DATE DESC");
    }
    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("entityId", entityId);
    List<Object> cardProgramResponse = qry.getResultList();
    Map<String, String> merchantMap = new HashMap<String, String>();
    if (StringUtil.isListNotNullNEmpty(cardProgramResponse)) {
      Iterator<Object> itr = cardProgramResponse.iterator();
      while (itr.hasNext()) {
        Object[] object = (Object[]) itr.next();
        BigInteger id = (BigInteger) object[0];
        merchantMap.put(id.toString(), object[1].toString());
      }
    }
    return merchantMap;
  }
}
