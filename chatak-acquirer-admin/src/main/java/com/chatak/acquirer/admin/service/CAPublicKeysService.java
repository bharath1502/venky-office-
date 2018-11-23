package com.chatak.acquirer.admin.service;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.acquirer.admin.model.CAPublicKeysResponse;
import com.chatak.acquirer.admin.model.Response;
import com.chatak.pg.acq.dao.model.PGCaPublicKeys;
import com.chatak.pg.bean.PublickeyNameResponse;
import com.chatak.pg.model.CAPublicKeysDTO;

public interface CAPublicKeysService {

	public Response createCAPublicKeys(CAPublicKeysDTO caPublicKeysDTO)
			throws ChatakAdminException;

	public Response updateCAPublicKeys(CAPublicKeysDTO caPublicKeysDTO)
			throws ChatakAdminException;

	public PGCaPublicKeys caPublicKeysById(Long getCAPublicKeysId);

	public CAPublicKeysResponse searchCAPublicKeys(
			CAPublicKeysDTO caPublicKeysDTO) throws ChatakAdminException;
	
	public CAPublicKeysResponse changeCAPublicKeysStatus(CAPublicKeysDTO caPublicKeysDTO);
	
	public PublickeyNameResponse validatePublickeyName(String publicKeyName);

	public CAPublicKeysDTO saveOrUpdateCAPublicKey(CAPublicKeysDTO caPublicKeyDTO)throws ChatakAdminException;
	
	public CAPublicKeysDTO findCAPublicKeyById(Long getCAPublicKeysId);

}

