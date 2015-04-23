/**
 * 
 */
package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.trades.TradeDayReport;
import com.comdosoft.financial.manage.domain.trades.TradeTotalDayReport;
import com.comdosoft.financial.manage.domain.trades.TradeTotalRegionReport;
import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.mapper.trades.TradeDayReportMapper;
import com.comdosoft.financial.manage.mapper.trades.TradeTotalDayReportMapper;
import com.comdosoft.financial.manage.mapper.trades.TradeTotalRegionReportMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年4月10日 上午10:50:24
 */
@Service
public class ReportService {
	
	@Autowired
	private TradeTotalDayReportMapper tradeTotalDayReportMapper;
	@Autowired
	private TradeDayReportMapper tradeDayReportMapper;
	@Autowired
	private TradeTotalRegionReportMapper tradeTotalRegionReportMapper;
	@Autowired
	private CityService cityService;

	public TradeTotalDayReport lastTradeTotalDayReport(){
		return tradeTotalDayReportMapper.selectNewest();
	}
	
	public List<TradeDayReport> lastWeekTradeReport(){
		return tradeDayReportMapper.selectLastWeek();
	}
	
	public Page<Map<String,Object>> lastRegionReport(Integer page,Integer pageSize){
		PageRequest request = new PageRequest(page, pageSize);
		List<TradeTotalRegionReport> content = tradeTotalRegionReportMapper.selectLastRegionReport(request);
		Map<Integer,City> provinecMap = cityService.provinceMap();
		List<Map<String,Object>> datas = new ArrayList<>();
		for(TradeTotalRegionReport r : content) {
			Map<String,Object> data = new HashMap<>();
			String name = provinecMap.get(r.getRegionId()).getName();
			if(name.endsWith("省")||name.endsWith("市")){
				name = name.substring(0, name.length()-1);
			}
			data.put("name", name);
			data.put("amount", r.getTradeSum());
			data.put("num", r.getTradeNum());
			datas.add(data);
		}
		Long total = tradeTotalRegionReportMapper.countLastRegionReport();
		return new Page<>(request, datas, total);
	}
}
