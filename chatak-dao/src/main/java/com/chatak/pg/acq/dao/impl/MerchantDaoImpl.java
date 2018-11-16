package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.PGMerchantUsers;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGAccountHistory;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantConfig;
import com.chatak.pg.acq.dao.model.QPGTransfers;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.AdminUserDaoRepository;
import com.chatak.pg.acq.dao.repository.BankCurrencyMappingRepository;
import com.chatak.pg.acq.dao.repository.FeeProgramRepository;
import com.chatak.pg.acq.dao.repository.MerchantBankRepository;
import com.chatak.pg.acq.dao.repository.MerchantConfigRepositrory;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.MerchantUserRepository;
import com.chatak.pg.acq.dao.repository.StateRepository;
import com.chatak.pg.acq.dao.repository.TerminalRepository;
import com.chatak.pg.acq.dao.repository.TransactionRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.Merchant;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.AddStoreRequest;
import com.chatak.pg.user.bean.AddStoreResponse;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.DeleteStoreRequest;
import com.chatak.pg.user.bean.DeleteStoreResponse;
import com.chatak.pg.user.bean.DeleteTerminalRequest;
import com.chatak.pg.user.bean.DeleteTerminalResponse;
import com.chatak.pg.user.bean.FeeProgramNameListDTO;
import com.chatak.pg.user.bean.GetMerchantBankDetailsRequest;
import com.chatak.pg.user.bean.GetMerchantBankDetailsResponse;
import com.chatak.pg.user.bean.GetMerchantListResponse;
import com.chatak.pg.user.bean.GetStoreListRequest;
import com.chatak.pg.user.bean.GetStoreListResponse;
import com.chatak.pg.user.bean.GetTerminalListRequest;
import com.chatak.pg.user.bean.GetTerminalListResponse;
import com.chatak.pg.user.bean.UpdateMerchantResponse;
import com.chatak.pg.user.bean.UpdateStoreRequest;
import com.chatak.pg.user.bean.UpdateStoreResponse;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * Class provides implementation of API's required for Merchandise application,
 * basically to insert, update, delete and fetch the list of Merchant, Store and
 * Terminal related data.
 * 
 * @author Girmiti Software
 * @date 01-Jan-2015 4:14:47 PM
 * @version 1.0
 */
@SuppressWarnings("unused")
@Repository("merchantDao")
public class MerchantDaoImpl implements MerchantDao {

  private static Logger logger = Logger.getLogger(MerchantDaoImpl.class);

  @Autowired
  private MerchantRepository merchantRepository;

  @Autowired
  private MerchantConfigRepositrory merchantConfigRepositrory;

  @Autowired
  private FeeProgramRepository feeProgramRepository;

  @Autowired
  private StateRepository stateRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private MerchantBankRepository bankRepository;

  @Autowired
  private TerminalRepository terminalRepository;

  @Autowired
  private MerchantUserRepository merchantUserRepository;

  @Autowired
  private BankCurrencyMappingRepository bankCurrencyMappingRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private AdminUserDaoRepository adminUserDaoRepository;
  
  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Method to get merchant data on merchant code
   * 
   * @param merchantCode
   * @return
   */
  public PGMerchant getMerchant(String merchantCode) {
    return merchantRepository.findByMerchantCode(merchantCode);
  }

  protected BooleanExpression isUserNameLike(String userName) {
    return (userName != null && !"".equals(userName))
        ? QPGMerchant.pGMerchant.userName.toUpperCase().eq(userName.toUpperCase()) : null;
  }

  protected BooleanExpression isSubMerchantIdEq(String subId) {
    return (subId != null && !"".equals(subId))
        ? QPGMerchant.pGMerchant.parentMerchantId.eq(Long.valueOf(subId)) : null;
  }

  protected BooleanExpression isPhoneEq(Long phone) {
    return phone != null
        ? QPGMerchant.pGMerchant.phone.like("%" + phone + "%") : null;
  }

  protected BooleanExpression isMerchantCodeEq(String merchantCode) {
    return (merchantCode != null && !"".equals(merchantCode)) ? QPGMerchant.pGMerchant.merchantCode
        .toUpperCase().like("%" + merchantCode.toUpperCase().replace("*", "") + "%") : null;
  }

  protected BooleanExpression isBusinessNameLike(String businessName) {
    return (businessName != null && !"".equals(businessName)) ? QPGMerchant.pGMerchant.businessName
        .toUpperCase().like("%" + businessName.toUpperCase().replace("*", "") + "%") : null;
  }

