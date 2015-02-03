package com.comdosoft.financial.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCardType;
import com.comdosoft.financial.manage.mapper.zhangfu.DictionaryCardTypeMapper;

@Service
public class DictionaryCardTypeService {

	@Autowired
	private DictionaryCardTypeMapper dictionaryCardTypeMapper;
	
	public List<DictionaryCardType> listAll(){
		return dictionaryCardTypeMapper.selectAll();
	}
}
