package com.chatak.acquirer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.MDRService;
import com.chatak.acquirer.admin.util.CommonUtil;
import com.chatak.pg.acq.dao.MDRDao;
import com.chatak.pg.acq.dao.model.PGDynamicMDR;
import com.chatak.pg.bean.Response;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.DynamicMDRDTO;
import com.chatak.pg.util.DateUtil;

@Service
public class MDRServiceImpl implements MDRService{
	
	private static Logger logger = Logger.getLogger(MDRServiceImpl.class);
	
	@Autowired
	MDRDao mdrDao;

	@Override
	public DynamicMDRDTO saveOrUpdateDynamicMDR(DynamicMDRDTO dynamicMDRDTO)
			throws ChatakAdminException {
		logger.info("MDRServiceImpl | saveOrUpdateDynamicMDR | Entering");
		try{
		PGDynamicMDR pgDynamicMDR = new PGDynamicMDR();
		
		pgDynamicMDR.setId(dynamicMDRDTO.getId());
		pgDynamicMDR.setBinNumber(dynamicMDRDTO.getBinNumber());
		pgDynamicMDR.setAccountType(dynamicMDRDTO.getAccountType());
		pgDynamicMDR.setTransactionType(dynamicMDRDTO.getTransactionType());
		pgDynamicMDR.setProductType(dynamicMDRDTO.getProductType());
		pgDynamicMDR.setBankName(dynamicMDRDTO.getBankName());
		pgDynamicMDR.setPaymentSchemeName(dynamicMDRDTO.getPaymentSchemeName());
		pgDynamicMDR.setSlab(Double.valueOf(dynamicMDRDTO.getSlab()));
		pgDynamicMDR.setCreatedDate(DateUtil.getCurrentTimestamp());
		pgDynamicMDR.setCreatedBy(dynamicMDRDTO.getCreatedBy());
		pgDynamicMDR.setUpdatedDate(DateUtil.getCurrentTimestamp());
		pgDynamicMDR.setUpdatedBy(dynamicMDRDTO.getCreatedBy());
		
		PGDynamicMDR pgDynamicMDRResponse = mdrDao.saveDynamicMDR(pgDynamicMDR);
		if(pgDynamicMDRResponse != null ){
			dynamicMDRDTO.setErrorMessage("Successfully Done !");
			dynamicMDRDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
		}else{
			dynamicMDRDTO.setErrorMessage("Failed to Create or Update !");
			dynamicMDRDTO.setErrorCode(ActionErrorCode.ERROR_CODE_MDR1);
		}
		}catch(Exception e ){
			logger.error("ERROR:: MDRServiceImpl:: saveOrUpdateDynamicMDR method", e);
			throw new ChatakAdminException(e.getMessage());
		}
		logger.info("MDRServiceImpl | saveOrUpdateDynamicMDR | Exiting");
		return dynamicMDRDTO;
	}

	@Override
	public DynamicMDRDTO searchMDRById(Long getMDRId) {
		logger.info("MDRServiceImpl | searchMDRById | Entering");
		logger.info("MDRServiceImpl | searchMDRById | Exiting");
		return mdrDao.findById(getMDRId);
		
	}
	
	@Override
	public Response searchDynamicMDR(DynamicMDRDTO dynamicMDRDTO) {
		logger.info("MDRServiceImpl | searchDynamicMDR | Entering");
		DynamicMDRDTO dynamicMDR = null;
		List<DynamicMDRDTO> dynamicMDRDTOs = new ArrayList<DynamicMDRDTO>();
		List<PGDynamicMDR> pgMDRDynamic = mdrDao.searchDynamicMDR(dynamicMDRDTO);
		if(StringUtil.isListNotNullNEmpty(pgMDRDynamic)){
			for(PGDynamicMDR pgMDR: pgMDRDynamic){
				dynamicMDR = new DynamicMDRDTO();
				dynamicMDR.setId(pgMDR.getId());
				dynamicMDR.setBinNumber(pgMDR.getBinNumber());
				dynamicMDR.setPaymentSchemeName(pgMDR.getPaymentSchemeName());
				dynamicMDR.setBankName(pgMDR.getBankName());
				dynamicMDR.setAccountType(pgMDR.getAccountType());
				dynamicMDR.setProductType(pgMDR.getProductType());
				dynamicMDR.setTransactionType(pgMDR.getTransactionType());
				dynamicMDR.setSlab(pgMDR.getSlab().toString());
				dynamicMDRDTOs.add(dynamicMDR);
			}
		}
		Response response = CommonUtil.getSuccessResponse(); 
		response.setTotalNoOfRows(dynamicMDRDTO.getNoOfRecords());
		response.setResponseList(dynamicMDRDTOs);
		logger.info("MDRServiceImpl | searchDynamicMDR | Exiting");
		return response;
	}

}
