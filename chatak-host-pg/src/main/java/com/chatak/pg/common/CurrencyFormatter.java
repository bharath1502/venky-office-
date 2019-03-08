package com.chatak.pg.common;

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chatak.pg.acq.processor.PGMessage;
import com.chatak.pg.exception.InvalidCurrencyFormatException;
import com.chatak.pg.server.coreLauncher.PaymentGateway;

/**
 *  This class is used to convert the currency value into various
 *           format.
 */
public class CurrencyFormatter {


  private final static String ZEROS_27 = "000000000000000000000000000";

  static  Logger logger = LogManager.getLogger(CurrencyFormatter.class);  

  
  public String convertTo8583FormattedAmount(Double amt) {
    String bal = String.valueOf(amt) + "00";
    int idxOfDecimal = bal.indexOf('.');
    if(idxOfDecimal < 0) {
      idxOfDecimal = String.valueOf(amt).length();
    }
    String tmpBal = bal.substring(0, idxOfDecimal) + bal.substring((++idxOfDecimal), (idxOfDecimal + 2));
    return  addLeadingZeros(tmpBal, 12);
  }

  /**
   * This method adding the leading zero's for string
   * 
   * @param str
   * @param length
   * @return String
   */
  private String addLeadingZeros(String str, int length) {
    // Converts String to left-zero padded string, len chars long.
    if(str.length() > length)
      return str.substring(0, length);
    else if(str.length() < length) // pad on left with zeros
      return ZEROS_27.substring(0, length - str.length()) + str;
    else
      return str;
  }

  
  /**
   * This method used for amount value convert into decimal format
   * 
   * @param value
   * @param format
   * @return String
   */
  public String getDecimalFormat(double value, String format) {
    String retVal = null;
    if(format == null)
      format = "####.##";
    DecimalFormat df = new DecimalFormat(format);

    value += 0.0005;
    retVal = df.format(value);

    return retVal;
  }

  /**
   * This method used for amount convert into float value
   * 
   * @param value
   * @return float
   */
  public float getFloatFormat(float value) {
    String retVal = null;
    String format = "####.##";
    float floatValue = 0;
    DecimalFormat df = new DecimalFormat(format);
    value += 0.0005;
    retVal = df.format(value);
    floatValue = Float.parseFloat(retVal);
    return floatValue;
  }
  
  /**
   * Description: This method parses the AmountTransaction (Field 4), converts to decimal format
   * 
   * @param : String value of Field 4 (amountTransactionValue)
   * @return : double
   */
  public double convertCurrencyFormat(String origAmt) throws InvalidCurrencyFormatException {
    if(origAmt == null)
      throw new InvalidCurrencyFormatException("Original amount is null");

    double currencyValue = 0;
    try {
    	Double valueAmount_Double = new Double(origAmt);
    	currencyValue = valueAmount_Double.doubleValue();
    	currencyValue = currencyValue / 100;
    }
    catch(Exception e) {
      String msg = "Currency format conversion error";
      throw new InvalidCurrencyFormatException(msg);
    }
  
    return currencyValue;
  }
  
  public static void main(String[] args) {
	logger.info("test");
	CurrencyFormatter currencyFormatter = new CurrencyFormatter();
	try {
		double amount = currencyFormatter.convertCurrencyFormat("000000001118");
		logger.info(amount);
		logger.info(currencyFormatter.getDecimalFormat(amount, "##########.##"));
		logger.info(currencyFormatter.convertTo8583FormattedAmount(amount));
	} catch (InvalidCurrencyFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}