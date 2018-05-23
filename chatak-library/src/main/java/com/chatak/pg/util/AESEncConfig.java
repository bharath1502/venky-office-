package com.chatak.pg.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESEncConfig {
  
  private AESEncConfig() {
 // Do nothing
  }

	private static final String ALGO = "AES";


	private static final byte[] keyValue = 
			new byte[] {'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y', 'S', 'e', 'r', 'v', 'i', 'c', 'e'};


  public static String encrypt(String data) throws NoSuchAlgorithmException,
                                            NoSuchPaddingException,
                                            InvalidKeyException,
                                            IllegalBlockSizeException,
                                            BadPaddingException {
    Key key = generateKey();
    Cipher c = Cipher.getInstance(ALGO);
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[] encVal = c.doFinal(data.getBytes());
    String encryptedValue = new BASE64Encoder().encode(encVal);
    return encryptedValue;
  }

  public static String decrypt(String encryptedData) throws NoSuchAlgorithmException,
                                                     NoSuchPaddingException,
                                                     InvalidKeyException,
                                                     IOException,
                                                     IllegalBlockSizeException,
                                                     BadPaddingException {
    Key key = generateKey();
    Cipher c = Cipher.getInstance(ALGO);
    c.init(Cipher.DECRYPT_MODE, key);
    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
    byte[] decValue = c.doFinal(decordedValue);
    String decryptedValue = new String(decValue);
    return decryptedValue;
  }
	private static Key generateKey() {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}

}
