package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RijndaelTest {

	@InjectMocks
	Rijndael rijndael;

	@Test
	public void testMakeKey() {
		byte[] cipherKey = { 1, 1, 1, 1 };
		rijndael.makeKey(cipherKey, Integer.parseInt("256"), 0);
	}

	@Test(expected = CryptoException.class)
	public void testMakeKeys() {
		byte[] cipherKey = { 1, 1 };
		rijndael.makeKey(cipherKey, Integer.parseInt("3"));
	}

	@Test(expected = NullPointerException.class)
	public void testEncryptBlock() {
		byte[] pt = { Byte.parseByte("45"), Byte.parseByte("54"), 1, 1, 1, 1, 1 };
		byte[] ct = { 1, 1, 1, 1, 1, 1, 1, 1 };
		rijndael.encryptBlock(pt, ct);
	}

	@Test(expected = NullPointerException.class)
	public void testDecryptBlock() {
		byte[] ct = { Byte.parseByte("2"), Byte.parseByte("2"), Byte.parseByte("4"), Byte.parseByte("4") };
		byte[] pt = { Byte.parseByte("5"), Byte.parseByte("4"), Byte.parseByte("3"), Byte.parseByte("2"), 1 };
		rijndael.encryptBlock(pt, ct);

	}

}
