package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.AcquirerFeeValueDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGFeeProgramValue;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantConfig;
import com.chatak.pg.acq.dao.model.QPGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.QPGFeeProgram;
import com.chatak.pg.acq.dao.model.QPGMerchant;
import com.chatak.pg.acq.dao.model.QPGMerchantConfig;
import com.chatak.pg.acq.dao.repository.AcquirerFeeValueRepository;
import com.chatak.pg.acq.dao.repository.FeeProgramRepository;
import com.chatak.pg.acq.dao.repository.FeeProgramValueRepository;
import com.chatak.pg.acq.dao.repository.MerchantConfigRepositrory;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.FeeProgramDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("feeProgramDao")
public class FeeProgramDaoImpl implements FeeProgramDao {
  private static Logger logger = Logger.getLogger(FeeProgramDaoImpl.class);

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  FeeProgramRepository feeProgramRepository;

  @Autowired
  FeeProgramValueRepository feeProgramValueRepository;

  @Autowired
  AcquirerFeeValueRepository acquirerFeeValueRepository;

  @Autowired

  AcquirerFeeValueDao acquirerFeeValueDao;

  @Autowired
  MerchantRepository merchantRepository;

  @Autowired
  MerchantConfigRepositrory merchantConfigRepositrory;

  @Override
  public List<PGFeeProgram> findByFeeProgramName(String feeProgramName) throws DataAccessException {

    return feeProgramRepository.findByFeeProgramNameIgnoreCase(feeProgramName);
  }


  @Override
  public PGFeeProgram createFeeProgram(PGFeeProgram feeProgramDaoDetails)
      throws DataAccessException {

    return feeProgramRepository.save(feeProgramDaoDetails);
  }

  @Override
  public List<PGFeeProgramValue> createFeeProgramValue(
      List<PGFeeProgramValue> feeProgramValueDaoDetails) throws DataAccessException {

    return feeProgramValueRepository.save(feeProgramValueDaoDetails);
  }


  @Override
  public List<FeeProgramDTO> searchFeeProgramForJpa(FeeProgramDTO feeProgramDTO)
      throws DataAccessException {
    logger.info("prepaidservice :: FeeProgramDaoImpl :: searchFeeProgramForJpa Entering ");
    List<FeeProgramDTO> feeProgramRequestList = new ArrayList<FeeProgramDTO>();
    int offset = 0;
    int limit = 0;
    Integer totalRecords;

    if (feeProgramDTO.getPageIndex() == null || feeProgramDTO.getPageIndex() == 1) {
      totalRecords = getTotalNumberOfRecords(feeProgramDTO);
      feeProgramDTO.setNoOfRecords(totalRecords);
    }
    if (feeProgramDTO.getPageIndex() == null && feeProgramDTO.getPageSize() == null) {
      offset = 0;
      limit = Constants.DEFAULT_PAGE_SIZE;
    } else {
      offset = (feeProgramDTO.getPageIndex() - 1) * feeProgramDTO.getPageSize();
      limit = feeProgramDTO.getPageSize();
    }
    JPAQuery query = new JPAQuery(entityManager);
    QPGFeeProgram qfeecode = QPGFeeProgram.pGFeeProgram;
    List<Tuple> tupleCardList = query.from(qfeecode)
        .where(isFeeProgramName(feeProgramDTO.getFeeProgramName()),
            isStatusEq(feeProgramDTO.getStatus()), isProcessorEq(feeProgramDTO.getProcessor()),
            isFeeProgramId(feeProgramDTO.getFeeProgramId()), isStatusNotEq())
        .offset(offset).limit(limit).orderBy(orderByFeeProgramIdDsc())
        .list(qfeecode.feeProgramName, qfeecode.status, qfeecode.feeProgramId, qfeecode.processor);
    for (Tuple tuple : tupleCardList) {
      FeeProgramDTO feeProgram = new FeeProgramDTO();
      feeProgram.setFeeProgramName(tuple.get(qfeecode.feeProgramName));
      feeProgram.setStatus(tuple.get(qfeecode.status));
      feeProgram.setProcessor(tuple.get(qfeecode.processor));
      feeProgram.setFeeProgramId(tuple.get(qfeecode.feeProgramId));
      feeProgramRequestList.add(feeProgram);
    }
    return feeProgramRequestList;
  }


  private OrderSpecifier<Long> orderByFeeProgramIdDsc() {
    return QPGFeeProgram.pGFeeProgram.feeProgramId.desc();
  }

  private BooleanExpression isFeeProgramName(String feeProgramName) {
    return (feeProgramName != null && !"".equals(feeProgramName))
        ? QPGFeeProgram.pGFeeProgram.feeProgramName.toUpperCase()
            .like("%" + feeProgramName.toUpperCase().replace("*", "") + "%")

        : null;
  }

  private BooleanExpression isStatusEq(String status) {
    return (status != null && !"".equals(status)) ? QPGFeeProgram.pGFeeProgram.status.eq(status)
        : null;
  }

  private BooleanExpression isProcessorEq(String processor) {
    return (processor != null && !"".equals(processor))
        ? QPGFeeProgram.pGFeeProgram.processor.eq(processor) : null;
  }


  private BooleanExpression isFeeProgramId(Long feeProgramId) {
    return feeProgramId != null
        ? QPGFeeProgram.pGFeeProgram.feeProgramId.eq(feeProgramId) : null;
  }

  private int getTotalNumberOfRecords(FeeProgramDTO feeProgramDTO) {
    JPAQuery query = new JPAQuery(entityManager);
    QPGFeeProgram qfeecode = QPGFeeProgram.pGFeeProgram;
    List<Long> tupleCardList =
        query.from(qfeecode).where(isFeeProgramName(feeProgramDTO.getFeeProgramName()),
            isStatusEq(feeProgramDTO.getStatus()), isStatusNotEq()).list(qfeecode.feeProgramId);

    return (StringUtil.isListNotNullNEmpty(tupleCardList) ? tupleCardList.size() : 0);
  }

