package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PG_EMV_TRANSACTION")
public class PGEMVTransaction implements Serializable {

  private static final long serialVersionUID = -4097441693665168699L;

  @Id
  /*@SequenceGenerator(name = "SEQ_PG_EMV_TRANSACTION_ID", sequenceName = "SEQ_PG_EMV_TRANSACTION")
  @GeneratedValue(generator = "SEQ_PG_EMV_TRANSACTION_ID")*/
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "PG_TRANSACTION_ID")
  private String pgTransactionId;

  @Column(name = "AID")
  private String aid;

  @Column(name = "IST")
  private String ist;

  @Column(name = "IST1")
  private String ist1;

  @Column(name = "AIP")
  private String aip;

  @Column(name = "IID")
  private String iid;

  @Column(name = "TVR")
  private String tvr;

  @Column(name = "AED")
  private String aed;

  @Column(name = "FCI")
  private String fci;

  @Column(name = "FCIP")
  private String fcip;

  @Column(name = "TXN_STATUS_INFO")
  private String txnStatusInfo;

  @Column(name = "PSL")
  private String psn;

  @Column(name = "TAVN")
  private String tavn;

  @Column(name = "IAD")
  private String iad;

  @Column(name = "IFD")
  private String ifd;

  @Column(name = "APP_CRYPTO")
  private String appCrypto;

  @Column(name = "CRYPTO_INFO")
  private String cryptoInfo;

  @Column(name = "TERMINAL_CAPABILITIES")
  private String terminalCapabilities;

  @Column(name = "CVMR")
  private String cvrm;

  @Column(name = "TERMINAL_TYPE")
  private String terminalType;

  @Column(name = "ATC")
  private String atc;

  @Column(name = "UNPRED_NUMBER")
  private String unPredNumber;

  @Column(name = "TSN")
  private String tsn;

  @Column(name = "TCC")
  private String tcc;

  @Column(name = "ISR")
  private String isr;

  @Column(name = "LAN_PREF")
  private String lanRef;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the pgTransactionId
   */
  public String getPgTransactionId() {
    return pgTransactionId;
  }

  /**
   * @param pgTransactionId
   *          the pgTransactionId to set
   */
  public void setPgTransactionId(String pgTransactionId) {
    this.pgTransactionId = pgTransactionId;
  }

  /**
   * @return the aid
   */
  public String getAid() {
    return aid;
  }

  /**
   * @param aid
   *          the aid to set
   */
  public void setAid(String aid) {
    this.aid = aid;
  }

  /**
   * @return the ist
   */
  public String getIst() {
    return ist;
  }

  /**
   * @param ist
   *          the ist to set
   */
  public void setIst(String ist) {
    this.ist = ist;
  }

  /**
   * @return the ist1
   */
  public String getIst1() {
    return ist1;
  }

  /**
   * @param ist1
   *          the ist1 to set
   */
  public void setIst1(String ist1) {
    this.ist1 = ist1;
  }

  /**
   * @return the aip
   */
  public String getAip() {
    return aip;
  }

  /**
   * @param aip
   *          the aip to set
   */
  public void setAip(String aip) {
    this.aip = aip;
  }

  /**
   * @return the iid
   */
  public String getIid() {
    return iid;
  }

  /**
   * @param iid
   *          the iid to set
   */
  public void setIid(String iid) {
    this.iid = iid;
  }

  /**
   * @return the tvr
   */
  public String getTvr() {
    return tvr;
  }

  /**
   * @param tvr
   *          the tvr to set
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
   * @param aed
   *          the aed to set
   */
  public void setAed(String aed) {
    this.aed = aed;
  }

  /**
   * @return the fci
   */
  public String getFci() {
    return fci;
  }

  /**
   * @param fci
   *          the fci to set
   */
  public void setFci(String fci) {
    this.fci = fci;
  }

  /**
   * @return the fcip
   */
  public String getFcip() {
    return fcip;
  }

  /**
   * @param fcip
   *          the fcip to set
   */
  public void setFcip(String fcip) {
    this.fcip = fcip;
  }

  /**
   * @return the txnStatusInfo
   */
  public String getTxnStatusInfo() {
    return txnStatusInfo;
  }

  /**
   * @param txnStatusInfo
   *          the txnStatusInfo to set
   */
  public void setTxnStatusInfo(String txnStatusInfo) {
    this.txnStatusInfo = txnStatusInfo;
  }

  /**
   * @return the psn
   */
  public String getPsn() {
    return psn;
  }

  /**
   * @param psn
   *          the psn to set
   */
  public void setPsn(String psn) {
    this.psn = psn;
  }

  /**
   * @return the tavn
   */
  public String getTavn() {
    return tavn;
  }

  /**
   * @param tavn
   *          the tavn to set
   */
  public void setTavn(String tavn) {
    this.tavn = tavn;
  }

  /**
   * @return the iad
   */
  public String getIad() {
    return iad;
  }

  /**
   * @param iad
   *          the iad to set
   */
  public void setIad(String iad) {
    this.iad = iad;
  }

  /**
   * @return the ifd
   */
  public String getIfd() {
    return ifd;
  }

  /**
   * @param ifd
   *          the ifd to set
   */
  public void setIfd(String ifd) {
    this.ifd = ifd;
  }

  /**
   * @return the appCrypto
   */
  public String getAppCrypto() {
    return appCrypto;
  }

  /**
   * @param appCrypto
   *          the appCrypto to set
   */
  public void setAppCrypto(String appCrypto) {
    this.appCrypto = appCrypto;
  }

  /**
   * @return the cryptoInfo
   */
  public String getCryptoInfo() {
    return cryptoInfo;
  }

  /**
   * @param cryptoInfo
   *          the cryptoInfo to set
   */
  public void setCryptoInfo(String cryptoInfo) {
    this.cryptoInfo = cryptoInfo;
  }

  /**
   * @return the terminalCapabilities
   */
  public String getTerminalCapabilities() {
    return terminalCapabilities;
  }

  /**
   * @param terminalCapabilities
   *          the terminalCapabilities to set
   */
  public void setTerminalCapabilities(String terminalCapabilities) {
    this.terminalCapabilities = terminalCapabilities;
  }

  /**
   * @return the cvrm
   */
  public String getCvrm() {
    return cvrm;
  }

  /**
   * @param cvrm
   *          the cvrm to set
   */
  public void setCvrm(String cvrm) {
    this.cvrm = cvrm;
  }

  /**
   * @return the terminalType
   */
  public String getTerminalType() {
    return terminalType;
  }

  /**
   * @param terminalType
   *          the terminalType to set
   */
  public void setTerminalType(String terminalType) {
    this.terminalType = terminalType;
  }

  /**
   * @return the atc
   */
  public String getAtc() {
    return atc;
  }

  /**
   * @param atc
   *          the atc to set
   */
  public void setAtc(String atc) {
    this.atc = atc;
  }

  /**
   * @return the unPredNumber
   */
  public String getUnPredNumber() {
    return unPredNumber;
  }

  /**
   * @param unPredNumber
   *          the unPredNumber to set
   */
  public void setUnPredNumber(String unPredNumber) {
    this.unPredNumber = unPredNumber;
  }

  /**
   * @return the tsn
   */
  public String getTsn() {
    return tsn;
  }

  /**
   * @param tsn
   *          the tsn to set
   */
  public void setTsn(String tsn) {
    this.tsn = tsn;
  }

  /**
   * @return the tcc
   */
  public String getTcc() {
    return tcc;
  }

  /**
   * @param tcc
   *          the tcc to set
   */
  public void setTcc(String tcc) {
    this.tcc = tcc;
  }

  /**
   * @return the isr
   */
  public String getIsr() {
    return isr;
  }

  /**
   * @param isr
   *          the isr to set
   */
  public void setIsr(String isr) {
    this.isr = isr;
  }

  /**
   * @return the lanRef
   */
  public String getLanRef() {
    return lanRef;
  }

  /**
   * @param lanRef
   *          the lanRef to set
   */
  public void setLanRef(String lanRef) {
    this.lanRef = lanRef;
  }

}