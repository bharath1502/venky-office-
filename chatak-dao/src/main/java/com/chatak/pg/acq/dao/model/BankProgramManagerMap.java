package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.chatak.pg.user.bean.BankProgramManagerMapRequest;
import com.chatak.pg.util.CommonUtil;

@Entity
@Table(name = "PG_BANK_PROGRAM_MANAGER_MAP")
public class BankProgramManagerMap implements Serializable {

  private static Logger logger = Logger.getLogger(BankProgramManagerMap.class);

  private static final long serialVersionUID = 8372809426150166151L;

  @Id
  @SequenceGenerator(name = "SEQ_BANK_PROGRAM_MANAGER_MAP",
      sequenceName = "SEQ_BANK_PROGRAM_MANAGER_MAP")
  @GeneratedValue(generator = "SEQ_BANK_PROGRAM_MANAGER_MAP")
  @Column(name = "ID")
  private Long id;

  @Column(name = "BANK_ID")
  private Long bankId;

  @Column(name = "PROGRAM_MANAGER_ID")
  private Long programManagerId;

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

  public Long getProgramManagerId() {
    return programManagerId;
  }

  public void setProgramManagerId(Long programManagerId) {
    this.programManagerId = programManagerId;
  }

  public BankProgramManagerMapRequest convertToRequest() {
    BankProgramManagerMapRequest bankProgramManagerRequest = new BankProgramManagerMapRequest();
    try {
      bankProgramManagerRequest =
          CommonUtil.copyBeanProperties(this, BankProgramManagerMapRequest.class);
    } catch (Exception e) {
      logger.error("Error :: BankProgramManagerMap :: convertToRequest", e);
    }

    return bankProgramManagerRequest;
  }

  public static BankProgramManagerMap convertToDAO(
      BankProgramManagerMapRequest bankProgramManagerMapRequest) {
    BankProgramManagerMap bankProgramManagerMap = new BankProgramManagerMap();

    try {
      bankProgramManagerMap =
          CommonUtil.copyBeanProperties(bankProgramManagerMapRequest, BankProgramManagerMap.class);
    } catch (Exception e) {
      logger.error("Error :: BankProgramManagerMap :: convertToDAO", e);
    }

    return bankProgramManagerMap;
  }

}
