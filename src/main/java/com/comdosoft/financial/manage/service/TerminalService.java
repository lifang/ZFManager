package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService {

    @Value("${page.size}")
    private Integer pageSize;
	@Autowired
	private TerminalMapper terminalMapper;
	
	public long countCustomerTerminals(Integer customerId){
		return terminalMapper.countCustomerTerminals(customerId);
	}

	public Page<Terminal> customerTerminalPage(Integer customerId,Integer page){
		long count = terminalMapper.countCustomerTerminals(customerId);
		PageRequest request = new PageRequest(page, pageSize);
		List<Terminal> terminals = terminalMapper.selectCustomerTerminals(customerId, request);
		return new Page<>(request,terminals,count);
	}
}
