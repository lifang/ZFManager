package com.comdosoft.financial.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.OrderMark;
import com.comdosoft.financial.manage.mapper.zhangfu.OrderMarkMapper;

@Service
public class OrderMarkService {

	@Autowired
	private OrderMarkMapper orderMarkMapper;

	public int insert(Integer customerId,Integer orderId,String content) {
		OrderMark record=new OrderMark();
		record.setOrderId(orderId);
		record.setCreatedAt(new Date());
		record.setContent(content);
		record.setCustomerId(customerId);
		return orderMarkMapper.insert(record);
	}
}
