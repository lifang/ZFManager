package com.comdosoft.financial.manage.controller;

import com.comdosoft.financial.manage.domain.trades.TradeRecord;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.service.TradeService;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gookin on 15/3/7.
 */
@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model){
        List<DictionaryTradeType> tradeTypes = tradeService.allTradeTypes();
        model.addAttribute("tradeTypes",tradeTypes);
        type(tradeTypes.get(0).getId(),model);
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
        Map<String,Long> profits = tradeService.pageProfit(page,id,status,start,end);
        model.addAttribute("profits",profits);
        return "trade/trade_list_page";
    }

    @RequestMapping(value = "/{id}/info",method = RequestMethod.GET)
    public String tradeInfo(@PathVariable Integer id){
        return "trade/trade_info";
    }
}
