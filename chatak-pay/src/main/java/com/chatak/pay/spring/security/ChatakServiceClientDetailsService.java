/**
 * 
 */
package com.chatak.pay.spring.security;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 03-Mar-2015 9:42:32 AM
 * @version 1.0
 */
@Component("chatakServiceClientDetailsService")
public class ChatakServiceClientDetailsService implements ClientDetailsService {

  public ChatakServiceClientDetailsService() {
    super();
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {
    BaseClientDetails clientDetails = new BaseClientDetails(clientId, "", "", "password,authorization_code,refresh_token,implicit", "ROLE_APP");
    clientDetails.setClientSecret("secret");
    return clientDetails;
  }


}
