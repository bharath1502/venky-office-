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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chatak.pg.acq.dao.ProgramManagerDao;
import com.chatak.pg.acq.dao.model.BankProgramManagerMap;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PmCardProgamMapping;
import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.acq.dao.model.ProgramManagerAccount;
import com.chatak.pg.acq.dao.model.QBankProgramManagerMap;
import com.chatak.pg.acq.dao.model.QCardProgram;
import com.chatak.pg.acq.dao.model.QPGBank;
import com.chatak.pg.acq.dao.model.QPmCardProgamMapping;
import com.chatak.pg.acq.dao.model.QProgramManager;
import com.chatak.pg.acq.dao.model.QProgramManagerAccount;
import com.chatak.pg.acq.dao.repository.BankProgramManagerRepository;
import com.chatak.pg.acq.dao.repository.PmCardProgramMappingRepository;
import com.chatak.pg.acq.dao.repository.ProgramManagerAccountRepository;
import com.chatak.pg.acq.dao.repository.ProgramManagerRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.exception.PrepaidAdminException;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.CardProgramMappingRequest;
import com.chatak.pg.user.bean.CardProgramRequest;
import com.chatak.pg.user.bean.CardProgramResponse;
import com.chatak.pg.user.bean.MerchantResponse;
import com.chatak.pg.user.bean.ProgramManagerAccountRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@SuppressWarnings({"unchecked", "rawtypes"})
@Repository
public class ProgramManagerDaoImpl implements ProgramManagerDao {

  private Logger logger = LogManager.getLogger(this.getClass());

  private final String className = ProgramManagerDaoImpl.class.getSimpleName();

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  private ProgramManagerRepository programManagerRepository;

  @Autowired
  private ProgramManagerAccountRepository programManagerAccountRepository;

  @Autowired
  private BankProgramManagerRepository bankProgramManagerRepository;
  
  @Autowired
  private PmCardProgramMappingRepository pmCardProgramMappingRepository;

  public Long getProgramManagerAccountNumber() {
    List<BigInteger> list =
        entityManager.createNativeQuery(
            "select max(account_number) from PG_PROGRAM_MANAGER_ACCOUNT ").getResultList();
    if (StringUtil.isListNotNullNEmpty(list) && list.get(0) != null) {
      return list.get(0).longValue() + 1l;
    }
    return Constants.PM_ACCOUNT_NUMBER1;
  }

  public Long getRevenueProgramManagerAccountNumber() {
    List<BigInteger> list =
        entityManager.createNativeQuery(
            "select max(account_number) from PG_PROGRAM_MANAGER_ACCOUNT ").getResultList();
    if (StringUtil.isListNotNullNEmpty(list) && list.get(0) != null) {
      return list.get(0).longValue() + Long.parseLong("2");
    }
    return Constants.PM_ACCOUNT_NUMBER2;
  }

  @Override
  @Transactional
  public ProgramManager saveOrUpdateProgramManager(ProgramManager programManager) {
    return programManagerRepository.save(programManager);
  }

  @Override
  public ProgramManagerAccount saveOrUpdateProgramManagerAccount(
      ProgramManagerAccount programManagerAccount) {
    return programManagerAccountRepository.save(programManagerAccount);
  }

  @Override
  public void deleteBankProgramManagerMap(Long pmId) {
    bankProgramManagerRepository.deleteByPmId(pmId);
  }

  @Override
  public Set<BankProgramManagerMap> findBankProgramManagerMapByProgramManagerId(
      Long programManagerId) {
    Set<BankProgramManagerMap> bankProgramManagerMaps =
        bankProgramManagerRepository.findByProgramManagerId(programManagerId);
    return bankProgramManagerMaps;
  }
  
  @Override
  public Set<PmCardProgamMapping> findPmCardProgamMappingMapByProgramManagerId(
      Long programManagerId) {
    Set<PmCardProgamMapping> cardProgramMapping =
    		pmCardProgramMappingRepository.findByProgramManagerId(programManagerId);
    return cardProgramMapping;
  }

  @Override
  public ProgramManagerAccount findByProgramManagerIdAndAccountNumber(Long pmId, Long accountNumber) {
    ProgramManagerAccount programManagerAccount =
        programManagerAccountRepository.findByProgramManagerIdAndAccountNumber(pmId, accountNumber);
    return programManagerAccount;
  }

  @Override
  public List<ProgramManagerAccount> findByAccountNumber(Long accountNumber) {
    List<ProgramManagerAccount> programManagerAccount =
        programManagerAccountRepository.findByAccountNumber(accountNumber);
    return programManagerAccount;
  }


  @Override
  public ProgramManagerAccount findByAccountId(Long accountId) {
    return programManagerAccountRepository.findById(accountId);
  }

  @Override
  public void changeStatus(ProgramManager programManager) {
    programManagerRepository.changeStatus(programManager.getStatus(), programManager.getReason(),
        programManager.getUpdatedDate(), programManager.getUpdatedBy(), programManager.getId());
  }



  @Override
  public List<ProgramManager> findByProgramManagerName(String programManagerName) {
    JPAQuery query = new JPAQuery(entityManager);
    List<ProgramManager> list =
        query
            .from(QProgramManager.programManager)
            .where(
                QProgramManager.programManager.programManagerName.toLowerCase().equalsIgnoreCase(
                    programManagerName.toLowerCase())).list(QProgramManager.programManager);
    return (StringUtil.isListNotNullNEmpty(list) ? list : null);
  }

