package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeeCodeResponseDetails;
import com.chatak.acquirer.admin.service.FeeCodeService;
import com.chatak.pg.acq.dao.AccountDao;
import com.chatak.pg.acq.dao.AcquirerFeeCodeDao;
import com.chatak.pg.acq.dao.FeeCodeDao;
import com.chatak.pg.acq.dao.MerchantDao;
import com.chatak.pg.acq.dao.MerchantUpdateDao;
import com.chatak.pg.acq.dao.PartnerFeeCodeDao;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeCode;
import com.chatak.pg.acq.dao.model.PGFeeCode;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGPartnerFeeCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.AcquirerFeeCodeDTO;
import com.chatak.pg.model.FeeCodeDTO;
import com.chatak.pg.model.PartnerFeeCodeDTO;
import com.chatak.pg.model.PartnerFeeCodeResponseDetails;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

@Service("feeCodeService")
public class FeeCodeServiceImpl implements FeeCodeService {
  private static Logger logger = Logger.getLogger(FeeCodeServiceImpl.class);

  @Autowired
  FeeCodeDao feeCodeDao;

  @Autowired
  PartnerFeeCodeDao partnerFeeCodeDao;

  @Autowired
  MerchantDao merchantDao;

  @Autowired
  AccountDao accountDao;

  @Autowired
  AcquirerFeeCodeDao acquirerFeeCodeDao;

  @Autowired
  MerchantUpdateDao merchantUpdateDao;

