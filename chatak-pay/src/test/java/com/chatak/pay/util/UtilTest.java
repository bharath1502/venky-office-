package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UtilTest {

	@InjectMocks
	Util util;

	@Test
	public void testAreEqual() {
		byte[] first = { 1, 1, 1 };
		byte[] second = { 1, 1 };
		util.areEqual(first, second);
	}

	@Test
	public void testAreEqualElse() {
		byte[] first = { 1, 1, 1 };
		util.areEqual(first, first);
	}

	@Test
	public void testaddByteArrays() {
		byte[] first = { 1, 1, 1 };
		byte[] second = { 1, 1 };
		util.addByteArrays(first, second);
	}

}
