package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGPosDevice;
import com.chatak.pg.model.PosDeviceDTO;
import com.chatak.pg.user.bean.DeleteAssetResponse;

public interface AssetManagementDao {

	public PGPosDevice createOrUpdateDevice(PGPosDevice asset);
	
	public List<PGPosDevice> searchAssetDevice(PosDeviceDTO deviceTO);
	
	public PGPosDevice findByDeviceId(Long deviceId);
	
	public List<PGPosDevice> findByDeviceSerialNoIgnoreCase(String deviceSerialNo);
	/**
	 * @param id
	 * @return
	 */
	public DeleteAssetResponse deleteAssetDevice(Long id);

	
}
