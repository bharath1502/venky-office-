/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.MDRDao;
import com.chatak.pg.acq.dao.model.PGDynamicMDR;
import com.chatak.pg.acq.dao.model.QPGDynamicMDR;
import com.chatak.pg.acq.dao.repository.MDRRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.DynamicMDRDTO;
import com.chatak.pg.util.Constants;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * @Author: Girmiti Software
 * @Date: Aug 31, 2016
 * @Time: 7:54:16 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository
public class MDRDaoImpl implements MDRDao{
	
	private static Logger logger = Logger.getLogger(MDRDaoImpl.class);

	@Autowired
	MDRRepository mdrRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PGDynamicMDR saveDynamicMDR(PGDynamicMDR pgDynamicMDR) {
		logger.info("MDRDaoImpl | saveDynamicMDR | Entering");
		try{
			PGDynamicMDR updateDynamicMDR = null;
			if (pgDynamicMDR.getBinNumber()!=null && pgDynamicMDR.getId()==null){  
				
				updateDynamicMDR = mdrRepository.findByBinNumber(pgDynamicMDR.getBinNumber());
				
			    if(updateDynamicMDR !=null){
			    	pgDynamicMDR = null;
			    	return pgDynamicMDR;
			    }
			    else{
				return mdrRepository.save(pgDynamicMDR);
				}
			 }else{
				 updateDynamicMDR= mdrRepository.findByBinNumber(pgDynamicMDR.getBinNumber());
				 updateDynamicMDR.setPaymentSchemeName(pgDynamicMDR.getPaymentSchemeName());
				 updateDynamicMDR.setAccountType(pgDynamicMDR.getAccountType());
				 updateDynamicMDR.setProductType(pgDynamicMDR.getProductType());
				 updateDynamicMDR.setBankName(pgDynamicMDR.getBankName());
				 updateDynamicMDR.setTransactionType(pgDynamicMDR.getTransactionType());
				 updateDynamicMDR.setSlab(pgDynamicMDR.getSlab());
				 updateDynamicMDR.setCreatedBy(pgDynamicMDR.getCreatedBy());
					return mdrRepository.save(updateDynamicMDR);
				}
		}
			catch(Exception e ){
			logger.error("MDRDaoImpl | saveDynamicMDR | Exception " + e);
		}
		logger.info("MDRDaoImpl | saveDynamicMDR | Exiting");
		return pgDynamicMDR;
}

	@Override
	public DynamicMDRDTO findById(Long getMDRId) {
		logger.info("MDRDaoImpl | findById | Entering");
		DynamicMDRDTO dynamicMDRDTO = new DynamicMDRDTO();
		PGDynamicMDR pgDynamicMDR = mdrRepository.findById(getMDRId).orElse(null);
		if(pgDynamicMDR != null){
			dynamicMDRDTO.setId(pgDynamicMDR.getId());
			dynamicMDRDTO.setBinNumber(pgDynamicMDR.getBinNumber());
			dynamicMDRDTO.setAccountType(pgDynamicMDR.getAccountType());
			dynamicMDRDTO.setProductType(pgDynamicMDR.getProductType());
			dynamicMDRDTO.setTransactionType(pgDynamicMDR.getTransactionType());
			dynamicMDRDTO.setPaymentSchemeName(pgDynamicMDR.getPaymentSchemeName());
			dynamicMDRDTO.setBankName(pgDynamicMDR.getBankName());
			dynamicMDRDTO.setSlab(pgDynamicMDR.getSlab().toString());
		}
		logger.info("MDRDaoImpl | findById | Exiting");
		return dynamicMDRDTO;
	}
	
