package com.chatak.pg.common;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.jpos.iso.ISOUtil;

/**
 *  This is an utility class to encoded the password
 */
public class PasswordEncoder {

  public PasswordEncoder() {
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
   * This method used for encrypted password.
   * 
   * @param message
   * @return byte[]
   * @throws Exception
   */
  public static String encrypt(String message) throws Exception {
    final MessageDigest md = MessageDigest.getInstance("MD5");
    final byte[] digestOfPassword = md.digest();
    final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
    for(int j = 0, k = 16; j < 8;) {
      keyBytes[k++] = keyBytes[j++];
    }

    final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
    final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);

    final byte[] plainTextBytes = message.getBytes("utf-8");
    final byte[] cipherText = cipher.doFinal(plainTextBytes);
    return ISOUtil.hexString(cipherText);
  }

  /**
   * This method used for decrypted the encrypted password.
   * 
   * @param message
   * @return String
   * @throws Exception
   */
  public static String decrypt(String message) throws Exception {
    final MessageDigest md = MessageDigest.getInstance("MD5");
    final byte[] digestOfPassword = md.digest();
    final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
    for(int j = 0, k = 16; j < 8;) {
      keyBytes[k++] = keyBytes[j++];
    }

    final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
    final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
    final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
    decipher.init(Cipher.DECRYPT_MODE, key, iv);

    final byte[] plainText = decipher.doFinal(ISOUtil.hex2byte(message));
    return new String(plainText, "UTF-8");
  }

  public static void main(String arg[]) throws Exception {
    System.out.println("MDS(DESede) Encrypt : " + PasswordEncoder.encrypt(""));
    System.out.println("MDS(DESede) Decrypt : " + PasswordEncoder.decrypt("CF5C6A2A2E626A57"));
  }
}
