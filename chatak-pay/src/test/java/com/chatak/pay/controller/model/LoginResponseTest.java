package com.chatak.pay.controller.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pay.model.TSMResponse;
import com.chatak.pg.model.CurrencyDTO;

@RunWith(MockitoJUnitRunner.class)
public class LoginResponseTest {
	
	@InjectMocks
	LoginResponse loginResponse = new LoginResponse();
	
	@Mock
	List<String> existingFeature;
	
	@Mock
	CurrencyDTO currencyDTO;
	
	@Mock
	TSMResponse terminalData;
	

	@Before
	public void setUp() {
		loginResponse.setStatus(true);
		loginResponse.setUserId(Long.parseLong("45"));
		loginResponse.setMessage("23");
		loginResponse.setEmail("23");
		loginResponse.setAccessToken("23");
		loginResponse.setSubServiceProviderId(Long.parseLong("45"));
		loginResponse.setServiceProviderId(Long.parseLong("45"));
		loginResponse.setExistingFeature(existingFeature);
		loginResponse.setUserType("23");
		loginResponse.setUserRoleId(Long.parseLong("45"));
		loginResponse.setMakerCheckerRequired(true);
		loginResponse.setMerchantCode("23");
		loginResponse.setTerminalID("23");
		loginResponse.setBussinessName("23");
		loginResponse.setCurrencyDTO(currencyDTO);
		loginResponse.setTerminalData(terminalData);
		loginResponse.setAddress("23");
		loginResponse.setCity("23");
		loginResponse.setState("23");
		loginResponse.setCountry("23");
		loginResponse.setPin("23");
	}

	@Test
	public void testLoginResponse() {
		Assert.assertEquals(true, loginResponse.getStatus());
		Assert.assertEquals(Long.valueOf("45"), loginResponse.getUserId());
		Assert.assertEquals("23", loginResponse.getMessage());
		Assert.assertEquals("23", loginResponse.getEmail());
		Assert.assertEquals("23", loginResponse.getAccessToken());
		Assert.assertEquals(Long.valueOf("45"), loginResponse.getSubServiceProviderId());
		Assert.assertEquals(Long.valueOf("45"), loginResponse.getServiceProviderId());
		Assert.assertEquals(existingFeature, loginResponse.getExistingFeature());
		Assert.assertEquals("23", loginResponse.getUserType());
		Assert.assertEquals(Long.valueOf("45"), loginResponse.getUserRoleId());
		Assert.assertEquals(true, loginResponse.getMakerCheckerRequired());
		Assert.assertEquals("23", loginResponse.getMerchantCode());
		Assert.assertEquals("23", loginResponse.getTerminalID());
		Assert.assertEquals("23", loginResponse.getBussinessName());
		Assert.assertEquals(currencyDTO, loginResponse.getCurrencyDTO());
		Assert.assertEquals(terminalData, loginResponse.getTerminalData());
		Assert.assertEquals("23", loginResponse.getAddress());
		Assert.assertEquals("23", loginResponse.getCity());
		Assert.assertEquals("23", loginResponse.getState());
		Assert.assertEquals("23", loginResponse.getCountry());
		Assert.assertEquals("23", loginResponse.getPin());
	}

}
