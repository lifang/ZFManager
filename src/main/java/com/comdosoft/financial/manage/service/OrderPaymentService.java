/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2015年3月20日 下午1:32:19
 */
package com.comdosoft.financial.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.OrderPayment;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderPaymentMapper;

@Service
public class OrderPaymentService {
	@Autowired
	private OrderPaymentMapper orderPaymentMapper;
	
	public int insert(Integer orderId,Integer price,Byte payType,Integer createdUserId,Byte createdUserType){
		OrderPayment record=new OrderPayment();
		record.setOrderId(orderId);
		record.setPrice(price);
		record.setPayType(payType);
		record.setCreatedUserId(createdUserId);
		record.setCreatedUserType(createdUserType);
		return orderPaymentMapper.insert(record);
	}
}
