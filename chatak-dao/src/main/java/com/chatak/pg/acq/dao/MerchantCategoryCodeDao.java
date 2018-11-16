/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGMerchantCategoryCode;
import com.chatak.pg.user.bean.GetMerchantCategoryCodeListResponse;
import com.chatak.pg.user.bean.MerchantCategoryCodeRequest;
import com.chatak.pg.user.bean.MerchantCategoryCodeResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface MerchantCategoryCodeDao.
 *
 * @Author: Girmiti Software
 * @Date: Aug 22, 2016
 * @Time: 12:46:47 PM
 * @Version: 1.0
 * @Comments: 
 */
public interface MerchantCategoryCodeDao {

	/**
	 * Creates the MCC.
	 *
	 * @param mccRequest the mcc request
	 * @return the merchant category code response
	 */
	public MerchantCategoryCodeResponse createMCC(
			MerchantCategoryCodeRequest mccRequest);

	/**
	 * Gets the MCC list.
	 *
	 * @param mccRequest the mcc request
	 * @return the MCC list
	 */
	public GetMerchantCategoryCodeListResponse getMCCList(
			MerchantCategoryCodeRequest mccRequest);

	/**
	 * Find by merchant category code.
	 *
	 * @param mcc the mcc
	 * @return the PG merchant category code
	 * @throws DataAccessException the data access exception
	 */
	public Optional<PGMerchantCategoryCode> findById(Long mcc)
			throws DataAccessException;

	/**
	 * Update MCC.
	 *
	 * @param mccRequest the mcc request
	 * @return the merchant category code response
	 */
	public MerchantCategoryCodeResponse updateMCC(
			MerchantCategoryCodeRequest mccRequest);
	
	public List<String> getAllMCC();
	
	public PGMerchantCategoryCode createOrUpdateMerchantCategoryCode(PGMerchantCategoryCode pgMerchantCategoryCode) throws DataAccessException;

}
