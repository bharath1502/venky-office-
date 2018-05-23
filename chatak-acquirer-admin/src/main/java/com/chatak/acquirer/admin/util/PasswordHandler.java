package com.chatak.acquirer.admin.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.chatak.pg.util.Constants;

public final class PasswordHandler {

  private static Logger logger = Logger.getLogger(PasswordHandler.class);

  private static String PREFIX = "_crypt_chatak_";

  private static String SUFFIX = "M19A02N20V11I==";
  
  private static String ALPHA_CHARS =
	      "CK8A3BNEFV3AIC5K13RFKA3EJF6F9486M48507B5P5DFRK8A3BEF6F9486M48507B5P5DFK";

  /**
   * Hex decimal character array. Used in selecting the mapping for each four
   * binary bits
   */
  private static final char HEX[] =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
  
  private Pattern pattern;

  private Matcher matcher;

  private static final int HEX_CHAR_LENGTH = 4;

  private static final String TEMP_SECURE_WORD = "@@CHATAK_ACQ@@";

  private static final String PASS_WORD_PATTERN =
      "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@,£^*?€\"!$]).{8,16})";

  public PasswordHandler() {
    pattern = Pattern.compile(PASS_WORD_PATTERN);
  }
  
  /**
   * Method used to encrypt raw string format to encrypted format
   * 
   * @param str
   *          - raw string
   * @return encrypted string
   */
  public static String encrypt(String str) {
    String encrypted = PREFIX;

    if (str != null) {
      for (int k = 0; k < str.length(); k++) {
        encrypted += Character.toString(ALPHA_CHARS.charAt(k)) + Character.toString(str.charAt(k));
      }
    }
    encrypted += SUFFIX;
    return encrypted;
  }

  /**
   * Encode the byte array of binary data to hex decimal format
   * 
   * @param data
   *          : byte array
   * @return: Hex decimal string representing the bytes
   */
  private static String encodeHex(byte data[]) {
    int datalength = data.length;
    // multiply by 2 as every byte will be represented by two characters
    char out[] = new char[datalength * Constants.TWO];
    int j = 0;
    for (int i = 0; i < datalength; i++) {
      out[j++] = HEX[(0xf0 & data[i]) >>> HEX_CHAR_LENGTH];
      out[j++] = HEX[0xf & data[i]];
    }
    return new String(out);
  }
  
  /**
   * Validate password with regular expression
   * 
   * @param password
   *          password for validation
   * @return true valid password, false invalid password
   */
  public boolean validate(final String password) {

    matcher = pattern.matcher(password);
    return matcher.matches();

  }

  /**
   * Method to get System Generated Password
   * 
   * @return password
   */
  public static String getSystemGeneratedPassword(int length) {
    final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    StringBuilder sb = new StringBuilder(length);
    Random rnd = new Random();
    for (int i = 0; i < length; i++) {
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    }
    return sb.toString();
  }

  /**
   * Generate Random numbers for given length
   * 
   * @param length
   * @return
   */
  public static String generateRandomNumber(int length) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int n = 0; n < length; n++) {
      int j = (random.nextInt() % Constants.TEN);
      // If First digit is "0", skip that and get next random
      if (n == 0 && j == 0) {
        n--;
        continue;
      }
      sb.append(Integer.toString(j));
    }
    return sb.toString();
  }
  
  public static boolean isValidPassword(final String rawPassword, final String hashedPassword) {
    String pwsd = encodePassword(rawPassword);
    if (pwsd != null) {
      return pwsd.equals(hashedPassword);
    }
    return false;
  }
  
  /**
   * Encrypt the password string using MD5 encryption and return the Hex decimal
   * format of it
   * 
   * @param password
   * @return: MD5 encrypted password in Hex decimal format.
   * @throws Exception
   */
  public static String encodePassword(String password) {
    try {

      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      String tempPassword = TEMP_SECURE_WORD + password + TEMP_SECURE_WORD;
      byte[] md5Binary = messageDigest.digest(tempPassword.getBytes());
      String hexParam = encodeHex(md5Binary);
      return hexParam.toUpperCase();
    } catch (NoSuchAlgorithmException e) {
      logger.error("ERROR:: PasswordHandler::encodePassword ", e);
    }
    return null;
  }

}
