/**
 * 
 */
package com.chatak.pg.exception;

import java.util.List;

import com.chatak.pg.util.Constants;

/**
 * @author Girmiti Software
 */
public class PrepaidAdminException extends PrepaidException {

  private static final long serialVersionUID = 1L;

  private String httpStatusCode;

  private List<Object> errorParams;

  public PrepaidAdminException(Exception ex, String httpStatusCode, String message, String reason, String errorCode) {
    initCause(ex);
    setHttpStatusCode(httpStatusCode);
    setMessage(message);
    setReason(reason);
    setErrorCode(errorCode);
  }

  public PrepaidAdminException(Exception ex, String exceptionSource) {
    initCause(ex);
    setHttpStatusCode(Constants.HTTP_INTERNAL_SERVER_ERROR);
    setMessage("Error in :" + exceptionSource);
    setReason(ex.getMessage());
    setErrorCode(Constants.GENERIC_MALFORMED_REQUEST);
  }

  public PrepaidAdminException(String exceptionSource) {
    setHttpStatusCode(Constants.HTTP_INTERNAL_SERVER_ERROR);
    setMessage("Error in :" + exceptionSource);
    setErrorCode(Constants.GENERIC_REQUEST_EXCEPTION);
  }

  public PrepaidAdminException(String errorCode, String errorMessage) {
    setErrorCode(errorCode);
    setMessage(errorMessage);
  }

  public PrepaidAdminException(String errorCode, String errorMessage, List<Object> errorParams) {
    setErrorCode(errorCode);
    setMessage(errorMessage);
    setErrorParams(errorParams);
  }

  public List<Object> getErrorParams() {
    return errorParams;
  }

  public void setErrorParams(List<Object> errorParams) {
    this.errorParams = errorParams;
  }

  public String getHttpStatusCode() {
    return httpStatusCode;
  }

  public void setHttpStatusCode(String httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }

}
