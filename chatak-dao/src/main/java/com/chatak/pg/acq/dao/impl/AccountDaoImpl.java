/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.QPGAccount;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantBank;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AccountBalanceDTO;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.user.bean.AddMerchantRequest;
import com.chatak.pg.user.bean.AddMerchantResponse;
import com.chatak.pg.user.bean.MerchantAccountSearchDto;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.DateUtils;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Apr 28, 2015
 * @Time: 9:07:17 PM
 * @Version: 1.0
 * @Comments:
 */
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
  
  private static Logger logger = Logger.getLogger(AccountDaoImpl.class);

  @Autowired
  private AccountRepository accountRepository;
  
  @PersistenceContext
  private EntityManager entityManager;
  
  /**
   * @param addMerchantRequest
   * @return
   * @throws DataAccessException
   */
  @Override
  public AddMerchantResponse savePgAcc(AddMerchantRequest addMerchantRequest,
      AddMerchantResponse addMerchantResponse) throws DataAccessException {

    Long accNum = generateAccountNum();
    PGAccount pgAccount = new PGAccount();
    pgAccount.setAccountDesc(PGConstants.ACC_DESC);
    pgAccount.setAccountNum(accNum);
    pgAccount.setAutoPaymentLimit(null!=addMerchantRequest.getAutoTransferLimit()?addMerchantRequest.getAutoTransferLimit():null);
    
    pgAccount.setAutoTransferDay(addMerchantRequest.getAutoTransferDay());
    pgAccount.setAutoPaymentMethod(addMerchantRequest.getAutoPaymentMethod());
    pgAccount.setAvailableBalance(PGConstants.ZERO);
    pgAccount.setCreatedDate(DateUtil.getCurrentTimestamp());
    pgAccount.setCategory(addMerchantRequest.getCategory());
    pgAccount.setCurrentBalance(PGConstants.ZERO);
    pgAccount.setEntityType(PGConstants.MERCHANT);
    pgAccount.setEntityId(addMerchantResponse.getEntityId());
    pgAccount.setStatus(Constants.ACTIVE);

    accountRepository.save(pgAccount);
    addMerchantResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    addMerchantResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));

    return addMerchantResponse;

  }

  private Long generateAccountNum() {

    String accountNum = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MER_ACC_NUM);

    PGAccount response = getAccount(Long.valueOf(accountNum));

    if(response != null) {
      return generateAccountNum();
    }
    return Long.valueOf(accountNum);

  }

  /**
   * @param accountNum
   * @return
   */
  private PGAccount getAccount(Long accountNum) {
    return accountRepository.findByAccountNum(accountNum);
  }

  /**
   * @param entityId
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGAccount getPgAccount(String entityId) throws DataAccessException {
    return accountRepository.findByEntityIdAndCategory(entityId,PGConstants.PRIMARY_ACCOUNT);
  }

  /**
   * @param pgAccount
   * @return
   * @throws DataAccessException
   */
  @Override
  public void savePGAccount(PGAccount pgAccount) throws DataAccessException {
    accountRepository.save(pgAccount);
  }

  public AccountRepository getAccountRepository() {
    return accountRepository;
  }

  public void setAccountRepository(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  /**
   * @param payoutFrequency
   * @param autoPaymentMethod
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGAccount> getPGAccountsOnPayoutFrequencyAndAutoPaymentMethod(String payoutFrequency,
      String autoPaymentMethod) throws DataAccessException {
    List<PGAccount> pgAccountList=new ArrayList<PGAccount>();
    List<PGAccount> temp_pgAccountList;
    if(payoutFrequency.startsWith(Constants.DAILY)){
      return accountRepository.findByPayoutFrequencyAndautoPaymentMethod(payoutFrequency, autoPaymentMethod);
    }
    else if(payoutFrequency.startsWith(Constants.WEEKLY)){
      temp_pgAccountList=accountRepository.findByPayoutFrequencyAndautoPaymentMethod(payoutFrequency, autoPaymentMethod);
      for(PGAccount pgAccount:temp_pgAccountList){
        String paymentDayWeek=pgAccount.getAutoTransferDay().split(":")[1];
        if(paymentDayWeek.equals(DateUtils.getCurrentDayOfWeekNumber()+"")){
          pgAccountList.add(pgAccount);
        }
      }
      return pgAccountList;
    } else if(payoutFrequency.startsWith(Constants.MONTHLY)){
      temp_pgAccountList=accountRepository.findByPayoutFrequencyAndautoPaymentMethod(payoutFrequency, autoPaymentMethod);
      for(PGAccount pgAccount:temp_pgAccountList){
        String paymentDayMonth=pgAccount.getAutoTransferDay().split(":")[1];
        if(paymentDayMonth.equals(DateUtils.getCurrentDayOfMonthNumber()+"")){
          pgAccountList.add(pgAccount);
        }
      }
      return pgAccountList;
    }
    return accountRepository.findByPayoutFrequencyAndautoPaymentMethod(payoutFrequency, autoPaymentMethod);
  }

  /**
   * @param payoutFrequency
   * @return
   * @throws DataAccessException
   */

  public List<ReportsDTO> getOverViewData(){
	  List<ReportsDTO> reportList = new ArrayList<ReportsDTO>();
    
    List<String> pgAccCurrency = accountRepository.getAllCurrency();
    for(String pgCurrency : pgAccCurrency){
    	ReportsDTO reportsDTO=new ReportsDTO();
    	reportsDTO.setMerchantAccountCount(accountRepository.getAccountCountOnEntityType("Merchant", pgCurrency));
    	reportsDTO.setSubMerchantAccountCount(accountRepository.getAccountCountOnEntityType("SubMerchant", pgCurrency));
    	reportsDTO.setChatakAccountCount(accountRepository.getAccountCountOnEntityType("Chatak", pgCurrency));
		reportsDTO.setMerchantAccountBalance(StringUtils.amountToString(accountRepository.getBalanceOnAccount("Merchant", pgCurrency)));
		reportsDTO.setSubMerchantAccountBalance(StringUtils.amountToString(accountRepository.getBalanceOnAccount("SubMerchant", pgCurrency)));
		reportsDTO.setChatakAccountBalance(StringUtils.amountToString(accountRepository.getBalanceOnAccount("Chatak", pgCurrency)));
		reportsDTO.setCurrency(pgCurrency);
		reportList.add(reportsDTO);
    }
    return reportList;
  }

  @Override
  public List<PGAccount> getPGAccountsOnPayoutFrequency(String payoutFrequency) throws DataAccessException {
    List<PGAccount> pgAccountList=new ArrayList<PGAccount>();
    List<PGAccount> temp_pgAccountList;
    if(payoutFrequency.startsWith(Constants.DAILY)){
      return accountRepository.findByPayoutFrequency(payoutFrequency);
    }
    else if(payoutFrequency.startsWith(Constants.WEEKLY)){
      temp_pgAccountList=accountRepository.findByPayoutFrequency(payoutFrequency);
      for(PGAccount pgAccount:temp_pgAccountList){
        String paymentDayWeek=pgAccount.getAutoTransferDay().split(":")[1];
        if(paymentDayWeek.equals(DateUtils.getCurrentDayOfWeekNumber()+"")){
          pgAccountList.add(pgAccount);
        }
      }
      return pgAccountList;
    } else if(payoutFrequency.startsWith(Constants.MONTHLY)){
      temp_pgAccountList=accountRepository.findByPayoutFrequency(payoutFrequency);
      for(PGAccount pgAccount:temp_pgAccountList){
        String paymentDayMonth=pgAccount.getAutoTransferDay().split(":")[1];
        if(paymentDayMonth.equals(DateUtils.getCurrentDayOfMonthNumber()+"")){
          pgAccountList.add(pgAccount);
        }
      }
      return pgAccountList;
    }
    return accountRepository.findByPayoutFrequency(payoutFrequency);
  }

  /**
   * @param pgAccount
   * @return
   */
  @Override
  public PGAccount addMerchantAccount(PGAccount pgAccount) {
    return accountRepository.save(pgAccount);
  }

  /**
   * @param merchantAccountSearchDto
   * @return
   */
  @Override
  public List<MerchantAccountSearchDto> searchMerchantAccount(MerchantAccountSearchDto requestDto) {
    
    List<MerchantAccountSearchDto> searchList = null;
    
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords;

      if(requestDto.getPageIndex() == null || requestDto.getPageIndex() == 1) {
        totalRecords = getTotalNumberOfRecords(requestDto);
        requestDto.setNoOfRecords(totalRecords);
      }
      
      if(requestDto.getPageIndex() == null && requestDto.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (requestDto.getPageIndex() - 1) * requestDto.getPageSize();
        limit = requestDto.getPageSize();
      }
      
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGMerchant.pGMerchant, QPGAccount.pGAccount, QPGMerchantBank.pGMerchantBank).
          where(isBusinessNameLike(requestDto.getBusinessName()),
          isAccountNoLike(requestDto.getMerchantAccountNumber()),
          isParentMerchantIdEq(requestDto.getParentMerchantCode()),
          isMerchantTypeEq(requestDto.getMerchantType()),
          isAccountStatusEq(requestDto.getAccountStatus()),
          isMerchantStatusEq(),
          QPGAccount.pGAccount.pgMerchantBank.id.eq(QPGMerchantBank.pGMerchantBank.id),
          QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode)).
          offset(offset).
          limit(limit).
          orderBy(orderByCreatedDateDesc()).
          list(QPGMerchant.pGMerchant.businessName,
              QPGMerchant.pGMerchant.merchantCode,
              QPGMerchant.pGMerchant.parentMerchantId,
              QPGMerchant.pGMerchant.merchantType,
              QPGAccount.pGAccount.id,
              QPGAccount.pGAccount.accountNum,
              QPGAccount.pGAccount.currentBalance,
              QPGAccount.pGAccount.status,
              QPGAccount.pGAccount.createdDate,
              QPGMerchantBank.pGMerchantBank.nameOnAccount,
              QPGMerchantBank.pGMerchantBank.state);
      
      if(!CollectionUtils.isEmpty(tupleList)) {
        searchList = new ArrayList<MerchantAccountSearchDto>();
        MerchantAccountSearchDto tempDto = null;
        for(Tuple tuple : tupleList) {
          tempDto = new MerchantAccountSearchDto();
          tempDto.setBusinessName(tuple.get(QPGMerchant.pGMerchant.businessName));
          tempDto.setMerchantCode(tuple.get(QPGMerchant.pGMerchant.merchantCode));
          tempDto.setParentMerchantCode(tuple.get(QPGMerchant.pGMerchant.parentMerchantId));
          tempDto.setMerchantType(tuple.get(QPGMerchant.pGMerchant.merchantType));
          tempDto.setAccountId(tuple.get(QPGAccount.pGAccount.id));
          tempDto.setMerchantAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
          tempDto.setCurrentBalance(StringUtils.amountToString(tuple.get(QPGAccount.pGAccount.currentBalance)));
          tempDto.setAccountStatus(tuple.get(QPGAccount.pGAccount.status));
          tempDto.setBankAccountName(tuple.get(QPGMerchantBank.pGMerchantBank.nameOnAccount));
          tempDto.setBankState(tuple.get(QPGMerchantBank.pGMerchantBank.state));
          searchList.add(tempDto);
        }
      }
    } catch(Exception e) {
      logger.error("AccountDaoImpl | searchMerchantAccount | Exception " + e);
    }
    return searchList;
  }
    
  /**
   * @param requestDto
   * @return
   */
  private Integer getTotalNumberOfRecords(MerchantAccountSearchDto requestDto) {
    JPAQuery query = new JPAQuery(entityManager);
    List<Long> tupleList = query.from(QPGMerchant.pGMerchant, QPGAccount.pGAccount, QPGMerchantBank.pGMerchantBank).
        where(isBusinessNameLike(requestDto.getBusinessName()),
        isAccountNoLike(requestDto.getMerchantAccountNumber()),
        isParentMerchantIdEq(requestDto.getParentMerchantCode()),
        isMerchantTypeEq(requestDto.getMerchantType()),
        isAccountStatusEq(requestDto.getAccountStatus()),
        isMerchantStatusEq(),
        QPGAccount.pGAccount.pgMerchantBank.id.eq(QPGMerchantBank.pGMerchantBank.id),
        QPGAccount.pGAccount.entityId.eq(QPGMerchant.pGMerchant.merchantCode)).
        list(QPGAccount.pGAccount.id);
    
    return (StringUtils.isListNotNullNEmpty(tupleList) ? tupleList.size() : 0);
  }

  private BooleanExpression isAccountNoLike(Long accountNo) {
    return (accountNo != null && !"".equals(accountNo.toString())) ? 
        QPGAccount.pGAccount.accountNum.like("%" + accountNo + "%") : null;
  }
  
  private BooleanExpression isBusinessNameLike(String businessName) {
    return (businessName != null && !"".equals(businessName)) ? 
        QPGMerchant.pGMerchant.businessName.toUpperCase().like("%" + businessName.toUpperCase() + "%") : null;
  }
  
  private BooleanExpression isParentMerchantIdEq(Long parentMerchantId) {
    return (null != parentMerchantId && !"".equals(parentMerchantId.toString())) ? QPGMerchant.pGMerchant.parentMerchantId.eq(parentMerchantId) : null;
  }
  
  private BooleanExpression isAccountStatusEq(String status) {
    return (null != status && !"".equals(status)) ? QPGAccount.pGAccount.status.eq(status) : null;
  }
  
  private BooleanExpression isMerchantTypeEq(String merchantType) {
    return (null != merchantType && !"".equals(merchantType)) ? QPGAccount.pGAccount.entityType.eq(merchantType) : null;
  }
  
  private BooleanExpression isMerchantStatusEq() {
    return QPGMerchant.pGMerchant.status.eq(PGConstants.STATUS_SUCCESS);
  }
  
  private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
    return QPGAccount.pGAccount.createdDate.desc();
  }

  @Override
  public void changeAccountStatus(Long accountId, String accountStatus, String reason) {
    
    PGAccount pgAccount = accountRepository.findOne(accountId);
    
    if(null != pgAccount) {
      pgAccount.setStatus(accountStatus);
      pgAccount.setReason(reason);
      pgAccount.setUpdatedDate(DateUtil.getCurrentTimestamp());
      
      accountRepository.save(pgAccount);
    }
  }

  /**
   * @param accountId
   * @return
   */
  @Override
  public PGAccount getAccountOnId(Long accountId) {
    return accountRepository.findById(accountId);
  }

  /**
   * @param accountNumber
   * @return
   */
  @Override
  public PGAccount getAccountonAccountNumber(Long accountNumber) {
    return accountRepository.findByAccountNum(accountNumber);
  }
  
  
  public PGAccount getSecondaryAccount(String entityId){
    
  List<PGAccount> list=accountRepository.findByEntityIdAndCategoryAndStatus(entityId, PGConstants.SECONDARY_ACCOUNT, Constants.ACTIVE);
  if(CommonUtil.isListNotNullAndEmpty(list)){
    return list.get(0);
  }
    return null;
  }

  /**
   * @param entityId
   * @return
   */
  @Override
  public List<PGAccount> getActivePGAccounts(String entityId) {
    return accountRepository.findByEntityIdAndStatus(entityId, Constants.ACTIVE);
  }
  
  public List<AccountBalanceDTO> getAccDetailsOnAccNums(List<Long> accounts) {
	    List<AccountBalanceDTO> accountBalanceDTOs = new ArrayList<AccountBalanceDTO>(0);
	    JPAQuery query = new JPAQuery(entityManager);
	    List<Tuple> tuples = query.from(QPGAccount.pGAccount)
	          .where(QPGAccount.pGAccount.accountNum.in(accounts))
	          .list(QPGAccount.pGAccount.accountNum, QPGAccount.pGAccount.availableBalance, QPGAccount.pGAccount.currentBalance, QPGAccount.pGAccount.accountDesc,QPGAccount.pGAccount.status);
	    if(!CollectionUtils.isEmpty(tuples)){
	      AccountBalanceDTO accountBalanceDTO = null;
	      for(Tuple tuple : tuples){
	        accountBalanceDTO = new AccountBalanceDTO();
	        accountBalanceDTO.setAccountNumber(tuple.get(QPGAccount.pGAccount.accountNum));
	        accountBalanceDTO.setNameOnAccount(tuple.get(QPGAccount.pGAccount.accountDesc));
	        accountBalanceDTO.setAvailableBalance(tuple.get(QPGAccount.pGAccount.availableBalance));
	        accountBalanceDTO.setCurrentBalance(tuple.get(QPGAccount.pGAccount.currentBalance));
	        accountBalanceDTO.setErrorCode(PGConstants.SUCCESS);
	        accountBalanceDTO.setStatus(tuple.get(QPGAccount.pGAccount.status));
	        accountBalanceDTOs.add(accountBalanceDTO);
	      }
	    }
	    return accountBalanceDTOs;
	  }

	@Override
	public List<MerchantAccountSearchDto> searchMerchantAccountOnMerchantCode(MerchantAccountSearchDto requestDto) {
	  return new ArrayList<>();
	}

}
