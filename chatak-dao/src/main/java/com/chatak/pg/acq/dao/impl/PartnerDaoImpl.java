package com.chatak.pg.acq.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.PartnerDao;
import com.chatak.pg.acq.dao.model.BankPartnerMap;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.Partner;
import com.chatak.pg.acq.dao.model.PartnerAccount;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.QBankPartnerMap;
import com.chatak.pg.acq.dao.model.QPGBank;
import com.chatak.pg.acq.dao.model.QPartner;
import com.chatak.pg.acq.dao.model.QPartnerAccount;
import com.chatak.pg.acq.dao.model.QProgramManager;
import com.chatak.pg.acq.dao.repository.BankPartnerRepository;
import com.chatak.pg.acq.dao.repository.PartnerAccountRepository;
import com.chatak.pg.acq.dao.repository.PartnerRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.enums.AccountType;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.user.bean.BankPartnerMapRequest;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.PartnerAccountRequest;
import com.chatak.pg.user.bean.PartnerRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@SuppressWarnings({"unchecked", "rawtypes"})
@Repository("partnerDao")
public class PartnerDaoImpl implements PartnerDao {

  private final String className = PartnerDaoImpl.class.getSimpleName();

  private Logger logger = LogManager.getLogger(this.getClass());

  @Autowired
  PartnerRepository partnerRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  PartnerAccountRepository partnerAccountRepository;

  @Autowired
  private BankPartnerRepository bankPartnerRepository;

  @Override
  public Partner saveOrUpdatePartner(Partner partner) throws DataAccessException {
    return partnerRepository.save(partner);
  }

  @Override
  public Set<BankPartnerMap> findBankPartnerMapByPartnerId(Long partnerId)
      throws DataAccessException {
    return bankPartnerRepository.findByPartnerId(partnerId);
  }

  @Override
  public void deleteBankPartner(Set<BankPartnerMap> bankPartnerMap) throws DataAccessException {
    bankPartnerRepository.delete(bankPartnerMap);
  }

  @Override
  public Partner findByPartnerId(Long partnerId) throws DataAccessException {
    return partnerRepository.findByPartnerId(partnerId);
  }

  @Override
  public List<Partner> findByPartnerName(String partnerName) throws DataAccessException {
    JPAQuery query = new JPAQuery(entityManager);
    List<Partner> list = query.from(QPartner.partner)
        .where(
            QPartner.partner.partnerName.toLowerCase().equalsIgnoreCase(partnerName.toLowerCase()))
        .list(QPartner.partner);
    return (StringUtil.isListNotNullNEmpty(list) ? list : null);
  }

  @Override
  public PartnerRequest getDetailsByPartnerId(PartnerRequest partnerRequest)
      throws PrepaidAdminException {

    if (StringUtil.isNull(partnerRequest)) {
      throw new PrepaidAdminException(Constants.PARTNER_ID_IS_EMPTY,
          "Partner Request cannot be empty");
    }

    if (StringUtil.isNull(partnerRequest.getPartnerId())) {
      throw new PrepaidAdminException(Constants.PARTNER_ID_IS_EMPTY, "Partner Id cannot be empty");
    }

    PartnerRequest partnerRequest2 = null;

    try {
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPartner.partner, QProgramManager.programManager)
          .where(isPartnerId(partnerRequest.getPartnerId()),
              QPartner.partner.programManagerId.eq(QProgramManager.programManager.id))
          .list(QPartner.partner, QProgramManager.programManager);

      if (StringUtil.isListNotNullNEmpty(tupleList)) {
        for (Tuple tuple : tupleList) {
          Partner partner = tuple.get(QPartner.partner);
          partnerRequest2 = CommonUtil.copyBeanProperties(partner, PartnerRequest.class);

          ProgramManager programManager = tuple.get(QProgramManager.programManager);
          partnerRequest2.setProgramManagerRequest((ProgramManagerRequest) CommonUtil
              .copyBeanProperties(programManager, ProgramManagerRequest.class));

          List<PartnerAccount> partnerAccountList = query.from(QPartnerAccount.partnerAccount)
              .where(QPartnerAccount.partnerAccount.partnerId.eq(partner.getPartnerId())).distinct()
              .list(QPartnerAccount.partnerAccount);

          getPartnerAccountList(partnerRequest2, partnerAccountList);

          List<Tuple> tupleList2 = query.from(QBankPartnerMap.bankPartnerMap, QPGBank.pGBank)
              .where(QBankPartnerMap.bankPartnerMap.partnerId.eq(partner.getPartnerId()))
              .orderBy(QPGBank.pGBank.bankName.asc()).distinct()
              .list(QBankPartnerMap.bankPartnerMap, QPGBank.pGBank);

          processToupleData(partnerRequest2, tupleList2);
        }
      }
    } catch (Exception e) {
      logger.error(
          className + " : getDetailsByPartnerId : Error in retrieving the partner details.", e);
    }

