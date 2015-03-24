/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年3月22日 下午10:10:33
 */
package com.comdosoft.financial.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.OrderLogistic;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderLogisticMapper;

@Service
public class OrderLogisticService {

	@Autowired
	private OrderLogisticMapper orderLogisticMapper;
	
	public int insert(Integer orderId,String logisticsName,String logisticsNumber){
		OrderLogistic record=new OrderLogistic();
		Date createdAt = new Date();
		record.setCreatedAt(createdAt);
		record.setUpdatedAt(createdAt);
		record.setOrderId(orderId);
		record.setLogisticsName(logisticsName);
		record.setLogisticsNumber(logisticsNumber);
//		record.setCsOutStorageId(csOutStorageId);//这个值来源未知
		return orderLogisticMapper.insert(record);
	}
}
