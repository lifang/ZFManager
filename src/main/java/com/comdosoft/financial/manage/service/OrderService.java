package com.comdosoft.financial.manage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.comdosoft.financial.manage.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
import com.comdosoft.financial.manage.domain.zhangfu.OrderPayment;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.SysConfig;
import com.comdosoft.financial.manage.mapper.zhangfu.CsOutStorageMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.FactoryMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodsPictureMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderGoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderPaymentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.PayChannelMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.SysConfigMapper;
import com.comdosoft.financial.manage.utils.Constant;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class OrderService {
	@Value("${page.size}")
	private Integer pageSize;
	@Value("${filePath}")
    private String filePath ;
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderGoodMapper orderGoodMapper;

	@Autowired
	private GoodsPictureMapper goodsPictureMapper;

	@Autowired
	private GoodMapper goodMapper;
	@Autowired
	private OrderPaymentMapper orderPaymentMapper;
	
	@Autowired
	private FactoryMapper factoryMapper;
	
	@Autowired
	private CsOutStorageMapper csOutStorageMapper;
	
	@Autowired
	private SysConfigMapper sysConfigMapper;
	@Autowired
	private PayChannelMapper payChannelMapper;

	/**
	 * agents表customer_id不能重复,分页问题时出现每页不满pageSize的现象
	 */
	public Page<Order> findPages(int page, Byte status, String keys,
			Integer factoryId, List<Byte> types) {
		pageSize = 3;
		if (keys != null) {
			if ("".equals(keys.trim())) {
				keys = null;
			} else {
				keys = "%" + keys.trim() + "%";
			}
		}
		List<Integer> orderIdsGood=null;
		if(null!=factoryId&&factoryId>0){
			orderIdsGood=new ArrayList<Integer>();
			Factory factory = factoryMapper.selectByPrimaryKey(factoryId);
			List<OrderGood> selectOrderGoodByGoodCreate = orderGoodMapper.selectOrderGoodByGoodCreate(factory.getCustomerId());
			if(!CollectionUtils.isEmpty(selectOrderGoodByGoodCreate)){
				for(OrderGood og:selectOrderGoodByGoodCreate ){
					orderIdsGood.add(og.getOrderId());
				}
			}else{
				orderIdsGood.add(-1);
			}
			
		}
		long count = orderMapper.countByKeys(status, keys, null, types,orderIdsGood);
		if (count == 0) {
			return new Page<Order>(new PageRequest(1, pageSize),
					new ArrayList<Order>(), count);
		}
		PageRequest request = new PageRequest(page, pageSize);
		List<Order> result = null;
		result = orderMapper.findPageOrdersByKeys(request, status, keys,
				null, types,orderIdsGood);
		Page<Order> orders = new Page<>(request, result, count);
		if (orders.getCurrentPage() > orders.getTotalPage()) {
			request = new PageRequest(orders.getTotalPage(), pageSize);
			result = orderMapper.findPageOrdersByKeys(request, status, keys,
					null, types,orderIdsGood);
			orders = new Page<>(request, result, count);
		}
		List<Integer> orderIds = new ArrayList<Integer>();
		for (Order o : result) {
			orderIds.add(o.getId());
		}
		List<OrderGood> selectOrderGoods = orderGoodMapper
				.selectOrderGoods(orderIds);
		List<Integer> goodIds = new ArrayList<Integer>();
		List<OrderPayment> orderPaymentList = orderPaymentMapper.selectByOrderIds(orderIds);
		List<CsOutStorage> csOutStorages=null;
		if(!CollectionUtils.isEmpty(types)&& types.get(0)==5){
			csOutStorages = csOutStorageMapper.selectByOrderIds(orderIds);
		}
		for (Order order : result) {
			order.setOrderGoods(new ArrayList<OrderGood>());
			order.setOrderPayments(new ArrayList<OrderPayment>());
			order.setCsOutStorages(new ArrayList<CsOutStorage>() );
			for (int i = 0, size = selectOrderGoods.size(); i < size; i++) {
				OrderGood o = selectOrderGoods.get(i);
				if (order.getId().equals(o.getOrderId())) {
					order.getOrderGoods().add(o);
					goodIds.add(o.getGoodId());
				}
			}
			if(!CollectionUtils.isEmpty(orderPaymentList)){
				for(OrderPayment orderPayment:orderPaymentList){
					if(order.getId().equals(orderPayment.getOrderId())){
						order.getOrderPayments().add(orderPayment);
					}
				}
			}
			if(!CollectionUtils.isEmpty(csOutStorages)){
				for(CsOutStorage csOutStorage:csOutStorages){
					if(order.getId().equals(csOutStorage.getOrderId())){
						order.getCsOutStorages().add(csOutStorage);
					}
				}
			}
		}
		if (!CollectionUtils.isEmpty(goodIds)) {
			List<GoodsPicture> selectGoodsPictures = goodsPictureMapper
					.selectGoodsPictures(goodIds);
			for (OrderGood og : selectOrderGoods) {
				if (null != og.getGood()) {
					og.getGood().setPictures(new ArrayList<GoodsPicture>());
					for (GoodsPicture gp : selectGoodsPictures) {
						if (og.getGoodId().equals(gp.getGoodId())) {
						    if(gp.getUrlPath().split(filePath).length<2){
						        gp.setUrlPath(filePath+gp.getUrlPath());
						    }
							og.getGood().getPictures().add(gp);
						}
					}
				}
			}
		}
		return orders;
	}
	

	
	public Order findOrderInfo(Integer id) {
		Order order = orderMapper.findOrderInfo(id);
		List<Integer> goodIds = new ArrayList<Integer>();
		for (OrderGood orderGood : order.getOrderGoods()) {
			goodIds.add(orderGood.getGoodId());
		}
		if (!CollectionUtils.isEmpty(goodIds)) {
			List<GoodsPicture> selectGoodsPictures = goodsPictureMapper
					.selectGoodsPictures(goodIds);
			for (OrderGood og : order.getOrderGoods()) {
				if (null != og.getGood()) {
					og.getGood().setPictures(new ArrayList<GoodsPicture>());
					for (GoodsPicture gp : selectGoodsPictures) {
						if (og.getGoodId().equals(gp.getGoodId())) {
						    gp.setUrlPath(filePath+gp.getUrlPath());
							og.getGood().getPictures().add(gp);
						}
					}
				}
			}
		}
		return order;
	}
	
	/**
	 * @description 支付批购定金 
	 * @author Tory
	 * @date 2015年4月18日 上午11:36:48
	 */
	public int savePayFront(Integer orderId){
		Order record = orderMapper.findOrderInfo(orderId);
		record.setStatus((byte) 2);
		record.setFrontPayStatus((byte) 2);
		if(record.getFrontMoney().equals(record.getActualPrice())){
			record.setPayStatus((byte) 2);
		}
		return orderMapper.updateByPrimaryKey(record);
	}

	public int save(Integer orderId, Byte status, Float actualPrice,
			Byte payStatus) {
		Order order = orderMapper.findOrderInfo(orderId);
		order.setId(orderId);
		if (null != status){
			order.setStatus(status);
			addGoodQuantity(order);
		}
		if (null != actualPrice){
			actualPrice=actualPrice*100;
			order.setActualPrice(actualPrice.intValue());
		}
		if (null != payStatus)
			order.setPayStatus(payStatus);
		return orderMapper.updateByPrimaryKey(order);
	}
	
	/**
	 * @description 修改实际价格，修改定金价格 
	 * @author Tory
	 * @date 2015年4月18日 上午11:24:33
	 */
	public int save(Integer orderId, Byte status, Float actualPrice,
			Byte payStatus,Float frontMoney) {
		Order order = orderMapper.findOrderInfo(orderId);
		order.setId(orderId);
		if (null != status){
			order.setStatus(status);
			addGoodQuantity(order);
		}
		if (null != actualPrice){
			actualPrice=actualPrice*100;
			order.setActualPrice(actualPrice.intValue());
		}
		if (null != payStatus)
			order.setPayStatus(payStatus);
		if(null!=frontMoney){
			frontMoney=frontMoney*100;
			order.setFrontMoney(frontMoney.intValue());
		}
		return orderMapper.updateByPrimaryKey(order);
	}
	

	@Transactional("transactionManager")
	public int save(Customer customer,Integer customerId, Integer goodId, Integer quantity,
			String comment, String invoiceInfo, Integer customerAddressId,
			Integer invoiceType, Boolean needInvoice, int type,
			Integer payChannelId,Integer agentCustomerId) throws Exception {
		
		Order order = new Order();
		Good good = goodMapper.findGoodLazyInfo(goodId);
		PayChannel payChannel = payChannelMapper.selectByPrimaryKey(payChannelId);
		int totalPrice = (good.getRetailPrice()+payChannel.getOpeningCost())* quantity;
		if(1==type || 2==type){
			order.setCustomerId(customerId);
			order.setBelongsUserId(customer.getId());
		}else if(3==type || 4==type){
			order.setCustomerId(customerId);
			order.setBelongsUserId(agentCustomerId);
		}else if(5==type){
			order.setBelongsUserId(customerId);
			if(null!=good.getFloorPurchaseQuantity()&&quantity<good.getFloorPurchaseQuantity()){
				throw new Exception("所选批购数量小于最小批购数!");
			}
			totalPrice=(good.getPurchasePrice()+payChannel.getOpeningCost())*quantity;
			SysConfig findByKey = sysConfigMapper.findByKey(Constant.PURCHASE_ORDER_RATIO);
			int frontMoney = totalPrice*(Integer.parseInt(findByKey.getParamValue()))/100;
			order.setFrontMoney(frontMoney);
		}
		if(1==type || 2==type || 3==type || 4==type){
			minusGoodQuantity(good, quantity);
		}
		order.setActualPrice(totalPrice);
		order.setComment(comment);
		Date createdAt = new Date();
		order.setCreatedAt(createdAt);
		order.setCreatedUserId(customer.getId());
		order.setCreatedUserType(customer.getTypes());
		order.setCustomerAddressId(customerAddressId);
		order.setInvoiceInfo(invoiceInfo);
		order.setInvoiceType(invoiceType);
		order.setNeedInvoice(needInvoice);
		order.setTotalPrice(totalPrice);
		order.setTypes((byte) type);
		order.setUpdatedAt(createdAt);
		order.setTotalQuantity(quantity);
		String orderNumber = getOrderNum(type);
		order.setOrderNumber(orderNumber);
		order.setStatus((byte) 1);
		order.setBelongsTo(good.getBelongsTo());
		orderMapper.insert(order);
		int orderId = order.getId();
		OrderGood orderGood = new OrderGood();
		orderGood.setOrderId(orderId);
		orderGood.setGoodId(goodId);
		orderGood.setCreatedAt(createdAt);
		orderGood.setUpdatedAt(createdAt);
		orderGood.setQuantity(quantity);
		orderGood.setPrice(totalPrice);
		orderGood.setActualPrice(totalPrice);
		orderGood.setPayChannelId(payChannelId);
		orderGoodMapper.insert(orderGood);
		return orderId;
	}

	/**
	 * @description 再次订购的时候 
	 * @author Tory
	 * @date 2015年4月20日 下午9:01:24
	 */
	@Transactional("transactionManager")
	public int save(Customer customer,Integer customerId, Integer orderId, String goodQuantity,
			String comment, String invoiceInfo, Integer customerAddressId,
			Integer invoiceType, Boolean needInvoice, int type,Integer agentCustomerId)
			throws Exception {
		Integer belongsTo=0;
		Order orderOld = orderMapper.findOrderInfo(orderId);
		List<OrderGood> orderGoods = orderOld.getOrderGoods();
		List<Map<String, Integer>> goodQuantityList = new ArrayList<Map<String, Integer>>();
		List<Integer> goodIds = new ArrayList<Integer>();
		String[] split = goodQuantity.split(",");
		Integer totalQuantity = 0;
		for (String goodQuantityE : split) {
			String[] split2 = goodQuantityE.split(":");
			Integer goodId = Integer.parseInt(split2[0].replaceAll("quantity_",
					""));
			goodIds.add(goodId);
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("goodId", goodId);
			int quantity = Integer.parseInt(split2[1]);
			map.put("quantity", quantity);
			totalQuantity += quantity;
			goodQuantityList.add(map);
		}
		List<Good> selectGoodsByIds = goodMapper.selectGoodsByIds(goodIds);
		Integer totalPrice = 0;
		for (Good good : selectGoodsByIds) {
			if(null==good.getBelongsTo()){
				belongsTo=null;
			}else if(null!=belongsTo){
				belongsTo=good.getBelongsTo();
			}
			for (Map<String, Integer> map : goodQuantityList) {
				if (map.get("goodId").equals(good.getId())) {
					if(type==5){
						for (OrderGood orderGoodOld : orderGoods) {
							if (orderGoodOld.getGoodId().equals(good.getId())) {
								totalPrice += (good.getPurchasePrice()+orderGoodOld.getPayChannel().getOpeningCost()) * map.get("quantity");
								break;
							}
						}
					}else{
						minusGoodQuantity(good, map.get("quantity"));
						for (OrderGood orderGoodOld : orderGoods) {
							if (orderGoodOld.getGoodId().equals(good.getId())) {
								totalPrice += (good.getRetailPrice()+orderGoodOld.getPayChannel().getOpeningCost()) * map.get("quantity");
								break;
							}
						}
					}
					if(null!=good.getFloorPurchaseQuantity()&&map.get("quantity")<good.getFloorPurchaseQuantity()){
						throw new Exception("所选批购数量小于最小批购数!");
					}
					break;
				}
			}
		}
		Order orderNew = new Order();
		orderNew.setActualPrice(totalPrice);
		orderNew.setComment(comment);
		Date createdAt = new Date();
		orderNew.setCreatedAt(createdAt);
		orderNew.setCreatedUserId(customerId);
		orderNew.setCreatedUserType(customer.getTypes());
		orderNew.setCustomerAddressId(customerAddressId);
		orderNew.setInvoiceInfo(invoiceInfo);
		orderNew.setInvoiceType(invoiceType);
		orderNew.setNeedInvoice(needInvoice);
		/*if (null != customerId) {
			orderNew.setCustomerId(customerId);
		} else {
			orderNew.setCustomerId(orderOld.getCustomerId());
		}*/
		if(1==type || 2==type){
			orderNew.setCustomerId(customerId);
			orderNew.setBelongsUserId(customer.getId());
		}else if(3==type || 4==type){
			orderNew.setCustomerId(customerId);
			orderNew.setBelongsUserId(agentCustomerId);
		}else if(5==type){
			orderNew.setBelongsUserId(customerId);
			SysConfig findByKey = sysConfigMapper.findByKey(Constant.PURCHASE_ORDER_RATIO);
			int frontMoney = totalPrice*(Integer.parseInt(findByKey.getParamValue()))/100;
			orderNew.setFrontMoney(frontMoney);
		}
		orderNew.setTotalPrice(totalPrice);
		orderNew.setTypes((byte) type);
		orderNew.setUpdatedAt(createdAt);
		orderNew.setTotalQuantity(totalQuantity);
		String orderNumber = getOrderNum(type);
		orderNew.setOrderNumber(orderNumber);
		orderNew.setStatus((byte) 1);
		orderNew.setBelongsTo(belongsTo);
		orderMapper.insert(orderNew);
		int orderNewId=orderNew.getId();
		int newOrderId = orderNew.getId();
		for (Good good : selectGoodsByIds) {
			for (Map<String, Integer> map : goodQuantityList) {
				if (map.get("goodId").equals(good.getId())) {
					OrderGood orderGood = new OrderGood();
					Integer orderGoodPrice=0;
					orderGood.setOrderId(newOrderId);
					orderGood.setGoodId(good.getId());
					orderGood.setCreatedAt(createdAt);
					orderGood.setUpdatedAt(createdAt);
					orderGood.setQuantity(map.get("quantity"));
					for (OrderGood orderGoodOld : orderGoods) {
						if (orderGoodOld.getGoodId().equals(good.getId())) {
							orderGood.setPayChannelId(orderGoodOld
									.getPayChannelId());
							if(type==5){
								orderGoodPrice+=(good.getPurchasePrice()+orderGoodOld.getPayChannel().getOpeningCost()) * map.get("quantity");
							}else{
								orderGoodPrice += (good.getRetailPrice()+orderGoodOld.getPayChannel().getOpeningCost()) * map.get("quantity");
							}
							break;
						}
					}
					orderGood.setPrice(orderGoodPrice);
					orderGood.setActualPrice(orderGoodPrice);
					int insert = orderGoodMapper.insert(orderGood);
					if (insert != 1) {
						throw new Exception("保存订单商品失败");
					}
					break;
				}
			}
		}
		return orderNewId;
	}
	
	/**
	 * @description 确认订单减少库存
	 * @author Tory
	 * @date 2015年4月24日 下午11:02:45
	 */
	private void minusGoodQuantity(Good good,Integer quantity) throws Exception{
		good=goodMapper.selectByPrimaryKey(good.getId());
		Integer stocks=good.getQuantity()-quantity ;//库存量
		if(stocks<0){
			throw new Exception("商品库存不足");
		}
		good.setQuantity(stocks);
		goodMapper.updateByPrimaryKey(good);
	}
	
	/**
	 * @description 取消订单增加库存
	 * @author Tory
	 * @date 2015年4月24日 下午11:03:02
	 */
	private void addGoodQuantity(Order order){
		Byte type = order.getTypes();
		if(1==type || 2==type || 3==type || 4==type){
			List<OrderGood> orderGoods = order.getOrderGoods();
			for(OrderGood orderGood:orderGoods){
				Good good =goodMapper.selectByPrimaryKey( orderGood.getGood().getId());
				good.setQuantity(good.getQuantity()+orderGood.getQuantity());
				goodMapper.updateByPrimaryKey(good);
			}
		}
		return;
	}

	/**
	 * 
	 * @param type
	 *            1.用户订购 2 用户租赁 3 代理商代购 4 代理商代租赁 5 代理商批购
	 * @return
	 */
	public static String getOrderNum(int type) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
		return type + sdf.format(new Date());
	}

	public Page<Order> findFactoryPages(Integer page, Byte status, String keys,Integer customerId) {
		pageSize = 3;
		if (keys != null) {
			if ("".equals(keys.trim())) {
				keys = null;
			} else {
				keys = "%" + keys + "%";
			}
		}
		long count = orderMapper.countFactoryByKeys(status, keys, customerId);
		if (count == 0) {
			return new Page<Order>(new PageRequest(1, pageSize),
					new ArrayList<Order>(), count);
		}
		PageRequest request = new PageRequest(page, pageSize);
		List<Order> result = null;
		result = orderMapper.findFactoryPageOrdersByKeys(request, status, keys,customerId);
		Page<Order> orders = new Page<>(request, result, count);
		if (orders.getCurrentPage() > orders.getTotalPage()) {
			request = new PageRequest(orders.getTotalPage(), pageSize);
			result = orderMapper.findFactoryPageOrdersByKeys(request, status, keys,customerId);
			orders = new Page<>(request, result, count);
		}
		List<Integer> orderIds = new ArrayList<Integer>();
		for (Order o : result) {
			orderIds.add(o.getId());
		}
		List<OrderGood> selectOrderGoods = orderGoodMapper.selectOrderGoods(orderIds);
		List<Integer> goodIds = new ArrayList<Integer>();
		for (Order order : result) {
			order.setOrderGoods(new ArrayList<OrderGood>());
			for (int i = 0, size = selectOrderGoods.size(); i < size; i++) {
				OrderGood o = selectOrderGoods.get(i);
				if (order.getId().equals(o.getOrderId())) {
					order.getOrderGoods().add(o);
					goodIds.add(o.getGoodId());
				}
			}
		}
		if (!CollectionUtils.isEmpty(goodIds)) {
			List<GoodsPicture> selectGoodsPictures = goodsPictureMapper.selectGoodsPictures(goodIds);
			for (OrderGood og : selectOrderGoods) {
				if (null != og.getGood()) {
					og.getGood().setPictures(new ArrayList<GoodsPicture>());
					for (GoodsPicture gp : selectGoodsPictures) {
						if (og.getGoodId().equals(gp.getGoodId())) {
						    gp.setUrlPath(filePath+gp.getUrlPath());
							og.getGood().getPictures().add(gp);
						}
					}
				}
			}
		}
		return orders;
	}
}
