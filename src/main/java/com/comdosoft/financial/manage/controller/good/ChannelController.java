package com.comdosoft.financial.manage.controller.good;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("good/channel")
public class ChannelController {

	@Autowired
	private PayChannelService payChannelService;
    @Autowired
    private SupportTradeTypeService supportTradeTypeService ;
    @Autowired
    private FactoryService factoryService;
    @Autowired
    private CityService cityService;
    @Autowired
    private DictionaryService dictionaryService;


	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/channel/list";
	}

	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/channel/pageChannel";
	}

	@RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
	public String firstUnCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusFirstUnCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
	public String firstCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusFirstCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
	public String unCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusUnCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/check",method=RequestMethod.GET)
	public String check(@PathVariable Integer id, String source, Boolean isThird, Model model){
		PayChannel channel = payChannelService.statusCheck(id, isThird);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/stop",method=RequestMethod.GET)
	public String stop(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusStop(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/start",method=RequestMethod.GET)
	public String start(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusWaitingFirstCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
		PayChannel channel = payChannelService.findChannelInfo(id);
		model.addAttribute("channel", channel);
		return "good/channel/info";
	}

	@RequestMapping(value="{id}/profit",method=RequestMethod.GET)
	public String profit(@PathVariable Integer id, Model model){
        PayChannel channel = payChannelService.findChannelInfo(id);
        model.addAttribute("channel", channel);
		return "good/channel/profit";
	}

    @RequestMapping(value="{id}/editProfit",method=RequestMethod.POST)
    @ResponseBody
    public Response editProfit(@PathVariable Integer id, Integer baseProfit,
                               @RequestParam(value = "tradeTypeIds[]", required = false) Integer[] tradeTypeIds,
                               @RequestParam(value = "terminalRates[]", required = false) Integer[] terminalRates,
                               @RequestParam(value = "baseRates[]", required = false) Integer[] baseRates,
                               @RequestParam(value = "floorCharges[]", required = false) Float[] floorCharges,
                               @RequestParam(value = "floorProfits[]", required = false) Float[] floorProfits,
                               @RequestParam(value = "topCharges[]", required = false) Float[] topCharges,
                               @RequestParam(value = "topProfits[]", required = false) Float[] topProfits){
        supportTradeTypeService.updateSupportTradeTypes(id, baseProfit,
                tradeTypeIds, terminalRates, baseRates, floorCharges, floorProfits, topCharges, topProfits);
        return Response.getSuccess("");
    }

    @RequestMapping(value="create",method=RequestMethod.GET)
    public String create(Model model){
        List<Factory> factories = factoryService.findCheckedFactories();
        List<City> provinces = cityService.provinces();
        List<DictionaryTradeStandardRate> standardRates = dictionaryService.listAllDictionaryTradeStandardRates();
        List<DictionaryBillingCycle> billingCycles = dictionaryService.listAllDictionaryBillingCycles();
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        model.addAttribute("factories", factories);
        model.addAttribute("provinces", provinces);
        model.addAttribute("standardRates", standardRates);
        model.addAttribute("billingCycles", billingCycles);
        model.addAttribute("tradeTypes", tradeTypes);
        return "good/channel/create";
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model){
        PayChannel channel = payChannelService.findChannelInfo(id);
        for(Iterator<SupportTradeType> it=channel.getSupportTradeTypes().iterator();it.hasNext();){
            SupportTradeType supportTradeType = (SupportTradeType)it.next();
            if(supportTradeType.getTradeType() == SupportTradeType.TYPE_TRADE) {
                it.remove();
            }
        }
        model.addAttribute("channel", channel);
        List<Factory> factories = factoryService.findCheckedFactories();
        List<City> provinces = cityService.provinces();
        List<DictionaryTradeStandardRate> standardRates = dictionaryService.listAllDictionaryTradeStandardRates();
        List<DictionaryBillingCycle> billingCycles = dictionaryService.listAllDictionaryBillingCycles();
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        model.addAttribute("factories", factories);
        model.addAttribute("provinces", provinces);
        model.addAttribute("standardRates", standardRates);
        model.addAttribute("billingCycles", billingCycles);
        model.addAttribute("tradeTypes", tradeTypes);
        return "good/channel/create";
    }


    private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<PayChannel> channels = payChannelService.findPages(page, Constants.PAGE_SIZE, status, keys);
		model.addAttribute("channels", channels);
	}
}
