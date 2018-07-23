package com.chatak.pg.user.bean;

import java.io.Serializable;

import com.chatak.pg.util.CommonUtil;

public class AddMerchantBankRequest implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String merchant_code;

  private String bank_name;

  private String bank_acc_number;

  private String bank_code;

  private String currency_code;

  private String account_type;
  
  private String routing_num;

  public String validate() {
    String message = "";
    if(merchant_code == null) {
      message = "merchant_name is the Required field";
    }
    else if(CommonUtil.isNullAndEmpty(bank_name)) {
      message = "bank_name is the Required field";
    }
    else if(CommonUtil.isNullAndEmpty(bank_acc_number)) {
      message = "bank_acc_number is the Required field";
    }
    else if(CommonUtil.isNullAndEmpty(bank_code)) {
      message = "bank_code is the Required field";
    }
    else if(CommonUtil.isNullAndEmpty(account_type)) {
      message = "account_type is the Required field";
    }
    else if(currency_code == null) {
      message = "currency_code is the Required field";
    }
    return message;
  }

  /**
   * @return the merchant_code
   */
  public String getMerchant_code() {
    return merchant_code;
  }

  /**
   * @param merchant_code
   *          the merchant_code to set
   */
  public void setMerchant_code(String merchant_code) {
    this.merchant_code = merchant_code;
  }

  /**
   * @return the bank_name
   */
  public String getBank_name() {
    return bank_name;
  }

  /**
   * @param bank_name
   *          the bank_name to set
   */
  public void setBank_name(String bank_name) {
    this.bank_name = bank_name;
  }

  /**
   * @return the bank_acc_number
   */
  public String getBank_acc_number() {
    return bank_acc_number;
  }

  /**
   * @param bank_acc_number
   *          the bank_acc_number to set
   */
  public void setBank_acc_number(String bank_acc_number) {
    this.bank_acc_number = bank_acc_number;
  }

  /**
   * @return the bank_code
   */
  public String getBank_code() {
    return bank_code;
  }

  /**
   * @param bank_code
   *          the bank_code to set
   */
  public void setBank_code(String bank_code) {
    this.bank_code = bank_code;
  }

  /**
   * @return the currency_code
   */
  public String getCurrency_code() {
    return currency_code;
  }

  /**
   * @param currency_code
   *          the currency_code to set
   */
  public void setCurrency_code(String currency_code) {
    this.currency_code = currency_code;
  }

  /**
   * @return the account_type
   */
  public String getAccount_type() {
    return account_type;
  }

  /**
   * @param account_type
   *          the account_type to set
   */
  public void setAccount_type(String account_type) {
    this.account_type = account_type;
  }

  /**
   * @return the routing_num
   */
  public String getRouting_num() {
    return routing_num;
  }

  /**
   * @param routing_num the routing_num to set
   */
  public void setRouting_num(String routing_num) {
    this.routing_num = routing_num;
  }

}
