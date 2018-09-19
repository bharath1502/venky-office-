package com.chatak.pay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TSMResponse extends com.chatak.pay.controller.model.Response {

	private static final long serialVersionUID = 8227190264868980458L;

	private String terminalId;
	private String updateType;
	private String updateURL;
	private String updateVersion;
	
	public String getUpdateVersion() {
		return updateVersion;
	}

	public void setUpdateVersion(String updateVersion) {
		this.updateVersion = updateVersion;
	}

	public String getTerminalId() {
		return terminalId;
	}
	
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	public String getUpdateType() {
		return updateType;
	}
	
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	
	public String getUpdateURL() {
		return updateURL;
	}
	
	public void setUpdateURL(String updateURL) {
		this.updateURL = updateURL;
	}
}
