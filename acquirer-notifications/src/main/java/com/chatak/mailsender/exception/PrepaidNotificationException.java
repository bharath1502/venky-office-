package com.chatak.mailsender.exception;

import com.chatak.prepaid.notification.constants.ErrorCode;

public class PrepaidNotificationException extends Exception {

	private static final long serialVersionUID = -2906943075351241595L;
	
	private String errorCode;
	
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public PrepaidNotificationException(Exception ex) {
		initCause(ex);
		setErrorCode(ErrorCode.GENERAL_NOTIFICATION_ERROR_CODE);
		setErrorMessage(ErrorCode.GENERAL_NOTIFICATION_ERROR_MESSAGE);
	}
	
	public PrepaidNotificationException(String errorCode, String errorMessage) {
		setErrorCode(errorCode);
		setErrorMessage(errorMessage);
	}

	public PrepaidNotificationException(Exception ex, String errorCode, String message) {
		initCause(ex);
		setErrorCode(errorCode);
		setErrorMessage(message);
	}

	public PrepaidNotificationException(Exception ex, String errorCode) {
		initCause(ex);
		setErrorCode(errorCode);
		setErrorMessage(ErrorCode.GENERAL_NOTIFICATION_ERROR_MESSAGE);
	}
}
