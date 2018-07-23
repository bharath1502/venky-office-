package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.user.bean.FeatureData;



public interface FeatureMappingDao 
{
    public void saveFeatureMapping(PGRolesFeatureMapping pGRolesFeatureMapping);
    
    public void deleteFeatureMapping(Long roleId);
    
    public List<FeatureData> getFeatureData(Long roleId);
}
