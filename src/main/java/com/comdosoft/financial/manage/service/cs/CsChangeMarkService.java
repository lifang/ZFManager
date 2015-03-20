package com.comdosoft.financial.manage.service.cs;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CsChangeMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsChangeMarkMapper;

@Service
public class CsChangeMarkService {

	@Autowired
	private CsChangeMarkMapper csChangeMarkMapper;

	public CsChangeMark create(Customer customer, Integer csChangeId, String content) {
		CsChangeMark csChangeMark = new CsChangeMark();
		csChangeMark.setCustomId(customer.getId());
		csChangeMark.setCsChangeId(csChangeId);
		csChangeMark.setCreatedAt(new Date());
		csChangeMark.setContent(content);
		csChangeMarkMapper.insert(csChangeMark);
		csChangeMark.setCustomer(customer);
		return csChangeMark;
	}
	
	public List<CsChangeMark> findByChangeId(Integer csChangeId) {
		return csChangeMarkMapper.selectByChangeId(csChangeId);
	}
}
