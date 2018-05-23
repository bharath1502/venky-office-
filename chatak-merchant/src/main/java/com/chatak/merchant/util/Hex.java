package com.chatak.merchant.util;

import com.chatak.pg.util.Constants;

/**
 * This class transforms the byte arrays to hex decimal format and vice versa.
 * This helps so much in saving the binary formats in the DB
 * 
 * @author Raj Ks
 */
public class Hex {

  /**
   * No need to instantiate this.
   */
  private Hex() {
  }

  /**
   * Hex decimal character array. Used in selecting the mapping for each four
   * binary bits
   */
  private static final char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  /**
   *
   */
  public static final int HEX_CHAR_LENGTH = 4;

  /**
   * Encode the byte array of binary data to hex decimal format
   * 
   * @param data
   *          : byte array
   * @return: Hex decimal string representing the bytes
   */
  public static String encodeHex(byte data[]) {
    int datalength = data.length;
    // multiply by 2 as every byte will be represented by two characters
    char out[] = new char[datalength * Constants.TWO];
    int j = 0;
    for(int i = 0; i < datalength; i++) {
      out[j++] = hex[(0xf0 & data[i]) >>> HEX_CHAR_LENGTH];
      out[j++] = hex[0xf & data[i]];
    }
    return new String(out);
  }

  /**
   * Decodes the hex decimal string back into byte array
   * 
   * @param data
   *          : character array of hex decimal characters
   * @return: byte array of binary representation
   * @throws DecoderException
   */
  public static byte[] decodeHex(char data[]) {
    int datalength = data.length;
    if((datalength % Constants.TWO) != 0) {
      throw new NumberFormatException("Invalid charachter array length.");
    }
    // dividing by two as every two characters will represent one byte
    byte out[] = new byte[datalength / Constants.TWO];
    int i = 0;
    for(int j = 0; j < datalength;) {
      int upperBytes = toDigit(data[j], j) << Constants.FOUR;
      j++;
      int byteInteger = upperBytes | toDigit(data[j], j);
      j++;
      // Anding with 0xff to clear the left byte so as to narrow the int to byte
      // smoothly
      out[i] = (byte) (byteInteger & 0xff);
      i++;
    }
    return out;
  }

  /**
   * Decodes the hexdeciaml string back into byte array
   * 
   * @param data
   *          : String of hexdecimal charchters
   * @return: byte array of binary representation
   * @throws DecoderException
   */
  public static byte[] decodeHex(String data) {
    char[] charArray = new char[data.length()];
    data.getChars(0, charArray.length, charArray, 0);
    return decodeHex(charArray);
  }

  /**
   * Converts the passed charcter to the integer representation of the Ascii
   * with radiax of 16 for hexdecimal
   * 
   * @param ch
   *          : The charchter that need to be converted to int.
   * @param index
   *          : The index of charchter in the String so as to declare where this
   *          error came from
   * @return: int format of the charchter
   * @throws DecoderException
   */
  protected static int toDigit(char ch, int index) {
    int digit = Character.digit(ch, Constants.SIXTEEN);
    if(digit == -1) {
      throw new NumberFormatException("Illegal hexadecimal charcter " + ch + " at index " + index);
    }
    return digit;
  }

  public static byte[] asciiToBinary(String string) {
    byte[] array = new byte[string.length() / Constants.TWO];
    for(int i = 0; i < string.length(); i += Constants.TWO) {
      array[i / Constants.TWO] = (byte) Hex.asciiToBinary((byte) string.charAt(i), (byte) string.charAt(i + 1));
    }
    return array;
  }

  public static byte[] asciiToBinary(byte[] buffer) {
    byte[] array = new byte[buffer.length / Constants.TWO];
    for(int i = 0; i < buffer.length; i += Constants.TWO) {
      if(buffer[i] == Constants.TEN || buffer[i] == Constants.THIRTEEN) {
        --i;
        continue;
      }
      array[i / Constants.TWO] = (byte) Hex.asciiToBinary(buffer[i], buffer[i + 1]);
    }
    return array;
  }

  /**
   * Composes a single byte from the two hexadecimal values
   * 
   * @param mBuffer
   *          the buffer containing data
   * @return the byte composed from the two consecutive hexadecimal values
   * @throws Exception
   */
  public static int asciiToBinary(byte msb, byte lsb) {
    return (asciiToHex(msb) << Constants.FOUR | asciiToHex(lsb));
  }

  /**
   * Converts from ascii to hexadecimal
   * 
   * @param b
   *          the ascii character
   * @return the hexadecimal value converted from the ascii
   * @throws Exception
   */
  public static int asciiToHex(byte b) {
    if(b >= '0' && b <= '9')
      return b - '0';
    else if(b >= 'A' && b <= 'F')
      return b - 'A' + Constants.TEN;
    else if(b >= 'a' && b <= 'f')
      return b - 'a' + Constants.TEN;
    else {
      throw new NumberFormatException("Invalid hexadecimal character (" + b + ") passed to the method");
    }
  }
}
