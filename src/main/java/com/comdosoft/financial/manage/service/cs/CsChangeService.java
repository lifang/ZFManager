package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsChangeStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsChangeStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsChangeStatus.HANDLE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.CsChange;
import com.comdosoft.financial.manage.domain.zhangfu.CsChangeMark;
import com.comdosoft.financial.manage.domain.zhangfu.CsOutStorage;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CsChangeMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsChangeMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsOutStorageMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReceiverAddressMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OutStoreMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsChangeService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsChangeMapper csChangeMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsChangeMarkMapper csChangeMarkMapper;
	@Autowired
	private CsReceiverAddressMapper csReceiverAddressMapper;
	@Autowired
	private CsOutStorageMapper csOutStorageMapper;
	@Autowired
	private OutStoreMapper outStoreMapper;
	
	public Page<CsChange> findPage(Customer customer, int page, Byte status, String keyword) {
		long count = csChangeMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsChange>(new PageRequest(1, pageSize), new ArrayList<CsChange>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsChange> result = csChangeMapper.findPageSelective(request,customer.getId(), status, keyword);
		Page<CsChange> csChanges = new Page<CsChange>(request, result, count);
		return csChanges;
	}
	
	public CsChange findInfoById(Integer id) {
		return csChangeMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsChange updateStatus(Integer csChangeId, Byte status) {
		CsChange csChange = csChangeMapper.selectByPrimaryKey(csChangeId);
		csChange.setStatus(status);
		csChange.setUpdatedAt(new Date());
		csChangeMapper.updateByPrimaryKey(csChange);
		return csChange;
	}
	
	public void handle(Integer csChangeId) {
		updateStatus(csChangeId, HANDLE);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csChangeId) {
		CsChange csChange = updateStatus(csChangeId, CANCEL);
		
		Integer terminalId = csChange.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csChangeId) {
		CsChange csChange = updateStatus(csChangeId, FINISH);
		
		Integer terminalId = csChange.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void confirm(Integer csChangeId, CsReceiverAddress csReceiverAddress, Customer customer) {
		csReceiverAddress.setCreatedAt(new Date());
		csReceiverAddressMapper.insert(csReceiverAddress);
		
		CsChange csChange = csChangeMapper.selectInfoByPrimaryKey(csChangeId);
		if (null != csChange) {
			csChange.setReturnAddressId(csReceiverAddress.getId());
			csChange.setStatus(HANDLE);
			csChange.setUpdatedAt(new Date());
			csChangeMapper.updateByPrimaryKey(csChange);
			
//			CsOutStorage csOutStorage = new CsOutStorage();
//			csOutStorage.setCsApplyId(csChange.getApplyNum());
//			csOutStorage.setCsApplyTypes(CsOutStorage.TYPE_CHANGE);
//			csOutStorage.setCreatedAt(new Date());
//			csOutStorage.setUpdatedAt(new Date());
//			csOutStorage.setOrderId(csChange.getTerminal().getOrderId());
//			csOutStorage.setProcessUserId(customer.getId());
//			csOutStorage.setProcessUserName(customer.getName());
//			csOutStorage.setQuantity(1);
//			csOutStorage.setStatus(CsOutStorage.STATUS_WAIT_PROCESS);
//			csOutStorageMapper.insert(csOutStorage);
		}
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csChangeMapper.dispatchUserByIds(params);
	}
	
	public CsChangeMark createMark(Customer customer, Integer csChangeId, String content) {
		CsChangeMark csChangeMark = new CsChangeMark();
		csChangeMark.setCustomId(customer.getId());
		csChangeMark.setCsChangeId(csChangeId);
		csChangeMark.setCreatedAt(new Date());
		csChangeMark.setContent(content);
		csChangeMarkMapper.insert(csChangeMark);
		csChangeMark.setCustomer(customer);
		return csChangeMark;
	}
	
	public List<CsChangeMark> findMarksByCsChangeId(Integer csChangeId) {
		return csChangeMarkMapper.selectByChangeId(csChangeId);
	}
	
	public List<Map<String, Object>> getPayChannelList(){
		return outStoreMapper.getPayChannelList();
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Response changeGood(int csChangeId,String terminalsStr,Customer customer,int payChannelId) throws Exception{
		Response res=new Response();
		int resultCode=1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("退换货成功");
		//验证终端号是否可用
		String temp=terminalsStr;
		List<Map<String, Object>> tempList=outStoreMapper.getTerminalsInfo(temp);
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
				throw new Exception("输入的终端号不是未开通状态,不可使用");
			}
			if(null==tempList.get(0).get("is_return_cs_depots")||tempList.get(0).get("is_return_cs_depots").toString().equals("true")){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("输入的终端号在售后库存中");
				throw new Exception("输入的终端号在售后库存中");
			}
			//重新绑定信息
			int i=outStoreMapper.updateTerminals(customer.getId()+"", null,null, temp, payChannelId,"");
			if(i<1){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("绑定新终端与用户信息出错");
				throw new Exception("绑定新终端与用户信息出错");
			}
			//减少库存
			int goodId=Integer.parseInt(tempList.get(0).get("goodId").toString());
			int j=outStoreMapper.updateGoodsVolumeNumber(goodId);
			if(j<1){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("减少库存出错");
				throw new Exception("减少库存出错");
			}
		}else{
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("输入的终端号不存在");
			throw new Exception("输入的终端号不存在");
		}
		//解绑信息  在任务，售后库存管理  放入售后库存中操作
		if(resultCode==1){
			res.setCode(resultCode);
			res.setMessage(resultInfo.toString());
		}else{
			res.setCode(resultCode);
			res.setMessage(resultInfo.toString());
		}
		return res;
	}
}
