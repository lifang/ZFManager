package com.comdosoft.financial.manage.service.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsChange;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsChangeMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsChangeService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsChangeMapper csChangeMapper;

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
		List<CsChange> result = csChangeMapper.findPageSelective(request, status, keyword);
		
		// let the records above if the current user is process user
		Collections.sort(result, new Comparator<CsChange>() {
			@Override
			public int compare(CsChange o1, CsChange o2) {
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
		Page<CsChange> csChanges = new Page<CsChange>(request, result, count);
		return csChanges;
	}
	
	public CsChange findInfoById(Integer id) {
		return csChangeMapper.selectInfoByPrimaryKey(id);
	}
}
