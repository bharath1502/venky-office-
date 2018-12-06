package com.chatak.pg.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

/**
 * @author Kumar This is an utility class to Encrypt and Decrypt String
 */
public class EncryptionUtil {
  
  private static Logger logger = LogManager.getLogger(EncryptionUtil.class);

	private static final String ALGO_MD5 = "MD5";

	private static final String CHARSET_UTF8 = "UTF-8";

	private static final String DES_ALGO = "DESede/CBC/PKCS5Padding";

	private static final String DES_KEY = "DESede";

	private static byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B,
			0x0D, 0x11 };

  private EncryptionUtil() {
  }

	

	/**
	 * This method used for generate the alpha numeric password based on length.
	 * 
	 * @param length
	 * @return String
	 * @throws NoSuchAlgorithmException 
	 */
	public static String generatePassword(int length) {
		String charString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
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

	  return CommonUtil.generateRandNumeric(length);
	}
	
	/**
   * This method used for generate the random password pin.
   * 
   * @param length
   * @return String - random pin
   */
  public static String generatePin(int length) {
    String charString = "0123456789";
    StringBuilder sb = new StringBuilder(length);
    try {
      SecureRandom rnd = new SecureRandom();
      for (int i = 0; i < length; i++) {
        sb.append(charString.charAt(rnd.nextInt(charString.length())));
      }
    }
    catch(Exception e) {
      logger.error("Error :: EncryptionUtil :: generatePin", e.getMessage());
    }
    return sb.toString();
  }

	/**
	 * This method used for encrypted password.
	 * 
	 * @param message
	 * @return byte[]
	 * @throws 
	 */
  public static String encrypt(String message) {
    try {
      return AESEncConfig.encrypt(message);
    } catch(InvalidKeyException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp1 ", e);
    } catch(NoSuchAlgorithmException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp2 ", e);
    } catch(NoSuchPaddingException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp3 ", e);
    } catch(IllegalBlockSizeException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp4 ", e);
    } catch(BadPaddingException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp5 ", e);
    }
    return null;
  }

	/**
	 * This method used for decrypted the encrypted password.
	 * 
	 * @param message
	 * @return String
	 * @throws 
	 */
  public static String decrypt(String message) {
    try {
      return AESEncConfig.decrypt(message);
    } catch(InvalidKeyException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp1 ", e);
    } catch(IOException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp2 ", e);
    } catch(BadPaddingException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp3 ", e);
    } catch(NoSuchAlgorithmException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp4 ", e);
    } catch(IllegalBlockSizeException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp5 ", e);
    } catch(NoSuchPaddingException e) {
      logger.error("ERROR:: EncryptionUtil::getStartDayTimestamp6 ", e);
    }
    return null;
  }

	/**
	 * This method used for encrypted password.
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
  public static String encrypt(String message, String key) throws UnsupportedEncodingException,
                                                           NoSuchAlgorithmException,
                                                           NoSuchPaddingException,
                                                           InvalidKeyException,
                                                           InvalidAlgorithmParameterException,
                                                           IllegalBlockSizeException,
                                                           BadPaddingException {
		String encText = "";
		byte[] keyArray = new byte[Integer.parseInt("24")];
		byte[] temporaryKey;
		byte[] toEncryptArray = null;
		toEncryptArray = message.getBytes(CHARSET_UTF8);
		MessageDigest m = MessageDigest.getInstance(ALGO_MD5);
		temporaryKey = m.digest(key.getBytes(CHARSET_UTF8));

		if (temporaryKey.length < Integer.parseInt("24")) // DESede require 24 byte length key
		{
			int index = 0;
			for (int i = temporaryKey.length; i < Integer.parseInt("24"); i++) {
				keyArray[i] = temporaryKey[index];
			}
		}

		Cipher c = Cipher.getInstance(DES_ALGO);
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, DES_KEY),
				new IvParameterSpec(sharedvector));
		byte[] encrypted = c.doFinal(toEncryptArray);
		encText = new String(Base64.encode(encrypted));

		return HexConverterUtil.stringToHex(encText);
	}

	/**
	 * This method used for decrypted the encrypted password.
	 * 
	 * @param message
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
  public static String decrypt(String message, String key) throws NoSuchAlgorithmException,
                                                           InvalidKeyException,
                                                           InvalidAlgorithmParameterException,
                                                           NoSuchPaddingException,
                                                           IllegalBlockSizeException,
                                                           BadPaddingException,
                                                           UnsupportedEncodingException {

		String rawText = "";
		byte[] keyArray = new byte[Integer.parseInt("24")];
		byte[] temporaryKey;
		message = HexConverterUtil.hexToString(message);
		MessageDigest m = MessageDigest.getInstance(ALGO_MD5);
		temporaryKey = m.digest(key.getBytes());

		if (temporaryKey.length < Integer.parseInt("24")) // DESede require 24 byte length key
		{
			int index = 0;
			for (int i = temporaryKey.length; i < Integer.parseInt("24"); i++) {
				keyArray[i] = temporaryKey[index];
			}
		}

		Cipher c = Cipher.getInstance(DES_ALGO);
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, DES_KEY),
				new IvParameterSpec(sharedvector));
		byte[] decrypted = c.doFinal(Base64.decode(message));

		rawText = new String(decrypted, CHARSET_UTF8);
		return rawText;

	}

	/**
	 * @param b
	 *            source byte array
	 * @param offset
	 *            starting offset
	 * @param len
	 *            number of bytes in destination (processes len*2)
	 * @return byte[len]
	 */
	public static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * Integer.parseInt("2"); i++) {
			int shift = i % Integer.parseInt("2") == 1 ? 0 : Integer.parseInt("4");
			d[i >> 1] |= Character.digit((char) b[offset + i], Integer.parseInt("16")) << shift;
		}
		return d;
	}

	/**
	 * @param s
	 *            source string (with Hex representation)
	 * @return byte array
	 */
	public static byte[] hex2byte(String s) {
		if (s.length() % Integer.parseInt("2") == 0) {
			return hex2byte(s.getBytes(), 0, s.length() >> 1);
		} else {
			// Padding left zero to make it even size #Bug raised by tommy
			return hex2byte("0" + s);
		}
	}

	/**
	 * converts a byte array to hex string (suitable for dumps and ASCII
	 * packaging of Binary fields
	 * 
	 * @param b
	 *            - byte array
	 * @return String representation
	 */
	public static String hexString(byte[] b) {
		StringBuilder d = new StringBuilder(b.length * Integer.parseInt("2"));
		for (int i = 0; i < b.length; i++) {
			char hi = Character.forDigit((b[i] >> Integer.parseInt("4")) & 0x0F, Integer.parseInt("16"));
			char lo = Character.forDigit(b[i] & 0x0F, Integer.parseInt("16"));
			d.append(Character.toUpperCase(hi));
			d.append(Character.toUpperCase(lo));
		}
		return d.toString();
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
}
