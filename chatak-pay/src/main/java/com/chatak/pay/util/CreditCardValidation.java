package com.chatak.pay.util;

import org.apache.log4j.Logger;

import com.chatak.pg.bean.BillingData;
import com.chatak.pg.util.CommonUtil;
import com.chatak.pg.util.Constants;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 26-Feb-2015 10:12:40 AM
 * @version 1.0
 */
public class CreditCardValidation {

  private CreditCardValidation() {
    super();
  }

  private static Logger logger = Logger.getLogger(CreditCardValidation.class);

  /**
   * @param args
   *          the command line arguments
   */
  public static boolean isValid(long number) {

    int total = sumOfDoubleEvenPlace(number) + sumOfOddPlace(number);

    if ((total % Constants.TEN == 0) && (prefixMatched(number, 1) == true)) {
      logger.info("isValid :: valid card");
      return true;
    } else {
      logger.info("isValid :: invalid card");
      return false;
    }
  }

  private static int getDigit(int number) {

    if (number <= Constants.NINE) {
      return number;
    } else {
      int firstDigit = number % Constants.TEN;
      int secondDigit = (int) (number / Constants.TEN);

      return firstDigit + secondDigit;
    }
  }

  private static int sumOfOddPlace(long number) {
    int result = 0;

    while (number > 0) {
      result += (int) (number % Constants.TEN);
      number = number / Constants.MAX_PAGE_SIZE;
    }

    return result;
  }

  private static int sumOfDoubleEvenPlace(long number) {

    int result = 0;
    long temp = 0;

    while (number > 0) {
      temp = number % Constants.MAX_PAGE_SIZE;
      result += getDigit((int) (temp / Constants.TEN) * Constants.TWO);
      number = number / Constants.MAX_PAGE_SIZE;
    }

    return result;
  }

  private static boolean prefixMatched(long number, int d) {

		if ((getPrefix(number, d) == 1) || (getPrefix(number, d) == Constants.TWO)
				|| (getPrefix(number, d) == Constants.THREE) || (getPrefix(number, d) == Constants.FOUR)
				|| (getPrefix(number, d) == Constants.FIVE) || (getPrefix(number, d) == Constants.SIX)
				|| (getPrefix(number, d) == Constants.SEVEN) || (getPrefix(number, d) == Constants.EIGHT)
				|| (getPrefix(number, d) == Constants.NINE)) {
			logger.info("\nChatak Prepaid Card ! ");
			return true;
		} else {
			logger.info("prefixMatched :: unknown card");
			return false;

		}
  }

  private static int getSize(long d) {
    int count = 0;
    while (d > 0) {
      d = d / Constants.TEN;
      count++;
    }
    return count;
  }

  /**
   * Return the first k number of digits from number. If the number of digits in
   * number is less than k, return number.
   */
  private static long getPrefix(long number, int k) {

    if (getSize(number) < k) {
      return number;
    } else {

      int size = (int) getSize(number);

      for (int i = 0; i < (size - k); i++) {
        number = number / Constants.TEN;
      }

      return number;

    }
  }

  /**
   * <<validating billing data if it is not null>>
   * 
   * @param billingData
   * @return
   */
  public static String validateBillingData(BillingData billingData) {
    String message = "";
    if (null != billingData) {

      if (CommonUtil.isNullAndEmpty(billingData.getAddress1())) {
        message += "Address1 value required";
      } else if (CommonUtil.isNullAndEmpty(billingData.getCity())) {
        message += "city value required";
      } else if (CommonUtil.isNullAndEmpty(billingData.getCountry())) {
        message += "Country value required";
      } else if (CommonUtil.isNullAndEmpty(billingData.getState())) {
        message += "State value required";
      } else if (CommonUtil.isNullAndEmpty(billingData.getZipCode())) {
        message += "ZipCode value required";
      } else if (CommonUtil.isNullAndEmpty(billingData.getEmail())) {
        message += "Email Id value required";
      }
      return message;
    } else {
      message += "Billing data is required";
    }

    return message;
  }

}
