package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsRepairStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsRepairStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsRepairStatus.HANDLE;

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
import com.comdosoft.financial.manage.domain.zhangfu.CsRepair;
import com.comdosoft.financial.manage.domain.zhangfu.CsRepairMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReceiverAddressMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsRepairMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsRepairMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.service.cs.CsConstants.CsRepairStatus;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsRepairService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsRepairMapper csRepairMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsRepairMarkMapper csRepairMarkMapper;
	@Autowired
	private CsReceiverAddressMapper csReceiverAddressMapper;

	public Page<CsRepair> findPage(Customer customer, int page, Byte status, String keyword) {
		long count = csRepairMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsRepair>(new PageRequest(1, pageSize), new ArrayList<CsRepair>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsRepair> result = csRepairMapper.findPageSelective(request,customer.getId(), status, keyword);
		Page<CsRepair> csRepairs = new Page<CsRepair>(request, result, count);
		return csRepairs;
	}
	
	public CsRepair findInfoById(Integer id) {
		return csRepairMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsRepair updateStatus(Integer csRepairId, Byte status) {
		CsRepair csRepair = csRepairMapper.selectByPrimaryKey(csRepairId);
		csRepair.setStatus(status);
		csRepair.setUpdatedAt(new Date());
		csRepairMapper.updateByPrimaryKey(csRepair);
		return csRepair;
	}
	
	@Transactional("transactionManager")
	private void cancelOrFinish(Integer csRepairId, Byte status) {
		CsRepair csRepair = updateStatus(csRepairId, status);
		
		Integer terminalId = csRepair.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	public void handle(Integer csRepairId) {
		updateStatus(csRepairId, HANDLE);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csRepairId) {
		CsRepair csRepair = updateStatus(csRepairId, CANCEL);
		
		Integer terminalId = csRepair.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csRepairId) {
		CsRepair csRepair = updateStatus(csRepairId, FINISH);
		
		Integer terminalId = csRepair.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	public void addPay(Integer csRepairId, Byte payType) {
		CsRepair csRepair = csRepairMapper.selectByPrimaryKey(csRepairId);
		if (null != csRepair) {
			csRepair.setPayTypes(payType);
			csRepairMapper.updateByPrimaryKey(csRepair);
		}
	}
	
	public void updatePay(Integer csRepairId, Integer repairPrice) {
		CsRepair csRepair = csRepairMapper.selectByPrimaryKey(csRepairId);
		if (null != csRepair) {
			csRepair.setRepairPrice(repairPrice);
			csRepairMapper.updateByPrimaryKey(csRepair);
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csRepairMapper.dispatchUserByIds(params);
	}
	
	@Transactional("transactionManager")
	public CsRepairMark createMark(Customer customer, Integer csRepairId, String content) {
		updateStatus(csRepairId, (byte)2);
		
		CsRepairMark csRepairMark = new CsRepairMark();
		csRepairMark.setCustomerId(customer.getId());
		csRepairMark.setCsRepairId(csRepairId);
		csRepairMark.setCreatedAt(new Date());
		csRepairMark.setContent(content);
		csRepairMarkMapper.insert(csRepairMark);
		csRepairMark.setCustomer(customer);
		return csRepairMark;
	}
	
	public List<CsRepairMark> findMarksByCsRepairId(Integer csRepairId) {
		return csRepairMarkMapper.selectByRepairId(csRepairId);
	}
	
	@Transactional("transactionManager")
	public Integer createBill(Customer customer, CsReceiverAddress csReceiverAddress, 
			String terminalNum, Integer repairPrice, String description) {
		CsRepair csRepair = new CsRepair();
		Terminal terminal = terminalMapper.findTerminalByNum(terminalNum);
		if (null != terminal) {
			csRepair.setTerminalId(terminal.getId());
		}
		csReceiverAddress.setCreatedAt(new Date());
		csReceiverAddressMapper.insert(csReceiverAddress);
		
		csRepair.setApplyNum(String.valueOf(System.currentTimeMillis()) + customer.getId());
		csRepair.setReceiveAddressId(csReceiverAddress.getId());
		csRepair.setProcessUserId(customer.getId());
		csRepair.setProcessUserName(customer.getName());
		csRepair.setRepairPrice(repairPrice);
		csRepair.setDescription(description);
		csRepair.setCreatedAt(new Date());
		csRepair.setUpdatedAt(new Date());
		csRepair.setStatus(CsRepairStatus.NOT_PAID);
		csRepair.setCsRepairMarksId(0);
		
		csRepairMapper.insert(csRepair);
		return csRepair.getId();
	}
}
