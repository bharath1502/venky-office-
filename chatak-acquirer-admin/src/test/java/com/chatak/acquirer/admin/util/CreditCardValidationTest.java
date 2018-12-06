package com.chatak.acquirer.admin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@SuppressWarnings({"static-access"})
@RunWith(MockitoJUnitRunner.class)
public class CreditCardValidationTest {

	@InjectMocks
	CreditCardValidation creditCardValidation;

	@Test
	public void testIsValid() {
		creditCardValidation.isValid(1);
	}

	@Test
	public void testGetDigit() {
		creditCardValidation.getDigit(1);
	}

	@Test
	public void testGetDigitElse() {
		creditCardValidation.getDigit(Integer.parseInt("40"));
	}

	@Test
	public void testSumOfOddPlace() {
		creditCardValidation.sumOfOddPlace(1);
	}

	@Test
	public void testSumOfDoubleEvenPlace() {
		creditCardValidation.sumOfDoubleEvenPlace(1);
	}

	@Test
	public void testPrefixMatchedThree() {
		creditCardValidation.prefixMatched(Long.parseLong("3"), Integer.parseInt("3"));
	}

	@Test
	public void testPrefixMatchedFive() {
		creditCardValidation.prefixMatched(Long.parseLong("5"), Integer.parseInt("3"));
	}

	@Test
	public void testPrefixMatchedSix() {
		creditCardValidation.prefixMatched(Long.parseLong("6"), Integer.parseInt("3"));
	}

	@Test
	public void testPrefixMatchedFour() {
		creditCardValidation.prefixMatched(Long.parseLong("4"), Integer.parseInt("4"));
	}

	@Test
	public void testPrefixMatchedElse() {
		creditCardValidation.prefixMatched(Long.parseLong("8"), Integer.parseInt("8"));
	}

	@Test
	public void testGetPrefix() {
		creditCardValidation.getPrefix(Long.parseLong("1"), Integer.parseInt("0"));
	}

}
