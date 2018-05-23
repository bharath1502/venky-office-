/**
 * 
 */
package com.chatak.pg.emv.util;

import com.chatak.pg.exception.InvalidEMVDataFormatException;
import com.chatak.pg.util.StringUtils;

/**
 * << Add Comments Here >>
 * 
 * @author Girmiti Software
 * @date 05-Dec-2014 10:22:00 AM
 * @version 1.0
 */
public class EMVData {

  private String ist_1;

  private String ist;

  private String aip; // Application Interchange Profile

  private String aid; // Dedicated File Name

  private String iad; // Issuer Authentication Data

  private String tvr; // Terminal Verification Results

  private String aed; // Application expiration date

  private String pan;
  
  private String fci;
  
  private String fcip;

  private String txnDate; // Txn Date

  private String txnStatusInfo; // "TRANSACTION_STATUS_INFORMATION"

  private String txnType; // = "TRANSACTION_TYPE"

  private String txnCurrency; // = "TXN_CURRENCY_CODE"

  private String psn; // PAN sequence number

  private String amount; // Authorized Amount

  private String amountOther;

  private String avn; // Terminal Application Version Number

  private String issuerApplicationData; // "ISSUER_APPLICATION_DATA"

  private String tcc; // = "TERMINAL_COUNTRY_CODE"

  private String ifd; // Interface Device (IFD) Serial number

  private String acr; // "APPLICATION_CRYPTOGRAM"

  private String cid; // "CRYPTOGRAM_INFORMATION_DATA"

  private String tc; // Terminal Capabilities

  private String cvmr; // Cardholder Verification Method Results

  private String tt; // "TERMINAL_TYPE"

  private String atc; // "APPLICATION_TRANSACTION_COUNTER"

  private String unPredictableNum; // "UNPREDICTABLE_NUMBER"

  private String tsn; // Transaction Sequence Number

  private String tCurrencyCode; // Transaction Currency Code

  private String isr; // Issuer Script Result
  
  private String lan; //Language
  
  /**
   * Method to validate EMV request
   * 
   * @param isChipTxn
   * @param emvData
   * @throws InvalidEMVDataFormatException
   */
  public static void validateEMVRequest(boolean isChipTxn, EMVData emvData) throws InvalidEMVDataFormatException {
      if(isChipTxn) {
        if(null == emvData) {
          throw new InvalidEMVDataFormatException();
        }
        else {
          if(!StringUtils.isValidString(emvData.getIssuerApplicationData())
             || !StringUtils.isValidString(emvData.getTvr()) || !StringUtils.isValidString(emvData.getTxnDate())
             || !StringUtils.isValidString(emvData.getTxnType()) || !StringUtils.isValidString(emvData.getTxnCurrency())
             || !StringUtils.isValidString(emvData.getPsn()) || !StringUtils.isValidString(emvData.getAmount())
             || !StringUtils.isValidString(emvData.getTcc()) /*|| !StringUtils.isValidString(emvData.getIfd())*/
             || !StringUtils.isValidString(emvData.getAcr()) || !StringUtils.isValidString(emvData.getCid())
             || !StringUtils.isValidString(emvData.getAtc()) || !StringUtils.isValidString(emvData.getUnPredictableNum())
          /*
           * || !StringUtils.isValidString(emvData.getIsr()) Check for ISR
           * Conditional (if provided by ICC, to be sent in reversal, void,
           * adjust, tip adjust, batch upload txns)
           */) {
            throw new InvalidEMVDataFormatException();
          }
        }
      }
    }

  /**
   * @return the ist_1
   */
  public String getIst_1() {
    return ist_1;
  }

  /**
   * @param ist_1 the ist_1 to set
   */
  public void setIst_1(String ist_1) {
    this.ist_1 = ist_1;
  }

  /**
   * @return the ist
   */
  public String getIst() {
    return ist;
  }

  /**
   * @param ist the ist to set
   */
  public void setIst(String ist) {
    this.ist = ist;
  }

  /**
   * @return the aip
   */
  public String getAip() {
    return aip;
  }

  /**
   * @param aip the aip to set
   */
  public void setAip(String aip) {
    this.aip = aip;
  }

  /**
   * @return the aid
   */
  public String getAid() {
    return aid;
  }

  /**
   * @param aid the aid to set
   */
  public void setAid(String aid) {
    this.aid = aid;
  }

  /**
   * @return the iad
   */
  public String getIad() {
    return iad;
  }

  /**
   * @param iad the iad to set
   */
  public void setIad(String iad) {
    this.iad = iad;
  }

  /**
   * @return the tvr
   */
  public String getTvr() {
    return tvr;
  }

  /**
   * @param tvr the tvr to set
   */
  public void setTvr(String tvr) {
    this.tvr = tvr;
  }

  /**
   * @return the aed
   */
  public String getAed() {
    return aed;
  }

  /**
   * @param aed the aed to set
   */
  public void setAed(String aed) {
    this.aed = aed;
  }

  /**
   * @return the pan
   */
  public String getPan() {
    return pan;
  }

  /**
   * @param pan the pan to set
   */
  public void setPan(String pan) {
    this.pan = pan;
  }

  /**
   * @return the txnDate
   */
  public String getTxnDate() {
    return txnDate;
  }

