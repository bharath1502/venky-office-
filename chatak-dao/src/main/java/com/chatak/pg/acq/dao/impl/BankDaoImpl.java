/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.BankDao;
import com.chatak.pg.acq.dao.model.BankProgramManagerMap;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGBank;
import com.chatak.pg.acq.dao.model.PGBankCurrencyMapping;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.QPGBank;
import com.chatak.pg.acq.dao.model.QPGBankCurrencyMapping;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.BankCurrencyConfigRepository;
import com.chatak.pg.acq.dao.repository.BankCurrencyRepository;
import com.chatak.pg.acq.dao.repository.BankProgramManagerRepository;
import com.chatak.pg.acq.dao.repository.BankRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.user.bean.BankRequest;
import com.chatak.pg.user.bean.BankResponse;
import com.chatak.pg.user.bean.GetBankListResopnse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 2, 2016
 * @Time: 3:43:16 PM
 * @Version: 1.0
 * @Comments:
 *
 */

@Repository
public class BankDaoImpl implements BankDao {

	private static Logger logger = Logger.getLogger(MerchantDaoImpl.class);

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankProgramManagerRepository bankProgramManagerRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired		
	private BankCurrencyRepository bankCurrencyRepository;		
			
	@Autowired		
	private AccountRepository accountRepository;
	
	@Autowired
	private BankCurrencyConfigRepository bankCurrencyConfigRepository;
	
	@Autowired
	MerchantRepository merchantRepository;
	
	@Autowired
	MessageSource messageSource;

	/**
	 * @param bankRequest
	 * @return
	 */
	@Override
	public BankResponse createBank(BankRequest bankRequest) {
		logger.info("BankDaoImpl | createBank | Entering");
		BankResponse bankResponse = new BankResponse();
		PGBank pgBank = null;		
		PGBank pgBankCode = bankRepository.findByBankCode(bankRequest.getBankCode());
		if(pgBankCode != null){
			bankResponse.setErrorCode(ActionErrorCode.DUPLICATE_BANK_CODE);
			bankResponse.setErrorMessage(messageSource
		            .getMessage(ActionErrorCode.DUPLICATE_BANK_CODE, null, LocaleContextHolder.getLocale()));
			return bankResponse;
		}
		pgBank = bankRepository.findByBankName(bankRequest.getBankName());
		if (null != pgBank && !pgBank.getStatus().equals(PGConstants.S_STATUS_DELETED)) {
			
			bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B1);
			bankResponse.setErrorMessage(messageSource
		            .getMessage(ActionErrorCode.ERROR_CODE_B1, null, LocaleContextHolder.getLocale()));
			return bankResponse;
		}
		PGBank bank = new PGBank();
		bank.setIssuanceBankId(bankRequest.getIssuanceBankId());
		bank.setBankName(bankRequest.getBankName());
		bank.setBankShortName(bankRequest.getBankShortName());
		bank.setAcqirerId(bankRequest.getAcquirerId());
		bank.setAddress1(bankRequest.getAddress1());
		bank.setAddress2(bankRequest.getAddress2());
		bank.setCity(bankRequest.getCity());
		bank.setState(bankRequest.getState());
		bank.setZip(bankRequest.getZip());
		bank.setCountry(bankRequest.getCountry());
		bank.setCreatedBy(bankRequest.getCreatedBy());
		bank.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		bank.setStatus(PGConstants.S_STATUS_ACTIVE);
		bank.setCurrencyId(bankRequest.getCurrencyId());
		bank.setUpdatedBy(bankRequest.getCreatedBy());
		bank.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		bank.setBankCode(bankRequest.getBankCode());
	    bank.setSettlRoutingNumber(bankRequest.getSettlRoutingNumber());
	    bank.setExtension(bankRequest.getExtension());
	    bank.setSettlAccountNumber(bankRequest.getSettlAccountNumber());
	    bank.setContactPersonCell(bankRequest.getContactPersonCell());
	    bank.setContactPersonEmail(bankRequest.getContactPersonEmail());
	    bank.setContactPersonFax(bankRequest.getContactPersonFax());
	    bank.setContactPersonName(bankRequest.getContactPersonName());
	    bank.setContactPersonPhone(bankRequest.getContactPersonPhone());
		bank.setSettlAccountNumber(bankRequest.getSettlAccountNumber());
		bank.setSettlRoutingNumber(bankRequest.getSettlRoutingNumber());
		// Check if there is a revenue account with the bank's currency.		
				// If yes, associate it, else create a new revenue account with the currency code.		
				PGAccount account = accountRepository.findByEntityTypeAndCurrencyAndStatus(PGConstants.DEFAULT_ENTITY_TYPE, bankRequest.getCurrencyCodeAlpha(), PGConstants.S_STATUS_ACTIVE);		
				if(account != null) {		
					bank.setRevenueAccountNumber(account.getId());
							
				} else {		
						
					Long accNum = generateAccountNum();		
				    PGAccount pgAccount = new PGAccount();		
				    pgAccount.setAccountDesc(PGConstants.REVENUE_ACCOUNT);
				    pgAccount.setAccountNum(accNum);		
				    pgAccount.setAvailableBalance(PGConstants.ZERO);		
				    pgAccount.setCategory(PGConstants.PRIMARY_ACCOUNT);		
				    pgAccount.setCreatedDate(DateUtil.getCurrentTimestamp());		
				    pgAccount.setCurrentBalance(PGConstants.ZERO);		
				    pgAccount.setFeeBalance(PGConstants.ZERO);		
				    pgAccount.setCurrency(bankRequest.getCurrencyCodeAlpha());		
				    pgAccount.setEntityId("1");		
				    pgAccount.setEntityType(PGConstants.DEFAULT_ENTITY_TYPE);	
				    pgAccount.setStatus(Constants.ACTIVE);		
				    		
				    pgAccount = accountRepository.save(pgAccount);		
				    		
				    bank.setRevenueAccountNumber(pgAccount.getId());		
				}

