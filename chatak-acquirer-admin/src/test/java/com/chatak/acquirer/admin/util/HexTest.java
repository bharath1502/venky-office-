package com.chatak.acquirer.admin.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.util.Constants;
import com.chatak.pg.util.Properties;

@SuppressWarnings("static-access")
@RunWith(MockitoJUnitRunner.class)
public class HexTest {

	@InjectMocks
	Hex hex;
	
	@Mock
	Constants constants;
	
	@Before
	public void init() {
		java.util.Properties propsExportedLocal = new java.util.Properties();
		propsExportedLocal.setProperty("max.download.limit", "12");
		Properties.mergeProperties(propsExportedLocal);
	}
	
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

	@Test
	public void testDecodeHexException() {
		char data[] = { '1', '1', '0','2' };
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

	@Test
	public void testAsciiToHexCaseException() {
		hex.asciiToHex(Byte.parseByte("98"));
	}

	@Test
	public void testAsciiToBinaryByte() {
		byte[] buffer = { Byte.parseByte("13"), Byte.parseByte("98"), Byte.parseByte("99") };
		hex.asciiToBinary(buffer);
	}

}
