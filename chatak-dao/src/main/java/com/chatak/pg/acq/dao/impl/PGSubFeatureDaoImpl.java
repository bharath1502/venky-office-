package com.chatak.pg.acq.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.SubFeatureDao;
import com.chatak.pg.acq.dao.model.PGSubFeature;
import com.chatak.pg.acq.dao.repository.SubFeatureRepository;
import com.chatak.pg.dao.util.StringUtil;

@Repository("subFeatureDao")

public class PGSubFeatureDaoImpl implements SubFeatureDao
{

	@Autowired
	SubFeatureRepository subFeatureRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<PGSubFeature> getSubFeatureList() {
	List<PGSubFeature> subFeatureList = subFeatureRepository.findAll();
		// TODO Auto-generated method stub
		return (StringUtil.isListNotNullNEmpty(subFeatureList))?subFeatureList:null;
	}
	
	
	
	

}
