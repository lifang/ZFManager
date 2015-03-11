package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.domain.zhangfu.City;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.AgentService;
import com.comdosoft.financial.manage.service.CityService;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by junxi.qu on 2015/3/10.
 */
@Controller
@RequestMapping("/system/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CustomerService customerService;
    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String keys, Model model){
        findPage(page, status, keys, model);
        return "system/agent_list";
    }

    @RequestMapping(value="page",method=RequestMethod.GET)
    public String page(Integer page, Byte status, String keys, Model model){
        findPage(page, status, keys, model);
        return "system/agent_list_page";
    }

    @RequestMapping(value="{id}/info",method=RequestMethod.GET)
    public String page(@PathVariable Integer id, Model model){
        Agent agent = agentService.findAgentInfo(id);
        model.addAttribute("agent", agent);
        return "system/agent_info";
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model){
        Agent agent = agentService.findAgentInfo(id);
        List<City> provinces = cityService.provinces();
        model.addAttribute("agent", agent);
        model.addAttribute("provinces", provinces);
        return "system/agent_create";
    }

    @RequestMapping(value="create",method=RequestMethod.GET)
    public String create( Model model){
        List<City> provinces = cityService.provinces();
        model.addAttribute("provinces", provinces);
        return "system/agent_create";
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.POST)
    @ResponseBody
    public Response edit(@PathVariable Integer id,
                       Integer types, String name, String cardId, String companyName,
                       String businessLicense, String phone, String email, Integer cityId,
                       String address, String username, String password){
        boolean result = agentService.update(id, types, name, cardId, companyName,
                    businessLicense, phone, email, cityId, address,
                    username, password);
        if (!result){
            return Response.getError("登录ID");
        }
        return Response.getSuccess(null);
    }

    @RequestMapping(value="create",method=RequestMethod.POST)
    @ResponseBody
    public Response create( Integer types, String name, String cardId, String companyName,
                          String businessLicense, String phone, String email, Integer cityId, String address,
                          String username, String password){
        boolean result = agentService.create(types, name, cardId, companyName,
                businessLicense, phone, email, cityId, address,
                username, password);
        if (!result){
            return Response.getError("登录ID");
        }
        return Response.getSuccess(null);
    }

    private void findPage(Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Agent> agents = agentService.findPages(page, status, keys);
        model.addAttribute("page", agents);
    }

}
