package com.comdosoft.financial.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.mapper.zhangfu.ReocrdOperateMapper;



@Service
public class RecordOperateService {
	
	@Autowired
	private ReocrdOperateMapper mapper;
	
	public int saveOperateRecord(int operateUserId,String operateUserName,int operateUsreType,
			int types,String content,int operateTargetId){
		return mapper.save(operateUserId,operateUserName,operateUsreType,types,content,operateTargetId);
	}
}
