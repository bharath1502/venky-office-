package com.chatak.acquirer.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.service.impl.FeatureServiceImpl;
import com.chatak.pg.acq.dao.FeatureDao;
import com.chatak.pg.acq.dao.SubFeatureDao;
import com.chatak.pg.acq.dao.model.PGFeatures;
import com.chatak.pg.acq.dao.model.PGSubFeature;

@RunWith(MockitoJUnitRunner.class)
public class FeatureServiceImplTest {

	@InjectMocks
	FeatureServiceImpl featureServiceImpl = new FeatureServiceImpl();
	
	@Mock
	  FeatureDao featureDao;

	@Mock
	  SubFeatureDao subFeatureDao;


	@Test
	public void testGetFeature() throws ChatakAdminException {
		List<PGFeatures> featureList =new ArrayList<>();
		PGFeatures features=new PGFeatures();
		featureList.add(features);
		List<PGSubFeature> subFeatureList=new ArrayList<>();
		PGSubFeature subFeature=new PGSubFeature();
		subFeatureList.add(subFeature);
		Mockito.when(featureDao.getFeatureList()).thenReturn(featureList);
		Mockito.when(subFeatureDao.getSubFeatureList()).thenReturn(subFeatureList);
		featureServiceImpl.getFeature();
	}
	
	@Test(expected=ChatakAdminException.class)
	public void testGetFeatureException() throws ChatakAdminException {
		Mockito.when(featureDao.getFeatureList()).thenThrow(new NullPointerException());
		featureServiceImpl.getFeature();
	}

}
