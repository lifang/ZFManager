package com.comdosoft.financial.manage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.domain.zhangfu.CsCancel;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Merchant;
import com.comdosoft.financial.manage.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.OpeningApplyService;
import com.comdosoft.financial.manage.service.TerminalCSService;
import com.comdosoft.financial.manage.service.TerminalService;
import com.comdosoft.financial.manage.utils.HttpFile;

/**
 * 
 * @author yyb
 *
 */
@Controller
@RequestMapping("terminalCs")
public class TerminalCSController {
	
	private Logger LOG = LoggerFactory.getLogger(TerminalCSController.class);
	@Resource
	private TerminalCSService terminalCSService;
	@Resource
	private OpeningApplyService openingApplyService;
	@Resource
	private TerminalService terminalsService;
	
	@Value("${filePath}")
	private String filePath;
	@Value("${sysFileTerminal}")
	private String sysFileTerminal;
	@Resource
	private CityService cityService;
	
	//1.待处理
	public static final Integer CsChange_STATUS_1 = 1;
	//2.换货中
	public static final Integer CsChange_STATUS_2 = 2;
	//4.处理完成
	public static final Integer CsChange_STATUS_4 = 4;
	//5.已取消
	public static final Integer CsChange_STATUS_5 = 5;
	
	//1.代理处
	public static final Integer CsUpdateInfo_STATUS_1 = 1;
	//2.处理中
	public static final Integer CsUpdateInfo_STATUS_2 = 2;
	//4.处理完成
	public static final Integer CsUpdateInfo_STATUS_4 = 4;
	//5.已取消
	public static final Integer CsUpdateInfo_STATUS_5 = 5;
	
	//1.代理处
	public static final Integer CsCancel_STATUS_1 = 1;
	//2.处理中
	public static final Integer CsCancel_STATUS_2 = 2;
	//4.处理完成
	public static final Integer CsCancel_STATUS_4 = 4;
	//5.已取消
	public static final Integer CsCancel_STATUS_5 = 5;
	
	//1.代理处
	public static final Integer CsLeaseReturn_STATUS_1 = 1;
	//2.处理中
	public static final Integer CsLeaseReturn_STATUS_2 = 2;
	//4.处理完成
	public static final Integer CsLeaseReturn_STATUS_4 = 4;
	//5.已取消
	public static final Integer CsLeaseReturn_STATUS_5 = 5;
	
	//申请状态(1 待初次预审 2待初预审不通过 3二次预审中 4二次预审不通过 5待审核 6审核中7审核失败8审核成功9已取消)
	public static final int OpeningApplie_STATUS_1 = 1;
	public static final int OpeningApplie_STATUS_2 = 2;
	public static final int OpeningApplie_STATUS_3 = 3;
	public static final int OpeningApplie_STATUS_4 = 4;
	public static final int OpeningApplie_STATUS_5 = 5;
	public static final int OpeningApplie_STATUS_6 = 6;
	public static final int OpeningApplie_STATUS_7 = 7;
	public static final int OpeningApplie_STATUS_8 = 8;
	public static final int OpeningApplie_STATUS_9 = 9;
	//申请开通材料对公对私状态(对公)
	public static final int OpeningApplie_TYPES_1 = 1;
	//申请开通材料对公对私状态(对私)
	public static final int OpeningApplie_TYPES_2 = 2;
	
