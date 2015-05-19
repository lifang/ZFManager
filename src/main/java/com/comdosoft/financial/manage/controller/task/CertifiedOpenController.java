package com.comdosoft.financial.manage.controller.task;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.comdosoft.financial.manage.domain.Response;
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
    @Value("${applyURL}")
    private String applyURL;
    @Value("${timingPath}")
    private String timingPath;
    
    @Autowired
    private SessionService sessionService;

    @Autowired
    private CertifiedOpenService certifiedOpenService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private RecordOperateService recordOperateService;

    @Autowired
    private TerminalService terminalService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(HttpServletRequest request,Integer page, Byte status, String keys, Model model) {
        findPage( request,page, status, keys, model);
        return "task/certifiedOpen/list";
    }

    @RequestMapping(value = "page", method = RequestMethod.POST)
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
        certifiedOpenService.upVstatus2(id,1);
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

    @RequestMapping(value = "endRecord", method = RequestMethod.POST)
    @ResponseBody
    public Response endRecord(HttpServletRequest request,Integer id, String url, Model model) {
        terminalService.updateVideoFile(id, url);
        return Response.getSuccess(null);
    }

    @RequestMapping(value = "upstatus", method = RequestMethod.POST)
    @ResponseBody
    public String upStatus(HttpServletRequest request,Integer id, Integer status, Integer tid,Model model) {
    	Integer a = 0;
    	if(status!=6){
        	a=certifiedOpenService.upStatus(id, status);
        }
        String button=null;
        String result ="";
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
            try {
				result = (String) sendPost(tid);
				operationRefundContent(request,button,id);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
            return result;
        }else{
            return a.toString();
        }
        operationRefundContent(request,button,id);
        return a.toString();
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

    /**
     * 获取开通视频认证返回信息  
     * @param terminalId 终端号id
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "unchecked", "unused" })
	private Object  sendPost(Integer terminalId) throws IOException, ClassNotFoundException {
        URL postUrl = new URL(timingPath+applyURL);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        String content = "terminalId=" + terminalId;
        out.writeBytes(content); 
        out.flush();
        out.close(); // flush and close
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        List<String> contents = new ArrayList<String>();  
        while ((line = reader.readLine()) != null) {
            contents.add(line);
        }
        reader.close();
        connection.disconnect();
        String json =  contents.get(0);
        Map<String,Object> m = JSON.parseObject(json);
        Object code ="";
        Object result ="";
        Object message ="";
        for (Object o : m.entrySet()) { 
        	  Map.Entry<String,Object> entry = (Map.Entry<String,Object>)o; 
              if(entry.getKey().equals("code")){ 
            	   code =  entry.getValue();
              }
              if(entry.getKey().equals("result")){ 
            	   result =  entry.getValue();
              }
              if(entry.getKey().equals("message")){ 
            	   message =  entry.getValue();
              }
		}
        return result;
    }
    @RequestMapping(value="/upFail",method=RequestMethod.POST)
    @ResponseBody
    public Response upFail(Integer id,Integer status,String reason,
    		HttpServletRequest request,String serialNum){
    	Customer customer = sessionService.getLoginInfo(request);
    	try {
			certifiedOpenService.upFail(id,status,reason,customer,serialNum);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.getError("审核失败");
		}
    	return Response.getSuccess("审核成功");
    }
}
