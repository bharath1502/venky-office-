/**
 * 
 */
package com.chatak.pg.util.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 25-May-2015 9:46:19 AM
 * @version 1.0
 */
public class AESEncryption implements ChatakEncryption {

  public AESEncryption() {
    // need to implement based on requirement
  }

  /**
   * Method encrypt the given value with AES-128. Base64 encode value will
   * return
   * 
   * @param toEncrypt
   * @param saltKey
   * @return
   */
  public String encrypt(String toEncrypt, String saltKey) {
    try {
      Cipher c = getCipher(Cipher.ENCRYPT_MODE, saltKey);
      byte[] encryptedVal = c.doFinal(getBytes(toEncrypt));
      String s = getString(Base64.encodeBase64(encryptedVal));
      return s;
    }
    catch(Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Method decrypt the given AES-128 encrypted value. Base64 decode will be
   * done.
   * 
   * @param toDecrypt
   * @param saltKey
   * @return
   * @throws UnsupportedEncodingException 
   * @throws InvalidKeySpecException 
   * @throws InvalidAlgorithmParameterException 
   * @throws NoSuchPaddingException 
   * @throws NoSuchAlgorithmException 
   * @throws InvalidKeyException 
   * @throws BadPaddingException 
   * @throws IllegalBlockSizeException 
   * @throws Exception
   */
  public String decrypt(String toDecrypt, String saltKey) throws UnsupportedEncodingException,
                                                          InvalidKeyException,
                                                          NoSuchAlgorithmException,
                                                          NoSuchPaddingException,
                                                          InvalidAlgorithmParameterException,
                                                          InvalidKeySpecException,
                                                          IllegalBlockSizeException,
                                                          BadPaddingException {
    byte[] decodedValue = Base64.decodeBase64(getBytes(toDecrypt));
    Cipher c = getCipher(Cipher.DECRYPT_MODE, saltKey);
    byte[] decValue = c.doFinal(decodedValue);
    return new String(decValue);
  }

  private String getString(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, CHAR_SET_UTF_8);
  }

  /**
   * Method to get Bytes from String with UTF-8
   * 
   * @param str
   * @return
   * @throws UnsupportedEncodingException
   */
  private byte[] getBytes(String str) throws UnsupportedEncodingException {
    return str.getBytes(CHAR_SET_UTF_8);
  }

  /**
   * Method to get Cipher with AES
   * 
   * @param mode
   * @param saltKey
   * @return
   * @throws NoSuchPaddingException 
   * @throws NoSuchAlgorithmException 
   * @throws UnsupportedEncodingException 
   * @throws InvalidKeySpecException 
   * @throws InvalidAlgorithmParameterException 
   * @throws InvalidKeyException 
   * @throws Exception
   */
  public Cipher getCipher(int mode, String saltKey) throws NoSuchAlgorithmException,
                                                    NoSuchPaddingException,
                                                    UnsupportedEncodingException,
                                                    InvalidKeyException,
                                                    InvalidAlgorithmParameterException,
                                                    InvalidKeySpecException {
    Cipher c = Cipher.getInstance(ALG_CBC_PAD_AES);
    byte[] iv = getBytes(PASSWORD);
    c.init(mode, generateKey(saltKey), new IvParameterSpec(iv));
    return c;
  }

  /**
   * Method to generate Key with SALT and Password
   * 
   * @param saltKey
   * @return
   * @throws NoSuchAlgorithmException 
   * @throws UnsupportedEncodingException 
   * @throws InvalidKeySpecException 
   * @throws Exception
   */
  private Key generateKey(String saltKey) throws NoSuchAlgorithmException,
                                          UnsupportedEncodingException,
                                          InvalidKeySpecException {
    SecretKeyFactory factory = SecretKeyFactory.getInstance(HMAC_SHA);
    char[] passwordChar = PASSWORD.toCharArray();
    byte[] salt = getBytes(saltKey);

    KeySpec spec = new PBEKeySpec(passwordChar, salt, Integer.parseInt("65536"), Integer.parseInt("128"));
    SecretKey tmp = factory.generateSecret(spec);
    byte[] encoded = tmp.getEncoded();
    return new SecretKeySpec(encoded, ALG_TYPE_AES);
  }
}