  @Override
  public List<ProgramManagerRequest> getAllProgramManagers(
      ProgramManagerRequest programManagerRequest)  {
    List<ProgramManagerRequest> programManagerRequests = new ArrayList<>();
    JPAQuery query = new JPAQuery(entityManager);
    List<ProgramManager> programManagers = query.from(QProgramManager.programManager)
        .where(isProgramManagerStatuses(programManagerRequest.getStatuses()),
            isDefaultProgramManager(programManagerRequest.getDefaultProgramManager()),
            nonSystemProgramManager(), isProgramManagerId(programManagerRequest.getId()),
            isprogramManagerIdIn(programManagerRequest.getProgramManagerIds()))
        .orderBy(QProgramManager.programManager.programManagerName.asc())
        .list(QProgramManager.programManager);
    if (StringUtil.isListNotNullNEmpty(programManagers)) {
      try {
        programManagerRequests =
            CommonUtil.copyListBeanProperty(programManagers, ProgramManagerRequest.class);
      } catch (Exception e) {
        logger.error(
            className + " : getAllProgramManagers : Error in retrieving the program managers.", e);
      }
    }
    return programManagerRequests;
  }

  @Override
  public ProgramManagerRequest findProgramManagerById(ProgramManagerRequest programManagerRequest)
      throws PrepaidAdminException {

    if (StringUtil.isNull(programManagerRequest)) {
      throw new PrepaidAdminException(Constants.PROGRAM_MANAGER_ID_IS_EMPTY,
          "Program manager Request cannot be empty");
    }

    if (StringUtil.isNull(programManagerRequest.getId())) {
      throw new PrepaidAdminException(Constants.PROGRAM_MANAGER_ID_IS_EMPTY,
          "Program manager Id cannot be empty");
    }

    ProgramManagerRequest programManagerRequest2 = null;
    try {
      ProgramManager programManager =
          programManagerRepository.findOne(programManagerRequest.getId());
      if (StringUtil.isNull(programManager)) {
        return programManagerRequest2;
      }
      programManagerRequest2 =
          CommonUtil.copyBeanProperties(programManager, ProgramManagerRequest.class);


      List<ProgramManagerAccount> programManagerAccounts =
          programManagerAccountRepository.findByProgramManagerId(programManager.getId());
      if (CommonUtil.isListNotNullAndEmpty(programManagerAccounts)) {
        List<ProgramManagerAccountRequest> programManagerAccountRequests = new ArrayList<>();
        ProgramManagerAccountRequest programManagerAccountRequest = null;
        for (ProgramManagerAccount programManagerAccount : programManagerAccounts) {
          programManagerAccountRequest = CommonUtil.copyBeanProperties(programManagerAccount,
              ProgramManagerAccountRequest.class);
          programManagerAccountRequest.setCurrentBalance(
              CommonUtil.getDoubleAmount(programManagerAccount.getCurrentBalance()));
          programManagerAccountRequest.setAvailableBalance(
              CommonUtil.getDoubleAmount(programManagerAccount.getAvailableBalance()));
          programManagerAccountRequest.setAccountThresholdAmount(
              CommonUtil.getDoubleAmount(programManagerAccount.getAccountThresholdLimit()));
          setAccountThresholdlimit(programManagerRequest2, programManagerAccount);

          programManagerAccountRequests.add(programManagerAccountRequest);
        }
        programManagerRequest2.setProgramManagerAccountRequests(programManagerAccountRequests);
      }

      
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> pmBanks = query.from(QPGBank.pGBank,QBankProgramManagerMap.bankProgramManagerMap)
          .where(QBankProgramManagerMap.bankProgramManagerMap.programManagerId.eq(programManagerRequest.getId())
        		  .and(QBankProgramManagerMap.bankProgramManagerMap.bankId.eq(QPGBank.pGBank.id)))
          .list(QPGBank.pGBank.id,QPGBank.pGBank.bankName);
      
      List<BankRequest> bankRequest = new ArrayList<>(0);
      BankRequest bank;
      for(Tuple pmBank: pmBanks){
    	  bank = new BankRequest();
    	  bank.setId(pmBank.get(QPGBank.pGBank.id));
    	  bank.setBankName(pmBank.get(QPGBank.pGBank.bankName));
    	  bankRequest.add(bank);
      }
      programManagerRequest2.setBankRequest(bankRequest);
      if(programManager.getIssuancepmid()!=null){
    	  programManagerRequest2.setProgramManagerType(Constants.ONBOARDED);
      }else programManagerRequest2.setProgramManagerType(Constants.CREATE_INDEPENDENT);
      
      
      query = new JPAQuery(entityManager);
      List<Tuple> pmCardProgram = query.from(QPmCardProgamMapping.pmCardProgamMapping,QCardProgram.cardProgram)
          .where(QPmCardProgamMapping.pmCardProgamMapping.programManagerId.eq(programManagerRequest.getId())
        		  .and(QPmCardProgamMapping.pmCardProgamMapping.cardProgramId.eq(QCardProgram.cardProgram.cardProgramId)))
          .list(QCardProgram.cardProgram.cardProgramId,QCardProgram.cardProgram.cardProgramName);
      
      List<CardProgramMappingRequest> cardProgramMappingRequest = new ArrayList<>(0);
      CardProgramMappingRequest cardProgramMapping;
      for(Tuple cardProgram :  pmCardProgram){
    	  cardProgramMapping = new CardProgramMappingRequest();
    	  cardProgramMapping.setCardProgramId(cardProgram.get(QCardProgram.cardProgram.cardProgramId));
    	  cardProgramMapping.setCardProgramName(cardProgram.get(QCardProgram.cardProgram.cardProgramName));
    	  cardProgramMappingRequest.add(cardProgramMapping);
      }
      programManagerRequest2.setCardProgamMapping(cardProgramMappingRequest);
    } catch (Exception e) {
      logger.error(
          className + " : findAgentById : Error in retrieving the program manager details.", e);
    }

    return programManagerRequest2;
  }

