package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.SysMessage;
import com.comdosoft.financial.manage.service.MessageService;
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
	public String createGet(){
		return "system/message_create";
	}
	
	@RequestMapping(value="/{id}/view",method=RequestMethod.GET)
	public String view(@PathVariable Integer id, Model model){
        SysMessage message = messageService.findInfo(id);
        model.addAttribute("message", message);
		return "system/message_view";
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
