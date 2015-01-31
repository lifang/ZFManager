package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
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
		if (keys != null) {
			keys = "%"+keys+"%";
		}
		long count = goodMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<Good>(new PageRequest(1, pageSize), new ArrayList<Good>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<Good> result = goodMapper.findPageGoodsByKeys(request, status, keys);
		Page<Good> goods = new Page<Good>(request, result, count);
		if (goods.getCurrentPage() > goods.getTotalPage()) {
			request = new PageRequest(goods.getTotalPage(), pageSize);
			result = goodMapper.findPageGoodsByKeys(request, status, keys);
			goods = new Page<Good>(request, result, count);
		}
		return goods;
	}
}
