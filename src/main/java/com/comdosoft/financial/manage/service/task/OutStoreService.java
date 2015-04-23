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
import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.manage.domain.zhangfu.task.Good;
import com.comdosoft.financial.manage.domain.zhangfu.task.OutStore;
import com.comdosoft.financial.manage.mapper.zhangfu.OutStoreMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.ReocrdOperateMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class OutStoreService {
	
	@Value("${page.outStore.size}")
    private int pageSize=2;
	
	@Autowired
	private OutStoreMapper outStoreMapper;
	@Autowired
	private ReocrdOperateMapper mapper;
	
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
			int actualPrice=Integer.parseInt(map.get("actualPrice").toString())/100;
			map.put("actualPrice", actualPrice);
			
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
	public Map<String, Object> saveRemark(int id,String remarkContent,int loginId,int userType) throws Exception{
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
		String userName=outStoreMapper.getNameByLoginId(loginId);
		String content=userName+"执行了任务的出库页面查看详情【添加备注】的操作，操作的记录Id是"+id;
		mapper.save(loginId, userName, userType, (int)OperateRecord.TYPES_CHECKOUT, content,id);
		
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	};
	
	
	@SuppressWarnings("finally")
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> save(int outStorageId,String wlCompany,String wlNum,String terminalNums,int loginId,int userType){
		Map<String, Object> result=new HashMap<String, Object>();
		StringBuffer portsAll=new StringBuffer("");
		int allQuantity=0;
		
		Map<Integer, Integer> goodQuantityMap=new HashMap<Integer, Integer>();
		
		
		int resultCode=Response.SUCCESS_CODE;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("保存成功");
		int orderId=getOrderIdByOutStorageId(outStorageId);
		Map<String, Object> tempOrderMap=outStoreMapper.getCutomerTypeByOrderId(orderId);
		int types=Integer.parseInt(tempOrderMap.get("types").toString());
		String customerId="";
		if(null!=tempOrderMap.get("customerId")){
			customerId=tempOrderMap.get("customerId").toString();
		}else{
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("订单管理的customerId为空");
		}
		if(resultCode==Response.SUCCESS_CODE){
		//循环对商品及其终端号进行保存
		if(terminalNums.length()>0){
			String[] temp=terminalNums.split("\\|");
			for(int i=0;i<temp.length;i++){
				String[] tempChild=temp[i].toString().split("\\_");
				
				int goodId=Integer.parseInt(tempChild[0].toString());
				String[] ports=tempChild[1].toString().split("\\,");
				allQuantity=allQuantity+ports.length;
				
				goodQuantityMap.put(goodId, ports.length);
				
				int temp2=outStoreMapper.saveTerminalNum(orderId, goodId, tempChild[1],loginId,ports.length,outStorageId);
				if(temp2<1){
					resultCode=Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("保存商品的终端号出错");
				}
				
				//计算goodid对应数量是否正确
				if(resultCode==Response.SUCCESS_CODE){
					if(types!=3){
						int quantity=outStoreMapper.getQuantityByOrderGood(goodId,orderId);
						if(quantity!=ports.length){
							resultCode=Response.ERROR_CODE;
							resultInfo.setLength(0);
							resultInfo.append("输入终端数量与订单需求数量不一致");
						}
					}
				}
				if(resultCode==Response.SUCCESS_CODE){
					for(int j=0;j<ports.length;j++){
						String port=ports[j];
						if(portsAll.length()<1){
							portsAll.append(port);
						}else{
							portsAll.append(","+port);
						}
						int temp1=0;
						if(resultCode==Response.SUCCESS_CODE){
							int countTemp=outStoreMapper.getTerminalIsUsed(port);
							if(countTemp>0){
								resultCode=Response.ERROR_CODE;
								resultInfo.setLength(0);
								resultInfo.append("输入的终端号已经关联其他订单号");
							}
							if(resultCode==Response.SUCCESS_CODE){
								if(types==1 || types==2){
									temp1=outStoreMapper.updateTerminals(customerId, "0", orderId, port);
								}else if(types==3){
									int agentId=outStoreMapper.getAgentIdByCustomerId(customerId);
									temp1=outStoreMapper.updateTerminals("0", agentId+"", orderId, port);
								}else{
									int agentId=outStoreMapper.getAgentIdByCustomerId(customerId);
									temp1=outStoreMapper.updateTerminals(customerId, agentId+"", orderId, port);
								}
								if(temp1<1){
									//更新失败
									resultCode=Response.ERROR_CODE;
									resultInfo.setLength(0);
									resultInfo.append("更新terminals表信息出错");
								}
							}
						}
					}
					
				}
			}
		}
		}
		//更新cs_out_storage状太为处理完成，终端list,以及数量
		if(resultCode==Response.SUCCESS_CODE){
		     //更新cs_out_storage状态
		     int temp1=outStoreMapper.updateCsOutStorages(3, allQuantity, portsAll.toString(), outStorageId);
		     if(temp1<1){
		      resultCode=Response.ERROR_CODE;
		      resultInfo.setLength(0);
		      resultInfo.append("更新cs_out_storage表信息出错");
		     }
		}
		//更新orders表
		if(resultCode==Response.SUCCESS_CODE){
			int temp1=0;
			if(types!=3){
				//非批购
				temp1=outStoreMapper.updateOrderStatus(3, orderId);
			}else{
				List<Map<String, Object>> temp3=outStoreMapper.getOrderGoodQuantity(orderId);
				
				for(int i=0;i<temp3.size();i++){
					int goodId=Integer.parseInt(temp3.get(i).get("goodId").toString());
					int quantity1=0;
					if(null!=temp3){
						if(null!=temp3.get(i).get("quantity")){
							quantity1=Integer.parseInt(temp3.get(i).get("quantity").toString());
						}
					}
					Map<String, Object> temp2=outStoreMapper.getInOutStorageInfo(orderId,goodId);
					int quantity2=0;
					if(null!=temp2){
						if(null!=temp2.get("quantity")){
							quantity2=Integer.parseInt(temp2.get("quantity").toString());
						}
					}
					int quantity3=goodQuantityMap.get(goodId);
					
					if((quantity1-quantity2)==quantity3){
						temp1=outStoreMapper.updateOrderStatus(3, orderId);
					}else if((quantity1-quantity2)<quantity3){
						//出错
						  resultCode=Response.ERROR_CODE;
					      resultInfo.setLength(0);
					      resultInfo.append("出库数量多于应发货数量");
					}
				}
			}
		}
		
		if(resultCode==Response.SUCCESS_CODE){
			//根据orderId,对物流公司，物流单号进行保存
			int temp1=outStoreMapper.saveWLInfo(wlCompany, wlNum, orderId,outStorageId);
			if(temp1<1){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("保存物流公司名称，物流单号出错");
			}
		}
		//执行保存操作记录
		String userName=outStoreMapper.getNameByLoginId(loginId);
		String content=userName+"执行了任务的出库页面【添加出库记录】的操作，操作的记录Id是"+outStorageId;
		mapper.save(loginId, userName, userType, (int)OperateRecord.TYPES_CHECKOUT, content,outStorageId);
		
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		
		return result;
	};
	
	public Map<String, Object> checkCancel(int status,int loginId,int id,int userType){
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
		//执行保存操作记录
		String userName=outStoreMapper.getNameByLoginId(loginId);
		String content=userName+"执行了任务的出库页面【取消】的操作，操作的记录Id是"+id;
		mapper.save(loginId, userName, userType, (int)OperateRecord.TYPES_CHECKOUT, content,id);
		
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveProcessUser(String ids,int customerId,String customerName,int loginId,int userType) throws Exception{
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
				}
			}
		}else{
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("没有选择要分派的记录");
		}
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		
		//执行保存操作记录
		String userName=outStoreMapper.getNameByLoginId(loginId);
		String content=userName+"执行了任务的出库页面【分派】的操作，操作的记录Id是"+ids;
		mapper.save(loginId, userName, userType, (int)OperateRecord.TYPES_CHECKOUT, content,0);
		
		return result;
	}
}