  protected BooleanExpression isFirstNameLike(String firstName) {
    return (firstName != null && !"".equals(firstName)) ? QPGMerchant.pGMerchant.firstName
        .toUpperCase().like("%" + firstName.toUpperCase().replace("*", "") + "%") : null;
  }

  protected BooleanExpression isLastNameLike(String lastName) {
    return (lastName != null && !"".equals(lastName)) ? QPGMerchant.pGMerchant.lastName
        .toUpperCase().like("%" + lastName.toUpperCase().replace("*", "") + "%") : null;
  }

  protected BooleanExpression isEmailLike(String email) {
    return (email != null && !"".equals(email)) ? QPGMerchant.pGMerchant.emailId.toUpperCase()
        .like("%" + email.toUpperCase().replace("*", "") + "%") : null;
  }

  protected BooleanExpression isCityLike(String city) {
    return (city != null && !"".equals(city)) ? QPGMerchant.pGMerchant.city.toUpperCase()
        .like("%" + city.toUpperCase().replace("*", "") + "%") : null;
  }

  protected BooleanExpression isCountryEq(String country) {
    return (country != null && !"".equals(country)) ? QPGMerchant.pGMerchant.country.eq(country)
        : null;
  }

  protected BooleanExpression isStatusEqSelfRegistered(Integer status) {
    return (status != null && !"".equals(status.toString()))
        ? QPGMerchant.pGMerchant.status.eq(status) : null;
  }

  protected BooleanExpression isStatusEq(Integer status) {
    return (status != null && !"".equals(status.toString()))
        ? QPGMerchant.pGMerchant.status.eq(status) : null;
  }

  protected BooleanExpression isSubMerchantStatusNotEq() {
    return (QPGMerchant.pGMerchant.status.ne(Integer.parseInt("3")));
  }

  protected BooleanExpression isMerchantNotEq() {
    return (QPGMerchant.pGMerchant.merchantType.ne("TEST"));
  }

  protected BooleanExpression isMerchantStatusNotEq() {
    return (QPGMerchant.pGMerchant.status.ne(Integer.parseInt("3")));
  }

  protected BooleanExpression isMerchantStatusNotEqDecline() {
    return (QPGMerchant.pGMerchant.status.ne(Integer.parseInt("4")));
  }

  protected BooleanExpression isCreatedDateLike(Timestamp createdDate) {
    return createdDate != null
        ? QPGMerchant.pGMerchant.createdDate.eq(createdDate) : null;
  }

  protected OrderSpecifier<Long> orderByCreatedDateDesc() {
    return QPGMerchant.pGMerchant.id.desc();
  }

  @Override
  public GetMerchantListResponse getActiveMerchants() {
    return null;
  }

  @Override
  public UpdateMerchantResponse updateMerchantStatus(String merchantCode, Integer status) {
    return null;
  }

  @Override
  public GetMerchantBankDetailsResponse getMerchantBankDetails(
      GetMerchantBankDetailsRequest getMerchantBankDetailsRequest) {
    return null;
  }

  @Override
  public AddStoreResponse addStore(AddStoreRequest addStoreRequest) {
    return null;
  }

  @Override
  public UpdateStoreResponse updateStore(UpdateStoreRequest updateStoreRequest) {
    return null;
  }

  @Override
  public DeleteStoreResponse deleteStore(DeleteStoreRequest deleteStoreRequest) {
    return null;
  }

  @Override
  public GetStoreListResponse getStorelist(GetStoreListRequest searchStore) {
    return null;
  }

  @Override
  public AddTerminalResponse addTerminal(AddTerminalRequest addTerminalRequest) {
    return null;
  }

  @Override
  public UpdateTerminalResponse updateTerminal(UpdateTerminalRequest updateTerminalRequest) {
    return null;
  }

  @Override
  public DeleteTerminalResponse deleteTerminal(DeleteTerminalRequest deleteTerminalRequest) {
    return null;
  }

  @Override
  public GetTerminalListResponse getTerminallist(GetTerminalListRequest searchTerminal) {
    return null;
  }

  protected BooleanExpression isSubMerchantCodeEq(String subMerchantCode) {
    return (subMerchantCode != null && !"".equals(subMerchantCode)
        && null != QPGMerchant.pGMerchant.parentMerchantId)
            ? QPGMerchant.pGMerchant.merchantCode.toUpperCase()
                .like("%" + subMerchantCode.toUpperCase().replace("*", "") + "%")
            : null;
  }

