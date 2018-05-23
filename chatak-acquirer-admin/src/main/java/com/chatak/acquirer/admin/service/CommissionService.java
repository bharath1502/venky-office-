package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CommissionProgramResponseDetails;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.CommissionDTO;


public interface CommissionService 
{

	public Response createCommission(CommissionDTO commissionDTO) throws ChatakAdminException;

	public Response searchCommissionProgram(CommissionDTO commissionDTO) throws ChatakAdminException;;

	public CommissionProgramResponseDetails getCommissionProgramDetails(CommissionDTO commissionDTO) throws ChatakAdminException;

	public CommissionProgramResponseDetails getByCommProgramId(CommissionDTO commissionDTO) throws ChatakAdminException;

	public Response updateCommissionProgram(CommissionDTO commissionDTO) throws ChatakAdminException;

}
