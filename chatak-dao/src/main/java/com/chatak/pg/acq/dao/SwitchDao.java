/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.chatak.pg.acq.dao.model.PGSwitch;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;

/**
 *
 * DAO class to handle Switch DB operations
 *
 * @author Girmiti Software
 * @date 22-Dec-2014 5:17:01 pm
 * @version 1.0
 */
public interface SwitchDao {

	public PGSwitch getSwitchByName(String switchName); 
	
	public List<PGSwitch> getAllSwitchNamesByStatus(Integer status);
	
	public SwitchResponse addSwitchInformation(SwitchRequest addSwitchRequest, String userid);

	public List<SwitchRequest> searchSwitchInformation(SwitchRequest addSwitchRequest);

	public SwitchResponse updateSwitchInformation(SwitchRequest updateSwitchRequest, String userid);

	public PGSwitch getSwtichInformationById(Long getSwitchId);
	
	public PGSwitch createOrUpdateUser(PGSwitch pGSwitch) throws DataAccessException;

}
