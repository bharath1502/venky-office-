/**
 * 
 */
package com.chatak.pg.util.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.chatak.pg.util.Properties;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 25-May-2015 9:45:24 AM
 * @version 1.0
 */
public interface ChatakEncryption {
  
  String ALG_TYPE_3DES = "DESede";
  String ALG_TYPE_HASH_SHA_256 = "SHA-256";
  String ALG_TYPE_AES = "AES";
  String ALG_CBC_PAD_AES = "AES/CBC/PKCS5Padding";
  String ALG_PROVIDER_BC = "SunJCE";
  String KEYSTORE_TYPE = "JCEKS";
  String HMAC_SHA = "PBKDF2WithHmacSHA1";
  String PASSWORD = Properties.getProperty("chatak.pay.salt.pass");//"C1t1K@0219#20150"////
  String CHAR_SET_UTF_8 = "UTF-8";

  public String encrypt(String raw, String saltKey);

  public String decrypt(String raw, String saltKey) throws UnsupportedEncodingException,
                                                    InvalidKeyException,
                                                    NoSuchAlgorithmException,
                                                    NoSuchPaddingException,
                                                    InvalidAlgorithmParameterException,
                                                    InvalidKeySpecException,
                                                    IllegalBlockSizeException,
                                                    BadPaddingException;
  
  public Cipher getCipher(int operationMode, String key) throws NoSuchAlgorithmException,
                                                         NoSuchPaddingException,
                                                         UnsupportedEncodingException,
                                                         InvalidKeyException,
                                                         InvalidAlgorithmParameterException,
                                                         InvalidKeySpecException;
}
