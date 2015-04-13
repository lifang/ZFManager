package com.comdosoft.financial.manage.controller.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
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

    @Autowired
    private RecordOperateService recordOperateService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,Integer page, Byte status, String keys, Model model) {
        findPage(request,page, status, keys, model);
        return "task/intentions/list";
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    public String page(HttpServletRequest request,Integer page, Byte status, String keys, Model model) {
        findPage(request,page, status, keys, model);
        return "task/intentions/page";
    }

    @RequestMapping(value = "{id}/info", method = RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model) {
        Intention tinfo = intentionService.findInfo(id);
        model.addAttribute("one", tinfo);
        List<Mark> marks = intentionService.getMark(id);
        model.addAttribute("marks", marks);
        return "task/intentions/info";
    }

    @RequestMapping(value = "addmark", method = RequestMethod.POST)
    public String addMark(@ModelAttribute("mark") Mark mark, Model model, HttpServletRequest request) {
        Customer customer = sessionService.getLoginInfo(request);
        mark.setCusid(customer.getId());
        mark.setName(customer.getName());
        intentionService.addMark(mark);
        operationRefundContent(request,"备注",mark.getId());
        return getMark(mark.getApplyid(), model);
    }

    @RequestMapping(value = "getmark", method = RequestMethod.GET)
    public String getMark(@PathVariable Integer id, Model model) {
        List<Mark> marks = intentionService.getMark(id);
        model.addAttribute("marks", marks);
        return "task/intentions/mark";
    }

    @RequestMapping(value = "upstatus", method = RequestMethod.POST)
    @ResponseBody
    public int upStatus(HttpServletRequest request,Integer id, Integer status, Model model) {
        int a=intentionService.upStatus(id, status);
        String button=null;
        if(status==2){
            button="标记为处理中";
        }else if(status==3){
            button="标记为已处理";
        }else{
            return a;
        }
        operationRefundContent(request,button,id);
        return a;
    }

    private void findPage(HttpServletRequest request,Integer page, Byte status, String keys, Model model) {
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Customer customer = sessionService.getLoginInfo(request);
        Page<Intention> app = intentionService.findPages(customer.getId(),page, status, keys);
        model.addAttribute("intentions", app);
    }

    @RequestMapping(value = "dispatch", method = RequestMethod.POST)
    @ResponseBody
    public void dispatch(HttpServletRequest request,String ids, Integer customerId, String customerName) {
        intentionService.dispatch(ids, customerId, customerName);
        if(!"".equals(ids.trim())){
            String []idd=ids.split(",");
            for (String id : idd) {
                operationRefundContent(request,"分派",Integer.valueOf(id));
            }
        }
    }
    
    private String content = "name执行了A page的button操作，操作记录的id是 nowId";
    public void operationRefundContent(HttpServletRequest request,String button,int id){
        Customer customer = sessionService.getLoginInfo(request);
        String contentNew = content.replace("name", customer.getName())
                .replace("A page", "购买意向页面")
                .replace("button", button)
                .replace("nowId", String.valueOf(id));
        recordOperateService.saveOperateRecord(customer.getId(), customer.getName(), customer.getTypes(), OperateRecord.TYPES_BUY_INTENTION, contentNew, id);
        
    }

}
