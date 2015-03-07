package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Merchant;
import com.comdosoft.financial.manage.mapper.zhangfu.MerchantMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {

    @Value("${page.size}")
    private Integer pageSize;
	@Autowired
	private MerchantMapper merchantMapper;
	
	/**
	 * 用户详情    商户资料列表
	 * @param customerId
	 * @param page
	 * @return
	 */
	public Page<Merchant> userMerchantPage(Integer customerId,int page) {
		PageRequest request = new PageRequest(page, pageSize);
		Long total = merchantMapper.countByCustomerId(customerId);
		List<Merchant> merchants = merchantMapper.selectByCustomerId(customerId, request);
		return new Page<>(request, merchants, total);
	}

}
