package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGActionCodeParameters;
import com.chatak.pg.acq.dao.model.PGAid;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.acq.dao.model.PGMagneticStripeParameters;
import com.chatak.pg.acq.dao.model.PGParameterProfile;
import com.chatak.pg.acq.dao.model.PGTerminalDeviceMangement;
import com.chatak.pg.model.TerminalDeviceManagementDTO;

public interface TerminalDeviceManagementDao 
{
	public List<PGAid> getAllApplicationId()throws DataAccessException;
	
	public List<PGMagneticStripeParameters> getAllMagneticStripeCardParameters()throws DataAccessException;
	
	public List<PGCaPublicKeys> getAllCAPublicKeyResponse()throws DataAccessException;
	
	public List<PGActionCodeParameters> getAllActionCodeParameters()throws DataAccessException;
	
	public List<PGParameterProfile> getAllParametersProfileName(String parameterType)throws DataAccessException;
	
	public PGTerminalDeviceMangement createTerminalDevice(PGTerminalDeviceMangement mobileDeviceMangement)throws DataAccessException;
	
	public List<TerminalDeviceManagementDTO> searchTerminalDevice(TerminalDeviceManagementDTO mobileDeviceManagementTO)throws DataAccessException;
	
	public PGTerminalDeviceMangement findByTerminalDeviceId(Long deviceId)throws DataAccessException;

	public PGTerminalDeviceMangement updateTerminalDeviceDetails(PGTerminalDeviceMangement mobileDeviceMangement)throws DataAccessException;
	
	public PGAid findByApplicationId(Long applicationId)throws DataAccessException;
	
	public PGMagneticStripeParameters findByMagneticStripeId(Long magneticStripeId)throws DataAccessException;
	
	public PGCaPublicKeys findByPublicKeyId(Long publicKeyId)throws DataAccessException;
	
	public PGActionCodeParameters findByActioncodeId(Long actioncodeId)throws DataAccessException;
	
	public Boolean updateCaKeys(TerminalDeviceManagementDTO mobileDeviceManagement) throws DataAccessException;
	
	public Boolean updateDeviceStatus(PGTerminalDeviceMangement mobileDeviceMangement) throws DataAccessException;
	
	public PGTerminalDeviceMangement findByDeviceId(Long deviceId)throws DataAccessException;
	
	public void updateDeviceId(PGTerminalDeviceMangement mobileDeviceMangement) throws DataAccessException;
	
	public PGTerminalDeviceMangement findByMerchantTerminalId(String merchantTerminalId)throws DataAccessException;
	
	public void linkTerminalDevice(PGTerminalDeviceMangement mobileDeviceMangement) throws DataAccessException;
	
} 
