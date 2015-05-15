package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CsCancel;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerAddress;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalCSMapper;

@Service
public class TerminalCSService {
	
	@Autowired
    private TerminalCSMapper terminalCSMapper;
	@Value("${filePath}")
	private String filePath;
	/**
	 * 换货申请判断
	 * yyb
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int JudgeChangStatus(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalCSMapper.JudgeChangStatus(map);
	}
	/**
	 * 维修申请判断
	 * yyb
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int JudgeReturn(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalCSMapper.JudgeReturn(map);
	}
	/**
	 * 跟新申请判断
	 * 
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int judgeUpdateStatus(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalCSMapper.JudgeUpdateStatus(map);
	}
	
	/**
	 * 注销申请判断
	 * 
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int JudgeRentalReturnStatus(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalCSMapper.JudgeRentalReturnStatus(map);
	}
	/**
	 * POS找回密码
	 * @param id
	 * @return
	 */
	public String findPassword(Integer id){
		return terminalCSMapper.findPassword(id);
	}
	/**
	 * 租赁申请判断
	 * @param terminalId
	 * @param statusa
	 * @param statusb
	 * @return
	 */
	public int JudgeLeaseReturn(Integer terminalId,Integer statusa,Integer statusb) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalId", terminalId);
		map.put("statusa", statusa);
		map.put("statusb", statusb);
		return terminalCSMapper.JudgeLeaseReturn(map);
	}
	/**
	 * 提交注销
	 * @param map
	 */
	public void subRentalReturn(CsCancel csCancel){
		terminalCSMapper.subRentalReturn(csCancel);
	}
	/**
	 *申请维修添加地址
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public CsReceiverAddress subRepairAddress(Map<Object, Object> map) {
		CsReceiverAddress csReceiverAddress = new CsReceiverAddress();
		csReceiverAddress.setAddress((String)map.get("address"));
		csReceiverAddress.setPhone((String)map.get("phone"));
		csReceiverAddress.setZipCode((String)map.get("zipCode"));
		csReceiverAddress.setReceiver((String)map.get("receiver"));
		terminalCSMapper.subRepairAddress(csReceiverAddress);
		return csReceiverAddress;
	}
	/**
	 *添加申请维修
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public void subChange(Map<Object, Object> map) {
		terminalCSMapper.subChange(map);
	}
	/**
	 *添加退货
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public void subReturn(Map<Object, Object> map) {
		terminalCSMapper.subReturn(map);
	}
	/**
	 * 提交更新申请
	 * @param map
	 */
	public void subToUpdate(Map<Object, Object> map){
		terminalCSMapper.subToUpdate(map);
	}
	/**
	 * 提交退还申请
	 * @param map
	 */
	public void subLeaseReturn(Map<Object, Object> map){
		terminalCSMapper.subLeaseReturn(map);
	}
	/**
     * 判断该终端是否开通
     * 
     * @param map
     * @return
     */
    public int judgeOpen(int terminalId){
    	return terminalCSMapper.judgeOpen(terminalId);
    }
    /**
	 * 获得终端详情
	 * @param id
	 * @return
	 */
	public Map<Object, Object> getApplyDetails(Integer id){
		return terminalCSMapper.getApplyDetails(id);
	}
	/**
	 * 获得该终端交易类型详情
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getRate(Integer id){
		return terminalCSMapper.getRate(id);
	}
	/**
	 * 获得租赁信息
	 * @param id
	 * @return
	 */
	public Map<String, String> getTenancy(Integer id){
		return terminalCSMapper.getTenancy(id);
	}
	/**
	 * 获得追踪记录
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> getTrackRecord(Integer id){
		return terminalCSMapper.getTrackRecord(id);
	}
	/**
	 * 开通详情
	 * @param id
	 * @return
	 */
	public List<Map<Object, Object>> getOpeningDetails(Integer id){
         
         List<Map<Object, Object>> list = new ArrayList<Map<Object,Object>>();
         list = terminalCSMapper.getOpeningDetails(id);
         for(int i=0;i<list.size();i++){
        	 if(list.get(i) !=null){
        		 if((Integer)list.get(i).get("types") == 2){
            		 list.get(i).put("value",filePath+list.get(i).get("value").toString());
            	 }else{
            		 list.get(i).put("value",list.get(i).get("value").toString());
            	 }
        	 }
         }
		return list;
	}
	
	/**
	 * 获得注销模板
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>>  getModule(Integer  terminalsId,int type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("terminalsId",terminalsId);
		map.put("type", type);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = terminalCSMapper.getModule(map);
		for(int i=0;i<list.size();i++){
			list.get(i).put("templet_file_path",filePath+list.get(i).get("templet_file_path").toString());
		}
		return list;
	}
	/**
	 * 获得该用户收获地址
	 * 
	 * @param id
	 * @param offSetPage
	 * @param pageSize
	 * @return
	 */
	public List<Map<Object, Object>> getCustomerAddress(Integer id) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("customerId", id);
		map.put("status", CustomerAddress.STATUS_1);
		return terminalCSMapper.getCustomerAddress(map);
	}
}
