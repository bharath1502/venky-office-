package com.chatak.merchant.util;

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
		byte[] data = { Byte.parseByte("3"), Byte.parseByte("2") };
		hex.encodeHex(data);
	}

	@Test
	public void testDecodeHex() {
		char[] data = { 'a', 'b' };
		hex.decodeHex(data);
	}

	@Test
	public void testDecodeHexException() {
		char[] data = {'a', 'b'};
		hex.decodeHex(data);
	}

	@Test
	public void testDecodeHexString() {
	  char[] data = {'a','b'};
      hex.decodeHex(data);
	}

	@Test
	public void testAsciiToBinary() {
		hex.asciiToBinary("ab");
	}

	@Test
	public void testAsciiToBinaryByte() {
    byte[] buffer = {'a', 'b'};
		hex.asciiToBinary(buffer);
	}

	@Test(expected = NumberFormatException.class)
	public void testAsciiToHex() {
		byte b = Byte.parseByte("10");
		hex.asciiToHex(b);
	}

}
