package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsAgentStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsAgentStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsAgentStatus.HANDLE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsAgentMark;
import com.comdosoft.financial.manage.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CsAgentMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsAgentMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsOutStorageMapper;
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
	@Autowired
	private CsOutStorageMapper csOutStorageMapper;
	@Autowired
	private CsAgentMarkMapper csAgentMarkMapper;
	
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
		List<CsAgent> result = csAgentMapper.findPageSelective(request,customer.getId(), status, keyword);
		Page<CsAgent> csAgents = new Page<CsAgent>(request, result, count);
		return csAgents;
	}
	
	public CsAgent findInfoById(Integer id) {
		return csAgentMapper.selectByPrimaryKey(id);
	}
	
	public CsAgent updateStatus (Integer csAgentId, Byte status) {
		CsAgent csAgent = csAgentMapper.selectByPrimaryKey(csAgentId);
		csAgent.setStatus(status);
		csAgent.setUpdatedAt(new Date());
		csAgentMapper.updateByPrimaryKey(csAgent);
		return csAgent;
	}
	
	public void handle(Integer csAgentId) {
		updateStatus(csAgentId, HANDLE);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csAgentId) {
		CsAgent csAgent = updateStatus(csAgentId, CANCEL);
		
		String terminalsList = csAgent.getTerminalsList();
		if (null != terminalsList && !"".equals(terminalsList)) {
			String[] serialNums = terminalsList.split(",");
			terminalMapper.closeCsReturnDepotsByNums(serialNums);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csAgentId) {
		CsAgent csAgent = updateStatus(csAgentId, FINISH);
		
		String terminalsList = csAgent.getTerminalsList();
		if (null != terminalsList && !"".equals(terminalsList)) {
			String[] serialNums = terminalsList.split(",");
			terminalMapper.closeCsReturnDepotsByNums(serialNums);
		}
	}
	
//	public void output(Integer csAgentId, String terminalList) {
//		CsAgent csAgent = csAgentMapper.selectByPrimaryKey(csAgentId);
//		List<Terminal> terminals = terminalMapper.findTerminalsByNums(terminalList.split(","));
//		for (Terminal terminal : terminals) {
//			CsOutStorage csOutStorage = new CsOutStorage();
//			csOutStorage.setCsApplyId(csAgent.getApplyNum());
//			csOutStorage.setCsApplyTypes(CsOutStorage.TYPE_AGENT);
//			csOutStorage.setCreatedAt(new Date());
//			csOutStorage.setUpdatedAt(new Date());
//			csOutStorage.setOrderId(terminal.getOrderId());
//			csOutStorage.setProcessUserId(csAgent.getProcessUserId());
//			csOutStorage.setProcessUserName(csAgent.getProcessUserName());
//			csOutStorage.setQuantity(csAgent.getTerminalsQuantity());
//			csOutStorage.setStatus(CsOutStorage.STATUS_OUTPUT);
//			csOutStorage.setTerminalList(terminal.getSerialNum());
//			csOutStorageMapper.insert(csOutStorage);
//		}
//	}
	
	public void output(Integer csAgentId, Terminal terminal) {
		CsAgent csAgent = csAgentMapper.selectByPrimaryKey(csAgentId);
		CsOutStorage csOutStorage = new CsOutStorage();
		csOutStorage.setCsApplyId(csAgent.getApplyNum());
		csOutStorage.setCsApplyTypes(CsOutStorage.TYPE_AGENT);
		csOutStorage.setCreatedAt(new Date());
		csOutStorage.setUpdatedAt(new Date());
		csOutStorage.setOrderId(terminal.getOrderId());
		csOutStorage.setProcessUserId(csAgent.getProcessUserId());
		csOutStorage.setProcessUserName(csAgent.getProcessUserName());
		csOutStorage.setQuantity(csAgent.getTerminalsQuantity());
		csOutStorage.setStatus(CsOutStorage.STATUS_OUTPUT);
		csOutStorage.setTerminalList(terminal.getSerialNum());
		csOutStorage.setApplyNum(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		csOutStorage.setDescription(csAgent.getApplyNum());
		csOutStorageMapper.insert(csOutStorage);
		terminal.setAgentId(csAgentId);
		terminalMapper.updateByPrimaryKey(terminal);
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csAgentMapper.dispatchUserByIds(params);
	}
	
	public CsAgentMark createMark(Customer customer, Integer csAgentId, String content) {
		CsAgentMark csAgentMark = new CsAgentMark();
		csAgentMark.setCustomerId(customer.getId());
		csAgentMark.setCsAgentId(csAgentId);
		csAgentMark.setCreatedAt(new Date());
		csAgentMark.setContent(content);
		csAgentMarkMapper.insert(csAgentMark);
		csAgentMark.setCustomer(customer);
		return csAgentMark;
	}
	
	public List<CsAgentMark> findMarksByCsAgentId(Integer csAgentId) {
		return csAgentMarkMapper.selectByAgentId(csAgentId);
	}

	public Terminal findTerminal(String terminal) {
		return terminalMapper.findTerminal(terminal);
	}
}
