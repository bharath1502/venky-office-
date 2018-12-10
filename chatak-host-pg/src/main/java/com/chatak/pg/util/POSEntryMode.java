package com.chatak.pg.util;

/**
 *
 * Interface to have POS entry mode type constants
 *
 * @author Girmiti Software
 * @date 19-Dec-2014 5:31:34 pm
 * @version 1.0
 */
public interface POSEntryMode {

	//Pos Entry mode
	//Manual
	public static final String MANUAL_WITH_PIN_UNSPECIFIED = "010"; //MANUAL WITH PIN ENTRY CAPABILITY UNSPECIFIED
	public static final String MANUAL_WITH_PIN = "011"; //MANUAL WITH PIN ENTRY CAPABILITY
	public static final String MANUAL_WITH_NO_PIN = "012"; //MANUAL WITH NO PIN ENTRY CAPABILITY

	//Magnetic Stripe
	public static final String SWIPE_WITH_PIN_UNSPECIFIED = "020"; //Magnetic Stripe WITH PIN ENTRY CAPABILITY UNSPECIFIED
	public static final String SWIPE_WITH_PIN = "021"; //Magnetic Stripe WITH PIN ENTRY CAPABILITY
	public static final String SWIPE_WITH_NO_PIN = "022"; //Magnetic Stripe WITH NO PIN ENTRY CAPABILITY

	//Icc Read
	public static final String ICC_READ_WITH_PIN_UNSPECIFIED = "050"; //Icc Read WITH PIN ENTRY CAPABILITY UNSPECIFIED
	public static final String ICC_READ_WITH_PIN = "051"; //Icc Read WITH PIN ENTRY CAPABILITY
	public static final String ICC_READ_WITH_NO_PIN = "052"; //Icc Read WITH NO PIN ENTRY CAPABILITY

	public static final String ICC_READ_WITH_PIN_UNSPECIFIED_9 = "950"; //Icc Read WITH PIN ENTRY CAPABILITY UNSPECIFIED
	public static final String ICC_READ_WITH_PIN_9 = "951"; //Icc Read WITH PIN ENTRY CAPABILITY
	public static final String ICC_READ_WITH_NO_PIN_9 = "952"; //Icc Read WITH NO PIN ENTRY CAPABILITY

	//Magnetic Stripe even though ICC Capable
	public static final String ICC_SWIPE_WITH_PIN_UNSPECIFIED = "800"; //(ICC Capable)Magnetic Stripe WITH PIN ENTRY CAPABILITY UNSPECIFIED
	public static final String ICC_SWIPE_WITH_PIN = "801"; //(ICC Capable)Magnetic Stripe WITH PIN ENTRY CAPABILITY
	public static final String ICC_SWIPE_WITH_NO_PIN = "802"; //(ICC Capable)Magnetic Stripe WITH NO PIN ENTRY CAPABILITY
}
