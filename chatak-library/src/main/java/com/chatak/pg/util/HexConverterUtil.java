package com.chatak.pg.util;


/**
 * @author raj.k
 * 
 */
public class HexConverterUtil {
  
  private HexConverterUtil() {
 // Do nothing
  }

	private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	public static String stringToHex(String input) {
		return asHex(input.getBytes());
	}

	public static String hexToString(String txtInHex) {
		byte[] txtInByte = new byte[txtInHex.length() / Integer.parseInt("2")];
		int j = 0;
		for (int i = 0; i < txtInHex.length(); i += Integer.parseInt("2")) {
			txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + Integer.parseInt("2")), Integer.parseInt("16"));
		}
		return new String(txtInByte);
	}

	private static String asHex(byte[] buf) {
		char[] chars = new char[Integer.parseInt("2") * buf.length];
		for (int i = 0; i < buf.length; ++i) {
			chars[Integer.parseInt("2") * i] = HEX_CHARS[(buf[i] & 0xF0) >>> Integer.parseInt("4")];
			chars[Integer.parseInt("2") * i + 1] = HEX_CHARS[buf[i] & 0x0F];
		}
		return new String(chars);
	}
}
