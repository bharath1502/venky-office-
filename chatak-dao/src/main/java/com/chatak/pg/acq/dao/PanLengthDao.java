package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGPanLength;

public interface PanLengthDao {

	public List<PGPanLength> getAllPanLengths()throws DataAccessException;
}
