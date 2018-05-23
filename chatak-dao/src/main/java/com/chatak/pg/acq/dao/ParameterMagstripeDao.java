package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGParameterMagstripe;
import com.chatak.pg.model.ParameterMagstripeDTO;

public interface ParameterMagstripeDao {

	public PGParameterMagstripe createOrUpdateParameterMagstripe(PGParameterMagstripe parameterMagstripe);
	
	public List<PGParameterMagstripe> searchParameterMagstripe(ParameterMagstripeDTO parameterMagstripeTO);
	
	public PGParameterMagstripe findByMagstripeId(Long magstripeId);
	
	public List<PGParameterMagstripe> findByMagstripeName(String magstripeName);
}
