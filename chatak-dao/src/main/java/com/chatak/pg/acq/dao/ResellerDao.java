package com.chatak.pg.acq.dao;

import java.util.List;

import com.chatak.pg.acq.dao.model.PGReseller;
import com.chatak.pg.user.bean.AddResellerRequest;
import com.chatak.pg.user.bean.AddResellerResponse;
import com.chatak.pg.user.bean.DeleteResellerResponse;
import com.chatak.pg.user.bean.GetResellerListRequest;
import com.chatak.pg.user.bean.GetResellerListResponse;
import com.chatak.pg.user.bean.UpdateResellerRequest;
import com.chatak.pg.user.bean.UpdateResellerResponse;

public interface ResellerDao {

	
	public AddResellerResponse addReseller(AddResellerRequest addResellerRequest);
	
	public String getResllerAccountNumber();
	
	public GetResellerListResponse getResellerlist(GetResellerListRequest serachReseller);
	
	public PGReseller getResellerByresellerId(Long resellerId);
	
	public UpdateResellerResponse updateReseller(UpdateResellerRequest updateResellerRequest);
	
	public DeleteResellerResponse deleteReseller(Long resellerId);

	public List<PGReseller> getResellerData();
}