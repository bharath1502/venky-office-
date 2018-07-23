package com.chatak.prepaid.notification.constants;

public interface ErrorCode {
	public static final String SUCCESS_CODE = "0000";

	public static final String SUCCESS_MESSAGE = "SUCCESS";

	public static final String GENERAL_NOTIFICATION_ERROR_CODE = "GN_001";

	public static final String GENERAL_NOTIFICATION_ERROR_MESSAGE = "Error in the notification service";

	public static final String CONFIGURATION_ERROR_CODE = "MAIL_001";

	public static final String CONFIGURATION_ERROR_MESSAGE = "Error in the email configurations";

	public static final String MAIL_SEND_ERROR_CODE = "MAIL_002";

	public static final String MAIL_SEND_ERROR_MESSAGE = "Error in sending mail";

}
