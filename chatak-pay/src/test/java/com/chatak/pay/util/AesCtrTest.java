package com.chatak.pay.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AesCtrTest {

	@InjectMocks
	AesCtr aesCtr;

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testEncrypt() {
		aesCtr.encrypt("123", "1234567890123456", Integer.parseInt("128"));
	}

	@Test(expected = CryptoException.class)
	public void testEncryptCryptoException() {
		aesCtr.encrypt("abcd", "abcd", 0);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testDecrypt() {
		aesCtr.decrypt("123", "1234567890123456", Integer.parseInt("128"));
	}

	@Test
	public void testDecryptCryptoException() {
		aesCtr.decrypt("abcd", "abcd", 0);
	}

}
