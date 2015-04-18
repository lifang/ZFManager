/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * ©2015年3月20日 下午1:32:19
 */
package com.comdosoft.financial.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderPayment;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderPaymentMapper;

@Service
public class OrderPaymentService {
	@Autowired
	private OrderPaymentMapper orderPaymentMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	public int insert(Integer orderId,Integer price,Byte payType,Integer createdUserId,Byte createdUserType){
		OrderPayment record=new OrderPayment();
		record.setOrderId(orderId);
		record.setPrice(price);
		record.setPayType(payType);
		record.setCreatedUserId(createdUserId);
		record.setCreatedUserType(createdUserType);
		record.setCreatedAt(new Date());
		return orderPaymentMapper.insert(record);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int payForBatch(Customer customer,Integer orderId,Byte payType,Float payPrice){
		insert(orderId, (int) (payPrice*100), payType, customer.getId(), customer.getTypes());
		Order order = orderMapper.findOrderInfo(orderId);
		List<OrderPayment> selectOrderPaymentsByOrderId = orderPaymentMapper.selectOrderPaymentsByOrderId(orderId);
		Integer total=0;
		for(OrderPayment orderPayment:selectOrderPaymentsByOrderId){
			total+=orderPayment.getPrice();
		}
		order.setStatus((byte) 3);//已发货
		if(order.getActualPrice().equals(total)){
			order.setPayStatus((byte) 2);//已付清
		}
		if(order.getFrontMoney()==null||order.getFrontMoney()<=total){
			order.setFrontPayStatus((byte) 2);//已付
		}
		orderMapper.updateByPrimaryKey(order);
		return 1;
	}
}
