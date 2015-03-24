package com.comdosoft.financial.manage.service.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;

@Service
public class CsCommonService {

	@Autowired
	private CustomerMapper customerMapper;

	public List<Customer> findDispatchUsers() {
		return customerMapper.selectDispatchUsers();
	}
}
