package com.comdosoft.financial.manage.service.cs;

import static com.comdosoft.financial.manage.service.cs.CsConstants.CsLeaseStatus.CANCEL;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsLeaseStatus.FINISH;
import static com.comdosoft.financial.manage.service.cs.CsConstants.CsLeaseStatus.HANDLE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.trades.TradeRecord;
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.CsReceiverAddress;
import com.comdosoft.financial.manage.domain.zhangfu.CsRefund;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.mapper.zhangfu.CsLeaseReturnMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsLeaseReturnMarkMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsReceiverAddressMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.CsRefundMapper;
import com.comdosoft.financial.manage.mapper.zhangfu.TerminalMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CsLeaseService {
	
	@Value("${page.size}")
	private Integer pageSize;

	@Autowired
	private CsCommonService csCommonService;
	@Autowired
	private CsLeaseReturnMapper csLeaseReturnMapper;
	@Autowired
	private TerminalMapper terminalMapper;
	@Autowired
	private CsLeaseReturnMarkMapper csleaLeaseReturnMarkMapper;
	@Autowired
	private CsReceiverAddressMapper csReceiverAddressMapper;
	@Autowired
	private CsRefundMapper csRefundMapper;
	
	public Page<CsLeaseReturn> findPage(Customer customer, int page, Integer status, String keyword) {
		long count = csLeaseReturnMapper.countSelective(status, keyword);
		PageRequest request = new PageRequest(page, pageSize);
		if (count == 0) {
			return new Page<CsLeaseReturn>(new PageRequest(1, pageSize), new ArrayList<CsLeaseReturn>(), count);
		} else {
			if (pageSize <= 0) pageSize = 1;
			int totalPage = (int)Math.ceil((double) count / pageSize);
			if (page > totalPage) request = new PageRequest(totalPage, pageSize);
		}
		List<CsLeaseReturn> result = csLeaseReturnMapper.findPageSelective(request,customer.getId(), status, keyword);
		Page<CsLeaseReturn> csLeases = new Page<CsLeaseReturn>(request, result, count);
		return csLeases;
	}
	
	public CsLeaseReturn findInfoById(Integer id) {
		return csLeaseReturnMapper.selectInfoByPrimaryKey(id);
	}
	
	public CsLeaseReturn updateStatus(Integer csLeaseId, Integer status) {
		CsLeaseReturn csLease = csLeaseReturnMapper.selectByPrimaryKey(csLeaseId);
		csLease.setStatus(status);
		csLease.setUpdatedAt(new Date());
		csLeaseReturnMapper.updateByPrimaryKey(csLease);
		return csLease;
	}
	
	public void handle(Integer csLeaseId) {
		updateStatus(csLeaseId, HANDLE);
	}
	
	@Transactional("transactionManager")
	public void cancel(Integer csLeaseId) {
		CsLeaseReturn csLease = updateStatus(csLeaseId, CANCEL);
		
		Integer terminalId = csLease.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional("transactionManager")
	public void finish(Integer csLeaseId) {
		CsLeaseReturn csLease = updateStatus(csLeaseId, FINISH);
		
		Integer terminalId = csLease.getTerminalId();
		if (null != terminalId) {
			terminalMapper.closeCsReturnDepotsById(terminalId);
		}
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Response createRefund(int csLeaseId,Customer customer) throws Exception{
		Response res=new Response();
		int resultCode=1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("生成退款单成功");
		
		CsLeaseReturn csLease =csLeaseReturnMapper.selectInfoByPrimaryKey(csLeaseId);
		CsReceiverAddress csReceiverAddress=csReceiverAddressMapper.selectByPrimaryKey(csLease.getReturnAddressId());
		  if (null != csLease) {
		   CsRefund csRefund = new CsRefund();
		   csRefund.setBankAccount(null);
		   csRefund.setBankName(null);
		   csRefund.setCreatedAt(new Date());
		   csRefund.setPayee(csReceiverAddress.getReceiver());
		   csRefund.setPayeePhone(csReceiverAddress.getPhone());
		   csRefund.setProcessUserId(customer.getId());
		   csRefund.setProcessUserName(customer.getName());
		   csRefund.setReturnPrice(csLease.getReturnPrice());
		   csRefund.setStatus((byte)CsRefund.STATIC_1);
		   csRefund.setTargetId(csLeaseId);
		   csRefund.setTargetType((byte)CsRefund.TYPE_2);
		   csRefund.setTypes((byte)1);
		   csRefund.setUpdatedAt(new Date());
		   csRefund.setApplyNum(new Date().getTime()+"");
		   
		   List<Map<String, Object>> temp1=csRefundMapper.getByTargetIdType(csRefund.getTargetId(), csRefund.getTargetType());
		   if(null!=temp1 && temp1.size()>0){
			   resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("已生成退款单");
				throw new Exception("已生成退款单");
		   }else{
			   int temp=csRefundMapper.insert(csRefund);
			   if(temp<1){
				   resultCode=Response.ERROR_CODE;
					resultInfo.setLength(0);
					resultInfo.append("生成退款单出错");
					throw new Exception("生成退款单出错");
			   }
		   }
		  }
		  res.setCode(resultCode);
			res.setMessage(resultInfo.toString());
			return res;
	}
	
	@Transactional(value="transactionManager",propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Response confirm(Integer csLeaseId, CsReceiverAddress csReceiverAddress, Customer customer) throws Exception {
		Response res=new Response();
		int resultCode=1;
		StringBuilder resultInfo=new StringBuilder();
		resultInfo.setLength(0);
		resultInfo.append("确认退货成功");
		
		csReceiverAddress.setCreatedAt(new Date());
		Float temp=csReceiverAddress.getReturnMoney()*100;
		int temp1=csReceiverAddressMapper.insert(csReceiverAddress);
		if(temp1<1){
			resultCode=Response.ERROR_CODE;
			resultInfo.setLength(0);
			resultInfo.append("插入退货地址出错");
			throw new Exception("插入退货地址出错");
		}
		int temp3=0;
		try {
			temp3=temp.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("输入的退款金额有误，请重新输入");
		}
		CsLeaseReturn csLease =csLeaseReturnMapper.selectInfoByPrimaryKey(csLeaseId);
		if (null != csLease) {
			csLease.setReturnAddressId(csReceiverAddress.getId());
			csLease.setUpdatedAt(new Date());
			csLease.setStatus(HANDLE);
			csLease.setReturnPrice(temp3);
		   
		    int temp2=csLeaseReturnMapper.updateByPrimaryKey(csLease);
			if(temp2<1){
				resultCode=Response.ERROR_CODE;
				resultInfo.setLength(0);
				resultInfo.append("更新退款金额出错");
				throw new Exception("更新退款金额出错");
			}
		}else{
			resultCode=Response.ERROR_CODE;
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
		csLeaseReturnMapper.dispatchUserByIds(params);
	}
	
	public CsLeaseReturnMark createMark(Customer customer, Integer csLeaseId, String content) {
		CsLeaseReturnMark csLeaseMark = new CsLeaseReturnMark();
		csLeaseMark.setCustomerId(customer.getId());
		csLeaseMark.setCsLeaseReturnId(csLeaseId);
		csLeaseMark.setCreatedAt(new Date());
		csLeaseMark.setContent(content);
		csleaLeaseReturnMarkMapper.insert(csLeaseMark);
		csLeaseMark.setCustomer(customer);
		return csLeaseMark;
	}
	
	public List<CsLeaseReturnMark> findMarksByCsLeaseReturnId(Integer csLeaseId) {
		return csleaLeaseReturnMarkMapper.selectBycsLeaseId(csLeaseId);
	}
	
	/**
	 * 计算租赁退还相关数据
	 */
	public int calculateLease(Model model, CsLeaseReturn csLease, boolean isReturnPrice) {
		if (null == csLease.getGood() || null == csLease.getOrder()) return 0;
		
		// 最短租赁时长
		int minLeaseMonth = toInt(csLease.getGood().getLeaseTime());
		// 最长租赁时长
		int maxLeaseMonth = toInt(csLease.getGood().getReturnTime());
		// 月租金
		int rentPerMonth = toInt(csLease.getGood().getLeasePrice());
		// 押金
		int deposit = toInt(csLease.getGood().getLeaseDeposit());
		
		// 租赁开始时间为相应订单的更新时间
		Date leaseStart = csLease.getOrder().getUpdatedAt();
		// 租赁结束时间为该条申请的创建时间
		Date leaseEnd = csLease.getCreatedAt();
		// 租赁时长(天) 
		int daysApart = getDaysApart(leaseStart, leaseEnd);
		if(daysApart<0){
			daysApart = 0;
		}
		// 租赁时长(月)
		int monthsApart = getMonthsApart(leaseStart, leaseEnd, true);
		
		// 租金 = 租赁时长(月) * 月租金
		int rentTotal;
		// 退款金额 = 押金 - 租金
		int refundAmount;
		
		// 租赁时长必须在[最短租赁时长, 最长租赁时长]之间
		// 如果租赁时长超过最长租赁时长, 则退款金额为0
		if (monthsApart > maxLeaseMonth) {
			monthsApart = maxLeaseMonth;
			refundAmount = 0;
		} else {
			monthsApart = monthsApart < minLeaseMonth ? minLeaseMonth : monthsApart;
			refundAmount = deposit - monthsApart * rentPerMonth;
		}
		rentTotal = monthsApart * rentPerMonth;
		refundAmount = refundAmount < 0 ? 0 : refundAmount;
		if (isReturnPrice || null == model) {
			return refundAmount;
		}
		
		model.addAttribute("minLeaseMonth", minLeaseMonth);
		model.addAttribute("maxLeaseMonth", maxLeaseMonth);
		model.addAttribute("leaseStart", leaseStart);
		model.addAttribute("deposit", deposit * 1.0 / 100);
		model.addAttribute("daysApart", daysApart);
		model.addAttribute("rentTotal", rentTotal * 1.0 / 100);
		model.addAttribute("refundAmount", refundAmount * 1.0 / 100);
		
		statisticTrade(model, csLease.getTerminal(), leaseStart, leaseEnd);
		return refundAmount;
	}
	
	/**
	 * 统计终端的交易流水
	 */
	private void statisticTrade(Model model, Terminal terminal, Date start, Date end) {
		// 一次性从数据库查询该时间段的交易记录, 然后统计每个月的交易汇总
		List<TradeRecord> tradeRecords = csCommonService.findTradeRecords(
				terminal.getSerialNum(), TradeRecord.TRADE_STATUS_SUCCESS, start, end);
		
		LinkedHashMap<String, TradeStatisticVO> month2Statistic = new LinkedHashMap<String, TradeStatisticVO>();
		Date[][] sections = divide2Months(start, end);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		for (Date[] section : sections) {
			String month = sdf.format(section[0]);
			TradeStatisticVO vo = new TradeStatisticVO();
			vo.setMonth(month);
			vo.setStart(section[0]);
			vo.setEnd(section[1]);
			vo.setAmount(0);
			month2Statistic.put(month, vo);
		}
		
		for (TradeRecord tradeRecord : tradeRecords) {
			Date tradedAt = tradeRecord.getTradedAt();
			Integer amount = tradeRecord.getAmount();
			
			if (null == tradedAt || null == amount) continue;
			String month = sdf.format(tradedAt);
			TradeStatisticVO vo = month2Statistic.get(month);
			if (null == vo) continue;
			vo.setAmount(vo.getAmount() + amount);
		}
		
		List<TradeStatisticVO> statistics = new ArrayList<TradeStatisticVO>();
		for (Iterator<TradeStatisticVO> it = month2Statistic.values().iterator(); it.hasNext();) {
			TradeStatisticVO vo = it.next();
			statistics.add(vo);
		}
		model.addAttribute("statistics", statistics);
	}
	
	private int toInt(Integer target) {
		return null == target ? 0 : target.intValue();
	}
	
	/**
	 * 计算2个日期相隔天数
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	private int getDaysApart(Date start, Date end) {
		return (int) (end.getTime() - start.getTime()) / (1000 * 3600 * 24);
	}

	/**
	 * 计算2个日期相隔月数
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param isExtend 不足一个月的是否要补足一个月
	 * @return
	 */
	private int getMonthsApart(Date start, Date end, boolean isExtend) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(start);
		int yearStart = calendar.get(Calendar.YEAR);
		int monthStart = calendar.get(Calendar.MONTH);
		int dayStart = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.setTime(end);
		int yearEnd = calendar.get(Calendar.YEAR);
		int monthEnd = calendar.get(Calendar.MONTH);
		int dayEnd = calendar.get(Calendar.DAY_OF_MONTH);

		int months = (yearEnd - yearStart) * 12 + (monthEnd - monthStart);
		if (isExtend && dayStart > dayEnd) {
			return months + 1;
		}
		return months;
	}
	
	/**
	 * 按月划分区间
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private Date[][] divide2Months(Date start, Date end) {
		int monthsApart = getMonthsApart(start, end, false);
		Date[][] dates = new Date[monthsApart + 1][2];
		
		// 获取开始时间所在月的第一天
		Calendar min = Calendar.getInstance();
		min.setTime(start);
		min.set(Calendar.DAY_OF_MONTH, min.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		// 获取结束时间所在月的最后一天
		Calendar max = Calendar.getInstance();
		max.setTime(end);
		max.set(Calendar.DAY_OF_MONTH, min.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Calendar curr = min;
		int index = 0;
		while(curr.before(max)) {
			curr.set(Calendar.DAY_OF_MONTH, min.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date currEnd = curr.getTime();
			curr.set(Calendar.DAY_OF_MONTH, min.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date currStart = curr.getTime();
			dates[index][0] = currStart;
			dates[index][1] = currEnd;
			curr.add(Calendar.MONTH, 1);
			index++;
		} 
		return dates;
	}
	
	/**
	 * This is a Value Object for trade statistic
	 */
	public class TradeStatisticVO {
		
		private String month;
		private Date start;
		private Date end;
		private Integer amount;

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public Date getStart() {
			return start;
		}

		public void setStart(Date start) {
			this.start = start;
		}

		public Date getEnd() {
			return end;
		}

		public void setEnd(Date end) {
			this.end = end;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

	}
}
