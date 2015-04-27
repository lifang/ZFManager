package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsChangeStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsChangeStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsChangeStatus.HANDLE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.CsChange;
import com.comdosoft.financial.manage.domain.zhangfu.CsChangeMark;
import com.comdosoft.financial.manage.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsChangeMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsChangeMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsOutStorageMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReceiverAddressMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsChangeService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsChangeMapper csChangeMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsChangeMarkMapper csChangeMarkMapper;
	@Autowired
	private CsReceiverAddressMapper csReceiverAddressMapper;
	@Autowired
	private CsOutStorageMapper csOutStorageMapper;

	public Page<CsChange> findPage(Customer customer, int page, Byte status, String keyword) {
		long count = csChangeMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsChange>(new PageRequest(1, pageSize), new ArrayList<CsChange>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsChange> result = csChangeMapper.findPageSelective(request,customer.getId(), status, keyword);
		Page<CsChange> csChanges = new Page<CsChange>(request, result, count);
		return csChanges;
	}
	
	public CsChange findInfoById(Integer id) {
		return csChangeMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsChange updateStatus(Integer csChangeId, Byte status) {
		CsChange csChange = csChangeMapper.selectByPrimaryKey(csChangeId);
		csChange.setStatus(status);
		csChange.setUpdatedAt(new Date());
		csChangeMapper.updateByPrimaryKey(csChange);
		return csChange;
	}
	
	public void handle(Integer csChangeId) {
		updateStatus(csChangeId, HANDLE);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csChangeId) {
		CsChange csChange = updateStatus(csChangeId, CANCEL);
		
		Integer terminalId = csChange.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csChangeId) {
		CsChange csChange = updateStatus(csChangeId, FINISH);
		
		Integer terminalId = csChange.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void confirm(Integer csChangeId, CsReceiverAddress csReceiverAddress, Customer customer) {
		csReceiverAddress.setCreatedAt(new Date());
		csReceiverAddressMapper.insert(csReceiverAddress);
		
		CsChange csChange = csChangeMapper.selectInfoByPrimaryKey(csChangeId);
		if (null != csChange) {
			csChange.setReturnAddressId(csReceiverAddress.getId());
			csChange.setStatus(HANDLE);
			csChange.setUpdatedAt(new Date());
			csChangeMapper.updateByPrimaryKey(csChange);
			
			CsOutStorage csOutStorage = new CsOutStorage();
			csOutStorage.setCsApplyId(csChange.getApplyNum());
			csOutStorage.setCsApplyTypes(CsOutStorage.TYPE_CHANGE);
			csOutStorage.setCreatedAt(new Date());
			csOutStorage.setUpdatedAt(new Date());
			csOutStorage.setOrderId(csChange.getTerminal().getOrderId());
			csOutStorage.setProcessUserId(customer.getId());
			csOutStorage.setProcessUserName(customer.getName());
			csOutStorage.setQuantity(1);
			csOutStorage.setStatus(CsOutStorage.STATUS_NOT_OUTPUT);
			csOutStorageMapper.insert(csOutStorage);
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csChangeMapper.dispatchUserByIds(params);
	}
	
	public CsChangeMark createMark(Customer customer, Integer csChangeId, String content) {
		CsChangeMark csChangeMark = new CsChangeMark();
		csChangeMark.setCustomId(customer.getId());
		csChangeMark.setCsChangeId(csChangeId);
		csChangeMark.setCreatedAt(new Date());
		csChangeMark.setContent(content);
		csChangeMarkMapper.insert(csChangeMark);
		csChangeMark.setCustomer(customer);
		return csChangeMark;
	}
	
	public List<CsChangeMark> findMarksByCsChangeId(Integer csChangeId) {
		return csChangeMarkMapper.selectByChangeId(csChangeId);
	}
}
