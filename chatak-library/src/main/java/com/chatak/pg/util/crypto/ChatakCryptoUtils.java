package com.chatak.pg.util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 25-May-2015 9:47:00 AM
 * @version 1.0
 */
public class ChatakCryptoUtils {

	private ChatakCryptoUtils() {
	// need to implement based on requirement
	}

	/**
	 * Method to convert SHA value to Hash
	 * 
	 * @param shaValue
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	public static String sha2Hash(String shaValue) throws NoSuchAlgorithmException {
		String hashValue = null;
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		hashValue = bytes2Hex(messageDigest.digest(shaValue.getBytes()));
		return hashValue;
	}

	/**
	 * Method to convert bytes array to Hex value
	 * 
	 * @param hash
	 * @return
	 */
	private static String bytes2Hex(byte hash[]) {
		Formatter formatter = new Formatter();
		byte abyte0[];
		abyte0 = hash;
		int j = abyte0.length;
		for (int i = 0; i < j; i++) {
			byte b = abyte0[i];
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}
		String hex = formatter.toString();
		formatter.close();
		return hex;
	}

	/**
	 * Method to generate Random String for the given length
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length) {
		String ALPHA_NUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ9876543210!@#$&_abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int ndx = (int) (Math.random() * (double) ALPHA_NUM.length());
			sb.append(ALPHA_NUM.charAt(ndx));
		}
		return sb.toString();
	}

	/**
	 * Method to get SHA-256 value
	 * 
	 * @param value
	 * @return
	 * @throws SecurityException
	 */
	public static String getSHA256(String value) throws SecurityException {
		String hashValue = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			hashValue = bytes2Hex(messageDigest.digest(value.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityException(e.getMessage(), e);
		}
		return hashValue;
	}

	/**
	 * Method to get Last given number characters
	 * 
	 * @param inputString
	 * @param subStringLength
	 * @return
	 */
	public static String getLastNChars(String inputString, int subStringLength) {
		if (inputString != null && inputString.length() > 0) {
			int length = inputString.length();
			if (length <= subStringLength) {
				return inputString;
			} else {
				int startIndex = length - subStringLength;
				return inputString.substring(startIndex);
			}
		} else {
			return "";
		}
	}

}
