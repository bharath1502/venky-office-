package com.chatak.merchant.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.chatak.merchant.controller.model.Option;

public class GetFraudDetailsResponseTest {

	@InjectMocks
	GetFraudDetailsResponse getFraudDetailsResponse = new GetFraudDetailsResponse();

	@Mock
	private List<String> iPMultiple;
	@Mock
	private List<String> binMultiple;
	@Mock
	private List<String> eMailMultiple;
	@Mock
	private List<String> countryMultiple;
	@Mock
	private List<Option> isoCountryList;

	@Before
	public void setUp() {
		getFraudDetailsResponse.setiPMultiple(iPMultiple);
		getFraudDetailsResponse.setBinMultiple(binMultiple);
		getFraudDetailsResponse.seteMailMultiple(eMailMultiple);
		getFraudDetailsResponse.setCountryMultiple(countryMultiple);
		getFraudDetailsResponse.setIsoCountryList(isoCountryList);

	}

	@Test
	public void testGetFraudDetailsResponse() {
		Assert.assertEquals(iPMultiple, getFraudDetailsResponse.getiPMultiple());
		Assert.assertEquals(binMultiple, getFraudDetailsResponse.getBinMultiple());
		Assert.assertEquals(eMailMultiple, getFraudDetailsResponse.geteMailMultiple());
		Assert.assertEquals(countryMultiple, getFraudDetailsResponse.getCountryMultiple());
		Assert.assertEquals(isoCountryList, getFraudDetailsResponse.getIsoCountryList());

	}
}
