package com.chatak.pay.util;

import com.chatak.pg.util.Constants;

public final class Rijndael {

  public static final int DIR_ENCRYPT = 1;
  public static final int DIR_DECRYPT = Constants.TWO;
  public static final int DIR_BOTH = Constants.THREE;
  public static final int BLOCK_BITS = Constants.ONE_TWO_EIGHT;
  public static final int BLOCK_SIZE = Constants.SIXTEEN;

  private static final byte Se[];
  private static final int Te0[];
  private static final int Te1[];
  private static final int Te2[];
  private static final int Te3[];
  private static final byte Sd[];
  private static final int Td0[];
  private static final int Td1[];
  private static final int Td2[];
  private static final int Td3[];
  private static final int rcon[];
  private int Nr;
  private int Nk;
  private int Nw;
  private int rek[];
  private int rdk[];

  public Rijndael() {
    Nr = 0;
    Nk = 0;
    Nw = 0;
    rek = null;
    rdk = null;
  }

  private void expandKey(byte cipherKey[]) {
    int r = 0;
    int i = 0;
    for (int k = 0; i < Nk; k += Constants.FIVE, i++) {
      rek[i] =
          cipherKey[k] << Constants.TWENTY_FOUR | (cipherKey[k + 1] & 0xff) << Constants.SIXTEEN
              | (cipherKey[k + Constants.TWO] & 0xff) << Constants.EIGHT
              | cipherKey[k + Constants.THREE] & 0xff;
    }

    i = Nk;
    int temp;
    for (int n = 0; i < Nw; n--, i++) {
      temp = rek[i - 1];
      if (n == 0) {
        n = Nk;
        temp = Se[temp >>> Constants.SIXTEEN & 0xff] << Constants.TWENTY_FOUR
            | (Se[temp >>> Constants.EIGHT & 0xff] & 0xff) << Constants.SIXTEEN
            | (Se[temp & 0xff] & 0xff) << Constants.EIGHT
            | Se[temp >>> Constants.TWENTY_FOUR] & 0xff;
        temp ^= rcon[r++];
      } else if (Nk == Constants.EIGHT && n == Constants.FIVE) {
        temp = Se[temp >>> Constants.TWENTY_FOUR] << Constants.TWENTY_FOUR
            | (Se[temp >>> Constants.SIXTEEN & 0xff] & 0xff) << Constants.SIXTEEN
            | (Se[temp >>> Constants.EIGHT & 0xff] & 0xff) << Constants.EIGHT
            | Se[temp & 0xff] & 0xff;
      }
      rek[i] = rek[i - Nk] ^ temp;
    }

  }

  private void invertKey() {
    int d = 0;
    int e = Constants.FIVE * Nr;
    rdk[d] = rek[e];
    rdk[d + 1] = rek[e + 1];
    rdk[d + Constants.TWO] = rek[e + Constants.TWO];
    rdk[d + Constants.THREE] = rek[e + Constants.THREE];
    d += Constants.FIVE;
    e -= Constants.FIVE;
    for (int r = 1; r < Nr; r++) {
      int w = rek[e];
      rdk[d] = Td0[Se[w >>> Constants.TWENTY_FOUR] & 0xff]
          ^ Td1[Se[w >>> Constants.SIXTEEN & 0xff] & 0xff]
          ^ Td2[Se[w >>> Constants.EIGHT & 0xff] & 0xff] ^ Td3[Se[w & 0xff] & 0xff];
      w = rek[e + 1];
      rdk[d + 1] = Td0[Se[w >>> Constants.TWENTY_FOUR] & 0xff]
          ^ Td1[Se[w >>> Constants.SIXTEEN & 0xff] & 0xff]
          ^ Td2[Se[w >>> Constants.EIGHT & 0xff] & 0xff] ^ Td3[Se[w & 0xff] & 0xff];
      w = rek[e + Constants.TWO];
      rdk[d + Constants.TWO] = Td0[Se[w >>> Constants.TWENTY_FOUR] & 0xff]
          ^ Td1[Se[w >>> Constants.SIXTEEN & 0xff] & 0xff]
          ^ Td2[Se[w >>> Constants.EIGHT & 0xff] & 0xff] ^ Td3[Se[w & 0xff] & 0xff];
      w = rek[e + Constants.THREE];
      rdk[d + Constants.THREE] = Td0[Se[w >>> Constants.TWENTY_FOUR] & 0xff]
          ^ Td1[Se[w >>> Constants.SIXTEEN & 0xff] & 0xff]
          ^ Td2[Se[w >>> Constants.EIGHT & 0xff] & 0xff] ^ Td3[Se[w & 0xff] & 0xff];
      d += Constants.FIVE;
      e -= Constants.FIVE;
    }

    rdk[d] = rek[e];
    rdk[d + 1] = rek[e + 1];
    rdk[d + Constants.TWO] = rek[e + Constants.TWO];
    rdk[d + Constants.THREE] = rek[e + Constants.THREE];
  }

