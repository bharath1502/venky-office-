package com.chatak.acquirer.admin.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CommissionProgramResponseDetails;
import com.chatak.acquirer.admin.service.CommissionService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.acquirer.admin.util.StringUtil;
import com.chatak.pg.acq.dao.CommissionDao;
import com.chatak.pg.acq.dao.model.PGCommission;
import com.chatak.pg.acq.dao.model.PGOtherCommission;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.CommissionDTO;
import com.chatak.pg.model.OtherCommissionDTO;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;


@Service("commissionService")
public class CommissionServiceImpl implements CommissionService {
  private static Logger logger = Logger.getLogger(CommissionServiceImpl.class);

  @Autowired
  CommissionDao commissionDao;

  @Override
  public Response createCommission(CommissionDTO commissionDTO) throws ChatakAdminException {
    Response response = new Response();
    List<PGOtherCommission> otherCommission = new ArrayList<PGOtherCommission>();
    PGOtherCommission pgOtherCommission = null;
    try {
      PGCommission commissionDaoDetails =
          CommonUtil.copyBeanProperties(commissionDTO, PGCommission.class);
      commissionDaoDetails
          .setMerchantOnBoardingFee(Double.valueOf(commissionDTO.getMerchantOnBoardingFee()));
      commissionDaoDetails.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      for (OtherCommissionDTO otherCommissionValue : commissionDTO.getOtherCommissionDTO()) {
        pgOtherCommission = new PGOtherCommission();
        pgOtherCommission.setCommissionType(otherCommissionValue.getCommissionType());
        setMiscCommissionValues(pgOtherCommission, otherCommissionValue);
        otherCommission.add(pgOtherCommission);
      }
      commissionDaoDetails.setOtherCommission(otherCommission);
       commissionDao.createCommissionProgram(commissionDaoDetails);
      response.setErrorCode(Constants.SUCCESS_CODE);
      response.setErrorMessage(Constants.SUCESS);
      return response;
    } catch (Exception e) {
      logger.error("Error :: CommissionServiceImpl :: createCommission", e);
      logger.error("ERROR:: CommissionServiceImpl:: createFeeProgram method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }

    return response;
  }

private void setMiscCommissionValues(PGOtherCommission pgOtherCommission, OtherCommissionDTO otherCommissionValue) {
	if (otherCommissionValue.getCommissionType() != null) {
	    if (otherCommissionValue.getCommissionType().equals("flat")) {
	        pgOtherCommission.setFlatFee(Double.valueOf(otherCommissionValue.getFlatFee()));

	      } else {
	        pgOtherCommission.setFromValue(Double.valueOf(otherCommissionValue.getFromValue()));
	        pgOtherCommission.setToValue(Double.valueOf(otherCommissionValue.getToValue()));
	        pgOtherCommission.setAmount(Double.valueOf(otherCommissionValue.getAmount()));
	      }
	} else {
	  pgOtherCommission.setFromValue(Double.valueOf(otherCommissionValue.getFromValue()));
	  pgOtherCommission.setToValue(Double.valueOf(otherCommissionValue.getToValue()));
	  pgOtherCommission.setAmount(Double.valueOf(otherCommissionValue.getAmount()));
	}
}

  @Override
  public Response searchCommissionProgram(CommissionDTO commissionDTO) throws ChatakAdminException {
    List<CommissionDTO> dynamicMDRDTOs = new ArrayList<CommissionDTO>();
    List<PGCommission> pgcommission = commissionDao.searchCommissionProgram(commissionDTO);
    if (StringUtil.isListNotNullNEmpty(pgcommission)) {
      dynamicMDRDTOs = CommonUtil.copyListBeanProperty(pgcommission, CommissionDTO.class);
    }
    Response response = CommonUtil.getSuccessResponse();
    response.setTotalNoOfRows(commissionDTO.getNoOfRecords());
    response.setResponseList(dynamicMDRDTOs);
    return response;
  }

  @Override
  public CommissionProgramResponseDetails getCommissionProgramDetails(CommissionDTO commissionDTO)
      throws ChatakAdminException {
    logger.info("Entering :: CommissionServiceImpl :: getCommissionProgramDetails Method");
    CommissionProgramResponseDetails commProgramResponse = new CommissionProgramResponseDetails();
    OtherCommissionDTO othComm = null;
    List<OtherCommissionDTO> othComms = new ArrayList<OtherCommissionDTO>();
    CommissionDTO commProgram = new CommissionDTO();
    List<CommissionDTO> commPrograms = new ArrayList<CommissionDTO>();
    try {
      List<PGCommission> commProgramModel =
          commissionDao.getByCommProgramId(commissionDTO.getCommissionProgramId());
      if (StringUtil.isListNotNullNEmpty(commProgramModel)) {
        commProgram.setCommissionProgramId(commProgramModel.get(0).getCommissionProgramId());
        commProgram.setCommissionName(commProgramModel.get(0).getCommissionName());
        commProgram.setMerchantOnBoardingFee(
            commProgramModel.get(0).getMerchantOnBoardingFee().toString());
        commProgram.setStatus(commProgramModel.get(0).getStatus());
        List<PGOtherCommission> othCommDTOValues = commissionDao
            .getOtherCommissionByCommissionProgramId(commissionDTO.getCommissionProgramId());
        for (PGOtherCommission othCommModel : othCommDTOValues) {
          othComm = new OtherCommissionDTO();
          othComm.setCommissionProgramId(othCommModel.getCommissionProgramId());
          othComm.setCommissionType(othCommModel.getCommissionType());
          setCommissionValues(othComm, othCommModel);
          othComms.add(othComm);
        }
        commProgram.setOtherCommissionDTO(othComms);
        commPrograms.add(commProgram);
      }
      if (StringUtil.isListNotNullNEmpty(commPrograms)) {
        commProgramResponse.setErrorCode(Constants.STATUS_CODE_SUCCESS);
        commProgramResponse.setErrorMessage(Constants.SUCESS);
        commProgramResponse.setCommProgList(commPrograms);
      } else {
        commProgramResponse.setErrorCode(Properties.getProperty("fee.program.not.exist.errorcode"));
        commProgramResponse
            .setErrorMessage(Properties.getProperty(commProgramResponse.getErrorCode()));
      }
    } catch (Exception e) {
      logger.error("Exception :: CommissionServiceImpl :: getCommissionProgramDetails :: Method ",
          e);
      String errorCode = Properties.getProperty("service.general.errorcode");
      commProgramResponse.setErrorCode(errorCode);
      commProgramResponse.setErrorMessage(Properties.getProperty(errorCode));
    }

    logger.info("Exiting :: CommissionServiceImpl :: getCommissionProgramDetails : Method");
    return commProgramResponse;
  }

private void setCommissionValues(OtherCommissionDTO othComm, PGOtherCommission othCommModel) {
	if (othCommModel.getCommissionType() != null) {
		  if (othComm.getCommissionType().equals("flat")) {
	          othComm.setFlatFee(othCommModel.getFlatFee().toString());

	        } else {
	          othComm.setId(othComm.getId());
	          othComm.setFromValue(othCommModel.getFromValue().toString());
	          othComm.setToValue(othCommModel.getToValue().toString());
	          othComm.setAmount(othCommModel.getAmount().toString());
	        }
	  } else {
	    othComm.setId(othComm.getId());
	    othComm.setFromValue(othCommModel.getFromValue().toString());
	    othComm.setToValue(othCommModel.getToValue().toString());
	    othComm.setAmount(othCommModel.getAmount().toString());
	  }
}

  @Override
  public CommissionProgramResponseDetails getByCommProgramId(CommissionDTO commissionDTO)
      throws ChatakAdminException {

    logger.info("Entering :: CommissionServiceImpl :: getByFeeProgramId Method");
    CommissionProgramResponseDetails commProgramResponse = new CommissionProgramResponseDetails();
    try {
      List<PGCommission> commProgramlist =
          commissionDao.getByCommProgramId(commissionDTO.getCommissionProgramId());
      if (StringUtil.isListNotNullNEmpty(commProgramlist)) {
        List<CommissionDTO> commProgList1 =
            CommonUtil.copyListBeanProperty(commProgramlist, CommissionDTO.class);
        commProgramResponse.setCommProgList(commProgList1);
        commProgramResponse.setErrorCode(Constants.STATUS_CODE_SUCCESS);
        commProgramResponse.setErrorMessage(Constants.SUCESS);
      } else {
        commProgramResponse.setErrorCode(Properties.getProperty("bank.name.not.exist.errorcode"));
        commProgramResponse
            .setErrorMessage(Properties.getProperty(commProgramResponse.getErrorCode()));
      }
    } catch (Exception e) {
      logger.error("ERROR:: CommissionServiceImpl:: getByCommisionProgramId method", e);
      commProgramResponse.setErrorCode(Constants.ERROR);
      commProgramResponse.setErrorMessage(Constants.ERROR_DATA);
    }
    return commProgramResponse;
  }

  @Override
  public Response updateCommissionProgram(CommissionDTO commissionDTO) throws ChatakAdminException {
    logger.info("Entering :: CommissionServiceImpl :: updateCommissionProgram Method");
    Response response = new Response();
    List<PGOtherCommission> otherCommission = new ArrayList<PGOtherCommission>();
    try {
      List<PGCommission> pgCommDetails =
          commissionDao.findByCommissionName(commissionDTO.getCommissionName());
      if (StringUtil.isListNotNullNEmpty(pgCommDetails) && pgCommDetails.get(0)
          .getCommissionProgramId().equals(commissionDTO.getCommissionProgramId())) {
        PGCommission commProgramDaoDetails =
            CommonUtil.copyBeanProperties(commissionDTO, PGCommission.class);
        commProgramDaoDetails.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        commProgramDaoDetails
            .setMerchantOnBoardingFee(Double.valueOf(commissionDTO.getMerchantOnBoardingFee()));
        List<PGOtherCommission> othCommission = commissionDao
            .getOtherCommissionByCommissionProgramId(commissionDTO.getCommissionProgramId());
         commissionDao.removeOthCommission(othCommission);
        populateCommissionValues(commissionDTO, otherCommission);
        commProgramDaoDetails.setOtherCommission(otherCommission);
        commissionDao.createCommissionProgram(commProgramDaoDetails);
        response.setErrorCode(Constants.SUCCESS_CODE);
        response.setErrorMessage(Constants.SUCESS);
        return response;
      }
    } catch (Exception e) {
      logger.error("ERROR:: CommissionServiceImpl:: updateCommissionProgram method", e);
      response.setErrorCode(Constants.ERROR);
      response.setErrorMessage(Constants.ERROR_DATA);
    }
    return response;
  }

	private void populateCommissionValues(CommissionDTO commissionDTO, List<PGOtherCommission> otherCommission) {
		PGOtherCommission pgOtherCommission;
		for (OtherCommissionDTO othValue : commissionDTO.getOtherCommissionDTO()) {
			if (null != othValue) {
				pgOtherCommission = new PGOtherCommission();
				pgOtherCommission.setCommissionType(othValue.getCommissionType());
				setMiscCommissionValues(pgOtherCommission, othValue);
				pgOtherCommission.setCommissionProgramId(othValue.getCommissionProgramId());
				otherCommission.add(pgOtherCommission);
			}
		}
	}
}


