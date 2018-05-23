package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.model.Switch;
import com.chatak.pg.user.bean.SwitchRequest;
import com.chatak.pg.user.bean.SwitchResponse;

public interface SwitchService {
	
	public SwitchResponse addSwitchInformation(Switch switchInfo, String userid) throws ChatakAdminException;
	
	public SwitchResponse searchSwitchInformation(SwitchRequest searchSwitchRequest) throws ChatakAdminException;

	public SwitchResponse updateSwitchInformation(SwitchRequest updateSwitch, String userid);

	public SwitchRequest getSwtichInformationById(Long getSwitchId);
	
	public SwitchResponse getSwitchByStatus(Integer status );
	
	public SwitchResponse changeSwitchStatus(SwitchRequest switchRequest, String switchStatus);

}
