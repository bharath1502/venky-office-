package com.chatak.acquirer.admin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.log4j.Logger;

import com.chatak.pg.util.Constants;

/**
 *  This is an utility class to encode and decode
 */
public class EncryptionUtil {

   private static Logger logger = Logger.getLogger(EncryptionUtil.class);
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
  
  EncryptionUtil() {
  }
  
  /**
   * This method used for generate the alpha numeric password based on length.
   * 
   * @param length
   * @return String
   * @throws NoSuchAlgorithmException 
   */
  public static String generatePassword(int length) throws NoSuchAlgorithmException {
    String charString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    SecureRandom rnd = new SecureRandom();
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
   * @throws NoSuchAlgorithmException 
   */
  public static String generateRandNumeric(int length) throws NoSuchAlgorithmException {
	 String finalRandString = "";
	 SecureRandom randomObj = new SecureRandom();
    for(int j = 0; j < length; j++) {
      int randInt = randomObj.nextInt(Constants.SEVENTYTWO);
      finalRandString += Integer.toString(randInt);
      if(finalRandString.length() >= length) {
        finalRandString = finalRandString.substring(0, length);
        break;
      }
    }
    return finalRandString;
  }

  /**
   * This method used for generate the random password pin.
   * 
   * @param length
   * @return String - random pin
   * @throws NoSuchAlgorithmException 
   */
  public static String generatePin(int length) throws NoSuchAlgorithmException {
    String charString = "0123456789";
    SecureRandom rnd = new SecureRandom();
    StringBuilder sb = new StringBuilder(length);
    for(int i = 0; i < length; i++) {
      sb.append(charString.charAt(rnd.nextInt(charString.length())));
    }
    return sb.toString();
  }
  
  /**
   * @param   b       source byte array
   * @param   offset  starting offset
   * @param   len     number of bytes in destination (processes len*)
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
  
  public static void main(String arg[]) throws Exception {
    logger.info(encodePassword("Girmiti@1234"));
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
