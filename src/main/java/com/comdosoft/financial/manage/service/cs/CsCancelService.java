package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsCancelStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsCancelStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsCancelStatus.HANDLE;

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

import com.comdosoft.financial.manage.domain.zhangfu.CsCancel;
import com.comdosoft.financial.manage.domain.zhangfu.CsCancelMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsCancelMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsCancelMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsCancelService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsCancelMapper csCancelMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsCancelMarkMapper csCancelMarkMapper;

	public Page<CsCancel> findPage(Customer customer, int page, Integer status, String keyword) {
		long count = csCancelMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsCancel>(new PageRequest(1, pageSize), new ArrayList<CsCancel>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsCancel> result = csCancelMapper.findPageSelective(request, status, keyword);
		
		Collections.sort(result, new Comparator<CsCancel>() {
			@Override
			public int compare(CsCancel o1, CsCancel o2) {
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
		Page<CsCancel> csCancels = new Page<CsCancel>(request, result, count);
		return csCancels;
	}
	
	public CsCancel findInfoById(Integer id) {
		return csCancelMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsCancel updateStatus(Integer csCancelId, Integer status) {
		CsCancel csCancel = csCancelMapper.selectByPrimaryKey(csCancelId);
		csCancel.setStatus(status);
		csCancel.setUpdatedAt(new Date());
		csCancelMapper.updateByPrimaryKey(csCancel);
		return csCancel;
	}
	
	public void handle(Integer csCancelId) {
		updateStatus(csCancelId, HANDLE);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csCancelId) {
		CsCancel csCancel = updateStatus(csCancelId, CANCEL);
		Integer terminalId = csCancel.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csCancelId) {
		CsCancel csCancel = updateStatus(csCancelId, FINISH);
		Integer terminalId = csCancel.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csCancelMapper.dispatchUserByIds(params);
	}
	
	@Transactional("transactionManager")
	public CsCancelMark createMark(Customer customer, Integer csCancelId, String content) {
		handle(csCancelId);
		
		CsCancelMark csCancelMark = new CsCancelMark();
		csCancelMark.setCustomerId(customer.getId());
		csCancelMark.setCsCancelId(csCancelId);
		csCancelMark.setCreatedAt(new Date());
		csCancelMark.setContent(content);
		csCancelMarkMapper.insert(csCancelMark);
		csCancelMark.setCustomer(customer);
		return csCancelMark;
	}
	
	public List<CsCancelMark> findMarksByCsCancelId(Integer csCancelId) {
		return csCancelMarkMapper.selectByCancelId(csCancelId);
	}
}
