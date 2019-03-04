package com.chatak.pg.acq.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FeatureMappingDao;
import com.chatak.pg.acq.dao.model.PGRolesFeatureMapping;
import com.chatak.pg.acq.dao.repository.FeatureMappingRepository;
import com.chatak.pg.user.bean.FeatureData;
import com.chatak.pg.util.CommonUtil;


@Repository("featureMappingDao")
public class FeatureMappingDaoImpl implements FeatureMappingDao
{
	@Autowired
    FeatureMappingRepository featureMappingRepository;
    
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public void saveFeatureMapping(PGRolesFeatureMapping pGRolesFeatureMapping) {
		featureMappingRepository.save(pGRolesFeatureMapping);
		
	}

	@Override
	public void deleteFeatureMapping(Long roleId) {
	List<PGRolesFeatureMapping> pGRolesFeatureMappingList = featureMappingRepository.findByRoleId(roleId);
	if(CommonUtil.isListNotNullAndEmpty(pGRolesFeatureMappingList)) {
	   featureMappingRepository.deleteInBatch(pGRolesFeatureMappingList);
	  }
	}

	@Override
	public List<FeatureData> getFeatureData(Long roleId)
	{
		return Collections.emptyList();
	}

	

}
