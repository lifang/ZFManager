package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.SysMessage;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.MessageService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.utils.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private PayChannelService payChannelService;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Integer page, Model model){
        findPage(page, model);
		return "system/message_list";
	}

    @RequestMapping(value="page",method=RequestMethod.GET)
    public String page(Integer page, Model model){
        findPage(page, model);
        return "system/message_list_page";
    }

    @RequestMapping(value="delete",method=RequestMethod.GET)
    @ResponseBody
    public Response delete(@RequestParam("ids[]") List<Integer> ids, Model model){
        messageService.delete(ids);
        return Response.getSuccess("");
    }

    @RequestMapping(value="{id}/delete",method=RequestMethod.GET)
    @ResponseBody
    public Response delete(@PathVariable Integer id, Model model){
        messageService.delete(id);
        return Response.getSuccess("");
    }

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String createGet(Model model){
        List<Good> goods = goodService.findCheckedGoods();
        List<PayChannel> channels = payChannelService.findCheckedChannels();
        model.addAttribute("goods", goods);
        model.addAttribute("channels", channels);
        return "system/message_create";
	}

    @RequestMapping(value="/create",method=RequestMethod.POST)
    @ResponseBody
    public Response create(String title, String content, Integer customerId,
                           Integer goodId, Integer channelId, Byte customerType,  Model model){
        messageService.create(title, content, customerId, goodId, channelId, customerType);
        return Response.getSuccess("");
    }
	
	@RequestMapping(value="/{id}/view",method=RequestMethod.GET)
	public String view(@PathVariable Integer id, Model model){
        SysMessage message = messageService.findInfo(id);
        model.addAttribute("message", message);
		return "system/message_view";
	}

    @RequestMapping(value="searchCustomer",method=RequestMethod.GET)
    public String searchCustomer(String name, Model model){
        if (name != null && name.length() > 0) {
            List<Customer> customers = customerService.findUserAndAgent(name);
            model.addAttribute("customers", customers);
        }
        return "system/message_search_customer";
    }

    private void findPage(Integer page, Model model){
        if (page == null) {
            page = 1;
        }
        Page<SysMessage> messages = messageService.findPages(page);
        model.addAttribute("page", messages);
        model.addAttribute("pageNum", page);
    }
}
