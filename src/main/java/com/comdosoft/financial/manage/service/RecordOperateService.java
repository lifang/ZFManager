package com.comdosoft.financial.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.mapper.zhangfu.ReocrdOperateMapper;



@Service
public class RecordOperateService {
	
	@Autowired
	private ReocrdOperateMapper mapper;
	
	public int saveOperateRecord(Integer operateUserId,String operateUserName,Byte operateUsreType,
			Integer types,String content,Integer operateTargetId){
		return mapper.save(operateUserId,operateUserName,operateUsreType,types,content,operateTargetId);
	}
}
