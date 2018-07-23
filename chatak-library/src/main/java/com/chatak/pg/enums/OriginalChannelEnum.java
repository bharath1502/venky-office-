package com.chatak.pg.enums;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 01-Apr-2015 3:42:44 PM
 * @version 1.0
 */
public enum OriginalChannelEnum {
MERCHANT_WEB("MERCHANT_WEB"),
ADMIN_WEB("ADMIN_WEB"),
MOBILE("Mob"),
POS("POS"),
WEB("Web");

	private final String value;

	OriginalChannelEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static OriginalChannelEnum fromValue(String v) {
		for (OriginalChannelEnum c : OriginalChannelEnum.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
