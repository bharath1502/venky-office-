package com.chatak.pg.acq.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chatak.pg.acq.dao.AssetManagementDao;
import com.chatak.pg.acq.dao.model.PGPosDevice;
import com.chatak.pg.acq.dao.model.QPGPosDevice;
import com.chatak.pg.acq.dao.repository.AssetManagementRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.PosDeviceDTO;
import com.chatak.pg.user.bean.DeleteAssetResponse;
import com.chatak.pg.util.Constants;
import com.chatak.pg.util.DateUtil;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

@Repository("assetManagementDao")
public class AssetManagementDaoImpl implements AssetManagementDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private AssetManagementRepository assetManagementRepository;

	@Override
	public PGPosDevice createOrUpdateDevice(PGPosDevice device) {
		return assetManagementRepository.save(device);
	}

	@Override
	public List<PGPosDevice> searchAssetDevice(PosDeviceDTO deviceTO) {
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = deviceTO.getPageIndex();
		Integer pageSize = deviceTO.getPageSize();
		Integer totalRecords;
		
		 if(deviceTO.getPageIndex() == null || deviceTO.getPageIndex() == 1){
	            totalRecords = getTotalNumberOfRecords(deviceTO);
	            deviceTO.setNoOfRecords(totalRecords);
	     }

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			offset = (pageIndex - 1) * pageSize;
			limit = pageSize;
		}
		JPAQuery query = new JPAQuery(entityManager);
		QPGPosDevice qDevice = QPGPosDevice.pGPosDevice;
		List<PGPosDevice> list = query.from(qDevice)
				                        .where(isDeviceSerialNoLike(deviceTO.getDeviceSerialNo()),
				                        		isDeviceMakeEq(deviceTO.getDeviceMake()),
				                        		isDeviceTypeEq(deviceTO.getDeviceType()),
				                        		isDeviceModelEq(deviceTO.getDeviceModel()),
								               isStatusEq(deviceTO.getStatus()))
								       .offset(offset)
						               .limit(limit).orderBy(orderByDeviceIdDesc())
						               .list(qDevice);
		return list;
	}
	
	private int getTotalNumberOfRecords(PosDeviceDTO deviceTO) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> list = query.from(QPGPosDevice.pGPosDevice)
				 .where(isDeviceSerialNoLike(deviceTO.getDeviceSerialNo()),
                 		isDeviceMakeEq(deviceTO.getDeviceMake()),
                 		isDeviceTypeEq(deviceTO.getDeviceType()),
                 		isDeviceModelEq(deviceTO.getDeviceModel()),
			               isStatusEq(deviceTO.getStatus()))
						.list(QPGPosDevice.pGPosDevice.id);
		return (StringUtil.isListNotNullNEmpty(list) ? list.size() : 0);
	}

	private OrderSpecifier<Long> orderByDeviceIdDesc() {
        return QPGPosDevice.pGPosDevice.id.desc();
    }

	private BooleanExpression isDeviceSerialNoLike(String deviceSerialNo) {
		return (deviceSerialNo != null && !"".equals(deviceSerialNo))? QPGPosDevice.pGPosDevice.deviceSerialNo.toUpperCase().like("%" + deviceSerialNo.toUpperCase().replace("*", "") + "%"): null;
	}
	
	private BooleanExpression isDeviceMakeEq(String deviceMake) {
		return (deviceMake != null && !"".equals(deviceMake)) ?QPGPosDevice.pGPosDevice.deviceMake.eq(deviceMake): null;
	}
	
	private BooleanExpression isDeviceTypeEq(String deviceType) {
		return (deviceType != null && !"".equals(deviceType)) ?QPGPosDevice.pGPosDevice.deviceType.eq(deviceType): null;
	}
	
	private BooleanExpression isDeviceModelEq(String deviceModel) {
		return (deviceModel != null && !"".equals(deviceModel)) ?QPGPosDevice.pGPosDevice.deviceModel.eq(deviceModel): null;
	}
    
	private BooleanExpression isStatusEq(String status) {
		return (status != null && !"".equals(status))? QPGPosDevice.pGPosDevice.status.eq(status): null;
	}
	
	@Override
	public PGPosDevice findByDeviceId(Long deviceId) {
		return assetManagementRepository.findOne(deviceId);
	}

	@Override
	public List<PGPosDevice> findByDeviceSerialNoIgnoreCase(String deviceSerialNo) {
		return assetManagementRepository.findByDeviceSerialNoIgnoreCase(deviceSerialNo);
	}

	/**
	 * @param deviceSerialNo
	 * @return
	 */
	@Override
	  public DeleteAssetResponse deleteAssetDevice(Long id) {
	  DeleteAssetResponse deleteAssetResponse = new DeleteAssetResponse();
	  PGPosDevice posDeviceDb = assetManagementRepository.findById(id);
	  if(null != posDeviceDb) {

		  posDeviceDb.setStatus("Deleted");
		  posDeviceDb.setUpdatedDate(DateUtil.getCurrentTimestamp());		 
		  assetManagementRepository.save(posDeviceDb);
		  deleteAssetResponse.setIsdeleated(true);
		  deleteAssetResponse.setErrorCode("00");
		  deleteAssetResponse.setErrorMessage("Success");
		  return deleteAssetResponse;
	  }
	  else {
		  deleteAssetResponse.setIsdeleated(false);
		  deleteAssetResponse.setErrorCode("11");
		  deleteAssetResponse.setErrorMessage("Failure");
		  return deleteAssetResponse;
		  }
	
	}
}
