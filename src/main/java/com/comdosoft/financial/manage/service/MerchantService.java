package com.comdosoft.financial.manage.service;

import com.comdosoft.financial.manage.domain.zhangfu.Merchant;
import com.comdosoft.financial.manage.mapper.zhangfu.MerchantMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Long count = merchantMapper.countByCustomerId(customerId);
        if (count == 0) {
            return new Page<>(new PageRequest(1, pageSize), new ArrayList<Merchant>(), count);
        }
        PageRequest request = new PageRequest(page, pageSize);
        List<Merchant> result = merchantMapper.selectByCustomerId(customerId, request);

        Page<Merchant> merchants = new Page<>(request, result, count);
        if (merchants.getCurrentPage() > merchants.getTotalPage()) {
            request = new PageRequest(merchants.getTotalPage(), pageSize);
            result =  merchantMapper.selectByCustomerId(customerId, request);
            merchants = new Page<>(request, result, count);
        }
        return merchants;
	}

    public Merchant findMerchantInfo(Integer id){
        return merchantMapper.selectByPrimaryKey(id);
    }

}