				bank = bankRepository.save(bank);
				bankResponse.setBankid(bank.getId());
			    logger.info("BankDaoImpl | createBank | Bank details inserted successfully");

			    //Save currency mapping	if PGBankCurrencyMapping not exist
			    //else if PGBankCurrencyMapping exist with bankId update
			    PGBankCurrencyMapping bankCurrencyMapped = bankCurrencyRepository.findByBankId(bank.getId());
			    if (bankCurrencyMapped != null) {
			        bankCurrencyMapped.setBankId(bankCurrencyMapped.getId());
	                bankCurrencyMapped.setCurrencyCodeAlpha(bankRequest.getCurrencyCodeAlpha());
	                bankCurrencyMapped.setStatus(PGConstants.STATUS_SUCCESS);
	                bankCurrencyMapped.setCreatedBy(bankRequest.getCreatedBy());
	                bankCurrencyMapped.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	                bankCurrencyMapped.setUpdatedBy(bankRequest.getCreatedBy());
	                bankCurrencyMapped.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	                bankCurrencyRepository.save(bankCurrencyMapped);
                } else {
                  PGBankCurrencyMapping bankCurrency = new PGBankCurrencyMapping();
                  bankCurrency.setBankId(bank.getId());
                  bankCurrency.setCurrencyCodeAlpha(bankRequest.getCurrencyCodeAlpha());
                  bankCurrency.setStatus(PGConstants.STATUS_SUCCESS);
                  bankCurrency.setCreatedBy(bankRequest.getCreatedBy());
                  bankCurrency.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                  bankCurrency.setUpdatedBy(bankRequest.getCreatedBy());
                  bankCurrency.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                  bankCurrencyRepository.save(bankCurrency);
                }

