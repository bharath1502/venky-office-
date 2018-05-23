package com.chatak.acquirer.admin.service;

import java.util.List;

import com.chatak.acquirer.admin.exception.ChatakAdminException;
import com.chatak.pg.bean.BinDuplicateResponse;
import com.chatak.pg.model.BinDTO;
import com.chatak.pg.model.BinResponse;

public interface BinService {
	
	public List<BinDTO> searchBin(BinDTO binDTO) throws ChatakAdminException;
	
	public BinDTO saveOrUpdateBin(BinDTO binDTO) throws ChatakAdminException;
	
	public BinDTO searchBinById(Long binId) throws ChatakAdminException;
	
	public BinResponse searchBins(BinDTO binDTO) throws ChatakAdminException;
	
	public BinDuplicateResponse validateBin(Long bin) throws ChatakAdminException;
	
	public BinResponse changeBinStatus(BinDTO binDTO) throws ChatakAdminException;
	

}