  private void setAccountThresholdlimit(ProgramManagerRequest programManagerRequest2,
      ProgramManagerAccount programManagerAccount) {
    if (programManagerAccount.getAccountType().equals("Revenue Account")) {
      programManagerRequest2.setAccountThresholdLimit(
          CommonUtil.getDoubleAmount(programManagerAccount.getAccountThresholdLimit()));
    }
  }

  @Override
  public ProgramManager searchSystemProgramManager(ProgramManagerRequest programManagerRequest) {
    JPAQuery query = new JPAQuery(entityManager);
    List<ProgramManager> programManagersList =
        query
            .from(QProgramManager.programManager)
            .where(
                QProgramManager.programManager.programManagerName
                    .equalsIgnoreCase(Constants.SYSTEM_PROGRAM_MANAGER))
            .list(QProgramManager.programManager);
    return programManagersList.get(0);
  }

  @Override
  public List<ProgramManagerRequest> searchProgramManagers(
      ProgramManagerRequest programManagerRequest)  {
    List<ProgramManagerRequest> programManagerRequests = new ArrayList<>();
    int offset = 0;
    int limit = 0;
    Integer totalRecords = programManagerRequest.getNoOfRecords();

    if (programManagerRequest.getPageIndex() == null || programManagerRequest.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfRecords(programManagerRequest);
      programManagerRequest.setNoOfRecords(totalRecords);
    }

    if (programManagerRequest.getPageIndex() == null
        && programManagerRequest.getPageSize() == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (programManagerRequest.getPageIndex() - 1) * programManagerRequest.getPageSize();
      limit = programManagerRequest.getPageSize();
    }

    JPAQuery query = new JPAQuery(entityManager);
    List<Tuple> tupleList = query
        .from(QProgramManager.programManager, QBankProgramManagerMap.bankProgramManagerMap,
            QPGBank.pGBank)
        .where(isCompanyNameLike(programManagerRequest.getCompanyName()),
            isProgramManagerNameLike(programManagerRequest.getProgramManagerName()),
            isProgramManagerId(programManagerRequest.getId()),
            isProgramManagerIds(programManagerRequest.getProgramManagerIds()),
            isStatus(programManagerRequest.getStatus()),
            isBankId(programManagerRequest.getBankNames()),
            QProgramManager.programManager.id
                .eq(QBankProgramManagerMap.bankProgramManagerMap.programManagerId),
            QBankProgramManagerMap.bankProgramManagerMap.bankId.eq(QPGBank.pGBank.id))
        .offset(offset).limit(limit).orderBy(orderByIdDesc()).distinct()
        .list(QProgramManager.programManager.id, QProgramManager.programManager.programManagerName,
            QProgramManager.programManager.defaultProgramManager,
            QProgramManager.programManager.companyName, QProgramManager.programManager.businessName,
            QProgramManager.programManager.status, QProgramManager.programManager.accountCurrency);

    try {
      ProgramManagerRequest pmRequest = null;
      for (Tuple tuple : tupleList) {
        pmRequest = new ProgramManagerRequest();
        pmRequest.setId(tuple.get(QProgramManager.programManager.id));
        pmRequest
            .setProgramManagerName(tuple.get(QProgramManager.programManager.programManagerName));
        pmRequest.setDefaultProgramManager(
            tuple.get(QProgramManager.programManager.defaultProgramManager));
        pmRequest.setCompanyName(tuple.get(QProgramManager.programManager.companyName));
        pmRequest.setBusinessName(tuple.get(QProgramManager.programManager.businessName));
        pmRequest.setStatus(tuple.get(QProgramManager.programManager.status));
        pmRequest.setAccountCurrency(tuple.get(QProgramManager.programManager.accountCurrency));
        pmRequest.setNoOfRecords(totalRecords);
        programManagerRequests.add((ProgramManagerRequest) CommonUtil.copyBeanProperties(pmRequest,
            ProgramManagerRequest.class));
      }
    } catch (Exception e) {
      logger.error(
          className + " : searchProgramManagers : Error in searching the program manager details.",
          e);
    }

    return programManagerRequests;
  }

  private int getTotalNumberOfRecords(ProgramManagerRequest programManagerRequest) {
    JPAQuery query = new JPAQuery(entityManager);
    List<Long> programManagers = query
        .from(QProgramManager.programManager, QBankProgramManagerMap.bankProgramManagerMap,
            QPGBank.pGBank)
        .where(isCompanyNameLike(programManagerRequest.getCompanyName()),
            isProgramManagerNameLike(programManagerRequest.getProgramManagerName()),
            isProgramManagerId(programManagerRequest.getId()),
            isStatus(programManagerRequest.getStatus()),
            isBankId(programManagerRequest.getBankNames()),
            QProgramManager.programManager.id
                .eq(QBankProgramManagerMap.bankProgramManagerMap.programManagerId),
            QBankProgramManagerMap.bankProgramManagerMap.bankId.eq(QPGBank.pGBank.id))
        .distinct().list(QProgramManager.programManager.id);

    return (StringUtil.isListNotNullNEmpty(programManagers) ? programManagers.size() : 0);
  }