    return partnerRequest2;
  }

  private void processToupleData(PartnerRequest partnerRequest2, List<Tuple> tupleList2)
      throws InstantiationException, IllegalAccessException {
    if (StringUtil.isListNotNullNEmpty(tupleList2)) {
      List<BankPartnerMapRequest> bankPartnerMaps = new ArrayList<>();
      List<BankRequest> banks = new ArrayList<>();
      for (Tuple tuple2 : tupleList2) {
        BankPartnerMap bankPartnerMap = tuple2.get(QBankPartnerMap.bankPartnerMap);
        PGBank bank = tuple2.get(QPGBank.pGBank);
        bankPartnerMaps.add((BankPartnerMapRequest) CommonUtil.copyBeanProperties(bankPartnerMap,
            BankPartnerMapRequest.class));
        banks.add((BankRequest) CommonUtil.copyBeanProperties(bank, BankRequest.class));
      }
      partnerRequest2.setBankRequests(banks);
      partnerRequest2.setBankPartnerMapRequests(bankPartnerMaps);
    }
  }

  private void getPartnerAccountList(PartnerRequest partnerRequest2,
      List<PartnerAccount> partnerAccountList)
          throws InstantiationException, IllegalAccessException {
    if (StringUtil.isListNotNullNEmpty(partnerAccountList)) {
      List<PartnerAccountRequest> partnerAccountRequestList = new ArrayList<>();
      PartnerAccountRequest partnerAccountRequest = null;
      for (PartnerAccount partneraccount : partnerAccountList) {
        partnerAccountRequest =
            CommonUtil.copyBeanProperties(partneraccount, PartnerAccountRequest.class);
        partnerAccountRequest
            .setAvailableBalance(CommonUtil.getDoubleAmount(partneraccount.getAvailableBalance()));
        partnerAccountRequest
            .setCurrentBalance(CommonUtil.getDoubleAmount(partneraccount.getCurrentBalance()));
        partnerAccountRequest.setUpdatedDate(partneraccount.getUpdatedDate());
        partnerAccountRequestList.add(partnerAccountRequest);
      }
      partnerRequest2.setPartnerAccountRequests(partnerAccountRequestList);
    }
  }

  /**
   * Service method for search partner
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public List<PartnerRequest> searchPartner(PartnerRequest partnerRequest)
      throws DataAccessException {
    List<PartnerRequest> partnerRequests = new ArrayList<>();
    int offset = 0;
    int limit = 0;
    Integer totalRecords;

    if (partnerRequest.getPageIndex() == null || partnerRequest.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfRecords(partnerRequest);
      partnerRequest.setNoOfRecords(totalRecords);
    }

    if (partnerRequest.getPageIndex() == null && partnerRequest.getPageSize() == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (partnerRequest.getPageIndex() - 1) * partnerRequest.getPageSize();
      limit = partnerRequest.getPageSize();
    }
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> tupleList = query
        .from(QPartner.partner, QProgramManager.programManager, QBankPartnerMap.bankPartnerMap,
            QPGBank.pGBank)
        .where(isCompanyNameLike(partnerRequest.getCompanyName()),
            isPartnerNameLike(partnerRequest.getPartnerName()),
            isPartnerType(partnerRequest.getPartnerType()),
            isPartnerId(partnerRequest.getPartnerId()), isStatusIn(partnerRequest.getStatusList()),
            isBankId(partnerRequest.getBankName()),
            isProgramManagerId(partnerRequest.getProgramManagerId()),
            isStatus(partnerRequest.getStatus()),
            QPartner.partner.programManagerId.eq(QProgramManager.programManager.id),
            QPGBank.pGBank.id.eq(QBankPartnerMap.bankPartnerMap.bankId),
            QPartner.partner.partnerId.eq(QBankPartnerMap.bankPartnerMap.partnerId))
        .offset(offset).limit(limit).orderBy(orderByPartnerIdDesc()).distinct()
        .list(QPartner.partner.partnerId, QPartner.partner.companyName,
            QPartner.partner.businessEntityName, QPartner.partner.partnerName,
            QPartner.partner.status, QProgramManager.programManager.id,
            QProgramManager.programManager.programManagerName, QPartner.partner.accountCurrency);
    if (StringUtil.isListNotNullNEmpty(tupleList)) {
      for (Tuple tuple : tupleList) {
        PartnerRequest partnerRequest2 = new PartnerRequest();
        ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
        partnerRequest2.setPartnerId(tuple.get(QPartner.partner.partnerId));
        partnerRequest2.setCompanyName(tuple.get(QPartner.partner.companyName));
        partnerRequest2.setBusinessEntityName(tuple.get(QPartner.partner.businessEntityName));
        partnerRequest2.setPartnerName(tuple.get(QPartner.partner.partnerName));
        partnerRequest2.setStatus(tuple.get(QPartner.partner.status));
        partnerRequest2.setAccountCurrency(tuple.get(QPartner.partner.accountCurrency));
        programManagerRequest.setId(tuple.get(QProgramManager.programManager.id));
        programManagerRequest
            .setProgramManagerName(tuple.get(QProgramManager.programManager.programManagerName));
        partnerRequest2.setProgramManagerRequest(programManagerRequest);
        partnerRequests.add(partnerRequest2);
      }
    }

    return partnerRequests;
  }

  /**
   * Service method for getting all partner id
   * 
   * @param request
   * @param partnerRequest
   * @return
   */
  public List<PartnerRequest> getAllPartners(PartnerRequest partnerRequestStatus)
      throws DataAccessException {
    List<PartnerRequest> response = new ArrayList<>();
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> tuple = query.from(QPartner.partner, QProgramManager.programManager)
        .where(nonSystemPartner(), isPartnerId(partnerRequestStatus.getPartnerId()),
            isPartnerTypeIn(partnerRequestStatus.getPartnerTypeList()),
            isProgramManagerId(partnerRequestStatus.getProgramManagerId()),
        isStatusIn(partnerRequestStatus.getStatusList()),
        QProgramManager.programManager.id.eq(QPartner.partner.programManagerId))
        .list(QPartner.partner.partnerId, QPartner.partner.partnerName, QPartner.partner.status,
            QPartner.partner.programManagerId, QPartner.partner.accountCurrency,
            QProgramManager.programManager.programManagerName);

    for (Tuple tuples : tuple) {
      PartnerRequest partnerRequest = new PartnerRequest();
      ProgramManagerRequest programManagerRequest = new ProgramManagerRequest();
      partnerRequest.setPartnerId(tuples.get(QPartner.partner.partnerId));
      partnerRequest.setPartnerName(tuples.get(QPartner.partner.partnerName));
      partnerRequest.setStatus(tuples.get(QPartner.partner.status));
      programManagerRequest.setProgramManagerId(tuples.get(QPartner.partner.programManagerId));
      programManagerRequest
          .setProgramManagerName(tuples.get(QProgramManager.programManager.programManagerName));
      partnerRequest.setAccountCurrency(tuples.get(QPartner.partner.accountCurrency));
      partnerRequest.setProgramManagerRequest(programManagerRequest);
      query = new JPAQuery(entityManager);
      List<BankPartnerMap> bankPartnerMaps = query.from(QBankPartnerMap.bankPartnerMap)
          .where(QBankPartnerMap.bankPartnerMap.partnerId.eq(partnerRequest.getPartnerId()))
          .list(QBankPartnerMap.bankPartnerMap);
      try {
        List<BankPartnerMapRequest> bankPartnerMapRequests =
            CommonUtil.copyListBeanProperty(bankPartnerMaps, BankPartnerMapRequest.class);
        partnerRequest.setBankPartnerMapRequests(bankPartnerMapRequests);
      } catch (Exception e) {
        logger.error(className + " : getAllPartners : Error in retrieving the bank partner map.",
            e);
      }

      response.add(partnerRequest);
    }
    return response;
  }

  private BooleanExpression isStatusIn(List<String> typeList) {
    return StringUtil.isListNotNullNEmpty(typeList) ? QPartner.partner.status.in(typeList) : null;
  }

  private BooleanExpression isPartnerTypeIn(List<String> typeList) {
    return StringUtil.isListNotNullNEmpty(typeList) ? QPartner.partner.partnerType.in(typeList)
        : null;
  }

  private int getTotalNumberOfRecords(PartnerRequest partnerRequest) {
    JPAQuery query = new JPAQuery(entityManager);
    List<Long> partnerIdList = query
        .from(QPartner.partner, QProgramManager.programManager, QBankPartnerMap.bankPartnerMap,
            QPGBank.pGBank)
        .where(isCompanyNameLike(partnerRequest.getCompanyName()),
            isPartnerNameLike(partnerRequest.getPartnerName()),
            isPartnerType(partnerRequest.getPartnerType()),
            isStatusIn(partnerRequest.getStatusList()), isBankId(partnerRequest.getBankName()),
            isPartnerId(partnerRequest.getPartnerId()),
            isProgramManagerId(partnerRequest.getProgramManagerId()),
            isStatus(partnerRequest.getStatus()),
            QPartner.partner.programManagerId.eq(QProgramManager.programManager.id),
            QPGBank.pGBank.id.eq(QBankPartnerMap.bankPartnerMap.bankId),
            QPartner.partner.partnerId.eq(QBankPartnerMap.bankPartnerMap.partnerId))
        .distinct().list(QPartner.partner.partnerId);

    return (StringUtil.isListNotNullNEmpty(partnerIdList) ? partnerIdList.size() : 0);
  }

  private BooleanExpression isCompanyNameLike(String companyName) {
    return (StringUtil.isNullEmpty(companyName) ? null
        : QPartner.partner.companyName.toUpperCase()
            .like("%" + companyName.toUpperCase().replace("*", "") + "%"));
  }

  private BooleanExpression isPartnerNameLike(String partnerName) {
    return (StringUtil.isNullEmpty(partnerName) ? null
        : QPartner.partner.partnerName.toUpperCase()
            .like("%" + partnerName.toUpperCase().replace("*", "") + "%"));
  }

  private BooleanExpression isPartnerType(String partnerType) {
    return (StringUtil.isNullEmpty(partnerType) ? null
        : QPartner.partner.partnerType.eq(partnerType));
  }

  private BooleanExpression nonSystemPartner() {
    return QPartner.partner.partnerName.ne(Constants.SYSTEM_PARTNER);
  }

  private BooleanExpression isStatus(String status) {
    return (StringUtil.isNullEmpty(status) ? null : QPartner.partner.status.eq(status));
  }

  private BooleanExpression isBankId(String bankName) {
    return (StringUtil.isNullAndEmpty(bankName)) ? null : QPGBank.pGBank.bankName.eq(bankName);
  }

  private BooleanExpression isProgramManagerId(String programManagerId) {
    return (StringUtil.isNullEmpty(programManagerId)) ? null
        : QPartner.partner.programManagerId.eq(Long.parseLong(programManagerId));
  }

  private OrderSpecifier<Long> orderByPartnerIdDesc() {
    return QPartner.partner.partnerId.desc();
  }

  private BooleanExpression isPartnerIdLike(Long partnerId) {
    return (partnerId != null) ? QPartnerAccount.partnerAccount.partnerId.eq((partnerId)) : null;
  }

  private BooleanExpression isAccountTypeLike(AccountType accountType) {
    return (accountType != null)
        ? QPartnerAccount.partnerAccount.accountType.eq((accountType.toString())) : null;
  }

  @Override
  public PartnerAccount saveOrUpdatePartnerAccount(PartnerAccount partnerAccount)
      throws DataAccessException {
    return partnerAccountRepository.save(partnerAccount);
  }

  public Long revenuePartnerAccount() throws DataAccessException {
    List<BigInteger> list = entityManager
        .createNativeQuery("select max(account_number) from PG_PARTNER_ACCOUNT").getResultList();
    if (StringUtil.isListNotNullNEmpty(list) && list.get(0) != null) {
      return list.get(0).longValue() + 1l;
    }
    return Constants.PM_ACCOUNT_NUMBER1;
  }

  public Long systemPartnerAccount() throws DataAccessException {
    List<BigInteger> list = entityManager
        .createNativeQuery("select max(account_number) from PG_PARTNER_ACCOUNT").getResultList();
    if (StringUtil.isListNotNullNEmpty(list) && list.get(0) != null) {
      return list.get(0).longValue() + Long.parseLong("2");
    }
    return Constants.PM_ACCOUNT_NUMBER2;
  }

  public Long networkPartnerAccount() throws DataAccessException {
    List<BigInteger> list = entityManager
        .createNativeQuery("select max(account_number) from PG_PARTNER_ACCOUNT").getResultList();
    if (StringUtil.isListNotNullNEmpty(list) && list.get(0) != null) {
      return list.get(0).longValue() + Long.parseLong("3");
    }
    return Constants.PM_ACCOUNT_NUMBER3;
  }

  private BooleanExpression isPartnerId(Long partnerId) {
    return (partnerId != null) ? QPartner.partner.partnerId.eq(partnerId) : null;
  }

  public List<PartnerRequest> getAllPartnersForAdminUser(PartnerRequest partnerRequestStatus)
      throws DataAccessException {
    List<PartnerRequest> response = new ArrayList<>();
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> tuple = query.from(QPartner.partner)
        .where(QPartner.partner.partnerName.equalsIgnoreCase(Constants.SYSTEM_PARTNER))
        .list(QPartner.partner.partnerId, QPartner.partner.partnerName, QPartner.partner.status);

    for (Tuple tuples : tuple) {
      PartnerRequest partnerRequest = new PartnerRequest();
      partnerRequest.setPartnerId(tuples.get(QPartner.partner.partnerId));
      partnerRequest.setPartnerName(tuples.get(QPartner.partner.partnerName));
      partnerRequest.setStatus(tuples.get(QPartner.partner.status));
      response.add(partnerRequest);
    }
    return response;
  }

  @Override
  public PartnerAccount getPartnerAccountByAccountIdAndActType(Long partnerAccountid,
      String accountType) throws DataAccessException {
    return partnerAccountRepository.findByPartnerAccountIdAndAccountType(partnerAccountid,
        accountType);
  }

  @Override
  public List<Partner> findByPartnerClientId(String partnerClientId) throws DataAccessException {
    JPAQuery query = new JPAQuery(entityManager);
    List<Partner> list = query.from(QPartner.partner).where(QPartner.partner.partnerClientId
        .toLowerCase().equalsIgnoreCase(partnerClientId.toLowerCase())).list(QPartner.partner);
    return (StringUtil.isListNotNullNEmpty(list) ? list : null);
  }

  @Override
  public List<PartnerRequest> searchAccountPartner(PartnerRequest partnerRequest)
      throws DataAccessException {
    List<PartnerRequest> partnerRequests = new ArrayList<>();
    int startIndex = 0;
    int endIndex = 0;
    Integer totalRecords;

    if (partnerRequest.getPageIndex() == null || partnerRequest.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfRecordsforPartnerAccounts(partnerRequest);
      partnerRequest.setNoOfRecords(totalRecords);
    }

    if (partnerRequest.getPageIndex() == null && partnerRequest.getPageSize() == null) {
      startIndex = 0;
      endIndex = Constants.DEFAULT_PAGE_SIZE;
    } else {
      startIndex = (partnerRequest.getPageIndex() - 1) * partnerRequest.getPageSize();
      endIndex = startIndex + partnerRequest.getPageSize();
    }
    int resultIndex = endIndex - startIndex;
    StringBuilder query = new StringBuilder();
    query.append(
        " select PA.PARTNER_ACCOUNT_ID,PA.ACCOUNT_TYPE,PA.AVAILABLE_BALANCE,PA.CURRENT_BALANCE,P.PARTNER_NAME,P.STATUS,PA.NICK_NAME,P.PARTNER_ID,PA.ACCOUNT_NUMBER,P.ACCOUNT_CURRENCY,CC.CURRENCY_CODE_ALPHA from PG_PARTNER_ACCOUNT PA");
    query.append(" left JOIN PG_PARTNER P on P.PARTNER_ID=PA.PARTNER_ID");
    query.append(" LEFT JOIN CURRENCY_CONFIG CC on P.ACCOUNT_CURRENCY=CC.CURRENCY_NAME ");
    query.append(" where (:partnerId = 0 or P.PARTNER_ID = :partnerId)");
    query.append(" and (:status is NULL or P.STATUS in(:status))");
    query.append(" order by P.CREATED_DATE desc");
    query.append(" LIMIT :startIndex, :resultIndex");


    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("partnerId",
        ((partnerRequest.getPartnerId() == null) ? 0 : (partnerRequest.getPartnerId())));
    qry.setParameter(Constants.STATUS, (partnerRequest.getStatus()));
    qry.setParameter("startIndex", startIndex);
    qry.setParameter("resultIndex", resultIndex);

    List<Object> list = qry.getResultList();
    if (StringUtil.isListNotNullNEmpty(list)) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        try {
          Object[] objs = (Object[]) it.next();

          PartnerRequest partnerRequest2 = getPartnerDetails(objs);
          partnerRequests.add(partnerRequest2);
        } catch (Exception e) {
          logger.error("Error in retrieving the data : : searchAccountPartner", e);
        }
      }
    }
    return partnerRequests;
  }

  private int getTotalNumberOfRecordsforPartnerAccounts(PartnerRequest partnerRequest) {

    StringBuilder query = new StringBuilder();
    query.append(" select count(1) from (");
    query.append(
        " select PA.PARTNER_ACCOUNT_ID,PA.ACCOUNT_TYPE,PA.AVAILABLE_BALANCE,PA.CURRENT_BALANCE,P.PARTNER_NAME,PA.STATUS,PA.NICK_NAME,P.PARTNER_ID,PA.ACCOUNT_NUMBER,CC.CURRENCY_CODE_ALPHA from PG_PARTNER_ACCOUNT PA");
    query.append(" left JOIN PG_PARTNER P on P.PARTNER_ID=PA.PARTNER_ID");
    query.append(" LEFT JOIN CURRENCY_CONFIG CC on P.ACCOUNT_CURRENCY=CC.CURRENCY_NAME ");
    query.append(" where (:partnerId = 0 or P.PARTNER_ID = :partnerId)");
    query.append(" and (:status is NULL or P.STATUS in(:status))");
    query.append(" ) temp");

    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("partnerId",
        ((partnerRequest.getPartnerId() == null) ? 0 : (partnerRequest.getPartnerId())));
    qry.setParameter(Constants.STATUS, (partnerRequest.getStatus()));

    List<Object> list = qry.getResultList();
    return (StringUtil.isListNotNullNEmpty(list) ? ((BigInteger) list.get(0)).intValue() : 0);
  }

  @Override
  public PartnerAccountRequest findBankDetailsByPartnerId(PartnerAccountRequest parnAccountRequest)
      throws DataAccessException {
    JPAQuery query = new JPAQuery(entityManager);
    Map<Long, String> mappedBanks = new HashMap<Long, String>();
    List<Tuple> results =
        query.from(QPartner.partner, QBankPartnerMap.bankPartnerMap, QPGBank.pGBank)
            .where(QPartner.partner.partnerId.eq(parnAccountRequest.getPartnerId()),
                QBankPartnerMap.bankPartnerMap.partnerId.eq(QPartner.partner.partnerId))
        .list(QPGBank.pGBank.id, QPGBank.pGBank.bankName);

    for (Tuple tuple3 : results) {
      mappedBanks.put(tuple3.get(QPGBank.pGBank.id), tuple3.get(QPGBank.pGBank.bankName));
    }
    parnAccountRequest.setBanks(mappedBanks);
    return parnAccountRequest;
  }

  @Override
  public List<PartnerRequest> searchPartnerAccountByPm(PartnerRequest partnerRequest)
      throws DataAccessException {
    List<PartnerRequest> partnerRequests = new ArrayList<>();
    int startIndex = 0;
    int endIndex = 0;
    Integer totalRecords;

    if (partnerRequest.getPageIndex() == null || partnerRequest.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfRecordsforPartnerAccountsByPm(partnerRequest);
      partnerRequest.setNoOfRecords(totalRecords);
    }

    if (partnerRequest.getPageIndex() == null && partnerRequest.getPageSize() == null) {
      startIndex = 0;
      endIndex = Constants.DEFAULT_PAGE_SIZE;
    } else {
      startIndex = (partnerRequest.getPageIndex() - 1) * partnerRequest.getPageSize();
      endIndex = startIndex + partnerRequest.getPageSize();
    }
    int resultIndex = endIndex - startIndex;
    StringBuilder query = new StringBuilder();
    query.append(
        " select PA.PARTNER_ACCOUNT_ID,PA.ACCOUNT_TYPE,PA.AVAILABLE_BALANCE,PA.CURRENT_BALANCE,P.PARTNER_NAME,");
    query.append(" P.STATUS,PA.NICK_NAME,P.PARTNER_ID,PA.ACCOUNT_NUMBER,P.ACCOUNT_CURRENCY,");
    query.append(" CC.CURRENCY_CODE_ALPHA from PG_PARTNER_ACCOUNT PA");
    query.append(" left JOIN PG_PARTNER P on P.PARTNER_ID=PA.PARTNER_ID");
    query.append(" LEFT JOIN CURRENCY_CONFIG CC on P.ACCOUNT_CURRENCY=CC.CURRENCY_NAME");
    query.append(
        " where  P.PARTNER_ID in (select pt.PARTNER_ID from PG_PARTNER pt, PG_PARTNER_ACCOUNT pa");
    query.append(
        " where pt.PARTNER_ID = pa.PARTNER_ID AND pt.PROGRAM_MANAGER_ID = :programManagerId)");
    query.append(" and (:status is NULL or P.STATUS in(:status))");
    query.append(" order by P.CREATED_DATE desc");
    query.append(" LIMIT :startIndex, :resultIndex ");


    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("programManagerId", partnerRequest.getProgramManagerRequest().getId() == null
        ? 0 : partnerRequest.getProgramManagerRequest().getId());
    qry.setParameter(Constants.STATUS, (partnerRequest.getStatus()));
    qry.setParameter("startIndex", startIndex);
    qry.setParameter("resultIndex", resultIndex);

    List<Object> list = qry.getResultList();
    if (StringUtil.isListNotNullNEmpty(list)) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        try {
          Object[] objs = (Object[]) it.next();

          PartnerRequest partnerRequest2 = getPartnerDetails(objs);
          partnerRequests.add(partnerRequest2);
        } catch (Exception e) {
          logger.error("Error in retrieving the data : : searchAccountPartner", e);
        }
      }
    }
    return partnerRequests;
  }

  private PartnerRequest getPartnerDetails(Object[] objs) {
    PartnerRequest partnerRequest2 = new PartnerRequest();
    partnerRequest2.setPartnerAccountId(
        StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
    partnerRequest2.setAccountType(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
    partnerRequest2.setAvailableBalance(StringUtil.isNull(objs[Integer.parseInt("2")]) ? null
        : (CommonUtil.getDoubleAmount(((BigInteger) objs[Integer.parseInt("2")]).longValue())));
    partnerRequest2.setCurrentBalance(StringUtil.isNull(objs[Integer.parseInt("3")]) ? null
        : (CommonUtil.getDoubleAmount(((BigInteger) objs[Integer.parseInt("3")]).longValue())));
    partnerRequest2.setPartnerName(
        StringUtil.isNull(objs[Integer.parseInt("4")]) ? null : ((String) objs[Integer.parseInt("4")]));
    partnerRequest2.setStatus(
        StringUtil.isNull(objs[Integer.parseInt("5")]) ? null : ((String) objs[Integer.parseInt("5")]));
    partnerRequest2.setNickName(
        StringUtil.isNull(objs[Integer.parseInt("6")]) ? null : ((String) objs[Integer.parseInt("6")]));
    partnerRequest2.setPartnerId(StringUtil.isNull(objs[Integer.parseInt("7")]) ? null
        : ((BigInteger) objs[Integer.parseInt("7")]).longValue());
    partnerRequest2.setAccountNumber(StringUtil.isNull(objs[Integer.parseInt("8")]) ? null
        : ((BigInteger) objs[Integer.parseInt("8")]).longValue());
    partnerRequest2.setAccountCurrency(
        StringUtil.isNull(objs[Integer.parseInt("9")]) ? null : ((String) objs[Integer.parseInt("9")]));
    partnerRequest2.setCurrencyCodeAlpha(
        StringUtil.isNull(objs[Integer.parseInt("10")]) ? null : (String) objs[Integer.parseInt("10")]);
    return partnerRequest2;
  }

  private int getTotalNumberOfRecordsforPartnerAccountsByPm(PartnerRequest partnerRequest) {

    StringBuilder query = new StringBuilder();
    query.append(
        " SELECT COUNT(1) FROM(select pt.PARTNER_ID,pa.ACCOUNT_NUMBER from PG_PARTNER pt, PG_PARTNER_ACCOUNT pa");
    query.append(
        " where pt.PARTNER_ID = pa.PARTNER_ID AND pt.PROGRAM_MANAGER_ID = :programManagerId");
    query.append(" and (:status is NULL or pt.STATUS in(:status))");
    query.append(" )temp ");
    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("programManagerId", partnerRequest.getProgramManagerRequest().getId() == null
        ? 0 : partnerRequest.getProgramManagerRequest().getId());
    qry.setParameter(Constants.STATUS, partnerRequest.getStatus());
    List<Object> list = qry.getResultList();
    return (StringUtil.isListNotNullNEmpty(list) ? ((BigInteger) list.get(0)).intValue() : 0);
  }

  @Override
  public List<PartnerAccount> getPartnerAccountsByPartnerId(Long partnerId, AccountType accountType)
      throws DataAccessException {
    List<PartnerAccount> list = new ArrayList<>();
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> tuple = query.from(QPartnerAccount.partnerAccount)
        .where(isPartnerIdLike(partnerId), isAccountTypeLike(accountType))
        .list(QPartnerAccount.partnerAccount.accountNumber,
            QPartnerAccount.partnerAccount.accountType,
            QPartnerAccount.partnerAccount.availableBalance,
            QPartnerAccount.partnerAccount.createdBy, QPartnerAccount.partnerAccount.createdDate,
            QPartnerAccount.partnerAccount.currentBalance,
            QPartnerAccount.partnerAccount.partnerAccountId,
            QPartnerAccount.partnerAccount.partnerId, QPartnerAccount.partnerAccount.status,
            QPartnerAccount.partnerAccount.updatedBy, QPartnerAccount.partnerAccount.updatedDate);
    for (Tuple tuples : tuple) {
      PartnerAccount partnerAccount = new PartnerAccount();
      partnerAccount.setAccountNumber(tuples.get(QPartnerAccount.partnerAccount.accountNumber));
      partnerAccount.setAccountType(tuples.get(QPartnerAccount.partnerAccount.accountType));
      partnerAccount
          .setAvailableBalance(tuples.get(QPartnerAccount.partnerAccount.availableBalance));
      partnerAccount.setCreatedBy(tuples.get(QPartnerAccount.partnerAccount.createdBy));
      partnerAccount.setCreatedDate(tuples.get(QPartnerAccount.partnerAccount.createdDate));
      partnerAccount.setCurrentBalance(tuples.get(QPartnerAccount.partnerAccount.currentBalance));
      partnerAccount
          .setPartnerAccountId(tuples.get(QPartnerAccount.partnerAccount.partnerAccountId));
      partnerAccount.setPartnerId(tuples.get(QPartnerAccount.partnerAccount.partnerId));
      partnerAccount.setStatus(tuples.get(QPartnerAccount.partnerAccount.status));
      partnerAccount.setUpdatedBy(tuples.get(QPartnerAccount.partnerAccount.updatedBy));
      partnerAccount.setUpdatedDate(tuples.get(QPartnerAccount.partnerAccount.updatedDate));
      list.add(partnerAccount);
    }
    return list;
  }
  
  @Override
  public ProgramManager findProgramManagerByPartnerId(String partnerId) {
    ProgramManager programManager = null;
    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> tuples = query.from(QPartner.partner, QProgramManager.programManager)
        .where(QPartner.partner.partnerId.eq(Long.parseLong(partnerId))
            .and(QPartner.partner.programManagerId.eq(QProgramManager.programManager.id)))
        .list(QProgramManager.programManager.id, QProgramManager.programManager.programManagerName);
    for (Tuple tuple : tuples) {
      programManager = new ProgramManager();
      programManager.setId(tuple.get(QProgramManager.programManager.id));
      programManager.setProgramManagerName(tuple.get(QProgramManager.programManager.programManagerName));
    }
    return programManager;
  }

  @Override
  public List<Partner> getPartnersByPMId(Long programManagerId) {
    return partnerRepository.findByProgramManagerId(programManagerId);
  }

  @Override
  public List<Partner> getActivePartners() {
    return partnerRepository.findByStatus(Constants.ACTIVE);
  }
}
