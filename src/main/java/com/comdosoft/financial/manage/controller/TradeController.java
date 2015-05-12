package com.comdosoft.financial.manage.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.trades.Profit;
import com.comdosoft.financial.manage.domain.trades.TradeRechargeRecord;
import com.comdosoft.financial.manage.domain.trades.TradeRecord;
import com.comdosoft.financial.manage.domain.trades.TradeTransferRepaymentRecord;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.domain.zhangfu.Merchant;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.TerminalService;
import com.comdosoft.financial.manage.service.TradeService;
import com.comdosoft.financial.manage.utils.page.Page;
import com.google.common.base.Joiner;

/**
 * Created by gookin on 15/3/7.
 */
@Controller
@RequestMapping("/trade")
public class TradeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private PayChannelService payChannelService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model){
        List<DictionaryTradeType> tradeTypes = tradeService.allTradeTypes();
        model.addAttribute("tradeTypes",tradeTypes);
        type(tradeTypes.get(0).getId(),model);
        model.addAttribute("tradeTypeID", 1);
        return "trade/trade_index";
    }

    @RequestMapping(value = "/type/{id}/list",method = RequestMethod.GET)
    public String type(@PathVariable Integer id,Model model){
        DictionaryTradeType tradeType = tradeService.tradeType(id);
        model.addAttribute("type",tradeType);
        return "trade/trade_list";
    }

    @RequestMapping(value = "/type/{id}/page/{page}",method = RequestMethod.POST)
    public String tradePage(@PathVariable Integer id,
                            @PathVariable Integer page,
                            Integer status,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                            Model model){
        Page<TradeRecord> recordPage = tradeService.tradeRecordPage(page, id, status, start, end);
        model.addAttribute("recordPage",recordPage);
        Map<String,Long> profits = tradeService.pageProfit(id,status,start,end);
        model.addAttribute("profits",profits);
        return "trade/trade_list_page";
    }

    @RequestMapping(value = "/{id}/info",method = RequestMethod.GET)
    public String tradeInfo(@PathVariable Integer id,Model model){
    	TradeRecord tradeRecord = tradeService.tradeRecord(id);
    	model.addAttribute(tradeRecord);
    	DictionaryTradeType tradeType = tradeService.tradeType(tradeRecord.getTradeTypeId());
    	model.addAttribute("tradeType",tradeType);
    	Profit profit = tradeService.tradeRecordProfit(id);
    	if(profit!=null) {
    		model.addAttribute(profit);
    	}
    	Terminal terminal = terminalService.findByNum(tradeRecord.getTerminalNumber());
    	if(terminal!=null){
    		Merchant merchant = terminal.getMerchant();
    		if(merchant == null) {
    			model.addAttribute(merchant);
    		}
    	}
    	PayChannel payChannel = payChannelService.findChannelInfo(tradeRecord.getPayChannelId());
    	if(payChannel!=null){
    		model.addAttribute(payChannel);
    	}
    	if(tradeRecord.getTradeTypeId() == DictionaryTradeType.ID_TRANSFER
    			|| tradeRecord.getTradeTypeId() == DictionaryTradeType.ID_REPAY){
    		TradeTransferRepaymentRecord transferRecord = tradeService.transferRecord(id);
    		if(transferRecord!=null){
    			model.addAttribute(transferRecord);
    		}
    	}else if(tradeRecord.getTradeTypeId() == DictionaryTradeType.ID_PHONE_RECHARGE){
    		TradeRechargeRecord rechargeRecord = tradeService.rechargeRecord(id);
    		if(rechargeRecord!=null) {
    			model.addAttribute(rechargeRecord);
    		}
    	}
        return "trade/trade_info";
    }
    
    @RequestMapping(value = "/{id}/statistics",method = RequestMethod.GET)
    public String statistics(@PathVariable Integer id,Model model){
        DictionaryTradeType tradeType = tradeService.tradeType(id);
        model.addAttribute("tradeType",tradeType);
        List<Map<String,Object>> statistics = tradeService.profitStatistics(id);
        model.addAttribute("statistics",statistics);
        Map<Date,Date> map = tradeService.getTradedDateRange(id);
        model.addAttribute("tradedDateRange", map);
        return "trade/trade_statistics";
    }
    
    @RequestMapping(value = "/{id}/statistics/export",method = RequestMethod.GET)
    public void statisticsExport(@PathVariable Integer id,HttpServletResponse resp){
    	try {
    		resp.setHeader("Content-Disposition", "attachment; filename=export.xls");  
    		resp.setContentType("application/octet-stream; charset=utf-8");  
			OutputStream outStream = resp.getOutputStream();
			tradeService.writeProfitStatistics(id, outStream);
			outStream.flush();
		} catch (IOException e) {
			LOG.error("",e);
		}
    }
    
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    @ResponseBody
    public Response importTrades(MultipartFile file,Integer selectTradeType){
    	try {
			List<Integer> errorRowNum = tradeService.importTrades(file.getInputStream(),selectTradeType);
			if(errorRowNum.size() == 0) {
				return Response.getSuccess(null);
			}else{
				String errMsg = Joiner.on(',').join(errorRowNum);
				return Response.getError("第["+errMsg+"]行导入出错，其余已导入成功。");
			}
		} catch (Exception e) {
			LOG.error("",e);
		}
    	return Response.getError("上传文件内容有误或不是[xls/xlsx]格式的文件。");
    }
    
    @RequestMapping(value = "/{id}/index",method = RequestMethod.GET)
    public String backToIndex(@PathVariable Integer id,Model model){
        List<DictionaryTradeType> tradeTypes = tradeService.allTradeTypes();
        model.addAttribute("tradeTypes",tradeTypes);
        type(id,model);
        model.addAttribute("tradeTypeID", id);
        return "trade/trade_index";
    }
}
