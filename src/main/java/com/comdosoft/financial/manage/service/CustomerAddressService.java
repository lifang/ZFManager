package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAddressMapper;

@Service
public class CustomerAddressService {

	
	@Autowired
	private CustomerAddressMapper customerAddressMapper;
    public List<CustomerAddress> selectCustomerAddress(Integer customerId){
    	return customerAddressMapper.selectCustomerAddress(customerId);
    }
}