  protected BooleanExpression isParentMerchantIdEq(Long id) {
    return (id != null && !"".equals(id.toString()))
        ? QPGMerchant.pGMerchant.parentMerchantId.eq(id) : null;
  }

  protected BooleanExpression isParentMerchantIdAlwaysEq(Long id) {
    return QPGMerchant.pGMerchant.parentMerchantId.eq(id);
  }

  protected BooleanExpression isParentMerchantIdNull(Integer status) {
    if (null != status && (Integer.parseInt("5") == status)) {
      return QPGMerchant.pGMerchant.status.eq(status);
    } else {
      return QPGMerchant.pGMerchant.parentMerchantId.isNull();
    }
  }

  public int getTotalNumberOfBalanceRecords() {
    JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
    List<Tuple> infoList = query.from(QPGMerchant.pGMerchant, QPGAccount.pGAccount)
    	.select(QPGMerchant.pGMerchant.userName,
            QPGMerchant.pGMerchant.businessName, QPGAccount.pGAccount.createdDate,
            QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.entityType,
            QPGMerchant.pGMerchant.localCurrency, QPGAccount.pGAccount.availableBalance,
            QPGAccount.pGAccount.currentBalance, QPGAccount.pGAccount.status)
        .where(QPGMerchant.pGMerchant.merchantCode.eq(QPGAccount.pGAccount.entityId))
        .orderBy(orderByCreatedDateDesc()).fetch();
    return (StringUtils.isListNotNullNEmpty(infoList) ? infoList.size() : 0);
  }

  @Override
  public List<ReportsDTO> getAccountTransferList() {
    List<ReportsDTO> reportList = null;
    ReportsDTO transferReport = null;
    JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
    List<Tuple> infoList =
        query.from(QPGTransfers.pGTransfers, QPGMerchant.pGMerchant)
        	.select(QPGTransfers.pGTransfers.fromAccount,
                QPGMerchant.pGMerchant.businessName, QPGTransfers.pGTransfers.updatedDate,
                QPGTransfers.pGTransfers.txnDescription, QPGMerchant.pGMerchant.userName,
                QPGTransfers.pGTransfers.pgTransfersId, QPGTransfers.pGTransfers.amount)
            .where(QPGMerchant.pGMerchant.merchantCode
                .eq(QPGTransfers.pGTransfers.merchantId.toString()))
            .orderBy().fetch();
    if (StringUtil.isListNotNullNEmpty(infoList)) {
      reportList = new ArrayList<>();
      for (Tuple tuple : infoList) {
        transferReport = new ReportsDTO();
        transferReport
            .setAccountNumber(Long.valueOf(tuple.get(QPGTransfers.pGTransfers.fromAccount)));
        transferReport.setCompanyName(tuple.get(QPGMerchant.pGMerchant.businessName));
        transferReport.setDateTime(DateUtil.toDateStringFormat(
            tuple.get(QPGTransfers.pGTransfers.updatedDate), DateUtil.VIEW_DATE_TIME_FORMAT));
        transferReport.setDescription(tuple.get(QPGTransfers.pGTransfers.txnDescription));
        transferReport.setUserName(tuple.get(QPGMerchant.pGMerchant.userName));
        transferReport
            .setTransactionId(tuple.get(QPGTransfers.pGTransfers.pgTransfersId).toString());
        transferReport.setAmount(PGConstants.DOLLAR_SYMBOL
            + StringUtils.amountToString(tuple.get(QPGTransfers.pGTransfers.amount)));
        reportList.add(transferReport);
      }
    }
    return reportList;
  }

  protected OrderSpecifier<Timestamp> orderByAccountHistoryUpdatedDateDesc() {
    return QPGAccountHistory.pGAccountHistory.updatedDate.desc();
  }

  @Override
  public Response getUserByEmailIdEdit(String email, String merchantCode) {
    PGMerchant merchant =
        merchantRepository.findByEmailIdAndStatusNotLike(email, PGConstants.STATUS_DELETED);
    Response response = new Response();
    if (merchant != null) {
      if (merchantCode.equals(merchant.getMerchantCode())) {
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        response.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        return response;
      }
      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
      return response;
    }
    PGMerchantUsers merchantUsers =
        merchantUserRepository.findByEmailIdAndStatusNotLike(email, PGConstants.STATUS_DELETED);
    if (merchantUsers != null) {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
      return response;
    } else {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return response;
    }
  }

