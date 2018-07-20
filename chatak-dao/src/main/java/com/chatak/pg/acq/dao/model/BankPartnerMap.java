package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.chatak.pg.user.bean.BankPartnerMapRequest;
import com.chatak.pg.util.CommonUtil;

@Entity
@Table(name = "PG_BANK_PARTNER_MAP")
public class BankPartnerMap implements Serializable {

  private static Logger logger = Logger.getLogger(BankPartnerMap.class);

  private static final long serialVersionUID = -7296082344874007961L;

  @Id
  @SequenceGenerator(name = "SEQ_BANK_PARTNER_MAP", sequenceName = "SEQ_BANK_PARTNER_MAP")
  @GeneratedValue(generator = "SEQ_BANK_PARTNER_MAP")
  @Column(name = "ID")
  private Long id;

  @Column(name = "BANK_ID")
  private Long bankId;

  @Column(name = "PARTNER_ID")
  private Long partnerId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public BankPartnerMapRequest convertToRequest() {
    BankPartnerMapRequest bankPartnerRequest = new BankPartnerMapRequest();
    try {
      bankPartnerRequest = CommonUtil.copyBeanProperties(this, BankPartnerMapRequest.class);
    } catch (Exception e) {
      logger.error("Error :: BankPartnerMap :: convertToRequest", e);
    }

    return bankPartnerRequest;
  }

  public static BankPartnerMap convertToDAO(BankPartnerMapRequest bankPartnerMapRequest) {
    BankPartnerMap bankPartnerMap = new BankPartnerMap();

    try {
      bankPartnerMap = CommonUtil.copyBeanProperties(bankPartnerMapRequest, BankPartnerMap.class);
    } catch (Exception e) {
      logger.error("Error :: BankPartnerMap :: convertToDAO", e);
    }

    return bankPartnerMap;
  }

}
