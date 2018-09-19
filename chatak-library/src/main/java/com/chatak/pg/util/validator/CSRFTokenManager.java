package com.chatak.pg.util.validator;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chatak.pg.exception.PrepaidException;

public class CSRFTokenManager {
  
  private static Logger logger = LogManager.getLogger(CSRFTokenManager.class);

  /**
   * The token parameter name
   */
  public static final String CSRF_PARAM_NAME = "CSRFToken";

  /**
   * The location on the session which stores the token
   */
  
  public final static String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenManager.class.getName() + ".tokenval";
  
  public static String getTokenForSession (HttpSession session) { 
    String token = null;    
  // I cannot allow more than one token on a session - in the case of two requests trying to  
    // init the token concurrently    
  synchronized (session) {  
  token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);    
    if (null==token) {  
        token=UUID.randomUUID().toString(); 
        session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);    
    }   }   
  return token; 
  }

  /**
   * Extracts the token value from the session
   * 
   * @param request
   * @return
   */
  public static String getTokenFromRequest(HttpServletRequest request) {
    return request.getParameter(CSRF_PARAM_NAME);
  }

  private CSRFTokenManager() {
    // Do nothing
  }
  
  /**
   * Validate the token value from the session
   * 
   * @param request
   * @return
   * @throws WalletException
   */
  public static void validateCSRFToken(HttpServletRequest request)
      throws PrepaidException {
    String token = getTokenForSession(request.getSession());
    String requestCSRFToken = request.getParameter(CSRF_PARAM_NAME);
    boolean isValid = true; 
    try {
      isValid = (requestCSRFToken == null)?false:token.equals(requestCSRFToken);
    } catch (Exception e) {
      logger.error("NO WORRY FOR THIS error in validateCSRFToken >>>>>>", e);
    }
    if (isValid) {
      // Valid request            
    } else {
      logger.info("CSRF Token in Session : " + token + " CSRF Token in Request : " + requestCSRFToken
                  + "Are not Matching");
      throw new PrepaidException();
    }
  }
}