  @Override
  public Response getUserByUsernameEdit(String userName, String merchantCode) {
    PGMerchant merchant = merchantRepository.findByUserName(userName);
    Response response = new Response();
    if (merchant != null) {
      if (merchantCode.equals(merchant.getMerchantCode())) {
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        response.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        return response;
      }
      response.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
      return response;
    } else {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return response;
    }
  }

  @Override
  public String getParentMerchantCode(String merchantCode) {
    return merchantRepository.getParentMerchantCode(merchantCode);
  }

  @Override
  public Response validateMerchantCode(String merchantCode) {
    PGMerchant merchant = merchantRepository.findByMerchantCode(merchantCode);
    Response response = new Response();
    if (merchant != null && null == merchant.getParentMerchantId() && (0 == merchant.getStatus())) {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      response.setParentMerchantId(merchant.getId());
      return response;
    } else {
      response.setErrorCode(ActionErrorCode.INVALID_PARENT_MERCHANT_CODE);
      response.setErrorMessage(
          ActionErrorCode.getInstance().getMessage(ActionErrorCode.INVALID_PARENT_MERCHANT_CODE));
      return response;
    }
  }

  @Override
  public String getAgentId(String merchantCode) {
    return merchantRepository.getAgentId(merchantCode);
  }

  @Override
  public List<String> getExistingAgentList(String partnerId) {
    JPAQuery<String> query = new JPAQuery<String>(entityManager);
    return query.distinct().from(QPGMerchant.pGMerchant)
    	.select(QPGMerchant.pGMerchant.agentId)
        .where(QPGMerchant.pGMerchant.agentId.isNotNull()
            .and(QPGMerchant.pGMerchant.status.ne(PGConstants.STATUS_DELETED)))
        .fetch();
  }

  /**
   * @param merchantCode
   * @return
   */
  @Override
  public String getApplicationMode(String merchantCode) {
    return merchantRepository.getApplicationMode(merchantCode);
  }

  /**
   * @return
   */
  @Override
  public List<Map<String, String>> getMerchantNamesAndMerchantCode() {
    return merchantRepository.getMerchantNameByMerchantCode();
  }

  /**
   * @param parentMerchantId
   * @return
   */
  @Override
  public List<PGMerchant> findById(Long parentMerchantId) {
    return merchantRepository.findByParentMerchantId(parentMerchantId);
  }

  /**
   * @param merchantCode
   * @return
   */
  @Override
  public Long getMerchantIdOnMerchantCode(String merchantCode) {
    return merchantRepository.findByMerchantCode(merchantCode).getId();
  }

  /**
   * @param merchantType
   * @return
   */
  @Override
  public List<Map<String, String>> getMerchantNameAndMerchantCodeByMerchantType(
      String merchantType) {
    return merchantRepository.getMerchantNameByMerchantCodeByMerchantType(merchantType);
  }

  @Override
  public String getMerchantTypeOnMerchantCode(String merchantCode) {
    return merchantRepository.getMerchantTypeOnMerchantCode(merchantCode);
  }

  @Override
  public String getMerchantCompanyNameOnMerchantCode(String merchantCode) {
    return merchantRepository.getMerchantBusinessNameOnMerchantCode(merchantCode);
  }

  /**
   * 
   * Method to get merchant code and corresponding company name
   * 
   * @return
   */
  @Override
  public Map<String, String> getAllMerchantMap() {
    JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
    Map<String, String> merchantMap = new HashMap<>();
    List<Tuple> tupleList = query.distinct().from(QPGMerchant.pGMerchant)
    	.select(QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.businessName)
        .fetch();
    if (StringUtil.isListNotNullNEmpty(tupleList)) {
      for (Tuple tuple : tupleList) {
        merchantMap.put(tuple.get(QPGMerchant.pGMerchant.merchantCode),
            tuple.get(QPGMerchant.pGMerchant.businessName));
      }
    }
    return merchantMap;
  }

  /**
   * @param merchantType
   * @return
   */
  @Override
  public List<Map<String, String>> getMerchantMapByMerchantType(String merchantType) {
    return merchantRepository.getMerchantMapByMerchantType(merchantType);
  }

