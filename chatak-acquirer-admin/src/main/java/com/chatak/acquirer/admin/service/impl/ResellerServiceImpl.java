package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.ResellerSearchResponse;
import com.chatak.acquirer.admin.model.ResellerValue;
import com.chatak.acquirer.admin.service.ResellerService;
import com.chatak.pg.acq.dao.ResellerDao;
import com.chatak.pg.acq.dao.model.PGReseller;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.ResellerData;
import com.chatak.pg.user.bean.AddResellerRequest;
import com.chatak.pg.user.bean.AddResellerResponse;
import com.chatak.pg.user.bean.DeleteResellerResponse;
import com.chatak.pg.user.bean.GetResellerListRequest;
import com.chatak.pg.user.bean.GetResellerListResponse;
import com.chatak.pg.user.bean.UpdateResellerRequest;
import com.chatak.pg.user.bean.UpdateResellerResponse;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.StringUtils;

@Service("resellerService")
public class ResellerServiceImpl implements ResellerService, PGConstants {

  private static Logger logger = Logger.getLogger(ResellerServiceImpl.class);

  @Autowired
  ResellerDao resellerDao;

  @Override
  public AddResellerResponse addReseller(ResellerData resellerData) throws ChatakAdminException {
    AddResellerRequest addResellerRequest = new AddResellerRequest();

    logger.info("Entering ResellerServiceImpl : : addReseller method ");
    addResellerRequest.setResellerName(resellerData.getResellerName());
    addResellerRequest.setEmailId(resellerData.getEmailId());
    addResellerRequest.setPhone(resellerData.getPhone());
    addResellerRequest.setContactName(resellerData.getContactName());
    addResellerRequest.setAddress1(resellerData.getAddress1());
    addResellerRequest.setAddress2(resellerData.getAddress2());
    addResellerRequest.setStatus(resellerData.getStatus());
    addResellerRequest.setCity(resellerData.getCity());
    addResellerRequest.setCountry(resellerData.getCountry());
    addResellerRequest.setState(resellerData.getState());
    addResellerRequest.setZip(resellerData.getZip());
    addResellerRequest.setCreatedBy(resellerData.getCreatedBy());
    addResellerRequest.setAccountNumber(resellerData.getAccountNumber());

    AddResellerResponse addResellerResponse = resellerDao.addReseller(addResellerRequest);

    logger.info("Exiting ResellerServiceImpl : : addReseller method ");
    return addResellerResponse;
  }


  @Override
  public String getResllerAccountNumber() throws ChatakAdminException {
    return resellerDao.getResllerAccountNumber();
  }

  @Override
  public ResellerSearchResponse searchReseller(ResellerData resellerData)
      throws ChatakAdminException {

    GetResellerListRequest serachReseller = new GetResellerListRequest();
    GetResellerListResponse getResellerListResponse = null;

    serachReseller.setResellerName(resellerData.getResellerName());
    serachReseller.setContactName(resellerData.getContactName());
    serachReseller.setEmailId(resellerData.getEmailId());
    serachReseller.setPhone(resellerData.getPhone());
    serachReseller.setAccountNumber(resellerData.getAccountNumber());
    serachReseller.setState(resellerData.getState());
    serachReseller.setCity(resellerData.getCity());
    serachReseller.setCountry(resellerData.getCountry());
    serachReseller.setStatus(resellerData.getStatus());
    serachReseller.setPageIndex(resellerData.getPageIndex());
    serachReseller.setPageSize(resellerData.getPageSize());
    serachReseller.setNoOfRecords(resellerData.getNoOfRecords());
    serachReseller.setAccountBalance(resellerData.getAccountBalance());

    getResellerListResponse = resellerDao.getResellerlist(serachReseller);

    List<PGReseller> pgResellers = getResellerListResponse.getResellers();
    ResellerSearchResponse resellerSearchResponse = new ResellerSearchResponse();

    if (!CollectionUtils.isEmpty(pgResellers)) {
      List<ResellerValue> resellerValues = new ArrayList<>(pgResellers.size());
      ResellerValue resellerRespObj = null;
      for (PGReseller pgReseller : pgResellers) {
        resellerRespObj = new ResellerValue();
        resellerRespObj.setResellerId(pgReseller.getResellerId());
        resellerRespObj.setResellerName(pgReseller.getResellerName());
        resellerRespObj.setContactName(pgReseller.getContactName());
        resellerRespObj.setEmailId(pgReseller.getEmailId());
        resellerRespObj.setPhone(pgReseller.getPhone());
        resellerRespObj.setAccountNumber(pgReseller.getAccountNumber());
        resellerRespObj.setCity(pgReseller.getCity());
        resellerRespObj.setCountry(pgReseller.getCountry());
        resellerRespObj
            .setAccountBalance(StringUtils.amountToString(pgReseller.getAccountBalance()));
        if (pgReseller.getStatus() == STATUS_SUCCESS) {
          resellerRespObj.setStatus(S_STATUS_ACTIVE);
        } else if (pgReseller.getStatus() == STATUS_PENDING) {
          resellerRespObj.setStatus(S_STATUS_PENDING);
        } else if (pgReseller.getStatus() == STATUS_DELETED) {
          resellerRespObj.setStatus(S_STATUS_DELETED);
        } else if (pgReseller.getStatus() == STATUS_SUSPENDED) {
          resellerRespObj.setStatus(S_STATUS_SUSPENDED);
        } else if (pgReseller.getStatus() == STATUS_SELF_REGISTERATION_PENDING) {
          resellerRespObj.setStatus(S_STATUS_SELFREGISTERED);
        }
        resellerValues.add(resellerRespObj);
      }
      resellerSearchResponse.setTotalNoOfRows(getResellerListResponse.getNoOfRecords());
      resellerSearchResponse.setReseller(resellerValues);
    }

    logger.info("Exiting:: ResellerServiceImpl:: searchReseller method");
    return resellerSearchResponse;
  }

