package com.chatak.pg.util;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CreditCardValidation {
	
	static  Logger logger = LogManager.getLogger(CreditCardValidation.class);

    /**
     * @param args the command line arguments
     */
    public static boolean isValid(long number) {

        int total = sumOfDoubleEvenPlace(number) + sumOfOddPlace(number);

        if ((total % 10 == 0) && (prefixMatched(number, 1) == true)) {
            return true;
        } else {
            return false;
        }
    }

    public static int getDigit(int number) {

        if (number <= 9) {
            return number;
        } else {
            int firstDigit = number % 10;
            int secondDigit = (int) (number / 10);

            return firstDigit + secondDigit;
        }
    }

    public static int sumOfOddPlace(long number) {
        int result = 0;

        while (number > 0) {
            result += (int) (number % 10);
            number = number / 100;
        }

        return result;
    }

    public static int sumOfDoubleEvenPlace(long number) {

        int result = 0;
        long temp = 0;

        while (number > 0) {
            temp = number % 100;
            result += getDigit((int) (temp / 10) * 2);
            number = number / 100;
        }

        return result;
    }

    public static boolean prefixMatched(long number, int d) {

        if ((getPrefix(number, d) == 3)
                || (getPrefix(number, d) == 4)
                || (getPrefix(number, d) == 5)
                || (getPrefix(number, d) == 6)) {

            if (getPrefix(number, d) == 3) {
            	logger.info("\nAmerican Express Card ! ");
            } else if (getPrefix(number, d) == 4) {
            	logger.info("\nVisa Card ! ");
            } else if (getPrefix(number, d) == 5) {
            	logger.info("\nMaster Card !");
            } else if (getPrefix(number, d) == 6) {
            	logger.info("\nDiscover Card !");
            }

            return true;
        
        } else {

            return false;

        }
    }

    public static int getSize(long d) {

        int count = 0;

        while (d > 0) {
            d = d / 10;

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
                number = number / 10;
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