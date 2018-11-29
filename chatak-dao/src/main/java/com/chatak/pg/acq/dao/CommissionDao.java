/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGCommission;
import com.chatak.pg.acq.dao.model.PGOtherCommission;
import com.chatak.pg.model.CommissionDTO;

/**
 * @Author: Girmiti Software
 * @Date: Aug 24, 2016
 * @Time: 8:29:06 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface CommissionDao 

{
	public PGCommission createCommissionProgram(PGCommission commissionDaoDetails)throws DataAccessException;

	public List<PGCommission> searchCommissionProgram(CommissionDTO commissionDTO);

	public List<PGCommission> getByCommProgramId(Long commissionProgramId) throws DataAccessException;

	public List<PGCommission> findByCommissionName(String commissionName);

	public List<PGOtherCommission> getOtherCommissionByCommissionProgramId(Long commissionProgramId);

	public void removeOthCommission(List<PGOtherCommission> othCommission);

}
