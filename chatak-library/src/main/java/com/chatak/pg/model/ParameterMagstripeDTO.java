package com.chatak.pg.model;

import java.io.Serializable;
import java.util.Map;

import com.chatak.pg.bean.SearchRequest;

public class ParameterMagstripeDTO extends SearchRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1701161878366397904L;
	
	private Long magstripeId;
	
	private String magstripeName;
	
	private Long cardRangeLow;
	
	private Long cardRangeHigh;
	
	private String cardLabel;
	
	private String panLength;
	
	private String status;
	
	private Map<Long, Long> availablePanLength;
	
	private Map<Long, Long> selectedPanLength;

	/**
	 * @return the magstripeId
	 */
	public Long getMagstripeId() {
		return magstripeId;
	}

	/**
	 * @return the magstripeName
	 */
	public String getMagstripeName() {
		return magstripeName;
	}
	
	/**
   * @param magstripeId the magstripeId to set
   */
  public void setMagstripeId(Long magstripeId) {
    this.magstripeId = magstripeId;
  }

	/**
	 * @param magstripeName the magstripeName to set
	 */
	public void setMagstripeName(String magstripeName) {
		this.magstripeName = magstripeName;
	}
	
	/**
   * @param cardRangeLow the cardRangeLow to set
   */
  public void setCardRangeLow(Long cardRangeLow) {
    this.cardRangeLow = cardRangeLow;
  }

	/**
	 * @return the cardRangeLow
	 */
	public Long getCardRangeLow() {
		return cardRangeLow;
	}

	/**
   * @return the cardLabel
   */
  public String getCardLabel() {
    return cardLabel;
  }

	/**
	 * @return the cardRangeHigh
	 */
	public Long getCardRangeHigh() {
		return cardRangeHigh;
	}
	
	/**
   * @return the panLength
   */
  public String getPanLength() {
    return panLength;
  }

	/**
	 * @param cardRangeHigh the cardRangeHigh to set
	 */
	public void setCardRangeHigh(Long cardRangeHigh) {
		this.cardRangeHigh = cardRangeHigh;
	}

	/**
	 * @param panLength the panLength to set
	 */
	public void setPanLength(String panLength) {
		this.panLength = panLength;
	}
	
	/**
   * @param cardLabel the cardLabel to set
   */
  public void setCardLabel(String cardLabel) {
    this.cardLabel = cardLabel;
  }

	/**
	 * @return the availablePanLength
	 */
	public Map<Long, Long> getAvailablePanLength() {
		return availablePanLength;
	}

	/**
	 * @param availablePanLength the availablePanLength to set
	 */
	public void setAvailablePanLength(Map<Long, Long> availablePanLength) {
		this.availablePanLength = availablePanLength;
	}

	/**
	 * @return the selectedPanLength
	 */
	public Map<Long, Long> getSelectedPanLength() {
		return selectedPanLength;
	}

	/**
	 * @param selectedPanLength the selectedPanLength to set
	 */
	public void setSelectedPanLength(Map<Long, Long> selectedPanLength) {
		this.selectedPanLength = selectedPanLength;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
