package com.chatak.pg.util;

/**
 * This class helps for reward and transaction based constants.
 */
public class TransactionConstants {
	
	private TransactionConstants() {
		
	}

  // SMS templates for payment gateway transaction
  public static final int REGISTER_SMS_TEMPLATE_ID = 1;

  public static final int PURCHASE_SMS_TEMPLATE_ID = 2;

  public static final int DEPOSIT_SMS_TEMPLATE_ID = 3;

  public static final int ACCOUNT_ACTIVATION_SMS_TEMPLATE_ID = 4;

  public static final int DEPOSIT_AND_PURCHASE_SMS_TEMPLATE_ID = 6;

  public static final int REWARD_SMS_TEMPLATE_ID = 111;

  public static final int REDEEM_CAMPAIGN_SMS_TEMPLATE_ID = 204;

  public static final Long GUEST_USER_ACCOUNT_ID = new Long(-100);

  public static final String GUEST_USER_PHONE_NUMBER = "0000000000";

  // Transaction types constants

  public static final String XML_CASHIER_CODE = "4444444444";

  public static final String PG_CASHIER_CODE = "1234567890";

  public static final Long TXN_BALANCE_QUERY = new Long(1);

  public static final Long TXN_PURCHASE_DEBIT = new Long(2);

  public static final Long TXN_DEPOSIT_CREDIT = new Long(3);

  public static final Long TXN_SEND_MONEY_CREDIT = new Long(4);

  public static final Long TXN_SEND_MONEY_DEBIT = new Long(5);

  public static final Long TXN_GENERAL_REWARD_EARN_CREDIT = new Long(6);

  public static final Long TXN_CAMPAIGN_EARN_CREDIT = new Long(7);

  public static final Long TXN_CAMPAIGN_REDEEM_DEBIT = new Long(8);

  public static final Long TXN_CAMPAIGN_EXPIRED_DEBIT = new Long(9);

  public static final Long TXN_GENERAL_REWARD_REDEEM_CREDIT = new Long(10);

  public static final Long TXN_GENERAL_REWARD_EXPIRED_DEBIT = new Long(11);

  public static final Long TXN_OFFER_EARN_CREDIT = new Long(12);

  public static final Long TXN_OFFER_REDEEM_DEBIT = new Long(13);

  public static final Long TXN_OFFER_EXPIRED_DEBIT = new Long(14);

  public static final Long TXN_ADMIN_ONLINE_DEBIT = new Long(15);

  public static final Long TXN_ADMIN_ONLINE_CREDIT = new Long(16);

  public static final Long TXN_ONLINE_TOPUP_CC_CREDIT = new Long(17);

  public static final Long TXN_ONLINE_TOPUP_CC_DEBIT = new Long(18);

  public static final Long TXN_ONLINE_TOPUP_ACH_CREDIT = new Long(19);

  public static final Long TXN_ONLINE_TOPUP_ACH_DEBIT = new Long(20);

  public static final Long TXN_OFFER_PAID_TO_CASHIER = new Long(22);
  
  public static final Long TXN_SETTLEMENT_DO_PAYOUT = new Long(23);
  
  public static final Long TXN_SETTLEMENT_PAID_BY_MERCHANT = new Long(24);
  
  public static final Long TXN_SETTLEMENT_DO_ADD_CREDIT = new Long(25);

  public static final Long TXN_MSG_PUSH_CREDIT = new Long(34);

  public static final Long TXN_MSG_PUSH_DEBIT = new Long(35);
  
  public static final Long TXN_OFFER_REDEMPTION = new Long(36);

  public static final Long TXN_PUSH_REMOVAL = new Long(37);
  
  public static final Long TXN_FISHBOWL_REWARD_EARN = new Long(41);
  
  public static final Long TXN_FISHBOWL_REWARD_REDEEM = new Long(42);
  // Common constants

  public static final Long IS_MERCHANT_REWARD = new Long("1");

  public static final Long IS_SYSTEM_REWARD = new Long("0");

  public static final String SYSTEM_MERCHANT_CODE = "000000000000";

  public static final String SYSTEM_TERMINAL_CODE = "0000000000000000";

  public static final int REWARD_DAY_FLAG = 1;

  public static final int REWARD_TIME_FLAG = 1;

  public static final Long ONLINE_REGISTRATION_REWARD_EVENT = 6L;

  public static final Long ACCOUNT_ACTIVATION_REWARD_EVENT = 8L;

  public static final Long DEPOSIT_REWARD_EVENT = 9L;

  public static final Long PURCHASE_REWARD_EVENT = 10L;

  public static final String REWARD_EARNED_DESC = "Reward Earned by ";

  public static final Long SYSTEM_FUNDID = new Long(0);

  public static final String ADMIN_CASHIER_CODE = "1111111111";

  public static final String APPROVAL_CODE = new String("0000");

  public static final String CC_TRANS_DESC = "Order is placed with Credit card Number XXXX....";

  // Check pgle offers process constants

  public static final int POS_PRINT_MAX_CHAR_LENGTH = 25;

  public static final int POS_TOTAL_OFFER_LIST_LENGTH = 4;

  public static final int POS_OFFER_LIST_LENGTH = 2;

  public static final int POS_OFFER_ID_LENGTH = 2;

  public static final int POS_OFFER_NAME_LENGTH = 2;

  public static final int POS_OFFER_DESCRIPTION_LENGTH = 2;

  public static final int POS_OFFER_AMOUNT_LENGTH = 12;

  public static final int POS_OFFER_TYPE_LENGTH = 1;

  public static final String POS_OFFER_TOTAL_AMOUNT = "Total offer(s) - ";

  public static final String POS_LINE_BREAK = "\r\n";

  public static final String POS_DOLLAR_SYMBOL = "$";

  public static final String POS_SEPERATE_LINE = "----------------------------------------";

  public static final String POS_REDEEM_COUPONS_HEADER = "Redeemed Coupons";

  public static final String REDEEM_CAMPAIGN_DESC = "Redeemed the Offer/Campaign - ";

  public static final String MOBILE_OFFER_TYPE_LEGACY = "0";

  public static final String MOBILE_OFFER_TYPE_PUSH = "1";
  
  public static final String MOBILE_OFFER_TYPE_FB_REWARD = "2";

  public static final int MOBILE_OFFER_DESC_LEN = 40;

  public static final int MOBILE_OFFER_NAME_LEN = 15;
  
  public static final int CARD_NUM_LENGTH = 19;
  
  public static final int EXP_DATE_LENGTH = 4;
  
  public static final String APPEND_LAST_3_DIGIT = "123";
  

}