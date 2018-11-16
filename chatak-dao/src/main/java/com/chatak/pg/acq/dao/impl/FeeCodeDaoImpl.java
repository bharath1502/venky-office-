package com.chatak.pg.acq.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FeeCodeDao;
import com.chatak.pg.acq.dao.model.PGFeeCode;
import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;
import com.chatak.pg.acq.dao.model.QPGPartnerFeeCode;
import com.chatak.pg.acq.dao.repository.FeeCodeRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.PartnerFeeCodeDTO;
import com.chatak.pg.model.PartnerFeeCodeResponseDetails;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;
import com.chatak.pg.util.StringUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 21-Apr-2015 2:10:13 PM
 * @version 1.0
 */
@Repository("feeCodeDao")
public class FeeCodeDaoImpl implements FeeCodeDao {
  
  private static Logger logger = Logger.getLogger(FeeCodeDaoImpl.class);

  @Autowired
  private FeeCodeRepository feeCodeRepository;

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGFeeCode> getAllFeeCodeList() throws DataAccessException {

    return feeCodeRepository.findAll();
  }

  /**
   * @param feeCode
   * @return
   * @throws DataAccessException
   */
  @Override
  public List<PGFeeCode> getFeeCodeDetails(Long feeCode) throws DataAccessException {

    return feeCodeRepository.findByFeeCode(feeCode);
  }

  /**
   * Method to fetch fee code and other details of fee code for edit
   * 
   * @param feeProgramId
   * @return
   * @throws IllegalAccessException 
   * @throws Exception
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public List<PGFeeCode> getAllFeeCodeListForEdit(Long feeProgramId) throws IllegalAccessException {

    List<PGFeeCode> feeCodes = new ArrayList<PGFeeCode>();

    StringBuilder query = new StringBuilder("select distinct pfc.fee_code ,pfc.fee_short_code ,pfc.proc_code_txn_type ,pfc.response_code ,pfc.fee_description ").append(" from pg_fee_code pfc ,pg_fee_program_value pfpv ").append(" where pfc.fee_code NOT IN (select distinct fee_code from pg_fee_program_value where fee_program_id= :feeProgramId)");
    Query qry = entityManager.createNativeQuery(query.toString());
    qry.setParameter("feeProgramId", feeProgramId);

    List<Object> list = qry.getResultList();

    if(StringUtil.isListNotNullNEmpty(list)) {
      Iterator it = list.iterator();
      while(it.hasNext()) {
        try {
          Object[] objs = (Object[]) it.next();
          PGFeeCode feeCode = new PGFeeCode();

          setPGFeeCodeData(objs, feeCode);

          feeCodes.add(feeCode);

        }
        catch(Exception e) {
          logger.error("ERROR:: FeeCodeDaoImpl:: getAllFeeCodeListForEdit method", e);
          throw new IllegalAccessException(Properties.getProperty("chatak.general.error"));
        }
      }
    }

    return feeCodes;
  }

  private void setPGFeeCodeData(Object[] objs, PGFeeCode feeCode) {
	feeCode.setFeeCode(StringUtil.isNull(objs[0]) ? null : ((BigDecimal) objs[0]).longValue());
	feeCode.setFeeShortCode(StringUtil.isNull(objs[1]) ? null : ((String) objs[1]));
	feeCode.setTxnShortCode(StringUtil.isNull(objs[Integer.parseInt("2")]) ? null : ((String) objs[Integer.parseInt("2")]));
	feeCode.setTxnResponseCode(StringUtil.isNull(objs[Integer.parseInt("3")]) ? null : ((String) objs[Integer.parseInt("3")]));
	feeCode.setFeeDescription(StringUtil.isNull(objs[Integer.parseInt("4")]) ? null : ((String) objs[Integer.parseInt("4")]));
  }

  public PartnerFeeCodeResponseDetails getPartnetFeeList(PartnerFeeCodeDTO partnerFeeCodeDTO) {
    logger.info("MerchantDaoImpl | getMerchantlist | Entering");
    PartnerFeeCodeResponseDetails partnerFeeCodeResponseDetails = new PartnerFeeCodeResponseDetails();
    try {

      int offset = 0;
      int limit = 0;
      Integer totalRecords = partnerFeeCodeDTO.getNoOfRecords();

      if(partnerFeeCodeDTO.getPageIndex() == null || partnerFeeCodeDTO.getPageIndex() == 1) {
        totalRecords = getTotalNumberOfRecords();
        partnerFeeCodeDTO.setNoOfRecords(totalRecords);
      }
      partnerFeeCodeResponseDetails.setNoOfRecords(totalRecords);
      if(partnerFeeCodeDTO.getPageIndex() == null && partnerFeeCodeDTO.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      }
      else {
        offset = (partnerFeeCodeDTO.getPageIndex() - 1) * partnerFeeCodeDTO.getPageSize();
        limit = partnerFeeCodeDTO.getPageSize();
      }

      JPAQuery<PGPartnerFeeCode> query = new JPAQuery<PGPartnerFeeCode>(entityManager);
      List<PGPartnerFeeCode> pgPartnerFeeCodeList = query.from(QPGPartnerFeeCode.pGPartnerFeeCode).select(QPGPartnerFeeCode.pGPartnerFeeCode).offset(offset).limit(limit).orderBy(orderById()).fetch();
        List<PartnerFeeCodeDTO> partnerFeeCodeDTOList = new ArrayList<PartnerFeeCodeDTO>();
        if(CommonUtil.isListNotNullAndEmpty(pgPartnerFeeCodeList)) {
          for(PGPartnerFeeCode pgPartnerFeeCode : pgPartnerFeeCodeList) {
            PartnerFeeCodeDTO feeCodeDTO = new PartnerFeeCodeDTO();
            feeCodeDTO.setAccountNumber(pgPartnerFeeCode.getAccountNumber());
            feeCodeDTO.setMerchantCode(pgPartnerFeeCode.getPartnerEntityId());
            feeCodeDTO.setFlatFee(pgPartnerFeeCode.getFlatFee());
            feeCodeDTO.setPercentageOfTxn(pgPartnerFeeCode.getFeePercentageOnly());
            partnerFeeCodeDTOList.add(feeCodeDTO);
          }
          partnerFeeCodeResponseDetails.setPartnerFeeCodeList(partnerFeeCodeDTOList);
        }
    }
    catch(Exception e) {
       logger.error("MerchantDaoImpl | getMerchantlist | Exception " + e);
      partnerFeeCodeResponseDetails.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      partnerFeeCodeResponseDetails.setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }

     logger.info("MerchantDaoImpl | getMerchantlist | Exiting");
    return partnerFeeCodeResponseDetails;
  }

  private int getTotalNumberOfRecords() {
    JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
    List<Long> list = query.from(QPGPartnerFeeCode.pGPartnerFeeCode).select(QPGPartnerFeeCode.pGPartnerFeeCode.partnerFeeCodeId).fetch();

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private OrderSpecifier<Long> orderById() {
    return QPGPartnerFeeCode.pGPartnerFeeCode.partnerFeeCodeId.desc();
  }

}
