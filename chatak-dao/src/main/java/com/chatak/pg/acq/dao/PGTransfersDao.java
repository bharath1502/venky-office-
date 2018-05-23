/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGTransfers;
import com.chatak.pg.model.ReportsDTO;
import com.chatak.pg.model.TransferRequestsCount;
import com.chatak.pg.user.bean.GetTransferListRequest;

/**
 * @Author: Girmiti Software
 * @Date: Aug 16, 2015
 * @Time: 5:26:20 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public interface PGTransfersDao {
public List<PGTransfers> getPGTransferRecordsByMerchantId(Long merchantId)throws DataAccessException;
public PGTransfers getPGTransferRecordById(Long id)throws DataAccessException;
public PGTransfers createOrUpdateTransferRecord(PGTransfers pgTransfers)throws DataAccessException;
public TransferRequestsCount getTransferRequestCountOnTransferMode(String transferMode) throws DataAccessException;
public List<PGTransfers> getTransferList(GetTransferListRequest request) throws DataAccessException;

public List<ReportsDTO> getPGTransfersListOnTransferMode(GetTransferListRequest request) ;
public List<ReportsDTO> getAllEftTransfersListOnMerchantCode(GetTransferListRequest request) ;

}
