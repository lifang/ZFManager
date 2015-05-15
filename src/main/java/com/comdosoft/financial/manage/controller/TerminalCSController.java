package com.comdosoft.financial.manage.controller;

import java.text.SimpleDateFormat;
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
import com.comdosoft.financial.manage.domain.zhangfu.CsCancel;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Merchant;
import com.comdosoft.financial.manage.domain.zhangfu.OpeningApplie;
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
	@Value("${filePath}")
	private String filePath;
	@Value("${sysFileTerminal}")
	private String sysFileTerminal;
	
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
	
	/**
	 * 换货
	 * @param maps
	 * @return
	 * #/terminalExchangeGoods?terminalId="+$scope.terminalId
	 */
	@RequestMapping(value = "judgeChang", method = RequestMethod.POST)
	public Response judgeChang(@RequestBody Map<Object, Object> maps) {
		try {
			//判断该终端是否已有未处理完的申请
			int count = terminalCSService.JudgeChangStatus((Integer)maps.get("terminalid"),CsChange_STATUS_1,CsChange_STATUS_2);
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
	 * 判断退货申请
	 * window.location.href = "#/terminalReturnGood?terminalId="+$scope.terminalId;
	 * @param maps
	 */
	@RequestMapping(value = "judgeReturn", method = RequestMethod.POST)
	public Response judgeReturn(@RequestBody Map<Object, Object> maps) {
		try {
			int count = terminalCSService.JudgeReturn((Integer)maps.get("terminalid"),CsUpdateInfo_STATUS_1,CsUpdateInfo_STATUS_2);
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
	 * 进入终端详情(Web)
	 * 
	 * @param id
	 */
	@RequestMapping(value = "getWebApplyDetails", method = RequestMethod.POST)
	public String getWebApplyDetails(@RequestBody Map<Object, Object> maps,Model model) {
		
			Map<Object, Object> map = new HashMap<Object, Object>();
			// 获得终端详情
			model.addAttribute("applyDetails",
					terminalCSService.getApplyDetails((Integer)maps.get("terminalsId")));
			// 终端交易类型
			model.addAttribute("rates", terminalCSService.getRate((Integer)maps.get("terminalsId")));
			//获得租赁信息
			model.addAttribute("tenancy", terminalCSService.getTenancy((Integer)maps.get("terminalsId")));
			// 追踪记录
			model.addAttribute("trackRecord", terminalCSService.getTrackRecord((Integer)maps.get("terminalsId")));
			// 开通详情
			model.addAttribute("openingDetails",
					terminalCSService.getOpeningDetails((Integer)maps.get("terminalsId")));
			//获得模板路径
			model.addAttribute("ReModel", terminalCSService.getModule((Integer)maps.get("terminalsId"),(Integer)maps.get("types")));
			//获得用户收货地址
			model.addAttribute("address", terminalCSService.getCustomerAddress((Integer)maps.get("customerId")));
			model.addAttribute("openingInfos",
					openingApplyService.getOppinfo((Integer)maps.get("terminalsId")));
			//城市级联
			/*map.put("Cities", terminalsService.getCities());*/
			return "common/terminal/terminalUpdate";
	}
	
	/**
	 * 判断申请注销
	 * window.location.href = "#/terminalCancellation?terminalId="+$scope.terminalId;
	 * @param maps
	 */
	@RequestMapping(value = "judgeRentalReturn", method = RequestMethod.POST)
	public Response judgeRentalReturn(@RequestBody Map<Object, Object> maps) {
		try {
			int count = terminalCSService.JudgeRentalReturnStatus((Integer)maps.get("terminalid"),CsCancel_STATUS_1,CsCancel_STATUS_2);
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
	 * 找回POS机密码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "Encryption", method = RequestMethod.POST)
	public Response Encryption(@RequestBody Map<String, Object> map) {
		try {
			String  password= terminalCSService.findPassword((Integer)map.get("terminalid")) == null?null:
				terminalCSService.findPassword((Integer)map.get("terminalid"));
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
	 * 判断租赁退还申请
	 * window.location.href = "#/terminalRentalReturn?terminalId="+$scope.terminalId;
	 * @param maps
	 */
	@RequestMapping(value = "JudgeLeaseReturn", method = RequestMethod.POST)
	public Response JudgeLeaseReturn(@RequestBody Map<Object, Object> maps) {
		try {
			int count = terminalCSService.JudgeLeaseReturn((Integer)maps.get("terminalid"),CsLeaseReturn_STATUS_1,CsLeaseReturn_STATUS_2);
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
	 * 提交换货申请
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subChange", method = RequestMethod.POST)
	public Response subChange(@RequestBody Map<Object, Object> maps) {
		try {
			if(maps.get("modelStatus") == null){
				maps.put("csCencelId", null);
			}else if(Integer.parseInt((String)maps.get("modelStatus")) == 1){
				CsCancel csCancel =new CsCancel();
				csCancel.setTerminalId((Integer)maps.get("terminalsId"));
				csCancel.setStatus((Integer)maps.get("status"));
				csCancel.setTempleteInfoXml((maps.get("templeteInfoXml").toString()));
				csCancel.setTypes((Byte)maps.get("type"));
				csCancel.setCustomerId((Integer)maps.get("customerId"));
				//先注销
				terminalCSService.subRentalReturn(csCancel);
				maps.put("csCencelId", csCancel.getId());
			}
			CsReceiverAddress csReceiverAddress =new CsReceiverAddress();
			//先添加换货地址表
			maps.put("templeteInfoXml", maps.get("templeteInfoXml").toString());
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
	
	/**
	 * 提交退货申请
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subReturn", method = RequestMethod.POST)
	public Response subReturn(@RequestBody Map<Object, Object> maps) {
		try {
			if(maps.get("modelStatus") == null){
				maps.put("csCencelId", null);
			}else if(Integer.parseInt((String)maps.get("modelStatus")) == 1){
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
			terminalCSService.subReturn(maps);
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
	public Response getApplyToUpdate(@RequestBody Map<Object, Object> maps) {
		try {
			maps.put("templeteInfoXml", maps.get("templeteInfoXml").toString());
			maps.put("status", CsUpdateInfo_STATUS_1);
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
	public Response subRentalReturn(@RequestBody Map<Object, Object> maps) {
		try {
			CsCancel csCancel =new CsCancel();
			csCancel.setTerminalId((Integer)maps.get("terminalId"));
			csCancel.setStatus(CsCancel_STATUS_1);
			csCancel.setTempleteInfoXml(maps.get("templeteInfoXml").toString());
			csCancel.setTypes((Byte)maps.get("type"));
			csCancel.setCustomerId((Integer)maps.get("customerId"));
			//注销
			terminalCSService.subRentalReturn(csCancel);
			return Response.getSuccess("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("请求失败！");
		}
	}
	
	/**
	 * 提交退还申请
	 * 
	 * @param maps
	 */
	@RequestMapping(value = "subLeaseReturn", method = RequestMethod.POST)
	public Response subLeaseReturn(@RequestBody Map<Object, Object> maps) {
		try {
			if(maps.get("modelStatus") == null){
				maps.put("csCencelId", null);
			}else if(Integer.parseInt((String)maps.get("modelStatus")) == 1){
				CsCancel csCancel =new CsCancel();
				csCancel.setTerminalId((Integer)maps.get("terminalId"));
				csCancel.setStatus((Integer)maps.get("status"));
				csCancel.setTempleteInfoXml((maps.get("templeteInfoXml").toString()));
				csCancel.setTypes((Byte)maps.get("type"));
				csCancel.setCustomerId((Integer)maps.get("customerId"));
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
