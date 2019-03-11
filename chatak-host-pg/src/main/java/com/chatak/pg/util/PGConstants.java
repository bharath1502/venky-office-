package com.chatak.pg.util;

public class PGConstants {
	
	private PGConstants() {
		
	}

	// Transaction Types
	public static final String TXN_TYPE_SALE = "sale";
	public static final String TXN_TYPE_AUTH = "auth";
	public static final String TXN_TYPE_SALE_ADJ = "sale-adj";
	public static final String TXN_TYPE_VOID = "void";
	public static final String TXN_TYPE_REVERSAL = "reversal";
	public static final String TXN_TYPE_REFUND = "refund";
	public static final String TXN_TYPE_BALANCE_ENQ = "balance-enquiry";
	public static final String TXN_TYPE_CASH_WITHDRAWAL = "cash-withdrawal";
	public static final String TXN_TYPE_CASH_BACK = "cash-back";

	public static final String PAYMENT_METHOD_DEBIT = "debit";
	public static final String PAYMENT_METHOD_CREDIT = "credit";
	
	

	// Status
	public static final Integer STATUS_SUCCESS = 0;
	public static final Integer STATUS_INPROCESS = 1;
	public static final Integer STATUS_FAILED = 2;
	public static final Integer STATUS_DECLINED = 3;

	// Field Lengths
	public static final Integer LENGTH_TXN_REF_NUM = 12;
	public static final Integer LENGTH_AUTH_ID = 6;

	public static final String VOID_TXN_AMOUNT = "00";

	// POS entry mode values for Chip Transactions
	public static final String POS_ENTRY_MODE_CHIP_TXN_05 = "05";

	public static final String POS_ENTRY_MODE_CHIP_TXN_95 = "95";

	// Service code of Track-2 for Chip Fallback Transactions
	public static final String SERVICE_CODE_2 = "2";

	public static final String SERVICE_CODE_6 = "6";
	
	public static final String POS_ENTRY_MODE_CHIP_FALLBACK_TXN_80 = "80";
	
}