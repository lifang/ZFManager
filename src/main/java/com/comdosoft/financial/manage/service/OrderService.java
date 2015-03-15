package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Order;
import com.comdosoft.financial.manage.domain.zhangfu.OrderGood;
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
	
	public Page<Order> findPages(int page, Byte status, String keys){
		if (keys != null) {
			keys = "%"+keys+"%";
		}
		long count = orderMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<Order>(new PageRequest(1, pageSize), new ArrayList<Order>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<Order> result = orderMapper.findPageOrdersByKeys(request, status, keys);
		Page<Order> orders = new Page<>(request, result, count);
		if (orders.getCurrentPage() > orders.getTotalPage()) {
			request = new PageRequest(orders.getTotalPage(), pageSize);
			result = orderMapper.findPageOrdersByKeys(request, status, keys);
			orders = new Page<>(request, result, count);
		}
		StringBuffer str=new StringBuffer();
		for(int i=0,size=result.size();i<size;i++){
			str.append(result.get(i).getId());
			if(i<size-1){
				str.append(",");
			}
		}
		List<OrderGood> selectOrderGoods = orderGoodMapper.selectOrderGoods(str.toString());
		for(Order order:result){
			order.setOrderGoods(new ArrayList<OrderGood>() );
			for(OrderGood o:selectOrderGoods){
				if(order.getId().equals(o.getOrderId())){
					order.getOrderGoods().add(o);
				}
			}
		}
		return orders;
	}
}
