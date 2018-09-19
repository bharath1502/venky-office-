package com.chatak.pg.constants;


import java.util.HashMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ActionErrorCode {


	private static HashMap mMessages;

	private static ActionErrorCode mInstance = null;

	public static final String ERROR_CODE_00  =  "00";
	public static final String ERROR_CODE_01  =  "01";
	public static final String ERROR_CODE_02  =  "02";
	public static final String ERROR_CODE_03  =  "03";
	public static final String ERROR_CODE_04  =  "04";
	public static final String ERROR_CODE_05  =  "05";
	public static final String ERROR_CODE_06  =  "06";
	public static final String ERROR_CODE_07  =  "07";
	public static final String ERROR_CODE_08  =  "08";
	public static final String ERROR_CODE_09  =  "09";
	public static final String ERROR_CODE_10  =  "10";
	public static final String ERROR_CODE_11  =  "11";
	public static final String ERROR_CODE_12  =  "12";
	public static final String ERROR_CODE_13  =  "13";
	public static final String ERROR_CODE_14  =  "14";
	public static final String ERROR_CODE_15  =  "15";
	public static final String ERROR_CODE_16  =  "16";
	public static final String ERROR_CODE_17  =  "17";
	public static final String ERROR_CODE_18  =  "18";
	public static final String ERROR_CODE_19  =  "19";
	public static final String ERROR_CODE_20  =  "20";
	public static final String ERROR_CODE_21  =  "21";
	public static final String ERROR_CODE_22  =  "22";
	public static final String ERROR_CODE_23  =  "23";
	public static final String ERROR_CODE_24  =  "24";
	public static final String ERROR_CODE_25  =  "25";
	public static final String ERROR_CODE_26  =  "26";
	public static final String ERROR_CODE_27  =  "27";
	public static final String ERROR_CODE_28  =  "28";
	public static final String ERROR_CODE_29  =  "29";
	public static final String ERROR_CODE_30  =  "30";
	public static final String ERROR_CODE_31  =  "31";
	public static final String ERROR_CODE_32  =  "32";
	public static final String ERROR_CODE_33  =  "33";
	public static final String ERROR_CODE_34  =  "34";
	public static final String ERROR_CODE_35  =  "35";
	public static final String ERROR_CODE_36  =  "36";
	public static final String ERROR_CODE_37  =  "37";

     /* Reserved for ISO use */	public static final String ERROR_CODE_47 =  "47";
	public static final String ERROR_CODE_48 = "48";
	public static final String ERROR_CODE_49 = "49";
	public static final String ERROR_CODE_50 = "50";
	public static final String ERROR_CODE_51 = "51";
	public static final String ERROR_CODE_52 = "52";
	public static final String ERROR_CODE_53 = "53";
	public static final String ERROR_CODE_54 = "54";
	public static final String ERROR_CODE_55 = "55";
	public static final String ERROR_CODE_56 = "56";
	public static final String ERROR_CODE_57 = "57";
	public static final String ERROR_CODE_58 = "58";
	public static final String ERROR_CODE_59 = "59";
	public static final String ERROR_CODE_60 = "60";
	public static final String ERROR_CODE_61 = "61";
	public static final String ERROR_CODE_62 = "62";
	public static final String ERROR_CODE_63 = "63";
	public static final String ERROR_CODE_64 = "64";
	public static final String ERROR_CODE_65 = "65";
	public static final String ERROR_CODE_66 = "66";
	public static final String ERROR_CODE_67 = "67";
	public static final String ERROR_CODE_68 = "68";
	public static final String ERROR_CODE_69 = "69";
	public static final String ERROR_CODE_70 = "70";
	public static final String ERROR_CODE_71 = "71";
	public static final String ERROR_CODE_72 = "72";
	public static final String ERROR_CODE_73 = "73";
	public static final String ERROR_CODE_74 = "74";
	public static final String ERROR_CODE_75 = "75";
	public static final String ERROR_CODE_76 = "76";
	public static final String ERROR_CODE_77 = "77";
	public static final String ERROR_CODE_78 = "78";
	public static final String ERROR_CODE_79 = "79";
	public static final String ERROR_CODE_80 = "80";
	public static final String ERROR_CODE_81 = "81";
	public static final String ERROR_CODE_82 = "82";
	public static final String ERROR_CODE_83 = "83";
	public static final String ERROR_CODE_84 = "84";
	public static final String ERROR_CODE_85 = "85";
	public static final String ERROR_CODE_86 = "86";
	public static final String ERROR_CODE_87 = "87";
	public static final String ERROR_CODE_88 = "88";
	public static final String ERROR_CODE_89 = "89";
	public static final String ERROR_CODE_90 = "90";
	public static final String ERROR_CODE_91 = "91";
	public static final String ERROR_CODE_92 = "92";
	public static final String ERROR_CODE_93 = "93";
	public static final String ERROR_CODE_94 = "94";
	public static final String ERROR_CODE_95 = "95";
	public static final String ERROR_CODE_96 = "96";
	public static final String ERROR_CODE_97 = "97";
	public static final String ERROR_CODE_98 = "98";
	public static final String ERROR_CODE_99 = "99";
	
	public static final String ERROR_CODE_201 = "201";
	
	public static final String ERROR_CODE_202 = "202";

	public static final String ERROR_CODE_Y1 = "Y1";
	public static final String ERROR_CODE_Y3 = "Y3";
	public static final String ERROR_CODE_Z1 = "Z1";
	public static final String ERROR_CODE_Z3 = "Z3";
	
	public static final String ERROR_CODE_Z5 = "Z5";
	public static final String ERROR_CODE_Z6 = "Z6";
	public static final String ERROR_CODE_Z7 = "Z7";
	public static final String ERROR_CODE_Z8 = "Z8";
	public static final String ERROR_CODE_Z9 = "Z9";
	public static final String ERROR_CODE_Z10 = "Z10";
	public static final String ERROR_CODE_Z11 = "Z11";
	public static final String ERROR_CODE_Z12 = "Z12";
	
	//Rest Service Error codes
	public static final String ERROR_CODE_PG_SERVICE = "R0";
	public static final String ERROR_CODE_DUPLICATE_ENTRY = "R1";
	public static final String ERROR_CODE_DUPLICATE_VOID_ENTRY = "R2";
	public static final String ERROR_CODE_TXN_NULL = "R2001";
	public static final String ERROR_CODE_TXN_EXECUTED = "R2002";
	public static final String ERROR_CODE_DUPLICATE_REFUND_ENTRY = "R3";
	public static final String ERROR_CODE_DUPLICATE_CAPTURE_ENTRY = "R4";
	public static final String ERROR_CODE_SAME_PAN_SAME_AMOUNT = "R5";
	public static final String ERROR_CODE_DUPLICATE_INVOICE = "R6";
	public static final String REFUND_AMOUNT_EXCEEDS = "R7";
	public static final String REFUND_AMOUNT_INVALID = "R8";
	public static final String INVALID_PARENT_MERCHANT_CODE = "M01";
	
	//Bank Error codes
	public static final String ERROR_CODE_B0 = "B0";
	public static final String ERROR_CODE_B1 = "B1";
  public static final String ERROR_CODE_B2 = "B2";
  public static final String ERROR_CODE_B3 = "B3";
  public static final String ERROR_CODE_B4 = "B4";
  public static final String ERROR_CODE_B5 = "B5";
  public static final String ERROR_CODE_BANK_LINKED = "B6";
  
//MCC Error codes
  public static final String ERROR_CODE_MCC0 = "MCC0";
  public static final String ERROR_CODE_MCC1 = "MCC1";
  public static final String ERROR_CODE_MCC2 = "MCC2";
  public static final String ERROR_CODE_MCC3 = "MCC3";
  public static final String ERROR_CODE_MCC4 = "MCC4";
  public static final String ERROR_CODE_MCC5 = "MCC5";
  
  public static final String ERROR_CODE_BIN_01 ="BIN_01";
  
  public static final String ERROR_CODE_MDR1 = "MDR1";
  public static final String ERROR_CODE_API_CONNECT = "CONNECT";

//FeeProgram Error codes
  public static final String ERROR_CODE_F1 = "F1";
  public static final String ERROR_CODE_F2 = "F2";
  public static final String ERROR_CODE_F3 = "F3";
  
  // Currency Error codes
  public static final String ERROR_CODE_CURRENCY_DELETE = "C1";
  public static final String ERROR_CODE_CURRENCY_LINKED = "C2";
  public static final String ERROR_CODE_CURRENCY_CREATE = "C3";
  
  //TimeZone
  public static final String ERROR_CODE_TIME_ZONE_02  =  "T02";
  public static final String ERROR_CODE_TIME_ZONE_03  =  "T03";
  public static final String ERROR_CODE_TIME_ZONE_04  =  "T04";
  public static final String ERROR_CODE_TIME_ZONE_05  =  "T05";
  
  public static final String ERROR_CODE_PM_01  =  "PM01";
  public static final String ERROR_CODE_PM_02  =  "PM02";
  public static final String ERROR_CODE_ISO_01  =  "ISO01";  

	/**
	 * This method get the message based on code
	 *
	 * @param code
	 * @return String
	 */
	public String getMessage(String code) {
		Object obj = mMessages.get(code);
		String msg = null;
		if(obj != null)
			msg = (String) obj;
		else
			msg = "Unknown Message";
		return msg;
	}
	
//Merchant Error codes
  
  public static final String MERCHANT_LINKED_WITH_TRANSACTION = "M1";
  
  /** Default Constructor */
  public ActionErrorCode()
  {
    initMessages();
  }

	private static void initMessages()
	{
		 mMessages = new HashMap(Integer.parseInt("125"));
		 mMessages.put(ERROR_CODE_01 ,"Call Issuer");
		 mMessages.put(ERROR_CODE_00 ,"Approved");
		 mMessages.put(ERROR_CODE_02 ,"Call Issuer");
		 mMessages.put(ERROR_CODE_04 ,"Declined pick up card");
		 mMessages.put(ERROR_CODE_03 ,"Invalid Merchant");
		 mMessages.put(ERROR_CODE_05 ,"Do not honour trans declined");
		 mMessages.put(ERROR_CODE_07 ,"Pick-up card, special conditions");
		 mMessages.put(ERROR_CODE_06 ,"Invalid Format");
		 mMessages.put(ERROR_CODE_08 ,"Approved verify ID & SIGNATURE");
		 mMessages.put(ERROR_CODE_10 ,"Approved for partial amount");
		 mMessages.put(ERROR_CODE_09 ,"Please wait");
		 mMessages.put(ERROR_CODE_11 ,"Approved (VIP)");
		 mMessages.put(ERROR_CODE_13 ,"Invalid amount");
		 mMessages.put(ERROR_CODE_12 ,"Invalid transaction");
		 mMessages.put(ERROR_CODE_14 ,"Invalid card number");
		 mMessages.put(ERROR_CODE_16 ,"Approved, update Track 3");
		 mMessages.put(ERROR_CODE_15 ,"No such issuer");
		 mMessages.put(ERROR_CODE_17 ,"Customer cancellation, reversal");
		 mMessages.put(ERROR_CODE_19 ,"Re-enter transaction");
		 mMessages.put(ERROR_CODE_18 ,"Retry");
		 mMessages.put(ERROR_CODE_20 ,"Invalid response");
		 mMessages.put(ERROR_CODE_23 ,"Unacceptable transaction fee");
		 mMessages.put(ERROR_CODE_21 ,"Retry");
		 mMessages.put(ERROR_CODE_24 ,"DECLINE # 24");
		 mMessages.put(ERROR_CODE_22 ,"Retry");
		 mMessages.put(ERROR_CODE_25 ,"RETRY # 25");
		 mMessages.put(ERROR_CODE_27 ,"RETRY # 27");
		 mMessages.put(ERROR_CODE_26 ,"DECLINE # 26");
		 mMessages.put(ERROR_CODE_28 ,"RETRY # 28");
		 mMessages.put(ERROR_CODE_30 ,"Format error");
		 mMessages.put(ERROR_CODE_29 ,"RETRY # 29");
		 mMessages.put(ERROR_CODE_31 ,"RETRY # 31");
		 mMessages.put(ERROR_CODE_33 ,"Expired card, pick-up");
		 mMessages.put(ERROR_CODE_32 ,"RETRY # 32");
		 mMessages.put(ERROR_CODE_34 ,"Suspected fraud, pick-up");
		 mMessages.put(ERROR_CODE_36 ,"Restricted card, pick-up");
		 mMessages.put(ERROR_CODE_35 ,"Card acceptor contact acquirer, pick-up");
		 mMessages.put(ERROR_CODE_37 ,"Card acceptor call acquirer security, pick-up");
		 mMessages.put(ERROR_CODE_51 ,"Insufficient funds");
		 mMessages.put(ERROR_CODE_48 ,"Reserved for ISO use");
		 mMessages.put(ERROR_CODE_52 ,"No checking account");
		 mMessages.put(ERROR_CODE_47 ,"Reserved for ISO use");
		 mMessages.put(ERROR_CODE_49 ,"Reserved for IO use");
		 mMessages.put(ERROR_CODE_50 ,"Reserved for ISO use");
		 mMessages.put(ERROR_CODE_53 ,"No savings account");
		 mMessages.put(ERROR_CODE_55 ,"Incorrect PIN");
		 mMessages.put(ERROR_CODE_54 ,"Expired card");
		 mMessages.put(ERROR_CODE_56 ,"No card record");
		 mMessages.put(ERROR_CODE_58 ,"Transaction not permitted to terminal");
		 mMessages.put(ERROR_CODE_57 ,"Transaction not permitted to cardholder");
		 mMessages.put(ERROR_CODE_59 ,"Suspected fraud");
		 mMessages.put(ERROR_CODE_61 ,"Exceeds amount limit");
		 mMessages.put(ERROR_CODE_60 ,"Card acceptor contact acquirer");
		 mMessages.put(ERROR_CODE_62 ,"Restricted card");
		 mMessages.put(ERROR_CODE_64 ,"RETRY");
		 mMessages.put(ERROR_CODE_63 ,"Security violation");
		 mMessages.put(ERROR_CODE_65 ,"Exceeds count");
		 mMessages.put(ERROR_CODE_67 ,"Hard capture, pick-up");
		 mMessages.put(ERROR_CODE_66 ,"Card acceptor call acquirer security");
		 mMessages.put(ERROR_CODE_68 ,"RETRY");
		 mMessages.put(ERROR_CODE_75 ,"PIN tries exceeded");
		 mMessages.put(ERROR_CODE_69 ,"Reserved for ISO");
		 mMessages.put(ERROR_CODE_77 ,"RETRY # 77");
		 mMessages.put(ERROR_CODE_70 ,"Reserved for ISO");
		 mMessages.put(ERROR_CODE_78 ,"Customer not eligible for POS");
		 mMessages.put(ERROR_CODE_71 ,"Reserved for ISO");
		 mMessages.put(ERROR_CODE_79 ,"Invalid Digital Signature");
		 mMessages.put(ERROR_CODE_72 ,"Reserved for ISO");
		 mMessages.put(ERROR_CODE_80 ,"Bad Batch NO");
		 mMessages.put(ERROR_CODE_73 ,"Reserved for ISO");
		 mMessages.put(ERROR_CODE_81 ,"Issuer requested stand-in");
		 mMessages.put(ERROR_CODE_74 ,"Reserved for ISO");
		 mMessages.put(ERROR_CODE_82 ,"Initiate key exchange");
		 mMessages.put(ERROR_CODE_76 ,"RETRY # 76");
		 mMessages.put(ERROR_CODE_83 ,"Reserved for private use");
		 mMessages.put(ERROR_CODE_84 ,"Reserved for private use");
		 mMessages.put(ERROR_CODE_85 ,"Reserved for private use");
		 mMessages.put(ERROR_CODE_86 ,"Reserved for private use");
		 mMessages.put(ERROR_CODE_87 ,"Reserved for private use");
		 mMessages.put(ERROR_CODE_88 ,"Information not on file");
		 mMessages.put(ERROR_CODE_89 ,"Card verification value (CVV) verification failed (no pick-up)");
		 mMessages.put(ERROR_CODE_90 ,"Cutoff in progress");
		 mMessages.put(ERROR_CODE_91 ,"Issuer or switch is inoperative");
		 mMessages.put(ERROR_CODE_92 ,"Financial institution or intermediate network unknown for routing");
		 mMessages.put(ERROR_CODE_93 ,"DECLINED # 93");
		 mMessages.put(ERROR_CODE_94 ,"Duplication transaction");
		 mMessages.put(ERROR_CODE_95 ,"Total Mismatch Reconcile error");//Only for Settlements
		 mMessages.put(ERROR_CODE_96 ,"System Malfunction"); //If Mandatory fields are absent OR the Packet contains invalid characters
		 mMessages.put(ERROR_CODE_97 ,"Reserved for national use");
		 mMessages.put(ERROR_CODE_98 ,"Reserved for national use");
		 mMessages.put(ERROR_CODE_99 ,"Reserved for national use");
		 mMessages.put(ERROR_CODE_Y1 ,"OFFLINE APPROVED 1");//EMV transaction - Offline approved by terminal
		 mMessages.put(ERROR_CODE_Y3 ,"OFFLINE APPROVED 3"); //EMV transaction - Unable to go online; Offline approved by terminal
		 mMessages.put(ERROR_CODE_Z1 ,"OFFLINE DECLINED 1");//EMV transaction - Offline declined at terminal
		 mMessages.put(ERROR_CODE_Z3 ,"OFFLINE DECLINED 3");//EMV transaction - Unable to go online; Offline declined at terminal
		 mMessages.put(ERROR_CODE_Z5 ,"General Error");//Error for General Exception
		 mMessages.put(ERROR_CODE_Z6 ,"Mandatory fields missing");//Error for Mandatory fields missing
		 mMessages.put(ERROR_CODE_Z7 ,"Token expired");//Error for Oauth token expiry
		 mMessages.put(ERROR_CODE_Z8 ,"Duplicate Merchant Username");
		 mMessages.put(ERROR_CODE_Z10 ,"Duplicate Reseller Name");
		 mMessages.put(ERROR_CODE_Z11 ,"Duplicate Switch Name");
		 mMessages.put(ERROR_CODE_Z12 ,"Processor Error");
		 mMessages.put(ERROR_CODE_PG_SERVICE ,"PG Service Error");//Any errors/Exception Trying to communicate with PG
		 mMessages.put(ERROR_CODE_DUPLICATE_ENTRY ,"Duplicate Record");//Duplicate Record error
		 mMessages.put(ERROR_CODE_DUPLICATE_VOID_ENTRY ,"Duplicate Record or Transaction already voided");
		 mMessages.put(ERROR_CODE_TXN_NULL ,"Invalid Transaction Id");
		 mMessages.put(ERROR_CODE_TXN_EXECUTED ,"Cannot Void an Executed Transaction");
		 mMessages.put(ERROR_CODE_DUPLICATE_REFUND_ENTRY ,"Duplicate Record or refund already done ");
		 mMessages.put(ERROR_CODE_DUPLICATE_CAPTURE_ENTRY ,"Duplicate Record or Auth completion already done ");
		 mMessages.put(ERROR_CODE_Z9 ,"Invalid Network management code from acquirer");
		 mMessages.put(ERROR_CODE_SAME_PAN_SAME_AMOUNT,"Transaction blocked as same card with same amount within block time");
		 mMessages.put(ERROR_CODE_DUPLICATE_INVOICE,"Duplicate invoice,transaction declined");
		 mMessages.put(REFUND_AMOUNT_EXCEEDS,"Refund Amount Exceeds Original Transaction Amount");
		 mMessages.put(INVALID_PARENT_MERCHANT_CODE,"Invalid Primary Merchant Code");
		 mMessages.put(REFUND_AMOUNT_INVALID,"Refund Amount Not Equal to Original Transaction Amount");
		 
		 mMessages.put(ERROR_CODE_201, "Invalid Account Number");
		 mMessages.put(ERROR_CODE_202, "Error in getting Account details");
		 
		 mMessages.put(ERROR_CODE_B0 ,"New Bank has been created successfully");
		 mMessages.put(ERROR_CODE_B1 ,"Duplicte Bank Name");
		 
		 mMessages.put(ERROR_CODE_B2 ,"Bank has been deleted successfully");
		 mMessages.put(ERROR_CODE_B3 ,"Bank deletion error");
		 
		 mMessages.put(ERROR_CODE_B4 ,"Bank updated successfully");
		 
		 mMessages.put(ERROR_CODE_B5 ,"Bank not found");
		 
		 mMessages.put(ERROR_CODE_BIN_01 , "No Bin Found");
		 
		 mMessages.put(ERROR_CODE_MCC0 ,"New Merchant Category Code created successfully");
		 mMessages.put(ERROR_CODE_MCC1 ,"Duplicate Merchant Category Code");
		 mMessages.put(ERROR_CODE_MCC2 ,"Merchant Category Code updated successfully");
		 mMessages.put(ERROR_CODE_MCC3 ," Error in Merchant Category Code Updation");
		 
		 mMessages.put(ERROR_CODE_MDR1 ,"Duplicate MDR Bin Number");
		 mMessages.put(ERROR_CODE_API_CONNECT ,"Unable to connect to API server,Please try again");
		 
		 mMessages.put(ERROR_CODE_F1 ,"There exists a Fee Program with the same name, please choose a different name");
		 mMessages.put(ERROR_CODE_F2 ,"FeeProgram has been deleted successfully");
		 mMessages.put(ERROR_CODE_F3 ,"FeeProgram deletion error");
		 
		 mMessages.put(ERROR_CODE_TIME_ZONE_02 ,"Time Zone Id is NULL");
		 mMessages.put(ERROR_CODE_TIME_ZONE_03 ," Error in retrieving all the time zone");
		 mMessages.put(ERROR_CODE_TIME_ZONE_04 ," Error in Merchant Category Code Updation");
		 mMessages.put(ERROR_CODE_TIME_ZONE_05 ," Error in Merchant Category Code Updation");
		 mMessages.put(ERROR_CODE_PM_01 ,"Program manager not associated");
		 mMessages.put(ERROR_CODE_PM_02 ,"Program manager not active");
		 mMessages.put(ERROR_CODE_ISO_01 ,"ISO not active");
	}

	/**
	 * Returns the singleton instance.
	 *
	 * @return the singleton instance
	 */
	public static final synchronized ActionErrorCode getInstance() {
		if(mInstance == null) {
			mInstance = new ActionErrorCode();
		}
		return mInstance;
	}

}
