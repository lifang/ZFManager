package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class TerminalService {
	
	@Autowired
	private TerminalMapper terminalMapper;
	
	public long countCustomerTerminals(Integer customerId){
		return terminalMapper.countCustomerTerminals(customerId);
	}

	public Page<Terminal> customerTerminalPage(Integer customerId,Integer page){
		long count = terminalMapper.countCustomerTerminals(customerId);
		PageRequest request = new PageRequest(page, Constants.PAGE_SIZE);
		List<Terminal> terminals = terminalMapper.selectCustomerTerminals(customerId, request);
		return new Page<Terminal>(request,terminals,count);
	}
}
