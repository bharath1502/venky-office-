/**
 * 
 */
package com.chatak.pg.acq.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Girmiti Software
 * @Date: May 8, 2018
 * @Time: 4:51:41 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Entity
@Table(name = "PG_ISO_CARD_PROGRAM_MAPPING")
public class IsoCardProgramMap implements Serializable{

	private static final long serialVersionUID = -1028000249711407433L;
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "ID")
	  private Long id;
	  
	  @Column(name = "ISO_ID")
	  private Long isoId;
	  
	  @Column(name = "CARD_PROGRAM_ID")
	  private Long cardProgramId;
	  
	  @Column(name = "AMBIGUITY_PM_ID")
      private Long ambiguityPmId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the isoId
	 */
	public Long getIsoId() {
		return isoId;
	}

	/**
	 * @param isoId the isoId to set
	 */
	public void setIsoId(Long isoId) {
		this.isoId = isoId;
	}

	/**
	 * @return the cardProgramId
	 */
	public Long getCardProgramId() {
		return cardProgramId;
	}

	/**
	 * @param cardProgramId the cardProgramId to set
	 */
	public void setCardProgramId(Long cardProgramId) {
		this.cardProgramId = cardProgramId;
	}

  /**
   * @return the ambiguityPmId
   */
  public Long getAmbiguityPmId() {
    return ambiguityPmId;
  }

  /**
   * @param ambiguityPmId the ambiguityPmId to set
   */
  public void setAmbiguityPmId(Long ambiguityPmId) {
    this.ambiguityPmId = ambiguityPmId;
  }

}
