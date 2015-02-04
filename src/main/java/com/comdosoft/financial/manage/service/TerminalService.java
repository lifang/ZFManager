package com.comdosoft.financial.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;

@Service
public class TerminalService {
	
	@Autowired
	private TerminalMapper terminalMapper;
	
	public long countCustomerTerminals(Integer customerId){
		return terminalMapper.countCustomerTerminals(customerId);
	}

}
