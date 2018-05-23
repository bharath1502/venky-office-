package com.chatak.pay.util;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.chatak.pg.util.Constants;

// Referenced classes of package javacryption.aes:
// Rijndael, Util

public class AesCtr {

  private AesCtr() {}

  public static String encrypt(String plaintext, String password, int nBits) {
    Rijndael aes = new Rijndael();
    if (nBits != Constants.ONE_TWO_EIGHT && nBits != Constants.ONE_NINE_TWO && nBits != Constants.TWO_FIVE_SIX) {
      throw new CryptoException(
          (new StringBuilder("Invalid key size (")).append(nBits).append(" bits)").toString());
    }
    int nBytes = nBits / Constants.EIGHT;
    byte[] key = validateNBytesValue(password, aes, nBytes);
    byte counterBlock[] = new byte[Constants.SIXTEEN];
    long nonce = (new Date()).getTime();
    int nonceMs = (int) nonce % Constants.ONE_THOUSAND;
    int nonceSec = (int) Math.floor(nonce / Constants.ONE_THOUSAND_LONG);
    int nonceRnd = (int) Math.floor(Math.random() * Constants.SIX_FIVE_FIVE_THREE_FIVE_DOUBLE);
    for (int i = 0; i < Constants.TWO; i++) {
      counterBlock[i] = (byte) (nonceMs >>> i * Constants.EIGHT & 0xff);
    }

    for (int i = 0; i < Constants.TWO; i++) {
      counterBlock[i + Constants.TWO] = (byte) (nonceRnd >>> i * Constants.EIGHT & 0xff);
    }

    for (int i = 0; i < Constants.FOUR; i++) {
      counterBlock[i + Constants.FOUR] = (byte) (nonceSec >>> i * Constants.EIGHT & 0xff);
    }

    byte ctrTxt[] = new byte[Constants.EIGHT];
    getCounterBlock(counterBlock, ctrTxt);

    aes.makeKey(key, Constants.TWO_FIVE_SIX, 1);
    int blockCount = (int) Math.ceil((new Float(plaintext.length())).floatValue() / Constants.SIXTEEN_FLOAT);
    byte ciphertxt[] = new byte[plaintext.length()];
    for (int b = 0; b < blockCount; b++) {
      for (int c = 0; c < Constants.FOUR; c++) {
        counterBlock[Constants.FIFTEEN - c] = (byte) (b >>> c * Constants.EIGHT & 0xff);
      }

      for (int c = 0; c < Constants.FOUR; c++) {
        counterBlock[Constants.FIFTEEN - c - Constants.FOUR] = 0;
      }

      byte cipherCntr[] = aes.encryptBlock(counterBlock, new byte[Constants.SIXTEEN]);
      int blockLength = b >= blockCount - 1 ? (plaintext.length() - 1) % Constants.SIXTEEN + 1 : Constants.SIXTEEN;
      for (int i = 0; i < blockLength; i++) {
        ciphertxt[b * Constants.SIXTEEN + i] = (byte) (cipherCntr[i] ^ plaintext.charAt(b * Constants.SIXTEEN + i));
      }

    }

    aes.finalize();
    byte ciphertext[] = Util.addByteArrays(ctrTxt, ciphertxt);
    String ciphertext64 = new String(Base64.encodeBase64(ciphertext));
    return ciphertext64;
  }

private static byte[] validateNBytesValue(String password, Rijndael aes, int nBytes) {
	byte pwBytes[] = new byte[nBytes];
    getpwBytes(password, nBytes, pwBytes);

    aes.makeKey(pwBytes, Constants.TWO_FIVE_SIX, 1);
    byte key[] = aes.encryptBlock(pwBytes, new byte[Constants.SIXTEEN]);
    aes.finalize();
    if (nBytes > Constants.SIXTEEN) {
      byte keySlice[] = new byte[nBytes - Constants.SIXTEEN];
      getKeySlice(nBytes, key, keySlice);

      key = Util.addByteArrays(key, keySlice);
    }
	return key;
}

  private static byte[] getKeySlice(int nBytes, byte[] key, byte[] keySlice) {
    for (int i = 0; i < nBytes - Constants.SIXTEEN; i++) {
      keySlice[i] = key[i];
    }
    return keySlice;
  }

  private static byte[] getpwBytes(String password, int nBytes, byte[] pwBytes) {
    for (int i = 0; i < nBytes; i++) {
      pwBytes[i] = (byte) password.charAt(i);
    }
    return pwBytes;
  }

  public static String decrypt(String ciphertext, String password, int nBits) {
    Rijndael aes = new Rijndael();
    if (nBits != Constants.ONE_TWO_EIGHT && nBits != Constants.ONE_NINE_TWO && nBits != Constants.TWO_FIVE_SIX) {
      return null;
    }
    byte cipherByte[] = Base64.decodeBase64(ciphertext.getBytes());
    int nBytes = nBits / Constants.EIGHT;
    byte[] key = validateNBytesValue(password, aes, nBytes);
    byte counterBlock[] = new byte[Constants.SIXTEEN];
    getCounterBlock(cipherByte, counterBlock);

    aes.makeKey(key, Constants.TWO_FIVE_SIX, 1);
    int blockCount = (int) Math.ceil((new Float(cipherByte.length - Constants.EIGHT)).floatValue() / Constants.SIXTEEN_FLOAT);
    byte plaintxt[] = new byte[cipherByte.length - Constants.EIGHT];
    for (int b = 0; b < blockCount; b++) {
      for (int c = 0; c < Constants.FOUR; c++) {
        counterBlock[Constants.FIFTEEN - c] = (byte) (b >>> c * Constants.EIGHT & 0xff);
      }

      for (int c = 0; c < Constants.FOUR; c++) {
        counterBlock[Constants.FIFTEEN - c - Constants.FOUR] = 0;
      }

      byte cipherCntr[] = aes.encryptBlock(counterBlock, new byte[Constants.SIXTEEN]);
      int blockLength = b >= blockCount - 1 ? (cipherByte.length - Constants.NINE) % Constants.SIXTEEN + 1 : Constants.SIXTEEN;
      for (int i = 0; i < blockLength; i++) {
        plaintxt[b * Constants.SIXTEEN + i] = (byte) (cipherCntr[i] ^ cipherByte[Constants.EIGHT + b * Constants.SIXTEEN + i]);
      }

    }

    aes.finalize();
    String plaintext = new String(plaintxt);
    return plaintext;
  }

  private static byte[] getCounterBlock(byte[] cipherByte, byte[] counterBlock) {
    for (int i = 0; i < Constants.EIGHT; i++) {
      counterBlock[i] = cipherByte[i];
    }
    return counterBlock;
  }
}
