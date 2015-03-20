package com.comdosoft.financial.manage.service.cs;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgentMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsAgentMarkMapper;

@Service
public class CsAgentMarkService {

	@Autowired
	private CsAgentMarkMapper csAgentMarkMapper;

	public CsAgentMark create(Customer customer, Integer csAgentId, String content) {
		CsAgentMark csAgentMark = new CsAgentMark();
		csAgentMark.setCustomerId(customer.getId());
		csAgentMark.setCsAgentId(csAgentId);
		csAgentMark.setCreatedAt(new Date());
		csAgentMark.setContent(content);
		csAgentMarkMapper.insert(csAgentMark);
		csAgentMark.setCustomer(customer);
		return csAgentMark;
	}
	
	public List<CsAgentMark> findByAgentId(Integer csAgentId) {
		return csAgentMarkMapper.selectByAgentId(csAgentId);
	}
}
