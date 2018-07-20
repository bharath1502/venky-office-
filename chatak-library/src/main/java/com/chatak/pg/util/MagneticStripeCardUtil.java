package com.chatak.pg.util;

import com.chatak.pg.exception.MagneticStripeParseException;

public class MagneticStripeCardUtil {

	/**
	 * Primary Account Number
	 */
  private String pan;

	/**
	 * Card Expiry date format: YYMM 
	 */
  private String expDate;

	/**
	 * Card Expiry year
	 */
  private String expYear;

	/**
	 * Card Expiry month
	 */
  private String expMonth;

	/**
	 * Service Code
	 */
  private String serviceCode;


	//Format of track1 data: %B1234123412341234^CardUser/John^030510100000019301000000877000000?
	public void _parseTrack1(String track1) throws MagneticStripeParseException {
		int t1Start = track1.indexOf('%');
		int t1End = track1.indexOf('?');
		int fs1 = track1.indexOf('^');
		int fs2 = track1.lastIndexOf('^');

		if((t1Start == -1 || t1End == -1 || fs1 == -1 || fs2 == -1) || ((fs1 >= fs2) || (fs2 >= t1End))){
			throw new MagneticStripeParseException("Invalid track 1 data");
		} 

		String accStr = track1.substring(0,fs1);

		// look for starting sentinel and format code
		if( accStr.charAt(0) == '%' ){
			if( ! Character.isDigit(accStr.charAt(1) ) ){
				accStr = accStr.substring(Integer.parseInt("2"));
			}else{
				accStr = accStr.substring(1);
			}
		}
		pan = accStr;

		String accNameStr = track1.substring(fs1+1, fs2);

		int iNameDelim = accNameStr.indexOf('/');
		if( iNameDelim == -1 ) {
			throw new MagneticStripeParseException("Missing delimiter [/] in account holder name data");
		}

		String dateExpStr = track1.substring(fs2+1,fs2+Integer.parseInt("5"));
		expDate = dateExpStr;

		expYear =  dateExpStr.substring(0,Integer.parseInt("2"));
		expMonth =  dateExpStr.substring(Integer.parseInt("2"),Integer.parseInt("4"));

		serviceCode = track1.substring(fs2+Integer.parseInt("5"),fs2+Integer.parseInt("8"));

	}

	//Format of track2 data: ;1234123412341234=0305101193010877?
	// 4617862001085976d140120100125800
	public void _parseTrack2(String track2) throws MagneticStripeParseException {
		int fs = track2.toUpperCase().indexOf('=');
		
		if(fs == -1){
			fs = track2.indexOf('='); //checking for Non-Chip Track2 Data
			if(fs == -1) {
			  fs = track2.indexOf('D'); //checking for Non-Chip Track2 Data
			  if(fs == -1) {
			    throw new MagneticStripeParseException("Invalid track 2 data");
			  }
			}
		}
		
		pan = track2.substring(0,fs);
		if( pan.charAt(0) == ';' ){
			pan = pan.substring(1);
		}
		
		String dateExpStr = track2.substring(fs+1,fs+Integer.parseInt("5"));
		expDate = dateExpStr;

		expYear =  dateExpStr.substring(0,Integer.parseInt("2"));
		expMonth =  dateExpStr.substring(Integer.parseInt("2"),Integer.parseInt("4"));

		serviceCode = track2.substring(fs+Integer.parseInt("5"),fs+Integer.parseInt("8"));
	}

  public String getPan() {
    return pan;
  }

  public void setPan(String pan) {
    this.pan = pan;
  }

  public String getExpDate() {
    return expDate;
  }

  public void setExpDate(String expDate) {
    this.expDate = expDate;
  }

  public String getExpYear() {
    return expYear;
  }

  public void setExpYear(String expYear) {
    this.expYear = expYear;
  }

  public String getExpMonth() {
    return expMonth;
  }

  public void setExpMonth(String expMonth) {
    this.expMonth = expMonth;
  }

  public String getServiceCode() {
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    this.serviceCode = serviceCode;
  }
}
