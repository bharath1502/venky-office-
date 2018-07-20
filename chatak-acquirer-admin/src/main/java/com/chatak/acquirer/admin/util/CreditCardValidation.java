package com.chatak.acquirer.admin.util;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.chatak.pg.util.Constants;


public class CreditCardValidation {
	
	private static Logger logger = Logger.getLogger(CreditCardValidation.class);

    public static boolean isValid(long number) {

        int total = sumOfDoubleEvenPlace(number) + sumOfOddPlace(number);

        if ((total % Constants.MAX_ENTITY_DISPLAY_SIZE == 0) && (prefixMatched(number, 1) == true)) {
            return true;
        } else {
            return false;
        }
    }

    public static int getDigit(int number) {

        if (number <= Constants.NINE) {
            return number;
        } else {
            int firstDigit = number % Constants.MAX_ENTITY_DISPLAY_SIZE;
            int secondDigit =  (number / Constants.MAX_ENTITY_DISPLAY_SIZE);

            return firstDigit + secondDigit;
        }
    }

    public static int sumOfOddPlace(long number) {
        int result = 0;

        while (number > 0) {
            result += (int) (number % Constants.MAX_ENTITY_DISPLAY_SIZE);
            number = number / Constants.MAX_PAGE_SIZE;
        }

        return result;
    }

    public static int sumOfDoubleEvenPlace(long number) {

        int result = 0;
        long temp = 0;

        while (number > 0) {
            temp = number % Constants.MAX_PAGE_SIZE;
            result += getDigit((int) (temp / Constants.MAX_ENTITY_DISPLAY_SIZE) * Constants.TWO);
            number = number / Constants.MAX_PAGE_SIZE;
        }

        return result;
    }

    public static boolean prefixMatched(long number, int d) {

		if ((getPrefix(number, d) != 0)) {
			logger.info("\nChatak Prepaid Card ! ");
			return true;
		} else {
			logger.info("prefixMatched :: unknown card");
			return false;

		}
    }

    public static int getSize(long d) {

        int count = 0;

        while (d > 0) {
            d = d / Constants.MAX_ENTITY_DISPLAY_SIZE;

            count++;
        }

        return count;

    }

    /**
     * Return the first k number of digits from number. If the number of digits
     * in number is less than k, return number.
     */
    public static long getPrefix(long number, int k) {

        if (getSize(number) < k) {
            return number;
        } else {

            int size = (int) getSize(number);

            for (int i = 0; i < (size - k); i++) {
                number = number / Constants.MAX_ENTITY_DISPLAY_SIZE;
            }

            return number;

        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       logger.info("Enter your Card Number : ");
        long input = sc.nextLong();
        if (isValid(input) == true) {
            logger.info("\n*****Your card is Valid*****");
        } else {
        	logger.info("\n!!!!Your Card is not Valid !!!!! ");
        }

    }
}