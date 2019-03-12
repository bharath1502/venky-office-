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
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.BINDao;
import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.acq.dao.model.QPGBINRange;
import com.chatak.pg.acq.dao.model.QPGSwitch;
import com.chatak.pg.acq.dao.repository.BINRepository;
import com.chatak.pg.bean.BinDuplicateResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.BinDTO;
import com.chatak.pg.model.BinResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.NumberExpression;

/**
 * @Author: Girmiti Software
 * @Date: Jun 10, 2015
 * @Time: 10:23:06 AM
 * @Version: 1.0
 * @Comments:
 *
 */
@Repository
public class BINDaoImpl implements BINDao {
  private static Logger logger = Logger.getLogger(BINDaoImpl.class);

  @Autowired
  BINRepository binRepository;

  @PersistenceContext
  private EntityManager entityManager;
  
  private static final List<Long> BINS = new ArrayList<>(1);

  /**
   * @return
   * @throws DataAccessException
   */
  @Override
  public boolean containsBin(Long bin) throws DataAccessException {
    List<PGBINRange> binList = binRepository.findByActiveBin(bin);
    if (CommonUtil.isListNotNullAndEmpty(binList))
      return true;
    else
      return false;
  }

  /**
   * @return
   */
  @Override
  public List<BinDTO> getAllBins() throws DataAccessException {
    logger.info("BINDaoImpl | getAllBins | Entering");
    List<PGBINRange> list = binRepository.findAll();
    List<BinDTO> binList = new ArrayList<>();
    if (CommonUtil.isListNotNullAndEmpty(list)) {
      for (PGBINRange pgBin : list) {
        BinDTO binDTO = new BinDTO();
        binDTO.setId(pgBin.getId());
        binDTO.setBinNumber(pgBin.getBin().intValue());
        binDTO.setCreatedDate(pgBin.getCreatedDate());
        binDTO.setSwitchId(pgBin.getSwitchId().intValue());
        binDTO.setEmvSupported(pgBin.getEmvSupported());
        binDTO.setDccSupported(pgBin.getDccSupported());
        if (pgBin.getStatus() == 0) {
          binDTO.setStatus(PGConstants.S_STATUS_ACTIVE);
        } else if (pgBin.getStatus() == 1) {
          binDTO.setStatus(PGConstants.S_STATUS_PENDING);
        }
        binList.add(binDTO);
      }
    }
    return binList;
  }

  /**
   * @return
   */
  @Override
  public List<Long> getAllActiveBins() throws DataAccessException {
//    List<PGBINRange> list = binRepository.getAllActiveBins();
//    List<Long> binList = new ArrayList<Long>(1);
//    if (CommonUtil.isListNotNullAndEmpty(list)) {
//      for (PGBINRange pgBin : list) {
//        binList.add(pgBin.getBin());
//      }
//    }
//    return binList;
    
    // PERF >> Retrieve only BIN column that is required
	if(BINS.isEmpty()) {
	  List<Long> binList = binRepository.getActiveBins();
	  BINS.addAll(binList);
	}
    return BINS;
  }

  /**
   * @param pgbinRange
   * @return
   * @throws DataAccessException
   */
  @Override
  public PGBINRange saveBin(PGBINRange pgbinRange) throws DataAccessException {
    try {
      PGBINRange updateBin = binRepository.findByBin(pgbinRange.getBin());
      if (null != updateBin) {

        updateBin.setStatus(pgbinRange.getStatus());
        updateBin.setEmvSupported(pgbinRange.getEmvSupported());
        updateBin.setDccSupported(pgbinRange.getDccSupported());
        updateBin.setSwitchId(pgbinRange.getSwitchId());
        return binRepository.save(updateBin);
      } else {
    	BINS.add(pgbinRange.getBin());
        return binRepository.save(pgbinRange);
      }
    } catch (Exception e) {
      logger.error("BINDaoImpl | update | Exception " + e);
    }
    return pgbinRange;
  }

  /**
   * @param binId
   * @return
   * @throws DataAccessException
   */
  @Override
  public BinDTO findById(Long binId) throws DataAccessException {
    logger.info("BINDaoImpl | findById | Entering");
    BinDTO binDTO = new BinDTO();
    PGBINRange pgbinRange = binRepository.findById(binId);
    if (pgbinRange != null) {
      binDTO.setBinNumber(pgbinRange.getBin().intValue());
      if (pgbinRange.getStatus() == 0) {
        binDTO.setStatus(PGConstants.S_STATUS_ACTIVE);
      } else if (pgbinRange.getStatus() == 1) {
        binDTO.setStatus(PGConstants.S_STATUS_PENDING);
      }
      binDTO.setDccSupported(pgbinRange.getDccSupported());
      binDTO.setEmvSupported(pgbinRange.getEmvSupported());
      binDTO.setSwitchId(pgbinRange.getSwitchId().intValue());
    }
    logger.info("BINDaoImpl | findById | Exiting");
    return binDTO;
  }

