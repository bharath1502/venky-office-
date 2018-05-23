package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.chatak.acquirer.admin.controller.model.Option;
import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.MerchantCategoryCodeSearchResponse;
import com.chatak.acquirer.admin.service.MerchantCategoryCodeService;
import com.chatak.pg.acq.dao.MerchantCategoryCodeDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.TransactionCategoryCodeDao;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGMerchantCategoryCode;
import com.chatak.pg.bean.TransactionCategoryCodeRequest;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.MerchantCategoryCode;
import com.chatak.pg.user.bean.GetMerchantCategoryCodeListResponse;
import com.chatak.pg.user.bean.MerchantCategoryCodeRequest;
import com.chatak.pg.user.bean.MerchantCategoryCodeResponse;
import com.chatak.pg.util.Constants;

@Service
public class MerchantCategoryCodeServiceImpl implements MerchantCategoryCodeService, PGConstants {
  private static Logger logger = Logger.getLogger(MerchantCategoryCodeServiceImpl.class);
  @Autowired
  TransactionCategoryCodeDao tccDao;

  @Autowired
  MerchantCategoryCodeDao mccDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  MessageSource messageSource;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Override
  public MerchantCategoryCodeResponse createMerchantCategoryCode(MerchantCategoryCode mcc)
      throws ChatakAdminException {
    logger.info("Entering:: MerchantCategoryCodeServiceImpl:: createMerchantCategoryCode method");
    MerchantCategoryCodeRequest request = new MerchantCategoryCodeRequest();
    request.setMerchantCategoryCode(mcc.getMerchantCategoryCode());
    request.setSelectedTcc(mcc.getSelectedTcc());
    request.setDescription(mcc.getDescription());
    request.setCreatedBy(mcc.getCreatedBy());
    MerchantCategoryCodeResponse response = mccDao.createMCC(request);
    logger.info("Exiting:: MerchantCategoryCodeServiceImpl:: createMerchantCategoryCode method");
    return response;
  }

  @Override
  public MerchantCategoryCodeSearchResponse searchMerchantCategoryCode(MerchantCategoryCode mcc)
      throws ChatakAdminException {
    logger.info("Entering:: MerchantCategoryCodeServiceImpl:: searchMerchantCategoryCode method");
    MerchantCategoryCodeRequest mccSearchRequest = new MerchantCategoryCodeRequest();
    MerchantCategoryCodeSearchResponse mccSearchResponse = new MerchantCategoryCodeSearchResponse();
    mccSearchRequest.setMerchantCategoryCode(mcc.getMerchantCategoryCode());
    mccSearchRequest.setStatus(mcc.getStatus());
    mccSearchRequest.setPageSize(mcc.getPageSize());
    mccSearchRequest.setPageIndex(mcc.getPageIndex());
    mccSearchRequest.setNoOfRecords(mcc.getNoOfRecords());
    GetMerchantCategoryCodeListResponse getMCCSearchResponse = mccDao.getMCCList(mccSearchRequest);

    List<PGMerchantCategoryCode> pgMCCs = getMCCSearchResponse.getPgMCC();

    if (!CollectionUtils.isEmpty(pgMCCs)) {
      List<MerchantCategoryCode> mccs = new ArrayList<>(pgMCCs.size());
      MerchantCategoryCode mccRespObj = null;
      for (PGMerchantCategoryCode pgMCC : pgMCCs) {
        mccRespObj = new MerchantCategoryCode();
        mccRespObj.setId(pgMCC.getId());
        mccRespObj.setMerchantCategoryCode(pgMCC.getMerchantCategoryCode());
        mccRespObj.setSelectedTcc(pgMCC.getSelectedTcc());
        mccRespObj.setDescription(pgMCC.getDescription());
        mccRespObj.setStatus(pgMCC.getStatus());
        mccs.add(mccRespObj);
      }
      mccSearchResponse.setMccs(mccs);
    }
    mccSearchResponse.setTotalNoOfRows(getMCCSearchResponse.getNoOfRecords());
    logger.info("Exiting:: MerchantCategoryCodeServiceImpl:: searchMerchantCategoryCode method");
    return mccSearchResponse;
  }

  @Override
  public MerchantCategoryCode getMCCDetails(Long id) throws ChatakAdminException {
    logger.info("Entering:: MerchantCategoryCodeServiceImpl:: getMCCDetails method");
    MerchantCategoryCode merchantCategoryCode = new MerchantCategoryCode();
    PGMerchantCategoryCode pgMCC = mccDao.findById(id);
    if (null != pgMCC) {
      merchantCategoryCode.setId(pgMCC.getId());
      merchantCategoryCode.setMerchantCategoryCode(pgMCC.getMerchantCategoryCode());
      merchantCategoryCode.setSelectedTCCMultiple(Arrays.asList(pgMCC.getSelectedTcc().split(",")));
      merchantCategoryCode.setDescription(pgMCC.getDescription());
      merchantCategoryCode.setStatus(pgMCC.getStatus());

    }
    logger.info("Exiting:: MerchantCategoryCodeServiceImpl:: getMCCDetails method");
    return merchantCategoryCode;
  }