	@RequestMapping(value = "addCostometAddress", method = RequestMethod.POST)
	@ResponseBody
	public Response addAddress(Integer cityId,String receiver,String address,String moblephone,String zipCode,Integer customerId){
		CustomerAddress cusAddress = new CustomerAddress();
	    cusAddress.setReceiver(receiver);
	    cusAddress.setMoblephone(moblephone);
	    cusAddress.setCityId(cityId);
	    cusAddress.setAddress(address);
	    cusAddress.setCustomerId(customerId);
	    cusAddress.setZipCode(zipCode);
	    
	    terminalCSService.addCostometAddress(cusAddress);
	    if(cusAddress.getId()>0){
            return Response.getSuccess("操作成功！");
		}else {
		    return Response.getError("操作失败！");
		}
	}
	
	
	/**
	 * 判断退货申请
	 * window.location.href = "#/terminalReturnGood?terminalId="+$scope.terminalId;
	 * @param maps
	 */
	@RequestMapping(value = "judgeReturn", method = RequestMethod.POST)
	@ResponseBody
	public Response judgeReturn(Integer terminalId) {
		try {
			int count = terminalCSService.JudgeReturn(terminalId,CsUpdateInfo_STATUS_1,CsUpdateInfo_STATUS_2);
			if(count == 0){
				return Response.getSuccess("可以申请！");
			}else{
				return Response.getError("已有相关申请！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	/**
	 * 换货
	 * @param maps
	 * @return
	 * #/terminalExchangeGoods?terminalId="+$scope.terminalId
	 */
	@RequestMapping(value = "judgeChang", method = RequestMethod.POST)
	@ResponseBody
	public Response judgeChang(Integer terminalId) {
		try {
			//判断该终端是否已有未处理完的申请
			int count = terminalCSService.JudgeChangStatus(terminalId,CsChange_STATUS_1,CsChange_STATUS_2);
			if(count == 0){
				return Response.getSuccess("可以申请！");
			}else{
				return Response.getError("已有相关申请！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 判断申请更新资料
	 * @param maps
	 * window.location.href = "#/terminalToUpdate?terminalId="+$scope.terminalId;
	 */
	@RequestMapping(value = "judgeUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Response JudgeUpdate(Integer terminalId) {
		try {
			int count = terminalCSService.judgeUpdateStatus(terminalId,CsUpdateInfo_STATUS_1,CsUpdateInfo_STATUS_2);
			if(count == 0){
				return Response.getSuccess("可以申请！");
			}else{
				return Response.getError("已有相关申请！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	/**
	 * 判断申请注销
	 * window.location.href = "#/terminalCancellation?terminalId="+$scope.terminalId;
	 * @param maps
	 */
	@RequestMapping(value = "judgeRentalReturn", method = RequestMethod.POST)
	@ResponseBody
	public Response judgeRentalReturn(Integer terminalId) {
		try {
			int count = terminalCSService.JudgeRentalReturnStatus(terminalId,CsCancel_STATUS_1,CsCancel_STATUS_2);
			if(count == 0){
				return Response.getSuccess("可以申请！");
			}else{
				return Response.getError("已有相关申请！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	/**
	 * 判断租赁退还申请
	 * window.location.href = "#/terminalRentalReturn?terminalId="+$scope.terminalId;
	 * @param maps
	 */
	@RequestMapping(value = "JudgeLeaseReturn", method = RequestMethod.POST)
	@ResponseBody
	public Response JudgeLeaseReturn(Integer terminalId) {
		try {
			int count = terminalCSService.JudgeLeaseReturn(terminalId,CsLeaseReturn_STATUS_1,CsLeaseReturn_STATUS_2);
			if(count == 0){
				return Response.getSuccess("可以申请！");
			}else{
				return Response.getError("已有相关申请！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	@RequestMapping(value = "getCustomerAddress", method = RequestMethod.POST)
	public String getCustomerAddress(Integer customerId,Model model){
		//获得用户收货地址
		List<Map<Object, Object>> addressList= terminalCSService.getCustomerAddressByCustomerId(customerId);
		model.addAttribute("address", addressList);
		List<City> provinces = cityService.provinces();
		model.addAttribute("provinces",provinces );
		model.addAttribute("addressListLength", addressList.size());
		return "terminal/addressList";
	}
	
	/**
	 * 提交申请售后
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value="submitAgent",method=RequestMethod.POST)
	@ResponseBody
	public Response submitAgent(Integer customerId,Integer agentId,String content,Integer addressId,String terminalsList,String reasons ){
		try{
			List<String> errorlist = new ArrayList<String>();//错误终端号数据
			List<String> successlist = new ArrayList<String>();//正确终端号数据
			
			Map<Object, Object> mapsTemp=new HashMap<Object, Object>();
			mapsTemp.put("addressId", addressId);
			
			CsReceiverAddress csReceiverAddress = terminalCSService.subRepairAddress(mapsTemp);
			
			CsAgent csAgent = new CsAgent();
			csAgent.setCustomerId(customerId);
			csAgent.setAddress(csReceiverAddress.getAddress());
			csAgent.setReason(reasons);
			csAgent.setTerminalsList(terminalsList);
			csAgent.setReciver(csReceiverAddress.getReceiver());
			csAgent.setPhone(csReceiverAddress.getPhone());
			
			String[] arr = csAgent.getTerminalsList().split(",");
			
			for(int i=0;i<arr.length;i++){
				int count = terminalCSService.checkTerminalCode(arr[i],agentId,Terminal.TerminalTYPEID_4,Terminal.TerminalTYPEID_5);
				if(count == 0){
					errorlist.add(arr[i]);
				}else{
					successlist.add(arr[i]);
				}
			}
			if(errorlist.size() == 0){
				//提交数据操作
				csAgent.setStatus(CsAgent.STSTUS_1);
				csAgent.setApplyNum(String.valueOf(System.currentTimeMillis())+csAgent.getCustomerId());
				csAgent.setTerminalsQuantity(arr.length);
				
				terminalCSService.submitAgent(csAgent);//添加售后信息
				mapsTemp.put("agentId", csAgent.getId());
				mapsTemp.put("customerId",(Integer)mapsTemp.get("agentUserId"));
				terminalCSService.addCsAgentMark(mapsTemp);//物流信息
				return Response.getSuccess("提交申请成功！");
			}else{
				//返回错误终端号数组
				Response res=new Response();
				res.setCode(2);
				res.setResult(errorlist);
				return res;
			}
		}catch(Exception e){
			LOG.error("提交申请售后失败！", e);
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 进入终端详情(Web)
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getWebApplyDetails", method = RequestMethod.GET)
	public String getWebApplyDetails(Integer terminalId,Integer type,Model model) {
			Integer types=2;
			// 获得终端详情
			model.addAttribute("applyDetails",
					terminalCSService.getApplyDetails(terminalId));
			// 终端交易类型
			model.addAttribute("rates", terminalCSService.getRate(terminalId));
			//获得租赁信息
			model.addAttribute("tenancy", terminalCSService.getTenancy(terminalId));
			// 追踪记录
			model.addAttribute("trackRecord", terminalCSService.getTrackRecord(terminalId));
			// 开通详情
			model.addAttribute("openingDetails",
					terminalCSService.getOpeningDetails(terminalId));
			//获得模板路径
			model.addAttribute("ReModel", terminalCSService.getModule(terminalId,types));
			//获得用户收货地址
			List<Map<Object, Object>> addressList= terminalCSService.getCustomerAddress(terminalId);
			model.addAttribute("address", addressList);
			model.addAttribute("addressListLength", addressList.size());
			model.addAttribute("openingInfos",
					openingApplyService.getOppinfo(terminalId));
			
			List<City> provinces = cityService.provinces();
			
			model.addAttribute("provinces",provinces );
			//城市级联
			/*map.put("Cities", terminalsService.getCities());*/
			if(type==1){
				return "terminal/terminalUpdate";
			}else if(type==2){
				//退货
				return "terminal/terminalReturn";
			}else if(type==3){
				//换货
				return "terminal/terminalChange";
			}else if(type==4){
				//注销
				return "terminal/terminalCancellation";
			}else if(type==5){
				//租赁退还
				return "terminal/terminalRentalReturn";
			}else if(type==6){
				//代理商售后
				return "terminal/terminalService";
			}else{
				return "";
			}
	}
	@RequestMapping(value = "terminalService", method = RequestMethod.GET)
	public String terminalService(Model model) {
		//代理商售后
		return "terminal/terminalService";
	}
	
	/**
	 * 租赁退还
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subLeaseReturn", method = RequestMethod.POST)
	@ResponseBody
	public Response subLeaseReturn(Integer terminalsId,Integer customerId,Integer status,String templeteInfoXml,
			String reason,String relationPeople,String relationPhone,Byte type,Integer modelStatus,Integer orderTypes) {
		try {
			Map<Object, Object> maps=new HashMap<Object, Object>();
			
			if(modelStatus == null){
				maps.put("csCencelId", null);
			}else if(modelStatus == 1){
				CsCancel csCancel =new CsCancel();
				csCancel.setTerminalId(terminalsId);
				csCancel.setStatus(status);
				csCancel.setTempleteInfoXml(templeteInfoXml);
				csCancel.setTypes(type);
				csCancel.setCustomerId(customerId);
				//先注销
				terminalCSService.subRentalReturn(csCancel);
				maps.put("csCencelId", csCancel.getId());
			}
			//退还
			terminalCSService.subLeaseReturn(maps);
			return Response.getSuccess("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	
	
	/**
	 * 找回POS机密码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "Encryption", method = RequestMethod.POST)
	@ResponseBody
	public Response Encryption(Integer terminalId) {
		try {
			String  password= terminalCSService.findPassword(terminalId) == null?null:
				terminalCSService.findPassword(terminalId);
			String pass = "该终端未设置密码！";
			if(password != null){
				/*pass = SysUtils.Decrypt(
						password,passPath);*/
				pass = password;
			}
			return Response.getSuccess(pass);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败!");
		}
	}
	
	/**
	 * 提交换货申请
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subChange", method = RequestMethod.POST)
	@ResponseBody
	public Response subChange(Integer terminalsId,Integer customerId,Integer status,String templeteInfoXml,
			String reason,Byte type,Integer modelStatus,Integer addressId) {
		try {
			Map<Object, Object> maps=new HashMap<Object, Object>();
			
			maps.put("terminalsId",terminalsId );
			maps.put("customerId", customerId);
			maps.put("status",status );
			maps.put("templeteInfoXml", templeteInfoXml);
			maps.put("reason", reason);
			maps.put("type", type);
			maps.put("modelStatus", modelStatus);
			maps.put("addressId",addressId );
			
			if(modelStatus == null){
				maps.put("csCencelId", null);
			}else if(modelStatus == 1){
				CsCancel csCancel =new CsCancel();
				csCancel.setTerminalId(terminalsId);
				csCancel.setStatus(status);
				csCancel.setTempleteInfoXml(templeteInfoXml);
				csCancel.setTypes(type);
				csCancel.setCustomerId(customerId);
				//先注销
				terminalCSService.subRentalReturn(csCancel);
				maps.put("csCencelId", csCancel.getId());
			}
			CsReceiverAddress csReceiverAddress =new CsReceiverAddress();
			//先添加换货地址表
			maps.put("templeteInfoXml", templeteInfoXml);
			csReceiverAddress = terminalCSService.subRepairAddress(maps);
			
			maps.put("receiveAddressId", csReceiverAddress.getId());
			maps.put("status", CsChange_STATUS_1);
			terminalCSService.subChange(maps);
			return Response.getSuccess("操作成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**(以下web)
	 * 申请更新资料
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "getApplyToUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Response getApplyToUpdate(Integer terminalsId,Integer customerId,Integer status,String templeteInfoXml) {
		try {
			Map<Object, Object> maps=new HashMap<Object, Object>();
			maps.put("templeteInfoXml", templeteInfoXml);
			maps.put("status", CsUpdateInfo_STATUS_1);
			maps.put("terminalsId",terminalsId);
			maps.put("customerId", customerId);
			terminalCSService.subToUpdate(maps);
			return Response.getSuccess("更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	/**
	 * 提交注销
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subRentalReturn", method = RequestMethod.POST)
	public Response subRentalReturn(Integer terminalsId,Integer customerId,Integer status,String templeteInfoXml,Byte type) {
		try {
			CsCancel csCancel =new CsCancel();
			csCancel.setTerminalId(terminalsId);
			csCancel.setStatus(CsCancel_STATUS_1);
			csCancel.setTempleteInfoXml(templeteInfoXml);
			csCancel.setTypes(type);
			csCancel.setCustomerId(customerId);
			//注销
			terminalCSService.subRentalReturn(csCancel);
			return Response.getSuccess("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	/**
	 * 提交退货申请
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subReturn", method = RequestMethod.POST)
	@ResponseBody
	public Response subReturn(Integer terminalsId,Integer customerId,Integer status,String templeteInfoXml,
			String reason,String relationPeople,String relationPhone,Integer modelStatus,Integer type) {
		try {
			Map<Object, Object> maps=new HashMap<Object, Object>();
			CustomerAddress cusAddress = new CustomerAddress();
		    cusAddress.setReceiver(relationPeople);
		    cusAddress.setMoblephone(relationPhone);
		    cusAddress.setCityId(customerId);
			if(modelStatus==null){
				maps.put("csCencelId", null);
			}else if(modelStatus==1){
				maps.put("templeteInfoXml", templeteInfoXml);
				maps.put("status", CsUpdateInfo_STATUS_1);
				maps.put("terminalsId",terminalsId);
				maps.put("customerId", customerId);
				maps.put("reason",reason);
				maps.put("relationPeople",relationPeople);
				maps.put("relationPhone",relationPhone);
				maps.put("type", type);
				
				CsCancel csCancel =new CsCancel();
				csCancel.setTerminalId((Integer)maps.get("terminalsId"));
				csCancel.setStatus((Integer)maps.get("status"));
				csCancel.setTempleteInfoXml(maps.get("templeteInfoXml").toString());
				csCancel.setTypes((Byte)maps.get("type"));
				csCancel.setCustomerId((Integer)maps.get("customerId"));
				//先注销
				terminalCSService.subRentalReturn(csCancel);
				maps.put("csCencelId", csCancel.getId());
			}
			terminalCSService.addCostometAddress(cusAddress);
			if(cusAddress.getId()>0){
			    maps.put("returnAddressId",cusAddress.getId());
			    terminalCSService.subReturn(maps);
	            return Response.getSuccess("操作成功！");
			}else {
			    return Response.getError("操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	
	
	
	/**
	 * 添加申请信息
	 * 
	 * @param paramMap
	 */
	@RequestMapping(value = "addOpeningApply", method = RequestMethod.POST)
	public Response addOpeningApply(@RequestBody List<Map<String, Object>> paramMap) {
		try {
			OpeningApplie openingApplie = new OpeningApplie();
			String openingAppliesId = null;
			Integer terminalId = null;
			String key = null;
			Object value = null;
			Integer types = null;
			Integer openingRequirementId = null;
			Integer targetId =null;
			int y = 0;
			for (Map<String, Object> map : paramMap) {
				if (y == 0) {
					terminalId = (Integer)map.get("terminalId");
					//获得开通基本资料
					openingApplie.setTerminalId((Integer) map
							.get("terminalId"));
					openingApplie.setApplyCustomerId((Integer) map
							.get("applyCustomerId"));
					openingApplie.setTypes((Integer) map
							.get("publicPrivateStatus"));
					openingApplie.setMerchantId((Integer) map
							.get("merchantId"));
					openingApplie.setMerchantName((String) map
							.get("merchantName"));
					openingApplie.setSex((Integer) map
							.get("sex"));
					openingApplie.setBirthday( new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("birthday")));
					openingApplie.setCardId((String) map
							.get("cardId"));
					openingApplie.setPhone((String) map
							.get("phone"));
					openingApplie.setEmail((String) map
							.get("email"));
					openingApplie.setCityId((Integer) map
							.get("cityId"));
					openingApplie.setName((String) map
							.get("name"));
					openingApplie.setPayChannelId((Integer) map
							.get("channel"));
					openingApplie.setAccountBankNum((String) map
							.get("bankNum"));
					openingApplie.setBillingCydeId((Integer) map
							.get("billingId"));
					openingApplie.setAccountBankName((String) map
							.get("bankName"));
					openingApplie.setAccountBankCode((String) map
							.get("bankCode"));
					openingApplie.setTaxRegisteredNo((String) map
							.get("registeredNo"));
					openingApplie.setOrganizationCodeNo((String) map
							.get("organizationNo"));
					openingApplie.setBankName((String) map
							.get("bank_name"));
					if((Integer) map.get("needPreliminaryVerify") == 0){
						openingApplie.setStatus(OpeningApplie_STATUS_5);
					}
					if((Integer) map.get("needPreliminaryVerify") == 1){
						openingApplie.setStatus(OpeningApplie_STATUS_1);
					}
					//判断该商户是否存在
					Map<Object, Object> countMap =  openingApplyService.getMerchantsIsNo((String) map.get("merchantName"),(String) map.get("phone"));
					//添加商户
					Merchant merchant = new Merchant();
					merchant.setLegalPersonName((String) map
							.get("name"));
					merchant.setLegalPersonCardId((String) map
							.get("cardId"));
					merchant.setTitle((String) map
							.get("merchantName"));
					merchant.setTaxRegisteredNo((String) map
							.get("registeredNo"));
					merchant.setOrganizationCodeNo((String) map
							.get("organizationNo"));
					merchant.setAccountBankNum((String) map
							.get("bankNum"));
					merchant.setCustomerId((Integer) map
							.get("applyCustomerId"));
					merchant.setCityId((Integer)map.get("cityId"));
					merchant.setPhone((String) map
						.get("phone"));
					if(countMap == null){
						openingApplyService.addMerchan(merchant);
						//获得添加后商户Id
						//terminalId = merchant.getId();
						openingApplie.setMerchantId(merchant.getId());
					}else if(countMap !=null){
						merchant.setId((Integer)countMap.get("id"));
						openingApplyService.updateMerchan(merchant);
						openingApplie.setMerchantId((Integer)countMap.get("id"));
					}
					//为终端表关联对应的商户id和通道周期ID 
					openingApplyService.updateterminal(openingApplie.getMerchantId(),terminalId,(Integer) map
							.get("billingId"));
					
					//判断该终端是申请开通还是从新申请
					if(terminalCSService.judgeOpen(terminalId) != 0){
						openingAppliesId = String.valueOf(openingApplyService
								.getApplyesId(terminalId));
						// 删除旧数据
						openingApplyService.deleteOpeningInfos(Integer
								.valueOf(openingAppliesId));
						//修改申请表中的基本资料
						openingApplie.setId(Integer.parseInt(openingAppliesId));
						openingApplyService.updateApply(openingApplie);
						
					}else {
						openingApplyService.addOpeningApply(openingApplie);
						openingAppliesId = String
								.valueOf(openingApplie.getId());
					}
				} else {
							key = (String) map.get("key");
							types = (Integer) map.get("types");
							if(types == 2){
								value =  map.get("value").toString().substring(filePath.length());
							}else{
								value =  map.get("value");
							}
							openingRequirementId = (Integer) map.get("openingRequirementId");
							targetId =(Integer) map.get("targetId");
					openingApplyService.addApply(key, value,types, openingAppliesId,openingRequirementId,targetId);
				}
				y++;
			}
			return Response.getSuccess("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	/**
     * 终端更新资料上传
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "upload/tempUpdateFile/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Response tempUpdateFile(@PathVariable(value="id") int id,@RequestParam(value = "updatefile") MultipartFile updatefile, HttpServletRequest request) {
    	try {
        	String joinpath = HttpFile.upload(updatefile, sysFileTerminal+id+"/update/");
        	if("上传失败".equals(joinpath) || "同步上传失败".equals(joinpath))
        		return Response.getError(joinpath);
        		return Response.getSuccess(joinpath);
    	} catch (Exception e) {
    		return Response.getError("请求失败！");
    	}
    }
    
}
