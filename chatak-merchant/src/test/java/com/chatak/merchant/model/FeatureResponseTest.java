package com.chatak.merchant.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class FeatureResponseTest {

	@InjectMocks
	FeatureResponse featureResponse = new FeatureResponse();

	@Mock
	List<String> feature;

	@Mock
	List<String> subFeature;

	@Before
	public void setUp() {
		featureResponse.setFeature(feature);
		featureResponse.setSubFeature(subFeature);

	}

	@Test
	public void testFeatureResponse() {
		Assert.assertEquals(feature, featureResponse.getFeature());
		Assert.assertEquals(subFeature, featureResponse.getSubFeature());

	}

}
