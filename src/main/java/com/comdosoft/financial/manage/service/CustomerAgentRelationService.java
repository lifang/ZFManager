package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAgentRelationMapper;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CustomerAgentRelationService {
	
	@Autowired
	private CustomerAgentRelationMapper customerAgentRelationMapper;
	
	/**
	 * 用户相关联的代理商列表
	 * @param customerId
	 * @return
	 */
	public Page<Agent> customerAgents(Integer customerId,int page){
		PageRequest request = new PageRequest(page, Constants.PAGE_SIZE);
		List<Agent> agents = customerAgentRelationMapper.customerAgents(customerId,request);
		long total = customerAgentRelationMapper.countCustomerAgents(customerId);
		return new Page<Agent>(request, agents, total);
	}

}
