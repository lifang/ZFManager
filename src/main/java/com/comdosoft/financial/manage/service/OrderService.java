package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.comdosoft.financial.manage.domain.zhangfu.GoodsPicture;
import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
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
//		System.out.println(order.getOrderMarks());
		return order;
	}
}
