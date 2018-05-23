package com.chatak.pg.model;

import java.io.Serializable;
import java.util.List;

public class CIProgramManagerDetails implements Serializable {
	
	/**
   * 
   */
  private static final long serialVersionUID = 4373345751840702774L;

  private String programManagerName;
	
	private String programManagerId;
	
	private String programManagerAccNumber;
	
  private List<CIPartnerDetails> partnerDetailList;
  
	/**
	 * @return the programManagerName
	 */
	public String getProgramManagerName() {
		return programManagerName;
	}
	/**
	 * @param programManagerName the programManagerName to set
	 */
	public void setProgramManagerName(String programManagerName) {
		this.programManagerName = programManagerName;
	}
	/**
	 * @return the programManagerId
	 */
	public String getProgramManagerId() {
		return programManagerId;
	}
	/**
	 * @param programManagerId the programManagerId to set
	 */
	public void setProgramManagerId(String programManagerId) {
		this.programManagerId = programManagerId;
	}
	/**
	 * @return the programManagerAccNumber
	 */
	public String getProgramManagerAccNumber() {
		return programManagerAccNumber;
	}
	/**
	 * @param programManagerAccNumber the programManagerAccNumber to set
	 */
	public void setProgramManagerAccNumber(String programManagerAccNumber) {
		this.programManagerAccNumber = programManagerAccNumber;
	}
  /**
   * @return the partnerDetailList
   */
  public List<CIPartnerDetails> getPartnerDetailList() {
    return partnerDetailList;
  }
  /**
   * @param partnerDetailList the partnerDetailList to set
   */
  public void setPartnerDetailList(List<CIPartnerDetails> partnerDetailList) {
    this.partnerDetailList = partnerDetailList;
  }
}