  /**
   * @param binDTO
   * @return
   * @throws DataAccessException
   */
  @Override
  public BinResponse searchBin(BinDTO binDTO) throws DataAccessException {
    logger.info("BINDaoImpl | searchBin | Entering");
    BinResponse binResponse = new BinResponse();
    List<BinDTO> binList = null;
    try {
      int offset = 0;
      int limit = 0;
      Integer totalRecords = binDTO.getNoOfRecords();
      if (binDTO.getPageIndex() == null || binDTO.getPageIndex() == 1) {
        totalRecords = getTotalNumberOfMerchantRecords(binDTO);
        binDTO.setNoOfRecords(totalRecords);
      }
      binResponse.setNoOfRecords(totalRecords);
      if (binDTO.getPageIndex() == null && binDTO.getPageSize() == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (binDTO.getPageIndex() - 1) * binDTO.getPageSize();
        limit = binDTO.getPageSize();
      }
      JPAQuery query = new JPAQuery(entityManager);
      List<Tuple> tupleList = query.from(QPGBINRange.pGBINRange, QPGSwitch.pGSwitch)
          .where(isBinEq(binDTO.getBinNumber()), isSwitchIdEq(binDTO.getSwitchId()),
              isIdEq(QPGBINRange.pGBINRange.switchId), isBinStatusNotEq())
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc()).list(
              QPGBINRange.pGBINRange.id, QPGBINRange.pGBINRange.bin, QPGBINRange.pGBINRange.status,
              QPGBINRange.pGBINRange.switchId, QPGBINRange.pGBINRange.dccSupported,
              QPGBINRange.pGBINRange.emvSupported, QPGSwitch.pGSwitch.switchName);
      if (!CollectionUtils.isEmpty(tupleList)) {
        binList = new ArrayList<BinDTO>();
        BinDTO pgBin = null;
        for (Tuple tuple : tupleList) {
          pgBin = new BinDTO();
          pgBin.setBinNumber(tuple.get(QPGBINRange.pGBINRange.bin).intValue());
          pgBin.setId(tuple.get(QPGBINRange.pGBINRange.id));
          validatePGBINRange(pgBin, tuple);
          pgBin.setSwitchId(tuple.get(QPGBINRange.pGBINRange.switchId).intValue());
          pgBin.setDccSupported(tuple.get(QPGBINRange.pGBINRange.dccSupported));
          pgBin.setEmvSupported(tuple.get(QPGBINRange.pGBINRange.emvSupported));
          pgBin.setSwitchName(tuple.get(QPGSwitch.pGSwitch.switchName));
          binList.add(pgBin);
        }
      }
      if (binList != null && !binList.isEmpty()) {
        binResponse.setBins(binList);
        binResponse.setErrorCode(PGConstants.SUCCESS);
        binResponse.setErrorMessage("Success");
      } else {
        binResponse.setErrorCode(ActionErrorCode.ERROR_CODE_BIN_01);
        binResponse.setErrorMessage(PGConstants.NO_RECORDS_FOUND);
      }
    } catch (Exception e) {
      logger.error("BINDaoImpl | searchBin | Exception " + e);
      binResponse.setErrorCode(ActionErrorCode.ERROR_CODE_BIN_01);
      binResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    logger.info("BINDaoImpl | searchBin | Exiting");
    return binResponse;

  }

  private void validatePGBINRange(BinDTO pgBin, Tuple tuple) {
	if (tuple.get(QPGBINRange.pGBINRange.status) == 0) {
	    pgBin.setStatus(PGConstants.S_STATUS_ACTIVE);
	  } else {
	    pgBin.setStatus(PGConstants.S_STATUS_SUSPENDED);
	  }
  }

  private int getTotalNumberOfMerchantRecords(BinDTO binDTO) {
    JPAQuery query = new JPAQuery(entityManager);
    List<PGBINRange> list = query.from(QPGBINRange.pGBINRange, QPGSwitch.pGSwitch)
        .where(isBinEq(binDTO.getBinNumber()), isSwitchIdEq(binDTO.getSwitchId()),
            isIdEq(QPGBINRange.pGBINRange.switchId), isSwitchIdEq(binDTO.getSwitchId()),
            isBinStatusNotEq())
        .list(QPGBINRange.pGBINRange);

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private BooleanExpression isBinEq(Integer bin) {
    return (bin != null ? QPGBINRange.pGBINRange.bin.eq(bin.longValue()) : null);
  }

  private BooleanExpression isSwitchIdEq(Integer switchId) {
    return (switchId != null ? QPGBINRange.pGBINRange.switchId.eq(switchId.longValue()) : null);
  }

  private BooleanExpression isBinStatusNotEq() {
    return (QPGBINRange.pGBINRange.status.ne(Integer.parseInt("3")));
  }

  private BooleanExpression isIdEq(NumberExpression<Long> numberExpression) {
    return (numberExpression != null ? QPGSwitch.pGSwitch.id.eq(numberExpression) : null);
  }

  private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
    return QPGBINRange.pGBINRange.createdDate.desc();
  }

  /**
   * @param binNumber
   * @return
   * @throws DataAccessException
   */
  @Override
  public BinDuplicateResponse getUserByBin(Long bin) throws DataAccessException {

    PGBINRange pgbinRange = binRepository.findByBin(bin);

    BinDuplicateResponse binResponse = new BinDuplicateResponse();

    if (pgbinRange != null) {

      if (pgbinRange.getStatus() != null && pgbinRange.getStatus() == Long.parseLong("3")) {
        binResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        binResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        return binResponse;

      } else {
        binResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
        binResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
        return binResponse;
      }
    } else {
      binResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      binResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return binResponse;
    }
  }

  @Override
  public PGBINRange findByBinId(Long binId) throws DataAccessException {
    PGBINRange pgbinRange = binRepository.findById(binId);
    return pgbinRange;
  }

  @Override
  public PGBINRange createOrUpdateBin(PGBINRange pGBINRange) throws DataAccessException {
    return binRepository.save(pGBINRange);
  }

}
