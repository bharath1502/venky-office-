package com.chatak.pg.bean;

import java.io.Serializable;
import java.util.List;

public class GetCardTokensResponse extends VaultResponse implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -6108514849813744777L;
  
  private List<CardToken> cardTokenList;

  /**
   * @return the cardTokenList
   */
  public List<CardToken> getCardTokenList() {
    return cardTokenList;
  }

  /**
   * @param cardTokenList the cardTokenList to set
   */
  public void setCardTokenList(List<CardToken> cardTokenList) {
    this.cardTokenList = cardTokenList;
  }
  
}
