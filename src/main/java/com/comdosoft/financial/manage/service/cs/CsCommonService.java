package com.comdosoft.financial.manage.service.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OtherRequirementMapper;

@Service
public class CsCommonService {

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private OtherRequirementMapper otherRequirementMapper;

	public List<Customer> findDispatchUsers() {
		return customerMapper.selectDispatchUsers();
	}

	public List<OtherRequirement> findRequirementByType(int type) {
		return otherRequirementMapper.findByType(type);
	}
}
