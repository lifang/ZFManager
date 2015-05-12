package com.comdosoft.financial.manage.controller.factory;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.TerminalService;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by quqiang on 15/4/9.
 */
@Controller("FactoryTerminalController")
@RequestMapping("/factory/terminal")
public class TerminalController {
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private PayChannelService payChannelService;
    @Autowired
    private SessionService sessionService;


    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/terminal/list";
    }

    @RequestMapping(value="page",method=RequestMethod.POST)
    public String page(Integer page, Byte status, String keys, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        findPage(customer.getId(), page, status, keys, model);
        return "factory_role/terminal/pageTerminal";
    }

    @RequestMapping(value="{id}/info",method=RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model){
        Terminal terminal = terminalService.findTerminalInfo(id);
        String videoUrl = terminalService.videoFile(terminal.getId());
        model.addAttribute("terminal", terminal);
        model.addAttribute("isFactory", true);
        model.addAttribute("videoUrl", videoUrl);
        return "factory_role/terminal/info";
    }

    private void findPage(Integer customerId, Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Terminal> terminals = terminalService.findPages(customerId, page, status, keys);
        Map<String, Integer> statusMap = new HashMap<String, Integer>();
        Map<String, OpeningApplie> applyMap = new HashMap<String, OpeningApplie>();
        for (Terminal terminal : terminals.getContent()){
            Integer id = terminal.getId();
            int terminalStatus = terminalService.videoStatus(id);
            statusMap.put(id+"", terminalStatus);
            applyMap.put(id+"", terminalService.getOpeningApplie(id));
        }
        model.addAttribute("terminals", terminals);
        model.addAttribute("statusMap", statusMap);
        model.addAttribute("applyMap", applyMap);
    }
}
