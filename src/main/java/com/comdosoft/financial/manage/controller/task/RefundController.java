package com.comdosoft.financial.manage.controller.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.manage.domain.zhangfu.CsRefund;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OperateRecord;
import com.comdosoft.financial.manage.service.RecordOperateService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.cs.CsCommonService;
import com.comdosoft.financial.manage.service.task.RefundService;
import com.comdosoft.financial.manage.utils.HttpFile;
import com.comdosoft.financial.manage.utils.page.Page;


@Controller
@RequestMapping("/task/refund")
public class RefundController {
	private Logger LOG = LoggerFactory.getLogger(RefundController.class);
	
	private String content = "name执行了A page的button操作，操作记录的id是 nowId";
	
	@Autowired
	private RefundService refundService ;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private CsCommonService csCommonService;
	
	@Autowired
	private RecordOperateService recordOperateService;
	
	@Value("${filePath}")
	private String filePath;
	
	@Value("${userTerminal}")
	private String userTerminal;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(HttpServletRequest request,Integer page, Byte status, String orderNumber, Model model){
		findPage(request,page, status, orderNumber, model);
		return "task/refund/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String info(HttpServletRequest request,Integer page, Byte status, String orderNumber, Model model){
		findPage(request,page, status, orderNumber, model);
		return "task/refund/pageRefund";
	}
	
	private void findPage(HttpServletRequest request,Integer page, Byte status, String orderNumber, Model model){
		Customer customer = sessionService.getLoginInfo(request);
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Object> refund = refundService.findPages(page, status, orderNumber,customer.getId());
		model.addAttribute("refund", refund);
	}
	
	@RequestMapping(value="refundeDetails/{id}",method=RequestMethod.GET)
	public String getRefundeDetails(@PathVariable("id")Integer id,Model model){
		model.addAttribute("refundDetails",refundService.getRefundDetails(id));
		model.addAttribute("refundRecord",refundService.getRefundByDetailRecord(id));
		return "task/refundDetails/details";
	}
	
	@RequestMapping(value="addRefundMark",method=RequestMethod.POST)
	public String addRefundMark(HttpServletRequest request,int refundId,String content,Model model){
		Customer customer = sessionService.getLoginInfo(request);
		int customerId = customer.getId();
	
		refundService.addRefundMark(refundId,content,customerId);
		model.addAttribute("refundRecord",refundService.getRefundByDetailRecord(refundId));
		return "task/refundDetails/refundRecord";
	}
	
	@RequestMapping(value="{id}/updsateRefundStatus",method=RequestMethod.GET)
	public String updsateRefundStatus(HttpServletRequest request,@PathVariable("id") int id,Model model){
		operationRefundContent(request,"标记退款完成",id);
		
		refundService.updsateRefundStatus(id,CsRefund.STATIC_2);
		
		model.addAttribute("refundDetails",refundService.getRefundDetails(id));
		model.addAttribute("refundRecord",refundService.getRefundByDetailRecord(id));
		
		return "task/refundDetails/details";
	}
	
	@RequestMapping(value="{id}/updsateListRefundStatus",method=RequestMethod.GET)
	public String updsateListRefundStatus(HttpServletRequest request,@PathVariable("id") int id,Integer page, Byte status, String orderNumber,Model model){
		operationRefundContent(request,"标记退款完成",id);
		
		refundService.updsateRefundStatus(id,CsRefund.STATIC_2);
		findPage(request,page, status, orderNumber, model);
		return "task/refund/list";
	}
	
	@RequestMapping(value="{id}/updsateRefundDeStatus",method=RequestMethod.GET)
	public String updsateRefundDeStatus(HttpServletRequest request,@PathVariable("id") int id,Integer page, Byte status, String orderNumber,Model model){
		
		operationRefundContent(request,"取消",id);
		
		refundService.updsateRefundStatus(id,CsRefund.STATIC_3);
		findPage(request,page, status, orderNumber, model);
		return "task/refund/list";
	}
	/**
	 * 分派所需人员下拉框
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "dispatch", method = RequestMethod.GET)
	public String dispatch(Model model) {
		List<Customer> customers = csCommonService.findDispatchUsers();
		model.addAttribute("customers", customers);
		return "cs/dispatch";
	}
	
	@RequestMapping(value = "dispatch", method = RequestMethod.POST)
	public void dispatch(HttpServletResponse response, String ids, Integer customerId, String customerName) {
		refundService.dispatch(ids, customerId, customerName);
	}
	
	/**
     * 上传文件
     * 
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping(value = "{id}/upload/tempImage", method = RequestMethod.POST)
    @ResponseBody
    public String tempImage(@PathVariable("id") int id,@RequestParam(value="img") MultipartFile img, HttpServletRequest request) {
        try {
        	String joinpath="";
        	joinpath = HttpFile.upload(img, userTerminal+id+"/RefundCertificate/");
        	refundService.updateRefund(id,joinpath);
        	if("上传失败".equals(joinpath) || "同步上传失败".equals(joinpath))
        		return "false";
        	joinpath = filePath+joinpath;
        		return joinpath;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    public void operationRefundContent(HttpServletRequest request,String button,int id){
    	Customer customer = sessionService.getLoginInfo(request);
		String contentNew = content.replace("name", customer.getName())
				.replace("A page", "任务退款页面")
				.replace("button", button)
				.replace("nowId", String.valueOf(id));
		recordOperateService.saveOperateRecord(customer.getId(), customer.getName(), customer.getTypes(), OperateRecord.TYPES_REFUND, contentNew, id);
		
    }
}
