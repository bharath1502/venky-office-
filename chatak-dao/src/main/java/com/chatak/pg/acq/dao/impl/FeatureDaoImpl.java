package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.FeatureDao;
import com.chatak.pg.acq.dao.model.PGFeatures;
import com.chatak.pg.acq.dao.repository.FeatureRepository;
import com.chatak.pg.util.StringUtils;

@Repository("featureDao")
public class FeatureDaoImpl implements FeatureDao {
  @Autowired
  FeatureRepository featureRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<PGFeatures> getFeatureList() {
    List<PGFeatures> featureList = featureRepository.findAll();
    return (StringUtils.isListNotNullNEmpty(featureList)) ? featureList : null;
  }

}
