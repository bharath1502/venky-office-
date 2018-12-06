package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeeProgramResponseDetails;
import com.chatak.acquirer.admin.service.FeeProgramService;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.AcquirerFeeValueDao;
import com.chatak.pg.acq.dao.CardProgramDao;
import com.chatak.pg.acq.dao.FeeCodeDao;
import com.chatak.pg.acq.dao.FeeProgramDao;
import com.chatak.pg.acq.dao.model.CardProgram;
import com.chatak.pg.acq.dao.model.PGAccount;
import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGMerchant;
import com.chatak.pg.acq.dao.model.PGOtherFeeValue;
import com.chatak.pg.acq.dao.repository.AccountRepository;
import com.chatak.pg.acq.dao.repository.MerchantRepository;
import com.chatak.pg.bean.FeeprogramNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.FeeProgramDTO;
import com.chatak.pg.model.FeeValue;
import com.chatak.pg.model.OtherFeesDTO;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@Service("feeProgramService")
public class FeeProgramServiceImpl implements FeeProgramService {
  private static Logger logger = Logger.getLogger(FeeProgramServiceImpl.class);

  @Autowired
  FeeProgramDao feeProgramDao;

  @Autowired
  FeeCodeDao feeCodeDao;

  @Autowired
  AcquirerFeeValueDao acquirerFeeValueDao;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  MerchantRepository merchantRepository;
  
  @Autowired
  MessageSource messageSource;
  
  @Autowired
  CardProgramDao cardProgramDao;

