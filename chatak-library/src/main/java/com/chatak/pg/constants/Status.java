package com.chatak.pg.constants;

import com.chatak.pg.util.Constants;

public enum Status {
	ACTIVE,
	PENDING,
	PENDINGCREATE,
	PENDINGUPDATE,
	PENDINGSUSPEND,
	PENDINGTERMINATE,
	PENDINGACTIVE,
	SUSPENDED,
	TERMINATED,
	OTPPENDING,
	EMAILPENDING,
	INACTIVE,
	HOLD,
	LINKED,
	DELINKED
	;
	
	/**
	 * Method to get Status enum value
	 * 
	 * @param status
	 * @return
	 */
	public static String valueOf(Integer status) {
		switch (status) {
		case 0:
			return ACTIVE.name();
		case 1:
			return PENDING.name();
		case Constants.TWO_INT:
			return SUSPENDED.name();
		case Constants.THREE_INT:
			return TERMINATED.name();
		case Constants.FOUR_INT:
			return HOLD.name();
		default:
			return "";
		}
	}
}