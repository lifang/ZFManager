/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年3月22日 下午10:10:33
 */
package com.comdosoft.financial.manage.service;

import java.text.SimpleDateFormat;
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
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
import com.comdosoft.financial.manage.domain.zhangfu.OrderLogistic;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CsOutStorageMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
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
	@Autowired
	private GoodMapper goodMapper;
	
	
	private int insert(Customer customer,Order order,String logisticsName,String logisticsNumber,Integer quantity,String terminalSerialNum,Integer csOutStorageStatus){
		OrderLogistic record=new OrderLogistic();
		Date createdAt = new Date();
		record.setCreatedAt(createdAt);
		record.setUpdatedAt(createdAt);
		record.setOrderId(order.getId());
		record.setLogisticsName(logisticsName);
		record.setLogisticsNumber(logisticsNumber);
		CsOutStorage csOutStorage=new CsOutStorage(); 
		csOutStorage.setCreatedAt(createdAt);
		csOutStorage.setOrderId(order.getId());
		csOutStorage.setUpdatedAt(createdAt);
		csOutStorage.setQuantity(quantity);
		csOutStorage.setProcessUserId(customer.getId());
		csOutStorage.setProcessUserName(customer.getName());
		//csOutStorage.setQuantity(quantity);
		csOutStorage.setStatus(csOutStorageStatus);//1待处理，2已取消，3处理完成
		csOutStorage.setTerminalList(terminalSerialNum);
		csOutStorage.setApplyNum(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(createdAt));
		csOutStorage.setDescription(order.getOrderNumber());
		csOutStorageMapper.insert(csOutStorage);
		record.setCsOutStorageId(csOutStorage.getId());
		if(order.getBelongsTo() == null){ 
			return 1;
		}
		return orderLogisticMapper.insert(record);
	}
	
	/*
	 * 发货
	 */
	@Transactional(rollbackFor=Exception.class)
	public int deliver(Customer customer,Integer orderId,String terminalSerialNum,String logisticsName,String logisticsNumber,String goodQuantity,String reserver2) throws Exception{
		Order order=orderService.findOrderInfo(orderId);
		if(null==order.getBelongsTo()){//订单属于掌富，发货数量，状态为1
			return insert(customer, order, logisticsName, logisticsNumber, order.getTotalQuantity(), terminalSerialNum, 1);
		}
		Integer totalQuantity = 0;
		Integer csOutStorageStatus=3;
		Map<Integer,Integer> mapGoodQuantity=new HashMap<Integer, Integer>();
		if(null!=goodQuantity){
			List<Integer> ids = new ArrayList<Integer>();
			String[] goodStr = goodQuantity.split(",");
			for (String goodQuantityE : goodStr) {
				String[] split2 = goodQuantityE.split(":");
				Integer id = Integer.parseInt(split2[0].replaceAll("deliverNum_",
						""));
				ids.add(id);
				int quantity = Integer.parseInt(split2[1]);
				mapGoodQuantity.put(id, quantity);
				totalQuantity += quantity;
			}
		}
		if((order.getTotalOutQuantity()+totalQuantity)>order.getTotalQuantity()){
			throw new Exception("发货失败，发货总量已超过订单商品数量！");
		}
		Set<String> set=new HashSet<String>();
		if(null==terminalSerialNum||"".equals(terminalSerialNum.trim())){
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
		StringBuilder sb = new StringBuilder("");
		String temp = "";
		for(String s:set){
			Terminal terminal = terminalMapper.findTerminal(s);
			if(terminal == null){
				sb.append(s+"，");
			}
		}
		temp = sb.toString();
		if(!"".equals(temp)){
			throw new Exception("终端号为"+temp.substring(0, temp.lastIndexOf("，"))+"的无法发货");
		}
		List<OrderGood> orderGoodDelivered=new ArrayList<OrderGood>();
		for(String s:set){
			Boolean isExist=false;
			List<Terminal> findTerminalsByNums = terminalMapper.findTerminalsByNums(new String[]{s});
			if(CollectionUtils.isEmpty(findTerminalsByNums)){
				throw new Exception("终端号不存在！");
			}
			
			for(int i=0;i<order.getOrderGoods().size();i++){
				OrderGood og=order.getOrderGoods().get(i);
				if(csOutStorageStatus==3&&null==og.getGood().getBelongsTo()){
					csOutStorageStatus=1;
				}
				for(Terminal findTerminalByNum:findTerminalsByNums){
					if(og.getGoodId().equals(findTerminalByNum.getGoodId())){
						Good good=goodMapper.selectByPrimaryKey(og.getGoodId());
						if(order.getTypes()==1||order.getTypes()==3){
							Integer volumeNumber = good.getVolumeNumber()!=null?good.getVolumeNumber():0;
							good.setVolumeNumber(volumeNumber+og.getQuantity());
						}else if(order.getTypes()==5){
							Integer purchaseNumber = good.getPurchaseNumber()!=null?good.getPurchaseNumber():0;
							if(null==og.getId()){
								throw new Exception("没有找到商品对应的发货量");
							}
							good.setPurchaseNumber(purchaseNumber+mapGoodQuantity.get(og.getId()));
						}
						goodMapper.updateByPrimaryKey(good);
						findTerminalByNum.setOrderId(orderId);
						isExist=true;
						findTerminalByNum.setCustomerId(order.getCustomerId());
						if(order.getTypes()==3||order.getTypes()==4||order.getTypes()==5){
							findTerminalByNum.setAgentId(order.getBelongsAgent().getId());
						}
						findTerminalByNum.setPayChannelId(og.getPayChannelId());
						findTerminalByNum.setReserver2(reserver2);
						terminalMapper.updateByPrimaryKey(findTerminalByNum);
						orderGoodDelivered.add(order.getOrderGoods().get(i));
						order.getOrderGoods().remove(i);
						break;
					}
				}
			}
			for(Terminal findTerminalByNum:findTerminalsByNums){
				for(OrderGood orderGood:orderGoodDelivered){
					if(orderGood.getGoodId().equals(findTerminalByNum.getGoodId())){
						findTerminalByNum.setOrderId(orderId);
						isExist=true;
						findTerminalByNum.setCustomerId(order.getCustomerId());
						if(order.getTypes()==3||order.getTypes()==4||order.getTypes()==5){
							findTerminalByNum.setAgentId(order.getBelongsAgent().getId());
						}
						findTerminalByNum.setPayChannelId(orderGood.getPayChannelId());
						findTerminalByNum.setReserver2(reserver2);
						terminalMapper.updateByPrimaryKey(findTerminalByNum);
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
		}
		if(null==order.getBelongsTo()){
			csOutStorageStatus=1;
		}
		orderService.save(orderId, status, null, null);
		if(0==totalQuantity){
			totalQuantity=order.getTotalQuantity();
		}
		insert(customer,order, logisticsName, logisticsNumber,totalQuantity,terminalSerialNum,csOutStorageStatus);
		return 1;
	}

	public CsOutStorage judgeDeliver(Integer orderId) {
		return csOutStorageMapper.selectByOrderId(orderId);
	}
}
