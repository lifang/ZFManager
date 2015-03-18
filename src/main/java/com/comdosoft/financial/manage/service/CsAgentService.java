package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.mapper.zhangfu.CsAgentMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsAgentService {
	
    @Value("${page.size}")
    private Integer pageSize;

	@Autowired
	private CsAgentMapper csAgentMapper;

	public Page<CsAgent> findPages(int page, Byte status, String keyword) {
		if (keyword != null) {
			keyword = "%" + keyword + "%";
		}
		
		long count = csAgentMapper.countSelective(status, keyword);
		if (count == 0) {
			return new Page<CsAgent>(new PageRequest(1, pageSize), new ArrayList<CsAgent>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<CsAgent> result = csAgentMapper.findPageSelective(request, status, keyword);
		Page<CsAgent> csAgents = new Page<CsAgent>(request, result, count);
		if (csAgents.getCurrentPage() > csAgents.getTotalPage()) {
			request = new PageRequest(csAgents.getTotalPage(), pageSize);
			result = csAgentMapper.findPageSelective(request, status, keyword);
			csAgents = new Page<CsAgent>(request, result, count);
		}
		return csAgents;
	}
}