  @Override
  public MerchantCategoryCodeResponse updateMerchantCategoryCode(MerchantCategoryCode mcc)
      throws ChatakAdminException {
    logger.info("Entering:: MerchantCategoryCodeServiceImpl:: updateMerchantCategoryCode method");
    MerchantCategoryCodeRequest request = new MerchantCategoryCodeRequest();
    request.setId(mcc.getId());
    request.setMerchantCategoryCode(mcc.getMerchantCategoryCode());
    request.setSelectedTcc(mcc.getSelectedTcc());
    request.setDescription(mcc.getDescription());
    request.setStatus(S_STATUS_ACTIVE);
    request.setUpdatedBy(mcc.getUpdatedBy());
    MerchantCategoryCodeResponse response = mccDao.updateMCC(request);
    logger.info("Exiting:: MerchantCategoryCodeServiceImpl:: updateMerchantCategoryCode method");
    return response;
  }

  @Override
  public List<Option> getAllTCCs() {
    logger.info("Entering:: MerchantCategoryCodeServiceImpl:: getAllTCCs method");
    List<TransactionCategoryCodeRequest> tcc = tccDao.findAllTCC();
    List<Option> options = new ArrayList<>();
    if (tcc != null) {
      for (TransactionCategoryCodeRequest tccRequest : tcc) {
        Option option = new Option();
        option.setValue(tccRequest.getTransactionCategoryCode());
        option.setLabel(tccRequest.getDescription());
        options.add(option);
      }
    }
    Collections.sort(options, ALPHABETICAL_ORDER);
    logger.info("Exiting:: MerchantCategoryCodeServiceImpl:: getAllTCCs method");
    return options;
  }

  @Override
  public MerchantCategoryCodeResponse changeMerchantCategoryCodeStatus(
      MerchantCategoryCode merchantCategoryCode) {
    logger.info(
        "Entering:: MerchantCategoryCodeServiceImpl:: changeMerchantCategoryCodeStatus method");
    MerchantCategoryCodeResponse merchantCategoryCodeResponse = new MerchantCategoryCodeResponse();
    try {
      if (null != merchantCategoryCode) {
        PGMerchantCategoryCode pgMerchantCategoryCode =
            mccDao.findById(merchantCategoryCode.getId());
        pgMerchantCategoryCode.setStatus(merchantCategoryCode.getStatus());
        pgMerchantCategoryCode.setReason(merchantCategoryCode.getReason());
        mccDao.createOrUpdateMerchantCategoryCode(pgMerchantCategoryCode);
        merchantCategoryCodeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (DataAccessException e) {
      merchantCategoryCodeResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      logger.error("", e);
    }
    return merchantCategoryCodeResponse;
  }

  /**
   * Comparator method for option class
   */
  private static Comparator<Option> ALPHABETICAL_ORDER = ((Option str1, Option str2) -> {
    int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getValue(), str2.getValue());
    if (res == 0) {
      res = str1.getValue().compareTo(str2.getValue());
    }
    return res;
  });

  @Override
  public List<String> getAllMCC() {
    return mccDao.getAllMCC();
  }

  @Override
  public MerchantCategoryCodeResponse deleteMcc(Long id) throws ChatakAdminException {
    MerchantCategoryCodeResponse mccs = new MerchantCategoryCodeResponse();
    try {
      PGMerchantCategoryCode merchantCategoryCode = mccDao.findById(id);
      List<PGMerchant> merchant =
          merchantUpdateDao.findByMcc(merchantCategoryCode.getMerchantCategoryCode());
      if (StringUtil.isListNotNullNEmpty(merchant)) {
        mccs.setErrorCode(Constants.ERROR);
        mccs.setErrorMessage(messageSource.getMessage("chatak.mcc.delete.message", null,
            LocaleContextHolder.getLocale()));
        return mccs;
      }
      if (null != merchantCategoryCode) {
        merchantCategoryCode.setStatus(PGConstants.S_STATUS_DELETED);
        mccDao.createOrUpdateMerchantCategoryCode(merchantCategoryCode);
        mccs.setErrorCode(PGConstants.SUCCESS);
        mccs.setErrorMessage(messageSource.getMessage("chatak.mcc.delete.success", null,
            LocaleContextHolder.getLocale()));
      }
    } catch (Exception e) {
      throw new ChatakAdminException(e);
    }
    return mccs;
  }

}
