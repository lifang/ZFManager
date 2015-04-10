package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAgentRelationMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
		long count = customerAgentRelationMapper.countCustomerAgents(customerId);
        if (count == 0) {
            return new Page<>(new PageRequest(1, pageSize), new ArrayList<Agent>(), count);
        }
        PageRequest request = new PageRequest(page, pageSize);
        List<Agent> result = customerAgentRelationMapper.customerAgents(customerId, request);
        Page<Agent> agents = new Page<>(request, result, count);
        if (agents.getCurrentPage() > agents.getTotalPage()) {
            request = new PageRequest(agents.getTotalPage(), pageSize);
            result =  customerAgentRelationMapper.customerAgents(customerId, request);
            agents = new Page<>(request, result, count);
        }
        return agents;
	}

}