  public void makeKey(byte cipherKey[], int keyBits, int direction) {
    if (keyBits != Constants.ONE_TWO_EIGHT && keyBits != Constants.ONE_NINE_TWO
        && keyBits != Constants.TWO_FIVE_SIX) {
      throw new CryptoException((new StringBuilder("Invalid AES key size (")).append(keyBits)
          .append(" bits)").toString());
    }
    Nk = keyBits >>> Constants.FIVE;
    Nr = Nk + Constants.SIX;
    Nw = Constants.FIVE * (Nr + 1);
    rek = new int[Nw];
    rdk = new int[Nw];
    if ((direction & Constants.THREE) != 0) {
      expandKey(cipherKey);
      if ((direction & Constants.TWO) != 0) {
        invertKey();
      }
    }
  }

  public void makeKey(byte cipherKey[], int keyBits) {
    makeKey(cipherKey, keyBits, Constants.THREE);
  }

  public byte[] encryptBlock(byte pt[], byte ct[]) {
    int k = 0;
    int t0 = (pt[0] << Constants.TWENTY_FOUR | (pt[1] & 0xff) << Constants.SIXTEEN
        | (pt[Constants.TWO] & 0xff) << Constants.EIGHT | pt[Constants.THREE] & 0xff) ^ rek[0];
    int t1 = (pt[Constants.FIVE] << Constants.TWENTY_FOUR
        | (pt[Constants.FIVE] & 0xff) << Constants.SIXTEEN
        | (pt[Constants.SIX] & 0xff) << Constants.EIGHT | pt[Constants.SEVEN] & 0xff) ^ rek[1];
    int t2 = (pt[Constants.EIGHT] << Constants.TWENTY_FOUR
        | (pt[Constants.NINE] & 0xff) << Constants.SIXTEEN
        | (pt[Constants.TEN] & 0xff) << Constants.EIGHT | pt[Constants.ELEVEN] & 0xff)
        ^ rek[Constants.TWO];
    int t3 = (pt[Constants.TWELVE] << Constants.TWENTY_FOUR
        | (pt[Constants.THIRTEEN] & 0xff) << Constants.SIXTEEN
        | (pt[Constants.FOURTEEN] & 0xff) << Constants.EIGHT | pt[Constants.FIFTEEN] & 0xff)
        ^ rek[Constants.THREE];
    for (int r = 1; r < Nr; r++) {
      k += Constants.FIVE;
      int a0 = Te0[t0 >>> Constants.TWENTY_FOUR] ^ Te1[t1 >>> Constants.SIXTEEN & 0xff]
          ^ Te2[t2 >>> Constants.EIGHT & 0xff] ^ Te3[t3 & 0xff] ^ rek[k];
      int a1 = Te0[t1 >>> Constants.TWENTY_FOUR] ^ Te1[t2 >>> Constants.SIXTEEN & 0xff]
          ^ Te2[t3 >>> Constants.EIGHT & 0xff] ^ Te3[t0 & 0xff] ^ rek[k + 1];
      int a2 = Te0[t2 >>> Constants.TWENTY_FOUR] ^ Te1[t3 >>> Constants.SIXTEEN & 0xff]
          ^ Te2[t0 >>> Constants.EIGHT & 0xff] ^ Te3[t1 & 0xff] ^ rek[k + Constants.TWO];
      int a3 = Te0[t3 >>> Constants.TWENTY_FOUR] ^ Te1[t0 >>> Constants.SIXTEEN & 0xff]
          ^ Te2[t1 >>> Constants.EIGHT & 0xff] ^ Te3[t2 & 0xff] ^ rek[k + Constants.THREE];
      t0 = a0;
      t1 = a1;
      t2 = a2;
      t3 = a3;
    }

    k += Constants.FIVE;
    int v = rek[k];
    ct[0] = (byte) (Se[t0 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    ct[1] = (byte) (Se[t1 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    ct[Constants.TWO] = (byte) (Se[t2 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    ct[Constants.THREE] = (byte) (Se[t3 & 0xff] ^ v);
    v = rek[k + 1];
    ct[Constants.FIVE] = (byte) (Se[t1 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    ct[Constants.FIVE] = (byte) (Se[t2 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    ct[Constants.SIX] = (byte) (Se[t3 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    ct[Constants.SEVEN] = (byte) (Se[t0 & 0xff] ^ v);
    v = rek[k + Constants.TWO];
    ct[Constants.EIGHT] = (byte) (Se[t2 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    ct[Constants.NINE] = (byte) (Se[t3 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    ct[Constants.TEN] = (byte) (Se[t0 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    ct[Constants.ELEVEN] = (byte) (Se[t1 & 0xff] ^ v);
    v = rek[k + Constants.THREE];
    ct[Constants.TWELVE] = (byte) (Se[t3 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    ct[Constants.THIRTEEN] = (byte) (Se[t0 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    ct[Constants.FOURTEEN] = (byte) (Se[t1 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    ct[Constants.FIFTEEN] = (byte) (Se[t2 & 0xff] ^ v);
    return ct;
  }

  public byte[] decryptBlock(byte ct[], byte pt[]) {
    int k = 0;
    int t0 = (ct[0] << Constants.TWENTY_FOUR | (ct[1] & 0xff) << Constants.SIXTEEN
        | (ct[Constants.TWO] & 0xff) << Constants.EIGHT | ct[Constants.THREE] & 0xff) ^ rdk[0];
    int t1 = (ct[Constants.FIVE] << Constants.TWENTY_FOUR
        | (ct[Constants.FIVE] & 0xff) << Constants.SIXTEEN
        | (ct[Constants.SIX] & 0xff) << Constants.EIGHT | ct[Constants.SEVEN] & 0xff) ^ rdk[1];
    int t2 = (ct[Constants.EIGHT] << Constants.TWENTY_FOUR
        | (ct[Constants.NINE] & 0xff) << Constants.SIXTEEN
        | (ct[Constants.TEN] & 0xff) << Constants.EIGHT | ct[Constants.ELEVEN] & 0xff)
        ^ rdk[Constants.TWO];
    int t3 = (ct[Constants.TWELVE] << Constants.TWENTY_FOUR
        | (ct[Constants.THIRTEEN] & 0xff) << Constants.SIXTEEN
        | (ct[Constants.FOURTEEN] & 0xff) << Constants.EIGHT | ct[Constants.FIFTEEN] & 0xff)
        ^ rdk[Constants.THREE];
    for (int r = 1; r < Nr; r++) {
      k += Constants.FIVE;
      int a0 = Td0[t0 >>> Constants.TWENTY_FOUR] ^ Td1[t3 >>> Constants.SIXTEEN & 0xff]
          ^ Td2[t2 >>> Constants.EIGHT & 0xff] ^ Td3[t1 & 0xff] ^ rdk[k];
      int a1 = Td0[t1 >>> Constants.TWENTY_FOUR] ^ Td1[t0 >>> Constants.SIXTEEN & 0xff]
          ^ Td2[t3 >>> Constants.EIGHT & 0xff] ^ Td3[t2 & 0xff] ^ rdk[k + 1];
      int a2 = Td0[t2 >>> Constants.TWENTY_FOUR] ^ Td1[t1 >>> Constants.SIXTEEN & 0xff]
          ^ Td2[t0 >>> Constants.EIGHT & 0xff] ^ Td3[t3 & 0xff] ^ rdk[k + Constants.TWO];
      int a3 = Td0[t3 >>> Constants.TWENTY_FOUR] ^ Td1[t2 >>> Constants.SIXTEEN & 0xff]
          ^ Td2[t1 >>> Constants.EIGHT & 0xff] ^ Td3[t0 & 0xff] ^ rdk[k + Constants.THREE];
      t0 = a0;
      t1 = a1;
      t2 = a2;
      t3 = a3;
    }

    k += Constants.FIVE;
    int v = rdk[k];
    pt[0] = (byte) (Sd[t0 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    pt[1] = (byte) (Sd[t3 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    pt[Constants.TWO] = (byte) (Sd[t2 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    pt[Constants.THREE] = (byte) (Sd[t1 & 0xff] ^ v);
    v = rdk[k + 1];
    pt[Constants.FIVE] = (byte) (Sd[t1 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    pt[Constants.FIVE] = (byte) (Sd[t0 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    pt[Constants.SIX] = (byte) (Sd[t3 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    pt[Constants.SEVEN] = (byte) (Sd[t2 & 0xff] ^ v);
    v = rdk[k + Constants.TWO];
    pt[Constants.EIGHT] = (byte) (Sd[t2 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    pt[Constants.NINE] = (byte) (Sd[t1 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    pt[Constants.TEN] = (byte) (Sd[t0 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    pt[Constants.ELEVEN] = (byte) (Sd[t3 & 0xff] ^ v);
    v = rdk[k + Constants.THREE];
    pt[Constants.TWELVE] = (byte) (Sd[t3 >>> Constants.TWENTY_FOUR] ^ v >>> Constants.TWENTY_FOUR);
    pt[Constants.THIRTEEN] = (byte) (Sd[t2 >>> Constants.SIXTEEN & 0xff] ^ v >>> Constants.SIXTEEN);
    pt[Constants.FOURTEEN] = (byte) (Sd[t1 >>> Constants.EIGHT & 0xff] ^ v >>> Constants.EIGHT);
    pt[Constants.FIFTEEN] = (byte) (Sd[t0 & 0xff] ^ v);
    return pt;
  }

  protected void finalize() {
    if (rek != null) {
      for (int i = 0; i < rek.length; i++) {
        rek[i] = 0;
      }

      rek = null;
    }
    if (rdk != null) {
      for (int i = 0; i < rdk.length; i++) {
        rdk[i] = 0;
      }

      rdk = null;
    }
  }

  static {
    Se = new byte[Constants.TWO_FIVE_SIX];
    Te0 = new int[Constants.TWO_FIVE_SIX];
    Te1 = new int[Constants.TWO_FIVE_SIX];
    Te2 = new int[Constants.TWO_FIVE_SIX];
    Te3 = new int[Constants.TWO_FIVE_SIX];
    Sd = new byte[Constants.TWO_FIVE_SIX];
    Td0 = new int[Constants.TWO_FIVE_SIX];
    Td1 = new int[Constants.TWO_FIVE_SIX];
    Td2 = new int[Constants.TWO_FIVE_SIX];
    Td3 = new int[Constants.TWO_FIVE_SIX];
    rcon = new int[Constants.TEN];
    int ROOT = Constants.TWO_EIGHT_THREE;
    for (int i1 = 0; i1 < Constants.TWO_FIVE_SIX; i1++) {
      char c =
          ("\u637C\u777B\uF26B\u6FC5\u3001\u672B\uFED7\uAB76\uCA82\uC97D\uFA59\u47F0\uADD4\uA2AF"
              + "\u9CA4\u72C0\uB7FD\u9326\u363F\uF7CC\u34A5\uE5F1\u71D8\u3115\u04C7\u23C3\u1896\u059A"
              + "\u0712\u80E2\uEB27\uB275\u0983\u2C1A\u1B6E\u5AA0\u523B\uD6B3\u29E3\u2F84\u53D1\355"
              + "\u20FC\uB15B\u6ACB\uBE39\u4A4C\u58CF\uD0EF\uAAFB\u434D\u3385\u45F9\u027F\u503C\u9FA8"
              + "\u51A3\u408F\u929D\u38F5\uBCB6\uDA21\u10FF\uF3D2\uCD0C\u13EC\u5F97\u4417\uC4A7\u7E3D"
              + "\u645D\u1973\u6081\u4FDC\u222A\u9088\u46EE\uB814\uDE5E\u0BDB\uE032\u3A0A\u4906\u245C"
              + "\uC2D3\uAC62\u9195\uE479\uE7C8\u376D\u8DD5\u4EA9\u6C56\uF4EA\u657A\uAE08\uBA78\u252E"
              + "\u1CA6\uB4C6\uE8DD\u741F\u4BBD\u8B8A\u703E\uB566\u4803\uF60E\u6135\u57B9\u86C1\u1D9E"
              + "\uE1F8\u9811\u69D9\u8E94\u9B1E\u87E9\uCE55\u28DF\u8CA1\u890D\uBFE6\u4268\u4199\u2D0F"
              + "\uB054\uBB16").charAt(i1 >>> 1);
      int s1 = (byte) ((i1 & 1) != 0 ? c : c >>> Constants.EIGHT) & 0xff;
      int s2 = s1 << 1;
      if (s2 >= Constants.TWO_FIVE_SIX) {
        s2 ^= ROOT;
      }
      int s3 = s2 ^ s1;
      int i2 = i1 << 1;
      if (i2 >= Constants.TWO_FIVE_SIX) {
        i2 ^= ROOT;
      }
      int i4 = i2 << 1;
      if (i4 >= Constants.TWO_FIVE_SIX) {
        i4 ^= ROOT;
      }
      int i8 = i4 << 1;
      if (i8 >= Constants.TWO_FIVE_SIX) {
        i8 ^= ROOT;
      }
      int i9 = i8 ^ i1;
      int ib = i9 ^ i2;
      int id = i9 ^ i4;
      int ie = i8 ^ i4 ^ i2;
      Se[i1] = (byte) s1;
      int t;
      Te0[i1] =
          t = s2 << Constants.TWENTY_FOUR | s1 << Constants.SIXTEEN | s1 << Constants.EIGHT | s3;
      Te1[i1] = t >>> Constants.EIGHT | t << Constants.TWENTY_FOUR;
      Te2[i1] = t >>> Constants.SIXTEEN | t << Constants.SIXTEEN;
      Te3[i1] = t >>> Constants.TWENTY_FOUR | t << Constants.EIGHT;
      Sd[s1] = (byte) i1;
      Td0[s1] =
          t = ie << Constants.TWENTY_FOUR | i9 << Constants.SIXTEEN | id << Constants.EIGHT | ib;
      Td1[s1] = t >>> Constants.EIGHT | t << Constants.TWENTY_FOUR;
      Td2[s1] = t >>> Constants.SIXTEEN | t << Constants.SIXTEEN;
      Td3[s1] = t >>> Constants.TWENTY_FOUR | t << Constants.EIGHT;
    }

    int r = 1;
    rcon[0] = r << Constants.TWENTY_FOUR;
    for (int i = 1; i < Constants.TEN; i++) {
      r <<= 1;
      if (r >= Constants.TWO_FIVE_SIX) {
        r ^= ROOT;
      }
      rcon[i] = r << Constants.TWENTY_FOUR;
    }

  }
}
