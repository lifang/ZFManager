package com.comdosoft.financial.manage.service.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CsAgentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsAgentService {
	
    @Value("${page.size}")
    private Integer pageSize;

	@Autowired
	private CsAgentMapper csAgentMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	
	public Page<CsAgent> findPage(Customer customer, int page, Byte status, String keyword) {
		long count = csAgentMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsAgent>(new PageRequest(1, pageSize), new ArrayList<CsAgent>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsAgent> result = csAgentMapper.findPageSelective(request, status, keyword);
		
		// let the records above if the current user is process user
		Collections.sort(result, new Comparator<CsAgent>() {
			@Override
			public int compare(CsAgent o1, CsAgent o2) {
				Integer customerId = customer.getId();
				if (customerId.equals(o1.getProcessUserId()) && customerId.equals(o2.getProcessUserId()))
					return o2.getCreatedAt().compareTo(o1.getCreatedAt());
				else if (customerId.equals(o1.getProcessUserId()))
					return -1;
				else if (customerId.equals(o2.getProcessUserId()))
					return 1;
				else
					return o2.getCreatedAt().compareTo(o1.getCreatedAt());
			}
		});
		Page<CsAgent> csAgents = new Page<CsAgent>(request, result, count);
		return csAgents;
	}
	
	public CsAgent findInfoById(Integer id) {
		return csAgentMapper.selectByPrimaryKey(id);
	}
	
	@Transactional
	private void updateStatus(Integer csAgentId, Byte status) {
		CsAgent csAgent = csAgentMapper.selectByPrimaryKey(csAgentId);
		csAgent.setStatus(status);
		csAgent.setUpdatedAt(new Date());
		csAgentMapper.updateByPrimaryKey(csAgent);
		
		String terminalsList = csAgent.getTerminalsList();
		if (null != terminalsList && !"".equals(terminalsList)) {
			String[] serialNums = terminalsList.split(",");
			terminalMapper.closeCsReturnDepotsByNums(serialNums);
		}
	}
	
	public void cancel(Integer csAgentId) {
		updateStatus(csAgentId, (byte)2);
	}
	
	public void finish(Integer csAgentId) {
		updateStatus(csAgentId, (byte)3);
	}
	
	public void output(Integer csAgentId) {
		CsAgent csAgent = csAgentMapper.selectByPrimaryKey(csAgentId);
		String terminalsList = csAgent.getTerminalsList();
		List<Terminal> terminals = terminalMapper.findTerminalsByNums(terminalsList.split(","));
		for (Terminal terminal : terminals) {
			System.out.println(terminal.getOrder());
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csAgentMapper.dispatchUserByIds(params);
	}
	
	
}
