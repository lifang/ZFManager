package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsReturnStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsReturnStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsReturnStatus.HANDLE;

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
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CsRefund;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReceiverAddressMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsRefundMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReturnMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReturnMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsReturnService {

	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsReturnMapper csReturnMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsReturnMarkMapper csReturnMarkMapper;
	@Autowired
	private CsReceiverAddressMapper csReceiverAddressMapper;
	@Autowired
	private CsRefundMapper csRefundMapper;

	public Page<CsReturn> findPage(Customer customer, int page, Byte status,
			String keyword) {
		long count = csReturnMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsReturn>(new PageRequest(1, pageSize),
					new ArrayList<CsReturn>(), count);
		} else {
			if (pageSize <= 0)
				pageSize = 1;
			int totalPage = (int) Math.ceil((double) count / pageSize);
			if (page > totalPage)
				request = new PageRequest(totalPage, pageSize);
		}
		List<CsReturn> result = csReturnMapper.findPageSelective(request,
				customer.getId(), status, keyword);
		Page<CsReturn> csReturns = new Page<CsReturn>(request, result, count);
		return csReturns;
	}

	public CsReturn findInfoById(Integer id) {
		return csReturnMapper.selectInfoByPrimaryKey(id);
	}

	public CsReturn updateStatus(Integer csReturnId, Byte status) {
		CsReturn csReturn = csReturnMapper.selectByPrimaryKey(csReturnId);
		csReturn.setStatus(status);
		csReturn.setUpdatedAt(new Date());
		csReturnMapper.updateByPrimaryKey(csReturn);
		return csReturn;
	}

	public void handle(Integer csReturnId) {
		updateStatus(csReturnId, HANDLE);
	}

	@Transactional("transactionManager")
	public void cancel(Integer csReturnId) {
		CsReturn csReturn = updateStatus(csReturnId, CANCEL);

		Integer terminalId = csReturn.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}

	@Transactional("transactionManager")
	public void finish(Integer csReturnId) {
		CsReturn csReturn = updateStatus(csReturnId, FINISH);

		Integer terminalId = csReturn.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Response createRefund(int csReturnId, Customer customer)
			throws Exception {
		Response res = new Response();
		int resultCode = 1;
		StringBuilder resultInfo = new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("生成退款单成功");

		CsReturn csReturn = csReturnMapper.selectInfoByPrimaryKey(csReturnId);
		CsReceiverAddress csReceiverAddress = csReceiverAddressMapper
				.selectByPrimaryKey(csReturn.getReturnAddressId());
		if (null != csReturn) {
			if (csReturn.getStatus() == CsReturn.STATUS_1
					|| csReturn.getStatus() == CsReturn.STATUS_2) {
				resultCode = Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("退款单已生成，请勿重复生成");
				throw new Exception("退款单已生成，请勿重复生成");
			} else {
				CsRefund csRefund = new CsRefund();
				csRefund.setBankAccount(csReturn.getBankAccount());
				csRefund.setBankName(csReturn.getBankName());
				csRefund.setCreatedAt(new Date());
				csRefund.setPayee(csReceiverAddress.getReceiver());
				csRefund.setPayeePhone(csReceiverAddress.getPhone());
				csRefund.setProcessUserId(customer.getId());
				csRefund.setProcessUserName(customer.getName());
				csRefund.setReturnPrice(csReturn.getReturnPrice());
				csRefund.setStatus((byte) CsRefund.STATIC_1);
				csRefund.setTargetId(csReturnId);
				csRefund.setTargetType((byte) CsRefund.TYPE_1);
				csRefund.setTypes((byte) 1);
				csRefund.setUpdatedAt(new Date());
				csRefund.setApplyNum(new Date().getTime() + "");

				List<Map<String, Object>> temp1 = csRefundMapper
						.getByTargetIdType(csRefund.getTargetId(),
								csRefund.getTargetType());
				if (null != temp1 && temp1.size() > 0) {
					resultCode = Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("已生成退款单");
					throw new Exception("已生成退款单");
				} else {
					int temp = csRefundMapper.insert(csRefund);
					if (temp < 1) {
						resultCode = Response.ERROR_CODE;
						resultInfo.setLength(0);
						resultInfo.append("生成退款单出错");
						throw new Exception("生成退款单出错");
					}
				}
			}

		}
		res.setCode(resultCode);
		res.setMessage(resultInfo.toString());
		return res;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Response confirm(Integer csReturnId,
			CsReceiverAddress csReceiverAddress, Customer customer)
			throws Exception {
		Response res = new Response();
		int resultCode = 1;
		StringBuilder resultInfo = new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("确认退货成功");

		csReceiverAddress.setCreatedAt(new Date());
		Float temp = csReceiverAddress.getReturnMoney() * 100;
		int temp1 = csReceiverAddressMapper.insert(csReceiverAddress);
		if (temp1 < 1) {
			resultCode = Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("插入退货地址出错");
			throw new Exception("插入退货地址出错");
		}
		int temp3 = 0;
		try {
			temp3 = temp.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("输入的退款金额有误，请重新输入");
		}
		CsReturn csReturn = csReturnMapper.selectInfoByPrimaryKey(csReturnId);
		if (null != csReturn) {
			csReturn.setReturnAddressId(csReceiverAddress.getId());
			csReturn.setUpdatedAt(new Date());
			csReturn.setStatus(HANDLE);
			csReturn.setReturnPrice(temp3);

			int temp2 = csReturnMapper.updateByPrimaryKey(csReturn);
			if (temp2 < 1) {
				resultCode = Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("更新退款金额出错");
				throw new Exception("更新退款金额出错");
			}
		} else {
			resultCode = Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("系统异常，请稍后重试");
			throw new Exception("系统异常，请稍后重试");
		}
		res.setCode(resultCode);
		res.setMessage(resultInfo.toString());
		return res;
	}

	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		csReturnMapper.dispatchUserByIds(params);
	}

	public CsReturnMark createMark(Customer customer, Integer csReturnId,
			String content) {
		CsReturnMark csReturnMark = new CsReturnMark();
		csReturnMark.setCustomerId(customer.getId());
		csReturnMark.setCsReturnId(csReturnId);
		csReturnMark.setCreatedAt(new Date());
		csReturnMark.setContent(content);
		csReturnMarkMapper.insert(csReturnMark);
		csReturnMark.setCustomer(customer);
		return csReturnMark;
	}

	public List<CsReturnMark> findMarksByCsReturnId(Integer csReturnId) {
		return csReturnMarkMapper.selectByReturnId(csReturnId);
	}
}