  private BooleanExpression isCompanyNameLike(String companyName) {
    return (StringUtil.isNullAndEmpty(companyName) ? null
        : QProgramManager.programManager.companyName.toUpperCase()
            .like("%" + companyName.toUpperCase().replace("*", "") + "%"));
  }

  private BooleanExpression isProgramManagerNameLike(String programManagerName) {
    return (StringUtil.isNullAndEmpty(programManagerName) ? null
        : QProgramManager.programManager.programManagerName.toUpperCase()
            .like("%" + programManagerName.toUpperCase().replace("*", "") + "%"));
  }

  private BooleanExpression isStatus(String status) {
    return (status != null && !"".equals(status))
        ? QProgramManager.programManager.status.eq((status)) : null;
  }

  private BooleanExpression isProgramManagerStatuses(List<String> statuses) {
    return (StringUtil.isListNotNullNEmpty(statuses))
        ? QProgramManager.programManager.status.in((statuses)) : null;
  }

  private BooleanExpression isDefaultProgramManager(Boolean defaultStatus) {
    return !StringUtil.isNull(defaultStatus)
        ? QProgramManager.programManager.defaultProgramManager.eq(defaultStatus) : null;
  }

  private BooleanExpression isBankStatuses(List<String> statuses) {
    return (StringUtil.isListNotNullNEmpty(statuses)) ? QPGBank.pGBank.status.in((statuses)) : null;
  }

  private BooleanExpression nonSystemProgramManager() {
    return QProgramManager.programManager.programManagerName.ne(Constants.SYSTEM_PROGRAM_MANAGER);
  }

  private OrderSpecifier<Long> orderByIdDesc() {
    return QProgramManager.programManager.id.desc();
  }

  private OrderSpecifier<Long> orderByBankNameAsc() {
    return QPGBank.pGBank.id.asc();
  }

  private BooleanExpression isBankId(String bankName) {
    return (StringUtil.isNullAndEmpty(bankName) ? null
        : QPGBank.pGBank.bankName.eq(bankName));
  }

  private BooleanExpression isProgramManagerId(Long programManagerId) {
    return (StringUtil.isNull(programManagerId) ? null
        : QProgramManager.programManager.id.eq(programManagerId));
  }

  private BooleanExpression isProgramManagerIds(List<Long> programManagerId) {
    return (StringUtil.isNull(programManagerId) ? null
        : QProgramManager.programManager.id.in(programManagerId));
  }

  private BooleanExpression isprogramManagerIdIn(List<Long> programManagerId) {
    return (StringUtil.isListNotNullNEmpty(programManagerId)
        ? QProgramManager.programManager.id.in(programManagerId) : null);
  }

  @Override
  public List<BankRequest> getAllBanksForProgramManager(ProgramManagerRequest programManagerRequest) {
    List<BankRequest> bankRequests = new ArrayList<>();
    JPAQuery query = new JPAQuery(entityManager);
    List<PGBank> banks =
        query
            .from(QProgramManager.programManager, QBankProgramManagerMap.bankProgramManagerMap,
                QPGBank.pGBank)
            .where(
                isProgramManagerId(programManagerRequest.getId()),
                isBankStatuses(programManagerRequest.getStatuses()),
                QProgramManager.programManager.id
                    .eq(QBankProgramManagerMap.bankProgramManagerMap.programManagerId),
                QBankProgramManagerMap.bankProgramManagerMap.bankId.eq(QPGBank.pGBank.id))
            .orderBy(orderByBankNameAsc()).distinct().list(QPGBank.pGBank);

    if (StringUtil.isListNotNullNEmpty(banks)) {
      for (PGBank bank : banks) {
        try {
          BankRequest bankRequest =
              (BankRequest) CommonUtil.copyBeanProperties(bank, BankRequest.class);
          bankRequests.add(bankRequest);
        } catch (Exception e) {
          logger.error("Error :: ProgramManagerDaoImpl :: getAllBanksForProgramManager", e);
        }
      }
    }

    return bankRequests;
  }

  @Override
  public ProgramManagerAccount getProgramManagerAccountById(Long programManagerAccountId) {
    return programManagerAccountRepository.findOne(programManagerAccountId);
  }

  @Override
  public ProgramManagerAccount getProgramManagerAccountByIdAndAccountType(
      Long programManagerAccountId, String accountType) {
    return programManagerAccountRepository.findByIdAndAccountType(programManagerAccountId,
        accountType);
  }

  @Override
  public ProgramManagerAccount findByProgramManagerIdAndAccountType(Long programManagerAccountId,
      String accountType) {
    return programManagerAccountRepository.findByProgramManagerIdAndAccountType(
        programManagerAccountId, accountType);
  }

  @Override
  public List<Long> getProgramManagerAllAccountsByPmId(Long programManagerId) {

    JPAQuery query = new JPAQuery(entityManager);
    List<Long> programAccounts =
        query
            .from(QProgramManager.programManager, QProgramManagerAccount.programManagerAccount)
            .where(
                QProgramManager.programManager.status.eq(Constants.ACTIVE),
                QProgramManager.programManager.id.eq(programManagerId),
                QProgramManager.programManager.id
                    .eq(QProgramManagerAccount.programManagerAccount.programManagerId)).distinct()
            .list(QProgramManagerAccount.programManagerAccount.accountNumber);
    return programAccounts;
  }

