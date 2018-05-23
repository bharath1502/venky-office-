package com.chatak.pg.acq.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.PanLengthDao;
import com.chatak.pg.acq.dao.model.PGPanLength;
import com.chatak.pg.acq.dao.repository.PanLengthRepository;

@Repository("panLengthDao")
public class PanLengthDaoImpl implements PanLengthDao {

	@Autowired
	PanLengthRepository panLengthRepository;
	
	@Override
	public List<PGPanLength> getAllPanLengths() throws DataAccessException {
		return panLengthRepository.findAll();
	}

}