  /**
   * @param feeProgram
   * @return
   */
  @Override
  public FeeProgramNameListDTO getActiveAndCurrentFeePrograms(String feeProgramName) {
    logger.info("MerchantDaoImpl | getActiveFeePrograms | Entering");
    FeeProgramNameListDTO feeProgramNameListDTO = new FeeProgramNameListDTO();
    List<PGFeeProgram> feeProgramsList = null;
    try {
      feeProgramsList = feeProgramRepository.findByStatusOrFeeProgramName("Active", feeProgramName);
      if (feeProgramsList != null && !feeProgramsList.isEmpty()) {
        feeProgramNameListDTO.setFeeProgramDTOs(feeProgramsList);
        feeProgramNameListDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        feeProgramNameListDTO.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      } else {
        feeProgramNameListDTO.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
        feeProgramNameListDTO.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }
    } catch (Exception e) {
      feeProgramNameListDTO.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      feeProgramNameListDTO
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
      logger.error("MerchantDaoImpl | getActiveFeePrograms | Exception " + e);
    }

    logger.info("MerchantDaoImpl | getActiveFeePrograms | Exiting");
    return feeProgramNameListDTO;
  }

  /**
   * @param merchant
   * @return
   */
  @Override
  public List<PGMerchant> getMerchantDetailsForAccountCreation(Merchant merchant) {
    logger.info("MerchantDaoImpl | getMerchantlistOnSubMerchantCode | Entering");

    List<PGMerchant> merchantList = null;
    int offset = 0;
    int limit = 0;
    Integer totalRecords;

    if (merchant.getPageIndex() == null || merchant.getPageIndex() == 1) {
      totalRecords = getTotalMerchantsForAccountDetails(merchant);
      merchant.setNoOfRecords(totalRecords);
    }
    if (merchant.getPageIndex() == null && merchant.getPageSize() == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (merchant.getPageIndex() - 1) * merchant.getPageSize();
      limit = merchant.getPageSize();
    }

    JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
    List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant)
    	.select(QPGMerchant.pGMerchant.businessName, QPGMerchant.pGMerchant.status,
            QPGMerchant.pGMerchant.merchantCode, QPGMerchant.pGMerchant.parentMerchantId)
        .where(QPGMerchant.pGMerchant.merchantType.eq(merchant.getMerchantType()),
            isBusinessNameLike(merchant.getBusinessName()),
            isMerchantCodeEq(merchant.getMerchantCode()),
            isSubMerchantCodeEq(merchant.getMerchantCode()))
        .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc())
        .fetch();
    if (!CollectionUtils.isEmpty(tupleList)) {
      merchantList = new ArrayList<>();
      PGMerchant pgMerchant = null;
      for (Tuple tuple : tupleList) {
        pgMerchant = new PGMerchant();
        pgMerchant.setBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
        pgMerchant.setStatus(tuple.get(QPGMerchant.pGMerchant.status));
        pgMerchant.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));

        merchantList.add(pgMerchant);
      }
    }
    return merchantList;
  }

  private Integer getTotalMerchantsForAccountDetails(Merchant merchant) {
    JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
    List<Long> list = query.from(QPGMerchant.pGMerchant).select(QPGMerchant.pGMerchant.id)
        .where(QPGMerchant.pGMerchant.merchantType.eq(merchant.getMerchantType()),
            isBusinessNameLike(merchant.getBusinessName()),
            isMerchantCodeEq(merchant.getMerchantCode()),
            isSubMerchantCodeEq(merchant.getMerchantCode()))
        .fetch();
    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  /**
   * @param merchant
   */
  @Override
  public void getMerchantConfigDetailsForAccountCreate(Merchant merchant) {
    PGMerchantConfig pgMerchantConfig =
        merchantConfigRepositrory.findById(merchant.getMerchantConfigId()).orElse(null);
    if (null != pgMerchantConfig) {
      merchant.setFeeProgram(pgMerchantConfig.getFeeProgram());
      merchant.setProcessor(pgMerchantConfig.getProcessor());
    }
  }

  /**
   * @param merchantType
   * @return
   */
  @Override
  public List<Map<String, String>> getMerchantCodeAndCompanyName(String merchantType) {
    return merchantRepository.getMerchantCodeAndCompanyName(merchantType);
  }
  
	@Override
	public PGMerchant findBymerchantConfig(String merchantId) {
		PGMerchantConfig deviceManufacturer = new PGMerchantConfig();
		deviceManufacturer.setId(Long.valueOf(merchantId));
		return merchantRepository.findByMerchantConfig(deviceManufacturer);
	}
}
