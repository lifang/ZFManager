package com.comdosoft.financial.manage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comdosoft.financial.manage.domain.trades.TradeDayReport;
import com.comdosoft.financial.manage.domain.trades.TradeTotalDayReport;
import com.comdosoft.financial.manage.service.ReportService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("real/trade")
public class RealTradeController {

	@Autowired
	private ReportService reportService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String realTradePage(Model model){
		TradeTotalDayReport lastTradeTotal = reportService.lastTradeTotalDayReport();
		model.addAttribute(lastTradeTotal);
		List<TradeDayReport> lastWeekTradeDay = reportService.lastWeekTradeReport();
		model.addAttribute(lastWeekTradeDay);
		if(lastWeekTradeDay.size()!=0){
			TradeDayReport lastTradeDay = lastWeekTradeDay.get(0);
			model.addAttribute(lastTradeDay);
		}
		return "real_trade";
	}

	@RequestMapping(value="map",method=RequestMethod.POST)
	@ResponseBody
	public Page<Map<String,Object>> map(Integer page,Integer pageSize){
		if(page == null) {
			page = 1;
		}
		if(pageSize == null) {
			pageSize = 5;
		}
		return reportService.lastRegionReport(page, pageSize);
	}
}
