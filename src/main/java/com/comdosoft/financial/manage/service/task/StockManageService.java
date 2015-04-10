package com.comdosoft.financial.manage.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.manage.mapper.zhangfu.ReocrdOperateMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.StockManageMapper;
import com.google.common.collect.Maps;

@Service
public class StockManageService {
	@Autowired
	private StockManageMapper stockManageMapper;
	@Autowired
	private ReocrdOperateMapper mapper;
	
	public Map<String, Object> showInfo(String account,int loginId){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端号不存在");

		Map<String, Object> temp=stockManageMapper.checkAccount(account);
		if(null!=temp){
			resultCode=1;
			resultInfo.setLength(0);
			resultInfo.append("当前终端未审核售后服务");
			//终端ID
			int terminalId=Integer.parseInt(temp.get("id").toString());
			
			Map<String, Object> temp1=stockManageMapper.isAgents(terminalId);
			Map<String, Object> temp2=stockManageMapper.isCancels(terminalId);
			Map<String, Object> temp3=stockManageMapper.isChanges(terminalId);
			Map<String, Object> temp4=stockManageMapper.isLeaseReturns(terminalId);
			Map<String, Object> temp5=stockManageMapper.isRepairs(terminalId);
			Map<String, Object> temp6=stockManageMapper.isReturns(terminalId);
			
			if(null!=temp1){
				resultInfo.setLength(0);
				resultInfo.append("代理商售后");
			}
			if(null!=temp2){
				resultInfo.setLength(0);
				resultInfo.append("注销");
			}
			if(null!=temp3){
				resultInfo.setLength(0);
				resultInfo.append("换货");
			}
			if(null!=temp4){
				resultInfo.setLength(0);
				resultInfo.append("租赁退还");
			}
			if(null!=temp5){
				resultInfo.setLength(0);
				resultInfo.append("返修");
			}
			if(null!=temp6){
				resultInfo.setLength(0);
				resultInfo.append("返回");
			}
		}
		
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> checkAccount(String account,int loginId,String userName,int userType){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端号不存在");

		Map<String, Object> temp=stockManageMapper.checkAccount(account);
		if(null!=temp){
			resultCode=1;
			resultInfo.setLength(0);
			resultInfo.append("终端号存在");
		}
		
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> toAfterSaleStock(String account,int loginId,String userName,int userType){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端退回售后库失败");
		
		Map<String, Object> mapTemp=stockManageMapper.checkAccount(account);
		if(null!=null){
			int temp=stockManageMapper.toAfterSaleStock(account, loginId);
			if(temp>0){
				resultCode=1;
				resultInfo.setLength(0);
				resultInfo.append("终端退回售后库成功");
			}
			
			String content=userName+"执行了任务的售后库存管理页面的【放入售后库存】的操作，操作的记录Id是"+mapTemp.get("id");
			mapper.save(loginId, userName, userType, (int)OperateRecord.TYPES_TERMINAL, content,Integer.parseInt(mapTemp.get("id").toString()));
		}
		
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> toNormalStock(String account,int loginId,String userName,int userType){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端入正常库失败");

		Map<String, Object> temp1=stockManageMapper.checkAccountIsInAfterSale(account);
		if(null==temp1){
			resultCode=-2;
			resultInfo.setLength(0);
			resultInfo.append("终端号不在售后库中");
		}else{
			
			int temp=stockManageMapper.toNormalStock(account, loginId);
			if(temp>0){
				resultCode=1;
				resultInfo.setLength(0);
				resultInfo.append("终端入正常库成功");
			}
		}
		
		StringBuilder content= new StringBuilder();
		if(null!=temp1){
			content.setLength(0);
			content.append(userName+"执行了任务的售后库存管理页面的【正常入库】的操作，操作的记录Id是"+temp1.get("id"));
			mapper.save(loginId, userName, userType, (int)OperateRecord.TYPES_AFTERMARKET_INVENTORY, content.toString(),Integer.parseInt(temp1.get("id").toString()));
		}
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> breakDown(String account,int loginId,String userName,int userType){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端报废失败");
		
		Map<String, Object> temp1=stockManageMapper.checkAccountIsInAfterSale(account);
		if(null==temp1){
			resultCode=-2;
			resultInfo.setLength(0);
			resultInfo.append("终端号不在售后库中");
		}else{
			int temp=stockManageMapper.breakDown(account, loginId);
			if(temp>0){
				resultCode=1;
				resultInfo.setLength(0);
				resultInfo.append("终端报废成功");
			}
			String content=userName+"执行了任务的售后库存管理页面的【报废】的操作，操作的记录Id是"+temp1.get("id");
			mapper.save(loginId, userName, userType, (int)OperateRecord.TYPES_AFTERMARKET_INVENTORY, content,Integer.parseInt(temp1.get("id").toString()));
		}
		
		
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
}
