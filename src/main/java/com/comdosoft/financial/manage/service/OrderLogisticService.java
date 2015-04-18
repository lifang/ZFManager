/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年3月22日 下午10:10:33
 */
package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.comdosoft.financial.manage.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
import com.comdosoft.financial.manage.domain.zhangfu.OrderLogistic;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CsOutStorageMapper;
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
	@Autowired
	private CsOutStorageMapper csOutStorageMapper;
	
	public int insert(Customer customer,Integer orderId,String logisticsName,String logisticsNumber,Integer quantity,String terminalSerialNum){
		OrderLogistic record=new OrderLogistic();
		Date createdAt = new Date();
		record.setCreatedAt(createdAt);
		record.setUpdatedAt(createdAt);
		record.setOrderId(orderId);
		record.setLogisticsName(logisticsName);
		record.setLogisticsNumber(logisticsNumber);
		CsOutStorage csOutStorage=new CsOutStorage(); 
		csOutStorage.setCreatedAt(createdAt);
		csOutStorage.setOrderId(orderId);
		csOutStorage.setUpdatedAt(createdAt);
		csOutStorage.setQuantity(quantity);
		csOutStorage.setProcessUserId(customer.getId());
		csOutStorage.setProcessUserName(customer.getName());
		csOutStorage.setQuantity(quantity);
		csOutStorage.setStatus(1);//已发货
		csOutStorageMapper.insert(csOutStorage);
		//terminalSerialNum还没有生成实体
		record.setCsOutStorageId(csOutStorage.getId());
		return orderLogisticMapper.insert(record);
	}
	
	/*
	 * 发货
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deliver(Customer customer,Integer orderId,String terminalSerialNum,String logisticsName,String logisticsNumber,String goodQuantity,String reserver2) throws Exception{
		Integer totalQuantity = 0;
		if(null!=goodQuantity){
			List<Map<String, Integer>> goodQuantityList = new ArrayList<Map<String, Integer>>();
			List<Integer> ids = new ArrayList<Integer>();
			String[] goodStr = goodQuantity.split(",");
			for (String goodQuantityE : goodStr) {
				String[] split2 = goodQuantityE.split(":");
				Integer id = Integer.parseInt(split2[0].replaceAll("deliverNum_",
						""));
				ids.add(id);
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("id", id);
				int quantity = Integer.parseInt(split2[1]);
				map.put("quantity", quantity);
				totalQuantity += quantity;
				goodQuantityList.add(map);
			}
		}
		Set<String> set=new HashSet<String>();
		if(null==terminalSerialNum){
			throw new Exception("请输入终端号！");
		}
		String[] split = terminalSerialNum.trim().split(",");
		for(String s:split){
			String[] split1 = s.trim().split("\n");
			for(String s1:split1){
				if(!"".equals(s1.trim())){
					set.add(s1.trim());
				}
			}
		}
		Order order=orderService.findOrderInfo(orderId);
		for(String s:set){
			Boolean isExist=false;
			List<Terminal> findTerminalsByNums = terminalMapper.findTerminalsByNums(new String[]{s});
			if(CollectionUtils.isEmpty(findTerminalsByNums)){
				throw new Exception("终端号不存在！");
			}
			for(OrderGood og:order.getOrderGoods()){
				for(Terminal findTerminalByNum:findTerminalsByNums){
					if(og.getGoodId().equals(findTerminalByNum.getGoodId())){
						findTerminalByNum.setOrderId(orderId);
						isExist=true;
						findTerminalByNum.setCustomerId(order.getCustomerId());
						if(order.getTypes()==3||order.getTypes()==4||order.getTypes()==5){
							findTerminalByNum.setAgentId(order.getBelongsAgent().getId());
						}
						findTerminalByNum.setPayChannelId(og.getPayChannelId());
						findTerminalByNum.setReserver2(reserver2);
						terminalMapper.updateByPrimaryKey(findTerminalByNum);
						break;
					}
				}
			}
			if(!isExist){
				throw new Exception("终端号与订单商品不对应！");
			}
		}
		Byte status=3;
		if(order.getTypes()==5){
			if(order.getTotalOutQuantity()+totalQuantity!=order.getTotalQuantity()){
				status=null;
			}
		}else{
			status=3;
		}
		orderService.save(orderId, status, null, null);
		if(0==totalQuantity){
			totalQuantity=order.getTotalQuantity();
		}
		insert(customer,orderId, logisticsName, logisticsNumber,totalQuantity,terminalSerialNum);
		return 1;
	}
}
