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

import com.comdosoft.financial.manage.domain.zhangfu.CsReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReturnMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReturnMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsReturnService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsReturnMapper csReturnMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsReturnMarkMapper csReturnMarkMapper;
	
	public Page<CsReturn> findPage(Customer customer, int page, Byte status, String keyword) {
		long count = csReturnMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsReturn>(new PageRequest(1, pageSize), new ArrayList<CsReturn>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsReturn> result = csReturnMapper.findPageSelective(request, status, keyword);
		
		Collections.sort(result, new Comparator<CsReturn>() {
			@Override
			public int compare(CsReturn o1, CsReturn o2) {
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
		Page<CsReturn> csReturns = new Page<CsReturn>(request, result, count);
		return csReturns;
	}

	public CsReturn findInfoById(Integer id) {
		return csReturnMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsReturn updateStatus(Integer csReturnId, Byte status) {
		CsReturn csReturn = csReturnMapper.selectByPrimaryKey(csReturnId);
		csReturn.setStatus(status);
		csReturn.setUpdatedAt(new Date());
		csReturnMapper.updateByPrimaryKey(csReturn);
		return csReturn;
	}
	
	@Transactional("transactionManager")
	private void cancelOrFinish(Integer csReturnId, Byte status) {
		CsReturn csReturn = updateStatus(csReturnId, status);
		
		Integer terminalId = csReturn.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	public void cancel(Integer csReturnId) {
		cancelOrFinish(csReturnId, (byte)2);
	}
	
	public void finish(Integer csReturnId) {
		cancelOrFinish(csReturnId, (byte)3);
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csReturnMapper.dispatchUserByIds(params);
	}
	
	@Transactional("transactionManager")
	public CsReturnMark createMark(Customer customer, Integer csReturnId, String content) {
		updateStatus(csReturnId, (byte)1);
		
		CsReturnMark csReturnMark = new CsReturnMark();
		csReturnMark.setCustomerId(customer.getId());
		csReturnMark.setCsReturnId(csReturnId);
		csReturnMark.setCreatedAt(new Date());
		csReturnMark.setContent(content);
		csReturnMarkMapper.insert(csReturnMark);
		csReturnMark.setCustomer(customer);
		return csReturnMark;
	}
	
	public List<CsReturnMark> findMarksByCsReturnId(Integer csReturnId) {
		return csReturnMarkMapper.selectByReturnId(csReturnId);
	}
}
