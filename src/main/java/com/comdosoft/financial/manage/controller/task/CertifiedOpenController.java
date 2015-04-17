package com.comdosoft.financial.manage.controller.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCreditType;
import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
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

    @Autowired
    private RecordOperateService recordOperateService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,Integer page, Byte status, String keys, Model model) {
        findPage( request,page, status, keys, model);
        return "task/certifiedOpen/list";
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)
    public String page(HttpServletRequest request,Integer page, Byte status, String keys, Model model) {
        findPage(request,page, status, keys, model);
        return "task/certifiedOpen/page";
    }

    @RequestMapping(value = "{id}/info", method = RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model) {
        Showinfo tinfo = certifiedOpenService.findInfo(id);
        model.addAttribute("tinfo", tinfo);
        List<Opendetailsinfo> opendetailsinfos = certifiedOpenService.opendetailsinfo(id);
        if (opendetailsinfos != null && opendetailsinfos.size() > 0) {
            model.addAttribute("opendetailsinfos", opendetailsinfos);
        }
        List<Mark> marks = certifiedOpenService.getMark(id);
        model.addAttribute("marks", marks);
        return "task/certifiedOpen/info";
    }

    @RequestMapping(value = "{id}/video", method = RequestMethod.GET)
    public String video(@PathVariable Integer id, Model model, HttpServletRequest request) {
        Showinfo tinfo = certifiedOpenService.findInfo(id);
        model.addAttribute("tinfo", tinfo);
        Customer customer = sessionService.getLoginInfo(request);
        model.addAttribute("customer", customer);
        List<Opendetailsinfo> opendetailsinfos = certifiedOpenService.opendetailsinfo(id);
        if (opendetailsinfos != null && opendetailsinfos.size() > 0) {
            model.addAttribute("opendetailsinfos", opendetailsinfos);
        }
        List<DictionaryCreditType> creditTypes = dictionaryService.listAllDictionaryCreditTypes();
        model.addAttribute("creditTypes", creditTypes);
        return "task/certifiedOpen/video";
    }

    @RequestMapping(value = "addmark", method = RequestMethod.POST)
    public String addMark(@ModelAttribute("mark") Mark mark, Model model, HttpServletRequest request) {
        Customer customer = sessionService.getLoginInfo(request);
        mark.setCusid(customer.getId());
        certifiedOpenService.addMark(mark);
        operationRefundContent(request,"备注",mark.getId());
        return getMark(mark.getApplyid(), model);
    }

    @RequestMapping(value = "getmark", method = RequestMethod.GET)
    public String getMark(@PathVariable Integer id, Model model) {
        List<Mark> marks = certifiedOpenService.getMark(id);
        model.addAttribute("marks", marks);
        return "task/certifiedOpen/mark";
    }

    @RequestMapping(value = "upvstatus", method = RequestMethod.POST)
    @ResponseBody
    public int upVstatus(HttpServletRequest request,Integer id, Integer status, Model model) {
        int a=certifiedOpenService.upVstatus(id, status);
        String button=null;
        if(status==1){
            button="重置视频认证";
        }else if(status==2){
            button="视频认证通过";
        }else if(status==3){
            button="视频认证失败";
        }else{
            return a;
        }
        operationRefundContent(request,button,id);
        return a;
    }

    @RequestMapping(value = "upstatus", method = RequestMethod.POST)
    @ResponseBody
    public int upStatus(HttpServletRequest request,Integer id, Integer status, Model model) {
        int a=certifiedOpenService.upStatus(id, status);
        String button=null;
        if(status==2){
            button="初审失败";
        }else if(status==3){
            button="初审通过";
        }else if(status==4){
            button="二审失败";
        }else if(status==5){
            button="二审通过";
        }else if(status==6){
            button="提交开通申请";
        }else{
            return a;
        }
        operationRefundContent(request,button,id);
        return a;
    }

    @RequestMapping(value = "upmstatus", method = RequestMethod.POST)
    @ResponseBody
    public int upMstatus(HttpServletRequest request,Integer id, Integer status, Model model) {
        int a=certifiedOpenService.upMstatus(id, status);
        operationRefundContent(request,"信用风险",id);
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
        Page<CertifiedOpen> app = certifiedOpenService.findPages(customer.getId(),page, status, keys);
        model.addAttribute("apply", app);
    }

    @RequestMapping(value = "dispatch", method = RequestMethod.POST)
    @ResponseBody
    public void dispatch(HttpServletRequest request,String ids, Integer customerId, String customerName) {
        certifiedOpenService.dispatch(ids, customerId, customerName);
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
                .replace("A page", "认证开通页面")
                .replace("button", button)
                .replace("nowId", String.valueOf(id));
        recordOperateService.saveOperateRecord(customer.getId(), customer.getName(), customer.getTypes(), OperateRecord.TYPES_OPEN_AUTHENTICATION, contentNew, id);
        
    }

}