				logger.info("BankDaoImpl | createBank | Bank currency mapping inserted successfully");
		bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B0);
		bankResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(
				ActionErrorCode.ERROR_CODE_B0));

		logger.info("BankDaoImpl | createBank | Exiting");
		return bankResponse;
	}
	
	public Long generateAccountNum() {		
	    String accountNum = CommonUtil.generateRandNumeric(PGConstants.LENGTH_MER_ACC_NUM);		
	    PGAccount response = accountRepository.findByAccountNum(Long.valueOf(accountNum));		
	    if(response  != null) {		
	      return generateAccountNum();		
	    }		
	    return Long.valueOf(accountNum);		
	  }		


	/**
	 * @param bankRequest
	 * @return
	 */
	@Override
	public BankResponse updateBank(BankRequest bankRequest) {
		logger.info("BankDaoImpl | updateBank | Entering");
		BankResponse bankResponse = new BankResponse();
		PGBank pgBank;

		pgBank = bankRepository.findByBankNameAndStatusNotLike(bankRequest.getBankName(),PGConstants.S_STATUS_DELETED);
		if (null == pgBank) {
			bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B5);
			bankResponse.setErrorMessage(ActionErrorCode.getInstance()
					.getMessage(ActionErrorCode.ERROR_CODE_B5));
			return bankResponse;
		}
		pgBank.setBankShortName(bankRequest.getBankShortName());
		pgBank.setAcqirerId(bankRequest.getAcquirerId());
		pgBank.setAddress1(bankRequest.getAddress1());
		pgBank.setAddress2(bankRequest.getAddress2());
		pgBank.setCity(bankRequest.getCity());
		pgBank.setState(bankRequest.getState());
		pgBank.setZip(bankRequest.getZip());
		pgBank.setCountry(bankRequest.getCountry());
		pgBank.setUpdatedBy(bankRequest.getUpdatedBy());
		pgBank.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		pgBank.setStatus(bankRequest.getStatus());
		pgBank.setBankCode(bankRequest.getBankCode());
		pgBank.setSettlRoutingNumber(bankRequest.getSettlRoutingNumber());
		pgBank.setExtension(bankRequest.getExtension());
		pgBank.setSettlAccountNumber(bankRequest.getSettlAccountNumber());
		pgBank.setContactPersonCell(bankRequest.getContactPersonCell());
		pgBank.setContactPersonEmail(bankRequest.getContactPersonEmail());
		pgBank.setContactPersonFax(bankRequest.getContactPersonFax());
		pgBank.setContactPersonName(bankRequest.getContactPersonName());
		pgBank.setContactPersonPhone(bankRequest.getContactPersonPhone());
		bankRepository.save(pgBank);
		logger.info("BankDaoImpl | updateBank | Bank details updated successfully");
		bankResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B4);
		bankResponse.setErrorMessage(ActionErrorCode.getInstance().getMessage(
				ActionErrorCode.ERROR_CODE_B4));

		logger.info("BankDaoImpl | updateBank | Exiting");
		return bankResponse;
	}

	/**
	 * @param bankName
	 * @return
	 */
	@Override
	public PGBank getBankByName(String bankName) {
		return bankRepository.findByBankNameAndStatusNotLike(bankName,PGConstants.S_STATUS_DELETED);
	}

	/**
	 * @param searchBank
	 * @return
	 */
	@Override
	public GetBankListResopnse getBanklist(BankRequest bankRequest) {
		logger.info("BankDaoImpl | getBanklist | Entering");
		GetBankListResopnse bankListResopnse = new GetBankListResopnse();
		List<PGBank> pgBankList = null;
		try {
			int offset = 0;
			int limit = 0;
			Integer totalRecords = bankRequest.getNoOfRecords();

			if (bankRequest.getPageIndex() == null
					|| bankRequest.getPageIndex() == 1) {
				totalRecords = getTotalNumberOfBankRecords(bankRequest);
				bankRequest.setNoOfRecords(totalRecords);
			}
			bankListResopnse.setTotalNoOfRows(totalRecords);
			if (bankRequest.getPageIndex() == null
					&& bankRequest.getPageSize() == null) {
				offset = 0;
				limit = Constants.DEFAULT_PAGE_SIZE;
			} else {
				offset = (bankRequest.getPageIndex() - 1)
						* bankRequest.getPageSize();
				limit = bankRequest.getPageSize();
			}

			JPAQuery query = new JPAQuery(entityManager);
			List<Tuple> tupleList = query
					.from(QPGBank.pGBank)
					.where(isBankNameLike(bankRequest.getBankName()),
							isBankCodeLike(bankRequest.getBankCode()),
							isEmailAddressLike(bankRequest.getContactPersonEmail()),
							isStatusEq(bankRequest.getStatus()),isBankStatusNotEq())
					.offset(offset)
					.limit(limit)
					.orderBy(orderByCreatedDateDesc())
					.list(QPGBank.pGBank.bankName,
							QPGBank.pGBank.bankCode,
							QPGBank.pGBank.contactPersonEmail, QPGBank.pGBank.status,QPGBank.pGBank.currencyId);
			if (!CollectionUtils.isEmpty(tupleList)) {
				pgBankList = new ArrayList<PGBank>();
				PGBank pgBank = null;
				for (Tuple tuple : tupleList) {
					pgBank = new PGBank();
					pgBank.setBankName(tuple.get(QPGBank.pGBank.bankName));
					pgBank.setBankCode(tuple.get(QPGBank.pGBank.bankCode));
					pgBank.setContactPersonEmail(tuple.get(QPGBank.pGBank.contactPersonEmail));
					pgBank.setStatus(tuple.get(QPGBank.pGBank.status));
					pgBank.setCurrencyId(tuple.get(QPGBank.pGBank.currencyId));

					pgBankList.add(pgBank);
				}
			}

			if (pgBankList != null && !pgBankList.isEmpty()) {
				bankListResopnse.setBanks(pgBankList);
				bankListResopnse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
				bankListResopnse.setErrorMessage(ActionErrorCode.getInstance()
						.getMessage(ActionErrorCode.ERROR_CODE_00));
			} else {
				bankListResopnse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
				bankListResopnse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
			}
		} catch (Exception e) {
			logger.error("BankDaoImpl | getBanklist | Exception "+ e);
			bankListResopnse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
			bankListResopnse.setErrorMessage(ActionErrorCode.getInstance()
					.getMessage(ActionErrorCode.ERROR_CODE_Z5));
		}
		logger.info("BankDaoImpl | getBanklist | Exiting");
		return bankListResopnse;
	}

	/**
	 * @param bankName
	 * @return
	 */
	@Override
	public BankResponse deleteBank(String bankName) {

		BankResponse bankDeleteResponse = new BankResponse();

		PGBank pgBank = bankRepository.findByBankName(bankName);

		if (null != pgBank) {
			
			Set<BankProgramManagerMap> set = bankProgramManagerRepository.findByBankId(pgBank.getId());
	        if (!set.isEmpty()) {
	          logger.info("Select bank linked to " + set.size() + " Entitie(s)");
	          bankDeleteResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
	          return bankDeleteResponse;
	        }
	        List<PGMerchant> pgMerchant = merchantRepository.findByBankId(pgBank.getId());
			if (StringUtil.isListNotNullNEmpty(pgMerchant)) {
				
				bankDeleteResponse.setErrorCode(ActionErrorCode.ERROR_CODE_BANK_LINKED);
				bankDeleteResponse.setErrorMessage(messageSource.getMessage("chatak.acquirer.deletebank.linked.error", null,
						LocaleContextHolder.getLocale()));
				
			} else {
				pgBank.setStatus(PGConstants.S_STATUS_DELETED);
				pgBank.setUpdatedDate(DateUtil.getCurrentTimestamp());
				
				bankRepository.save(pgBank);
				
				bankDeleteResponse.setErrorCode(ActionErrorCode.ERROR_CODE_B2);
				bankDeleteResponse.setErrorMessage(messageSource.getMessage(ActionErrorCode.ERROR_CODE_B2, null, LocaleContextHolder.getLocale()));
			}
			
		}

		return bankDeleteResponse;
	}

	private BooleanExpression isBankNameLike(String bankName) {
		return (bankName != null && !"".equals(bankName)) ? QPGBank.pGBank.bankName
				.toUpperCase().like(
						"%" + bankName.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isBankCodeLike(String bankCode) {
		return (bankCode != null && !"".equals(bankCode)) ? QPGBank.pGBank.bankCode
				.toUpperCase().like(
						"%" + bankCode.toUpperCase().replace("*", "")
								+ "%") : null;
	}

	private BooleanExpression isEmailAddressLike(String emailAddress) {
		return (emailAddress != null && !"".equals(emailAddress)) ? QPGBank.pGBank.contactPersonEmail
				.toUpperCase().like(
						"%" + emailAddress.toUpperCase().replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status)) ? QPGBank.pGBank.status
				.eq(status) : null;
	}
	 private BooleanExpression isBankStatusNotEq(){
		  return(QPGBank.pGBank.status.ne("Deleted"));
	  }

	private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
		return QPGBank.pGBank.createdDate.desc();
	}

	private int getTotalNumberOfBankRecords(BankRequest bankRequest) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> list = query
				.from(QPGBank.pGBank)
				.where(isBankNameLike(bankRequest.getBankName()),
						isBankCodeLike(bankRequest.getBankCode()),
						isEmailAddressLike(bankRequest.getContactPersonEmail()),
						isStatusEq(bankRequest.getStatus()),isBankStatusNotEq())
				.list(QPGBank.pGBank.id);

		return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
	}

	@Override
	public List<PGBank> getBankData() {
		
			List<PGBank> pgBank = null;
			  JPAQuery query = new JPAQuery(entityManager);
			  List<Tuple> tupleList = query.from(QPGBank.pGBank).
					  where(QPGBank.pGBank.status.like("Active")).
							  orderBy(orderByCreatedDateDesc()).
							  list(QPGBank.pGBank.id,
									  QPGBank.pGBank.bankName
									  );
			  if(!CollectionUtils.isEmpty(tupleList)) {
				  pgBank = new ArrayList<PGBank>();
				  PGBank bank = null;
				  for(Tuple tuple : tupleList) {
					  bank = new PGBank();
					  bank.setId(tuple.get(QPGBank.pGBank.id));
					  bank.setBankName(tuple.get(QPGBank.pGBank.bankName));
					  pgBank.add(bank);
				  }
			  }
			  return pgBank;
	}

	@Override
	public  List<PGBankCurrencyMapping> getCurrencyByBankId(Long bankId) {
		List<PGBankCurrencyMapping> pgMapping = null;
		
		JPAQuery query = new JPAQuery(entityManager);
		  List<Tuple> tupleList = query.from(QPGBankCurrencyMapping.pGBankCurrencyMapping).
				  where(QPGBankCurrencyMapping.pGBankCurrencyMapping.status.eq(0), QPGBankCurrencyMapping.pGBankCurrencyMapping.bankId.eq(bankId)).
				  orderBy(QPGBankCurrencyMapping.pGBankCurrencyMapping.createdDate.desc()).
				  list(QPGBankCurrencyMapping.pGBankCurrencyMapping.id,
						  QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha);
		
		  if(!CollectionUtils.isEmpty(tupleList)) {
			  pgMapping = new ArrayList<PGBankCurrencyMapping>();
			  PGBankCurrencyMapping map = null;
			  for(Tuple tuple : tupleList) {
				  map = new PGBankCurrencyMapping();
				  map.setId(tuple.get(QPGBankCurrencyMapping.pGBankCurrencyMapping.id));
				  map.setCurrencyCodeAlpha(tuple.get(QPGBankCurrencyMapping.pGBankCurrencyMapping.currencyCodeAlpha));
				  pgMapping.add(map);
			  }
			  
		  }
		  
		return pgMapping;
	}
	
	

	/**
	 * @param currencyId
	 * @return
	 */
	@Override
	public PGCurrencyConfig getCurrencyAlpha(Long currencyId) {
		PGCurrencyConfig pgCurrencyConfig = bankCurrencyConfigRepository.findById(currencyId);
		if(pgCurrencyConfig!=null){
			return pgCurrencyConfig;
		}
		return null;
	}

	/**
	 * @param currencyId
	 * @return
	 */
	@Override
	public List<PGBank> getBankName(Long currencyId) {
		return bankRepository.findByCurrencyIdAndStatusLike(currencyId, PGConstants.S_STATUS_ACTIVE);
	}
	
	@Override
	public PGBank createOrUpdateBank(PGBank pgBank) throws DataAccessException {
	    return bankRepository.save(pgBank);
	}
	
	@Override
	@Transactional
    public BankProgramManagerMap createOrUpdateBankProgramManagerMapping(BankProgramManagerMap bankProgramManagerMap)
            throws DataAccessException {
        return bankProgramManagerRepository.save(bankProgramManagerMap);
    }
	
  @Override
  public List<PGBank> findByIssuanceBankId(Long issuanceBankId) {
    return bankRepository.findByIssuanceBankId(issuanceBankId);
  }
}