	@Override
	public List<PGDynamicMDR> searchDynamicMDR(DynamicMDRDTO dynamicMDRDTO) {
		logger.info("MDRDaoImpl | searchDynamicMDR | Entering");
	int offset = 0;
    int limit = 0;
    Integer totalRecords = dynamicMDRDTO.getNoOfRecords();
    
    if(dynamicMDRDTO.getPageIndex() == null || dynamicMDRDTO.getPageIndex() == 1) {
        totalRecords = getTotalNumberOfRecords(dynamicMDRDTO);
        dynamicMDRDTO.setNoOfRecords(totalRecords);
      }
       dynamicMDRDTO.setNoOfRecords(totalRecords);
      if(dynamicMDRDTO.getPageIndex() == null && dynamicMDRDTO.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (dynamicMDRDTO.getPageIndex() - 1) * dynamicMDRDTO.getPageSize();
        limit = dynamicMDRDTO.getPageSize();
      }
		
		JPAQuery<PGDynamicMDR> query = new JPAQuery<PGDynamicMDR>(entityManager);
		QPGDynamicMDR qpgDynamicMDR = QPGDynamicMDR.pGDynamicMDR;
         
          List<PGDynamicMDR> list = query
				.from(qpgDynamicMDR)
				.select(qpgDynamicMDR)
				.where(isBinNumber(dynamicMDRDTO.getBinNumber()),
						isPaymentScheme(dynamicMDRDTO.getPaymentSchemeName()),
						isBankName(dynamicMDRDTO.getBankName()),
						isAccountType(dynamicMDRDTO.getAccountType()),
						isProductType(dynamicMDRDTO.getProductType()),
						isTransactionType(dynamicMDRDTO.getTransactionType()),
						isSlab(dynamicMDRDTO.getSlab()))
						.offset(offset)
			            .limit(limit)
			            .orderBy(orderByDeviceIdDesc())
				       .fetch();
          logger.info("MDRDaoImpl | searchDynamicMDR | Exiting");
		return list;
		
	}
	
	private int getTotalNumberOfRecords(DynamicMDRDTO dynamicMDRDTO) {
		logger.info("MDRDaoImpl | getTotalNumberOfRecords | Entering");
		JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
		List<Long> list = query.from(QPGDynamicMDR.pGDynamicMDR)
				 .select(QPGDynamicMDR.pGDynamicMDR.id)
				 .where(isBinNumber(dynamicMDRDTO.getBinNumber()),
							isPaymentScheme(dynamicMDRDTO.getPaymentSchemeName()),
							isBankName(dynamicMDRDTO.getBankName()),
							isAccountType(dynamicMDRDTO.getAccountType()),
							isProductType(dynamicMDRDTO.getProductType()),
							isTransactionType(dynamicMDRDTO.getTransactionType()),
							isSlab(dynamicMDRDTO.getSlab()))
						.fetch();
		logger.info("MDRDaoImpl | getTotalNumberOfRecords | Exiting");
		return (StringUtil.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	private OrderSpecifier<Long> orderByDeviceIdDesc() {
        return QPGDynamicMDR.pGDynamicMDR.id.desc();
    }
	
	private BooleanExpression isBinNumber(Long binNumber) {
		return binNumber != null ? QPGDynamicMDR.pGDynamicMDR.binNumber.eq(binNumber): null;
	}
	
	private BooleanExpression isPaymentScheme(String paymentSchemeName) {
		return (paymentSchemeName != null && !"".equals(paymentSchemeName))? QPGDynamicMDR.pGDynamicMDR.paymentSchemeName.like("%" + paymentSchemeName.replace("*", "") + "%"): null;
	}

	private BooleanExpression isBankName(String bankName) {
		return (bankName != null && !"".equals(bankName))? QPGDynamicMDR.pGDynamicMDR.bankName.like("%" + bankName.replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isAccountType(String accountType) {
		return (accountType != null && !"".equals(accountType))? QPGDynamicMDR.pGDynamicMDR.accountType.like("%" + accountType.replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isTransactionType(String transactionType) {
		return (transactionType != null && !"".equals(transactionType))? QPGDynamicMDR.pGDynamicMDR.transactionType.like("%" + transactionType.replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isProductType(String productType) {
		return (productType != null && !"".equals(productType))? QPGDynamicMDR.pGDynamicMDR.productType.like("%" + productType.replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isSlab(String slab) {
		return (slab != null && !"".equals(slab))? QPGDynamicMDR.pGDynamicMDR.slab.eq(Double.valueOf(slab)): null;
	}
}
