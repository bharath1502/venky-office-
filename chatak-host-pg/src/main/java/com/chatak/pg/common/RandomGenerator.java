package com.chatak.pg.common;

import java.security.SecureRandom;
import java.util.Random;

/**
 * This class is used to generate random numbers, alpha-numeric
 * characters etc
 */
public class RandomGenerator {
	
	private RandomGenerator() {
		//Do nothing
	}

  /**
   * This method generate the default random alpha numeric value
   * 
   * @return String
   */
  public static String generateRandAlphaNumeric() {
    char[] randomChar = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                          'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
    String finalRandString = "";
    SecureRandom randomObj = new SecureRandom();
    for(int j = 0; j < 6; j++) {
      int rand_int = randomObj.nextInt(72);
      finalRandString += randomChar[rand_int];
    }
    return finalRandString;
  }

  /**
   * This method generate the random alpha numeric value based on length
   * 
   * @param length
   * @return String
   */
  public static String generateRandAlphaNumeric(int length) {
    char[] randomChar = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                          'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
    String finalRandString = "";
    SecureRandom randomObj = new SecureRandom();
    for(int j = 0; j < length; j++) {
      int rand_int = randomObj.nextInt(72);
      finalRandString += randomChar[rand_int];
      if(finalRandString.length() >= length) {
        finalRandString = finalRandString.substring(0, length);
        break;
      }
    }
    return finalRandString;
  }

  /**
   * This method generate the random numeric value based on length
   * 
   * @param length
   * @return String
   */
  public static String generateRandNumeric(int length) {
    String finalRandString = "";
    SecureRandom randomObj = new SecureRandom();
    for(int j = 0; j < length; j++) {
      int rand_int = randomObj.nextInt(72);
      finalRandString += rand_int;
      if(finalRandString.length() >= length) {
        finalRandString = finalRandString.substring(0, length);
        break;
      }
    }
    return finalRandString;
  }

  /**
   * This method generate the random PC code based on length
   * 
   * @param pcLen
   * @return String
   */
  public static String generatePCCode(int pcLen) {
    int i = 0;
    int diff = 0;
    String temp = null;
    i = (int) (Math.random() * 1000000);
    temp = String.valueOf(i);
    if(temp.length() < pcLen) {
      diff = pcLen - temp.length();
      for(int j = 0; j < diff; j++) {
        temp = "0" + temp;
      }
    }
    return temp;
  }
}