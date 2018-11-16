/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.CurrencyConfigDao;
import com.chatak.pg.acq.dao.model.PGCurrencyConfig;
import com.chatak.pg.acq.dao.model.QPGCurrencyConfig;
import com.chatak.pg.acq.dao.repository.CurrencyConfigRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.CurrencyDTO;
import com.chatak.pg.user.bean.CurrencyDTOList;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
/**
 * @Author: Girmiti Software
 * @Date: Nov 24, 2016
 * @Time: 4:43:18 PM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository("currencyConfigDao")
public class CurrencyConfigDaoImpl implements CurrencyConfigDao {

	private static Logger logger = Logger.getLogger(CurrencyConfigDaoImpl.class);

	@Autowired
	CurrencyConfigRepository currencyConfigRepository;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @param pgCurrencyConfig
	 * @return
	 */
	@Override
	public PGCurrencyConfig saveCurrencyConfig(PGCurrencyConfig pgCurrencyConfig) {

		logger.info("CurrencyConfigDaoImpl | saveCurrencyConfig | Entering");

		String currencyName = pgCurrencyConfig.getCurrencyName();
		PGCurrencyConfig saveCurrencyConfig = currencyConfigRepository.findByCurrencyName(currencyName);
		if (saveCurrencyConfig != null) {
				return null;
		}
		
		saveCurrencyConfig = new PGCurrencyConfig();
		saveCurrencyConfig.setCurrencyName(pgCurrencyConfig.getCurrencyName());
		saveCurrencyConfig.setCurrencyCodeNumeric(pgCurrencyConfig.getCurrencyCodeNumeric());
		saveCurrencyConfig.setCurrencyCodeAlpha(pgCurrencyConfig.getCurrencyCodeAlpha());
		saveCurrencyConfig.setCurrencyExponent(pgCurrencyConfig.getCurrencyExponent());
		saveCurrencyConfig.setCurrencySeparatorPosition(pgCurrencyConfig.getCurrencySeparatorPosition());
		saveCurrencyConfig.setCurrencyMinorUnit(pgCurrencyConfig.getCurrencyMinorUnit());
		saveCurrencyConfig.setStatus(pgCurrencyConfig.getStatus());
		saveCurrencyConfig.setCreatedBy(pgCurrencyConfig.getCreatedBy());
		saveCurrencyConfig.setCreatedDate(DateUtil.getCurrentTimestamp());
		saveCurrencyConfig.setCurrencyThousandsUnit(pgCurrencyConfig.getCurrencyThousandsUnit());
		return currencyConfigRepository.save(saveCurrencyConfig);
	}

	@Override
	public List<PGCurrencyConfig> searchCurrencyConfig(CurrencyDTO currencyDTO) {

		logger.info("CurrencyConfigDaoImpl | searchCurrencyConfig | Entering");

		int offset = 0;
		int limit = 0;
		Integer totalRecords = currencyDTO.getNoOfRecords();

		if (currencyDTO.getPageIndex() == null || currencyDTO.getPageIndex() == 1) {
			totalRecords = getTotalNumberOfRecords(currencyDTO);
			currencyDTO.setNoOfRecords(totalRecords);
		}
		currencyDTO.setNoOfRecords(totalRecords);
		if (currencyDTO.getPageIndex() == null && currencyDTO.getPageSize() == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (currencyDTO.getPageIndex() - 1) * currencyDTO.getPageSize();
			limit = currencyDTO.getPageSize();
		}

		JPAQuery<PGCurrencyConfig> query = new JPAQuery<PGCurrencyConfig>(entityManager);
		QPGCurrencyConfig qpgCurrencyConfig = QPGCurrencyConfig.pGCurrencyConfig;

		List<PGCurrencyConfig> list = query.from(qpgCurrencyConfig)
				.select(qpgCurrencyConfig)
				.where(isStatus(currencyDTO.getStatus()),isStatusNotEq(),
						isCurrencyName(currencyDTO.getCurrencyName()),
						isCurrencyCodeNumeric(currencyDTO.getCurrencyCodeNumeric()),
						isCurrencyCodeAlpha(currencyDTO.getCurrencyCodeAlpha()),
						isCurrencyExponent(currencyDTO.getCurrencyExponent()))
				.offset(offset).limit(limit).orderBy(orderByIdDesc()).fetch();
		logger.info("CurrencyConfigDaoImpl | searchCurrencyConfig | Exiting");
		return list;
	}

	private int getTotalNumberOfRecords(CurrencyDTO currencyDTO) {
		logger.info("CurrencyConfigDaoImpl | getTotalNumberOfRecords | Entering");
		JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
		List<Long> list = query.from(QPGCurrencyConfig.pGCurrencyConfig)
				.select(QPGCurrencyConfig.pGCurrencyConfig.id)
				.where(isStatus(currencyDTO.getStatus()),isStatusNotEq(),
						isCurrencyName(currencyDTO.getCurrencyName()),
						isCurrencyCodeNumeric(currencyDTO.getCurrencyCodeNumeric()),
						isCurrencyCodeAlpha(currencyDTO.getCurrencyCodeAlpha()),
						isCurrencyExponent(currencyDTO.getCurrencyExponent()))
				.fetch();
		logger.info("CurrencyConfigDaoImpl | getTotalNumberOfRecords | Exiting");
		return (StringUtil.isListNotNullNEmpty(list) ? list.size() : 0);
	}

	private OrderSpecifier<Long> orderByIdDesc() {
		return QPGCurrencyConfig.pGCurrencyConfig.id.desc();
	}

	private BooleanExpression isCurrencyName(String currencyName) {
		return (currencyName != null && !"".equals(currencyName))
				? QPGCurrencyConfig.pGCurrencyConfig.currencyName.like("%" + currencyName.replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isCurrencyCodeNumeric(String currencyNumeric) {
		return (currencyNumeric != null && !"".equals(currencyNumeric))
				? QPGCurrencyConfig.pGCurrencyConfig.currencyCodeNumeric
						.like("%" + currencyNumeric.replace("*", "") + "%")
				: null;
	}

	private BooleanExpression isCurrencyCodeAlpha(String currencyAlpha) {
		return (currencyAlpha != null && !"".equals(currencyAlpha))
				? QPGCurrencyConfig.pGCurrencyConfig.currencyCodeAlpha.like("%" + currencyAlpha.replace("*", "") + "%")
				: null;
	}
	
	private BooleanExpression isStatus(Integer status) {
		return null != status ? QPGCurrencyConfig.pGCurrencyConfig.status.eq(status) : null;
	}
	
	/**
	 * @param currencyConfigId
	 * @return
	 */
	@Override
	public PGCurrencyConfig findByCurrencyConfigId(Long currencyConfigId) {
		
		return currencyConfigRepository.findById(currencyConfigId).orElse(null);
	}

	/**
	 * @param pgCurrencyConfig
	 * @return
	 */
	@Override
	public PGCurrencyConfig updateCurrencyConfig(PGCurrencyConfig pgCurrencyConfig) {
		return currencyConfigRepository.save(pgCurrencyConfig);
	}

	/**
	 * @return
	 */
	@Override
	public CurrencyDTOList getActiveCurrencyConfigCode() {
		logger.info("CurrencyConfigDaoImpl | getActiveCurrencyConfigCode | Entering");
		CurrencyDTOList currencyDTOList = new CurrencyDTOList();
	    List<PGCurrencyConfig> currencyConfigList = null;
	    try {
	    	currencyConfigList = currencyConfigRepository.findByStatus(PGConstants.STATUS_SUCCESS);
	      if(currencyConfigList != null && !currencyConfigList.isEmpty()) {
	    	  currencyDTOList.setPgCurrencyDTOLists(currencyConfigList);
	    	  currencyDTOList.setErrorCode(ActionErrorCode.ERROR_CODE_00);
	    	  currencyDTOList.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
	      }
	      else {
	    	  currencyDTOList.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
	    	  currencyDTOList.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
	      }
	    }
	    catch(Exception exception) {
	      logger.error("CurrencyConfigDaoImpl | getActiveCurrencyConfigCode | Exception " + exception);
	      currencyDTOList.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
	      currencyDTOList.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
	    }

	    logger.info("CurrencyConfigDaoImpl | getActiveCurrencyConfigCode | Exiting");
	    return currencyDTOList;
	}

	/**
	 * @param CurrencyId
	 * @return
	 */
	@Override
	public PGCurrencyConfig getCurrencyCodeNumeric(String currencyCodeAlpha) {
		   PGCurrencyConfig pgCurrencyConfig = currencyConfigRepository.findByCurrencyCodeAlpha(currencyCodeAlpha);
		return pgCurrencyConfig;
	}

	/**
	 * @param bankCurrencyCode
	 * @return
	 */
	@Override
	public PGCurrencyConfig getcurrencyCodeAlpha(String currencyConfigCurrencyCodeNumeric) {
		PGCurrencyConfig pgCurrencyConfig = currencyConfigRepository.findByCurrencyCodeNumeric(currencyConfigCurrencyCodeNumeric);
		return pgCurrencyConfig;
	}
	
	private BooleanExpression isStatusNotEq(){
		  return(QPGCurrencyConfig.pGCurrencyConfig.status.ne(Integer.parseInt("3")));
	  }
	
	@Override
	public PGCurrencyConfig createOrUpdateCurrencyConfig(PGCurrencyConfig pgCurrencyConfig) {
	    return currencyConfigRepository.save(pgCurrencyConfig);
	}
	
	private BooleanExpression isCurrencyExponent(Integer currencyExponent) {
		return (!StringUtils.isNull(currencyExponent))
				? QPGCurrencyConfig.pGCurrencyConfig.currencyExponent.eq(currencyExponent)
				: null;
	}
	
}