  @Override
  public ResellerData getReseller(ResellerData resellerData) throws ChatakAdminException {
    logger.info("Entering:: ResellerServiceImpl:: getReseller method");
    PGReseller pgReseller = resellerDao.getResellerByresellerId(resellerData.getResellerId());
    if (pgReseller != null) {
      resellerData.setResellerId(pgReseller.getResellerId());
      resellerData.setAddress1(pgReseller.getAddress1());
      resellerData.setAddress2(pgReseller.getAddress2());
      resellerData.setCity(pgReseller.getCity());
      resellerData.setCountry(pgReseller.getCountry());
      resellerData.setResellerId(pgReseller.getResellerId());
      resellerData.setState(pgReseller.getState());
      resellerData.setResellerName(pgReseller.getResellerName());
      resellerData.setEmailId(pgReseller.getEmailId());
      resellerData.setContactName(pgReseller.getContactName());
      resellerData.setStatus(pgReseller.getStatus());
      resellerData.setZip(pgReseller.getZip());
      resellerData.setPhone(pgReseller.getPhone());
      resellerData.setAccountNumber(pgReseller.getAccountNumber());
      resellerData.setAccountBalance(StringUtils.amountToString(pgReseller.getAccountBalance()));
      resellerData.setUpdatedBy(pgReseller.getUpdatedBy());
    }
    logger.info("Exiting:: ResellerServiceImpl:: getReseller method");
    return resellerData;
  }

  @Override
  public UpdateResellerResponse updateReseller(ResellerData resellerData)
      throws ChatakAdminException {
    logger.info("Entering:: ResellerServiceImpl:: updateReseller method");

    UpdateResellerRequest updateResellerRequest = new UpdateResellerRequest();
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    updateResellerRequest.setAddress1(resellerData.getAddress1());
    updateResellerRequest.setAddress2(resellerData.getAddress2());
    updateResellerRequest.setCity(resellerData.getCity());
    updateResellerRequest.setCountry(resellerData.getCountry());
    updateResellerRequest.setResellerId(resellerData.getResellerId());
    updateResellerRequest.setState(resellerData.getState());
    updateResellerRequest.setResellerName(resellerData.getResellerName());
    updateResellerRequest.setEmailId(resellerData.getEmailId());
    updateResellerRequest.setContactName(resellerData.getContactName());
    updateResellerRequest.setStatus(resellerData.getStatus());
    updateResellerRequest.setZip(resellerData.getZip());
    updateResellerRequest.setPhone(resellerData.getPhone());
    updateResellerRequest.setAccountNumber(resellerData.getAccountNumber());
    updateResellerRequest.setAccountBalance(resellerData.getAccountBalance());
    updateResellerRequest.setUpdatedBy(resellerData.getUpdatedBy());
    updateResellerRequest.setUpdatedDate(currentDate);
    updateResellerRequest.setCreatedDate(currentDate);

    return resellerDao.updateReseller(updateResellerRequest);
  }

  @Override
  public DeleteResellerResponse deleteReseller(Long resellerId) {
    return resellerDao.deleteReseller(resellerId);
  }

  @Override
  public List<Option> getResellerData() throws ChatakAdminException {
    List<PGReseller> resellerList = resellerDao.getResellerData();
    List<PGReseller> pgReseller = resellerList;
    List<Option> resellerNames =
        new ArrayList<>(CommonUtil.isListNotNullAndEmpty(pgReseller) ? pgReseller.size() : 0);

    if (resellerList != null) {
      Option resellerNamesList = null;
      for (PGReseller pgResellerList : pgReseller) {
        resellerNamesList = new Option();
        resellerNamesList.setValue(pgResellerList.getResellerId().toString());
        resellerNamesList.setLabel(pgResellerList.getResellerName());
        resellerNames.add(resellerNamesList);
      }
    }
    return resellerNames;
  }
}
