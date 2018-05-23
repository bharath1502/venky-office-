package com.chatak.pg.util;

import org.apache.log4j.Logger;

/**
 *  This class is used to convert all the primitive data types in
 *  Java to bytes and vice-versa. Could be important while passing
 *  information over the network
 */
public class ByteConversionUtils {
  
  private static Logger logger = Logger.getLogger(ByteConversionUtils.class);

	/**
	 * Method to convert int to a byte[2] array
	 * 
	 * @param value
	 *          value to be converted
	 * @return byte array
	 */
	public static byte[] intToByteArray(int value) {
		byte[] bytes = new byte[Integer.parseInt("2")];
		for(int i = 0, shift = Integer.parseInt("8"); i < Integer.parseInt("2"); i++, shift -= Integer.parseInt("8"))
			bytes[i] = (byte) (0xFF & (value >> shift));
		return bytes;
	}

	/**
	 * Method to convert int to Byte array
	 * 
	 * @param value
	 *          value to be converted
	 * @param length
	 *          length of byte array
	 * @return byte array
	 */
	public static byte[] intToByteArray(int value, int length) {
		byte[] bytes = new byte[length];

		for(int i = 0, shift = Integer.parseInt("24"); i < length; i++, shift -= Integer.parseInt("8")) {
			bytes[i] = (byte) (0xFF & (value >> shift));
		}
		return bytes;
	}

	/**
	 * Method to extract int from byte array
	 * 
	 * @param ba
	 *          byte array
	 * @return int value represented by byte array
	 */
	public static int getIntFromByteArray(byte[] ba) {
		int intValue = 0;

		for(int j = 0; j < Integer.parseInt("2"); j++) {
			int temp = ba[j];
			if(temp < 0) {
				// Since we support only 2 bytes
				temp += Integer.parseInt("256");
			}
			intValue <<= Integer.parseInt("8");
			intValue = intValue + temp;
		}
		return intValue;
	}

	/**
	 * Method for setting even parity bit
	 * 
	 * @param bytes
	 *          byte array
	 * @param length
	 *          length of byte array
	 * @return void
	 */
	public static void setEvenParity(byte[] data, int length) {
		// iterate through the byte array
		for(int i = 0; i < length; i++) {
			// count number of bits set
			int count = 0;
			for(int j = 0; j < Integer.parseInt("7"); j++)
				if(((data[i] >> j) & 1) == 1)
					count++;
			// check if count is even, if not set the parity bit
			if((count % Integer.parseInt("2")) != 0)
				data[i] |= 0x80;
		}
	}

	/**
	 * Method to convert 7 bit parity data to 8 bit no parity
	 * 
	 * @param bytes
	 *          byte array
	 * @param length
	 *          length of byte array
	 * @return void
	 */
	public static void clearParityBit(byte[] bytes, int length) {
		for(int i = 0; i < length; i++)
			bytes[i] = (byte) (bytes[i] & 0x7F);
	}

	/**
	 * Method to extract int from byteArray
	 * 
	 * @param ba
	 *          byte array
	 * @param length
	 *          length of returned byte array
	 * @return int value
	 */
	public static int getIntFromByteArray(byte[] ba, int length) {
		int intValue = 0;
		for(int j = 0; j < length; j++) {
		  intValue <<= Integer.parseInt("8");
			intValue = intValue + ba[j];
		}
		return intValue;
	}

	/**
	 * Method to convert long to byte array
	 * 
	 * @param value
	 *          long value to be converted
	 * @return byte array
	 */
	public static byte[] longToByteArray(long value) {
	  logger.error(value + "-->  ");
		byte[] bytes = new byte[Integer.parseInt("8")];
		for(int i = 0, shift = Integer.parseInt("56"); i < Integer.parseInt("8"); i++, shift -= Integer.parseInt("8"))
			bytes[i] = (byte) (0xFF & (value >> shift));
		return bytes;
	}

	/**
	 * Method to extract long from byte array
	 * 
	 * @param ba
	 *          byte array
	 * @return long value
	 */
	public static long getLongFromByteArray(byte[] ba) {
		long i = 0;
		for(int j = 0; j < Integer.parseInt("8"); j++) {
		  i <<= Integer.parseInt("8");
			i = i + ba[j];
		}
		return i;
	}

	/**
	 * Method to convert 1 byte to hex string. Handle leading zeroes as necessary
	 * 
	 * @param b
	 *          byte to be converted
	 * @return string representation
	 */
	public static String byteToHexString(byte b) {
		String hex = Integer.toString((b & 0xff) + 0x100, Integer.parseInt("16")).substring(1);
		return hex;
	}

	/**
	 * Method to convert array of bytes to a hex string
	 * 
	 * @param b
	 *          byte array
	 * @return return hex string representation
	 */
	public static String byteArrayToHexString(byte[] b, int length) {
		char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		StringBuilder sb = new StringBuilder(length * Integer.parseInt("2"));
		sb.append('|');
		for(int i = 0; i < length; i++) {
			// look up high nibble char
			sb.append(hexChar[(b[i] & 0xf0) >>> Integer.parseInt("4")]);
			// look up low nibble char
			sb.append(hexChar[b[i] & 0x0f]);
			sb.append('|');
		}
		return sb.toString();
	}

