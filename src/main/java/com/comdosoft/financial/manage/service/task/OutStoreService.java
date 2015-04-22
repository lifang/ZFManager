package com.comdosoft.financial.manage.service.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.task.Good;
import com.comdosoft.financial.manage.domain.zhangfu.task.OutStore;
import com.comdosoft.financial.manage.mapper.zhangfu.OutStoreMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class OutStoreService {
	
	@Value("${page.outStore.size}")
    private int pageSize=2;
	
	@Autowired
	private OutStoreMapper outStoreMapper;
	
	public Page<OutStore> findPages(int page, Byte status, String keys){
		if (keys != null) {
			keys = "%"+keys+"%";
		}
		long count = outStoreMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<OutStore>(new PageRequest(1, pageSize), new ArrayList<OutStore>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<OutStore> result = outStoreMapper.findPageOutStoresByKeys(request, status, keys);
		Page<OutStore> outStores = new Page<>(request, result, count);
		if (outStores.getCurrentPage() > outStores.getTotalPage()) {
			request = new PageRequest(outStores.getTotalPage(), pageSize);
			result = outStoreMapper.findPageOutStoresByKeys(request, status, keys);
			outStores = new Page<>(request, result, count);
		}
		return outStores;
	}
	
	public Integer getOrderIdByOutStorageId(int id){
		Map<String, Object> map=outStoreMapper.getOrderIdByOutStorageId(id);
		if(null !=map){
			if(null != map.get("orderId")){
				return Integer.parseInt(map.get("orderId").toString());
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public String getOrderNumberByOutStorageId(int id){
		Map<String, Object> map=outStoreMapper.getOrderIdByOutStorageId(id);
		if(null !=map){
			if(null != map.get("orderNumber")){
				return map.get("orderNumber").toString();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public List<Good> getGoodInfoInit(int id){
		List<Good> goods=outStoreMapper.getGoodInfoInit(id);
		List<Good> goodsNew=new ArrayList<Good>();
		
		StringBuilder str=new StringBuilder();
		if(null!=getOrderIdByOutStorageId(id)){
			int orderId=getOrderIdByOutStorageId(id);
			for(int i=0;i<goods.size();i++){
				str.setLength(0);
				Good goodTemp=goods.get(i);
				List<Map<String, Object>> terminals=outStoreMapper.getTerminalNum(orderId, goodTemp.getId());
				
				for(int j=0;j<terminals.size();j++){
					
					if(str.length()==0){
						str.append(terminals.get(j).get("num").toString());
					}else{
						str.append(" "+terminals.get(j).get("num").toString());
					}
				}
				goodTemp.setTerminalPort(str.toString());
				goodsNew.add(goodTemp);
			}
			return goodsNew;
		}else{
			return null;
		}
	}
	
	public String getAddressInit(int id){
		Map<String, Object> map =outStoreMapper.getAddressInit(id);
		if(null!=map){
			if(null!=map.get("address")){
				return map.get("address").toString();
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	public Map<String,Object> getWLInfo(int id){
		if(null !=getOrderIdByOutStorageId(id)){
			List<Map<String, Object>> temp=outStoreMapper.getWLByOrderId(getOrderIdByOutStorageId(id));
			if(null!=temp && temp.size()>0){
				return temp.get(0);
			}else{
				return null;
			}
			
		}else{
			return null;
		}
	}
	
	public Map<String,Object> getOrderDetailInfo(int id){
		if(null !=getOrderIdByOutStorageId(id)){
			Map<String,Object> map=outStoreMapper.getInfoInit(getOrderIdByOutStorageId(id));
			int invoiceType=Integer.parseInt(map.get("invoiceType").toString());
			if(invoiceType==0){
				map.put("invoiceName", "公司");
			}else{
				map.put("invoiceName", "个人");
			}
			int types=Integer.parseInt(map.get("types").toString());
			if(types==0){
				map.put("typesName", "代购");
			}else{
				map.put("typesName", "批购");
			}
			
			
			return map;
		}else{
			return null;
		}
	}
	
	public String getOperater(int id){
		Map<String, Object> map=outStoreMapper.getOrderIdByOutStorageId(id);
		if(null !=map){
			if(null != map.get("processUserId")){
				String name= map.get("processUserId").toString();
				return name;
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	
	public List<Map<String, Object>> getRemarks(int id){
		return outStoreMapper.getRemarks(id);
	}
	
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveRemark(int id,String remarkContent,int loginId){
		Map<String, Object> result=new HashMap<String, Object>();
		int resultCode=Response.SUCCESS_CODE;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("保存备注成功");
		
		//保存备注
		int temp=outStoreMapper.saveRemark(loginId,id,remarkContent);
		if(temp<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("保存备注出错");
		}
		//执行保存操作记录
		
		
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	};
	
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> save(int id,String wlCompany,String wlNum,String terminalNum,int loginId){
		Map<String, Object> result=new HashMap<String, Object>();
		int resultCode=Response.SUCCESS_CODE;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("保存物流和终端号成功");
		
		int orderId=getOrderIdByOutStorageId(id);
		
		Map<String, Object> tempOrderMap=outStoreMapper.getCutomerTypeByOrderId(orderId);
		int types=Integer.parseInt(tempOrderMap.get("types").toString());
		String customerId=tempOrderMap.get("customerId").toString();
		
		if(types==1 ||types==2){
			//用户订购
			int temp1=outStoreMapper.updateTerminals(customerId, "", orderId, terminalNum);
			if(temp1<0){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("更新terminals表出错");
			}else{
				resultCode=Response.SUCCESS_CODE;
			}
		}else if(types==3){
			//代理商代购
			int agentId=outStoreMapper.getAgentIdByCustomerId(customerId);
			int temp1=outStoreMapper.updateTerminals("", agentId+"", orderId, terminalNum);
			if(temp1<0){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("更新terminals表出错");
			}else{
				resultCode=Response.SUCCESS_CODE;
			}
		}else if(types==4 || types==5){
			//代理商代租赁
			int agentId=outStoreMapper.getAgentIdByCustomerId(customerId);
			int temp1=outStoreMapper.updateTerminals(customerId, agentId+"", orderId, terminalNum);
			if(temp1<0){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("更新terminals表出错");
			}else{
				resultCode=Response.SUCCESS_CODE;
			}
		}
		if(resultCode==Response.ERROR_CODE){
			
		}else{
			if(null!=outStoreMapper.getWLByOrderId(orderId) && outStoreMapper.getWLByOrderId(orderId).size()>0){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("已经存在该订单号的物流记录");
			}else{
				//根据orderId,对物流公司，物流单号进行保存
				int temp1=outStoreMapper.saveWLInfo(wlCompany, wlNum, orderId);
				if(temp1<1){
					resultCode=Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("保存物流公司名称，物流单号出错");
				}else{
					//循环对商品及其终端号进行保存
					if(terminalNum.length()>0){
						String[] temp=terminalNum.split("//|");
						for(int i=0;i<temp.length;i++){
							String[] tempChild=temp[i].toString().split("//_");
							int goodId=Integer.parseInt(tempChild[0].toString());
							String[] ports=tempChild[1].toString().split("//,");
							for(int j=0;j<ports.length;j++){
								String port=ports[j];
								//执行保存
								int temp2=outStoreMapper.saveTerminalNum(orderId, goodId, port,loginId);
								if(temp2<1){
									resultCode=Response.ERROR_CODE;
									resultInfo.setLength(0);
									resultInfo.append("保存商品的终端号出错");
									break;
								}
							}
									
						}
					}else{
						resultCode=Response.ERROR_CODE;
						resultInfo.setLength(0);
						resultInfo.append("商品的终端号为空出错");
					}
				}
			}
			//修改出库单状态
			int temp4=outStoreMapper.changeStatus(3,loginId,id);
			if(temp4<1){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("更改出库订单状态出错");
			}
		}
		//执行保存操作记录
		
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	};
	
	public Map<String, Object> checkCancel(int status,int loginId,int id){
		Map<String, Object> result=new HashMap<String, Object>();
		int resultCode=Response.SUCCESS_CODE;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("修改状态成功");
		
		//修改出库单状态
		int temp4=outStoreMapper.changeStatus(status,loginId,id);
		if(temp4<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("更改出库订单状态出错");
		}
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveProcessUser(String ids,int customerId,String customerName,int loginId){
		Map<String, Object> result=new HashMap<String, Object>();
		int resultCode=Response.SUCCESS_CODE;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("分派成功");
		
		if(ids.length()>0){
			String[] idTemp=ids.split("//,");
			for(int i=0;i<idTemp.length;i++){
				int temp=outStoreMapper.saveProcessUser(customerId, customerName,Integer.parseInt(idTemp[i]));
				
				if(temp<1){
					resultCode=Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("保存分派信息失败");
					break;
				}
			}
		}else{
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("没有选择要分派的记录");
		}
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	}
}
