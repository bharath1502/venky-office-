
package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CAPublicKeysResponse;
import com.chatak.acquirer.admin.model.Response;
import com.chatak.acquirer.admin.service.CAPublicKeysService;
import com.chatak.pg.acq.dao.CAPublicKeysDao;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.bean.PublickeyNameResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.CAPublicKeysDTO;
import com.chatak.pg.util.Constants;

@Service("caPublicKeysService")
public class CAPublicKeysServiceImpl implements CAPublicKeysService, PGConstants {

  @Autowired
  private CAPublicKeysDao caPublicKeysDao;

  private static Logger logger = Logger.getLogger(CAPublicKeysServiceImpl.class);

  @Override
  public CAPublicKeysResponse searchCAPublicKeys(CAPublicKeysDTO caPublicKeysDTO)
      throws ChatakAdminException {
    logger
        .info("caPublicKeysService:: CAPublicKeysServiceImpl:: searchCAPublickeys method Entering");
    CAPublicKeysResponse caPublicKeysResponse = new CAPublicKeysResponse();

    try {
      List<CAPublicKeysDTO> caPublicKeysResponseList =
          caPublicKeysDao.searchCAPublicKeys(caPublicKeysDTO);
      if (caPublicKeysResponseList != null) {
        caPublicKeysResponse.setCaPublicKeysList(caPublicKeysResponseList);
        caPublicKeysResponse.setTotalNoOfRows(caPublicKeysDTO.getNoOfRecords());
      }
      caPublicKeysResponse.setErrorCode(Constants.STATUS_CODE_SUCCESS);
      caPublicKeysResponse.setErrorMessage(Constants.SUCESS);
      logger.info("caPublicKeysService :: CAPublicKeysServiceImpl :: searchCAPublicKeys Exiting");
      return caPublicKeysResponse;
    } catch (Exception e) {
      logger.error("ERROR:: CAPublicKeysServiceImpl:: getALLCAPublicKeys method", e);
      caPublicKeysResponse.setErrorCode(Constants.ERROR);
      caPublicKeysResponse.setErrorMessage(Constants.ERROR_DATA);
    }
    return caPublicKeysResponse;
  }

  @Override
  public Response createCAPublicKeys(CAPublicKeysDTO caPublicKeysDTO) throws ChatakAdminException {

    Response response = new Response();
    PGCaPublicKeys caPublicKeysDaoDetails = setPGCaPublicKeysDetails(caPublicKeysDTO);
    caPublicKeysDao.createCAPublicKeys(caPublicKeysDaoDetails);
    response.setErrorCode(Constants.SUCCESS_CODE);
    response.setErrorMessage(Constants.SUCESS);
    return response;
  }

  private PGCaPublicKeys setPGCaPublicKeysDetails(CAPublicKeysDTO caPublicKeysDTO) {
	PGCaPublicKeys caPublicKeysDaoDetails = new PGCaPublicKeys();
    caPublicKeysDaoDetails.setPublicKeyId(caPublicKeysDTO.getPublicKeyId());
    caPublicKeysDaoDetails.setPublicKeyName(caPublicKeysDTO.getPublicKeyName());
    caPublicKeysDaoDetails.setCreatedBy(caPublicKeysDTO.getCreatedBy());
    caPublicKeysDaoDetails.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    caPublicKeysDaoDetails.setStatus(S_STATUS_ACTIVE);
    caPublicKeysDaoDetails.setrId(caPublicKeysDTO.getRid());
    caPublicKeysDaoDetails.setPublicKeyModulus(caPublicKeysDTO.getPublicKeyModulus());
    caPublicKeysDaoDetails.setPublicKeyExponent(caPublicKeysDTO.getPublicKeyExponent());
    caPublicKeysDaoDetails.setExpiryDate(caPublicKeysDTO.getExpiryDate());
    caPublicKeysDaoDetails.setPublicKeyIndex(caPublicKeysDTO.getPublicKeyIndex());
	return caPublicKeysDaoDetails;
  }

  @Override
  public Response updateCAPublicKeys(CAPublicKeysDTO caPublicKeysDTO) throws ChatakAdminException {

    Response response = new Response();
    PGCaPublicKeys caPublicKeysDaoDetails = setPGCaPublicKeysDetails(caPublicKeysDTO);
    caPublicKeysDao.updateCAPublicKeys(caPublicKeysDaoDetails);
    response.setErrorCode(Constants.SUCCESS_CODE);
    response.setErrorMessage(Constants.SUCESS);
    return response;
  }

  @Override
  public PGCaPublicKeys caPublicKeysById(Long getCAPublicKeysId) {
    return caPublicKeysDao.caPublicKeysById(getCAPublicKeysId);
  }

