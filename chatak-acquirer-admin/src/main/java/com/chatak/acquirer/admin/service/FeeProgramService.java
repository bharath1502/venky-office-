package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.FeeProgramResponseDetails;
import com.chatak.pg.bean.FeeprogramNameResponse;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.FeeProgramDTO;

public interface FeeProgramService 
{

	/**
	 * @param feeProgramDTO
	 * @return
	 * @throws ChatakAdminException
	 */
	public Response createFeeProgram(FeeProgramDTO feeProgramDTO) throws ChatakAdminException;
	
	
	/**
	 * @param feeProgramDTO
	 * @return
	 * @throws ChatakAdminException
	 */
	public FeeProgramResponseDetails searchFeeProgramForJpa(FeeProgramDTO feeProgramDTO)throws ChatakAdminException;
	
	
	
	/**
	 * @param feeProgramDTO
	 * @return
	 * @throws ChatakAdminException
	 */
	public FeeProgramResponseDetails getFeeProgramDetails(FeeProgramDTO feeProgramDTO)throws ChatakAdminException;
	
	
	
	/**
	 * @param feeProgramRequest
	 * @return
	 * @throws ChatakAdminException
	 */
	public FeeProgramResponseDetails getByFeeProgramId(FeeProgramDTO feeProgramDTO)throws ChatakAdminException;
	
	
	/**
	 * @param feeProgramDTO
	 * @return
	 * @throws ChatakAdminException
	 */
	public Response updateFeeProgram(FeeProgramDTO feeProgramDTO) throws ChatakAdminException;


	public boolean validateFeePgmAccNo(String specificAccountNumber);

	/**
	 * @param getFeeProgramId
	 * @return
	 */
	public Response deleteFeeProgram(Long getFeeProgramId);
	
	public FeeprogramNameResponse validateFeeprogramName(String feeProgramName); 
	
	/**
	 * @param feeProgramDTO
	 * @return
	 * @throws ChatakAdminException
	 */
	public Response changeFeeProgramStatus(FeeProgramDTO feeProgramDTO) throws ChatakAdminException;
}
