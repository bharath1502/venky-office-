package com.chatak.pg.acq.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MerchantTerminalDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantTerminal;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.acq.dao.repository.MerchantTerminalRepository;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.util.CommonUtil;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository("merchantTerminalDao")
public class MerchantTerminalDaoImpl implements MerchantTerminalDao {
	
	private static Logger logger = Logger.getLogger(MerchantTerminalDaoImpl.class);

	private static final String CLASS_NAME=MerchantTerminalDaoImpl.class.getSimpleName();

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	MerchantTerminalRepository merchantTerminalRepository;
	
	@Autowired
  private MerchantRepository merchantRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public PGMerchantTerminal createOrUpdateMerchantTerminal(PGMerchantTerminal merchantTerminal) throws DataAccessException {
		return merchantTerminalRepository.save(merchantTerminal);
	}

	@Override
	public PGMerchantTerminal findByTerminalId(String terminalId) throws DataAccessException {
		return merchantTerminalRepository.findByTerminalId(terminalId);
	}

	@Override
	public PGMerchantTerminal findById(Long id) throws DataAccessException {
		return merchantTerminalRepository.findById(id);
	}

	@Override
	public List<PGMerchantTerminal> findByMerchantId(String merchantId) throws DataAccessException {
		return merchantTerminalRepository.findByMerchantId(merchantId);
	}
	
	@Override
	public List<Long> getTerminalsByMerchantIdList(List<Long> merchantIdList)throws DataAccessException{
		final String METHOD_NAME = "findByMerchantIdList";
		logger.info("Entering:: "+CLASS_NAME +" : "+METHOD_NAME);
		
		logger.info("Exiting:: "+CLASS_NAME +" : "+METHOD_NAME);
		return Collections.emptyList();
	}
	
	public PGMerchant validateMerchantIdAndTerminalId(String merchantId, String terminalId)throws DataAccessException{
	  List<PGMerchant> merchants = merchantRepository
	      .findByMerchantCodeAndStatus(merchantId,
	                                   PGConstants.STATUS_SUCCESS);

	  if (merchants != null && !merchants.isEmpty()) {
	    PGMerchant merchant = merchants.get(0);
	    if(merchantTerminalRepository.findByMerchantIdAndTerminalId(merchant.getId(), terminalId)!=null) {
	      List<PGAccount> accountList=accountRepository.findByEntityIdAndCategoryAndStatus(merchantId, PGConstants.PRIMARY_ACCOUNT, "Active");
	      PGAccount account=null;
	      if(CommonUtil.isListNotNullAndEmpty(accountList))
	      {
	        account=accountList.get(0);
	      }
	      //Validating Merchant Active Primary Account before allowing for transaction 
	      if(null!=account){
	        return merchant;
	      }
	      return null;
	    } else {
	      return null;
	    }
	  }
	  return null;
	}

	/**
	 * @param merchantId
	 * @return
	 */
	@Override
	public PGMerchant validateMerchantId(String merchantId) {
		List<PGMerchant> merchants = merchantRepository
			      .findByMerchantCodeAndStatus(merchantId,
			                                   PGConstants.STATUS_SUCCESS);
		PGMerchant merchant = merchants.get(0);
		return merchant;
	}

	/**
	 * @return
	 */
}
