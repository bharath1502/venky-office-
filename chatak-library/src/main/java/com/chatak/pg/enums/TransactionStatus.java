package com.chatak.pg.enums;

/**
 * @author raj.k
 * 
 */
public enum TransactionStatus {
	INITATE("Initiate"), INIT_COMPLETED("Init Completed"), PROCESSING("Processing"), COMPLETED("Completed"), FALIURE("Failure");

	private final String value;

	TransactionStatus(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static TransactionStatus fromValue(String v) {
		for (TransactionStatus c : TransactionStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