  private BooleanExpression isStatusNotEq() {
    String status = "Deleted";
    return (status != null && !"".equals(status)) ? QPGFeeProgram.pGFeeProgram.status.ne(status)
        : null;
  }

  @Override
  public List<PGFeeProgram> getByFeeProgramId(Long feePgmId) throws DataAccessException {

    return feeProgramRepository.findByFeeProgramId(feePgmId);
  }


  @Override
  public List<PGFeeProgramValue> getByFeeProgramValueId(Long feePgmId) throws DataAccessException {

    return feeProgramValueRepository.findByFeeProgramId(feePgmId);
  }


  @Override
  public void removeFeeProgram(List<PGFeeProgramValue> feeProgramValueDaoDetails)
      throws DataAccessException {
    feeProgramValueRepository.delete(feeProgramValueDaoDetails);

  }

  @Override
  public List<PGAcquirerFeeValue> getAcquirerFeeValueByMerchantIdAndCardType(String merchantId,
      String cardType) throws DataAccessException {

    JPAQuery query = new JPAQuery(entityManager);
    List<PGAcquirerFeeValue> tupleCardList =
        query.distinct()
            .from(QPGFeeProgram.pGFeeProgram, QPGAcquirerFeeValue.pGAcquirerFeeValue,
                QPGMerchant.pGMerchant, QPGMerchantConfig.pGMerchantConfig)
            .where(
                isStatusEq(
                    Constants.ACTIVE),
                QPGMerchant.pGMerchant.merchantCode.eq(merchantId)
                    .and(
                        QPGMerchant.pGMerchant.merchantConfig.id
                            .eq(QPGMerchantConfig.pGMerchantConfig.id)
                            .and(QPGMerchantConfig.pGMerchantConfig.feeProgram
                                .eq(QPGFeeProgram.pGFeeProgram.feeProgramName)
                                .and(QPGFeeProgram.pGFeeProgram.feeProgramId
                                    .eq(QPGAcquirerFeeValue.pGAcquirerFeeValue.feeProgramId)
                                    .and(QPGAcquirerFeeValue.pGAcquirerFeeValue.cardType
                                        .eq(cardType))))))
        .list(QPGAcquirerFeeValue.pGAcquirerFeeValue);
    if (CommonUtil.isListNotNullAndEmpty(tupleCardList)) {
      return tupleCardList;
    }
    return null;
  }

  @Override
  public Response deleteFeeProgram(Long getFeeProgramId) {
    boolean merchantMapped = false;
    Response response = new Response();
    List<PGFeeProgram> pgFeeProgram = feeProgramRepository.findByFeeProgramId(getFeeProgramId);

    if (StringUtil.isListNotNullNEmpty(pgFeeProgram)) {
      String feeName = pgFeeProgram.get(0).getFeeProgramName();
      List<PGMerchantConfig> pgMerchantConfig = merchantConfigRepositrory.findByFeeProgram(feeName);

      if (StringUtil.isListNotNullNEmpty(pgMerchantConfig)) {
        merchantMapped = validatePGMerchantConfig(response, pgMerchantConfig);

        if (!merchantMapped)
          response = deleteFee(pgFeeProgram, response);

      } else {
        response = deleteFee(pgFeeProgram, response);
      }
    } else {
      response.setErrorCode(ActionErrorCode.ERROR_CODE_F3);
      response
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_F3));
    }
    return response;
  }


  private boolean validatePGMerchantConfig(Response response,
      List<PGMerchantConfig> pgMerchantConfig) {
    boolean merchantMapped = false;
    for (PGMerchantConfig pgConfig : pgMerchantConfig) {
      Long pgId = pgConfig.getId();
      List<PGMerchant> pgMerchant = findByMerchantConfigIdAndStatusNotLike(pgId, Integer.parseInt("3")); //Here 3 means status Delete 
      if (StringUtil.isListNotNullNEmpty(pgMerchant)) {
        response.setErrorCode(ActionErrorCode.ERROR_CODE_F3);
        response.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_F3));
        merchantMapped = true;
        break;
      }
    }
    return merchantMapped;
  }

  private Response deleteFee(List<PGFeeProgram> pgFeeProgram, Response response) {
    pgFeeProgram.get(0).setStatus(PGConstants.S_STATUS_DELETED);
    pgFeeProgram.get(0).setUpdatedDate(DateUtil.getCurrentTimestamp());
    feeProgramRepository.save(pgFeeProgram);
    response.setErrorCode(ActionErrorCode.ERROR_CODE_F2);
    response
        .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_F2));
    return response;
  }

  private List<PGMerchant> findByMerchantConfigIdAndStatusNotLike(Long pgId, Integer status) {
    JPAQuery query = new JPAQuery(entityManager);
    List<PGMerchant> pgMerchant = query.from(QPGMerchant.pGMerchant)
        .where(isFeePgId(pgId), isStatusNEq(status)).list(QPGMerchant.pGMerchant);
    return pgMerchant;
  }

  private BooleanExpression isFeePgId(Long pgId) {
    return pgId != null ? QPGMerchant.pGMerchant.merchantConfig.id.eq(pgId)
        : null;
  }

  private BooleanExpression isStatusNEq(Integer status) {
    return status != null ? QPGMerchant.pGMerchant.status.ne(status) : null;
  }

  @Override
  public PGFeeProgram getFeeprogramName(String feeProgramName) {
    return feeProgramRepository.findByFeeProgramName(feeProgramName);
  }
}
