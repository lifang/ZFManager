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

import com.comdosoft.financial.manage.domain.zhangfu.CsUpdateInfo;
import com.comdosoft.financial.manage.domain.zhangfu.CsUpdateInfoMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsUpdateInfoMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsUpdateInfoMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsUpdateService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsUpdateInfoMapper csUpdateInfoMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsUpdateInfoMarkMapper csUpdateInfoMarkMapper;

	public Page<CsUpdateInfo> findPage(Customer customer, int page, Byte status, String keyword) {
		long count = csUpdateInfoMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsUpdateInfo>(new PageRequest(1, pageSize), new ArrayList<CsUpdateInfo>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsUpdateInfo> result = csUpdateInfoMapper.findPageSelective(request, status, keyword);
		
		Collections.sort(result, new Comparator<CsUpdateInfo>() {
			@Override
			public int compare(CsUpdateInfo o1, CsUpdateInfo o2) {
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
		Page<CsUpdateInfo> csUpdates = new Page<CsUpdateInfo>(request, result, count);
		return csUpdates;
	}
	
	public CsUpdateInfo findInfoById(Integer id) {
		return csUpdateInfoMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsUpdateInfo updateStatus(Integer csUpdateId, Byte status) {
		CsUpdateInfo csUpdate = csUpdateInfoMapper.selectByPrimaryKey(csUpdateId);
		csUpdate.setStatus(status);
		csUpdate.setUpdatedAt(new Date());
		csUpdateInfoMapper.updateByPrimaryKey(csUpdate);
		return csUpdate;
	}
	
	public void handle(Integer csUpdateId) {
		updateStatus(csUpdateId, (byte)1);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csUpdateId) {
		CsUpdateInfo csUpdate = updateStatus(csUpdateId, (byte)3);
		
		Integer terminalId = csUpdate.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csUpdateId) {
		CsUpdateInfo csUpdate = updateStatus(csUpdateId, (byte)2);
		
		Integer terminalId = csUpdate.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csUpdateInfoMapper.dispatchUserByIds(params);
	}
	
	@Transactional("transactionManager")
	public CsUpdateInfoMark createMark(Customer customer, Integer csUpdateId, String content) {
		handle(csUpdateId);
		
		CsUpdateInfoMark csUpdateMark = new CsUpdateInfoMark();
		csUpdateMark.setCustomerId(customer.getId());
		csUpdateMark.setCsUpdateInfoId(csUpdateId);
		csUpdateMark.setCreatedAt(new Date());
		csUpdateMark.setContent(content);
		csUpdateInfoMarkMapper.insert(csUpdateMark);
		csUpdateMark.setCustomer(customer);
		return csUpdateMark;
	}
	
	public List<CsUpdateInfoMark> findMarksByCsUpdateId(Integer csUpdateId) {
		return csUpdateInfoMarkMapper.selectByUpdateId(csUpdateId);
	}
}
