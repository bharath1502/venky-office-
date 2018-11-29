package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HexTest {

	@InjectMocks
	Hex hex;

	@Test(expected = IllegalArgumentException.class)
	public void testGeneratePin() {
		char[] data = { 'a', 'b', 'c' };
		hex.decodeHex(data);
	}

	@Test
	public void testGeneratePinElse() {
		char[] data = { 'a', 'b' };
		hex.decodeHex(data);
	}

	@Test
	public void testEncodeHex() {
		byte[] data = { '1', '0', '1' };
		hex.encodeHex(data);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeHex() {
		hex.decodeHex("1");
	}

	@Test
	public void testAsciiToBinary() {
		byte[] data = { '1', '0' };
		hex.asciiToBinary(data);
	}

	@Test
	public void testAsciiToBinaryString() {
		hex.asciiToBinary("21");
	}

	@Test
	public void testAsciiToHexD() {
		byte b = Byte.parseByte("55");
		hex.asciiToHex(b);
	}

	@Test
	public void testAsciiToHexA() {
		byte b = Byte.parseByte("65");
		hex.asciiToHex(b);
	}

	@Test
	public void testAsciiToHexValue() {
		byte b = Byte.parseByte("97");
		hex.asciiToHex(b);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAsciiToHexException() {
		byte b = 1;
		hex.asciiToHex(b);
	}

	@Test
	public void testAsciiToBinaryByte() {
		byte b = Byte.parseByte("97");
		hex.asciiToBinary(b, b);
	}
}
