package com.chatak.merchant.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UtilTest {

	@InjectMocks
	Util util;

	@Test
	public void testAddByteArrays() {
		byte first[] = { 1, 1 };
		byte second[] = { 1, 0 };
		util.addByteArrays(first, second);
	}

	@Test
	public void testAreEqual() {
		byte first[] = { 1, 1 };
		byte second[] = { 1, 0 };
		util.areEqual(first, second);
	}

}
