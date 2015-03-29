package com.comdosoft.financial.manage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodsPictureMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderGoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class OrderService {
    @Value("${page.size}")
    private Integer pageSize;
    
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderGoodMapper orderGoodMapper;
	
	@Autowired
	private GoodsPictureMapper goodsPictureMapper;
	
	@Autowired
	private GoodMapper goodMapper;
	
	public Page<Order> findPages(int page, Byte status, String keys,Integer factoryId,List<Byte> types){
		pageSize=2;
		if (keys != null) {
			keys = "%"+keys+"%";
		}
		long count = orderMapper.countByKeys(status, keys,factoryId,types);
		if (count == 0) {
			return new Page<Order>(new PageRequest(1, pageSize), new ArrayList<Order>(), count);
		}
		PageRequest request = new PageRequest(page, pageSize);
		List<Order> result = orderMapper.findPageOrdersByKeys(request, status, keys,factoryId,types);
		Page<Order> orders = new Page<>(request, result, count);
		if (orders.getCurrentPage() > orders.getTotalPage()) {
			request = new PageRequest(orders.getTotalPage(), pageSize);
			result = orderMapper.findPageOrdersByKeys(request, status, keys,factoryId,types);
			orders = new Page<>(request, result, count);
		}
		List<Integer> orderIds=new ArrayList<Integer>();
		for(Order o:result){
			orderIds.add(o.getId());
		}
		List<OrderGood> selectOrderGoods = orderGoodMapper.selectOrderGoods(orderIds);
		List<Integer> goodIds=new ArrayList<Integer>();
		for(Order order:result){
			order.setOrderGoods(new ArrayList<OrderGood>() );
			for(int i=0,size=selectOrderGoods.size();i<size;i++){
				OrderGood o=selectOrderGoods.get(i);
				if(order.getId().equals(o.getOrderId())){
					order.getOrderGoods().add(o);
					goodIds.add(o.getGoodId());
				}
			}
		}
		if(!CollectionUtils.isEmpty(goodIds)){
			List<GoodsPicture> selectGoodsPictures = goodsPictureMapper.selectGoodsPictures(goodIds);
			for(OrderGood og:selectOrderGoods){
				if(null!=og.getGood()){
					og.getGood().setPictures(new ArrayList<GoodsPicture>());
					for(GoodsPicture gp:selectGoodsPictures){
						if(og.getGoodId().equals(gp.getGoodId())){
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
		List<Integer> goodIds=new ArrayList<Integer>();
		for(OrderGood orderGood:order.getOrderGoods()){
			goodIds.add(orderGood.getGoodId());
		}
		if(!CollectionUtils.isEmpty(goodIds)){
			List<GoodsPicture> selectGoodsPictures = goodsPictureMapper.selectGoodsPictures(goodIds);
			for(OrderGood og:order.getOrderGoods()){
				if(null!=og.getGood()){
					og.getGood().setPictures(new ArrayList<GoodsPicture>());
					for(GoodsPicture gp:selectGoodsPictures){
						if(og.getGoodId().equals(gp.getGoodId())){
							og.getGood().getPictures().add(gp);
						}
					}
				}
			}
		}
		return order;
	}
	
	public int save(Integer orderId,Byte status,Integer actualPrice,Byte payStatus){
		Order record=orderMapper.findOrderInfo(orderId);
		record.setId(orderId);
		if(null!=status) record.setStatus(status);
		if(null!=actualPrice) record.setActualPrice(actualPrice);
		if(null!=payStatus) record.setPayStatus(payStatus);
		return orderMapper.updateByPrimaryKey(record);
	}
	
	@Transactional("transactionManager")
	public int save(Customer customer,Integer goodId,Integer quantity,String comment,String invoiceInfo,
			Integer customerAddressId,Integer invoiceType,Boolean needInvoice,int type,Integer payChannelId){
		Order order=new Order();
		Good good=goodMapper.findGoodLazyInfo(goodId);
		order.setActualPrice(good.getPrice());
		order.setComment(comment);
		Date createdAt = new Date();
		order.setCreatedAt(createdAt);
		order.setCreatedUserId(customer.getId());
		order.setCustomerAddressId(customerAddressId);
		order.setInvoiceInfo(invoiceInfo);
		order.setInvoiceType(invoiceType);
		order.setNeedInvoice(needInvoice);
		order.setCustomerId(customer.getId());
		order.setTotalPrice(good.getPrice()* quantity);
		order.setTypes((byte) type);
		order.setUpdatedAt(createdAt);
		order.setTotalQuantity(quantity);
		String orderNumber=getOrderNum(type);
		order.setOrderNumber(orderNumber);
		order.setStatus((byte) 1);
		orderMapper.insert(order);
		int orderId=order.getId();
		OrderGood orderGood=new OrderGood();
		orderGood.setOrderId(orderId);
		orderGood.setGoodId(goodId);
		orderGood.setCreatedAt(createdAt);
		orderGood.setUpdatedAt(createdAt);
		orderGood.setQuantity(quantity);
		orderGood.setPrice(good.getPrice()* quantity);
		orderGood.setActualPrice(good.getPrice()* quantity);
		orderGood.setPayChannelId(payChannelId);
		int insert = orderGoodMapper.insert(orderGood);
		return insert;
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
}
