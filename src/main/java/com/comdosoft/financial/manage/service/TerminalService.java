package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
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

    public Page<Terminal> findPages(Integer page, int pageSize, Byte status, String keys) {
        if (keys != null) {
            keys = "%"+keys+"%";
        }
        long count = terminalMapper.countByKeys(status, keys);
        if (count == 0) {
            return new Page<Terminal>(new PageRequest(1, pageSize), new ArrayList<Terminal>(), count);
        }

        PageRequest request = new PageRequest(page, pageSize);
        List<Terminal> result = terminalMapper.selectPageTerminalsByKeys(request, status, keys);
        Page<Terminal> goods = new Page<>(request, result, count);
        if (goods.getCurrentPage() > goods.getTotalPage()) {
            request = new PageRequest(goods.getTotalPage(), pageSize);
            result = terminalMapper.selectPageTerminalsByKeys(request, status, keys);
            goods = new Page<>(request, result, count);
        }
        return goods;
    }
}