  @Override
  public CAPublicKeysResponse changeCAPublicKeysStatus(CAPublicKeysDTO caPublicKeysDTO) {
    logger.info("Entering:: CAPublicKeysServiceImpl:: changeCAPublicKeysStatus method");
    CAPublicKeysResponse caPublicKeysResponse = new CAPublicKeysResponse();
    try {
      if (null != caPublicKeysDTO) {
        PGCaPublicKeys pgCaPublicKeys =
            caPublicKeysDao.caPublicKeysById(caPublicKeysDTO.getPublicKeyId());
        pgCaPublicKeys.setStatus(caPublicKeysDTO.getStatus());
        pgCaPublicKeys.setReason(caPublicKeysDTO.getReason());
        caPublicKeysDao.createCAPublicKeys(pgCaPublicKeys);
        caPublicKeysResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (DataAccessException e) {
      caPublicKeysResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
      logger.error("", e);
    }
    logger.info("Exiting:: CAPublicKeysServiceImpl:: changeCAPublicKeysStatus method");
    return caPublicKeysResponse;
  }

  @Override
  public PublickeyNameResponse validatePublickeyName(String publicKeyName) {
    PublickeyNameResponse publickeyNameResponse = new PublickeyNameResponse();
    PGCaPublicKeys caPublicKeys = caPublicKeysDao.getpublicKeyName(publicKeyName);
    if (caPublicKeys != null) {
      if (caPublicKeys.getStatus().equalsIgnoreCase(PGConstants.S_STATUS_SUSPENDED)
          || caPublicKeys.getStatus().equalsIgnoreCase(PGConstants.S_STATUS_ACTIVE)) {
        publickeyNameResponse.setPublicKeyId(caPublicKeys.getPublicKeyId());
        publickeyNameResponse.setPublicKeyName(caPublicKeys.getPublicKeyName());
        publickeyNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY);
        publickeyNameResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_DUPLICATE_ENTRY));
        return publickeyNameResponse;
      } else {
        publickeyNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
        publickeyNameResponse.setErrorMessage(
            ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
        return publickeyNameResponse;
      }
    } else {
      publickeyNameResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      publickeyNameResponse
          .setErrorMessage(ActionErrorCode.getInstance().getMessage(ActionErrorCode.ERROR_CODE_00));
      return publickeyNameResponse;
    }

  }

  @Override
  public CAPublicKeysDTO saveOrUpdateCAPublicKey(CAPublicKeysDTO caPublicKeyDTO)
      throws ChatakAdminException {
    try {
      PGCaPublicKeys pgCaPublicKeys = new PGCaPublicKeys();
      if (S_STATUS_ACTIVE.equals(caPublicKeyDTO.getStatus())) {
        pgCaPublicKeys.setStatus(STATUS_SUCCESS.toString());
      } else if (S_STATUS_PENDING.equals(caPublicKeyDTO.getStatus())) {
        pgCaPublicKeys.setStatus(STATUS_PENDING.toString());
      } else if (S_STATUS_DELETED.equalsIgnoreCase(caPublicKeyDTO.getStatus())) {
        pgCaPublicKeys.setStatus(S_STATUS_DELETED);
      }

      pgCaPublicKeys.setPublicKeyName(caPublicKeyDTO.getPublicKeyName());
      pgCaPublicKeys.setrId(caPublicKeyDTO.getRid());
      pgCaPublicKeys.setPublicKeyModulus(caPublicKeyDTO.getPublicKeyModulus());
      pgCaPublicKeys.setPublicKeyExponent(caPublicKeyDTO.getPublicKeyExponent());
      pgCaPublicKeys.setExpiryDate(caPublicKeyDTO.getExpiryDate());
      pgCaPublicKeys.setPublicKeyIndex(caPublicKeyDTO.getPublicKeyIndex());
      pgCaPublicKeys.setPublicKeyId(caPublicKeyDTO.getPublicKeyId());
      pgCaPublicKeys.setCreatedBy(caPublicKeyDTO.getCreatedBy());
      pgCaPublicKeys.setCreatedDate(caPublicKeyDTO.getCreateDate());
      

      PGCaPublicKeys pGCaPublicKeysResponse = caPublicKeysDao.saveCAPublicKey(pgCaPublicKeys);
      if (pGCaPublicKeysResponse != null) {
        caPublicKeyDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
      }
    } catch (Exception e) {
    	 logger.error("Exiting:: CAPublicKeysServiceImpl:: saveOrUpdateCAPublicKey method",e);
      throw new ChatakAdminException(e.getMessage());
    }
    return caPublicKeyDTO;
  }

  @Override
  public CAPublicKeysDTO findCAPublicKeyById(Long getCAPublicKeysId) {
    return caPublicKeysDao.findByPublicKeyId(getCAPublicKeysId);
  }
}
