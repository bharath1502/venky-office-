package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Map;

import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.model.FeatureDTO;
import com.chatak.pg.model.UserRolesDTO;

public interface RolesFeatureMappingDao
{
	public void saveRoleFeatureMapping(PGRolesFeatureMapping pgRolesFeatureMapping);
	
	public List<PGRolesFeatureMapping> getRolesFeature(Long roleId);
	
	public void deleteRolesFeatureMApping(Long roleID);
	
	public Map<Long, List<String>> getFeatureList(List<UserRolesDTO> roleList);

	/**
	 * @param adminFeatureRequest
	 * @return
	 */
	public List<PGRolesFeatureMapping> getAdminRoleFeaturesList(FeatureDTO featureDTO);

}
