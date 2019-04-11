package com.chatak.pay.controller.model;

import java.util.List;

import com.chatak.pg.acq.dao.model.ProgramManager;
import com.chatak.pg.user.bean.IsoRequest;
import com.chatak.pg.user.bean.ProgramManagerRequest;


public class MerchantIsoOnboardingResponse extends Response {

	private static final long serialVersionUID = 1L;

	private List<ProgramManagerRequest> programManager;
	
	private List<IsoRequest> isoRequest;
	
	public List<IsoRequest> getIsoRequest() {
		return isoRequest;
	}

	public void setIsoRequest(List<IsoRequest> isoRequest) {
		this.isoRequest = isoRequest;
	}

	public List<ProgramManagerRequest> getProgramManager() {
		return programManager;
	}

	public void setProgramManager(List<ProgramManagerRequest> programManager) {
		this.programManager = programManager;
	}
}
