package com.chatak.acquirer.admin.spring.util;

import java.io.Serializable;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 03-Jan-2015 10:34:40 AM
 * @version 1.0
 */
public class ChatakAuthenticationProvider implements AuthenticationProvider, Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -4074746028982442760L;
  private SessionRegistryImpl sessionRegistry;

  /**
   * Method to authenticate the user
   * 
   * @param authentication
   */
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authentication.getCredentials(),
                                                                                                      authentication.getCredentials(),
                                                                                                      authentication.getAuthorities());
    return authenticationToken;
  }

  /**
   * Method to supports authenticated user
   * 
   * @param authentication
   */
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  /**
   * @return the sessionRegistry
   */
  public SessionRegistryImpl getSessionRegistry() {
    return sessionRegistry;
  }

  /**
   * @param sessionRegistry
   *          the sessionRegistry to set
   */
  public void setSessionRegistry(SessionRegistryImpl sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

}
