package com.chatak.merchant.service;

import java.util.List;
import java.util.Map;

import com.chatak.merchant.exception.ChatakMerchantException;
import com.chatak.merchant.model.FeatureResponse;
import com.chatak.pg.model.UserRolesDTO;

public interface RoleService {
	
	
	public List<UserRolesDTO> roleList(UserRolesDTO userRolesDTO)throws ChatakMerchantException;
	
	public FeatureResponse getFeature(Long roleId)throws ChatakMerchantException;
	

	public List<UserRolesDTO> getRoleList()throws ChatakMerchantException;
	
	public void deletRole(Long roleId)throws ChatakMerchantException;
	
	public Map<Long, List<String>> getFeatureList(List<UserRolesDTO> roleList);

} 
