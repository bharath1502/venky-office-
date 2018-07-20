/**
 * 
 */
package com.chatak.pg.acq.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chatak.pg.acq.dao.model.PGBINRange;
import com.chatak.pg.bean.BinDuplicateResponse;
import com.chatak.pg.model.BinDTO;
import com.chatak.pg.model.BinResponse;

/**
 * @Author: Girmiti Software
 * @Date: Jun 10, 2015
 * @Time: 10:19:38 AM
 * @Version: 1.0
 * @Comments:
 *
 */
public interface BINDao {
  public List<BinDTO> getAllBins() throws DataAccessException;

  public List<Long> getAllActiveBins() throws DataAccessException;

  public boolean containsBin(Long bin) throws DataAccessException;

  public PGBINRange saveBin(PGBINRange pgbinRange) throws DataAccessException;

  public BinDTO findById(Long binId) throws DataAccessException;

  public BinResponse searchBin(BinDTO binDTO) throws DataAccessException;

  public BinDuplicateResponse getUserByBin(Long bin) throws DataAccessException;

  public PGBINRange findByBinId(Long binId) throws DataAccessException;

  public PGBINRange createOrUpdateBin(PGBINRange pGBINRange) throws DataAccessException;

}
