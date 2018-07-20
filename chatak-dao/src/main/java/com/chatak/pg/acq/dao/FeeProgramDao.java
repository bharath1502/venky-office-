package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGAcquirerFeeValue;
import com.chatak.pg.acq.dao.model.PGFeeProgram;
import com.chatak.pg.acq.dao.model.PGFeeProgramValue;
import com.chatak.pg.bean.Response;
import com.chatak.pg.model.FeeProgramDTO;

public interface FeeProgramDao {

	
	/**
	 * @param feeProgramName
	 * @return
	 * @throws DataAccessException
	 */
	public List<PGFeeProgram> findByFeeProgramName(String feeProgramName)throws DataAccessException;
	
	/**
	 * @param feeProgramDaoDetails
	 * @return
	 * @throws DataAccessException
	 */
	public  PGFeeProgram createFeeProgram(PGFeeProgram feeProgramDaoDetails)throws DataAccessException;
	
	
	/**
	 * @param feeProgramValueDaoDetails
	 * @return
	 * @throws DataAccessException
	 */
	public  List<PGFeeProgramValue> createFeeProgramValue(List<PGFeeProgramValue> feeProgramValueDaoDetails)throws DataAccessException;
	
	
	
	/**
	 * @param feeProgramRequest
	 * @return
	 * @throws PrepaidAdminException
	 */
	public List<FeeProgramDTO> searchFeeProgramForJpa(FeeProgramDTO feeProgramDTO) throws DataAccessException;
	
	
	/**
	 * @param feePgmId
	 * @return
	 * @throws ChatakAdminException
	 */
	public List<PGFeeProgram> getByFeeProgramId(Long feePgmId)throws DataAccessException;
	
	
	/**
	 * @param feePgmId
	 * @return
	 * @throws ChatakAdminException
	 */
	public List<PGFeeProgramValue> getByFeeProgramValueId(Long feePgmId)throws DataAccessException;	
	
	
	/**
	 * @param feeProgramValueDaoDetails
	 * @throws ChatakAdminException
	 * @throws Exception
	 */
	public void removeFeeProgram(List<PGFeeProgramValue> feeProgramValueDaoDetails)throws DataAccessException;
	
	/**
	 * @param merchantId
	 * @param cardType
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public List<PGAcquirerFeeValue> getAcquirerFeeValueByMerchantIdAndCardType(String merchantId,String cardType);

	/**
	 * @param getFeeProgramId
	 * @return
	 */
	public Response deleteFeeProgram(Long getFeeProgramId);
	
	public PGFeeProgram getFeeprogramName(String feeProgramName);
	
	public List<PGFeeProgram> findByCardProgramId(Long cardProgramId);
	
	public List<PGAcquirerFeeValue> getAcquirerFeeValueByCardProgramId(Long cardProgramId);
	
}
