package com.comdosoft.financial.manage.service.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.ReocrdOperateMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.StockManageMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.fasterxml.jackson.databind.type.MapType;

@Service
public class StockManageService {
	@Autowired
	private StockManageMapper stockManageMapper;
	@Autowired
	private ReocrdOperateMapper mapper;
	@Autowired
	private TerminalMapper terminalMapper;
	
	public Map<String, Object> showInfo(String serialNum,int loginId){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端号不存在");
		
		int resultType=-1;
		Map<String, Object> temp=stockManageMapper.checkAccount(serialNum);
		if(null!=temp){
			resultCode=1;
			resultInfo.setLength(0);
			resultInfo.append("当前终端未审核售后服务");
			//终端ID
			int terminalId=Integer.parseInt(temp.get("id").toString());
			
			List<Map<String, Object>> temp1=stockManageMapper.isAgents(serialNum);
			Map<String, Object> temp2=stockManageMapper.isCancels(terminalId);
			Map<String, Object> temp3=stockManageMapper.isChanges(terminalId);
			Map<String, Object> temp4=stockManageMapper.isLeaseReturns(terminalId);
			Map<String, Object> temp5=stockManageMapper.isRepairs(terminalId);
			Map<String, Object> temp6=stockManageMapper.isReturns(terminalId);
			
			if(null!=temp1&& temp1.size()>0){
				resultInfo.setLength(0);
				resultType=1;
				resultInfo.append("代理商售后");
			}
			if(null!=temp2){
				resultInfo.setLength(0);
				resultType=2;
				resultInfo.append("注销");
			}
			if(null!=temp3){
				resultInfo.setLength(0);
				resultType=3;
				resultInfo.append("换货");
			}
			if(null!=temp4){
				resultInfo.setLength(0);
				resultType=4;
				resultInfo.append("租赁退还");
			}
			if(null!=temp5){
				resultInfo.setLength(0);
				resultType=5;
				resultInfo.append("返修");
			}
			if(null!=temp6){
				resultInfo.setLength(0);
				resultType=6;
				resultInfo.append("退货");
			}
		}
		
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		map.put("resultType", resultType);
		return map;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> checkAccount(String serialNum,int loginId,String userName,int userType){
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端号不存在");

		Map<String, Object> temp=stockManageMapper.checkAccount(serialNum);
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
	public Map<String, Object> toAfterSaleStock(String serialNum,int loginId,String userName,int userType) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
			
		resultInfo.setLength(0);
		resultInfo.append("终端退回售后库失败");
		
		Map<String, Object> mapTemp=stockManageMapper.checkAccount(serialNum);
		if(null!=mapTemp){
			int type=Integer.parseInt(mapTemp.get("resultType").toString());
			if(type==5 || type==2 || type==-1){
				//返修，注销，未找到
				resultCode=1;
				resultInfo.setLength(0);
				resultInfo.append("该终端未找到售后类型或返修或者注销中,不予退回售后库");
			}else{
				int terminalId=Integer.parseInt(mapTemp.get("id").toString());
				Terminal t=terminalMapper.selectByPrimaryKey(terminalId);
				t.setCustomerId(null);
				t.setAgentId(null);
				t.setStatus(Terminal.STATUS_NO_OPEN);
				t.setIsReturnCsDepots(true);
				int temp1=terminalMapper.updateByPrimaryKey(t);
				if(temp1>0){
				}else{
					throw new Exception("初始化终端信息出错");
				}
				
				int temp=stockManageMapper.toAfterSaleStock(serialNum, loginId);
				if(temp>0){
					resultCode=1;
					resultInfo.setLength(0);
					resultInfo.append("终端退回售后库成功");
				}else{
					throw new Exception("终端退回售后库成功");
				}
			}
			String content=userName+"执行了任务的售后库存管理页面的【放入售后库存】的操作，操作的记录Id是"+mapTemp.get("id");
			mapper.save(loginId, userName, userType, OperateRecord.TYPES_TERMINAL, content,Integer.parseInt(mapTemp.get("id").toString()));
		}
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> toNormalStock(String serialNum,int loginId,String userName,int userType) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		
		resultInfo.setLength(0);
		resultInfo.append("终端入正常库失败");

		Map<String, Object> temp1=stockManageMapper.checkAccountIsInAfterSale(serialNum);
		if(null==temp1){
			resultCode=-2;
			resultInfo.setLength(0);
			resultInfo.append("终端号不在售后库中");
		}else{
			
			int temp=stockManageMapper.toNormalStock(serialNum, loginId);
			if(temp>0){
				resultCode=1;
				resultInfo.setLength(0);
				resultInfo.append("终端入正常库成功");
			}else{
				throw new Exception("终端入正常库成功");
			}
		}
		
		StringBuilder content= new StringBuilder();
		if(null!=temp1){
			content.setLength(0);
			content.append(userName+"执行了任务的售后库存管理页面的【正常入库】的操作，操作的记录Id是"+temp1.get("id"));
			mapper.save(loginId, userName, userType, OperateRecord.TYPES_AFTERMARKET_INVENTORY, content.toString(),Integer.parseInt(temp1.get("id").toString()));
		}
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> breakDown(String serialNum,int loginId,String userName,int userType) throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		int resultCode=-1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("终端报废失败");
		
		Map<String, Object> temp1=stockManageMapper.checkAccountIsInAfterSale(serialNum);
		if(null==temp1){
			resultCode=-2;
			resultInfo.setLength(0);
			resultInfo.append("终端号不在售后库中");
		}else{
			int temp=stockManageMapper.breakDown(serialNum, loginId);
			if(temp>0){
				resultCode=1;
				resultInfo.setLength(0);
				resultInfo.append("终端报废成功");
			}else{
				throw new Exception("终端报废成功");
			}
			String content=userName+"执行了任务的售后库存管理页面的【报废】的操作，操作的记录Id是"+temp1.get("id");
			mapper.save(loginId, userName, userType, OperateRecord.TYPES_AFTERMARKET_INVENTORY, content,Integer.parseInt(temp1.get("id").toString()));
		}
		map.put("resultCode", resultCode);
		map.put("resultInfo", resultInfo);
		return map;
	}
	
}
