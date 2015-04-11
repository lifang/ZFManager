/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年4月5日 下午7:04:54
 */
package com.comdosoft.financial.manage.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.Agent;
import com.comdosoft.financial.manage.service.AgentService;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/order/agent")
public class AgentsController extends BaseController {
	
	private Integer maxPageSize=9999;
	@Autowired
	private AgentService agentService;
	
    @RequestMapping(value="search",method= RequestMethod.GET)
    public String search(Integer page, Byte status, String keys, Model model){
        findPage(page, status, keys, model);
        return "order/agentSearch";
    }
    
    private void findPage(Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Agent> agents = agentService.findPages(page, status, keys,maxPageSize);
        model.addAttribute("agents", agents.getContent());
    }
}
