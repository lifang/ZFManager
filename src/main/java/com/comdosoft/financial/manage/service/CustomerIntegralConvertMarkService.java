package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralConvertMark;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerIntegralConvertMarkMapper;

@Service
public class CustomerIntegralConvertMarkService {
	@Autowired
	private CustomerIntegralConvertMarkMapper customerIntegralConvertMarkMapper;
	
	public  List<CustomerIntegralConvertMark> findMarksById(Integer id) {
		return customerIntegralConvertMarkMapper.findMarksById(id);
	}

	public CustomerIntegralConvertMark createMark(Customer customer,
			Integer pid, String content) {
		return customerIntegralConvertMarkMapper.createMark(pid,content,customer.getId(),customer.getName());
	}

}
