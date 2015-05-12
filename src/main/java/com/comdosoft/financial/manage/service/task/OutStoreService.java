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
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.domain.zhangfu.task.Good;
import com.comdosoft.financial.manage.domain.zhangfu.task.OutStore;
import com.comdosoft.financial.manage.mapper.zhangfu.GoodMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OutStoreMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.ReocrdOperateMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class OutStoreService {
	
	@Value("${page.outStore.size}")
    private int pageSize=2;
	@Value("${filePath}")
	private String filePath;
	@Autowired
	private OutStoreMapper outStoreMapper;
	@Autowired
	private ReocrdOperateMapper mapper;
	@Autowired
	private GoodMapper goodMapper;
	
	public Page<OutStore> findPages(int page, Byte status, String keys){
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
				
				goodTemp.setUrlPath(filePath+goodTemp.getUrlPath());
				
				List<Map<String, Object>> terminals=outStoreMapper.getTerminalNum(orderId, goodTemp.getId());
				
				for(int j=0;j<terminals.size();j++){
					
					if(str.length()==0){
						str.append(terminals.get(j).get("num").toString());
					}else{
						str.append(" "+terminals.get(j).get("num").toString());
					}
				}
				int status=outStoreMapper.getCsOutStorageStatus(id);
				
				if(str.length()>0){
					goodTemp.setTerminalPort(str.toString());
				}else{
					if(status==3){
						goodTemp.setTerminalPort("第三方库存发货");
					}else{
						goodTemp.setTerminalPort(" ");
					}
				}
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
			List<Map<String,Object>> map=outStoreMapper.getInfoInit(getOrderIdByOutStorageId(id));
			
			Map<String , Object> mapTemp=map.get(0);
			
			int invoiceType=Integer.parseInt(map.get(0).get("invoiceType").toString());
			int oldPrice=Integer.parseInt(map.get(0).get("oldPrice").toString())/100;
			int actualPrice=Integer.parseInt(map.get(0).get("actualPrice").toString())/100;
			int payType=Integer.parseInt(map.get(0).get("payType").toString());
			mapTemp.put("actualPrice", actualPrice);
			mapTemp.put("oldPrice", oldPrice);
			if(payType==1){
				mapTemp.put("payTypeName", "支付宝");
			}else if(payType==2){
				mapTemp.put("payTypeName", "银联");
			}else{
				mapTemp.put("payTypeName", "现金");
			}
			if(invoiceType==0){
				mapTemp.put("invoiceName", "公司");
			}else{
				mapTemp.put("invoiceName", "个人");
			}
			int types=Integer.parseInt(map.get(0).get("types").toString());
			if(types==1){
				mapTemp.put("typesName", "用户订购");
			}else if(types==2){
				mapTemp.put("typesName", "用户租赁");
			}else if(types==3){
				mapTemp.put("typesName", "代理商代购");
			}else if(types==4){
				mapTemp.put("typesName", "代理商租赁");
			}else if(types==5){
				mapTemp.put("typesName", "代理商批购");
			}
			
			
			return mapTemp;
		}else{
			return null;
		}
	}
	
	public String getOperater(int id){
		Map<String, Object> map=outStoreMapper.getOrderIdByOutStorageId(id);
		if(null !=map){
			if(null != map.get("processUserName")){
				String name= map.get("processUserName").toString();
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
			throw new Exception("保存备注出错");
		}
		//执行保存操作记录
		String userName=outStoreMapper.getNameByLoginId(loginId);
		String content=userName+"执行了任务的出库页面查看详情【添加备注】的操作，操作的记录Id是"+id;
		int temp1=mapper.save(loginId, userName, userType, OperateRecord.TYPES_CHECKOUT, content,id);
		if(temp1<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("记录操作日志出错");
			throw new Exception("记录操作日志出错");
		}
		
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	};
	
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> save(int outStorageId,String wlCompany,String wlNum,String terminalNums,int loginId,int userType) throws Exception{
		Map<String, Object> result=new HashMap<String, Object>();
		int resultCode=Response.SUCCESS_CODE;
		StringBuilder resultInfo=new StringBuilder();
		
		resultInfo.setLength(0);
		resultInfo.append("保存成功");
		int orderId=getOrderIdByOutStorageId(outStorageId);
		Map<String, Object> tempOrderMap=outStoreMapper.getCutomerTypeByOrderId(orderId);
		int types=Integer.parseInt(tempOrderMap.get("types").toString());
		String agentIdCustomerId=tempOrderMap.get("belongsUserId").toString();
		String customerId="";
		
		if(null!=tempOrderMap.get("customerId")){
			customerId=tempOrderMap.get("customerId").toString();
		}else{
			if(null!=tempOrderMap.get("types")){
				if(tempOrderMap.get("types").toString().equals("3") ||tempOrderMap.get("types").toString().equals("5")){
					
				}else{
					resultCode=Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("订单没有关联用户");
					throw new Exception("订单没有关联用户");
				}
			}
		}
		//计算输入终端数量是否与输入激活码数目相等
		if(terminalNums.length()>0){
			String[] temp=terminalNums.split("\\|");
			for(int i=0;i<temp.length;i++){
				String[] tempChild=temp[i].toString().split("\\_");
				
				String portsStr=tempChild[1].toString();
				String checkCodeStr=tempChild[2].toString();
				String[] ports=portsStr.split("\\s+|,|;");
				if(!checkCodeStr.equals("无")){
					String[] checkCodes=checkCodeStr.split("\\s+|,|;");
					if(ports.length!=checkCodes.length){
						resultCode=Response.ERROR_CODE;
						resultInfo.setLength(0);
						resultInfo.append("输入的终端数量与输入的激活码数量不一致");
						throw new Exception("输入的终端数量与输入的激活码数量不一致");
					}
				}
			}
		}
		//总的终端号个数
		int allQuantity=0;
		//输入的商品对应的终端数量
		Map<Integer, Integer> goodQuantityMap=new HashMap<Integer, Integer>();
		//批购
		if(types==5){
//			if(terminalNums.length()>0){
//				String[] goodPorts=terminalNums.toString().split("\\_");
//				int goodId=Integer.parseInt(goodPorts[0].toString());
//				String portsStr=goodPorts[1].toString();
//				String[] ports=portsStr.split("\\s+|,|;");
//				//累计终端个数
//				allQuantity=allQuantity+ports.length;
//				
//				int payChannelId=outStoreMapper.getPayChannleIdByOrderId(orderId, goodId);
//				//条件过滤
//				for(int j=0;j<ports.length;j++){
//					int temp1=outStoreMapper.getTerminalsInfo(ports[j]);
//					if(temp1<0){
//						resultCode=Response.ERROR_CODE;
//						resultInfo.setLength(0);
//						resultInfo.append("输入的终端号不存在");
//						throw new Exception("输入的终端号不存在");
//					}
//					
//					int countTemp=outStoreMapper.getTerminalIsUsed(ports[j]);
//					if(countTemp>0){
//						resultCode=Response.ERROR_CODE;
//						resultInfo.setLength(0);
//						resultInfo.append("输入的终端号已经关联其他订单号");
//						throw new Exception("输入的终端号已经关联其他订单号");
//					}
//					
//					Map<String, Object> mapTemp1=outStoreMapper.getInOutStorageTerminalInfo(orderId, goodId, ports[j], outStorageId);
//					
//					int numTemp=Integer.parseInt(mapTemp1.get("num").toString());
//					if(numTemp>0){
//						resultCode=Response.ERROR_CODE;
//						resultInfo.setLength(0);
//						resultInfo.append("已存在终端号对应的记录");
//						throw new Exception("已存在终端号对应的记录");
//					}
//					
//					//更新terminals表数据
//					Map<String, Object> mapTemp=outStoreMapper.getAgentIdByCustomerId(agentIdCustomerId);
//					if(mapTemp!=null){
//						int agentId=Integer.parseInt(mapTemp.get("id").toString());
//						int temp2=outStoreMapper.updateTerminals(null, agentId+"", orderId, ports[j],payChannelId);
//						int temp3=outStoreMapper.updateGoodsPurchaseNumber(goodId);
//						
//						if(temp2<1){
//							//更新失败
//							resultCode=Response.ERROR_CODE;
//							resultInfo.setLength(0);
//							resultInfo.append("系统繁忙，请稍后再试");
//							throw new Exception("系统繁忙，请稍后再试");
//						}
//						if(temp3<1){
//							//更新失败
//							resultCode=Response.ERROR_CODE;
//							resultInfo.setLength(0);
//							resultInfo.append("系统繁忙，请稍后再试");
//							throw new Exception("系统繁忙，请稍后再试");
//						}
//					}
//				}
//				
//				//新增in_out_storages记录
//				int temp4=outStoreMapper.saveTerminalNum(orderId, goodId,portsStr,loginId,ports.length,outStorageId);
//				if(temp4<1){
//					resultCode=Response.ERROR_CODE;
//					resultInfo.setLength(0);
//					resultInfo.append("系统繁忙，请稍后再试");
//					throw new Exception("系统繁忙，请稍后再试");
//				}
//				//更新cs_out_storage表
//				List<Map<String, Object>> inOutStorageInfo=outStoreMapper.getInOutStoragesAllInfo(orderId, outStorageId);
//				StringBuffer terminalStr=new StringBuffer("");
//				int quantity=0;
//				if(null!=inOutStorageInfo){
//					for(int i=0;i<inOutStorageInfo.size();i++){
//						quantity=quantity+Integer.parseInt(inOutStorageInfo.get(i).get("quantity").toString());
//						terminalStr.append(inOutStorageInfo.get(i).get("terminal_number"));
//					}
//				}
//				int temp=outStoreMapper.updateCsOutStorages(1, quantity, terminalStr.toString(), outStorageId);
//				if(temp<1){
//					resultCode=Response.ERROR_CODE;
//					resultInfo.setLength(0);
//					resultInfo.append("系统繁忙，请稍后再试");
//					throw new Exception("系统繁忙，请稍后再试");
//				}
//				//统计所有的cs_out_storages的数量
//				int quantityOrders=outStoreMapper.getQuantityByOrderGood(goodId, orderId);
//				int quantityCsOutStorage=outStoreMapper.getAllQuantityCsOutStorage(orderId);
//				if(quantityOrders<quantityCsOutStorage){
//					resultCode=Response.ERROR_CODE;
//					resultInfo.setLength(0);
//					resultInfo.append("发货总数量超过订单商品数量");
//					throw new Exception("发货总数量超过订单商品数量");
//				}else if(quantityOrders>quantityCsOutStorage){
//					
//				}else if(quantityOrders==quantityCsOutStorage){
//					//更新cs_out_storage状态，order状态
//					int temp1=outStoreMapper.changeStatus(3,loginId,outStorageId);
//					if(temp1<1){
//						resultCode=Response.ERROR_CODE;
//						resultInfo.setLength(0);
//						resultInfo.append("系统繁忙，请稍后再试");
//						throw new Exception("系统繁忙，请稍后再试");
//					}
//					int temp2=outStoreMapper.updateOrderStatus(3, orderId);
//					if(temp2<1){
//						resultCode=Response.ERROR_CODE;
//						resultInfo.setLength(0);
//						resultInfo.append("系统繁忙，请稍后再试");
//						throw new Exception("系统繁忙，请稍后再试");
//					}
//				}
//			}
		}else{
			if(terminalNums.length()>0){
				String[] temp=terminalNums.split("\\|");
				for(int i=0;i<temp.length;i++){
					String[] tempChild=temp[i].toString().split("\\_");
					
					int goodId=Integer.parseInt(tempChild[0].toString());
					String portsStr=tempChild[1].toString();
					String checkCodeStr=tempChild[2].toString();
					
					
					
					String[] ports=portsStr.split("\\s+|,|;");
					
					allQuantity=allQuantity+ports.length;
					goodQuantityMap.put(goodId, ports.length);
					
					int payChannelId=outStoreMapper.getPayChannleIdByOrderId(orderId, goodId);
					
					//条件过滤
					for(int j=0;j<ports.length;j++){
						String checkCode="";
						if(!checkCodeStr.equals("无")){
							String[] checkCodes=checkCodeStr.split("\\s+|,|;");
							checkCode=checkCodes[j];
						}
						
						
						List<Map<String, Object>> tempList=outStoreMapper.getTerminalsInfo(ports[j]);
						if(tempList.size()>0){
							if(tempList.get(0).get("customerId").equals("") && tempList.get(0).get("agentId").equals("")){
							}else{
								resultCode=Response.ERROR_CODE;
								resultInfo.setLength(0);
								resultInfo.append("输入的终端号已被使用");
								throw new Exception("输入的终端号已被使用");
							}
							if(!tempList.get(0).get("status").toString().equals(String.valueOf(Terminal.STATUS_NO_OPEN))){
								resultCode=Response.ERROR_CODE;
								resultInfo.setLength(0);
								resultInfo.append("输入的终端号不是未开通状态,不可使用");
								throw new Exception("输入的终端号不是未未开通状态,不可使用");
							}
							
							if(null== tempList.get(0).get("is_return_cs_depots") ||  tempList.get(0).get("is_return_cs_depots").toString().equals("true")){
								resultCode=Response.ERROR_CODE;
								resultInfo.setLength(0);
								resultInfo.append("输入的终端号在售后库存中");
								throw new Exception("输入的终端号在售后库存中");
							}
							
						}else{
							resultCode=Response.ERROR_CODE;
							resultInfo.setLength(0);
							resultInfo.append("输入的终端号不存在");
							throw new Exception("输入的终端号不存在");
						}
						
//						int countTemp=outStoreMapper.getTerminalIsUsed(ports[j]);
//						if(countTemp>0){
//							resultCode=Response.ERROR_CODE;
//							resultInfo.setLength(0);
//							resultInfo.append("输入的终端号已经关联其他订单号");
//							throw new Exception("输入的终端号已经关联其他订单号");
//						}
						
//						Map<String, Object> mapTemp1=outStoreMapper.getInOutStorageTerminalInfo(orderId, goodId, ports[j], outStorageId);
//						
//						int numTemp=Integer.parseInt(mapTemp1.get("num").toString());
//						if(numTemp>0){
//							resultCode=Response.ERROR_CODE;
//							resultInfo.setLength(0);
//							resultInfo.append("");
//							throw new Exception("系统繁忙，请稍后再试");
//						}
						
						//更新terminals表数据,激活码5月12日修改
						if(types==1 || types==2){
							
							int temp2=outStoreMapper.updateTerminals(customerId, null, orderId, ports[j],payChannelId,checkCode);
							int temp3=outStoreMapper.updateGoodsVolumeNumber(goodId);
							if(temp2<1){
								//更新失败
								resultCode=Response.ERROR_CODE;
								resultInfo.setLength(0);
								resultInfo.append("系统繁忙，请稍后再试");
								throw new Exception("系统繁忙，请稍后再试");
							}
							if(temp3<1){
								//更新失败
								resultCode=Response.ERROR_CODE;
								resultInfo.setLength(0);
								resultInfo.append("系统繁忙，请稍后再试");
								throw new Exception("系统繁忙，请稍后再试");
							}
						}else{
							Map<String, Object> mapTemp=outStoreMapper.getAgentIdByCustomerId(agentIdCustomerId);
							if(mapTemp!=null){
								int agentId=Integer.parseInt(mapTemp.get("id").toString());
								int temp2=0;
								if(customerId.equals("")){
									temp2=outStoreMapper.updateTerminals(null, agentId+"", orderId, ports[j],payChannelId,checkCode);
								}else{
									temp2=outStoreMapper.updateTerminals(customerId, agentId+"", orderId, ports[j],payChannelId,checkCode);
								}
									int temp3=outStoreMapper.updateGoodsVolumeNumber(goodId);
								
								if(temp2<1){
									//更新失败
									resultCode=Response.ERROR_CODE;
									resultInfo.setLength(0);
									resultInfo.append("系统繁忙，请稍后再试");
									throw new Exception("系统繁忙，请稍后再试");
								}
								if(temp3<1){
									//更新失败
									resultCode=Response.ERROR_CODE;
									resultInfo.setLength(0);
									resultInfo.append("系统繁忙，请稍后再试");
									throw new Exception("系统繁忙，请稍后再试");
								}
							}
						}
					}
					//条件过滤结束
					
					//新增in_out_storages记录
					int temp4=outStoreMapper.saveTerminalNum(orderId, goodId,portsStr,loginId,ports.length,
							outStorageId);
					if(temp4<1){
						resultCode=Response.ERROR_CODE;
						resultInfo.setLength(0);
						resultInfo.append("系统繁忙，请稍后再试");
						throw new Exception("系统繁忙，请稍后再试");
					}
				}
				//循环结束
				
				//更新cs_out_storage表
				List<Map<String, Object>> inOutStorageInfo=outStoreMapper.getInOutStoragesAllInfo(orderId, outStorageId);
				StringBuffer terminalStr=new StringBuffer("");
				int quantity=0;
				if(null!=inOutStorageInfo){
					for(int i=0;i<inOutStorageInfo.size();i++){
						quantity=quantity+Integer.parseInt(inOutStorageInfo.get(i).get("quantity").toString());
						terminalStr.append(inOutStorageInfo.get(i).get("terminal_number"));
					}
				}
				int temp1=outStoreMapper.updateCsOutStorages(1, quantity, terminalStr.toString(), outStorageId);
				if(temp1<1){
					resultCode=Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("系统繁忙，请稍后再试");
					throw new Exception("系统繁忙，请稍后再试");
				}
				
				//统计所有的cs_out_storages的数量
				//循环good_terminals
				int sign=1;
				if(terminalNums.length()>0){
					String[] temp2=terminalNums.split("\\|");
					for(int i=0;i<temp2.length;i++){
						String[] tempChild=temp2[i].toString().split("\\_");
						
						int goodId=Integer.parseInt(tempChild[0].toString());
						String portsStr=tempChild[1].toString();
						String[] ports=portsStr.split("\\s+|,|;");
						
						allQuantity=allQuantity+ports.length;
						goodQuantityMap.put(goodId, ports.length);
						
						int quantityOrders=outStoreMapper.getQuantityByOrderGood(goodId, orderId);
						//计算对应的good的数量,从in_out_storage
						int quantityInOutStorage=outStoreMapper.getQuantityInOutStorage(orderId, goodId, outStorageId);
						
						if(quantityOrders!=quantityInOutStorage){
							sign=0;
							resultCode=Response.ERROR_CODE;
							resultInfo.setLength(0);
							resultInfo.append("发货总数量与订单商品数量不等");
							throw new Exception("发货总数量与订单商品数量不等");
						}
					}
				}
				if(sign==1){
					//更新cs_out_storage状态，order状态
					int temp3=outStoreMapper.changeStatus(3,loginId,outStorageId);
					if(temp3<1){
						resultCode=Response.ERROR_CODE;
						resultInfo.setLength(0);
						resultInfo.append("系统繁忙，请稍后再试");
						throw new Exception("系统繁忙，请稍后再试");
					}
					int temp4=outStoreMapper.updateOrderStatus(3, orderId);
					if(temp4<1){
						resultCode=Response.ERROR_CODE;
						resultInfo.setLength(0);
						resultInfo.append("系统繁忙，请稍后再试");
						throw new Exception("系统繁忙，请稍后再试");
					}
				}
			}
		}
		
		
		//根据orderId,对物流公司，物流单号进行保存
		int temp1=outStoreMapper.saveWLInfo(wlCompany, wlNum, orderId,outStorageId);
		if(temp1<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("保存物流公司名称，物流单号出错");
			throw new Exception("保存物流公司名称，物流单号出错");
		}
		//保存订单物流信息
		String orderNumber=outStoreMapper.getOrderNumberById(orderId);
		String tempStr=orderNumber+"订单号的商品已发货，物流公司"+wlCompany+"物流单号"+wlNum;
		int temp2=outStoreMapper.saveOrderMarks(loginId, orderId, tempStr);
		if(temp2<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("保存订单物流信息出错");
			throw new Exception("保存订单物流信息出错");
		}
		//执行保存操作记录
		String userName=outStoreMapper.getNameByLoginId(loginId);
		String content=userName+"执行了任务的出库页面【添加出库记录】的操作，操作的记录Id是"+outStorageId;
		int temp=mapper.save(loginId, userName, userType, OperateRecord.TYPES_CHECKOUT, content,outStorageId);
		if(temp<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("记录操作日志出错");
			throw new Exception("记录操作日志出错");
		}
		if(resultCode==Response.SUCCESS_CODE){
			resultCode=Response.SUCCESS_CODE;
			resultInfo.setLength(0);
			resultInfo.append("添加出库记录成功");
		}
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
		mapper.save(loginId, userName, userType, OperateRecord.TYPES_CHECKOUT, content,id);
		
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
					throw new Exception("保存分派信息失败");
				}
			}
		}else{
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("没有选择要分派的记录");
		}
		
		//执行保存操作记录
		String userName=outStoreMapper.getNameByLoginId(loginId);
		String content=userName+"执行了任务的出库页面【分派】的操作，操作的记录Id是"+ids;
		int temp1=mapper.save(loginId, userName, userType, OperateRecord.TYPES_CHECKOUT, content,0);
		if(temp1<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("记录操作日志出错");
			throw new Exception("记录操作日志出错");
		}
		result.put("resultCode", resultCode);
		result.put("resultInfo", resultInfo);
		return result;
	}

	public void subGoodQuantity(String quantities, String goodIds) {
		String[] quantity = quantities.split("-");
		String[] goodId = goodIds.split("-");
		for(int i=0;i<quantity.length;i++){
			goodMapper.updateQuantity(Integer.valueOf(goodId[i]),Integer.valueOf(quantity[i]));
		}
		
	}
}