package com.chatak.acquirer.admin.model;

import java.util.List;

import com.chatak.pg.model.CAPublicKeysDTO;

public class CAPublicKeysResponse extends Response {

	private static final long serialVersionUID = 1L;
	private List<CAPublicKeysDTO> caPublicKeysList;

	public List<CAPublicKeysDTO> getCaPublicKeysList() {
		return caPublicKeysList;
	}

	public void setCaPublicKeysList(List<CAPublicKeysDTO> caPublicKeysList) {
		this.caPublicKeysList = caPublicKeysList;
	}
	
}
