package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class GoodService {

	@Autowired
	private GoodMapper goodMapper;
	
	public Page<Good> findPages(int page, int pageSize, Integer status, String keys){
		
		long count = goodMapper.countByKeys(status, keys);
		PageRequest request = new PageRequest(page, pageSize);
		List<Good> result = goodMapper.findPageGoodsByKeys(request, status, keys);
		return new Page<Good>(request, result, count);
	}
}
