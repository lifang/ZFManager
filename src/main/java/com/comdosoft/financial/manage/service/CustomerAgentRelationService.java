package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAgentRelation;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerAgentRelationMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

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

	/**
	 * 不存在就新建关系
	 * @param customerId
	 * @param agentId
	 */
	public void makeRelation(Integer customerId,Integer agentId){
		if(null==customerId || null==agentId ){
			return;
		}
		List<CustomerAgentRelation> relations = customerAgentRelationMapper.selectByAgentId(agentId, 1, 1);
		boolean exists = false;
		for(CustomerAgentRelation relation:relations) {
			if(relation.getCustomerId() == customerId){
				exists = true;
				break;
			}
		}
		if(exists) {
			return;
		}
		CustomerAgentRelation car = new CustomerAgentRelation();
		car.setAgentId(agentId);
		car.setCreatedAt(new Date());
		car.setCustomerId(customerId);
		car.setStatus(1);
		car.setTypes(1);
		car.setUpdatedAt(new Date());
		customerAgentRelationMapper.insert(car);
	}
}
