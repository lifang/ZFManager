package com.comdosoft.financial.manage.controller.cs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.trades.TradeRecord;
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturn;
import com.comdosoft.financial.manage.domain.zhangfu.CsLeaseReturnMark;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OtherRequirement;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.cs.CsConstants.MaterialType;
import com.comdosoft.financial.manage.service.cs.CsLeaseService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("cs/lease")
public class CsLeaseController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CsLeaseService csLeaseService;
	@Autowired
	private CsCommonService csCommonService;

	private void findPage(Customer customer, Integer page, Integer status, String keyword, Model model){
		if (page == null) page = 1;
		if (null != status && status < 0) status = null;
		if ("".equals(keyword)) keyword = null;
		Page<CsLeaseReturn> csLeases = csLeaseService.findPage(customer, page, status, null != keyword ? keyword.trim() : keyword);
		model.addAttribute("csLeases", csLeases);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Integer page, Integer status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/lease/list";
	}
	
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Integer page, Integer status, String keyword, Model model) {
		Customer customer = sessionService.getLoginInfo(request);
		findPage(customer, page, status, keyword, model);
		return "cs/lease/table";
	}
	
	@RequestMapping(value = "{id}/info", method = RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model) {
		CsLeaseReturn csLease = csLeaseService.findInfoById(id);
		List<CsLeaseReturnMark> csLeaseMarks = csLeaseService.findMarksByCsLeaseReturnId(id);
		model.addAttribute("csLease", csLease);
		model.addAttribute("csLeaseMarks", csLeaseMarks);
		calculateLease(model, csLease);
		if (null != csLease.getCsCencelId() && csLease.getCsCencelId() > 0) {
			List<OtherRequirement> materials = csCommonService.findRequirementByType(MaterialType.CANCEL);
			model.addAttribute("materials", materials);
		}
		return "cs/lease/info";
	}
	
	/**
	 * 计算租赁退还相关数据
	 */
	private void calculateLease(Model model, CsLeaseReturn csLease) {
		if (null == csLease.getGood() || null == csLease.getOrder()) return;
		
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
		
		model.addAttribute("minLeaseMonth", minLeaseMonth);
		model.addAttribute("maxLeaseMonth", maxLeaseMonth);
		model.addAttribute("leaseStart", leaseStart);
		model.addAttribute("deposit", deposit * 1.0 / 100);
		model.addAttribute("daysApart", daysApart);
		model.addAttribute("rentTotal", rentTotal * 1.0 / 100);
		model.addAttribute("refundAmount", refundAmount * 1.0 / 100);
		
		statisticTrade(model, csLease.getTerminal(), leaseStart, leaseEnd);
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
	
	@RequestMapping(value = "{id}/handle", method = RequestMethod.POST)
	public void handle(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csLeaseService.handle(id);
	}
	
	@RequestMapping(value = "{id}/cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csLeaseService.cancel(id);
	}
	
	@RequestMapping(value = "{id}/finish", method = RequestMethod.POST)
	public void finish(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		csLeaseService.finish(id);
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String ids, Integer customerId, String customerName) {
		csLeaseService.dispatch(ids, customerId, customerName);
	}
	
	@RequestMapping(value = "{id}/mark/create", method = RequestMethod.POST)
	public String createMark(HttpServletRequest request, @PathVariable Integer id, String content, Model model) {
    	Customer customer = sessionService.getLoginInfo(request);
    	CsLeaseReturnMark csLeaseMark = csLeaseService.createMark(customer, id, content);
    	model.addAttribute("mark", csLeaseMark);
        return "cs/mark";
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
