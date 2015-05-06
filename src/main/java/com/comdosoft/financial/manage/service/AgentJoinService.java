package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.AgentJoin;
import com.comdosoft.financial.manage.mapper.zhangfu.AgentJoinMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class AgentJoinService {
	@Value("${page.size}")
	private Integer pageSize;
	@Autowired
	private AgentJoinMapper agentJoinMapper;

	public Page<AgentJoin> findPage(Integer id, Integer page, Byte statu,
			String keys) {
		long count = agentJoinMapper.countByKeys(statu, keys);
		if (count == 0) {
			return new Page<AgentJoin>(new PageRequest(1, pageSize),
					new ArrayList<AgentJoin>(), count);
		}
		PageRequest request = new PageRequest(page, pageSize);
		List<AgentJoin> result = agentJoinMapper.findAgentJoinInfoByType(
				request, statu, keys, id);

		Page<AgentJoin> goods = new Page<>(request, result, count);
		if (goods.getCurrentPage() > goods.getTotalPage()) {
			request = new PageRequest(goods.getTotalPage(), pageSize);
			result = agentJoinMapper.findAgentJoinInfoByType(request, statu,
					keys, id);
			goods = new Page<>(request, result, count);
		}
		return goods;
	}

	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		agentJoinMapper.dispatch(params);
	}

	public int upStatus(Integer id, Integer status) {
		return agentJoinMapper.upStatus(id, status);
	}
	
	
	public AgentJoin findAgentById(Integer id){
		return agentJoinMapper.findInfo(id);
	}
}
