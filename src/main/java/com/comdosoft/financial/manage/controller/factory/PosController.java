package com.comdosoft.financial.manage.controller.factory;

import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by quqiang on 15-4-7.
 */
@Controller("FactoryPosController")
@RequestMapping("/factory/pos")
public class PosController {
    @Autowired
    private GoodService goodService ;
    @Autowired
    private PosCategoryService posCategoryService;
    @Autowired
    private FactoryService factoryService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private PayChannelService payChannelService;
    @Autowired
    private GoodCommentService goodCommentService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/pos/list";
    }

    @RequestMapping(value="page",method=RequestMethod.POST)
    public String page(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/pos/pagePos";
    }

    @RequestMapping(value="{id}/info",method=RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model){
        Good good = goodService.findGoodInfo(id);
        model.addAttribute("good", good);
        model.addAttribute("isFactory", true);
        return "factory_role/pos/info";
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model, HttpServletRequest request){
        Good good = goodService.findGoodInfo(id);
        Collection<PosCategory> posCategories = posCategoryService.listAll();
        List<Factory> factories = new ArrayList<>();
        Customer customer = sessionService.getLoginInfo(request);
        Factory factory = factoryService.findCustomerFactory(customer.getId());
        if(factory != null){
            factories.add(factory);
        }
        List<DictionarySignOrderWay> signOrderWays = dictionaryService.listAllDictionarySignOrderWays();
        List<DictionaryCardType> cardTypes = dictionaryService.listAllDictionaryCardTypes();
        List<DictionaryEncryptCardWay> encryptCardWays = dictionaryService.listAllDictionaryEncryptCardWays();
        model.addAttribute("good", good);
        model.addAttribute("posCategories", posCategories);
        model.addAttribute("factories", factories);
        model.addAttribute("signOrderWays", signOrderWays);
        model.addAttribute("cardTypes", cardTypes);
        model.addAttribute("encryptCardWays", encryptCardWays);
        model.addAttribute("isFactory", true);

        return "good/pos/create";
    }

    @RequestMapping(value="create",method=RequestMethod.GET)
    public String create(Model model, HttpServletRequest request){
        Collection<PosCategory> posCategories = posCategoryService.listAll();
        List<Factory> factories = new ArrayList<>();
        Customer customer = sessionService.getLoginInfo(request);
        Factory factory = factoryService.findCustomerFactory(customer.getId());
        if(factory != null){
            factories.add(factory);
        }
        List<DictionarySignOrderWay> signOrderWays = dictionaryService.listAllDictionarySignOrderWays();
        List<DictionaryCardType> cardTypes = dictionaryService.listAllDictionaryCardTypes();
        List<DictionaryEncryptCardWay> encryptCardWays = dictionaryService.listAllDictionaryEncryptCardWays();
        model.addAttribute("posCategories", posCategories);
        model.addAttribute("factories", factories);
        model.addAttribute("signOrderWays", signOrderWays);
        model.addAttribute("cardTypes", cardTypes);
        model.addAttribute("encryptCardWays", encryptCardWays);
        model.addAttribute("isFactory", true);

        return "good/pos/create";
    }

    private void findPage(Integer customerId, Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Good> goods = goodService.findPages(customerId, page, status, keys);
        model.addAttribute("goods", goods);
    }
}
