package com.chatak.merchant.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.chatak.pg.util.Constants;

/**
 *  This is an utility class to encode and decode
 */
public class EncryptionUtil {

  private EncryptionUtil() {
    super();
  }

  /**
   * Encrypt the password string using MD5 encryption and return the Hex decimal
   * format of it
   * 
   * @param password
   * @return: MD5 encrypted password in Hex decimal format.
   * @throws Exception
   */
  public static String encodePassword(String password) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    byte[] md5Binary = messageDigest.digest(password.getBytes());
    String hexParam = Hex.encodeHex(md5Binary);
    return hexParam.toUpperCase();
  }

  /**
   * This method used for generate the random password pin.
   * 
   * @param length
   * @return String - random pin
   */
  public static String generatePin(int length) {
    String charString = "0123456789";
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder(length);
    for(int i = 0; i < length; i++) {
      sb.append(charString.charAt(rnd.nextInt(charString.length())));
    }
    return sb.toString();
  }
  
  /**
   * This method used for generate the alpha numeric password based on length.
   * 
   * @param length
   * @return String
   */
  public static String generatePassword(int length) {
    String charString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder(length);
    for(int i = 0; i < length; i++) {
      sb.append(charString.charAt(rnd.nextInt(charString.length())));
    }
    return sb.toString();
  }
  
  /**
   * This method generate the random numeric value based on length
   * 
   * @param length
   * @return String
   */
  public static String generateRandNumeric(int length) {
    String finalRandString = "";
    Random randomObj = new Random();
    for(int j = 0; j < length; j++) {
      int rand_int = randomObj.nextInt(Constants.SEVENTYTWO);
      finalRandString += Integer.toString(rand_int);
      if(finalRandString.length() >= length) {
        finalRandString = finalRandString.substring(0, length);
        break;
      }
    }
    return finalRandString;
  }

  /**
   * @param   b       source byte array
   * @param   offset  starting offset
   * @param   len     number of bytes in destination (processes len*2)
   * @return  byte[len]
   */
  public static byte[] hex2byte (byte[] b, int offset, int len) {
      byte[] d = new byte[len];
      for (int i=0; i<len*Constants.TWO; i++) {
          int shift = i%Constants.TWO == 1 ? 0 : Constants.FOUR;
          d[i>>1] |= Character.digit((char) b[offset+i], Constants.SIXTEEN) << shift;
      }
      return d;
  }
  /**
   * @param s source string (with Hex representation)
   * @return byte array
   */
  public static byte[] hex2byte (String s) {
      if (s.length() % Constants.TWO == 0) {
          return hex2byte (s.getBytes(), 0, s.length() >> 1);
      } else {
      	// Padding left zero to make it even size #Bug raised by tommy
      	return hex2byte("0"+s);
      }
  }
  
  /**
   * converts a byte array to hex string 
   * (suitable for dumps and ASCII packaging of Binary fields
   * @param b - byte array
   * @return String representation
   */
  public static String hexString(byte[] b) {
    StringBuilder d = new StringBuilder(b.length * Constants.TWO);
      for (int i=0; i<b.length; i++) {
          char hi = Character.forDigit ((b[i] >> Constants.FOUR) & 0x0F, Constants.SIXTEEN);
          char lo = Character.forDigit (b[i] & 0x0F, Constants.SIXTEEN);
          d.append(Character.toUpperCase(hi));
          d.append(Character.toUpperCase(lo));
      }
      return d.toString();
  }

}