  /**
   * @param txnDate the txnDate to set
   */
  public void setTxnDate(String txnDate) {
    this.txnDate = txnDate;
  }

  /**
   * @return the txnStatusInfo
   */
  public String getTxnStatusInfo() {
    return txnStatusInfo;
  }

  /**
   * @param txnStatusInfo the txnStatusInfo to set
   */
  public void setTxnStatusInfo(String txnStatusInfo) {
    this.txnStatusInfo = txnStatusInfo;
  }

  /**
   * @return the txnType
   */
  public String getTxnType() {
    return txnType;
  }

  /**
   * @param txnType the txnType to set
   */
  public void setTxnType(String txnType) {
    this.txnType = txnType;
  }

  /**
   * @return the txnCurrency
   */
  public String getTxnCurrency() {
    return txnCurrency;
  }

  /**
   * @param txnCurrency the txnCurrency to set
   */
  public void setTxnCurrency(String txnCurrency) {
    this.txnCurrency = txnCurrency;
  }

  /**
   * @return the psn
   */
  public String getPsn() {
    return psn;
  }

  /**
   * @param psn the psn to set
   */
  public void setPsn(String psn) {
    this.psn = psn;
  }

  /**
   * @return the amount
   */
  public String getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(String amount) {
    this.amount = amount;
  }

  /**
   * @return the amountOther
   */
  public String getAmountOther() {
    return amountOther;
  }

  /**
   * @param amountOther the amountOther to set
   */
  public void setAmountOther(String amountOther) {
    this.amountOther = amountOther;
  }

  /**
   * @return the avn
   */
  public String getAvn() {
    return avn;
  }

  /**
   * @param avn the avn to set
   */
  public void setAvn(String avn) {
    this.avn = avn;
  }

  /**
   * @return the issuerApplicationData
   */
  public String getIssuerApplicationData() {
    return issuerApplicationData;
  }

  /**
   * @param issuerApplicationData the issuerApplicationData to set
   */
  public void setIssuerApplicationData(String issuerApplicationData) {
    this.issuerApplicationData = issuerApplicationData;
  }

  /**
   * @return the tcc
   */
  public String getTcc() {
    return tcc;
  }

  /**
   * @param tcc the tcc to set
   */
  public void setTcc(String tcc) {
    this.tcc = tcc;
  }

  /**
   * @return the ifd
   */
  public String getIfd() {
    return ifd;
  }

  /**
   * @param ifd the ifd to set
   */
  public void setIfd(String ifd) {
    this.ifd = ifd;
  }

  /**
   * @return the acr
   */
  public String getAcr() {
    return acr;
  }

  /**
   * @param acr the acr to set
   */
  public void setAcr(String acr) {
    this.acr = acr;
  }

  /**
   * @return the cid
   */
  public String getCid() {
    return cid;
  }

  /**
   * @param cid the cid to set
   */
  public void setCid(String cid) {
    this.cid = cid;
  }

  /**
   * @return the tc
   */
  public String getTc() {
    return tc;
  }

  /**
   * @param tc the tc to set
   */
  public void setTc(String tc) {
    this.tc = tc;
  }

  /**
   * @return the cvmr
   */
  public String getCvmr() {
    return cvmr;
  }

  /**
   * @param cvmr the cvmr to set
   */
  public void setCvmr(String cvmr) {
    this.cvmr = cvmr;
  }

  /**
   * @return the tt
   */
  public String getTt() {
    return tt;
  }

  /**
   * @param tt the tt to set
   */
  public void setTt(String tt) {
    this.tt = tt;
  }

  /**
   * @return the atc
   */
  public String getAtc() {
    return atc;
  }

  /**
   * @param atc the atc to set
   */
  public void setAtc(String atc) {
    this.atc = atc;
  }

  /**
   * @return the unPredictableNum
   */
  public String getUnPredictableNum() {
    return unPredictableNum;
  }

  /**
   * @param unPredictableNum the unPredictableNum to set
   */
  public void setUnPredictableNum(String unPredictableNum) {
    this.unPredictableNum = unPredictableNum;
  }

  /**
   * @return the tsn
   */
  public String getTsn() {
    return tsn;
  }

  /**
   * @param tsn the tsn to set
   */
  public void setTsn(String tsn) {
    this.tsn = tsn;
  }

  /**
   * @return the tCurrencyCode
   */
  public String gettCurrencyCode() {
    return tCurrencyCode;
  }

  /**
   * @param tCurrencyCode the tCurrencyCode to set
   */
  public void settCurrencyCode(String tCurrencyCode) {
    this.tCurrencyCode = tCurrencyCode;
  }

  /**
   * @return the isr
   */
  public String getIsr() {
    return isr;
  }

  /**
   * @param isr the isr to set
   */
  public void setIsr(String isr) {
    this.isr = isr;
  }
  
  /**
   * @return
   */
  @Override
  public String toString() {
    return super.toString();
  }

  /**
   * @return the fci
   */
  public String getFci() {
    return fci;
  }

  /**
   * @param fci the fci to set
   */
  public void setFci(String fci) {
    this.fci = fci;
  }

  public String getFcip() {
    return fcip;
  }

  public void setFcip(String fcip) {
    this.fcip = fcip;
  }

  public String getLan() {
    return lan;
  }

  public void setLan(String lan) {
    this.lan = lan;
  }

}
