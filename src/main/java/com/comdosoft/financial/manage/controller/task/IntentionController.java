package com.comdosoft.financial.manage.controller.task;

import java.util.List;





import javax.servlet.http.HttpServletRequest;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.task.Intention;
import com.comdosoft.financial.manage.domain.zhangfu.task.Mark;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.service.task.IntentionService;
import com.comdosoft.financial.manage.utils.page.Page;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/task/intention")
public class IntentionController {
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
    private IntentionService intentionService;
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "task/intentions/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "task/intentions/page";
	}
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
	    Intention tinfo = intentionService.findInfo(id);
		model.addAttribute("one", tinfo);
        List<Mark> marks = intentionService.getMark(id);
        model.addAttribute("marks", marks);
		return "task/intentions/info";
	}
	
	@RequestMapping(value="addmark",method=RequestMethod.POST)
    public String addMark(@ModelAttribute("mark")Mark mark, Model model, HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        mark.setCusid(customer.getId());
        mark.setName(customer.getName());
        intentionService.addMark(mark);
        return getMark(mark.getApplyid(),model);
    }
    
    @RequestMapping(value="getmark",method=RequestMethod.GET)
    public String getMark(@PathVariable Integer id, Model model){
        List<Mark> marks = intentionService.getMark(id);
        model.addAttribute("marks", marks);
        return "task/intentions/mark";
    }

    
    @RequestMapping(value="upstatus",method=RequestMethod.POST)
    @ResponseBody
    public int upStatus( Integer id, Integer status, Model model){
        return intentionService.upStatus(id,status);
    }
	
	private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Intention> app = intentionService.findPages(page, status, keys);
		model.addAttribute("intentions", app);
	}
	
	
	@RequestMapping(value="dispatch",method=RequestMethod.POST)
    public void dispatch(String ids, Integer customerId, String customerName){
         intentionService.dispatch(ids, customerId, customerName);
    }


    


}
