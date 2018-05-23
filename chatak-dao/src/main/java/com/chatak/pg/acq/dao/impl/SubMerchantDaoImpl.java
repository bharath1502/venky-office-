/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.SubMerchantDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.user.bean.GetMerchantListRequest;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: Aug 21, 2017
 * @Time: 7:16:48 PM
 * @Version: 1.0
 * @Comments: 
 *
 */

@Repository("subMerchantDao")
public class SubMerchantDaoImpl extends MerchantDaoImpl implements SubMerchantDao {

  private static Logger logger = Logger.getLogger(SubMerchantDaoImpl.class);

  @Autowired
  private MerchantRepository merchantRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public GetMerchantListResponse getMerchantlistOnSubMerchantCode(
      GetMerchantListRequest searchMerchant) {

    logger.info("Entering :: SubMerchantDaoImpl :: getMerchantlistOnSubMerchantCode Method");
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    List<PGMerchant> merchantList = null;
    List<PGMerchant> subMerchantList = null;
    try {

      int limit = 0;
      int offset = 0;
      Integer totalRecords = searchMerchant.getNoOfRecords();

      if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
        totalRecords = getTotalSubMerchantOnMerchantCode(searchMerchant);
        searchMerchant.setNoOfRecords(totalRecords);
      }
      getMerchantListResponse.setNoOfRecords(totalRecords);
      if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
        limit = Constants.DEFAULT_PAGE_SIZE;
        offset = 0;
      } else {
        offset = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
        limit = searchMerchant.getPageSize();
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant)
          .where(isBusinessNameLike(searchMerchant.getBusinessName()),
              isMerchantCodeEq(searchMerchant.getMerchantCode()),
              isCityLike(searchMerchant.getCity()), isCountryEq(searchMerchant.getCountry()),
              isEmailLike(searchMerchant.getEmailId()),
              isFirstNameLike(searchMerchant.getFirstName()),
              isLastNameLike(searchMerchant.getLastName()), isPhoneEq(searchMerchant.getPhone()),
              isStatusEq(searchMerchant.getStatus()),
              isSubMerchantCodeEq(searchMerchant.getSubMerchantCode()), isMerchantNotEq(),
              isMerchantStatusNotEq())
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.firstName,
              QPGMerchant.pGMerchant.lastName, QPGMerchant.pGMerchant.emailId,
              QPGMerchant.pGMerchant.phone, QPGMerchant.pGMerchant.city,
              QPGMerchant.pGMerchant.country, QPGMerchant.pGMerchant.status,
              QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.parentMerchantId,
              QPGMerchant.pGMerchant.id, QPGMerchant.pGMerchant.agentAccountNumber,
              QPGMerchant.pGMerchant.agentANI, QPGMerchant.pGMerchant.agentClientId);
      if (!CollectionUtils.isEmpty(tupleList)) {
    	  PGMerchant merchant = null;
        merchantList = new ArrayList<>();
        subMerchantList = new ArrayList<>();
        for (Tuple tuple : tupleList) {
          merchant = new PGMerchant();
          merchant.setLastName(tuple.get(QPGMerchant.pGMerchant.lastName));
          merchant.setId(tuple.get(QPGMerchant.pGMerchant.id));
          merchant.setBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
          merchant.setFirstName(tuple.get(QPGMerchant.pGMerchant.firstName));
          merchant.setCity(tuple.get(QPGMerchant.pGMerchant.city));
          merchant.setPhone(tuple.get(QPGMerchant.pGMerchant.phone));
          merchant.setEmailId(tuple.get(QPGMerchant.pGMerchant.emailId));
          merchant.setCountry(tuple.get(QPGMerchant.pGMerchant.country));
          merchant.setStatus(tuple.get(QPGMerchant.pGMerchant.status));
          merchant.setAgentAccountNumber(tuple.get(QPGMerchant.pGMerchant.agentAccountNumber));
          merchant.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));
          merchant.setParentMerchantId(tuple.get(QPGMerchant.pGMerchant.parentMerchantId));
          merchant.setAgentANI(tuple.get(QPGMerchant.pGMerchant.agentANI));
          merchant.setAgentClientId(tuple.get(QPGMerchant.pGMerchant.agentClientId));

          subMerchantList.add(merchant);
        }
        if (CommonUtil.isListNotNullAndEmpty(subMerchantList)
            && null != subMerchantList.get(0).getParentMerchantId()) {

          PGMerchant parentMerchant =
              merchantRepository.findById(subMerchantList.get(0).getParentMerchantId());
          merchantList.add(parentMerchant);
        }
      }
      if (merchantList != null && !merchantList.isEmpty()) {
        getMerchantListResponse.setMerchants(merchantList);
        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        getMerchantListResponse.setSubMerchants(subMerchantList);
        getMerchantListResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        getMerchantListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }

    } catch (Exception e) {
      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getMerchantListResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("Error :: SubMerchantDaoImpl :: getMerchantlistOnSubMerchantCode Method", e);
    }

    logger.info("Exiting :: SubMerchantDaoImpl :: getMerchantlistOnSubMerchantCode Method");
    return getMerchantListResponse;
  }

  @Override
  public GetMerchantListResponse getSubMerchantListOnMerchantId(
      GetMerchantListRequest searchMerchant) {

    logger.info("Entering :: SubMerchantDaoImpl :: getSubMerchantListOnMerchantId Method");
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    List<PGMerchant> merchantList = null;
    List<PGMerchant> subMerchantList = null;
    try {

      int offset = 0;
      int limit = 0;
      Integer totalRecords = searchMerchant.getNoOfRecords();

      if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
        totalRecords = getTotalSubMerchantOnMerchantCode(searchMerchant);
        searchMerchant.setNoOfRecords(totalRecords);
      }
      getMerchantListResponse.setNoOfRecords(totalRecords);
      if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
        limit = searchMerchant.getPageSize();
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant)
          .where(isParentMerchantIdAlwaysEq(searchMerchant.getId()),
              isBusinessNameLike(searchMerchant.getBusinessName()),
              isMerchantCodeEq(searchMerchant.getMerchantCode()),
              isCityLike(searchMerchant.getCity()), isCountryEq(searchMerchant.getCountry()),
              isEmailLike(searchMerchant.getEmailId()),
              isFirstNameLike(searchMerchant.getFirstName()),
              isLastNameLike(searchMerchant.getLastName()), isPhoneEq(searchMerchant.getPhone()),
              isStatusEq(searchMerchant.getStatus()), isSubMerchantStatusNotEq())
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.firstName,
              QPGMerchant.pGMerchant.lastName, QPGMerchant.pGMerchant.emailId,
              QPGMerchant.pGMerchant.phone, QPGMerchant.pGMerchant.city,
              QPGMerchant.pGMerchant.country, QPGMerchant.pGMerchant.status,
              QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.parentMerchantId,
              QPGMerchant.pGMerchant.id, QPGMerchant.pGMerchant.agentAccountNumber,
              QPGMerchant.pGMerchant.agentANI, QPGMerchant.pGMerchant.agentClientId,
              QPGMerchant.pGMerchant.localCurrency);
      if (!CollectionUtils.isEmpty(tupleList)) {
        merchantList = new ArrayList<>();
        subMerchantList = new ArrayList<>();
        PGMerchant merchant = null;
        for (Tuple tuple : tupleList) {
          merchant = new PGMerchant();
          merchant.setFirstName(tuple.get(QPGMerchant.pGMerchant.firstName));
          merchant.setId(tuple.get(QPGMerchant.pGMerchant.id));
          merchant.setBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
          merchant.setLastName(tuple.get(QPGMerchant.pGMerchant.lastName));
          merchant.setPhone(tuple.get(QPGMerchant.pGMerchant.phone));
          merchant.setEmailId(tuple.get(QPGMerchant.pGMerchant.emailId));
          merchant.setCountry(tuple.get(QPGMerchant.pGMerchant.country));
          merchant.setStatus(tuple.get(QPGMerchant.pGMerchant.status));
          merchant.setCity(tuple.get(QPGMerchant.pGMerchant.city));
          merchant.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));
          merchant.setParentMerchantId(tuple.get(QPGMerchant.pGMerchant.parentMerchantId));
          merchant.setAgentANI(tuple.get(QPGMerchant.pGMerchant.agentANI));
          merchant.setAgentClientId(tuple.get(QPGMerchant.pGMerchant.agentClientId));
          merchant.setAgentAccountNumber(tuple.get(QPGMerchant.pGMerchant.agentAccountNumber));
          merchant.setLocalCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
          subMerchantList.add(merchant);
        }
      }
      if (subMerchantList != null && !subMerchantList.isEmpty()) {
        getMerchantListResponse.setMerchants(merchantList);
        getMerchantListResponse.setSubMerchants(subMerchantList);
        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        getMerchantListResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
    	  getMerchantListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
    	  getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      }

    } catch (Exception e) {
      logger.error("Error :: SubMerchantDaoImpl :: getSubMerchantListOnMerchantId Method", e);
      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getMerchantListResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

    logger.info("Exiting :: SubMerchantDaoImpl :: getSubMerchantListOnMerchantId Method");
    return getMerchantListResponse;

  }

  /**
   * @param searchMerchant
   * @return
   */
  @Override
  public GetMerchantListResponse getSubMerchantList(GetMerchantListRequest searchMerchant) {
    logger.info("Entering :: SubMerchantDaoImpl :: getSubMerchantList Method");
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    List<PGMerchant> merchantList = null;
    List<PGMerchant> subMerchantList = null;
    try {

      int offset = 0;
      int limit = 0;
      Integer totalRecords = searchMerchant.getNoOfRecords();

      if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
        totalRecords = getTotalSubMerchants(searchMerchant);
        searchMerchant.setNoOfRecords(totalRecords);
      }
      getMerchantListResponse.setNoOfRecords(totalRecords);
      if (searchMerchant.getPageIndex() == null && searchMerchant.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (searchMerchant.getPageIndex() - 1) * searchMerchant.getPageSize();
        limit = searchMerchant.getPageSize();
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant)
          .where(QPGMerchant.pGMerchant.merchantType.eq(PGConstants.SUB_MERCHANT),
              isBusinessNameLike(searchMerchant.getBusinessName()),
              isMerchantCodeEq(searchMerchant.getSubMerchantCode()),
              isSubMerchantIdEq((null != searchMerchant.getMerchantCode()
                  && "" != searchMerchant.getMerchantCode())
                      ? getMerchantIdOnMerchantCode(searchMerchant.getMerchantCode()).toString()
                      : null),
              isCityLike(searchMerchant.getCity()), isCountryEq(searchMerchant.getCountry()),
              isEmailLike(searchMerchant.getEmailId()),
              isFirstNameLike(searchMerchant.getFirstName()),
              isLastNameLike(searchMerchant.getLastName()), isPhoneEq(searchMerchant.getPhone()),
              isStatusEq(searchMerchant.getStatus()), isSubMerchantStatusNotEq())
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
          .list(QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.firstName,
              QPGMerchant.pGMerchant.lastName, QPGMerchant.pGMerchant.emailId,
              QPGMerchant.pGMerchant.phone, QPGMerchant.pGMerchant.city,
              QPGMerchant.pGMerchant.country, QPGMerchant.pGMerchant.status,
              QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.parentMerchantId,
              QPGMerchant.pGMerchant.id, QPGMerchant.pGMerchant.agentAccountNumber,
              QPGMerchant.pGMerchant.agentANI, QPGMerchant.pGMerchant.agentClientId,QPGMerchant.pGMerchant.localCurrency);
      if (!CollectionUtils.isEmpty(tupleList)) {
        merchantList = new ArrayList<>();
        subMerchantList = new ArrayList<>();
        PGMerchant merchant = null;
        for (Tuple tuple : tupleList) {
          merchant = new PGMerchant();
          merchant.setId(tuple.get(QPGMerchant.pGMerchant.id));
          merchant.setBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
          merchant.setFirstName(tuple.get(QPGMerchant.pGMerchant.firstName));
          merchant.setLastName(tuple.get(QPGMerchant.pGMerchant.lastName));
          merchant.setPhone(tuple.get(QPGMerchant.pGMerchant.phone));
          merchant.setEmailId(tuple.get(QPGMerchant.pGMerchant.emailId));
          merchant.setCity(tuple.get(QPGMerchant.pGMerchant.city));
          merchant.setCountry(tuple.get(QPGMerchant.pGMerchant.country));
          merchant.setStatus(tuple.get(QPGMerchant.pGMerchant.status));
          merchant.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));
          merchant.setParentMerchantId(tuple.get(QPGMerchant.pGMerchant.parentMerchantId));
          merchant.setAgentAccountNumber(tuple.get(QPGMerchant.pGMerchant.agentAccountNumber));
          merchant.setAgentANI(tuple.get(QPGMerchant.pGMerchant.agentANI));
          merchant.setAgentClientId(tuple.get(QPGMerchant.pGMerchant.agentClientId));
          merchant.setLocalCurrency(tuple.get(QPGMerchant.pGMerchant.localCurrency));
          subMerchantList.add(merchant);
        }
      }
      if (subMerchantList != null && !subMerchantList.isEmpty()) {
        getMerchantListResponse.setSubMerchants(subMerchantList);
        getMerchantListResponse.setMerchants(merchantList);
        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        getMerchantListResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        getMerchantListResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }

    } catch (Exception e) {
      logger.error("Error :: SubMerchantDaoImpl :: getSubMerchantList Method", e);
      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getMerchantListResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

    logger.info("Exiting :: SubMerchantDaoImpl :: getSubMerchantList Method");
    return getMerchantListResponse;
  }

  /**
   * @param merchantCode
   * @return
   */
  @Override
  public List<String> getMerchantAndSubMerchantList(String merchantCode) {
    QPGMerchant tempMerchant = new QPGMerchant("parentMerchant");
    JPAQuery query = new JPAQuery(entityManager);
    return query.from(QPGMerchant.pGMerchant)
        .where(QPGMerchant.pGMerchant.merchantCode.eq(merchantCode)
            .or((QPGMerchant.pGMerchant.parentMerchantId.in(new JPASubQuery().from(tempMerchant)
                .where(tempMerchant.merchantCode.eq(merchantCode)).list(tempMerchant.id)))))
        .list(QPGMerchant.pGMerchant.merchantCode);
  }

  /**
   * @param submerchantsList
   */
  @Override
  public void updateSubMerchantsPartnerAndAgentId(List<PGMerchant> submerchantsList) {

    for (PGMerchant pgMerchant : submerchantsList) {
      pgMerchant.setIssuancePartnerId(null);
      pgMerchant.setAgentId(null);
      pgMerchant.setProgramManagerId(null);
      pgMerchant.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
      merchantRepository.save(pgMerchant);
    }

  }

  public int getTotalSubMerchantOnMerchantCode(GetMerchantListRequest searchMerchant) {
    JPAQuery query = new JPAQuery(entityManager);
    if (null == searchMerchant.getId()) {
      return 0;
    }
    List<Long> list = query.from(QPGMerchant.pGMerchant)
        .where(isBusinessNameLike(searchMerchant.getBusinessName()),
            isCityLike(searchMerchant.getCity()), isCountryEq(searchMerchant.getCountry()),
            isEmailLike(searchMerchant.getEmailId()),
            isFirstNameLike(searchMerchant.getFirstName()),
            isLastNameLike(searchMerchant.getLastName()), isPhoneEq(searchMerchant.getPhone()),
            QPGMerchant.pGMerchant.parentMerchantId.eq(searchMerchant.getId()),
            isStatusEqSelfRegistered(searchMerchant.getStatus()), isMerchantNotEq(),
            isMerchantStatusNotEq())
        .list(QPGMerchant.pGMerchant.id);

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  /**
   * @param searchMerchant
   * @return
   */
  protected Integer getTotalSubMerchants(GetMerchantListRequest searchMerchant) {
    JPAQuery query = new JPAQuery(entityManager);

    List<Long> list = query.from(QPGMerchant.pGMerchant)
        .where(QPGMerchant.pGMerchant.merchantType.eq("SubMerchant"),
            isBusinessNameLike(
                searchMerchant.getBusinessName()),
        isMerchantCodeEq(searchMerchant.getSubMerchantCode()),
        isSubMerchantIdEq(
            (null != searchMerchant.getMerchantCode() && "" != searchMerchant.getMerchantCode())
                ? getMerchantIdOnMerchantCode(searchMerchant.getMerchantCode()).toString() : null),
        isCityLike(searchMerchant.getCity()), isCountryEq(searchMerchant.getCountry()),
        isEmailLike(searchMerchant.getEmailId()), isFirstNameLike(searchMerchant.getFirstName()),
        isLastNameLike(searchMerchant.getLastName()), isPhoneEq(searchMerchant.getPhone()),
        isStatusEq(searchMerchant.getStatus()), isSubMerchantStatusNotEq())
        .list(QPGMerchant.pGMerchant.id);

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  @Override
  public List<Map<String, String>> getSubMerchantCodeAndCompanyName(String merchantCode) {
    return merchantRepository.getSubMerchantMapByMerchantId(merchantCode);
  }
}
