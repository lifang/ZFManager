package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by junxi.qu on 2015/3/10.
 */
@Controller
@RequestMapping("/system/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Model model){
        return "system/agent_list";
    }

}
