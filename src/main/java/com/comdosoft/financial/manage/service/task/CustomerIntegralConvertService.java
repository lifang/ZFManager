package com.comdosoft.financial.manage.service.task;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralConvert;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntegralRecord;
import com.comdosoft.financial.manage.domain.zhangfu.CustomerIntentionMark;
import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerIntegralConvertMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerIntegralRecordMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CustomerIntegralConvertService {
	@Resource
	private CustomerIntegralConvertMapper customerIntegralConvertMapper;
	@Resource
	private CustomerIntegralRecordMapper customerIntegralRecordMapper;

	@Autowired
	private CustomerMapper customerMapper;
	
	@Value("${page.size}")
	private Integer pageSize;

	public Page<CustomerIntegralConvert> findPage(Customer customer, int page, Byte status, String keyword) {
		long count = customerIntegralConvertMapper.count(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CustomerIntegralConvert>(new PageRequest(1, pageSize), new ArrayList<CustomerIntegralConvert>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CustomerIntegralConvert> result = customerIntegralConvertMapper.findPage(request, status, keyword,customer.getId());
		Page<CustomerIntegralConvert> integralConvert = new Page<CustomerIntegralConvert>(request, result, count);
		return integralConvert;
	}
	
	public CustomerIntegralConvert findInfoById(Integer id) {
		return customerIntegralConvertMapper.selectByPrimaryKey(id);
	}
	
	public CustomerIntegralConvert updateStatus(Integer csUpdateId, Byte status) {
		CustomerIntegralConvert integralConvert = customerIntegralConvertMapper.selectByPrimaryKey(csUpdateId);
		integralConvert.setStatus(status);
		integralConvert.setUpdatedAt(new Date());
		customerIntegralConvertMapper.updateByPrimaryKey(integralConvert);
		return integralConvert;
	}
//	
//	public void handle(Integer csUpdateId) {
//		updateStatus(csUpdateId, CustomerIntegralConvert.CONVERT_PENDING);
//	}
	
	@Transactional("transactionManager")
	public void cancel(Integer id) {
		updateStatus(id, CustomerIntegralConvert.CONVERT_FILED);
//		customerIntegralConvertMapper.updateStatus(integralConvert);
		 
	}
	
	@Transactional("transactionManager")
	public void finish(Integer id) {
		CustomerIntegralConvert integralConvert = customerIntegralConvertMapper.selectByPrimaryKey(id);
		integralConvert.setStatus(CustomerIntegralConvert.CONVERT_SUCCESS);
		integralConvert.setUpdatedAt(new Date());
		//更新积分兑换记录状态
		customerIntegralConvertMapper.updateByPrimaryKey(integralConvert);
		
		CustomerIntegralRecord cir = new CustomerIntegralRecord();
		cir.setCustomerId(integralConvert.getCustomerId());
		cir.setCreatedAt(new Date());
		cir.setDescription(integralConvert.getApplyNum());
		Integer q = integralConvert.getQuantity();
		Integer customerid = integralConvert.getCustomerId();
		cir.setQuantity(integralConvert.getQuantity()*-1);
		cir.setTargetId(integralConvert.getId());
		cir.setTargetType(CustomerIntegralRecord.TARGET_TYPE_DH);
		cir.setTypes(CustomerIntegralRecord.TYPE_SUBTRACT);
		//增加 积分收支记录 表
		customerIntegralRecordMapper.insert(cir);
		Customer  customer = customerMapper.selectByPrimaryKey(customerid);
		Integer integral = customer.getIntegral();
		BigDecimal bd = new BigDecimal(integral);
		customer.setIntegral(bd.subtract(new BigDecimal(q)).intValue());
		customerMapper.updateInteger(customer);
	
	}
	
 
	
	@Transactional("transactionManager")
	public CustomerIntentionMark createMark(Customer customer, Integer csUpdateId, String content) {
//		handle(csUpdateId);
//		
		CustomerIntentionMark intentionMark = new CustomerIntentionMark();
		intentionMark.setCustomId(customer.getId());
		intentionMark.setContent(content);
		intentionMark.setCreatedAt(new Date());
		intentionMark.setIntentionId(csUpdateId);
		intentionMark.setProcessUserId(customer.getId());
		intentionMark.setProcessUserName(customer.getName());
		customerIntegralConvertMapper.insertMark(intentionMark);
		return intentionMark;
	}
	
	public List<CustomerIntentionMark> findMarksById(Integer id) {
		return customerIntegralConvertMapper.selectMarksById(id);
	}

	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		customerIntegralConvertMapper.dispatchUserByIds(params);
	}
	
	/**
	 * 
	 * @param customer 用户
	 * @param id		操作的id
	 * @param type 操作名称
	 */
	public void record(Customer customer, Integer id,String type) {
		OperateRecord or = new OperateRecord();
		or.setCreatedAt(new Date());
		or.setTypes(OperateRecord.TYPES_POINTS_EXCHANGE);
		or.setOperateUserId(customer.getId());
		or.setOperateUserType(customer.getTypes());
		or.setOperateUserName(customer.getName());
		or.setOperateTargetId(id);//操作id
		or.setContent(customer.getName()+"执行了'积分兑换操作'的"+type+"操作，操作记录的id是"+id);
		customerIntegralConvertMapper.updateStatus(or);
	}
}
