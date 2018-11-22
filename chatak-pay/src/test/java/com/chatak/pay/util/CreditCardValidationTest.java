package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.bean.BillingData;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardValidationTest {

	@InjectMocks
	CreditCardValidation creditCardValidation;

	@Test
	public void testIsValid() {
		creditCardValidation.isValid(Long.parseLong("3"));
	}

	@Test
	public void testIsValidElse() {
		creditCardValidation.isValid(Long.parseLong("657560"));
	}

	@Test
	public void testValidateBillingData() {
		BillingData billingData = new BillingData();
		billingData.setAddress1("a");
		billingData.setCity("b");
		billingData.setCountry("c");
		billingData.setState("a");
		billingData.setZipCode("b");
		billingData.setEmail("c");
		creditCardValidation.validateBillingData(billingData);
	}

	@Test
	public void testValidateBillingDataAddress() {
		BillingData billingData = new BillingData();
		billingData.setCity("b");
		creditCardValidation.validateBillingData(billingData);
	}

	@Test
	public void testValidateBillingDatatCity() {
		BillingData billingData = new BillingData();
		billingData.setAddress1("a");
		creditCardValidation.validateBillingData(billingData);
	}

	@Test
	public void testValidateBillingDatatCountry() {
		BillingData billingData = new BillingData();
		billingData.setAddress1("a");
		billingData.setCity("b");
		creditCardValidation.validateBillingData(billingData);
	}

	@Test
	public void testValidateBillingDatatState() {
		BillingData billingData = new BillingData();
		billingData.setAddress1("a");
		billingData.setCity("b");
		billingData.setCountry("c");
		creditCardValidation.validateBillingData(billingData);
	}

	@Test
	public void testValidateBillingDatatZipCode() {
		BillingData billingData = new BillingData();
		billingData.setAddress1("a");
		billingData.setCity("b");
		billingData.setCountry("c");
		billingData.setState("a");
		creditCardValidation.validateBillingData(billingData);
	}

	@Test
	public void testValidateBillingDatatEmail() {
		BillingData billingData = new BillingData();
		billingData.setAddress1("a");
		billingData.setCity("b");
		billingData.setCountry("c");
		billingData.setState("a");
		billingData.setZipCode("b");
		creditCardValidation.validateBillingData(billingData);
	}

	@Test
	public void testValidateBillingDataElse() {
		BillingData billingData = null;
		creditCardValidation.validateBillingData(billingData);
	}

}
