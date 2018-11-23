/**
 * 
 */
package com.chatak.pg.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Girmiti Software
 * @Date: May 3, 2018
 * @Time: 1:14:53 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class PartnerGroupPartnerMapRequest implements Serializable {
	
	private static final long serialVersionUID = 958761991312281886L;

	private Long id;

	private Long partnerGroupId;

	private PartnerRequest partnerRequest;
	
	private List<Long> programManagerId;

	public PartnerRequest getPartnerRequest() {
		return partnerRequest;
	}

	public void setPartnerRequest(PartnerRequest partnerRequest) {
		this.partnerRequest = partnerRequest;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPartnerGroupId() {
		return partnerGroupId;
	}

	public void setPartnerGroupId(Long partnerGroupId) {
		this.partnerGroupId = partnerGroupId;
	}

	public List<Long> getProgramManagerId() {
		return programManagerId;
	}

	public void setProgramManagerId(List<Long> programManagerId) {
		this.programManagerId = programManagerId;
	}
}