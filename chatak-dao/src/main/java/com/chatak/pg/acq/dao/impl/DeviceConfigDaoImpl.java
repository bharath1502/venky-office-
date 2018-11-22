/**
 * 
 */
package com.chatak.pg.acq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.chatak.pg.acq.dao.DeviceConfigDao;
import com.chatak.pg.acq.dao.model.Device;
import com.chatak.pg.acq.dao.model.QDevice;
import com.chatak.pg.acq.dao.repository.DeviceRepository;
import com.chatak.pg.dao.util.StringUtil;
import com.chatak.pg.model.PosDeviceDTO;
import com.chatak.pg.util.Constants;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;

/**
 * @Author: Girmiti Software
 * @Date: Aug 3, 2016
 * @Time: 5:14:53 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
@Repository("deviceConfigDao")
public class DeviceConfigDaoImpl implements DeviceConfigDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	DeviceRepository deviceRepository;
	/**
	 * @param deviceTO
	 * @return
	 */
	@Override
	public List<Device> searchDeviceData(PosDeviceDTO deviceTO) {
		Integer offset = 0;
		Integer limit = 0;
		Integer pageIndex = deviceTO.getPageIndex();
		Integer totalRecords;
		Integer pageSize = deviceTO.getPageSize();
		
		
		 if(deviceTO.getPageIndex() == null || deviceTO.getPageIndex() == 1){
	            totalRecords = getTotalNumberOfRecords(deviceTO);
	            deviceTO.setNoOfRecords(totalRecords);
	     }

		if (pageIndex == null && pageSize == null) {
			offset = 0;
			limit = Constants.DEFAULT_PAGE_SIZE;
		} else {
			limit = pageSize;
			offset = (pageIndex - 1) * pageSize;
		}
		JPAQuery query = new JPAQuery(entityManager);
		QDevice qDevice = QDevice.device;
		List<Device> list = query.from(qDevice)
				                        .where(isDeviceMakeEq(deviceTO.getDeviceMake()),
				                        		isDeviceTypeEq(deviceTO.getDeviceType()),
				                        		isDeviceModelEq(deviceTO.getDeviceModel()),
								               isStatusEq(deviceTO.getDeviceStatus()))
								       .offset(offset)
						               .limit(limit).orderBy(orderByDeviceIdDesc())
						               .list(qDevice);
		return list;
	}

	/**
	 * @param deviceTO
	 * @return
	 */
	private Integer getTotalNumberOfRecords(PosDeviceDTO deviceTO) {
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> list = query.from(QDevice.device)
				 .where(isDeviceMakeEq(deviceTO.getDeviceMake()),
                 		isDeviceTypeEq(deviceTO.getDeviceType()),
                 		isDeviceModelEq(deviceTO.getDeviceModel()),
                 		isStatusEq(deviceTO.getDeviceStatus()))
						.list(QDevice.device.id);
		return (StringUtil.isListNotNullNEmpty(list) ? list.size() : 0);
	}
	
	private OrderSpecifier<Long> orderByDeviceIdDesc() {
        return QDevice.device.id.desc();
    }
	
	private BooleanExpression isDeviceMakeEq(String deviceMake) {
		return (deviceMake != null && !"".equals(deviceMake)) ?QDevice.device.deviceMake.eq(deviceMake): null;
	}
	
	private BooleanExpression isDeviceTypeEq(String deviceType) {
		return (deviceType != null && !"".equals(deviceType)) ?QDevice.device.deviceType.eq(deviceType): null;
	}
	
	private BooleanExpression isDeviceModelEq(String deviceModel) {
		return (deviceModel != null && !"".equals(deviceModel)) ?QDevice.device.deviceModel.eq(deviceModel): null;
	}
    
	private BooleanExpression isStatusEq(Integer status) {
		return status != null ? QDevice.device.deviceStatus.eq(status): null;
	}

	/**
	 * @param device
	 * @return
	 */
	@Override
	public Device createOrUpdateDevice(Device device) {
		return deviceRepository.save(device);
	}
	
	@Override
	public Device findByDeviceId(Long deviceId) {
		return deviceRepository.findOne(deviceId);
	}

	/**
	 * @return
	 */
	@Override
	public List<PosDeviceDTO> getDeviceData() {
		List<PosDeviceDTO> deviceData = null;
		  JPAQuery query = new JPAQuery(entityManager);
		  List<Tuple> tupleList = query.from(QDevice.device).
				  where(QDevice.device.deviceStatus.eq(0)).
						  orderBy(orderByDeviceIdDesc()).
						  list(QDevice.device.id,
								  QDevice.device.deviceMake,
								  QDevice.device.deviceModel,
								  QDevice.device.deviceType);
		  if(!CollectionUtils.isEmpty(tupleList)) {
			  deviceData = new ArrayList<PosDeviceDTO>();
			  PosDeviceDTO device = null;
			  for(Tuple tuple : tupleList) {
				  device = new PosDeviceDTO();
				  device.setId(tuple.get(QDevice.device.id));
				  device.setDeviceMake(tuple.get(QDevice.device.deviceMake));
				  device.setDeviceModel(tuple.get(QDevice.device.deviceModel));
				  device.setDeviceType(tuple.get(QDevice.device.deviceType));
				  deviceData.add(device);
			  }
		  }
		  return deviceData;
 }
	
}
