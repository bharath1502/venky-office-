package com.chatak.pay.service;

import com.chatak.pay.controller.model.CardData;
import com.chatak.pay.exception.ChatakVaultException;
import com.chatak.pg.bean.CardTokenData;
import com.chatak.pg.bean.GetCardTokensRequest;
import com.chatak.pg.bean.GetCardTokensResponse;
import com.chatak.pg.bean.RegisterCardRequest;
import com.chatak.pg.bean.VaultResponse;

public interface VaultService {

  public VaultResponse registerCardToken(RegisterCardRequest registerCardRequest) throws ChatakVaultException;

  public void validateRegisterCardRequest(RegisterCardRequest registerCardRequest) throws ChatakVaultException;

  public void validateTokensRequest(GetCardTokensRequest getCardTokensRequest) throws ChatakVaultException;

  public GetCardTokensResponse getCardTokens(GetCardTokensRequest getCardTokensRequest) throws ChatakVaultException;

  public CardData getCardDataOnTokenData(CardTokenData cardTokenData) throws ChatakVaultException;

}