  @Override
  public Response createFeeProgram(FeeProgramDTO feeProgramDTO) throws ChatakAdminException {

    logger.info("prepaidservice :: FeeProgramServiceImpl :: createFeeProgram Entering");
    Response response = new Response();
    List<PGAcquirerFeeValue> feeProgramValueDaoDetails = new ArrayList<>();
    PGAcquirerFeeValue feeProgramValue = null;
    PGOtherFeeValue otherFeeValue = new PGOtherFeeValue();

    try {
      List<PGFeeProgram> feeProgram = feeProgramDao.findByCardProgramId(feeProgramDTO.getCardProgramId());
      if(StringUtil.isListNotNullNEmpty(feeProgram)){
        response.setErrorCode(Constants.CARD_PROGRAM_ALREADY_MAPPED);
        response.setErrorMessage(messageSource.getMessage(response.getErrorCode(), null, LocaleContextHolder.getLocale()));
        return response;
      }
      
      PGFeeProgram feeProgramDaoDetails = CommonUtil.copyBeanProperties(feeProgramDTO, PGFeeProgram.class);
      feeProgramDaoDetails.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      feeProgramDaoDetails.setStatus(Constants.ACTIVE);
      for (FeeValue feeValue : feeProgramDTO.getFeeValueList()) {
        feeProgramValue = new PGAcquirerFeeValue();
        feeProgramValue.setFeePercentageOnly(Double.valueOf(feeValue.getFeePercentage()));
        feeProgramValue
            .setFlatFee(CommonUtil.getLongAmountFromDoubleString((feeValue.getFlatFee())));
        feeProgramValue.setStatus(Constants.ACTIVE);
        feeProgramValue.setCreatedBy(feeProgramDTO.getCreatedBy());
        feeProgramValue.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        feeProgramValue.setCardType(feeValue.getCardType());
        feeProgramValue.setAccountType(feeValue.getAccountType());
        if (Constants.ACCOUNT_TYPE_OTHER.equals(feeValue.getAccountType())) {
          feeProgramValue.setAccountNumber(feeValue.getAccountNumber());
        }
        feeProgramValueDaoDetails.add(feeProgramValue);
      }
      feeProgramDaoDetails.setAcquirerFeeValueList(feeProgramValueDaoDetails);
      otherFeeValue.setCreatedBy(feeProgramDTO.getCreatedBy());
      otherFeeValue.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      feeProgramDaoDetails.setPgOtherFeeValue(otherFeeValue);
      feeProgramDao.createFeeProgram(feeProgramDaoDetails);
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setErrorMessage(Constants.SUCESS);
      return response;
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramServiceImpl:: createFeeProgram method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return response;
  }

  @Override
  public FeeProgramResponseDetails searchFeeProgramForJpa(FeeProgramDTO feeProgramDTO)
      throws ChatakAdminException {

    logger.info("prepaidservice :: FeeProgramManagementImpl :: searchFeeProgramForJpa Entering");
    FeeProgramResponseDetails feeProgramResponse = new FeeProgramResponseDetails();
    try {
      List<FeeProgramDTO> feeProgramRequestResponseList = null;
      if (feeProgramDTO.getUserType() != null
          && feeProgramDTO.getUserType().equals(Constants.PM_USER_TYPE)) {
        feeProgramRequestResponseList = feeProgramDao.fetchFeeProgramForPM(feeProgramDTO);
      } else {
        feeProgramRequestResponseList = feeProgramDao.searchFeeProgramForJpa(feeProgramDTO);
      }
      if (CommonUtil.isListNotNullAndEmpty(feeProgramRequestResponseList)) {
        feeProgramResponse.setFeeCodeList(feeProgramRequestResponseList);
        feeProgramResponse.setTotalNoOfRows(feeProgramDTO.getNoOfRecords());
      }
      feeProgramResponse.setErrorCode(Constants.STATUS_CODE_SUCCESS);
      feeProgramResponse.setErrorMessage(Constants.SUCESS);
      logger.info("prepaidservice :: FeeProgramManagementImpl :: searchFeeProgramForJpa Exiting");
      return feeProgramResponse;
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramServiceImpl:: getAllFeeCodeForJpa method", e);
      feeProgramResponse.setErrorCode(Constants.ERROR);
      feeProgramResponse.setErrorMessage(Constants.ERROR_DATA);
    }
    return feeProgramResponse;
  }

  @Override
  public FeeProgramResponseDetails getFeeProgramDetails(FeeProgramDTO feeProgramDTO)
      throws ChatakAdminException {
    logger.info("prepaidservice :: FeeProgramManagementImpl :: getFeeProgramDetails Entering");
    FeeProgramResponseDetails feeProgramResponse = new FeeProgramResponseDetails();
    FeeValue feeValue = null;
    List<FeeValue> feeValues = new ArrayList<>();
    FeeProgramDTO feeProgram = new FeeProgramDTO();
    List<FeeProgramDTO> feePrograms = new ArrayList<>();

    try {
      List<PGFeeProgram> feeProgramModel =
          feeProgramDao.getByFeeProgramId(feeProgramDTO.getFeeProgramId());

      if (StringUtil.isListNotNullNEmpty(feeProgramModel)) {
        CardProgram cardProgram = cardProgramDao.findByCardProgramId(feeProgramModel.get(0).getCardProgramId());
        feeProgram.setCardProgramId(cardProgram.getCardProgramId());
        feeProgram.setCardProgramName(cardProgram.getCardProgramName());
        feeProgram.setFeeProgramId(feeProgramModel.get(0).getFeeProgramId());
        feeProgram.setFeeProgramName(feeProgramModel.get(0).getFeeProgramName());
        feeProgram.setFeeProgramDescription(feeProgramModel.get(0).getFeeProgramDescription());
        feeProgram.setStatus(feeProgramModel.get(0).getStatus());
        feeProgram.setProcessor(feeProgramModel.get(0).getProcessor());
        feeProgram.setPmShare(feeProgramModel.get(0).getPmShare());
        feeProgram.setIsoShare(feeProgramModel.get(0).getIsoShare());
        // Setting other fee
        OtherFeesDTO otherFeesDTO = new OtherFeesDTO();
        feeProgram.setOtherFee(otherFeesDTO);
        // Setting Acquirer fee values
        List<PGAcquirerFeeValue> acquirerFeeValueModels =
            acquirerFeeValueDao.getAcquirerFeeValuesByFeeProgramId(feeProgramDTO.getFeeProgramId());
        for (PGAcquirerFeeValue feeValueModel : acquirerFeeValueModels) {
          feeValue = new FeeValue();
          feeValue.setFeePercentage(String.format("%.2f", feeValueModel.getFeePercentageOnly()));
          feeValue.setFlatFee(
              String.format("%.2f", CommonUtil.getDoubleAmountNotNull(feeValueModel.getFlatFee())));
          feeValue.setFeePgmValueId(feeValueModel.getFeeProgramId());
          feeValue.setCardType(feeValueModel.getCardType());
          feeValue.setAccountNumber(feeValueModel.getAccountNumber());
          feeValue.setAccountType(feeValueModel.getAccountType());
          feeValue.setFeeValueId(feeValueModel.getId());
          feeValues.add(feeValue);
        }
        feeProgram.setFeeValueList(feeValues);
        feePrograms.add(feeProgram);
      }
      if (StringUtil.isListNotNullNEmpty(feePrograms)) {
        feeProgramResponse.setFeeCodeList((feePrograms));
        feeProgramResponse.setErrorCode(Constants.STATUS_CODE_SUCCESS);
        feeProgramResponse.setErrorMessage(Constants.SUCESS);
      } else {
        feeProgramResponse.setErrorCode(Properties.getProperty("fee.program.not.exist.errorcode"));
        feeProgramResponse
            .setErrorMessage(Properties.getProperty(feeProgramResponse.getErrorCode()));
      }
    }
    catch (Exception e) {
      logger.error(
          "prepaidservice :: FeeProgramManagementImpl :: getFeeProgramDetails :: Exception ", e);
      String errorCode = Properties.getProperty("service.general.errorcode");
      feeProgramResponse.setErrorCode(errorCode);
      feeProgramResponse.setErrorMessage(Properties.getProperty(errorCode));
    }

    logger.info("prepaidservice :: FeeProgramManagementImpl :: getFeeProgramDetails Exiting");
    return feeProgramResponse;
  }

  @Override
  public FeeProgramResponseDetails getByFeeProgramId(FeeProgramDTO feeProgramDTO)
      throws ChatakAdminException {

    logger.info("prepaidservice :: FeeProgramManagementImpl :: getByFeeProgramId Entering");
    FeeProgramResponseDetails feeProgramResponse = new FeeProgramResponseDetails();
    try {
      List<PGFeeProgram> feeProgramlist =
          feeProgramDao.getByFeeProgramId(feeProgramDTO.getFeeProgramId());
      if (StringUtil.isListNotNullNEmpty(feeProgramlist)) {
        List<FeeProgramDTO> feeCodeList1 =
            CommonUtil.copyListBeanProperty(feeProgramlist, FeeProgramDTO.class);
        feeProgramResponse.setFeeCodeList(feeCodeList1);
        feeProgramResponse.setErrorCode(Constants.STATUS_CODE_SUCCESS);
        feeProgramResponse.setErrorMessage(Constants.SUCESS);
      } else {
        feeProgramResponse.setErrorCode(Properties.getProperty("bank.name.not.exist.errorcode"));
        feeProgramResponse
            .setErrorMessage(Properties.getProperty(feeProgramResponse.getErrorCode()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramServiceImpl:: getAllFeeCodeForJpa method", e);
      feeProgramResponse.setErrorCode(Constants.ERROR);
      feeProgramResponse.setErrorMessage(Constants.ERROR_DATA);
    }
    return feeProgramResponse;
  }

  @Override
  public Response updateFeeProgram(FeeProgramDTO feeProgramDTO) throws ChatakAdminException {
    logger.info("prepaidservice :: FeeProgramManagementImpl :: updateFeeProgram Entering");
    
    Response response = new Response();
    List<PGAcquirerFeeValue> feeProgramValueDaoDetails = new ArrayList<>();
    PGOtherFeeValue otherFeeValue = null;
    try {
      List<PGFeeProgram> feeProgramDetails =
          feeProgramDao.findByFeeProgramName(feeProgramDTO.getFeeProgramName());

      if (StringUtil.isListNotNullNEmpty(feeProgramDetails)
          && feeProgramDetails.get(0).getFeeProgramId().equals(feeProgramDTO.getFeeProgramId())) {
        PGFeeProgram feeProgramDaoDetails =
            CommonUtil.copyBeanProperties(feeProgramDTO, PGFeeProgram.class);
        feeProgramDaoDetails.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        List<PGAcquirerFeeValue> acquirerFeeValueModels =
            acquirerFeeValueDao.getAcquirerFeeValuesByFeeProgramId(feeProgramDTO.getFeeProgramId());
        acquirerFeeValueDao.removeAcquirerFeeValues(acquirerFeeValueModels);
        setFeeProgramValue(feeProgramDTO, feeProgramValueDaoDetails, acquirerFeeValueModels);
        feeProgramDaoDetails.setAcquirerFeeValueList(feeProgramValueDaoDetails);
        otherFeeValue = feeProgramDetails.get(0).getPgOtherFeeValue();
        otherFeeValue.setUpdatedBy(feeProgramDTO.getUpdatedBy());
        otherFeeValue.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        feeProgramDaoDetails.setCardProgramId(feeProgramDetails.get(0).getCardProgramId());
        feeProgramDaoDetails.setPgOtherFeeValue(otherFeeValue);
        feeProgramDao.createFeeProgram(feeProgramDaoDetails);
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
        return response;
      } else {
        throw new ChatakAdminException(
            Properties.getProperty("chatak.acquirer.updatefeeprogram.error.message"));
      }
    } catch (ChatakAdminException e) {
      logger.error("ERROR:: FeeProgramServiceImpl:: updateFeeProgram method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
      throw new ChatakAdminException(e.getMessage());
    } catch (Exception e) {
      logger.error("ERROR:: FeeProgramServiceImpl:: updateFeeProgram method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
      throw new ChatakAdminException();
    }
  }

private void setFeeProgramValue(FeeProgramDTO feeProgramDTO, List<PGAcquirerFeeValue> feeProgramValueDaoDetails,
		List<PGAcquirerFeeValue> acquirerFeeValueModels) {
	PGAcquirerFeeValue feeProgramValue;
	for (FeeValue feeValue : feeProgramDTO.getFeeValueList()) {
	  if (!feeValue.isEmpty()) {
	    feeProgramValue = new PGAcquirerFeeValue();
	    feeProgramValue.setFeePercentageOnly(Double.valueOf(feeValue.getFeePercentage()));
	    feeProgramValue
	        .setFlatFee(CommonUtil.getLongAmount(Double.valueOf(feeValue.getFlatFee())));
	    feeProgramValue.setStatus(feeProgramDTO.getStatus());
	    feeProgramValue.setCreatedBy(acquirerFeeValueModels.get(0).getCreatedBy());
	    feeProgramValue.setCreatedDate(acquirerFeeValueModels.get(0).getCreatedDate());
	    feeProgramValue.setUpdatedBy(feeProgramDTO.getUpdatedBy());
	    feeProgramValue.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	    feeProgramValueDaoDetails.add(feeProgramValue);
	  }
	}
}

  @Override
  public boolean validateFeePgmAccNo(String specificAccountNumber) {
    logger.info("prepaidservice :: FeeProgramServiceImpl :: validateFeePgmAccNo Entering");
    PGAccount pgAccount = accountRepository.findByAccountNum(Long.valueOf(specificAccountNumber));
    if (pgAccount != null
        && !(pgAccount.getEntityType().equalsIgnoreCase(PGConstants.DEFAULT_REVENUE_ACCOUNT))
        && pgAccount.getStatus().equalsIgnoreCase(PGConstants.S_STATUS_ACTIVE)) {
      PGMerchant pgMerchant = merchantRepository.findByMerchantCode(pgAccount.getEntityId());
      if (pgMerchant != null && pgMerchant.getStatus() == PGConstants.STATUS_SUCCESS) {
        logger.info("prepaidservice :: FeeProgramServiceImpl :: validateFeePgmAccNo Exiting");
        return true;
      }
    }

    logger.info("prepaidservice :: FeeProgramServiceImpl :: validateFeePgmAccNo Exiting");
    return false;
  }

  public Response deleteFeeProgram(Long getFeeProgramId) {
    logger.info("Entering:: FeeProgramServiceImpl :: deleteFeeProgram method");
    return feeProgramDao.deleteFeeProgram(getFeeProgramId);
  }

  @Override
  public FeeprogramNameResponse validateFeeprogramName(String feeProgramName) {
    FeeprogramNameResponse feeprogramNameResponse = new FeeprogramNameResponse();
    PGFeeProgram feeProgram = feeProgramDao.getFeeprogramName(feeProgramName);
    if (feeProgram != null) {
      if (feeProgram.getStatus().equalsIgnoreCase(PGConstants.S_STATUS_DELETED)) {
        feeprogramNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        feeprogramNameResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        return feeprogramNameResponse;
      } else {
        feeprogramNameResponse.setFeeProgramId(feeProgram.getFeeProgramId());
        feeprogramNameResponse.setFeeProgramName(feeProgram.getFeeProgramName());
        feeprogramNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
        feeprogramNameResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
        return feeprogramNameResponse;
      }

    } else {
      feeprogramNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      feeprogramNameResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return feeprogramNameResponse;
    }
  }

  @Override
  public Response changeFeeProgramStatus(FeeProgramDTO feeProgramDTO) throws ChatakAdminException {
    logger.info("Entering:: FeeProgramServiceImpl:: changeFeeProgramStatus method");
    Response response = new Response();
    try {
      if (null != feeProgramDTO) {
        List<PGFeeProgram> feeProgramModel =
            feeProgramDao.getByFeeProgramId(feeProgramDTO.getFeeProgramId());
        if (StringUtil.isListNotNullNEmpty(feeProgramModel)) {
          PGFeeProgram pgFeeProgram = feeProgramModel.get(0);
          pgFeeProgram.setStatus(feeProgramDTO.getStatus());
          pgFeeProgram.setReason(feeProgramDTO.getReason());
          feeProgramDao.createFeeProgram(pgFeeProgram);
        }
        response.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (DataAccessException e) {
    	logger.error("ERROR:: FeeProgramServiceImpl:: changeFeeProgramStatus method", e);
      response.setErrorCode(ActionErrorCode.ERROR_CODE_01);
    }
    logger.info("Exiting:: FeeProgramServiceImpl:: changeFeeProgramStatus method");
    return response;
  }
}
