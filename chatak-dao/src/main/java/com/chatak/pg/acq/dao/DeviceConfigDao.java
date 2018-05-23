/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.Device;
import com.chatak.pg.model.PosDeviceDTO;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 5:14:36 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface DeviceConfigDao {

	/**
	 * @param deviceTO
	 * @return
	 * @throws Exception 
	 * @throws DataAccessException 
	 */
	public List<Device> searchDeviceData(PosDeviceDTO deviceTO);

	/**
	 * @param device
	 * @return
	 */
	public Device createOrUpdateDevice(Device device);

	/**
	 * @param deviceId
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public Device findByDeviceId(Long deviceId);

	/**
	 * @return
	 */
	public List<PosDeviceDTO> getDeviceData();
}
