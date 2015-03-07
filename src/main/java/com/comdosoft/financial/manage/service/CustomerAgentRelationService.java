package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAgentRelationMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAgentRelationService {

    @Value("${page.size}")
    private Integer pageSize;

	@Autowired
	private CustomerAgentRelationMapper customerAgentRelationMapper;
	
	/**
	 * 用户相关联的代理商列表
	 * @param customerId
	 * @return
	 */
	public Page<Agent> customerAgents(Integer customerId,int page){
		PageRequest request = new PageRequest(page, pageSize);
		List<Agent> agents = customerAgentRelationMapper.customerAgents(customerId,request);
		long total = customerAgentRelationMapper.countCustomerAgents(customerId);
		return new Page<Agent>(request, agents, total);
	}

}
