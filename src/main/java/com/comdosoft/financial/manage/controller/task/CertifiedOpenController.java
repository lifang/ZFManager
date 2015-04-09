package com.comdosoft.financial.manage.controller.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCreditType;
import com.comdosoft.financial.manage.domain.zhangfu.task.CertifiedOpen;
import com.comdosoft.financial.manage.domain.zhangfu.task.Mark;
import com.comdosoft.financial.manage.domain.zhangfu.task.Opendetailsinfo;
import com.comdosoft.financial.manage.domain.zhangfu.task.Showinfo;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.service.task.CertifiedOpenService;
import com.comdosoft.financial.manage.utils.page.Page;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/task/certifiedopen")
public class CertifiedOpenController {
	
	
	@Value("${path.root}")
	private String rootPath;
    @Value("${path.prefix.pos}")
    private String posPath;
	
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
    private CertifiedOpenService certifiedOpenService;
	
	 @Autowired
     private DictionaryService dictionaryService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "task/certifiedOpen/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "task/certifiedOpen/page";
	}
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
	    Showinfo tinfo = certifiedOpenService.findInfo(id);
		model.addAttribute("tinfo", tinfo);
		List<Opendetailsinfo> opendetailsinfos = certifiedOpenService.opendetailsinfo(id);
		if(opendetailsinfos!=null&&opendetailsinfos.size()>0){
            model.addAttribute("opendetailsinfos", opendetailsinfos);
        }
        List<Mark> marks = certifiedOpenService.getMark(id);
        model.addAttribute("marks", marks);
		return "task/certifiedOpen/info";
	}
	
	@RequestMapping(value="{id}/video",method=RequestMethod.GET)
    public String video(@PathVariable Integer id, Model model){
	    Showinfo tinfo = certifiedOpenService.findInfo(id);
        model.addAttribute("tinfo", tinfo);
        List<Opendetailsinfo> opendetailsinfos = certifiedOpenService.opendetailsinfo(id);
        if(opendetailsinfos!=null&&opendetailsinfos.size()>0){
            model.addAttribute("opendetailsinfos", opendetailsinfos);
        }
        List<DictionaryCreditType> creditTypes = dictionaryService.listAllDictionaryCreditTypes();
        model.addAttribute("creditTypes", creditTypes);
        return "task/certifiedOpen/video";
    }
	
	@RequestMapping(value="addmark",method=RequestMethod.POST)
    public String addMark(@ModelAttribute("mark")Mark mark, Model model, HttpServletRequest request){
	    Customer customer = sessionService.getLoginInfo(request);
	    mark.setCusid(customer.getId());
	    certifiedOpenService.addMark(mark);
        return getMark(mark.getApplyid(),model);
    }
	
	@RequestMapping(value="getmark",method=RequestMethod.GET)
    public String getMark(@PathVariable Integer id, Model model){
	    List<Mark> marks = certifiedOpenService.getMark(id);
        model.addAttribute("marks", marks);
        return "task/certifiedOpen/mark";
    }

	@RequestMapping(value="upvstatus",method=RequestMethod.POST)
	@ResponseBody
    public int upVstatus( Integer id, Integer status, Model model){
	    return certifiedOpenService.upVstatus(id,status);
    }
	
	@RequestMapping(value="upstatus",method=RequestMethod.POST)
    @ResponseBody
    public int upStatus( Integer id, Integer status, Model model){
	    return certifiedOpenService.upStatus(id,status);
    }
	
	@RequestMapping(value="upmstatus",method=RequestMethod.POST)
    @ResponseBody
    public int upMstatus( Integer id, Integer status, Model model){
        return certifiedOpenService.upMstatus(id,status);
    }
	
	private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<CertifiedOpen> app = certifiedOpenService.findPages(page, status, keys);
		model.addAttribute("apply", app);
	}
	
	@RequestMapping(value="dispatch",method=RequestMethod.POST)
    public void dispatch(String ids, Integer customerId, String customerName){
	    certifiedOpenService.dispatch(ids, customerId, customerName);
    }

    


}
