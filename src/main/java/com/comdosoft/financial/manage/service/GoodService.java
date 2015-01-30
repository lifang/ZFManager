package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;

@Service
public class GoodService {

	@Autowired
	private GoodMapper goodMapper;
	
	public List<Good> listPage(int page, int pageSize){
		return goodMapper.selectPage(0, 1);
	}
}
