package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Merchant;
import com.comdosoft.financial.manage.mapper.zhangfu.MerchantMapper;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class MerchantService {
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	/**
	 * 用户详情    商户资料列表
	 * @param customerId
	 * @param page
	 * @return
	 */
	public Page<Merchant> userMerchantPage(Integer customerId,int page) {
		PageRequest request = new PageRequest(page, Constants.PAGE_SIZE);
		Long total = merchantMapper.countByCustomerId(customerId);
		List<Merchant> merchants = merchantMapper.selectByCustomerId(customerId, request);
		return new Page<Merchant>(request, merchants, total);
	}

}