	/**
	 * Method to convert array of bytes to a hex string without '|'
	 * 
	 * @param b
	 *          byte array
	 * @return return hex string representation
	 */
	public static String byteArrayToHexString(byte[] b, int length, boolean withDelimitter) {
		char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		StringBuilder sb = new StringBuilder(length * Integer.parseInt("2"));
		if(withDelimitter)
			sb.append('|');

		for(int i = 0; i < length; i++) {
			// look up high nibble char
			sb.append(hexChar[(b[i] & 0xf0) >>> Integer.parseInt("4")]);
			// look up low nibble char
			sb.append(hexChar[b[i] & 0x0f]);
			if(withDelimitter)
				sb.append('|');

		}
		return sb.toString();
	}

	/***
	 * Converts an String representing hexidecimal values into an array of bytes
	 * of those same values. The returned array will be half the length of the
	 * passed array, as it takes two characters to represent any given byte. An
	 * exception is thrown if the passed char array has an odd number of elements.
	 * 
	 * @param hexString
	 *          String containing hexidecimal digits
	 * @return A byte array containing binary data decoded from the supplied char
	 *         array.
	 */
	public static byte[] HexStringToByteArray(String hexString) {

		char[] data = null;
		data = hexString.toCharArray();
		int len = hexString.length();
		if((len & 0x01) != 0) {
		  logger.error("Odd Number of Characters");
		}
		byte[] out = new byte[len >> 1];
		// two characters form the hex value.
		for(int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << Integer.parseInt("4");
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}

	/***
	 * Converts a hexadecimal character to an integer.
	 * 
	 * @param ch
	 *          A character to convert to an integer digit
	 * @param index
	 *          The index of the character in the source
	 * @return An integer
	 */
	protected static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, Integer.parseInt("16"));
		if(digit == -1) {
		  logger.error("Illegal hexadecimal charcter " + ch + " at index " + index);
		}
		return digit;
	}

	/**
	 * Method to convert array of bytes to an ASCII string
	 * 
	 * @param bytes
	 *          byte array
	 * @param length
	 * @return ASCII string
	 */
	public static String byteArrayToString(byte[] bytes, int length) {
		// initialize byte string
	  StringBuilder buffer = new StringBuilder(length);

		// iterate through bytes in message
		String cString = null;
		for(int i = 0; i < length; i++) {
			// check for control characters
			if((cString = getControlChar(bytes[i])) != null) {
				buffer.append(cString);
			}
			else {
				// convert to char and add to string
				buffer.append((char) bytes[i]);
			}
		}
		return buffer.toString();
	}

	/**
	 * Method to return byte for an integer
	 * 
	 * @param buffer
	 *          byte buffer
	 * @return int length
	 */
	public static byte intToByte(int number) {
		byte intByte;
		Integer intInteger = new Integer(number);
		intByte = intInteger.byteValue();
		return intByte;
	}

	/**
	 * Method to return control character string representation
	 * 
	 * @param b
	 *          ASCII byte
	 * @return String representation or null if not control character
	 */
	public static String getControlChar(byte b) {
		int NUM_CONTROL_CHARS = Integer.parseInt("32");
		String[] ControlChars = { "NUL","SOH","STX","ETX","EOT","ENQ","ACK","BEL","BS","HT","LF","VT","FF","CR","SO","SI",
				"DLE","DC1","DC2","DC3","DC4","NAK","SYN","ETB","CAN","EM","SUB","ESC","FS","GS","RS","US" };
		if(b >= 0 && b < NUM_CONTROL_CHARS)
			return "<" + ControlChars[b] + ">";
		else
			return null;
	}

	/**
	 * Method to convert String to Hex value
	 * @param str
	 * @return Hex String
	 */
	public static String asciiToHex(String str){
	  StringBuilder hexString = new StringBuilder();
		for (int i=0; i<str.length(); i++)  
		{  
			hexString.append(String.format("%x", (byte)(str.charAt(i))));
		}  

		return hexString.toString();
	}
	
	/**
	 * Method to convert hex to char
	 * @param str
	 * @return Hex String
	 */
	public static String hexToAsciiString(String str){
	  StringBuilder asciiString = new StringBuilder();
		
		byte[] b = HexStringToByteArray(str);
		for(int i=0 ; i<b.length;i++){
			asciiString.append((char)b[i]);
		}
		
		return asciiString.toString();
	}

	
	/**
	 * Test methods added to this java file
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] b = HexStringToByteArray("3032");
		for(int i=0 ; i<b.length;i++){
		  logger.info((char)b[i]);
		}
		
		logger.info(asciiToHex("Request"));
		
		logger.info(hexToAsciiString("5556"));
		
		logger.info(asciiToHex("4617862001085976d140120100125800"));
	}
}