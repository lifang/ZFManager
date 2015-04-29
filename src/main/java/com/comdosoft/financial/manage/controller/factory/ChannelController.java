package com.comdosoft.financial.manage.controller.factory;

import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.utils.FreeMarkerUtils;
import com.comdosoft.financial.manage.utils.page.Page;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by quqiang on 15/4/9.
 */
@Controller("FactoryChannelController")
@RequestMapping("/factory/channel")
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
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/channel/list";
    }

    @RequestMapping(value="page",method=RequestMethod.POST)
    public String page(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/channel/pageChannel";
    }

    @RequestMapping(value="{id}/info",method=RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model){
        PayChannel channel = payChannelService.findChannelInfo(id);
        model.addAttribute("channel", channel);
        model.addAttribute("isFactory", true);

        return "factory_role/channel/info";
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model, HttpServletRequest request) throws TemplateModelException {
        PayChannel channel = payChannelService.findChannelInfo(id);
        for(Iterator<SupportTradeType> it=channel.getSupportTradeTypes().iterator();it.hasNext();){
            SupportTradeType supportTradeType = it.next();
            if(supportTradeType.getTradeType() == SupportTradeType.TYPE_TRADE) {
                it.remove();
            }
        }
        model.addAttribute("channel", channel);
        List<Factory> factories = new ArrayList<>();
        Customer customer = sessionService.getLoginInfo(request);
        Factory factory = factoryService.findCustomerFactory(customer.getId());
        if(factory != null){
            factories.add(factory);
        }
        List<City> provinces = cityService.provinces();
        List<DictionaryTradeStandardRate> standardRates = dictionaryService.listAllDictionaryTradeStandardRates();
        List<DictionaryBillingCycle> billingCycles = dictionaryService.listAllDictionaryBillingCycles();
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        List<DictionaryOpenPrivateInfo> openPrivateInfos = dictionaryService.listAllDictionaryOpenPrivateInfos();
        model.addAttribute("factories", factories);
        model.addAttribute("provinces", provinces);
        model.addAttribute("standardRates", standardRates);
        model.addAttribute("billingCycles", billingCycles);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("openPrivateInfos", openPrivateInfos);
        model.addAttribute("DictionaryTradeType", FreeMarkerUtils.useClass(DictionaryTradeType.class.getName()));
        model.addAttribute("isFactory", true);

        return "factory_role/channel/create";
    }

    @RequestMapping(value="create",method=RequestMethod.GET)
    public String create(Model model, HttpServletRequest request) throws TemplateModelException {
        List<Factory> factories = new ArrayList<>();
        Customer customer = sessionService.getLoginInfo(request);
        Factory factory = factoryService.findCustomerFactory(customer.getId());
        if(factory != null){
            factories.add(factory);
        }
        List<City> provinces = cityService.provinces();
        List<DictionaryTradeStandardRate> standardRates = dictionaryService.listAllDictionaryTradeStandardRates();
        List<DictionaryBillingCycle> billingCycles = dictionaryService.listAllDictionaryBillingCycles();
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        List<DictionaryOpenPrivateInfo> openPrivateInfos = dictionaryService.listAllDictionaryOpenPrivateInfos();
        model.addAttribute("factories", factories);
        model.addAttribute("provinces", provinces);
        model.addAttribute("standardRates", standardRates);
        model.addAttribute("billingCycles", billingCycles);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("openPrivateInfos", openPrivateInfos);
        model.addAttribute("DictionaryTradeType", FreeMarkerUtils.useClass(DictionaryTradeType.class.getName()));
        model.addAttribute("isFactory", true);

        return "factory_role/channel/create";
    }

    private void findPage(Integer customerId, Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<PayChannel> channels = payChannelService.findPages(customerId, page, status, keys);
        model.addAttribute("channels", channels);
    }

}
