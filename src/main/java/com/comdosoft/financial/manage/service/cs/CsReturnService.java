package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsReturnStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsReturnStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsReturnStatus.HANDLE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReceiverAddressMapper;
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
	@Autowired
	private CsReceiverAddressMapper csReceiverAddressMapper;
	
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
		List<CsReturn> result = csReturnMapper.findPageSelective(request,customer.getId(), status, keyword);
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
	
	public void handle(Integer csReturnId) {
		updateStatus(csReturnId, HANDLE);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csReturnId) {
		CsReturn csReturn = updateStatus(csReturnId, CANCEL);
		
		Integer terminalId = csReturn.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csReturnId) {
		CsReturn csReturn = updateStatus(csReturnId, FINISH);
		
		Integer terminalId = csReturn.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void confirm(Integer csReturnId, CsReceiverAddress csReceiverAddress) {
		csReceiverAddress.setCreatedAt(new Date());
		csReceiverAddressMapper.insert(csReceiverAddress);
		
		CsReturn csReturn = csReturnMapper.selectByPrimaryKey(csReturnId);
		if (null != csReturn) {
			csReturn.setReturnAddressId(csReceiverAddress.getId());
			csReturn.setUpdatedAt(new Date());
			csReturn.setStatus(HANDLE);
			csReturnMapper.updateByPrimaryKey(csReturn);
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csReturnMapper.dispatchUserByIds(params);
	}
	
	public CsReturnMark createMark(Customer customer, Integer csReturnId, String content) {
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
