package com.chatak.pg.acq.dao.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

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
	public PGMerchant validateMerchantId(String merchantId, Long entityId, String userType) {
		PGMerchant merchant = new PGMerchant();
		Query qry = null;
		StringBuilder query = null;
		if (userType.equals(Constants.PM_USER_TYPE)) {
			query = new StringBuilder(" select a.MERCHANT_CODE,a.BUSINESS_NAME from ( select PGM.MERCHANT_CODE,PGM.BUSINESS_NAME ")
					.append(" FROM PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING PMEM ON PGM.ID = PMEM.MERCHANT_ID AND PMEM.ENTITY_ID=:entityId AND PGM.MERCHANT_CODE=:merchantCode AND PGM.STATUS=:status ")
					.append(" union ").append(" select PGM.MERCHANT_CODE,PGM.BUSINESS_NAME ")
					.append(" from PG_MERCHANT as PGM INNER JOIN PG_MERCHANT_ENTITY_MAPPING AS PMEM ON PGM.ID = PMEM.MERCHANT_ID")
					.append(" INNER JOIN PG_PM_ISO_MAPPING AS PMIM ON PMEM.ENTITY_ID = PMIM.ISO_ID AND PMIM.PM_ID =:entityId AND PGM.MERCHANT_CODE =:merchantCode AND PGM.STATUS=:status ")
					.append(" )a ");
			qry = entityManager.createNativeQuery(query.toString());
			qry.setParameter("entityId", entityId);
			qry.setParameter("merchantCode", merchantId);
			qry.setParameter("status", PGConstants.STATUS_SUCCESS);
			List<Object> list = qry.getResultList();
			if (StringUtil.isListNotNullNEmpty(list)) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Object[] objs = (Object[]) it.next();
					merchant.setMerchantCode(getMerchantCode(objs));
					merchant.setBusinessName(getBusinessName(objs));
				}
			}
		} else {
			List<PGMerchant> merchants = merchantRepository.findByMerchantCodeAndStatus(merchantId,
					PGConstants.STATUS_SUCCESS);
			if (StringUtil.isListNotNullNEmpty(merchants)) {
				merchant = merchants.get(0);
			}
		}
		return merchant;
	}


  private String getBusinessName(Object[] objs) {
    return StringUtil.isNull(objs[1]) ? null : ((String) objs[1]);
  }

  private String getMerchantCode(Object[] objs) {
    return StringUtil.isNull(objs[0]) ? null : ((String) objs[0]);
  }
}
