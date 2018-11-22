package com.chatak.acquirer.admin.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.BinService;
import com.chatak.pg.acq.dao.BINDao;
import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.bean.BinDuplicateResponse;
import com.chatak.pg.constants.ActionErrorCode;
import com.chatak.pg.constants.PGConstants;
import com.chatak.pg.model.BinDTO;
import com.chatak.pg.model.BinResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;

@Service
public class BinServiceImpl implements BinService, PGConstants{
	private static Logger logger = Logger.getLogger(BinServiceImpl.class);
	
	@Autowired
	private BINDao binDao; 

	@Override
	public List<BinDTO> searchBin(BinDTO binDTO) throws ChatakAdminException {
		return binDao.getAllBins();
	}

	@Override
	public BinDTO saveOrUpdateBin(BinDTO binDTO) throws ChatakAdminException {
		try{
		PGBINRange pgbinRange = new PGBINRange();
			if ("Active".equals(binDTO.getStatus()) ) {
				pgbinRange.setStatus(0);
			} else if ("Suspended".equals(binDTO.getStatus())) {
				pgbinRange.setStatus(1);
			} else if ("Deleted".equals(binDTO.getStatus())) {
				pgbinRange.setStatus(Constants.THREE);
			}
		pgbinRange.setCreatedDate(DateUtil.getCurrentTimestamp());
		pgbinRange.setBin(binDTO.getBinNumber().longValue());
		pgbinRange.setDccSupported(binDTO.getDccSupported());
		pgbinRange.setEmvSupported(binDTO.getEmvSupported());
		pgbinRange.setSwitchId(Long.valueOf(binDTO.getSwitchId()));
		
		PGBINRange pgBinResponse = binDao.saveBin(pgbinRange);
		if(pgBinResponse != null ){
			binDTO.setErrorCode(ActionErrorCode.ERROR_CODE_00);
		}
		}catch(Exception e ){
			logger.error("Entering:: BinServiceImpl:: saveOrUpdateBin method",e);
			throw new ChatakAdminException(e.getMessage());
		}
		return binDTO;
	}
	
	public BinResponse changeBinStatus(BinDTO binDTO) throws ChatakAdminException {
		logger.info("Entering:: BinServiceImpl:: changeBinStatus method");
		BinResponse binResponse = new BinResponse();
		try {
			if (null != binDTO) {
				PGBINRange pgbinRange = binDao.findByBinId(binDTO.getId());
				if (S_STATUS_ACTIVE.equals(binDTO.getStatus())) {
					pgbinRange.setStatus(STATUS_ACTIVE);
				} else {
					pgbinRange.setStatus(STATUS_SUSPENDED);
				}
				pgbinRange.setReason(binDTO.getReason());
				binDao.createOrUpdateBin(pgbinRange);
				binResponse.setErrorCode(ActionErrorCode.ERROR_CODE_00);
			}
		} catch (DataAccessException e) {
			logger.error("Entering:: BinServiceImpl:: changeBinStatus method",e);
			binResponse.setErrorCode(ActionErrorCode.ERROR_CODE_01);
		}
		return binResponse;
	}

	@Override
	public BinDTO searchBinById(Long binId) throws ChatakAdminException {
		return binDao.findById(binId);
	}
	
	@Override
	public BinResponse searchBins(BinDTO binDTO) throws ChatakAdminException{
		return binDao.searchBin(binDTO);
	}

	@Override
	public BinDuplicateResponse validateBin(Long bin) throws DataAccessException {
		return binDao.getUserByBin(bin);
	}
}
