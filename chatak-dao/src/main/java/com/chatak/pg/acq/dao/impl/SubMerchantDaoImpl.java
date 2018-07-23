/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.MerchantRequest;
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
  @SuppressWarnings("unchecked")
  @Override
  public GetMerchantListResponse getSubMerchantList(GetMerchantListRequest searchMerchant) {
    logger.info("Entering :: SubMerchantDaoImpl :: getSubMerchantList Method");
    GetMerchantListResponse getMerchantListResponse = new GetMerchantListResponse();
    List<PGMerchant> merchantList = null;
    List<MerchantRequest> subMerchantList = new ArrayList<MerchantRequest>();
    try {

      int startIndex = 0;
      int endIndex = 0;
      Integer totalRecords = searchMerchant.getNoOfRecords();

      if (searchMerchant.getPageIndex() == null || searchMerchant.getPageIndex() == 1) {
        totalRecords = getTotalSubMerchants(searchMerchant);
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
      StringBuilder subMerchantSqlQuery = new StringBuilder();
	  subMerchantSqlQuery.append(" SELECT f.MerchantCode,  f.MerchantName,  f.EntityType,  f.ProgramManagerName,  f.IsoName,");
	  subMerchantSqlQuery.append("   g.CardProgrmName,  f.Email,  f.Country,  f.State,  f.STATUS,  f.City,  f.Currency,");
	  subMerchantSqlQuery.append("   f.ID,  f.CreatedDate FROM");
	  subMerchantSqlQuery.append("   (");
	  subMerchantSqlQuery.append("   SELECT e.MerchantCode,  e.MerchantName,  e.EntityType,  e.ProgramManagerName, group_concat( e.IsoName) AS IsoName,");
	  subMerchantSqlQuery.append("     e.Email,  e.Country,  e.State,  e.STATUS,  e.City,  e.Currency,  e.ID,  e.CreatedDate FROM   ");
	  subMerchantSqlQuery.append("     ( ");
	  subMerchantSqlQuery.append("     SELECT DISTINCT d.MerchantCode, d.MerchantName, d.EntityType, d.ProgramManagerName, d.IsoName,");
	  subMerchantSqlQuery.append("       d.Email,  d.Country, d.State, d.STATUS, d.City, d.Currency, d.ID,  d.CreatedDate  FROM");
	  subMerchantSqlQuery.append("       (");
	  subMerchantSqlQuery.append("       SELECT c.MerchantCode, c.MerchantName, c.EntityType, CASE WHEN group_concat( c.ProgramManagerName) = ''");
	  subMerchantSqlQuery.append("           THEN NULL   ELSE group_concat( c.ProgramManagerName)  END ProgramManagerName,");
	  subMerchantSqlQuery.append("         CASE    WHEN c.IsoName = ''  THEN NULL  ELSE c.IsoName  END IsoName, c.Email, c.Country,");
	  subMerchantSqlQuery.append("         c.State, c.STATUS, c.City, c.Currency, c.ID, c.CreatedDate   FROM");
	  subMerchantSqlQuery.append("         ( ");
	  subMerchantSqlQuery.append("         SELECT DISTINCT b.MerchantCode, b.MerchantName, b.EntityType,b.ProgramManagerName,");
	  subMerchantSqlQuery.append("           b.IsoName, b.Email, b.Country, b.State, b.STATUS, b.City, b.Currency,b.ID,b.CreatedDate");
	  subMerchantSqlQuery.append("         FROM");
	  subMerchantSqlQuery.append("           (SELECT DISTINCT a.MERCHANT_CODE AS MerchantCode,a.BUSINESS_NAME                AS MerchantName,");
	  subMerchantSqlQuery.append("             pgme.ENTITY_TYPE               AS EntityType, pgiso.PROGRAM_MANAGER_NAME     AS ProgramManagerName,");
	  subMerchantSqlQuery.append("             pgisos.ISO_NAME                AS IsoName, a.EMAIL                        AS Email,");
	  subMerchantSqlQuery.append("             a.COUNTRY                      AS Country, a.STATE                        AS State,");
	  subMerchantSqlQuery.append("             a.STATUS,a.CITY           AS City, a.LOCAL_CURRENCY AS Currency,a.ID   AS Id, a.CreatedDate  FROM");
	  subMerchantSqlQuery.append("             (");
	  subMerchantSqlQuery.append("             SELECT pgm.MERCHANT_CODE, pgm.BUSINESS_NAME,  pgm.EMAIL, pgm.COUNTRY, pgm.STATE, pgm.STATUS,");
	  subMerchantSqlQuery.append("               pgm.MERCHANT_TYPE,  pgm.PARENT_MERCHANT_ID,  pgm.CITY,  pgm.LOCAL_CURRENCY,  pgm.ID,");
	  subMerchantSqlQuery.append("               pgm.CREATED_DATE AS CreatedDate   FROM PG_MERCHANT pgm  WHERE pgm.MERCHANT_TYPE='Submerchant'");
	  subMerchantSqlQuery.append("             )a");
	  subMerchantSqlQuery.append("           JOIN PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("           ON pgm.id=a.PARENT_MERCHANT_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_MERCHANT_CARD_PROGRAM_MAPPING pgmcp");
	  subMerchantSqlQuery.append("           ON pgm.id=pgmcp.MERCHANT_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_CARD_PROGRAM pgcp");
	  subMerchantSqlQuery.append("           ON pgcp.ID=pgmcp.CARD_PROGRAM_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN");
	  subMerchantSqlQuery.append("             (SELECT MERCHANT_ID, ENTITY_ID, ENTITY_TYPE  FROM PG_MERCHANT_ENTITY_MAPPING");
	  subMerchantSqlQuery.append("             WHERE ENTITY_ID IS NOT NULL");
	  subMerchantSqlQuery.append("             ) pgme");
	  subMerchantSqlQuery.append("           ON pgme.MERCHANT_ID=pgm.id");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_PROGRAM_MANAGER pgiso");
	  subMerchantSqlQuery.append("           ON pgiso.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_ISO pgisos");
	  subMerchantSqlQuery.append("           ON pgisos.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("           )b");
	  subMerchantSqlQuery.append("         )c");
	  subMerchantSqlQuery.append("       GROUP BY c.MerchantCode, c.MerchantName, c.EntityType, c.IsoName,");
	  subMerchantSqlQuery.append("         c.Email, c.Country, c.State, c.STATUS, c.City, c.Currency, c.ID,");
	  subMerchantSqlQuery.append("         c.CreatedDate");
	  subMerchantSqlQuery.append("       )d");
	  subMerchantSqlQuery.append("     )e");
	  subMerchantSqlQuery.append("   GROUP BY e.MerchantCode,e.MerchantName,e.EntityType,e.ProgramManagerName,");
	  subMerchantSqlQuery.append("     e.Email,e.Country,e.State,e.STATUS,e.City, e.Currency, e.ID, e.CreatedDate");
	  subMerchantSqlQuery.append("   )f");
	  subMerchantSqlQuery.append(" JOIN");
	  subMerchantSqlQuery.append("   (SELECT c.MerchantCode, group_concat( c.CardProgrmName ) AS CardProgrmName");
	  subMerchantSqlQuery.append("   FROM   ( SELECT DISTINCT b.MerchantCode, b.CardProgrmName");
	  subMerchantSqlQuery.append("     FROM");
	  subMerchantSqlQuery.append("       (SELECT DISTINCT a.MERCHANT_CODE AS MerchantCode,");
	  subMerchantSqlQuery.append("         a.BUSINESS_NAME                AS MerchantName,");
	  subMerchantSqlQuery.append("         pgme.ENTITY_TYPE               AS EntityType,");
	  subMerchantSqlQuery.append("         pgiso.PROGRAM_MANAGER_NAME     AS ProgramManagerName,");
	  subMerchantSqlQuery.append("         ''                             AS IsoName,");
	  subMerchantSqlQuery.append("         a.EMAIL                        AS Email,");
	  subMerchantSqlQuery.append("         pgcp.CARD_PROGRAM_NAME         AS CardProgrmName,");
	  subMerchantSqlQuery.append("         a.COUNTRY                      AS Country,");
	  subMerchantSqlQuery.append("         a.STATE                        AS State,");
	  subMerchantSqlQuery.append("         a.STATUS,");
	  subMerchantSqlQuery.append("         a.CITY           AS City,");
	  subMerchantSqlQuery.append("         a.LOCAL_CURRENCY AS Currency,");
	  subMerchantSqlQuery.append("         a.ID             AS Id,");
	  subMerchantSqlQuery.append("         a.CreatedDate");
	  subMerchantSqlQuery.append("       FROM");
	  subMerchantSqlQuery.append("         (SELECT pgm.MERCHANT_CODE,");
	  subMerchantSqlQuery.append("           pgm.BUSINESS_NAME,");
	  subMerchantSqlQuery.append("          pgm.EMAIL,");
	  subMerchantSqlQuery.append("          pgm.COUNTRY,");
	  subMerchantSqlQuery.append("          pgm.STATE,");
	  subMerchantSqlQuery.append("          pgm.STATUS,");
	  subMerchantSqlQuery.append("          pgm.MERCHANT_TYPE,");
	  subMerchantSqlQuery.append("          pgm.PARENT_MERCHANT_ID,");
	  subMerchantSqlQuery.append("          pgm.CITY,");
	  subMerchantSqlQuery.append("          pgm.LOCAL_CURRENCY,");
	  subMerchantSqlQuery.append("          pgm.ID,");
	  subMerchantSqlQuery.append("          pgm.CREATED_DATE AS CreatedDate");
	  subMerchantSqlQuery.append("        FROM PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("        WHERE pgm.MERCHANT_TYPE='Submerchant'");
	  subMerchantSqlQuery.append("        )a");
	  subMerchantSqlQuery.append("      JOIN PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("      ON pgm.id=a.PARENT_MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_CARD_PROGRAM_MAPPING pgmcp");
	  subMerchantSqlQuery.append("      ON pgm.id=pgmcp.MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_CARD_PROGRAM pgcp");
	  subMerchantSqlQuery.append("      ON pgcp.ID=pgmcp.CARD_PROGRAM_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_ENTITY_MAPPING pgme");
	  subMerchantSqlQuery.append("      ON pgme.MERCHANT_ID=pgm.id");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_PROGRAM_MANAGER pgiso");
	  subMerchantSqlQuery.append("      ON pgiso.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("      UNION ALL");
	  subMerchantSqlQuery.append("      SELECT DISTINCT a.MERCHANT_CODE AS MerchantCode,");
	  subMerchantSqlQuery.append("        a.BUSINESS_NAME               AS MerchantName,");
	  subMerchantSqlQuery.append("        pgme.ENTITY_TYPE              AS EntityType,");
	  subMerchantSqlQuery.append("        ''                            AS ProgramManagerName,");
	  subMerchantSqlQuery.append("        pgiso.ISO_NAME                AS IsoName,");
	  subMerchantSqlQuery.append("        a.EMAIL                       AS Email,");
	  subMerchantSqlQuery.append("        pgcp.CARD_PROGRAM_NAME        AS CardProgrmName,");
	  subMerchantSqlQuery.append("        a.COUNTRY                     AS Country,");
	  subMerchantSqlQuery.append("        a.STATE                       AS State,");
	  subMerchantSqlQuery.append("        a.STATUS,");
	  subMerchantSqlQuery.append("        a.CITY           AS City,");
	  subMerchantSqlQuery.append("        a.LOCAL_CURRENCY AS Currency,");
	  subMerchantSqlQuery.append("        a.ID             AS Id,");
	  subMerchantSqlQuery.append("        a.CreatedDate");
	  subMerchantSqlQuery.append("      FROM");
	  subMerchantSqlQuery.append("        (SELECT pgm.MERCHANT_CODE,");
	  subMerchantSqlQuery.append("          pgm.BUSINESS_NAME,");
	  subMerchantSqlQuery.append("          pgm.EMAIL,");
	  subMerchantSqlQuery.append("          pgm.COUNTRY,");
	  subMerchantSqlQuery.append("          pgm.STATE,");
	  subMerchantSqlQuery.append("          pgm.STATUS,");
	  subMerchantSqlQuery.append("          pgm.MERCHANT_TYPE,");
	  subMerchantSqlQuery.append("          pgm.PARENT_MERCHANT_ID,");
	  subMerchantSqlQuery.append("          pgm.CITY,");
	  subMerchantSqlQuery.append("          pgm.LOCAL_CURRENCY,");
	  subMerchantSqlQuery.append("          pgm.ID,");
	  subMerchantSqlQuery.append("          pgm.CREATED_DATE AS CreatedDate");
	  subMerchantSqlQuery.append("        FROM PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("        WHERE pgm.MERCHANT_TYPE='Submerchant'");
	  subMerchantSqlQuery.append("        )a");
	  subMerchantSqlQuery.append("      JOIN PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("      ON pgm.id=a.PARENT_MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_CARD_PROGRAM_MAPPING pgmcp");
	  subMerchantSqlQuery.append("      ON pgm.id=pgmcp.MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_CARD_PROGRAM pgcp");
	  subMerchantSqlQuery.append("      ON pgcp.ID=pgmcp.CARD_PROGRAM_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_ENTITY_MAPPING pgme");
	  subMerchantSqlQuery.append("      ON pgme.MERCHANT_ID=pgm.id");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_ISO pgiso");
	  subMerchantSqlQuery.append("      ON pgiso.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("      )b");
	  subMerchantSqlQuery.append("    )c");
	  subMerchantSqlQuery.append("  GROUP BY c.MerchantCode");
	  subMerchantSqlQuery.append("  )g ON g.MerchantCode=f.MerchantCode");
	  subMerchantSqlQuery.append(" where (:subMerchantCode is null or f.MerchantCode = :subMerchantCode) ");
	  subMerchantSqlQuery.append(" and (:ProgramManagerName is null or f.ProgramManagerName = :ProgramManagerName) ");
	  subMerchantSqlQuery.append(" and (:CardProgrmName is null or g.CardProgrmName = :CardProgrmName) ");
	  subMerchantSqlQuery.append(" and (:MerchantName is null or f.MerchantName = :MerchantName) ");
	  subMerchantSqlQuery.append(" and (:Email is null or f.Email = :Email) ");			  
	  subMerchantSqlQuery.append(" and (:City is null or f.City = :City) ");			  
	  subMerchantSqlQuery.append(" and (:Country is null or f.Country = :Country) ");			  
	  subMerchantSqlQuery.append(" and (:status is null or f.STATUS = :status) ");
	  subMerchantSqlQuery.append(" and (:isoName is null or f.IsoName = :isoName) ");
	  subMerchantSqlQuery.append(" order by f.CreatedDate desc limit :startIndex, :resultSize ");
	  Query subMerchantParam = entityManager.createNativeQuery(subMerchantSqlQuery.toString());
	  subMerchantParam.setParameter("ProgramManagerName", searchProgramManagerName(searchMerchant));
	 subMerchantParam.setParameter("CardProgrmName", searchCardProgramName(searchMerchant));
	 subMerchantParam.setParameter("MerchantName", searchBusinessName(searchMerchant));
	  subMerchantParam.setParameter("City", searchMerchant.getCity() != "" ? searchMerchant.getCity() : null);
		subMerchantParam.setParameter("Country", searchMerchant.getCountry() != "" ? searchMerchant.getCountry() : null);
	 subMerchantParam.setParameter("Email", searchMerchant.getEmailId() != "" ? searchMerchant.getEmailId() : null);
		subMerchantParam.setParameter("subMerchantCode", searchMerchant.getSubMerchantCode() != "" ? searchMerchant.getSubMerchantCode() :  null);
	 subMerchantParam.setParameter("status", searchMerchant.getStatus() != null ? searchMerchant.getStatus() : null);
	 subMerchantParam.setParameter("isoName", searchMerchant.getIsoName() != "" ? searchMerchant.getIsoName() : null);
	 subMerchantParam.setParameter("startIndex" , startIndex);
	subMerchantParam.setParameter("resultSize", resultIndex);
			  List<Object> listOfReport = subMerchantParam.getResultList();
			  getListOfSubMerchants(subMerchantList, listOfReport);
      if (subMerchantList != null && !subMerchantList.isEmpty()) {
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
      logger.error("Error :: SubMerchantDaoImpl :: getSubMerchantList Method", e);
      getMerchantListResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      getMerchantListResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

    logger.info("Exiting :: SubMerchantDaoImpl :: getSubMerchantList Method");
    return getMerchantListResponse;
  }

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object searchBusinessName(GetMerchantListRequest searchMerchant) {
		return searchMerchant.getBusinessName() != "" ? searchMerchant.getBusinessName() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object searchCardProgramName(GetMerchantListRequest searchMerchant) {
		return searchMerchant.getCardProgramName() != "" ? searchMerchant.getCardProgramName() : null;
	}

	/**
	 * @param searchMerchant
	 * @return
	 */
	private Object searchProgramManagerName(GetMerchantListRequest searchMerchant) {
		return searchMerchant.getProgramManagerName() != "" ? searchMerchant.getProgramManagerName() : null;
	}

/**
 * @param subMerchantList
 * @param listOfReport
 */
private void getListOfSubMerchants(List<MerchantRequest> subMerchantList, List<Object> listOfReport) {
	if (StringUtil.isListNotNullNEmpty(listOfReport)) {
		Iterator it = listOfReport.iterator();
		while (it.hasNext()) {
			try {
				Object[] obj = (Object[]) it.next();
				MerchantRequest merchantRequest = new MerchantRequest();
				merchantRequest.setMerchantCode((StringUtil.isNull(obj[0]) ? null : (String) obj[0]));
				merchantRequest.setBusinessName(subMerchantsBusinessName(obj));
				merchantRequest.setEntityType(subMerchantsEntityType(obj));
				if(merchantRequest.getEntityType().equals(PGConstants.PROGRAM_MANAGER_NAME)){
					merchantRequest.setEntityName(subMerchantsProgramManagerName(obj));
				}else {
					merchantRequest.setEntityName(subMerchantsIsoName(obj));
				}
				merchantRequest.setCardProgramName(subMerchantsCardProgramName(obj));
				merchantRequest.setEmailId(subMerchantsEmailId(obj));
				setAddress(obj, merchantRequest);
				merchantRequest.setStatus(Integer.parseInt(obj[9].toString()));
				merchantRequest.setCity(subMerchantCity(obj));
				merchantRequest.setCurrency((StringUtil.isNull(obj[Integer.parseInt("11")]) ? null : (String)obj[Integer.parseInt("11")]));
				merchantRequest.setId((StringUtil.isNull(obj[Integer.parseInt("12")]) ? null : ((BigInteger)obj[Integer.parseInt("12")]).longValue()));
				subMerchantList.add(merchantRequest);
			} catch (Exception e) {
				logger.error("Error :: SubMerchantDaoImpl :: getListOfSubMerchants Method", e);
			}
		}
	}
}

	/**
	 * @param obj
	 * @return
	 */
	private String subMerchantCity(Object[] obj) {
		return StringUtil.isNull(obj[Integer.parseInt("10")]) ? null : (String) obj[Integer.parseInt("10")];
	}

	/**
	 * @param obj
	 * @return
	 */
	private String subMerchantsEmailId(Object[] obj) {
		return StringUtil.isNull(obj[Integer.parseInt("6")]) ? null : (String) obj[Integer.parseInt("6")];
	}

	/**
	 * @param obj
	 * @return
	 */
	private String subMerchantsCardProgramName(Object[] obj) {
		return StringUtil.isNull(obj[Integer.parseInt("5")]) ? null : (String) obj[Integer.parseInt("5")];
	}

	/**
	 * @param obj
	 * @return
	 */
	private String subMerchantsIsoName(Object[] obj) {
		return StringUtil.isNull(obj[Integer.parseInt("4")]) ? null : (String) obj[Integer.parseInt("4")];
	}

	/**
	 * @param obj
	 * @return
	 */
	private String subMerchantsProgramManagerName(Object[] obj) {
		return StringUtil.isNull(obj[Integer.parseInt("3")]) ? null : (String) obj[Integer.parseInt("3")];
	}

	/**
	 * @param obj
	 * @return
	 */
	private String subMerchantsEntityType(Object[] obj) {
		return StringUtil.isNull(obj[Integer.parseInt("2")]) ? null : (String) obj[Integer.parseInt("2")];
	}

	/**
	 * @param obj
	 * @return
	 */
	private String subMerchantsBusinessName(Object[] obj) {
		return StringUtil.isNull(obj[Integer.parseInt("1")]) ? null : (String) obj[Integer.parseInt("1")];
	}

	/**
	 * @param obj
	 * @param merchantRequest
	 */
	private void setAddress(Object[] obj, MerchantRequest merchantRequest) {
		merchantRequest.setCountry(
				(StringUtil.isNull(obj[Integer.parseInt("7")]) ? null : (String) obj[Integer.parseInt("7")]));
		merchantRequest
				.setState((StringUtil.isNull(obj[Integer.parseInt("8")]) ? null : (String) obj[Integer.parseInt("8")]));
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
      pgMerchant.setAgentId(null);
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
	@SuppressWarnings("unchecked")
	protected Integer getTotalSubMerchants(GetMerchantListRequest searchMerchant) {
		StringBuilder subMerchantSqlQuery = new StringBuilder();
	  subMerchantSqlQuery.append("  SELECT f.MerchantCode,  f.MerchantName,  f.EntityType,  f.ProgramManagerName,  f.IsoName,");
	  subMerchantSqlQuery.append("   g.CardProgrmName,  f.Email,  f.Country,  f.State,  f.STATUS,  f.City,  f.Currency,");
	  subMerchantSqlQuery.append("   f.ID,  f.CreatedDate FROM");
	  subMerchantSqlQuery.append("   (");
	  subMerchantSqlQuery.append("   SELECT e.MerchantCode,  e.MerchantName,  e.EntityType,  e.ProgramManagerName, group_concat( e.IsoName) AS IsoName,");
	  subMerchantSqlQuery.append("     e.Email,  e.Country,  e.State,  e.STATUS,  e.City,  e.Currency,  e.ID,  e.CreatedDate FROM   ");
	  subMerchantSqlQuery.append("     ( ");
	  subMerchantSqlQuery.append("     SELECT DISTINCT d.MerchantCode, d.MerchantName, d.EntityType, d.ProgramManagerName, d.IsoName,");
	  subMerchantSqlQuery.append("       d.Email,  d.Country, d.State, d.STATUS, d.City, d.Currency, d.ID,  d.CreatedDate  FROM");
	  subMerchantSqlQuery.append("       (");
	  subMerchantSqlQuery.append("       SELECT c.MerchantCode, c.MerchantName, c.EntityType, CASE WHEN group_concat( c.ProgramManagerName) = ''");
	  subMerchantSqlQuery.append("           THEN NULL   ELSE group_concat( c.ProgramManagerName)  END ProgramManagerName,");
	  subMerchantSqlQuery.append("         CASE    WHEN c.IsoName = ''  THEN NULL  ELSE c.IsoName  END IsoName, c.Email, c.Country,");
	  subMerchantSqlQuery.append("         c.State, c.STATUS, c.City, c.Currency, c.ID, c.CreatedDate   FROM");
	  subMerchantSqlQuery.append("         ( ");
	  subMerchantSqlQuery.append("         SELECT DISTINCT b.MerchantCode, b.MerchantName, b.EntityType,b.ProgramManagerName,");
	  subMerchantSqlQuery.append("           b.IsoName, b.Email, b.Country, b.State, b.STATUS, b.City, b.Currency,b.ID,b.CreatedDate");
	  subMerchantSqlQuery.append("         FROM");
	  subMerchantSqlQuery.append("           (SELECT DISTINCT a.MERCHANT_CODE AS MerchantCode,a.BUSINESS_NAME                AS MerchantName,");
	  subMerchantSqlQuery.append("             pgme.ENTITY_TYPE               AS EntityType, pgiso.PROGRAM_MANAGER_NAME     AS ProgramManagerName,");
	  subMerchantSqlQuery.append("             pgisos.ISO_NAME                AS IsoName, a.EMAIL                        AS Email,");
	  subMerchantSqlQuery.append("             a.COUNTRY                      AS Country, a.STATE                        AS State,");
	  subMerchantSqlQuery.append("             a.STATUS,a.CITY           AS City, a.LOCAL_CURRENCY AS Currency,a.ID   AS Id, a.CreatedDate  FROM");
	  subMerchantSqlQuery.append("             (");
	  subMerchantSqlQuery.append("             SELECT pgm.MERCHANT_CODE, pgm.BUSINESS_NAME,  pgm.EMAIL, pgm.COUNTRY, pgm.STATE, pgm.STATUS,");
	  subMerchantSqlQuery.append("               pgm.MERCHANT_TYPE,  pgm.PARENT_MERCHANT_ID,  pgm.CITY,  pgm.LOCAL_CURRENCY,  pgm.ID,");
	  subMerchantSqlQuery.append("               pgm.CREATED_DATE AS CreatedDate   FROM PG_MERCHANT pgm  WHERE pgm.MERCHANT_TYPE='Submerchant'");
	  subMerchantSqlQuery.append("             )a");
	  subMerchantSqlQuery.append("           JOIN PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("           ON pgm.id=a.PARENT_MERCHANT_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_MERCHANT_CARD_PROGRAM_MAPPING pgmcp");
	  subMerchantSqlQuery.append("           ON pgm.id=pgmcp.MERCHANT_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_CARD_PROGRAM pgcp");
	  subMerchantSqlQuery.append("           ON pgcp.ID=pgmcp.CARD_PROGRAM_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN");
	  subMerchantSqlQuery.append("             (SELECT MERCHANT_ID, ENTITY_ID, ENTITY_TYPE  FROM PG_MERCHANT_ENTITY_MAPPING");
	  subMerchantSqlQuery.append("             WHERE ENTITY_ID IS NOT NULL");
	  subMerchantSqlQuery.append("             ) pgme");
	  subMerchantSqlQuery.append("           ON pgme.MERCHANT_ID=pgm.id");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_PROGRAM_MANAGER pgiso");
	  subMerchantSqlQuery.append("           ON pgiso.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("           LEFT JOIN PG_ISO pgisos");
	  subMerchantSqlQuery.append("           ON pgisos.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("           )b");
	  subMerchantSqlQuery.append("         )c");
	  subMerchantSqlQuery.append("       GROUP BY c.MerchantCode, c.MerchantName, c.EntityType, c.IsoName,");
	  subMerchantSqlQuery.append("         c.Email, c.Country, c.State, c.STATUS, c.City, c.Currency, c.ID,");
	  subMerchantSqlQuery.append("         c.CreatedDate");
	  subMerchantSqlQuery.append("       )d");
	  subMerchantSqlQuery.append("     )e");
	  subMerchantSqlQuery.append("   GROUP BY e.MerchantCode,e.MerchantName,e.EntityType,e.ProgramManagerName,");
	  subMerchantSqlQuery.append("     e.Email,e.Country,e.State,e.STATUS,e.City, e.Currency, e.ID, e.CreatedDate");
	  subMerchantSqlQuery.append("   )f");
	  subMerchantSqlQuery.append(" JOIN");
	  subMerchantSqlQuery.append("   (SELECT c.MerchantCode, group_concat( c.CardProgrmName ) AS CardProgrmName");
	  subMerchantSqlQuery.append("   FROM   ( SELECT DISTINCT b.MerchantCode, b.CardProgrmName");
	  subMerchantSqlQuery.append("     FROM");
	  subMerchantSqlQuery.append("       (SELECT DISTINCT a.MERCHANT_CODE AS MerchantCode,");
	  subMerchantSqlQuery.append("         a.BUSINESS_NAME                AS MerchantName,");
	  subMerchantSqlQuery.append("         pgme.ENTITY_TYPE               AS EntityType,");
	  subMerchantSqlQuery.append("         pgiso.PROGRAM_MANAGER_NAME     AS ProgramManagerName,");
	  subMerchantSqlQuery.append("         ''                             AS IsoName,");
	  subMerchantSqlQuery.append("         a.EMAIL                        AS Email,");
	  subMerchantSqlQuery.append("         pgcp.CARD_PROGRAM_NAME         AS CardProgrmName,");
	  subMerchantSqlQuery.append("         a.COUNTRY                      AS Country,");
	  subMerchantSqlQuery.append("         a.STATE                        AS State,");
	  subMerchantSqlQuery.append("         a.STATUS,");
	  subMerchantSqlQuery.append("         a.CITY           AS City,");
	  subMerchantSqlQuery.append("         a.LOCAL_CURRENCY AS Currency,");
	  subMerchantSqlQuery.append("         a.ID             AS Id,");
	  subMerchantSqlQuery.append("         a.CreatedDate");
	  subMerchantSqlQuery.append("       FROM");
	  subMerchantSqlQuery.append("         (SELECT pgm.MERCHANT_CODE,");
	  subMerchantSqlQuery.append("           pgm.BUSINESS_NAME,");
	  subMerchantSqlQuery.append("          pgm.EMAIL,");
	  subMerchantSqlQuery.append("          pgm.COUNTRY,");
	  subMerchantSqlQuery.append("          pgm.STATE,");
	  subMerchantSqlQuery.append("          pgm.STATUS,");
	  subMerchantSqlQuery.append("          pgm.MERCHANT_TYPE,");
	  subMerchantSqlQuery.append("          pgm.PARENT_MERCHANT_ID,");
	  subMerchantSqlQuery.append("          pgm.CITY,");
	  subMerchantSqlQuery.append("          pgm.LOCAL_CURRENCY,");
	  subMerchantSqlQuery.append("          pgm.ID,");
	  subMerchantSqlQuery.append("          pgm.CREATED_DATE AS CreatedDate");
	  subMerchantSqlQuery.append("        FROM PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("        WHERE pgm.MERCHANT_TYPE='Submerchant'");
	  subMerchantSqlQuery.append("        )a");
	  subMerchantSqlQuery.append("      JOIN PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("      ON pgm.id=a.PARENT_MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_CARD_PROGRAM_MAPPING pgmcp");
	  subMerchantSqlQuery.append("      ON pgm.id=pgmcp.MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_CARD_PROGRAM pgcp");
	  subMerchantSqlQuery.append("      ON pgcp.ID=pgmcp.CARD_PROGRAM_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_ENTITY_MAPPING pgme");
	  subMerchantSqlQuery.append("      ON pgme.MERCHANT_ID=pgm.id");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_PROGRAM_MANAGER pgiso");
	  subMerchantSqlQuery.append("      ON pgiso.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("      UNION ALL");
	  subMerchantSqlQuery.append("      SELECT DISTINCT a.MERCHANT_CODE AS MerchantCode,");
	  subMerchantSqlQuery.append("        a.BUSINESS_NAME               AS MerchantName,");
	  subMerchantSqlQuery.append("        pgme.ENTITY_TYPE              AS EntityType,");
	  subMerchantSqlQuery.append("        ''                            AS ProgramManagerName,");
	  subMerchantSqlQuery.append("        pgiso.ISO_NAME                AS IsoName,");
	  subMerchantSqlQuery.append("        a.EMAIL                       AS Email,");
	  subMerchantSqlQuery.append("        pgcp.CARD_PROGRAM_NAME        AS CardProgrmName,");
	  subMerchantSqlQuery.append("        a.COUNTRY                     AS Country,");
	  subMerchantSqlQuery.append("        a.STATE                       AS State,");
	  subMerchantSqlQuery.append("        a.STATUS,");
	  subMerchantSqlQuery.append("        a.CITY           AS City,");
	  subMerchantSqlQuery.append("        a.LOCAL_CURRENCY AS Currency,");
	  subMerchantSqlQuery.append("        a.ID             AS Id,");
	  subMerchantSqlQuery.append("        a.CreatedDate");
	  subMerchantSqlQuery.append("      FROM");
	  subMerchantSqlQuery.append("        (SELECT pgm.MERCHANT_CODE,");
	  subMerchantSqlQuery.append("          pgm.BUSINESS_NAME,");
	  subMerchantSqlQuery.append("          pgm.EMAIL,");
	  subMerchantSqlQuery.append("          pgm.COUNTRY,");
	  subMerchantSqlQuery.append("          pgm.STATE,");
	  subMerchantSqlQuery.append("          pgm.STATUS,");
	  subMerchantSqlQuery.append("          pgm.MERCHANT_TYPE,");
	  subMerchantSqlQuery.append("          pgm.PARENT_MERCHANT_ID,");
	  subMerchantSqlQuery.append("          pgm.CITY,");
	  subMerchantSqlQuery.append("          pgm.LOCAL_CURRENCY,");
	  subMerchantSqlQuery.append("          pgm.ID,");
	  subMerchantSqlQuery.append("          pgm.CREATED_DATE AS CreatedDate");
	  subMerchantSqlQuery.append("        FROM PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("        WHERE pgm.MERCHANT_TYPE='Submerchant'");
	  subMerchantSqlQuery.append("        )a");
	  subMerchantSqlQuery.append("      JOIN PG_MERCHANT pgm");
	  subMerchantSqlQuery.append("      ON pgm.id=a.PARENT_MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_CARD_PROGRAM_MAPPING pgmcp");
	  subMerchantSqlQuery.append("      ON pgm.id=pgmcp.MERCHANT_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_CARD_PROGRAM pgcp");
	  subMerchantSqlQuery.append("      ON pgcp.ID=pgmcp.CARD_PROGRAM_ID");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_MERCHANT_ENTITY_MAPPING pgme");
	  subMerchantSqlQuery.append("      ON pgme.MERCHANT_ID=pgm.id");
	  subMerchantSqlQuery.append("      LEFT JOIN PG_ISO pgiso");
	  subMerchantSqlQuery.append("      ON pgiso.id=pgme.ENTITY_ID");
	  subMerchantSqlQuery.append("      )b");
	  subMerchantSqlQuery.append("    )c");
	  subMerchantSqlQuery.append("  GROUP BY c.MerchantCode");
	  subMerchantSqlQuery.append("  )g ON g.MerchantCode=f.MerchantCode");
	  subMerchantSqlQuery.append(" where (:subMerchantCode is null or f.MerchantCode = :subMerchantCode) ");
	  subMerchantSqlQuery.append(" and (:ProgramManagerName is null or f.ProgramManagerName = :ProgramManagerName) ");
	  subMerchantSqlQuery.append(" and (:CardProgrmName is null or g.CardProgrmName = :CardProgrmName) ");
	  subMerchantSqlQuery.append(" and (:MerchantName is null or f.MerchantName = :MerchantName) ");
	  subMerchantSqlQuery.append(" and (:Email is null or f.Email = :Email) ");			  
	  subMerchantSqlQuery.append(" and (:City is null or f.City = :City) ");			  
	  subMerchantSqlQuery.append(" and (:Country is null or f.Country = :Country) ");			  
	  subMerchantSqlQuery.append(" and (:status is null or f.STATUS = :status) ");
	  subMerchantSqlQuery.append(" and (:isoName is null or f.IsoName = :isoName) ");
		Query subMerchantParam = entityManager.createNativeQuery(subMerchantSqlQuery.toString());
		subMerchantParam.setParameter("ProgramManagerName",
				searchProgramManagerName(searchMerchant));
		subMerchantParam.setParameter("CardProgrmName",
				searchCardProgramName(searchMerchant));
		subMerchantParam.setParameter("MerchantName",
				searchBusinessName(searchMerchant));
		subMerchantParam.setParameter("City", searchMerchant.getCity() != "" ? searchMerchant.getCity() : null);
		subMerchantParam.setParameter("Country",
				searchMerchant.getCountry() != "" ? searchMerchant.getCountry() : null);
		subMerchantParam.setParameter("Email", searchMerchant.getEmailId() != "" ? searchMerchant.getEmailId() : null);
		subMerchantParam.setParameter("subMerchantCode",
				searchMerchant.getSubMerchantCode() != "" ? searchMerchant.getSubMerchantCode() : null);
		subMerchantParam.setParameter("status", searchMerchant.getStatus() != null ? searchMerchant.getStatus() : null);
		subMerchantParam.setParameter("isoName", searchMerchant.getIsoName() != "" ? searchMerchant.getIsoName() : null);
		List<Object> subMerchantList = subMerchantParam.getResultList();
		return (StringUtils.isListNotNullNEmpty(subMerchantList) ? subMerchantList.size() : 0);
	}

  @Override
  public List<Map<String, String>> getSubMerchantCodeAndCompanyName(String merchantCode) {
    return merchantRepository.getSubMerchantMapByMerchantId(merchantCode);
  }
}
