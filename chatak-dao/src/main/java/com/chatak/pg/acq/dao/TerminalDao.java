package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGTerminal;
import com.chatak.pg.user.bean.AddTerminalRequest;
import com.chatak.pg.user.bean.AddTerminalResponse;
import com.chatak.pg.user.bean.DeleteTerminalRequest;
import com.chatak.pg.user.bean.DeleteTerminalResponse;
import com.chatak.pg.user.bean.GetTerminalListRequest;
import com.chatak.pg.user.bean.GetTerminalListResponse;
import com.chatak.pg.user.bean.UpdateTerminalRequest;
import com.chatak.pg.user.bean.UpdateTerminalResponse;


public interface TerminalDao {

	/**
	 * Method to Validate merchant and terminal Id of a request
	 * 
	 * @param merchantId
	 * @param terminalid
	 * @return 
	 * @throws DaoException
	 */
	public boolean validateTermial(Long merchantId, String terminalid) throws DataAccessException;
	
	/**
   * Method to check given terminal id is active
   * 
   * @param merchantId
   * @param terminalid
   * @return 
   * @throws DaoException
   */
  public boolean isActiveTermial(Long merchantId, String terminalid) throws DataAccessException;
	
	/**
	 * Method to add new terminal
	 * 
	 * @param terminalRequest
	 * @return
	 * @throws DataAccessException
	 */
	public AddTerminalResponse addTerminal(AddTerminalRequest terminalRequest) throws DataAccessException;

  /**
   * Method to update terminal
   * 
   * @param terminalRequest
   * @return
   * @throws DataAccessException
   */
  public UpdateTerminalResponse updateTerminal(UpdateTerminalRequest terminalRequest) throws DataAccessException;
  
  /**
   *  Method to delete terminal
   * 
   * @param deleteTerminalRequest
   * @return
   * @throws DataAccessException
   */
  public DeleteTerminalResponse deleteTerminal(DeleteTerminalRequest deleteTerminalRequest) throws DataAccessException;
  
  /**
   * Method to change the terminal status
   * 
   * @param terminal
   * @param status
   * @return
   * @throws DataAccessException
   */
  public boolean changeTerminalStatus(PGTerminal terminal, Integer status) throws DataAccessException;
  
  /**
   * Method to get terminals
   * 
   * @param getTerminalListRequest
   * @return
   * @throws DataAccessException
   */
  public GetTerminalListResponse getTerminals(GetTerminalListRequest getTerminalListRequest) throws DataAccessException;
  
  /**
   * Method to get terminal
   * 
   * @param terminalCode
   * @return
   * @throws DataAccessException
   */
  public PGTerminal getTerminal(Long terminalId) throws DataAccessException;
	
  
  /**
   * @param merchantCode
   * @return
   * @throws DataAccessException
   */
  public PGTerminal getTerminalonMerchantCode(Long merchantCode);
  
  public List<Map<String, String>> getMerchantId(Long terminalId) throws DataAccessException;


  
}
