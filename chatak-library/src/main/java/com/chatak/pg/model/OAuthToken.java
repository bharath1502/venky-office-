/**
 * 
 */
package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * << Add Comments Here >>
 *
 * @author Girmiti Software
 * @date 10-Mar-2015 10:37:27 AM
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class OAuthToken implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8760650517781245395L;

  private String access_token;

  private String refresh_token;

  private String token_type;

  private Integer expires_in;
  
  private String value;
  
  private Integer expiresIn;
  
  private Long expiration;
  
  private String tokenType;
  
  private List<String> scope;
  
  private Map additionalInformation;
  
  private Boolean expired;
  
  private OAuthToken refreshToken;

  /**
   * @return the access_token
   */
  public String getAccess_token() {
    return access_token;
  }

  /**
   * @param access_token
   *          the access_token to set
   */
  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  /**
   * @return the refresh_token
   */
  public String getRefresh_token() {
    return refresh_token;
  }

  /**
   * @param refresh_token
   *          the refresh_token to set
   */
  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  /**
   * @return the token_type
   */
  public String getToken_type() {
    return token_type;
  }

  /**
   * @param token_type
   *          the token_type to set
   */
  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }

  /**
   * @return the expires_in
   */
  public Integer getExpires_in() {
    return expires_in;
  }

  /**
   * @param expires_in
   *          the expires_in to set
   */
  public void setExpires_in(Integer expires_in) {
    this.expires_in = expires_in;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    setAccess_token(value);
    this.value = value;
  }

  public Integer getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(Integer expiresIn) {
    setExpires_in(expiresIn);
    this.expiresIn = expiresIn;
  }

  public Long getExpiration() {
    return expiration;
  }

  public void setExpiration(Long expiration) {
    this.expiration = expiration;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    setToken_type(tokenType);
    this.tokenType = tokenType;
  }

  public List<String> getScope() {
    return scope;
  }

  public void setScope(List<String> scope) {
    this.scope = scope;
  }

  public Map getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(Map additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public Boolean getExpired() {
    return expired;
  }

  public void setExpired(Boolean expired) {
    this.expired = expired;
  }

  public OAuthToken getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(OAuthToken refreshToken) {
    this.refreshToken = refreshToken;
    if(refreshToken != null) {
      setRefresh_token(refreshToken.getValue());
    }
  }
}