  @Override
  public FeeCodeResponseDetails getAllFeeCodeList() throws ChatakAdminException {
    FeeCodeResponseDetails response = new FeeCodeResponseDetails();
    try {
      List<PGFeeCode> feeCode = feeCodeDao.getAllFeeCodeList();
      if (CommonUtil.isListNotNullAndEmpty(feeCode)) {
        List<FeeCodeDTO> accountProgramRequest =
            CommonUtil.copyListBeanProperty(feeCode, FeeCodeDTO.class);
        response.setFeeCodeList(accountProgramRequest);
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
      }
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramServiceImpl:: getAllFeeCodeForJpa method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return response;

  }

  @Override
  public PartnerFeeCodeResponseDetails getAllPartnerFeeCodeList() throws ChatakAdminException {
    PartnerFeeCodeResponseDetails partnerFeeCodeResponseDetails =
        new PartnerFeeCodeResponseDetails();
    List<PGPartnerFeeCode> pgPartnerFeeCodeList = partnerFeeCodeDao.getAllFeeCodes();
    List<PartnerFeeCodeDTO> partnerFeeCodeDTOList = new ArrayList<>();
    if (CommonUtil.isListNotNullAndEmpty(pgPartnerFeeCodeList)) {
      for (PGPartnerFeeCode pgPartnerFeeCode : pgPartnerFeeCodeList) {
        PartnerFeeCodeDTO partnerFeeCodeDTO = new PartnerFeeCodeDTO();
        partnerFeeCodeDTO.setAccountNumber(pgPartnerFeeCode.getAccountNumber());
        partnerFeeCodeDTO.setMerchantCode(pgPartnerFeeCode.getPartnerEntityId());
        partnerFeeCodeDTO.setFlatFee(pgPartnerFeeCode.getFlatFee());
        partnerFeeCodeDTO.setPercentageOfTxn(pgPartnerFeeCode.getFeePercentageOnly());
        partnerFeeCodeDTO.setPartnerName(pgPartnerFeeCode.getPartnerName());
        partnerFeeCodeDTO.setPartnerId(pgPartnerFeeCode.getPartnerFeeCodeId());
        partnerFeeCodeDTO.setAcquirerFeeCodeList(populateAcquirerFee(acquirerFeeCodeDao
            .getAcquirerFeeCodeByPartnerId(pgPartnerFeeCode.getPartnerFeeCodeId())));
        partnerFeeCodeDTOList.add(partnerFeeCodeDTO);
      }
      partnerFeeCodeResponseDetails.setPartnerFeeCodeList(partnerFeeCodeDTOList);
    }
    return partnerFeeCodeResponseDetails;
  }

  @Override
  public PartnerFeeCodeDTO getPartnerFeeCodeByEntityId(String partnerEntityId)
      throws ChatakAdminException {
    PGPartnerFeeCode pgPartnerFeeCode =
        partnerFeeCodeDao.getPartnerFeeCodeByEntityId(partnerEntityId);
    PartnerFeeCodeDTO partnerFeeCodeDTO = new PartnerFeeCodeDTO();
    if (null != pgPartnerFeeCode) {
      partnerFeeCodeDTO.setAccountNumber(pgPartnerFeeCode.getAccountNumber());
      partnerFeeCodeDTO.setFlatFee(pgPartnerFeeCode.getFlatFee());
      partnerFeeCodeDTO.setPercentageOfTxn(pgPartnerFeeCode.getFeePercentageOnly());
      partnerFeeCodeDTO.setMerchantCode(pgPartnerFeeCode.getPartnerEntityId());
      partnerFeeCodeDTO.setPartnerName(pgPartnerFeeCode.getPartnerName());
    }
    return partnerFeeCodeDTO;
  }

  @Override
  public void addPartnerFeeCode(PartnerFeeCodeDTO partnerFeeCodeDTO) throws ChatakAdminException {
    PGPartnerFeeCode pgPartnerFeeCode = new PGPartnerFeeCode();
    pgPartnerFeeCode.setPartnerEntityId(partnerFeeCodeDTO.getMerchantCode());
    pgPartnerFeeCode.setAccountNumber(partnerFeeCodeDTO.getAccountNumber());
    pgPartnerFeeCode.setFlatFee(partnerFeeCodeDTO.getFlatFee());
    pgPartnerFeeCode.setFeePercentageOnly(partnerFeeCodeDTO.getPercentageOfTxn());
    pgPartnerFeeCode.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    pgPartnerFeeCode.setPartnerName(partnerFeeCodeDTO.getPartnerName());
    pgPartnerFeeCode.setAccountNumber(partnerFeeCodeDTO.getAccountNumber());
    pgPartnerFeeCode.setCreatedBy(partnerFeeCodeDTO.getCreatedBy());
    partnerFeeCodeDao.createPartnerFeecode(pgPartnerFeeCode);
  }

  @Override
  public PGPartnerFeeCode validatePartnerFeeCode(String merchantCode) throws ChatakAdminException {
    PGMerchant pgMerchant = merchantUpdateDao.getMerchant(merchantCode);
    PGPartnerFeeCode pgPartnerFeeCode = new PGPartnerFeeCode();

    PGPartnerFeeCode duplicateFeeCode = partnerFeeCodeDao.getPartnerFeeCodeByEntityId(merchantCode);
    if (null != duplicateFeeCode) {
      return null;
    }
    if (null != pgMerchant) {
      PGAccount pgAccount = accountDao.getPgAccount(pgMerchant.getMerchantCode());
      if (null != pgAccount) {
        pgPartnerFeeCode.setPartnerEntityId(merchantCode);
        pgPartnerFeeCode.setAccountNumber(pgAccount.getAccountNum());
        pgPartnerFeeCode.setPartnerName(pgMerchant.getBusinessName());
        return pgPartnerFeeCode;
      } else {
        return null;
      }

    }

    return null;
  }

  @Override
  public List<PGAcquirerFeeCode> getAllAcquirerFeeCodes() throws ChatakAdminException {
    return acquirerFeeCodeDao.getAllFeeCodes();
  }

  @Override
  public void updatePartnerFeeCode(PartnerFeeCodeDTO partnerFeeCodeDTO)
      throws ChatakAdminException {
    PGPartnerFeeCode pgPartnerFeeCode =
        partnerFeeCodeDao.getPartnerFeeCodeByEntityId(partnerFeeCodeDTO.getMerchantCode());
    if (null != pgPartnerFeeCode) {
      pgPartnerFeeCode.setAccountNumber(partnerFeeCodeDTO.getAccountNumber());
      pgPartnerFeeCode.setFlatFee(partnerFeeCodeDTO.getFlatFee() * Constants.MAX_PAGE_SIZE);
      pgPartnerFeeCode.setFeePercentageOnly(partnerFeeCodeDTO.getPercentageOfTxn());
      pgPartnerFeeCode.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      pgPartnerFeeCode.setPartnerName(partnerFeeCodeDTO.getPartnerName());
      pgPartnerFeeCode.setUpdatedBy(partnerFeeCodeDTO.getUpdatedBy());
      partnerFeeCodeDao.updatePartnerFeeCode(pgPartnerFeeCode);
    } else {
      throw new ChatakAdminException("");
    }
  }

  @Override
  public PartnerFeeCodeDTO getPartnerFeeCodeByAccountNumber(Long accountNumber)
      throws ChatakAdminException {
    PGPartnerFeeCode pgPartnerFeeCode =
        partnerFeeCodeDao.getPartnerFeeCodeByAccountNumber(accountNumber);
    PartnerFeeCodeDTO partnerFeeCodeDTO = null;
    if (null != pgPartnerFeeCode) {
      partnerFeeCodeDTO = new PartnerFeeCodeDTO();
      partnerFeeCodeDTO.setAccountNumber(pgPartnerFeeCode.getAccountNumber());
      partnerFeeCodeDTO.setFlatFee(pgPartnerFeeCode.getFlatFee());
      partnerFeeCodeDTO.setPercentageOfTxn(pgPartnerFeeCode.getFeePercentageOnly());
      partnerFeeCodeDTO.setPartnerName(pgPartnerFeeCode.getPartnerName());
      partnerFeeCodeDTO.setMerchantCode(pgPartnerFeeCode.getPartnerEntityId());
    }
    return partnerFeeCodeDTO;
  }

  @Override
  public AcquirerFeeCodeDTO getAcquirerFeeCodeByID(Long acquirerFeeCodeId)
      throws ChatakAdminException {
    PGAcquirerFeeCode pgAcquirerFeeCode =
        acquirerFeeCodeDao.getAcquirerFeeCodeByFeeCodeId(acquirerFeeCodeId);
    AcquirerFeeCodeDTO acquirerFeeCodeDTO = null;
    if (null != pgAcquirerFeeCode) {
      acquirerFeeCodeDTO = new AcquirerFeeCodeDTO();
      acquirerFeeCodeDTO.setAcquirerFeeCodeId(pgAcquirerFeeCode.getAcquirerFeeCodeId());
      acquirerFeeCodeDTO.setFlatFee(pgAcquirerFeeCode.getFlatFee() / Constants.MAX_PAGE_SIZE);
      acquirerFeeCodeDTO.setPercentageOfTxn(pgAcquirerFeeCode.getFeePercentageOnly());
      acquirerFeeCodeDTO.setAcquirerName(pgAcquirerFeeCode.getAcquirerName());
      acquirerFeeCodeDTO.setPartnerId(pgAcquirerFeeCode.getAcquirerFeeCodeId());
      acquirerFeeCodeDTO.setMerchantCode(pgAcquirerFeeCode.getMerchantCode());
    }
    return acquirerFeeCodeDTO;

  }

  @Override
  public void updateAcquirerFeeCode(AcquirerFeeCodeDTO pgAcquirerFeeCodeDTO)
      throws ChatakAdminException {
    PGAcquirerFeeCode pgAcquirerFeeCode = new PGAcquirerFeeCode();
    pgAcquirerFeeCode.setAcquirerFeeCodeId(pgAcquirerFeeCodeDTO.getAcquirerFeeCodeId());
    pgAcquirerFeeCode.setAcquirerName(pgAcquirerFeeCodeDTO.getAcquirerName());
    pgAcquirerFeeCode.setFeePercentageOnly(pgAcquirerFeeCodeDTO.getPercentageOfTxn());
    pgAcquirerFeeCode.setFlatFee(pgAcquirerFeeCodeDTO.getFlatFee() * Constants.MAX_PAGE_SIZE);
    pgAcquirerFeeCode.setMerchantCode(pgAcquirerFeeCodeDTO.getMerchantCode());
    pgAcquirerFeeCode.setPartnerId(pgAcquirerFeeCodeDTO.getPartnerId());
    pgAcquirerFeeCode.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
    pgAcquirerFeeCode.setUpdatedBy(pgAcquirerFeeCodeDTO.getUpdatedBy());
    acquirerFeeCodeDao.updateAcquirerFeecode(pgAcquirerFeeCode);
  }

  @Override
  public List<PGAcquirerFeeCode> getAllAcquirerFeeCodesByPartnerId(Long partnerId)
      throws ChatakAdminException {
    return acquirerFeeCodeDao.getAcquirerFeeCodeByPartnerId(partnerId);
  }

  @Override
  public List<AcquirerFeeCodeDTO> findByPartnerIdAndMerchantCode(Long partnerId,
      String merchantCode) throws ChatakAdminException {
    List<PGAcquirerFeeCode> pgAcquirerFeeCodeList =
        acquirerFeeCodeDao.getFeeCodesByPartnerIdAndMerchantCode(partnerId, merchantCode);
    List<AcquirerFeeCodeDTO> acquirerFeeCodeDTOs = new ArrayList<>();
    for (PGAcquirerFeeCode pgAcquirerFeeCode : pgAcquirerFeeCodeList) {
      AcquirerFeeCodeDTO acquirerFeeCodeDTO = new AcquirerFeeCodeDTO();
      acquirerFeeCodeDTO.setAcquirerFeeCodeId(pgAcquirerFeeCode.getAcquirerFeeCodeId());
      acquirerFeeCodeDTO.setFlatFee(pgAcquirerFeeCode.getFlatFee() / Constants.MAX_PAGE_SIZE);
      acquirerFeeCodeDTO.setPercentageOfTxn(pgAcquirerFeeCode.getFeePercentageOnly());
      acquirerFeeCodeDTO.setAcquirerName(pgAcquirerFeeCode.getAcquirerName());
      acquirerFeeCodeDTO.setPartnerId(pgAcquirerFeeCode.getAcquirerFeeCodeId());
      acquirerFeeCodeDTO.setMerchantCode(pgAcquirerFeeCode.getMerchantCode());
      acquirerFeeCodeDTOs.add(acquirerFeeCodeDTO);
    }
    return acquirerFeeCodeDTOs;
  }

  public List<AcquirerFeeCodeDTO> populateAcquirerFee(List<PGAcquirerFeeCode> acquirerFeeCodes) {
    List<AcquirerFeeCodeDTO> list = new ArrayList<>();
    if (CommonUtil.isListNotNullAndEmpty(acquirerFeeCodes)) {
      AcquirerFeeCodeDTO acquirerFeeCodeDTO = null;
      for (PGAcquirerFeeCode pgAcquirerFeeCode : acquirerFeeCodes) {
        acquirerFeeCodeDTO = new AcquirerFeeCodeDTO();
        acquirerFeeCodeDTO.setFlatFee(pgAcquirerFeeCode.getFlatFee() / Constants.MAX_PAGE_SIZE);
        acquirerFeeCodeDTO.setPercentageOfTxn(pgAcquirerFeeCode.getFeePercentageOnly());
        acquirerFeeCodeDTO.setAcquirerName(pgAcquirerFeeCode.getAcquirerName());
        acquirerFeeCodeDTO.setPartnerId(pgAcquirerFeeCode.getAcquirerFeeCodeId());
        list.add(acquirerFeeCodeDTO);
      }
      return list;
    } else {
      return list;
    }
  }

  @Override
  public void addAcquirerFeeCode(AcquirerFeeCodeDTO pgAcquirerFeeCode) throws ChatakAdminException {
    PGAcquirerFeeCode pgAcquirerFeeCode2 = new PGAcquirerFeeCode();
    pgAcquirerFeeCode2.setAcquirerName(pgAcquirerFeeCode.getAcquirerName());
    pgAcquirerFeeCode2.setFeePercentageOnly(pgAcquirerFeeCode.getPercentageOfTxn());
    pgAcquirerFeeCode2.setFlatFee(pgAcquirerFeeCode.getFlatFee() * Constants.MAX_PAGE_SIZE);
    pgAcquirerFeeCode2.setMerchantCode(pgAcquirerFeeCode.getMerchantCode());
    pgAcquirerFeeCode2.setPartnerId(pgAcquirerFeeCode.getPartnerId());
    pgAcquirerFeeCode2.setUpdatedDate(pgAcquirerFeeCode.getUpdatedDate());
    pgAcquirerFeeCode2.setUpdatedBy(pgAcquirerFeeCode.getUpdatedBy());
    acquirerFeeCodeDao.addAcquirerFeecode(pgAcquirerFeeCode2);
  }

  @Override
  public PGMerchant getAcquirerFeeByMerchantCode(String merchantCode) throws ChatakAdminException {
    return merchantUpdateDao.getMerchant(merchantCode);
  }

}
