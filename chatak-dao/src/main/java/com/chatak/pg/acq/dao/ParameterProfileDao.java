package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGCurrencyCode;
import com.chatak.pg.acq.dao.model.PGIsoCountryCode;
import com.chatak.pg.acq.dao.model.PGLocalCurrencyCode;
import com.chatak.pg.acq.dao.model.PGParameterProfile;
import com.chatak.pg.acq.dao.model.PGTerminalCapabilities;
import com.chatak.pg.model.ParameterProfileDTO;


public interface ParameterProfileDao {
	
	public Long findByProfileName(String profileName);
	
	public PGParameterProfile createOrUpdateParameterProfile(PGParameterProfile parameterProfile);
	
	public PGParameterProfile findByParameterProfileId(Long profileId);
	
	public PGTerminalCapabilities findByTerminalCapabilitiesId(Long terminalCapablitiesId);
	
	public List<ParameterProfileDTO> searchParameterProfile(ParameterProfileDTO parameterProfileTO);
	
	public List<PGIsoCountryCode> getIsoCountryCodeList();
	
	public List<PGLocalCurrencyCode> getLocalCurrencyCodeList();
	
	public List<PGCurrencyCode> getCurrencyCodeList();
	
	public PGParameterProfile updateStatus(PGParameterProfile parameterProfile)throws DataAccessException;

}
