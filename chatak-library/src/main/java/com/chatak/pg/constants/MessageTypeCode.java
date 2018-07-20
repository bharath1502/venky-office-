package com.chatak.pg.constants;

/**
 * This interface handles message types
 */
public interface MessageTypeCode {
	//ONLINE : CARD
	//ADVICE : TRANSACTION REFERENCE NUMBER

	//MTI ISO 8583:2003
	public static final String AUTHORIZATION_REQUEST_2003 = "2100"; //Online

	public static final String AUTHORIZATION_RESPONSE_2003 = "2110";

	public static final String ADVICE_REQUEST_2003 = "2220"; //Advice

	public static final String ADVICE_RESPONSE_2003 = "2230";

	public static final String AUTH_CAPTURE_REQUEST_2003 = "2200"; // SALE/PURCHASE TRANSACTION

	public static final String AUTH_CAPTURE_RESPONSE_2003 = "2210";

	public static final String REVERSAL_REQUEST_2003 = "2400"; //ONLINE 

	public static final String REVERSAL_RESPONSE_2003 = "2410";

	public static final String RECONCILATION_REQUEST_2003 = "2500"; //SETTLEMENT

	public static final String RECONCILATION_RESPONSE_2003 = "2510";


	//ISO 8583:1987
	public static final String AUTHORIZATION_REQUEST = "0100"; //Online

	public static final String AUTHORIZATION_RESPONSE = "0110";

	public static final String ONLINE_REQUEST = "0200"; // SALE/PURCHASE TRANSACTION

	public static final String ONLINE_RESPONSE = "0210";
	
	public static final String OFFLINE_REQUEST = "0220"; //Advice

	public static final String OFFLINE_RESPONSE = "0230";

	public static final String REVERSAL_REQUEST = "0400"; //ONLINE 

	public static final String REVERSAL_RESPONSE = "0410";
	
	 public static final String REVERSAL_ADVICE_REQUEST = "0420"; //ONLINE 

	  public static final String REVERSAL_ADVICE_RESPONSE = "0430";

	public static final String RECONCILATION_REQUEST = "0500"; //SETTLEMENT

	public static final String RECONCILATION_RESPONSE = "0510";
	
	public static final String NETWORK_REQUEST = "0800"; // NETWORK TRANSACTION
	
	public static final String NETWORK_RESPONSE = "0810"; 

	//Proc Codes : 1987
	public static final String PROC_CODE_AUTH = "000000"; //ONLINE - AUTH/PURCHASE - new transaction
	
	public static final String PROC_CODE_REFUND = "200000"; //ADVICE/ONLINE - REFUND

	public static final String PROC_CODE_VOID = "200030"; //VOID

	public static final String PROC_CODE_CAPTURE = "000000"; //ADVICE

	public static final String PROC_CODE_PURCHASE = "000000"; //ONLINE

	public static final String PROC_CODE_TIP_ADJUSTMENT = "020000"; //ONLINE/ADVICE - VOID/ADJUST/TIP
	
	public static final String PROC_CODE_REFUND_ADJUSTMENT = "220000"; //ONLINE/ADVICE - VOID/ADJUST/TIP
	
	public static final String PROC_CODE_BALANCE_ENQUIRY = "310000"; //BALANCE ENQUIRY
	
	public static final String PROC_CODE_CASH_WITHDRAWAL = "010000"; //Cash withdrawal
	
	public static final String PROC_CODE_CASH_BACK = "090000"; //Cash back
	
//Proc Codes : 1987
  public static final String PROC_CODE_AUTH_SAV = "001010"; //ONLINE - AUTH/PURCHASE - new transaction
  
  public static final String PROC_CODE_REFUND_SAV = "201010"; //ADVICE/ONLINE - REFUND

  public static final String PROC_CODE_VOID_SAV = "021010"; //ADVICE

  

}