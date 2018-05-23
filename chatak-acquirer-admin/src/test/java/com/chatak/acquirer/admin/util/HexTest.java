package com.chatak.acquirer.admin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HexTest {

	@InjectMocks
	Hex hex;

	@Test
	public void testEncodeHex() {
		byte data[] = { 1, 0, 1 };
		hex.encodeHex(data);
	}

	@Test
	public void testDecodeHex() {
		char data[] = { '1', '1', '1', '1' };
		hex.decodeHex(data);
	}

	@Test(expected=NumberFormatException.class)
	public void testDecodeHexException() {
		char data[] = { '1', '1', '0' };
		hex.decodeHex(data);
	}

	@Test
	public void testAsciiToBinary() {
		hex.asciiToBinary("10");
	}

	@Test
	public void testDecodeHexString() {
		hex.decodeHex("10");
	}

	@Test
	public void testAsciiToHexCase1() {
		hex.asciiToHex(Byte.parseByte("54"));
	}

	@Test
	public void testAsciiToHexCase2() {
		hex.asciiToHex(Byte.parseByte("67"));
	}

	@Test
	public void testAsciiToHexCase3() {
		hex.asciiToHex(Byte.parseByte("97"));
	}

	@Test(expected=NumberFormatException.class)
	public void testAsciiToHexCaseException() {
		hex.asciiToHex(Byte.parseByte("34"));
	}

	@Test(expected=NumberFormatException.class)
	public void testAsciiToBinaryByte() {
		byte[] buffer = { Byte.parseByte("13"), Byte.parseByte("43"), Byte.parseByte("5") };
		hex.asciiToBinary(buffer);
	}

}