  @Override
  public Set<BankProgramManagerMap> findByBankId(Long bankId) {
    return bankProgramManagerRepository.findByBankId(bankId);
  }

  @Override
  public List<ProgramManagerRequest> searchProgramManagersAccounts(
      ProgramManagerRequest programManagerRequest) {
    List<ProgramManagerRequest> programManagerRequests = new ArrayList<>();
    int startIndex = 0;
    int endIndex = 0;
    Integer totalRecords;

    if (programManagerRequest.getPageIndex() == null || programManagerRequest.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfRecordsforPMAccounts(programManagerRequest);
      programManagerRequest.setNoOfRecords(totalRecords);
    }

    if (programManagerRequest.getPageIndex() == null
        && programManagerRequest.getPageSize() == null) {
      startIndex = 0;
      endIndex = Constants.DEFAULT_PAGE_SIZE;
    } else {
      startIndex = (programManagerRequest.getPageIndex() - 1) * programManagerRequest.getPageSize();
      endIndex = startIndex + programManagerRequest.getPageSize();
    }
    int resultIndex = endIndex - startIndex;
    StringBuilder query = new StringBuilder();
    query.append(
        " select PMA.ID,PMA.ACCOUNT_TYPE,PM.PROGRAM_MANAGER_NAME,PMA.PROGRAM_MANAGER_ID,PMA.AVAILABLE_BALANCE,PMA.CURRENT_BALANCE,PMA.NICK_NAME,PM.STATUS,PMA.ACCOUNT_NUMBER,CC.CURRENCY_CODE_ALPHA,PM.ACCOUNT_CURRENCY from PG_PROGRAM_MANAGER_ACCOUNT PMA");
    query.append(" left JOIN PG_PROGRAM_MANAGER PM on PM.ID=PMA.PROGRAM_MANAGER_ID");
    query.append(" left join CURRENCY_CONFIG CC on CC.CURRENCY_NAME=PM.ACCOUNT_CURRENCY");
    query.append(" where (:programManagerId = 0 or PM.id =:programManagerId)");
    query.append(" and (:status is NULL or PM.STATUS in(:status))");
    query.append(" order by PM.created_date desc limit :startIndex,:resultIndex ");

    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("programManagerId",
        ((programManagerRequest.getId() == null) ? 0 : (programManagerRequest.getId())));
    qry.setParameter("status", (programManagerRequest.getStatus()));
    qry.setParameter("startIndex", startIndex);
    qry.setParameter("resultIndex", resultIndex);

    List<Object> list = qry.getResultList();
    if (StringUtil.isListNotNullNEmpty(list)) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        try {
          Object[] objs = (Object[]) it.next();

          ProgramManagerRequest prograManagerRequest = processDetails(objs);
          programManagerRequests.add(prograManagerRequest);
        } catch (Exception e) {
          logger.error("Error in retrieving the data : : searchProgramManagersAccounts", e);
        }
      }
    }
    return programManagerRequests;
  }

  private ProgramManagerRequest processDetails(Object[] objs) {
    ProgramManagerRequest prograManagerRequest = new ProgramManagerRequest();
    prograManagerRequest.setProgramManagerAccountId(
        StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
    prograManagerRequest
        .setAccountType(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
    prograManagerRequest
        .setProgramManagerName(StringUtil.isNull(objs[Integer.parseInt("2")]) ? null : ((String) objs[Integer.parseInt("2")]));
    prograManagerRequest.setProgramManagerId(
        StringUtil.isNull(objs[Integer.parseInt("3")]) ? null : ((BigInteger) objs[Integer.parseInt("3")]).longValue());
    prograManagerRequest.setAvailableBalance(StringUtil.isNull(objs[Integer.parseInt("4")]) ? null
        : (CommonUtil.getDoubleAmount(((BigInteger) objs[Integer.parseInt("4")]).longValue())));
    prograManagerRequest.setCurrentBalance(StringUtil.isNull(objs[Integer.parseInt("5")]) ? null
        : (CommonUtil.getDoubleAmount(((BigInteger) objs[Integer.parseInt("5")]).longValue())));
    prograManagerRequest.setNickName(StringUtil.isNull(objs[Integer.parseInt("6")]) ? null : ((String) objs[Integer.parseInt("6")]));
    prograManagerRequest.setStatus(StringUtil.isNull(objs[Integer.parseInt("7")]) ? null : ((String) objs[Integer.parseInt("7")]));
    prograManagerRequest.setPmAccountNumber(
        StringUtil.isNull(objs[Integer.parseInt("8")]) ? null : ((BigInteger) objs[Integer.parseInt("8")]).longValue());
    prograManagerRequest
        .setCurrencyCodeAlpha(StringUtil.isNull(objs[Integer.parseInt("9")]) ? null : (String) objs[Integer.parseInt("9")]);
    prograManagerRequest
        .setAccountCurrency(StringUtil.isNull(objs[Integer.parseInt("10")]) ? null : ((String) objs[Integer.parseInt("10")]));
    return prograManagerRequest;
  }

  private int getTotalNumberOfRecordsforPMAccounts(ProgramManagerRequest programManagerRequest) {

    StringBuilder query = new StringBuilder();
    query.append("select count(1) from (");
    query.append(
        " select PMA.ID,PMA.ACCOUNT_TYPE,PM.PROGRAM_MANAGER_NAME,PMA.PROGRAM_MANAGER_ID,PMA.AVAILABLE_BALANCE,PMA.NICK_NAME,PM.STATUS,PMA.ACCOUNT_NUMBER,CC.CURRENCY_CODE_ALPHA from PG_PROGRAM_MANAGER_ACCOUNT PMA");
    query.append(" left JOIN PG_PROGRAM_MANAGER PM on PM.ID=PMA.PROGRAM_MANAGER_ID");
    query.append(" left join CURRENCY_CONFIG CC on CC.CURRENCY_NAME=PM.ACCOUNT_CURRENCY");
    query.append(" where (:programManagerId = 0 or PM.id =:programManagerId)");
    query.append(" and (:status is NULL or PM.STATUS in(:status))");
    query.append(") temp ");

    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("status", (programManagerRequest.getStatus()));
    qry.setParameter("programManagerId",
        ((programManagerRequest.getId() == null) ? 0 : (programManagerRequest.getId())));

    List<Object> list = qry.getResultList();
    return (StringUtil.isListNotNullNEmpty(list) ? ((BigInteger) list.get(0)).intValue() : 0);
  }

  @Override
  public List<ProgramManagerAccountRequest> findPMAccountsToAutoSweep() {

    List<ProgramManagerAccountRequest> programManagerAccountRequests = new ArrayList<>();
    StringBuilder pmAccountQuery = new StringBuilder();
    pmAccountQuery
        .append(" select PM.ID,PM.PROGRAM_MANAGER_NAME,pma.AUTO_REPLENISH,pma.ACCOUNT_NUMBER,");
    pmAccountQuery.append(
        " pma.ID as program_manager_account_id,pma.ACCOUNT_TYPE,pma.AVAILABLE_BALANCE,pma.ACCOUNT_THRESHOLD_LIMIT,");
    pmAccountQuery.append(
        " (pma.AVAILABLE_BALANCE-pma.ACCOUNT_THRESHOLD_LIMIT) difference_amount,pma.SEND_FUNDS_MODE from PG_PROGRAM_MANAGER_ACCOUNT pma");
    pmAccountQuery.append(" left join PG_PROGRAM_MANAGER PM on pma.PROGRAM_MANAGER_ID = PM.ID");
    pmAccountQuery.append(" where pma.AUTO_REPLENISH =1");
    pmAccountQuery.append(" and pma.AVAILABLE_BALANCE > pma.ACCOUNT_THRESHOLD_LIMIT");

    Query pmAcountListQuery = entityManager.createNativeQuery(pmAccountQuery.toString());

    List<Object> list = pmAcountListQuery.getResultList();

    if (StringUtil.isListNotNullNEmpty(list)) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        try {
          Object[] objs = (Object[]) it.next();

          ProgramManagerAccountRequest programManagerAccountRequest = processPmAccountDetails(objs);
          programManagerAccountRequests.add(programManagerAccountRequest);
        } catch (Exception e) {
          logger.error("Error in retrieving the data : : findPMAccountsToAutoSweep", e);
        }
      }
    }

    return programManagerAccountRequests;
  }

  private ProgramManagerAccountRequest processPmAccountDetails(Object[] objs) {
    ProgramManagerAccountRequest programManagerAccountRequest =
        new ProgramManagerAccountRequest();
    programManagerAccountRequest.setProgramManagerId(
        StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
    programManagerAccountRequest.setAccountNumber(
        StringUtil.isNull(objs[Integer.parseInt("3")]) ? null : ((BigInteger) objs[Integer.parseInt("3")]).longValue());
    programManagerAccountRequest
        .setId(StringUtil.isNull(objs[Integer.parseInt("4")]) ? null : ((BigInteger) objs[Integer.parseInt("4")]).longValue());
    programManagerAccountRequest
        .setAccountType(StringUtil.isNull(objs[Integer.parseInt("5")]) ? null : ((String) objs[Integer.parseInt("5")]));
    programManagerAccountRequest.setAvailableBalance(StringUtil.isNull(objs[Integer.parseInt("6")]) ? null
        : (CommonUtil.getDoubleAmount(((BigInteger) objs[Integer.parseInt("6")]).longValue())));
    programManagerAccountRequest.setAccountThresholdAmount(StringUtil.isNull(objs[Integer.parseInt("7")]) ? null
        : (CommonUtil.getDoubleAmount(((BigInteger) objs[Integer.parseInt("7")]).longValue())));
    programManagerAccountRequest.setDifferenceAmount(StringUtil.isNull(objs[Integer.parseInt("8")]) ? null
        : (CommonUtil.getDoubleAmount(((BigInteger) objs[Integer.parseInt("8")]).longValue())));
    programManagerAccountRequest
        .setSendFundsMode(StringUtil.isNull(objs[Integer.parseInt("9")]) ? null : ((String) objs[Integer.parseInt("9")]));
    return programManagerAccountRequest;
  }

  @Override
  public ProgramManagerAccountRequest findBankDetailsByPMId(
      ProgramManagerAccountRequest programManagerAccountRequest) {
    JPAQuery query = new JPAQuery(entityManager);
    Map<Long, String> mappedBanks = new HashMap<Long, String>();
    List<Tuple> results =
        query
            .from(QProgramManager.programManager, QBankProgramManagerMap.bankProgramManagerMap,
                QPGBank.pGBank)
            .where(
                QProgramManager.programManager.id.eq(programManagerAccountRequest
                    .getProgramManagerId()),
                QBankProgramManagerMap.bankProgramManagerMap.programManagerId
                    .eq(QProgramManager.programManager.id),
                QBankProgramManagerMap.bankProgramManagerMap.bankId.eq(QPGBank.pGBank.id))
            .list(QProgramManager.programManager.programManagerName, QPGBank.pGBank.id,
                QPGBank.pGBank.bankName);

    for (Tuple tuple3 : results) {
      mappedBanks.put(tuple3.get(QPGBank.pGBank.id), tuple3.get(QPGBank.pGBank.bankName));
    }
    programManagerAccountRequest.setBanks(mappedBanks);
    return programManagerAccountRequest;
  }

  @Override
  public List<ProgramManager> findAllProgramManagerDetails() {
    JPAQuery query = new JPAQuery(entityManager);
    List<ProgramManager> list =
        query.from(QProgramManager.programManager).where(nonSystemProgramManager())
            .list(QProgramManager.programManager);
    return (StringUtil.isListNotNullNEmpty(list) ? list : null);
  }

  @Override
  public ProgramManagerRequest findProgramManagerById(Long id) {
    ProgramManagerRequest response = new ProgramManagerRequest();
    ProgramManager result = programManagerRepository.findById(id);
    if (!StringUtil.isNull(result)) {
      try {
    	  response.setId(result.getId());
    	  response.setProgramManagerName(result.getProgramManagerName());
    	  response.setBusinessName(result.getBusinessName());
    	  response.setContactName(result.getContactName());
    	  response.setContactEmail(result.getContactEmail());
    	  response.setContactPhone(result.getContactPhone());
    	  response.setAccountCurrency(result.getAccountCurrency());
    	  response.setStatus(result.getStatus());
    	  response.setProgramManagerLogo(result.getProgramManagerLogo());
    	  response.setBankNames(result.getBusinessName());
    	  response.setReason(result.getReason());
    	  response.setExtension(result.getExtension());
    	  response.setCreatedDate(result.getCreatedDate());
    	  response.setCreatedBy(result.getCreatedBy());
    	  response.setCompanyName(result.getCompanyName());
    	  response.setBatchPrefix(result.getBatchPrefix());
    	  response.setIssuancepmid(result.getIssuancepmid());
    	  response.setCountry(result.getCountry());
    	  response.setState(result.getState());
    	  response.setPmTimeZone(result.getPmTimeZone());
    	  response.setSchedulerRunTime(result.getSchedulerRunTime());
      } catch (Exception e) {
        logger.error(
            className + " : findProgramManagerById : Error in retrieving the program manager.", e);
      }
      return response;
    }
    return null;
  }

  @Override
  public List<ProgramManagerAccount> getProgramManagerAccountByProgramManagerId(
      Long programManagerAccountId) {
    return programManagerAccountRepository.findByProgramManagerId(programManagerAccountId);
  }

  @Override
  public void changeStatusPMAccnt(ProgramManager programManager) {
    programManagerAccountRepository.changeStatus(programManager.getStatus(),
        programManager.getUpdatedDate(), programManager.getUpdatedBy(), programManager.getId());
  }

/**
 * @param currencyId
 * @return
 */
  @Override
  public List<ProgramManager> getProgramManagerNameByAccountCurrency(String currencyId) {
    return programManagerRepository.findByAccountCurrencyAndStatusLike(currencyId,
        PGConstants.S_STATUS_ACTIVE);
  }

/**
 * @param id
 * @param currencyId
 * @return
 */
  @Override
  public MerchantResponse getProgramManagerNameByCurrencyAndId(Long id, String currencyId) {
    MerchantResponse response = new MerchantResponse();
    List<ProgramManagerRequest> list;
    StringBuilder query =
        new StringBuilder("select subqry.ID,subqry.PROGRAM_MANAGER_NAME")
            .append(" from (select distinct pm.Id,pm.PROGRAM_MANAGER_NAME ")
            .append(" from PG_PROGRAM_MANAGER as pm")
            .append(" where pm.ACCOUNT_CURRENCY = :currencyId )as subqry")
            .append(
                " where subqry.ID not in (select entity.ENTITY_ID from PG_MERCHANT_ENTITY_MAPPING as entity")
            .append(" where entity.ENTITY_ID = subqry.ID and entity.MERCHANT_ID = :merchantId)");
    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("merchantId", id);
    qry.setParameter("currencyId", currencyId);
    List<Object> programManagerResponse = qry.getResultList();
    list = new ArrayList<>();
    ProgramManagerRequest requests;
    if (StringUtil.isListNotNullNEmpty(programManagerResponse)) {
      Iterator<Object> itr = programManagerResponse.iterator();
      while (itr.hasNext()) {
        Object[] object = (Object[]) itr.next();
        requests = new ProgramManagerRequest();
        requests.setId(((BigInteger) object[0]).longValue());
        requests.setProgramManagerName(object[1].toString());
        list.add(requests);
      }
      response.setProgramManagerRequests(list);
    }
    return response;
  }
	
  @Override
  public void deleteCpProgramManagerMap(Long pmId) {
    pmCardProgramMappingRepository.deleteByPmId(pmId);
  }

  public List<ProgramManager> findByIssuancePmid(Long issuancePmId) {
    return programManagerRepository.findByIssuancepmid(issuancePmId);
  }

  /**
   * @param pmId
   * @param currencyId
   * @return
   */
  @Override
  public List<ProgramManager> findByProgramManagerIdAndAccountCurrency(Long pmId, String currencyId) {
    return programManagerRepository.findByIdAndAccountCurrency(pmId, currencyId);
  }

/**
 * @param pmSystemConvertedTime
 * @return
 */
	@Override
	public List<ProgramManager> findByBatchTime(String pmSystemConvertedTime) {
		return programManagerRepository.findByBatchTime(pmSystemConvertedTime);
	}

	/**
	 * @param merchantId
	 * @return
	 */
	@Override
	public CardProgramResponse fetchPMCardProgramByMerchantId(Long merchantId) {
		CardProgramResponse response = new CardProgramResponse();

		StringBuilder query = new StringBuilder(" SELECT pm.ID as programMangerId,pm.PROGRAM_MANAGER_NAME,pgcp.ID as cardProgramId,pgcp.CARD_PROGRAM_NAME,pgcp.IIN,pgcp.IIN_EXT, pgcp.IIN_PARTNER_EXT,pgcp.ISSUANCE_PARTNER_NAME,pgcp.CURRENCY ")
				.append(" FROM PG_MERCHANT_ENTITY_MAPPING pgem")
				.append(" JOIN PG_PROGRAM_MANAGER pm")
				.append(" ON pm.ID=pgem.ENTITY_ID")
				.append(" JOIN PG_PM_CARD_PROGRAM_MAPPING pmcp")
				.append(" ON pmcp.PM_ID=pm.ID")
				.append(" JOIN PG_CARD_PROGRAM pgcp")
				.append(" ON pgcp.ID               =pmcp.CARD_PROGRAM_ID")
				.append(" WHERE pgem.MERCHANT_ID = :merchantId")
				.append(" AND CARD_PROGRAM_ID NOT IN")
				.append("   (SELECT CARD_PROGRAM_ID FROM PG_MERCHANT_CARD_PROGRAM_MAPPING where MERCHANT_ID =:merchantId ")
				 .append("  )");

		Query qry = entityManager.createNativeQuery(query.toString());
		qry.setParameter("merchantId", merchantId);
		List<Object> cardProgramResponse = qry.getResultList();
		Iterator<Object> itr = cardProgramResponse.iterator();
		List<CardProgramRequest> cardProgramList = new ArrayList<>();
		setCardPrograms(cardProgramList, itr);
		response.setCardProgramList(cardProgramList);
		return response;
	}
	
	private void setCardPrograms(List<CardProgramRequest> cardProgramList,Iterator<Object> itr) {
		CardProgramRequest cardProgramRequest;
		while(itr.hasNext()){
			Object[] objs = (Object[]) itr.next();
			cardProgramRequest= new CardProgramRequest();
			cardProgramRequest.setProgramManagerId(StringUtil.isNull(objs[0]) ? null : ((BigInteger) objs[0]).longValue());
			cardProgramRequest.setProgramManagerName(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
			cardProgramRequest.setCardProgramId(StringUtil.isNull(objs[2]) ? null : ((BigInteger) objs[2]).longValue());
			cardProgramRequest.setCardProgramName(StringUtil.isNull(objs[3]) ? null : ((String) objs[3]));
			cardProgramRequest.setIin(StringUtil.isNull(objs[4]) ? null : ((String) objs[4]));
			cardProgramRequest.setIinExt(StringUtil.isNull(objs[5]) ? null : ((String)objs[5]));
			cardProgramRequest.setPartnerCode(getCardProgramDetails(objs, Integer.parseInt("6")));
			cardProgramRequest.setPartnerName(getCardProgramDetails(objs, Integer.parseInt("7")));
			cardProgramRequest.setCurrency(getCardProgramDetails(objs, Integer.parseInt("8")));
			cardProgramList.add(cardProgramRequest);
		}
	}

	/**
	 * @param objs
	 * @return
	 */
	private String getCardProgramDetails(Object[] objs, int index) {
		return StringUtil.isNull(objs[index]) ? null : ((String) objs[index]);
	}

  /**
   * @param cardProgramId
   * @return
   */
  @Override
  public PmCardProgamMapping findByCardProgramId(Long cardProgramId) {
    return pmCardProgramMappingRepository.findByCardProgramId(cardProgramId);
  }
  
  @Override
  public ProgramManager findByProgramManagerId(Long pmId) {
	  return programManagerRepository.findById(pmId);
  }

  @Override
  public ProgramManagerRequest findStatusAndBatchPrefixByProgramManagerId(Long id) {
//	  String batchPrefix = programManagerRepository.findStatusAndBatchPrefixByProgramManagerId(id);
	  ProgramManagerRequest response = new ProgramManagerRequest();
	  response.setId(id);
//	  response.setBatchPrefix(batchPrefix);
	  
	  StringBuilder query = new StringBuilder("select BATCH_PREFIX, STATUS from PG_PROGRAM_MANAGER where ID = :id"); 
	  
	  Query qry = entityManager.createNativeQuery(query.toString());
	  qry.setParameter("id", id);
	  
	  List<Object> obj = qry.getResultList();
	  
	  if(StringUtil.isListNotNullNEmpty(obj)){
	    Iterator<Object> itr = obj.iterator();
	    while(itr.hasNext()) {
	      Object[] pmList = (Object[])itr.next();
	      response.setBatchPrefix(pmList!=null ? ((String)pmList[0]) : null);
	      response.setStatus(pmList!=null ? ((String)pmList[1]) : null);
	    }
	  }
      return response;
  }
  
}
