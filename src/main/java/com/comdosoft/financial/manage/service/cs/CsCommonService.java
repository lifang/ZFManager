package com.comdosoft.financial.manage.service.cs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.trades.TradeRecord;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.mapper.trades.TradeRecordMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CustomerMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.OtherRequirementMapper;

@Service
public class CsCommonService {

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private OtherRequirementMapper otherRequirementMapper;
	@Autowired
	private TradeRecordMapper tradeRecordMapper;
	
	@Value("${filePath}")
	private String filePath;

	public List<Customer> findDispatchUsers() {
		return customerMapper.selectDispatchUsers();
	}
	
	public List<OtherRequirement> findRequirementByType(int type) {
	List<OtherRequirement> list = new ArrayList<OtherRequirement>();
	list = otherRequirementMapper.findByType(type);
	for(OtherRequirement other:list){
		other.setTempletFilePath(filePath+other.getTempletFilePath());
	}
		return list;
	}

	public List<TradeRecord> findTradeRecords(String serialNum, Integer status,
			Date start, Date end) {
		return tradeRecordMapper.getTradeRecords(serialNum, status, start, end);
	}
}
