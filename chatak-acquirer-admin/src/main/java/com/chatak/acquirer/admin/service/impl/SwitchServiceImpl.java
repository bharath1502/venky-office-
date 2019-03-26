package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.SwitchService;
import com.chatak.pg.acq.dao.SwitchDao;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.Switch;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;
import com.chatak.pg.util.DateUtil;

@Service
public class SwitchServiceImpl implements SwitchService, PGConstants {

  private static Logger logger = Logger.getLogger(SwitchServiceImpl.class);

  @Autowired
  private SwitchDao switchDao;

  @Override
  public SwitchResponse addSwitchInformation(Switch switchInfo, String userid)
      throws ChatakAdminException {
    logger.info("Entering:: SwitchServiceImpl:: addSwitchInformation method");
    SwitchResponse addSwitchResponse = new SwitchResponse();
    try {
      PGSwitch pgSwitch = switchDao.getSwitchByName(switchInfo.getSwitchName());
      if (pgSwitch != null) {
        addSwitchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_Z11);
        addSwitchResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_Z11));
        return addSwitchResponse;
      } else {
        SwitchRequest addSwitchRequest = new SwitchRequest();
        addSwitchRequest.setSwitchName(switchInfo.getSwitchName());
        addSwitchRequest.setSwitchType(switchInfo.getSwitchType());
        addSwitchRequest.setPrimarySwitchURL(switchInfo.getPrimarySwitchURL());
        addSwitchRequest.setPrimarySwitchPort(switchInfo.getPrimarySwitchPort());
        addSwitchRequest.setSecondarySwitchURL(switchInfo.getSecondarySwitchURL());
        addSwitchRequest.setSecondarySwitchPort(switchInfo.getSecondarySwitchPort());
        addSwitchRequest.setPriority(switchInfo.getPriority());
        addSwitchRequest.setStatus(STATUS_ACTIVE);
        addSwitchRequest.setCreatedBy(userid);
        addSwitchRequest.setCreatedDate(DateUtil.getCurrentTimestamp());
        addSwitchResponse = switchDao.addSwitchInformation(addSwitchRequest, userid);
      }
    } catch (Exception e) {
      logger.error("SwitchDaoImpl | addSwitchInformation | Exception" + e);
    }
    logger.info("Exiting:: SwitchServiceImpl:: addSwitchInformation method");
    return addSwitchResponse;
  }

  @Override
  public SwitchResponse searchSwitchInformation(SwitchRequest searchSwitchRequest)
      throws ChatakAdminException {
    logger.info("Entering:: SwitchServiceImpl:: searchSwitchInfo method");
    SwitchResponse searchSwitchResponse = new SwitchResponse();
    try {
      List<SwitchRequest> searchSwitchRequestList =
          switchDao.searchSwitchInformation(searchSwitchRequest);
      if (!CollectionUtils.isEmpty(searchSwitchRequestList)) {
        List<SwitchRequest> list = new ArrayList<>(searchSwitchRequestList.size());
        SwitchRequest searchSwitch = null;
        for (SwitchRequest swRequest : searchSwitchRequestList) {
          searchSwitch = new SwitchRequest();
          searchSwitch.setId(swRequest.getId());
          searchSwitch.setSwitchName(swRequest.getSwitchName());
          searchSwitch.setSwitchType(swRequest.getSwitchType());
          searchSwitch.setPrimarySwitchURL(swRequest.getPrimarySwitchURL());
          searchSwitch.setPrimarySwitchPort(swRequest.getPrimarySwitchPort());
          searchSwitch.setSecondarySwitchURL(swRequest.getSecondarySwitchURL());
          searchSwitch.setSecondarySwitchPort(swRequest.getSecondarySwitchPort());
          searchSwitch.setPriority(swRequest.getPriority());
          searchSwitch.setStatus(swRequest.getStatus());
          searchSwitch.setCreatedDate(swRequest.getCreatedDate());
          searchSwitch = setSwitchStatus(searchSwitch, swRequest);

          list.add(searchSwitch);
        }
        searchSwitchResponse.setSwitchRequest(list);
        searchSwitchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }

    } catch (Exception e) {
      logger.info("ERROR:: SwitchServiceImpl:: searchSwitchInfo method2", e);
    }
    searchSwitchResponse.setTotalNoOfRows(searchSwitchRequest.getNoOfRecords());
    logger.info("Exiting:: SwitchServiceImpl:: searchSwitchInfo method");
    return searchSwitchResponse;
  }

  private SwitchRequest setSwitchStatus(SwitchRequest searchSwitch, SwitchRequest swRequest) {
    if (swRequest.getStatus().equals(STATUS_SUCCESS)) {
      searchSwitch.setStatusDisp(S_STATUS_ACTIVE);
    } else if (swRequest.getStatus().equals(STATUS_PENDING)) {
      searchSwitch.setStatusDisp(S_STATUS_PENDING);
    } else if (swRequest.getStatus().equals(STATUS_SUSPENDED)) {
      searchSwitch.setStatusDisp(S_STATUS_SUSPENDED);
    }
    return searchSwitch;
  }

  @Override
  public SwitchResponse updateSwitchInformation(SwitchRequest switchUpdate, String userid) {
    logger.info("Entering:: SwitchServiceImpl:: updateSwitch method");
    SwitchResponse updateSwitchResponse = new SwitchResponse();
    try {
      updateSwitchResponse = switchDao.updateSwitchInformation(switchUpdate, userid);
    } catch (Exception e) {
      logger.error("ERROR:: SwitchServiceImpl:: updateSwitch method" + e);
    }
    logger.info("Exiting:: SwitchServiceImpl:: updateSwitch method");
    return updateSwitchResponse;
  }

  @Override
  public SwitchResponse changeSwitchStatus(SwitchRequest switchRequest, String switchStatus) {
    logger.info("Entering:: SwitchServiceImpl:: changeSwitchStatus method");
    SwitchResponse switchResponse = new SwitchResponse();
    try {
      if (null != switchRequest) {
        PGSwitch pgSwitch = switchDao.getSwtichInformationById(switchRequest.getId());
        if (S_STATUS_ACTIVE.equals(switchStatus)) {
          pgSwitch.setStatus(STATUS_ACTIVE);
        } else {
          pgSwitch.setStatus(STATUS_SUSPENDED);
        }
        pgSwitch.setReason(switchRequest.getReason());
        switchDao.createOrUpdateUser(pgSwitch);
        switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (DataAccessException e) {
      switchResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      logger.error("", e);
    }
    return switchResponse;
  }

  @Override
  public SwitchRequest getSwtichInformationById(Long getSwitchId) {
    logger.info("Entering:: SwitchServiceImpl:: getSwtichInfoById method");
    SwitchRequest switchRequest = new SwitchRequest();
    try {
      PGSwitch pgSwitch = switchDao.getSwtichInformationById(getSwitchId);
      if (pgSwitch != null) {
        switchRequest.setId(pgSwitch.getId());
        switchRequest.setSwitchName(pgSwitch.getSwitchName());
        switchRequest.setSwitchType(pgSwitch.getSwitchType());
        switchRequest.setPrimarySwitchURL(pgSwitch.getPrimarySwitchURL());
        switchRequest.setPrimarySwitchPort(pgSwitch.getPrimarySwitchPort());
        switchRequest.setSecondarySwitchURL(pgSwitch.getSecondarySwitchURL());
        switchRequest.setSecondarySwitchPort(pgSwitch.getSecondarySwitchPort());
        switchRequest.setPriority(pgSwitch.getPriority());
        switchRequest.setStatus(pgSwitch.getStatus());
      }
    } catch (Exception e) {
      logger.error("ERROR:: SwitchServiceImpl:: getSwtichInfoById method" + e);
    }
    logger.info("Exiting:: SwitchServiceImpl:: getSwtichInfoById method");
    return switchRequest;
  }

  @Override
  public SwitchResponse getSwitchByStatus(Integer status) {
    logger.info("Entering:: SwitchServiceImpl:: getSwitchByStatus method");
    SwitchResponse response = new SwitchResponse();
    List<SwitchRequest> switches = new ArrayList<>();
    try {
      List<PGSwitch> pgSwitch = switchDao.getAllSwitchNamesByStatus(status);
      if (null != pgSwitch) {
        for (PGSwitch switchs : pgSwitch) {
          SwitchRequest switchRequest = new SwitchRequest();
          switchRequest.setId(switchs.getId());
          switchRequest.setSwitchName(switchs.getSwitchName());
          switchRequest.setSwitchType(switchs.getSwitchType());
          switchRequest.setPrimarySwitchURL(switchs.getPrimarySwitchURL());
          switchRequest.setPrimarySwitchPort(switchs.getPrimarySwitchPort());
          switchRequest.setSecondarySwitchURL(switchs.getSecondarySwitchURL());
          switchRequest.setSecondarySwitchPort(switchs.getSecondarySwitchPort());
          switchRequest.setPriority(switchs.getPriority());
          switchRequest.setStatus(switchs.getStatus());
          switchRequest.setCreatedDate(switchs.getCreatedDate());
          switchRequest = setSwitchDisplayStatus(switchs, switchRequest);
          switches.add(switchRequest);
        }
      }
      response.setSwitchRequest(switches);
    } catch (Exception e) {
      logger.error("ERROR:: SwitchServiceImpl:: getSwitchByStatus method" + e);
    }
    logger.info("Exiting:: SwitchServiceImpl:: getSwitchByStatus method");
    return response;
  }

  private SwitchRequest setSwitchDisplayStatus(PGSwitch switchs, SwitchRequest switchRequest) {
    if (switchs.getStatus().equals(STATUS_SUCCESS)) {
      switchRequest.setStatusDisp(S_STATUS_ACTIVE);
    } else if (switchs.getStatus().equals(STATUS_PENDING)) {
      switchRequest.setStatusDisp(S_STATUS_PENDING);
    } else if (switchs.getStatus().equals(STATUS_SUSPENDED)) {
      switchRequest.setStatusDisp(S_STATUS_SUSPENDED);
    }
    return switchRequest;
  }

}
