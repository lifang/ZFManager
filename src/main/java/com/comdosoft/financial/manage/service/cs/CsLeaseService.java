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

import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsLeaseReturnMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsLeaseReturnMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsLeaseService {
	
	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsLeaseReturnMapper csLeaseReturnMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsLeaseReturnMarkMapper csleaLeaseReturnMarkMapper;

	public Page<CsLeaseReturn> findPage(Customer customer, int page, Integer status, String keyword) {
		long count = csLeaseReturnMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsLeaseReturn>(new PageRequest(1, pageSize), new ArrayList<CsLeaseReturn>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsLeaseReturn> result = csLeaseReturnMapper.findPageSelective(request, status, keyword);
		
		Collections.sort(result, new Comparator<CsLeaseReturn>() {
			@Override
			public int compare(CsLeaseReturn o1, CsLeaseReturn o2) {
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
		Page<CsLeaseReturn> csLeases = new Page<CsLeaseReturn>(request, result, count);
		return csLeases;
	}
	
	public CsLeaseReturn findInfoById(Integer id) {
		return csLeaseReturnMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsLeaseReturn updateStatus(Integer csLeaseId, Integer status) {
		CsLeaseReturn csLease = csLeaseReturnMapper.selectByPrimaryKey(csLeaseId);
		csLease.setStatus(status);
		csLease.setUpdatedAt(new Date());
		csLeaseReturnMapper.updateByPrimaryKey(csLease);
		return csLease;
	}
	
	public void handle(Integer csLeaseId) {
		updateStatus(csLeaseId, 1);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csLeaseId) {
		CsLeaseReturn csLease = updateStatus(csLeaseId, 3);
		
		Integer terminalId = csLease.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csLeaseId) {
		CsLeaseReturn csLease = updateStatus(csLeaseId, 2);
		
		Integer terminalId = csLease.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csLeaseReturnMapper.dispatchUserByIds(params);
	}
	
	@Transactional("transactionManager")
	public CsLeaseReturnMark createMark(Customer customer, Integer csLeaseId, String content) {
		handle(csLeaseId);
		
		CsLeaseReturnMark csLeaseMark = new CsLeaseReturnMark();
		csLeaseMark.setCustomerId(customer.getId());
		csLeaseMark.setCsLeaseReturnId(csLeaseId);
		csLeaseMark.setCreatedAt(new Date());
		csLeaseMark.setContent(content);
		csleaLeaseReturnMarkMapper.insert(csLeaseMark);
		csLeaseMark.setCustomer(customer);
		return csLeaseMark;
	}
	
	public List<CsLeaseReturnMark> findMarksByCsLeaseReturnId(Integer csLeaseId) {
		return csleaLeaseReturnMarkMapper.selectBycsLeaseId(csLeaseId);
	}
}
