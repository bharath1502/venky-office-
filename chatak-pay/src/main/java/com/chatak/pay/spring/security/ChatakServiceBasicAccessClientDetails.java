/**
 * 
 */
package com.chatak.pay.spring.security;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chatak.pay.exception.ChatakPayException;
import com.chatak.pay.service.ChatakPayService;
import com.chatak.pg.acq.dao.model.PGApplicationClient;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Mar-2015 9:42:32 AM
 * @version 1.0
 */
@Component("chatakServiceBasicAccessClientDetails")
public class ChatakServiceBasicAccessClientDetails implements UserDetailsService {

  private static Logger logger = LogManager.getLogger(ChatakServiceBasicAccessClientDetails.class); 

  @Autowired
  private ChatakPayService chatakPayService;

  public ChatakServiceBasicAccessClientDetails() {
    super();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      PGApplicationClient pgApplicationClient = chatakPayService.getApplicationClientAuth(username);
      final String[] roleStringsAsArray = pgApplicationClient.getAppClientRole().split("\\,");
      final List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(roleStringsAsArray);
      return new User(pgApplicationClient.getAppAuthUser(), pgApplicationClient.getAppAuthPass(), auths);
    }
    catch(ChatakPayException e) {
      logger.error("Error :: ChatakServiceBasicAccessClientDetails :: loadUserByUsername", e);
    }
    throw new UsernameNotFoundException("Client is not available. Please contact support team for more information.");
  }

}
