package com.chatak.pg.acq.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.acq.dao.model.QPGSwitch;
import com.chatak.pg.acq.dao.repository.SwitchRepository;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.chatak.pg.util.StringUtils;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * Dao class to perform switch record related insert, update and search
 * operation
 */
@Repository("switchDao")
public class SwitchDaoImpl implements SwitchDao {
  private static Logger log = Logger.getLogger(SwitchDaoImpl.class);

  @Autowired
  private SwitchRepository switchRepository;

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public PGSwitch getSwitchByName(String switchName) {
    List<PGSwitch> switchs = switchRepository.findBySwitchName(switchName);
    return switchs != null && !switchs.isEmpty() ? switchs.get(0) : null;
  }

  public List<PGSwitch> getAllSwitchNamesByStatus(Integer status) {
    return switchRepository.findByStatus(status);
  }

  @Override
  public SwitchResponse addSwitchInformation(SwitchRequest addSwitchRequest, String userid) {
    log.info("SwitchDaoImpl | addSwitchInformation | Entering");
    SwitchResponse addSwitchResponse = new SwitchResponse();
    try {
      PGSwitch pgSwitch = new PGSwitch();
      pgSwitch.setCreatedDate(DateUtil.getCurrentTimestamp());
      pgSwitch.setSwitchName(addSwitchRequest.getSwitchName());
      pgSwitch.setSwitchType(addSwitchRequest.getSwitchType());
      pgSwitch.setPrimarySwitchURL(addSwitchRequest.getPrimarySwitchURL());
      pgSwitch.setPrimarySwitchPort(addSwitchRequest.getPrimarySwitchPort());
      pgSwitch.setSecondarySwitchURL(addSwitchRequest.getSecondarySwitchURL());
      pgSwitch.setPriority(addSwitchRequest.getPriority());
      pgSwitch.setStatus(addSwitchRequest.getStatus());
      pgSwitch.setSecondarySwitchPort(addSwitchRequest.getSecondarySwitchPort());
      pgSwitch.setCreatedBy(Long.parseLong(userid));
      switchRepository.save(pgSwitch);
      addSwitchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
    } catch (Exception e) {
      log.error("SwitchDaoImpl | addSwitchInformation | Exception" + e);
      addSwitchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z5);
      addSwitchResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z5));
    }
    log.info("SwitchDaoImpl | addSwitchInformation | Exiting");
    return addSwitchResponse;
  }

  public List<SwitchRequest> searchSwitchInformation(SwitchRequest searchSwitchRequest) {
    log.info("SwitchDaoImpl | searchSwitchInfo | Entering");

    Integer pageIndex = searchSwitchRequest.getPageIndex();
    Integer pageSize = searchSwitchRequest.getPageSize();
    Integer totalRecords = searchSwitchRequest.getNoOfRecords();
    List<SwitchRequest> searchswitchList = new ArrayList<>();
    try {
      int offset = 0;
      int limit = 0;

      if (pageIndex == null || pageIndex == 1) {
        totalRecords = getTotalNumberOfRecords(searchSwitchRequest);
        searchSwitchRequest.setNoOfRecords(totalRecords);
      }

      if (pageIndex == null && pageSize == null) {
        offset = 0;
        limit = Constants.DEFAULT_PAGE_SIZE;
      } else {
        offset = (pageIndex - 1) * pageSize;
        limit = pageSize;
      }

      JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
      List<Tuple> tupleList = query.from(QPGSwitch.pGSwitch)
    	   .select(QPGSwitch.pGSwitch.id,
              QPGSwitch.pGSwitch.switchName, QPGSwitch.pGSwitch.switchType,
              QPGSwitch.pGSwitch.primarySwitchURL, QPGSwitch.pGSwitch.primarySwitchPort,
              QPGSwitch.pGSwitch.secondarySwitchURL, QPGSwitch.pGSwitch.secondarySwitchPort,
              QPGSwitch.pGSwitch.priority, QPGSwitch.pGSwitch.status)
          .where(isSwitchNameLike(searchSwitchRequest.getSwitchName()),
              isSwitchTypeLike(searchSwitchRequest.getSwitchType()),
              isPrimarySwitchURLLike(searchSwitchRequest.getPrimarySwitchURL()),
              isPrimarySwitchPortEq(searchSwitchRequest.getPrimarySwitchPort()),
              isSecondarySwitchURLLike(searchSwitchRequest.getSecondarySwitchURL()),
              isSecondarySwitchPortEq(searchSwitchRequest.getSecondarySwitchPort()),
              isPriorityEq(searchSwitchRequest.getPriority()),
              isStatusEq(searchSwitchRequest.getStatus()))
          .offset(offset).limit(limit).orderBy(orderByCreatedDateDesc()).fetch();

      if (!CollectionUtils.isEmpty(tupleList)) {
        SwitchRequest pgswitch = null;
        for (Tuple tuple : tupleList) {

          pgswitch = new SwitchRequest();
          pgswitch.setId(tuple.get(QPGSwitch.pGSwitch.id));
          pgswitch.setSwitchName(tuple.get(QPGSwitch.pGSwitch.switchName));
          pgswitch.setSwitchType(tuple.get(QPGSwitch.pGSwitch.switchType));
          pgswitch.setPrimarySwitchURL(tuple.get(QPGSwitch.pGSwitch.primarySwitchURL));
          pgswitch.setPrimarySwitchPort(tuple.get(QPGSwitch.pGSwitch.primarySwitchPort));
          pgswitch.setSecondarySwitchURL(tuple.get(QPGSwitch.pGSwitch.secondarySwitchURL));
          pgswitch.setSecondarySwitchPort(tuple.get(QPGSwitch.pGSwitch.secondarySwitchPort));
          pgswitch.setPriority(tuple.get(QPGSwitch.pGSwitch.priority));
          pgswitch.setStatus(tuple.get(QPGSwitch.pGSwitch.status));
          searchswitchList.add(pgswitch);
        }
      }
    } catch (Exception e) {
      log.error("SwitchDaoImpl | searchSwitchInfo | Exception " + e);
    }
    log.info("SwitchDaoImpl | searchSwitchInfo | Exiting");
    return searchswitchList;
  }

  @Override
  public SwitchResponse updateSwitchInformation(SwitchRequest updateSwitchRequest, String userid) {
    log.info("SwitchDaoImpl | updateSwitch | Entering");
    SwitchResponse updateSwitchResponse = new SwitchResponse();
    PGSwitch pgSwitch = switchRepository.findById(updateSwitchRequest.getId()).orElse(null);
    try {
      if (pgSwitch != null) {
        pgSwitch.setId(updateSwitchRequest.getId());
        pgSwitch.setSwitchName(updateSwitchRequest.getSwitchName());
        pgSwitch.setSwitchType(updateSwitchRequest.getSwitchType());
        pgSwitch.setPrimarySwitchURL(updateSwitchRequest.getPrimarySwitchURL());
        pgSwitch.setPrimarySwitchPort(updateSwitchRequest.getPrimarySwitchPort());
        pgSwitch.setSecondarySwitchURL(updateSwitchRequest.getSecondarySwitchURL());
        pgSwitch.setPriority(updateSwitchRequest.getPriority());
        pgSwitch.setSecondarySwitchPort(updateSwitchRequest.getSecondarySwitchPort());
        pgSwitch.setUpdatedBy(Long.parseLong(userid));
        pgSwitch.setUpdatedDate(DateUtil.getCurrentTimestamp());
        switchRepository.save(pgSwitch);
        updateSwitchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      } else {
        updateSwitchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      }
    } catch (Exception e) {
      log.error("SwitchDaoImpl | updateSwitch | Exception " + e);
    }
    log.info("SwitchDaoImpl | updateSwitch | Exiting");
    return updateSwitchResponse;
  }

  @Override
  public PGSwitch getSwtichInformationById(Long getSwitchId) {
    return switchRepository.findById(getSwitchId).orElse(null);
  }

  @Override
  public PGSwitch createOrUpdateUser(PGSwitch pGSwitch) {
    return switchRepository.save(pGSwitch);
  }

  public int getTotalNumberOfRecords(SwitchRequest switchRecords) {
    JPAQuery<Long> query = new JPAQuery<Long>(entityManager);
    List<Long> list = query.from(QPGSwitch.pGSwitch)
        .where(isSwitchNameLike(switchRecords.getSwitchName()),
            isSwitchTypeLike(switchRecords.getSwitchType()),
            isPrimarySwitchURLLike(switchRecords.getPrimarySwitchURL()),
            isPrimarySwitchPortEq(switchRecords.getPrimarySwitchPort()),
            isSecondarySwitchURLLike(switchRecords.getSecondarySwitchURL()),
            isSecondarySwitchPortEq(switchRecords.getSecondarySwitchPort()),
            isPriorityEq(switchRecords.getPriority()), isStatusEq(switchRecords.getStatus()))
        .fetch();

    return (StringUtils.isListNotNullNEmpty(list) ? list.size() : 0);
  }

  private BooleanExpression isStatusEq(Integer status) {
    return (status != null && !"".equals(status.toString())) ? QPGSwitch.pGSwitch.status.eq(status)
        : null;
  }

  private BooleanExpression isPriorityEq(Integer priority) {
    return (priority != null && !"".equals(priority.toString()))
        ? QPGSwitch.pGSwitch.priority.eq(priority) : null;
  }

  private BooleanExpression isSwitchTypeLike(String switchType) {
    return (switchType != null && !"".equals(switchType)) ? QPGSwitch.pGSwitch.switchType
        .toUpperCase().like("%" + switchType.toUpperCase().replace("*", "") + "%") : null;
  }

  private BooleanExpression isSecondarySwitchPortEq(Integer secondarySwitchPort) {
    return (secondarySwitchPort != null && !"".equals(secondarySwitchPort.toString()))
        ? QPGSwitch.pGSwitch.secondarySwitchPort.eq(secondarySwitchPort) : null;

  }

  private BooleanExpression isSecondarySwitchURLLike(String secondarySwitchURL) {
    return (secondarySwitchURL != null && !"".equals(secondarySwitchURL))
        ? QPGSwitch.pGSwitch.secondarySwitchURL.toUpperCase()
            .like("%" + secondarySwitchURL.toUpperCase().replace("*", "") + "%")
        : null;
  }

  private BooleanExpression isPrimarySwitchPortEq(Integer primarySwitchPort) {
    return (primarySwitchPort != null && !"".equals(primarySwitchPort.toString()))
        ? QPGSwitch.pGSwitch.primarySwitchPort.eq(primarySwitchPort) : null;
  }

  private BooleanExpression isPrimarySwitchURLLike(String primarySwitchURL) {
    return (primarySwitchURL != null && !"".equals(primarySwitchURL))
        ? QPGSwitch.pGSwitch.primarySwitchURL.toUpperCase()
            .like("%" + primarySwitchURL.toUpperCase().replace("*", "") + "%")
        : null;
  }

  private BooleanExpression isSwitchNameLike(String switchName) {
    return (switchName != null && !"".equals(switchName)) ? QPGSwitch.pGSwitch.switchName
        .toUpperCase().like("%" + switchName.toUpperCase().replace("*", "") + "%") : null;
  }

  private OrderSpecifier<Timestamp> orderByCreatedDateDesc() {
    return QPGSwitch.pGSwitch.createdDate.desc();
  }

}
