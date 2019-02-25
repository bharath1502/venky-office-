/**
 * 
 */
package com.chatak.pg.user.bean;

import java.util.List;
import java.util.Map;

import com.chatak.pg.bean.SearchRequest;

/**
 * @Author: Girmiti Software
 * @Date: May 5, 2018
 * @Time: 12:53:58 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class IsoRequest extends SearchRequest{

	private static final long serialVersionUID = 3389321489649862234L;
	private ProgramManagerRequest programManagerRequest;
	private String isoName;
	private Long programManagerIds;
	private List<String> cardProgramIds;
	private Long id;
	private String address;
	private String country;
	private String state;
	private String city;
	private String zipCode;
	private List<Long> ids;
	private String bankName;
	private String bankAccNum;
	private String routingNumber;
	private Map<Long, Long> cardProgramAndEntityId;
	private List<PanRangeRequest> panRangeList;
	private String processor;

	/**
	 * @return the panRangeList
	 */
	public List<PanRangeRequest> getPanRangeList() {
		return panRangeList;
	}

	/**
	 * @param panRangeList the panRangeList to set
	 */
	public void setPanRangeList(List<PanRangeRequest> panRangeList) {
		this.panRangeList = panRangeList;
	}
	
	/**
	 * @return the isoName
	 */
	public String getIsoName() {
		return isoName;
	}

	/**
	 * @param isoName
	 *            the isoName to set
	 */
	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the programManagerIds
	 */
	public Long getProgramManagerIds() {
		return programManagerIds;
	}

	/**
	 * @param programManagerIds
	 *            the programManagerIds to set
	 */
	public void setProgramManagerIds(Long programManagerIds) {
		this.programManagerIds = programManagerIds;
	}

	/**
	 * @return the programManagerRequest
	 */
	public ProgramManagerRequest getProgramManagerRequest() {
		return programManagerRequest;
	}

	/**
	 * @param programManagerRequest
	 *            the programManagerRequest to set
	 */
	public void setProgramManagerRequest(ProgramManagerRequest programManagerRequest) {
		this.programManagerRequest = programManagerRequest;
	}

	/**
	 * @return the ids
	 */
	public List<Long> getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankAccNum
	 */
	public String getBankAccNum() {
		return bankAccNum;
	}

	/**
	 * @param bankAccNum
	 *            the bankAccNum to set
	 */
	public void setBankAccNum(String bankAccNum) {
		this.bankAccNum = bankAccNum;
	}

	/**
	 * @return the routingNumber
	 */
	public String getRoutingNumber() {
		return routingNumber;
	}

	/**
	 * @param routingNumber
	 *            the routingNumber to set
	 */
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public List<String> getCardProgramIds() {
		return cardProgramIds;
	}

	public void setCardProgramIds(List<String> cardProgramIds) {
		this.cardProgramIds = cardProgramIds;
	}

	public Map<Long, Long> getCardProgramAndEntityId() {
		return cardProgramAndEntityId;
	}

	public void setCardProgramAndEntityId(Map<Long, Long> cardProgramAndEntityId) {
		this.cardProgramAndEntityId = cardProgramAndEntityId;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}
}
