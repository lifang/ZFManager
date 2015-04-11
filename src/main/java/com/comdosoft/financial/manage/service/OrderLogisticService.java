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

import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
import com.comdosoft.financial.manage.domain.zhangfu.OrderLogistic;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderLogisticMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;

@Service
public class OrderLogisticService {

	@Autowired
	private OrderLogisticMapper orderLogisticMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private OrderService orderService;
	
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
	
	public int deliver(Integer orderId,String terminalSerialNum,String logisticsName,String logisticsNumber) throws Exception{
		String[] split = terminalSerialNum.split(",");
		Order order=orderService.findOrderInfo(orderId);
		for(String s:split){
			Boolean isExist=false;
			Terminal findTerminalByNum = terminalMapper.findTerminalByNum(s);
			for(OrderGood og:order.getOrderGoods()){
				if(og.getGoodId().equals(findTerminalByNum.getGoodId())){
					findTerminalByNum.setOrderId(orderId);
					isExist=true;
					break;
				}
			}
			if(!isExist){
				throw new Exception("终端号不存在！");
			}
			
		}
		return 1;
	}
}
