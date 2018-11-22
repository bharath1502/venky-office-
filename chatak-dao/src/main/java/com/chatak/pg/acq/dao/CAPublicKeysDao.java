package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.model.CAPublicKeysDTO;

/**
 * @Author: Girmiti Software
 * @Date: Aug 4, 2016
 * @Time: 6:25:58 PM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface CAPublicKeysDao {

	public PGCaPublicKeys createCAPublicKeys(
			PGCaPublicKeys caPublicKeysDaoDetails) throws DataAccessException;

	public PGCaPublicKeys updateCAPublicKeys(
			PGCaPublicKeys caPublicKeysDaoDetails);

	public PGCaPublicKeys caPublicKeysById(Long getCAPublicKeysId);

	public List<CAPublicKeysDTO> searchCAPublicKeys(
			CAPublicKeysDTO caPublicKeysDTO) throws DataAccessException;
	
	public PGCaPublicKeys getpublicKeyName(String publicKeyName);
	
	public PGCaPublicKeys saveCAPublicKey(PGCaPublicKeys pgCaPublicKey) throws DataAccessException;

	public CAPublicKeysDTO findByPublicKeyId(Long getCAPublicKeysId);

}