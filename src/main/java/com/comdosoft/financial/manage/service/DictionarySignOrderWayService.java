package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.DictionarySignOrderWay;
import com.comdosoft.financial.manage.mapper.zhangfu.DictionarySignOrderWayMapper;

@Service
public class DictionarySignOrderWayService {

	@Autowired
	private DictionarySignOrderWayMapper dictionarySignOrderWayMapper;
	
	public List<DictionarySignOrderWay> listAll(){
		return dictionarySignOrderWayMapper.selectAll();
	}
}
